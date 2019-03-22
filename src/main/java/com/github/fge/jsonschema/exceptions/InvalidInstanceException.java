// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.exceptions;

import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;

public final class InvalidInstanceException extends ProcessingException
{
    public InvalidInstanceException(final ProcessingMessage message) {
        super(message);
    }
}
