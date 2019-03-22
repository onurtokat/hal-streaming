// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.JavaType;

public abstract class ContainerDeserializerBase<T> extends StdDeserializer<T>
{
    protected ContainerDeserializerBase(final JavaType selfType) {
        super(selfType);
    }
    
    protected ContainerDeserializerBase(final Class<?> selfType) {
        super(selfType);
    }
    
    @Override
    public SettableBeanProperty findBackReference(final String refName) {
        final JsonDeserializer<Object> valueDeser = this.getContentDeserializer();
        if (valueDeser == null) {
            throw new IllegalArgumentException("Can not handle managed/back reference '" + refName + "': type: container deserializer of type " + this.getClass().getName() + " returned null for 'getContentDeserializer()'");
        }
        return valueDeser.findBackReference(refName);
    }
    
    public abstract JavaType getContentType();
    
    public abstract JsonDeserializer<Object> getContentDeserializer();
}
