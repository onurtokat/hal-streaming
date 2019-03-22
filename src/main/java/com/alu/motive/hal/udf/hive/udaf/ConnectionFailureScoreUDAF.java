// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.udf.hive.udaf;

import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import java.util.List;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import java.util.ArrayList;
import org.apache.hadoop.hive.serde2.io.DoubleWritable;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.DoubleObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.LongObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.IntObjectInspector;
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

@Description(name = "connection_failure_score_udaf", value = "_FUNC_(expr, expr, expr, expr) ")
public class ConnectionFailureScoreUDAF extends AbstractGenericUDAFResolver
{
    private static final Logger LOG;
    boolean useAggEvaluator;
    
    public ConnectionFailureScoreUDAF() {
        this.useAggEvaluator = true;
    }
    
    public ConnectionFailureScoreUDAFEvaluator getEvaluator(final TypeInfo[] parameters) throws SemanticException {
        return new ConnectionFailureScoreUDAFEvaluator();
    }
    
    public GenericUDAFEvaluator getEvaluator(final GenericUDAFParameterInfo paramInfo) throws SemanticException {
        if (paramInfo.isAllColumns()) {
            throw new SemanticException("The specified syntax for UDAF invocation is invalid. Wildcard not accepted");
        }
        final TypeInfo[] parameters = paramInfo.getParameters();
        if (parameters.length < 4 || parameters.length > 5) {
            throw new UDFArgumentLengthException("Number of arguments must be 4 or 5.");
        }
        for (int i = 0; i < parameters.length; ++i) {
            if (parameters[i].getCategory() != ObjectInspector.Category.PRIMITIVE) {
                throw new UDFArgumentTypeException(i, "Only primitive type arguments are accepted but " + parameters[i].getTypeName() + " is passed.");
            }
            switch (((PrimitiveTypeInfo)parameters[i]).getPrimitiveCategory()) {
                case VOID:
                case BYTE:
                case SHORT:
                case INT:
                case LONG:
                case FLOAT: {
                    break;
                }
                case STRING: {
                    throw new UDFArgumentTypeException(i, "Only numeric or boolean type arguments are accepted for this parameter but " + parameters[i].getTypeName() + " is passed.");
                }
                case BOOLEAN: {
                    if (parameters.length == 4) {
                        throw new UDFArgumentTypeException(i, "If Argument number is 4 (agg mode) no boolean arguments are accepted.");
                    }
                    if (i != 2 && i != 3) {
                        throw new UDFArgumentTypeException(i, "Only arguments 3 and 4 must be boolean but " + parameters[i].getTypeName() + " was passed.");
                    }
                    this.useAggEvaluator = false;
                    break;
                }
                case DOUBLE: {
                    if (i != 0 && i != 1) {
                        throw new UDFArgumentTypeException(i, "Only first and second arguments is a double but double was passed on argument " + i);
                    }
                    break;
                }
                default: {
                    throw new UDFArgumentTypeException(i, "Only numeric type arguments are accepted but " + parameters[i].getTypeName() + " is passed.");
                }
            }
        }
        if (this.useAggEvaluator) {
            return new ConnectionFailureScoreAggUDAFEvaluator();
        }
        return new ConnectionFailureScoreUDAFEvaluator();
    }
    
    static {
        LOG = LoggerFactory.getLogger(ConnectionFailureScoreUDAF.class);
    }
    
    public static class ConnectionFailureScoreUDAFEvaluator extends GenericUDAFEvaluator
    {
        private static final double DEFAULT_KQI_VALUE = 0.0;
        private static final double DEFAULT_WIFI_FAILURE_OFFSET = 0.0;
        protected PrimitiveObjectInspector[] inputOI;
        private Object[] partialResult;
        private StructObjectInspector soi;
        private StructField count1;
        private IntObjectInspector count1OI;
        private StructField sum1;
        private LongObjectInspector sum1OI;
        private StructField countNotNullAuthState;
        private IntObjectInspector countNotNullAuthStateOI;
        private StructField countNotNullActive;
        private IntObjectInspector countNotNullActiveOI;
        private StructField sumNbrEntries;
        private LongObjectInspector sumNbrEntriesOI;
        private StructField defaultKqi;
        private DoubleObjectInspector defaultKqiOI;
        private StructField wifiFailureOffsetField;
        private DoubleObjectInspector wifiFailureOffsetFieldOI;
        private DoubleWritable result;
        private Double defaultValue;
        
        public ObjectInspector init(final GenericUDAFEvaluator.Mode m, final ObjectInspector[] parameters) throws HiveException {
            super.init(m, parameters);
            if (m == GenericUDAFEvaluator.Mode.PARTIAL1 || m == GenericUDAFEvaluator.Mode.COMPLETE) {
                (this.inputOI = new PrimitiveObjectInspector[5])[0] = (PrimitiveObjectInspector)parameters[0];
                this.inputOI[1] = (PrimitiveObjectInspector)parameters[1];
                this.inputOI[2] = (PrimitiveObjectInspector)parameters[2];
                this.inputOI[3] = (PrimitiveObjectInspector)parameters[3];
                this.inputOI[4] = (PrimitiveObjectInspector)parameters[4];
            }
            else {
                this.soi = (StructObjectInspector)parameters[0];
                this.count1 = this.soi.getStructFieldRef("count1");
                this.count1OI = (IntObjectInspector)this.count1.getFieldObjectInspector();
                this.sum1 = this.soi.getStructFieldRef("sum1");
                this.sum1OI = (LongObjectInspector)this.sum1.getFieldObjectInspector();
                this.countNotNullAuthState = this.soi.getStructFieldRef("countNotNullAuthState");
                this.countNotNullAuthStateOI = (IntObjectInspector)this.countNotNullAuthState.getFieldObjectInspector();
                this.countNotNullActive = this.soi.getStructFieldRef("countNotNullActive");
                this.countNotNullActiveOI = (IntObjectInspector)this.countNotNullActive.getFieldObjectInspector();
                this.sumNbrEntries = this.soi.getStructFieldRef("sumNbrEntries");
                this.sumNbrEntriesOI = (LongObjectInspector)this.sumNbrEntries.getFieldObjectInspector();
                this.defaultKqi = this.soi.getStructFieldRef("defaultKqi");
                this.defaultKqiOI = (DoubleObjectInspector)this.defaultKqi.getFieldObjectInspector();
                this.wifiFailureOffsetField = this.soi.getStructFieldRef("wifiFailureOffset");
                this.wifiFailureOffsetFieldOI = (DoubleObjectInspector)this.wifiFailureOffsetField.getFieldObjectInspector();
            }
            if (m == GenericUDAFEvaluator.Mode.PARTIAL1 || m == GenericUDAFEvaluator.Mode.PARTIAL2) {
                final ArrayList<ObjectInspector> foi = new ArrayList<ObjectInspector>();
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.javaIntObjectInspector);
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.javaLongObjectInspector);
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.javaIntObjectInspector);
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.javaIntObjectInspector);
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.javaLongObjectInspector);
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.javaDoubleObjectInspector);
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.javaDoubleObjectInspector);
                final ArrayList<String> fname = new ArrayList<String>();
                fname.add("count1");
                fname.add("sum1");
                fname.add("countNotNullAuthState");
                fname.add("countNotNullActive");
                fname.add("sumNbrEntries");
                fname.add("wifiFailureOffset");
                fname.add("defaultKqi");
                (this.partialResult = new Object[7])[0] = new Integer(0);
                this.partialResult[1] = new Long(0L);
                this.partialResult[2] = new Integer(0);
                this.partialResult[3] = new Integer(0);
                this.partialResult[4] = new Long(0L);
                this.partialResult[5] = new Double(0.0);
                this.partialResult[6] = new Double(0.0);
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
            ((ConnectionFailureAgg)agg).count1 = new Integer(0);
            ((ConnectionFailureAgg)agg).sum1 = new Long(0L);
            ((ConnectionFailureAgg)agg).countNotNullAuthState = new Integer(0);
            ((ConnectionFailureAgg)agg).countNotNullActive = new Integer(0);
            ((ConnectionFailureAgg)agg).sumNbrEntries = new Long(0L);
            ((ConnectionFailureAgg)agg).wifiFailureOffset = 0.0;
            ((ConnectionFailureAgg)agg).defaultKqi = 0.0;
        }
        
        public void iterate(final GenericUDAFEvaluator.AggregationBuffer agg, final Object[] parameters) throws HiveException {
            assert parameters.length == 5;
            final ConnectionFailureAgg myagg = (ConnectionFailureAgg)agg;
            if (parameters == null) {
                return;
            }
            if (parameters[0] != null && myagg.defaultKqi == 0.0) {
                myagg.defaultKqi = PrimitiveObjectInspectorUtils.getDouble(parameters[0], this.inputOI[0]);
            }
            if (parameters[1] != null && myagg.wifiFailureOffset == 0.0) {
                myagg.wifiFailureOffset = PrimitiveObjectInspectorUtils.getDouble(parameters[1], this.inputOI[1]);
            }
            if (parameters[2] != null && parameters[3] != null) {
                final Boolean param1 = PrimitiveObjectInspectorUtils.getBoolean(parameters[2], this.inputOI[2]);
                final Boolean param2 = PrimitiveObjectInspectorUtils.getBoolean(parameters[3], this.inputOI[3]);
                if (param1 != null) {
                    final ConnectionFailureAgg connectionFailureAgg = myagg;
                    ++connectionFailureAgg.countNotNullAuthState;
                }
                if (param2 != null) {
                    final ConnectionFailureAgg connectionFailureAgg2 = myagg;
                    ++connectionFailureAgg2.countNotNullActive;
                }
                if (!param1 && param2) {
                    final ConnectionFailureAgg connectionFailureAgg3 = myagg;
                    ++connectionFailureAgg3.sum1;
                }
                final ConnectionFailureAgg connectionFailureAgg4 = myagg;
                ++connectionFailureAgg4.count1;
            }
            if (parameters[4] != null) {
                final Long param3 = PrimitiveObjectInspectorUtils.getLong(parameters[4], this.inputOI[4]);
                final ConnectionFailureAgg connectionFailureAgg5 = myagg;
                connectionFailureAgg5.sumNbrEntries += param3;
            }
        }
        
        public void merge(final GenericUDAFEvaluator.AggregationBuffer agg, final Object partial) throws HiveException {
            if (partial != null) {
                final ConnectionFailureAgg myAgg = (ConnectionFailureAgg)agg;
                final Object partialFieldCount1 = this.soi.getStructFieldData(partial, this.count1);
                final Integer partialCount1 = this.count1OI.get(partialFieldCount1);
                final ConnectionFailureAgg connectionFailureAgg = myAgg;
                connectionFailureAgg.count1 += partialCount1;
                Object partialFieldSum = this.soi.getStructFieldData(partial, this.sum1);
                Long sum = this.sum1OI.get(partialFieldSum);
                final ConnectionFailureAgg connectionFailureAgg2 = myAgg;
                connectionFailureAgg2.sum1 += sum;
                partialFieldSum = this.soi.getStructFieldData(partial, this.countNotNullAuthState);
                Integer sumBools = this.countNotNullAuthStateOI.get(partialFieldSum);
                final ConnectionFailureAgg connectionFailureAgg3 = myAgg;
                connectionFailureAgg3.countNotNullAuthState += sumBools;
                partialFieldSum = this.soi.getStructFieldData(partial, this.countNotNullActive);
                sumBools = this.countNotNullActiveOI.get(partialFieldSum);
                final ConnectionFailureAgg connectionFailureAgg4 = myAgg;
                connectionFailureAgg4.countNotNullActive += sumBools;
                partialFieldSum = this.soi.getStructFieldData(partial, this.sumNbrEntries);
                sum = this.sumNbrEntriesOI.get(partialFieldSum);
                final ConnectionFailureAgg connectionFailureAgg5 = myAgg;
                connectionFailureAgg5.sumNbrEntries += sum;
                if (myAgg.wifiFailureOffset == 0.0) {
                    myAgg.wifiFailureOffset = this.wifiFailureOffsetFieldOI.get(this.soi.getStructFieldData(partial, this.wifiFailureOffsetField));
                }
                myAgg.defaultKqi = this.defaultKqiOI.get(this.soi.getStructFieldData(partial, this.defaultKqi));
            }
        }
        
        public Object terminate(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final ConnectionFailureAgg myAgg = (ConnectionFailureAgg)agg;
            if (myAgg.countNotNullAuthState == 0 || myAgg.countNotNullActive == 0) {
                return null;
            }
            if (myAgg.sumNbrEntries == 0L) {
                this.getResult().set(myAgg.defaultKqi);
                return this.result;
            }
            final double rate = myAgg.wifiFailureOffset + myAgg.sum1 / myAgg.count1;
            final double least = (rate < 1.0) ? rate : 1.0;
            this.getResult().set(1.0 - least);
            return this.result;
        }
        
        public void setResult(final DoubleWritable result) {
            this.result = result;
        }
        
        public DoubleWritable getResult() {
            return this.result;
        }
        
        public Object terminatePartial(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final ConnectionFailureAgg myAgg = (ConnectionFailureAgg)agg;
            this.partialResult[0] = myAgg.count1;
            this.partialResult[1] = myAgg.sum1;
            this.partialResult[2] = myAgg.countNotNullAuthState;
            this.partialResult[3] = myAgg.countNotNullActive;
            this.partialResult[4] = myAgg.sumNbrEntries;
            this.partialResult[5] = myAgg.wifiFailureOffset;
            this.partialResult[6] = myAgg.defaultKqi;
            return this.partialResult;
        }
        
        @GenericUDAFEvaluator.AggregationType(estimable = true)
        static class ConnectionFailureAgg extends GenericUDAFEvaluator.AbstractAggregationBuffer
        {
            int count1;
            long sum1;
            int countNotNullAuthState;
            int countNotNullActive;
            long sumNbrEntries;
            double wifiFailureOffset;
            double defaultKqi;
            
            public int estimate() {
                return 224;
            }
        }
    }
    
    public static class ConnectionFailureScoreAggUDAFEvaluator extends GenericUDAFEvaluator
    {
        private static final double DEFAULT_WIFI_FAILURE_OFFSET = 0.0;
        protected PrimitiveObjectInspector[] inputOI;
        private Object[] partialResult;
        private StructObjectInspector soi;
        private StructField sum1;
        private StructField sum2;
        private StructField wifiFailureOffsetField;
        private StructField defaultKqi;
        private LongObjectInspector sum1OI;
        private LongObjectInspector sum2OI;
        private DoubleObjectInspector wifiFailureOffsetFieldOI;
        private DoubleObjectInspector defaultKqiOI;
        private DoubleWritable result;
        private Double defaultValue;
        
        public ObjectInspector init(final GenericUDAFEvaluator.Mode m, final ObjectInspector[] parameters) throws HiveException {
            super.init(m, parameters);
            if (m == GenericUDAFEvaluator.Mode.PARTIAL1 || m == GenericUDAFEvaluator.Mode.COMPLETE) {
                (this.inputOI = new PrimitiveObjectInspector[4])[0] = (PrimitiveObjectInspector)parameters[0];
                this.inputOI[1] = (PrimitiveObjectInspector)parameters[1];
                this.inputOI[2] = (PrimitiveObjectInspector)parameters[2];
                this.inputOI[3] = (PrimitiveObjectInspector)parameters[3];
            }
            else {
                this.soi = (StructObjectInspector)parameters[0];
                this.sum1 = this.soi.getStructFieldRef("sum1");
                this.sum2 = this.soi.getStructFieldRef("sum2");
                this.wifiFailureOffsetField = this.soi.getStructFieldRef("wifiFailureOffset");
                this.sum1OI = (LongObjectInspector)this.sum1.getFieldObjectInspector();
                this.sum2OI = (LongObjectInspector)this.sum2.getFieldObjectInspector();
                this.wifiFailureOffsetFieldOI = (DoubleObjectInspector)this.wifiFailureOffsetField.getFieldObjectInspector();
                this.defaultKqi = this.soi.getStructFieldRef("defaultKqi");
                this.defaultKqiOI = (DoubleObjectInspector)this.defaultKqi.getFieldObjectInspector();
            }
            if (m == GenericUDAFEvaluator.Mode.PARTIAL1 || m == GenericUDAFEvaluator.Mode.PARTIAL2) {
                final ArrayList<ObjectInspector> foi = new ArrayList<ObjectInspector>();
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.javaLongObjectInspector);
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.javaLongObjectInspector);
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.javaDoubleObjectInspector);
                foi.add((ObjectInspector)PrimitiveObjectInspectorFactory.javaDoubleObjectInspector);
                final ArrayList<String> fname = new ArrayList<String>();
                fname.add("sum1");
                fname.add("sum2");
                fname.add("wifiFailureOffset");
                fname.add("defaultKqi");
                (this.partialResult = new Object[4])[0] = new Long(0L);
                this.partialResult[1] = new Long(0L);
                this.partialResult[2] = new Double(0.0);
                return (ObjectInspector)ObjectInspectorFactory.getStandardStructObjectInspector((List)fname, (List)foi);
            }
            this.result = new DoubleWritable(0.0);
            return (ObjectInspector)PrimitiveObjectInspectorFactory.writableDoubleObjectInspector;
        }
        
        public GenericUDAFEvaluator.AggregationBuffer getNewAggregationBuffer() throws HiveException {
            final ConnectionFailureScoreAgg buffer = new ConnectionFailureScoreAgg();
            this.reset((GenericUDAFEvaluator.AggregationBuffer)buffer);
            return (GenericUDAFEvaluator.AggregationBuffer)buffer;
        }
        
        public void reset(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final ConnectionFailureScoreAgg connectionFailureScoreAgg = (ConnectionFailureScoreAgg)agg;
            connectionFailureScoreAgg.sum1 = new Long(0L);
            connectionFailureScoreAgg.sum2 = new Long(0L);
            connectionFailureScoreAgg.wifiFailureOffset = 0.0;
            connectionFailureScoreAgg.defaultKqi = new Double(0.0);
        }
        
        public void iterate(final GenericUDAFEvaluator.AggregationBuffer agg, final Object[] parameters) throws HiveException {
            final ConnectionFailureScoreAgg myagg = (ConnectionFailureScoreAgg)agg;
            if (parameters == null) {
                return;
            }
            if (parameters[0] != null && this.defaultValue == null) {
                final Double param1 = PrimitiveObjectInspectorUtils.getDouble(parameters[0], this.inputOI[0]);
                if (param1 != null) {
                    myagg.defaultKqi = param1;
                }
            }
            if (parameters[1] != null && myagg.wifiFailureOffset == 0.0) {
                myagg.wifiFailureOffset = PrimitiveObjectInspectorUtils.getDouble(parameters[1], this.inputOI[1]);
            }
            final ConnectionFailureScoreAgg connectionFailureScoreAgg = (ConnectionFailureScoreAgg)agg;
            if (parameters[2] != null) {
                final Long param2 = PrimitiveObjectInspectorUtils.getLong(parameters[2], this.inputOI[2]);
                if (param2 != 0L) {
                    final ConnectionFailureScoreAgg connectionFailureScoreAgg2 = connectionFailureScoreAgg;
                    connectionFailureScoreAgg2.sum1 += param2;
                }
            }
            if (parameters[3] != null) {
                final Long param3 = PrimitiveObjectInspectorUtils.getLong(parameters[3], this.inputOI[3]);
                if (param3 != null) {
                    final ConnectionFailureScoreAgg connectionFailureScoreAgg3 = connectionFailureScoreAgg;
                    connectionFailureScoreAgg3.sum2 += param3;
                }
            }
        }
        
        public void merge(final GenericUDAFEvaluator.AggregationBuffer agg, final Object partial) throws HiveException {
            if (partial != null) {
                final ConnectionFailureScoreAgg myAgg = (ConnectionFailureScoreAgg)agg;
                final Object defaultKqiFieldData = this.soi.getStructFieldData(partial, this.defaultKqi);
                myAgg.defaultKqi = this.defaultKqiOI.get(defaultKqiFieldData);
                Object partialFieldSum = this.soi.getStructFieldData(partial, this.sum1);
                Long sum = this.sum1OI.get(partialFieldSum);
                final ConnectionFailureScoreAgg connectionFailureScoreAgg = myAgg;
                connectionFailureScoreAgg.sum1 += sum;
                partialFieldSum = this.soi.getStructFieldData(partial, this.sum2);
                sum = this.sum2OI.get(partialFieldSum);
                final ConnectionFailureScoreAgg connectionFailureScoreAgg2 = myAgg;
                connectionFailureScoreAgg2.sum2 += sum;
                if (myAgg.wifiFailureOffset == 0.0) {
                    myAgg.wifiFailureOffset = this.wifiFailureOffsetFieldOI.get(this.soi.getStructFieldData(partial, this.wifiFailureOffsetField));
                }
            }
        }
        
        public Object terminate(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final ConnectionFailureScoreAgg myAgg = (ConnectionFailureScoreAgg)agg;
            double rate = 0.0;
            if (myAgg.sum2 == 0L) {
                this.result.set(myAgg.defaultKqi);
                return this.result;
            }
            rate = myAgg.wifiFailureOffset + myAgg.sum1 / myAgg.sum2;
            final double least = (rate < 1.0) ? rate : 1.0;
            this.getResult().set(1.0 - least);
            return this.result;
        }
        
        public void setResult(final DoubleWritable result) {
            this.result = result;
        }
        
        public DoubleWritable getResult() {
            return this.result;
        }
        
        public Object terminatePartial(final GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException {
            final ConnectionFailureScoreAgg myAgg = (ConnectionFailureScoreAgg)agg;
            this.partialResult[0] = myAgg.sum1;
            this.partialResult[1] = myAgg.sum2;
            this.partialResult[2] = myAgg.wifiFailureOffset;
            this.partialResult[3] = myAgg.defaultKqi;
            return this.partialResult;
        }
        
        @GenericUDAFEvaluator.AggregationType(estimable = true)
        static class ConnectionFailureScoreAgg extends GenericUDAFEvaluator.AbstractAggregationBuffer
        {
            long sum1;
            long sum2;
            double wifiFailureOffset;
            double defaultKqi;
            
            public int estimate() {
                return 96;
            }
        }
    }
}
