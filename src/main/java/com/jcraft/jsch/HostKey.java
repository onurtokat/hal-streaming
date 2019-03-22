// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

public class HostKey
{
    private static final byte[] sshdss;
    private static final byte[] sshrsa;
    protected static final int GUESS = 0;
    public static final int SSHDSS = 1;
    public static final int SSHRSA = 2;
    static final int UNKNOWN = 3;
    protected String host;
    protected int type;
    protected byte[] key;
    
    public HostKey(final String s, final byte[] array) throws JSchException {
        this(s, 0, array);
    }
    
    public HostKey(final String host, final int type, final byte[] key) throws JSchException {
        this.host = host;
        if (type == 0) {
            if (key[8] == 100) {
                this.type = 1;
            }
            else {
                if (key[8] != 114) {
                    throw new JSchException("invalid key type");
                }
                this.type = 2;
            }
        }
        else {
            this.type = type;
        }
        this.key = key;
    }
    
    public String getHost() {
        return this.host;
    }
    
    public String getType() {
        if (this.type == 1) {
            return new String(HostKey.sshdss);
        }
        if (this.type == 2) {
            return new String(HostKey.sshrsa);
        }
        return "UNKNOWN";
    }
    
    public String getKey() {
        return new String(Util.toBase64(this.key, 0, this.key.length));
    }
    
    public String getFingerPrint(final JSch sch) {
        HASH hash = null;
        try {
            hash = (HASH)Class.forName(JSch.getConfig("md5")).newInstance();
        }
        catch (Exception ex) {
            System.err.println("getFingerPrint: " + ex);
        }
        return Util.getFingerPrint(hash, this.key);
    }
    
    boolean isMatched(final String s) {
        return this.isIncluded(s);
    }
    
    private boolean isIncluded(final String s) {
        int i = 0;
        final String host = this.host;
        final int length = host.length();
        final int length2 = s.length();
        while (i < length) {
            final int index = host.indexOf(44, i);
            if (index == -1) {
                return length2 == length - i && host.regionMatches(true, i, s, 0, length2);
            }
            if (length2 == index - i && host.regionMatches(true, i, s, 0, length2)) {
                return true;
            }
            i = index + 1;
        }
        return false;
    }
    
    static {
        sshdss = "ssh-dss".getBytes();
        sshrsa = "ssh-rsa".getBytes();
    }
}
