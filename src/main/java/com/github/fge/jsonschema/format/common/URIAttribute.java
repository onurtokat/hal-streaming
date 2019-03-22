// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.format.common;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import java.net.URISyntaxException;
import java.net.URI;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.format.FormatAttribute;
import com.github.fge.jsonschema.format.AbstractFormatAttribute;

public final class URIAttribute extends AbstractFormatAttribute
{
    private static final FormatAttribute INSTANCE;
    
    public static FormatAttribute getInstance() {
        return URIAttribute.INSTANCE;
    }
    
    private URIAttribute() {
        super("uri", NodeType.STRING, new NodeType[0]);
    }
    
    @Override
    public void validate(final ProcessingReport report, final MessageBundle bundle, final FullData data) throws ProcessingException {
        final String value = data.getInstance().getNode().textValue();
        try {
            new URI(value);
        }
        catch (URISyntaxException ignored) {
            report.error(this.newMsg(data, bundle, "err.format.invalidURI").putArgument("value", value));
        }
    }
    
    static {
        INSTANCE = new URIAttribute();
    }
}
