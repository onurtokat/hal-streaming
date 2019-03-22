// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.json.impl.reader;

class JsonToken
{
    public static final int START_OBJECT = 1;
    public static final int END_OBJECT = 2;
    public static final int START_ARRAY = 3;
    public static final int END_ARRAY = 4;
    public static final int COLON = 5;
    public static final int COMMA = 6;
    public static final int STRING = 7;
    public static final int NUMBER = 8;
    public static final int TRUE = 9;
    public static final int FALSE = 10;
    public static final int NULL = 11;
    public int tokenType;
    public String tokenText;
    public int line;
    public int charBegin;
    public int charEnd;
    
    JsonToken(final int tokenType, final String text, final int line, final int charBegin, final int charEnd) {
        this.tokenType = tokenType;
        this.tokenText = text;
        this.line = line;
        this.charBegin = charBegin;
        this.charEnd = charEnd;
    }
    
    @Override
    public String toString() {
        return "(token|" + this.tokenType + "|" + this.tokenText + ")";
    }
}
