// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.uritemplate.parse;

import com.google.common.collect.ImmutableMap;
import com.github.fge.msgsimple.load.MessageBundleLoader;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.uritemplate.URITemplateMessageBundle;
import com.github.fge.uritemplate.vars.specs.VariableSpec;
import java.util.List;
import com.github.fge.uritemplate.expression.TemplateExpression;
import com.google.common.collect.Lists;
import com.github.fge.uritemplate.URITemplateParseException;
import com.github.fge.uritemplate.expression.URITemplateExpression;
import java.nio.CharBuffer;
import com.google.common.base.CharMatcher;
import com.github.fge.uritemplate.expression.ExpressionType;
import java.util.Map;
import com.github.fge.msgsimple.bundle.MessageBundle;

final class ExpressionParser implements TemplateParser
{
    private static final MessageBundle BUNDLE;
    private static final Map<Character, ExpressionType> EXPRESSION_TYPE_MAP;
    private static final CharMatcher COMMA;
    private static final CharMatcher END_EXPRESSION;
    
    @Override
    public URITemplateExpression parse(final CharBuffer buffer) throws URITemplateParseException {
        buffer.get();
        if (!buffer.hasRemaining()) {
            throw new URITemplateParseException(ExpressionParser.BUNDLE.getMessage("parse.unexpectedEOF"), buffer, true);
        }
        ExpressionType type = ExpressionType.SIMPLE;
        char c = buffer.charAt(0);
        if (ExpressionParser.EXPRESSION_TYPE_MAP.containsKey(c)) {
            type = ExpressionParser.EXPRESSION_TYPE_MAP.get(buffer.get());
        }
        final List<VariableSpec> varspecs = (List<VariableSpec>)Lists.newArrayList();
        while (true) {
            varspecs.add(VariableSpecParser.parse(buffer));
            if (!buffer.hasRemaining()) {
                throw new URITemplateParseException(ExpressionParser.BUNDLE.getMessage("parse.unexpectedEOF"), buffer, true);
            }
            c = buffer.get();
            if (ExpressionParser.COMMA.matches(c)) {
                continue;
            }
            if (ExpressionParser.END_EXPRESSION.matches(c)) {
                return new TemplateExpression(type, varspecs);
            }
            throw new URITemplateParseException(ExpressionParser.BUNDLE.getMessage("parse.unexpectedToken"), buffer, true);
        }
    }
    
    static {
        BUNDLE = MessageBundles.getBundle(URITemplateMessageBundle.class);
        COMMA = CharMatcher.is(',');
        END_EXPRESSION = CharMatcher.is('}');
        final ImmutableMap.Builder<Character, ExpressionType> builder = ImmutableMap.builder();
        char c = '+';
        ExpressionType type = ExpressionType.RESERVED;
        builder.put(c, type);
        c = '#';
        type = ExpressionType.FRAGMENT;
        builder.put(c, type);
        c = '.';
        type = ExpressionType.NAME_LABELS;
        builder.put(c, type);
        c = '/';
        type = ExpressionType.PATH_SEGMENTS;
        builder.put(c, type);
        c = ';';
        type = ExpressionType.PATH_PARAMETERS;
        builder.put(c, type);
        c = '?';
        type = ExpressionType.QUERY_STRING;
        builder.put(c, type);
        c = '&';
        type = ExpressionType.QUERY_CONT;
        builder.put(c, type);
        EXPRESSION_TYPE_MAP = builder.build();
    }
}
