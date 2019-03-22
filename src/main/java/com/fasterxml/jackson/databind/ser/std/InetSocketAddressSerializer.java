// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.core.JsonGenerationException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Inet6Address;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.core.JsonGenerator;
import java.net.InetSocketAddress;

public class InetSocketAddressSerializer extends StdScalarSerializer<InetSocketAddress>
{
    public InetSocketAddressSerializer() {
        super(InetSocketAddress.class);
    }
    
    @Override
    public void serialize(final InetSocketAddress value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonGenerationException {
        final InetAddress addr = value.getAddress();
        String str = (addr == null) ? value.getHostName() : addr.toString().trim();
        final int ix = str.indexOf(47);
        if (ix >= 0) {
            if (ix == 0) {
                str = ((addr instanceof Inet6Address) ? ("[" + str.substring(1) + "]") : str.substring(1));
            }
            else {
                str = str.substring(0, ix);
            }
        }
        jgen.writeString(str + ":" + value.getPort());
    }
    
    @Override
    public void serializeWithType(final InetSocketAddress value, final JsonGenerator jgen, final SerializerProvider provider, final TypeSerializer typeSer) throws IOException, JsonGenerationException {
        typeSer.writeTypePrefixForScalar(value, jgen, InetSocketAddress.class);
        this.serialize(value, jgen, provider);
        typeSer.writeTypeSuffixForScalar(value, jgen);
    }
}
