// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerationException;
import java.io.IOException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.core.JsonGenerator;
import java.util.Calendar;
import java.util.Date;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;

public class StdKeySerializers
{
    protected static final JsonSerializer<Object> DEFAULT_KEY_SERIALIZER;
    protected static final JsonSerializer<Object> DEFAULT_STRING_SERIALIZER;
    
    public static JsonSerializer<Object> getStdKeySerializer(final JavaType keyType) {
        if (keyType == null) {
            return StdKeySerializers.DEFAULT_KEY_SERIALIZER;
        }
        final Class<?> cls = keyType.getRawClass();
        if (cls == String.class) {
            return StdKeySerializers.DEFAULT_STRING_SERIALIZER;
        }
        if (cls == Object.class || cls.isPrimitive() || Number.class.isAssignableFrom(cls)) {
            return StdKeySerializers.DEFAULT_KEY_SERIALIZER;
        }
        if (Date.class.isAssignableFrom(cls)) {
            return (JsonSerializer<Object>)DateKeySerializer.instance;
        }
        if (Calendar.class.isAssignableFrom(cls)) {
            return (JsonSerializer<Object>)CalendarKeySerializer.instance;
        }
        return StdKeySerializers.DEFAULT_KEY_SERIALIZER;
    }
    
    static {
        DEFAULT_KEY_SERIALIZER = new StdKeySerializer();
        DEFAULT_STRING_SERIALIZER = new StringKeySerializer();
    }
    
    public static class StringKeySerializer extends StdSerializer<String>
    {
        public StringKeySerializer() {
            super(String.class);
        }
        
        @Override
        public void serialize(final String value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonGenerationException {
            jgen.writeFieldName(value);
        }
    }
    
    public static class DateKeySerializer extends StdSerializer<Date>
    {
        protected static final JsonSerializer<?> instance;
        
        public DateKeySerializer() {
            super(Date.class);
        }
        
        @Override
        public void serialize(final Date value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonGenerationException {
            provider.defaultSerializeDateKey(value, jgen);
        }
        
        static {
            instance = new DateKeySerializer();
        }
    }
    
    public static class CalendarKeySerializer extends StdSerializer<Calendar>
    {
        protected static final JsonSerializer<?> instance;
        
        public CalendarKeySerializer() {
            super(Calendar.class);
        }
        
        @Override
        public void serialize(final Calendar value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonGenerationException {
            provider.defaultSerializeDateKey(value.getTimeInMillis(), jgen);
        }
        
        static {
            instance = new CalendarKeySerializer();
        }
    }
}
