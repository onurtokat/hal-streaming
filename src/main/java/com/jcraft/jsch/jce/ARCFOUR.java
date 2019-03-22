// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch.jce;

import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import com.jcraft.jsch.Cipher;

public class ARCFOUR implements Cipher
{
    private static final int ivsize = 8;
    private static final int bsize = 16;
    private javax.crypto.Cipher cipher;
    
    public int getIVSize() {
        return 8;
    }
    
    public int getBlockSize() {
        return 16;
    }
    
    public void init(final int n, byte[] array, final byte[] array2) throws Exception {
        if (array.length > 16) {
            final byte[] array3 = new byte[16];
            System.arraycopy(array, 0, array3, 0, array3.length);
            array = array3;
        }
        try {
            (this.cipher = javax.crypto.Cipher.getInstance("RC4")).init((n == 0) ? 1 : 2, new SecretKeySpec(array, "RC4"));
        }
        catch (Exception ex) {
            this.cipher = null;
            throw ex;
        }
    }
    
    public void update(final byte[] array, final int n, final int n2, final byte[] array2, final int n3) throws Exception {
        this.cipher.update(array, n, n2, array2, n3);
    }
}
