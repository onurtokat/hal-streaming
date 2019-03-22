// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.process;

import scala.Tuple2;
import scala.Function1;
import com.alu.hal.streaming.kafka.KafkaWriter;
import com.alu.hal.streaming.hive.model.KafkaTopicId;
import com.alu.hal.streaming.config.KafkaTopicConfig;
import com.alu.hal.streaming.utils.HalSparkSession;
import org.apache.spark.sql.RowFactory;

import java.util.ArrayList;
import scala.collection.Seq;
import scala.collection.JavaConversions;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Map;
import org.apache.spark.sql.Dataset;
import com.alu.hal.streaming.hive.model.SparkTempView;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import com.alu.hal.streaming.hive.model.recommendation.RecommInputBean;
import org.apache.spark.api.java.Optional;
import org.apache.spark.sql.types.StructType;

import java.util.List;
import com.alu.hal.streaming.config.WifiRecommendations;
import com.alu.hal.streaming.hive.model.recommendation.RecommendationType;
import com.alu.hal.streaming.config.WifiConfigSingleton;
import com.alu.hal.streaming.config.WifiSparkConfig;
import com.alu.hal.streaming.hive.model.recommendation.GenericRecommendation;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.sql.Row;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.log4j.Logger;

public class RecommendationsProcessor
{
    private static final String MONITORED_POINT_TYPE = "Monitored_Point_type";
    private static final String MONITORED_POINT_ID = "Monitored_Point_ID";
    private static final String IS_POSITIVE_COLUMN_NAME = "isPositive";
    private static final String ERROR_DELIM = ";";
    private static final String TABLE_NOT_FOUND = "Table not found: ";
    private static final String RECOMMENDATIONS_PHOENIX_TABLE = "RECOMMENDATIONS";
    private static Logger LOG;
    
    public static JavaPairDStream<String, GenericRecommendation> computeRecomendations(final JavaStreamingContext javaStreamingContext, final JavaDStream<Row> aggregatedUnifiedWithRefDataFrameDstream) {
        RecommendationsProcessor.LOG.info("computing recommendations ...");
        final WifiRecommendations recommendations = ((WifiSparkConfig)WifiConfigSingleton.getInstance(aggregatedUnifiedWithRefDataFrameDstream.context().sc()).getValue()).getRecommendations();
        final List<RecommendationType> recTypes = recommendations.getRecommendationsTypes();
        JavaPairDStream<String, GenericRecommendation> stateDstream = null;
        for (final RecommendationType recType : recTypes) {
            RecommendationsProcessor.LOG.info("treating recommendation " + recType.getName());
            stateDstream = updateStateAndExportRecommendation(javaStreamingContext, aggregatedUnifiedWithRefDataFrameDstream, recType);
        }
        return stateDstream;
    }
    
    private static JavaPairDStream<String, GenericRecommendation> updateStateAndExportRecommendation(final JavaStreamingContext javaStreamingContext, final JavaDStream<Row> aggregatedUnifiedWithRefDataFrameDstream, final RecommendationType recType) {
        final StructType[] inputSchema = { null };
        final JavaPairDStream<String, RecommInputBean> recommDStream = (JavaPairDStream<String, RecommInputBean>)aggregatedUnifiedWithRefDataFrameDstream.transformToPair(aggUnifiedRowRDD -> computeRecPair(recType, (JavaRDD<Row>)aggUnifiedRowRDD, inputSchema));
        final GenericRecommendation recomm = new GenericRecommendation((Optional<RecommendationType>)Optional.of((Object)recType));
        final JavaPairDStream<String, GenericRecommendation> stateDstream = (JavaPairDStream<String, GenericRecommendation>)recommDStream.updateStateByKey(recomm::update).filter(GenericRecommendation::isToExport);
        exportToKafka(stateDstream, inputSchema);
        return stateDstream;
    }
    
    private static JavaPairRDD<String, RecommInputBean> computeRecPair(final RecommendationType recType, final JavaRDD<Row> unifiedRowRDD, final StructType[] inputSchema) {
        final Dataset<Row> positiveRecDataFrame = addIsPositiveColumn(WifiKpisComputer.calculateKpisByStaticQuery(recType.getPositiveQuery(), SparkTempView.ofRecommendationObservation(recType, true), false), true);
        final Dataset<Row> negativeRecDataFrame = addIsPositiveColumn(WifiKpisComputer.calculateKpisByStaticQuery(recType.getNegativeQuery(), SparkTempView.ofRecommendationObservation(recType, false), false), false);
        final Dataset<Row> unionPositiveAndNegativeRecDataFrame = (Dataset<Row>)positiveRecDataFrame.unionAll((Dataset)negativeRecDataFrame);
        final Map<String, Integer> columnIndexesByName = IntStream.range(0, unionPositiveAndNegativeRecDataFrame.schema().fields().length).boxed().collect(Collectors.toMap(index -> unionPositiveAndNegativeRecDataFrame.schema().fields()[index].name(), index -> index));
        inputSchema[0] = unionPositiveAndNegativeRecDataFrame.schema();
        return (JavaPairRDD<String, RecommInputBean>)unionPositiveAndNegativeRecDataFrame.toJavaRDD().mapToPair(row -> {
            final int isPositiveIndex = row.fieldIndex("isPositive");
            final RecommInputBean input = new RecommInputBean(row, columnIndexesByName, recType, row.getBoolean(isPositiveIndex));
            final String uniquePairKey = getUniquePairKey(row, columnIndexesByName);
            RecommendationsProcessor.LOG.debug("uniquePairKey=" + uniquePairKey + "; input=" + input);
            return new Tuple2((T1)uniquePairKey, (T2)input);
        });
    }
    
    private static Dataset<Row> addIsPositiveColumn(Dataset<Row> dataFrame, final boolean isPositiveColumnValue) {
        if (isPositiveColumnValue) {
            dataFrame = (Dataset<Row>)dataFrame.withColumn("isPositive", dataFrame.col("TS").isNotNull());
        }
        else {
            dataFrame = (Dataset<Row>)dataFrame.withColumn("isPositive", dataFrame.col("TS").isNull());
        }
        return dataFrame;
    }
    
    private static String getUniquePairKey(final Row row, final Map<String, Integer> columnIndexesByName) {
        final String[] keyColumns = { "Monitored_Point_ID", "Monitored_Point_type" };
        final StringBuffer pairKeyBuffer = new StringBuffer();
        for (final String keyColumn : keyColumns) {
            pairKeyBuffer.append(row.getString((int)columnIndexesByName.get(keyColumn))).append("@");
        }
        return pairKeyBuffer.substring(0, pairKeyBuffer.lastIndexOf("@"));
    }
    
    private static void exportToKafka(final JavaPairDStream<String, GenericRecommendation> stateDstream, final StructType[] inputSchema) {
        stateDstream.foreachRDD(statePairRDD -> {
            RecommendationsProcessor.LOG.debug("recommendations export to Kafka called here...");
            final JavaRDD<GenericRecommendation> recRDD = (JavaRDD<GenericRecommendation>)statePairRDD.map(pairRdd -> pairRdd._2());
            final StructType[] schema = { getFinalRecommendationSchema(inputSchema[0]) };
            final JavaRDD<Row> recRowRDD = createRecRow(recRDD);
            Dataset<Row> dataFrame = (Dataset<Row>)HalSparkSession.getInstance().createDataFrame((JavaRDD)recRowRDD, schema[0]);
            dataFrame = (Dataset<Row>)dataFrame.drop("isPositive");
            dataFrame.foreachPartition((Function1)new KafkaWriter(KafkaTopicConfig.instance().getAvroKafkaTopic(KafkaTopicId.RECOMMENDATIONS_AVRO_TOPIC), HalSparkSession.getInstance().sparkContext()));
        });
    }
    
    private static StructType getFinalRecommendationSchema(final StructType inputSchema) {
        final StructField[] oldSchema = inputSchema.fields();
        final StructField[] newSchema = new StructField[oldSchema.length + 4];
        for (int i = 0; i < oldSchema.length; ++i) {
            newSchema[i] = oldSchema[i];
        }
        newSchema[oldSchema.length] = DataTypes.createStructField("Recommendation_UUID", DataTypes.StringType, false);
        newSchema[oldSchema.length + 1] = DataTypes.createStructField("Recommendation_BeginDate", DataTypes.LongType, true);
        newSchema[oldSchema.length + 2] = DataTypes.createStructField("Recommendation_EndDate", DataTypes.LongType, true);
        newSchema[oldSchema.length + 3] = DataTypes.createStructField("Recommendation_State", DataTypes.StringType, true);
        final StructType schema = new StructType(newSchema);
        return schema;
    }
    
    private static JavaRDD<Row> createRecRow(final JavaRDD<GenericRecommendation> recRDD) {
        final JavaRDD<Row> rowRDD = (JavaRDD<Row>)recRDD.map(rec -> {
            RecommendationsProcessor.LOG.info("recommendation to save: " + rec);
            final Row inputRow = rec.getRecommData().getRecRow();
            final StructType inputSchema = inputRow.schema();
            final int inputSchemaSize = inputSchema.length();
            final Map<String, Integer> columnIndexesByName = IntStream.range(0, inputSchema.fields().length).boxed().collect(Collectors.toMap(index -> inputSchema.fields()[index].name(), index -> index));
            final List<Object> inputRowColumns = new ArrayList<Object>(JavaConversions.seqAsJavaList((Seq<Object>)inputRow.toSeq()));
            inputRowColumns.set(columnIndexesByName.get("TS"), rec.getLastUpdateTs());
            inputRowColumns.add(inputSchemaSize, rec.getId().toString());
            inputRowColumns.add(inputSchemaSize + 1, rec.getBeginDate());
            inputRowColumns.add(inputSchemaSize + 2, rec.getEndDate());
            inputRowColumns.add(inputSchemaSize + 3, rec.getState().name());
            final Row recommendationRow = RowFactory.create(inputRowColumns.toArray());
            RecommendationsProcessor.LOG.info("newRow=" + recommendationRow);
            return recommendationRow;
        });
        return rowRDD;
    }
    
    static {
        RecommendationsProcessor.LOG = Logger.getLogger(RecommendationsProcessor.class);
    }
}
