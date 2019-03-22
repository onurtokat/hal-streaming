// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.format.helpers;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jackson.NodeType;
import com.google.common.base.CharMatcher;
import com.github.fge.jsonschema.format.AbstractFormatAttribute;

public abstract class HexStringFormatAttribute extends AbstractFormatAttribute
{
    private static final CharMatcher HEX_CHARS;
    protected final int length;
    
    protected HexStringFormatAttribute(final String fmt, final int length) {
        super(fmt, NodeType.STRING, new NodeType[0]);
        this.length = length;
    }
    
    @Override
    public final void validate(final ProcessingReport report, final MessageBundle bundle, final FullData data) throws ProcessingException {
        final String input = data.getInstance().getNode().textValue();
        if (this.length != input.length()) {
            report.error(this.newMsg(data, bundle, "err.format.hexString.badLength").putArgument("actual", input.length()).putArgument("expected", this.length));
            return;
        }
        if (HexStringFormatAttribute.HEX_CHARS.matchesAllOf(input)) {
            return;
        }
        final int index = HexStringFormatAttribute.HEX_CHARS.negate().indexIn(input);
        report.error(this.newMsg(data, bundle, "err.format.hexString.illegalChar").putArgument("character", Character.toString(input.charAt(index))).putArgument("index", index));
    }
    
    static {
        HEX_CHARS = CharMatcher.anyOf("0123456789abcdefABCDEF").precomputed();
    }
}
