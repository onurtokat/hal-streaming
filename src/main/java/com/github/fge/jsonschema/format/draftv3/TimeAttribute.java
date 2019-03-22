// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.format.draftv3;

import org.joda.time.DateTimeFieldType;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.DateTimeFormatter;
import com.github.fge.jsonschema.format.FormatAttribute;
import com.github.fge.jsonschema.format.helpers.AbstractDateFormatAttribute;

public final class TimeAttribute extends AbstractDateFormatAttribute
{
    private static final FormatAttribute INSTANCE;
    
    private TimeAttribute() {
        super("time", "HH:mm:ss");
    }
    
    public static FormatAttribute getInstance() {
        return TimeAttribute.INSTANCE;
    }
    
    @Override
    protected DateTimeFormatter getFormatter() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        builder = builder.appendFixedDecimal(DateTimeFieldType.hourOfDay(), 2).appendLiteral(':').appendFixedDecimal(DateTimeFieldType.minuteOfHour(), 2).appendLiteral(':').appendFixedDecimal(DateTimeFieldType.secondOfMinute(), 2);
        return builder.toFormatter();
    }
    
    static {
        INSTANCE = new TimeAttribute();
    }
}
