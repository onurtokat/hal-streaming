// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.udf.hive.udaf;

import org.apache.hadoop.hive.serde2.objectinspector.primitive.WritableConstantStringObjectInspector;
import java.util.Map;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.hive.serde2.objectinspector.StandardMapObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.StringObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.LongObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.DoubleObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StructField;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.slf4j.LoggerFactory;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.serde2.typeinfo.PrimitiveTypeInfo;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFParameterInfo;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;
import org.slf4j.Logger;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.udf.generic.AbstractGenericUDAFResolver;

@Description(name = "associations_obs_udaf", value = "_FUNC_(String hdfsURL, String configFile, double signalStrength, String MacAddress/Long Associations, signalStrengthThresholds, associationsThresholds, severityValues)")
public class AssociationsObsUDAF extends AbstractGenericUDAFResolver
{
    public static final String UDAF_NAME = "associations_obs_udaf";
    public static final String SIGNAL_STRENGTH_THRESHOLDS_KEY = "associations_obs_udaf.strength_thresholds";
    public static final String ASSOC_THRESHOLDS_KEY = "associations_obs_udaf.associations_thresholds";
    public static final String SEVERITIES_KEY = "associations_obs_udaf.severities";
    private static final Logger LOG;
    private static final int NB_ARGS = 4;
    private boolean useBasicEvaluator;
    
    public AssociationsObsUDAF() {
        this.useBasicEvaluator = false;
    }
    
    public AssociationsObsUDAFEvaluator getEvaluator(final TypeInfo[] parameters) throws SemanticException {
        return new AssociationsObsUDAFEvaluator();
    }
    
    public GenericUDAFEvaluator getEvaluator(final GenericUDAFParameterInfo paramInfo) throws SemanticException {
        if (paramInfo.isAllColumns()) {
            throw new SemanticException("The specified syntax for UDAF invocation is invalid. Wildcard not accepted");
        }
        final TypeInfo[] parameters = paramInfo.getParameters();
        if (parameters.length != 4) {
            throw new UDFArgumentLengthException("For aggregation associations_obs_udaf, 4 arguments are expected.");
        }
        for (int i = 0; i < 4; ++i) {
            if (parameters[i].getCategory() != ObjectInspector.Category.PRIMITIVE) {
                throw new UDFArgumentTypeException(i, "Only primitive type arguments are accepted but " + parameters[i].getTypeName() + " is passed.");
            }
            switch (((PrimitiveTypeInfo)parameters[i]).getPrimitiveCategory()) {
                case VOID:
                case BYTE:
                case SHORT:
                case INT:
                case LONG:
                case FLOAT:
                case DOUBLE: {
                    break;
                }
                case STRING: {
                    if (i == 2) {
                        throw new UDFArgumentTypeException(i, "Only numeric type arguments are accepted for this parameter but " + parameters[i].getTypeName() + " is passed.");
                    }
                    if (i == 3) {
                        this.useBasicEvaluator = true;
                        break;
                    }
                    break;
                }
                default: {
                    throw new UDFArgumentTypeException(i, "Only numeric or string type arguments are accepted but " + parameters[i].getTypeName() + " is passed.");
                }
            }
        }
        if (this.useBasicEvaluator) {
            return new AssociationsObsUDAFEvaluator();
        }
        return new AssociationsObsDailyAggrUDAFEvaluator();
    }
    
    private static UDAFConfig getConfigThresholds(final String hdfsUrl, final String fileName) throws UDFArgumentException {
        UDAFConfig config = null;
        try {
            config = UDAFConfig.getInstance(hdfsUrl, fileName);
        }
        catch (Exception e) {
            AssociationsObsUDAF.LOG.error("Cannot initialize default configuration :" + e.getMessage(), e);
            throw new UDFArgumentException("Initialize associations_obs_udaf udf exception type:" + e.getClass().getName() + ", message:" + e.getMessage());
        }
        return config;
    }
    
    private static void log(final String message) {
        AssociationsObsUDAF.LOG.debug(message);
    }
    
    static {
        LOG = LoggerFactory.getLogger(AssociationsObsUDAF.class);
    }
    
    public static class AssociationsObsUDAFEvaluator extends GenericUDAFEvaluator
    {
        private PrimitiveObjectInspector[] inputOI;
        private PrimitiveObjectInspector hdfsUrlOI;
        private PrimitiveObjectInspector fileNameOI;
        private StructObjectInspector soi;
        private StructField sumSignalStrengthField;
        private StructField countSignalStrengthField;
        private StructField mapMacAddressField;
        private StructField hdfsUrlField;
        private StructField fileNameField;
        private DoubleObjectInspector sumSignalStrengthFieldOI;
        private LongObjectInspector countSignalStrengthFieldOI;
        private StringObjectInspector hdfsUrlFieldOI;
        private StringObjectInspector fileNameFieldOI;
        private StandardMapObjectInspector mapMacAddressFieldOI;
        private Object[] partialResult;
        private Text result;
        private UDAFConfig config;
        private List<Integer> thresholdsSignalStrength;
        private List<Integer> thresholdsAssociations;
        private List<String> severities;
        
        public AssociationsObsUDAFEvaluator() {
            this.result = new Text();
        }
        
        public ObjectInspector init(final GenericUDAFEvaluator.Mode mode, final ObjectInspector[] parameters) throws HiveException {
            super.init(mode, parameters);
            if (mode == GenericUDAFEvaluator.Mode.PARTIAL1 || mode == GenericUDAFEvaluator.Mode.COMPLETE) {
                this.hdfsUrlOI = (PrimitiveObjectInspector)parameters[0];
                this.fileNameOI = (PrimitiveObjectInspector)parameters[1];
                (this.inputOI = new PrimitiveObjectInspector[2])[0] = (PrimitiveObjectInspector)parameters[2];
                this.inputOI[1] = (PrimitiveObjectInspector)parameters[3];
            }
            else {
                this.soi = (StructObjectInspector)parameters[0];
                this.sumSignalStrengthField = this.soi.getStructFieldRef("sumSignalStrength");
                this.countSignalStrengthField = this.soi.getStructFieldRef("countSignalStrength");
                this.mapMacAddressField = this.soi.getStructFieldRef("mapAssociations");
                this.hdfsUrlField = this.soi.getStructFieldRef("hdfsUrl");
                this.fileNameField = this.soi.getStructFieldRef("fileName");
                this.sumSignalStrengthFieldOI = (DoubleObjectInspector)this.sumSignalStrengthField.getFieldObjectInspector();
                this.countSignalStrengthFieldOI = (LongObjectInspector)this.countSignalStrengthField.getFieldObjectInspector();
                this.mapMacAddressFieldOI = (StandardMapObjectInspector)this.mapMacAddressField.getFieldObjectInspector();
                this.hdfsUrlFieldOI = (StringObjectInspector)this.hdfsUrlField.getFieldObjectInspector();
                this.fileNameFieldOI = (StringObjectInspector)this.fileNameField.getFieldObjectInspector();
            }
            if (mode == GenericUDAFEvaluator.Mode.PARTIAL1 || mode == GenericUDAFEvaluator.Mode.PARTIAL2) {
                (this.partialResult = new Object[5])[0] = new Double(0.0);
                this.partialResult[1] = new Long(0L);
                this.partialResult[2] = new HashMap();
                this.partialResult[3] = "";
                this.partialResult[4] = "";
                final ArrayList<ObjectInspector> foi = new ArrayList<ObjectInspector>();
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.javaDoubleObjectInspector);
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.javaLongObjectInspector);
                foi.add((ObjectInspector)ObjectInspectorFactory.getStandardMapObjectInspector((ObjectInspector)PrimitiveObjectInspectorFactory.javaStringObjectInspector, (ObjectInspector)PrimitiveObjectInspectorFactory.javaLongObjectInspector));
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.javaStringObjectInspector);
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.javaStringObjectInspector);
                final ArrayList<String> fname = new ArrayList<String>();
                fname.add("sumSignalStrength");
                fname.add("countSignalStrength");
                fname.add("mapAssociations");
                fname.add("hdfsUrl");
                fname.add("fileName");
                return (ObjectInspector)ObjectInspectorFactory.getStandardStructObjectInspector((List)fname, (List)foi);
            }
            this.setResult(null);
            return (ObjectInspector)PrimitiveObjectInspectorFactory.writableStringObjectInspector;
        }
        
        public GenericUDAFEvaluator.AbstractAggregationBuffer getNewAggregationBuffer() throws HiveException {
            final AssociationsObsAgg result = new AssociationsObsAgg();
            this.reset((GenericUDAFEvaluator.AggregationBuffer)result);
            return result;
        }
        
        public void reset(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final AssociationsObsAgg myagg = (AssociationsObsAgg)agg;
            myagg.sumSignalStrength = 0.0;
            myagg.countSignalStrength = 0L;
            myagg.mapAssociations = new HashMap<String, Long>();
            myagg.hdfsUrl = "";
            myagg.fileName = "";
        }
        
        public void iterate(final GenericUDAFEvaluator.AggregationBuffer agg, final Object[] parameters) throws HiveException {
            final AssociationsObsAgg aggregation = (AssociationsObsAgg)agg;
            AssociationsObsUDAF.LOG.debug("iterate- myagg.hdfsUrl=" + aggregation.hdfsUrl + " myagg.fileName=" + aggregation.fileName);
            if (parameters[0] != null) {
                aggregation.hdfsUrl = PrimitiveObjectInspectorUtils.getString(parameters[0], this.hdfsUrlOI);
            }
            if (parameters[1] != null) {
                aggregation.fileName = PrimitiveObjectInspectorUtils.getString(parameters[1], this.fileNameOI);
            }
            AssociationsObsUDAF.LOG.debug("iterate- after setting myagg.hdfsUrl=" + aggregation.hdfsUrl + " myagg.fileName=" + aggregation.fileName);
            if (parameters[2] != null) {
                final double signalStrength = PrimitiveObjectInspectorUtils.getDouble(parameters[2], this.inputOI[0]);
                final AssociationsObsAgg associationsObsAgg = aggregation;
                associationsObsAgg.sumSignalStrength += signalStrength;
                final AssociationsObsAgg associationsObsAgg2 = aggregation;
                ++associationsObsAgg2.countSignalStrength;
                AssociationsObsUDAF.LOG.debug("iterate signalStrength=" + signalStrength);
            }
            if (parameters[3] != null) {
                final String association = PrimitiveObjectInspectorUtils.getString(parameters[3], this.inputOI[1]);
                if (association != null) {
                    aggregation.mapAssociations.put(association, 0L);
                }
                AssociationsObsUDAF.LOG.debug("iterate association=" + association);
            }
            AssociationsObsUDAF.LOG.debug("iterate end");
        }
        
        public Object terminatePartial(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final AssociationsObsAgg myagg = (AssociationsObsAgg)agg;
            this.partialResult[0] = myagg.sumSignalStrength;
            this.partialResult[1] = myagg.countSignalStrength;
            this.partialResult[2] = myagg.mapAssociations;
            this.partialResult[3] = myagg.hdfsUrl;
            this.partialResult[4] = myagg.fileName;
            AssociationsObsUDAF.LOG.debug("terminatePartial myagg.hdfsUrl=" + myagg.hdfsUrl + " myagg.fileName=" + myagg.fileName);
            return this.partialResult;
        }
        
        public void merge(final GenericUDAFEvaluator.AggregationBuffer agg, final Object partial) throws HiveException {
            if (partial != null) {
                final AssociationsObsAgg associationsObsAgg;
                final AssociationsObsAgg myagg = associationsObsAgg = (AssociationsObsAgg)agg;
                associationsObsAgg.sumSignalStrength += this.sumSignalStrengthFieldOI.get(this.soi.getStructFieldData(partial, this.sumSignalStrengthField));
                final AssociationsObsAgg associationsObsAgg2 = myagg;
                associationsObsAgg2.countSignalStrength += this.countSignalStrengthFieldOI.get(this.soi.getStructFieldData(partial, this.countSignalStrengthField));
                final Object partialFieldMap1 = this.soi.getStructFieldData(partial, this.mapMacAddressField);
                final Map<String, Long> partialMap = (Map<String, Long>)this.mapMacAddressFieldOI.getMap(partialFieldMap1);
                myagg.mapAssociations.putAll(partialMap);
                AssociationsObsUDAF.LOG.debug("merge myagg.hdfsUrl=" + myagg.hdfsUrl + " myagg.fileName=" + myagg.fileName);
                myagg.hdfsUrl = this.hdfsUrlFieldOI.getPrimitiveJavaObject(this.soi.getStructFieldData(partial, this.hdfsUrlField));
                myagg.fileName = this.fileNameFieldOI.getPrimitiveJavaObject(this.soi.getStructFieldData(partial, this.fileNameField));
                AssociationsObsUDAF.LOG.debug("merge after set: myagg.hdfsUrl=" + myagg.hdfsUrl + " myagg.fileName=" + myagg.fileName);
            }
        }
        
        public Object terminate(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final AssociationsObsAgg myAgg = (AssociationsObsAgg)agg;
            final double avgSignalStrength = myAgg.sumSignalStrength / myAgg.countSignalStrength;
            final int countDistinctAssociations = myAgg.mapAssociations.keySet().size();
            if (myAgg.hdfsUrl.equals("")) {
                return this.result;
            }
            this.config = getConfigThresholds(myAgg.hdfsUrl, myAgg.fileName);
            this.thresholdsSignalStrength = this.config.getListOfInteger("associations_obs_udaf.strength_thresholds");
            this.thresholdsAssociations = this.config.getListOfInteger("associations_obs_udaf.associations_thresholds");
            this.severities = this.config.getListOfString("associations_obs_udaf.severities");
            final String message = "terminate- config from file; config=" + this.config + " thresholdsSignalStrength=" + this.thresholdsSignalStrength + " thresholdsAssociations=" + this.thresholdsAssociations + " severities=" + this.severities;
            AssociationsObsUDAF.LOG.debug(message);
            if (this.severities == null || this.thresholdsAssociations == null || this.thresholdsSignalStrength == null) {
                throw new UDFArgumentException(myAgg.toString() + "\n" + message);
            }
            if (myAgg.countSignalStrength == 0L) {
                this.setResult(this.severities.get(3));
            }
            else if (avgSignalStrength < this.thresholdsSignalStrength.get(0) && countDistinctAssociations > this.thresholdsAssociations.get(0)) {
                this.setResult(this.severities.get(0));
            }
            else if (avgSignalStrength < this.thresholdsSignalStrength.get(1) && countDistinctAssociations > this.thresholdsAssociations.get(1)) {
                this.setResult(this.severities.get(1));
            }
            else if (avgSignalStrength >= this.thresholdsSignalStrength.get(1)) {
                this.setResult(this.severities.get(2));
            }
            return this.getResult();
        }
        
        public void setResult(final String result) {
            if (result != null) {
                this.result = new Text(result);
            }
        }
        
        public Text getResult() {
            AssociationsObsUDAF.LOG.debug("result is=" + this.result.toString());
            return this.result;
        }
        
        @GenericUDAFEvaluator.AggregationType(estimable = true)
        static class AssociationsObsAgg extends GenericUDAFEvaluator.AbstractAggregationBuffer
        {
            double sumSignalStrength;
            long countSignalStrength;
            Map<String, Long> mapAssociations;
            String hdfsUrl;
            String fileName;
            
            public int estimate() {
                return 64;
            }
        }
    }
    
    public static class AssociationsObsDailyAggrUDAFEvaluator extends GenericUDAFEvaluator
    {
        private PrimitiveObjectInspector[] inputOI;
        private PrimitiveObjectInspector hdfsUrlOI;
        private PrimitiveObjectInspector fileNameOI;
        private String hdfsUrl;
        private String fileName;
        private StructObjectInspector soi;
        private StructField sumSignalStrengthField;
        private StructField sumAssociationsField;
        private StructField countSignalStrengthField;
        private StructField countAssociationsField;
        private StructField hdfsUrlField;
        private StructField fileNameField;
        private DoubleObjectInspector sumSignalStrengthFieldOI;
        private LongObjectInspector sumAssociationsFieldOI;
        private LongObjectInspector countSignalStrengthFieldOI;
        private LongObjectInspector countAssociationsFieldOI;
        private StringObjectInspector hdfsUrlFieldOI;
        private StringObjectInspector fileNameFieldOI;
        private Object[] partialResult;
        private Text result;
        private UDAFConfig config;
        private static List<Integer> thresholdsSignalStrength;
        private static List<Integer> thresholdsAssociations;
        private static List<String> severities;
        
        public AssociationsObsDailyAggrUDAFEvaluator() {
            this.result = new Text();
        }
        
        public ObjectInspector init(final GenericUDAFEvaluator.Mode mode, final ObjectInspector[] parameters) throws HiveException {
            super.init(mode, parameters);
            this.initInput(mode, parameters);
            if (mode == GenericUDAFEvaluator.Mode.PARTIAL1 || mode == GenericUDAFEvaluator.Mode.PARTIAL2) {
                return this.initOutput();
            }
            this.setResult(null);
            return (ObjectInspector)PrimitiveObjectInspectorFactory.writableStringObjectInspector;
        }
        
        private void initInput(final GenericUDAFEvaluator.Mode mode, final ObjectInspector[] parameters) throws UDFArgumentException {
            if (mode == GenericUDAFEvaluator.Mode.PARTIAL1 || mode == GenericUDAFEvaluator.Mode.COMPLETE) {
                this.hdfsUrlOI = (PrimitiveObjectInspector)parameters[0];
                this.fileNameOI = (PrimitiveObjectInspector)parameters[1];
                (this.inputOI = new PrimitiveObjectInspector[2])[0] = (PrimitiveObjectInspector)parameters[2];
                this.inputOI[1] = (PrimitiveObjectInspector)parameters[3];
                if (parameters[0] != null) {
                    this.hdfsUrl = ((WritableConstantStringObjectInspector)parameters[0]).getWritableConstantValue().toString();
                }
                if (parameters[1] != null) {
                    this.fileName = ((WritableConstantStringObjectInspector)parameters[1]).getWritableConstantValue().toString();
                }
            }
            else {
                this.soi = (StructObjectInspector)parameters[0];
                this.sumSignalStrengthField = this.soi.getStructFieldRef("sumSignalStrength");
                this.sumAssociationsField = this.soi.getStructFieldRef("sumAssociations");
                this.countSignalStrengthField = this.soi.getStructFieldRef("countSignalStrength");
                this.countAssociationsField = this.soi.getStructFieldRef("countAssociations");
                this.hdfsUrlField = this.soi.getStructFieldRef("hdfsUrl");
                this.fileNameField = this.soi.getStructFieldRef("fileName");
                this.sumSignalStrengthFieldOI = (DoubleObjectInspector)this.sumSignalStrengthField.getFieldObjectInspector();
                this.sumAssociationsFieldOI = (LongObjectInspector)this.sumAssociationsField.getFieldObjectInspector();
                this.countSignalStrengthFieldOI = (LongObjectInspector)this.countSignalStrengthField.getFieldObjectInspector();
                this.countAssociationsFieldOI = (LongObjectInspector)this.countAssociationsField.getFieldObjectInspector();
                this.hdfsUrlFieldOI = (StringObjectInspector)this.hdfsUrlField.getFieldObjectInspector();
                this.fileNameFieldOI = (StringObjectInspector)this.fileNameField.getFieldObjectInspector();
            }
        }
        
        private ObjectInspector initOutput() {
            (this.partialResult = new Object[6])[0] = new Double(0.0);
            this.partialResult[1] = new Long(0L);
            this.partialResult[2] = new Long(0L);
            this.partialResult[3] = new Long(0L);
            final ArrayList<ObjectInspector> foi = new ArrayList<ObjectInspector>();
            foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.javaDoubleObjectInspector);
            foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.javaLongObjectInspector);
            foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.javaLongObjectInspector);
            foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.javaLongObjectInspector);
            foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.javaStringObjectInspector);
            foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.javaStringObjectInspector);
            final ArrayList<String> fname = new ArrayList<String>();
            fname.add("sumSignalStrength");
            fname.add("sumAssociations");
            fname.add("countSignalStrength");
            fname.add("countAssociations");
            fname.add("hdfsUrl");
            fname.add("fileName");
            return (ObjectInspector)ObjectInspectorFactory.getStandardStructObjectInspector((List)fname, (List)foi);
        }
        
        public GenericUDAFEvaluator.AggregationBuffer getNewAggregationBuffer() throws HiveException {
            final AssociationsObsDailyAggr result = new AssociationsObsDailyAggr();
            this.reset((GenericUDAFEvaluator.AggregationBuffer)result);
            return (GenericUDAFEvaluator.AggregationBuffer)result;
        }
        
        public void reset(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final AssociationsObsDailyAggr myagg = (AssociationsObsDailyAggr)agg;
            myagg.sumSignalStrength = 0.0;
            myagg.sumAssociations = 0L;
            myagg.countSignalStrength = 0L;
            myagg.countAssociations = 0L;
        }
        
        public void iterate(final GenericUDAFEvaluator.AggregationBuffer agg, final Object[] parameters) throws HiveException {
            log("AssociationsObsDailyAggrUDAFEvaluator.iterate() entered");
            final AssociationsObsDailyAggr aggregation = (AssociationsObsDailyAggr)agg;
            aggregation.hdfsUrl = this.hdfsUrl;
            aggregation.fileName = this.fileName;
            if (parameters[2] != null) {
                final double signalStrength = PrimitiveObjectInspectorUtils.getDouble(parameters[2], this.inputOI[0]);
                final AssociationsObsDailyAggr associationsObsDailyAggr = aggregation;
                associationsObsDailyAggr.sumSignalStrength += signalStrength;
                final AssociationsObsDailyAggr associationsObsDailyAggr2 = aggregation;
                ++associationsObsDailyAggr2.countSignalStrength;
            }
            if (parameters[3] != null) {
                final long associations = PrimitiveObjectInspectorUtils.getLong(parameters[3], this.inputOI[1]);
                final AssociationsObsDailyAggr associationsObsDailyAggr3 = aggregation;
                associationsObsDailyAggr3.sumAssociations += associations;
                final AssociationsObsDailyAggr associationsObsDailyAggr4 = aggregation;
                ++associationsObsDailyAggr4.countAssociations;
            }
        }
        
        public Object terminatePartial(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final AssociationsObsDailyAggr aggregation = (AssociationsObsDailyAggr)agg;
            this.partialResult[0] = aggregation.sumSignalStrength;
            this.partialResult[1] = aggregation.sumAssociations;
            this.partialResult[2] = aggregation.countSignalStrength;
            this.partialResult[3] = aggregation.countAssociations;
            this.partialResult[4] = aggregation.hdfsUrl;
            this.partialResult[5] = aggregation.fileName;
            return this.partialResult;
        }
        
        public void merge(final GenericUDAFEvaluator.AggregationBuffer agg, final Object partial) throws HiveException {
            if (partial != null) {
                final AssociationsObsDailyAggr aggregation = (AssociationsObsDailyAggr)agg;
                final double sumSignalStrength = this.sumSignalStrengthFieldOI.get(this.soi.getStructFieldData(partial, this.sumSignalStrengthField));
                final AssociationsObsDailyAggr associationsObsDailyAggr = aggregation;
                associationsObsDailyAggr.sumSignalStrength += sumSignalStrength;
                final long sumAssociations = this.sumAssociationsFieldOI.get(this.soi.getStructFieldData(partial, this.sumAssociationsField));
                final AssociationsObsDailyAggr associationsObsDailyAggr2 = aggregation;
                associationsObsDailyAggr2.sumAssociations += sumAssociations;
                final AssociationsObsDailyAggr associationsObsDailyAggr3 = aggregation;
                associationsObsDailyAggr3.countSignalStrength += this.countSignalStrengthFieldOI.get(this.soi.getStructFieldData(partial, this.countSignalStrengthField));
                final AssociationsObsDailyAggr associationsObsDailyAggr4 = aggregation;
                associationsObsDailyAggr4.countAssociations += this.countAssociationsFieldOI.get(this.soi.getStructFieldData(partial, this.countAssociationsField));
                aggregation.hdfsUrl = this.hdfsUrlFieldOI.getPrimitiveJavaObject(this.soi.getStructFieldData(partial, this.hdfsUrlField));
                aggregation.fileName = this.fileNameFieldOI.getPrimitiveJavaObject(this.soi.getStructFieldData(partial, this.fileNameField));
            }
        }
        
        public Object terminate(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final AssociationsObsDailyAggr aggregation = (AssociationsObsDailyAggr)agg;
            final double avgSignalStrength = aggregation.sumSignalStrength / aggregation.countSignalStrength;
            final double avgAssociations = aggregation.sumAssociations / aggregation.countAssociations;
            this.config = getConfigThresholds(aggregation.hdfsUrl, aggregation.fileName);
            AssociationsObsDailyAggrUDAFEvaluator.thresholdsSignalStrength = this.config.getListOfInteger("associations_obs_udaf.strength_thresholds");
            AssociationsObsDailyAggrUDAFEvaluator.thresholdsAssociations = this.config.getListOfInteger("associations_obs_udaf.associations_thresholds");
            AssociationsObsDailyAggrUDAFEvaluator.severities = this.config.getListOfString("associations_obs_udaf.severities");
            if (AssociationsObsDailyAggrUDAFEvaluator.severities == null || AssociationsObsDailyAggrUDAFEvaluator.thresholdsAssociations == null || AssociationsObsDailyAggrUDAFEvaluator.thresholdsSignalStrength == null) {
                throw new UDFArgumentException(aggregation.toString());
            }
            if (aggregation.countSignalStrength == 0L) {
                this.setResult(AssociationsObsDailyAggrUDAFEvaluator.severities.get(3));
            }
            else if (avgSignalStrength < AssociationsObsDailyAggrUDAFEvaluator.thresholdsSignalStrength.get(0) && avgAssociations > AssociationsObsDailyAggrUDAFEvaluator.thresholdsAssociations.get(0)) {
                this.setResult(AssociationsObsDailyAggrUDAFEvaluator.severities.get(0));
            }
            else if (avgSignalStrength < AssociationsObsDailyAggrUDAFEvaluator.thresholdsSignalStrength.get(1) && avgAssociations > AssociationsObsDailyAggrUDAFEvaluator.thresholdsAssociations.get(1)) {
                this.setResult(AssociationsObsDailyAggrUDAFEvaluator.severities.get(1));
            }
            else if (avgSignalStrength >= AssociationsObsDailyAggrUDAFEvaluator.thresholdsSignalStrength.get(1)) {
                this.setResult(AssociationsObsDailyAggrUDAFEvaluator.severities.get(2));
            }
            return this.getResult();
        }
        
        public void setResult(final String result) {
            if (result != null) {
                this.result = new Text(result);
            }
        }
        
        public Text getResult() {
            return this.result;
        }
        
        @GenericUDAFEvaluator.AggregationType(estimable = true)
        static class AssociationsObsDailyAggr extends GenericUDAFEvaluator.AbstractAggregationBuffer
        {
            double sumSignalStrength;
            long sumAssociations;
            long countSignalStrength;
            long countAssociations;
            String hdfsUrl;
            String fileName;
            
            public int estimate() {
                return 32;
            }
        }
    }
}
