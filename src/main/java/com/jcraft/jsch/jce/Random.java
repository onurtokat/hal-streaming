// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch.jce;

import java.security.SecureRandom;

public class Random implements com.jcraft.jsch.Random
{
    private byte[] tmp;
    private SecureRandom random;
    
    public Random() {
        this.tmp = new byte[16];
        this.random = null;
        this.random = new SecureRandom();
    }
    
    public void fill(final byte[] array, final int n, final int n2) {
        if (n2 > this.tmp.length) {
            this.tmp = new byte[n2];
        }
        this.random.nextBytes(this.tmp);
        System.arraycopy(this.tmp, 0, array, n, n2);
    }
}
