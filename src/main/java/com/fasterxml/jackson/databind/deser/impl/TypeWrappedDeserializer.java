// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.JsonDeserializer;

public final class TypeWrappedDeserializer extends JsonDeserializer<Object>
{
    final TypeDeserializer _typeDeserializer;
    final JsonDeserializer<Object> _deserializer;
    
    public TypeWrappedDeserializer(final TypeDeserializer typeDeser, final JsonDeserializer<Object> deser) {
        this._typeDeserializer = typeDeser;
        this._deserializer = deser;
    }
    
    @Override
    public Class<?> handledType() {
        return this._deserializer.handledType();
    }
    
    @Override
    public Object deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return this._deserializer.deserializeWithType(jp, ctxt, this._typeDeserializer);
    }
    
    @Override
    public Object deserializeWithType(final JsonParser jp, final DeserializationContext ctxt, final TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
        throw new IllegalStateException("Type-wrapped deserializer's deserializeWithType should never get called");
    }
    
    @Override
    public Object deserialize(final JsonParser jp, final DeserializationContext ctxt, final Object intoValue) throws IOException, JsonProcessingException {
        return this._deserializer.deserialize(jp, ctxt, intoValue);
    }
}
