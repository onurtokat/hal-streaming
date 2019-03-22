// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

import java.io.FileInputStream;
import java.io.File;

class IdentityFile implements Identity
{
    String identity;
    byte[] key;
    byte[] iv;
    private JSch jsch;
    private HASH hash;
    private byte[] encoded_data;
    private Cipher cipher;
    private byte[] P_array;
    private byte[] Q_array;
    private byte[] G_array;
    private byte[] pub_array;
    private byte[] prv_array;
    private byte[] n_array;
    private byte[] e_array;
    private byte[] d_array;
    private String algname;
    private static final int ERROR = 0;
    private static final int RSA = 1;
    private static final int DSS = 2;
    private static final int UNKNOWN = 3;
    private static final int OPENSSH = 0;
    private static final int FSECURE = 1;
    private static final int PUTTY = 2;
    private int type;
    private int keytype;
    private byte[] publickeyblob;
    private boolean encrypted;
    
    static IdentityFile newInstance(final String s, final String s2, final JSch sch) throws JSchException {
        byte[] array = null;
        FileInputStream fileInputStream = null;
        byte[] array2;
        try {
            final File file = new File(s);
            fileInputStream = new FileInputStream(s);
            array2 = new byte[(int)file.length()];
            int n = 0;
            while (true) {
                final int read = fileInputStream.read(array2, n, array2.length - n);
                if (read <= 0) {
                    break;
                }
                n += read;
            }
            fileInputStream.close();
        }
        catch (Exception ex) {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            }
            catch (Exception ex3) {}
            if (ex instanceof Throwable) {
                throw new JSchException(ex.toString(), ex);
            }
            throw new JSchException(ex.toString());
        }
        String string = s2;
        if (s2 == null) {
            string = s + ".pub";
        }
        try {
            final File file2 = new File(string);
            fileInputStream = new FileInputStream(string);
            array = new byte[(int)file2.length()];
            int n2 = 0;
            while (true) {
                final int read2 = fileInputStream.read(array, n2, array.length - n2);
                if (read2 <= 0) {
                    break;
                }
                n2 += read2;
            }
            fileInputStream.close();
        }
        catch (Exception ex2) {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            }
            catch (Exception ex4) {}
            if (s2 != null) {
                if (ex2 instanceof Throwable) {
                    throw new JSchException(ex2.toString(), ex2);
                }
                throw new JSchException(ex2.toString());
            }
        }
        return newInstance(s, array2, array, sch);
    }
    
    static IdentityFile newInstance(final String s, final byte[] array, final byte[] array2, final JSch sch) throws JSchException {
        try {
            return new IdentityFile(s, array, array2, sch);
        }
        finally {
            Util.bzero(array);
        }
    }
    
    private IdentityFile(final String identity, final byte[] array, final byte[] array2, final JSch jsch) throws JSchException {
        this.algname = "ssh-rsa";
        this.type = 0;
        this.keytype = 0;
        this.publickeyblob = null;
        this.encrypted = true;
        this.identity = identity;
        this.jsch = jsch;
        try {
            this.cipher = (Cipher)Class.forName(JSch.getConfig("3des-cbc")).newInstance();
            this.key = new byte[this.cipher.getBlockSize()];
            this.iv = new byte[this.cipher.getIVSize()];
            (this.hash = (HASH)Class.forName(JSch.getConfig("md5")).newInstance()).init();
            int length = array.length;
            int i = 0;
            while (i < length) {
                if (array[i] == 66 && array[i + 1] == 69 && array[i + 2] == 71 && array[i + 3] == 73) {
                    i += 6;
                    if (array[i] == 68 && array[i + 1] == 83 && array[i + 2] == 65) {
                        this.type = 2;
                    }
                    else if (array[i] == 82 && array[i + 1] == 83 && array[i + 2] == 65) {
                        this.type = 1;
                    }
                    else {
                        if (array[i] != 83 || array[i + 1] != 83 || array[i + 2] != 72) {
                            throw new JSchException("invalid privatekey: " + this.identity);
                        }
                        this.type = 3;
                        this.keytype = 1;
                    }
                    i += 3;
                }
                else if (array[i] == 65 && array[i + 1] == 69 && array[i + 2] == 83 && array[i + 3] == 45 && array[i + 4] == 50 && array[i + 5] == 53 && array[i + 6] == 54 && array[i + 7] == 45) {
                    i += 8;
                    if (!Session.checkCipher(JSch.getConfig("aes256-cbc"))) {
                        throw new JSchException("privatekey: aes256-cbc is not available " + this.identity);
                    }
                    this.cipher = (Cipher)Class.forName(JSch.getConfig("aes256-cbc")).newInstance();
                    this.key = new byte[this.cipher.getBlockSize()];
                    this.iv = new byte[this.cipher.getIVSize()];
                }
                else if (array[i] == 67 && array[i + 1] == 66 && array[i + 2] == 67 && array[i + 3] == 44) {
                    i += 4;
                    for (int j = 0; j < this.iv.length; ++j) {
                        this.iv[j] = (byte)((this.a2b(array[i++]) << 4 & 0xF0) + (this.a2b(array[i++]) & 0xF));
                    }
                }
                else if (array[i] == 13 && i + 1 < array.length && array[i + 1] == 10) {
                    ++i;
                }
                else {
                    if (array[i] == 10 && i + 1 < array.length) {
                        if (array[i + 1] == 10) {
                            i += 2;
                            break;
                        }
                        if (array[i + 1] == 13 && i + 2 < array.length && array[i + 2] == 10) {
                            i += 3;
                            break;
                        }
                        boolean b = false;
                        for (int k = i + 1; k < array.length; ++k) {
                            if (array[k] == 10) {
                                break;
                            }
                            if (array[k] == 58) {
                                b = true;
                                break;
                            }
                        }
                        if (!b) {
                            ++i;
                            this.encrypted = false;
                            break;
                        }
                    }
                    ++i;
                }
            }
            if (this.type == 0) {
                throw new JSchException("invalid privatekey: " + this.identity);
            }
            final int n = i;
            while (i < length) {
                if (array[i] == 10) {
                    final int n2 = (array[i - 1] == 13) ? 1 : 0;
                    System.arraycopy(array, i + 1, array, i - n2, length - i - 1 - n2);
                    if (n2 != 0) {
                        --length;
                    }
                    --length;
                }
                else {
                    if (array[i] == 45) {
                        break;
                    }
                    ++i;
                }
            }
            this.encoded_data = Util.fromBase64(array, n, i - n);
            if (this.encoded_data.length > 4 && this.encoded_data[0] == 63 && this.encoded_data[1] == 111 && this.encoded_data[2] == -7 && this.encoded_data[3] == -21) {
                final Buffer buffer = new Buffer(this.encoded_data);
                buffer.getInt();
                buffer.getInt();
                buffer.getString();
                final String s = new String(buffer.getString());
                if (s.equals("3des-cbc")) {
                    buffer.getInt();
                    final byte[] encoded_data = new byte[this.encoded_data.length - buffer.getOffSet()];
                    buffer.getByte(encoded_data);
                    this.encoded_data = encoded_data;
                    this.encrypted = true;
                    throw new JSchException("unknown privatekey format: " + this.identity);
                }
                if (s.equals("none")) {
                    buffer.getInt();
                    this.encrypted = false;
                    final byte[] encoded_data2 = new byte[this.encoded_data.length - buffer.getOffSet()];
                    buffer.getByte(encoded_data2);
                    this.encoded_data = encoded_data2;
                }
            }
            if (array2 == null) {
                return;
            }
            int length2 = array2.length;
            if (array2.length > 4 && array2[0] == 45 && array2[1] == 45 && array2[2] == 45 && array2[3] == 45) {
                int l = 0;
                do {
                    ++l;
                } while (length2 > l && array2[l] != 10);
                if (length2 <= l) {
                    return;
                }
                while (l < length2) {
                    if (array2[l] == 10) {
                        boolean b2 = false;
                        for (int n3 = l + 1; n3 < length2; ++n3) {
                            if (array2[n3] == 10) {
                                break;
                            }
                            if (array2[n3] == 58) {
                                b2 = true;
                                break;
                            }
                        }
                        if (!b2) {
                            ++l;
                            break;
                        }
                    }
                    ++l;
                }
                if (length2 <= l) {
                    return;
                }
                final int n4 = l;
                while (l < length2) {
                    if (array2[l] == 10) {
                        System.arraycopy(array2, l + 1, array2, l, length2 - l - 1);
                        --length2;
                    }
                    else {
                        if (array2[l] == 45) {
                            break;
                        }
                        ++l;
                    }
                }
                this.publickeyblob = Util.fromBase64(array2, n4, l - n4);
                if (this.type == 3 && this.publickeyblob.length > 8) {
                    if (this.publickeyblob[8] == 100) {
                        this.type = 2;
                    }
                    else if (this.publickeyblob[8] == 114) {
                        this.type = 1;
                    }
                }
            }
            else {
                if (array2[0] != 115 || array2[1] != 115 || array2[2] != 104 || array2[3] != 45) {
                    return;
                }
                int n5;
                for (n5 = 0; n5 < length2 && array2[n5] != 32; ++n5) {}
                if (++n5 >= length2) {
                    return;
                }
                final int n6 = n5;
                while (n5 < length2 && array2[n5] != 32 && array2[n5] != 10) {
                    ++n5;
                }
                this.publickeyblob = Util.fromBase64(array2, n6, n5 - n6);
                if (this.publickeyblob.length < 11) {
                    if (JSch.getLogger().isEnabled(2)) {
                        JSch.getLogger().log(2, "failed to parse the public key");
                    }
                    this.publickeyblob = null;
                }
            }
        }
        catch (Exception ex) {
            if (ex instanceof JSchException) {
                throw (JSchException)ex;
            }
            if (ex instanceof Throwable) {
                throw new JSchException(ex.toString(), ex);
            }
            throw new JSchException(ex.toString());
        }
    }
    
    public String getAlgName() {
        if (this.type == 1) {
            return "ssh-rsa";
        }
        return "ssh-dss";
    }
    
    public boolean setPassphrase(final byte[] array) throws JSchException {
        try {
            if (this.encrypted) {
                if (array == null) {
                    return false;
                }
                final int blockSize = this.hash.getBlockSize();
                final byte[] array2 = new byte[this.key.length / blockSize * blockSize + ((this.key.length % blockSize == 0) ? 0 : blockSize)];
                byte[] array3 = null;
                if (this.keytype == 0) {
                    for (int n = 0; n + blockSize <= array2.length; n += array3.length) {
                        if (array3 != null) {
                            this.hash.update(array3, 0, array3.length);
                        }
                        this.hash.update(array, 0, array.length);
                        this.hash.update(this.iv, 0, (this.iv.length > 8) ? 8 : this.iv.length);
                        array3 = this.hash.digest();
                        System.arraycopy(array3, 0, array2, n, array3.length);
                    }
                    System.arraycopy(array2, 0, this.key, 0, this.key.length);
                }
                else if (this.keytype == 1) {
                    for (int n2 = 0; n2 + blockSize <= array2.length; n2 += array3.length) {
                        if (array3 != null) {
                            this.hash.update(array3, 0, array3.length);
                        }
                        this.hash.update(array, 0, array.length);
                        array3 = this.hash.digest();
                        System.arraycopy(array3, 0, array2, n2, array3.length);
                    }
                    System.arraycopy(array2, 0, this.key, 0, this.key.length);
                }
                Util.bzero(array);
            }
            if (this.decrypt()) {
                this.encrypted = false;
                return true;
            }
            final byte[] p_array = null;
            this.prv_array = p_array;
            this.pub_array = p_array;
            this.G_array = p_array;
            this.Q_array = p_array;
            this.P_array = p_array;
            return false;
        }
        catch (Exception ex) {
            if (ex instanceof JSchException) {
                throw (JSchException)ex;
            }
            if (ex instanceof Throwable) {
                throw new JSchException(ex.toString(), ex);
            }
            throw new JSchException(ex.toString());
        }
    }
    
    public byte[] getPublicKeyBlob() {
        if (this.publickeyblob != null) {
            return this.publickeyblob;
        }
        if (this.type == 1) {
            return this.getPublicKeyBlob_rsa();
        }
        return this.getPublicKeyBlob_dss();
    }
    
    byte[] getPublicKeyBlob_rsa() {
        if (this.e_array == null) {
            return null;
        }
        final Buffer buffer = new Buffer("ssh-rsa".length() + 4 + this.e_array.length + 4 + this.n_array.length + 4);
        buffer.putString("ssh-rsa".getBytes());
        buffer.putString(this.e_array);
        buffer.putString(this.n_array);
        return buffer.buffer;
    }
    
    byte[] getPublicKeyBlob_dss() {
        if (this.P_array == null) {
            return null;
        }
        final Buffer buffer = new Buffer("ssh-dss".length() + 4 + this.P_array.length + 4 + this.Q_array.length + 4 + this.G_array.length + 4 + this.pub_array.length + 4);
        buffer.putString("ssh-dss".getBytes());
        buffer.putString(this.P_array);
        buffer.putString(this.Q_array);
        buffer.putString(this.G_array);
        buffer.putString(this.pub_array);
        return buffer.buffer;
    }
    
    public byte[] getSignature(final byte[] array) {
        if (this.type == 1) {
            return this.getSignature_rsa(array);
        }
        return this.getSignature_dss(array);
    }
    
    byte[] getSignature_rsa(final byte[] array) {
        try {
            final SignatureRSA signatureRSA = (SignatureRSA)Class.forName(JSch.getConfig("signature.rsa")).newInstance();
            signatureRSA.init();
            signatureRSA.setPrvKey(this.d_array, this.n_array);
            signatureRSA.update(array);
            final byte[] sign = signatureRSA.sign();
            final Buffer buffer = new Buffer("ssh-rsa".length() + 4 + sign.length + 4);
            buffer.putString("ssh-rsa".getBytes());
            buffer.putString(sign);
            return buffer.buffer;
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    byte[] getSignature_dss(final byte[] array) {
        try {
            final SignatureDSA signatureDSA = (SignatureDSA)Class.forName(JSch.getConfig("signature.dss")).newInstance();
            signatureDSA.init();
            signatureDSA.setPrvKey(this.prv_array, this.P_array, this.Q_array, this.G_array);
            signatureDSA.update(array);
            final byte[] sign = signatureDSA.sign();
            final Buffer buffer = new Buffer("ssh-dss".length() + 4 + sign.length + 4);
            buffer.putString("ssh-dss".getBytes());
            buffer.putString(sign);
            return buffer.buffer;
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public boolean decrypt() {
        if (this.type == 1) {
            return this.decrypt_rsa();
        }
        return this.decrypt_dss();
    }
    
    boolean decrypt_rsa() {
        try {
            byte[] encoded_data;
            if (this.encrypted) {
                if (this.keytype == 0) {
                    this.cipher.init(1, this.key, this.iv);
                    encoded_data = new byte[this.encoded_data.length];
                    this.cipher.update(this.encoded_data, 0, this.encoded_data.length, encoded_data, 0);
                }
                else {
                    if (this.keytype != 1) {
                        return false;
                    }
                    for (int i = 0; i < this.iv.length; ++i) {
                        this.iv[i] = 0;
                    }
                    this.cipher.init(1, this.key, this.iv);
                    encoded_data = new byte[this.encoded_data.length];
                    this.cipher.update(this.encoded_data, 0, this.encoded_data.length, encoded_data, 0);
                }
            }
            else {
                if (this.n_array != null) {
                    return true;
                }
                encoded_data = this.encoded_data;
            }
            if (this.keytype == 1) {
                final Buffer buffer = new Buffer(encoded_data);
                if (encoded_data.length != buffer.getInt() + 4) {
                    return false;
                }
                this.e_array = buffer.getMPIntBits();
                this.d_array = buffer.getMPIntBits();
                this.n_array = buffer.getMPIntBits();
                buffer.getMPIntBits();
                buffer.getMPIntBits();
                buffer.getMPIntBits();
                return true;
            }
            else {
                int n = 0;
                if (encoded_data[n] != 48) {
                    return false;
                }
                ++n;
                final int n2 = encoded_data[n++] & 0xFF;
                if ((n2 & 0x80) != 0x0) {
                    int n3 = n2 & 0x7F;
                    int n4 = 0;
                    while (n3-- > 0) {
                        n4 = (n4 << 8) + (encoded_data[n++] & 0xFF);
                    }
                }
                if (encoded_data[n] != 2) {
                    return false;
                }
                ++n;
                int n5 = encoded_data[n++] & 0xFF;
                if ((n5 & 0x80) != 0x0) {
                    int n6 = n5 & 0x7F;
                    n5 = 0;
                    while (n6-- > 0) {
                        n5 = (n5 << 8) + (encoded_data[n++] & 0xFF);
                    }
                }
                int n7 = n + n5;
                ++n7;
                int n8 = encoded_data[n7++] & 0xFF;
                if ((n8 & 0x80) != 0x0) {
                    int n9 = n8 & 0x7F;
                    n8 = 0;
                    while (n9-- > 0) {
                        n8 = (n8 << 8) + (encoded_data[n7++] & 0xFF);
                    }
                }
                System.arraycopy(encoded_data, n7, this.n_array = new byte[n8], 0, n8);
                int n10 = n7 + n8;
                ++n10;
                int n11 = encoded_data[n10++] & 0xFF;
                if ((n11 & 0x80) != 0x0) {
                    int n12 = n11 & 0x7F;
                    n11 = 0;
                    while (n12-- > 0) {
                        n11 = (n11 << 8) + (encoded_data[n10++] & 0xFF);
                    }
                }
                System.arraycopy(encoded_data, n10, this.e_array = new byte[n11], 0, n11);
                int n13 = n10 + n11;
                ++n13;
                int n14 = encoded_data[n13++] & 0xFF;
                if ((n14 & 0x80) != 0x0) {
                    int n15 = n14 & 0x7F;
                    n14 = 0;
                    while (n15-- > 0) {
                        n14 = (n14 << 8) + (encoded_data[n13++] & 0xFF);
                    }
                }
                System.arraycopy(encoded_data, n13, this.d_array = new byte[n14], 0, n14);
                int n16 = n13 + n14;
                ++n16;
                int n17 = encoded_data[n16++] & 0xFF;
                if ((n17 & 0x80) != 0x0) {
                    int n18 = n17 & 0x7F;
                    n17 = 0;
                    while (n18-- > 0) {
                        n17 = (n17 << 8) + (encoded_data[n16++] & 0xFF);
                    }
                }
                System.arraycopy(encoded_data, n16, new byte[n17], 0, n17);
                int n19 = n16 + n17;
                ++n19;
                int n20 = encoded_data[n19++] & 0xFF;
                if ((n20 & 0x80) != 0x0) {
                    int n21 = n20 & 0x7F;
                    n20 = 0;
                    while (n21-- > 0) {
                        n20 = (n20 << 8) + (encoded_data[n19++] & 0xFF);
                    }
                }
                System.arraycopy(encoded_data, n19, new byte[n20], 0, n20);
                int n22 = n19 + n20;
                ++n22;
                int n23 = encoded_data[n22++] & 0xFF;
                if ((n23 & 0x80) != 0x0) {
                    int n24 = n23 & 0x7F;
                    n23 = 0;
                    while (n24-- > 0) {
                        n23 = (n23 << 8) + (encoded_data[n22++] & 0xFF);
                    }
                }
                System.arraycopy(encoded_data, n22, new byte[n23], 0, n23);
                int n25 = n22 + n23;
                ++n25;
                int n26 = encoded_data[n25++] & 0xFF;
                if ((n26 & 0x80) != 0x0) {
                    int n27 = n26 & 0x7F;
                    n26 = 0;
                    while (n27-- > 0) {
                        n26 = (n26 << 8) + (encoded_data[n25++] & 0xFF);
                    }
                }
                System.arraycopy(encoded_data, n25, new byte[n26], 0, n26);
                int n28 = n25 + n26;
                ++n28;
                int n29 = encoded_data[n28++] & 0xFF;
                if ((n29 & 0x80) != 0x0) {
                    int n30 = n29 & 0x7F;
                    n29 = 0;
                    while (n30-- > 0) {
                        n29 = (n29 << 8) + (encoded_data[n28++] & 0xFF);
                    }
                }
                System.arraycopy(encoded_data, n28, new byte[n29], 0, n29);
            }
        }
        catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    boolean decrypt_dss() {
        try {
            byte[] encoded_data;
            if (this.encrypted) {
                if (this.keytype == 0) {
                    this.cipher.init(1, this.key, this.iv);
                    encoded_data = new byte[this.encoded_data.length];
                    this.cipher.update(this.encoded_data, 0, this.encoded_data.length, encoded_data, 0);
                }
                else {
                    if (this.keytype != 1) {
                        return false;
                    }
                    for (int i = 0; i < this.iv.length; ++i) {
                        this.iv[i] = 0;
                    }
                    this.cipher.init(1, this.key, this.iv);
                    encoded_data = new byte[this.encoded_data.length];
                    this.cipher.update(this.encoded_data, 0, this.encoded_data.length, encoded_data, 0);
                }
            }
            else {
                if (this.P_array != null) {
                    return true;
                }
                encoded_data = this.encoded_data;
            }
            if (this.keytype == 1) {
                final Buffer buffer = new Buffer(encoded_data);
                if (encoded_data.length != buffer.getInt() + 4) {
                    return false;
                }
                this.P_array = buffer.getMPIntBits();
                this.G_array = buffer.getMPIntBits();
                this.Q_array = buffer.getMPIntBits();
                this.pub_array = buffer.getMPIntBits();
                this.prv_array = buffer.getMPIntBits();
                return true;
            }
            else {
                int n = 0;
                if (encoded_data[n] != 48) {
                    return false;
                }
                ++n;
                final int n2 = encoded_data[n++] & 0xFF;
                if ((n2 & 0x80) != 0x0) {
                    int n3 = n2 & 0x7F;
                    int n4 = 0;
                    while (n3-- > 0) {
                        n4 = (n4 << 8) + (encoded_data[n++] & 0xFF);
                    }
                }
                if (encoded_data[n] != 2) {
                    return false;
                }
                ++n;
                int n5 = encoded_data[n++] & 0xFF;
                if ((n5 & 0x80) != 0x0) {
                    int n6 = n5 & 0x7F;
                    n5 = 0;
                    while (n6-- > 0) {
                        n5 = (n5 << 8) + (encoded_data[n++] & 0xFF);
                    }
                }
                int n7 = n + n5;
                ++n7;
                int n8 = encoded_data[n7++] & 0xFF;
                if ((n8 & 0x80) != 0x0) {
                    int n9 = n8 & 0x7F;
                    n8 = 0;
                    while (n9-- > 0) {
                        n8 = (n8 << 8) + (encoded_data[n7++] & 0xFF);
                    }
                }
                System.arraycopy(encoded_data, n7, this.P_array = new byte[n8], 0, n8);
                int n10 = n7 + n8;
                ++n10;
                int n11 = encoded_data[n10++] & 0xFF;
                if ((n11 & 0x80) != 0x0) {
                    int n12 = n11 & 0x7F;
                    n11 = 0;
                    while (n12-- > 0) {
                        n11 = (n11 << 8) + (encoded_data[n10++] & 0xFF);
                    }
                }
                System.arraycopy(encoded_data, n10, this.Q_array = new byte[n11], 0, n11);
                int n13 = n10 + n11;
                ++n13;
                int n14 = encoded_data[n13++] & 0xFF;
                if ((n14 & 0x80) != 0x0) {
                    int n15 = n14 & 0x7F;
                    n14 = 0;
                    while (n15-- > 0) {
                        n14 = (n14 << 8) + (encoded_data[n13++] & 0xFF);
                    }
                }
                System.arraycopy(encoded_data, n13, this.G_array = new byte[n14], 0, n14);
                int n16 = n13 + n14;
                ++n16;
                int n17 = encoded_data[n16++] & 0xFF;
                if ((n17 & 0x80) != 0x0) {
                    int n18 = n17 & 0x7F;
                    n17 = 0;
                    while (n18-- > 0) {
                        n17 = (n17 << 8) + (encoded_data[n16++] & 0xFF);
                    }
                }
                System.arraycopy(encoded_data, n16, this.pub_array = new byte[n17], 0, n17);
                int n19 = n16 + n17;
                ++n19;
                int n20 = encoded_data[n19++] & 0xFF;
                if ((n20 & 0x80) != 0x0) {
                    int n21 = n20 & 0x7F;
                    n20 = 0;
                    while (n21-- > 0) {
                        n20 = (n20 << 8) + (encoded_data[n19++] & 0xFF);
                    }
                }
                System.arraycopy(encoded_data, n19, this.prv_array = new byte[n20], 0, n20);
            }
        }
        catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    public boolean isEncrypted() {
        return this.encrypted;
    }
    
    public String getName() {
        return this.identity;
    }
    
    private byte a2b(final byte b) {
        if (48 <= b && b <= 57) {
            return (byte)(b - 48);
        }
        if (97 <= b && b <= 122) {
            return (byte)(b - 97 + 10);
        }
        return (byte)(b - 65 + 10);
    }
    
    public boolean equals(final Object o) {
        if (!(o instanceof IdentityFile)) {
            return super.equals(o);
        }
        return this.getName().equals(((IdentityFile)o).getName());
    }
    
    public void clear() {
        Util.bzero(this.encoded_data);
        Util.bzero(this.prv_array);
        Util.bzero(this.d_array);
        Util.bzero(this.key);
        Util.bzero(this.iv);
    }
    
    public void finalize() {
        this.clear();
    }
}
