// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.json.impl.reader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.Reader;

class JsonLexer
{
    public static final int YYEOF = -1;
    private static final int ZZ_BUFFERSIZE = 16384;
    public static final int STRING = 2;
    public static final int YYINITIAL = 0;
    private static final int[] ZZ_LEXSTATE;
    private static final String ZZ_CMAP_PACKED = "\b\u0000\u0002\u0001\u0001\u0019\u0002\u0000\u0001\u0019\u0012\u0000\u0001\u0001\u0001\u0000\u0001\u0017\b\u0000\u0001\u0007\u0001\b\u0001\u0002\u0001\u0005\u0001\u001a\u0001\u0003\t\u0004\u0001\t\u0006\u0000\u0004\u001c\u0001\u0006\u0001\u001c\u0014\u0000\u0001\n\u0001\u0018\u0001\u000b\u0003\u0000\u0001\u0013\u0001\u001b\u0002\u001c\u0001\u0011\u0001\u0012\u0005\u0000\u0001\u0014\u0001\u0000\u0001\u0016\u0003\u0000\u0001\u000f\u0001\u0015\u0001\u000e\u0001\u0010\u0005\u0000\u0001\f\u0001\u0000\u0001\r\uff82\u0000";
    private static final char[] ZZ_CMAP;
    private static final int[] ZZ_ACTION;
    private static final String ZZ_ACTION_PACKED_0 = "\u0002\u0000\u0001\u0001\u0001\u0000\u0002\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0003\u0000\u0001\t\u0001\n\u0001\u000b\u0006\u0000\u0001\f\u0001\r\u0001\u0000\u0001\u000e\u0001\u000f\u0001\u0010\u0001\u0011\u0001\u0012\u0001\u0013\u0001\u0002\u0001\u0000\u0001\u0002\u0004\u0000\u0001\u0014\u0001\u0000\u0001\u0015\u0001\u0000\u0001\u0016\u0001\u0000\u0001\u0017";
    private static final int[] ZZ_ROWMAP;
    private static final String ZZ_ROWMAP_PACKED_0 = "\u0000\u0000\u0000\u001d\u0000:\u0000W\u0000t\u0000\u0091\u0000:\u0000:\u0000:\u0000:\u0000:\u0000:\u0000®\u0000\u00cb\u0000\u00e8\u0000:\u0000\u0105\u0000:\u0000\u0122\u0000\u013f\u0000\u015c\u0000\u0179\u0000\u0196\u0000\u01b3\u0000:\u0000:\u0000\u01d0\u0000:\u0000:\u0000:\u0000:\u0000:\u0000:\u0000\u01ed\u0000\u020a\u0000\u020a\u0000\u0227\u0000\u0244\u0000\u0261\u0000\u027e\u0000:\u0000\u029b\u0000:\u0000\u02b8\u0000:\u0000\u02d5\u0000:";
    private static final int[] ZZ_TRANS;
    private static final String ZZ_TRANS_PACKED_0 = "\u0001\u0000\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0003\u0000\u0001\u0007\u0001\b\u0001\t\u0001\n\u0001\u000b\u0001\f\u0001\r\u0003\u0000\u0001\u000e\u0003\u0000\u0001\u000f\u0001\u0010\u0001\u0000\u0001\u0003\u0003\u0000\u0017\u0011\u0001\u0012\u0001\u0013\u0001\u0000\u0003\u0011 \u0000\u0001\u0005\u0001\u0006\u001d\u0000\u0001\u0014\u0001\u0015\n\u0000\u0001\u0015\u000e\u0000\u0002\u0006\u0001\u0014\u0001\u0015\n\u0000\u0001\u0015\u001a\u0000\u0001\u0016 \u0000\u0001\u0017\u0019\u0000\u0001\u0018\f\u0000\u0017\u0011\u0003\u0000\u0003\u0011\u000e\u0000\u0001\u0019\u0001\u001a\u0001\u001b\u0001\u0000\u0001\u001c\u0003\u0000\u0001\u001d\u0001\u001e\u0001\u001f\u0001\u0000\u0001 \u0001!\u0004\u0000\u0002\"\u001a\u0000\u0001#\u0002$\u0002\u0000\u0001#%\u0000\u0001% \u0000\u0001&\u001c\u0000\u0001'\u000b\u0000\u0002(\u0001\u0000\u0001(\n\u0000\u0003(\u0007\u0000\u0002(\u0003\u0000\u0002\"\u0001\u0000\u0001\u0015\n\u0000\u0001\u0015\u000e\u0000\u0002$)\u0000\u0001) \u0000\u0001*\u001b\u0000\u0001+\u000b\u0000\u0002,\u0001\u0000\u0001,\n\u0000\u0003,\u0007\u0000\u0002,\u0011\u0000\u0001-\u000e\u0000\u0002.\u0001\u0000\u0001.\n\u0000\u0003.\u0007\u0000\u0002.\u0003\u0000\u0002/\u0001\u0000\u0001/\n\u0000\u0003/\u0007\u0000\u0002/";
    private static final int ZZ_UNKNOWN_ERROR = 0;
    private static final int ZZ_NO_MATCH = 1;
    private static final int ZZ_PUSHBACK_2BIG = 2;
    private static final String[] ZZ_ERROR_MSG;
    private static final int[] ZZ_ATTRIBUTE;
    private static final String ZZ_ATTRIBUTE_PACKED_0 = "\u0002\u0000\u0001\t\u0001\u0000\u0002\u0001\u0006\t\u0003\u0000\u0001\t\u0001\u0001\u0001\t\u0006\u0000\u0002\t\u0001\u0000\u0006\t\u0001\u0001\u0001\u0000\u0001\u0001\u0004\u0000\u0001\t\u0001\u0000\u0001\t\u0001\u0000\u0001\t\u0001\u0000\u0001\t";
    private Reader zzReader;
    private int zzState;
    private int zzLexicalState;
    private char[] zzBuffer;
    private int zzMarkedPos;
    private int zzCurrentPos;
    private int zzStartRead;
    private int zzEndRead;
    private int yyline;
    private int yychar;
    private int yycolumn;
    private boolean zzAtBOL;
    private boolean zzAtEOF;
    private boolean zzEOFDone;
    StringBuffer string;
    
    private static int[] zzUnpackAction() {
        final int[] result = new int[47];
        int offset = 0;
        offset = zzUnpackAction("\u0002\u0000\u0001\u0001\u0001\u0000\u0002\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0003\u0000\u0001\t\u0001\n\u0001\u000b\u0006\u0000\u0001\f\u0001\r\u0001\u0000\u0001\u000e\u0001\u000f\u0001\u0010\u0001\u0011\u0001\u0012\u0001\u0013\u0001\u0002\u0001\u0000\u0001\u0002\u0004\u0000\u0001\u0014\u0001\u0000\u0001\u0015\u0001\u0000\u0001\u0016\u0001\u0000\u0001\u0017", offset, result);
        return result;
    }
    
    private static int zzUnpackAction(final String packed, final int offset, final int[] result) {
        int i = 0;
        int j = offset;
        final int l = packed.length();
        while (i < l) {
            int count = packed.charAt(i++);
            final int value = packed.charAt(i++);
            do {
                result[j++] = value;
            } while (--count > 0);
        }
        return j;
    }
    
    private static int[] zzUnpackRowMap() {
        final int[] result = new int[47];
        int offset = 0;
        offset = zzUnpackRowMap("\u0000\u0000\u0000\u001d\u0000:\u0000W\u0000t\u0000\u0091\u0000:\u0000:\u0000:\u0000:\u0000:\u0000:\u0000®\u0000\u00cb\u0000\u00e8\u0000:\u0000\u0105\u0000:\u0000\u0122\u0000\u013f\u0000\u015c\u0000\u0179\u0000\u0196\u0000\u01b3\u0000:\u0000:\u0000\u01d0\u0000:\u0000:\u0000:\u0000:\u0000:\u0000:\u0000\u01ed\u0000\u020a\u0000\u020a\u0000\u0227\u0000\u0244\u0000\u0261\u0000\u027e\u0000:\u0000\u029b\u0000:\u0000\u02b8\u0000:\u0000\u02d5\u0000:", offset, result);
        return result;
    }
    
    private static int zzUnpackRowMap(final String packed, final int offset, final int[] result) {
        int i = 0;
        int j = offset;
        int high;
        for (int l = packed.length(); i < l; high = packed.charAt(i++) << 16, result[j++] = (high | packed.charAt(i++))) {}
        return j;
    }
    
    private static int[] zzUnpackTrans() {
        final int[] result = new int[754];
        int offset = 0;
        offset = zzUnpackTrans("\u0001\u0000\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0003\u0000\u0001\u0007\u0001\b\u0001\t\u0001\n\u0001\u000b\u0001\f\u0001\r\u0003\u0000\u0001\u000e\u0003\u0000\u0001\u000f\u0001\u0010\u0001\u0000\u0001\u0003\u0003\u0000\u0017\u0011\u0001\u0012\u0001\u0013\u0001\u0000\u0003\u0011 \u0000\u0001\u0005\u0001\u0006\u001d\u0000\u0001\u0014\u0001\u0015\n\u0000\u0001\u0015\u000e\u0000\u0002\u0006\u0001\u0014\u0001\u0015\n\u0000\u0001\u0015\u001a\u0000\u0001\u0016 \u0000\u0001\u0017\u0019\u0000\u0001\u0018\f\u0000\u0017\u0011\u0003\u0000\u0003\u0011\u000e\u0000\u0001\u0019\u0001\u001a\u0001\u001b\u0001\u0000\u0001\u001c\u0003\u0000\u0001\u001d\u0001\u001e\u0001\u001f\u0001\u0000\u0001 \u0001!\u0004\u0000\u0002\"\u001a\u0000\u0001#\u0002$\u0002\u0000\u0001#%\u0000\u0001% \u0000\u0001&\u001c\u0000\u0001'\u000b\u0000\u0002(\u0001\u0000\u0001(\n\u0000\u0003(\u0007\u0000\u0002(\u0003\u0000\u0002\"\u0001\u0000\u0001\u0015\n\u0000\u0001\u0015\u000e\u0000\u0002$)\u0000\u0001) \u0000\u0001*\u001b\u0000\u0001+\u000b\u0000\u0002,\u0001\u0000\u0001,\n\u0000\u0003,\u0007\u0000\u0002,\u0011\u0000\u0001-\u000e\u0000\u0002.\u0001\u0000\u0001.\n\u0000\u0003.\u0007\u0000\u0002.\u0003\u0000\u0002/\u0001\u0000\u0001/\n\u0000\u0003/\u0007\u0000\u0002/", offset, result);
        return result;
    }
    
    private static int zzUnpackTrans(final String packed, final int offset, final int[] result) {
        int i = 0;
        int j = offset;
        final int l = packed.length();
        while (i < l) {
            int count = packed.charAt(i++);
            int value = packed.charAt(i++);
            --value;
            do {
                result[j++] = value;
            } while (--count > 0);
        }
        return j;
    }
    
    private static int[] zzUnpackAttribute() {
        final int[] result = new int[47];
        int offset = 0;
        offset = zzUnpackAttribute("\u0002\u0000\u0001\t\u0001\u0000\u0002\u0001\u0006\t\u0003\u0000\u0001\t\u0001\u0001\u0001\t\u0006\u0000\u0002\t\u0001\u0000\u0006\t\u0001\u0001\u0001\u0000\u0001\u0001\u0004\u0000\u0001\t\u0001\u0000\u0001\t\u0001\u0000\u0001\t\u0001\u0000\u0001\t", offset, result);
        return result;
    }
    
    private static int zzUnpackAttribute(final String packed, final int offset, final int[] result) {
        int i = 0;
        int j = offset;
        final int l = packed.length();
        while (i < l) {
            int count = packed.charAt(i++);
            final int value = packed.charAt(i++);
            do {
                result[j++] = value;
            } while (--count > 0);
        }
        return j;
    }
    
    public int getCharOffset() {
        return this.yychar;
    }
    
    public int getLineNumber() {
        return this.yyline;
    }
    
    public int getColumn() {
        return this.yycolumn;
    }
    
    JsonLexer(final Reader in) {
        this.zzLexicalState = 0;
        this.zzBuffer = new char[16384];
        this.zzAtBOL = true;
        this.string = new StringBuffer();
        this.zzReader = in;
    }
    
    JsonLexer(final InputStream in) {
        this(new InputStreamReader(in));
    }
    
    private static char[] zzUnpackCMap(final String packed) {
        final char[] map = new char[65536];
        int i = 0;
        int j = 0;
        while (i < 92) {
            int count = packed.charAt(i++);
            final char value = packed.charAt(i++);
            do {
                map[j++] = value;
            } while (--count > 0);
        }
        return map;
    }
    
    private boolean zzRefill() throws IOException {
        if (this.zzStartRead > 0) {
            System.arraycopy(this.zzBuffer, this.zzStartRead, this.zzBuffer, 0, this.zzEndRead - this.zzStartRead);
            this.zzEndRead -= this.zzStartRead;
            this.zzCurrentPos -= this.zzStartRead;
            this.zzMarkedPos -= this.zzStartRead;
            this.zzStartRead = 0;
        }
        if (this.zzCurrentPos >= this.zzBuffer.length) {
            final char[] newBuffer = new char[this.zzCurrentPos * 2];
            System.arraycopy(this.zzBuffer, 0, newBuffer, 0, this.zzBuffer.length);
            this.zzBuffer = newBuffer;
        }
        final int numRead = this.zzReader.read(this.zzBuffer, this.zzEndRead, this.zzBuffer.length - this.zzEndRead);
        if (numRead > 0) {
            this.zzEndRead += numRead;
            return false;
        }
        if (numRead != 0) {
            return true;
        }
        final int c = this.zzReader.read();
        if (c == -1) {
            return true;
        }
        this.zzBuffer[this.zzEndRead++] = (char)c;
        return false;
    }
    
    public final void yyclose() throws IOException {
        this.zzAtEOF = true;
        this.zzEndRead = this.zzStartRead;
        if (this.zzReader != null) {
            this.zzReader.close();
        }
    }
    
    public final void yyreset(final Reader reader) {
        this.zzReader = reader;
        this.zzAtBOL = true;
        this.zzAtEOF = false;
        this.zzEOFDone = false;
        final boolean b = false;
        this.zzStartRead = (b ? 1 : 0);
        this.zzEndRead = (b ? 1 : 0);
        final boolean b2 = false;
        this.zzMarkedPos = (b2 ? 1 : 0);
        this.zzCurrentPos = (b2 ? 1 : 0);
        final boolean yyline = false;
        this.yycolumn = (yyline ? 1 : 0);
        this.yychar = (yyline ? 1 : 0);
        this.yyline = (yyline ? 1 : 0);
        this.zzLexicalState = 0;
    }
    
    public final int yystate() {
        return this.zzLexicalState;
    }
    
    public final void yybegin(final int newState) {
        this.zzLexicalState = newState;
    }
    
    public final String yytext() {
        return new String(this.zzBuffer, this.zzStartRead, this.zzMarkedPos - this.zzStartRead);
    }
    
    public final char yycharat(final int pos) {
        return this.zzBuffer[this.zzStartRead + pos];
    }
    
    public final int yylength() {
        return this.zzMarkedPos - this.zzStartRead;
    }
    
    private void zzScanError(final int errorCode) {
        String message;
        try {
            message = JsonLexer.ZZ_ERROR_MSG[errorCode];
        }
        catch (ArrayIndexOutOfBoundsException e) {
            message = JsonLexer.ZZ_ERROR_MSG[0];
        }
        throw new Error(message);
    }
    
    public void yypushback(final int number) {
        if (number > this.yylength()) {
            this.zzScanError(2);
        }
        this.zzMarkedPos -= number;
    }
    
    public JsonToken yylex() throws IOException {
        int zzEndReadL = this.zzEndRead;
        char[] zzBufferL = this.zzBuffer;
        final char[] zzCMapL = JsonLexer.ZZ_CMAP;
        final int[] zzTransL = JsonLexer.ZZ_TRANS;
        final int[] zzRowMapL = JsonLexer.ZZ_ROWMAP;
        final int[] zzAttrL = JsonLexer.ZZ_ATTRIBUTE;
        while (true) {
            int zzMarkedPosL = this.zzMarkedPos;
            this.yychar += zzMarkedPosL - this.zzStartRead;
            boolean zzR = false;
            for (int zzCurrentPosL = this.zzStartRead; zzCurrentPosL < zzMarkedPosL; ++zzCurrentPosL) {
                switch (zzBufferL[zzCurrentPosL]) {
                    case '\u000b':
                    case '\f':
                    case '\u0085':
                    case '\u2028':
                    case '\u2029': {
                        ++this.yyline;
                        zzR = false;
                        break;
                    }
                    case '\r': {
                        ++this.yyline;
                        zzR = true;
                        break;
                    }
                    case '\n': {
                        if (zzR) {
                            zzR = false;
                            break;
                        }
                        ++this.yyline;
                        break;
                    }
                    default: {
                        zzR = false;
                        break;
                    }
                }
            }
            if (zzR) {
                boolean zzPeek;
                if (zzMarkedPosL < zzEndReadL) {
                    zzPeek = (zzBufferL[zzMarkedPosL] == '\n');
                }
                else if (this.zzAtEOF) {
                    zzPeek = false;
                }
                else {
                    final boolean eof = this.zzRefill();
                    zzEndReadL = this.zzEndRead;
                    zzMarkedPosL = this.zzMarkedPos;
                    zzBufferL = this.zzBuffer;
                    zzPeek = (!eof && zzBufferL[zzMarkedPosL] == '\n');
                }
                if (zzPeek) {
                    --this.yyline;
                }
            }
            int zzAction = -1;
            final int n = zzMarkedPosL;
            this.zzStartRead = n;
            this.zzCurrentPos = n;
            int zzCurrentPosL = n;
            this.zzState = JsonLexer.ZZ_LEXSTATE[this.zzLexicalState];
            int zzInput;
            while (true) {
                if (zzCurrentPosL < zzEndReadL) {
                    zzInput = zzBufferL[zzCurrentPosL++];
                }
                else {
                    if (this.zzAtEOF) {
                        zzInput = -1;
                        break;
                    }
                    this.zzCurrentPos = zzCurrentPosL;
                    this.zzMarkedPos = zzMarkedPosL;
                    final boolean eof2 = this.zzRefill();
                    zzCurrentPosL = this.zzCurrentPos;
                    zzMarkedPosL = this.zzMarkedPos;
                    zzBufferL = this.zzBuffer;
                    zzEndReadL = this.zzEndRead;
                    if (eof2) {
                        zzInput = -1;
                        break;
                    }
                    zzInput = zzBufferL[zzCurrentPosL++];
                }
                final int zzNext = zzTransL[zzRowMapL[this.zzState] + zzCMapL[zzInput]];
                if (zzNext == -1) {
                    break;
                }
                this.zzState = zzNext;
                final int zzAttributes = zzAttrL[this.zzState];
                if ((zzAttributes & 0x1) != 0x1) {
                    continue;
                }
                zzAction = this.zzState;
                zzMarkedPosL = zzCurrentPosL;
                if ((zzAttributes & 0x8) == 0x8) {
                    break;
                }
            }
            this.zzMarkedPos = zzMarkedPosL;
            switch ((zzAction < 0) ? zzAction : JsonLexer.ZZ_ACTION[zzAction]) {
                case 23: {
                    this.string.append(Character.toChars(Integer.parseInt(this.yytext().substring(2), 16)));
                }
                case 24: {
                    continue;
                }
                case 15: {
                    this.string.append('\n');
                }
                case 25: {
                    continue;
                }
                case 6: {
                    return new JsonToken(4, this.yytext(), this.yyline, this.yychar, this.yychar + 1);
                }
                case 26: {
                    continue;
                }
                case 7: {
                    return new JsonToken(1, this.yytext(), this.yyline, this.yychar, this.yychar + 1);
                }
                case 27: {
                    continue;
                }
                case 3: {
                    return new JsonToken(6, this.yytext(), this.yyline, this.yychar, this.yychar + 1);
                }
                case 28: {
                    continue;
                }
                case 4: {
                    return new JsonToken(5, this.yytext(), this.yyline, this.yychar, this.yychar + 1);
                }
                case 29: {
                    continue;
                }
                case 19: {
                    this.string.append('\b');
                }
                case 30: {
                    continue;
                }
                case 8: {
                    return new JsonToken(2, this.yytext(), this.yyline, this.yychar, this.yychar + 1);
                }
                case 31: {
                    continue;
                }
                case 21: {
                    return new JsonToken(11, this.yytext(), this.yyline, this.yychar, this.yychar + this.yylength());
                }
                case 32: {
                    continue;
                }
                case 18: {
                    this.string.append('/');
                }
                case 33: {
                    continue;
                }
                case 17: {
                    this.string.append('\\');
                }
                case 34: {
                    continue;
                }
                case 22: {
                    return new JsonToken(10, this.yytext(), this.yyline, this.yychar, this.yychar + this.yylength());
                }
                case 35: {
                    continue;
                }
                case 9: {
                    this.string.setLength(0);
                    this.yybegin(2);
                }
                case 36: {
                    continue;
                }
                case 11: {
                    this.yybegin(0);
                    return new JsonToken(7, this.string.toString(), this.yyline, this.yychar, this.yychar + this.string.length());
                }
                case 37: {
                    continue;
                }
                case 2: {
                    return new JsonToken(8, this.yytext(), this.yyline, this.yychar, this.yychar + this.yylength());
                }
                case 38: {
                    continue;
                }
                case 20: {
                    return new JsonToken(9, this.yytext(), this.yyline, this.yychar, this.yychar + this.yylength());
                }
                case 39: {
                    continue;
                }
                case 13: {
                    this.string.append('\r');
                }
                case 40: {
                    continue;
                }
                case 10: {
                    this.string.append(this.yytext());
                }
                case 41: {
                    continue;
                }
                case 16: {
                    this.string.append('\"');
                }
                case 42: {
                    continue;
                }
                case 14: {
                    this.string.append('\f');
                }
                case 43: {
                    continue;
                }
                case 5: {
                    return new JsonToken(3, this.yytext(), this.yyline, this.yychar, this.yychar + 1);
                }
                case 44: {
                    continue;
                }
                case 12: {
                    this.string.append('\t');
                }
                case 45: {
                    continue;
                }
                case 1:
                case 46: {
                    continue;
                }
                default: {
                    if (zzInput == -1 && this.zzStartRead == this.zzCurrentPos) {
                        this.zzAtEOF = true;
                        return null;
                    }
                    this.zzScanError(1);
                    continue;
                }
            }
        }
    }
    
    static {
        ZZ_LEXSTATE = new int[] { 0, 0, 1, 1 };
        ZZ_CMAP = zzUnpackCMap("\b\u0000\u0002\u0001\u0001\u0019\u0002\u0000\u0001\u0019\u0012\u0000\u0001\u0001\u0001\u0000\u0001\u0017\b\u0000\u0001\u0007\u0001\b\u0001\u0002\u0001\u0005\u0001\u001a\u0001\u0003\t\u0004\u0001\t\u0006\u0000\u0004\u001c\u0001\u0006\u0001\u001c\u0014\u0000\u0001\n\u0001\u0018\u0001\u000b\u0003\u0000\u0001\u0013\u0001\u001b\u0002\u001c\u0001\u0011\u0001\u0012\u0005\u0000\u0001\u0014\u0001\u0000\u0001\u0016\u0003\u0000\u0001\u000f\u0001\u0015\u0001\u000e\u0001\u0010\u0005\u0000\u0001\f\u0001\u0000\u0001\r\uff82\u0000");
        ZZ_ACTION = zzUnpackAction();
        ZZ_ROWMAP = zzUnpackRowMap();
        ZZ_TRANS = zzUnpackTrans();
        ZZ_ERROR_MSG = new String[] { "Unkown internal scanner error", "Error: could not match input", "Error: pushback value was too large" };
        ZZ_ATTRIBUTE = zzUnpackAttribute();
    }
}
