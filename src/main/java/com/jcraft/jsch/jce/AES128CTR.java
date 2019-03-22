// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch.jce;

import java.security.spec.AlgorithmParameterSpec;
import java.security.Key;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import com.jcraft.jsch.Cipher;

public class AES128CTR implements Cipher
{
    private static final int ivsize = 16;
    private static final int bsize = 16;
    private javax.crypto.Cipher cipher;
    
    public int getIVSize() {
        return 16;
    }
    
    public int getBlockSize() {
        return 16;
    }
    
    public void init(final int n, byte[] array, byte[] array2) throws Exception {
        final String s = "NoPadding";
        if (array2.length > 16) {
            final byte[] array3 = new byte[16];
            System.arraycopy(array2, 0, array3, 0, array3.length);
            array2 = array3;
        }
        if (array.length > 16) {
            final byte[] array4 = new byte[16];
            System.arraycopy(array, 0, array4, 0, array4.length);
            array = array4;
        }
        try {
            (this.cipher = javax.crypto.Cipher.getInstance("AES/CTR/" + s)).init((n == 0) ? 1 : 2, new SecretKeySpec(array, "AES"), new IvParameterSpec(array2));
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
