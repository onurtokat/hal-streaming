// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.core.JsonGenerationException;
import java.io.IOException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;

public abstract class ArraySerializerBase<T> extends ContainerSerializer<T>
{
    protected final BeanProperty _property;
    
    protected ArraySerializerBase(final Class<T> cls) {
        super(cls);
        this._property = null;
    }
    
    protected ArraySerializerBase(final Class<T> cls, final BeanProperty property) {
        super(cls);
        this._property = property;
    }
    
    protected ArraySerializerBase(final ArraySerializerBase<?> src) {
        super(src._handledType, false);
        this._property = src._property;
    }
    
    protected ArraySerializerBase(final ArraySerializerBase<?> src, final BeanProperty property) {
        super(src._handledType, false);
        this._property = property;
    }
    
    @Override
    public final void serialize(final T value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonGenerationException {
        if (provider.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED) && this.hasSingleElement(value)) {
            this.serializeContents(value, jgen, provider);
            return;
        }
        jgen.writeStartArray();
        this.serializeContents(value, jgen, provider);
        jgen.writeEndArray();
    }
    
    @Override
    public final void serializeWithType(final T value, final JsonGenerator jgen, final SerializerProvider provider, final TypeSerializer typeSer) throws IOException, JsonGenerationException {
        typeSer.writeTypePrefixForArray(value, jgen);
        this.serializeContents(value, jgen, provider);
        typeSer.writeTypeSuffixForArray(value, jgen);
    }
    
    protected abstract void serializeContents(final T p0, final JsonGenerator p1, final SerializerProvider p2) throws IOException, JsonGenerationException;
}
