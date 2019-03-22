// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.udf.hive.udaf;

import java.util.regex.Pattern;
import java.util.Iterator;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.BooleanObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.LongObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.IntObjectInspector;
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

@Description(name = "connection_signal_observation_udaf", value = "_FUNC_(String hdfsUrl, String thresholdsFile, string (Monitored_Point_Name, double AssocDevice_SignalStrength)")
public class ConnectionSignalObservationUDAF extends AbstractGenericUDAFResolver
{
    public static final String UDAF_NAME = "connection_signal_observation_udaf";
    public static final String PROPERTY_PREFIX = "connection_signal_observation_udaf.";
    public static final String MONITORED_POINT_TYPES = "connection_signal_observation_udaf.monitored_point_types";
    public static final String MONITORED_POINT_FILTER = ".MONITORED_POINT_FILTER";
    public static final String BAD_LINK_THRESHOLD = ".bad_link_threshold";
    public static final String GOOD_LINK_THRESHOLD = ".good_link_threshold";
    public static final String MINIMUM_SAMPLES_LINK_OBS_THRESHOLD = ".minimum_samples_link_observation_threshold";
    public static final String MINIMUM_SAMPLES_DEGRADED_LINK_OBS_THRESHOLD = ".minimum_samples_degraded_link_observation_theshold";
    public static final String MINIMUM_SAMPLES_BAD_LINK_OBS_THRESHOLD = ".minimum_samples_bad_link_observation_theshold";
    private static final Logger LOG;
    private static final int NB_ARGS = 4;
    
    public ConnectionSignalObsUDAFEvaluator getEvaluator(final TypeInfo[] parameters) throws SemanticException {
        return new ConnectionSignalObsUDAFEvaluator();
    }
    
    public GenericUDAFEvaluator getEvaluator(final GenericUDAFParameterInfo paramInfo) throws SemanticException {
        if (paramInfo.isAllColumns()) {
            throw new SemanticException("The specified syntax for UDAF invocation is invalid. Wildcard not accepted");
        }
        final TypeInfo[] parameters = paramInfo.getParameters();
        if (parameters.length != 4) {
            throw new UDFArgumentLengthException("For aggregation connection_signal_observation_udaf, 4 arguments are expected.");
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
                    if (i <= 2) {
                        throw new UDFArgumentTypeException(i, "Only string type arguments are accepted for this parameter but " + parameters[i].getTypeName() + " is passed in " + "connection_signal_observation_udaf");
                    }
                    break;
                }
                case STRING: {
                    if (i > 2) {
                        throw new UDFArgumentTypeException(i, "Only numeric type arguments are accepted for this parameter but " + parameters[i].getTypeName() + " is passed in " + "connection_signal_observation_udaf");
                    }
                    break;
                }
                default: {
                    throw new UDFArgumentTypeException(i, "Only numeric or string type arguments are accepted but " + parameters[i].getTypeName() + " is passed in " + "connection_signal_observation_udaf");
                }
            }
        }
        return new ConnectionSignalObsUDAFEvaluator();
    }
    
    private static UDAFConfig getConfigThresholds(final String hdfsUrl, final String fileName) throws UDFArgumentException {
        UDAFConfig config = null;
        try {
            config = UDAFConfig.getInstance(hdfsUrl, fileName);
        }
        catch (Exception e) {
            ConnectionSignalObservationUDAF.LOG.error("Cannot initialize default configuration :" + e.getMessage(), e);
            throw new UDFArgumentException("connection_signal_observation_udaf udf exception type:" + e.getClass().getName() + ", message:" + e.getMessage());
        }
        return config;
    }
    
    static {
        LOG = LoggerFactory.getLogger(ConnectionSignalObservationUDAF.class);
    }
    
    public static class ConnectionSignalObsUDAFEvaluator extends GenericUDAFEvaluator
    {
        protected static final String GOOD_SIGNAL = "Good";
        protected static final String UNKNOWN_SIGNAL = "Unknown";
        protected static final String INSUFFICIENT_UPTIME_SIGNAL = "Insufficient uptime";
        protected static final String INSUFFICIENT_VALID_SAMPLES_SIGNAL = "Insufficient valid samples";
        protected static final String BAD_SIGNAL = "Bad";
        protected static final String NOT_SUPPORTED_SIGNAL = "Not Supported";
        protected static final String DEGRADED_SIGNAL = "Degraded";
        private PrimitiveObjectInspector[] inputOI;
        private StructObjectInspector soi;
        private StructField minimumSamplesLinkField;
        private StructField minimumSamplesDegradedLinkField;
        private StructField minimumSamplesBadLinkField;
        private StructField countField;
        private StructField countStrengthField;
        private StructField negativeCountField;
        private StructField badLinkCountField;
        private StructField mediumLinkCountField;
        private StructField goodLinkCountField;
        private StructField isFilterMatchField;
        private IntObjectInspector minimumSamplesLinkOI;
        private IntObjectInspector minimumSamplesDegradedLinkOI;
        private IntObjectInspector minimumSamplesBadLinkOI;
        private LongObjectInspector countFieldOI;
        private LongObjectInspector countStrengthFieldOI;
        private LongObjectInspector negativeCountFieldOI;
        private LongObjectInspector badLinkCountFieldOI;
        private LongObjectInspector mediumLinkCountFieldOI;
        private LongObjectInspector goodLinkCountFieldOI;
        private BooleanObjectInspector isFilterMatchFieldOI;
        private Object[] partialResult;
        private final Text result;
        private UDAFConfig config;
        private List<String> monitoredPointTypes;
        private String monitoredPointFilter;
        private long badLinkThreshold;
        private long goodLinkThreshold;
        
        public ConnectionSignalObsUDAFEvaluator() {
            this.result = new Text();
        }
        
        public ObjectInspector init(final GenericUDAFEvaluator.Mode mode, final ObjectInspector[] parameters) throws HiveException {
            super.init(mode, parameters);
            if (mode == GenericUDAFEvaluator.Mode.PARTIAL1 || mode == GenericUDAFEvaluator.Mode.COMPLETE) {
                (this.inputOI = new PrimitiveObjectInspector[4])[0] = (PrimitiveObjectInspector)parameters[0];
                this.inputOI[1] = (PrimitiveObjectInspector)parameters[1];
                this.inputOI[2] = (PrimitiveObjectInspector)parameters[2];
                this.inputOI[3] = (PrimitiveObjectInspector)parameters[3];
            }
            else {
                this.soi = (StructObjectInspector)parameters[0];
                this.minimumSamplesLinkField = this.soi.getStructFieldRef("minSamplesLinkObsThreshold");
                this.minimumSamplesDegradedLinkField = this.soi.getStructFieldRef("minSamplesDegradedLinkObsThreshold");
                this.minimumSamplesBadLinkField = this.soi.getStructFieldRef("minSamplesBadLinkObsThreshold");
                this.countField = this.soi.getStructFieldRef("count");
                this.countStrengthField = this.soi.getStructFieldRef("countStrength");
                this.negativeCountField = this.soi.getStructFieldRef("negativeCount");
                this.badLinkCountField = this.soi.getStructFieldRef("badLinkCount");
                this.mediumLinkCountField = this.soi.getStructFieldRef("mediumLinkCount");
                this.goodLinkCountField = this.soi.getStructFieldRef("goodLinkCount");
                this.isFilterMatchField = this.soi.getStructFieldRef("isFilterMatch");
                this.minimumSamplesLinkOI = (IntObjectInspector)this.minimumSamplesLinkField.getFieldObjectInspector();
                this.minimumSamplesDegradedLinkOI = (IntObjectInspector)this.minimumSamplesDegradedLinkField.getFieldObjectInspector();
                this.minimumSamplesBadLinkOI = (IntObjectInspector)this.minimumSamplesBadLinkField.getFieldObjectInspector();
                this.countFieldOI = (LongObjectInspector)this.countField.getFieldObjectInspector();
                this.countStrengthFieldOI = (LongObjectInspector)this.countStrengthField.getFieldObjectInspector();
                this.negativeCountFieldOI = (LongObjectInspector)this.negativeCountField.getFieldObjectInspector();
                this.badLinkCountFieldOI = (LongObjectInspector)this.badLinkCountField.getFieldObjectInspector();
                this.mediumLinkCountFieldOI = (LongObjectInspector)this.mediumLinkCountField.getFieldObjectInspector();
                this.goodLinkCountFieldOI = (LongObjectInspector)this.goodLinkCountField.getFieldObjectInspector();
                this.isFilterMatchFieldOI = (BooleanObjectInspector)this.isFilterMatchField.getFieldObjectInspector();
            }
            if (mode == GenericUDAFEvaluator.Mode.PARTIAL1 || mode == GenericUDAFEvaluator.Mode.PARTIAL2) {
                (this.partialResult = new Object[10])[0] = 0;
                this.partialResult[1] = 0;
                this.partialResult[2] = 0;
                this.partialResult[3] = 0;
                this.partialResult[4] = 0;
                this.partialResult[5] = 0;
                this.partialResult[6] = 0;
                this.partialResult[7] = 0;
                this.partialResult[8] = 0;
                this.partialResult[9] = false;
                final ArrayList<ObjectInspector> foi = new ArrayList<ObjectInspector>();
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.javaIntObjectInspector);
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.javaIntObjectInspector);
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.javaIntObjectInspector);
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.javaLongObjectInspector);
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.javaLongObjectInspector);
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.javaLongObjectInspector);
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.javaLongObjectInspector);
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.javaLongObjectInspector);
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.javaLongObjectInspector);
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.javaBooleanObjectInspector);
                final ArrayList<String> fname = new ArrayList<String>();
                fname.add("minSamplesLinkObsThreshold");
                fname.add("minSamplesDegradedLinkObsThreshold");
                fname.add("minSamplesBadLinkObsThreshold");
                fname.add("count");
                fname.add("countStrength");
                fname.add("negativeCount");
                fname.add("badLinkCount");
                fname.add("mediumLinkCount");
                fname.add("goodLinkCount");
                fname.add("isFilterMatch");
                return (ObjectInspector)ObjectInspectorFactory.getStandardStructObjectInspector((List)fname, (List)foi);
            }
            this.setResult(null);
            return (ObjectInspector)PrimitiveObjectInspectorFactory.writableStringObjectInspector;
        }
        
        public GenericUDAFEvaluator.AbstractAggregationBuffer getNewAggregationBuffer() throws HiveException {
            final ConnectionSignalObsAgg result = new ConnectionSignalObsAgg();
            this.reset((GenericUDAFEvaluator.AggregationBuffer)result);
            return result;
        }
        
        public void reset(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final ConnectionSignalObsAgg myagg = (ConnectionSignalObsAgg)agg;
            myagg.minimumSamplesLinkObsThreshold = 0;
            myagg.minimumSamplesDegradedLinkObsThreshold = 0;
            myagg.minimumSamplesBadLinkObsThreshold = 0;
            myagg.count = 0L;
            myagg.countStrength = 0L;
            myagg.negativeCount = 0L;
            myagg.badLinkCount = 0L;
            myagg.mediumLinkCount = 0L;
            myagg.goodLinkCount = 0L;
            myagg.isFilterMatch = false;
        }
        
        public void iterate(final GenericUDAFEvaluator.AggregationBuffer agg, final Object[] parameters) throws HiveException {
            final ConnectionSignalObsAgg aggregation = (ConnectionSignalObsAgg)agg;
            if (parameters[3] != null) {
                final ConnectionSignalObsAgg connectionSignalObsAgg = aggregation;
                ++connectionSignalObsAgg.countStrength;
            }
            if (parameters[2] != null) {
                final String monitoredPointName = PrimitiveObjectInspectorUtils.getString(parameters[2], this.inputOI[2]);
                if (parameters[0] != null && parameters[1] != null) {
                    final String hdfsUrl = PrimitiveObjectInspectorUtils.getString(parameters[0], this.inputOI[0]);
                    final String fileName = PrimitiveObjectInspectorUtils.getString(parameters[1], this.inputOI[1]);
                    ConnectionSignalObservationUDAF.LOG.debug("iterate aggregation=" + aggregation.toString());
                    if (this.config == null) {
                        this.config = getConfigThresholds(hdfsUrl, fileName);
                        this.monitoredPointTypes = this.config.getListOfString("connection_signal_observation_udaf.monitored_point_types");
                        ConnectionSignalObservationUDAF.LOG.debug("iterate- read from config file; config=" + this.config + " monitoredPointTypes=" + this.monitoredPointTypes);
                    }
                    if (this.monitoredPointFilter == null || aggregation.count == 0L) {
                        for (final String type : this.monitoredPointTypes) {
                            final String monitoredPointFilterOfType = this.config.getString("connection_signal_observation_udaf." + type + ".MONITORED_POINT_FILTER");
                            if (matchExpr(monitoredPointName, monitoredPointFilterOfType)) {
                                this.monitoredPointFilter = monitoredPointFilterOfType;
                                this.badLinkThreshold = this.config.getLong("connection_signal_observation_udaf." + type + ".bad_link_threshold");
                                this.goodLinkThreshold = this.config.getLong("connection_signal_observation_udaf." + type + ".good_link_threshold");
                                aggregation.minimumSamplesLinkObsThreshold = this.config.getInteger("connection_signal_observation_udaf." + type + ".minimum_samples_link_observation_threshold");
                                aggregation.minimumSamplesDegradedLinkObsThreshold = this.config.getInteger("connection_signal_observation_udaf." + type + ".minimum_samples_degraded_link_observation_theshold");
                                aggregation.minimumSamplesBadLinkObsThreshold = this.config.getInteger("connection_signal_observation_udaf." + type + ".minimum_samples_bad_link_observation_theshold");
                                aggregation.isFilterMatch = false;
                                break;
                            }
                            aggregation.isFilterMatch = true;
                        }
                    }
                    if (this.monitoredPointFilter != null) {
                        final ConnectionSignalObsAgg connectionSignalObsAgg2 = aggregation;
                        ++connectionSignalObsAgg2.count;
                        if (parameters[3] != null) {
                            final long signalStrength = PrimitiveObjectInspectorUtils.getLong(parameters[3], this.inputOI[3]);
                            if (-100L <= signalStrength && signalStrength <= 0L) {
                                final ConnectionSignalObsAgg connectionSignalObsAgg3 = aggregation;
                                ++connectionSignalObsAgg3.negativeCount;
                            }
                            if (signalStrength < this.badLinkThreshold) {
                                final ConnectionSignalObsAgg connectionSignalObsAgg4 = aggregation;
                                ++connectionSignalObsAgg4.badLinkCount;
                            }
                            else if (this.badLinkThreshold < signalStrength && signalStrength < this.goodLinkThreshold) {
                                final ConnectionSignalObsAgg connectionSignalObsAgg5 = aggregation;
                                ++connectionSignalObsAgg5.mediumLinkCount;
                            }
                            else if (signalStrength > this.goodLinkThreshold) {
                                final ConnectionSignalObsAgg connectionSignalObsAgg6 = aggregation;
                                ++connectionSignalObsAgg6.goodLinkCount;
                            }
                        }
                    }
                }
            }
            ConnectionSignalObservationUDAF.LOG.debug("iterate calculated aggregation=" + aggregation.toString());
        }
        
        private static boolean matchExpr(final String str, final String likeExpression) {
            final String regex = toRegex(likeExpression);
            final Pattern p = Pattern.compile(regex, 34);
            return p.matcher(str).matches();
        }
        
        public static String toRegex(final String expr) {
            if (expr == null) {
                throw new IllegalArgumentException("argument cannot be null");
            }
            final int len = expr.length();
            if (len == 0) {
                return "";
            }
            final StringBuilder sb = new StringBuilder(len * 2);
            for (int i = 0; i < len; ++i) {
                final char c = expr.charAt(i);
                if ("[](){}.*+?$^|#\\".indexOf(c) != -1) {
                    sb.append("\\");
                }
                sb.append(c);
            }
            return sb.toString().replace("_", ".").replace("%", ".*?");
        }
        
        public Object terminatePartial(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final ConnectionSignalObsAgg myagg = (ConnectionSignalObsAgg)agg;
            this.partialResult[0] = myagg.minimumSamplesLinkObsThreshold;
            this.partialResult[1] = myagg.minimumSamplesDegradedLinkObsThreshold;
            this.partialResult[2] = myagg.minimumSamplesBadLinkObsThreshold;
            this.partialResult[3] = myagg.count;
            this.partialResult[4] = myagg.countStrength;
            this.partialResult[5] = myagg.negativeCount;
            this.partialResult[6] = myagg.badLinkCount;
            this.partialResult[7] = myagg.mediumLinkCount;
            this.partialResult[8] = myagg.goodLinkCount;
            this.partialResult[9] = myagg.isFilterMatch;
            return this.partialResult;
        }
        
        public void merge(final GenericUDAFEvaluator.AggregationBuffer agg, final Object partial) throws HiveException {
            if (partial != null) {
                final ConnectionSignalObsAgg myagg = (ConnectionSignalObsAgg)agg;
                myagg.minimumSamplesLinkObsThreshold = this.minimumSamplesLinkOI.get(this.soi.getStructFieldData(partial, this.minimumSamplesLinkField));
                myagg.minimumSamplesDegradedLinkObsThreshold = this.minimumSamplesDegradedLinkOI.get(this.soi.getStructFieldData(partial, this.minimumSamplesDegradedLinkField));
                myagg.minimumSamplesBadLinkObsThreshold = this.minimumSamplesBadLinkOI.get(this.soi.getStructFieldData(partial, this.minimumSamplesBadLinkField));
                final ConnectionSignalObsAgg connectionSignalObsAgg = myagg;
                connectionSignalObsAgg.count += this.countFieldOI.get(this.soi.getStructFieldData(partial, this.countField));
                final ConnectionSignalObsAgg connectionSignalObsAgg2 = myagg;
                connectionSignalObsAgg2.countStrength += this.countStrengthFieldOI.get(this.soi.getStructFieldData(partial, this.countStrengthField));
                final ConnectionSignalObsAgg connectionSignalObsAgg3 = myagg;
                connectionSignalObsAgg3.negativeCount += this.negativeCountFieldOI.get(this.soi.getStructFieldData(partial, this.negativeCountField));
                final ConnectionSignalObsAgg connectionSignalObsAgg4 = myagg;
                connectionSignalObsAgg4.badLinkCount += this.badLinkCountFieldOI.get(this.soi.getStructFieldData(partial, this.badLinkCountField));
                final ConnectionSignalObsAgg connectionSignalObsAgg5 = myagg;
                connectionSignalObsAgg5.goodLinkCount += this.goodLinkCountFieldOI.get(this.soi.getStructFieldData(partial, this.goodLinkCountField));
                final ConnectionSignalObsAgg connectionSignalObsAgg6 = myagg;
                connectionSignalObsAgg6.mediumLinkCount += this.mediumLinkCountFieldOI.get(this.soi.getStructFieldData(partial, this.mediumLinkCountField));
                myagg.isFilterMatch = (myagg.isFilterMatch || this.isFilterMatchFieldOI.get(this.soi.getStructFieldData(partial, this.isFilterMatchField)));
            }
        }
        
        public Object terminate(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final ConnectionSignalObsAgg myAgg = (ConnectionSignalObsAgg)agg;
            if (myAgg.countStrength == 0L) {
                return new Text("Unknown");
            }
            if (myAgg.isFilterMatch) {
                return new Text("Not Supported");
            }
            if (myAgg.count == 0L) {
                return new Text("Not Supported");
            }
            if (myAgg.count < myAgg.minimumSamplesLinkObsThreshold) {
                return new Text("Insufficient uptime");
            }
            if (myAgg.negativeCount < myAgg.minimumSamplesLinkObsThreshold) {
                return new Text("Insufficient valid samples");
            }
            if (myAgg.badLinkCount >= myAgg.minimumSamplesBadLinkObsThreshold) {
                return new Text("Bad");
            }
            if (myAgg.mediumLinkCount >= myAgg.minimumSamplesDegradedLinkObsThreshold) {
                return new Text("Degraded");
            }
            if (myAgg.goodLinkCount >= myAgg.minimumSamplesDegradedLinkObsThreshold) {
                return new Text("Good");
            }
            return new Text("Not Supported");
        }
        
        public void setResult(final String result) {
            if (result != null) {
                this.result.set(result);
            }
        }
        
        public Text getResult() {
            ConnectionSignalObservationUDAF.LOG.debug("result is=" + this.result.toString());
            return this.result;
        }
        
        @GenericUDAFEvaluator.AggregationType(estimable = true)
        static class ConnectionSignalObsAgg extends GenericUDAFEvaluator.AbstractAggregationBuffer
        {
            int minimumSamplesLinkObsThreshold;
            int minimumSamplesDegradedLinkObsThreshold;
            int minimumSamplesBadLinkObsThreshold;
            long count;
            long countStrength;
            long negativeCount;
            long badLinkCount;
            long mediumLinkCount;
            long goodLinkCount;
            boolean isFilterMatch;
            
            public int estimate() {
                return 72;
            }
            
            public String toString() {
                return "ConnectionSignalObsAgg [minimumSamplesLinkObsThreshold=" + this.minimumSamplesLinkObsThreshold + ", minimumSamplesDegradedLinkObsThreshold=" + this.minimumSamplesDegradedLinkObsThreshold + ", minimumSamplesBadLinkObsThreshold=" + this.minimumSamplesBadLinkObsThreshold + ", count=" + this.count + ", countStrength=" + this.countStrength + ", negativeCount=" + this.negativeCount + ", badLinkCount=" + this.badLinkCount + ", mediumLinkCount=" + this.mediumLinkCount + ", goodLinkCount=" + this.goodLinkCount + "]";
            }
        }
    }
}
