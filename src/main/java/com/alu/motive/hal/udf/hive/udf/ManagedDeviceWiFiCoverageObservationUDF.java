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

@Description(name = "coverage_observation_mgd_dvc_and_home_udf")
public class ManagedDeviceWiFiCoverageObservationUDF extends GenericUDF
{
    private static final String OPNAME = "coverage_observation_mgd_dvc_and_home_udf";
    private DoubleObjectInspector coverageScoreObjectInspector;
    private StringObjectInspector thresholdsObjectInspector;
    private StringObjectInspector observationValuesObjectInspector;
    
    public ObjectInspector initialize(final ObjectInspector[] arguments) throws UDFArgumentException {
        if (arguments.length != 3) {
            throw new UDFArgumentLengthException("coverage_observation_mgd_dvc_and_home_udf requires 3 arguments.");
        }
        this.checkPrimitveArgument(arguments[0], PrimitiveObjectInspector.PrimitiveCategory.DOUBLE, 0);
        this.checkPrimitveArgument(arguments[1], PrimitiveObjectInspector.PrimitiveCategory.STRING, 1);
        this.checkPrimitveArgument(arguments[2], PrimitiveObjectInspector.PrimitiveCategory.STRING, 2);
        this.coverageScoreObjectInspector = (DoubleObjectInspector)arguments[0];
        this.thresholdsObjectInspector = (StringObjectInspector)arguments[1];
        this.observationValuesObjectInspector = (StringObjectInspector)arguments[2];
        return (ObjectInspector)PrimitiveObjectInspectorFactory.writableStringObjectInspector;
    }
    
    private void checkPrimitveArgument(final ObjectInspector objectInspector, final PrimitiveObjectInspector.PrimitiveCategory expectedCategory, final int argumentNumber) throws UDFArgumentTypeException {
        if (!objectInspector.getCategory().equals((Object)ObjectInspector.Category.PRIMITIVE) || !((PrimitiveObjectInspector)objectInspector).getPrimitiveCategory().equals((Object)expectedCategory)) {
            throw new UDFArgumentTypeException(argumentNumber, "Argument number " + (argumentNumber + 1) + " must be a " + expectedCategory);
        }
    }
    
    public Object evaluate(final GenericUDF.DeferredObject[] arguments) throws HiveException {
        final String[] observationValues = this.observationValuesObjectInspector.getPrimitiveJavaObject(arguments[2].get()).split(",");
        final String errorObservationValue = observationValues[observationValues.length - 1];
        final String[] observationValuesToPass = Arrays.copyOfRange(observationValues, 0, observationValues.length - 1);
        if (null == arguments[0].get()) {
            return new Text(errorObservationValue);
        }
        final double coverageScore = this.coverageScoreObjectInspector.get(arguments[0].get());
        final String[] thresholdsNames = this.thresholdsObjectInspector.getPrimitiveJavaObject(arguments[1].get()).split(",");
        final double[] thresholds = new double[thresholdsNames.length];
        for (int i = 0; i < thresholdsNames.length; ++i) {
            thresholds[i] = Double.parseDouble(thresholdsNames[i]);
        }
        final ObservationCalculator observation = new ObservationCalculator(coverageScore, thresholds, observationValuesToPass);
        return new Text(observation.calculateValue());
    }
    
    public String getDisplayString(final String[] children) {
        final StringBuffer sb = new StringBuffer();
        sb.append("returns coverage_observation_mgd_dvc_and_home_udf(\"");
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
