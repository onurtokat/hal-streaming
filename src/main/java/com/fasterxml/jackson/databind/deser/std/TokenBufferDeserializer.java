// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.util.TokenBuffer;

@JacksonStdImpl
public class TokenBufferDeserializer extends StdScalarDeserializer<TokenBuffer>
{
    private static final long serialVersionUID = 1L;
    
    public TokenBufferDeserializer() {
        super(TokenBuffer.class);
    }
    
    @Override
    public TokenBuffer deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
        return this.createBufferInstance(jp).deserialize(jp, ctxt);
    }
    
    protected TokenBuffer createBufferInstance(final JsonParser jp) {
        return new TokenBuffer(jp);
    }
}
