// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.PipedOutputStream;
import java.net.Socket;
import java.util.Vector;

public class ChannelForwardedTCPIP extends Channel
{
    static Vector pool;
    private static final int LOCAL_WINDOW_SIZE_MAX = 131072;
    private static final int LOCAL_MAXIMUM_PACKET_SIZE = 16384;
    private static final int TIMEOUT = 10000;
    SocketFactory factory;
    private Socket socket;
    private ForwardedTCPIPDaemon daemon;
    String target;
    int lport;
    int rport;
    
    ChannelForwardedTCPIP() {
        this.factory = null;
        this.socket = null;
        this.daemon = null;
        this.setLocalWindowSizeMax(131072);
        this.setLocalWindowSize(131072);
        this.setLocalPacketSize(16384);
        this.io = new IO();
        this.connected = true;
    }
    
    public void run() {
        try {
            if (this.lport == -1) {
                this.daemon = (ForwardedTCPIPDaemon)Class.forName(this.target).newInstance();
                final PipedOutputStream pipedOutputStream = new PipedOutputStream();
                this.io.setInputStream(new PassiveInputStream(this, pipedOutputStream, 32768), false);
                this.daemon.setChannel(this, this.getInputStream(), pipedOutputStream);
                this.daemon.setArg((Object[])getPort(this.getSession(), this.rport)[3]);
                new Thread(this.daemon).start();
            }
            else {
                (this.socket = ((this.factory == null) ? Util.createSocket(this.target, this.lport, 10000) : this.factory.createSocket(this.target, this.lport))).setTcpNoDelay(true);
                this.io.setInputStream(this.socket.getInputStream());
                this.io.setOutputStream(this.socket.getOutputStream());
            }
            this.sendOpenConfirmation();
        }
        catch (Exception ex) {
            this.sendOpenFailure(1);
            this.close = true;
            this.disconnect();
            return;
        }
        this.thread = Thread.currentThread();
        final Buffer buffer = new Buffer(this.rmpsize);
        final Packet packet = new Packet(buffer);
        try {
            while (this.thread != null && this.io != null && this.io.in != null) {
                final int read = this.io.in.read(buffer.buffer, 14, buffer.buffer.length - 14 - 32 - 20);
                if (read <= 0) {
                    this.eof();
                    break;
                }
                packet.reset();
                if (this.close) {
                    break;
                }
                buffer.putByte((byte)94);
                buffer.putInt(this.recipient);
                buffer.putInt(read);
                buffer.skip(read);
                this.getSession().write(packet, this, read);
            }
        }
        catch (Exception ex2) {}
        this.disconnect();
    }
    
    void getData(final Buffer buffer) {
        this.setRecipient(buffer.getInt());
        this.setRemoteWindowSize(buffer.getInt());
        this.setRemotePacketSize(buffer.getInt());
        buffer.getString();
        final int \u0131nt = buffer.getInt();
        buffer.getString();
        buffer.getInt();
        Object session = null;
        try {
            session = this.getSession();
        }
        catch (JSchException ex) {}
        synchronized (ChannelForwardedTCPIP.pool) {
            for (int i = 0; i < ChannelForwardedTCPIP.pool.size(); ++i) {
                final Object[] array = ChannelForwardedTCPIP.pool.elementAt(i);
                if (array[0] == session) {
                    if ((int)array[1] == \u0131nt) {
                        this.rport = \u0131nt;
                        this.target = (String)array[2];
                        if (array[3] == null || array[3] instanceof Object[]) {
                            this.lport = -1;
                        }
                        else {
                            this.lport = (int)array[3];
                        }
                        if (array.length >= 6) {
                            this.factory = (SocketFactory)array[5];
                            break;
                        }
                        break;
                    }
                }
            }
            if (this.target == null) {}
        }
    }
    
    static Object[] getPort(final Session session, final int n) {
        synchronized (ChannelForwardedTCPIP.pool) {
            for (int i = 0; i < ChannelForwardedTCPIP.pool.size(); ++i) {
                final Object[] array = ChannelForwardedTCPIP.pool.elementAt(i);
                if (array[0] == session) {
                    if ((int)array[1] == n) {
                        return array;
                    }
                }
            }
            return null;
        }
    }
    
    static String[] getPortForwarding(final Session session) {
        final Vector vector = new Vector<String>();
        synchronized (ChannelForwardedTCPIP.pool) {
            for (int i = 0; i < ChannelForwardedTCPIP.pool.size(); ++i) {
                final Object[] array = ChannelForwardedTCPIP.pool.elementAt(i);
                if (array[0] == session) {
                    if (array[3] == null) {
                        vector.addElement(array[1] + ":" + array[2] + ":");
                    }
                    else {
                        vector.addElement(array[1] + ":" + array[2] + ":" + array[3]);
                    }
                }
            }
        }
        final String[] array2 = new String[vector.size()];
        for (int j = 0; j < vector.size(); ++j) {
            array2[j] = vector.elementAt(j);
        }
        return array2;
    }
    
    static String normalize(final String s) {
        if (s == null) {
            return "localhost";
        }
        if (s.length() == 0 || s.equals("*")) {
            return "";
        }
        return s;
    }
    
    static void addPort(final Session session, final String s, final int n, final String s2, final int n2, final SocketFactory socketFactory) throws JSchException {
        final String normalize = normalize(s);
        synchronized (ChannelForwardedTCPIP.pool) {
            if (getPort(session, n) != null) {
                throw new JSchException("PortForwardingR: remote port " + n + " is already registered.");
            }
            ChannelForwardedTCPIP.pool.addElement(new Object[] { session, new Integer(n), s2, new Integer(n2), normalize, socketFactory });
        }
    }
    
    static void addPort(final Session session, final String s, final int n, final String s2, final Object[] array) throws JSchException {
        final String normalize = normalize(s);
        synchronized (ChannelForwardedTCPIP.pool) {
            if (getPort(session, n) != null) {
                throw new JSchException("PortForwardingR: remote port " + n + " is already registered.");
            }
            ChannelForwardedTCPIP.pool.addElement(new Object[] { session, new Integer(n), s2, array, normalize });
        }
    }
    
    static void delPort(final ChannelForwardedTCPIP channelForwardedTCPIP) {
        Session session = null;
        try {
            session = channelForwardedTCPIP.getSession();
        }
        catch (JSchException ex) {}
        if (session != null) {
            delPort(session, channelForwardedTCPIP.rport);
        }
    }
    
    static void delPort(final Session session, final int n) {
        delPort(session, null, n);
    }
    
    static void delPort(final Session session, String s, final int n) {
        synchronized (ChannelForwardedTCPIP.pool) {
            Object o = null;
            for (int i = 0; i < ChannelForwardedTCPIP.pool.size(); ++i) {
                final Object[] array = ChannelForwardedTCPIP.pool.elementAt(i);
                if (array[0] == session) {
                    if ((int)array[1] == n) {
                        o = array;
                        break;
                    }
                }
            }
            if (o == null) {
                return;
            }
            ChannelForwardedTCPIP.pool.removeElement(o);
            if (s == null) {
                s = (String)o[4];
            }
            if (s == null) {
                s = "0.0.0.0";
            }
        }
        final Buffer buffer = new Buffer(100);
        final Packet packet = new Packet(buffer);
        try {
            packet.reset();
            buffer.putByte((byte)80);
            buffer.putString("cancel-tcpip-forward".getBytes());
            buffer.putByte((byte)0);
            buffer.putString(s.getBytes());
            buffer.putInt(n);
            session.write(packet);
        }
        catch (Exception ex) {}
    }
    
    static void delPort(final Session session) {
        int[] array = null;
        int n = 0;
        synchronized (ChannelForwardedTCPIP.pool) {
            array = new int[ChannelForwardedTCPIP.pool.size()];
            for (int i = 0; i < ChannelForwardedTCPIP.pool.size(); ++i) {
                final Object[] array2 = ChannelForwardedTCPIP.pool.elementAt(i);
                if (array2[0] == session) {
                    array[n++] = (int)array2[1];
                }
            }
        }
        for (int j = 0; j < n; ++j) {
            delPort(session, array[j]);
        }
    }
    
    public int getRemotePort() {
        return this.rport;
    }
    
    void setSocketFactory(final SocketFactory factory) {
        this.factory = factory;
    }
    
    static {
        ChannelForwardedTCPIP.pool = new Vector();
    }
}
