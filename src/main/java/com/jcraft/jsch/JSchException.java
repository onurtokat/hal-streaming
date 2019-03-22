// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

public class JSchException extends Exception
{
    private Throwable cause;
    
    public JSchException() {
        this.cause = null;
    }
    
    public JSchException(final String s) {
        super(s);
        this.cause = null;
    }
    
    public JSchException(final String s, final Throwable cause) {
        super(s);
        this.cause = null;
        this.cause = cause;
    }
    
    public Throwable getCause() {
        return this.cause;
    }
}
