// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.udf.hive.udaf;

import org.apache.hadoop.util.StringUtils;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import java.util.List;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import java.util.ArrayList;
import org.apache.hadoop.hive.serde2.io.DoubleWritable;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.LongObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.DoubleObjectInspector;
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

@Deprecated
@Description(name = "connection_quality_score_assoc_device_udaf", value = "_FUNC_(x1,x2,x3) -")
public class ConnectionQualityScoreAssocDeviceUDAF extends AbstractGenericUDAFResolver
{
    private static final Logger LOG;
    
    public ConnectionQualityScoreAssocDeviceUDAFEvaluator getEvaluator(final TypeInfo[] parameters) throws SemanticException {
        return new ConnectionQualityScoreAssocDeviceUDAFEvaluator();
    }
    
    public GenericUDAFEvaluator getEvaluator(final GenericUDAFParameterInfo paramInfo) throws SemanticException {
        if (paramInfo.isAllColumns()) {
            throw new SemanticException("The specified syntax for UDAF invocation is invalid. Wildcard not accepted");
        }
        final TypeInfo[] parameters = paramInfo.getParameters();
        if (parameters.length != 3) {
            throw new UDFArgumentLengthException("Exactly three arguments are expected.");
        }
        for (int i = 0; i < parameters.length; ++i) {
            if (parameters[i].getCategory() != ObjectInspector.Category.PRIMITIVE) {
                throw new UDFArgumentTypeException(i, "Only primitive type arguments are accepted but " + parameters[i].getTypeName() + " is passed.");
            }
            switch (((PrimitiveTypeInfo)parameters[i]).getPrimitiveCategory()) {
                case BYTE:
                case SHORT:
                case INT:
                case LONG:
                case FLOAT:
                case DOUBLE: {
                    break;
                }
                case STRING: {
                    if (i < parameters.length - 1) {
                        throw new UDFArgumentTypeException(i, "Only numeric type arguments are accepted for this parameter but " + parameters[i].getTypeName() + " is passed.");
                    }
                    break;
                }
                default: {
                    throw new UDFArgumentTypeException(i, "Only numeric or string type arguments are accepted but " + parameters[i].getTypeName() + " is passed.");
                }
            }
        }
        return new ConnectionQualityScoreAssocDeviceUDAFEvaluator();
    }
    
    static {
        LOG = LoggerFactory.getLogger(ConnectionQualityScoreAssocDeviceUDAF.class);
    }
    
    public static class ConnectionQualityScoreAssocDeviceUDAFEvaluator extends GenericUDAFEvaluator
    {
        private PrimitiveObjectInspector[] inputOI;
        private StructObjectInspector soi;
        private StructField sumErrorRateField;
        private StructField countSNRField;
        private StructField sumSNRField;
        private StructField varianceSNRField;
        private DoubleObjectInspector sumErrorRateFieldOI;
        private LongObjectInspector countFieldOI;
        private DoubleObjectInspector sumFieldOI;
        private DoubleObjectInspector varianceFieldOI;
        private Object[] partialResult;
        private DoubleWritable result;
        
        public ObjectInspector init(final GenericUDAFEvaluator.Mode mode, final ObjectInspector[] parameters) throws HiveException {
            super.init(mode, parameters);
            if (mode == GenericUDAFEvaluator.Mode.PARTIAL1 || mode == GenericUDAFEvaluator.Mode.COMPLETE) {
                (this.inputOI = new PrimitiveObjectInspector[3])[0] = (PrimitiveObjectInspector)parameters[0];
                this.inputOI[1] = (PrimitiveObjectInspector)parameters[1];
                this.inputOI[2] = (PrimitiveObjectInspector)parameters[2];
            }
            else {
                this.soi = (StructObjectInspector)parameters[0];
                this.sumErrorRateField = this.soi.getStructFieldRef("sumError");
                this.countSNRField = this.soi.getStructFieldRef("count");
                this.sumSNRField = this.soi.getStructFieldRef("sum");
                this.varianceSNRField = this.soi.getStructFieldRef("variance");
                this.sumErrorRateFieldOI = (DoubleObjectInspector)this.sumErrorRateField.getFieldObjectInspector();
                this.countFieldOI = (LongObjectInspector)this.countSNRField.getFieldObjectInspector();
                this.sumFieldOI = (DoubleObjectInspector)this.sumSNRField.getFieldObjectInspector();
                this.varianceFieldOI = (DoubleObjectInspector)this.varianceSNRField.getFieldObjectInspector();
            }
            if (mode == GenericUDAFEvaluator.Mode.PARTIAL1 || mode == GenericUDAFEvaluator.Mode.PARTIAL2) {
                final ArrayList<ObjectInspector> foi = new ArrayList<ObjectInspector>();
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.writableDoubleObjectInspector);
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.writableLongObjectInspector);
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.writableDoubleObjectInspector);
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.writableDoubleObjectInspector);
                final ArrayList<String> fname = new ArrayList<String>();
                fname.add("sumError");
                fname.add("count");
                fname.add("sum");
                fname.add("variance");
                (this.partialResult = new Object[4])[0] = new DoubleWritable(0.0);
                this.partialResult[1] = new LongWritable(0L);
                this.partialResult[2] = new DoubleWritable(0.0);
                this.partialResult[3] = new DoubleWritable(0.0);
                return (ObjectInspector)ObjectInspectorFactory.getStandardStructObjectInspector((List)fname, (List)foi);
            }
            this.setResult(new DoubleWritable(0.0));
            return (ObjectInspector)PrimitiveObjectInspectorFactory.writableDoubleObjectInspector;
        }
        
        public GenericUDAFEvaluator.AggregationBuffer getNewAggregationBuffer() throws HiveException {
            final ConnectionQualityAgg result = new ConnectionQualityAgg();
            this.reset((GenericUDAFEvaluator.AggregationBuffer)result);
            return (GenericUDAFEvaluator.AggregationBuffer)result;
        }
        
        public void reset(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final ConnectionQualityAgg myagg = (ConnectionQualityAgg)agg;
            myagg.sumErrorRate = 0.0;
            myagg.count = 0L;
            myagg.sum = 0.0;
            myagg.variance = 0.0;
        }
        
        public void iterate(final GenericUDAFEvaluator.AggregationBuffer agg, final Object[] parameters) throws HiveException {
            assert parameters.length == 3;
            final ConnectionQualityAgg myagg = (ConnectionQualityAgg)agg;
            if (parameters[1] != null) {
                final double errorsSent = PrimitiveObjectInspectorUtils.getDouble(parameters[0], this.inputOI[0]);
                final double packetsSent = PrimitiveObjectInspectorUtils.getDouble(parameters[1], this.inputOI[1]);
                if (packetsSent != 0.0) {
                    final ConnectionQualityAgg connectionQualityAgg = myagg;
                    connectionQualityAgg.sumErrorRate += errorsSent / packetsSent;
                }
            }
            if (parameters[2] != null) {
                try {
                    final double v = PrimitiveObjectInspectorUtils.getDouble(parameters[2], this.inputOI[2]);
                    final ConnectionQualityAgg connectionQualityAgg2 = myagg;
                    ++connectionQualityAgg2.count;
                    final ConnectionQualityAgg connectionQualityAgg3 = myagg;
                    connectionQualityAgg3.sum += v;
                    if (myagg.count > 1L) {
                        final double t = myagg.count * v - myagg.sum;
                        final ConnectionQualityAgg connectionQualityAgg4 = myagg;
                        connectionQualityAgg4.variance += t * t / (myagg.count * (myagg.count - 1L));
                    }
                }
                catch (NumberFormatException e) {
                    ConnectionQualityScoreAssocDeviceUDAF.LOG.warn(this.getClass().getSimpleName() + " " + StringUtils.stringifyException(e));
                }
            }
        }
        
        public Object terminatePartial(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final ConnectionQualityAgg myagg = (ConnectionQualityAgg)agg;
            ((DoubleWritable)this.partialResult[0]).set(myagg.sumErrorRate);
            ((LongWritable)this.partialResult[1]).set(myagg.count);
            ((DoubleWritable)this.partialResult[2]).set(myagg.sum);
            ((DoubleWritable)this.partialResult[3]).set(myagg.variance);
            return this.partialResult;
        }
        
        public void merge(final GenericUDAFEvaluator.AggregationBuffer agg, final Object partial) throws HiveException {
            if (partial != null) {
                final ConnectionQualityAgg myagg = (ConnectionQualityAgg)agg;
                final Object partialSumErrorRate = this.soi.getStructFieldData(partial, this.sumErrorRateField);
                final double mergeSumErrorRate = this.sumErrorRateFieldOI.get(partialSumErrorRate);
                final ConnectionQualityAgg connectionQualityAgg = myagg;
                connectionQualityAgg.sumErrorRate += mergeSumErrorRate;
                final Object partialCount = this.soi.getStructFieldData(partial, this.countSNRField);
                final Object partialSum = this.soi.getStructFieldData(partial, this.sumSNRField);
                final Object partialVariance = this.soi.getStructFieldData(partial, this.varianceSNRField);
                final long n = myagg.count;
                final long m = this.countFieldOI.get(partialCount);
                if (n == 0L) {
                    myagg.variance = this.sumFieldOI.get(partialVariance);
                    myagg.count = this.countFieldOI.get(partialCount);
                    myagg.sum = this.sumFieldOI.get(partialSum);
                }
                if (m != 0L && n != 0L) {
                    final double a = myagg.sum;
                    final double b = this.sumFieldOI.get(partialSum);
                    final ConnectionQualityAgg connectionQualityAgg2 = myagg;
                    connectionQualityAgg2.count += m;
                    final ConnectionQualityAgg connectionQualityAgg3 = myagg;
                    connectionQualityAgg3.sum += b;
                    final double t = m / n * a - b;
                    final ConnectionQualityAgg connectionQualityAgg4 = myagg;
                    connectionQualityAgg4.variance += this.sumFieldOI.get(partialVariance) + n / m / (n + m) * t * t;
                }
            }
        }
        
        public Object terminate(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final ConnectionQualityAgg myAgg = (ConnectionQualityAgg)agg;
            final double leastSumErrorRate = (myAgg.sumErrorRate < 1.0) ? myAgg.sumErrorRate : 1.0;
            this.getResult().set(1.0 - leastSumErrorRate);
            final DoubleWritable leastAverage = new DoubleWritable(0.0);
            final DoubleWritable averageDiff = new DoubleWritable(0.0);
            if (myAgg.count != 0L) {
                averageDiff.set(myAgg.sum / myAgg.count * 2.0 / 100.0);
                if (myAgg.count > 1L) {
                    averageDiff.set((myAgg.sum / myAgg.count - myAgg.variance / myAgg.count) * 2.0 / 100.0);
                }
            }
            leastAverage.set((averageDiff.get() < 1.0) ? averageDiff.get() : 1.0);
            this.getResult().set((1.0 - leastSumErrorRate + leastAverage.get()) / 2.0);
            return this.getResult();
        }
        
        public void setResult(final DoubleWritable result) {
            this.result = result;
        }
        
        public DoubleWritable getResult() {
            return this.result;
        }
        
        @GenericUDAFEvaluator.AggregationType(estimable = true)
        static class ConnectionQualityAgg extends GenericUDAFEvaluator.AbstractAggregationBuffer
        {
            double sumErrorRate;
            long count;
            double sum;
            double variance;
            
            public int estimate() {
                return 32;
            }
        }
    }
}
