// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.format.extra;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import java.util.UUID;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.format.FormatAttribute;
import com.github.fge.jsonschema.format.AbstractFormatAttribute;

public final class UUIDFormatAttribute extends AbstractFormatAttribute
{
    private static final FormatAttribute instance;
    
    private UUIDFormatAttribute() {
        super("uuid", NodeType.STRING, new NodeType[0]);
    }
    
    public static FormatAttribute getInstance() {
        return UUIDFormatAttribute.instance;
    }
    
    @Override
    public void validate(final ProcessingReport report, final MessageBundle bundle, final FullData data) throws ProcessingException {
        final String input = data.getInstance().getNode().textValue();
        try {
            UUID.fromString(input);
        }
        catch (IllegalArgumentException ignored) {
            report.error(this.newMsg(data, bundle, "err.format.UUID.invalid").putArgument("value", input));
        }
    }
    
    static {
        instance = new UUIDFormatAttribute();
    }
}
