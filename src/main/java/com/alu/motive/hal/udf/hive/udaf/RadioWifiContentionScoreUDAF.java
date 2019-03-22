// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.udf.hive.udaf;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import java.util.List;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.io.DoubleWritable;
import java.util.ArrayList;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.DoubleObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StructField;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.typeinfo.PrimitiveTypeInfo;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFParameterInfo;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.udf.generic.AbstractGenericUDAFResolver;

@Description(name = "contention_score_radio_udaf", value = "_FUNC_(channel_utilization_score double, channel_utilization_max double)")
public class RadioWifiContentionScoreUDAF extends AbstractGenericUDAFResolver
{
    public GenericUDAFEvaluator getEvaluator(final GenericUDAFParameterInfo paramInfo) throws SemanticException {
        if (paramInfo.isAllColumns()) {
            throw new SemanticException("The specified syntax for UDAF invocation is invalid. Wildcard not accepted");
        }
        final TypeInfo[] parameters = paramInfo.getParameters();
        return this.getEvaluator(parameters);
    }
    
    public GenericUDAFEvaluator getEvaluator(final TypeInfo[] parameters) throws SemanticException {
        if (parameters.length != 2) {
            throw new UDFArgumentLengthException("Wrong arguments number encountered.");
        }
        int i = 0;
        while (i < parameters.length) {
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
                    ++i;
                    continue;
                }
                default: {
                    throw new UDFArgumentTypeException(i, "Only numeric type arguments are accepted but #" + (i + 1) + " argument is " + parameters[i].getTypeName() + ".");
                }
            }
        }
        return new MaximumChannelUtilizationScoreEvaluator();
    }
    
    public static class MaximumChannelUtilizationScoreEvaluator extends GenericUDAFEvaluator
    {
        private PrimitiveObjectInspector[] inputOI;
        private StructObjectInspector structObjectInspector;
        private StructField channelUtilization;
        private StructField maxChannelUtilizationProp;
        private DoubleObjectInspector channelUtilizationObjectInspector;
        private DoubleObjectInspector maxChannelUtilizationPropObjectInspector;
        protected Object[] partialResult;
        
        public ObjectInspector init(final GenericUDAFEvaluator.Mode mode, final ObjectInspector[] parameters) throws HiveException {
            super.init(mode, parameters);
            if (mode == GenericUDAFEvaluator.Mode.PARTIAL1 || mode == GenericUDAFEvaluator.Mode.COMPLETE) {
                this.inputOI = new PrimitiveObjectInspector[parameters.length];
                int i = 0;
                for (final ObjectInspector objectInspector : parameters) {
                    this.inputOI[i++] = (PrimitiveObjectInspector)objectInspector;
                }
            }
            else {
                this.structObjectInspector = (StructObjectInspector)parameters[0];
                this.channelUtilization = this.structObjectInspector.getStructFieldRef("score");
                this.maxChannelUtilizationProp = this.structObjectInspector.getStructFieldRef("maxScore");
                this.channelUtilizationObjectInspector = (DoubleObjectInspector)this.channelUtilization.getFieldObjectInspector();
                this.maxChannelUtilizationPropObjectInspector = (DoubleObjectInspector)this.maxChannelUtilizationProp.getFieldObjectInspector();
            }
            if (mode == GenericUDAFEvaluator.Mode.PARTIAL1 || mode == GenericUDAFEvaluator.Mode.PARTIAL2) {
                final List<String> fields = new ArrayList<String>();
                final List<ObjectInspector> structFieldObjectInspectors = new ArrayList<ObjectInspector>();
                (this.partialResult = new Object[2])[0] = new DoubleWritable();
                this.partialResult[1] = new DoubleWritable();
                structFieldObjectInspectors.add((ObjectInspector)PrimitiveObjectInspectorFactory.writableDoubleObjectInspector);
                structFieldObjectInspectors.add((ObjectInspector)PrimitiveObjectInspectorFactory.writableDoubleObjectInspector);
                fields.add("score");
                fields.add("maxScore");
                return (ObjectInspector)ObjectInspectorFactory.getStandardStructObjectInspector((List)fields, (List)structFieldObjectInspectors);
            }
            return (ObjectInspector)PrimitiveObjectInspectorFactory.writableDoubleObjectInspector;
        }
        
        public GenericUDAFEvaluator.AggregationBuffer getNewAggregationBuffer() throws HiveException {
            return (GenericUDAFEvaluator.AggregationBuffer)new MaxChannelUtilization();
        }
        
        public void reset(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final MaxChannelUtilization myAgg = (MaxChannelUtilization)agg;
            myAgg.channelUtilization = null;
            myAgg.maxChannelUtilizationProp = null;
        }
        
        public void iterate(final GenericUDAFEvaluator.AggregationBuffer agg, final Object[] parameters) throws HiveException {
            if (parameters[1] != null) {
                final MaxChannelUtilization myAgg = (MaxChannelUtilization)agg;
                final double maxScore = PrimitiveObjectInspectorUtils.getDouble(parameters[1], this.inputOI[1]);
                if (myAgg.maxChannelUtilizationProp == null) {
                    myAgg.maxChannelUtilizationProp = maxScore;
                }
                if (parameters[0] == null) {
                    return;
                }
                if (myAgg.maxChannelUtilizationProp <= 0.0 || myAgg.maxChannelUtilizationProp > 100.0) {
                    throw new UDFArgumentException("channel_utilization_max should be part of (0, 100] interval");
                }
                final double score = PrimitiveObjectInspectorUtils.getDouble(parameters[0], this.inputOI[0]);
                if (myAgg.channelUtilization == null) {
                    myAgg.channelUtilization = score;
                }
                else if (myAgg.channelUtilization < score) {
                    myAgg.channelUtilization = score;
                }
            }
        }
        
        public Object terminatePartial(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final MaxChannelUtilization myAgg = (MaxChannelUtilization)agg;
            if (myAgg.channelUtilization != null) {
                ((DoubleWritable)this.partialResult[0]).set((double)myAgg.channelUtilization);
                ((DoubleWritable)this.partialResult[1]).set((double)myAgg.maxChannelUtilizationProp);
            }
            else {
                this.partialResult[0] = new DoubleWritable();
                this.partialResult[1] = new DoubleWritable();
            }
            return this.partialResult;
        }
        
        public void merge(final GenericUDAFEvaluator.AggregationBuffer agg, final Object partial) throws HiveException {
            final MaxChannelUtilization myAgg = (MaxChannelUtilization)agg;
            final double maxChannelUtilizationPartialResult = this.maxChannelUtilizationPropObjectInspector.get(this.structObjectInspector.getStructFieldData(partial, this.maxChannelUtilizationProp));
            if (maxChannelUtilizationPartialResult != 0.0) {
                myAgg.maxChannelUtilizationProp = maxChannelUtilizationPartialResult;
            }
            final double channelUtilizationInPartialResult = this.channelUtilizationObjectInspector.get(this.structObjectInspector.getStructFieldData(partial, this.channelUtilization));
            if (myAgg.channelUtilization == null) {
                myAgg.channelUtilization = channelUtilizationInPartialResult;
            }
            else if (myAgg.channelUtilization < channelUtilizationInPartialResult) {
                myAgg.channelUtilization = channelUtilizationInPartialResult;
            }
        }
        
        public Object terminate(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final MaxChannelUtilization myAgg = (MaxChannelUtilization)agg;
            if (myAgg.maxChannelUtilizationProp == null || myAgg.channelUtilization == null) {
                return null;
            }
            final double score = myAgg.channelUtilization / myAgg.maxChannelUtilizationProp;
            return new DoubleWritable(Math.max(Math.min(1.0 - score, 1.0), 0.0));
        }
        
        public static class MaxChannelUtilization extends GenericUDAFEvaluator.AbstractAggregationBuffer
        {
            public Double channelUtilization;
            public Double maxChannelUtilizationProp;
        }
    }
}
