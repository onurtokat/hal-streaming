// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.kryo.serializers;

import java.io.InputStream;
import java.util.zip.InflaterInputStream;
import java.util.zip.Inflater;
import com.esotericsoftware.kryo.io.Input;
import java.io.IOException;
import com.esotericsoftware.kryo.KryoException;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Deflater;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;

public class DeflateSerializer extends Serializer
{
    private final Serializer serializer;
    private boolean noHeaders;
    private int compressionLevel;
    
    public DeflateSerializer(final Serializer serializer) {
        this.noHeaders = true;
        this.compressionLevel = 4;
        this.serializer = serializer;
    }
    
    public void write(final Kryo kryo, final Output output, final Object object) {
        final Deflater deflater = new Deflater(this.compressionLevel, this.noHeaders);
        final DeflaterOutputStream deflaterStream = new DeflaterOutputStream(output, deflater);
        final Output deflaterOutput = new Output(deflaterStream, 256);
        kryo.writeObject(deflaterOutput, object, this.serializer);
        deflaterOutput.flush();
        try {
            deflaterStream.finish();
        }
        catch (IOException ex) {
            throw new KryoException(ex);
        }
    }
    
    public Object read(final Kryo kryo, final Input input, final Class type) {
        final Inflater inflater = new Inflater(this.noHeaders);
        final InflaterInputStream inflaterInput = new InflaterInputStream(input, inflater);
        return kryo.readObject(new Input(inflaterInput, 256), (Class<Object>)type, this.serializer);
    }
    
    public void setNoHeaders(final boolean noHeaders) {
        this.noHeaders = noHeaders;
    }
    
    public void setCompressionLevel(final int compressionLevel) {
        this.compressionLevel = compressionLevel;
    }
    
    public Object copy(final Kryo kryo, final Object original) {
        return this.serializer.copy(kryo, original);
    }
}
