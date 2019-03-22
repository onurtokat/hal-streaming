// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.format.common;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.format.FormatAttribute;
import com.github.fge.jsonschema.format.AbstractFormatAttribute;

public final class EmailAttribute extends AbstractFormatAttribute
{
    private static final FormatAttribute INSTANCE;
    
    public static FormatAttribute getInstance() {
        return EmailAttribute.INSTANCE;
    }
    
    private EmailAttribute() {
        super("email", NodeType.STRING, new NodeType[0]);
    }
    
    @Override
    public void validate(final ProcessingReport report, final MessageBundle bundle, final FullData data) throws ProcessingException {
        final String value = data.getInstance().getNode().textValue();
        try {
            new InternetAddress(value, true);
        }
        catch (AddressException ignored) {
            report.error(this.newMsg(data, bundle, "err.format.invalidEmail").putArgument("value", value));
        }
    }
    
    static {
        INSTANCE = new EmailAttribute();
    }
}
