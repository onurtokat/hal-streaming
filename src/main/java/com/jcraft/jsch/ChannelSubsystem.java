// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ChannelSubsystem extends ChannelSession
{
    boolean xforwading;
    boolean pty;
    boolean want_reply;
    String subsystem;
    
    public ChannelSubsystem() {
        this.xforwading = false;
        this.pty = false;
        this.want_reply = true;
        this.subsystem = "";
    }
    
    public void setXForwarding(final boolean b) {
        this.xforwading = true;
    }
    
    public void setPty(final boolean pty) {
        this.pty = pty;
    }
    
    public void setWantReply(final boolean want_reply) {
        this.want_reply = want_reply;
    }
    
    public void setSubsystem(final String subsystem) {
        this.subsystem = subsystem;
    }
    
    public void start() throws JSchException {
        final Session session = this.getSession();
        try {
            if (this.xforwading) {
                new RequestX11().request(session, this);
            }
            if (this.pty) {
                new RequestPtyReq().request(session, this);
            }
            new RequestSubsystem().request(session, this, this.subsystem, this.want_reply);
        }
        catch (Exception ex) {
            if (ex instanceof JSchException) {
                throw (JSchException)ex;
            }
            if (ex instanceof Throwable) {
                throw new JSchException("ChannelSubsystem", ex);
            }
            throw new JSchException("ChannelSubsystem");
        }
        if (this.io.in != null) {
            (this.thread = new Thread(this)).setName("Subsystem for " + session.host);
            if (session.daemon_thread) {
                this.thread.setDaemon(session.daemon_thread);
            }
            this.thread.start();
        }
    }
    
    void init() throws JSchException {
        this.io.setInputStream(this.getSession().in);
        this.io.setOutputStream(this.getSession().out);
    }
    
    public void setErrStream(final OutputStream extOutputStream) {
        this.setExtOutputStream(extOutputStream);
    }
    
    public InputStream getErrStream() throws IOException {
        return this.getExtInputStream();
    }
}
