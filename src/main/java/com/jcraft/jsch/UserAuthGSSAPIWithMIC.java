// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

public class UserAuthGSSAPIWithMIC extends UserAuth
{
    private static final int SSH_MSG_USERAUTH_GSSAPI_RESPONSE = 60;
    private static final int SSH_MSG_USERAUTH_GSSAPI_TOKEN = 61;
    private static final int SSH_MSG_USERAUTH_GSSAPI_EXCHANGE_COMPLETE = 63;
    private static final int SSH_MSG_USERAUTH_GSSAPI_ERROR = 64;
    private static final int SSH_MSG_USERAUTH_GSSAPI_ERRTOK = 65;
    private static final int SSH_MSG_USERAUTH_GSSAPI_MIC = 66;
    private static final byte[][] supported_oid;
    private static final String[] supported_method;
    
    public boolean start(final Session session) throws Exception {
        super.start(session);
        final byte[] str2byte = Util.str2byte(this.username);
        this.packet.reset();
        this.buf.putByte((byte)50);
        this.buf.putString(str2byte);
        this.buf.putString("ssh-connection".getBytes());
        this.buf.putString("gssapi-with-mic".getBytes());
        this.buf.putInt(UserAuthGSSAPIWithMIC.supported_oid.length);
        for (int i = 0; i < UserAuthGSSAPIWithMIC.supported_oid.length; ++i) {
            this.buf.putString(UserAuthGSSAPIWithMIC.supported_oid[i]);
        }
        session.write(this.packet);
        String s = null;
        while (true) {
            this.buf = session.read(this.buf);
            final int n = this.buf.getCommand() & 0xFF;
            if (n == 51) {
                return false;
            }
            if (n == 60) {
                this.buf.getInt();
                this.buf.getByte();
                this.buf.getByte();
                final byte[] string = this.buf.getString();
                for (int j = 0; j < UserAuthGSSAPIWithMIC.supported_oid.length; ++j) {
                    if (Util.array_equals(string, UserAuthGSSAPIWithMIC.supported_oid[j])) {
                        s = UserAuthGSSAPIWithMIC.supported_method[j];
                        break;
                    }
                }
                if (s == null) {
                    return false;
                }
                GSSContext gssContext;
                try {
                    gssContext = (GSSContext)Class.forName(session.getConfig(s)).newInstance();
                }
                catch (Exception ex) {
                    return false;
                }
                try {
                    gssContext.create(this.username, session.host);
                }
                catch (JSchException ex2) {
                    return false;
                }
                byte[] array = new byte[0];
                while (!gssContext.isEstablished()) {
                    try {
                        array = gssContext.init(array, 0, array.length);
                    }
                    catch (JSchException ex3) {
                        return false;
                    }
                    if (array != null) {
                        this.packet.reset();
                        this.buf.putByte((byte)61);
                        this.buf.putString(array);
                        session.write(this.packet);
                    }
                    if (!gssContext.isEstablished()) {
                        this.buf = session.read(this.buf);
                        int n2 = this.buf.getCommand() & 0xFF;
                        if (n2 == 64) {
                            this.buf = session.read(this.buf);
                            n2 = (this.buf.getCommand() & 0xFF);
                        }
                        else if (n2 == 65) {
                            this.buf = session.read(this.buf);
                            n2 = (this.buf.getCommand() & 0xFF);
                        }
                        if (n2 == 51) {
                            return false;
                        }
                        this.buf.getInt();
                        this.buf.getByte();
                        this.buf.getByte();
                        array = this.buf.getString();
                    }
                }
                final Buffer buffer = new Buffer();
                buffer.putString(session.getSessionId());
                buffer.putByte((byte)50);
                buffer.putString(str2byte);
                buffer.putString("ssh-connection".getBytes());
                buffer.putString("gssapi-with-mic".getBytes());
                final byte[] m\u0131c = gssContext.getMIC(buffer.buffer, 0, buffer.getLength());
                if (m\u0131c == null) {
                    return false;
                }
                this.packet.reset();
                this.buf.putByte((byte)66);
                this.buf.putString(m\u0131c);
                session.write(this.packet);
                gssContext.dispose();
                this.buf = session.read(this.buf);
                final int n3 = this.buf.getCommand() & 0xFF;
                if (n3 == 52) {
                    return true;
                }
                if (n3 == 51) {
                    this.buf.getInt();
                    this.buf.getByte();
                    this.buf.getByte();
                    final byte[] string2 = this.buf.getString();
                    if (this.buf.getByte() != 0) {
                        throw new JSchPartialAuthException(new String(string2));
                    }
                }
                return false;
            }
            else {
                if (n != 53) {
                    return false;
                }
                this.buf.getInt();
                this.buf.getByte();
                this.buf.getByte();
                final byte[] string3 = this.buf.getString();
                this.buf.getString();
                final String byte2str = Util.byte2str(string3);
                if (this.userinfo == null) {
                    continue;
                }
                this.userinfo.showMessage(byte2str);
            }
        }
    }
    
    static {
        supported_oid = new byte[][] { { 6, 9, 42, -122, 72, -122, -9, 18, 1, 2, 2 } };
        supported_method = new String[] { "gssapi-with-mic.krb5" };
    }
}
