// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

public class Packet
{
    private static Random random;
    Buffer buffer;
    byte[] ba4;
    
    static void setRandom(final Random random) {
        Packet.random = random;
    }
    
    public Packet(final Buffer buffer) {
        this.ba4 = new byte[4];
        this.buffer = buffer;
    }
    
    public void reset() {
        this.buffer.index = 5;
    }
    
    void padding(final int n) {
        final int index = this.buffer.index;
        int n2 = -index & n - 1;
        if (n2 < n) {
            n2 += n;
        }
        final int n3 = index + n2 - 4;
        this.ba4[0] = (byte)(n3 >>> 24);
        this.ba4[1] = (byte)(n3 >>> 16);
        this.ba4[2] = (byte)(n3 >>> 8);
        this.ba4[3] = (byte)n3;
        System.arraycopy(this.ba4, 0, this.buffer.buffer, 0, 4);
        this.buffer.buffer[4] = (byte)n2;
        synchronized (Packet.random) {
            Packet.random.fill(this.buffer.buffer, this.buffer.index, n2);
        }
        this.buffer.skip(n2);
    }
    
    int shift(final int n, final int n2) {
        final int n3 = n + 5 + 9;
        int n4 = -n3 & 0xF;
        if (n4 < 16) {
            n4 += 16;
        }
        final int n5 = n3 + n4 + n2;
        if (this.buffer.buffer.length < n5 + this.buffer.index - 5 - 9 - n) {
            final byte[] buffer = new byte[n5 + this.buffer.index - 5 - 9 - n];
            System.arraycopy(this.buffer.buffer, 0, buffer, 0, this.buffer.buffer.length);
            this.buffer.buffer = buffer;
        }
        System.arraycopy(this.buffer.buffer, n + 5 + 9, this.buffer.buffer, n5, this.buffer.index - 5 - 9 - n);
        this.buffer.index = 10;
        this.buffer.putInt(n);
        this.buffer.index = n + 5 + 9;
        return n5;
    }
    
    void unshift(final byte b, final int n, final int n2, final int n3) {
        System.arraycopy(this.buffer.buffer, n2, this.buffer.buffer, 14, n3);
        this.buffer.buffer[5] = b;
        this.buffer.index = 6;
        this.buffer.putInt(n);
        this.buffer.putInt(n3);
        this.buffer.index = n3 + 5 + 9;
    }
    
    Buffer getBuffer() {
        return this.buffer;
    }
    
    static {
        Packet.random = null;
    }
}
