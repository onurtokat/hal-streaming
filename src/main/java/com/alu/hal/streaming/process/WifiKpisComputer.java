// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.process;

import java.time.Duration;
import com.alu.hal.streaming.utils.DatasetUtils;
import com.alu.hal.streaming.hive.HiveUtils;
import java.time.Instant;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.Dataset;
import com.alu.hal.streaming.hive.model.SparkTempView;
import org.apache.spark.storage.StorageLevel;
import com.alu.hal.streaming.config.DashBoardConfig;
import org.apache.log4j.Logger;

public class WifiKpisComputer
{
    private static Logger LOG;
    private static DashBoardConfig CONFIG;
    private static StorageLevel storageLevel;
    
    public static Dataset<Row> calculateKpisByStaticQuery(final String query, final SparkTempView sparkTempView, final boolean persist) {
        final Instant start = Instant.now();
        WifiKpisComputer.LOG.info("Calculating insight dataframe " + sparkTempView);
        Dataset<Row> kpiDF = HiveUtils.executeQuery(query);
        if (persist) {
            WifiKpisComputer.LOG.debug("Persisting insight dataframe " + sparkTempView + " with storageLevel" + WifiKpisComputer.storageLevel.description());
            kpiDF = (Dataset<Row>)kpiDF.persist(WifiKpisComputer.storageLevel);
        }
        DatasetUtils.registerAsTempView(kpiDF, sparkTempView);
        WifiKpisComputer.LOG.info(sparkTempView + " insight calculation time = " + Duration.between(start, Instant.now()).toMillis() + "ms");
        return kpiDF;
    }
    
    static {
        WifiKpisComputer.LOG = Logger.getLogger(WifiKpisComputer.class);
        WifiKpisComputer.CONFIG = DashBoardConfig.instance();
        WifiKpisComputer.storageLevel = StorageLevel.fromString(WifiKpisComputer.CONFIG.getString("kpi.persistence.storage.level", "MEMORY_ONLY_SER"));
    }
}
