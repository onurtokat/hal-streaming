// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.report;

public interface ReportProvider
{
    ProcessingReport newReport();
    
    ProcessingReport newReport(final LogLevel p0);
    
    ProcessingReport newReport(final LogLevel p0, final LogLevel p1);
}
