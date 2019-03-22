// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.ext;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import java.lang.reflect.Type;
import com.fasterxml.jackson.core.JsonGenerationException;
import java.io.IOException;
import java.util.Calendar;
import com.fasterxml.jackson.databind.ser.std.CalendarSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import javax.xml.datatype.XMLGregorianCalendar;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import javax.xml.namespace.QName;
import javax.xml.datatype.Duration;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.Serializers;

public class CoreXMLSerializers extends Serializers.Base
{
    @Override
    public JsonSerializer<?> findSerializer(final SerializationConfig config, final JavaType type, final BeanDescription beanDesc) {
        final Class<?> raw = type.getRawClass();
        if (Duration.class.isAssignableFrom(raw) || QName.class.isAssignableFrom(raw)) {
            return ToStringSerializer.instance;
        }
        if (XMLGregorianCalendar.class.isAssignableFrom(raw)) {
            return new XMLGregorianCalendarSerializer();
        }
        return null;
    }
    
    public static class XMLGregorianCalendarSerializer extends StdSerializer<XMLGregorianCalendar>
    {
        public XMLGregorianCalendarSerializer() {
            super(XMLGregorianCalendar.class);
        }
        
        @Override
        public void serialize(final XMLGregorianCalendar value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonGenerationException {
            CalendarSerializer.instance.serialize(value.toGregorianCalendar(), jgen, provider);
        }
        
        @Override
        public JsonNode getSchema(final SerializerProvider provider, final Type typeHint) throws JsonMappingException {
            return CalendarSerializer.instance.getSchema(provider, typeHint);
        }
        
        @Override
        public void acceptJsonFormatVisitor(final JsonFormatVisitorWrapper visitor, final JavaType typeHint) throws JsonMappingException {
            CalendarSerializer.instance.acceptJsonFormatVisitor(visitor, null);
        }
    }
}
