// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.kryo.serializers;

import java.io.InputStream;
import com.esotericsoftware.kryo.io.InputChunked;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.util.ObjectMap;
import java.io.OutputStream;
import com.esotericsoftware.kryo.io.OutputChunked;
import com.esotericsoftware.minlog.Log;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.Kryo;

public class CompatibleFieldSerializer<T> extends FieldSerializer<T>
{
    public CompatibleFieldSerializer(final Kryo kryo, final Class type) {
        super(kryo, type);
    }
    
    public void write(final Kryo kryo, final Output output, final T object) {
        final CachedField[] fields = this.getFields();
        final ObjectMap context = kryo.getGraphContext();
        if (!context.containsKey(this)) {
            context.put(this, null);
            if (Log.TRACE) {
                Log.trace("kryo", "Write " + fields.length + " field names.");
            }
            output.writeInt(fields.length, true);
            for (int i = 0, n = fields.length; i < n; ++i) {
                output.writeString(fields[i].field.getName());
            }
        }
        final OutputChunked outputChunked = new OutputChunked(output, 1024);
        for (int j = 0, n2 = fields.length; j < n2; ++j) {
            fields[j].write(outputChunked, object);
            outputChunked.endChunks();
        }
    }
    
    public T read(final Kryo kryo, final Input input, final Class<T> type) {
        final T object = this.create(kryo, input, type);
        kryo.reference(object);
        final ObjectMap context = kryo.getGraphContext();
        CachedField[] fields = context.get(this);
        if (fields == null) {
            final int length = input.readInt(true);
            if (Log.TRACE) {
                Log.trace("kryo", "Read " + length + " field names.");
            }
            final String[] names = new String[length];
            for (int i = 0; i < length; ++i) {
                names[i] = input.readString();
            }
            fields = new CachedField[length];
            final CachedField[] allFields = this.getFields();
            int j = 0;
            final int n = names.length;
        Label_0136:
            while (j < n) {
                final String schemaName = names[j];
                while (true) {
                    for (int ii = 0, nn = allFields.length; ii < nn; ++ii) {
                        if (allFields[ii].field.getName().equals(schemaName)) {
                            fields[j] = allFields[ii];
                            ++j;
                            continue Label_0136;
                        }
                    }
                    if (Log.TRACE) {
                        Log.trace("kryo", "Ignore obsolete field: " + schemaName);
                    }
                    continue;
                }
            }
            context.put(this, fields);
        }
        final InputChunked inputChunked = new InputChunked(input, 1024);
        for (int k = 0, n2 = fields.length; k < n2; ++k) {
            final CachedField cachedField = fields[k];
            if (cachedField == null) {
                if (Log.TRACE) {
                    Log.trace("kryo", "Skip obsolete field.");
                }
                inputChunked.nextChunks();
            }
            else {
                cachedField.read(inputChunked, object);
                inputChunked.nextChunks();
            }
        }
        return object;
    }
}
