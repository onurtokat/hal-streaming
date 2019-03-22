// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.commons.message.kafka;

import kafka.serializer.Encoder;
import com.alu.motive.hal.commons.dto.TR69DTO;

public class TR69MessageSerializer extends EncoderAdapter<TR69DTO>
{
    public TR69MessageSerializer() {
        super(new TR69MessageEncoder());
    }
    
    public TR69MessageSerializer(final Encoder<TR69DTO> encoder) {
        super(encoder);
    }
}
