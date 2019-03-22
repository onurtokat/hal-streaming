// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.uritemplate;

import java.nio.CharBuffer;

public final class URITemplateParseException extends URITemplateException
{
    private final String originalMessage;
    private final int offset;
    
    public URITemplateParseException(final String message, final CharBuffer buffer, final boolean previousChar) {
        super(message);
        this.originalMessage = message;
        this.offset = (previousChar ? (buffer.position() - 1) : buffer.position());
    }
    
    public URITemplateParseException(final String message, final CharBuffer buffer) {
        this(message, buffer, false);
    }
    
    public String getOriginalMessage() {
        return this.originalMessage;
    }
    
    public int getOffset() {
        return this.offset;
    }
    
    @Override
    public String getMessage() {
        return super.getMessage() + " (at offset " + this.offset + ')';
    }
}
