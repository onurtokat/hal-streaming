// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.udf.hive.udaf;

import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import java.util.List;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import java.util.ArrayList;
import org.apache.hadoop.hive.serde2.io.DoubleWritable;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.IntObjectInspector;
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

@Description(name = "connection_quality_score_retrans_perc_udaf", value = "_FUNC_(x1) - pair x3 and x4 is not mandatory")
public class ConnectionQualityScoreWithRetransPercUDAF extends AbstractGenericUDAFResolver
{
    private static final Logger LOG;
    
    public ConnectionQualityScoreWithRetransPercUDAFEvaluator getEvaluator(final TypeInfo[] parameters) throws SemanticException {
        return new ConnectionQualityScoreWithRetransPercUDAFEvaluator();
    }
    
    public GenericUDAFEvaluator getEvaluator(final GenericUDAFParameterInfo paramInfo) throws SemanticException {
        if (paramInfo.isAllColumns()) {
            throw new SemanticException("The specified syntax for UDAF invocation is invalid. Wildcard not accepted");
        }
        final TypeInfo[] parameters = paramInfo.getParameters();
        if (parameters.length != 2) {
            throw new UDFArgumentLengthException("Wrong arguments number encontered.");
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
                    throw new UDFArgumentTypeException(i, "Only numeric type arguments are accepted but " + parameters[i].getTypeName() + " is passed.");
                }
            }
        }
        return new ConnectionQualityScoreWithRetransPercUDAFEvaluator();
    }
    
    static {
        LOG = LoggerFactory.getLogger(ConnectionQualityScoreWithRetransPercUDAF.class);
    }
    
    public static class ConnectionQualityScoreWithRetransPercUDAFEvaluator extends GenericUDAFEvaluator
    {
        private static final double DEFAULT_WIFI_QUALITY_OFFSET = 0.0;
        private PrimitiveObjectInspector[] inputOI;
        private StructObjectInspector soi;
        private StructField sumRetransmissionField;
        private StructField countSamplesField;
        private StructField wifiQualityOffsetField;
        private DoubleObjectInspector sumRetransmissionFieldOI;
        private IntObjectInspector countSamplesFieldOI;
        private DoubleObjectInspector wifiQualityOffsetFieldOI;
        private Object[] partialResult;
        private DoubleWritable result;
        
        public ObjectInspector init(final GenericUDAFEvaluator.Mode mode, final ObjectInspector[] parameters) throws HiveException {
            super.init(mode, parameters);
            if (mode == GenericUDAFEvaluator.Mode.PARTIAL1 || mode == GenericUDAFEvaluator.Mode.COMPLETE) {
                this.inputOI = new PrimitiveObjectInspector[parameters.length];
                int i = 0;
                for (final ObjectInspector param : parameters) {
                    this.inputOI[i++] = (PrimitiveObjectInspector)param;
                }
            }
            else {
                this.soi = (StructObjectInspector)parameters[0];
                this.sumRetransmissionField = this.soi.getStructFieldRef("sumRetransmission");
                this.countSamplesField = this.soi.getStructFieldRef("countSamples");
                this.wifiQualityOffsetField = this.soi.getStructFieldRef("wifiQualityOffset");
                this.sumRetransmissionFieldOI = (DoubleObjectInspector)this.sumRetransmissionField.getFieldObjectInspector();
                this.countSamplesFieldOI = (IntObjectInspector)this.countSamplesField.getFieldObjectInspector();
                this.wifiQualityOffsetFieldOI = (DoubleObjectInspector)this.wifiQualityOffsetField.getFieldObjectInspector();
            }
            if (mode == GenericUDAFEvaluator.Mode.PARTIAL1 || mode == GenericUDAFEvaluator.Mode.PARTIAL2) {
                final ArrayList<ObjectInspector> foi = new ArrayList<ObjectInspector>();
                this.partialResult = new Object[3];
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.writableDoubleObjectInspector);
                this.partialResult[0] = new DoubleWritable(0.0);
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.writableIntObjectInspector);
                this.partialResult[1] = new IntWritable(0);
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.writableDoubleObjectInspector);
                this.partialResult[2] = new DoubleWritable(0.0);
                final ArrayList<String> fname = new ArrayList<String>();
                fname.add("sumRetransmission");
                fname.add("countSamples");
                fname.add("wifiQualityOffset");
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
            myagg.sumRetransmissions = 0.0;
            myagg.countSamples = 0;
            myagg.wifiQualityOffset = 0.0;
        }
        
        public void iterate(final GenericUDAFEvaluator.AggregationBuffer agg, final Object[] parameters) throws HiveException {
            final ConnectionQualityAgg myagg = (ConnectionQualityAgg)agg;
            if (parameters[0] != null) {
                myagg.wifiQualityOffset = PrimitiveObjectInspectorUtils.getDouble(parameters[0], this.inputOI[0]);
            }
            if (parameters[1] != null) {
                final ConnectionQualityAgg connectionQualityAgg = myagg;
                connectionQualityAgg.sumRetransmissions += PrimitiveObjectInspectorUtils.getDouble(parameters[1], this.inputOI[1]) / 100.0;
                final ConnectionQualityAgg connectionQualityAgg2 = myagg;
                ++connectionQualityAgg2.countSamples;
            }
        }
        
        public Object terminatePartial(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final ConnectionQualityAgg myagg = (ConnectionQualityAgg)agg;
            ((DoubleWritable)this.partialResult[0]).set(myagg.sumRetransmissions);
            ((IntWritable)this.partialResult[1]).set(myagg.countSamples);
            ((DoubleWritable)this.partialResult[2]).set(myagg.wifiQualityOffset);
            return this.partialResult;
        }
        
        public void merge(final GenericUDAFEvaluator.AggregationBuffer agg, final Object partial) throws HiveException {
            if (partial != null) {
                final ConnectionQualityAgg connectionQualityAgg;
                final ConnectionQualityAgg myagg = connectionQualityAgg = (ConnectionQualityAgg)agg;
                connectionQualityAgg.sumRetransmissions += this.sumRetransmissionFieldOI.get(this.soi.getStructFieldData(partial, this.sumRetransmissionField));
                final ConnectionQualityAgg connectionQualityAgg2 = myagg;
                connectionQualityAgg2.countSamples += this.countSamplesFieldOI.get(this.soi.getStructFieldData(partial, this.countSamplesField));
                if (myagg.wifiQualityOffset == 0.0) {
                    myagg.wifiQualityOffset = this.wifiQualityOffsetFieldOI.get(this.soi.getStructFieldData(partial, this.wifiQualityOffsetField));
                }
            }
        }
        
        public Object terminate(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final ConnectionQualityAgg myAgg = (ConnectionQualityAgg)agg;
            double rate = 0.0;
            if (myAgg.countSamples > 0) {
                rate = myAgg.wifiQualityOffset + myAgg.sumRetransmissions / myAgg.countSamples;
                final double least = (rate < 1.0) ? rate : 1.0;
                this.getResult().set(1.0 - least);
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
        static class ConnectionQualityAgg extends GenericUDAFEvaluator.AbstractAggregationBuffer
        {
            double sumRetransmissions;
            int countSamples;
            double wifiQualityOffset;
            
            public int estimate() {
                return 24;
            }
        }
    }
}
