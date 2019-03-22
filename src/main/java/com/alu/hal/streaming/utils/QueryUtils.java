// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.utils;

import java.util.Arrays;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.Dataset;
import org.apache.log4j.Logger;

public class QueryUtils
{
    public static final String DELIMITER = "^";
    private static Logger LOG;
    
    public static void displayDataSetRows(final Dataset<Row> dataFrame) {
        if (dataFrame != null && dataFrame.count() > 0L) {
            QueryUtils.LOG.debug("Query computed table columns:" + Arrays.toString(dataFrame.columns()));
            QueryUtils.LOG.debug("Query computed table rows:" + dataFrame.count());
            dataFrame.collectAsList().forEach(row -> QueryUtils.LOG.debug(row.mkString("^")));
        }
        else {
            QueryUtils.LOG.debug("no rows to display");
        }
    }
    
    static {
        QueryUtils.LOG = Logger.getLogger(QueryUtils.class);
    }
}
