// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

public class KeyPairRSA extends KeyPair
{
    private byte[] prv_array;
    private byte[] pub_array;
    private byte[] n_array;
    private byte[] p_array;
    private byte[] q_array;
    private byte[] ep_array;
    private byte[] eq_array;
    private byte[] c_array;
    private int key_size;
    private static final byte[] begin;
    private static final byte[] end;
    private static final byte[] sshrsa;
    
    public KeyPairRSA(final JSch sch) {
        super(sch);
        this.key_size = 1024;
    }
    
    void generate(final int key_size) throws JSchException {
        this.key_size = key_size;
        try {
            final KeyPairGenRSA keyPairGenRSA = (KeyPairGenRSA)Class.forName(JSch.getConfig("keypairgen.rsa")).newInstance();
            keyPairGenRSA.init(key_size);
            this.pub_array = keyPairGenRSA.getE();
            this.prv_array = keyPairGenRSA.getD();
            this.n_array = keyPairGenRSA.getN();
            this.p_array = keyPairGenRSA.getP();
            this.q_array = keyPairGenRSA.getQ();
            this.ep_array = keyPairGenRSA.getEP();
            this.eq_array = keyPairGenRSA.getEQ();
            this.c_array = keyPairGenRSA.getC();
        }
        catch (Exception ex) {
            if (ex instanceof Throwable) {
                throw new JSchException(ex.toString(), ex);
            }
            throw new JSchException(ex.toString());
        }
    }
    
    byte[] getBegin() {
        return KeyPairRSA.begin;
    }
    
    byte[] getEnd() {
        return KeyPairRSA.end;
    }
    
    byte[] getPrivateKey() {
        final int n = 1 + this.countLength(1) + 1 + 1 + this.countLength(this.n_array.length) + this.n_array.length + 1 + this.countLength(this.pub_array.length) + this.pub_array.length + 1 + this.countLength(this.prv_array.length) + this.prv_array.length + 1 + this.countLength(this.p_array.length) + this.p_array.length + 1 + this.countLength(this.q_array.length) + this.q_array.length + 1 + this.countLength(this.ep_array.length) + this.ep_array.length + 1 + this.countLength(this.eq_array.length) + this.eq_array.length + 1 + this.countLength(this.c_array.length) + this.c_array.length;
        final byte[] array = new byte[1 + this.countLength(n) + n];
        this.writeINTEGER(array, this.writeINTEGER(array, this.writeINTEGER(array, this.writeINTEGER(array, this.writeINTEGER(array, this.writeINTEGER(array, this.writeINTEGER(array, this.writeINTEGER(array, this.writeINTEGER(array, this.writeSEQUENCE(array, 0, n), new byte[1]), this.n_array), this.pub_array), this.prv_array), this.p_array), this.q_array), this.ep_array), this.eq_array), this.c_array);
        return array;
    }
    
    boolean parse(final byte[] array) {
        try {
            int n = 0;
            if (this.vendor == 1) {
                if (array[n] != 48) {
                    final Buffer buffer = new Buffer(array);
                    this.pub_array = buffer.getMPIntBits();
                    this.prv_array = buffer.getMPIntBits();
                    this.n_array = buffer.getMPIntBits();
                    buffer.getMPIntBits();
                    this.p_array = buffer.getMPIntBits();
                    this.q_array = buffer.getMPIntBits();
                    return true;
                }
                return false;
            }
            else {
                ++n;
                final int n2 = array[n++] & 0xFF;
                if ((n2 & 0x80) != 0x0) {
                    int n3 = n2 & 0x7F;
                    int n4 = 0;
                    while (n3-- > 0) {
                        n4 = (n4 << 8) + (array[n++] & 0xFF);
                    }
                }
                if (array[n] != 2) {
                    return false;
                }
                ++n;
                int n5 = array[n++] & 0xFF;
                if ((n5 & 0x80) != 0x0) {
                    int n6 = n5 & 0x7F;
                    n5 = 0;
                    while (n6-- > 0) {
                        n5 = (n5 << 8) + (array[n++] & 0xFF);
                    }
                }
                int n7 = n + n5;
                ++n7;
                int n8 = array[n7++] & 0xFF;
                if ((n8 & 0x80) != 0x0) {
                    int n9 = n8 & 0x7F;
                    n8 = 0;
                    while (n9-- > 0) {
                        n8 = (n8 << 8) + (array[n7++] & 0xFF);
                    }
                }
                System.arraycopy(array, n7, this.n_array = new byte[n8], 0, n8);
                int n10 = n7 + n8;
                ++n10;
                int n11 = array[n10++] & 0xFF;
                if ((n11 & 0x80) != 0x0) {
                    int n12 = n11 & 0x7F;
                    n11 = 0;
                    while (n12-- > 0) {
                        n11 = (n11 << 8) + (array[n10++] & 0xFF);
                    }
                }
                System.arraycopy(array, n10, this.pub_array = new byte[n11], 0, n11);
                int n13 = n10 + n11;
                ++n13;
                int n14 = array[n13++] & 0xFF;
                if ((n14 & 0x80) != 0x0) {
                    int n15 = n14 & 0x7F;
                    n14 = 0;
                    while (n15-- > 0) {
                        n14 = (n14 << 8) + (array[n13++] & 0xFF);
                    }
                }
                System.arraycopy(array, n13, this.prv_array = new byte[n14], 0, n14);
                int n16 = n13 + n14;
                ++n16;
                int n17 = array[n16++] & 0xFF;
                if ((n17 & 0x80) != 0x0) {
                    int n18 = n17 & 0x7F;
                    n17 = 0;
                    while (n18-- > 0) {
                        n17 = (n17 << 8) + (array[n16++] & 0xFF);
                    }
                }
                System.arraycopy(array, n16, this.p_array = new byte[n17], 0, n17);
                int n19 = n16 + n17;
                ++n19;
                int n20 = array[n19++] & 0xFF;
                if ((n20 & 0x80) != 0x0) {
                    int n21 = n20 & 0x7F;
                    n20 = 0;
                    while (n21-- > 0) {
                        n20 = (n20 << 8) + (array[n19++] & 0xFF);
                    }
                }
                System.arraycopy(array, n19, this.q_array = new byte[n20], 0, n20);
                int n22 = n19 + n20;
                ++n22;
                int n23 = array[n22++] & 0xFF;
                if ((n23 & 0x80) != 0x0) {
                    int n24 = n23 & 0x7F;
                    n23 = 0;
                    while (n24-- > 0) {
                        n23 = (n23 << 8) + (array[n22++] & 0xFF);
                    }
                }
                System.arraycopy(array, n22, this.ep_array = new byte[n23], 0, n23);
                int n25 = n22 + n23;
                ++n25;
                int n26 = array[n25++] & 0xFF;
                if ((n26 & 0x80) != 0x0) {
                    int n27 = n26 & 0x7F;
                    n26 = 0;
                    while (n27-- > 0) {
                        n26 = (n26 << 8) + (array[n25++] & 0xFF);
                    }
                }
                System.arraycopy(array, n25, this.eq_array = new byte[n26], 0, n26);
                int n28 = n25 + n26;
                ++n28;
                int n29 = array[n28++] & 0xFF;
                if ((n29 & 0x80) != 0x0) {
                    int n30 = n29 & 0x7F;
                    n29 = 0;
                    while (n30-- > 0) {
                        n29 = (n29 << 8) + (array[n28++] & 0xFF);
                    }
                }
                System.arraycopy(array, n28, this.c_array = new byte[n29], 0, n29);
            }
        }
        catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    public byte[] getPublicKeyBlob() {
        final byte[] publicKeyBlob = super.getPublicKeyBlob();
        if (publicKeyBlob != null) {
            return publicKeyBlob;
        }
        if (this.pub_array == null) {
            return null;
        }
        final Buffer buffer = new Buffer(KeyPairRSA.sshrsa.length + 4 + this.pub_array.length + 4 + this.n_array.length + 4);
        buffer.putString(KeyPairRSA.sshrsa);
        buffer.putString(this.pub_array);
        buffer.putString(this.n_array);
        return buffer.buffer;
    }
    
    byte[] getKeyTypeName() {
        return KeyPairRSA.sshrsa;
    }
    
    public int getKeyType() {
        return 2;
    }
    
    public int getKeySize() {
        return this.key_size;
    }
    
    public void dispose() {
        super.dispose();
        Util.bzero(this.prv_array);
    }
    
    static {
        begin = "-----BEGIN RSA PRIVATE KEY-----".getBytes();
        end = "-----END RSA PRIVATE KEY-----".getBytes();
        sshrsa = "ssh-rsa".getBytes();
    }
}
