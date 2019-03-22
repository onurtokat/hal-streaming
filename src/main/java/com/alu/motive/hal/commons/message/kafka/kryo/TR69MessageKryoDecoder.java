// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.commons.message.kafka.kryo;

import org.slf4j.LoggerFactory;
import java.io.InputStream;
import com.esotericsoftware.kryo.io.Input;
import java.io.ByteArrayInputStream;
import kafka.utils.VerifiableProperties;
import org.slf4j.Logger;
import com.alu.motive.hal.commons.dto.TR69DTO;

public class TR69MessageKryoDecoder extends KryoDecoder<TR69DTO>
{
    private static Logger LOG;
    
    public TR69MessageKryoDecoder(final VerifiableProperties props) {
        super(props);
    }
    
    public TR69MessageKryoDecoder() {
    }
    
    @Override
    public TR69DTO fromBytes(final byte[] arg0) {
        TR69MessageKryoDecoder.LOG.debug("fromBytes");
        final Input input = new Input(new ByteArrayInputStream(arg0));
        return this.readObject(input, TR69DTO.class);
    }
    
    static {
        TR69MessageKryoDecoder.LOG = LoggerFactory.getLogger(TR69MessageKryoDecoder.class);
    }
}
