// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.udf.hive.udf;

import org.slf4j.LoggerFactory;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.io.Text;
import com.alu.motive.hal.udf.hive.udf.exception.InvalidHDFSUrlException;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.hadoop.fs.FileSystem;
import java.io.Reader;
import java.io.BufferedReader;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.WritableConstantStringObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import java.util.HashMap;
import java.util.Map;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.StringObjectInspector;
import org.slf4j.Logger;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;

@Description(name = "lookupMacAddress", value = "_FUNC_(arg a,arg b, arg c,arg d) - Returns the friendly name for the device or a default value.Arg a and b are STRING Constants. Arg c and d are STRING type. a is the hdfs url (ex:hdfs://mas:8020). b is the hdfsFilePath of the MACs.csv file ( ex: /data/hda/MACs.txt). c is the device Mac Address. d is the default value if c is not found into the lookup file or if c is NULL. d can be NULL")
public class LookupMacAddress extends GenericUDF
{
    private static final Logger LOG;
    private static final String OPNAME = "lookupMacAddress";
    private StringObjectInspector soiMacAddressArg;
    private PrimitiveObjectInspector soiDefaultMacAddressArg;
    private final Map<String, String> macs;
    
    public LookupMacAddress() {
        this.macs = new HashMap<String, String>();
    }
    
    public ObjectInspector initialize(final ObjectInspector[] arguments) throws UDFArgumentException {
        if (arguments.length != 4) {
            throw new UDFArgumentLengthException("lookupMacAddress requires four arguments.");
        }
        for (int i = 0; i < 2; ++i) {
            if (arguments[i].getCategory() != ObjectInspector.Category.PRIMITIVE || ((PrimitiveObjectInspector)arguments[i]).getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.STRING) {
                throw new UDFArgumentTypeException(i, "Argument number " + (i + 1) + " must be a STRING");
            }
        }
        if (arguments[2].getCategory() != ObjectInspector.Category.PRIMITIVE || ((PrimitiveObjectInspector)arguments[2]).getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.STRING) {
            throw new UDFArgumentTypeException(2, "Argument number 3 must be a STRING");
        }
        this.soiMacAddressArg = (StringObjectInspector)arguments[2];
        if (arguments[3].getCategory() == ObjectInspector.Category.PRIMITIVE && (((PrimitiveObjectInspector)arguments[3]).getPrimitiveCategory() == PrimitiveObjectInspector.PrimitiveCategory.STRING || ((PrimitiveObjectInspector)arguments[3]).getPrimitiveCategory() == PrimitiveObjectInspector.PrimitiveCategory.VOID)) {
            this.soiDefaultMacAddressArg = (PrimitiveObjectInspector)arguments[3];
            try {
                this.buildMACsMap(arguments);
            }
            catch (Exception e) {
                LookupMacAddress.LOG.error("Cannot initialize macs address from configuration file :" + e.getMessage(), e);
                throw new UDFArgumentException("Initialize lookupMacAddress udf exception type:" + e.getClass().getName() + ", message:" + e.getMessage());
            }
            return (ObjectInspector)PrimitiveObjectInspectorFactory.writableStringObjectInspector;
        }
        throw new UDFArgumentTypeException(3, "Argument number 4 must be a STRING or NULL");
    }
    
    private void buildMACsMap(final ObjectInspector[] argOIs) throws IOException, InvalidHDFSUrlException {
        final String hdfsURL = ((WritableConstantStringObjectInspector)argOIs[0]).getWritableConstantValue().toString();
        final String hdfsFilePath = ((WritableConstantStringObjectInspector)argOIs[1]).getWritableConstantValue().toString();
        final FileSystem fileSystem = LookupMacAddressHelper.getFileSystem(hdfsURL);
        final InputStreamReader content = LookupMacAddressHelper.getInputStreamFromFile(fileSystem, hdfsFilePath);
        final BufferedReader reader = new BufferedReader(content);
        String line = null;
        String[] mapping = null;
        while ((line = reader.readLine()) != null) {
            final String lineTrimmed = line.trim();
            if (!lineTrimmed.isEmpty()) {
                mapping = lineTrimmed.split("\\^");
                this.macs.put(mapping[0], mapping[1]);
            }
        }
        reader.close();
        LookupMacAddressHelper.close(fileSystem);
    }
    
    public Map<String, String> getMacs() {
        return this.macs;
    }
    
    public Object evaluate(final GenericUDF.DeferredObject[] arguments) throws HiveException {
        if (arguments[2].get() == null) {
            return this.getDefaultMacAddress(arguments);
        }
        final String macAddress = this.getMacAddress(arguments);
        if (macAddress != null) {
            return new Text(macAddress);
        }
        return this.getDefaultMacAddress(arguments);
    }
    
    private String getMacAddress(final GenericUDF.DeferredObject[] arguments) throws HiveException {
        final String macAddress = this.soiMacAddressArg.getPrimitiveJavaObject(arguments[2].get());
        final List<String> machedValues = new ArrayList<String>();
        if (macAddress != null) {
            for (final Map.Entry<String, String> entry : this.macs.entrySet()) {
                if (macAddress.startsWith(entry.getKey())) {
                    machedValues.add(entry.getValue());
                }
            }
            if (machedValues.size() > 1) {
                LookupMacAddress.LOG.warn("for {} found many matches in file: {}", macAddress, machedValues);
            }
        }
        return machedValues.isEmpty() ? null : this.concatenateReadableNameWithMacAddressEnding(machedValues.get(0), macAddress);
    }
    
    private Object getDefaultMacAddress(final GenericUDF.DeferredObject[] arguments) throws HiveException {
        if (arguments[3].get() != null) {
            return new Text(this.soiDefaultMacAddressArg.getPrimitiveJavaObject(arguments[3].get()).toString());
        }
        return null;
    }
    
    private String concatenateReadableNameWithMacAddressEnding(final String readableName, final String macAddress) {
        final int lastThreeNibbleLength = 8;
        if (macAddress != null) {
            return readableName + "_" + ((macAddress.length() >= lastThreeNibbleLength) ? macAddress.substring(macAddress.length() - lastThreeNibbleLength) : macAddress);
        }
        return readableName;
    }
    
    public String getDisplayString(final String[] children) {
        final StringBuffer sb = new StringBuffer();
        sb.append("returns lookupMacAddress(\"");
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
    
    static {
        LOG = LoggerFactory.getLogger(LookupMacAddress.class);
    }
}
