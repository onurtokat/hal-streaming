// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.report;

public final class ListReportProvider extends AbstractReportProvider
{
    public ListReportProvider(final LogLevel logLevel, final LogLevel exceptionThreshold) {
        super(logLevel, exceptionThreshold);
    }
    
    @Override
    public ProcessingReport newReport() {
        return new ListProcessingReport(this.logLevel, this.exceptionThreshold);
    }
    
    @Override
    public ProcessingReport newReport(final LogLevel logLevel) {
        return new ListProcessingReport(logLevel);
    }
    
    @Override
    public ProcessingReport newReport(final LogLevel logLevel, final LogLevel exceptionThreshold) {
        return new ListProcessingReport(logLevel, exceptionThreshold);
    }
}
