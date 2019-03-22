// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

public class ChannelShell extends ChannelSession
{
    ChannelShell() {
        this.pty = true;
    }
    
    public void start() throws JSchException {
        final Session session = this.getSession();
        try {
            this.sendRequests();
            new RequestShell().request(session, this);
        }
        catch (Exception ex) {
            if (ex instanceof JSchException) {
                throw (JSchException)ex;
            }
            if (ex instanceof Throwable) {
                throw new JSchException("ChannelShell", ex);
            }
            throw new JSchException("ChannelShell");
        }
        if (this.io.in != null) {
            (this.thread = new Thread(this)).setName("Shell for " + session.host);
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
}
