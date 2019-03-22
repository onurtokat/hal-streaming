// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.lang.reflect.Type;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.IOException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class UnknownSerializer extends StdSerializer<Object>
{
    public UnknownSerializer() {
        super(Object.class);
    }
    
    @Override
    public void serialize(final Object value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonMappingException {
        if (provider.isEnabled(SerializationFeature.FAIL_ON_EMPTY_BEANS)) {
            this.failForEmpty(value);
        }
        jgen.writeStartObject();
        jgen.writeEndObject();
    }
    
    @Override
    public final void serializeWithType(final Object value, final JsonGenerator jgen, final SerializerProvider provider, final TypeSerializer typeSer) throws IOException, JsonGenerationException {
        if (provider.isEnabled(SerializationFeature.FAIL_ON_EMPTY_BEANS)) {
            this.failForEmpty(value);
        }
        typeSer.writeTypePrefixForObject(value, jgen);
        typeSer.writeTypeSuffixForObject(value, jgen);
    }
    
    @Override
    public JsonNode getSchema(final SerializerProvider provider, final Type typeHint) throws JsonMappingException {
        return null;
    }
    
    @Override
    public void acceptJsonFormatVisitor(final JsonFormatVisitorWrapper visitor, final JavaType typeHint) throws JsonMappingException {
        visitor.expectAnyFormat(typeHint);
    }
    
    protected void failForEmpty(final Object value) throws JsonMappingException {
        throw new JsonMappingException("No serializer found for class " + value.getClass().getName() + " and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS) )");
    }
}
