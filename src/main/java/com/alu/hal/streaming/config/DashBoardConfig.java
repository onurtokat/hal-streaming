// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.config;

import java.util.stream.Collector;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Properties;
import java.util.Map;
import java.util.function.Predicate;
import org.apache.log4j.Logger;

public class DashBoardConfig
{
    private static final String PROPERTIES = "/dashboard.properties";
    private static final String WIFI_PROPERTIES = "/tr69wificonfig.properties";
    private static final String MULTI_VALUE_SEPARATOR = ",";
    public static final String START_STRING_SPARK_PROPERTIES = "spark.";
    public static final String START_STRING_TR69_TOPIC_CONFIG = "kafka.tr69reports.topic.config";
    private static Logger LOG;
    public static String SPARK_CHECKPOINT_URL;
    public static String HDFS_URL;
    public static String SPARK_SERIALIZER;
    public static final String SPARK_EXECUTOR_CORES = "spark.executor.cores";
    public static final String SPARK_EXECUTOR_INSTANCES = "spark.executor.instances";
    public static final String SPARK_WINDOW_PARTITIONS = "spark.sql.window.partitions";
    public static final String SPARK_DEFAULT_PARALLELISM = "spark.default.parallelism";
    public static final String DELTA_TTL = "device.report.ttl";
    public static final String MIN_RADIO_CHANNEL_24GHZ = "min.channel.2_4GHz";
    public static final String MAX_RADIO_CHANNEL_24GHZ = "max.channel.2_4GHz";
    public static final String MIN_RADIO_CHANNEL_5GHZ = "min.channel.5_0GHz";
    public static final String MAX_RADIO_CHANNEL_5GHZ = "max.channel.5_0GHz";
    public static String DRIVER_BATCH_PROCESS_INTERVAL;
    public static String DRIVER_AGG_WINDOW_LENGTH_INTERVAL;
    public static String DRIVER_AGG_WINDOW_SLIDE_INTERVAL;
    public static String INVENTORY_DATA_FOLDER;
    public static String INVENTORY_DATA_WINDOW;
    public static String INVENTORY_DATA_SEPARATOR;
    public static String INVENTORY_DATA_DEVICE_ID_INDEX;
    public static String INVENTORY_DATA_ACCOUNT_ID_INDEX;
    public static String INVENTORY_DATA_MAC_ADRESS_OF_DEVICE_INDEX;
    public static String INVENTORY_DATA_IS_EXTENDER_INDEX;
    public static String INVENTORY_DATA_AUTO_CHANNEL_SUPPORTED_INDEX;
    public static String MAC_FILE;
    public static String UDF_THRESHOLDS_FILE;
    public static String COMPUTE_SWND_KPIS;
    public static String EXPORT_TRANSPOSE_TABLES;
    public static String COMPUTE_RECOMMENDATIONS;
    public static final String LOG_EXECUTION_PLAN = "log.execution.plan";
    public static final String KPI_PERSISTENCE_STORAGE_LEVEL = "kpi.persistence.storage.level";
    public static final Predicate<Map.Entry<Object, Object>> SINGLE_VALUE_SPARK_RUNTIME_CONFIG_PREDICATE;
    public static final Predicate<Map.Entry<Object, Object>> TR69_TOPIC_CONFIG_PREDICATE;
    private final Properties properties;
    public static final String HQL_HDFS_FILE_FOLDER = "hql.hdfs.file.folder";
    public static final String HQL_HDFS_TEMPLATES_FOLDER = "hql.hdfs.templates.folder";
    public static final String AVSC_HDFS_FILE_FOLDER = "avsc.hdfs.file.folder";
    public static final String AVSC_HDFS_TEMPLATES_FOLDER = "avsc.hdfs.templates.folder";
    public static final String VIEW_TEMPLATES_HDFS_FOLDER = "hql.hdfs.view.templates.folder";
    
    private DashBoardConfig() {
        this.properties = new Properties();
        try {
            this.properties.load(DashBoardConfig.class.getResourceAsStream("/dashboard.properties"));
            this.properties.load(DashBoardConfig.class.getResourceAsStream("/tr69wificonfig.properties"));
        }
        catch (Exception e) {
            DashBoardConfig.LOG.warn("/dashboard.properties or /tr69wificonfig.properties not found");
        }
    }
    
    public static DashBoardConfig instance() {
        return SingletonHolder.INSTANCE;
    }
    
    public Map<String, String> getStringsWithPredicate(final Predicate<Map.Entry<Object, Object>> predicate) {
        return this.properties.entrySet().stream().filter(entry -> predicate.test(entry)).collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
    }
    
    public String getString(final String key) {
        return instance().properties.getProperty(key);
    }
    
    public String getString(final String key, final String defaultValue) {
        return instance().properties.getProperty(key, defaultValue);
    }
    
    public int getInteger(final String key) {
        return Integer.parseInt(instance().properties.getProperty(key));
    }
    
    public int getInteger(final String key, final int defaultValue) {
        return Integer.parseInt(instance().properties.getProperty(key, Integer.toString(defaultValue)));
    }
    
    public long getLong(final String key) {
        return Long.parseLong(instance().properties.getProperty(key));
    }
    
    public long getLong(final String key, final long defaultValue) {
        return Long.parseLong(instance().properties.getProperty(key, Long.toString(defaultValue)));
    }
    
    public List<String> getListOfString(final String key) {
        return Pattern.compile(",").splitAsStream(this.properties.getProperty(key, "")).map((Function<? super String, ?>)String::trim).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
    }
    
    public boolean getBoolean(final String key, final boolean defaultValue) {
        return Boolean.valueOf(instance().properties.getProperty(key, Boolean.toString(defaultValue)));
    }
    
    static {
        DashBoardConfig.LOG = Logger.getLogger(DashBoardConfig.class);
        DashBoardConfig.SPARK_CHECKPOINT_URL = "checkpoint.url";
        DashBoardConfig.HDFS_URL = "hdfs.url";
        DashBoardConfig.SPARK_SERIALIZER = "spark.serializer";
        DashBoardConfig.DRIVER_BATCH_PROCESS_INTERVAL = "driver.batch.process.interval";
        DashBoardConfig.DRIVER_AGG_WINDOW_LENGTH_INTERVAL = "driver.agg.window.length.interval";
        DashBoardConfig.DRIVER_AGG_WINDOW_SLIDE_INTERVAL = "driver.agg.window.slide.interval";
        DashBoardConfig.INVENTORY_DATA_FOLDER = "inventory.data.folder";
        DashBoardConfig.INVENTORY_DATA_WINDOW = "inventory.data.window.length.interval";
        DashBoardConfig.INVENTORY_DATA_SEPARATOR = "inventory.data.separator";
        DashBoardConfig.INVENTORY_DATA_DEVICE_ID_INDEX = "inventory.data.deviceID.index";
        DashBoardConfig.INVENTORY_DATA_ACCOUNT_ID_INDEX = "inventory.data.accountID.index";
        DashBoardConfig.INVENTORY_DATA_MAC_ADRESS_OF_DEVICE_INDEX = "inventory.data.MACAdressOfDevice.index";
        DashBoardConfig.INVENTORY_DATA_IS_EXTENDER_INDEX = "inventory.data.isExtender.index";
        DashBoardConfig.INVENTORY_DATA_AUTO_CHANNEL_SUPPORTED_INDEX = "inventory.data.autoChannelSupported.index";
        DashBoardConfig.MAC_FILE = "mac.file";
        DashBoardConfig.UDF_THRESHOLDS_FILE = "udf.thresholds.file";
        DashBoardConfig.COMPUTE_SWND_KPIS = "compute.swnd.kpis";
        DashBoardConfig.EXPORT_TRANSPOSE_TABLES = "export.transpose.tables";
        DashBoardConfig.COMPUTE_RECOMMENDATIONS = "compute.recommendations";
        SINGLE_VALUE_SPARK_RUNTIME_CONFIG_PREDICATE = (entry -> entry.getKey().startsWith("spark.") && !((String)entry.getValue()).contains(","));
        TR69_TOPIC_CONFIG_PREDICATE = (entry -> entry.getKey().startsWith("kafka.tr69reports.topic.config"));
    }
    
    private static class SingletonHolder
    {
        public static final DashBoardConfig INSTANCE;
        
        static {
            INSTANCE = new DashBoardConfig(null);
        }
    }
}
