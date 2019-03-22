// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch.jcraft;

import java.security.MessageDigest;
import com.jcraft.jsch.MAC;

public class HMACSHA1 extends HMAC implements MAC
{
    private static final String name = "hmac-sha1";
    
    public HMACSHA1() {
        MessageDigest \u0131nstance = null;
        try {
            \u0131nstance = MessageDigest.getInstance("SHA-1");
        }
        catch (Exception ex) {
            System.err.println(ex);
        }
        this.setH(\u0131nstance);
    }
    
    public String getName() {
        return "hmac-sha1";
    }
}
