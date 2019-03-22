// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

import java.util.Enumeration;
import java.util.Hashtable;

class ChannelSession extends Channel
{
    private static byte[] _session;
    protected boolean agent_forwarding;
    protected boolean xforwading;
    protected Hashtable env;
    protected boolean pty;
    protected String ttype;
    protected int tcol;
    protected int trow;
    protected int twp;
    protected int thp;
    protected byte[] terminal_mode;
    
    ChannelSession() {
        this.agent_forwarding = false;
        this.xforwading = false;
        this.env = null;
        this.pty = false;
        this.ttype = "vt100";
        this.tcol = 80;
        this.trow = 24;
        this.twp = 640;
        this.thp = 480;
        this.terminal_mode = null;
        this.type = ChannelSession._session;
        this.io = new IO();
    }
    
    public void setAgentForwarding(final boolean agent_forwarding) {
        this.agent_forwarding = agent_forwarding;
    }
    
    public void setXForwarding(final boolean xforwading) {
        this.xforwading = xforwading;
    }
    
    public void setEnv(final Hashtable env) {
        synchronized (this) {
            this.env = env;
        }
    }
    
    public void setEnv(final String s, final String s2) {
        this.setEnv(s.getBytes(), s2.getBytes());
    }
    
    public void setEnv(final byte[] array, final byte[] array2) {
        synchronized (this) {
            this.getEnv().put(array, array2);
        }
    }
    
    private Hashtable getEnv() {
        if (this.env == null) {
            this.env = new Hashtable();
        }
        return this.env;
    }
    
    public void setPty(final boolean pty) {
        this.pty = pty;
    }
    
    public void setTerminalMode(final byte[] terminal_mode) {
        this.terminal_mode = terminal_mode;
    }
    
    public void setPtySize(final int n, final int n2, final int n3, final int n4) {
        this.setPtyType(this.ttype, n, n2, n3, n4);
        if (!this.pty || !this.isConnected()) {
            return;
        }
        try {
            final RequestWindowChange requestWindowChange = new RequestWindowChange();
            requestWindowChange.setSize(n, n2, n3, n4);
            requestWindowChange.request(this.getSession(), this);
        }
        catch (Exception ex) {}
    }
    
    public void setPtyType(final String s) {
        this.setPtyType(s, 80, 24, 640, 480);
    }
    
    public void setPtyType(final String ttype, final int tcol, final int trow, final int twp, final int thp) {
        this.ttype = ttype;
        this.tcol = tcol;
        this.trow = trow;
        this.twp = twp;
        this.thp = thp;
    }
    
    protected void sendRequests() throws Exception {
        final Session session = this.getSession();
        if (this.agent_forwarding) {
            new RequestAgentForwarding().request(session, this);
        }
        if (this.xforwading) {
            new RequestX11().request(session, this);
        }
        if (this.pty) {
            final RequestPtyReq requestPtyReq = new RequestPtyReq();
            requestPtyReq.setTType(this.ttype);
            requestPtyReq.setTSize(this.tcol, this.trow, this.twp, this.thp);
            if (this.terminal_mode != null) {
                requestPtyReq.setTerminalMode(this.terminal_mode);
            }
            requestPtyReq.request(session, this);
        }
        if (this.env != null) {
            final Enumeration<Object> keys = this.env.keys();
            while (keys.hasMoreElements()) {
                final Object nextElement = keys.nextElement();
                final Object value = this.env.get(nextElement);
                final RequestEnv requestEnv = new RequestEnv();
                requestEnv.setEnv(this.toByteArray(nextElement), this.toByteArray(value));
                requestEnv.request(session, this);
            }
        }
    }
    
    private byte[] toByteArray(final Object o) {
        if (o instanceof String) {
            return ((String)o).getBytes();
        }
        return (byte[])o;
    }
    
    public void run() {
        final Buffer buffer = new Buffer(this.rmpsize);
        final Packet packet = new Packet(buffer);
        try {
            while (this.isConnected() && this.thread != null && this.io != null && this.io.in != null) {
                final int read = this.io.in.read(buffer.buffer, 14, buffer.buffer.length - 14 - 32 - 20);
                if (read == 0) {
                    continue;
                }
                if (read == -1) {
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
                this.getSession().write(packet, this, read);
            }
        }
        catch (Exception ex) {}
        if (this.thread != null) {
            synchronized (this.thread) {
                this.thread.notifyAll();
            }
        }
        this.thread = null;
    }
    
    static {
        ChannelSession._session = "session".getBytes();
    }
}
