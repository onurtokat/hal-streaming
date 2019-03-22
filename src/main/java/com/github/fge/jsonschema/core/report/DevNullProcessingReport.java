// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.report;

public final class DevNullProcessingReport extends AbstractProcessingReport
{
    public DevNullProcessingReport(final LogLevel logLevel, final LogLevel exceptionThreshold) {
        super(logLevel, exceptionThreshold);
    }
    
    public DevNullProcessingReport(final LogLevel logLevel) {
        super(logLevel);
    }
    
    public DevNullProcessingReport() {
    }
    
    @Override
    public void log(final LogLevel level, final ProcessingMessage message) {
    }
}
