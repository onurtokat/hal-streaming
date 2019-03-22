// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

public class DHGEX extends KeyExchange
{
    private static final int SSH_MSG_KEX_DH_GEX_GROUP = 31;
    private static final int SSH_MSG_KEX_DH_GEX_INIT = 32;
    private static final int SSH_MSG_KEX_DH_GEX_REPLY = 33;
    private static final int SSH_MSG_KEX_DH_GEX_REQUEST = 34;
    static int min;
    static int preferred;
    static int max;
    static final int RSA = 0;
    static final int DSS = 1;
    private int type;
    private int state;
    DH dh;
    byte[] V_S;
    byte[] V_C;
    byte[] I_S;
    byte[] I_C;
    private Buffer buf;
    private Packet packet;
    private byte[] p;
    private byte[] g;
    private byte[] e;
    
    public DHGEX() {
        this.type = 0;
    }
    
    public void init(final Session session, final byte[] v_S, final byte[] v_C, final byte[] \u0131_S, final byte[] \u0131_C) throws Exception {
        this.session = session;
        this.V_S = v_S;
        this.V_C = v_C;
        this.I_S = \u0131_S;
        this.I_C = \u0131_C;
        try {
            (this.sha = (HASH)Class.forName(session.getConfig("sha-1")).newInstance()).init();
        }
        catch (Exception ex) {
            System.err.println(ex);
        }
        this.buf = new Buffer();
        this.packet = new Packet(this.buf);
        try {
            (this.dh = (DH)Class.forName(session.getConfig("dh")).newInstance()).init();
        }
        catch (Exception ex2) {
            throw ex2;
        }
        this.packet.reset();
        this.buf.putByte((byte)34);
        this.buf.putInt(DHGEX.min);
        this.buf.putInt(DHGEX.preferred);
        this.buf.putInt(DHGEX.max);
        session.write(this.packet);
        if (JSch.getLogger().isEnabled(1)) {
            JSch.getLogger().log(1, "SSH_MSG_KEX_DH_GEX_REQUEST(" + DHGEX.min + "<" + DHGEX.preferred + "<" + DHGEX.max + ") sent");
            JSch.getLogger().log(1, "expecting SSH_MSG_KEX_DH_GEX_GROUP");
        }
        this.state = 31;
    }
    
    public boolean next(final Buffer buffer) throws Exception {
        switch (this.state) {
            case 31: {
                buffer.getInt();
                buffer.getByte();
                final int byte1 = buffer.getByte();
                if (byte1 != 31) {
                    System.err.println("type: must be SSH_MSG_KEX_DH_GEX_GROUP " + byte1);
                    return false;
                }
                this.p = buffer.getMPInt();
                this.g = buffer.getMPInt();
                this.dh.setP(this.p);
                this.dh.setG(this.g);
                this.e = this.dh.getE();
                this.packet.reset();
                this.buf.putByte((byte)32);
                this.buf.putMPInt(this.e);
                this.session.write(this.packet);
                if (JSch.getLogger().isEnabled(1)) {
                    JSch.getLogger().log(1, "SSH_MSG_KEX_DH_GEX_INIT sent");
                    JSch.getLogger().log(1, "expecting SSH_MSG_KEX_DH_GEX_REPLY");
                }
                this.state = 33;
                return true;
            }
            case 33: {
                buffer.getInt();
                buffer.getByte();
                final int byte2 = buffer.getByte();
                if (byte2 != 33) {
                    System.err.println("type: must be SSH_MSG_KEX_DH_GEX_REPLY " + byte2);
                    return false;
                }
                this.K_S = buffer.getString();
                final byte[] mpInt = buffer.getMPInt();
                final byte[] string = buffer.getString();
                this.dh.setF(mpInt);
                this.K = this.dh.getK();
                this.buf.reset();
                this.buf.putString(this.V_C);
                this.buf.putString(this.V_S);
                this.buf.putString(this.I_C);
                this.buf.putString(this.I_S);
                this.buf.putString(this.K_S);
                this.buf.putInt(DHGEX.min);
                this.buf.putInt(DHGEX.preferred);
                this.buf.putInt(DHGEX.max);
                this.buf.putMPInt(this.p);
                this.buf.putMPInt(this.g);
                this.buf.putMPInt(this.e);
                this.buf.putMPInt(mpInt);
                this.buf.putMPInt(this.K);
                final byte[] array = new byte[this.buf.getLength()];
                this.buf.getByte(array);
                this.sha.update(array, 0, array.length);
                this.H = this.sha.digest();
                int n = 0;
                final int n2 = (this.K_S[n++] << 24 & 0xFF000000) | (this.K_S[n++] << 16 & 0xFF0000) | (this.K_S[n++] << 8 & 0xFF00) | (this.K_S[n++] & 0xFF);
                final String s = new String(this.K_S, n, n2);
                int n3 = n + n2;
                boolean b = false;
                if (s.equals("ssh-rsa")) {
                    this.type = 0;
                    final int n4 = (this.K_S[n3++] << 24 & 0xFF000000) | (this.K_S[n3++] << 16 & 0xFF0000) | (this.K_S[n3++] << 8 & 0xFF00) | (this.K_S[n3++] & 0xFF);
                    final byte[] array2 = new byte[n4];
                    System.arraycopy(this.K_S, n3, array2, 0, n4);
                    int n5 = n3 + n4;
                    final byte[] array3 = array2;
                    final int n6 = (this.K_S[n5++] << 24 & 0xFF000000) | (this.K_S[n5++] << 16 & 0xFF0000) | (this.K_S[n5++] << 8 & 0xFF00) | (this.K_S[n5++] & 0xFF);
                    final byte[] array4 = new byte[n6];
                    System.arraycopy(this.K_S, n5, array4, 0, n6);
                    final byte[] array5 = array4;
                    SignatureRSA signatureRSA = null;
                    try {
                        signatureRSA = (SignatureRSA)Class.forName(this.session.getConfig("signature.rsa")).newInstance();
                        signatureRSA.init();
                    }
                    catch (Exception ex) {
                        System.err.println(ex);
                    }
                    signatureRSA.setPubKey(array3, array5);
                    signatureRSA.update(this.H);
                    b = signatureRSA.verify(string);
                    if (JSch.getLogger().isEnabled(1)) {
                        JSch.getLogger().log(1, "ssh_rsa_verify: signature " + b);
                    }
                }
                else if (s.equals("ssh-dss")) {
                    this.type = 1;
                    final int n7 = (this.K_S[n3++] << 24 & 0xFF000000) | (this.K_S[n3++] << 16 & 0xFF0000) | (this.K_S[n3++] << 8 & 0xFF00) | (this.K_S[n3++] & 0xFF);
                    final byte[] p = new byte[n7];
                    System.arraycopy(this.K_S, n3, p, 0, n7);
                    int n8 = n3 + n7;
                    this.p = p;
                    final int n9 = (this.K_S[n8++] << 24 & 0xFF000000) | (this.K_S[n8++] << 16 & 0xFF0000) | (this.K_S[n8++] << 8 & 0xFF00) | (this.K_S[n8++] & 0xFF);
                    final byte[] array6 = new byte[n9];
                    System.arraycopy(this.K_S, n8, array6, 0, n9);
                    int n10 = n8 + n9;
                    final byte[] array7 = array6;
                    final int n11 = (this.K_S[n10++] << 24 & 0xFF000000) | (this.K_S[n10++] << 16 & 0xFF0000) | (this.K_S[n10++] << 8 & 0xFF00) | (this.K_S[n10++] & 0xFF);
                    final byte[] g = new byte[n11];
                    System.arraycopy(this.K_S, n10, g, 0, n11);
                    int n12 = n10 + n11;
                    this.g = g;
                    final int n13 = (this.K_S[n12++] << 24 & 0xFF000000) | (this.K_S[n12++] << 16 & 0xFF0000) | (this.K_S[n12++] << 8 & 0xFF00) | (this.K_S[n12++] & 0xFF);
                    final byte[] array8 = new byte[n13];
                    System.arraycopy(this.K_S, n12, array8, 0, n13);
                    final byte[] array9 = array8;
                    SignatureDSA signatureDSA = null;
                    try {
                        signatureDSA = (SignatureDSA)Class.forName(this.session.getConfig("signature.dss")).newInstance();
                        signatureDSA.init();
                    }
                    catch (Exception ex2) {
                        System.err.println(ex2);
                    }
                    signatureDSA.setPubKey(array9, this.p, array7, this.g);
                    signatureDSA.update(this.H);
                    b = signatureDSA.verify(string);
                    if (JSch.getLogger().isEnabled(1)) {
                        JSch.getLogger().log(1, "ssh_dss_verify: signature " + b);
                    }
                }
                else {
                    System.err.println("unknown alg");
                }
                this.state = 0;
                return b;
            }
            default: {
                return false;
            }
        }
    }
    
    public String getKeyType() {
        if (this.type == 1) {
            return "DSA";
        }
        return "RSA";
    }
    
    public int getState() {
        return this.state;
    }
    
    static {
        DHGEX.min = 1024;
        DHGEX.preferred = 1024;
        DHGEX.max = 1024;
    }
}
