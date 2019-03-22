// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

class UserAuthPassword extends UserAuth
{
    private final int SSH_MSG_USERAUTH_PASSWD_CHANGEREQ = 60;
    
    public boolean start(final Session session) throws Exception {
        super.start(session);
        byte[] array = session.password;
        String s = this.username + "@" + session.host;
        if (session.port != 22) {
            s = s + ":" + session.port;
        }
        try {
            while (true) {
                if (array == null) {
                    if (this.userinfo == null) {
                        return false;
                    }
                    if (!this.userinfo.promptPassword("Password for " + s)) {
                        throw new JSchAuthCancelException("password");
                    }
                    final String password = this.userinfo.getPassword();
                    if (password == null) {
                        throw new JSchAuthCancelException("password");
                    }
                    array = Util.str2byte(password);
                }
                final byte[] str2byte = Util.str2byte(this.username);
                this.packet.reset();
                this.buf.putByte((byte)50);
                this.buf.putString(str2byte);
                this.buf.putString("ssh-connection".getBytes());
                this.buf.putString("password".getBytes());
                this.buf.putByte((byte)0);
                this.buf.putString(array);
                session.write(this.packet);
                while (true) {
                    this.buf = session.read(this.buf);
                    final int n = this.buf.getCommand() & 0xFF;
                    if (n == 52) {
                        return true;
                    }
                    if (n == 53) {
                        this.buf.getInt();
                        this.buf.getByte();
                        this.buf.getByte();
                        final byte[] string = this.buf.getString();
                        this.buf.getString();
                        final String byte2str = Util.byte2str(string);
                        if (this.userinfo == null) {
                            continue;
                        }
                        this.userinfo.showMessage(byte2str);
                    }
                    else if (n == 60) {
                        this.buf.getInt();
                        this.buf.getByte();
                        this.buf.getByte();
                        final byte[] string2 = this.buf.getString();
                        this.buf.getString();
                        if (this.userinfo == null || !(this.userinfo instanceof UIKeyboardInteractive)) {
                            if (this.userinfo != null) {
                                this.userinfo.showMessage("Password must be changed.");
                            }
                            return false;
                        }
                        final String[] promptKeyboardInteractive = ((UIKeyboardInteractive)this.userinfo).promptKeyboardInteractive(s, "Password Change Required", new String(string2), new String[] { "New Password: " }, new boolean[] { false });
                        if (promptKeyboardInteractive == null) {
                            throw new JSchAuthCancelException("password");
                        }
                        final byte[] bytes = promptKeyboardInteractive[0].getBytes();
                        this.packet.reset();
                        this.buf.putByte((byte)50);
                        this.buf.putString(str2byte);
                        this.buf.putString("ssh-connection".getBytes());
                        this.buf.putString("password".getBytes());
                        this.buf.putByte((byte)1);
                        this.buf.putString(array);
                        this.buf.putString(bytes);
                        Util.bzero(bytes);
                        session.write(this.packet);
                    }
                    else {
                        if (n != 51) {
                            return false;
                        }
                        this.buf.getInt();
                        this.buf.getByte();
                        this.buf.getByte();
                        final byte[] string3 = this.buf.getString();
                        if (this.buf.getByte() != 0) {
                            throw new JSchPartialAuthException(new String(string3));
                        }
                        if (array != null) {
                            Util.bzero(array);
                            array = null;
                            break;
                        }
                        break;
                    }
                }
            }
        }
        finally {
            if (array != null) {
                Util.bzero(array);
            }
        }
    }
}
