// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.commons.message.kafka;

import com.alu.motive.hal.commons.message.util.JavaSerializationUtils;
import kafka.utils.VerifiableProperties;
import com.alu.motive.hal.commons.dto.TR69DTO;
import kafka.serializer.Encoder;

public class TR69MessageEncoder implements Encoder<TR69DTO>
{
    public TR69MessageEncoder(final VerifiableProperties verifiableProperties) {
    }
    
    public TR69MessageEncoder() {
    }
    
    @Override
    public byte[] toBytes(final TR69DTO tr69Message) {
        return JavaSerializationUtils.serialize(tr69Message);
    }
}
