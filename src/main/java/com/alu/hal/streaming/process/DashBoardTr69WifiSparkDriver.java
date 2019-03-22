// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.process;

import java.util.Hashtable;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.api.java.JavaSparkContext;
import com.alu.hal.streaming.utils.HalSparkSession;
import org.apache.spark.streaming.Seconds;
import java.util.Map;
import java.lang.invoke.SerializedLambda;
import com.alu.hal.streaming.exception.WifiTemplateParseException;
import org.apache.spark.streaming.api.java.JavaDStream;
import com.alu.motive.hal.commons.dto.TR69DTO;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import java.util.Collection;
import com.alu.hal.streaming.utils.DatasetUtils;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import java.util.Collections;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.apache.kafka.clients.consumer.RangeAssignor;
import com.alu.motive.hal.commons.message.kafka.TR69MessageDeserializer;
import org.apache.kafka.common.serialization.BytesDeserializer;
import com.alu.hal.streaming.config.KafkaConsumerConfig;
import org.apache.spark.api.java.function.Function0;
import com.alu.hal.streaming.config.KafkaTopicConfig;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.log4j.Logger;
import com.alu.hal.streaming.config.DashBoardConfig;

public class DashBoardTr69WifiSparkDriver
{
    private static final DashBoardConfig CONFIG;
    private static Logger LOG;
    
    public static void main(final String[] args) throws Exception {
        try {
            final JavaStreamingContext ssc = createComputationStreamingContext();
            ssc.start();
            ssc.awaitTermination();
        }
        catch (Exception e) {
            DashBoardTr69WifiSparkDriver.LOG.error("error HAL tr69 streaming app, closing... ", e);
            throw e;
        }
    }
    
    private static JavaStreamingContext createComputationStreamingContext() {
        final String topic = KafkaTopicConfig.instance().getTr69reportsTopicName();
        final Function0<JavaStreamingContext> createStreamingContextFunction = (Function0<JavaStreamingContext>)(() -> {
            DashBoardTr69WifiSparkDriver.LOG.info("Create new spark streaming context with batch interval " + Seconds.apply(DashBoardTr69WifiSparkDriver.CONFIG.getLong(DashBoardConfig.DRIVER_BATCH_PROCESS_INTERVAL)) + " and checkpoint dir " + DashBoardTr69WifiSparkDriver.CONFIG.getString(DashBoardConfig.SPARK_CHECKPOINT_URL, "hdfs://quickstart.cloudera:8020/hal-checkpoints"));
            final SparkSession sparkSession = HalSparkSession.getInstance();
            final SparkContext sparkContext = sparkSession.sparkContext();
            final JavaSparkContext javaSparkContext = JavaSparkContext.fromSparkContext(sparkContext);
            final JavaStreamingContext javaStreamingContext = new JavaStreamingContext(javaSparkContext, Seconds.apply(DashBoardTr69WifiSparkDriver.CONFIG.getLong(DashBoardConfig.DRIVER_BATCH_PROCESS_INTERVAL)));
            if (javaStreamingContext == null) {
                throw new IllegalStateException("javaStreamingContext should have been initialized at this point");
            }
            javaStreamingContext.checkpoint(DashBoardTr69WifiSparkDriver.CONFIG.getString(DashBoardConfig.SPARK_CHECKPOINT_URL, "hdfs://quickstart.cloudera:8020/hal-checkpoints"));
            processData(javaStreamingContext, topic, getKafkaProps());
            DashBoardTr69WifiSparkDriver.LOG.info("new spark streaming context created");
            return javaStreamingContext;
        });
        DashBoardTr69WifiSparkDriver.LOG.info("Get spark streaming context from existing checkpoint directory or create new instance");
        final JavaStreamingContext jsc = JavaStreamingContext.getOrCreate(DashBoardTr69WifiSparkDriver.CONFIG.getString(DashBoardConfig.SPARK_CHECKPOINT_URL, "hdfs://quickstart.cloudera:8020/hal-checkpoints"), (Function0)createStreamingContextFunction);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                jsc.stop(true, true);
                DashBoardTr69WifiSparkDriver.LOG.info("streaming context shutdown successfully ...");
            }
        });
        DashBoardTr69WifiSparkDriver.LOG.info("Stream context is running and ready processing data from Kafka topic " + topic);
        return jsc;
    }
    
    private static KafkaConsumerConfig getKafkaProps() {
        final KafkaConsumerConfig kafkaProps = KafkaConsumerConfig.instance();
        ((Hashtable<String, Class<BytesDeserializer>>)kafkaProps.getProperties()).put("key.deserializer", BytesDeserializer.class);
        ((Hashtable<String, Class<TR69MessageDeserializer>>)kafkaProps.getProperties()).put("value.deserializer", TR69MessageDeserializer.class);
        ((Hashtable<String, String>)kafkaProps.getProperties()).put("partition.assignment.strategy", RangeAssignor.class.getName());
        if (DashBoardTr69WifiSparkDriver.LOG.isDebugEnabled()) {
            final String collect = kafkaProps.getProperties().entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue()).collect((Collector<? super Object, ?, String>)Collectors.joining(" , "));
            DashBoardTr69WifiSparkDriver.LOG.debug("Kafka properties configured : " + collect);
        }
        return kafkaProps;
    }
    
    public static void processData(final JavaStreamingContext javaStreamingContext, final String kafkaTopic, final KafkaConsumerConfig kafkaProps) throws WifiTemplateParseException {
        DashBoardTr69WifiSparkDriver.LOG.debug("processData");
        final Collection<String> tr69ReportsTopicSingletonCollection = Collections.singletonList(kafkaTopic);
        final JavaInputDStream<ConsumerRecord<Bytes, TR69DTO>> tr69ReportsTopicJavaInputStream = KafkaUtils.createDirectStream(javaStreamingContext, LocationStrategies.PreferConsistent(), ConsumerStrategies.Subscribe(tr69ReportsTopicSingletonCollection, kafkaProps.getMap()));
        DashBoardTr69WifiSparkDriver.LOG.debug("tr69ReportsTopicJavaInputStream added");
        JavaDStream<TR69DTO> tr69ReportJavaDStream = (JavaDStream<TR69DTO>)tr69ReportsTopicJavaInputStream.map(consumerRecord -> consumerRecord.value());
        DashBoardTr69WifiSparkDriver.LOG.debug("tr69ReportJavaDStream added");
        tr69ReportJavaDStream = DatasetUtils.clearAllCachedDataSets(tr69ReportJavaDStream);
        DashBoardTr69WifiSparkDriver.LOG.debug("dropRegisteredSparkTempTables added");
        final JavaDStream<String> invDataStream = (JavaDStream<String>)javaStreamingContext.textFileStream(DashBoardTr69WifiSparkDriver.CONFIG.getString(DashBoardConfig.INVENTORY_DATA_FOLDER, "hdfs:///data/hda/inventory"));
        DashBoardTr69WifiSparkDriver.LOG.debug("invDataStream added");
        processReports(javaStreamingContext, tr69ReportJavaDStream, invDataStream, new DashBoardTr69WifiReportProcessor());
    }
    
    public static void processReports(final JavaStreamingContext javaStreamingContext, final JavaDStream<TR69DTO> tr69ReportJavaDStream, final JavaDStream<String> invDataStream, final WifiReportProcessor processor) {
        DashBoardTr69WifiSparkDriver.LOG.debug("processReports");
        processor.processReport(javaStreamingContext, tr69ReportJavaDStream, invDataStream);
    }
    
    static {
        CONFIG = DashBoardConfig.instance();
        DashBoardTr69WifiSparkDriver.LOG = Logger.getLogger(DashBoardTr69WifiSparkDriver.class);
    }
}
