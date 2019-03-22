// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch.jce;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.SecureRandom;
import java.security.KeyPairGenerator;

public class KeyPairGenRSA implements com.jcraft.jsch.KeyPairGenRSA
{
    byte[] d;
    byte[] e;
    byte[] n;
    byte[] c;
    byte[] ep;
    byte[] eq;
    byte[] p;
    byte[] q;
    
    public void init(final int n) throws Exception {
        final KeyPairGenerator \u0131nstance = KeyPairGenerator.getInstance("RSA");
        \u0131nstance.initialize(n, new SecureRandom());
        final KeyPair generateKeyPair = \u0131nstance.generateKeyPair();
        final PublicKey public1 = generateKeyPair.getPublic();
        final PrivateKey private1 = generateKeyPair.getPrivate();
        this.d = ((RSAPrivateKey)private1).getPrivateExponent().toByteArray();
        this.e = ((RSAPublicKey)public1).getPublicExponent().toByteArray();
        this.n = ((RSAPrivateCrtKey)private1).getModulus().toByteArray();
        this.c = ((RSAPrivateCrtKey)private1).getCrtCoefficient().toByteArray();
        this.ep = ((RSAPrivateCrtKey)private1).getPrimeExponentP().toByteArray();
        this.eq = ((RSAPrivateCrtKey)private1).getPrimeExponentQ().toByteArray();
        this.p = ((RSAPrivateCrtKey)private1).getPrimeP().toByteArray();
        this.q = ((RSAPrivateCrtKey)private1).getPrimeQ().toByteArray();
    }
    
    public byte[] getD() {
        return this.d;
    }
    
    public byte[] getE() {
        return this.e;
    }
    
    public byte[] getN() {
        return this.n;
    }
    
    public byte[] getC() {
        return this.c;
    }
    
    public byte[] getEP() {
        return this.ep;
    }
    
    public byte[] getEQ() {
        return this.eq;
    }
    
    public byte[] getP() {
        return this.p;
    }
    
    public byte[] getQ() {
        return this.q;
    }
}
