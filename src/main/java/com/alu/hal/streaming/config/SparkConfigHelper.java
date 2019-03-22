// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.config;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import scala.Tuple2;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import scala.collection.Seq;
import com.alu.hal.streaming.utils.ScalaUtils;
import org.apache.spark.SparkConf;
import org.apache.log4j.Logger;

public class SparkConfigHelper
{
    private static Logger LOG;
    private static final String SPARK_JARS = "spark.jars";
    public static String SERIALIZER;
    public static String KRYO_REGISTRATOR;
    private static final String MASTER_DEFAULT = "local[2]";
    private static final String APPNAME_DEFAULT = "WifiComputer";
    private static final String SERIALIZER_DEFAULT = "org.apache.spark.serializer.KryoSerializer";
    private static final String KRYO_REGISTRATOR_DEFAULT = "com.alu.hal.streaming.kryo.KryoRegistrator";
    
    public static SparkConf configure(final SparkConf sparkConf) {
        SparkConfigHelper.LOG.debug("Configuring SparkConf");
        sparkConf.setMaster("local[2]");
        sparkConf.setAppName("WifiComputer");
        sparkConf.set(SparkConfigHelper.SERIALIZER, "org.apache.spark.serializer.KryoSerializer");
        sparkConf.set(SparkConfigHelper.KRYO_REGISTRATOR, "com.alu.hal.streaming.kryo.KryoRegistrator");
        final Map<String, String> sparkConfMap = DashBoardConfig.instance().getStringsWithPredicate(DashBoardConfig.SINGLE_VALUE_SPARK_RUNTIME_CONFIG_PREDICATE);
        sparkConfMap.entrySet().forEach(entry -> sparkConf.set((String)entry.getKey(), (String)entry.getValue()));
        final List<String> SparkJars = DashBoardConfig.instance().getListOfString("spark.jars");
        if (!SparkJars.isEmpty()) {
            sparkConf.setJars((Seq)ScalaUtils.toScalaSeq(SparkJars));
        }
        if (SparkConfigHelper.LOG.isDebugEnabled()) {
            SparkConfigHelper.LOG.debug(toString(sparkConf));
        }
        return sparkConf;
    }
    
    public static String toString(final SparkConf sparkConf) {
        return "SparkConf =\n" + Arrays.stream(sparkConf.getAll()).map(tuple -> tuple._1() + ", " + (String)tuple._2()).collect((Collector<? super Object, ?, String>)Collectors.joining("\n"));
    }
    
    static {
        SparkConfigHelper.LOG = Logger.getLogger(SparkConfigHelper.class);
        SparkConfigHelper.SERIALIZER = "spark.serializer";
        SparkConfigHelper.KRYO_REGISTRATOR = "spark.kryo.registrator";
    }
}
