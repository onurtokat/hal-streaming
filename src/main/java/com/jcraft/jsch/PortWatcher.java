// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

import java.io.OutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.util.Vector;

class PortWatcher implements Runnable
{
    private static Vector pool;
    private static InetAddress anyLocalAddress;
    Session session;
    int lport;
    int rport;
    String host;
    InetAddress boundaddress;
    Runnable thread;
    ServerSocket ss;
    
    static String[] getPortForwarding(final Session session) {
        final Vector vector = new Vector<String>();
        synchronized (PortWatcher.pool) {
            for (int i = 0; i < PortWatcher.pool.size(); ++i) {
                final PortWatcher portWatcher = PortWatcher.pool.elementAt(i);
                if (portWatcher.session == session) {
                    vector.addElement(portWatcher.lport + ":" + portWatcher.host + ":" + portWatcher.rport);
                }
            }
        }
        final String[] array = new String[vector.size()];
        for (int j = 0; j < vector.size(); ++j) {
            array[j] = vector.elementAt(j);
        }
        return array;
    }
    
    static PortWatcher getPort(final Session session, final String s, final int n) throws JSchException {
        InetAddress byName;
        try {
            byName = InetAddress.getByName(s);
        }
        catch (UnknownHostException ex) {
            throw new JSchException("PortForwardingL: invalid address " + s + " specified.", ex);
        }
        synchronized (PortWatcher.pool) {
            for (int i = 0; i < PortWatcher.pool.size(); ++i) {
                final PortWatcher portWatcher = PortWatcher.pool.elementAt(i);
                if (portWatcher.session == session && portWatcher.lport == n && ((PortWatcher.anyLocalAddress != null && portWatcher.boundaddress.equals(PortWatcher.anyLocalAddress)) || portWatcher.boundaddress.equals(byName))) {
                    return portWatcher;
                }
            }
            return null;
        }
    }
    
    static PortWatcher addPort(final Session session, final String s, final int n, final String s2, final int n2, final ServerSocketFactory serverSocketFactory) throws JSchException {
        if (getPort(session, s, n) != null) {
            throw new JSchException("PortForwardingL: local port " + s + ":" + n + " is already registered.");
        }
        final PortWatcher portWatcher = new PortWatcher(session, s, n, s2, n2, serverSocketFactory);
        PortWatcher.pool.addElement(portWatcher);
        return portWatcher;
    }
    
    static void delPort(final Session session, final String s, final int n) throws JSchException {
        final PortWatcher port = getPort(session, s, n);
        if (port == null) {
            throw new JSchException("PortForwardingL: local port " + s + ":" + n + " is not registered.");
        }
        port.delete();
        PortWatcher.pool.removeElement(port);
    }
    
    static void delPort(final Session session) {
        synchronized (PortWatcher.pool) {
            final PortWatcher[] array = new PortWatcher[PortWatcher.pool.size()];
            int n = 0;
            for (int i = 0; i < PortWatcher.pool.size(); ++i) {
                final PortWatcher portWatcher = PortWatcher.pool.elementAt(i);
                if (portWatcher.session == session) {
                    portWatcher.delete();
                    array[n++] = portWatcher;
                }
            }
            for (int j = 0; j < n; ++j) {
                PortWatcher.pool.removeElement(array[j]);
            }
        }
    }
    
    PortWatcher(final Session session, final String s, final int lport, final String host, final int rport, final ServerSocketFactory serverSocketFactory) throws JSchException {
        this.session = session;
        this.lport = lport;
        this.host = host;
        this.rport = rport;
        try {
            this.boundaddress = InetAddress.getByName(s);
            this.ss = ((serverSocketFactory == null) ? new ServerSocket(lport, 0, this.boundaddress) : serverSocketFactory.createServerSocket(lport, 0, this.boundaddress));
        }
        catch (Exception ex) {
            final String string = "PortForwardingL: local port " + s + ":" + lport + " cannot be bound.";
            if (ex instanceof Throwable) {
                throw new JSchException(string, ex);
            }
            throw new JSchException(string);
        }
        if (lport == 0) {
            final int localPort = this.ss.getLocalPort();
            if (localPort != -1) {
                this.lport = localPort;
            }
        }
    }
    
    public void run() {
        this.thread = this;
        try {
            while (this.thread != null) {
                final Socket accept = this.ss.accept();
                accept.setTcpNoDelay(true);
                final InputStream \u0131nputStream = accept.getInputStream();
                final OutputStream outputStream = accept.getOutputStream();
                final ChannelDirectTCPIP channelDirectTCPIP = new ChannelDirectTCPIP();
                channelDirectTCPIP.init();
                channelDirectTCPIP.setInputStream(\u0131nputStream);
                channelDirectTCPIP.setOutputStream(outputStream);
                this.session.addChannel(channelDirectTCPIP);
                channelDirectTCPIP.setHost(this.host);
                channelDirectTCPIP.setPort(this.rport);
                channelDirectTCPIP.setOrgIPAddress(accept.getInetAddress().getHostAddress());
                channelDirectTCPIP.setOrgPort(accept.getPort());
                channelDirectTCPIP.connect();
                if (channelDirectTCPIP.exitstatus != -1) {}
            }
        }
        catch (Exception ex) {}
        this.delete();
    }
    
    void delete() {
        this.thread = null;
        try {
            if (this.ss != null) {
                this.ss.close();
            }
            this.ss = null;
        }
        catch (Exception ex) {}
    }
    
    static {
        PortWatcher.pool = new Vector();
        PortWatcher.anyLocalAddress = null;
        try {
            PortWatcher.anyLocalAddress = InetAddress.getByName("0.0.0.0");
        }
        catch (UnknownHostException ex) {}
    }
}
