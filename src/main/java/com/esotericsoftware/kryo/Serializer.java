// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.kryo;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public abstract class Serializer<T>
{
    private boolean acceptsNull;
    private boolean immutable;
    
    public Serializer() {
    }
    
    public Serializer(final boolean acceptsNull) {
        this.acceptsNull = acceptsNull;
    }
    
    public Serializer(final boolean acceptsNull, final boolean immutable) {
        this.acceptsNull = acceptsNull;
        this.immutable = immutable;
    }
    
    public abstract void write(final Kryo p0, final Output p1, final T p2);
    
    public abstract T read(final Kryo p0, final Input p1, final Class<T> p2);
    
    public boolean getAcceptsNull() {
        return this.acceptsNull;
    }
    
    public void setAcceptsNull(final boolean acceptsNull) {
        this.acceptsNull = acceptsNull;
    }
    
    public boolean isImmutable() {
        return this.immutable;
    }
    
    public void setImmutable(final boolean immutable) {
        this.immutable = immutable;
    }
    
    public void setGenerics(final Kryo kryo, final Class[] generics) {
    }
    
    public T copy(final Kryo kryo, final T original) {
        if (this.immutable) {
            return original;
        }
        throw new KryoException("Serializer does not support copy: " + this.getClass().getName());
    }
}
