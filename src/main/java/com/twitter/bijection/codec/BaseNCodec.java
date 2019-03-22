// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection.codec;

import java.util.Arrays;
import java.io.UnsupportedEncodingException;

public abstract class BaseNCodec
{
    static final int EOF = -1;
    public static final int MIME_CHUNK_SIZE = 76;
    public static final int PEM_CHUNK_SIZE = 64;
    private static final int DEFAULT_BUFFER_RESIZE_FACTOR = 2;
    private static final int DEFAULT_BUFFER_SIZE = 8192;
    protected static final int MASK_8BITS = 255;
    protected static final byte PAD_DEFAULT = 61;
    protected final byte PAD = 61;
    private final int unencodedBlockSize;
    private final int encodedBlockSize;
    protected final int lineLength;
    private final int chunkSeparatorLength;
    
    protected BaseNCodec(final int unencodedBlockSize, final int encodedBlockSize, final int n, final int chunkSeparatorLength) {
        this.unencodedBlockSize = unencodedBlockSize;
        this.encodedBlockSize = encodedBlockSize;
        this.lineLength = ((n > 0 && chunkSeparatorLength > 0) ? (n / encodedBlockSize * encodedBlockSize) : 0);
        this.chunkSeparatorLength = chunkSeparatorLength;
    }
    
    boolean hasData(final Context context) {
        return context.buffer != null;
    }
    
    int available(final Context context) {
        return (context.buffer != null) ? (context.pos - context.readPos) : 0;
    }
    
    protected int getDefaultBufferSize() {
        return 8192;
    }
    
    private byte[] resizeBuffer(final Context context) {
        if (context.buffer == null) {
            context.buffer = new byte[this.getDefaultBufferSize()];
            context.pos = 0;
            context.readPos = 0;
        }
        else {
            final byte[] buffer = new byte[context.buffer.length * 2];
            System.arraycopy(context.buffer, 0, buffer, 0, context.buffer.length);
            context.buffer = buffer;
        }
        return context.buffer;
    }
    
    protected byte[] ensureBufferSize(final int n, final Context context) {
        if (context.buffer == null || context.buffer.length < context.pos + n) {
            return this.resizeBuffer(context);
        }
        return context.buffer;
    }
    
    int readResults(final byte[] array, final int n, final int n2, final Context context) {
        if (context.buffer != null) {
            final int min = Math.min(this.available(context), n2);
            System.arraycopy(context.buffer, context.readPos, array, n, min);
            context.readPos += min;
            if (context.readPos >= context.pos) {
                context.buffer = null;
            }
            return min;
        }
        return context.eof ? -1 : 0;
    }
    
    protected static boolean isWhiteSpace(final byte b) {
        switch (b) {
            case 9:
            case 10:
            case 13:
            case 32: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public String encodeToString(final byte[] array) {
        return newUtf8(this.encode(array));
    }
    
    protected static String newUtf8(final byte[] array) {
        try {
            return (array == null) ? null : new String(array, "UTF-8");
        }
        catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("UTF-8 Not supported on this platform");
        }
    }
    
    protected static byte[] getBytesUtf8(final String s) {
        try {
            return (byte[])((s == null) ? null : s.getBytes("UTF-8"));
        }
        catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("UTF-8 Not supported on this platform");
        }
    }
    
    public String encodeAsString(final byte[] array) {
        return newUtf8(this.encode(array));
    }
    
    public byte[] decode(final String s) {
        return this.decode(getBytesUtf8(s));
    }
    
    public byte[] decode(final byte[] array) {
        if (array == null || array.length == 0) {
            return array;
        }
        final Context context = new Context();
        this.decode(array, 0, array.length, context);
        this.decode(array, 0, -1, context);
        final byte[] array2 = new byte[context.pos];
        this.readResults(array2, 0, array2.length, context);
        return array2;
    }
    
    public byte[] encode(final byte[] array) {
        if (array == null || array.length == 0) {
            return array;
        }
        final Context context = new Context();
        this.encode(array, 0, array.length, context);
        this.encode(array, 0, -1, context);
        final byte[] array2 = new byte[context.pos - context.readPos];
        this.readResults(array2, 0, array2.length, context);
        return array2;
    }
    
    abstract void encode(final byte[] p0, final int p1, final int p2, final Context p3);
    
    abstract void decode(final byte[] p0, final int p1, final int p2, final Context p3);
    
    protected abstract boolean isInAlphabet(final byte p0);
    
    public boolean isInAlphabet(final byte[] array, final boolean b) {
        for (int i = 0; i < array.length; ++i) {
            if (!this.isInAlphabet(array[i]) && (!b || (array[i] != 61 && !isWhiteSpace(array[i])))) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isInAlphabet(final String s) {
        return this.isInAlphabet(getBytesUtf8(s), true);
    }
    
    protected boolean containsAlphabetOrPad(final byte[] array) {
        if (array == null) {
            return false;
        }
        for (final byte b : array) {
            if (61 == b || this.isInAlphabet(b)) {
                return true;
            }
        }
        return false;
    }
    
    public long getEncodedLength(final byte[] array) {
        long n = (array.length + this.unencodedBlockSize - 1) / this.unencodedBlockSize * this.encodedBlockSize;
        if (this.lineLength > 0) {
            n += (n + this.lineLength - 1L) / this.lineLength * this.chunkSeparatorLength;
        }
        return n;
    }
    
    static class Context
    {
        int ibitWorkArea;
        long lbitWorkArea;
        byte[] buffer;
        int pos;
        int readPos;
        boolean eof;
        int currentLinePos;
        int modulus;
        
        @Override
        public String toString() {
            return String.format("%s[buffer=%s, currentLinePos=%s, eof=%s, ibitWorkArea=%s, lbitWorkArea=%s, modulus=%s, pos=%s, readPos=%s]", this.getClass().getSimpleName(), Arrays.toString(this.buffer), this.currentLinePos, this.eof, this.ibitWorkArea, this.lbitWorkArea, this.modulus, this.pos, this.readPos);
        }
    }
}
