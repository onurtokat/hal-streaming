// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.commons.message.kafka.kryo;

import java.lang.reflect.Type;
import java.lang.reflect.ParameterizedType;
import kafka.utils.VerifiableProperties;
import kafka.serializer.Decoder;
import com.esotericsoftware.kryo.Kryo;

public abstract class KryoDecoder<T> extends Kryo implements Decoder<T>
{
    public KryoDecoder(final VerifiableProperties props) {
    }
    
    public KryoDecoder() {
        final Type type = this.getClass().getGenericSuperclass();
        final Type[] trueType = ((ParameterizedType)type).getActualTypeArguments();
        super.register((Class)trueType[0]);
    }
}
