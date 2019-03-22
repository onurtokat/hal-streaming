// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.report;

public final class ConsoleProcessingReport extends AbstractProcessingReport
{
    public ConsoleProcessingReport(final LogLevel logLevel, final LogLevel exceptionThreshold) {
        super(logLevel, exceptionThreshold);
    }
    
    public ConsoleProcessingReport(final LogLevel logLevel) {
        super(logLevel);
    }
    
    public ConsoleProcessingReport() {
    }
    
    @Override
    public void log(final LogLevel level, final ProcessingMessage message) {
        System.out.println(message);
    }
}
