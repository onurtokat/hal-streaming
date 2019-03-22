// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.udf.hive.udaf;

import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
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

@Description(name = "throughput_observation_mgd_dvc_and_home_udaf", value = "_FUNC_(throughput_observation string)")
public class ManagedDeviceAndHomeNetworkThroughputObservationUDAF extends AbstractGenericUDAFResolver
{
    public GenericUDAFEvaluator getEvaluator(final GenericUDAFParameterInfo paramInfo) throws SemanticException {
        if (paramInfo.isAllColumns()) {
            throw new SemanticException("The specified syntax for UDAF invocation is invalid. Wildcard not accepted");
        }
        final TypeInfo[] parameters = paramInfo.getParameters();
        return this.getEvaluator(parameters);
    }
    
    public GenericUDAFEvaluator getEvaluator(final TypeInfo[] parameters) throws SemanticException {
        if (parameters.length != 1) {
            throw new UDFArgumentLengthException("Wrong arguments number encountered.");
        }
        if (parameters[0].getCategory() != ObjectInspector.Category.PRIMITIVE) {
            throw new UDFArgumentTypeException(0, "Only primitive type arguments are accepted but " + parameters[0].getTypeName() + " is passed.");
        }
        if (((PrimitiveTypeInfo)parameters[0]).getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.STRING) {
            throw new UDFArgumentTypeException(0, "Only string type argument are accepted as band but " + parameters[0].getTypeName() + " is passed.");
        }
        return new ThroughputObservationWorstUDAFEvaluator();
    }
    
    public static class ThroughputObservationWorstUDAFEvaluator extends GenericUDAFEvaluator
    {
        Object[] partialResult;
        private PrimitiveObjectInspector inputOI;
        
        public ObjectInspector init(final GenericUDAFEvaluator.Mode mode, final ObjectInspector[] parameters) throws HiveException {
            super.init(mode, parameters);
            this.inputOI = (PrimitiveObjectInspector)parameters[0];
            return (ObjectInspector)PrimitiveObjectInspectorFactory.javaStringObjectInspector;
        }
        
        public GenericUDAFEvaluator.AggregationBuffer getNewAggregationBuffer() throws HiveException {
            return (GenericUDAFEvaluator.AggregationBuffer)new Worst();
        }
        
        public void reset(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final Worst myAgg = (Worst)agg;
            myAgg.worst = "unknown";
        }
        
        public void iterate(final GenericUDAFEvaluator.AggregationBuffer agg, final Object[] parameters) throws HiveException {
            if (null != parameters[0]) {
                final Worst myAgg = (Worst)agg;
                final String aggregatedWorst = myAgg.worst;
                final String parameterWorst = PrimitiveObjectInspectorUtils.getString(parameters[0], this.inputOI);
                final String worst = this.selectWorst(aggregatedWorst, parameterWorst);
                myAgg.worst = worst;
            }
        }
        
        public Object terminatePartial(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final Worst myAgg = (Worst)agg;
            return myAgg.worst;
        }
        
        public void merge(final GenericUDAFEvaluator.AggregationBuffer agg, final Object partial) throws HiveException {
            final Worst myAgg = (Worst)agg;
            final String aggregatedWorst = myAgg.worst;
            final String parameterWorst = PrimitiveObjectInspectorUtils.getString(partial, this.inputOI);
            final String worst = this.selectWorst(aggregatedWorst, parameterWorst);
            myAgg.worst = worst;
        }
        
        private String selectWorst(final String aggregatedWorst, final String parameterWorst) {
            final ThroughputObservation aggregated = ThroughputObservation.valueOf(aggregatedWorst.toUpperCase());
            final ThroughputObservation parameter = ThroughputObservation.valueOf(parameterWorst.toUpperCase());
            if (aggregated.compareTo(parameter) < 0) {
                return aggregatedWorst;
            }
            return parameterWorst;
        }
        
        public Object terminate(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final Worst myAgg = (Worst)agg;
            return myAgg.worst;
        }
        
        public static class Worst extends GenericUDAFEvaluator.AbstractAggregationBuffer
        {
            public static final String DEFAULT_WORST = "unknown";
            public String worst;
            
            public Worst() {
                this.worst = "unknown";
            }
        }
    }
}
