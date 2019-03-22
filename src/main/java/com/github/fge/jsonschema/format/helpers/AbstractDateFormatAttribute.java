// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.format.helpers;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import org.joda.time.format.DateTimeFormatter;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.format.AbstractFormatAttribute;

public abstract class AbstractDateFormatAttribute extends AbstractFormatAttribute
{
    private final String format;
    
    protected AbstractDateFormatAttribute(final String fmt, final String format) {
        super(fmt, NodeType.STRING, new NodeType[0]);
        this.format = format;
    }
    
    protected abstract DateTimeFormatter getFormatter();
    
    @Override
    public final void validate(final ProcessingReport report, final MessageBundle bundle, final FullData data) throws ProcessingException {
        final DateTimeFormatter formatter = this.getFormatter();
        final String value = data.getInstance().getNode().textValue();
        try {
            formatter.parseDateTime(value);
        }
        catch (IllegalArgumentException ignored) {
            report.error(this.newMsg(data, bundle, "err.format.invalidDate").putArgument("value", value).putArgument("expected", this.format));
        }
    }
}
