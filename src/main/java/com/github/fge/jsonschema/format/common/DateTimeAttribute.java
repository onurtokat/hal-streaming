// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.format.common;

import org.joda.time.format.DateTimeParser;
import org.joda.time.DateTimeFieldType;
import org.joda.time.format.DateTimeFormatterBuilder;
import com.google.common.collect.ImmutableList;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.format.FormatAttribute;
import org.joda.time.format.DateTimeFormatter;
import java.util.List;
import com.github.fge.jsonschema.format.AbstractFormatAttribute;

public final class DateTimeAttribute extends AbstractFormatAttribute
{
    private static final List<String> FORMATS;
    private static final DateTimeFormatter FORMATTER;
    private static final FormatAttribute INSTANCE;
    
    public static FormatAttribute getInstance() {
        return DateTimeAttribute.INSTANCE;
    }
    
    private DateTimeAttribute() {
        super("date-time", NodeType.STRING, new NodeType[0]);
    }
    
    @Override
    public void validate(final ProcessingReport report, final MessageBundle bundle, final FullData data) throws ProcessingException {
        final String value = data.getInstance().getNode().textValue();
        try {
            DateTimeAttribute.FORMATTER.parseDateTime(value);
        }
        catch (IllegalArgumentException ignored) {
            report.error(this.newMsg(data, bundle, "err.format.invalidDate").putArgument("value", value).putArgument("expected", (Iterable<String>)DateTimeAttribute.FORMATS));
        }
    }
    
    static {
        FORMATS = ImmutableList.of("yyyy-MM-dd'T'HH:mm:ssZ", "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        final DateTimeParser msParser = new DateTimeFormatterBuilder().appendLiteral('.').appendDecimal(DateTimeFieldType.millisOfSecond(), 1, 3).toParser();
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        builder = builder.appendFixedDecimal(DateTimeFieldType.year(), 4).appendLiteral('-').appendFixedDecimal(DateTimeFieldType.monthOfYear(), 2).appendLiteral('-').appendFixedDecimal(DateTimeFieldType.dayOfMonth(), 2).appendLiteral('T').appendFixedDecimal(DateTimeFieldType.hourOfDay(), 2).appendLiteral(':').appendFixedDecimal(DateTimeFieldType.minuteOfHour(), 2).appendLiteral(':').appendFixedDecimal(DateTimeFieldType.secondOfMinute(), 2).appendOptional(msParser).appendTimeZoneOffset("Z", false, 2, 2);
        FORMATTER = builder.toFormatter();
        INSTANCE = new DateTimeAttribute();
    }
}
