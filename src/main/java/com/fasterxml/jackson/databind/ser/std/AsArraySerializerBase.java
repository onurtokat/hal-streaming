// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import com.fasterxml.jackson.databind.jsonschema.SchemaAware;
import com.fasterxml.jackson.databind.JsonNode;
import java.lang.reflect.Type;
import com.fasterxml.jackson.core.JsonGenerationException;
import java.io.IOException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;

public abstract class AsArraySerializerBase<T> extends ContainerSerializer<T> implements ContextualSerializer
{
    protected final boolean _staticTyping;
    protected final JavaType _elementType;
    protected final TypeSerializer _valueTypeSerializer;
    protected final JsonSerializer<Object> _elementSerializer;
    protected final BeanProperty _property;
    protected PropertySerializerMap _dynamicSerializers;
    
    protected AsArraySerializerBase(final Class<?> cls, final JavaType et, final boolean staticTyping, final TypeSerializer vts, final BeanProperty property, final JsonSerializer<Object> elementSerializer) {
        super(cls, false);
        this._elementType = et;
        this._staticTyping = (staticTyping || (et != null && et.isFinal()));
        this._valueTypeSerializer = vts;
        this._property = property;
        this._elementSerializer = elementSerializer;
        this._dynamicSerializers = PropertySerializerMap.emptyMap();
    }
    
    protected AsArraySerializerBase(final AsArraySerializerBase<?> src, final BeanProperty property, final TypeSerializer vts, final JsonSerializer<?> elementSerializer) {
        super(src);
        this._elementType = src._elementType;
        this._staticTyping = src._staticTyping;
        this._valueTypeSerializer = vts;
        this._property = property;
        this._elementSerializer = (JsonSerializer<Object>)elementSerializer;
        this._dynamicSerializers = src._dynamicSerializers;
    }
    
    public abstract AsArraySerializerBase<T> withResolved(final BeanProperty p0, final TypeSerializer p1, final JsonSerializer<?> p2);
    
    @Override
    public JsonSerializer<?> createContextual(final SerializerProvider provider, final BeanProperty property) throws JsonMappingException {
        TypeSerializer typeSer = this._valueTypeSerializer;
        if (typeSer != null) {
            typeSer = typeSer.forProperty(property);
        }
        JsonSerializer<?> ser = null;
        if (property != null) {
            final AnnotatedMember m = property.getMember();
            if (m != null) {
                final Object serDef = provider.getAnnotationIntrospector().findContentSerializer(m);
                if (serDef != null) {
                    ser = provider.serializerInstance(m, serDef);
                }
            }
        }
        if (ser == null) {
            ser = this._elementSerializer;
        }
        ser = this.findConvertingContentSerializer(provider, property, ser);
        if (ser == null) {
            if (this._elementType != null && ((this._staticTyping && this._elementType.getRawClass() != Object.class) || this.hasContentTypeAnnotation(provider, property))) {
                ser = provider.findValueSerializer(this._elementType, property);
            }
        }
        else {
            ser = provider.handleSecondaryContextualization(ser, property);
        }
        if (ser != this._elementSerializer || property != this._property || this._valueTypeSerializer != typeSer) {
            return this.withResolved(property, typeSer, ser);
        }
        return this;
    }
    
    @Override
    public JavaType getContentType() {
        return this._elementType;
    }
    
    @Override
    public JsonSerializer<?> getContentSerializer() {
        return this._elementSerializer;
    }
    
    @Override
    public final void serialize(final T value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonGenerationException {
        if (provider.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED) && this.hasSingleElement(value)) {
            this.serializeContents(value, jgen, provider);
            return;
        }
        jgen.writeStartArray();
        this.serializeContents(value, jgen, provider);
        jgen.writeEndArray();
    }
    
    @Override
    public void serializeWithType(final T value, final JsonGenerator jgen, final SerializerProvider provider, final TypeSerializer typeSer) throws IOException, JsonGenerationException {
        typeSer.writeTypePrefixForArray(value, jgen);
        this.serializeContents(value, jgen, provider);
        typeSer.writeTypeSuffixForArray(value, jgen);
    }
    
    protected abstract void serializeContents(final T p0, final JsonGenerator p1, final SerializerProvider p2) throws IOException, JsonGenerationException;
    
    @Override
    public JsonNode getSchema(final SerializerProvider provider, final Type typeHint) throws JsonMappingException {
        final ObjectNode o = this.createSchemaNode("array", true);
        final JavaType contentType = this._elementType;
        if (contentType != null) {
            JsonNode schemaNode = null;
            if (contentType.getRawClass() != Object.class) {
                final JsonSerializer<Object> ser = provider.findValueSerializer(contentType, this._property);
                if (ser instanceof SchemaAware) {
                    schemaNode = ((SchemaAware)ser).getSchema(provider, null);
                }
            }
            if (schemaNode == null) {
                schemaNode = JsonSchema.getDefaultSchemaNode();
            }
            o.put("items", schemaNode);
        }
        return o;
    }
    
    @Override
    public void acceptJsonFormatVisitor(final JsonFormatVisitorWrapper visitor, final JavaType typeHint) throws JsonMappingException {
        final JsonArrayFormatVisitor arrayVisitor = (visitor == null) ? null : visitor.expectArrayFormat(typeHint);
        if (arrayVisitor != null) {
            JsonSerializer<?> valueSer = this._elementSerializer;
            if (valueSer == null) {
                valueSer = visitor.getProvider().findValueSerializer(this._elementType, this._property);
            }
            arrayVisitor.itemsFormat(valueSer, this._elementType);
        }
    }
    
    protected final JsonSerializer<Object> _findAndAddDynamic(final PropertySerializerMap map, final Class<?> type, final SerializerProvider provider) throws JsonMappingException {
        final PropertySerializerMap.SerializerAndMapResult result = map.findAndAddSecondarySerializer(type, provider, this._property);
        if (map != result.map) {
            this._dynamicSerializers = result.map;
        }
        return result.serializer;
    }
    
    protected final JsonSerializer<Object> _findAndAddDynamic(final PropertySerializerMap map, final JavaType type, final SerializerProvider provider) throws JsonMappingException {
        final PropertySerializerMap.SerializerAndMapResult result = map.findAndAddSecondarySerializer(type, provider, this._property);
        if (map != result.map) {
            this._dynamicSerializers = result.map;
        }
        return result.serializer;
    }
}
