// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public abstract class KeyPair
{
    public static final int ERROR = 0;
    public static final int DSA = 1;
    public static final int RSA = 2;
    public static final int UNKNOWN = 3;
    static final int VENDOR_OPENSSH = 0;
    static final int VENDOR_FSECURE = 1;
    int vendor;
    private static final byte[] cr;
    JSch jsch;
    private Cipher cipher;
    private HASH hash;
    private Random random;
    private byte[] passphrase;
    static byte[][] header;
    private static byte[] space;
    private boolean encrypted;
    private byte[] data;
    private byte[] iv;
    private byte[] publickeyblob;
    
    public static KeyPair genKeyPair(final JSch sch, final int n) throws JSchException {
        return genKeyPair(sch, n, 1024);
    }
    
    public static KeyPair genKeyPair(final JSch sch, final int n, final int n2) throws JSchException {
        KeyPair keyPair = null;
        if (n == 1) {
            keyPair = new KeyPairDSA(sch);
        }
        else if (n == 2) {
            keyPair = new KeyPairRSA(sch);
        }
        if (keyPair != null) {
            keyPair.generate(n2);
        }
        return keyPair;
    }
    
    abstract void generate(final int p0) throws JSchException;
    
    abstract byte[] getBegin();
    
    abstract byte[] getEnd();
    
    abstract int getKeySize();
    
    public KeyPair(final JSch jsch) {
        this.vendor = 0;
        this.jsch = null;
        this.encrypted = false;
        this.data = null;
        this.iv = null;
        this.publickeyblob = null;
        this.jsch = jsch;
    }
    
    abstract byte[] getPrivateKey();
    
    public void writePrivateKey(final OutputStream outputStream) {
        final byte[] privateKey = this.getPrivateKey();
        final byte[][] array = { null };
        final byte[] encrypt = this.encrypt(privateKey, array);
        if (encrypt != privateKey) {
            Util.bzero(privateKey);
        }
        final byte[] array2 = array[0];
        final byte[] base64 = Util.toBase64(encrypt, 0, encrypt.length);
        try {
            outputStream.write(this.getBegin());
            outputStream.write(KeyPair.cr);
            if (this.passphrase != null) {
                outputStream.write(KeyPair.header[0]);
                outputStream.write(KeyPair.cr);
                outputStream.write(KeyPair.header[1]);
                for (int i = 0; i < array2.length; ++i) {
                    outputStream.write(b2a((byte)(array2[i] >>> 4 & 0xF)));
                    outputStream.write(b2a((byte)(array2[i] & 0xF)));
                }
                outputStream.write(KeyPair.cr);
                outputStream.write(KeyPair.cr);
            }
            for (int j = 0; j < base64.length; j += 64) {
                if (j + 64 >= base64.length) {
                    outputStream.write(base64, j, base64.length - j);
                    outputStream.write(KeyPair.cr);
                    break;
                }
                outputStream.write(base64, j, 64);
                outputStream.write(KeyPair.cr);
            }
            outputStream.write(this.getEnd());
            outputStream.write(KeyPair.cr);
        }
        catch (Exception ex) {}
    }
    
    abstract byte[] getKeyTypeName();
    
    public abstract int getKeyType();
    
    public byte[] getPublicKeyBlob() {
        return this.publickeyblob;
    }
    
    public void writePublicKey(final OutputStream outputStream, final String s) {
        final byte[] publicKeyBlob = this.getPublicKeyBlob();
        final byte[] base64 = Util.toBase64(publicKeyBlob, 0, publicKeyBlob.length);
        try {
            outputStream.write(this.getKeyTypeName());
            outputStream.write(KeyPair.space);
            outputStream.write(base64, 0, base64.length);
            outputStream.write(KeyPair.space);
            outputStream.write(s.getBytes());
            outputStream.write(KeyPair.cr);
        }
        catch (Exception ex) {}
    }
    
    public void writePublicKey(final String s, final String s2) throws FileNotFoundException, IOException {
        final FileOutputStream fileOutputStream = new FileOutputStream(s);
        this.writePublicKey(fileOutputStream, s2);
        fileOutputStream.close();
    }
    
    public void writeSECSHPublicKey(final OutputStream outputStream, final String s) {
        final byte[] publicKeyBlob = this.getPublicKeyBlob();
        final byte[] base64 = Util.toBase64(publicKeyBlob, 0, publicKeyBlob.length);
        try {
            outputStream.write("---- BEGIN SSH2 PUBLIC KEY ----".getBytes());
            outputStream.write(KeyPair.cr);
            outputStream.write(("Comment: \"" + s + "\"").getBytes());
            outputStream.write(KeyPair.cr);
            int n;
            for (int i = 0; i < base64.length; i += n) {
                n = 70;
                if (base64.length - i < n) {
                    n = base64.length - i;
                }
                outputStream.write(base64, i, n);
                outputStream.write(KeyPair.cr);
            }
            outputStream.write("---- END SSH2 PUBLIC KEY ----".getBytes());
            outputStream.write(KeyPair.cr);
        }
        catch (Exception ex) {}
    }
    
    public void writeSECSHPublicKey(final String s, final String s2) throws FileNotFoundException, IOException {
        final FileOutputStream fileOutputStream = new FileOutputStream(s);
        this.writeSECSHPublicKey(fileOutputStream, s2);
        fileOutputStream.close();
    }
    
    public void writePrivateKey(final String s) throws FileNotFoundException, IOException {
        final FileOutputStream fileOutputStream = new FileOutputStream(s);
        this.writePrivateKey(fileOutputStream);
        fileOutputStream.close();
    }
    
    public String getFingerPrint() {
        if (this.hash == null) {
            this.hash = this.genHash();
        }
        final byte[] publicKeyBlob = this.getPublicKeyBlob();
        if (publicKeyBlob == null) {
            return null;
        }
        return this.getKeySize() + " " + Util.getFingerPrint(this.hash, publicKeyBlob);
    }
    
    private byte[] encrypt(final byte[] array, final byte[][] array2) {
        if (this.passphrase == null) {
            return array;
        }
        if (this.cipher == null) {
            this.cipher = this.genCipher();
        }
        final int n = 0;
        final byte[] array3 = new byte[this.cipher.getIVSize()];
        array2[n] = array3;
        final byte[] array4 = array3;
        if (this.random == null) {
            this.random = this.genRandom();
        }
        this.random.fill(array4, 0, array4.length);
        final byte[] genKey = this.genKey(this.passphrase, array4);
        final int \u0131vSize = this.cipher.getIVSize();
        final byte[] array5 = new byte[(array.length / \u0131vSize + 1) * \u0131vSize];
        System.arraycopy(array, 0, array5, 0, array.length);
        for (int n2 = \u0131vSize - array.length % \u0131vSize, n3 = array5.length - 1; array5.length - n2 <= n3; --n3) {
            array5[n3] = (byte)n2;
        }
        final byte[] array6 = array5;
        try {
            this.cipher.init(0, genKey, array4);
            this.cipher.update(array6, 0, array6.length, array6, 0);
        }
        catch (Exception ex) {}
        Util.bzero(genKey);
        return array6;
    }
    
    abstract boolean parse(final byte[] p0);
    
    private byte[] decrypt(final byte[] array, final byte[] array2, final byte[] array3) {
        try {
            final byte[] genKey = this.genKey(array2, array3);
            this.cipher.init(1, genKey, array3);
            Util.bzero(genKey);
            final byte[] array4 = new byte[array.length];
            this.cipher.update(array, 0, array.length, array4, 0);
            return array4;
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    int writeSEQUENCE(final byte[] array, int writeLength, final int n) {
        array[writeLength++] = 48;
        writeLength = this.writeLength(array, writeLength, n);
        return writeLength;
    }
    
    int writeINTEGER(final byte[] array, int writeLength, final byte[] array2) {
        array[writeLength++] = 2;
        writeLength = this.writeLength(array, writeLength, array2.length);
        System.arraycopy(array2, 0, array, writeLength, array2.length);
        writeLength += array2.length;
        return writeLength;
    }
    
    int countLength(int i) {
        int n = 1;
        if (i <= 127) {
            return n;
        }
        while (i > 0) {
            i >>>= 8;
            ++n;
        }
        return n;
    }
    
    int writeLength(final byte[] array, int n, int n2) {
        int i = this.countLength(n2) - 1;
        if (i == 0) {
            array[n++] = (byte)n2;
            return n;
        }
        array[n++] = (byte)(0x80 | i);
        final int n3 = n + i;
        while (i > 0) {
            array[n + i - 1] = (byte)(n2 & 0xFF);
            n2 >>>= 8;
            --i;
        }
        return n3;
    }
    
    private Random genRandom() {
        if (this.random == null) {
            try {
                this.random = (Random)Class.forName(JSch.getConfig("random")).newInstance();
            }
            catch (Exception ex) {
                System.err.println("connect: random " + ex);
            }
        }
        return this.random;
    }
    
    private HASH genHash() {
        try {
            (this.hash = (HASH)Class.forName(JSch.getConfig("md5")).newInstance()).init();
        }
        catch (Exception ex) {}
        return this.hash;
    }
    
    private Cipher genCipher() {
        try {
            this.cipher = (Cipher)Class.forName(JSch.getConfig("3des-cbc")).newInstance();
        }
        catch (Exception ex) {}
        return this.cipher;
    }
    
    synchronized byte[] genKey(final byte[] array, final byte[] array2) {
        if (this.cipher == null) {
            this.cipher = this.genCipher();
        }
        if (this.hash == null) {
            this.hash = this.genHash();
        }
        final byte[] array3 = new byte[this.cipher.getBlockSize()];
        final int blockSize = this.hash.getBlockSize();
        final byte[] array4 = new byte[array3.length / blockSize * blockSize + ((array3.length % blockSize == 0) ? 0 : blockSize)];
        try {
            byte[] array5 = null;
            if (this.vendor == 0) {
                for (int n = 0; n + blockSize <= array4.length; n += array5.length) {
                    if (array5 != null) {
                        this.hash.update(array5, 0, array5.length);
                    }
                    this.hash.update(array, 0, array.length);
                    this.hash.update(array2, 0, array2.length);
                    array5 = this.hash.digest();
                    System.arraycopy(array5, 0, array4, n, array5.length);
                }
                System.arraycopy(array4, 0, array3, 0, array3.length);
            }
            else if (this.vendor == 1) {
                for (int n2 = 0; n2 + blockSize <= array4.length; n2 += array5.length) {
                    if (array5 != null) {
                        this.hash.update(array5, 0, array5.length);
                    }
                    this.hash.update(array, 0, array.length);
                    array5 = this.hash.digest();
                    System.arraycopy(array5, 0, array4, n2, array5.length);
                }
                System.arraycopy(array4, 0, array3, 0, array3.length);
            }
        }
        catch (Exception ex) {
            System.err.println(ex);
        }
        return array3;
    }
    
    public void setPassphrase(final String s) {
        if (s == null || s.length() == 0) {
            this.setPassphrase((byte[])null);
        }
        else {
            this.setPassphrase(Util.str2byte(s));
        }
    }
    
    public void setPassphrase(byte[] passphrase) {
        if (passphrase != null && passphrase.length == 0) {
            passphrase = null;
        }
        this.passphrase = passphrase;
    }
    
    public boolean isEncrypted() {
        return this.encrypted;
    }
    
    public boolean decrypt(final String s) {
        if (s == null || s.length() == 0) {
            return !this.encrypted;
        }
        return this.decrypt(Util.str2byte(s));
    }
    
    public boolean decrypt(byte[] array) {
        if (!this.encrypted) {
            return true;
        }
        if (array == null) {
            return !this.encrypted;
        }
        final byte[] array2 = new byte[array.length];
        System.arraycopy(array, 0, array2, 0, array2.length);
        array = array2;
        final byte[] decrypt = this.decrypt(this.data, array, this.iv);
        Util.bzero(array);
        if (this.parse(decrypt)) {
            this.encrypted = false;
        }
        return !this.encrypted;
    }
    
    public static KeyPair load(final JSch sch, final String s) throws JSchException {
        String string = s + ".pub";
        if (!new File(string).exists()) {
            string = null;
        }
        return load(sch, s, string);
    }
    
    public static KeyPair load(final JSch sch, final String s, final String s2) throws JSchException {
        final byte[] iv = new byte[8];
        boolean encrypted = true;
        byte[] publickeyblob = null;
        int n = 0;
        int vendor = 0;
        byte[] fromBase64;
        try {
            final File file = new File(s);
            final FileInputStream fileInputStream = new FileInputStream(s);
            final byte[] array = new byte[(int)file.length()];
            int n2 = 0;
            while (true) {
                final int read = fileInputStream.read(array, n2, array.length - n2);
                if (read <= 0) {
                    break;
                }
                n2 += read;
            }
            fileInputStream.close();
            int i = 0;
            while (i < n2) {
                if (array[i] == 66 && array[i + 1] == 69 && array[i + 2] == 71 && array[i + 3] == 73) {
                    i += 6;
                    if (array[i] == 68 && array[i + 1] == 83 && array[i + 2] == 65) {
                        n = 1;
                    }
                    else if (array[i] == 82 && array[i + 1] == 83 && array[i + 2] == 65) {
                        n = 2;
                    }
                    else {
                        if (array[i] != 83 || array[i + 1] != 83 || array[i + 2] != 72) {
                            throw new JSchException("invalid privatekey: " + s);
                        }
                        n = 3;
                        vendor = 1;
                    }
                    i += 3;
                }
                else if (array[i] == 67 && array[i + 1] == 66 && array[i + 2] == 67 && array[i + 3] == 44) {
                    i += 4;
                    for (int j = 0; j < iv.length; ++j) {
                        iv[j] = (byte)((a2b(array[i++]) << 4 & 0xF0) + (a2b(array[i++]) & 0xF));
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
                            encrypted = false;
                            break;
                        }
                    }
                    ++i;
                }
            }
            if (n == 0) {
                throw new JSchException("invalid privatekey: " + s);
            }
            final int n3 = i;
            while (i < n2) {
                if (array[i] == 10) {
                    final int n4 = (array[i - 1] == 13) ? 1 : 0;
                    System.arraycopy(array, i + 1, array, i - n4, n2 - i - 1 - n4);
                    if (n4 != 0) {
                        --n2;
                    }
                    --n2;
                }
                else {
                    if (array[i] == 45) {
                        break;
                    }
                    ++i;
                }
            }
            fromBase64 = Util.fromBase64(array, n3, i - n3);
            if (fromBase64.length > 4 && fromBase64[0] == 63 && fromBase64[1] == 111 && fromBase64[2] == -7 && fromBase64[3] == -21) {
                final Buffer buffer = new Buffer(fromBase64);
                buffer.getInt();
                buffer.getInt();
                buffer.getString();
                final String s3 = new String(buffer.getString());
                if (s3.equals("3des-cbc")) {
                    buffer.getInt();
                    buffer.getByte(new byte[fromBase64.length - buffer.getOffSet()]);
                    throw new JSchException("unknown privatekey format: " + s);
                }
                if (s3.equals("none")) {
                    buffer.getInt();
                    buffer.getInt();
                    encrypted = false;
                    final byte[] array2 = new byte[fromBase64.length - buffer.getOffSet()];
                    buffer.getByte(array2);
                    fromBase64 = array2;
                }
            }
            if (s2 != null) {
                try {
                    final File file2 = new File(s2);
                    final FileInputStream fileInputStream2 = new FileInputStream(s2);
                    final byte[] array3 = new byte[(int)file2.length()];
                    int n5 = 0;
                    while (true) {
                        final int read2 = fileInputStream2.read(array3, n5, array3.length - n5);
                        if (read2 <= 0) {
                            break;
                        }
                        n5 += read2;
                    }
                    fileInputStream2.close();
                    if (array3.length > 4 && array3[0] == 45 && array3[1] == 45 && array3[2] == 45 && array3[3] == 45) {
                        int l = 1;
                        int n6 = 0;
                        do {
                            ++n6;
                        } while (array3.length > n6 && array3[n6] != 10);
                        if (array3.length <= n6) {
                            l = 0;
                        }
                        while (l != 0) {
                            if (array3[n6] == 10) {
                                boolean b2 = false;
                                for (int n7 = n6 + 1; n7 < array3.length; ++n7) {
                                    if (array3[n7] == 10) {
                                        break;
                                    }
                                    if (array3[n7] == 58) {
                                        b2 = true;
                                        break;
                                    }
                                }
                                if (!b2) {
                                    ++n6;
                                    break;
                                }
                            }
                            ++n6;
                        }
                        if (array3.length <= n6) {
                            l = 0;
                        }
                        final int n8 = n6;
                        while (l != 0 && n6 < n5) {
                            if (array3[n6] == 10) {
                                System.arraycopy(array3, n6 + 1, array3, n6, n5 - n6 - 1);
                                --n5;
                            }
                            else {
                                if (array3[n6] == 45) {
                                    break;
                                }
                                ++n6;
                            }
                        }
                        if (l != 0) {
                            publickeyblob = Util.fromBase64(array3, n8, n6 - n8);
                            if (n == 3) {
                                if (publickeyblob[8] == 100) {
                                    n = 1;
                                }
                                else if (publickeyblob[8] == 114) {
                                    n = 2;
                                }
                            }
                        }
                    }
                    else if (array3[0] == 115 && array3[1] == 115 && array3[2] == 104 && array3[3] == 45) {
                        int n9;
                        for (n9 = 0; n9 < n5 && array3[n9] != 32; ++n9) {}
                        if (++n9 < n5) {
                            final int n10 = n9;
                            while (n9 < n5 && array3[n9] != 32) {
                                ++n9;
                            }
                            publickeyblob = Util.fromBase64(array3, n10, n9 - n10);
                        }
                    }
                }
                catch (Exception ex2) {}
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
        KeyPair keyPair = null;
        if (n == 1) {
            keyPair = new KeyPairDSA(sch);
        }
        else if (n == 2) {
            keyPair = new KeyPairRSA(sch);
        }
        if (keyPair != null) {
            keyPair.encrypted = encrypted;
            keyPair.publickeyblob = publickeyblob;
            keyPair.vendor = vendor;
            if (encrypted) {
                keyPair.iv = iv;
                keyPair.data = fromBase64;
            }
            else {
                if (keyPair.parse(fromBase64)) {
                    return keyPair;
                }
                throw new JSchException("invalid privatekey: " + s);
            }
        }
        return keyPair;
    }
    
    private static byte a2b(final byte b) {
        if (48 <= b && b <= 57) {
            return (byte)(b - 48);
        }
        return (byte)(b - 97 + 10);
    }
    
    private static byte b2a(final byte b) {
        if (0 <= b && b <= 9) {
            return (byte)(b + 48);
        }
        return (byte)(b - 10 + 65);
    }
    
    public void dispose() {
        Util.bzero(this.passphrase);
    }
    
    public void finalize() {
        this.dispose();
    }
    
    static {
        cr = "\n".getBytes();
        KeyPair.header = new byte[][] { "Proc-Type: 4,ENCRYPTED".getBytes(), "DEK-Info: DES-EDE3-CBC,".getBytes() };
        KeyPair.space = " ".getBytes();
    }
}
