// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch.jce;

import java.security.MessageDigest;
import com.jcraft.jsch.HASH;

public class SHA1 implements HASH
{
    MessageDigest md;
    
    public int getBlockSize() {
        return 20;
    }
    
    public void init() throws Exception {
        try {
            this.md = MessageDigest.getInstance("SHA-1");
        }
        catch (Exception ex) {
            System.err.println(ex);
        }
    }
    
    public void update(final byte[] array, final int n, final int n2) throws Exception {
        this.md.update(array, n, n2);
    }
    
    public byte[] digest() throws Exception {
        return this.md.digest();
    }
}
