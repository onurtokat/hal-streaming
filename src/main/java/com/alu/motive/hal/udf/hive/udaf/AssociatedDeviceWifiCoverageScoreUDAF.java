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
import org.apache.hadoop.hive.serde2.io.HiveVarcharWritable;
import org.apache.hadoop.hive.serde2.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import java.util.ArrayList;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.HiveVarcharObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.DoubleObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.IntObjectInspector;
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

@Description(name = "coverage_score_assoc_dvc_udaf", value = "_FUNC_(signal_strength double, frequency_band string, 24ghz_coverage_offset double, 50ghz_coverage_offset double, 24GHz_max_power double, 50GHz_max_power double)")
public class AssociatedDeviceWifiCoverageScoreUDAF extends AbstractGenericUDAFResolver
{
    public GenericUDAFEvaluator getEvaluator(final GenericUDAFParameterInfo paramInfo) throws SemanticException {
        if (paramInfo.isAllColumns()) {
            throw new SemanticException("The specified syntax for UDAF invocation is invalid. Wildcard not accepted");
        }
        final TypeInfo[] parameters = paramInfo.getParameters();
        return this.getEvaluator(parameters);
    }
    
    public GenericUDAFEvaluator getEvaluator(final TypeInfo[] parameters) throws SemanticException {
        if (parameters.length != 6) {
            throw new UDFArgumentLengthException("Wrong arguments number encountered.");
        }
        for (int i = 0; i < parameters.length; ++i) {
            if (parameters[i].getCategory() != ObjectInspector.Category.PRIMITIVE) {
                throw new UDFArgumentTypeException(i, "Only primitive type arguments are accepted but " + parameters[i].getTypeName() + " is passed.");
            }
            if (i == 1) {
                if (((PrimitiveTypeInfo)parameters[i]).getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.STRING) {
                    throw new UDFArgumentTypeException(i, "Only string type argument are accepted as band but " + parameters[i].getTypeName() + " is passed.");
                }
            }
            else {
                switch (((PrimitiveTypeInfo)parameters[i]).getPrimitiveCategory()) {
                    case BYTE:
                    case SHORT:
                    case INT:
                    case LONG:
                    case FLOAT:
                    case DOUBLE: {
                        break;
                    }
                    default: {
                        throw new UDFArgumentTypeException(i, "Only numeric type arguments are accepted but #" + (i + 1) + " argument is " + parameters[i].getTypeName() + ".");
                    }
                }
            }
        }
        return new GenericSignalStrenghtMeanUDAFEvaluator();
    }
    
    public static class GenericSignalStrenghtMeanUDAFEvaluator extends GenericUDAFEvaluator
    {
        private static final int WHOLE = 100;
        private static final int SIGNALSTRENGTH_INDEX = 0;
        private static final int OPERATINGBAND_INDEX = 1;
        Object[] partialResult;
        private PrimitiveObjectInspector[] inputOI;
        private StructObjectInspector structObjectInspector;
        private StructField countValues;
        private StructField sumValues;
        private IntObjectInspector countValuesObjectInspector;
        private DoubleObjectInspector sumValuesObjectInspector;
        private StructField sumDiff;
        private StructField mean;
        private StructField variance;
        private DoubleObjectInspector sumDiffObjectInspector;
        private DoubleObjectInspector meanObjectInspector;
        private DoubleObjectInspector varianceObjectInspector;
        private StructField operatingFrequencyBand;
        private StructField coverageScoreOffset;
        private StructField maxPower;
        private HiveVarcharObjectInspector operatingFrequencyBandObjectInspector;
        private DoubleObjectInspector coverageScoreOffsetObjectInspector;
        private DoubleObjectInspector maxPowerObjectInspector;
        
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
                this.sumValues = this.structObjectInspector.getStructFieldRef("sumValues");
                this.countValues = this.structObjectInspector.getStructFieldRef("countValues");
                this.sumDiff = this.structObjectInspector.getStructFieldRef("sumDiff");
                this.mean = this.structObjectInspector.getStructFieldRef("mean");
                this.variance = this.structObjectInspector.getStructFieldRef("variance");
                this.operatingFrequencyBand = this.structObjectInspector.getStructFieldRef("operatingFrequencyBand");
                this.coverageScoreOffset = this.structObjectInspector.getStructFieldRef("coverageScoreOffset");
                this.maxPower = this.structObjectInspector.getStructFieldRef("maxPower");
                this.sumValuesObjectInspector = (DoubleObjectInspector)this.sumValues.getFieldObjectInspector();
                this.countValuesObjectInspector = (IntObjectInspector)this.countValues.getFieldObjectInspector();
                this.sumDiffObjectInspector = (DoubleObjectInspector)this.sumDiff.getFieldObjectInspector();
                this.meanObjectInspector = (DoubleObjectInspector)this.mean.getFieldObjectInspector();
                this.varianceObjectInspector = (DoubleObjectInspector)this.variance.getFieldObjectInspector();
                this.operatingFrequencyBandObjectInspector = (HiveVarcharObjectInspector)this.operatingFrequencyBand.getFieldObjectInspector();
                this.coverageScoreOffsetObjectInspector = (DoubleObjectInspector)this.coverageScoreOffset.getFieldObjectInspector();
                this.maxPowerObjectInspector = (DoubleObjectInspector)this.maxPower.getFieldObjectInspector();
            }
            if (mode == GenericUDAFEvaluator.Mode.PARTIAL1 || mode == GenericUDAFEvaluator.Mode.PARTIAL2) {
                final List<String> fields = new ArrayList<String>();
                final List<ObjectInspector> structFieldObjectInspectors = new ArrayList<ObjectInspector>();
                (this.partialResult = new Object[8])[0] = new IntWritable();
                this.partialResult[1] = new DoubleWritable();
                this.partialResult[2] = new DoubleWritable();
                this.partialResult[3] = new DoubleWritable();
                this.partialResult[4] = new DoubleWritable();
                this.partialResult[5] = new HiveVarcharWritable();
                this.partialResult[6] = new DoubleWritable();
                this.partialResult[7] = new DoubleWritable();
                structFieldObjectInspectors.add((ObjectInspector)PrimitiveObjectInspectorFactory.writableIntObjectInspector);
                structFieldObjectInspectors.add((ObjectInspector)PrimitiveObjectInspectorFactory.writableDoubleObjectInspector);
                structFieldObjectInspectors.add((ObjectInspector)PrimitiveObjectInspectorFactory.writableDoubleObjectInspector);
                structFieldObjectInspectors.add((ObjectInspector)PrimitiveObjectInspectorFactory.writableDoubleObjectInspector);
                structFieldObjectInspectors.add((ObjectInspector)PrimitiveObjectInspectorFactory.writableDoubleObjectInspector);
                structFieldObjectInspectors.add((ObjectInspector)PrimitiveObjectInspectorFactory.writableHiveVarcharObjectInspector);
                structFieldObjectInspectors.add((ObjectInspector)PrimitiveObjectInspectorFactory.writableDoubleObjectInspector);
                structFieldObjectInspectors.add((ObjectInspector)PrimitiveObjectInspectorFactory.writableDoubleObjectInspector);
                fields.add("countValues");
                fields.add("sumDiff");
                fields.add("mean");
                fields.add("sumValues");
                fields.add("variance");
                fields.add("operatingFrequencyBand");
                fields.add("coverageScoreOffset");
                fields.add("maxPower");
                return (ObjectInspector)ObjectInspectorFactory.getStandardStructObjectInspector((List)fields, (List)structFieldObjectInspectors);
            }
            return (ObjectInspector)PrimitiveObjectInspectorFactory.writableDoubleObjectInspector;
        }
        
        public GenericUDAFEvaluator.AggregationBuffer getNewAggregationBuffer() throws HiveException {
            return (GenericUDAFEvaluator.AggregationBuffer)new Mean();
        }
        
        public void reset(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final Mean myAgg = (Mean)agg;
            myAgg.countValues = 0;
            myAgg.sumDiff = 0.0;
            myAgg.mean = 0.0;
            myAgg.variance = 0.0;
            myAgg.sumValues = 0.0;
            myAgg.operatingFrequencyBand = null;
            myAgg.coverageScoreOffset = null;
            myAgg.maxPower = null;
        }
        
        public void iterate(final GenericUDAFEvaluator.AggregationBuffer agg, final Object[] parameters) throws HiveException {
            if (null != parameters[0] && null != parameters[1]) {
                final String frequencyParam = PrimitiveObjectInspectorUtils.getString(parameters[1], this.inputOI[1]);
                if (frequencyParam.equals("2.4GHz") || frequencyParam.equals("5GHz")) {
                    final Mean myAgg = (Mean)agg;
                    this.setStaticParametersAtFirstIteration(parameters, myAgg);
                    final double value = PrimitiveObjectInspectorUtils.getDouble(parameters[0], this.inputOI[0]);
                    final Mean mean = myAgg;
                    mean.sumValues += value;
                    final Mean mean2 = myAgg;
                    ++mean2.countValues;
                    final double delta = myAgg.mean + (value - myAgg.mean) / myAgg.countValues;
                    final Mean mean3 = myAgg;
                    mean3.sumDiff += (value - myAgg.mean) * (value - delta);
                    myAgg.mean = delta;
                    myAgg.variance = myAgg.sumDiff / myAgg.countValues;
                }
            }
        }
        
        private void setStaticParametersAtFirstIteration(final Object[] parameters, final Mean agg) throws UDFArgumentException {
            int maxPowerIndex = -1;
            int coverageScoreOffsetIndex = -1;
            if (null == agg.operatingFrequencyBand) {
                agg.operatingFrequencyBand = PrimitiveObjectInspectorUtils.getString(parameters[1], this.inputOI[1]);
                if (agg.operatingFrequencyBand.equals("2.4GHz")) {
                    coverageScoreOffsetIndex = 2;
                    maxPowerIndex = 4;
                }
                else {
                    coverageScoreOffsetIndex = 3;
                    maxPowerIndex = 5;
                }
            }
            if (null == agg.coverageScoreOffset) {
                agg.coverageScoreOffset = PrimitiveObjectInspectorUtils.getDouble(parameters[coverageScoreOffsetIndex], this.inputOI[coverageScoreOffsetIndex]);
            }
            if (null == agg.maxPower) {
                agg.maxPower = PrimitiveObjectInspectorUtils.getDouble(parameters[maxPowerIndex], this.inputOI[maxPowerIndex]);
            }
        }
        
        public Object terminatePartial(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final Mean myAgg = (Mean)agg;
            ((IntWritable)this.partialResult[0]).set(myAgg.countValues);
            ((DoubleWritable)this.partialResult[1]).set(myAgg.sumDiff);
            ((DoubleWritable)this.partialResult[2]).set(myAgg.mean);
            ((DoubleWritable)this.partialResult[3]).set(myAgg.sumValues);
            ((DoubleWritable)this.partialResult[4]).set(myAgg.variance);
            if (myAgg.operatingFrequencyBand != null) {
                ((HiveVarcharWritable)this.partialResult[5]).set(myAgg.operatingFrequencyBand);
            }
            if (myAgg.coverageScoreOffset != null) {
                ((DoubleWritable)this.partialResult[6]).set((double)myAgg.coverageScoreOffset);
            }
            if (myAgg.maxPower != null) {
                ((DoubleWritable)this.partialResult[7]).set((double)myAgg.maxPower);
            }
            return this.partialResult;
        }
        
        public void merge(final GenericUDAFEvaluator.AggregationBuffer agg, final Object partial) throws HiveException {
            final Mean myAgg = (Mean)agg;
            if (0 == myAgg.countValues) {
                this.setStaticData(partial, myAgg);
                this.initZeroedAgg(partial, myAgg);
            }
            else {
                this.verifyStaticData(partial, myAgg);
                this.combineAgg(partial, myAgg);
            }
        }
        
        private void verifyStaticData(final Object partial, final Mean myAgg) throws UDFArgumentException {
            if (this.doesBothPartialAndAggHaveValues(partial, myAgg) && (this.isCoverageScoreOffsetChanged(partial, myAgg) || this.isMaxPowerChanged(partial, myAgg) || this.isOperatingBandFrequencyChanged(partial, myAgg))) {
                throw new UDFArgumentException("Different dimensioned data is to be merged");
            }
        }
        
        private boolean doesBothPartialAndAggHaveValues(final Object partial, final Mean myAgg) {
            return myAgg.countValues > 0 && this.countValuesObjectInspector.get(this.structObjectInspector.getStructFieldData(partial, this.countValues)) > 0;
        }
        
        private boolean isOperatingBandFrequencyChanged(final Object partial, final Mean myAgg) {
            return !myAgg.operatingFrequencyBand.equals(this.operatingFrequencyBandObjectInspector.getPrimitiveJavaObject(this.structObjectInspector.getStructFieldData(partial, this.operatingFrequencyBand)).getValue());
        }
        
        private boolean isMaxPowerChanged(final Object partial, final Mean myAgg) {
            return !myAgg.maxPower.equals(this.maxPowerObjectInspector.get(this.structObjectInspector.getStructFieldData(partial, this.maxPower)));
        }
        
        private boolean isCoverageScoreOffsetChanged(final Object partial, final Mean myAgg) {
            return !myAgg.coverageScoreOffset.equals(this.coverageScoreOffsetObjectInspector.get(this.structObjectInspector.getStructFieldData(partial, this.coverageScoreOffset)));
        }
        
        private void setStaticData(final Object partial, final Mean myAgg) {
            myAgg.coverageScoreOffset = this.coverageScoreOffsetObjectInspector.get(this.structObjectInspector.getStructFieldData(partial, this.coverageScoreOffset));
            myAgg.maxPower = this.maxPowerObjectInspector.get(this.structObjectInspector.getStructFieldData(partial, this.maxPower));
            myAgg.operatingFrequencyBand = this.operatingFrequencyBandObjectInspector.getPrimitiveJavaObject(this.structObjectInspector.getStructFieldData(partial, this.operatingFrequencyBand)).getValue();
        }
        
        private void combineAgg(final Object partial, final Mean myAgg) {
            final int originalCount = myAgg.countValues;
            final int mergingCount = this.countValuesObjectInspector.get(this.structObjectInspector.getStructFieldData(partial, this.countValues));
            final double originalMean = myAgg.mean;
            final double mergingMean = this.meanObjectInspector.get(this.structObjectInspector.getStructFieldData(partial, this.mean));
            final double originalVariance = myAgg.variance;
            final double mergingVariance = this.varianceObjectInspector.get(this.structObjectInspector.getStructFieldData(partial, this.variance));
            myAgg.sumValues += this.sumValuesObjectInspector.get(this.structObjectInspector.getStructFieldData(partial, this.sumValues));
            myAgg.mean = this.combineMean(originalCount, mergingCount, originalMean, mergingMean);
            final double combinedMean = myAgg.mean;
            myAgg.variance = this.combineVariance(originalCount, mergingCount, originalMean, mergingMean, originalVariance, mergingVariance, combinedMean);
            myAgg.countValues += mergingCount;
        }
        
        private double combineMean(final int originalCount, final int mergingCount, final double originalMean, final double mergingMean) {
            return (originalMean * originalCount + mergingMean * mergingCount) / (originalCount + mergingCount);
        }
        
        private double combineVariance(final int originalCount, final int mergingCount, final double originalMean, final double mergingMean, final double originalVariance, final double mergingVariance, final double combinedMean) {
            return (originalCount * (originalVariance + (originalMean - combinedMean) * (originalMean - combinedMean)) + mergingCount * (mergingVariance + (mergingMean - combinedMean) * (mergingMean - combinedMean))) / (originalCount + mergingCount);
        }
        
        private void initZeroedAgg(final Object partial, final Mean myAgg) {
            final int n2 = this.countValuesObjectInspector.get(this.structObjectInspector.getStructFieldData(partial, this.countValues));
            final double x2 = this.meanObjectInspector.get(this.structObjectInspector.getStructFieldData(partial, this.mean));
            myAgg.sumValues = this.sumValuesObjectInspector.get(this.structObjectInspector.getStructFieldData(partial, this.sumValues));
            myAgg.countValues = n2;
            myAgg.mean = x2;
            myAgg.variance = this.varianceObjectInspector.get(this.structObjectInspector.getStructFieldData(partial, this.variance));
        }
        
        public Object terminate(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final Mean myAgg = (Mean)agg;
            if (myAgg.countValues > 0) {
                final double score = myAgg.coverageScoreOffset + Math.max(Math.min((100.0 - myAgg.maxPower + myAgg.mean - Math.sqrt(myAgg.variance)) / 100.0, 1.0), 0.0);
                final DoubleWritable result = new DoubleWritable(Math.min(Math.max(score, 0.0), 1.0));
                return result;
            }
            return null;
        }
        
        public static class Mean extends GenericUDAFEvaluator.AbstractAggregationBuffer
        {
            public double sumDiff;
            public double mean;
            public int countValues;
            public double sumValues;
            public double variance;
            public Double coverageScoreOffset;
            public Double maxPower;
            public String operatingFrequencyBand;
        }
    }
}
