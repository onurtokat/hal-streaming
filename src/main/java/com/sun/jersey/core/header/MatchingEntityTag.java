// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.core.header;

import java.util.Collections;
import java.text.ParseException;
import com.sun.jersey.core.header.reader.HttpHeaderReader;
import java.util.Set;
import javax.ws.rs.core.EntityTag;

public class MatchingEntityTag extends EntityTag
{
    public static final Set<MatchingEntityTag> ANY_MATCH;
    
    public MatchingEntityTag(final String value) {
        super(value, false);
    }
    
    public MatchingEntityTag(final String value, final boolean weak) {
        super(value, weak);
    }
    
    public static MatchingEntityTag valueOf(final HttpHeaderReader reader) throws ParseException {
        final HttpHeaderReader.Event e = reader.next(false);
        if (e == HttpHeaderReader.Event.QuotedString) {
            return new MatchingEntityTag(reader.getEventValue());
        }
        if (e == HttpHeaderReader.Event.Token) {
            final String v = reader.getEventValue();
            if (v.equals("W")) {
                reader.nextSeparator('/');
                return new MatchingEntityTag(reader.nextQuotedString(), true);
            }
        }
        throw new ParseException("Error parsing entity tag", reader.getIndex());
    }
    
    static {
        ANY_MATCH = Collections.emptySet();
    }
}
