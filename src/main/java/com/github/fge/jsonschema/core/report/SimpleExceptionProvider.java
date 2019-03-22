// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.report;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.exceptions.ExceptionProvider;

public final class SimpleExceptionProvider implements ExceptionProvider
{
    private static final ExceptionProvider INSTANCE;
    
    public static ExceptionProvider getInstance() {
        return SimpleExceptionProvider.INSTANCE;
    }
    
    @Override
    public ProcessingException doException(final ProcessingMessage message) {
        return new ProcessingException(message);
    }
    
    static {
        INSTANCE = new SimpleExceptionProvider();
    }
}
