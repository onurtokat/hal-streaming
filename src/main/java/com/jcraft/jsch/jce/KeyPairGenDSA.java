// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch.jce;

import java.security.interfaces.DSAParams;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.KeyPair;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.DSAPrivateKey;
import java.security.SecureRandom;
import java.security.KeyPairGenerator;

public class KeyPairGenDSA implements com.jcraft.jsch.KeyPairGenDSA
{
    byte[] x;
    byte[] y;
    byte[] p;
    byte[] q;
    byte[] g;
    
    public void init(final int n) throws Exception {
        final KeyPairGenerator \u0131nstance = KeyPairGenerator.getInstance("DSA");
        \u0131nstance.initialize(n, new SecureRandom());
        final KeyPair generateKeyPair = \u0131nstance.generateKeyPair();
        final PublicKey public1 = generateKeyPair.getPublic();
        final PrivateKey private1 = generateKeyPair.getPrivate();
        this.x = ((DSAPrivateKey)private1).getX().toByteArray();
        this.y = ((DSAPublicKey)public1).getY().toByteArray();
        final DSAParams params = ((DSAPrivateKey)private1).getParams();
        this.p = params.getP().toByteArray();
        this.q = params.getQ().toByteArray();
        this.g = params.getG().toByteArray();
    }
    
    public byte[] getX() {
        return this.x;
    }
    
    public byte[] getY() {
        return this.y;
    }
    
    public byte[] getP() {
        return this.p;
    }
    
    public byte[] getQ() {
        return this.q;
    }
    
    public byte[] getG() {
        return this.g;
    }
}
