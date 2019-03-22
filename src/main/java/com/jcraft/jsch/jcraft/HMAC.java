// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch.jcraft;

import java.security.MessageDigest;

class HMAC
{
    private static final int B = 64;
    private byte[] k_ipad;
    private byte[] k_opad;
    private MessageDigest md;
    private int bsize;
    private final byte[] tmp;
    
    HMAC() {
        this.k_ipad = null;
        this.k_opad = null;
        this.md = null;
        this.bsize = 0;
        this.tmp = new byte[4];
    }
    
    protected void setH(final MessageDigest md) {
        this.md = md;
        this.bsize = md.getDigestLength();
    }
    
    public int getBlockSize() {
        return this.bsize;
    }
    
    public void init(byte[] digest) throws Exception {
        if (digest.length > this.bsize) {
            final byte[] array = new byte[this.bsize];
            System.arraycopy(digest, 0, array, 0, this.bsize);
            digest = array;
        }
        if (digest.length > 64) {
            this.md.update(digest, 0, digest.length);
            digest = this.md.digest();
        }
        System.arraycopy(digest, 0, this.k_ipad = new byte[64], 0, digest.length);
        System.arraycopy(digest, 0, this.k_opad = new byte[64], 0, digest.length);
        for (int i = 0; i < 64; ++i) {
            final byte[] k_ipad = this.k_ipad;
            final int n = i;
            k_ipad[n] ^= 0x36;
            final byte[] k_opad = this.k_opad;
            final int n2 = i;
            k_opad[n2] ^= 0x5C;
        }
        this.md.update(this.k_ipad, 0, 64);
    }
    
    public void update(final int n) {
        this.tmp[0] = (byte)(n >>> 24);
        this.tmp[1] = (byte)(n >>> 16);
        this.tmp[2] = (byte)(n >>> 8);
        this.tmp[3] = (byte)n;
        this.update(this.tmp, 0, 4);
    }
    
    public void update(final byte[] array, final int n, final int n2) {
        this.md.update(array, n, n2);
    }
    
    public void doFinal(final byte[] array, final int n) {
        final byte[] digest = this.md.digest();
        this.md.update(this.k_opad, 0, 64);
        this.md.update(digest, 0, this.bsize);
        try {
            this.md.digest(array, n, this.bsize);
        }
        catch (Exception ex) {}
        this.md.update(this.k_ipad, 0, 64);
    }
}
