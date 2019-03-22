// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.core.JsonGenerationException;
import java.io.IOException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.core.JsonGenerator;
import java.util.TimeZone;

public class TimeZoneSerializer extends StdScalarSerializer<TimeZone>
{
    public TimeZoneSerializer() {
        super(TimeZone.class);
    }
    
    @Override
    public void serialize(final TimeZone value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonGenerationException {
        jgen.writeString(value.getID());
    }
    
    @Override
    public void serializeWithType(final TimeZone value, final JsonGenerator jgen, final SerializerProvider provider, final TypeSerializer typeSer) throws IOException, JsonGenerationException {
        typeSer.writeTypePrefixForScalar(value, jgen, TimeZone.class);
        this.serialize(value, jgen, provider);
        typeSer.writeTypeSuffixForScalar(value, jgen);
    }
}
