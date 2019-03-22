// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.process;

import com.alu.hal.streaming.utils.DatasetUtils;
import com.alu.hal.streaming.utils.HalSparkSession;
import com.alu.hal.streaming.hive.model.ModelMetadata;
import org.apache.spark.api.java.Optional;
import org.apache.spark.sql.Dataset;
import com.alu.hal.streaming.utils.ExportUtils;
import com.alu.hal.streaming.hive.model.HiveTable;
import com.alu.hal.streaming.hive.model.SparkTempView;
import org.apache.spark.sql.Row;
import org.apache.spark.api.java.JavaSparkContext;
import com.alu.hal.streaming.hive.model.MgdDevTransposeColumnState;
import com.alu.motive.hal.commons.dto.TR69DTO;
import com.alu.hal.streaming.config.WifiSparkConfig;
import org.apache.spark.broadcast.Broadcast;
import com.alu.hal.streaming.config.WifiConfigSingleton;
import com.alu.hal.streaming.hive.model.MgdDevReport;
import org.apache.spark.streaming.Time;
import com.alu.hal.streaming.hive.model.DeltaStagingState;
import com.alu.motive.hal.commons.dto.DeviceIdDTO;
import scala.Tuple2;
import org.apache.spark.api.java.JavaRDD;
import org.apache.log4j.Logger;
import com.alu.hal.streaming.config.DashBoardConfig;
import java.io.Serializable;

public class TaskProcessor implements Serializable
{
    private static final long serialVersionUID = 1L;
    private static final DashBoardConfig CONFIG;
    private static Logger LOG;
    
    public static JavaRDD<MgdDevReport> computeDelta(final JavaRDD<Tuple2<DeviceIdDTO, DeltaStagingState>> rdd, final Time time) {
        final Broadcast<WifiSparkConfig> config = WifiConfigSingleton.getInstance(rdd.context());
        final JavaRDD<MgdDevReport> deltaComputedMgdDevReportRDD = (JavaRDD<MgdDevReport>)rdd.filter(tuple -> !time.greater(tuple._2().getBatchTime()) || tuple._2().getMgdDevTransposeColumnStates().size() < 2).flatMap(stagingStateTuple -> DeltaStagingState.getDelta(stagingStateTuple._2(), (WifiSparkConfig)config.getValue()).iterator());
        return deltaComputedMgdDevReportRDD;
    }
    
    public static JavaRDD<MgdDevTransposeColumnState> computeMgdDevReportState(final JavaRDD<TR69DTO> tr69DTOJavaRDD) {
        final Broadcast<WifiSparkConfig> config = WifiConfigSingleton.getInstance(new JavaSparkContext(tr69DTOJavaRDD.context()));
        return computeMgdDevReportRDD(tr69DTOJavaRDD, (WifiSparkConfig)config.getValue());
    }
    
    public static JavaRDD<Row> computeAndExportTr98Data(final JavaRDD<MgdDevReport> tr98MgdDevReportRDD) {
        final Broadcast<WifiSparkConfig> config = WifiConfigSingleton.getInstance(new JavaSparkContext(tr98MgdDevReportRDD.context()));
        final Dataset<Row> tr98TransposeDataFrame = createTransposeTableDataFrame(((WifiSparkConfig)config.getValue()).getTr98Metadata(), tr98MgdDevReportRDD);
        if (TaskProcessor.CONFIG.getBoolean(DashBoardConfig.EXPORT_TRANSPOSE_TABLES, false)) {
            ExportUtils.exportDataFrameAsHiveTable(SparkTempView.TR98_TRANSPOSE, HiveTable.TR98_TRANSPOSE_TABLE, tr98TransposeDataFrame);
        }
        return (JavaRDD<Row>)tr98TransposeDataFrame.toJavaRDD();
    }
    
    public static JavaRDD<Row> computeAndExportTr181Data(final JavaRDD<MgdDevReport> tr181MgdDevReportRDD) {
        final Broadcast<WifiSparkConfig> config = WifiConfigSingleton.getInstance(new JavaSparkContext(tr181MgdDevReportRDD.context()));
        final Dataset<Row> tr181TransposeDataFrame = createTransposeTableDataFrame(((WifiSparkConfig)config.getValue()).getTr181Metadata(), tr181MgdDevReportRDD);
        if (TaskProcessor.CONFIG.getBoolean(DashBoardConfig.EXPORT_TRANSPOSE_TABLES, false)) {
            ExportUtils.exportDataFrameAsHiveTable(SparkTempView.TR181_TRANSPOSE, HiveTable.TR181_TRANSPOSE_TABLE, tr181TransposeDataFrame);
        }
        return (JavaRDD<Row>)tr181TransposeDataFrame.toJavaRDD();
    }
    
    private static JavaRDD<MgdDevTransposeColumnState> computeMgdDevReportRDD(final JavaRDD<TR69DTO> tr69DTOJavaRDD, final WifiSparkConfig wifiSparkConfig) {
        return (JavaRDD<MgdDevTransposeColumnState>)tr69DTOJavaRDD.map(tr69DTO -> MgdDevTransposeColumnState.create(tr69DTO, wifiSparkConfig)).filter(Optional::isPresent).map(Optional::get);
    }
    
    private static Dataset<Row> createTransposeTableDataFrame(final ModelMetadata modelMetadata, final JavaRDD<MgdDevReport> mgdDeviceReportRDD) {
        final JavaRDD<Row> rowJavaRDD = (JavaRDD<Row>)mgdDeviceReportRDD.flatMap(mgdDeviceReport -> TransposeTableRowFactory.create(mgdDeviceReport, modelMetadata).iterator());
        final Dataset<Row> dataFrame = (Dataset<Row>)HalSparkSession.getInstance().createDataFrame((JavaRDD)rowJavaRDD, modelMetadata.getTransposeTableMetadata().getTransposeSchema());
        DatasetUtils.registerAsTempView(dataFrame, SparkTempView.ofTransposeTableOfModelType(modelMetadata.getTransposeTableMetadata().getModelType()));
        return dataFrame;
    }
    
    static {
        CONFIG = DashBoardConfig.instance();
        TaskProcessor.LOG = Logger.getLogger(TaskProcessor.class);
    }
}
