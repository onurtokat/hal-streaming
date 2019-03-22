// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch.jcraft;

public class HMACSHA196 extends HMACSHA1
{
    private static final String name = "hmac-sha1-96";
    private static final int BSIZE = 12;
    private final byte[] _buf16;
    
    public HMACSHA196() {
        this._buf16 = new byte[20];
    }
    
    public int getBlockSize() {
        return 12;
    }
    
    public void doFinal(final byte[] array, final int n) {
        super.doFinal(this._buf16, 0);
        System.arraycopy(this._buf16, 0, array, n, 12);
    }
    
    public String getName() {
        return "hmac-sha1-96";
    }
}
