// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

public interface SignatureDSA
{
    void init() throws Exception;
    
    void setPubKey(final byte[] p0, final byte[] p1, final byte[] p2, final byte[] p3) throws Exception;
    
    void setPrvKey(final byte[] p0, final byte[] p1, final byte[] p2, final byte[] p3) throws Exception;
    
    void update(final byte[] p0) throws Exception;
    
    boolean verify(final byte[] p0) throws Exception;
    
    byte[] sign() throws Exception;
}
