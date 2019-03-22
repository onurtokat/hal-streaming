// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.core.util.JsonParserSequence;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.JavaType;
import java.io.Serializable;

public class AsWrapperTypeDeserializer extends TypeDeserializerBase implements Serializable
{
    private static final long serialVersionUID = 5345570420394408290L;
    
    public AsWrapperTypeDeserializer(final JavaType bt, final TypeIdResolver idRes, final String typePropertyName, final boolean typeIdVisible, final Class<?> defaultImpl) {
        super(bt, idRes, typePropertyName, typeIdVisible, null);
    }
    
    protected AsWrapperTypeDeserializer(final AsWrapperTypeDeserializer src, final BeanProperty property) {
        super(src, property);
    }
    
    @Override
    public TypeDeserializer forProperty(final BeanProperty prop) {
        return (prop == this._property) ? this : new AsWrapperTypeDeserializer(this, prop);
    }
    
    @Override
    public JsonTypeInfo.As getTypeInclusion() {
        return JsonTypeInfo.As.WRAPPER_OBJECT;
    }
    
    @Override
    public Object deserializeTypedFromObject(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
        return this._deserialize(jp, ctxt);
    }
    
    @Override
    public Object deserializeTypedFromArray(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
        return this._deserialize(jp, ctxt);
    }
    
    @Override
    public Object deserializeTypedFromScalar(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
        return this._deserialize(jp, ctxt);
    }
    
    @Override
    public Object deserializeTypedFromAny(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
        return this._deserialize(jp, ctxt);
    }
    
    private final Object _deserialize(JsonParser jp, final DeserializationContext ctxt) throws IOException {
        if (jp.canReadTypeId()) {
            final Object typeId = jp.getTypeId();
            if (typeId != null) {
                return this._deserializeWithNativeTypeId(jp, ctxt, typeId);
            }
        }
        if (jp.getCurrentToken() != JsonToken.START_OBJECT) {
            throw ctxt.wrongTokenException(jp, JsonToken.START_OBJECT, "need JSON Object to contain As.WRAPPER_OBJECT type information for class " + this.baseTypeName());
        }
        if (jp.nextToken() != JsonToken.FIELD_NAME) {
            throw ctxt.wrongTokenException(jp, JsonToken.FIELD_NAME, "need JSON String that contains type id (for subtype of " + this.baseTypeName() + ")");
        }
        final String typeId2 = jp.getText();
        final JsonDeserializer<Object> deser = this._findDeserializer(ctxt, typeId2);
        jp.nextToken();
        if (this._typeIdVisible && jp.getCurrentToken() == JsonToken.START_OBJECT) {
            final TokenBuffer tb = new TokenBuffer(null, false);
            tb.writeStartObject();
            tb.writeFieldName(this._typePropertyName);
            tb.writeString(typeId2);
            jp = JsonParserSequence.createFlattened(tb.asParser(jp), jp);
            jp.nextToken();
        }
        final Object value = deser.deserialize(jp, ctxt);
        if (jp.nextToken() != JsonToken.END_OBJECT) {
            throw ctxt.wrongTokenException(jp, JsonToken.END_OBJECT, "expected closing END_OBJECT after type information and deserialized value");
        }
        return value;
    }
}
