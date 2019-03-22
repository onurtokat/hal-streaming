// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.kryo.io;

import java.io.IOException;
import com.esotericsoftware.minlog.Log;
import com.esotericsoftware.kryo.KryoException;
import java.io.InputStream;

public class InputChunked extends Input
{
    private int chunkSize;
    
    public InputChunked() {
        super(2048);
        this.chunkSize = -1;
    }
    
    public InputChunked(final int bufferSize) {
        super(bufferSize);
        this.chunkSize = -1;
    }
    
    public InputChunked(final InputStream inputStream) {
        super(inputStream, 2048);
        this.chunkSize = -1;
    }
    
    public InputChunked(final InputStream inputStream, final int bufferSize) {
        super(inputStream, bufferSize);
        this.chunkSize = -1;
    }
    
    public void setInputStream(final InputStream inputStream) {
        super.setInputStream(inputStream);
        this.chunkSize = -1;
    }
    
    public void setBuffer(final byte[] bytes, final int offset, final int count) {
        super.setBuffer(bytes, offset, count);
        this.chunkSize = -1;
    }
    
    public void rewind() {
        super.rewind();
        this.chunkSize = -1;
    }
    
    protected int fill(final byte[] buffer, final int offset, final int count) throws KryoException {
        if (this.chunkSize == -1) {
            this.readChunkSize();
        }
        else if (this.chunkSize == 0) {
            return -1;
        }
        final int actual = super.fill(buffer, offset, Math.min(this.chunkSize, count));
        this.chunkSize -= actual;
        if (this.chunkSize == 0) {
            this.readChunkSize();
        }
        return actual;
    }
    
    private void readChunkSize() {
        try {
            final InputStream inputStream = this.getInputStream();
            int offset = 0;
            int result = 0;
            while (offset < 32) {
                final int b = inputStream.read();
                if (b == -1) {
                    throw new KryoException("Buffer underflow.");
                }
                result |= (b & 0x7F) << offset;
                if ((b & 0x80) == 0x0) {
                    this.chunkSize = result;
                    if (Log.TRACE) {
                        Log.trace("kryo", "Read chunk: " + this.chunkSize);
                    }
                    return;
                }
                offset += 7;
            }
        }
        catch (IOException ex) {
            throw new KryoException(ex);
        }
        throw new KryoException("Malformed integer.");
    }
    
    public void nextChunks() {
        if (this.chunkSize == -1) {
            this.readChunkSize();
        }
        while (this.chunkSize > 0) {
            this.skip(this.chunkSize);
        }
        this.chunkSize = -1;
        if (Log.TRACE) {
            Log.trace("kryo", "Next chunks.");
        }
    }
}
