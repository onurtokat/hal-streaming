// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerationException;
import java.io.IOException;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.core.JsonGenerator;

public abstract class NonTypedScalarSerializerBase<T> extends StdScalarSerializer<T>
{
    protected NonTypedScalarSerializerBase(final Class<T> t) {
        super(t);
    }
    
    @Override
    public final void serializeWithType(final T value, final JsonGenerator jgen, final SerializerProvider provider, final TypeSerializer typeSer) throws IOException, JsonGenerationException {
        this.serialize(value, jgen, provider);
    }
}
