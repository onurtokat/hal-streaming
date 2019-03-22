// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.commons.message.kafka;

import com.alu.motive.hal.commons.message.util.JavaSerializationUtils;
import kafka.utils.VerifiableProperties;
import com.alu.motive.hal.commons.dto.TR69DTO;
import kafka.serializer.Decoder;

public class TR69MessageDecoder implements Decoder<TR69DTO>
{
    public TR69MessageDecoder(final VerifiableProperties verifiableProperties) {
    }
    
    public TR69MessageDecoder() {
    }
    
    @Override
    public TR69DTO fromBytes(final byte[] bytes) {
        return (TR69DTO)JavaSerializationUtils.deserialize(bytes);
    }
}
