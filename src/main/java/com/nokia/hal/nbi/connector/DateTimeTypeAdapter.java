// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector;

import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import org.joda.time.ReadableInstant;
import com.google.gson.stream.JsonWriter;
import org.joda.time.format.ISODateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.DateTime;
import com.google.gson.TypeAdapter;

class DateTimeTypeAdapter extends TypeAdapter<DateTime>
{
    private final DateTimeFormatter parseFormatter;
    private final DateTimeFormatter printFormatter;
    
    DateTimeTypeAdapter() {
        this.parseFormatter = ISODateTimeFormat.dateOptionalTimeParser();
        this.printFormatter = ISODateTimeFormat.dateTime();
    }
    
    @Override
    public void write(final JsonWriter out, final DateTime date) throws IOException {
        if (date == null) {
            out.nullValue();
        }
        else {
            out.value(this.printFormatter.print(date));
        }
    }
    
    @Override
    public DateTime read(final JsonReader in) throws IOException {
        switch (in.peek()) {
            case NULL: {
                in.nextNull();
                return null;
            }
            default: {
                final String date = in.nextString();
                return this.parseFormatter.parseDateTime(date);
            }
        }
    }
}
