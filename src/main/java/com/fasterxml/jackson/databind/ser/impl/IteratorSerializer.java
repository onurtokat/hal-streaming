// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.JsonGenerationException;
import java.io.IOException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import java.util.Iterator;
import com.fasterxml.jackson.databind.ser.std.AsArraySerializerBase;

@JacksonStdImpl
public class IteratorSerializer extends AsArraySerializerBase<Iterator<?>>
{
    public IteratorSerializer(final JavaType elemType, final boolean staticTyping, final TypeSerializer vts, final BeanProperty property) {
        super(Iterator.class, elemType, staticTyping, vts, property, null);
    }
    
    public IteratorSerializer(final IteratorSerializer src, final BeanProperty property, final TypeSerializer vts, final JsonSerializer<?> valueSerializer) {
        super(src, property, vts, valueSerializer);
    }
    
    @Override
    public boolean isEmpty(final Iterator<?> value) {
        return value == null || !value.hasNext();
    }
    
    @Override
    public boolean hasSingleElement(final Iterator<?> value) {
        return false;
    }
    
    public ContainerSerializer<?> _withValueTypeSerializer(final TypeSerializer vts) {
        return new IteratorSerializer(this._elementType, this._staticTyping, vts, this._property);
    }
    
    @Override
    public IteratorSerializer withResolved(final BeanProperty property, final TypeSerializer vts, final JsonSerializer<?> elementSerializer) {
        return new IteratorSerializer(this, property, vts, elementSerializer);
    }
    
    public void serializeContents(final Iterator<?> value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonGenerationException {
        if (value.hasNext()) {
            final TypeSerializer typeSer = this._valueTypeSerializer;
            JsonSerializer<Object> prevSerializer = null;
            Class<?> prevClass = null;
            do {
                final Object elem = value.next();
                if (elem == null) {
                    provider.defaultSerializeNull(jgen);
                }
                else {
                    JsonSerializer<Object> currSerializer = this._elementSerializer;
                    if (currSerializer == null) {
                        final Class<?> cc = elem.getClass();
                        if (cc == prevClass) {
                            currSerializer = prevSerializer;
                        }
                        else {
                            currSerializer = (prevSerializer = provider.findValueSerializer(cc, this._property));
                            prevClass = cc;
                        }
                    }
                    if (typeSer == null) {
                        currSerializer.serialize(elem, jgen, provider);
                    }
                    else {
                        currSerializer.serializeWithType(elem, jgen, provider, typeSer);
                    }
                }
            } while (value.hasNext());
        }
    }
}
