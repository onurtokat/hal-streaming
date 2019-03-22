// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.udf.hive.udf;

import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.io.Text;
import java.util.Arrays;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.StringObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.DoubleObjectInspector;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;

@Description(name = "coverage_observation_assoc_dvc_udf")
public class AssociatedDeviceWiFiCoverageScoreObservationUDF extends GenericUDF
{
    private static final String OPNAME = "coverage_observation_assoc_dvc_udf";
    private DoubleObjectInspector coverageScoreObjectInspector;
    private StringObjectInspector operatingBandObjectInspector;
    private StringObjectInspector thresholds24ObjectInspector;
    private StringObjectInspector thresholds5ObjectInspector;
    private StringObjectInspector observationValuesObjectInspector;
    
    public ObjectInspector initialize(final ObjectInspector[] arguments) throws UDFArgumentException {
        if (arguments.length != 5) {
            throw new UDFArgumentLengthException("coverage_observation_assoc_dvc_udf requires 5 arguments.");
        }
        this.checkPrimitveArgument(arguments[0], PrimitiveObjectInspector.PrimitiveCategory.DOUBLE, 0);
        this.checkPrimitveArgument(arguments[1], PrimitiveObjectInspector.PrimitiveCategory.STRING, 1);
        this.checkPrimitveArgument(arguments[2], PrimitiveObjectInspector.PrimitiveCategory.STRING, 2);
        this.checkPrimitveArgument(arguments[3], PrimitiveObjectInspector.PrimitiveCategory.STRING, 3);
        this.checkPrimitveArgument(arguments[4], PrimitiveObjectInspector.PrimitiveCategory.STRING, 4);
        this.coverageScoreObjectInspector = (DoubleObjectInspector)arguments[0];
        this.operatingBandObjectInspector = (StringObjectInspector)arguments[1];
        this.thresholds24ObjectInspector = (StringObjectInspector)arguments[2];
        this.thresholds5ObjectInspector = (StringObjectInspector)arguments[3];
        this.observationValuesObjectInspector = (StringObjectInspector)arguments[4];
        return (ObjectInspector)PrimitiveObjectInspectorFactory.writableStringObjectInspector;
    }
    
    private void checkPrimitveArgument(final ObjectInspector objectInspector, final PrimitiveObjectInspector.PrimitiveCategory expectedCategory, final int argumentNumber) throws UDFArgumentTypeException {
        if (!objectInspector.getCategory().equals((Object)ObjectInspector.Category.PRIMITIVE) || !((PrimitiveObjectInspector)objectInspector).getPrimitiveCategory().equals((Object)expectedCategory)) {
            throw new UDFArgumentTypeException(argumentNumber, "Argument number " + (argumentNumber + 1) + " must be a " + expectedCategory);
        }
    }
    
    public Object evaluate(final GenericUDF.DeferredObject[] arguments) throws HiveException {
        final String[] observationValues = this.observationValuesObjectInspector.getPrimitiveJavaObject(arguments[4].get()).split(",");
        final String errorObservationValue = observationValues[observationValues.length - 1];
        final String[] observationValuesToPass = Arrays.copyOfRange(observationValues, 0, observationValues.length - 1);
        final String operatingBand = this.operatingBandObjectInspector.getPrimitiveJavaObject(arguments[1].get());
        if (null == arguments[0].get()) {
            return new Text(errorObservationValue);
        }
        final double coverageScore = this.coverageScoreObjectInspector.get(arguments[0].get());
        String[] thresholdsNames = null;
        if ("2.4GHz".equals(operatingBand)) {
            thresholdsNames = this.thresholds24ObjectInspector.getPrimitiveJavaObject(arguments[2].get()).split(",");
        }
        else {
            if (!"5GHz".equals(operatingBand)) {
                return new Text(errorObservationValue);
            }
            thresholdsNames = this.thresholds5ObjectInspector.getPrimitiveJavaObject(arguments[3].get()).split(",");
        }
        final double[] thresholds = new double[thresholdsNames.length];
        for (int i = 0; i < thresholdsNames.length; ++i) {
            thresholds[i] = Double.parseDouble(thresholdsNames[i]);
        }
        final ObservationCalculator observation = new ObservationCalculator(coverageScore, thresholds, observationValuesToPass);
        return new Text(observation.calculateValue());
    }
    
    public String getDisplayString(final String[] children) {
        final StringBuffer sb = new StringBuffer();
        sb.append("returns coverage_observation_assoc_dvc_udf(\"");
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
