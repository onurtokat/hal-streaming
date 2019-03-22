// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jackson.jsonpointer;

import com.google.common.collect.ImmutableList;
import com.github.fge.msgsimple.load.MessageBundleLoader;
import com.github.fge.msgsimple.load.MessageBundles;
import java.nio.CharBuffer;
import java.util.List;
import com.github.fge.msgsimple.bundle.MessageBundle;
import javax.annotation.concurrent.Immutable;

@Immutable
public final class ReferenceToken
{
    private static final MessageBundle BUNDLE;
    private static final char ESCAPE = '~';
    private static final List<Character> ENCODED;
    private static final List<Character> DECODED;
    private final String cooked;
    private final String raw;
    
    private ReferenceToken(final String cooked, final String raw) {
        this.cooked = cooked;
        this.raw = raw;
    }
    
    public static ReferenceToken fromCooked(final String cooked) throws JsonPointerException {
        ReferenceToken.BUNDLE.checkNotNull(cooked, "nullInput");
        return new ReferenceToken(cooked, asRaw(cooked));
    }
    
    public static ReferenceToken fromRaw(final String raw) {
        ReferenceToken.BUNDLE.checkNotNull(raw, "nullInput");
        return new ReferenceToken(asCooked(raw), raw);
    }
    
    public static ReferenceToken fromInt(final int index) {
        final String s = Integer.toString(index);
        return new ReferenceToken(s, s);
    }
    
    public String getRaw() {
        return this.raw;
    }
    
    @Override
    public int hashCode() {
        return this.raw.hashCode();
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final ReferenceToken other = (ReferenceToken)obj;
        return this.raw.equals(other.raw);
    }
    
    @Override
    public String toString() {
        return this.cooked;
    }
    
    private static String asRaw(final String cooked) throws JsonPointerException {
        final StringBuilder raw = new StringBuilder(cooked.length());
        final CharBuffer buffer = CharBuffer.wrap(cooked);
        boolean inEscape = false;
        while (buffer.hasRemaining()) {
            final char c = buffer.get();
            if (inEscape) {
                appendEscaped(raw, c);
                inEscape = false;
            }
            else if (c == '~') {
                inEscape = true;
            }
            else {
                raw.append(c);
            }
        }
        if (inEscape) {
            throw new JsonPointerException(ReferenceToken.BUNDLE.getMessage("emptyEscape"));
        }
        return raw.toString();
    }
    
    private static void appendEscaped(final StringBuilder sb, final char c) throws JsonPointerException {
        final int index = ReferenceToken.ENCODED.indexOf(c);
        if (index == -1) {
            throw new JsonPointerException(ReferenceToken.BUNDLE.getMessage("illegalEscape"));
        }
        sb.append(ReferenceToken.DECODED.get(index));
    }
    
    private static String asCooked(final String raw) {
        final StringBuilder cooked = new StringBuilder(raw.length());
        final CharBuffer buffer = CharBuffer.wrap(raw);
        while (buffer.hasRemaining()) {
            final char c = buffer.get();
            final int index = ReferenceToken.DECODED.indexOf(c);
            if (index != -1) {
                cooked.append('~').append(ReferenceToken.ENCODED.get(index));
            }
            else {
                cooked.append(c);
            }
        }
        return cooked.toString();
    }
    
    static {
        BUNDLE = MessageBundles.getBundle(JsonPointerMessages.class);
        ENCODED = ImmutableList.of('0', '1');
        DECODED = ImmutableList.of('~', '/');
    }
}
