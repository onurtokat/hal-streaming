// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.udf.hive.udaf;

import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import java.util.ArrayList;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import java.util.List;
import org.apache.hadoop.hive.serde2.io.DoubleWritable;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.DoubleObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StructField;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.slf4j.LoggerFactory;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.serde2.typeinfo.PrimitiveTypeInfo;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFParameterInfo;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;
import org.slf4j.Logger;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.udf.generic.AbstractGenericUDAFResolver;

@Description(name = "expected_retransmissions_perc_udf", value = "_FUNC_(hdfsURL/string, thresholdsFileName/string, signalStrength/double, packetsSent/double) ")
public class ExpectedRetransmissionsPercUDAF extends AbstractGenericUDAFResolver
{
    private static final Logger LOG;
    public static final String UDAF_NAME = "expected_retransmissions_perc_udf";
    public static final String UDAF_THRESHOLDS_KEY = "expected_retransmissions_perc_udf.thresholds";
    public static final String UDAF_FACTORS_KEY = "expected_retransmissions_perc_udf.factors";
    private static final int NB_ARGS = 4;
    
    public ExpectedRetransmissionsPercUDAFEvaluator getEvaluator(final TypeInfo[] parameters) throws SemanticException {
        return new ExpectedRetransmissionsPercUDAFEvaluator();
    }
    
    public GenericUDAFEvaluator getEvaluator(final GenericUDAFParameterInfo paramInfo) throws SemanticException {
        if (paramInfo.isAllColumns()) {
            throw new SemanticException("The specified syntax for UDAF invocation is invalid. Wildcard not accepted");
        }
        final TypeInfo[] parameters = paramInfo.getParameters();
        if (parameters.length != 4) {
            throw new UDFArgumentLengthException("Exactly four arguments are expected.");
        }
        for (int i = 0; i < 2; ++i) {
            switch (((PrimitiveTypeInfo)parameters[i]).getPrimitiveCategory()) {
                case BYTE:
                case SHORT:
                case INT:
                case LONG:
                case FLOAT:
                case DOUBLE: {
                    if (i < 2) {
                        throw new UDFArgumentTypeException(i, "Only string type arguments are accepted but " + parameters[i].getTypeName() + " is passed at position " + i);
                    }
                    break;
                }
                case STRING: {
                    if (i > 1) {
                        throw new UDFArgumentTypeException(i, "Only numeric type arguments are accepted but " + parameters[i].getTypeName() + " is passed at position " + i);
                    }
                    break;
                }
                default: {
                    throw new UDFArgumentTypeException(i, "Only numeric or string type arguments are accepted but " + parameters[i].getTypeName() + " is passed.");
                }
            }
        }
        return new ExpectedRetransmissionsPercUDAFEvaluator();
    }
    
    private static UDAFConfig getConfigThresholds(final String hdfsUrl, final String fileName) throws UDFArgumentException {
        UDAFConfig config = null;
        try {
            config = UDAFConfig.getInstance(hdfsUrl, fileName);
        }
        catch (Exception e) {
            ExpectedRetransmissionsPercUDAF.LOG.error("Cannot initialize default configuration :" + e.getMessage(), e);
            throw new UDFArgumentException("Initialize expected_retransmissions_perc_udf udf exception type:" + e.getClass().getName() + ", message:" + e.getMessage());
        }
        return config;
    }
    
    static {
        LOG = LoggerFactory.getLogger(ExpectedRetransmissionsPercUDAF.class);
    }
    
    public static class ExpectedRetransmissionsPercUDAFEvaluator extends GenericUDAFEvaluator
    {
        private PrimitiveObjectInspector[] inputOI;
        private StructObjectInspector soi;
        private StructField sumExpectedPacketsSentField;
        private StructField sumPacketsSentField;
        private DoubleObjectInspector sumExpectedPacketsSentFieldOI;
        private DoubleObjectInspector sumPacketsSentFieldOI;
        private Object[] partialResult;
        private DoubleWritable result;
        private UDAFConfig config;
        private List<Integer> thresholdsSignalStrength;
        private List<Double> factorsPacketSent;
        
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
                this.sumExpectedPacketsSentField = this.soi.getStructFieldRef("sumExpectedPacketsSent");
                this.sumPacketsSentField = this.soi.getStructFieldRef("sumPacketsSent");
                this.sumExpectedPacketsSentFieldOI = (DoubleObjectInspector)this.sumExpectedPacketsSentField.getFieldObjectInspector();
                this.sumPacketsSentFieldOI = (DoubleObjectInspector)this.sumPacketsSentField.getFieldObjectInspector();
            }
            if (mode == GenericUDAFEvaluator.Mode.PARTIAL1 || mode == GenericUDAFEvaluator.Mode.PARTIAL2) {
                (this.partialResult = new Object[2])[0] = new DoubleWritable(0.0);
                this.partialResult[1] = new DoubleWritable(0.0);
                final ArrayList<ObjectInspector> foi = new ArrayList<ObjectInspector>();
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.writableDoubleObjectInspector);
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.writableDoubleObjectInspector);
                final ArrayList<String> fname = new ArrayList<String>();
                fname.add("sumExpectedPacketsSent");
                fname.add("sumPacketsSent");
                return (ObjectInspector)ObjectInspectorFactory.getStandardStructObjectInspector((List)fname, (List)foi);
            }
            this.setResult(new DoubleWritable(0.0));
            return (ObjectInspector)PrimitiveObjectInspectorFactory.writableDoubleObjectInspector;
        }
        
        public GenericUDAFEvaluator.AggregationBuffer getNewAggregationBuffer() throws HiveException {
            final ExpectedRetransmissionsPercAgg result = new ExpectedRetransmissionsPercAgg();
            this.reset((GenericUDAFEvaluator.AggregationBuffer)result);
            return (GenericUDAFEvaluator.AggregationBuffer)result;
        }
        
        public void reset(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final ExpectedRetransmissionsPercAgg myagg = (ExpectedRetransmissionsPercAgg)agg;
            myagg.sumExpectedPacketsSent = 0.0;
            myagg.sumPacketsSent = 0.0;
        }
        
        public void iterate(final GenericUDAFEvaluator.AggregationBuffer agg, final Object[] parameters) throws HiveException {
            final ExpectedRetransmissionsPercAgg myagg = (ExpectedRetransmissionsPercAgg)agg;
            if (parameters[0] != null && parameters[1] != null) {
                final String hdfsUrl = PrimitiveObjectInspectorUtils.getString(parameters[0], this.inputOI[0]);
                final String fileName = PrimitiveObjectInspectorUtils.getString(parameters[1], this.inputOI[1]);
                ExpectedRetransmissionsPercUDAF.LOG.debug("iterate- after setting hdfsUrl=" + hdfsUrl + " fileName=" + fileName);
                if (this.thresholdsSignalStrength == null || this.factorsPacketSent == null) {
                    this.config = getConfigThresholds(hdfsUrl, fileName);
                    this.thresholdsSignalStrength = this.config.getListOfInteger("expected_retransmissions_perc_udf.thresholds");
                    this.factorsPacketSent = this.config.getListOfDouble("expected_retransmissions_perc_udf.factors");
                    final String message = "iterate- read from config file; config=" + this.config + " thresholdsSignalStrength=" + this.thresholdsSignalStrength + " factorsPacketSent=" + this.factorsPacketSent;
                    ExpectedRetransmissionsPercUDAF.LOG.debug(message);
                }
                if (parameters[3] != null) {
                    if (parameters[2] != null) {
                        final double signalStrength = PrimitiveObjectInspectorUtils.getDouble(parameters[2], this.inputOI[2]);
                        final double packetsSent = PrimitiveObjectInspectorUtils.getDouble(parameters[3], this.inputOI[3]);
                        if (signalStrength < this.thresholdsSignalStrength.get(0)) {
                            myagg.sumExpectedPacketsSent += this.factorsPacketSent.get(0) * packetsSent;
                        }
                        else if (signalStrength >= this.thresholdsSignalStrength.get(0) && signalStrength < this.thresholdsSignalStrength.get(1)) {
                            myagg.sumExpectedPacketsSent += this.factorsPacketSent.get(1) * packetsSent;
                        }
                        else {
                            myagg.sumExpectedPacketsSent += this.factorsPacketSent.get(2) * packetsSent;
                        }
                    }
                    final ExpectedRetransmissionsPercAgg expectedRetransmissionsPercAgg = myagg;
                    expectedRetransmissionsPercAgg.sumPacketsSent += PrimitiveObjectInspectorUtils.getDouble(parameters[3], this.inputOI[3]);
                }
            }
            else {
                ExpectedRetransmissionsPercUDAF.LOG.debug("iterate received null parameters");
            }
        }
        
        public Object terminatePartial(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final ExpectedRetransmissionsPercAgg myagg = (ExpectedRetransmissionsPercAgg)agg;
            ((DoubleWritable)this.partialResult[0]).set(myagg.sumExpectedPacketsSent);
            ((DoubleWritable)this.partialResult[1]).set(myagg.sumPacketsSent);
            return this.partialResult;
        }
        
        public void merge(final GenericUDAFEvaluator.AggregationBuffer agg, final Object partial) throws HiveException {
            if (partial != null) {
                final ExpectedRetransmissionsPercAgg expectedRetransmissionsPercAgg;
                final ExpectedRetransmissionsPercAgg myagg = expectedRetransmissionsPercAgg = (ExpectedRetransmissionsPercAgg)agg;
                expectedRetransmissionsPercAgg.sumExpectedPacketsSent += this.sumExpectedPacketsSentFieldOI.get(this.soi.getStructFieldData(partial, this.sumExpectedPacketsSentField));
                final ExpectedRetransmissionsPercAgg expectedRetransmissionsPercAgg2 = myagg;
                expectedRetransmissionsPercAgg2.sumPacketsSent += this.sumPacketsSentFieldOI.get(this.soi.getStructFieldData(partial, this.sumPacketsSentField));
            }
        }
        
        public Object terminate(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final ExpectedRetransmissionsPercAgg myAgg = (ExpectedRetransmissionsPercAgg)agg;
            double perc = 0.0;
            if (myAgg.sumPacketsSent != 0.0) {
                perc = myAgg.sumExpectedPacketsSent / myAgg.sumPacketsSent;
            }
            this.getResult().set(perc);
            return this.getResult();
        }
        
        public void setResult(final DoubleWritable result) {
            this.result = result;
        }
        
        public DoubleWritable getResult() {
            return this.result;
        }
        
        @GenericUDAFEvaluator.AggregationType(estimable = true)
        static class ExpectedRetransmissionsPercAgg extends GenericUDAFEvaluator.AbstractAggregationBuffer
        {
            double sumExpectedPacketsSent;
            double sumPacketsSent;
            
            public int estimate() {
                return 16;
            }
        }
    }
}
