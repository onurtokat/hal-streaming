// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

import java.util.Vector;
import java.io.IOException;

class ChannelAgentForwarding extends Channel
{
    private static final int LOCAL_WINDOW_SIZE_MAX = 131072;
    private static final int LOCAL_MAXIMUM_PACKET_SIZE = 16384;
    private final int SSH2_AGENTC_REQUEST_IDENTITIES = 11;
    private final int SSH2_AGENT_IDENTITIES_ANSWER = 12;
    private final int SSH2_AGENTC_SIGN_REQUEST = 13;
    private final int SSH2_AGENT_SIGN_RESPONSE = 14;
    private final int SSH2_AGENTC_ADD_IDENTITY = 17;
    private final int SSH2_AGENTC_REMOVE_IDENTITY = 18;
    private final int SSH2_AGENTC_REMOVE_ALL_IDENTITIES = 19;
    private final int SSH2_AGENT_FAILURE = 30;
    boolean init;
    private Buffer rbuf;
    private Buffer wbuf;
    private Packet packet;
    private Buffer mbuf;
    
    ChannelAgentForwarding() {
        this.init = true;
        this.rbuf = null;
        this.wbuf = null;
        this.packet = null;
        this.mbuf = null;
        this.setLocalWindowSizeMax(131072);
        this.setLocalWindowSize(131072);
        this.setLocalPacketSize(16384);
        this.type = "auth-agent@openssh.com".getBytes();
        (this.rbuf = new Buffer()).reset();
        this.mbuf = new Buffer();
        this.connected = true;
    }
    
    public void run() {
        try {
            this.sendOpenConfirmation();
        }
        catch (Exception ex) {
            this.close = true;
            this.disconnect();
        }
    }
    
    void write(final byte[] array, final int n, final int n2) throws IOException {
        if (this.packet == null) {
            this.wbuf = new Buffer(this.rmpsize);
            this.packet = new Packet(this.wbuf);
        }
        this.rbuf.shift();
        if (this.rbuf.buffer.length < this.rbuf.index + n2) {
            final byte[] buffer = new byte[this.rbuf.s + n2];
            System.arraycopy(this.rbuf.buffer, 0, buffer, 0, this.rbuf.buffer.length);
            this.rbuf.buffer = buffer;
        }
        this.rbuf.putByte(array, n, n2);
        if (this.rbuf.getInt() > this.rbuf.getLength()) {
            final Buffer rbuf = this.rbuf;
            rbuf.s -= 4;
            return;
        }
        final int byte1 = this.rbuf.getByte();
        Session session;
        try {
            session = this.getSession();
        }
        catch (JSchException ex) {
            throw new IOException(ex.toString());
        }
        final Vector identities = session.jsch.identities;
        final UserInfo userInfo = session.getUserInfo();
        if (byte1 == 11) {
            this.mbuf.reset();
            this.mbuf.putByte((byte)12);
            synchronized (identities) {
                int n3 = 0;
                for (int i = 0; i < identities.size(); ++i) {
                    if (identities.elementAt(i).getPublicKeyBlob() != null) {
                        ++n3;
                    }
                }
                this.mbuf.putInt(n3);
                for (int j = 0; j < identities.size(); ++j) {
                    final byte[] publicKeyBlob = identities.elementAt(j).getPublicKeyBlob();
                    if (publicKeyBlob != null) {
                        this.mbuf.putString(publicKeyBlob);
                        this.mbuf.putString("".getBytes());
                    }
                }
            }
            final byte[] array2 = new byte[this.mbuf.getLength()];
            this.mbuf.getByte(array2);
            this.send(array2);
        }
        else if (byte1 == 13) {
            final byte[] string = this.rbuf.getString();
            final byte[] string2 = this.rbuf.getString();
            this.rbuf.getInt();
            Identity \u0131dentity = null;
            synchronized (identities) {
                for (int k = 0; k < identities.size(); ++k) {
                    final Identity \u0131dentity2 = identities.elementAt(k);
                    if (\u0131dentity2.getPublicKeyBlob() != null) {
                        if (Util.array_equals(string, \u0131dentity2.getPublicKeyBlob())) {
                            if (\u0131dentity2.isEncrypted()) {
                                if (userInfo == null) {
                                    continue;
                                }
                                while (\u0131dentity2.isEncrypted()) {
                                    if (!userInfo.promptPassphrase("Passphrase for " + \u0131dentity2.getName())) {
                                        break;
                                    }
                                    final String passphrase = userInfo.getPassphrase();
                                    if (passphrase == null) {
                                        break;
                                    }
                                    final byte[] str2byte = Util.str2byte(passphrase);
                                    try {
                                        if (\u0131dentity2.setPassphrase(str2byte)) {
                                            break;
                                        }
                                        continue;
                                    }
                                    catch (JSchException ex2) {
                                        break;
                                    }
                                }
                            }
                            if (!\u0131dentity2.isEncrypted()) {
                                \u0131dentity = \u0131dentity2;
                                break;
                            }
                        }
                    }
                }
            }
            byte[] signature = null;
            if (\u0131dentity != null) {
                signature = \u0131dentity.getSignature(string2);
            }
            this.mbuf.reset();
            if (signature == null) {
                this.mbuf.putByte((byte)30);
            }
            else {
                this.mbuf.putByte((byte)14);
                this.mbuf.putString(signature);
            }
            final byte[] array3 = new byte[this.mbuf.getLength()];
            this.mbuf.getByte(array3);
            this.send(array3);
        }
    }
    
    private void send(final byte[] array) {
        this.packet.reset();
        this.wbuf.putByte((byte)94);
        this.wbuf.putInt(this.recipient);
        this.wbuf.putInt(4 + array.length);
        this.wbuf.putString(array);
        try {
            this.getSession().write(this.packet, this, 4 + array.length);
        }
        catch (Exception ex) {}
    }
}
