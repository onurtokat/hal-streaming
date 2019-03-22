// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.uritemplate.parse;

import com.github.fge.msgsimple.load.MessageBundleLoader;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.uritemplate.URITemplateMessageBundle;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.github.fge.uritemplate.URITemplateParseException;
import java.nio.CharBuffer;
import com.github.fge.uritemplate.expression.URITemplateExpression;
import java.util.List;
import com.google.common.base.CharMatcher;
import com.github.fge.msgsimple.bundle.MessageBundle;

public final class URITemplateParser
{
    private static final MessageBundle BUNDLE;
    private static final CharMatcher BEGIN_EXPRESSION;
    
    public static List<URITemplateExpression> parse(final String input) throws URITemplateParseException {
        return parse(CharBuffer.wrap(input).asReadOnlyBuffer());
    }
    
    @VisibleForTesting
    static List<URITemplateExpression> parse(final CharBuffer buffer) throws URITemplateParseException {
        final List<URITemplateExpression> ret = (List<URITemplateExpression>)Lists.newArrayList();
        while (buffer.hasRemaining()) {
            final TemplateParser templateParser = selectParser(buffer);
            final URITemplateExpression expression = templateParser.parse(buffer);
            ret.add(expression);
        }
        return ret;
    }
    
    private static TemplateParser selectParser(final CharBuffer buffer) throws URITemplateParseException {
        final char c = buffer.charAt(0);
        TemplateParser parser;
        if (CharMatchers.LITERALS.matches(c)) {
            parser = new LiteralParser();
        }
        else {
            if (!URITemplateParser.BEGIN_EXPRESSION.matches(c)) {
                throw new URITemplateParseException(URITemplateParser.BUNDLE.getMessage("parse.noParser"), buffer);
            }
            parser = new ExpressionParser();
        }
        return parser;
    }
    
    static {
        BUNDLE = MessageBundles.getBundle(URITemplateMessageBundle.class);
        BEGIN_EXPRESSION = CharMatcher.is('{');
    }
}
