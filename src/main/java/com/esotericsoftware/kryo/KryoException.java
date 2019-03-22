// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.kryo;

public class KryoException extends RuntimeException
{
    private StringBuffer trace;
    
    public KryoException() {
    }
    
    public KryoException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public KryoException(final String message) {
        super(message);
    }
    
    public KryoException(final Throwable cause) {
        super(cause);
    }
    
    public String getMessage() {
        if (this.trace == null) {
            return super.getMessage();
        }
        final StringBuffer buffer = new StringBuffer(512);
        buffer.append(super.getMessage());
        if (buffer.length() > 0) {
            buffer.append('\n');
        }
        buffer.append("Serialization trace:");
        buffer.append(this.trace);
        return buffer.toString();
    }
    
    public void addTrace(final String info) {
        if (info == null) {
            throw new IllegalArgumentException("info cannot be null.");
        }
        if (this.trace == null) {
            this.trace = new StringBuffer(512);
        }
        this.trace.append('\n');
        this.trace.append(info);
    }
}
