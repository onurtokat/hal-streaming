// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.io.Input;
import java.util.Iterator;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.Kryo;
import java.util.Map;
import com.esotericsoftware.kryo.Serializer;

public class MapSerializer extends Serializer<Map>
{
    private Class keyClass;
    private Class valueClass;
    private Serializer keySerializer;
    private Serializer valueSerializer;
    private boolean keysCanBeNull;
    private boolean valuesCanBeNull;
    private Class keyGenericType;
    private Class valueGenericType;
    
    public MapSerializer() {
        this.keysCanBeNull = true;
        this.valuesCanBeNull = true;
    }
    
    public void setKeysCanBeNull(final boolean keysCanBeNull) {
        this.keysCanBeNull = keysCanBeNull;
    }
    
    public void setKeyClass(final Class keyClass, final Serializer keySerializer) {
        this.keyClass = keyClass;
        this.keySerializer = keySerializer;
    }
    
    public void setValueClass(final Class valueClass, final Serializer valueSerializer) {
        this.valueClass = valueClass;
        this.valueSerializer = valueSerializer;
    }
    
    public void setValuesCanBeNull(final boolean valuesCanBeNull) {
        this.valuesCanBeNull = valuesCanBeNull;
    }
    
    public void setGenerics(final Kryo kryo, final Class[] generics) {
        if (generics[0] != null && kryo.isFinal(generics[0])) {
            this.keyGenericType = generics[0];
        }
        if (generics[1] != null && kryo.isFinal(generics[1])) {
            this.valueGenericType = generics[1];
        }
    }
    
    public void write(final Kryo kryo, final Output output, final Map map) {
        final int length = map.size();
        output.writeInt(length, true);
        Serializer keySerializer = this.keySerializer;
        if (this.keyGenericType != null) {
            if (keySerializer == null) {
                keySerializer = kryo.getSerializer(this.keyGenericType);
            }
            this.keyGenericType = null;
        }
        Serializer valueSerializer = this.valueSerializer;
        if (this.valueGenericType != null) {
            if (valueSerializer == null) {
                valueSerializer = kryo.getSerializer(this.valueGenericType);
            }
            this.valueGenericType = null;
        }
        for (final Map.Entry entry : map.entrySet()) {
            if (keySerializer != null) {
                if (this.keysCanBeNull) {
                    kryo.writeObjectOrNull(output, entry.getKey(), keySerializer);
                }
                else {
                    kryo.writeObject(output, entry.getKey(), keySerializer);
                }
            }
            else {
                kryo.writeClassAndObject(output, entry.getKey());
            }
            if (valueSerializer != null) {
                if (this.valuesCanBeNull) {
                    kryo.writeObjectOrNull(output, entry.getValue(), valueSerializer);
                }
                else {
                    kryo.writeObject(output, entry.getValue(), valueSerializer);
                }
            }
            else {
                kryo.writeClassAndObject(output, entry.getValue());
            }
        }
    }
    
    protected Map create(final Kryo kryo, final Input input, final Class<Map> type) {
        return kryo.newInstance(type);
    }
    
    public Map read(final Kryo kryo, final Input input, final Class<Map> type) {
        final Map map = this.create(kryo, input, type);
        final int length = input.readInt(true);
        Class keyClass = this.keyClass;
        Class valueClass = this.valueClass;
        Serializer keySerializer = this.keySerializer;
        if (this.keyGenericType != null) {
            keyClass = this.keyGenericType;
            if (keySerializer == null) {
                keySerializer = kryo.getSerializer(keyClass);
            }
            this.keyGenericType = null;
        }
        Serializer valueSerializer = this.valueSerializer;
        if (this.valueGenericType != null) {
            valueClass = this.valueGenericType;
            if (valueSerializer == null) {
                valueSerializer = kryo.getSerializer(valueClass);
            }
            this.valueGenericType = null;
        }
        kryo.reference(map);
        for (int i = 0; i < length; ++i) {
            Object key;
            if (keySerializer != null) {
                if (this.keysCanBeNull) {
                    key = kryo.readObjectOrNull(input, (Class<Object>)keyClass, keySerializer);
                }
                else {
                    key = kryo.readObject(input, (Class<Object>)keyClass, keySerializer);
                }
            }
            else {
                key = kryo.readClassAndObject(input);
            }
            Object value;
            if (valueSerializer != null) {
                if (this.valuesCanBeNull) {
                    value = kryo.readObjectOrNull(input, (Class<Object>)valueClass, valueSerializer);
                }
                else {
                    value = kryo.readObject(input, (Class<Object>)valueClass, valueSerializer);
                }
            }
            else {
                value = kryo.readClassAndObject(input);
            }
            map.put(key, value);
        }
        return map;
    }
    
    protected Map createCopy(final Kryo kryo, final Map original) {
        return kryo.newInstance(original.getClass());
    }
    
    public Map copy(final Kryo kryo, final Map original) {
        final Map copy = this.createCopy(kryo, original);
        for (final Map.Entry entry : original.entrySet()) {
            copy.put(kryo.copy(entry.getKey()), kryo.copy(entry.getValue()));
        }
        return copy;
    }
}
