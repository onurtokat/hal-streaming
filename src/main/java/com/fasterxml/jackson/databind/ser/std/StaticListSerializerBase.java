// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.lang.reflect.Type;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.util.Collection;

public abstract class StaticListSerializerBase<T extends Collection<?>> extends StdSerializer<T>
{
    protected StaticListSerializerBase(final Class<?> cls) {
        super(cls, false);
    }
    
    @Override
    public boolean isEmpty(final T value) {
        return value == null || value.size() == 0;
    }
    
    @Override
    public JsonNode getSchema(final SerializerProvider provider, final Type typeHint) {
        return this.createSchemaNode("array", true).set("items", this.contentSchema());
    }
    
    @Override
    public void acceptJsonFormatVisitor(final JsonFormatVisitorWrapper visitor, final JavaType typeHint) throws JsonMappingException {
        this.acceptContentVisitor(visitor.expectArrayFormat(typeHint));
    }
    
    protected abstract JsonNode contentSchema();
    
    protected abstract void acceptContentVisitor(final JsonArrayFormatVisitor p0) throws JsonMappingException;
}
