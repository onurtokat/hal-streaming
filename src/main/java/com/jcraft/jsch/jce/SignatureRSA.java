// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch.jce;

import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.KeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.Signature;

public class SignatureRSA implements com.jcraft.jsch.SignatureRSA
{
    Signature signature;
    KeyFactory keyFactory;
    
    public void init() throws Exception {
        this.signature = Signature.getInstance("SHA1withRSA");
        this.keyFactory = KeyFactory.getInstance("RSA");
    }
    
    public void setPubKey(final byte[] array, final byte[] array2) throws Exception {
        this.signature.initVerify(this.keyFactory.generatePublic(new RSAPublicKeySpec(new BigInteger(array2), new BigInteger(array))));
    }
    
    public void setPrvKey(final byte[] array, final byte[] array2) throws Exception {
        this.signature.initSign(this.keyFactory.generatePrivate(new RSAPrivateKeySpec(new BigInteger(array2), new BigInteger(array))));
    }
    
    public byte[] sign() throws Exception {
        return this.signature.sign();
    }
    
    public void update(final byte[] array) throws Exception {
        this.signature.update(array);
    }
    
    public boolean verify(byte[] array) throws Exception {
        int n = 0;
        if (array[0] == 0 && array[1] == 0 && array[2] == 0) {
            int n2 = n + ((array[n++] << 24 & 0xFF000000) | (array[n++] << 16 & 0xFF0000) | (array[n++] << 8 & 0xFF00) | (array[n++] & 0xFF));
            final int n3 = (array[n2++] << 24 & 0xFF000000) | (array[n2++] << 16 & 0xFF0000) | (array[n2++] << 8 & 0xFF00) | (array[n2++] & 0xFF);
            final byte[] array2 = new byte[n3];
            System.arraycopy(array, n2, array2, 0, n3);
            array = array2;
        }
        return this.signature.verify(array);
    }
}
