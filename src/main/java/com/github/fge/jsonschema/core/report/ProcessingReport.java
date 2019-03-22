// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.report;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;

public interface ProcessingReport extends Iterable<ProcessingMessage>
{
    LogLevel getLogLevel();
    
    LogLevel getExceptionThreshold();
    
    void debug(final ProcessingMessage p0) throws ProcessingException;
    
    void info(final ProcessingMessage p0) throws ProcessingException;
    
    void warn(final ProcessingMessage p0) throws ProcessingException;
    
    void error(final ProcessingMessage p0) throws ProcessingException;
    
    void fatal(final ProcessingMessage p0) throws ProcessingException;
    
    boolean isSuccess();
    
    void mergeWith(final ProcessingReport p0) throws ProcessingException;
}
