// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.kryo.serializers;

import java.util.ArrayList;
import com.esotericsoftware.kryo.io.Input;
import java.util.Iterator;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.Kryo;
import java.util.Collection;
import com.esotericsoftware.kryo.Serializer;

public class CollectionSerializer extends Serializer<Collection>
{
    private boolean elementsCanBeNull;
    private Serializer serializer;
    private Class elementClass;
    private Class genericType;
    
    public CollectionSerializer() {
        this.elementsCanBeNull = true;
    }
    
    public CollectionSerializer(final Class elementClass, final Serializer serializer) {
        this.elementsCanBeNull = true;
        this.setElementClass(elementClass, serializer);
    }
    
    public CollectionSerializer(final Class elementClass, final Serializer serializer, final boolean elementsCanBeNull) {
        this.elementsCanBeNull = true;
        this.setElementClass(elementClass, serializer);
        this.elementsCanBeNull = elementsCanBeNull;
    }
    
    public void setElementsCanBeNull(final boolean elementsCanBeNull) {
        this.elementsCanBeNull = elementsCanBeNull;
    }
    
    public void setElementClass(final Class elementClass, final Serializer serializer) {
        this.elementClass = elementClass;
        this.serializer = serializer;
    }
    
    public void setGenerics(final Kryo kryo, final Class[] generics) {
        if (kryo.isFinal(generics[0])) {
            this.genericType = generics[0];
        }
    }
    
    public void write(final Kryo kryo, final Output output, final Collection collection) {
        final int length = collection.size();
        output.writeInt(length, true);
        Serializer serializer = this.serializer;
        if (this.genericType != null) {
            if (serializer == null) {
                serializer = kryo.getSerializer(this.genericType);
            }
            this.genericType = null;
        }
        if (serializer != null) {
            if (this.elementsCanBeNull) {
                for (final Object element : collection) {
                    kryo.writeObjectOrNull(output, element, serializer);
                }
            }
            else {
                for (final Object element : collection) {
                    kryo.writeObject(output, element, serializer);
                }
            }
        }
        else {
            for (final Object element : collection) {
                kryo.writeClassAndObject(output, element);
            }
        }
    }
    
    protected Collection create(final Kryo kryo, final Input input, final Class<Collection> type) {
        return kryo.newInstance(type);
    }
    
    public Collection read(final Kryo kryo, final Input input, final Class<Collection> type) {
        final Collection collection = this.create(kryo, input, type);
        kryo.reference(collection);
        final int length = input.readInt(true);
        if (collection instanceof ArrayList) {
            ((ArrayList)collection).ensureCapacity(length);
        }
        Class elementClass = this.elementClass;
        Serializer serializer = this.serializer;
        if (this.genericType != null) {
            if (serializer == null) {
                elementClass = this.genericType;
                serializer = kryo.getSerializer(this.genericType);
            }
            this.genericType = null;
        }
        if (serializer != null) {
            if (this.elementsCanBeNull) {
                for (int i = 0; i < length; ++i) {
                    collection.add(kryo.readObjectOrNull(input, (Class<Object>)elementClass, serializer));
                }
            }
            else {
                for (int i = 0; i < length; ++i) {
                    collection.add(kryo.readObject(input, (Class<Object>)elementClass, serializer));
                }
            }
        }
        else {
            for (int i = 0; i < length; ++i) {
                collection.add(kryo.readClassAndObject(input));
            }
        }
        return collection;
    }
    
    protected Collection createCopy(final Kryo kryo, final Collection original) {
        return kryo.newInstance(original.getClass());
    }
    
    public Collection copy(final Kryo kryo, final Collection original) {
        final Collection copy = this.createCopy(kryo, original);
        kryo.reference(copy);
        for (final Object element : original) {
            copy.add(kryo.copy(element));
        }
        return copy;
    }
}
