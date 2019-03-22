// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.exception;

public class ParameterFormatException extends RuntimeException
{
    public ParameterFormatException(final String message) {
        super(message);
    }
    
    public ParameterFormatException(final String message, final Throwable e) {
        super(message, e);
    }
}
