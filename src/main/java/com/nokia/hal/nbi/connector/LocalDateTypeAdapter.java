// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector;

import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import org.joda.time.ReadablePartial;
import com.google.gson.stream.JsonWriter;
import org.joda.time.format.ISODateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.LocalDate;
import com.google.gson.TypeAdapter;

class LocalDateTypeAdapter extends TypeAdapter<LocalDate>
{
    private final DateTimeFormatter formatter;
    
    LocalDateTypeAdapter() {
        this.formatter = ISODateTimeFormat.date();
    }
    
    @Override
    public void write(final JsonWriter out, final LocalDate date) throws IOException {
        if (date == null) {
            out.nullValue();
        }
        else {
            out.value(this.formatter.print(date));
        }
    }
    
    @Override
    public LocalDate read(final JsonReader in) throws IOException {
        switch (in.peek()) {
            case NULL: {
                in.nextNull();
                return null;
            }
            default: {
                final String date = in.nextString();
                return this.formatter.parseLocalDate(date);
            }
        }
    }
}
