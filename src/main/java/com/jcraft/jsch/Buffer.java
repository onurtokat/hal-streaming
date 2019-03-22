// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

public class Buffer
{
    final byte[] tmp;
    byte[] buffer;
    int index;
    int s;
    
    public Buffer(final int n) {
        this.tmp = new byte[4];
        this.buffer = new byte[n];
        this.index = 0;
        this.s = 0;
    }
    
    public Buffer(final byte[] buffer) {
        this.tmp = new byte[4];
        this.buffer = buffer;
        this.index = 0;
        this.s = 0;
    }
    
    public Buffer() {
        this(20480);
    }
    
    public void putByte(final byte b) {
        this.buffer[this.index++] = b;
    }
    
    public void putByte(final byte[] array) {
        this.putByte(array, 0, array.length);
    }
    
    public void putByte(final byte[] array, final int n, final int n2) {
        System.arraycopy(array, n, this.buffer, this.index, n2);
        this.index += n2;
    }
    
    public void putString(final byte[] array) {
        this.putString(array, 0, array.length);
    }
    
    public void putString(final byte[] array, final int n, final int n2) {
        this.putInt(n2);
        this.putByte(array, n, n2);
    }
    
    public void putInt(final int n) {
        this.tmp[0] = (byte)(n >>> 24);
        this.tmp[1] = (byte)(n >>> 16);
        this.tmp[2] = (byte)(n >>> 8);
        this.tmp[3] = (byte)n;
        System.arraycopy(this.tmp, 0, this.buffer, this.index, 4);
        this.index += 4;
    }
    
    public void putLong(final long n) {
        this.tmp[0] = (byte)(n >>> 56);
        this.tmp[1] = (byte)(n >>> 48);
        this.tmp[2] = (byte)(n >>> 40);
        this.tmp[3] = (byte)(n >>> 32);
        System.arraycopy(this.tmp, 0, this.buffer, this.index, 4);
        this.tmp[0] = (byte)(n >>> 24);
        this.tmp[1] = (byte)(n >>> 16);
        this.tmp[2] = (byte)(n >>> 8);
        this.tmp[3] = (byte)n;
        System.arraycopy(this.tmp, 0, this.buffer, this.index + 4, 4);
        this.index += 8;
    }
    
    void skip(final int n) {
        this.index += n;
    }
    
    void putPad(int i) {
        while (i > 0) {
            this.buffer[this.index++] = 0;
            --i;
        }
    }
    
    public void putMPInt(final byte[] array) {
        int length = array.length;
        if ((array[0] & 0x80) != 0x0) {
            ++length;
            this.putInt(length);
            this.putByte((byte)0);
        }
        else {
            this.putInt(length);
        }
        this.putByte(array);
    }
    
    public int getLength() {
        return this.index - this.s;
    }
    
    public int getOffSet() {
        return this.s;
    }
    
    public void setOffSet(final int s) {
        this.s = s;
    }
    
    public long getLong() {
        return (this.getInt() & 0xFFFFFFFFL) << 32 | (this.getInt() & 0xFFFFFFFFL);
    }
    
    public int getInt() {
        return (this.getShort() << 16 & 0xFFFF0000) | (this.getShort() & 0xFFFF);
    }
    
    int getShort() {
        return (this.getByte() << 8 & 0xFF00) | (this.getByte() & 0xFF);
    }
    
    public int getByte() {
        return this.buffer[this.s++] & 0xFF;
    }
    
    public void getByte(final byte[] array) {
        this.getByte(array, 0, array.length);
    }
    
    void getByte(final byte[] array, final int n, final int n2) {
        System.arraycopy(this.buffer, this.s, array, n, n2);
        this.s += n2;
    }
    
    public int getByte(final int n) {
        final int s = this.s;
        this.s += n;
        return s;
    }
    
    public byte[] getMPInt() {
        final int \u0131nt = this.getInt();
        final byte[] array = new byte[\u0131nt];
        this.getByte(array, 0, \u0131nt);
        return array;
    }
    
    public byte[] getMPIntBits() {
        final int n = (this.getInt() + 7) / 8;
        byte[] array = new byte[n];
        this.getByte(array, 0, n);
        if ((array[0] & 0x80) != 0x0) {
            final byte[] array2 = new byte[array.length + 1];
            array2[0] = 0;
            System.arraycopy(array, 0, array2, 1, array.length);
            array = array2;
        }
        return array;
    }
    
    public byte[] getString() {
        final int \u0131nt = this.getInt();
        final byte[] array = new byte[\u0131nt];
        this.getByte(array, 0, \u0131nt);
        return array;
    }
    
    byte[] getString(final int[] array, final int[] array2) {
        final int \u0131nt = this.getInt();
        array[0] = this.getByte(\u0131nt);
        array2[0] = \u0131nt;
        return this.buffer;
    }
    
    public void reset() {
        this.index = 0;
        this.s = 0;
    }
    
    public void shift() {
        if (this.s == 0) {
            return;
        }
        System.arraycopy(this.buffer, this.s, this.buffer, 0, this.index - this.s);
        this.index -= this.s;
        this.s = 0;
    }
    
    void rewind() {
        this.s = 0;
    }
    
    byte getCommand() {
        return this.buffer[5];
    }
}
