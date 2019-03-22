// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch.jcraft;

import java.security.MessageDigest;
import com.jcraft.jsch.MAC;

public class HMACMD5 extends HMAC implements MAC
{
    private static final String name = "hmac-md5";
    
    public HMACMD5() {
        MessageDigest \u0131nstance = null;
        try {
            \u0131nstance = MessageDigest.getInstance("MD5");
        }
        catch (Exception ex) {
            System.err.println(ex);
        }
        this.setH(\u0131nstance);
    }
    
    public String getName() {
        return "hmac-md5";
    }
}
