// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.kryo.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.io.InputStream;

public class ByteBufferInputStream extends InputStream
{
    private ByteBuffer byteBuffer;
    
    public ByteBufferInputStream() {
    }
    
    public ByteBufferInputStream(final int bufferSize) {
        this(ByteBuffer.allocate(bufferSize));
        this.byteBuffer.flip();
    }
    
    public ByteBufferInputStream(final ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }
    
    public ByteBuffer getByteBuffer() {
        return this.byteBuffer;
    }
    
    public void setByteBuffer(final ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }
    
    public int read() throws IOException {
        if (!this.byteBuffer.hasRemaining()) {
            return -1;
        }
        return this.byteBuffer.get();
    }
    
    public int read(final byte[] bytes, final int offset, final int length) throws IOException {
        final int count = Math.min(this.byteBuffer.remaining(), length);
        if (count == 0) {
            return -1;
        }
        this.byteBuffer.get(bytes, offset, count);
        return count;
    }
    
    public int available() throws IOException {
        return this.byteBuffer.remaining();
    }
}
