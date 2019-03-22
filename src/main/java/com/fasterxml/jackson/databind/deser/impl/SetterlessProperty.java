// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import java.lang.annotation.Annotation;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.util.Annotations;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import java.lang.reflect.Method;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;

public final class SetterlessProperty extends SettableBeanProperty
{
    private static final long serialVersionUID = 1L;
    protected final AnnotatedMethod _annotated;
    protected final Method _getter;
    
    public SetterlessProperty(final BeanPropertyDefinition propDef, final JavaType type, final TypeDeserializer typeDeser, final Annotations contextAnnotations, final AnnotatedMethod method) {
        super(propDef, type, typeDeser, contextAnnotations);
        this._annotated = method;
        this._getter = method.getAnnotated();
    }
    
    protected SetterlessProperty(final SetterlessProperty src, final JsonDeserializer<?> deser) {
        super(src, deser);
        this._annotated = src._annotated;
        this._getter = src._getter;
    }
    
    protected SetterlessProperty(final SetterlessProperty src, final PropertyName newName) {
        super(src, newName);
        this._annotated = src._annotated;
        this._getter = src._getter;
    }
    
    @Override
    public SetterlessProperty withName(final PropertyName newName) {
        return new SetterlessProperty(this, newName);
    }
    
    @Override
    public SetterlessProperty withValueDeserializer(final JsonDeserializer<?> deser) {
        return new SetterlessProperty(this, deser);
    }
    
    @Override
    public <A extends Annotation> A getAnnotation(final Class<A> acls) {
        return this._annotated.getAnnotation(acls);
    }
    
    @Override
    public AnnotatedMember getMember() {
        return this._annotated;
    }
    
    @Override
    public final void deserializeAndSet(final JsonParser jp, final DeserializationContext ctxt, final Object instance) throws IOException, JsonProcessingException {
        final JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.VALUE_NULL) {
            return;
        }
        if (this._valueTypeDeserializer != null) {
            throw new JsonMappingException("Problem deserializing 'setterless' property: no way to handle typed deser with setterless yet");
        }
        Object toModify;
        try {
            toModify = this._getter.invoke(instance, new Object[0]);
        }
        catch (Exception e) {
            this._throwAsIOE(e);
            return;
        }
        if (toModify == null) {
            throw new JsonMappingException("Problem deserializing 'setterless' property '" + this.getName() + "': get method returned null");
        }
        this._valueDeserializer.deserialize(jp, ctxt, toModify);
    }
    
    @Override
    public Object deserializeSetAndReturn(final JsonParser jp, final DeserializationContext ctxt, final Object instance) throws IOException, JsonProcessingException {
        this.deserializeAndSet(jp, ctxt, instance);
        return instance;
    }
    
    @Override
    public final void set(final Object instance, final Object value) throws IOException {
        throw new UnsupportedOperationException("Should never call 'set' on setterless property");
    }
    
    @Override
    public Object setAndReturn(final Object instance, final Object value) throws IOException {
        this.set(instance, value);
        return null;
    }
}
