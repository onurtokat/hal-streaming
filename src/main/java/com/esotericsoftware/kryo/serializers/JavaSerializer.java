// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.kryo.serializers;

import java.io.InputStream;
import java.io.ObjectInputStream;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.KryoException;
import java.io.OutputStream;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import java.io.ObjectOutputStream;
import com.esotericsoftware.kryo.Serializer;

public class JavaSerializer extends Serializer
{
    private ObjectOutputStream objectStream;
    private Output lastOutput;
    
    public void write(final Kryo kryo, final Output output, final Object object) {
        try {
            if (output != this.lastOutput) {
                this.objectStream = new ObjectOutputStream(output);
                this.lastOutput = output;
            }
            else {
                this.objectStream.reset();
            }
            this.objectStream.writeObject(object);
            this.objectStream.flush();
        }
        catch (Exception ex) {
            throw new KryoException("Error during Java serialization.", ex);
        }
    }
    
    public Object read(final Kryo kryo, final Input input, final Class type) {
        try {
            return new ObjectInputStream(input).readObject();
        }
        catch (Exception ex) {
            throw new KryoException("Error during Java deserialization.", ex);
        }
    }
}
