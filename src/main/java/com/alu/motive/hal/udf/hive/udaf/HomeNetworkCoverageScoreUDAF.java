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

@Description(name = "coverage_score_home_udaf", value = "_FUNC_(mgd_dvc_coverage_score double, home_coverage_offset double)")
public class HomeNetworkCoverageScoreUDAF extends AbstractGenericUDAFResolver
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
        return new HomeNetworkCoverageScoreEvaluator();
    }
    
    public static class HomeNetworkCoverageScoreEvaluator extends GenericUDAFEvaluator
    {
        private PrimitiveObjectInspector[] inputOI;
        private StructObjectInspector structObjectInspector;
        private StructField min;
        private StructField offset;
        private DoubleObjectInspector offsetObjectInspector;
        private DoubleObjectInspector minObjectInspector;
        private Object[] partialResult;
        
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
                this.min = this.structObjectInspector.getStructFieldRef("min");
                this.offset = this.structObjectInspector.getStructFieldRef("offset");
                this.minObjectInspector = (DoubleObjectInspector)this.min.getFieldObjectInspector();
                this.offsetObjectInspector = (DoubleObjectInspector)this.offset.getFieldObjectInspector();
            }
            if (mode == GenericUDAFEvaluator.Mode.PARTIAL1 || mode == GenericUDAFEvaluator.Mode.PARTIAL2) {
                final List<String> fields = new ArrayList<String>();
                final List<ObjectInspector> structFieldObjectInspectors = new ArrayList<ObjectInspector>();
                (this.partialResult = new Object[3])[0] = new DoubleWritable();
                this.partialResult[1] = new DoubleWritable();
                structFieldObjectInspectors.add((ObjectInspector)PrimitiveObjectInspectorFactory.writableDoubleObjectInspector);
                structFieldObjectInspectors.add((ObjectInspector)PrimitiveObjectInspectorFactory.writableDoubleObjectInspector);
                fields.add("min");
                fields.add("offset");
                return (ObjectInspector)ObjectInspectorFactory.getStandardStructObjectInspector((List)fields, (List)structFieldObjectInspectors);
            }
            return (ObjectInspector)PrimitiveObjectInspectorFactory.writableDoubleObjectInspector;
        }
        
        public GenericUDAFEvaluator.AggregationBuffer getNewAggregationBuffer() throws HiveException {
            return (GenericUDAFEvaluator.AggregationBuffer)new Mean();
        }
        
        public void reset(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final Mean mean = (Mean)agg;
            mean.min = null;
            mean.offset = null;
        }
        
        public void iterate(final GenericUDAFEvaluator.AggregationBuffer agg, final Object[] parameters) throws HiveException {
            final Mean myAgg = (Mean)agg;
            final double offset = PrimitiveObjectInspectorUtils.getDouble(parameters[1], this.inputOI[1]);
            if (myAgg.offset == null) {
                myAgg.offset = offset;
            }
            if (null != parameters[0]) {
                final double score = PrimitiveObjectInspectorUtils.getDouble(parameters[0], this.inputOI[0]);
                if (myAgg.min == null) {
                    myAgg.min = score;
                }
                else {
                    myAgg.min = Math.min(myAgg.min, score);
                }
            }
        }
        
        public void merge(final GenericUDAFEvaluator.AggregationBuffer agg, final Object partial) throws HiveException {
            final Mean myAgg = (Mean)agg;
            this.minObjectInspector.get(this.structObjectInspector.getStructFieldData(partial, this.min));
            if (myAgg.min == null) {
                myAgg.min = this.minObjectInspector.get(this.structObjectInspector.getStructFieldData(partial, this.min));
                myAgg.offset = this.offsetObjectInspector.get(this.structObjectInspector.getStructFieldData(partial, this.offset));
            }
            else {
                if (!myAgg.offset.equals(this.offsetObjectInspector.get(this.structObjectInspector.getStructFieldData(partial, this.offset)))) {
                    throw new UDFArgumentException("The offsets must be the same");
                }
                myAgg.min = Math.min(myAgg.min, this.minObjectInspector.get(this.structObjectInspector.getStructFieldData(partial, this.min)));
            }
        }
        
        public Object terminate(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final Mean myAgg = (Mean)agg;
            if (myAgg.min != null) {
                return new DoubleWritable(Math.max(Math.min(myAgg.offset + myAgg.min, 1.0), 0.0));
            }
            return null;
        }
        
        public Object terminatePartial(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final Mean myAgg = (Mean)agg;
            if (myAgg.min != null && myAgg.offset != null) {
                ((DoubleWritable)this.partialResult[0]).set((double)myAgg.min);
                ((DoubleWritable)this.partialResult[1]).set((double)myAgg.offset);
            }
            else {
                this.partialResult[0] = new DoubleWritable();
                this.partialResult[1] = new DoubleWritable();
            }
            return this.partialResult;
        }
        
        public static class Mean extends GenericUDAFEvaluator.AbstractAggregationBuffer
        {
            Double min;
            Double offset;
        }
    }
}
