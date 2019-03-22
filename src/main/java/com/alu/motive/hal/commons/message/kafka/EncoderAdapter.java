// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.commons.message.kafka;

import java.util.Map;
import kafka.serializer.Encoder;
import org.apache.kafka.common.serialization.Serializer;

public class EncoderAdapter<T> implements Serializer<T>
{
    private Encoder<T> encoder;
    
    public EncoderAdapter() {
    }
    
    public EncoderAdapter(final Encoder<T> encoder) {
        this.encoder = encoder;
    }
    
    public Encoder<T> getEncoder() {
        return this.encoder;
    }
    
    @Override
    public void configure(final Map<String, ?> configs, final boolean isKey) {
    }
    
    @Override
    public byte[] serialize(final String topic, final T data) {
        return this.encoder.toBytes(data);
    }
    
    @Override
    public void close() {
    }
}
