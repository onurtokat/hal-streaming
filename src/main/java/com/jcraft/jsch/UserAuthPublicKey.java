// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

import java.util.Vector;
import java.io.UnsupportedEncodingException;

class UserAuthPublicKey extends UserAuth
{
    public boolean start(final Session session) throws Exception {
        super.start(session);
        final Vector identities = session.jsch.identities;
        byte[] str2byte = null;
        synchronized (identities) {
            if (identities.size() <= 0) {
                return false;
            }
            final byte[] str2byte2 = Util.str2byte(this.username);
            for (int i = 0; i < identities.size(); ++i) {
                final Identity \u0131dentity = identities.elementAt(i);
                byte[] array = \u0131dentity.getPublicKeyBlob();
                if (array != null) {
                    this.packet.reset();
                    this.buf.putByte((byte)50);
                    this.buf.putString(str2byte2);
                    this.buf.putString("ssh-connection".getBytes());
                    this.buf.putString("publickey".getBytes());
                    this.buf.putByte((byte)0);
                    this.buf.putString(\u0131dentity.getAlgName().getBytes());
                    this.buf.putString(array);
                    session.write(this.packet);
                    int n;
                    while (true) {
                        this.buf = session.read(this.buf);
                        n = (this.buf.getCommand() & 0xFF);
                        if (n == 60) {
                            break;
                        }
                        if (n == 51) {
                            break;
                        }
                        if (n != 53) {
                            break;
                        }
                        this.buf.getInt();
                        this.buf.getByte();
                        this.buf.getByte();
                        final byte[] string = this.buf.getString();
                        this.buf.getString();
                        String s;
                        try {
                            s = new String(string, "UTF-8");
                        }
                        catch (UnsupportedEncodingException ex) {
                            s = new String(string);
                        }
                        if (this.userinfo == null) {
                            continue;
                        }
                        this.userinfo.showMessage(s);
                    }
                    if (n != 60) {
                        continue;
                    }
                }
                int n2 = 5;
                do {
                    if (\u0131dentity.isEncrypted() && str2byte == null) {
                        if (this.userinfo == null) {
                            throw new JSchException("USERAUTH fail");
                        }
                        if (\u0131dentity.isEncrypted() && !this.userinfo.promptPassphrase("Passphrase for " + \u0131dentity.getName())) {
                            throw new JSchAuthCancelException("publickey");
                        }
                        final String passphrase = this.userinfo.getPassphrase();
                        if (passphrase != null) {
                            str2byte = Util.str2byte(passphrase);
                        }
                    }
                    if ((!\u0131dentity.isEncrypted() || str2byte != null) && \u0131dentity.setPassphrase(str2byte)) {
                        break;
                    }
                    Util.bzero(str2byte);
                    str2byte = null;
                } while (--n2 != 0);
                Util.bzero(str2byte);
                str2byte = null;
                if (!\u0131dentity.isEncrypted()) {
                    if (array == null) {
                        array = \u0131dentity.getPublicKeyBlob();
                    }
                    if (array != null) {
                        this.packet.reset();
                        this.buf.putByte((byte)50);
                        this.buf.putString(str2byte2);
                        this.buf.putString("ssh-connection".getBytes());
                        this.buf.putString("publickey".getBytes());
                        this.buf.putByte((byte)1);
                        this.buf.putString(\u0131dentity.getAlgName().getBytes());
                        this.buf.putString(array);
                        final byte[] sessionId = session.getSessionId();
                        final int length = sessionId.length;
                        final byte[] array2 = new byte[4 + length + this.buf.index - 5];
                        array2[0] = (byte)(length >>> 24);
                        array2[1] = (byte)(length >>> 16);
                        array2[2] = (byte)(length >>> 8);
                        array2[3] = (byte)length;
                        System.arraycopy(sessionId, 0, array2, 4, length);
                        System.arraycopy(this.buf.buffer, 5, array2, 4 + length, this.buf.index - 5);
                        final byte[] signature = \u0131dentity.getSignature(array2);
                        if (signature == null) {
                            break;
                        }
                        this.buf.putString(signature);
                        session.write(this.packet);
                        while (true) {
                            this.buf = session.read(this.buf);
                            final int n3 = this.buf.getCommand() & 0xFF;
                            if (n3 == 52) {
                                return true;
                            }
                            if (n3 == 53) {
                                this.buf.getInt();
                                this.buf.getByte();
                                this.buf.getByte();
                                final byte[] string2 = this.buf.getString();
                                this.buf.getString();
                                String s2;
                                try {
                                    s2 = new String(string2, "UTF-8");
                                }
                                catch (UnsupportedEncodingException ex2) {
                                    s2 = new String(string2);
                                }
                                if (this.userinfo == null) {
                                    continue;
                                }
                                this.userinfo.showMessage(s2);
                            }
                            else {
                                if (n3 != 51) {
                                    break;
                                }
                                this.buf.getInt();
                                this.buf.getByte();
                                this.buf.getByte();
                                final byte[] string3 = this.buf.getString();
                                if (this.buf.getByte() != 0) {
                                    throw new JSchPartialAuthException(new String(string3));
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
