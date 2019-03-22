// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.utils;

import java.io.Serializable;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.types.StructType;
import com.alu.hal.streaming.hive.model.SparkTempView;
import org.apache.spark.sql.Row;
import org.apache.spark.streaming.api.java.JavaDStream;

public class DstreamSQL
{
    public static Result execute(final JavaDStream<Row> inputDStream, final String sqlText, final SparkTempView resultTable, final String hdfsUrl) {
        final StructType[] schema = { null };
        final JavaDStream<Row> transformedDStream = (JavaDStream<Row>)inputDStream.transform(rowRDD -> {
            final Dataset<Row> outputDataFrame = (Dataset<Row>)HalSparkSession.getInstance().sql(sqlText);
            schema[0] = outputDataFrame.schema();
            DatasetUtils.registerAsTempView(outputDataFrame, resultTable);
            return outputDataFrame.toJavaRDD();
        });
        return new Result((JavaDStream)transformedDStream, schema[0], resultTable);
    }
    
    public static class Result implements Serializable
    {
        private final JavaDStream<Row> dStream;
        private final StructType outputSchema;
        private final SparkTempView sparkTempView;
        
        private Result(final JavaDStream<Row> dStream, final StructType outputSchema, final SparkTempView sparkTempView) {
            this.dStream = dStream;
            this.outputSchema = outputSchema;
            this.sparkTempView = sparkTempView;
        }
        
        public JavaDStream<Row> getdStream() {
            return this.dStream;
        }
        
        public StructType getOutputSchema() {
            return this.outputSchema;
        }
        
        public SparkTempView getSparkTempView() {
            return this.sparkTempView;
        }
    }
}
