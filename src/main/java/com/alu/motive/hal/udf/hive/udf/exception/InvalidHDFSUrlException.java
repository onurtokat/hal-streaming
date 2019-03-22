// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.udf.hive.udf.exception;

public class InvalidHDFSUrlException extends Exception
{
    private static final long serialVersionUID = 1L;
    
    public InvalidHDFSUrlException(final String msg, final Throwable t) {
        super(msg, t);
    }
    
    public InvalidHDFSUrlException(final String msg) {
        super(msg);
    }
}
