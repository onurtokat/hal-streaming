// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.exceptions;

import com.github.fge.jsonschema.core.report.ProcessingMessage;

public interface ExceptionProvider
{
    ProcessingException doException(final ProcessingMessage p0);
}
