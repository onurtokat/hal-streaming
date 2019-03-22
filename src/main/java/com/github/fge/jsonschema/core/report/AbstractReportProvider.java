// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.report;

public abstract class AbstractReportProvider implements ReportProvider
{
    protected final LogLevel logLevel;
    protected final LogLevel exceptionThreshold;
    
    protected AbstractReportProvider(final LogLevel logLevel, final LogLevel exceptionThreshold) {
        this.logLevel = logLevel;
        this.exceptionThreshold = exceptionThreshold;
    }
}
