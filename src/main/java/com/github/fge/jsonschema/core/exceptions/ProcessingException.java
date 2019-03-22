// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.exceptions;

import com.github.fge.jsonschema.core.report.LogLevel;
import com.github.fge.jsonschema.core.report.ProcessingMessage;

public class ProcessingException extends Exception
{
    private final ProcessingMessage processingMessage;
    
    public ProcessingException() {
        this(new ProcessingMessage().setLogLevel(LogLevel.FATAL));
    }
    
    public ProcessingException(final String message) {
        this(new ProcessingMessage().setMessage(message).setLogLevel(LogLevel.FATAL));
    }
    
    public ProcessingException(final ProcessingMessage message) {
        this.processingMessage = message.setLogLevel(LogLevel.FATAL);
    }
    
    public ProcessingException(final String message, final Throwable e) {
        this.processingMessage = new ProcessingMessage().setLogLevel(LogLevel.FATAL).setMessage(message).put("exceptionClass", e.getClass().getName()).put("exceptionMessage", e.getMessage());
    }
    
    public ProcessingException(final ProcessingMessage message, final Throwable e) {
        this.processingMessage = message.setLogLevel(LogLevel.FATAL).put("exceptionClass", e.getClass().getName()).put("exceptionMessage", e.getMessage());
    }
    
    @Override
    public final String getMessage() {
        return this.processingMessage.toString();
    }
    
    public final String getShortMessage() {
        return this.processingMessage.getMessage();
    }
    
    public final ProcessingMessage getProcessingMessage() {
        return this.processingMessage;
    }
}
