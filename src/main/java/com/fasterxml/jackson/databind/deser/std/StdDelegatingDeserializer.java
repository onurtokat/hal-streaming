// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.util.Converter;
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

public class StdDelegatingDeserializer<T> extends StdDeserializer<T> implements ContextualDeserializer, ResolvableDeserializer
{
    private static final long serialVersionUID = 1L;
    protected final Converter<Object, T> _converter;
    protected final JavaType _delegateType;
    protected final JsonDeserializer<Object> _delegateDeserializer;
    
    public StdDelegatingDeserializer(final Converter<?, T> converter) {
        super(Object.class);
        this._converter = (Converter<Object, T>)converter;
        this._delegateType = null;
        this._delegateDeserializer = null;
    }
    
    public StdDelegatingDeserializer(final Converter<Object, T> converter, final JavaType delegateType, final JsonDeserializer<?> delegateDeserializer) {
        super(delegateType);
        this._converter = converter;
        this._delegateType = delegateType;
        this._delegateDeserializer = (JsonDeserializer<Object>)delegateDeserializer;
    }
    
    protected StdDelegatingDeserializer<T> withDelegate(final Converter<Object, T> converter, final JavaType delegateType, final JsonDeserializer<?> delegateDeserializer) {
        if (this.getClass() != StdDelegatingDeserializer.class) {
            throw new IllegalStateException("Sub-class " + this.getClass().getName() + " must override 'withDelegate'");
        }
        return new StdDelegatingDeserializer<T>(converter, delegateType, delegateDeserializer);
    }
    
    @Override
    public void resolve(final DeserializationContext ctxt) throws JsonMappingException {
        if (this._delegateDeserializer != null && this._delegateDeserializer instanceof ResolvableDeserializer) {
            ((ResolvableDeserializer)this._delegateDeserializer).resolve(ctxt);
        }
    }
    
    @Override
    public JsonDeserializer<?> createContextual(final DeserializationContext ctxt, final BeanProperty property) throws JsonMappingException {
        if (this._delegateDeserializer == null) {
            final JavaType delegateType = this._converter.getInputType(ctxt.getTypeFactory());
            return this.withDelegate(this._converter, delegateType, ctxt.findContextualValueDeserializer(delegateType, property));
        }
        final JsonDeserializer<?> deser = ctxt.handleSecondaryContextualization(this._delegateDeserializer, property);
        if (deser != this._delegateDeserializer) {
            return this.withDelegate(this._converter, this._delegateType, deser);
        }
        return this;
    }
    
    @Override
    public JsonDeserializer<?> getDelegatee() {
        return this._delegateDeserializer;
    }
    
    @Override
    public Class<?> handledType() {
        return this._delegateDeserializer.handledType();
    }
    
    @Override
    public T deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
        final Object delegateValue = this._delegateDeserializer.deserialize(jp, ctxt);
        if (delegateValue == null) {
            return null;
        }
        return this.convertValue(delegateValue);
    }
    
    @Override
    public Object deserializeWithType(final JsonParser jp, final DeserializationContext ctxt, final TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
        final Object delegateValue = this._delegateDeserializer.deserializeWithType(jp, ctxt, typeDeserializer);
        if (delegateValue == null) {
            return null;
        }
        return this.convertValue(delegateValue);
    }
    
    protected T convertValue(final Object delegateValue) {
        return this._converter.convert(delegateValue);
    }
}
