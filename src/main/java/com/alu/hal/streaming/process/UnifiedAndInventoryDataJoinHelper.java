// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.process;

import org.apache.spark.broadcast.Broadcast;
import com.alu.hal.streaming.config.WifiConfigSingleton;
import org.apache.spark.api.java.JavaSparkContext;
import com.alu.hal.streaming.config.WifiSparkConfig;

import java.util.List;
import org.apache.spark.sql.RowFactory;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.spark.sql.Dataset;
import org.apache.spark.storage.StorageLevel;
import com.alu.hal.streaming.utils.DatasetUtils;
import com.alu.hal.streaming.hive.model.SparkTempView;
import com.alu.hal.streaming.utils.HalSparkSession;
import org.apache.spark.sql.types.DataTypes;
import java.util.Arrays;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.api.java.JavaRDD;
import com.alu.hal.streaming.hive.model.UnifiedTableMetaData;
import org.apache.spark.api.java.Optional;
import scala.Tuple2;
import com.alu.hal.streaming.hive.model.ExtenderInventoryData;
import com.alu.hal.streaming.hive.model.MgdDeviceData;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.Durations;
import com.alu.hal.streaming.hive.model.InventoryFields;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.sql.Row;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.log4j.Logger;
import com.alu.hal.streaming.config.DashBoardConfig;

public class UnifiedAndInventoryDataJoinHelper
{
    private static final DashBoardConfig CONFIG;
    private static final Logger LOG;
    private static final String DEVICE_ID = "DeviceId";
    private static final String ASSOCDEVICE_MACADRESS = "AssocDevice_MACAddress";
    private static final String HOMENETWORKID = "homeNetworkId";
    private static final String ASSOCDEVICE_IS_EXTENDER = "assocdevice_isextender";
    
    public static JavaDStream<Row> getJoinedInvAndUnifiedDstreams(final JavaDStream<String> invDataStream, final JavaDStream<Row> unifiedDStream, final StructType[] unifiedSchema) {
        final JavaDStream<InventoryFields> partOfInvDataStream = (JavaDStream<InventoryFields>)invDataStream.map(InventoryFields::parseFromLine);
        final JavaDStream<InventoryFields> windowInvDataStream = (JavaDStream<InventoryFields>)partOfInvDataStream.window(Durations.seconds(UnifiedAndInventoryDataJoinHelper.CONFIG.getLong(DashBoardConfig.INVENTORY_DATA_WINDOW, 86400L)), Durations.seconds(UnifiedAndInventoryDataJoinHelper.CONFIG.getLong(DashBoardConfig.DRIVER_BATCH_PROCESS_INTERVAL)));
        final JavaPairDStream<String, MgdDeviceData> windowPairInvDataStream = (JavaPairDStream<String, MgdDeviceData>)windowInvDataStream.transformToPair(invRDD -> invRDD.mapToPair(inv -> new Tuple2((T1)inv.getInvData().getRefDeviceId(), (T2)inv.getInvData())));
        final JavaPairDStream<String, ExtenderInventoryData> windowPairExtenderInvDataStream = (JavaPairDStream<String, ExtenderInventoryData>)windowInvDataStream.filter(InventoryFields::isExtender).transformToPair(invRDD -> invRDD.mapToPair(inv -> new Tuple2((T1)inv.getExtenderData().getMACAddressOfDevice(), (T2)inv.getExtenderData())));
        UnifiedAndInventoryDataJoinHelper.LOG.info("extender data pair created here ...");
        final JavaPairDStream<String, Row> unifiedPairDstream = (JavaPairDStream<String, Row>)unifiedDStream.transformToPair(unifiedRDD -> {
            final Broadcast<WifiSparkConfig> config = WifiConfigSingleton.getInstance(new JavaSparkContext(unifiedRDD.context()));
            final StructField[] fields = ((WifiSparkConfig)config.getValue()).getUnifiedTableMetadata().getSchema().fields();
            final Map<String, Integer> columnIndexesByName = IntStream.range(0, fields.length).boxed().collect(Collectors.toMap(index -> fields[index].name(), index -> index));
            return unifiedRDD.mapToPair(row -> new Tuple2((T1)row.getString((int)columnIndexesByName.get("DeviceId")), (T2)row));
        });
        UnifiedAndInventoryDataJoinHelper.LOG.info("unified pair created here ...");
        final JavaPairDStream<String, Tuple2<Row, Optional<MgdDeviceData>>> unifiedPairWithInvDataDstream = (JavaPairDStream<String, Tuple2<Row, Optional<MgdDeviceData>>>)unifiedPairDstream.leftOuterJoin((JavaPairDStream)windowPairInvDataStream);
        UnifiedAndInventoryDataJoinHelper.LOG.info("unified pair joined with refdata pair here ...");
        final JavaPairDStream<String, Tuple2<String, Tuple2<Row, Optional<MgdDeviceData>>>> joinedPairDstream = (JavaPairDStream<String, Tuple2<String, Tuple2<Row, Optional<MgdDeviceData>>>>)unifiedPairWithInvDataDstream.transformToPair(unifiedWithInvData -> {
            final Broadcast<WifiSparkConfig> config = WifiConfigSingleton.getInstance(new JavaSparkContext(unifiedWithInvData.context()));
            final StructField[] fields = ((WifiSparkConfig)config.getValue()).getUnifiedTableMetadata().getSchema().fields();
            final Map<String, Integer> columnIndexesByName = IntStream.range(0, fields.length).boxed().collect(Collectors.toMap(index -> fields[index].name(), index -> index));
            return unifiedWithInvData.mapToPair(row -> new Tuple2((T1)row._2()._1().getString((int)columnIndexesByName.get("AssocDevice_MACAddress")), (T2)row));
        });
        UnifiedAndInventoryDataJoinHelper.LOG.info("unified pair joined with refdata pair transformed to macadress pair here ...");
        final JavaPairDStream<String, Tuple2<Tuple2<String, Tuple2<Row, Optional<MgdDeviceData>>>, Optional<ExtenderInventoryData>>> unifiedPairWithInvAndExtenderDataDstream = (JavaPairDStream<String, Tuple2<Tuple2<String, Tuple2<Row, Optional<MgdDeviceData>>>, Optional<ExtenderInventoryData>>>)joinedPairDstream.leftOuterJoin((JavaPairDStream)windowPairExtenderInvDataStream);
        UnifiedAndInventoryDataJoinHelper.LOG.info("unified pair joined with refdata pair and joined with macadress pair here ...");
        final JavaDStream<Tuple2<String, Tuple2<Tuple2<String, Tuple2<Row, Optional<MgdDeviceData>>>, Optional<ExtenderInventoryData>>>> joinedDStream = (JavaDStream<Tuple2<String, Tuple2<Tuple2<String, Tuple2<Row, Optional<MgdDeviceData>>>, Optional<ExtenderInventoryData>>>>)unifiedPairWithInvAndExtenderDataDstream.toJavaDStream();
        return transformJoinedTuppleDstreamIntoRowDstream(joinedDStream, unifiedSchema);
    }
    
    private static JavaDStream<Row> transformJoinedTuppleDstreamIntoRowDstream(final JavaDStream<Tuple2<String, Tuple2<Tuple2<String, Tuple2<Row, Optional<MgdDeviceData>>>, Optional<ExtenderInventoryData>>>> joinedDStream, final StructType[] unifiedSchema) {
        UnifiedAndInventoryDataJoinHelper.LOG.info("Create final unifiedRefData to be saved ...");
        return (JavaDStream<Row>)joinedDStream.transform(joined -> {
            final UnifiedTableMetaData unifiedTableMetaData = ((WifiSparkConfig)WifiConfigSingleton.getInstance(new JavaSparkContext(joined.context())).getValue()).getUnifiedTableMetadata();
            final JavaRDD<Row> rowRDD = (JavaRDD<Row>)joined.map(joinedRDD -> createUnifiedRow(unifiedTableMetaData.getSchema(), joinedRDD));
            return createUnifiedDataFrame(unifiedTableMetaData, (JavaRDD<Tuple2<String, Tuple2<Tuple2<String, Tuple2<Row, Optional<MgdDeviceData>>>, Optional<ExtenderInventoryData>>>>)joined, rowRDD, unifiedSchema);
        });
    }
    
    private static JavaRDD<Row> createUnifiedDataFrame(final UnifiedTableMetaData unifiedTableMetaData, final JavaRDD<Tuple2<String, Tuple2<Tuple2<String, Tuple2<Row, Optional<MgdDeviceData>>>, Optional<ExtenderInventoryData>>>> joined, final JavaRDD<Row> rowRDD, final StructType[] unifiedSchema) {
        final StructField[] oldSchema = unifiedTableMetaData.getSchema().fields();
        final StructField[] newSchema = Arrays.copyOf(oldSchema, oldSchema.length + 2);
        newSchema[oldSchema.length] = DataTypes.createStructField("homeNetworkId", DataTypes.StringType, true);
        newSchema[oldSchema.length + 1] = DataTypes.createStructField("assocdevice_isextender", DataTypes.StringType, true);
        unifiedSchema[0] = new StructType(newSchema);
        final JavaRDD<Row> distinctRDD = (JavaRDD<Row>)rowRDD.distinct();
        final Dataset<Row> unifiedWithRefDataFrame = (Dataset<Row>)HalSparkSession.getInstance().createDataFrame((JavaRDD)distinctRDD, unifiedSchema[0]);
        DatasetUtils.registerAsTempView(unifiedWithRefDataFrame, SparkTempView.UNIFIED_WIFI_DVC);
        final StorageLevel storageLevel = StorageLevel.fromString(UnifiedAndInventoryDataJoinHelper.CONFIG.getString("kpi.persistence.storage.level", "MEMORY_ONLY_SER"));
        UnifiedAndInventoryDataJoinHelper.LOG.debug("Persisting unifiedWithRefDataFrame " + SparkTempView.UNIFIED_WIFI_DVC + " with storageLevel" + storageLevel.description());
        unifiedWithRefDataFrame.persist(storageLevel);
        return (JavaRDD<Row>)unifiedWithRefDataFrame.toJavaRDD();
    }
    
    private static Row createUnifiedRow(final StructType unifiedSchema, final Tuple2<String, Tuple2<Tuple2<String, Tuple2<Row, Optional<MgdDeviceData>>>, Optional<ExtenderInventoryData>>> joinedRDD) {
        final int unifiedSchemaSize = unifiedSchema.length();
        final List<Object> joinedUnifiedCols = new ArrayList<Object>(Collections.nCopies(unifiedSchemaSize + 2, (Object)null));
        final Map<String, Integer> columnIndexesByName = IntStream.range(0, unifiedSchema.fields().length).boxed().collect(Collectors.toMap(index -> unifiedSchema.fields()[index].name(), index -> index));
        final String defaultHomeNetworkId = "vAcc_" + joinedRDD._2()._1()._2()._1().getAs((int)columnIndexesByName.get("DeviceId"));
        final String isExtender = joinedRDD._2()._2().isPresent() ? ((ExtenderInventoryData)joinedRDD._2()._2().get()).getAssocDevice_isExtender() : null;
        final String homeNetworkId = joinedRDD._2()._1()._2()._2().isPresent() ? ((MgdDeviceData)joinedRDD._2()._1()._2()._2().get()).getHomeNetworkId() : defaultHomeNetworkId;
        for (int i = 0; i < unifiedSchemaSize; ++i) {
            joinedUnifiedCols.set(i, joinedRDD._2()._1()._2()._1().getAs(i));
        }
        joinedUnifiedCols.set(unifiedSchemaSize, homeNetworkId);
        joinedUnifiedCols.set(unifiedSchemaSize + 1, isExtender);
        return RowFactory.create(joinedUnifiedCols.toArray());
    }
    
    static {
        CONFIG = DashBoardConfig.instance();
        LOG = Logger.getLogger(UnifiedAndInventoryDataJoinHelper.class);
    }
}
