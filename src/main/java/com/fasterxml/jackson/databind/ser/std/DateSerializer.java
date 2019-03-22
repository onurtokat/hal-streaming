// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerationException;
import java.io.IOException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.core.JsonGenerator;
import java.text.DateFormat;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import java.util.Date;

@JacksonStdImpl
public class DateSerializer extends DateTimeSerializerBase<Date>
{
    public static final DateSerializer instance;
    
    public DateSerializer() {
        this(null, null);
    }
    
    public DateSerializer(final Boolean useTimestamp, final DateFormat customFormat) {
        super(Date.class, useTimestamp, customFormat);
    }
    
    @Override
    public DateSerializer withFormat(final Boolean timestamp, final DateFormat customFormat) {
        return new DateSerializer(timestamp, customFormat);
    }
    
    @Override
    protected long _timestamp(final Date value) {
        return (value == null) ? 0L : value.getTime();
    }
    
    @Override
    public void serialize(final Date value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonGenerationException {
        if (this._asTimestamp(provider)) {
            jgen.writeNumber(this._timestamp(value));
        }
        else if (this._customFormat != null) {
            synchronized (this._customFormat) {
                jgen.writeString(this._customFormat.format(value));
            }
        }
        else {
            provider.defaultSerializeDateValue(value, jgen);
        }
    }
    
    static {
        instance = new DateSerializer();
    }
}
