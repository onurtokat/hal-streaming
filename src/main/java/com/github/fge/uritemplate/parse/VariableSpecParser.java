// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.uritemplate.parse;

import com.github.fge.msgsimple.load.MessageBundleLoader;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.uritemplate.URITemplateMessageBundle;
import java.util.List;
import com.google.common.collect.Lists;
import com.github.fge.uritemplate.URITemplateParseException;
import com.github.fge.uritemplate.vars.specs.PrefixVariable;
import com.github.fge.uritemplate.vars.specs.ExplodedVariable;
import com.github.fge.uritemplate.vars.specs.SimpleVariable;
import com.github.fge.uritemplate.vars.specs.VariableSpec;
import java.nio.CharBuffer;
import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.github.fge.msgsimple.bundle.MessageBundle;

final class VariableSpecParser
{
    private static final MessageBundle BUNDLE;
    private static final Joiner JOINER;
    private static final CharMatcher VARCHAR;
    private static final CharMatcher DOT;
    private static final CharMatcher COLON;
    private static final CharMatcher STAR;
    private static final CharMatcher DIGIT;
    
    public static VariableSpec parse(final CharBuffer buffer) throws URITemplateParseException {
        final String name = parseFullName(buffer);
        if (!buffer.hasRemaining()) {
            return new SimpleVariable(name);
        }
        final char c = buffer.charAt(0);
        if (VariableSpecParser.STAR.matches(c)) {
            buffer.get();
            return new ExplodedVariable(name);
        }
        if (VariableSpecParser.COLON.matches(c)) {
            buffer.get();
            return new PrefixVariable(name, getPrefixLength(buffer));
        }
        return new SimpleVariable(name);
    }
    
    private static String parseFullName(final CharBuffer buffer) throws URITemplateParseException {
        final List<String> components = (List<String>)Lists.newArrayList();
        while (true) {
            components.add(readName(buffer));
            if (!buffer.hasRemaining()) {
                break;
            }
            if (!VariableSpecParser.DOT.matches(buffer.charAt(0))) {
                break;
            }
            buffer.get();
        }
        return VariableSpecParser.JOINER.join(components);
    }
    
    private static String readName(final CharBuffer buffer) throws URITemplateParseException {
        final StringBuilder sb = new StringBuilder();
        while (buffer.hasRemaining()) {
            final char c = buffer.charAt(0);
            if (!VariableSpecParser.VARCHAR.matches(c)) {
                break;
            }
            sb.append(buffer.get());
            if (!CharMatchers.PERCENT.matches(c)) {
                continue;
            }
            parsePercentEncoded(buffer, sb);
        }
        final String ret = sb.toString();
        if (ret.isEmpty()) {
            throw new URITemplateParseException(VariableSpecParser.BUNDLE.getMessage("parse.emptyVarname"), buffer);
        }
        return ret;
    }
    
    private static void parsePercentEncoded(final CharBuffer buffer, final StringBuilder sb) throws URITemplateParseException {
        if (buffer.remaining() < 2) {
            throw new URITemplateParseException(VariableSpecParser.BUNDLE.getMessage("paser.percentShortRead"), buffer, true);
        }
        final char first = buffer.get();
        if (!CharMatchers.HEXDIGIT.matches(first)) {
            throw new URITemplateParseException(VariableSpecParser.BUNDLE.getMessage("parse.percentIllegal"), buffer, true);
        }
        final char second = buffer.get();
        if (!CharMatchers.HEXDIGIT.matches(second)) {
            throw new URITemplateParseException(VariableSpecParser.BUNDLE.getMessage("parse.percentIllegal"), buffer, true);
        }
        sb.append(first).append(second);
    }
    
    private static int getPrefixLength(final CharBuffer buffer) throws URITemplateParseException {
        final StringBuilder sb = new StringBuilder();
        while (buffer.hasRemaining()) {
            final char c = buffer.charAt(0);
            if (!VariableSpecParser.DIGIT.matches(c)) {
                break;
            }
            sb.append(buffer.get());
        }
        final String s = sb.toString();
        if (s.isEmpty()) {
            throw new URITemplateParseException(VariableSpecParser.BUNDLE.getMessage("parse.emptyPrefix"), buffer, true);
        }
        try {
            final int ret = Integer.parseInt(s);
            if (ret > 10000) {
                throw new NumberFormatException();
            }
            return ret;
        }
        catch (NumberFormatException ignored) {
            throw new URITemplateParseException(VariableSpecParser.BUNDLE.getMessage("parse.prefixTooLarge"), buffer, true);
        }
    }
    
    static {
        BUNDLE = MessageBundles.getBundle(URITemplateMessageBundle.class);
        JOINER = Joiner.on('.');
        VARCHAR = CharMatcher.inRange('0', '9').or(CharMatcher.inRange('a', 'z')).or(CharMatcher.inRange('A', 'Z')).or(CharMatcher.is('_')).or(CharMatchers.PERCENT).precomputed();
        DOT = CharMatcher.is('.');
        COLON = CharMatcher.is(':');
        STAR = CharMatcher.is('*');
        DIGIT = CharMatcher.inRange('0', '9').precomputed();
    }
}
