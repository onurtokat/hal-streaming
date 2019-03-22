// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.utils;

import java.util.NoSuchElementException;
import java.time.Duration;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.Column;
import java.time.Instant;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.Dataset;
import com.alu.hal.streaming.hive.model.HiveTable;
import com.alu.hal.streaming.hive.model.SparkTempView;
import org.apache.log4j.Logger;

public class ExportUtils
{
    private static final String TS_COLUMN = "TS";
    private static final Logger LOG;
    
    public static void exportDataFrameAsHiveTable(final SparkTempView sourceTable, final HiveTable destTable, final Dataset<Row> dataFrame) {
        final Instant start = Instant.now();
        ExportUtils.LOG.info("export in hive in " + destTable.getTableName() + " from " + sourceTable.getTempViewName());
        final Dataset<Row> dataset = (Dataset<Row>)dataFrame.withColumn("day", functions.callUDF("toDate", new Column[] { dataFrame.col("TS") })).withColumn("time", functions.callUDF("toTime", new Column[] { dataFrame.col("TS") }));
        dataset.write().mode("append").option("spark.sql.parquet.compression.codec", "snappy").format("parquet").insertInto(destTable.getTableName());
        final Instant end = Instant.now();
        ExportUtils.LOG.info(destTable.getTableName() + "insertInto insert time  =" + Duration.between(start, end).toMillis() + "ms");
    }
    
    private static boolean isDataFrameEmpty(final Dataset<Row> dataFrame) {
        try {
            final Row firstRow = (Row)dataFrame.first();
            if (firstRow == null) {
                return true;
            }
        }
        catch (NoSuchElementException e) {
            return true;
        }
        return false;
    }
    
    static {
        LOG = Logger.getLogger(ExportUtils.class);
    }
}
