// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.commons.message.kafka.kryo;

import kafka.utils.VerifiableProperties;
import com.alu.motive.hal.commons.dto.TR69DTO;

public class TR69MessageKryoEncoder extends KryoEncoder<TR69DTO>
{
    public TR69MessageKryoEncoder(final VerifiableProperties props) {
        super(props);
    }
    
    public TR69MessageKryoEncoder() {
    }
    
    @Override
    public byte[] toBytes(final TR69DTO arg0) {
        return super.toBytes(arg0);
    }
}
