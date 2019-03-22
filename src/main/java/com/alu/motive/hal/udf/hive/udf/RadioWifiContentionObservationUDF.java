// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.udf.hive.udf;

import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.IntObjectInspector;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.StringObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.DoubleObjectInspector;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;

@Description(name = "contention_observation_radio_udf")
public class RadioWifiContentionObservationUDF extends GenericUDF
{
    private static final String OPNAME = "contention_observation_radio_udf";
    private DoubleObjectInspector scoreObjectInspector;
    private ObjectInspector scoreOffsetObjectInspector;
    private StringObjectInspector thresholdsObjectInspector;
    private StringObjectInspector observationValuesObjectInspector;
    
    public ObjectInspector initialize(final ObjectInspector[] arguments) throws UDFArgumentException {
        if (arguments.length != 4) {
            throw new UDFArgumentLengthException("contention_observation_radio_udf requires 4 arguments.");
        }
        this.checkPrimitveArgument(arguments[0], PrimitiveObjectInspector.PrimitiveCategory.DOUBLE, 0);
        this.checkIntOrDoubleTypeArgument(arguments[1], 1);
        this.checkPrimitveArgument(arguments[2], PrimitiveObjectInspector.PrimitiveCategory.STRING, 2);
        this.checkPrimitveArgument(arguments[3], PrimitiveObjectInspector.PrimitiveCategory.STRING, 3);
        this.scoreObjectInspector = (DoubleObjectInspector)arguments[0];
        this.scoreOffsetObjectInspector = this.getCastedOffsetObjInspector(arguments[1]);
        this.thresholdsObjectInspector = (StringObjectInspector)arguments[2];
        this.observationValuesObjectInspector = (StringObjectInspector)arguments[3];
        return (ObjectInspector)PrimitiveObjectInspectorFactory.writableStringObjectInspector;
    }
    
    private void checkPrimitveArgument(final ObjectInspector objectInspector, final PrimitiveObjectInspector.PrimitiveCategory expectedCategory, final int argumentNumber) throws UDFArgumentTypeException {
        if (!objectInspector.getCategory().equals((Object)ObjectInspector.Category.PRIMITIVE) || !((PrimitiveObjectInspector)objectInspector).getPrimitiveCategory().equals((Object)expectedCategory)) {
            throw new UDFArgumentTypeException(argumentNumber, "Argument number " + (argumentNumber + 1) + " must be a " + expectedCategory);
        }
    }
    
    private void checkIntOrDoubleTypeArgument(final ObjectInspector objectInspector, final int paramIndex) throws UDFArgumentTypeException {
        if (objectInspector.getCategory() != ObjectInspector.Category.PRIMITIVE) {
            throw new UDFArgumentTypeException(paramIndex, "Only primitive type arguments are accepted but " + objectInspector.getTypeName() + " is passed.");
        }
        final PrimitiveObjectInspector.PrimitiveCategory type = ((PrimitiveObjectInspector)objectInspector).getPrimitiveCategory();
        if (PrimitiveObjectInspector.PrimitiveCategory.INT.equals((Object)type) || PrimitiveObjectInspector.PrimitiveCategory.DOUBLE.equals((Object)type)) {
            return;
        }
        throw new UDFArgumentTypeException(paramIndex, "Only int of double type arguments are accepted but #" + (paramIndex + 1) + " argument is " + objectInspector.getTypeName() + ".");
    }
    
    private ObjectInspector getCastedOffsetObjInspector(final ObjectInspector offsetObjInsp) {
        if (offsetObjInsp instanceof DoubleObjectInspector) {
            return offsetObjInsp;
        }
        return offsetObjInsp;
    }
    
    public Object evaluate(final GenericUDF.DeferredObject[] arguments) throws HiveException {
        final String[] observationValues = this.observationValuesObjectInspector.getPrimitiveJavaObject(arguments[3].get()).split(",");
        final String errorObservationValue = observationValues[2];
        if (null == arguments[0].get()) {
            return new Text(errorObservationValue);
        }
        final String[] thresholdsScores = this.thresholdsObjectInspector.getPrimitiveJavaObject(arguments[2].get()).split(",");
        final double[] thresholds = new double[thresholdsScores.length];
        for (int i = 0; i < thresholdsScores.length; ++i) {
            thresholds[i] = Double.parseDouble(thresholdsScores[i]);
        }
        final double score = this.scoreObjectInspector.get(arguments[0].get());
        final double offset = this.getOffsetFromIntOrDouble(arguments[1]);
        final double observationScore = getNormalizedScore(score, offset);
        if (thresholds[0] > 1.0 || thresholds[0] < 0.0) {
            throw new UDFArgumentException("Low Threshold is not between 0.0 and 1.0");
        }
        if (thresholds[1] > 1.0 || thresholds[1] < thresholds[0]) {
            throw new UDFArgumentException("High Threshold is not between Low Threshold and 1.0");
        }
        final ObservationCalculator observation = new ObservationCalculator(observationScore, thresholds, observationValues);
        return new Text(observation.calculateValue());
    }
    
    protected static double getNormalizedScore(final double score, final double offset) {
        return Math.max(Math.min(offset + score, 1.0), 0.0);
    }
    
    private Double getOffsetFromIntOrDouble(final GenericUDF.DeferredObject argument) throws HiveException {
        Double offset;
        if (this.scoreOffsetObjectInspector instanceof DoubleObjectInspector) {
            offset = ((DoubleObjectInspector)this.scoreOffsetObjectInspector).get(argument.get());
        }
        else {
            final Integer offsetAsInt = ((IntObjectInspector)this.scoreOffsetObjectInspector).get(argument.get());
            offset = (double)offsetAsInt;
        }
        return offset;
    }
    
    public String getDisplayString(final String[] children) {
        final StringBuffer sb = new StringBuffer();
        sb.append("returns contention_observation_radio_udf(\"");
        boolean isFirstElement = true;
        for (final String child : children) {
            if (isFirstElement) {
                isFirstElement = false;
            }
            else {
                sb.append("\",\"");
            }
            sb.append(child);
        }
        sb.append("\")");
        return sb.toString();
    }
}
