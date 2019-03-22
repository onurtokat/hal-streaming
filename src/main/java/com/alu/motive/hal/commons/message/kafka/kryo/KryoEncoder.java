// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.commons.message.kafka.kryo;

import java.io.OutputStream;
import com.esotericsoftware.kryo.io.Output;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.lang.reflect.ParameterizedType;
import kafka.utils.VerifiableProperties;
import kafka.serializer.Encoder;
import com.esotericsoftware.kryo.Kryo;

public class KryoEncoder<T> extends Kryo implements Encoder<T>
{
    public KryoEncoder(final VerifiableProperties props) {
    }
    
    public KryoEncoder() {
        final Type type = this.getClass().getGenericSuperclass();
        final Type[] trueType = ((ParameterizedType)type).getActualTypeArguments();
        super.register((Class)trueType[0]);
    }
    
    @Override
    public byte[] toBytes(final T arg0) {
        final Output out = new Output(new ByteArrayOutputStream());
        super.writeObject(out, arg0);
        final byte[] res = out.getBuffer();
        out.close();
        return res;
    }
}
