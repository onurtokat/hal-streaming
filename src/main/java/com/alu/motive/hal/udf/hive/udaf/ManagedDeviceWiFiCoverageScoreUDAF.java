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
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.hive.serde2.io.DoubleWritable;
import java.util.ArrayList;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.IntObjectInspector;
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

@Description(name = "coverage_score_mgd_dvc_udaf", value = "_FUNC_(assoc_dvc_coverage_score double, nbr_samples int, mgd_dvc_coverage_offset double)")
public class ManagedDeviceWiFiCoverageScoreUDAF extends AbstractGenericUDAFResolver
{
    public GenericUDAFEvaluator getEvaluator(final GenericUDAFParameterInfo paramInfo) throws SemanticException {
        if (paramInfo.isAllColumns()) {
            throw new SemanticException("The specified syntax for UDAF invocation is invalid. Wildcard not accepted");
        }
        final TypeInfo[] parameters = paramInfo.getParameters();
        return this.getEvaluator(parameters);
    }
    
    public GenericUDAFEvaluator getEvaluator(final TypeInfo[] parameters) throws SemanticException {
        if (parameters.length != 3) {
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
        return new ManagedDeviceCoverageScoreEvaluator();
    }
    
    public static class ManagedDeviceCoverageScoreEvaluator extends GenericUDAFEvaluator
    {
        private PrimitiveObjectInspector[] inputOI;
        private StructObjectInspector structObjectInspector;
        private StructField average;
        private StructField count;
        private DoubleObjectInspector averageObjectInspector;
        private IntObjectInspector countObjectInspector;
        private Object[] partialResult;
        private StructField coverageOffset;
        private DoubleObjectInspector coverageOffsetObjectInspector;
        
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
                this.average = this.structObjectInspector.getStructFieldRef("average");
                this.count = this.structObjectInspector.getStructFieldRef("count");
                this.coverageOffset = this.structObjectInspector.getStructFieldRef("coverageOffset");
                this.averageObjectInspector = (DoubleObjectInspector)this.average.getFieldObjectInspector();
                this.countObjectInspector = (IntObjectInspector)this.count.getFieldObjectInspector();
                this.coverageOffsetObjectInspector = (DoubleObjectInspector)this.coverageOffset.getFieldObjectInspector();
            }
            if (mode == GenericUDAFEvaluator.Mode.PARTIAL1 || mode == GenericUDAFEvaluator.Mode.PARTIAL2) {
                final List<String> fields = new ArrayList<String>();
                final List<ObjectInspector> structFieldObjectInspectors = new ArrayList<ObjectInspector>();
                (this.partialResult = new Object[3])[0] = new DoubleWritable();
                this.partialResult[1] = new IntWritable();
                this.partialResult[2] = new DoubleWritable();
                structFieldObjectInspectors.add((ObjectInspector)PrimitiveObjectInspectorFactory.writableDoubleObjectInspector);
                structFieldObjectInspectors.add((ObjectInspector)PrimitiveObjectInspectorFactory.writableIntObjectInspector);
                structFieldObjectInspectors.add((ObjectInspector)PrimitiveObjectInspectorFactory.writableDoubleObjectInspector);
                fields.add("average");
                fields.add("count");
                fields.add("coverageOffset");
                return (ObjectInspector)ObjectInspectorFactory.getStandardStructObjectInspector((List)fields, (List)structFieldObjectInspectors);
            }
            return (ObjectInspector)PrimitiveObjectInspectorFactory.writableDoubleObjectInspector;
        }
        
        public GenericUDAFEvaluator.AggregationBuffer getNewAggregationBuffer() throws HiveException {
            return (GenericUDAFEvaluator.AggregationBuffer)new Mean();
        }
        
        public void reset(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final Mean myAgg = (Mean)agg;
            myAgg.sumOfCoverageScoreMultipliedByNumberOfSamples = 0.0;
            myAgg.sumOfNumberOfSamples = 0;
            myAgg.offset = null;
        }
        
        public void iterate(final GenericUDAFEvaluator.AggregationBuffer agg, final Object[] parameters) throws HiveException {
            final Mean myAgg = (Mean)agg;
            final double coverageOffset = PrimitiveObjectInspectorUtils.getDouble(parameters[2], this.inputOI[2]);
            if (myAgg.offset == null) {
                myAgg.offset = coverageOffset;
            }
            if (null != parameters[0] && null != parameters[1]) {
                final double coverageScore = PrimitiveObjectInspectorUtils.getDouble(parameters[0], this.inputOI[0]);
                final int numberOfSamples = PrimitiveObjectInspectorUtils.getInt(parameters[1], this.inputOI[1]);
                final Mean mean = myAgg;
                mean.sumOfNumberOfSamples += numberOfSamples;
                final Mean mean2 = myAgg;
                mean2.sumOfCoverageScoreMultipliedByNumberOfSamples += coverageScore * numberOfSamples;
            }
        }
        
        public Object terminatePartial(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final Mean myAgg = (Mean)agg;
            ((DoubleWritable)this.partialResult[0]).set(myAgg.sumOfCoverageScoreMultipliedByNumberOfSamples);
            ((IntWritable)this.partialResult[1]).set(myAgg.sumOfNumberOfSamples);
            ((DoubleWritable)this.partialResult[2]).set((double)myAgg.offset);
            return this.partialResult;
        }
        
        public void merge(final GenericUDAFEvaluator.AggregationBuffer agg, final Object partial) throws HiveException {
            final Mean myAgg = (Mean)agg;
            if (myAgg.sumOfNumberOfSamples == 0) {
                myAgg.sumOfCoverageScoreMultipliedByNumberOfSamples = this.averageObjectInspector.get(this.structObjectInspector.getStructFieldData(partial, this.average));
                myAgg.sumOfNumberOfSamples = this.countObjectInspector.get(this.structObjectInspector.getStructFieldData(partial, this.count));
                myAgg.offset = this.coverageOffsetObjectInspector.get(this.structObjectInspector.getStructFieldData(partial, this.coverageOffset));
            }
            else {
                if (!myAgg.offset.equals(this.coverageOffsetObjectInspector.get(this.structObjectInspector.getStructFieldData(partial, this.coverageOffset)))) {
                    throw new UDFArgumentException("The offsets must be the same");
                }
                final Mean mean = myAgg;
                mean.sumOfCoverageScoreMultipliedByNumberOfSamples += this.averageObjectInspector.get(this.structObjectInspector.getStructFieldData(partial, this.average));
                final Mean mean2 = myAgg;
                mean2.sumOfNumberOfSamples += this.countObjectInspector.get(this.structObjectInspector.getStructFieldData(partial, this.count));
            }
        }
        
        public Object terminate(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final Mean myAgg = (Mean)agg;
            if (myAgg.sumOfNumberOfSamples > 0) {
                final double score = myAgg.offset + myAgg.sumOfCoverageScoreMultipliedByNumberOfSamples / myAgg.sumOfNumberOfSamples;
                return new DoubleWritable(Math.max(Math.min(score, 1.0), 0.0));
            }
            return null;
        }
        
        public static class Mean extends GenericUDAFEvaluator.AbstractAggregationBuffer
        {
            int sumOfNumberOfSamples;
            double sumOfCoverageScoreMultipliedByNumberOfSamples;
            Double offset;
        }
    }
}
