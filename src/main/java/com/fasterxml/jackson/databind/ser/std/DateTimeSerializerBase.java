// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonIntegerFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.core.JsonGenerationException;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.lang.reflect.Type;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.util.Locale;
import java.util.TimeZone;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import java.text.SimpleDateFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.text.DateFormat;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

public abstract class DateTimeSerializerBase<T> extends StdScalarSerializer<T> implements ContextualSerializer
{
    protected final Boolean _useTimestamp;
    protected final DateFormat _customFormat;
    
    protected DateTimeSerializerBase(final Class<T> type, final Boolean useTimestamp, final DateFormat customFormat) {
        super(type);
        this._useTimestamp = useTimestamp;
        this._customFormat = customFormat;
    }
    
    public abstract DateTimeSerializerBase<T> withFormat(final Boolean p0, final DateFormat p1);
    
    @Override
    public JsonSerializer<?> createContextual(final SerializerProvider prov, final BeanProperty property) throws JsonMappingException {
        if (property != null) {
            final JsonFormat.Value format = prov.getAnnotationIntrospector().findFormat(property.getMember());
            if (format != null) {
                if (format.getShape().isNumeric()) {
                    return this.withFormat(Boolean.TRUE, null);
                }
                final Boolean asNumber = (format.getShape() == JsonFormat.Shape.STRING) ? Boolean.FALSE : null;
                TimeZone tz = format.getTimeZone();
                if (format.hasPattern()) {
                    final String pattern = format.getPattern();
                    final Locale loc = format.hasLocale() ? format.getLocale() : prov.getLocale();
                    final SimpleDateFormat df = new SimpleDateFormat(pattern, loc);
                    if (tz == null) {
                        tz = prov.getTimeZone();
                    }
                    df.setTimeZone(tz);
                    return this.withFormat(asNumber, df);
                }
                if (tz != null) {
                    DateFormat df2 = prov.getConfig().getDateFormat();
                    if (df2.getClass() == StdDateFormat.class) {
                        final Locale loc = format.hasLocale() ? format.getLocale() : prov.getLocale();
                        df2 = StdDateFormat.getISO8601Format(tz, loc);
                    }
                    else {
                        df2 = (DateFormat)df2.clone();
                        df2.setTimeZone(tz);
                    }
                    return this.withFormat(asNumber, df2);
                }
            }
        }
        return this;
    }
    
    @Override
    public boolean isEmpty(final T value) {
        return value == null || this._timestamp(value) == 0L;
    }
    
    protected abstract long _timestamp(final T p0);
    
    @Override
    public JsonNode getSchema(final SerializerProvider provider, final Type typeHint) {
        return this.createSchemaNode(this._asTimestamp(provider) ? "number" : "string", true);
    }
    
    @Override
    public void acceptJsonFormatVisitor(final JsonFormatVisitorWrapper visitor, final JavaType typeHint) throws JsonMappingException {
        this._acceptJsonFormatVisitor(visitor, typeHint, this._asTimestamp(visitor.getProvider()));
    }
    
    @Override
    public abstract void serialize(final T p0, final JsonGenerator p1, final SerializerProvider p2) throws IOException, JsonGenerationException;
    
    protected boolean _asTimestamp(final SerializerProvider provider) {
        if (this._useTimestamp != null) {
            return this._useTimestamp;
        }
        if (this._customFormat != null) {
            return false;
        }
        if (provider != null) {
            return provider.isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        }
        throw new IllegalArgumentException("Null 'provider' passed for " + this.handledType().getName());
    }
    
    protected void _acceptJsonFormatVisitor(final JsonFormatVisitorWrapper visitor, final JavaType typeHint, final boolean asNumber) throws JsonMappingException {
        if (asNumber) {
            final JsonIntegerFormatVisitor v2 = visitor.expectIntegerFormat(typeHint);
            if (v2 != null) {
                v2.numberType(JsonParser.NumberType.LONG);
                v2.format(JsonValueFormat.UTC_MILLISEC);
            }
        }
        else {
            final JsonStringFormatVisitor v3 = visitor.expectStringFormat(typeHint);
            if (v3 != null) {
                v3.format(JsonValueFormat.DATE_TIME);
            }
        }
    }
}
