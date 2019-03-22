// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.core.header.reader;

import java.text.ParseException;

final class HttpHeaderReaderImpl extends HttpHeaderReader
{
    private static final int TOKEN = 0;
    private static final int QUOTED_STRING = 1;
    private static final int COMMENT = 2;
    private static final int SEPARATOR = 3;
    private static final int CONTROL = 4;
    private static final char[] WHITE_SPACE;
    private static final char[] SEPARATORS;
    private static final int[] EVENT_TABLE;
    private static final boolean[] IS_WHITE_SPACE;
    private static final boolean[] IS_TOKEN;
    private String header;
    private boolean processComments;
    private int index;
    private int length;
    private Event event;
    private String value;
    
    private static int[] createEventTable() {
        final int[] table = new int[128];
        for (int i = 0; i < 127; ++i) {
            table[i] = 0;
        }
        for (final char c : HttpHeaderReaderImpl.SEPARATORS) {
            table[c] = 3;
        }
        table[40] = 2;
        table[34] = 1;
        for (int i = 0; i < 32; ++i) {
            table[i] = 4;
        }
        table[127] = 4;
        for (final char c : HttpHeaderReaderImpl.WHITE_SPACE) {
            table[c] = -1;
        }
        return table;
    }
    
    private static boolean[] createWhiteSpaceTable() {
        final boolean[] table = new boolean[128];
        for (final char c : HttpHeaderReaderImpl.WHITE_SPACE) {
            table[c] = true;
        }
        return table;
    }
    
    private static boolean[] createTokenTable() {
        final boolean[] table = new boolean[128];
        for (int i = 0; i < 128; ++i) {
            table[i] = (HttpHeaderReaderImpl.EVENT_TABLE[i] == 0);
        }
        return table;
    }
    
    public HttpHeaderReaderImpl(final String header, final boolean processComments) {
        this.header = ((header == null) ? "" : header);
        this.processComments = processComments;
        this.index = 0;
        this.length = this.header.length();
    }
    
    public HttpHeaderReaderImpl(final String header) {
        this(header, false);
    }
    
    @Override
    public boolean hasNext() {
        return this.skipWhiteSpace();
    }
    
    @Override
    public boolean hasNextSeparator(final char separator, final boolean skipWhiteSpace) {
        if (skipWhiteSpace) {
            this.skipWhiteSpace();
        }
        if (this.index >= this.length) {
            return false;
        }
        final char c = this.header.charAt(this.index);
        return HttpHeaderReaderImpl.EVENT_TABLE[c] == 3 && c == separator;
    }
    
    @Override
    public String nextSeparatedString(final char startSeparator, final char endSeparator) throws ParseException {
        this.nextSeparator(startSeparator);
        final int start = this.index;
        while (this.index < this.length && this.header.charAt(this.index) != endSeparator) {
            ++this.index;
        }
        if (start == this.index) {
            throw new ParseException("No characters between the separators '" + startSeparator + "' and '" + endSeparator + "'", this.index);
        }
        if (this.index == this.length) {
            throw new ParseException("No end separator '" + endSeparator + "'", this.index);
        }
        this.event = Event.Token;
        return this.value = this.header.substring(start, this.index++);
    }
    
    @Override
    public Event next() throws ParseException {
        return this.event = this.process(this.getNextCharacter(true));
    }
    
    @Override
    public Event next(final boolean skipWhiteSpace) throws ParseException {
        return this.event = this.process(this.getNextCharacter(skipWhiteSpace));
    }
    
    @Override
    public Event getEvent() {
        return this.event;
    }
    
    @Override
    public String getEventValue() {
        return this.value;
    }
    
    @Override
    public String getRemainder() {
        return (this.index < this.length) ? this.header.substring(this.index) : null;
    }
    
    @Override
    public int getIndex() {
        return this.index;
    }
    
    private boolean skipWhiteSpace() {
        while (this.index < this.length) {
            if (!this.isWhiteSpace(this.header.charAt(this.index))) {
                return true;
            }
            ++this.index;
        }
        return false;
    }
    
    private char getNextCharacter(final boolean skipWhiteSpace) throws ParseException {
        if (skipWhiteSpace) {
            this.skipWhiteSpace();
        }
        if (this.index >= this.length) {
            throw new ParseException("End of header", this.index);
        }
        return this.header.charAt(this.index);
    }
    
    private Event process(final char c) throws ParseException {
        if (c > '\u007f') {
            ++this.index;
            return Event.Control;
        }
        switch (HttpHeaderReaderImpl.EVENT_TABLE[c]) {
            case 0: {
                final int start = this.index;
                ++this.index;
                while (this.index < this.length && this.isToken(this.header.charAt(this.index))) {
                    ++this.index;
                }
                this.value = this.header.substring(start, this.index);
                return Event.Token;
            }
            case 1: {
                this.processQuotedString();
                return Event.QuotedString;
            }
            case 2: {
                if (!this.processComments) {
                    throw new ParseException("Comments are not allowed", this.index);
                }
                this.processComment();
                return Event.Comment;
            }
            case 3: {
                ++this.index;
                this.value = String.valueOf(c);
                return Event.Separator;
            }
            case 4: {
                ++this.index;
                this.value = String.valueOf(c);
                return Event.Control;
            }
            default: {
                throw new ParseException("White space not allowed", this.index);
            }
        }
    }
    
    private void processComment() throws ParseException {
        boolean filter = false;
        final int start = ++this.index;
        int nesting = 1;
        while (nesting > 0 && this.index < this.length) {
            final char c = this.header.charAt(this.index);
            if (c == '\\') {
                ++this.index;
                filter = true;
            }
            else if (c == '\r') {
                filter = true;
            }
            else if (c == '(') {
                ++nesting;
            }
            else if (c == ')') {
                --nesting;
            }
            ++this.index;
        }
        if (nesting != 0) {
            throw new ParseException("Unbalanced comments", this.index);
        }
        this.value = (filter ? filterToken(this.header, start, this.index - 1) : this.header.substring(start, this.index - 1));
    }
    
    private void processQuotedString() throws ParseException {
        boolean filter = false;
        final int start = ++this.index;
        while (this.index < this.length) {
            final char c = this.header.charAt(this.index);
            if (c == '\\') {
                ++this.index;
                filter = true;
            }
            else if (c == '\r') {
                filter = true;
            }
            else if (c == '\"') {
                this.value = (filter ? filterToken(this.header, start, this.index) : this.header.substring(start, this.index));
                ++this.index;
                return;
            }
            ++this.index;
        }
        throw new ParseException("Unbalanced quoted string", this.index);
    }
    
    private boolean isWhiteSpace(final char c) {
        return c < '\u0080' && HttpHeaderReaderImpl.IS_WHITE_SPACE[c];
    }
    
    private boolean isToken(final char c) {
        return c < '\u0080' && HttpHeaderReaderImpl.IS_TOKEN[c];
    }
    
    private static String filterToken(final String s, final int start, final int end) {
        final StringBuffer sb = new StringBuffer();
        boolean gotEscape = false;
        boolean gotCR = false;
        for (int i = start; i < end; ++i) {
            final char c = s.charAt(i);
            if (c == '\n' && gotCR) {
                gotCR = false;
            }
            else {
                gotCR = false;
                if (!gotEscape) {
                    if (c == '\\') {
                        gotEscape = true;
                    }
                    else if (c == '\r') {
                        gotCR = true;
                    }
                    else {
                        sb.append(c);
                    }
                }
                else {
                    sb.append(c);
                    gotEscape = false;
                }
            }
        }
        return sb.toString();
    }
    
    static {
        WHITE_SPACE = new char[] { '\t', '\r', '\n', ' ' };
        SEPARATORS = new char[] { '(', ')', '<', '>', '@', ',', ';', ':', '\\', '\"', '/', '[', ']', '?', '=', '{', '}', ' ', '\t' };
        EVENT_TABLE = createEventTable();
        IS_WHITE_SPACE = createWhiteSpaceTable();
        IS_TOKEN = createTokenTable();
    }
}
