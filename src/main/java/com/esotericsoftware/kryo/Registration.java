// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.kryo;

import com.esotericsoftware.kryo.util.Util;
import com.esotericsoftware.minlog.Log;
import org.objenesis.instantiator.ObjectInstantiator;

public class Registration
{
    private final Class type;
    private final int id;
    private Serializer serializer;
    private ObjectInstantiator instantiator;
    
    public Registration(final Class type, final Serializer serializer, final int id) {
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null.");
        }
        if (serializer == null) {
            throw new IllegalArgumentException("serializer cannot be null.");
        }
        this.type = type;
        this.serializer = serializer;
        this.id = id;
    }
    
    public Class getType() {
        return this.type;
    }
    
    public int getId() {
        return this.id;
    }
    
    public Serializer getSerializer() {
        return this.serializer;
    }
    
    public void setSerializer(final Serializer serializer) {
        if (serializer == null) {
            throw new IllegalArgumentException("serializer cannot be null.");
        }
        this.serializer = serializer;
        if (Log.TRACE) {
            Log.trace("kryo", "Update registered serializer: " + this.type.getName() + " (" + serializer.getClass().getName() + ")");
        }
    }
    
    public ObjectInstantiator getInstantiator() {
        return this.instantiator;
    }
    
    public void setInstantiator(final ObjectInstantiator instantiator) {
        if (instantiator == null) {
            throw new IllegalArgumentException("instantiator cannot be null.");
        }
        this.instantiator = instantiator;
    }
    
    public String toString() {
        return "[" + this.id + ", " + Util.className(this.type) + "]";
    }
}
