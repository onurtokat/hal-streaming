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
import org.apache.hadoop.hive.serde2.objectinspector.primitive.LongObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StructField;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.slf4j.LoggerFactory;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
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

@Description(name = "connection_failure_score_assoc_device_udaf", value = "_FUNC_(expr, expr]) ")
public class ConnectionFailureScoreAssocDeviceUDAF extends AbstractGenericUDAFResolver
{
    private static final Logger LOG;
    
    public ConnectionFailureScoreAssocDeviceUDAFEvaluator getEvaluator(final TypeInfo[] parameters) throws SemanticException {
        return new ConnectionFailureScoreAssocDeviceUDAFEvaluator();
    }
    
    public GenericUDAFEvaluator getEvaluator(final GenericUDAFParameterInfo paramInfo) throws SemanticException {
        if (paramInfo.isAllColumns()) {
            throw new SemanticException("The specified syntax for UDAF invocation is invalid. Wildcard not accepted");
        }
        final TypeInfo[] parameters = paramInfo.getParameters();
        if (parameters.length != 2) {
            throw new UDFArgumentLengthException("Exactly two arguments are expected.");
        }
        for (int i = 0; i < parameters.length; ++i) {
            if (parameters[i].getCategory() != ObjectInspector.Category.PRIMITIVE) {
                throw new UDFArgumentTypeException(i, "Only primitive type arguments are accepted but " + parameters[i].getTypeName() + " is passed.");
            }
            if (((PrimitiveTypeInfo)parameters[i]).getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.LONG) {
                throw new UDFArgumentTypeException(i, "Only Long type arguments are accepted but " + parameters[i].getTypeName() + " is passed.");
            }
        }
        return new ConnectionFailureScoreAssocDeviceUDAFEvaluator();
    }
    
    static {
        LOG = LoggerFactory.getLogger(ConnectionFailureScoreAssocDeviceUDAF.class);
    }
    
    public static class ConnectionFailureScoreAssocDeviceUDAFEvaluator extends GenericUDAFEvaluator
    {
        protected PrimitiveObjectInspector[] inputOI;
        private Object[] partialResult;
        private StructObjectInspector soi;
        private StructField count1;
        private StructField count2;
        private LongObjectInspector count1OI;
        private LongObjectInspector count2OI;
        private DoubleWritable result;
        
        public ObjectInspector init(final GenericUDAFEvaluator.Mode m, final ObjectInspector[] parameters) throws HiveException {
            super.init(m, parameters);
            if (m == GenericUDAFEvaluator.Mode.PARTIAL1 || m == GenericUDAFEvaluator.Mode.COMPLETE) {
                (this.inputOI = new PrimitiveObjectInspector[2])[0] = (PrimitiveObjectInspector)parameters[0];
                this.inputOI[1] = (PrimitiveObjectInspector)parameters[1];
            }
            else {
                this.soi = (StructObjectInspector)parameters[0];
                this.count1 = this.soi.getStructFieldRef("count1");
                this.count2 = this.soi.getStructFieldRef("count2");
                this.count1OI = (LongObjectInspector)this.count1.getFieldObjectInspector();
                this.count2OI = (LongObjectInspector)this.count2.getFieldObjectInspector();
            }
            if (m == GenericUDAFEvaluator.Mode.PARTIAL1 || m == GenericUDAFEvaluator.Mode.PARTIAL2) {
                final ArrayList<ObjectInspector> foi = new ArrayList<ObjectInspector>();
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.writableLongObjectInspector);
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.writableLongObjectInspector);
                final ArrayList<String> fname = new ArrayList<String>();
                fname.add("count1");
                fname.add("count2");
                (this.partialResult = new Object[2])[0] = new LongWritable(0L);
                this.partialResult[1] = new LongWritable(0L);
                return (ObjectInspector)ObjectInspectorFactory.getStandardStructObjectInspector((List)fname, (List)foi);
            }
            this.result = new DoubleWritable(0.0);
            return (ObjectInspector)PrimitiveObjectInspectorFactory.writableDoubleObjectInspector;
        }
        
        public GenericUDAFEvaluator.AggregationBuffer getNewAggregationBuffer() throws HiveException {
            final ConnectionFailureAgg buffer = new ConnectionFailureAgg();
            this.reset((GenericUDAFEvaluator.AggregationBuffer)buffer);
            return (GenericUDAFEvaluator.AggregationBuffer)buffer;
        }
        
        public void reset(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            ((ConnectionFailureAgg)agg).countParam1 = 0L;
            ((ConnectionFailureAgg)agg).countParam2 = 0L;
        }
        
        public void iterate(final GenericUDAFEvaluator.AggregationBuffer agg, final Object[] parameters) throws HiveException {
            assert parameters.length == 2;
            final ConnectionFailureAgg myagg = (ConnectionFailureAgg)agg;
            if (parameters == null) {
                return;
            }
            if (parameters[0] != null && PrimitiveObjectInspectorUtils.getLong(parameters[0], this.inputOI[0]) != 0L) {
                final ConnectionFailureAgg connectionFailureAgg = myagg;
                ++connectionFailureAgg.countParam1;
            }
            if (parameters[1] != null && PrimitiveObjectInspectorUtils.getLong(parameters[1], this.inputOI[1]) != 0L) {
                final ConnectionFailureAgg connectionFailureAgg2 = myagg;
                ++connectionFailureAgg2.countParam2;
            }
        }
        
        public void merge(final GenericUDAFEvaluator.AggregationBuffer agg, final Object partial) throws HiveException {
            if (partial != null) {
                final ConnectionFailureAgg connectionFailureAgg;
                final ConnectionFailureAgg myAgg = connectionFailureAgg = (ConnectionFailureAgg)agg;
                connectionFailureAgg.countParam1 += this.count1OI.get(this.soi.getStructFieldData(partial, this.count1));
                final ConnectionFailureAgg connectionFailureAgg2 = myAgg;
                connectionFailureAgg2.countParam2 += this.count2OI.get(this.soi.getStructFieldData(partial, this.count2));
            }
        }
        
        public Object terminate(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final ConnectionFailureAgg myAgg = (ConnectionFailureAgg)agg;
            double expr = 1.0;
            if (myAgg.countParam2 != 0L) {
                expr = 1.0 - myAgg.countParam1 / myAgg.countParam2;
            }
            return this.result = new DoubleWritable((expr < 1.0) ? expr : 1.0);
        }
        
        public Object terminatePartial(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final ConnectionFailureAgg myAgg = (ConnectionFailureAgg)agg;
            ((LongWritable)this.partialResult[0]).set(myAgg.countParam1);
            ((LongWritable)this.partialResult[1]).set(myAgg.countParam2);
            return this.partialResult;
        }
        
        @GenericUDAFEvaluator.AggregationType(estimable = true)
        static class ConnectionFailureAgg extends GenericUDAFEvaluator.AbstractAggregationBuffer
        {
            long countParam1;
            long countParam2;
            
            public int estimate() {
                return 16;
            }
        }
    }
}
