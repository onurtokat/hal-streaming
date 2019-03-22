// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch.jcraft;

import com.jcraft.jzlib.ZStream;

public class Compression implements com.jcraft.jsch.Compression
{
    private static final int BUF_SIZE = 4096;
    private int type;
    private ZStream stream;
    private byte[] tmpbuf;
    private byte[] inflated_buf;
    
    public Compression() {
        this.tmpbuf = new byte[4096];
        this.stream = new ZStream();
    }
    
    public void init(final int n, final int n2) {
        if (n == 1) {
            this.stream.deflateInit(n2);
            this.type = 1;
        }
        else if (n == 0) {
            this.stream.inflateInit();
            this.inflated_buf = new byte[4096];
            this.type = 0;
        }
    }
    
    public int compress(final byte[] next_in, final int next_in_index, final int n) {
        this.stream.next_in = next_in;
        this.stream.next_in_index = next_in_index;
        this.stream.avail_in = n - next_in_index;
        int n2 = next_in_index;
        do {
            this.stream.next_out = this.tmpbuf;
            this.stream.next_out_index = 0;
            this.stream.avail_out = 4096;
            final int deflate = this.stream.deflate(1);
            switch (deflate) {
                case 0: {
                    System.arraycopy(this.tmpbuf, 0, next_in, n2, 4096 - this.stream.avail_out);
                    n2 += 4096 - this.stream.avail_out;
                    continue;
                }
                default: {
                    System.err.println("compress: deflate returnd " + deflate);
                    continue;
                }
            }
        } while (this.stream.avail_out == 0);
        return n2;
    }
    
    public byte[] uncompress(byte[] next_in, final int next_in_index, final int[] array) {
        int n = 0;
        this.stream.next_in = next_in;
        this.stream.next_in_index = next_in_index;
        this.stream.avail_in = array[0];
        while (true) {
            this.stream.next_out = this.tmpbuf;
            this.stream.next_out_index = 0;
            this.stream.avail_out = 4096;
            final int inflate = this.stream.inflate(1);
            switch (inflate) {
                case 0: {
                    if (this.inflated_buf.length < n + 4096 - this.stream.avail_out) {
                        final byte[] inflated_buf = new byte[n + 4096 - this.stream.avail_out];
                        System.arraycopy(this.inflated_buf, 0, inflated_buf, 0, n);
                        this.inflated_buf = inflated_buf;
                    }
                    System.arraycopy(this.tmpbuf, 0, this.inflated_buf, n, 4096 - this.stream.avail_out);
                    n += 4096 - this.stream.avail_out;
                    array[0] = n;
                    continue;
                }
                case -5: {
                    if (n > next_in.length - next_in_index) {
                        final byte[] array2 = new byte[n + next_in_index];
                        System.arraycopy(next_in, 0, array2, 0, next_in_index);
                        System.arraycopy(this.inflated_buf, 0, array2, next_in_index, n);
                        next_in = array2;
                    }
                    else {
                        System.arraycopy(this.inflated_buf, 0, next_in, next_in_index, n);
                    }
                    array[0] = n;
                    return next_in;
                }
                default: {
                    System.err.println("uncompress: inflate returnd " + inflate);
                    return null;
                }
            }
        }
    }
}
