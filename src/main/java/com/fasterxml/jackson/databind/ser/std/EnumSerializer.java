// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.Set;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonIntegerFormatVisitor;
import java.util.LinkedHashSet;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import java.util.Iterator;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.databind.JsonNode;
import java.lang.reflect.Type;
import com.fasterxml.jackson.core.JsonGenerationException;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.util.EnumValues;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

@JacksonStdImpl
public class EnumSerializer extends StdScalarSerializer<Enum<?>> implements ContextualSerializer
{
    protected final EnumValues _values;
    protected final Boolean _serializeAsIndex;
    
    public EnumSerializer(final EnumValues v) {
        this(v, null);
    }
    
    public EnumSerializer(final EnumValues v, final Boolean serializeAsIndex) {
        super(Enum.class, false);
        this._values = v;
        this._serializeAsIndex = serializeAsIndex;
    }
    
    public static EnumSerializer construct(final Class<Enum<?>> enumClass, final SerializationConfig config, final BeanDescription beanDesc, final JsonFormat.Value format) {
        final EnumValues v = EnumValues.construct(config, enumClass);
        final Boolean serializeAsIndex = _isShapeWrittenUsingIndex(enumClass, format, true);
        return new EnumSerializer(v, serializeAsIndex);
    }
    
    @Override
    public JsonSerializer<?> createContextual(final SerializerProvider prov, final BeanProperty property) throws JsonMappingException {
        if (property != null) {
            final JsonFormat.Value format = prov.getAnnotationIntrospector().findFormat(property.getMember());
            if (format != null) {
                final Boolean serializeAsIndex = _isShapeWrittenUsingIndex(property.getType().getRawClass(), format, false);
                if (serializeAsIndex != this._serializeAsIndex) {
                    return new EnumSerializer(this._values, serializeAsIndex);
                }
            }
        }
        return this;
    }
    
    public EnumValues getEnumValues() {
        return this._values;
    }
    
    @Override
    public final void serialize(final Enum<?> en, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonGenerationException {
        if (this._serializeAsIndex(provider)) {
            jgen.writeNumber(en.ordinal());
            return;
        }
        jgen.writeString(this._values.serializedValueFor(en));
    }
    
    @Override
    public JsonNode getSchema(final SerializerProvider provider, final Type typeHint) {
        if (this._serializeAsIndex(provider)) {
            return this.createSchemaNode("integer", true);
        }
        final ObjectNode objectNode = this.createSchemaNode("string", true);
        if (typeHint != null) {
            final JavaType type = provider.constructType(typeHint);
            if (type.isEnumType()) {
                final ArrayNode enumNode = objectNode.putArray("enum");
                for (final SerializableString value : this._values.values()) {
                    enumNode.add(value.getValue());
                }
            }
        }
        return objectNode;
    }
    
    @Override
    public void acceptJsonFormatVisitor(final JsonFormatVisitorWrapper visitor, final JavaType typeHint) throws JsonMappingException {
        if (this._serializeAsIndex(visitor.getProvider())) {
            final JsonIntegerFormatVisitor v2 = visitor.expectIntegerFormat(typeHint);
            if (v2 != null) {
                v2.numberType(JsonParser.NumberType.INT);
            }
        }
        else {
            final JsonStringFormatVisitor stringVisitor = visitor.expectStringFormat(typeHint);
            if (typeHint != null && stringVisitor != null && typeHint.isEnumType()) {
                final Set<String> enums = new LinkedHashSet<String>();
                for (final SerializableString value : this._values.values()) {
                    enums.add(value.getValue());
                }
                stringVisitor.enumTypes(enums);
            }
        }
    }
    
    protected final boolean _serializeAsIndex(final SerializerProvider provider) {
        if (this._serializeAsIndex != null) {
            return this._serializeAsIndex;
        }
        return provider.isEnabled(SerializationFeature.WRITE_ENUMS_USING_INDEX);
    }
    
    protected static Boolean _isShapeWrittenUsingIndex(final Class<?> enumClass, final JsonFormat.Value format, final boolean fromClass) {
        final JsonFormat.Shape shape = (format == null) ? null : format.getShape();
        if (shape == null) {
            return null;
        }
        if (shape == JsonFormat.Shape.ANY || shape == JsonFormat.Shape.SCALAR) {
            return null;
        }
        if (shape == JsonFormat.Shape.STRING) {
            return Boolean.FALSE;
        }
        if (shape.isNumeric()) {
            return Boolean.TRUE;
        }
        throw new IllegalArgumentException("Unsupported serialization shape (" + shape + ") for Enum " + enumClass.getName() + ", not supported as " + (fromClass ? "class" : "property") + " annotation");
    }
}
