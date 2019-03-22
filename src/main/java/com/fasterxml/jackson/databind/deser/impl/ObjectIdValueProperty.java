// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.deser.impl;

import java.io.IOException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import java.lang.annotation.Annotation;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;

public final class ObjectIdValueProperty extends SettableBeanProperty
{
    private static final long serialVersionUID = 1L;
    protected final ObjectIdReader _objectIdReader;
    
    public ObjectIdValueProperty(final ObjectIdReader objectIdReader) {
        this(objectIdReader, PropertyMetadata.STD_REQUIRED);
    }
    
    public ObjectIdValueProperty(final ObjectIdReader objectIdReader, final PropertyMetadata metadata) {
        super(objectIdReader.propertyName, objectIdReader.getIdType(), metadata, objectIdReader.getDeserializer());
        this._objectIdReader = objectIdReader;
    }
    
    protected ObjectIdValueProperty(final ObjectIdValueProperty src, final JsonDeserializer<?> deser) {
        super(src, deser);
        this._objectIdReader = src._objectIdReader;
    }
    
    protected ObjectIdValueProperty(final ObjectIdValueProperty src, final PropertyName newName) {
        super(src, newName);
        this._objectIdReader = src._objectIdReader;
    }
    
    protected ObjectIdValueProperty(final ObjectIdValueProperty src, final String newName) {
        this(src, new PropertyName(newName));
    }
    
    @Override
    public ObjectIdValueProperty withName(final PropertyName newName) {
        return new ObjectIdValueProperty(this, newName);
    }
    
    @Override
    public ObjectIdValueProperty withValueDeserializer(final JsonDeserializer<?> deser) {
        return new ObjectIdValueProperty(this, deser);
    }
    
    @Override
    public <A extends Annotation> A getAnnotation(final Class<A> acls) {
        return null;
    }
    
    @Override
    public AnnotatedMember getMember() {
        return null;
    }
    
    @Override
    public void deserializeAndSet(final JsonParser jp, final DeserializationContext ctxt, final Object instance) throws IOException {
        this.deserializeSetAndReturn(jp, ctxt, instance);
    }
    
    @Override
    public Object deserializeSetAndReturn(final JsonParser jp, final DeserializationContext ctxt, final Object instance) throws IOException {
        final Object id = this._valueDeserializer.deserialize(jp, ctxt);
        final ReadableObjectId roid = ctxt.findObjectId(id, this._objectIdReader.generator, this._objectIdReader.resolver);
        roid.bindItem(instance);
        final SettableBeanProperty idProp = this._objectIdReader.idProperty;
        if (idProp != null) {
            return idProp.setAndReturn(instance, id);
        }
        return instance;
    }
    
    @Override
    public void set(final Object instance, final Object value) throws IOException {
        this.setAndReturn(instance, value);
    }
    
    @Override
    public Object setAndReturn(final Object instance, final Object value) throws IOException {
        final SettableBeanProperty idProp = this._objectIdReader.idProperty;
        if (idProp == null) {
            throw new UnsupportedOperationException("Should not call set() on ObjectIdProperty that has no SettableBeanProperty");
        }
        return idProp.setAndReturn(instance, value);
    }
}
