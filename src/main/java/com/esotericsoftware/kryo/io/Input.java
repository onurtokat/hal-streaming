// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.kryo.io;

import java.io.IOException;
import com.esotericsoftware.kryo.KryoException;
import java.io.InputStream;

public class Input extends InputStream
{
    private byte[] buffer;
    private int capacity;
    private int position;
    private int limit;
    private int total;
    private char[] chars;
    private InputStream inputStream;
    
    public Input() {
        this.chars = new char[32];
    }
    
    public Input(final int bufferSize) {
        this.chars = new char[32];
        this.capacity = bufferSize;
        this.buffer = new byte[bufferSize];
    }
    
    public Input(final byte[] buffer) {
        this.chars = new char[32];
        this.setBuffer(buffer, 0, buffer.length);
    }
    
    public Input(final byte[] buffer, final int offset, final int count) {
        this.chars = new char[32];
        this.setBuffer(buffer, offset, count);
    }
    
    public Input(final InputStream inputStream) {
        this(4096);
        if (inputStream == null) {
            throw new IllegalArgumentException("inputStream cannot be null.");
        }
        this.inputStream = inputStream;
    }
    
    public Input(final InputStream inputStream, final int bufferSize) {
        this(bufferSize);
        if (inputStream == null) {
            throw new IllegalArgumentException("inputStream cannot be null.");
        }
        this.inputStream = inputStream;
    }
    
    public void setBuffer(final byte[] bytes) {
        this.setBuffer(bytes, 0, bytes.length);
    }
    
    public void setBuffer(final byte[] bytes, final int offset, final int count) {
        if (bytes == null) {
            throw new IllegalArgumentException("bytes cannot be null.");
        }
        this.buffer = bytes;
        this.position = offset;
        this.limit = count;
        this.capacity = bytes.length;
        this.total = 0;
        this.inputStream = null;
    }
    
    public byte[] getBuffer() {
        return this.buffer;
    }
    
    public InputStream getInputStream() {
        return this.inputStream;
    }
    
    public void setInputStream(final InputStream inputStream) {
        this.inputStream = inputStream;
        this.limit = 0;
        this.rewind();
    }
    
    public int total() {
        return this.total + this.position;
    }
    
    public void setTotal(final int total) {
        this.total = total;
    }
    
    public int position() {
        return this.position;
    }
    
    public void setPosition(final int position) {
        this.position = position;
    }
    
    public int limit() {
        return this.limit;
    }
    
    public void setLimit(final int limit) {
        this.limit = limit;
    }
    
    public void rewind() {
        this.position = 0;
        this.total = 0;
    }
    
    public void skip(int count) throws KryoException {
        int skipCount = Math.min(this.limit - this.position, count);
        while (true) {
            this.position += skipCount;
            count -= skipCount;
            if (count == 0) {
                break;
            }
            skipCount = Math.min(count, this.capacity);
            this.require(skipCount);
        }
    }
    
    protected int fill(final byte[] buffer, final int offset, final int count) throws KryoException {
        if (this.inputStream == null) {
            return -1;
        }
        try {
            return this.inputStream.read(buffer, offset, count);
        }
        catch (IOException ex) {
            throw new KryoException(ex);
        }
    }
    
    private int require(final int required) throws KryoException {
        int remaining = this.limit - this.position;
        if (remaining >= required) {
            return remaining;
        }
        if (required > this.capacity) {
            throw new KryoException("Buffer too small: capacity: " + this.capacity + ", required: " + required);
        }
        int count = this.fill(this.buffer, this.limit, this.capacity - this.limit);
        if (count == -1) {
            throw new KryoException("Buffer underflow.");
        }
        remaining += count;
        if (remaining >= required) {
            this.limit += count;
            return remaining;
        }
        System.arraycopy(this.buffer, this.position, this.buffer, 0, remaining);
        this.total += this.position;
        this.position = 0;
        do {
            count = this.fill(this.buffer, remaining, this.capacity - remaining);
            if (count == -1) {
                if (remaining >= required) {
                    break;
                }
                throw new KryoException("Buffer underflow.");
            }
            else {
                remaining += count;
            }
        } while (remaining < required);
        return this.limit = remaining;
    }
    
    private int optional(int optional) throws KryoException {
        int remaining = this.limit - this.position;
        if (remaining >= optional) {
            return optional;
        }
        optional = Math.min(optional, this.capacity);
        int count = this.fill(this.buffer, this.limit, this.capacity - this.limit);
        if (count == -1) {
            return (remaining == 0) ? -1 : Math.min(remaining, optional);
        }
        remaining += count;
        if (remaining >= optional) {
            this.limit += count;
            return optional;
        }
        System.arraycopy(this.buffer, this.position, this.buffer, 0, remaining);
        this.total += this.position;
        this.position = 0;
        do {
            count = this.fill(this.buffer, remaining, this.capacity - remaining);
            if (count == -1) {
                break;
            }
            remaining += count;
        } while (remaining < optional);
        this.limit = remaining;
        return (remaining == 0) ? -1 : Math.min(remaining, optional);
    }
    
    public boolean eof() {
        return this.optional(1) == 0;
    }
    
    public int available() throws IOException {
        return this.limit - this.position + ((null != this.inputStream) ? this.inputStream.available() : 0);
    }
    
    public int read() throws KryoException {
        if (this.optional(1) == 0) {
            return -1;
        }
        return this.buffer[this.position++] & 0xFF;
    }
    
    public int read(final byte[] bytes) throws KryoException {
        return this.read(bytes, 0, bytes.length);
    }
    
    public int read(final byte[] bytes, int offset, int count) throws KryoException {
        if (bytes == null) {
            throw new IllegalArgumentException("bytes cannot be null.");
        }
        final int startingCount = count;
        int copyCount = Math.min(this.limit - this.position, count);
        do {
            System.arraycopy(this.buffer, this.position, bytes, offset, copyCount);
            this.position += copyCount;
            count -= copyCount;
            if (count == 0) {
                break;
            }
            offset += copyCount;
            copyCount = this.optional(count);
            if (copyCount != -1) {
                continue;
            }
            if (startingCount == count) {
                return -1;
            }
            break;
        } while (this.position != this.limit);
        return startingCount - count;
    }
    
    public long skip(final long count) throws KryoException {
        int skip;
        for (long remaining = count; remaining > 0L; remaining -= skip) {
            skip = Math.max(Integer.MAX_VALUE, (int)remaining);
            this.skip(skip);
        }
        return count;
    }
    
    public void close() throws KryoException {
        if (this.inputStream != null) {
            try {
                this.inputStream.close();
            }
            catch (IOException ex) {}
        }
    }
    
    public byte readByte() throws KryoException {
        this.require(1);
        return this.buffer[this.position++];
    }
    
    public int readByteUnsigned() throws KryoException {
        this.require(1);
        return this.buffer[this.position++] & 0xFF;
    }
    
    public byte[] readBytes(final int length) throws KryoException {
        final byte[] bytes = new byte[length];
        this.readBytes(bytes, 0, length);
        return bytes;
    }
    
    public void readBytes(final byte[] bytes) throws KryoException {
        this.readBytes(bytes, 0, bytes.length);
    }
    
    public void readBytes(final byte[] bytes, int offset, int count) throws KryoException {
        if (bytes == null) {
            throw new IllegalArgumentException("bytes cannot be null.");
        }
        int copyCount = Math.min(this.limit - this.position, count);
        while (true) {
            System.arraycopy(this.buffer, this.position, bytes, offset, copyCount);
            this.position += copyCount;
            count -= copyCount;
            if (count == 0) {
                break;
            }
            offset += copyCount;
            copyCount = Math.min(count, this.capacity);
            this.require(copyCount);
        }
    }
    
    public int readInt() throws KryoException {
        this.require(4);
        final byte[] buffer = this.buffer;
        final int position = this.position;
        this.position = position + 4;
        return (buffer[position] & 0xFF) << 24 | (buffer[position + 1] & 0xFF) << 16 | (buffer[position + 2] & 0xFF) << 8 | (buffer[position + 3] & 0xFF);
    }
    
    public int readInt(final boolean optimizePositive) throws KryoException {
        if (this.require(1) < 5) {
            return this.readInt_slow(optimizePositive);
        }
        int b = this.buffer[this.position++];
        int result = b & 0x7F;
        if ((b & 0x80) != 0x0) {
            final byte[] buffer = this.buffer;
            b = buffer[this.position++];
            result |= (b & 0x7F) << 7;
            if ((b & 0x80) != 0x0) {
                b = buffer[this.position++];
                result |= (b & 0x7F) << 14;
                if ((b & 0x80) != 0x0) {
                    b = buffer[this.position++];
                    result |= (b & 0x7F) << 21;
                    if ((b & 0x80) != 0x0) {
                        b = buffer[this.position++];
                        result |= (b & 0x7F) << 28;
                    }
                }
            }
        }
        return optimizePositive ? result : (result >>> 1 ^ -(result & 0x1));
    }
    
    private int readInt_slow(final boolean optimizePositive) {
        int b = this.buffer[this.position++];
        int result = b & 0x7F;
        if ((b & 0x80) != 0x0) {
            this.require(1);
            final byte[] buffer = this.buffer;
            b = buffer[this.position++];
            result |= (b & 0x7F) << 7;
            if ((b & 0x80) != 0x0) {
                this.require(1);
                b = buffer[this.position++];
                result |= (b & 0x7F) << 14;
                if ((b & 0x80) != 0x0) {
                    this.require(1);
                    b = buffer[this.position++];
                    result |= (b & 0x7F) << 21;
                    if ((b & 0x80) != 0x0) {
                        this.require(1);
                        b = buffer[this.position++];
                        result |= (b & 0x7F) << 28;
                    }
                }
            }
        }
        return optimizePositive ? result : (result >>> 1 ^ -(result & 0x1));
    }
    
    public boolean canReadInt() throws KryoException {
        if (this.limit - this.position >= 5) {
            return true;
        }
        if (this.optional(5) <= 0) {
            return false;
        }
        int p = this.position;
        return (this.buffer[p++] & 0x80) == 0x0 || (p != this.limit && ((this.buffer[p++] & 0x80) == 0x0 || (p != this.limit && ((this.buffer[p++] & 0x80) == 0x0 || (p != this.limit && ((this.buffer[p++] & 0x80) == 0x0 || p != this.limit))))));
    }
    
    public boolean canReadLong() throws KryoException {
        if (this.limit - this.position >= 9) {
            return true;
        }
        if (this.optional(5) <= 0) {
            return false;
        }
        int p = this.position;
        return (this.buffer[p++] & 0x80) == 0x0 || (p != this.limit && ((this.buffer[p++] & 0x80) == 0x0 || (p != this.limit && ((this.buffer[p++] & 0x80) == 0x0 || (p != this.limit && ((this.buffer[p++] & 0x80) == 0x0 || (p != this.limit && ((this.buffer[p++] & 0x80) == 0x0 || (p != this.limit && ((this.buffer[p++] & 0x80) == 0x0 || (p != this.limit && ((this.buffer[p++] & 0x80) == 0x0 || (p != this.limit && ((this.buffer[p++] & 0x80) == 0x0 || p != this.limit))))))))))))));
    }
    
    public String readString() {
        final int available = this.require(1);
        final int b = this.buffer[this.position++];
        if ((b & 0x80) == 0x0) {
            return this.readAscii();
        }
        int charCount = (available >= 5) ? this.readUtf8Length(b) : this.readUtf8Length_slow(b);
        switch (charCount) {
            case 0: {
                return null;
            }
            case 1: {
                return "";
            }
            default: {
                --charCount;
                if (this.chars.length < charCount) {
                    this.chars = new char[charCount];
                }
                this.readUtf8(charCount);
                return new String(this.chars, 0, charCount);
            }
        }
    }
    
    private int readUtf8Length(int b) {
        int result = b & 0x3F;
        if ((b & 0x40) != 0x0) {
            final byte[] buffer = this.buffer;
            b = buffer[this.position++];
            result |= (b & 0x7F) << 6;
            if ((b & 0x80) != 0x0) {
                b = buffer[this.position++];
                result |= (b & 0x7F) << 13;
                if ((b & 0x80) != 0x0) {
                    b = buffer[this.position++];
                    result |= (b & 0x7F) << 20;
                    if ((b & 0x80) != 0x0) {
                        b = buffer[this.position++];
                        result |= (b & 0x7F) << 27;
                    }
                }
            }
        }
        return result;
    }
    
    private int readUtf8Length_slow(int b) {
        int result = b & 0x3F;
        if ((b & 0x40) != 0x0) {
            this.require(1);
            final byte[] buffer = this.buffer;
            b = buffer[this.position++];
            result |= (b & 0x7F) << 6;
            if ((b & 0x80) != 0x0) {
                this.require(1);
                b = buffer[this.position++];
                result |= (b & 0x7F) << 13;
                if ((b & 0x80) != 0x0) {
                    this.require(1);
                    b = buffer[this.position++];
                    result |= (b & 0x7F) << 20;
                    if ((b & 0x80) != 0x0) {
                        this.require(1);
                        b = buffer[this.position++];
                        result |= (b & 0x7F) << 27;
                    }
                }
            }
        }
        return result;
    }
    
    private void readUtf8(final int charCount) {
        final byte[] buffer = this.buffer;
        final char[] chars = this.chars;
        int charIndex = 0;
        final int count = Math.min(this.require(1), charCount);
        int position = this.position;
        while (charIndex < count) {
            final int b = buffer[position++];
            if (b < 0) {
                --position;
                break;
            }
            chars[charIndex++] = (char)b;
        }
        this.position = position;
        if (charIndex < charCount) {
            this.readUtf8_slow(charCount, charIndex);
        }
    }
    
    private void readUtf8_slow(final int charCount, int charIndex) {
        final char[] chars = this.chars;
        final byte[] buffer = this.buffer;
        while (charIndex < charCount) {
            if (this.position == this.limit) {
                this.require(1);
            }
            final int b = buffer[this.position++] & 0xFF;
            switch (b >> 4) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7: {
                    chars[charIndex] = (char)b;
                    break;
                }
                case 12:
                case 13: {
                    if (this.position == this.limit) {
                        this.require(1);
                    }
                    chars[charIndex] = (char)((b & 0x1F) << 6 | (buffer[this.position++] & 0x3F));
                    break;
                }
                case 14: {
                    this.require(2);
                    chars[charIndex] = (char)((b & 0xF) << 12 | (buffer[this.position++] & 0x3F) << 6 | (buffer[this.position++] & 0x3F));
                    break;
                }
            }
            ++charIndex;
        }
    }
    
    private String readAscii() {
        final byte[] buffer = this.buffer;
        int end = this.position;
        final int start = end - 1;
        final int limit = this.limit;
        while (end != limit) {
            final int b = buffer[end++];
            if ((b & 0x80) != 0x0) {
                final byte[] array = buffer;
                final int n = end - 1;
                array[n] &= 0x7F;
                final String value = new String(buffer, 0, start, end - start);
                final byte[] array2 = buffer;
                final int n2 = end - 1;
                array2[n2] |= (byte)128;
                this.position = end;
                return value;
            }
        }
        return this.readAscii_slow();
    }
    
    private String readAscii_slow() {
        --this.position;
        int charCount = this.limit - this.position;
        if (charCount > this.chars.length) {
            this.chars = new char[charCount * 2];
        }
        char[] chars = this.chars;
        final byte[] buffer = this.buffer;
        for (int i = this.position, ii = 0, n = this.limit; i < n; ++i, ++ii) {
            chars[ii] = (char)buffer[i];
        }
        this.position = this.limit;
        int b;
        while (true) {
            this.require(1);
            b = buffer[this.position++];
            if (charCount == chars.length) {
                final char[] newChars = new char[charCount * 2];
                System.arraycopy(chars, 0, newChars, 0, charCount);
                chars = newChars;
                this.chars = newChars;
            }
            if ((b & 0x80) == 0x80) {
                break;
            }
            chars[charCount++] = (char)b;
        }
        chars[charCount++] = (char)(b & 0x7F);
        return new String(chars, 0, charCount);
    }
    
    public StringBuilder readStringBuilder() {
        final int available = this.require(1);
        final int b = this.buffer[this.position++];
        if ((b & 0x80) == 0x0) {
            return new StringBuilder(this.readAscii());
        }
        int charCount = (available >= 5) ? this.readUtf8Length(b) : this.readUtf8Length_slow(b);
        switch (charCount) {
            case 0: {
                return null;
            }
            case 1: {
                return new StringBuilder("");
            }
            default: {
                --charCount;
                if (this.chars.length < charCount) {
                    this.chars = new char[charCount];
                }
                this.readUtf8(charCount);
                final StringBuilder builder = new StringBuilder(charCount);
                builder.append(this.chars, 0, charCount);
                return builder;
            }
        }
    }
    
    public float readFloat() throws KryoException {
        return Float.intBitsToFloat(this.readInt());
    }
    
    public float readFloat(final float precision, final boolean optimizePositive) throws KryoException {
        return this.readInt(optimizePositive) / precision;
    }
    
    public short readShort() throws KryoException {
        this.require(2);
        return (short)((this.buffer[this.position++] & 0xFF) << 8 | (this.buffer[this.position++] & 0xFF));
    }
    
    public int readShortUnsigned() throws KryoException {
        this.require(2);
        return (this.buffer[this.position++] & 0xFF) << 8 | (this.buffer[this.position++] & 0xFF);
    }
    
    public long readLong() throws KryoException {
        this.require(8);
        final byte[] buffer = this.buffer;
        return buffer[this.position++] << 56 | (buffer[this.position++] & 0xFF) << 48 | (buffer[this.position++] & 0xFF) << 40 | (buffer[this.position++] & 0xFF) << 32 | (buffer[this.position++] & 0xFF) << 24 | (buffer[this.position++] & 0xFF) << 16 | (buffer[this.position++] & 0xFF) << 8 | (buffer[this.position++] & 0xFF);
    }
    
    public long readLong(final boolean optimizePositive) throws KryoException {
        if (this.require(1) < 9) {
            return this.readLong_slow(optimizePositive);
        }
        int b = this.buffer[this.position++];
        long result = b & 0x7F;
        if ((b & 0x80) != 0x0) {
            final byte[] buffer = this.buffer;
            b = buffer[this.position++];
            result |= (b & 0x7F) << 7;
            if ((b & 0x80) != 0x0) {
                b = buffer[this.position++];
                result |= (b & 0x7F) << 14;
                if ((b & 0x80) != 0x0) {
                    b = buffer[this.position++];
                    result |= (b & 0x7F) << 21;
                    if ((b & 0x80) != 0x0) {
                        b = buffer[this.position++];
                        result |= (b & 0x7F) << 28;
                        if ((b & 0x80) != 0x0) {
                            b = buffer[this.position++];
                            result |= (b & 0x7F) << 35;
                            if ((b & 0x80) != 0x0) {
                                b = buffer[this.position++];
                                result |= (b & 0x7F) << 42;
                                if ((b & 0x80) != 0x0) {
                                    b = buffer[this.position++];
                                    result |= (b & 0x7F) << 49;
                                    if ((b & 0x80) != 0x0) {
                                        b = buffer[this.position++];
                                        result |= b << 56;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (!optimizePositive) {
            result = (result >>> 1 ^ -(result & 0x1L));
        }
        return result;
    }
    
    private long readLong_slow(final boolean optimizePositive) {
        int b = this.buffer[this.position++];
        long result = b & 0x7F;
        if ((b & 0x80) != 0x0) {
            this.require(1);
            final byte[] buffer = this.buffer;
            b = buffer[this.position++];
            result |= (b & 0x7F) << 7;
            if ((b & 0x80) != 0x0) {
                this.require(1);
                b = buffer[this.position++];
                result |= (b & 0x7F) << 14;
                if ((b & 0x80) != 0x0) {
                    this.require(1);
                    b = buffer[this.position++];
                    result |= (b & 0x7F) << 21;
                    if ((b & 0x80) != 0x0) {
                        this.require(1);
                        b = buffer[this.position++];
                        result |= (b & 0x7F) << 28;
                        if ((b & 0x80) != 0x0) {
                            this.require(1);
                            b = buffer[this.position++];
                            result |= (b & 0x7F) << 35;
                            if ((b & 0x80) != 0x0) {
                                this.require(1);
                                b = buffer[this.position++];
                                result |= (b & 0x7F) << 42;
                                if ((b & 0x80) != 0x0) {
                                    this.require(1);
                                    b = buffer[this.position++];
                                    result |= (b & 0x7F) << 49;
                                    if ((b & 0x80) != 0x0) {
                                        this.require(1);
                                        b = buffer[this.position++];
                                        result |= b << 56;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (!optimizePositive) {
            result = (result >>> 1 ^ -(result & 0x1L));
        }
        return result;
    }
    
    public boolean readBoolean() throws KryoException {
        this.require(1);
        return this.buffer[this.position++] == 1;
    }
    
    public char readChar() throws KryoException {
        this.require(2);
        return (char)((this.buffer[this.position++] & 0xFF) << 8 | (this.buffer[this.position++] & 0xFF));
    }
    
    public double readDouble() throws KryoException {
        return Double.longBitsToDouble(this.readLong());
    }
    
    public double readDouble(final double precision, final boolean optimizePositive) throws KryoException {
        return this.readLong(optimizePositive) / precision;
    }
}
