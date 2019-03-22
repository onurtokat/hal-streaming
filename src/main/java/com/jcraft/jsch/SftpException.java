// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

public class SftpException extends Exception
{
    public int id;
    private Throwable cause;
    
    public SftpException(final int id, final String s) {
        super(s);
        this.cause = null;
        this.id = id;
    }
    
    public SftpException(final int id, final String s, final Throwable cause) {
        super(s);
        this.cause = null;
        this.id = id;
        this.cause = cause;
    }
    
    public String toString() {
        return this.id + ": " + this.getMessage();
    }
    
    public Throwable getCause() {
        return this.cause;
    }
}
