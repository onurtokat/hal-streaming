// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.config;

import scala.reflect.ClassTag$;
import java.util.Map;
import com.alu.hal.streaming.process.DashBoardTr69WifiSparkDriver;
import java.util.HashMap;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import scala.reflect.ClassTag;
import org.apache.log4j.Logger;
import java.io.Serializable;

public class WifiConfigSingleton implements Serializable
{
    private static final long serialVersionUID = 2096243311875334208L;
    private static final DashBoardConfig CONFIG;
    private static Logger LOG;
    private static final ClassTag<WifiSparkConfig> WIFI_SPARK_CONFIG_CLASS_TAG;
    private static volatile Broadcast<WifiSparkConfig> instance;
    
    public static Broadcast<WifiSparkConfig> getInstance(final JavaSparkContext jsc) {
        WifiConfigSingleton.LOG.debug("WifiConfigSingleton instance=" + WifiConfigSingleton.instance);
        if (WifiConfigSingleton.instance == null) {
            synchronized (WifiConfigSingleton.class) {
                if (WifiConfigSingleton.instance == null) {
                    final WifiSparkConfig wifiSparkConfig = createWifiSparkConfig();
                    WifiConfigSingleton.instance = (Broadcast<WifiSparkConfig>)jsc.broadcast((Object)wifiSparkConfig);
                }
            }
        }
        return WifiConfigSingleton.instance;
    }
    
    public static Broadcast<WifiSparkConfig> getInstance(final SparkContext sc) {
        WifiConfigSingleton.LOG.debug("WifiConfigSingleton instance=" + WifiConfigSingleton.instance);
        if (WifiConfigSingleton.instance == null) {
            synchronized (WifiConfigSingleton.class) {
                if (WifiConfigSingleton.instance == null) {
                    final WifiSparkConfig wifiSparkConfig = createWifiSparkConfig();
                    WifiConfigSingleton.instance = (Broadcast<WifiSparkConfig>)sc.broadcast((Object)wifiSparkConfig, (ClassTag)WifiConfigSingleton.WIFI_SPARK_CONFIG_CLASS_TAG);
                }
            }
        }
        return WifiConfigSingleton.instance;
    }
    
    private static WifiSparkConfig createWifiSparkConfig() {
        WifiConfigSingleton.LOG.debug("creating new instance for WifiSparkConfig...");
        final Map<String, String> broadcastPropertiesMap = new HashMap<String, String>();
        broadcastPropertiesMap.put(DashBoardConfig.COMPUTE_RECOMMENDATIONS, WifiConfigSingleton.CONFIG.getString(DashBoardConfig.COMPUTE_RECOMMENDATIONS, "true"));
        final String checkPointUrl = WifiConfigSingleton.CONFIG.getString(DashBoardConfig.SPARK_CHECKPOINT_URL);
        broadcastPropertiesMap.put(DashBoardConfig.HDFS_URL, checkPointUrl.substring(0, checkPointUrl.lastIndexOf("/")));
        return new WifiSparkConfig(DashBoardTr69WifiSparkDriver.class.getResource("/HAL_ModelType-Mapping.xml"), WifiConfigSingleton.class.getResource("/HAL_DataMapping-TR98.xml"), WifiConfigSingleton.class.getResource("/HAL_DataMapping-TR181.xml"), WifiConfigSingleton.class.getResource("/HAL_Unified-Mapping.xml"), WifiConfigSingleton.class.getResource("/"), broadcastPropertiesMap);
    }
    
    public static void update(final SparkContext jsc) {
        synchronized (WifiConfigSingleton.class) {
            WifiSparkConfig wifiSparkConfig = null;
            if (WifiConfigSingleton.instance != null) {
                WifiConfigSingleton.instance.unpersist(true);
            }
            wifiSparkConfig = createWifiSparkConfig();
            WifiConfigSingleton.LOG.info("broadcasted HQL queries");
            WifiConfigSingleton.instance = (Broadcast<WifiSparkConfig>)jsc.broadcast((Object)wifiSparkConfig, (ClassTag)WifiConfigSingleton.WIFI_SPARK_CONFIG_CLASS_TAG);
        }
    }
    
    static {
        CONFIG = DashBoardConfig.instance();
        WifiConfigSingleton.LOG = Logger.getLogger(WifiConfigSingleton.class);
        WIFI_SPARK_CONFIG_CLASS_TAG = ClassTag$.MODULE$.apply(WifiSparkConfig.class);
        WifiConfigSingleton.instance = null;
    }
}
