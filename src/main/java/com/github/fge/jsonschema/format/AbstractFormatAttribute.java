// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.format;

import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.jackson.NodeType;
import java.util.EnumSet;

public abstract class AbstractFormatAttribute implements FormatAttribute
{
    private final EnumSet<NodeType> supported;
    private final String fmt;
    
    protected AbstractFormatAttribute(final String fmt, final NodeType first, final NodeType... other) {
        this.fmt = fmt;
        this.supported = EnumSet.of(first, other);
    }
    
    @Override
    public final EnumSet<NodeType> supportedTypes() {
        return EnumSet.copyOf(this.supported);
    }
    
    protected final ProcessingMessage newMsg(final FullData data, final MessageBundle bundle, final String key) {
        return data.newMessage().put("domain", "validation").put("keyword", "format").put("attribute", this.fmt).setMessage(bundle.getMessage(key)).put("value", data.getInstance().getNode());
    }
}
