// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.lang.annotation.Annotation;
import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.JsonNode;
import java.lang.reflect.Type;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.core.JsonGenerationException;
import java.io.IOException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.atomic.AtomicReference;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.JsonSerializable;

@JacksonStdImpl
public class SerializableSerializer extends StdSerializer<JsonSerializable>
{
    public static final SerializableSerializer instance;
    private static final AtomicReference<ObjectMapper> _mapperReference;
    
    protected SerializableSerializer() {
        super(JsonSerializable.class);
    }
    
    @Override
    public void serialize(final JsonSerializable value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonGenerationException {
        value.serialize(jgen, provider);
    }
    
    @Override
    public final void serializeWithType(final JsonSerializable value, final JsonGenerator jgen, final SerializerProvider provider, final TypeSerializer typeSer) throws IOException, JsonGenerationException {
        value.serializeWithType(jgen, provider, typeSer);
    }
    
    @Override
    public JsonNode getSchema(final SerializerProvider provider, final Type typeHint) throws JsonMappingException {
        final ObjectNode objectNode = this.createObjectNode();
        String schemaType = "any";
        String objectProperties = null;
        String itemDefinition = null;
        if (typeHint != null) {
            final Class<?> rawClass = TypeFactory.rawClass(typeHint);
            if (rawClass.isAnnotationPresent(JsonSerializableSchema.class)) {
                final JsonSerializableSchema schemaInfo = rawClass.getAnnotation(JsonSerializableSchema.class);
                schemaType = schemaInfo.schemaType();
                if (!"##irrelevant".equals(schemaInfo.schemaObjectPropertiesDefinition())) {
                    objectProperties = schemaInfo.schemaObjectPropertiesDefinition();
                }
                if (!"##irrelevant".equals(schemaInfo.schemaItemDefinition())) {
                    itemDefinition = schemaInfo.schemaItemDefinition();
                }
            }
        }
        objectNode.put("type", schemaType);
        if (objectProperties != null) {
            try {
                objectNode.put("properties", _getObjectMapper().readTree(objectProperties));
            }
            catch (IOException e) {
                throw new JsonMappingException("Failed to parse @JsonSerializableSchema.schemaObjectPropertiesDefinition value");
            }
        }
        if (itemDefinition != null) {
            try {
                objectNode.put("items", _getObjectMapper().readTree(itemDefinition));
            }
            catch (IOException e) {
                throw new JsonMappingException("Failed to parse @JsonSerializableSchema.schemaItemDefinition value");
            }
        }
        return objectNode;
    }
    
    private static final synchronized ObjectMapper _getObjectMapper() {
        ObjectMapper mapper = SerializableSerializer._mapperReference.get();
        if (mapper == null) {
            mapper = new ObjectMapper();
            SerializableSerializer._mapperReference.set(mapper);
        }
        return mapper;
    }
    
    @Override
    public void acceptJsonFormatVisitor(final JsonFormatVisitorWrapper visitor, final JavaType typeHint) throws JsonMappingException {
        visitor.expectAnyFormat(typeHint);
    }
    
    static {
        instance = new SerializableSerializer();
        _mapperReference = new AtomicReference<ObjectMapper>();
    }
}
