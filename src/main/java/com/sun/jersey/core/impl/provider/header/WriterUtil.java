// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.core.impl.provider.header;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WriterUtil
{
    private static Pattern whitespace;
    private static Pattern whitespaceOrQuote;
    
    public static void appendQuotedIfWhiteSpaceOrQuote(final StringBuilder b, final String value) {
        if (value == null) {
            return;
        }
        final Matcher m = WriterUtil.whitespaceOrQuote.matcher(value);
        final boolean quote = m.find();
        if (quote) {
            b.append('\"');
        }
        appendEscapingQuotes(b, value);
        if (quote) {
            b.append('\"');
        }
    }
    
    public static void appendQuotedIfWhitespace(final StringBuilder b, final String value) {
        if (value == null) {
            return;
        }
        final Matcher m = WriterUtil.whitespace.matcher(value);
        final boolean quote = m.find();
        if (quote) {
            b.append('\"');
        }
        appendEscapingQuotes(b, value);
        if (quote) {
            b.append('\"');
        }
    }
    
    public static void appendQuoted(final StringBuilder b, final String value) {
        b.append('\"');
        appendEscapingQuotes(b, value);
        b.append('\"');
    }
    
    public static void appendEscapingQuotes(final StringBuilder b, final String value) {
        for (int i = 0; i < value.length(); ++i) {
            final char c = value.charAt(i);
            if (c == '\"') {
                b.append('\\');
            }
            b.append(c);
        }
    }
    
    static {
        WriterUtil.whitespace = Pattern.compile("\\s");
        WriterUtil.whitespaceOrQuote = Pattern.compile("[\\s\"]");
    }
}
