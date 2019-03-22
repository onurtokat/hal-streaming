// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.utils;

import org.apache.spark.SparkContext;
import com.alu.hal.streaming.config.WifiConfigSingleton;
import com.alu.hal.streaming.config.WifiSparkConfig;
import scala.Function1;
import com.alu.hal.streaming.kafka.KafkaWriter;
import com.alu.hal.streaming.config.KafkaTopicConfig;
import org.apache.spark.sql.Dataset;
import com.alu.hal.streaming.process.WifiKpisComputer;
import com.alu.hal.streaming.config.DashBoardConfig;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.types.StructType;
import com.alu.hal.streaming.hive.model.KafkaTopicId;
import com.alu.hal.streaming.hive.model.SparkTempView;
import com.alu.hal.streaming.config.WifiQueries;
import org.apache.spark.sql.Row;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.log4j.Logger;

public class KpiUtils
{
    private static Logger LOG;
    private static final String INSIGHTS_PHOENIX_TABLE = "MONITORED_POINT_INSIGHTS";
    private static final String TS_COLUMN = "TS";
    
    public static JavaDStream<Row> calculateAndExport(final JavaDStream<Row> dStream, final WifiQueries.QueryNames queryName, final SparkTempView tempTable, final KafkaTopicId kafkaTopicId) {
        final StructType[] kpiSchema = { null };
        final JavaDStream<Row> kpiDStream = (JavaDStream<Row>)dStream.transform(rowRDD -> {
            final SparkContext sc = rowRDD.context();
            final WifiQueries queries = ((WifiSparkConfig)WifiConfigSingleton.getInstance(sc).getValue()).getWifiQueries();
            return calculate(queries, queryName, tempTable, kpiSchema);
        });
        kpiDStream.foreachRDD(kpiRDD -> {
            final Dataset<Row> dataFrame = DatasetUtils.addHivePartitionsColumns((Dataset<Row>)HalSparkSession.getInstance().createDataFrame(kpiRDD, kpiSchema[0]), tempTable.getTempViewName());
            dataFrame.foreachPartition((Function1)new KafkaWriter(KafkaTopicConfig.instance().getAvroKafkaTopic(kafkaTopicId), HalSparkSession.getInstance().sparkContext()));
        });
        return kpiDStream;
    }
    
    private static JavaRDD<Row> calculate(final WifiQueries wifiQueries, final WifiQueries.QueryNames queryName, final SparkTempView tempTable, final StructType[] schema) {
        KpiUtils.LOG.info("executing query: " + queryName);
        final String query = wifiQueries.getQuery(queryName);
        Dataset<Row> insightsDataFrame;
        if (DashBoardConfig.instance().getBoolean(DashBoardConfig.COMPUTE_SWND_KPIS, true)) {
            insightsDataFrame = WifiKpisComputer.calculateKpisByStaticQuery(query, tempTable, true);
        }
        else {
            insightsDataFrame = WifiKpisComputer.calculateKpisByStaticQuery(query, tempTable, false);
        }
        schema[0] = insightsDataFrame.schema();
        KpiUtils.LOG.info("resulting schema: " + schema[0].mkString(","));
        return (JavaRDD<Row>)insightsDataFrame.toJavaRDD();
    }
    
    static {
        KpiUtils.LOG = Logger.getLogger(KpiUtils.class);
    }
}
