// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.process;

import com.alu.hal.streaming.hive.model.ModelType;
import scala.Function1;
import com.alu.hal.streaming.kafka.KafkaWriter;
import com.alu.hal.streaming.config.KafkaTopicConfig;
import com.alu.hal.streaming.hive.model.HiveTable;
import org.apache.spark.sql.Dataset;
import com.alu.hal.streaming.utils.DatasetUtils;
import org.apache.spark.api.java.JavaRDD;
import com.alu.hal.streaming.utils.KpiUtils;
import com.alu.hal.streaming.hive.model.KafkaTopicId;
import com.alu.hal.streaming.hive.model.SparkTempView;
import java.util.ArrayList;
import org.apache.spark.streaming.api.java.JavaMapWithStateDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.StateSpec;
import org.apache.spark.streaming.Seconds;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.EnumSet;
import com.alu.hal.streaming.config.WifiQueries;
import com.alu.hal.streaming.hive.model.UnifiedTableMetaData;
import org.apache.spark.sql.Row;
import com.alu.hal.streaming.hive.model.MgdDevReport;
import org.apache.spark.sql.types.StructType;
import com.alu.hal.streaming.config.WifiSparkConfig;
import org.apache.spark.storage.StorageLevel;
import com.alu.motive.hal.commons.dto.TR69DTO;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import com.alu.hal.streaming.config.WifiConfigSingleton;
import org.apache.spark.SparkContext;

import java.util.List;
import org.apache.spark.sql.SparkSession;
import com.nokia.hal.nbi.colmapping.ColumnsMappingForNbi;
import com.alu.hal.streaming.utils.HalSparkSession;
import scala.Tuple2;
import com.alu.hal.streaming.hive.model.DeltaStagingState;
import org.apache.spark.streaming.State;
import com.alu.hal.streaming.hive.model.MgdDevTransposeColumnState;
import org.apache.spark.api.java.Optional;
import com.alu.motive.hal.commons.dto.DeviceIdDTO;
import org.apache.spark.streaming.Time;
import org.apache.spark.api.java.function.Function4;
import com.nokia.hal.ie.backend.StreamingNewInsightsProcessingHandler;
import org.apache.log4j.Logger;
import com.alu.hal.streaming.config.DashBoardConfig;

public class DashBoardTr69WifiReportProcessor implements WifiReportProcessor
{
    private static final long serialVersionUID = 871221002471819417L;
    private static final DashBoardConfig CONFIG;
    private static Logger LOG;
    private static final String TS_COLUMN = "TS";
    private static String hdfsURL;
    private static String hqlFileFolder;
    private static String hqlTemplateFileFolder;
    private static String avscFileFolder;
    private static String avscTemplateFileFolder;
    private static String viewTemplatesFolder;
    private static StreamingNewInsightsProcessingHandler handler;
    static final Function4<Time, DeviceIdDTO, Optional<MgdDevTransposeColumnState>, State<DeltaStagingState>, Optional<Tuple2<DeviceIdDTO, DeltaStagingState>>> DELTA_STAGING_MAPPING_FUNCTION;
    
    private void createViewsWithSparkSession(final StreamingNewInsightsProcessingHandler handler) {
        DashBoardTr69WifiReportProcessor.LOG.info("start view creation with Spark session");
        try {
            final SparkSession ss = HalSparkSession.getInstance();
            final List<String> newViews = handler.getCreateViewsStatements();
            for (final String stmts : newViews) {
                final String[] split;
                final String[] subStmts = split = stmts.split(";");
                for (final String sqlStmt : split) {
                    DashBoardTr69WifiReportProcessor.LOG.info("executing query:" + sqlStmt);
                    ss.sql(sqlStmt);
                }
            }
        }
        catch (Exception ex) {
            DashBoardTr69WifiReportProcessor.LOG.error("failed to create views for new Insights formulas");
            DashBoardTr69WifiReportProcessor.LOG.error(ColumnsMappingForNbi.logStack(ex));
        }
        finally {
            DashBoardTr69WifiReportProcessor.LOG.info("finished view creation with Spark session");
        }
    }
    
    private void initializeNewInsightsHandler() {
        (DashBoardTr69WifiReportProcessor.handler = createNewInsightsHandler()).setViewsTemplatesFolder(DashBoardTr69WifiReportProcessor.hdfsURL + DashBoardTr69WifiReportProcessor.viewTemplatesFolder);
    }
    
    private void checkForNewInsightsFormulas(final SparkContext sc) {
        final Boolean queriesRefreshed = DashBoardTr69WifiReportProcessor.handler.process();
        if (queriesRefreshed) {
            this.createViewsWithSparkSession(DashBoardTr69WifiReportProcessor.handler);
            DashBoardTr69WifiReportProcessor.LOG.info("new insights formulas generated new queries");
            WifiConfigSingleton.update(sc);
        }
    }
    
    @Override
    public void processReport(final JavaStreamingContext javaStreamingContext, final JavaDStream<TR69DTO> tr69ReportJavaDStream, final JavaDStream<String> invDataStream) {
        if (DashBoardTr69WifiReportProcessor.handler == null) {
            DashBoardTr69WifiReportProcessor.LOG.info("checking for new insights formulas in initialization");
            this.initializeNewInsightsHandler();
        }
        this.checkForNewInsightsFormulas(javaStreamingContext.sparkContext().sc());
        tr69ReportJavaDStream.foreachRDD(tr69Rdd -> {
            DashBoardTr69WifiReportProcessor.LOG.info("checking for new insights formulas after receiving RDDs");
            if (DashBoardTr69WifiReportProcessor.handler != null) {
                this.checkForNewInsightsFormulas(tr69Rdd.context());
            }
            else {
                this.initializeNewInsightsHandler();
                this.checkForNewInsightsFormulas(tr69Rdd.context());
            }
        });
        DashBoardTr69WifiReportProcessor.LOG.debug("processReport tr69ReportJavaDStream=" + tr69ReportJavaDStream);
        final int defaultParallelism = DashBoardTr69WifiReportProcessor.CONFIG.getInteger("spark.executor.cores") * DashBoardTr69WifiReportProcessor.CONFIG.getInteger("spark.executor.instances");
        final JavaDStream<MgdDevTransposeColumnState> mgdDeviceReportDStream = (JavaDStream<MgdDevTransposeColumnState>)tr69ReportJavaDStream.transform(tr69DTOJavaRDD -> TaskProcessor.computeMgdDevReportState((JavaRDD<TR69DTO>)tr69DTOJavaRDD));
        final JavaDStream<MgdDevReport> deltaComputedMgdDevReportDStream = this.updateAndComputeDeltaStateOnlyCurrentBatch(mgdDeviceReportDStream);
        deltaComputedMgdDevReportDStream.persist(StorageLevel.MEMORY_ONLY_SER());
        final JavaDStream<MgdDevReport> tr98MgdDevReportDstream = (JavaDStream<MgdDevReport>)deltaComputedMgdDevReportDStream.filter(mgdDeviceReport -> mgdDeviceReport.getModelType().equals(ModelType.TR98));
        final JavaDStream<Row> tr98DataFrameDstream = (JavaDStream<Row>)tr98MgdDevReportDstream.transform(tr98MgdDevReportRDD -> TaskProcessor.computeAndExportTr98Data((JavaRDD<MgdDevReport>)tr98MgdDevReportRDD));
        final UnifiedTableMetaData unifiedTableMetaData = ((WifiSparkConfig)WifiConfigSingleton.getInstance(javaStreamingContext.sparkContext()).getValue()).getUnifiedTableMetadata();
        final JavaDStream<Row> tr98UnifiedDstream = (JavaDStream<Row>)tr98DataFrameDstream.map(tr98Row -> UnifiedTableRowFactory.fromTr98Row(tr98Row, unifiedTableMetaData));
        final JavaDStream<MgdDevReport> tr181MgdDevReportDstream = (JavaDStream<MgdDevReport>)deltaComputedMgdDevReportDStream.filter(mgdDeviceReport -> mgdDeviceReport.getModelType().equals(ModelType.TR181));
        final JavaDStream<Row> tr181DataFrameDstream = (JavaDStream<Row>)tr181MgdDevReportDstream.transform(tr181MgdDevReportRDD -> TaskProcessor.computeAndExportTr181Data((JavaRDD<MgdDevReport>)tr181MgdDevReportRDD));
        deltaComputedMgdDevReportDStream.transform(deltaComputedMgdDevReportRDD -> deltaComputedMgdDevReportRDD.unpersist());
        final JavaDStream<Row> tr181UnifiedDstream = (JavaDStream<Row>)tr181DataFrameDstream.map(tr181Row -> UnifiedTableRowFactory.fromTr181Row(tr181Row, unifiedTableMetaData));
        final JavaDStream<Row> unifiedDStream = (JavaDStream<Row>)tr181UnifiedDstream.union((JavaDStream)tr98UnifiedDstream);
        final JavaDStream<Row> coalescedUnified = (JavaDStream<Row>)unifiedDStream.transform(rdd -> rdd.coalesce(defaultParallelism));
        DashBoardTr69WifiReportProcessor.LOG.info("getting inventory data from hdfs file ...");
        final StructType[] unifiedSchema = { null };
        final JavaDStream<Row> unifiedWithRefDataFrameDstream = UnifiedAndInventoryDataJoinHelper.getJoinedInvAndUnifiedDstreams(invDataStream, coalescedUnified, unifiedSchema);
        this.exportUnifiedWithRefWifiTable(unifiedWithRefDataFrameDstream);
        this.computeBasicKpis(unifiedWithRefDataFrameDstream);
        if (DashBoardTr69WifiReportProcessor.CONFIG.getBoolean(DashBoardConfig.COMPUTE_SWND_KPIS, true)) {
            final JavaDStream<Row> aggregatedUnifiedWithRefDStream = this.computeAggregatedUnifiedWithRefDStream(defaultParallelism, unifiedWithRefDataFrameDstream, unifiedSchema);
            final List<JavaDStream<Row>> swndKpisDstreams = this.computeSwndKpis(aggregatedUnifiedWithRefDStream);
            if (DashBoardTr69WifiReportProcessor.CONFIG.getBoolean(DashBoardConfig.COMPUTE_RECOMMENDATIONS, true)) {
                RecommendationsProcessor.computeRecomendations(javaStreamingContext, aggregatedUnifiedWithRefDStream);
            }
        }
    }
    
    private static StreamingNewInsightsProcessingHandler createNewInsightsHandler() {
        DashBoardTr69WifiReportProcessor.LOG.info("Initializing StreamingNewInsightsProcessingHandler");
        final StreamingNewInsightsProcessingHandler theHandler = new StreamingNewInsightsProcessingHandler();
        final List<String> hqlTemplateFileNames = EnumSet.allOf(WifiQueries.QueryNames.class).stream().map(qn -> qn.getHqlTemplateFileName()).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
        theHandler.setHdfsUrl(DashBoardTr69WifiReportProcessor.hdfsURL).setHqlTemplateFileFolder(DashBoardTr69WifiReportProcessor.hqlTemplateFileFolder).setHqlFileFolder(DashBoardTr69WifiReportProcessor.hqlFileFolder).setAvscTemplateFileFolder(DashBoardTr69WifiReportProcessor.avscTemplateFileFolder).setAvscFileFolder(DashBoardTr69WifiReportProcessor.avscFileFolder).setHqlTemplateFileNames(hqlTemplateFileNames);
        return theHandler;
    }
    
    private void exportUnifiedWithRefWifiTable(final JavaDStream<Row> unifiedWithRefDataFrameDstream) {
        unifiedWithRefDataFrameDstream.foreachRDD(unifiedWithRefRDD -> {
            final Dataset<Row> unifiedWithRefDataFrame = (Dataset<Row>)HalSparkSession.getInstance().table(SparkTempView.UNIFIED_WIFI_DVC.getTempViewName());
            final Dataset<Row> unifiedWithRefWithTimePartitionsDataFrame = DatasetUtils.addHivePartitionsColumns(unifiedWithRefDataFrame, HiveTable.UNIFIED_WIFI_DVC.getTableName());
            unifiedWithRefWithTimePartitionsDataFrame.foreachPartition((Function1)new KafkaWriter(KafkaTopicConfig.instance().getAvroKafkaTopic(KafkaTopicId.UNIFIED_WIFI_DVC_AVRO_TOPIC), HalSparkSession.getInstance().sparkContext()));
        });
    }
    
    private JavaDStream<Row> computeAggregatedUnifiedWithRefDStream(final int defaultParallelism, final JavaDStream<Row> unifiedWithRefDataFrameDstream, final StructType[] aggregatedInputSchema) {
        final JavaDStream<Row> aggregatedDStream = (JavaDStream<Row>)unifiedWithRefDataFrameDstream.window(Seconds.apply(DashBoardTr69WifiReportProcessor.CONFIG.getLong(DashBoardConfig.DRIVER_AGG_WINDOW_LENGTH_INTERVAL)), Seconds.apply(DashBoardTr69WifiReportProcessor.CONFIG.getLong(DashBoardConfig.DRIVER_AGG_WINDOW_SLIDE_INTERVAL))).transform(aggregatedRowRDD -> {
            final SparkSession sparkSession = HalSparkSession.getInstance();
            final Dataset<Row> aggregatedDataFrame = (Dataset<Row>)sparkSession.createDataFrame(aggregatedRowRDD, aggregatedInputSchema[0]);
            final Dataset<Row> aggregatedCoalescedDataFrame = (Dataset<Row>)aggregatedDataFrame.coalesce(DashBoardTr69WifiReportProcessor.CONFIG.getInteger("spark.sql.window.partitions", defaultParallelism));
            DatasetUtils.registerAsTempView(aggregatedCoalescedDataFrame, SparkTempView.AGGREGATED_INPUT_TABLE);
            final StorageLevel storageLevel = StorageLevel.fromString(DashBoardTr69WifiReportProcessor.CONFIG.getString("kpi.persistence.storage.level", "MEMORY_ONLY_SER"));
            DashBoardTr69WifiReportProcessor.LOG.debug("Persisting aggregated window of unified with ref data dataFrame " + SparkTempView.AGGREGATED_INPUT_TABLE + " with storageLevel" + storageLevel.description());
            aggregatedCoalescedDataFrame.persist(storageLevel);
            return aggregatedRowRDD;
        });
        return aggregatedDStream;
    }
    
    private JavaDStream<MgdDevReport> updateAndComputeDeltaStateOnlyCurrentBatch(final JavaDStream<MgdDevTransposeColumnState> mgdDevTransposeColumnStateDstream) {
        final JavaPairDStream<DeviceIdDTO, MgdDevTransposeColumnState> mgdDevTransposeColumnStatePairDstream = (JavaPairDStream<DeviceIdDTO, MgdDevTransposeColumnState>)mgdDevTransposeColumnStateDstream.mapToPair(mgdDevTransposeColumnState -> new Tuple2((T1)mgdDevTransposeColumnState.getDeviceId(), (T2)mgdDevTransposeColumnState));
        final JavaMapWithStateDStream<DeviceIdDTO, MgdDevTransposeColumnState, DeltaStagingState, Tuple2<DeviceIdDTO, DeltaStagingState>> mapDeviceReportStateDstream = (JavaMapWithStateDStream<DeviceIdDTO, MgdDevTransposeColumnState, DeltaStagingState, Tuple2<DeviceIdDTO, DeltaStagingState>>)mgdDevTransposeColumnStatePairDstream.mapWithState(StateSpec.function((Function4)DashBoardTr69WifiReportProcessor.DELTA_STAGING_MAPPING_FUNCTION).timeout(Durations.seconds(DashBoardTr69WifiReportProcessor.CONFIG.getLong("device.report.ttl", 86400L))));
        mapDeviceReportStateDstream.checkpoint(Durations.seconds(DashBoardTr69WifiReportProcessor.CONFIG.getLong(DashBoardConfig.DRIVER_BATCH_PROCESS_INTERVAL))).persist(StorageLevel.MEMORY_ONLY_SER());
        final JavaDStream<Tuple2<DeviceIdDTO, DeltaStagingState>> mapWithStateDistinct = (JavaDStream<Tuple2<DeviceIdDTO, DeltaStagingState>>)mapDeviceReportStateDstream.transform(rdd -> rdd.distinct());
        final JavaDStream<MgdDevReport> deltaComputedMgdDevReportDStream = (JavaDStream<MgdDevReport>)mapWithStateDistinct.transform((rdd, time) -> TaskProcessor.computeDelta((JavaRDD<Tuple2<DeviceIdDTO, DeltaStagingState>>)rdd, time));
        return deltaComputedMgdDevReportDStream;
    }
    
    private List<JavaDStream<Row>> computeSwndKpis(final JavaDStream<Row> aggregatedDStream) {
        final List<JavaDStream<Row>> dStreamList = new ArrayList<JavaDStream<Row>>();
        dStreamList.add(KpiUtils.calculateAndExport(aggregatedDStream, WifiQueries.QueryNames.KPI_HOME_WIFI_RADIO_SWND_HQL, SparkTempView.HOME_RADIO_KPI_SWND_AGG, KafkaTopicId.RADIO_KPI_SWND_AVRO_TOPIC));
        dStreamList.add(KpiUtils.calculateAndExport(aggregatedDStream, WifiQueries.QueryNames.KPI_HOME_WIFI_AGG_ACCESS_POINT, SparkTempView.HOME_ACCESS_POINT_KPI_KQI_OBS_SWND_AGG, KafkaTopicId.ACCESS_POINT_KPI_SWND_AVRO_TOPIC));
        dStreamList.add(KpiUtils.calculateAndExport(aggregatedDStream, WifiQueries.QueryNames.KPI_HOME_WIFI_AGG_ASSOC_DEVICE, SparkTempView.HOME_ASSOC_DEVICE_KPI_KQI_OBS_SWND_AGG, KafkaTopicId.ASSOC_DEVICE_KPI_SWND_AVRO_TOPIC));
        dStreamList.add(KpiUtils.calculateAndExport(aggregatedDStream, WifiQueries.QueryNames.KPI_HOME_WIFI_MGD_DEVICE_SWND, SparkTempView.HOME_DEVICE_KPI_SWND_AGG, KafkaTopicId.DEVICE_KPI_SWND_AVRO_TOPIC));
        dStreamList.add(KpiUtils.calculateAndExport(aggregatedDStream, WifiQueries.QueryNames.KPI_HOME_WIFI_SWND, SparkTempView.HOME_KPI_SWND_AGG, KafkaTopicId.HOME_KPI_SWND_AVRO_TOPIC));
        return dStreamList;
    }
    
    private List<JavaDStream<Row>> computeBasicKpis(final JavaDStream<Row> unifiedWithRefDataFrameDstream) {
        final List<JavaDStream<Row>> dStreamList = new ArrayList<JavaDStream<Row>>();
        dStreamList.add(KpiUtils.calculateAndExport(unifiedWithRefDataFrameDstream, WifiQueries.QueryNames.KPI_HOME_WIFI_RADIO, SparkTempView.HOME_RADIO_KPI_BASIC_AGG, KafkaTopicId.RADIO_KPI_BASIC_AVRO_TOPIC));
        dStreamList.add(KpiUtils.calculateAndExport(unifiedWithRefDataFrameDstream, WifiQueries.QueryNames.KPI_HOME_WIFI_ACCESS_POINT, SparkTempView.HOME_ACCESS_POINT_KPI_BASIC_AGG, KafkaTopicId.ACCESS_POINT_KPI_BASIC_AVRO_TOPIC));
        dStreamList.add(KpiUtils.calculateAndExport(unifiedWithRefDataFrameDstream, WifiQueries.QueryNames.KPI_HOME_WIFI_MGD_DEVICE, SparkTempView.HOME_DEVICE_KPI_BASIC_AGG, KafkaTopicId.DEVICE_KPI_BASIC_AVRO_TOPIC));
        dStreamList.add(KpiUtils.calculateAndExport(unifiedWithRefDataFrameDstream, WifiQueries.QueryNames.KPI_HOME_WIFI, SparkTempView.HOME_KPI_BASIC_AGG, KafkaTopicId.HOME_KPI_BASIC_AVRO_TOPIC));
        return dStreamList;
    }
    
    static {
        CONFIG = DashBoardConfig.instance();
        DashBoardTr69WifiReportProcessor.LOG = Logger.getLogger(DashBoardTr69WifiReportProcessor.class);
        DashBoardTr69WifiReportProcessor.hdfsURL = DashBoardConfig.instance().getString(DashBoardConfig.HDFS_URL, "hdfs://mas:8020");
        DashBoardTr69WifiReportProcessor.hqlFileFolder = DashBoardConfig.instance().getString("hql.hdfs.file.folder", "/data/hda/queries/wifi-streaming");
        DashBoardTr69WifiReportProcessor.hqlTemplateFileFolder = DashBoardConfig.instance().getString("hql.hdfs.templates.folder", "/data/hda/queries/wifi-streaming/templates");
        DashBoardTr69WifiReportProcessor.avscFileFolder = DashBoardConfig.instance().getString("avsc.hdfs.file.folder", "/data/hda/kafka-avsc");
        DashBoardTr69WifiReportProcessor.avscTemplateFileFolder = DashBoardConfig.instance().getString("avsc.hdfs.templates.folder", "/data/hda/kafka-avsc/templates");
        DashBoardTr69WifiReportProcessor.viewTemplatesFolder = DashBoardConfig.instance().getString("hql.hdfs.view.templates.folder", "/data/hda/queries/wifi-streaming/templates/views");
        DashBoardTr69WifiReportProcessor.handler = null;
        DELTA_STAGING_MAPPING_FUNCTION = ((batchTime, deviceId, mgdDevTransposeColumnState, prevState) -> {
            if (prevState.isTimingOut()) {
                DashBoardTr69WifiReportProcessor.LOG.info("Timing out deviceId= " + deviceId);
                final Optional<Tuple2<DeviceIdDTO, DeltaStagingState>> output = (Optional<Tuple2<DeviceIdDTO, DeltaStagingState>>)Optional.of((Object)new Tuple2(deviceId, prevState.get()));
                return output;
            }
            final DeltaStagingState deltaStagingState = DeltaStagingState.updateDeltaStagingState((Optional<MgdDevTransposeColumnState>)mgdDevTransposeColumnState, (State<DeltaStagingState>)prevState, batchTime);
            final Optional<Tuple2<DeviceIdDTO, DeltaStagingState>> output2 = (Optional<Tuple2<DeviceIdDTO, DeltaStagingState>>)Optional.of((Object)new Tuple2(deviceId, deltaStagingState));
            prevState.update((Object)deltaStagingState);
            return output2;
        });
    }
}
