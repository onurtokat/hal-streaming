// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch.jce;

import javax.crypto.ShortBufferException;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Mac;
import com.jcraft.jsch.MAC;

public class HMACSHA196 implements MAC
{
    private static final String name = "hmac-sha1-96";
    private static final int bsize = 12;
    private Mac mac;
    private final byte[] tmp;
    private final byte[] _buf20;
    
    public HMACSHA196() {
        this.tmp = new byte[4];
        this._buf20 = new byte[20];
    }
    
    public int getBlockSize() {
        return 12;
    }
    
    public void init(byte[] array) throws Exception {
        if (array.length > 20) {
            final byte[] array2 = new byte[20];
            System.arraycopy(array, 0, array2, 0, 20);
            array = array2;
        }
        (this.mac = Mac.getInstance("HmacSHA1")).init(new SecretKeySpec(array, "HmacSHA1"));
    }
    
    public void update(final int n) {
        this.tmp[0] = (byte)(n >>> 24);
        this.tmp[1] = (byte)(n >>> 16);
        this.tmp[2] = (byte)(n >>> 8);
        this.tmp[3] = (byte)n;
        this.update(this.tmp, 0, 4);
    }
    
    public void update(final byte[] array, final int n, final int n2) {
        this.mac.update(array, n, n2);
    }
    
    public void doFinal(final byte[] array, final int n) {
        try {
            this.mac.doFinal(this._buf20, 0);
        }
        catch (ShortBufferException ex) {}
        System.arraycopy(this._buf20, 0, array, 0, 12);
    }
    
    public String getName() {
        return "hmac-sha1-96";
    }
}
