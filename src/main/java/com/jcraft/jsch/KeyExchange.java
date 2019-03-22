// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

public abstract class KeyExchange
{
    static final int PROPOSAL_KEX_ALGS = 0;
    static final int PROPOSAL_SERVER_HOST_KEY_ALGS = 1;
    static final int PROPOSAL_ENC_ALGS_CTOS = 2;
    static final int PROPOSAL_ENC_ALGS_STOC = 3;
    static final int PROPOSAL_MAC_ALGS_CTOS = 4;
    static final int PROPOSAL_MAC_ALGS_STOC = 5;
    static final int PROPOSAL_COMP_ALGS_CTOS = 6;
    static final int PROPOSAL_COMP_ALGS_STOC = 7;
    static final int PROPOSAL_LANG_CTOS = 8;
    static final int PROPOSAL_LANG_STOC = 9;
    static final int PROPOSAL_MAX = 10;
    static String kex;
    static String server_host_key;
    static String enc_c2s;
    static String enc_s2c;
    static String mac_c2s;
    static String mac_s2c;
    static String lang_c2s;
    static String lang_s2c;
    public static final int STATE_END = 0;
    protected Session session;
    protected HASH sha;
    protected byte[] K;
    protected byte[] H;
    protected byte[] K_S;
    
    public KeyExchange() {
        this.session = null;
        this.sha = null;
        this.K = null;
        this.H = null;
        this.K_S = null;
    }
    
    public abstract void init(final Session p0, final byte[] p1, final byte[] p2, final byte[] p3, final byte[] p4) throws Exception;
    
    public abstract boolean next(final Buffer p0) throws Exception;
    
    public abstract String getKeyType();
    
    public abstract int getState();
    
    protected static String[] guess(final byte[] array, final byte[] array2) {
        final String[] array3 = new String[10];
        final Buffer buffer = new Buffer(array);
        buffer.setOffSet(17);
        final Buffer buffer2 = new Buffer(array2);
        buffer2.setOffSet(17);
        for (int i = 0; i < 10; ++i) {
            final byte[] string = buffer.getString();
            final byte[] string2 = buffer2.getString();
            int j = 0;
            int n = 0;
        Label_0222:
            while (j < string2.length) {
                while (j < string2.length && string2[j] != 44) {
                    ++j;
                }
                if (n == j) {
                    return null;
                }
                final String s = new String(string2, n, j - n);
                int k = 0;
                int n2 = 0;
                while (k < string.length) {
                    while (k < string.length && string[k] != 44) {
                        ++k;
                    }
                    if (n2 == k) {
                        return null;
                    }
                    if (s.equals(new String(string, n2, k - n2))) {
                        array3[i] = s;
                        break Label_0222;
                    }
                    n2 = ++k;
                }
                n = ++j;
            }
            if (j == 0) {
                array3[i] = "";
            }
            else if (array3[i] == null) {
                return null;
            }
        }
        if (JSch.getLogger().isEnabled(1)) {
            JSch.getLogger().log(1, "kex: server->client " + array3[3] + " " + array3[5] + " " + array3[7]);
            JSch.getLogger().log(1, "kex: client->server " + array3[2] + " " + array3[4] + " " + array3[6]);
        }
        return array3;
    }
    
    public String getFingerPrint() {
        HASH hash = null;
        try {
            hash = (HASH)Class.forName(this.session.getConfig("md5")).newInstance();
        }
        catch (Exception ex) {
            System.err.println("getFingerPrint: " + ex);
        }
        return Util.getFingerPrint(hash, this.getHostKey());
    }
    
    byte[] getK() {
        return this.K;
    }
    
    byte[] getH() {
        return this.H;
    }
    
    HASH getHash() {
        return this.sha;
    }
    
    byte[] getHostKey() {
        return this.K_S;
    }
    
    static {
        KeyExchange.kex = "diffie-hellman-group1-sha1";
        KeyExchange.server_host_key = "ssh-rsa,ssh-dss";
        KeyExchange.enc_c2s = "blowfish-cbc";
        KeyExchange.enc_s2c = "blowfish-cbc";
        KeyExchange.mac_c2s = "hmac-md5";
        KeyExchange.mac_s2c = "hmac-md5";
        KeyExchange.lang_c2s = "";
        KeyExchange.lang_s2c = "";
    }
}
