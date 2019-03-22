// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

import java.net.SocketException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;

public class IO
{
    InputStream in;
    OutputStream out;
    OutputStream out_ext;
    private boolean in_dontclose;
    private boolean out_dontclose;
    private boolean out_ext_dontclose;
    
    public IO() {
        this.in_dontclose = false;
        this.out_dontclose = false;
        this.out_ext_dontclose = false;
    }
    
    void setOutputStream(final OutputStream out) {
        this.out = out;
    }
    
    void setOutputStream(final OutputStream outputStream, final boolean out_dontclose) {
        this.out_dontclose = out_dontclose;
        this.setOutputStream(outputStream);
    }
    
    void setExtOutputStream(final OutputStream out_ext) {
        this.out_ext = out_ext;
    }
    
    void setExtOutputStream(final OutputStream extOutputStream, final boolean out_ext_dontclose) {
        this.out_ext_dontclose = out_ext_dontclose;
        this.setExtOutputStream(extOutputStream);
    }
    
    void setInputStream(final InputStream in) {
        this.in = in;
    }
    
    void setInputStream(final InputStream \u0131nputStream, final boolean in_dontclose) {
        this.in_dontclose = in_dontclose;
        this.setInputStream(\u0131nputStream);
    }
    
    public void put(final Packet packet) throws IOException, SocketException {
        this.out.write(packet.buffer.buffer, 0, packet.buffer.index);
        this.out.flush();
    }
    
    void put(final byte[] array, final int n, final int n2) throws IOException {
        this.out.write(array, n, n2);
        this.out.flush();
    }
    
    void put_ext(final byte[] array, final int n, final int n2) throws IOException {
        this.out_ext.write(array, n, n2);
        this.out_ext.flush();
    }
    
    int getByte() throws IOException {
        return this.in.read();
    }
    
    void getByte(final byte[] array) throws IOException {
        this.getByte(array, 0, array.length);
    }
    
    void getByte(final byte[] array, int n, int i) throws IOException {
        do {
            final int read = this.in.read(array, n, i);
            if (read < 0) {
                throw new IOException("End of IO Stream Read");
            }
            n += read;
            i -= read;
        } while (i > 0);
    }
    
    void out_close() {
        try {
            if (this.out != null && !this.out_dontclose) {
                this.out.close();
            }
            this.out = null;
        }
        catch (Exception ex) {}
    }
    
    public void close() {
        try {
            if (this.in != null && !this.in_dontclose) {
                this.in.close();
            }
            this.in = null;
        }
        catch (Exception ex) {}
        this.out_close();
        try {
            if (this.out_ext != null && !this.out_ext_dontclose) {
                this.out_ext.close();
            }
            this.out_ext = null;
        }
        catch (Exception ex2) {}
    }
}
