// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.commons.message.kafka;

import com.alu.motive.hal.commons.message.util.JavaSerializationUtils;
import java.util.Map;
import com.alu.motive.hal.commons.dto.TR69DTO;
import org.apache.kafka.common.serialization.Deserializer;

public class TR69MessageDeserializer implements Deserializer<TR69DTO>
{
    @Override
    public void configure(final Map<String, ?> map, final boolean b) {
    }
    
    @Override
    public TR69DTO deserialize(final String s, final byte[] bytes) {
        return (TR69DTO)JavaSerializationUtils.deserialize(bytes);
    }
    
    @Override
    public void close() {
    }
}
