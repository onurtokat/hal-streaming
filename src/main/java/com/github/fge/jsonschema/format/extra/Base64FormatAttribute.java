// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.format.extra;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.format.FormatAttribute;
import com.google.common.base.CharMatcher;
import java.util.regex.Pattern;
import com.github.fge.jsonschema.format.AbstractFormatAttribute;

public final class Base64FormatAttribute extends AbstractFormatAttribute
{
    private static final Pattern PATTERN;
    private static final CharMatcher NOT_BASE64;
    private static final FormatAttribute instance;
    
    public static FormatAttribute getInstance() {
        return Base64FormatAttribute.instance;
    }
    
    private Base64FormatAttribute() {
        super("base64", NodeType.STRING, new NodeType[0]);
    }
    
    @Override
    public void validate(final ProcessingReport report, final MessageBundle bundle, final FullData data) throws ProcessingException {
        final String input = data.getInstance().getNode().textValue();
        if (input.length() % 4 != 0) {
            report.error(this.newMsg(data, bundle, "err.format.base64.badLength").putArgument("length", input.length()));
            return;
        }
        final int index = Base64FormatAttribute.NOT_BASE64.indexIn(Base64FormatAttribute.PATTERN.matcher(input).replaceFirst(""));
        if (index == -1) {
            return;
        }
        report.error(this.newMsg(data, bundle, "err.format.base64.illegalChars").putArgument("character", Character.toString(input.charAt(index))).putArgument("index", index));
    }
    
    static {
        PATTERN = Pattern.compile("==?$");
        NOT_BASE64 = CharMatcher.inRange('a', 'z').or(CharMatcher.inRange('A', 'Z')).or(CharMatcher.inRange('0', '9')).or(CharMatcher.anyOf("+/")).negate();
        instance = new Base64FormatAttribute();
    }
}
