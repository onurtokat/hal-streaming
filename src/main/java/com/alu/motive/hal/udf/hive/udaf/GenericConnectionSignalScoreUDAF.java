// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.udf.hive.udaf;

import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import java.util.List;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import java.util.ArrayList;
import org.apache.hadoop.hive.serde2.io.DoubleWritable;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.DoubleObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.LongObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StructField;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.slf4j.LoggerFactory;
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

@Description(name = "connection_signal_score_udaf", value = "_FUNC_(x) -")
public class GenericConnectionSignalScoreUDAF extends AbstractGenericUDAFResolver
{
    private static final Logger LOG;
    
    public GenericConnectionSignalUDAFEvaluator getEvaluator(final TypeInfo[] parameters) throws SemanticException {
        return new GenericConnectionSignalUDAFEvaluator();
    }
    
    public GenericUDAFEvaluator getEvaluator(final GenericUDAFParameterInfo paramInfo) throws SemanticException {
        if (paramInfo.isAllColumns()) {
            throw new SemanticException("The specified syntax for UDAF invocation is invalid. Wildcard not accepted");
        }
        final TypeInfo[] parameters = paramInfo.getParameters();
        if (parameters.length != 2) {
            throw new UDFArgumentLengthException("Two arguments are expected.");
        }
        int i = 0;
        while (i < parameters.length) {
            if (parameters[i].getCategory() != ObjectInspector.Category.PRIMITIVE) {
                throw new UDFArgumentTypeException(0, "Only primitive type arguments are accepted but " + parameters[i].getTypeName() + " is passed.");
            }
            switch (((PrimitiveTypeInfo)parameters[i]).getPrimitiveCategory()) {
                case BYTE:
                case SHORT:
                case INT:
                case LONG:
                case FLOAT:
                case DOUBLE: {
                    ++i;
                    continue;
                }
                default: {
                    throw new UDFArgumentTypeException(i, "Only numeric type arguments are accepted but " + parameters[i].getTypeName() + " is passed.");
                }
            }
        }
        return new GenericConnectionSignalUDAFEvaluator();
    }
    
    static {
        LOG = LoggerFactory.getLogger(ConnectionQualityScoreAssocDeviceUDAF.class);
    }
    
    public static class GenericConnectionSignalUDAFEvaluator extends GenericUDAFEvaluator
    {
        private static final double DEFAULT_WIFI_STRENGTH_OFFSET = 130.0;
        private PrimitiveObjectInspector[] inputOI;
        private StructObjectInspector soi;
        private StructField countField;
        private StructField sumField;
        private StructField wifiStrengthOffsetField;
        private LongObjectInspector countFieldOI;
        private DoubleObjectInspector sumFieldOI;
        private DoubleObjectInspector wifiStrengthOffsetFieldOI;
        private Object[] partialResult;
        private DoubleWritable result;
        
        public ObjectInspector init(final GenericUDAFEvaluator.Mode mode, final ObjectInspector[] parameters) throws HiveException {
            super.init(mode, parameters);
            if (mode == GenericUDAFEvaluator.Mode.PARTIAL1 || mode == GenericUDAFEvaluator.Mode.COMPLETE) {
                (this.inputOI = new PrimitiveObjectInspector[2])[0] = (PrimitiveObjectInspector)parameters[0];
                this.inputOI[1] = (PrimitiveObjectInspector)parameters[1];
            }
            else {
                this.soi = (StructObjectInspector)parameters[0];
                this.countField = this.soi.getStructFieldRef("count");
                this.sumField = this.soi.getStructFieldRef("sum");
                this.wifiStrengthOffsetField = this.soi.getStructFieldRef("wifiStrengthOffset");
                this.countFieldOI = (LongObjectInspector)this.countField.getFieldObjectInspector();
                this.sumFieldOI = (DoubleObjectInspector)this.sumField.getFieldObjectInspector();
                this.wifiStrengthOffsetFieldOI = (DoubleObjectInspector)this.wifiStrengthOffsetField.getFieldObjectInspector();
            }
            if (mode == GenericUDAFEvaluator.Mode.PARTIAL1 || mode == GenericUDAFEvaluator.Mode.PARTIAL2) {
                final ArrayList<ObjectInspector> foi = new ArrayList<ObjectInspector>();
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.writableLongObjectInspector);
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.writableDoubleObjectInspector);
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.writableDoubleObjectInspector);
                final ArrayList<String> fname = new ArrayList<String>();
                fname.add("count");
                fname.add("sum");
                fname.add("wifiStrengthOffset");
                (this.partialResult = new Object[3])[0] = new LongWritable(0L);
                this.partialResult[1] = new DoubleWritable(0.0);
                this.partialResult[2] = new DoubleWritable(0.0);
                return (ObjectInspector)ObjectInspectorFactory.getStandardStructObjectInspector((List)fname, (List)foi);
            }
            this.setResult(new DoubleWritable(0.0));
            return (ObjectInspector)PrimitiveObjectInspectorFactory.writableDoubleObjectInspector;
        }
        
        public GenericUDAFEvaluator.AggregationBuffer getNewAggregationBuffer() throws HiveException {
            final ConnectionSignalAgg result = new ConnectionSignalAgg();
            this.reset((GenericUDAFEvaluator.AggregationBuffer)result);
            return (GenericUDAFEvaluator.AggregationBuffer)result;
        }
        
        public void reset(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final ConnectionSignalAgg myagg = (ConnectionSignalAgg)agg;
            myagg.count = 0L;
            myagg.sum = 0.0;
            myagg.wifiStrengthOffset = 130.0;
        }
        
        public void iterate(final GenericUDAFEvaluator.AggregationBuffer agg, final Object[] parameters) throws HiveException {
            assert parameters.length == 2;
            final ConnectionSignalAgg myagg = (ConnectionSignalAgg)agg;
            if (parameters[0] != null && myagg.wifiStrengthOffset == 130.0) {
                myagg.wifiStrengthOffset = PrimitiveObjectInspectorUtils.getDouble(parameters[0], this.inputOI[0]);
            }
            if (parameters[1] == null) {
                return;
            }
            final double v = PrimitiveObjectInspectorUtils.getDouble(parameters[1], this.inputOI[1]);
            final ConnectionSignalAgg connectionSignalAgg = myagg;
            ++connectionSignalAgg.count;
            final ConnectionSignalAgg connectionSignalAgg2 = myagg;
            connectionSignalAgg2.sum += v;
        }
        
        public Object terminatePartial(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final ConnectionSignalAgg myagg = (ConnectionSignalAgg)agg;
            ((LongWritable)this.partialResult[0]).set(myagg.count);
            ((DoubleWritable)this.partialResult[1]).set(myagg.sum);
            ((DoubleWritable)this.partialResult[2]).set(myagg.wifiStrengthOffset);
            return this.partialResult;
        }
        
        public void merge(final GenericUDAFEvaluator.AggregationBuffer agg, final Object partial) throws HiveException {
            if (partial != null) {
                final ConnectionSignalAgg myagg = (ConnectionSignalAgg)agg;
                final Object partialCount = this.soi.getStructFieldData(partial, this.countField);
                final Object partialSum = this.soi.getStructFieldData(partial, this.sumField);
                final Object partialWifiStrengthOffset = this.soi.getStructFieldData(partial, this.wifiStrengthOffsetField);
                final long c = myagg.count;
                final long pc = this.countFieldOI.get(partialCount);
                if (c == 0L) {
                    myagg.count = pc;
                    myagg.sum = this.sumFieldOI.get(partialSum);
                    if (myagg.wifiStrengthOffset == 130.0) {
                        myagg.wifiStrengthOffset = this.wifiStrengthOffsetFieldOI.get(partialWifiStrengthOffset);
                    }
                }
                if (pc != 0L && c != 0L) {
                    final ConnectionSignalAgg connectionSignalAgg = myagg;
                    connectionSignalAgg.count += pc;
                    final ConnectionSignalAgg connectionSignalAgg2 = myagg;
                    connectionSignalAgg2.sum += this.sumFieldOI.get(partialSum);
                }
            }
        }
        
        public Object terminate(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final ConnectionSignalAgg myAgg = (ConnectionSignalAgg)agg;
            final DoubleWritable averageDiff = new DoubleWritable(0.0);
            if (myAgg.count != 0L) {
                averageDiff.set((myAgg.wifiStrengthOffset + myAgg.sum / myAgg.count) / 100.0);
                final DoubleWritable signalScore = new DoubleWritable((averageDiff.get() < 1.0) ? averageDiff.get() : 1.0);
                GenericConnectionSignalScoreUDAF.LOG.debug("result " + this.getResult());
                this.getResult().set(signalScore.get());
                return this.getResult();
            }
            return null;
        }
        
        public void setResult(final DoubleWritable result) {
            this.result = result;
        }
        
        public DoubleWritable getResult() {
            return this.result;
        }
        
        @GenericUDAFEvaluator.AggregationType(estimable = true)
        static class ConnectionSignalAgg extends GenericUDAFEvaluator.AbstractAggregationBuffer
        {
            long count;
            double sum;
            double wifiStrengthOffset;
            
            public int estimate() {
                return 24;
            }
        }
    }
}
