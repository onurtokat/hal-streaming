// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonToken;
import java.util.Collection;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JavaType;

public class ArrayBlockingQueueDeserializer extends CollectionDeserializer
{
    private static final long serialVersionUID = 1L;
    
    public ArrayBlockingQueueDeserializer(final JavaType collectionType, final JsonDeserializer<Object> valueDeser, final TypeDeserializer valueTypeDeser, final ValueInstantiator valueInstantiator, final JsonDeserializer<Object> delegateDeser) {
        super(collectionType, valueDeser, valueTypeDeser, valueInstantiator, delegateDeser);
    }
    
    protected ArrayBlockingQueueDeserializer(final ArrayBlockingQueueDeserializer src) {
        super(src);
    }
    
    @Override
    protected ArrayBlockingQueueDeserializer withResolved(final JsonDeserializer<?> dd, final JsonDeserializer<?> vd, final TypeDeserializer vtd) {
        if (dd == this._delegateDeserializer && vd == this._valueDeserializer && vtd == this._valueTypeDeserializer) {
            return this;
        }
        return new ArrayBlockingQueueDeserializer(this._collectionType, (JsonDeserializer<Object>)vd, vtd, this._valueInstantiator, (JsonDeserializer<Object>)dd);
    }
    
    @Override
    public Collection<Object> deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
        if (this._delegateDeserializer != null) {
            return (Collection<Object>)this._valueInstantiator.createUsingDelegate(ctxt, this._delegateDeserializer.deserialize(jp, ctxt));
        }
        if (jp.getCurrentToken() == JsonToken.VALUE_STRING) {
            final String str = jp.getText();
            if (str.length() == 0) {
                return (Collection<Object>)this._valueInstantiator.createFromString(ctxt, str);
            }
        }
        return this.deserialize(jp, ctxt, null);
    }
    
    @Override
    public Collection<Object> deserialize(final JsonParser jp, final DeserializationContext ctxt, final Collection<Object> result0) throws IOException {
        if (!jp.isExpectedStartArrayToken()) {
            return this.handleNonArray(jp, ctxt, new ArrayBlockingQueue<Object>(1));
        }
        final ArrayList<Object> tmp = new ArrayList<Object>();
        final JsonDeserializer<Object> valueDes = this._valueDeserializer;
        final TypeDeserializer typeDeser = this._valueTypeDeserializer;
        try {
            JsonToken t;
            while ((t = jp.nextToken()) != JsonToken.END_ARRAY) {
                Object value;
                if (t == JsonToken.VALUE_NULL) {
                    value = valueDes.getNullValue();
                }
                else if (typeDeser == null) {
                    value = valueDes.deserialize(jp, ctxt);
                }
                else {
                    value = valueDes.deserializeWithType(jp, ctxt, typeDeser);
                }
                tmp.add(value);
            }
        }
        catch (Exception e) {
            throw JsonMappingException.wrapWithPath(e, tmp, tmp.size());
        }
        if (result0 != null) {
            result0.addAll(tmp);
            return result0;
        }
        return new ArrayBlockingQueue<Object>(tmp.size(), false, tmp);
    }
    
    @Override
    public Object deserializeWithType(final JsonParser jp, final DeserializationContext ctxt, final TypeDeserializer typeDeserializer) throws IOException {
        return typeDeserializer.deserializeTypedFromArray(jp, ctxt);
    }
}
