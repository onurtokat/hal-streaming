// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

public class DHG1 extends KeyExchange
{
    static final byte[] g;
    static final byte[] p;
    private static final int SSH_MSG_KEXDH_INIT = 30;
    private static final int SSH_MSG_KEXDH_REPLY = 31;
    static final int RSA = 0;
    static final int DSS = 1;
    private int type;
    private int state;
    DH dh;
    byte[] V_S;
    byte[] V_C;
    byte[] I_S;
    byte[] I_C;
    byte[] e;
    private Buffer buf;
    private Packet packet;
    
    public DHG1() {
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
        this.dh.setP(DHG1.p);
        this.dh.setG(DHG1.g);
        this.e = this.dh.getE();
        this.packet.reset();
        this.buf.putByte((byte)30);
        this.buf.putMPInt(this.e);
        session.write(this.packet);
        if (JSch.getLogger().isEnabled(1)) {
            JSch.getLogger().log(1, "SSH_MSG_KEXDH_INIT sent");
            JSch.getLogger().log(1, "expecting SSH_MSG_KEXDH_REPLY");
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
                    System.err.println("type: must be 31 " + byte1);
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
                    final byte[] array6 = new byte[n7];
                    System.arraycopy(this.K_S, n3, array6, 0, n7);
                    int n8 = n3 + n7;
                    final byte[] array7 = array6;
                    final int n9 = (this.K_S[n8++] << 24 & 0xFF000000) | (this.K_S[n8++] << 16 & 0xFF0000) | (this.K_S[n8++] << 8 & 0xFF00) | (this.K_S[n8++] & 0xFF);
                    final byte[] array8 = new byte[n9];
                    System.arraycopy(this.K_S, n8, array8, 0, n9);
                    int n10 = n8 + n9;
                    final byte[] array9 = array8;
                    final int n11 = (this.K_S[n10++] << 24 & 0xFF000000) | (this.K_S[n10++] << 16 & 0xFF0000) | (this.K_S[n10++] << 8 & 0xFF00) | (this.K_S[n10++] & 0xFF);
                    final byte[] array10 = new byte[n11];
                    System.arraycopy(this.K_S, n10, array10, 0, n11);
                    int n12 = n10 + n11;
                    final byte[] array11 = array10;
                    final int n13 = (this.K_S[n12++] << 24 & 0xFF000000) | (this.K_S[n12++] << 16 & 0xFF0000) | (this.K_S[n12++] << 8 & 0xFF00) | (this.K_S[n12++] & 0xFF);
                    final byte[] array12 = new byte[n13];
                    System.arraycopy(this.K_S, n12, array12, 0, n13);
                    final byte[] array13 = array12;
                    SignatureDSA signatureDSA = null;
                    try {
                        signatureDSA = (SignatureDSA)Class.forName(this.session.getConfig("signature.dss")).newInstance();
                        signatureDSA.init();
                    }
                    catch (Exception ex2) {
                        System.err.println(ex2);
                    }
                    signatureDSA.setPubKey(array13, array7, array9, array11);
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
        g = new byte[] { 2 };
        p = new byte[] { 0, -1, -1, -1, -1, -1, -1, -1, -1, -55, 15, -38, -94, 33, 104, -62, 52, -60, -58, 98, -117, -128, -36, 28, -47, 41, 2, 78, 8, -118, 103, -52, 116, 2, 11, -66, -90, 59, 19, -101, 34, 81, 74, 8, 121, -114, 52, 4, -35, -17, -107, 25, -77, -51, 58, 67, 27, 48, 43, 10, 109, -14, 95, 20, 55, 79, -31, 53, 109, 109, 81, -62, 69, -28, -123, -75, 118, 98, 94, 126, -58, -12, 76, 66, -23, -90, 55, -19, 107, 11, -1, 92, -74, -12, 6, -73, -19, -18, 56, 107, -5, 90, -119, -97, -91, -82, -97, 36, 17, 124, 75, 31, -26, 73, 40, 102, 81, -20, -26, 83, -127, -1, -1, -1, -1, -1, -1, -1, -1 };
    }
}
