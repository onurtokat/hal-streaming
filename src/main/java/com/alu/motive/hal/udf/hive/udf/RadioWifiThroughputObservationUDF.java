// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.udf.hive.udf;

import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.StringObjectInspector;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;

@Description(name = "throughput_observation_radio_udf", value = "_FUNC_(interference_observation string, contention_observation) - Returns throughput observation.")
public class RadioWifiThroughputObservationUDF extends GenericUDF
{
    private static final String OPNAME = "throughput_observation_radio_udf";
    private StringObjectInspector interferenceObservationObjectInspector;
    private StringObjectInspector contentionObservationObjectInspector;
    private ThroughputObservationRadioLevelCalculator throughputCalculator;
    
    public RadioWifiThroughputObservationUDF() {
        this.throughputCalculator = new ThroughputObservationRadioLevelCalculator();
    }
    
    public ObjectInspector initialize(final ObjectInspector[] arguments) throws UDFArgumentException {
        if (arguments.length != 2) {
            throw new UDFArgumentLengthException("throughput_observation_radio_udf requires 2 arguments.");
        }
        this.checkPrimitveArgument(arguments[0], PrimitiveObjectInspector.PrimitiveCategory.STRING, 0);
        this.checkPrimitveArgument(arguments[1], PrimitiveObjectInspector.PrimitiveCategory.STRING, 1);
        this.interferenceObservationObjectInspector = (StringObjectInspector)arguments[0];
        this.contentionObservationObjectInspector = (StringObjectInspector)arguments[1];
        return (ObjectInspector)PrimitiveObjectInspectorFactory.writableStringObjectInspector;
    }
    
    private void checkPrimitveArgument(final ObjectInspector objectInspector, final PrimitiveObjectInspector.PrimitiveCategory expectedCategory, final int argumentNumber) throws UDFArgumentTypeException {
        if (!objectInspector.getCategory().equals((Object)ObjectInspector.Category.PRIMITIVE) || !((PrimitiveObjectInspector)objectInspector).getPrimitiveCategory().equals((Object)expectedCategory)) {
            throw new UDFArgumentTypeException(argumentNumber, "Argument number " + (argumentNumber + 1) + " must be a " + expectedCategory);
        }
    }
    
    public Object evaluate(final GenericUDF.DeferredObject[] arguments) throws HiveException {
        final GenericUDF.DeferredObject interferenceArgument = arguments[0];
        final GenericUDF.DeferredObject contentionArgument = arguments[1];
        final String interferenceObservation = this.getArgumentWithNullCheck(this.interferenceObservationObjectInspector, interferenceArgument);
        final String contentionObservation = this.getArgumentWithNullCheck(this.contentionObservationObjectInspector, contentionArgument);
        Text throughputObservation = null;
        final String throughputObservationText = this.throughputCalculator.evaluate(interferenceObservation, contentionObservation);
        if (throughputObservationText != null) {
            throughputObservation = new Text(throughputObservationText);
        }
        return throughputObservation;
    }
    
    private String getArgumentWithNullCheck(final StringObjectInspector objectInspector, final GenericUDF.DeferredObject argument) throws HiveException {
        if (null == argument) {
            return null;
        }
        return objectInspector.getPrimitiveJavaObject(argument.get());
    }
    
    public String getDisplayString(final String[] children) {
        final StringBuffer sb = new StringBuffer();
        sb.append("returns throughput_observation_radio_udf(\"");
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
