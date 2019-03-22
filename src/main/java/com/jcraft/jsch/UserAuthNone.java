// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

import java.io.UnsupportedEncodingException;

class UserAuthNone extends UserAuth
{
    private static final int SSH_MSG_SERVICE_ACCEPT = 6;
    private String methods;
    
    UserAuthNone() {
        this.methods = null;
    }
    
    public boolean start(final Session session) throws Exception {
        super.start(session);
        this.packet.reset();
        this.buf.putByte((byte)5);
        this.buf.putString("ssh-userauth".getBytes());
        session.write(this.packet);
        if (JSch.getLogger().isEnabled(1)) {
            JSch.getLogger().log(1, "SSH_MSG_SERVICE_REQUEST sent");
        }
        this.buf = session.read(this.buf);
        final boolean b = this.buf.getCommand() == 6;
        if (JSch.getLogger().isEnabled(1)) {
            JSch.getLogger().log(1, "SSH_MSG_SERVICE_ACCEPT received");
        }
        if (!b) {
            return false;
        }
        final byte[] str2byte = Util.str2byte(this.username);
        this.packet.reset();
        this.buf.putByte((byte)50);
        this.buf.putString(str2byte);
        this.buf.putString("ssh-connection".getBytes());
        this.buf.putString("none".getBytes());
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
                try {
                    this.userinfo.showMessage(s);
                }
                catch (RuntimeException ex2) {}
            }
            else {
                if (n == 51) {
                    this.buf.getInt();
                    this.buf.getByte();
                    this.buf.getByte();
                    final byte[] string2 = this.buf.getString();
                    this.buf.getByte();
                    this.methods = new String(string2);
                    return false;
                }
                throw new JSchException("USERAUTH fail (" + n + ")");
            }
        }
    }
    
    String getMethods() {
        return this.methods;
    }
}
