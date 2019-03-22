// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

import java.io.OutputStream;
import java.io.InputStream;

public class ChannelDirectTCPIP extends Channel
{
    private static final int LOCAL_WINDOW_SIZE_MAX = 131072;
    private static final int LOCAL_MAXIMUM_PACKET_SIZE = 16384;
    String host;
    int port;
    String originator_IP_address;
    int originator_port;
    
    ChannelDirectTCPIP() {
        this.originator_IP_address = "127.0.0.1";
        this.originator_port = 0;
        this.setLocalWindowSizeMax(131072);
        this.setLocalWindowSize(131072);
        this.setLocalPacketSize(16384);
    }
    
    void init() {
        try {
            this.io = new IO();
        }
        catch (Exception ex) {
            System.err.println(ex);
        }
    }
    
    public void connect() throws JSchException {
        try {
            final Session session = this.getSession();
            if (!session.isConnected()) {
                throw new JSchException("session is down");
            }
            final Buffer buffer = new Buffer(150);
            final Packet packet = new Packet(buffer);
            packet.reset();
            buffer.putByte((byte)90);
            buffer.putString("direct-tcpip".getBytes());
            buffer.putInt(this.id);
            buffer.putInt(this.lwsize);
            buffer.putInt(this.lmpsize);
            buffer.putString(this.host.getBytes());
            buffer.putInt(this.port);
            buffer.putString(this.originator_IP_address.getBytes());
            buffer.putInt(this.originator_port);
            session.write(packet);
            int n = 1000;
            try {
                while (this.getRecipient() == -1 && session.isConnected() && n > 0 && !this.eof_remote) {
                    Thread.sleep(50L);
                    --n;
                }
            }
            catch (Exception ex2) {}
            if (!session.isConnected()) {
                throw new JSchException("session is down");
            }
            if (n == 0 || this.eof_remote) {
                throw new JSchException("channel is not opened.");
            }
            this.connected = true;
            if (this.io.in != null) {
                (this.thread = new Thread(this)).setName("DirectTCPIP thread " + session.getHost());
                if (session.daemon_thread) {
                    this.thread.setDaemon(session.daemon_thread);
                }
                this.thread.start();
            }
        }
        catch (Exception ex) {
            this.io.close();
            this.io = null;
            Channel.del(this);
            if (ex instanceof JSchException) {
                throw (JSchException)ex;
            }
        }
    }
    
    public void run() {
        final Buffer buffer = new Buffer(this.rmpsize);
        final Packet packet = new Packet(buffer);
        try {
            final Session session = this.getSession();
            while (this.isConnected() && this.thread != null && this.io != null && this.io.in != null) {
                final int read = this.io.in.read(buffer.buffer, 14, buffer.buffer.length - 14 - 32 - 20);
                if (read <= 0) {
                    this.eof();
                    break;
                }
                if (this.close) {
                    break;
                }
                packet.reset();
                buffer.putByte((byte)94);
                buffer.putInt(this.recipient);
                buffer.putInt(read);
                buffer.skip(read);
                session.write(packet, this, read);
            }
        }
        catch (Exception ex) {}
        this.disconnect();
    }
    
    public void setInputStream(final InputStream \u0131nputStream) {
        this.io.setInputStream(\u0131nputStream);
    }
    
    public void setOutputStream(final OutputStream outputStream) {
        this.io.setOutputStream(outputStream);
    }
    
    public void setHost(final String host) {
        this.host = host;
    }
    
    public void setPort(final int port) {
        this.port = port;
    }
    
    public void setOrgIPAddress(final String originator_IP_address) {
        this.originator_IP_address = originator_IP_address;
    }
    
    public void setOrgPort(final int originator_port) {
        this.originator_port = originator_port;
    }
}
