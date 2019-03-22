// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;
import java.util.List;
import java.util.Collection;
import java.util.Collections;
import java.util.Arrays;
import java.util.ArrayList;
import java.lang.invoke.SerializedLambda;
import com.alu.hal.streaming.exception.HalWifiException;
import org.apache.spark.sql.api.java.UDF2;
import org.apache.spark.sql.types.DataTypes;
import com.alu.hal.streaming.config.SparkConfigHelper;
import org.apache.spark.SparkConf;
import org.apache.log4j.Logger;
import org.apache.spark.sql.SparkSession;

public class HalSparkSession
{
    private static transient SparkSession instance;
    private static Logger LOG;
    
    public static SparkSession getInstance() {
        if (HalSparkSession.instance == null) {
            HalSparkSession.LOG.debug("Creating HAL Spark session...");
            final SparkConf sparkConf = SparkConfigHelper.configure(new SparkConf());
            SparkSession.setDefaultSession(HalSparkSession.instance = SparkSession.builder().config(sparkConf).enableHiveSupport().getOrCreate());
            registerUdfs(HalSparkSession.instance);
            setConfiguration(HalSparkSession.instance);
            HalSparkSession.LOG.debug("HAL Spark session created");
        }
        return HalSparkSession.instance;
    }
    
    private static void registerUdfs(final SparkSession sparkSession) {
        HalSparkSession.LOG.debug("Register UDF toDate");
        sparkSession.udf().register("toDate", timestamp -> {
            final Date d = new Date(timestamp * 1000L);
            return new SimpleDateFormat("yyyy-MM-dd").format(d);
        }, DataTypes.StringType);
        HalSparkSession.LOG.debug("Register UDF toHour");
        sparkSession.udf().register("toHour", timestamp -> {
            final Date d = new Date(timestamp * 1000L);
            final Calendar cal = new GregorianCalendar();
            cal.setTime(d);
            return cal.get(11);
        }, DataTypes.IntegerType);
        HalSparkSession.LOG.debug("Register UDF toTime");
        sparkSession.udf().register("toTime", timestamp -> {
            final Date d = new Date(timestamp * 1000L);
            final Calendar cal = new GregorianCalendar();
            cal.setTime(d);
            final String quarter = String.format("%02d", cal.get(12) / 15 * 15);
            return String.format("%02d", cal.get(11)) + quarter;
        }, DataTypes.StringType);
        HalSparkSession.LOG.debug("Register UDF toQuarter");
        sparkSession.udf().register("toQuarter", timestamp -> {
            final Date d = new Date(timestamp * 1000L);
            final Calendar cal = new GregorianCalendar();
            cal.setTime(d);
            return cal.get(12) / 15;
        }, DataTypes.IntegerType);
        HalSparkSession.LOG.debug("Register UDF toQuarterMinute");
        sparkSession.udf().register("toQuarterMinute", timestamp -> {
            final Date d = new Date(timestamp * 1000L);
            final Calendar cal = new GregorianCalendar();
            cal.setTime(d);
            return cal.get(12) % 15;
        }, DataTypes.IntegerType);
        HalSparkSession.LOG.debug("Register UDF greatestFromStr");
        sparkSession.udf().register("greatestFromStr", str -> {
            if (str != null) {
                final List<Double> doubleList = new ArrayList<Double>();
                Arrays.stream(str.split(",")).forEach(s -> doubleList.add(Double.valueOf(s.trim())));
                return Collections.max((Collection<?>)doubleList);
            }
            return null;
        }, DataTypes.DoubleType);
        HalSparkSession.LOG.debug("Register UDF maxThroughput");
        sparkSession.udf().register("maxThroughput", (UDF2)maxTrouughputUDF(), DataTypes.DoubleType);
        addCustomHiveUdaf(sparkSession);
    }
    
    private static UDF2<Integer, Integer, ?> expectedRetransmissionsPerc() {
        return (UDF2<Integer, Integer, ?>)((AssocDeviceSignalStrength, AssocDeviceStatsPacketsSent) -> {
            final Integer sum = null;
            return sum;
        });
    }
    
    private static UDF2<String, String, ?> maxTrouughputUDF() {
        return (UDF2<String, String, ?>)((RadioOperatingFrequencyBand, RadioOperatingStandards) -> {
            Double capacity = null;
            if (RadioOperatingStandards == null || RadioOperatingFrequencyBand == null) {
                return null;
            }
            if (RadioOperatingStandards.contains("n")) {
                capacity = 150.0;
            }
            else {
                if (RadioOperatingFrequencyBand.contains("2.4")) {
                    return getCapacityFor2_4GhzBand(RadioOperatingFrequencyBand, RadioOperatingStandards);
                }
                if (RadioOperatingFrequencyBand.contains("5")) {
                    return getCapacityFor5GhzBand(RadioOperatingFrequencyBand, RadioOperatingStandards);
                }
            }
            return capacity;
        });
    }
    
    private static Double getCapacityFor2_4GhzBand(final String RadioOperatingFrequencyBand, final String RadioOperatingStandards) {
        Double capacity = null;
        if (RadioOperatingStandards.contains("b")) {
            capacity = 11.0;
        }
        else if (RadioOperatingStandards.contains("g")) {
            capacity = 54.0;
        }
        return capacity;
    }
    
    private static Double getCapacityFor5GhzBand(final String RadioOperatingFrequencyBand, final String RadioOperatingStandards) {
        Double capacity = null;
        if (RadioOperatingStandards.contains("ac")) {
            capacity = 1300.0;
        }
        else if (RadioOperatingStandards.contains("a")) {
            capacity = 54.0;
        }
        return capacity;
    }
    
    private static void setConfiguration(final SparkSession sparkSession) {
        HalSparkSession.LOG.debug("Set additional hive and spark SQL conf hive.exec.dynamic.partition.mode, hive.exec.dynamic.partition, spark.sql.parquet.compression.codec, parquet.compression");
        sparkSession.conf().set("hive.exec.dynamic.partition.mode", "nonstrict");
        sparkSession.conf().set("hive.exec.dynamic.partition", "true");
        sparkSession.conf().set("spark.sql.parquet.compression.codec", "snappy");
        sparkSession.conf().set("parquet.compression", "snappy");
    }
    
    private static void addCustomHiveUdaf(final SparkSession sparkSession) {
        HalSparkSession.LOG.debug("Add custom UDAFs");
        try {
            sparkSession.sql("CREATE TEMPORARY FUNCTION connection_quality_score_udaf AS 'com.alu.motive.hal.udf.hive.udaf.GenericConnectionQualityScoreUDAF'");
            sparkSession.sql("CREATE TEMPORARY FUNCTION connection_quality_score_retrans_perc_udaf AS 'com.alu.motive.hal.udf.hive.udaf.ConnectionQualityScoreWithRetransPercUDAF'");
            sparkSession.sql("CREATE TEMPORARY FUNCTION connection_signal_score_udaf AS 'com.alu.motive.hal.udf.hive.udaf.GenericConnectionSignalScoreUDAF'");
            sparkSession.sql("CREATE TEMPORARY FUNCTION connection_failure_score_udaf AS 'com.alu.motive.hal.udf.hive.udaf.ConnectionFailureScoreUDAF'");
            sparkSession.sql("CREATE TEMPORARY FUNCTION associations_obs_udaf AS 'com.alu.motive.hal.udf.hive.udaf.AssociationsObsUDAF'");
            sparkSession.sql("CREATE TEMPORARY FUNCTION expected_retransmissions_perc_udf AS 'com.alu.motive.hal.udf.hive.udaf.ExpectedRetransmissionsPercUDAF'");
            sparkSession.sql("CREATE TEMPORARY FUNCTION lookupMacAddress AS 'com.alu.motive.hal.udf.hive.udf.LookupMacAddress'");
            sparkSession.sql("CREATE TEMPORARY FUNCTION connection_signal_observation_udaf AS 'com.alu.motive.hal.udf.hive.udaf.ConnectionSignalObservationUDAF'");
            sparkSession.sql("CREATE TEMPORARY FUNCTION coverage_observation_assoc_dvc_udf AS 'com.alu.motive.hal.udf.hive.udf.AssociatedDeviceWiFiCoverageScoreObservationUDF'");
            sparkSession.sql("CREATE TEMPORARY FUNCTION coverage_score_assoc_dvc_udaf AS 'com.alu.motive.hal.udf.hive.udaf.AssociatedDeviceWifiCoverageScoreUDAF'");
            sparkSession.sql("CREATE TEMPORARY FUNCTION coverage_observation_mgd_dvc_and_home_udf AS 'com.alu.motive.hal.udf.hive.udf.ManagedDeviceWiFiCoverageObservationUDF'");
            sparkSession.sql("CREATE TEMPORARY FUNCTION coverage_score_mgd_dvc_udaf AS 'com.alu.motive.hal.udf.hive.udaf.ManagedDeviceWiFiCoverageScoreUDAF'");
            sparkSession.sql("CREATE TEMPORARY FUNCTION coverage_score_home_udaf AS 'com.alu.motive.hal.udf.hive.udaf.HomeNetworkCoverageScoreUDAF'");
            sparkSession.sql("CREATE TEMPORARY FUNCTION contention_observation_radio_udf AS 'com.alu.motive.hal.udf.hive.udf.RadioWifiContentionObservationUDF'");
            sparkSession.sql("CREATE TEMPORARY FUNCTION contention_score_radio_udaf AS 'com.alu.motive.hal.udf.hive.udaf.RadioWifiContentionScoreUDAF'");
            sparkSession.sql("CREATE TEMPORARY FUNCTION throughput_observation_radio_udf AS 'com.alu.motive.hal.udf.hive.udf.RadioWifiThroughputObservationUDF'");
            sparkSession.sql("CREATE TEMPORARY FUNCTION throughput_observation_mgd_dvc_and_home_udaf AS 'com.alu.motive.hal.udf.hive.udaf.ManagedDeviceAndHomeNetworkThroughputObservationUDAF'");
            sparkSession.sql("SET parquet.compression=SNAPPY");
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new HalWifiException("unable to execute query ", e);
        }
    }
    
    static {
        HalSparkSession.instance = null;
        HalSparkSession.LOG = Logger.getLogger(HalSparkSession.class);
    }
}
