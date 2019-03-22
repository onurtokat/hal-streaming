// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.udf.hive.udtf;

public class InvalidHdfsUrlException extends Exception
{
    private static final long serialVersionUID = 1L;
    
    public InvalidHdfsUrlException(final String msg, final Throwable t) {
        super(msg, t);
    }
    
    public InvalidHdfsUrlException(final String msg) {
        super(msg);
    }
}
