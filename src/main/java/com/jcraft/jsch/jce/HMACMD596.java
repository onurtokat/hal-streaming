// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch.jce;

import javax.crypto.ShortBufferException;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Mac;
import com.jcraft.jsch.MAC;

public class HMACMD596 implements MAC
{
    private static final String name = "hmac-md5-96";
    private static final int bsize = 12;
    private Mac mac;
    private final byte[] tmp;
    private final byte[] _buf16;
    
    public HMACMD596() {
        this.tmp = new byte[4];
        this._buf16 = new byte[16];
    }
    
    public int getBlockSize() {
        return 12;
    }
    
    public void init(byte[] array) throws Exception {
        if (array.length > 16) {
            final byte[] array2 = new byte[16];
            System.arraycopy(array, 0, array2, 0, 16);
            array = array2;
        }
        (this.mac = Mac.getInstance("HmacMD5")).init(new SecretKeySpec(array, "HmacMD5"));
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
            this.mac.doFinal(this._buf16, 0);
        }
        catch (ShortBufferException ex) {}
        System.arraycopy(this._buf16, 0, array, 0, 12);
    }
    
    public String getName() {
        return "hmac-md5-96";
    }
}
