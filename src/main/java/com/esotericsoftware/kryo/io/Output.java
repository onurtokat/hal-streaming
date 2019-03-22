// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.kryo.io;

import java.io.IOException;
import com.esotericsoftware.kryo.KryoException;
import java.io.OutputStream;

public class Output extends OutputStream
{
    private int maxCapacity;
    private int capacity;
    private int position;
    private int total;
    private byte[] buffer;
    private OutputStream outputStream;
    
    public Output() {
    }
    
    public Output(final int bufferSize) {
        this(bufferSize, bufferSize);
    }
    
    public Output(final int bufferSize, final int maxBufferSize) {
        if (maxBufferSize < -1) {
            throw new IllegalArgumentException("maxBufferSize cannot be < -1: " + maxBufferSize);
        }
        this.capacity = bufferSize;
        this.maxCapacity = ((maxBufferSize == -1) ? Integer.MAX_VALUE : maxBufferSize);
        this.buffer = new byte[bufferSize];
    }
    
    public Output(final byte[] buffer) {
        this(buffer, buffer.length);
    }
    
    public Output(final byte[] buffer, final int maxBufferSize) {
        if (buffer == null) {
            throw new IllegalArgumentException("buffer cannot be null.");
        }
        this.setBuffer(buffer, maxBufferSize);
    }
    
    public Output(final OutputStream outputStream) {
        this(4096, 4096);
        if (outputStream == null) {
            throw new IllegalArgumentException("outputStream cannot be null.");
        }
        this.outputStream = outputStream;
    }
    
    public Output(final OutputStream outputStream, final int bufferSize) {
        this(bufferSize, bufferSize);
        if (outputStream == null) {
            throw new IllegalArgumentException("outputStream cannot be null.");
        }
        this.outputStream = outputStream;
    }
    
    public OutputStream getOutputStream() {
        return this.outputStream;
    }
    
    public void setOutputStream(final OutputStream outputStream) {
        this.outputStream = outputStream;
        this.position = 0;
        this.total = 0;
    }
    
    public void setBuffer(final byte[] buffer) {
        this.setBuffer(buffer, buffer.length);
    }
    
    public void setBuffer(final byte[] buffer, final int maxBufferSize) {
        if (buffer == null) {
            throw new IllegalArgumentException("buffer cannot be null.");
        }
        if (maxBufferSize < -1) {
            throw new IllegalArgumentException("maxBufferSize cannot be < -1: " + maxBufferSize);
        }
        this.buffer = buffer;
        this.maxCapacity = ((maxBufferSize == -1) ? Integer.MAX_VALUE : maxBufferSize);
        this.capacity = buffer.length;
        this.position = 0;
        this.total = 0;
        this.outputStream = null;
    }
    
    public byte[] getBuffer() {
        return this.buffer;
    }
    
    public byte[] toBytes() {
        final byte[] newBuffer = new byte[this.position];
        System.arraycopy(this.buffer, 0, newBuffer, 0, this.position);
        return newBuffer;
    }
    
    public int position() {
        return this.position;
    }
    
    public void setPosition(final int position) {
        this.position = position;
    }
    
    public int total() {
        return this.total + this.position;
    }
    
    public void clear() {
        this.position = 0;
        this.total = 0;
    }
    
    private boolean require(final int required) throws KryoException {
        if (this.capacity - this.position >= required) {
            return false;
        }
        if (required > this.maxCapacity) {
            throw new KryoException("Buffer overflow. Max capacity: " + this.maxCapacity + ", required: " + required);
        }
        this.flush();
        while (this.capacity - this.position < required) {
            if (this.capacity == this.maxCapacity) {
                throw new KryoException("Buffer overflow. Available: " + (this.capacity - this.position) + ", required: " + required);
            }
            this.capacity = Math.min(this.capacity * 2, this.maxCapacity);
            if (this.capacity < 0) {
                this.capacity = this.maxCapacity;
            }
            final byte[] newBuffer = new byte[this.capacity];
            System.arraycopy(this.buffer, 0, newBuffer, 0, this.position);
            this.buffer = newBuffer;
        }
        return true;
    }
    
    public void flush() throws KryoException {
        if (this.outputStream == null) {
            return;
        }
        try {
            this.outputStream.write(this.buffer, 0, this.position);
        }
        catch (IOException ex) {
            throw new KryoException(ex);
        }
        this.total += this.position;
        this.position = 0;
    }
    
    public void close() throws KryoException {
        this.flush();
        if (this.outputStream != null) {
            try {
                this.outputStream.close();
            }
            catch (IOException ex) {}
        }
    }
    
    public void write(final int value) throws KryoException {
        if (this.position == this.capacity) {
            this.require(1);
        }
        this.buffer[this.position++] = (byte)value;
    }
    
    public void write(final byte[] bytes) throws KryoException {
        if (bytes == null) {
            throw new IllegalArgumentException("bytes cannot be null.");
        }
        this.writeBytes(bytes, 0, bytes.length);
    }
    
    public void write(final byte[] bytes, final int offset, final int length) throws KryoException {
        this.writeBytes(bytes, offset, length);
    }
    
    public void writeByte(final byte value) throws KryoException {
        if (this.position == this.capacity) {
            this.require(1);
        }
        this.buffer[this.position++] = value;
    }
    
    public void writeByte(final int value) throws KryoException {
        if (this.position == this.capacity) {
            this.require(1);
        }
        this.buffer[this.position++] = (byte)value;
    }
    
    public void writeBytes(final byte[] bytes) throws KryoException {
        if (bytes == null) {
            throw new IllegalArgumentException("bytes cannot be null.");
        }
        this.writeBytes(bytes, 0, bytes.length);
    }
    
    public void writeBytes(final byte[] bytes, int offset, int count) throws KryoException {
        if (bytes == null) {
            throw new IllegalArgumentException("bytes cannot be null.");
        }
        int copyCount = Math.min(this.capacity - this.position, count);
        while (true) {
            System.arraycopy(bytes, offset, this.buffer, this.position, copyCount);
            this.position += copyCount;
            count -= copyCount;
            if (count == 0) {
                break;
            }
            offset += copyCount;
            copyCount = Math.min(this.capacity, count);
            this.require(copyCount);
        }
    }
    
    public void writeInt(final int value) throws KryoException {
        this.require(4);
        final byte[] buffer = this.buffer;
        buffer[this.position++] = (byte)(value >> 24);
        buffer[this.position++] = (byte)(value >> 16);
        buffer[this.position++] = (byte)(value >> 8);
        buffer[this.position++] = (byte)value;
    }
    
    public int writeInt(int value, final boolean optimizePositive) throws KryoException {
        if (!optimizePositive) {
            value = (value << 1 ^ value >> 31);
        }
        if (value >>> 7 == 0) {
            this.require(1);
            this.buffer[this.position++] = (byte)value;
            return 1;
        }
        if (value >>> 14 == 0) {
            this.require(2);
            this.buffer[this.position++] = (byte)((value & 0x7F) | 0x80);
            this.buffer[this.position++] = (byte)(value >>> 7);
            return 2;
        }
        if (value >>> 21 == 0) {
            this.require(3);
            this.buffer[this.position++] = (byte)((value & 0x7F) | 0x80);
            this.buffer[this.position++] = (byte)(value >>> 7 | 0x80);
            this.buffer[this.position++] = (byte)(value >>> 14);
            return 3;
        }
        if (value >>> 28 == 0) {
            this.require(4);
            this.buffer[this.position++] = (byte)((value & 0x7F) | 0x80);
            this.buffer[this.position++] = (byte)(value >>> 7 | 0x80);
            this.buffer[this.position++] = (byte)(value >>> 14 | 0x80);
            this.buffer[this.position++] = (byte)(value >>> 21);
            return 4;
        }
        this.require(5);
        this.buffer[this.position++] = (byte)((value & 0x7F) | 0x80);
        this.buffer[this.position++] = (byte)(value >>> 7 | 0x80);
        this.buffer[this.position++] = (byte)(value >>> 14 | 0x80);
        this.buffer[this.position++] = (byte)(value >>> 21 | 0x80);
        this.buffer[this.position++] = (byte)(value >>> 28);
        return 5;
    }
    
    public void writeString(final String value) throws KryoException {
        if (value == null) {
            this.writeByte(128);
            return;
        }
        final int charCount = value.length();
        if (charCount == 0) {
            this.writeByte(129);
            return;
        }
        boolean ascii = false;
        if (charCount > 1 && charCount < 64) {
            ascii = true;
            for (int i = 0; i < charCount; ++i) {
                final int c = value.charAt(i);
                if (c > 127) {
                    ascii = false;
                    break;
                }
            }
        }
        if (ascii) {
            if (this.capacity - this.position < charCount) {
                this.writeAscii_slow(value, charCount);
            }
            else {
                value.getBytes(0, charCount, this.buffer, this.position);
                this.position += charCount;
            }
            final byte[] buffer2 = this.buffer;
            final int n = this.position - 1;
            buffer2[n] |= (byte)128;
        }
        else {
            this.writeUtf8Length(charCount + 1);
            int charIndex = 0;
            if (this.capacity - this.position >= charCount) {
                final byte[] buffer = this.buffer;
                int position = this.position;
                while (charIndex < charCount) {
                    final int c2 = value.charAt(charIndex);
                    if (c2 > 127) {
                        break;
                    }
                    buffer[position++] = (byte)c2;
                    ++charIndex;
                }
                this.position = position;
            }
            if (charIndex < charCount) {
                this.writeString_slow(value, charCount, charIndex);
            }
        }
    }
    
    public void writeString(final CharSequence value) throws KryoException {
        if (value == null) {
            this.writeByte(128);
            return;
        }
        final int charCount = value.length();
        if (charCount == 0) {
            this.writeByte(129);
            return;
        }
        this.writeUtf8Length(charCount + 1);
        int charIndex = 0;
        if (this.capacity - this.position >= charCount) {
            final byte[] buffer = this.buffer;
            int position = this.position;
            while (charIndex < charCount) {
                final int c = value.charAt(charIndex);
                if (c > 127) {
                    break;
                }
                buffer[position++] = (byte)c;
                ++charIndex;
            }
            this.position = position;
        }
        if (charIndex < charCount) {
            this.writeString_slow(value, charCount, charIndex);
        }
    }
    
    public void writeAscii(final String value) throws KryoException {
        if (value == null) {
            this.writeByte(128);
            return;
        }
        final int charCount = value.length();
        if (charCount == 0) {
            this.writeByte(129);
            return;
        }
        if (this.capacity - this.position < charCount) {
            this.writeAscii_slow(value, charCount);
        }
        else {
            value.getBytes(0, charCount, this.buffer, this.position);
            this.position += charCount;
        }
        final byte[] buffer = this.buffer;
        final int n = this.position - 1;
        buffer[n] |= (byte)128;
    }
    
    private void writeUtf8Length(final int value) {
        if (value >>> 6 == 0) {
            this.require(1);
            this.buffer[this.position++] = (byte)(value | 0x80);
        }
        else if (value >>> 13 == 0) {
            this.require(2);
            final byte[] buffer = this.buffer;
            buffer[this.position++] = (byte)(value | 0x40 | 0x80);
            buffer[this.position++] = (byte)(value >>> 6);
        }
        else if (value >>> 20 == 0) {
            this.require(3);
            final byte[] buffer = this.buffer;
            buffer[this.position++] = (byte)(value | 0x40 | 0x80);
            buffer[this.position++] = (byte)(value >>> 6 | 0x80);
            buffer[this.position++] = (byte)(value >>> 13);
        }
        else if (value >>> 27 == 0) {
            this.require(4);
            final byte[] buffer = this.buffer;
            buffer[this.position++] = (byte)(value | 0x40 | 0x80);
            buffer[this.position++] = (byte)(value >>> 6 | 0x80);
            buffer[this.position++] = (byte)(value >>> 13 | 0x80);
            buffer[this.position++] = (byte)(value >>> 20);
        }
        else {
            this.require(5);
            final byte[] buffer = this.buffer;
            buffer[this.position++] = (byte)(value | 0x40 | 0x80);
            buffer[this.position++] = (byte)(value >>> 6 | 0x80);
            buffer[this.position++] = (byte)(value >>> 13 | 0x80);
            buffer[this.position++] = (byte)(value >>> 20 | 0x80);
            buffer[this.position++] = (byte)(value >>> 27);
        }
    }
    
    private void writeString_slow(final CharSequence value, final int charCount, int charIndex) {
        while (charIndex < charCount) {
            if (this.position == this.capacity) {
                this.require(Math.min(this.capacity, charCount - charIndex));
            }
            final int c = value.charAt(charIndex);
            if (c <= 127) {
                this.buffer[this.position++] = (byte)c;
            }
            else if (c > 2047) {
                this.buffer[this.position++] = (byte)(0xE0 | (c >> 12 & 0xF));
                this.require(2);
                this.buffer[this.position++] = (byte)(0x80 | (c >> 6 & 0x3F));
                this.buffer[this.position++] = (byte)(0x80 | (c & 0x3F));
            }
            else {
                this.buffer[this.position++] = (byte)(0xC0 | (c >> 6 & 0x1F));
                this.require(1);
                this.buffer[this.position++] = (byte)(0x80 | (c & 0x3F));
            }
            ++charIndex;
        }
    }
    
    private void writeAscii_slow(final String value, final int charCount) throws KryoException {
        byte[] buffer = this.buffer;
        int charIndex = 0;
        int charsToWrite = Math.min(charCount, this.capacity - this.position);
        while (charIndex < charCount) {
            value.getBytes(charIndex, charIndex + charsToWrite, buffer, this.position);
            charIndex += charsToWrite;
            this.position += charsToWrite;
            charsToWrite = Math.min(charCount - charIndex, this.capacity);
            if (this.require(charsToWrite)) {
                buffer = this.buffer;
            }
        }
    }
    
    public void writeFloat(final float value) throws KryoException {
        this.writeInt(Float.floatToIntBits(value));
    }
    
    public int writeFloat(final float value, final float precision, final boolean optimizePositive) throws KryoException {
        return this.writeInt((int)(value * precision), optimizePositive);
    }
    
    public void writeShort(final int value) throws KryoException {
        this.require(2);
        this.buffer[this.position++] = (byte)(value >>> 8);
        this.buffer[this.position++] = (byte)value;
    }
    
    public void writeLong(final long value) throws KryoException {
        this.require(8);
        final byte[] buffer = this.buffer;
        buffer[this.position++] = (byte)(value >>> 56);
        buffer[this.position++] = (byte)(value >>> 48);
        buffer[this.position++] = (byte)(value >>> 40);
        buffer[this.position++] = (byte)(value >>> 32);
        buffer[this.position++] = (byte)(value >>> 24);
        buffer[this.position++] = (byte)(value >>> 16);
        buffer[this.position++] = (byte)(value >>> 8);
        buffer[this.position++] = (byte)value;
    }
    
    public int writeLong(long value, final boolean optimizePositive) throws KryoException {
        if (!optimizePositive) {
            value = (value << 1 ^ value >> 63);
        }
        if (value >>> 7 == 0L) {
            this.require(1);
            this.buffer[this.position++] = (byte)value;
            return 1;
        }
        if (value >>> 14 == 0L) {
            this.require(2);
            this.buffer[this.position++] = (byte)((value & 0x7FL) | 0x80L);
            this.buffer[this.position++] = (byte)(value >>> 7);
            return 2;
        }
        if (value >>> 21 == 0L) {
            this.require(3);
            this.buffer[this.position++] = (byte)((value & 0x7FL) | 0x80L);
            this.buffer[this.position++] = (byte)(value >>> 7 | 0x80L);
            this.buffer[this.position++] = (byte)(value >>> 14);
            return 3;
        }
        if (value >>> 28 == 0L) {
            this.require(4);
            this.buffer[this.position++] = (byte)((value & 0x7FL) | 0x80L);
            this.buffer[this.position++] = (byte)(value >>> 7 | 0x80L);
            this.buffer[this.position++] = (byte)(value >>> 14 | 0x80L);
            this.buffer[this.position++] = (byte)(value >>> 21);
            return 4;
        }
        if (value >>> 35 == 0L) {
            this.require(5);
            this.buffer[this.position++] = (byte)((value & 0x7FL) | 0x80L);
            this.buffer[this.position++] = (byte)(value >>> 7 | 0x80L);
            this.buffer[this.position++] = (byte)(value >>> 14 | 0x80L);
            this.buffer[this.position++] = (byte)(value >>> 21 | 0x80L);
            this.buffer[this.position++] = (byte)(value >>> 28);
            return 5;
        }
        if (value >>> 42 == 0L) {
            this.require(6);
            this.buffer[this.position++] = (byte)((value & 0x7FL) | 0x80L);
            this.buffer[this.position++] = (byte)(value >>> 7 | 0x80L);
            this.buffer[this.position++] = (byte)(value >>> 14 | 0x80L);
            this.buffer[this.position++] = (byte)(value >>> 21 | 0x80L);
            this.buffer[this.position++] = (byte)(value >>> 28 | 0x80L);
            this.buffer[this.position++] = (byte)(value >>> 35);
            return 6;
        }
        if (value >>> 49 == 0L) {
            this.require(7);
            this.buffer[this.position++] = (byte)((value & 0x7FL) | 0x80L);
            this.buffer[this.position++] = (byte)(value >>> 7 | 0x80L);
            this.buffer[this.position++] = (byte)(value >>> 14 | 0x80L);
            this.buffer[this.position++] = (byte)(value >>> 21 | 0x80L);
            this.buffer[this.position++] = (byte)(value >>> 28 | 0x80L);
            this.buffer[this.position++] = (byte)(value >>> 35 | 0x80L);
            this.buffer[this.position++] = (byte)(value >>> 42);
            return 7;
        }
        if (value >>> 56 == 0L) {
            this.require(8);
            this.buffer[this.position++] = (byte)((value & 0x7FL) | 0x80L);
            this.buffer[this.position++] = (byte)(value >>> 7 | 0x80L);
            this.buffer[this.position++] = (byte)(value >>> 14 | 0x80L);
            this.buffer[this.position++] = (byte)(value >>> 21 | 0x80L);
            this.buffer[this.position++] = (byte)(value >>> 28 | 0x80L);
            this.buffer[this.position++] = (byte)(value >>> 35 | 0x80L);
            this.buffer[this.position++] = (byte)(value >>> 42 | 0x80L);
            this.buffer[this.position++] = (byte)(value >>> 49);
            return 8;
        }
        this.require(9);
        this.buffer[this.position++] = (byte)((value & 0x7FL) | 0x80L);
        this.buffer[this.position++] = (byte)(value >>> 7 | 0x80L);
        this.buffer[this.position++] = (byte)(value >>> 14 | 0x80L);
        this.buffer[this.position++] = (byte)(value >>> 21 | 0x80L);
        this.buffer[this.position++] = (byte)(value >>> 28 | 0x80L);
        this.buffer[this.position++] = (byte)(value >>> 35 | 0x80L);
        this.buffer[this.position++] = (byte)(value >>> 42 | 0x80L);
        this.buffer[this.position++] = (byte)(value >>> 49 | 0x80L);
        this.buffer[this.position++] = (byte)(value >>> 56);
        return 9;
    }
    
    public void writeBoolean(final boolean value) throws KryoException {
        this.require(1);
        this.buffer[this.position++] = (byte)(value ? 1 : 0);
    }
    
    public void writeChar(final char value) throws KryoException {
        this.require(2);
        this.buffer[this.position++] = (byte)(value >>> 8);
        this.buffer[this.position++] = (byte)value;
    }
    
    public void writeDouble(final double value) throws KryoException {
        this.writeLong(Double.doubleToLongBits(value));
    }
    
    public int writeDouble(final double value, final double precision, final boolean optimizePositive) throws KryoException {
        return this.writeLong((long)(value * precision), optimizePositive);
    }
    
    public static int intLength(int value, final boolean optimizePositive) {
        if (!optimizePositive) {
            value = (value << 1 ^ value >> 31);
        }
        if (value >>> 7 == 0) {
            return 1;
        }
        if (value >>> 14 == 0) {
            return 2;
        }
        if (value >>> 21 == 0) {
            return 3;
        }
        if (value >>> 28 == 0) {
            return 4;
        }
        return 5;
    }
    
    public static int longLength(long value, final boolean optimizePositive) {
        if (!optimizePositive) {
            value = (value << 1 ^ value >> 63);
        }
        if (value >>> 7 == 0L) {
            return 1;
        }
        if (value >>> 14 == 0L) {
            return 2;
        }
        if (value >>> 21 == 0L) {
            return 3;
        }
        if (value >>> 28 == 0L) {
            return 4;
        }
        if (value >>> 35 == 0L) {
            return 5;
        }
        if (value >>> 42 == 0L) {
            return 6;
        }
        if (value >>> 49 == 0L) {
            return 7;
        }
        if (value >>> 56 == 0L) {
            return 8;
        }
        return 9;
    }
}
