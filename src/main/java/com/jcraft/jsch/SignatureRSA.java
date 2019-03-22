// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

public interface SignatureRSA
{
    void init() throws Exception;
    
    void setPubKey(final byte[] p0, final byte[] p1) throws Exception;
    
    void setPrvKey(final byte[] p0, final byte[] p1) throws Exception;
    
    void update(final byte[] p0) throws Exception;
    
    boolean verify(final byte[] p0) throws Exception;
    
    byte[] sign() throws Exception;
}
