// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.JsonSerializer;

public final class TypeWrappedSerializer extends JsonSerializer<Object>
{
    protected final TypeSerializer _typeSerializer;
    protected final JsonSerializer<Object> _serializer;
    
    public TypeWrappedSerializer(final TypeSerializer typeSer, final JsonSerializer<?> ser) {
        this._typeSerializer = typeSer;
        this._serializer = (JsonSerializer<Object>)ser;
    }
    
    @Override
    public void serialize(final Object value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonProcessingException {
        this._serializer.serializeWithType(value, jgen, provider, this._typeSerializer);
    }
    
    @Override
    public void serializeWithType(final Object value, final JsonGenerator jgen, final SerializerProvider provider, final TypeSerializer typeSer) throws IOException, JsonProcessingException {
        this._serializer.serializeWithType(value, jgen, provider, typeSer);
    }
    
    @Override
    public Class<Object> handledType() {
        return Object.class;
    }
}
