// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch.jce;

import javax.crypto.ShortBufferException;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Mac;
import com.jcraft.jsch.MAC;

public class HMACMD5 implements MAC
{
    private static final String name = "hmac-md5";
    private static final int BSIZE = 16;
    private Mac mac;
    private final byte[] tmp;
    
    public HMACMD5() {
        this.tmp = new byte[4];
    }
    
    public int getBlockSize() {
        return 16;
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
            this.mac.doFinal(array, n);
        }
        catch (ShortBufferException ex) {}
    }
    
    public String getName() {
        return "hmac-md5";
    }
}
