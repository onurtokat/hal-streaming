// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch.jcraft;

public class HMACMD596 extends HMACMD5
{
    private static final String name = "hmac-md5-96";
    private static final int BSIZE = 12;
    private final byte[] _buf16;
    
    public HMACMD596() {
        this._buf16 = new byte[16];
    }
    
    public int getBlockSize() {
        return 12;
    }
    
    public void doFinal(final byte[] array, final int n) {
        super.doFinal(this._buf16, 0);
        System.arraycopy(this._buf16, 0, array, n, 12);
    }
    
    public String getName() {
        return "hmac-md5-96";
    }
}
