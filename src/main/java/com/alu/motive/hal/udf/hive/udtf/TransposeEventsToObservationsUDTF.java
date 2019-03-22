// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.udf.hive.udtf;

import org.slf4j.LoggerFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import java.util.ArrayList;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.WritableConstantStringObjectInspector;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import java.util.List;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.slf4j.Logger;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;

public class TransposeEventsToObservationsUDTF extends GenericUDTF
{
    private static final Logger LOG;
    protected static String ACCOUNT_ID_COL;
    protected static String TIMESTAMP_COL;
    protected static String MONITORED_POINT_TYPE_COL;
    protected static String MONITORED_POINT_ID_COL;
    protected static String PERIOD_TYPE_COL;
    protected static String USECASE_SEVERITY_COL;
    protected static String OBSERVATIONS_COL_PREFIX;
    protected static String USECASE_ID_COL;
    protected static int NUMBER_OF_INPUT_ARGS;
    protected static int NUMBER_OF_OBSERVATIONS_COLUMNS;
    private TransposeBuffer eventBuffer;
    private PrimitiveObjectInspector[] inputOI;
    private List<ObjectInspector> outputOIs;
    private UsecasesToObservationsMappingsLoadFromFile observationsMapper;
    
    public TransposeEventsToObservationsUDTF() {
        this.eventBuffer = new TransposeBuffer();
        this.inputOI = new PrimitiveObjectInspector[TransposeEventsToObservationsUDTF.NUMBER_OF_INPUT_ARGS];
        this.outputOIs = null;
        this.observationsMapper = null;
    }
    
    public StructObjectInspector initialize(final ObjectInspector[] argOIs) throws UDFArgumentException {
        List<String> outputColumnsNames = null;
        try {
            TransposeEventsToObservationsUDTF.LOG.debug("Entering initialize");
            this.initializeInputOIs(argOIs);
            this.observationsMapper = this.initializeObservationsMapper(argOIs);
            outputColumnsNames = getOutputColumnsNames();
            this.outputOIs = getOutputObjectInspectors();
            TransposeEventsToObservationsUDTF.LOG.debug("Leaving initialize");
        }
        catch (Exception ex) {
            throw new UDFArgumentException((Throwable)ex);
        }
        return (StructObjectInspector)ObjectInspectorFactory.getStandardStructObjectInspector((List)outputColumnsNames, (List)this.outputOIs);
    }
    
    public void close() throws HiveException {
        this.eventBuffer.dispatchBuffer(this);
    }
    
    public void process(final Object[] args) throws HiveException {
        final TransposeInputEvent event = TransposeInputEvent.createEvent(this.inputOI, args);
        this.eventBuffer.dispatchEvent(this, event);
    }
    
    public void forwardEvent(final TransposeInputEvent event) throws HiveException {
        final String[] severities = this.observationsMapper.getMappings(event.getUsecaseToSeveritiesMap(), TransposeEventsToObservationsUDTF.NUMBER_OF_OBSERVATIONS_COLUMNS);
        final Object[] output = new Object[this.outputOIs.size()];
        int i = 0;
        int j = 0;
        output[i++] = event.getAccountId();
        output[i++] = event.getTimestamp();
        output[i++] = event.getMonitoredPointType();
        output[i++] = event.getMonitoredPointId();
        output[i++] = event.getPeriodType();
        while (i < this.outputOIs.size()) {
            output[i++] = severities[j++];
        }
        this.forward((Object)output);
    }
    
    protected UsecasesToObservationsMappingsLoadFromFile initializeObservationsMapper(final ObjectInspector[] argOIs) throws IOException, InvalidHdfsUrlException {
        if (argOIs == null || argOIs.length < 2) {
            throw new InvalidHdfsUrlException("Cannot read transpose udtf file name");
        }
        final String hdfsURL = ((WritableConstantStringObjectInspector)argOIs[0]).getWritableConstantValue().toString();
        final String hdfsFilePath = ((WritableConstantStringObjectInspector)argOIs[1]).getWritableConstantValue().toString();
        final InputStreamReader content = HDFSHelper.getInputStreamFromFile(hdfsURL, hdfsFilePath);
        final UsecasesToObservationsMappingsLoadFromFile aMapper = new UsecasesToObservationsMappingsLoadFromFile(content);
        return aMapper;
    }
    
    protected void initializeInputOIs(final ObjectInspector[] ois) throws Exception {
        if (ois.length != TransposeEventsToObservationsUDTF.NUMBER_OF_INPUT_ARGS) {
            final String msg = String.format("Invalid number of arguments received (%d). Expected %d", ois.length, TransposeEventsToObservationsUDTF.NUMBER_OF_INPUT_ARGS);
            throw new Exception(msg);
        }
        int i = 0;
        this.inputOI[i] = checkInputArgumentIsString("hdfsUrl", ois[i]);
        ++i;
        this.inputOI[i] = checkInputArgumentIsString("hdfsFilePath", ois[i]);
        ++i;
        this.inputOI[i] = checkInputArgumentIsString(TransposeEventsToObservationsUDTF.MONITORED_POINT_ID_COL, ois[i]);
        ++i;
        this.inputOI[i] = checkInputArgumentIsString(TransposeEventsToObservationsUDTF.ACCOUNT_ID_COL, ois[i]);
        ++i;
        this.inputOI[i] = checkInputArgumentIsLong(TransposeEventsToObservationsUDTF.TIMESTAMP_COL, ois[i]);
        ++i;
        this.inputOI[i] = checkInputArgumentIsString(TransposeEventsToObservationsUDTF.MONITORED_POINT_TYPE_COL, ois[i]);
        ++i;
        this.inputOI[i] = checkInputArgumentIsString(TransposeEventsToObservationsUDTF.PERIOD_TYPE_COL, ois[i]);
        ++i;
        this.inputOI[i] = checkInputArgumentIsString(TransposeEventsToObservationsUDTF.USECASE_ID_COL, ois[i]);
        ++i;
        this.inputOI[i] = checkInputArgumentIsString(TransposeEventsToObservationsUDTF.USECASE_SEVERITY_COL, ois[i]);
        ++i;
    }
    
    protected static PrimitiveObjectInspector checkInputArgumentIsString(final String argName, final ObjectInspector oi) throws Exception {
        if (oi.getCategory() != ObjectInspector.Category.PRIMITIVE && ((PrimitiveObjectInspector)oi).getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.STRING) {
            final String msg = String.format("Invalid format of input argument %s. Expected \"String\"", argName);
            throw new Exception(msg);
        }
        return (PrimitiveObjectInspector)oi;
    }
    
    protected static PrimitiveObjectInspector checkInputArgumentIsLong(final String argName, final ObjectInspector oi) throws Exception {
        if (oi.getCategory() != ObjectInspector.Category.PRIMITIVE && ((PrimitiveObjectInspector)oi).getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.LONG) {
            final String msg = String.format("Invalid format of input argument %s. Expected \"Long\"", argName);
            throw new Exception(msg);
        }
        return (PrimitiveObjectInspector)oi;
    }
    
    protected static List<String> getOutputColumnsNames() {
        final List<String> outputNames = new ArrayList<String>();
        outputNames.add(TransposeEventsToObservationsUDTF.ACCOUNT_ID_COL);
        outputNames.add(TransposeEventsToObservationsUDTF.TIMESTAMP_COL);
        outputNames.add(TransposeEventsToObservationsUDTF.MONITORED_POINT_TYPE_COL);
        outputNames.add(TransposeEventsToObservationsUDTF.MONITORED_POINT_ID_COL);
        outputNames.add(TransposeEventsToObservationsUDTF.PERIOD_TYPE_COL);
        for (int i = 1; i <= TransposeEventsToObservationsUDTF.NUMBER_OF_OBSERVATIONS_COLUMNS; ++i) {
            outputNames.add(String.format("%s%03d", TransposeEventsToObservationsUDTF.OBSERVATIONS_COL_PREFIX, i));
        }
        return outputNames;
    }
    
    protected static List<ObjectInspector> getOutputObjectInspectors() {
        final List<ObjectInspector> outputOIs = new ArrayList<ObjectInspector>();
        outputOIs.add((ObjectInspector)PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        outputOIs.add((ObjectInspector)PrimitiveObjectInspectorFactory.javaLongObjectInspector);
        outputOIs.add((ObjectInspector)PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        outputOIs.add((ObjectInspector)PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        outputOIs.add((ObjectInspector)PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        for (int i = 1; i <= TransposeEventsToObservationsUDTF.NUMBER_OF_OBSERVATIONS_COLUMNS; ++i) {
            outputOIs.add((ObjectInspector)PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        }
        return outputOIs;
    }
    
    static {
        LOG = LoggerFactory.getLogger(TransposeEventsToObservationsUDTF.class);
        TransposeEventsToObservationsUDTF.ACCOUNT_ID_COL = "Account_ID";
        TransposeEventsToObservationsUDTF.TIMESTAMP_COL = "TS";
        TransposeEventsToObservationsUDTF.MONITORED_POINT_TYPE_COL = "Monitored_Point_Type";
        TransposeEventsToObservationsUDTF.MONITORED_POINT_ID_COL = "Monitored_Point_ID";
        TransposeEventsToObservationsUDTF.PERIOD_TYPE_COL = "Period_Type";
        TransposeEventsToObservationsUDTF.USECASE_SEVERITY_COL = "Severity";
        TransposeEventsToObservationsUDTF.OBSERVATIONS_COL_PREFIX = "OBS";
        TransposeEventsToObservationsUDTF.USECASE_ID_COL = "Usecase_ID";
        TransposeEventsToObservationsUDTF.NUMBER_OF_INPUT_ARGS = 9;
        TransposeEventsToObservationsUDTF.NUMBER_OF_OBSERVATIONS_COLUMNS = 50;
    }
}
