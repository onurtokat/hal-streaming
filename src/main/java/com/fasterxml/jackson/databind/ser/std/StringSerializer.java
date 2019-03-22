// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.lang.reflect.Type;
import java.io.IOException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;

@JacksonStdImpl
public final class StringSerializer extends NonTypedScalarSerializerBase<String>
{
    public StringSerializer() {
        super(String.class);
    }
    
    @Override
    public boolean isEmpty(final String value) {
        return value == null || value.length() == 0;
    }
    
    @Override
    public void serialize(final String value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
        jgen.writeString(value);
    }
    
    @Override
    public JsonNode getSchema(final SerializerProvider provider, final Type typeHint) {
        return this.createSchemaNode("string", true);
    }
    
    @Override
    public void acceptJsonFormatVisitor(final JsonFormatVisitorWrapper visitor, final JavaType typeHint) throws JsonMappingException {
        if (visitor != null) {
            visitor.expectStringFormat(typeHint);
        }
    }
}
