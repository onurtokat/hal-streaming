// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.util.Converter;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.lang.reflect.InvocationTargetException;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import java.lang.reflect.Type;
import com.fasterxml.jackson.core.JsonGenerationException;
import java.io.IOException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsonschema.SchemaAware;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.JsonSerializer;

public abstract class StdSerializer<T> extends JsonSerializer<T> implements JsonFormatVisitable, SchemaAware
{
    protected final Class<T> _handledType;
    
    protected StdSerializer(final Class<T> t) {
        this._handledType = t;
    }
    
    protected StdSerializer(final JavaType type) {
        this._handledType = (Class<T>)type.getRawClass();
    }
    
    protected StdSerializer(final Class<?> t, final boolean dummy) {
        this._handledType = (Class<T>)t;
    }
    
    @Override
    public Class<T> handledType() {
        return this._handledType;
    }
    
    @Override
    public abstract void serialize(final T p0, final JsonGenerator p1, final SerializerProvider p2) throws IOException, JsonGenerationException;
    
    @Override
    public JsonNode getSchema(final SerializerProvider provider, final Type typeHint) throws JsonMappingException {
        return this.createSchemaNode("string");
    }
    
    @Override
    public JsonNode getSchema(final SerializerProvider provider, final Type typeHint, final boolean isOptional) throws JsonMappingException {
        final ObjectNode schema = (ObjectNode)this.getSchema(provider, typeHint);
        if (!isOptional) {
            schema.put("required", !isOptional);
        }
        return schema;
    }
    
    protected ObjectNode createObjectNode() {
        return JsonNodeFactory.instance.objectNode();
    }
    
    protected ObjectNode createSchemaNode(final String type) {
        final ObjectNode schema = this.createObjectNode();
        schema.put("type", type);
        return schema;
    }
    
    protected ObjectNode createSchemaNode(final String type, final boolean isOptional) {
        final ObjectNode schema = this.createSchemaNode(type);
        if (!isOptional) {
            schema.put("required", !isOptional);
        }
        return schema;
    }
    
    @Override
    public void acceptJsonFormatVisitor(final JsonFormatVisitorWrapper visitor, final JavaType typeHint) throws JsonMappingException {
        visitor.expectAnyFormat(typeHint);
    }
    
    public void wrapAndThrow(final SerializerProvider provider, Throwable t, final Object bean, final String fieldName) throws IOException {
        while (t instanceof InvocationTargetException && t.getCause() != null) {
            t = t.getCause();
        }
        if (t instanceof Error) {
            throw (Error)t;
        }
        final boolean wrap = provider == null || provider.isEnabled(SerializationFeature.WRAP_EXCEPTIONS);
        if (t instanceof IOException) {
            if (!wrap || !(t instanceof JsonMappingException)) {
                throw (IOException)t;
            }
        }
        else if (!wrap && t instanceof RuntimeException) {
            throw (RuntimeException)t;
        }
        throw JsonMappingException.wrapWithPath(t, bean, fieldName);
    }
    
    public void wrapAndThrow(final SerializerProvider provider, Throwable t, final Object bean, final int index) throws IOException {
        while (t instanceof InvocationTargetException && t.getCause() != null) {
            t = t.getCause();
        }
        if (t instanceof Error) {
            throw (Error)t;
        }
        final boolean wrap = provider == null || provider.isEnabled(SerializationFeature.WRAP_EXCEPTIONS);
        if (t instanceof IOException) {
            if (!wrap || !(t instanceof JsonMappingException)) {
                throw (IOException)t;
            }
        }
        else if (!wrap && t instanceof RuntimeException) {
            throw (RuntimeException)t;
        }
        throw JsonMappingException.wrapWithPath(t, bean, index);
    }
    
    protected boolean isDefaultSerializer(final JsonSerializer<?> serializer) {
        return ClassUtil.isJacksonStdImpl(serializer);
    }
    
    protected JsonSerializer<?> findConvertingContentSerializer(final SerializerProvider provider, final BeanProperty prop, JsonSerializer<?> existingSerializer) throws JsonMappingException {
        final AnnotationIntrospector intr = provider.getAnnotationIntrospector();
        if (intr != null && prop != null) {
            final Object convDef = intr.findSerializationContentConverter(prop.getMember());
            if (convDef != null) {
                final Converter<Object, Object> conv = provider.converterInstance(prop.getMember(), convDef);
                final JavaType delegateType = conv.getOutputType(provider.getTypeFactory());
                if (existingSerializer == null) {
                    existingSerializer = provider.findValueSerializer(delegateType, prop);
                }
                return new StdDelegatingSerializer(conv, delegateType, existingSerializer);
            }
        }
        return existingSerializer;
    }
    
    protected PropertyFilter findPropertyFilter(final SerializerProvider provider, final Object filterId, final Object valueToFilter) throws JsonMappingException {
        final FilterProvider filters = provider.getFilterProvider();
        if (filters == null) {
            throw new JsonMappingException("Can not resolve PropertyFilter with id '" + filterId + "'; no FilterProvider configured");
        }
        final PropertyFilter filter = filters.findPropertyFilter(filterId, valueToFilter);
        return filter;
    }
}
