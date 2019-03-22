// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.uritemplate.parse;

import com.google.common.base.CharMatcher;

final class CharMatchers
{
    static final CharMatcher LITERALS;
    static final CharMatcher PERCENT;
    static final CharMatcher HEXDIGIT;
    
    static {
        PERCENT = CharMatcher.is('%');
        HEXDIGIT = CharMatcher.inRange('0', '9').or(CharMatcher.inRange('a', 'f')).or(CharMatcher.inRange('A', 'F')).precomputed();
        final CharMatcher ctl = CharMatcher.JAVA_ISO_CONTROL;
        final CharMatcher spc = CharMatcher.WHITESPACE;
        final CharMatcher other = CharMatcher.anyOf("\"'<>\\^`{|}");
        LITERALS = ctl.or(spc).or(other).negate().precomputed();
    }
}
