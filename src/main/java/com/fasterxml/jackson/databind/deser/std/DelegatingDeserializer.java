// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import java.util.Collection;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

public abstract class DelegatingDeserializer extends StdDeserializer<Object> implements ContextualDeserializer, ResolvableDeserializer
{
    private static final long serialVersionUID = 1L;
    protected final JsonDeserializer<?> _delegatee;
    
    public DelegatingDeserializer(final JsonDeserializer<?> delegatee) {
        super(_figureType(delegatee));
        this._delegatee = delegatee;
    }
    
    protected abstract JsonDeserializer<?> newDelegatingInstance(final JsonDeserializer<?> p0);
    
    private static Class<?> _figureType(final JsonDeserializer<?> deser) {
        final Class<?> cls = deser.handledType();
        if (cls != null) {
            return cls;
        }
        return Object.class;
    }
    
    @Override
    public void resolve(final DeserializationContext ctxt) throws JsonMappingException {
        if (this._delegatee instanceof ResolvableDeserializer) {
            ((ResolvableDeserializer)this._delegatee).resolve(ctxt);
        }
    }
    
    @Override
    public JsonDeserializer<?> createContextual(final DeserializationContext ctxt, final BeanProperty property) throws JsonMappingException {
        final JsonDeserializer<?> del = ctxt.handleSecondaryContextualization(this._delegatee, property);
        if (del == this._delegatee) {
            return this;
        }
        return this.newDelegatingInstance(del);
    }
    
    @Deprecated
    protected JsonDeserializer<?> _createContextual(final DeserializationContext ctxt, final BeanProperty property, final JsonDeserializer<?> newDelegatee) {
        if (newDelegatee == this._delegatee) {
            return this;
        }
        return this.newDelegatingInstance(newDelegatee);
    }
    
    @Override
    public SettableBeanProperty findBackReference(final String logicalName) {
        return this._delegatee.findBackReference(logicalName);
    }
    
    @Override
    public Object deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return this._delegatee.deserialize(jp, ctxt);
    }
    
    @Override
    public Object deserialize(final JsonParser jp, final DeserializationContext ctxt, final Object intoValue) throws IOException, JsonProcessingException {
        return this._delegatee.deserialize(jp, ctxt, intoValue);
    }
    
    @Override
    public Object deserializeWithType(final JsonParser jp, final DeserializationContext ctxt, final TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
        return this._delegatee.deserializeWithType(jp, ctxt, typeDeserializer);
    }
    
    @Override
    public JsonDeserializer<?> replaceDelegatee(final JsonDeserializer<?> delegatee) {
        if (delegatee == this._delegatee) {
            return this;
        }
        return this.newDelegatingInstance(delegatee);
    }
    
    @Override
    public Object getNullValue() {
        return this._delegatee.getNullValue();
    }
    
    @Override
    public Object getEmptyValue() {
        return this._delegatee.getEmptyValue();
    }
    
    @Override
    public Collection<Object> getKnownPropertyNames() {
        return this._delegatee.getKnownPropertyNames();
    }
    
    @Override
    public boolean isCachable() {
        return this._delegatee.isCachable();
    }
    
    @Override
    public ObjectIdReader getObjectIdReader() {
        return this._delegatee.getObjectIdReader();
    }
    
    @Override
    public JsonDeserializer<?> getDelegatee() {
        return this._delegatee;
    }
}
