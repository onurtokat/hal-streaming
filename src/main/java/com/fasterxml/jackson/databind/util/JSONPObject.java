// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializable;

public class JSONPObject implements JsonSerializable
{
    protected final String _function;
    protected final Object _value;
    protected final JavaType _serializationType;
    
    public JSONPObject(final String function, final Object value) {
        this(function, value, null);
    }
    
    public JSONPObject(final String function, final Object value, final JavaType asType) {
        this._function = function;
        this._value = value;
        this._serializationType = asType;
    }
    
    @Override
    public void serializeWithType(final JsonGenerator jgen, final SerializerProvider provider, final TypeSerializer typeSer) throws IOException, JsonProcessingException {
        this.serialize(jgen, provider);
    }
    
    @Override
    public void serialize(final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeRaw(this._function);
        jgen.writeRaw('(');
        if (this._value == null) {
            provider.defaultSerializeNull(jgen);
        }
        else if (this._serializationType != null) {
            provider.findTypedValueSerializer(this._serializationType, true, null).serialize(this._value, jgen, provider);
        }
        else {
            final Class<?> cls = this._value.getClass();
            provider.findTypedValueSerializer(cls, true, null).serialize(this._value, jgen, provider);
        }
        jgen.writeRaw(')');
    }
    
    public String getFunction() {
        return this._function;
    }
    
    public Object getValue() {
        return this._value;
    }
    
    public JavaType getSerializationType() {
        return this._serializationType;
    }
}
