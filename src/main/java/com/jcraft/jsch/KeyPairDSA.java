// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

public class KeyPairDSA extends KeyPair
{
    private byte[] P_array;
    private byte[] Q_array;
    private byte[] G_array;
    private byte[] pub_array;
    private byte[] prv_array;
    private int key_size;
    private static final byte[] begin;
    private static final byte[] end;
    private static final byte[] sshdss;
    
    public KeyPairDSA(final JSch sch) {
        super(sch);
        this.key_size = 1024;
    }
    
    void generate(final int key_size) throws JSchException {
        this.key_size = key_size;
        try {
            final KeyPairGenDSA keyPairGenDSA = (KeyPairGenDSA)Class.forName(JSch.getConfig("keypairgen.dsa")).newInstance();
            keyPairGenDSA.init(key_size);
            this.P_array = keyPairGenDSA.getP();
            this.Q_array = keyPairGenDSA.getQ();
            this.G_array = keyPairGenDSA.getG();
            this.pub_array = keyPairGenDSA.getY();
            this.prv_array = keyPairGenDSA.getX();
        }
        catch (Exception ex) {
            if (ex instanceof Throwable) {
                throw new JSchException(ex.toString(), ex);
            }
            throw new JSchException(ex.toString());
        }
    }
    
    byte[] getBegin() {
        return KeyPairDSA.begin;
    }
    
    byte[] getEnd() {
        return KeyPairDSA.end;
    }
    
    byte[] getPrivateKey() {
        final int n = 1 + this.countLength(1) + 1 + 1 + this.countLength(this.P_array.length) + this.P_array.length + 1 + this.countLength(this.Q_array.length) + this.Q_array.length + 1 + this.countLength(this.G_array.length) + this.G_array.length + 1 + this.countLength(this.pub_array.length) + this.pub_array.length + 1 + this.countLength(this.prv_array.length) + this.prv_array.length;
        final byte[] array = new byte[1 + this.countLength(n) + n];
        this.writeINTEGER(array, this.writeINTEGER(array, this.writeINTEGER(array, this.writeINTEGER(array, this.writeINTEGER(array, this.writeINTEGER(array, this.writeSEQUENCE(array, 0, n), new byte[1]), this.P_array), this.Q_array), this.G_array), this.pub_array), this.prv_array);
        return array;
    }
    
    boolean parse(final byte[] array) {
        try {
            if (this.vendor == 1) {
                if (array[0] != 48) {
                    final Buffer buffer = new Buffer(array);
                    buffer.getInt();
                    this.P_array = buffer.getMPIntBits();
                    this.G_array = buffer.getMPIntBits();
                    this.Q_array = buffer.getMPIntBits();
                    this.pub_array = buffer.getMPIntBits();
                    this.prv_array = buffer.getMPIntBits();
                    return true;
                }
                return false;
            }
            else {
                int n = 0;
                if (array[n] != 48) {
                    return false;
                }
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
                System.arraycopy(array, n7, this.P_array = new byte[n8], 0, n8);
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
                System.arraycopy(array, n10, this.Q_array = new byte[n11], 0, n11);
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
                System.arraycopy(array, n13, this.G_array = new byte[n14], 0, n14);
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
                System.arraycopy(array, n16, this.pub_array = new byte[n17], 0, n17);
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
                System.arraycopy(array, n19, this.prv_array = new byte[n20], 0, n20);
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
        if (this.P_array == null) {
            return null;
        }
        final Buffer buffer = new Buffer(KeyPairDSA.sshdss.length + 4 + this.P_array.length + 4 + this.Q_array.length + 4 + this.G_array.length + 4 + this.pub_array.length + 4);
        buffer.putString(KeyPairDSA.sshdss);
        buffer.putString(this.P_array);
        buffer.putString(this.Q_array);
        buffer.putString(this.G_array);
        buffer.putString(this.pub_array);
        return buffer.buffer;
    }
    
    byte[] getKeyTypeName() {
        return KeyPairDSA.sshdss;
    }
    
    public int getKeyType() {
        return 1;
    }
    
    public int getKeySize() {
        return this.key_size;
    }
    
    public void dispose() {
        super.dispose();
        Util.bzero(this.prv_array);
    }
    
    static {
        begin = "-----BEGIN DSA PRIVATE KEY-----".getBytes();
        end = "-----END DSA PRIVATE KEY-----".getBytes();
        sshdss = "ssh-dss".getBytes();
    }
}
