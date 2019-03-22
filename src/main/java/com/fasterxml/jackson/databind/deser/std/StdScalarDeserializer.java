// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.deser.std;

import java.io.IOException;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JavaType;

public abstract class StdScalarDeserializer<T> extends StdDeserializer<T>
{
    private static final long serialVersionUID = 1L;
    
    protected StdScalarDeserializer(final Class<?> vc) {
        super(vc);
    }
    
    protected StdScalarDeserializer(final JavaType valueType) {
        super(valueType);
    }
    
    @Override
    public Object deserializeWithType(final JsonParser jp, final DeserializationContext ctxt, final TypeDeserializer typeDeserializer) throws IOException {
        return typeDeserializer.deserializeTypedFromScalar(jp, ctxt);
    }
}
