// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.kryo.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.io.OutputStream;

public class ByteBufferOutputStream extends OutputStream
{
    private ByteBuffer byteBuffer;
    
    public ByteBufferOutputStream() {
    }
    
    public ByteBufferOutputStream(final int bufferSize) {
        this(ByteBuffer.allocate(bufferSize));
    }
    
    public ByteBufferOutputStream(final ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }
    
    public ByteBuffer getByteBuffer() {
        return this.byteBuffer;
    }
    
    public void setByteBuffer(final ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }
    
    public void write(final int b) throws IOException {
        if (!this.byteBuffer.hasRemaining()) {
            this.flush();
        }
        this.byteBuffer.put((byte)b);
    }
    
    public void write(final byte[] bytes, final int offset, final int length) throws IOException {
        if (this.byteBuffer.remaining() < length) {
            this.flush();
        }
        this.byteBuffer.put(bytes, offset, length);
    }
}
