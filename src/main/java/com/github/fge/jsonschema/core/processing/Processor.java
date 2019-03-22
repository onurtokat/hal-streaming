// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.processing;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.core.report.MessageProvider;

public interface Processor<IN extends MessageProvider, OUT extends MessageProvider>
{
    OUT process(final ProcessingReport p0, final IN p1) throws ProcessingException;
}
