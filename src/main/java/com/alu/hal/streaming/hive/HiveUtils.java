// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.hive;

import java.util.stream.Collectors;
import java.util.Arrays;
import com.alu.hal.streaming.hive.model.HiveTable;
import com.alu.hal.streaming.hive.model.SparkTempView;
import com.alu.hal.streaming.exception.HalWifiException;

import java.time.Duration;
import java.time.Instant;
import com.alu.hal.streaming.utils.HalSparkSession;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.Dataset;
import com.alu.hal.streaming.config.DashBoardConfig;
import org.apache.log4j.Logger;

public class HiveUtils
{
    private static Logger LOG;
    private static DashBoardConfig CONFIG;
    private static boolean explain;
    
    public static Dataset<Row> loadTable(final String tableName) {
        Dataset<Row> dataframe = null;
        HiveUtils.LOG.info("loading hive table..." + tableName);
        try {
            dataframe = executeQuery("select * from " + tableName);
        }
        catch (Exception e) {
            HiveUtils.LOG.error("unable to load data from hive table");
        }
        return dataframe;
    }
    
    public static void mergeTables(Dataset<Row> table1, Dataset<Row> table2, final Column joinColumn, final String joinType) {
        HiveUtils.LOG.info("merging data between " + table1 + " and " + table2 + " joining on " + joinColumn.toString());
        try {
            table1 = (Dataset<Row>)HalSparkSession.getInstance().createDataFrame(table1.rdd(), table1.schema());
            table2 = (Dataset<Row>)HalSparkSession.getInstance().createDataFrame(table2.rdd(), table2.schema());
            HiveUtils.LOG.info("new dataFrames were created");
            final Dataset<Row> rdd = (Dataset<Row>)table1.join((Dataset)table2, joinColumn, joinType);
            HiveUtils.LOG.info("join was done");
            rdd.printSchema();
        }
        catch (Exception e) {
            HiveUtils.LOG.error("unable to join tables", e);
        }
    }
    
    public static Dataset<Row> executeQuery(final String sql) {
        Dataset<Row> dataset = null;
        HiveUtils.LOG.info("executing hql ..." + sql);
        try {
            final Instant start = Instant.now();
            if (HiveUtils.explain) {
                System.out.println("Execution Plan for HQL: " + sql);
                System.out.println("==============");
                HalSparkSession.getInstance().sql(sql).explain(true);
            }
            dataset = (Dataset<Row>)HalSparkSession.getInstance().sql(sql);
            HiveUtils.LOG.info(" executeQuery time taken = " + Duration.between(start, Instant.now()).toMillis() + "ms");
        }
        catch (Exception e) {
            HiveUtils.LOG.error("unable to execute query ", e);
            throw new HalWifiException("unable to execute query ", e);
        }
        return dataset;
    }
    
    public static void exportDataSetAsHiveTable(final SparkTempView sourceTable, final HiveTable destTable, final Dataset<Row> dataFrame) {
        final long count = dataFrame.count();
        if (count > 0L) {
            final Instant start = Instant.now();
            dataFrame.toJavaRDD().coalesce(1);
            HiveUtils.LOG.info("exportDataFrameAsTable in " + destTable.getTableName() + " " + count + " from " + sourceTable.getTempViewName());
            final String query = "INSERT INTO TABLE " + destTable.getTableName() + " PARTITION (day, hour, quarter) SELECT " + Arrays.stream(dataFrame.columns()).collect(Collectors.joining(", ")) + ", toDate(TS) as day, toHour(TS) as hour, toQuarter(TS) as quarter FROM " + sourceTable.getTempViewName();
            executeQuery(query);
            final Instant end = Instant.now();
            HiveUtils.LOG.info(destTable.getTableName() + " hive insert time  =" + Duration.between(start, end).toMillis() + "ms");
        }
        else {
            HiveUtils.LOG.info("No data to export in the hive table " + destTable.getTableName());
        }
    }
    
    static {
        HiveUtils.LOG = Logger.getLogger(HiveUtils.class);
        HiveUtils.CONFIG = DashBoardConfig.instance();
        HiveUtils.explain = HiveUtils.CONFIG.getBoolean("log.execution.plan", false);
    }
}
