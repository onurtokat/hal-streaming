// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch.jce;

import java.security.spec.KeySpec;
import javax.crypto.spec.DHPublicKeySpec;
import java.security.KeyFactory;
import java.security.KeyPair;
import javax.crypto.interfaces.DHPublicKey;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.KeyAgreement;
import java.security.KeyPairGenerator;
import java.math.BigInteger;

public class DH implements com.jcraft.jsch.DH
{
    BigInteger p;
    BigInteger g;
    BigInteger e;
    byte[] e_array;
    BigInteger f;
    BigInteger K;
    byte[] K_array;
    private KeyPairGenerator myKpairGen;
    private KeyAgreement myKeyAgree;
    
    public void init() throws Exception {
        this.myKpairGen = KeyPairGenerator.getInstance("DH");
        this.myKeyAgree = KeyAgreement.getInstance("DH");
    }
    
    public byte[] getE() throws Exception {
        if (this.e == null) {
            this.myKpairGen.initialize(new DHParameterSpec(this.p, this.g));
            final KeyPair generateKeyPair = this.myKpairGen.generateKeyPair();
            this.myKeyAgree.init(generateKeyPair.getPrivate());
            generateKeyPair.getPublic().getEncoded();
            this.e = ((DHPublicKey)generateKeyPair.getPublic()).getY();
            this.e_array = this.e.toByteArray();
        }
        return this.e_array;
    }
    
    public byte[] getK() throws Exception {
        if (this.K == null) {
            this.myKeyAgree.doPhase(KeyFactory.getInstance("DH").generatePublic(new DHPublicKeySpec(this.f, this.p, this.g)), true);
            final byte[] generateSecret = this.myKeyAgree.generateSecret();
            this.K = new BigInteger(generateSecret);
            this.K_array = this.K.toByteArray();
            this.K_array = generateSecret;
        }
        return this.K_array;
    }
    
    public void setP(final byte[] array) {
        this.setP(new BigInteger(array));
    }
    
    public void setG(final byte[] array) {
        this.setG(new BigInteger(array));
    }
    
    public void setF(final byte[] array) {
        this.setF(new BigInteger(array));
    }
    
    void setP(final BigInteger p) {
        this.p = p;
    }
    
    void setG(final BigInteger g) {
        this.g = g;
    }
    
    void setF(final BigInteger f) {
        this.f = f;
    }
}
