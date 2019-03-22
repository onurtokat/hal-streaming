// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch.jce;

import java.security.spec.DSAPrivateKeySpec;
import java.security.spec.KeySpec;
import java.security.spec.DSAPublicKeySpec;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.Signature;

public class SignatureDSA implements com.jcraft.jsch.SignatureDSA
{
    Signature signature;
    KeyFactory keyFactory;
    
    public void init() throws Exception {
        this.signature = Signature.getInstance("SHA1withDSA");
        this.keyFactory = KeyFactory.getInstance("DSA");
    }
    
    public void setPubKey(final byte[] array, final byte[] array2, final byte[] array3, final byte[] array4) throws Exception {
        this.signature.initVerify(this.keyFactory.generatePublic(new DSAPublicKeySpec(new BigInteger(array), new BigInteger(array2), new BigInteger(array3), new BigInteger(array4))));
    }
    
    public void setPrvKey(final byte[] array, final byte[] array2, final byte[] array3, final byte[] array4) throws Exception {
        this.signature.initSign(this.keyFactory.generatePrivate(new DSAPrivateKeySpec(new BigInteger(array), new BigInteger(array2), new BigInteger(array3), new BigInteger(array4))));
    }
    
    public byte[] sign() throws Exception {
        final byte[] sign = this.signature.sign();
        int n = 3;
        final int n2 = sign[n++] & 0xFF;
        final byte[] array = new byte[n2];
        System.arraycopy(sign, n, array, 0, array.length);
        int n3 = n + n2 + 1;
        final byte[] array2 = new byte[sign[n3++] & 0xFF];
        System.arraycopy(sign, n3, array2, 0, array2.length);
        final byte[] array3 = new byte[40];
        System.arraycopy(array, (array.length > 20) ? 1 : 0, array3, (array.length > 20) ? 0 : (20 - array.length), (array.length > 20) ? 20 : array.length);
        System.arraycopy(array2, (array2.length > 20) ? 1 : 0, array3, (array2.length > 20) ? 20 : (40 - array2.length), (array2.length > 20) ? 20 : array2.length);
        return array3;
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
        final int n4 = ((array[0] & 0x80) != 0x0) ? 1 : 0;
        final int n5 = ((array[20] & 0x80) != 0x0) ? 1 : 0;
        final byte[] array3 = new byte[array.length + 6 + n4 + n5];
        array3[0] = 48;
        array3[1] = 44;
        final byte[] array4 = array3;
        final int n6 = 1;
        array4[n6] += (byte)n4;
        final byte[] array5 = array3;
        final int n7 = 1;
        array5[n7] += (byte)n5;
        array3[2] = 2;
        array3[3] = 20;
        final byte[] array6 = array3;
        final int n8 = 3;
        array6[n8] += (byte)n4;
        System.arraycopy(array, 0, array3, 4 + n4, 20);
        array3[4 + array3[3]] = 2;
        array3[5 + array3[3]] = 20;
        final byte[] array7 = array3;
        final byte b = (byte)(5 + array3[3]);
        array7[b] += (byte)n5;
        System.arraycopy(array, 20, array3, 6 + array3[3] + n5, 20);
        array = array3;
        return this.signature.verify(array);
    }
}
