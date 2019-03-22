// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.core.JsonGenerationException;
import java.io.IOException;
import java.util.Iterator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import java.util.EnumSet;

public class EnumSetSerializer extends AsArraySerializerBase<EnumSet<? extends Enum<?>>>
{
    public EnumSetSerializer(final JavaType elemType, final BeanProperty property) {
        super(EnumSet.class, elemType, true, null, property, null);
    }
    
    public EnumSetSerializer(final EnumSetSerializer src, final BeanProperty property, final TypeSerializer vts, final JsonSerializer<?> valueSerializer) {
        super(src, property, vts, valueSerializer);
    }
    
    public EnumSetSerializer _withValueTypeSerializer(final TypeSerializer vts) {
        return this;
    }
    
    @Override
    public EnumSetSerializer withResolved(final BeanProperty property, final TypeSerializer vts, final JsonSerializer<?> elementSerializer) {
        return new EnumSetSerializer(this, property, vts, elementSerializer);
    }
    
    @Override
    public boolean isEmpty(final EnumSet<? extends Enum<?>> value) {
        return value == null || value.isEmpty();
    }
    
    @Override
    public boolean hasSingleElement(final EnumSet<? extends Enum<?>> value) {
        return value.size() == 1;
    }
    
    public void serializeContents(final EnumSet<? extends Enum<?>> value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonGenerationException {
        JsonSerializer<Object> enumSer = this._elementSerializer;
        for (final Enum<?> en : value) {
            if (enumSer == null) {
                enumSer = provider.findValueSerializer(en.getDeclaringClass(), this._property);
            }
            enumSer.serialize(en, jgen, provider);
        }
    }
}
