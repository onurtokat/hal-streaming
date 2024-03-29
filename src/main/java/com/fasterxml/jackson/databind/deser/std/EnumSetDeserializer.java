// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import java.util.EnumSet;

public class EnumSetDeserializer extends StdDeserializer<EnumSet<?>> implements ContextualDeserializer
{
    private static final long serialVersionUID = 3479455075597887177L;
    protected final JavaType _enumType;
    protected final Class<Enum> _enumClass;
    protected JsonDeserializer<Enum<?>> _enumDeserializer;
    
    public EnumSetDeserializer(final JavaType enumType, final JsonDeserializer<?> deser) {
        super(EnumSet.class);
        this._enumType = enumType;
        this._enumClass = (Class<Enum>)enumType.getRawClass();
        this._enumDeserializer = (JsonDeserializer<Enum<?>>)deser;
    }
    
    public EnumSetDeserializer withDeserializer(final JsonDeserializer<?> deser) {
        if (this._enumDeserializer == deser) {
            return this;
        }
        return new EnumSetDeserializer(this._enumType, deser);
    }
    
    @Override
    public boolean isCachable() {
        return true;
    }
    
    @Override
    public JsonDeserializer<?> createContextual(final DeserializationContext ctxt, final BeanProperty property) throws JsonMappingException {
        JsonDeserializer<?> deser = this._enumDeserializer;
        if (deser == null) {
            deser = ctxt.findContextualValueDeserializer(this._enumType, property);
        }
        else {
            deser = ctxt.handleSecondaryContextualization(deser, property);
        }
        return this.withDeserializer(deser);
    }
    
    @Override
    public EnumSet<?> deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
        if (!jp.isExpectedStartArrayToken()) {
            throw ctxt.mappingException(EnumSet.class);
        }
        final EnumSet result = this.constructSet();
        try {
            JsonToken t;
            while ((t = jp.nextToken()) != JsonToken.END_ARRAY) {
                if (t == JsonToken.VALUE_NULL) {
                    throw ctxt.mappingException(this._enumClass);
                }
                final Enum<?> value = this._enumDeserializer.deserialize(jp, ctxt);
                if (value == null) {
                    continue;
                }
                result.add(value);
            }
        }
        catch (Exception e) {
            throw JsonMappingException.wrapWithPath(e, result, result.size());
        }
        return (EnumSet<?>)result;
    }
    
    @Override
    public Object deserializeWithType(final JsonParser jp, final DeserializationContext ctxt, final TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
        return typeDeserializer.deserializeTypedFromArray(jp, ctxt);
    }
    
    private EnumSet constructSet() {
        return EnumSet.noneOf(this._enumClass);
    }
}
