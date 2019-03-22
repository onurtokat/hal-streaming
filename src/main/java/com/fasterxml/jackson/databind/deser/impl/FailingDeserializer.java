// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class FailingDeserializer extends StdDeserializer<Object>
{
    private static final long serialVersionUID = 1L;
    protected final String _message;
    
    public FailingDeserializer(final String m) {
        super(Object.class);
        this._message = m;
    }
    
    @Override
    public Object deserialize(final JsonParser jp, final DeserializationContext ctxt) throws JsonMappingException {
        throw ctxt.mappingException(this._message);
    }
}
