// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;

public final class NullNode extends ValueNode
{
    public static final NullNode instance;
    
    public static NullNode getInstance() {
        return NullNode.instance;
    }
    
    @Override
    public JsonNodeType getNodeType() {
        return JsonNodeType.NULL;
    }
    
    @Override
    public JsonToken asToken() {
        return JsonToken.VALUE_NULL;
    }
    
    @Override
    public String asText(final String defaultValue) {
        return defaultValue;
    }
    
    @Override
    public String asText() {
        return "null";
    }
    
    @Override
    public final void serialize(final JsonGenerator jg, final SerializerProvider provider) throws IOException, JsonProcessingException {
        provider.defaultSerializeNull(jg);
    }
    
    @Override
    public boolean equals(final Object o) {
        return o == this;
    }
    
    static {
        instance = new NullNode();
    }
}
