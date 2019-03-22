// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.format.draftv3;

import org.joda.time.DateTimeFieldType;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.DateTimeFormatter;
import com.github.fge.jsonschema.format.FormatAttribute;
import com.github.fge.jsonschema.format.helpers.AbstractDateFormatAttribute;

public final class DateAttribute extends AbstractDateFormatAttribute
{
    private static final FormatAttribute INSTANCE;
    
    private DateAttribute() {
        super("date", "yyyy-MM-dd");
    }
    
    public static FormatAttribute getInstance() {
        return DateAttribute.INSTANCE;
    }
    
    @Override
    protected DateTimeFormatter getFormatter() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        builder = builder.appendFixedDecimal(DateTimeFieldType.year(), 4).appendLiteral('-').appendFixedDecimal(DateTimeFieldType.monthOfYear(), 2).appendLiteral('-').appendFixedDecimal(DateTimeFieldType.dayOfMonth(), 2);
        return builder.toFormatter();
    }
    
    static {
        INSTANCE = new DateAttribute();
    }
}
