// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.kryo.io;

import com.esotericsoftware.minlog.Log;
import java.io.IOException;
import com.esotericsoftware.kryo.KryoException;
import java.io.OutputStream;

public class OutputChunked extends Output
{
    public OutputChunked() {
        super(2048);
    }
    
    public OutputChunked(final int bufferSize) {
        super(bufferSize);
    }
    
    public OutputChunked(final OutputStream outputStream) {
        super(outputStream, 2048);
    }
    
    public OutputChunked(final OutputStream outputStream, final int bufferSize) {
        super(outputStream, bufferSize);
    }
    
    public void flush() throws KryoException {
        if (this.position() > 0) {
            try {
                this.writeChunkSize();
            }
            catch (IOException ex) {
                throw new KryoException(ex);
            }
        }
        super.flush();
    }
    
    private void writeChunkSize() throws IOException {
        int size = this.position();
        if (Log.TRACE) {
            Log.trace("kryo", "Write chunk: " + size);
        }
        final OutputStream outputStream = this.getOutputStream();
        if ((size & 0xFFFFFF80) == 0x0) {
            outputStream.write(size);
            return;
        }
        outputStream.write((size & 0x7F) | 0x80);
        size >>>= 7;
        if ((size & 0xFFFFFF80) == 0x0) {
            outputStream.write(size);
            return;
        }
        outputStream.write((size & 0x7F) | 0x80);
        size >>>= 7;
        if ((size & 0xFFFFFF80) == 0x0) {
            outputStream.write(size);
            return;
        }
        outputStream.write((size & 0x7F) | 0x80);
        size >>>= 7;
        if ((size & 0xFFFFFF80) == 0x0) {
            outputStream.write(size);
            return;
        }
        outputStream.write((size & 0x7F) | 0x80);
        size >>>= 7;
        outputStream.write(size);
    }
    
    public void endChunks() {
        this.flush();
        if (Log.TRACE) {
            Log.trace("kryo", "End chunks.");
        }
        try {
            this.getOutputStream().write(0);
        }
        catch (IOException ex) {
            throw new KryoException(ex);
        }
    }
}
