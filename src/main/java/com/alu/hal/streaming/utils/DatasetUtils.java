// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.utils;

import java.time.Duration;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.Column;
import java.time.Instant;
import org.apache.spark.sql.Row;
import com.alu.hal.streaming.hive.model.SparkTempView;
import org.apache.spark.sql.Dataset;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.log4j.Logger;

public class DatasetUtils
{
    private static Logger LOG;
    private static final String TS_COLUMN = "TS";
    
    public static <T> JavaDStream<T> clearAllCachedDataSets(final JavaDStream<T> javaDStream) {
        return (JavaDStream<T>)javaDStream.transform(rdd -> {
            DatasetUtils.LOG.debug("Clearing cache...");
            HalSparkSession.getInstance().catalog().clearCache();
            return rdd;
        });
    }
    
    public static <T> void registerAsTempView(final Dataset<T> dataset, final SparkTempView sparkTempView) {
        DatasetUtils.LOG.debug("Register dataset as SparkTempView " + sparkTempView);
        dataset.createOrReplaceTempView(sparkTempView.getTempViewName());
    }
    
    public static Dataset<Row> addHivePartitionsColumns(final Dataset<Row> dataFrame, final String tableName) {
        Instant start = null;
        if (DatasetUtils.LOG.isDebugEnabled()) {
            start = Instant.now();
            DatasetUtils.LOG.debug("Start adding timepartition cols day_part and time_part to " + tableName);
        }
        final Dataset<Row> dataFrameWithPartitionColumns = (Dataset<Row>)dataFrame.withColumn("day_part", functions.callUDF("toDate", new Column[] { dataFrame.col("TS") })).withColumn("time_part", functions.callUDF("toTime", new Column[] { dataFrame.col("TS") }));
        if (DatasetUtils.LOG.isDebugEnabled()) {
            DatasetUtils.LOG.debug("adding timepartition cols to unifiedWithRefData finished in " + Duration.between(start, Instant.now()).toMillis() + " ms");
        }
        return dataFrameWithPartitionColumns;
    }
    
    static {
        DatasetUtils.LOG = Logger.getLogger(DatasetUtils.class);
    }
}
