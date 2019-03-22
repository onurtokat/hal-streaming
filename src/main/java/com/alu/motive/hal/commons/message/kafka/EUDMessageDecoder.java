// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.commons.message.kafka;

import org.slf4j.LoggerFactory;
import java.io.IOException;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.io.BinaryDecoder;
import java.io.InputStream;
import org.apache.avro.io.DecoderFactory;
import it.unimi.dsi.fastutil.io.FastByteArrayInputStream;
import kafka.utils.VerifiableProperties;
import org.slf4j.Logger;
import com.alu.hal.sbi.json.EudData;
import kafka.serializer.Decoder;

public class EUDMessageDecoder implements Decoder<EudData>
{
    private static final Logger LOG;
    
    public EUDMessageDecoder(final VerifiableProperties props) {
    }
    
    public EUDMessageDecoder() {
    }
    
    @Override
    public EudData fromBytes(final byte[] bytes) {
        final FastByteArrayInputStream stream = new FastByteArrayInputStream(bytes);
        final BinaryDecoder decoder = DecoderFactory.get().directBinaryDecoder(stream, null);
        final SpecificDatumReader<EudData> reader = new SpecificDatumReader<EudData>(EudData.class);
        try {
            return reader.read(null, decoder);
        }
        catch (IOException e) {
            EUDMessageDecoder.LOG.error("Exception was thrown:", e);
            return null;
        }
    }
    
    static {
        LOG = LoggerFactory.getLogger(EUDMessageDecoder.class);
    }
}
