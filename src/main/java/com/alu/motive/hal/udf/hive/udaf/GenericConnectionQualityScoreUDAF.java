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

@Description(name = "connection_quality_score_udaf", value = "_FUNC_(x1,x2,x3,x4,x5,x6) - x1 is default value when sums are zero, x2 is the offset for the score, pair x5 and x6 is not mandatory and are only used when x3 and x4 are null")
public class GenericConnectionQualityScoreUDAF extends AbstractGenericUDAFResolver
{
    private static final Logger LOG;
    
    public GenericConnectionQualityScoreUDAFEvaluator getEvaluator(final TypeInfo[] parameters) throws SemanticException {
        return new GenericConnectionQualityScoreUDAFEvaluator();
    }
    
    public GenericUDAFEvaluator getEvaluator(final GenericUDAFParameterInfo paramInfo) throws SemanticException {
        if (paramInfo.isAllColumns()) {
            throw new SemanticException("The specified syntax for UDAF invocation is invalid. Wildcard not accepted");
        }
        final TypeInfo[] parameters = paramInfo.getParameters();
        if (parameters.length != 4 && parameters.length != 6) {
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
        return new GenericConnectionQualityScoreUDAFEvaluator();
    }
    
    static {
        LOG = LoggerFactory.getLogger(GenericConnectionQualityScoreUDAF.class);
    }
    
    public static class GenericConnectionQualityScoreUDAFEvaluator extends GenericUDAFEvaluator
    {
        private static final double DEFAULT_WIFI_QUALITY_OFFSET = 0.0;
        private PrimitiveObjectInspector[] inputOI;
        private StructObjectInspector soi;
        private StructField sumRetransmissionField;
        private StructField countRetransmissionField;
        private StructField sumPacketsSentField;
        private StructField countPacketsSentField;
        private StructField sumRetransmissionAssocDeviceField;
        private StructField countRetransmissionAssocDeviceField;
        private StructField sumPacketsSentAssocDeviceField;
        private StructField countPacketsSentAssocDeviceField;
        private StructField wifiQualityOffsetField;
        private DoubleObjectInspector sumRetransmissionFieldOI;
        private IntObjectInspector countRetransmissionFieldOI;
        private DoubleObjectInspector sumPacketsSentFieldOI;
        private IntObjectInspector countPacketsSentFieldOI;
        private DoubleObjectInspector sumRetransmissionAssocDeviceFieldOI;
        private IntObjectInspector countRetransmissionAssocDeviceFieldOI;
        private DoubleObjectInspector sumPacketsSentAssocDeviceFieldOI;
        private IntObjectInspector countPacketsSentAssocDeviceFieldOI;
        private DoubleObjectInspector wifiQualityOffsetFieldOI;
        private Object[] partialResult;
        private DoubleWritable result;
        private Double defaultValue;
        private int nbrParameters;
        
        public GenericConnectionQualityScoreUDAFEvaluator() {
            this.defaultValue = null;
            this.nbrParameters = 0;
        }
        
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
                this.countRetransmissionField = this.soi.getStructFieldRef("countRetransmission");
                this.sumPacketsSentField = this.soi.getStructFieldRef("sumPacketsSent");
                this.countPacketsSentField = this.soi.getStructFieldRef("countPacketsSent");
                this.sumRetransmissionAssocDeviceField = this.soi.getStructFieldRef("sumRetransmissionAssocDevice");
                this.countRetransmissionAssocDeviceField = this.soi.getStructFieldRef("countRetransmissionAssocDevice");
                this.sumPacketsSentAssocDeviceField = this.soi.getStructFieldRef("sumPacketsSentAssocDevice");
                this.countPacketsSentAssocDeviceField = this.soi.getStructFieldRef("countPacketsSentAssocDevice");
                this.wifiQualityOffsetField = this.soi.getStructFieldRef("wifiQualityOffset");
                this.sumRetransmissionFieldOI = (DoubleObjectInspector)this.sumRetransmissionField.getFieldObjectInspector();
                this.countRetransmissionFieldOI = (IntObjectInspector)this.countRetransmissionField.getFieldObjectInspector();
                this.sumPacketsSentFieldOI = (DoubleObjectInspector)this.sumPacketsSentField.getFieldObjectInspector();
                this.countPacketsSentFieldOI = (IntObjectInspector)this.countPacketsSentField.getFieldObjectInspector();
                this.sumRetransmissionAssocDeviceFieldOI = (DoubleObjectInspector)this.sumRetransmissionAssocDeviceField.getFieldObjectInspector();
                this.countRetransmissionAssocDeviceFieldOI = (IntObjectInspector)this.countRetransmissionAssocDeviceField.getFieldObjectInspector();
                this.sumPacketsSentAssocDeviceFieldOI = (DoubleObjectInspector)this.sumPacketsSentAssocDeviceField.getFieldObjectInspector();
                this.countPacketsSentAssocDeviceFieldOI = (IntObjectInspector)this.countPacketsSentAssocDeviceField.getFieldObjectInspector();
                this.wifiQualityOffsetFieldOI = (DoubleObjectInspector)this.wifiQualityOffsetField.getFieldObjectInspector();
            }
            if (mode == GenericUDAFEvaluator.Mode.PARTIAL1 || mode == GenericUDAFEvaluator.Mode.PARTIAL2) {
                final ArrayList<ObjectInspector> foi = new ArrayList<ObjectInspector>();
                this.partialResult = new Object[9];
                for (int j = 0; j < 4; ++j) {
                    foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.writableDoubleObjectInspector);
                    foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.writableIntObjectInspector);
                    this.partialResult[j * 2] = new DoubleWritable(0.0);
                    this.partialResult[j * 2 + 1] = new IntWritable(0);
                }
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.writableDoubleObjectInspector);
                this.partialResult[8] = new DoubleWritable(0.0);
                final ArrayList<String> fname = new ArrayList<String>();
                fname.add("sumRetransmission");
                fname.add("countRetransmission");
                fname.add("sumPacketsSent");
                fname.add("countPacketsSent");
                fname.add("sumRetransmissionAssocDevice");
                fname.add("countRetransmissionAssocDevice");
                fname.add("sumPacketsSentAssocDevice");
                fname.add("countPacketsSentAssocDevice");
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
            myagg.countRetransmissions = 0;
            myagg.sumPacketsSent = 0.0;
            myagg.countPacketsSent = 0;
            myagg.sumRetransmissionsAssocDevice = 0.0;
            myagg.countRetransmissionsAssocDevice = 0;
            myagg.sumPacketsSentAssocDevice = 0.0;
            myagg.countPacketsSentAssocDevice = 0;
            myagg.wifiQualityOffset = 0.0;
        }
        
        public void iterate(final GenericUDAFEvaluator.AggregationBuffer agg, final Object[] parameters) throws HiveException {
            this.nbrParameters = parameters.length;
            final ConnectionQualityAgg myagg = (ConnectionQualityAgg)agg;
            if (parameters[0] != null && this.defaultValue == null) {
                this.defaultValue = PrimitiveObjectInspectorUtils.getDouble(parameters[0], this.inputOI[0]);
            }
            if (parameters[1] != null && myagg.wifiQualityOffset == 0.0) {
                myagg.wifiQualityOffset = PrimitiveObjectInspectorUtils.getDouble(parameters[1], this.inputOI[1]);
            }
            if (parameters[2] != null) {
                final ConnectionQualityAgg connectionQualityAgg = myagg;
                ++connectionQualityAgg.countRetransmissions;
                final ConnectionQualityAgg connectionQualityAgg2 = myagg;
                connectionQualityAgg2.sumRetransmissions += PrimitiveObjectInspectorUtils.getDouble(parameters[2], this.inputOI[2]);
            }
            if (parameters[3] != null) {
                final ConnectionQualityAgg connectionQualityAgg3 = myagg;
                ++connectionQualityAgg3.countPacketsSent;
                final ConnectionQualityAgg connectionQualityAgg4 = myagg;
                connectionQualityAgg4.sumPacketsSent += PrimitiveObjectInspectorUtils.getDouble(parameters[3], this.inputOI[3]);
            }
            if (this.nbrParameters == 6) {
                if (parameters[4] != null) {
                    final ConnectionQualityAgg connectionQualityAgg5 = myagg;
                    ++connectionQualityAgg5.countRetransmissionsAssocDevice;
                    final ConnectionQualityAgg connectionQualityAgg6 = myagg;
                    connectionQualityAgg6.sumRetransmissionsAssocDevice += PrimitiveObjectInspectorUtils.getDouble(parameters[4], this.inputOI[4]);
                }
                if (parameters[5] != null) {
                    final ConnectionQualityAgg connectionQualityAgg7 = myagg;
                    ++connectionQualityAgg7.countPacketsSentAssocDevice;
                    final ConnectionQualityAgg connectionQualityAgg8 = myagg;
                    connectionQualityAgg8.sumPacketsSentAssocDevice += PrimitiveObjectInspectorUtils.getDouble(parameters[5], this.inputOI[5]);
                }
            }
        }
        
        public Object terminatePartial(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final ConnectionQualityAgg myagg = (ConnectionQualityAgg)agg;
            ((DoubleWritable)this.partialResult[0]).set(myagg.sumRetransmissions);
            ((IntWritable)this.partialResult[1]).set(myagg.countRetransmissions);
            ((DoubleWritable)this.partialResult[2]).set(myagg.sumPacketsSent);
            ((IntWritable)this.partialResult[3]).set(myagg.countPacketsSent);
            ((DoubleWritable)this.partialResult[4]).set(myagg.sumRetransmissionsAssocDevice);
            ((IntWritable)this.partialResult[5]).set(myagg.countRetransmissionsAssocDevice);
            ((DoubleWritable)this.partialResult[6]).set(myagg.sumPacketsSentAssocDevice);
            ((IntWritable)this.partialResult[7]).set(myagg.countPacketsSentAssocDevice);
            ((DoubleWritable)this.partialResult[8]).set(myagg.wifiQualityOffset);
            return this.partialResult;
        }
        
        public void merge(final GenericUDAFEvaluator.AggregationBuffer agg, final Object partial) throws HiveException {
            if (partial != null) {
                final ConnectionQualityAgg connectionQualityAgg;
                final ConnectionQualityAgg myagg = connectionQualityAgg = (ConnectionQualityAgg)agg;
                connectionQualityAgg.sumRetransmissions += this.sumRetransmissionFieldOI.get(this.soi.getStructFieldData(partial, this.sumRetransmissionField));
                final ConnectionQualityAgg connectionQualityAgg2 = myagg;
                connectionQualityAgg2.countRetransmissions += this.countRetransmissionFieldOI.get(this.soi.getStructFieldData(partial, this.countRetransmissionField));
                final ConnectionQualityAgg connectionQualityAgg3 = myagg;
                connectionQualityAgg3.sumPacketsSent += this.sumPacketsSentFieldOI.get(this.soi.getStructFieldData(partial, this.sumPacketsSentField));
                final ConnectionQualityAgg connectionQualityAgg4 = myagg;
                connectionQualityAgg4.countPacketsSent += this.countPacketsSentFieldOI.get(this.soi.getStructFieldData(partial, this.countPacketsSentField));
                final ConnectionQualityAgg connectionQualityAgg5 = myagg;
                connectionQualityAgg5.sumRetransmissionsAssocDevice += this.sumRetransmissionAssocDeviceFieldOI.get(this.soi.getStructFieldData(partial, this.sumRetransmissionAssocDeviceField));
                final ConnectionQualityAgg connectionQualityAgg6 = myagg;
                connectionQualityAgg6.countRetransmissionsAssocDevice += this.countRetransmissionAssocDeviceFieldOI.get(this.soi.getStructFieldData(partial, this.countRetransmissionAssocDeviceField));
                final ConnectionQualityAgg connectionQualityAgg7 = myagg;
                connectionQualityAgg7.sumPacketsSentAssocDevice += this.sumPacketsSentAssocDeviceFieldOI.get(this.soi.getStructFieldData(partial, this.sumPacketsSentAssocDeviceField));
                final ConnectionQualityAgg connectionQualityAgg8 = myagg;
                connectionQualityAgg8.countPacketsSentAssocDevice += this.countPacketsSentAssocDeviceFieldOI.get(this.soi.getStructFieldData(partial, this.countPacketsSentAssocDeviceField));
                if (myagg.wifiQualityOffset == 0.0) {
                    myagg.wifiQualityOffset = this.wifiQualityOffsetFieldOI.get(this.soi.getStructFieldData(partial, this.wifiQualityOffsetField));
                }
            }
        }
        
        public Object terminate(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final ConnectionQualityAgg myAgg = (ConnectionQualityAgg)agg;
            double rate = 0.0;
            if (myAgg.countPacketsSent > 0 && myAgg.countRetransmissions > 0) {
                if (myAgg.sumPacketsSent + myAgg.sumRetransmissions == 0.0) {
                    if (this.defaultValue != null) {
                        this.getResult().set((double)this.defaultValue);
                    }
                    return this.getResult();
                }
                rate = myAgg.wifiQualityOffset + myAgg.sumRetransmissions / (myAgg.sumPacketsSent + myAgg.sumRetransmissions);
            }
            else {
                if (myAgg.countPacketsSentAssocDevice <= 0 || myAgg.countRetransmissionsAssocDevice <= 0) {
                    return null;
                }
                if (myAgg.sumPacketsSentAssocDevice + myAgg.sumRetransmissionsAssocDevice == 0.0) {
                    if (this.defaultValue != null) {
                        this.getResult().set((double)this.defaultValue);
                    }
                    return this.getResult();
                }
                rate = myAgg.wifiQualityOffset + myAgg.sumRetransmissionsAssocDevice / (myAgg.sumPacketsSentAssocDevice + myAgg.sumRetransmissionsAssocDevice);
            }
            final double least = (rate < 1.0) ? rate : 1.0;
            this.getResult().set(1.0 - least);
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
            double sumRetransmissions;
            int countRetransmissions;
            double sumPacketsSent;
            int countPacketsSent;
            double sumRetransmissionsAssocDevice;
            int countRetransmissionsAssocDevice;
            double sumPacketsSentAssocDevice;
            int countPacketsSentAssocDevice;
            double wifiQualityOffset;
            
            public int estimate() {
                return 72;
            }
        }
    }
}
