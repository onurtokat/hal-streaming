// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

public abstract class TypeSerializerBase extends TypeSerializer
{
    protected final TypeIdResolver _idResolver;
    protected final BeanProperty _property;
    
    protected TypeSerializerBase(final TypeIdResolver idRes, final BeanProperty property) {
        this._idResolver = idRes;
        this._property = property;
    }
    
    @Override
    public abstract JsonTypeInfo.As getTypeInclusion();
    
    @Override
    public String getPropertyName() {
        return null;
    }
    
    @Override
    public TypeIdResolver getTypeIdResolver() {
        return this._idResolver;
    }
    
    protected String idFromValue(final Object value) {
        final String id = this._idResolver.idFromValue(value);
        if (id == null) {
            final String typeDesc = (value == null) ? "NULL" : value.getClass().getName();
            throw new IllegalArgumentException("Can not resolve type id for " + typeDesc + " (using " + this._idResolver.getClass().getName() + ")");
        }
        return id;
    }
    
    protected String idFromValueAndType(final Object value, final Class<?> type) {
        final String id = this._idResolver.idFromValueAndType(value, type);
        if (id == null) {
            final String typeDesc = (value == null) ? "NULL" : value.getClass().getName();
            throw new IllegalArgumentException("Can not resolve type id for " + typeDesc + " (using " + this._idResolver.getClass().getName() + ")");
        }
        return id;
    }
}
