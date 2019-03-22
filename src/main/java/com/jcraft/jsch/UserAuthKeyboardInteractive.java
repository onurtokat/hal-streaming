// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

import java.io.UnsupportedEncodingException;

class UserAuthKeyboardInteractive extends UserAuth
{
    public boolean start(final Session session) throws Exception {
        super.start(session);
        if (this.userinfo != null && !(this.userinfo instanceof UIKeyboardInteractive)) {
            return false;
        }
        String s = this.username + "@" + session.host;
        if (session.port != 22) {
            s = s + ":" + session.port;
        }
        byte[] password = session.password;
        boolean b = false;
        final byte[] str2byte = Util.str2byte(this.username);
        do {
            this.packet.reset();
            this.buf.putByte((byte)50);
            this.buf.putString(str2byte);
            this.buf.putString("ssh-connection".getBytes());
            this.buf.putString("keyboard-interactive".getBytes());
            this.buf.putString("".getBytes());
            this.buf.putString("".getBytes());
            session.write(this.packet);
            int n = 1;
            while (true) {
                this.buf = session.read(this.buf);
                final int n2 = this.buf.getCommand() & 0xFF;
                if (n2 == 52) {
                    return true;
                }
                if (n2 == 53) {
                    this.buf.getInt();
                    this.buf.getByte();
                    this.buf.getByte();
                    final byte[] string = this.buf.getString();
                    this.buf.getString();
                    String s2;
                    try {
                        s2 = new String(string, "UTF-8");
                    }
                    catch (UnsupportedEncodingException ex) {
                        s2 = new String(string);
                    }
                    if (this.userinfo == null) {
                        continue;
                    }
                    this.userinfo.showMessage(s2);
                }
                else if (n2 == 51) {
                    this.buf.getInt();
                    this.buf.getByte();
                    this.buf.getByte();
                    final byte[] string2 = this.buf.getString();
                    if (this.buf.getByte() != 0) {
                        throw new JSchPartialAuthException(new String(string2));
                    }
                    if (n != 0) {
                        return false;
                    }
                    break;
                }
                else {
                    if (n2 != 60) {
                        return false;
                    }
                    n = 0;
                    this.buf.getInt();
                    this.buf.getByte();
                    this.buf.getByte();
                    final String s3 = new String(this.buf.getString());
                    final String s4 = new String(this.buf.getString());
                    final String s5 = new String(this.buf.getString());
                    final int \u0131nt = this.buf.getInt();
                    final String[] array = new String[\u0131nt];
                    final boolean[] array2 = new boolean[\u0131nt];
                    for (int i = 0; i < \u0131nt; ++i) {
                        array[i] = new String(this.buf.getString());
                        array2[i] = (this.buf.getByte() != 0);
                    }
                    byte[][] array3 = null;
                    if (\u0131nt > 0 || s3.length() > 0 || s4.length() > 0) {
                        if (this.userinfo != null) {
                            final String[] promptKeyboardInteractive = ((UIKeyboardInteractive)this.userinfo).promptKeyboardInteractive(s, s3, s4, array, array2);
                            if (promptKeyboardInteractive != null) {
                                array3 = new byte[promptKeyboardInteractive.length][];
                                for (int j = 0; j < promptKeyboardInteractive.length; ++j) {
                                    array3[j] = Util.str2byte(promptKeyboardInteractive[j]);
                                }
                            }
                        }
                        else if (password != null && array.length == 1 && !array2[0] && array[0].toLowerCase().startsWith("password:")) {
                            array3 = new byte[][] { password };
                            password = null;
                        }
                    }
                    this.packet.reset();
                    this.buf.putByte((byte)61);
                    if (\u0131nt > 0 && (array3 == null || \u0131nt != array3.length)) {
                        if (array3 == null) {
                            this.buf.putInt(\u0131nt);
                            for (int k = 0; k < \u0131nt; ++k) {
                                this.buf.putString("".getBytes());
                            }
                        }
                        else {
                            this.buf.putInt(0);
                        }
                        if (array3 == null) {
                            b = true;
                        }
                    }
                    else {
                        this.buf.putInt(\u0131nt);
                        for (int l = 0; l < \u0131nt; ++l) {
                            this.buf.putString(array3[l]);
                        }
                    }
                    session.write(this.packet);
                }
            }
        } while (!b);
        throw new JSchAuthCancelException("keyboard-interactive");
    }
}
