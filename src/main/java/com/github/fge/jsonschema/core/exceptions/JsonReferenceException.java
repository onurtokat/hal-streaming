// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.exceptions;

import com.github.fge.jsonschema.core.report.ProcessingMessage;

public final class JsonReferenceException extends ProcessingException
{
    public JsonReferenceException(final ProcessingMessage message) {
        super(message);
    }
    
    public JsonReferenceException(final ProcessingMessage message, final Throwable e) {
        super(message, e);
    }
}
