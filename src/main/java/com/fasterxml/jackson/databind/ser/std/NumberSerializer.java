// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonNumberFormatVisitor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.lang.reflect.Type;
import java.io.IOException;
import java.math.BigInteger;
import java.math.BigDecimal;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;

@JacksonStdImpl
public final class NumberSerializer extends StdScalarSerializer<Number>
{
    public static final NumberSerializer instance;
    
    public NumberSerializer() {
        super(Number.class);
    }
    
    @Override
    public void serialize(final Number value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
        if (value instanceof BigDecimal) {
            jgen.writeNumber((BigDecimal)value);
        }
        else if (value instanceof BigInteger) {
            jgen.writeNumber((BigInteger)value);
        }
        else if (value instanceof Integer) {
            jgen.writeNumber(value.intValue());
        }
        else if (value instanceof Long) {
            jgen.writeNumber(value.longValue());
        }
        else if (value instanceof Double) {
            jgen.writeNumber(value.doubleValue());
        }
        else if (value instanceof Float) {
            jgen.writeNumber(value.floatValue());
        }
        else if (value instanceof Byte || value instanceof Short) {
            jgen.writeNumber(value.intValue());
        }
        else {
            jgen.writeNumber(value.toString());
        }
    }
    
    @Override
    public JsonNode getSchema(final SerializerProvider provider, final Type typeHint) {
        return this.createSchemaNode("number", true);
    }
    
    @Override
    public void acceptJsonFormatVisitor(final JsonFormatVisitorWrapper visitor, final JavaType typeHint) throws JsonMappingException {
        final JsonNumberFormatVisitor v2 = visitor.expectNumberFormat(typeHint);
        if (v2 != null) {
            v2.numberType(JsonParser.NumberType.BIG_DECIMAL);
        }
    }
    
    static {
        instance = new NumberSerializer();
    }
}
