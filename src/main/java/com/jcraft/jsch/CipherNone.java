// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

public class CipherNone implements Cipher
{
    private static final int ivsize = 8;
    private static final int bsize = 16;
    
    public int getIVSize() {
        return 8;
    }
    
    public int getBlockSize() {
        return 16;
    }
    
    public void init(final int n, final byte[] array, final byte[] array2) throws Exception {
    }
    
    public void update(final byte[] array, final int n, final int n2, final byte[] array2, final int n3) throws Exception {
    }
}
