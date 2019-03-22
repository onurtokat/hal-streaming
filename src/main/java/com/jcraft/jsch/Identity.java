// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

public interface Identity
{
    boolean setPassphrase(final byte[] p0) throws JSchException;
    
    byte[] getPublicKeyBlob();
    
    byte[] getSignature(final byte[] p0);
    
    boolean decrypt();
    
    String getAlgName();
    
    String getName();
    
    boolean isEncrypted();
    
    void clear();
}
