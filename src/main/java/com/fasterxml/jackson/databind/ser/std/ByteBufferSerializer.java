// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerationException;
import java.io.IOException;
import java.io.InputStream;
import com.fasterxml.jackson.databind.util.ByteBufferBackedInputStream;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.core.JsonGenerator;
import java.nio.ByteBuffer;

public class ByteBufferSerializer extends StdScalarSerializer<ByteBuffer>
{
    public ByteBufferSerializer() {
        super(ByteBuffer.class);
    }
    
    @Override
    public void serialize(final ByteBuffer bbuf, final JsonGenerator gen, final SerializerProvider provider) throws IOException, JsonGenerationException {
        if (bbuf.hasArray()) {
            gen.writeBinary(bbuf.array(), 0, bbuf.limit());
            return;
        }
        final ByteBuffer copy = bbuf.asReadOnlyBuffer();
        if (copy.position() > 0) {
            copy.rewind();
        }
        final InputStream in = new ByteBufferBackedInputStream(copy);
        gen.writeBinary(in, copy.remaining());
        in.close();
    }
}
