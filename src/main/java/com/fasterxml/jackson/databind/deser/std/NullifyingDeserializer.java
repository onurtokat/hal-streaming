// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.core.JsonParser;

public class NullifyingDeserializer extends StdDeserializer<Object>
{
    private static final long serialVersionUID = 1L;
    public static final NullifyingDeserializer instance;
    
    public NullifyingDeserializer() {
        super(Object.class);
    }
    
    @Override
    public Object deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
        jp.skipChildren();
        return null;
    }
    
    @Override
    public Object deserializeWithType(final JsonParser jp, final DeserializationContext ctxt, final TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
        final JsonToken t = jp.getCurrentToken();
        switch (t) {
            case START_ARRAY:
            case START_OBJECT:
            case FIELD_NAME: {
                return typeDeserializer.deserializeTypedFromAny(jp, ctxt);
            }
            default: {
                return null;
            }
        }
    }
    
    static {
        instance = new NullifyingDeserializer();
    }
}
