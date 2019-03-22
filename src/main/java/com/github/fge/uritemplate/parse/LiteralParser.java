// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.uritemplate.parse;

import com.github.fge.msgsimple.load.MessageBundleLoader;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.uritemplate.URITemplateMessageBundle;
import com.github.fge.uritemplate.URITemplateParseException;
import com.github.fge.uritemplate.expression.TemplateLiteral;
import com.github.fge.uritemplate.expression.URITemplateExpression;
import java.nio.CharBuffer;
import com.github.fge.msgsimple.bundle.MessageBundle;

final class LiteralParser implements TemplateParser
{
    private static final MessageBundle BUNDLE;
    
    @Override
    public URITemplateExpression parse(final CharBuffer buffer) throws URITemplateParseException {
        final StringBuilder sb = new StringBuilder();
        while (buffer.hasRemaining()) {
            final char c = buffer.charAt(0);
            if (!CharMatchers.LITERALS.matches(c)) {
                break;
            }
            sb.append(buffer.get());
            if (!CharMatchers.PERCENT.matches(c)) {
                continue;
            }
            parsePercentEncoded(buffer, sb);
        }
        return new TemplateLiteral(sb.toString());
    }
    
    private static void parsePercentEncoded(final CharBuffer buffer, final StringBuilder sb) throws URITemplateParseException {
        if (buffer.remaining() < 2) {
            throw new URITemplateParseException(LiteralParser.BUNDLE.getMessage("paser.percentShortRead"), buffer, true);
        }
        final char first = buffer.get();
        if (!CharMatchers.HEXDIGIT.matches(first)) {
            throw new URITemplateParseException(LiteralParser.BUNDLE.getMessage("parse.percentIllegal"), buffer, true);
        }
        final char second = buffer.get();
        if (!CharMatchers.HEXDIGIT.matches(second)) {
            throw new URITemplateParseException(LiteralParser.BUNDLE.getMessage("parse.percentIllegal"), buffer, true);
        }
        sb.append(first).append(second);
    }
    
    static {
        BUNDLE = MessageBundles.getBundle(URITemplateMessageBundle.class);
    }
}
