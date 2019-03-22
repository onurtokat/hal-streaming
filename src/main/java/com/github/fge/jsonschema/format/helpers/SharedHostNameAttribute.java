// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.format.helpers;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.google.common.net.InternetDomainName;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.format.AbstractFormatAttribute;

public final class SharedHostNameAttribute extends AbstractFormatAttribute
{
    public SharedHostNameAttribute(final String fmt) {
        super(fmt, NodeType.STRING, new NodeType[0]);
    }
    
    @Override
    public void validate(final ProcessingReport report, final MessageBundle bundle, final FullData data) throws ProcessingException {
        final String value = data.getInstance().getNode().textValue();
        try {
            InternetDomainName.from(value);
        }
        catch (IllegalArgumentException ignored) {
            report.error(this.newMsg(data, bundle, "err.format.invalidHostname").putArgument("value", value));
        }
    }
}
