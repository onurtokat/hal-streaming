// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ChannelExec extends ChannelSession
{
    byte[] command;
    
    public ChannelExec() {
        this.command = new byte[0];
    }
    
    public void start() throws JSchException {
        final Session session = this.getSession();
        try {
            this.sendRequests();
            new RequestExec(this.command).request(session, this);
        }
        catch (Exception ex) {
            if (ex instanceof JSchException) {
                throw (JSchException)ex;
            }
            if (ex instanceof Throwable) {
                throw new JSchException("ChannelExec", ex);
            }
            throw new JSchException("ChannelExec");
        }
        if (this.io.in != null) {
            (this.thread = new Thread(this)).setName("Exec thread " + session.getHost());
            if (session.daemon_thread) {
                this.thread.setDaemon(session.daemon_thread);
            }
            this.thread.start();
        }
    }
    
    public void setCommand(final String s) {
        this.command = s.getBytes();
    }
    
    public void setCommand(final byte[] command) {
        this.command = command;
    }
    
    void init() throws JSchException {
        this.io.setInputStream(this.getSession().in);
        this.io.setOutputStream(this.getSession().out);
    }
    
    public void setErrStream(final OutputStream extOutputStream) {
        this.setExtOutputStream(extOutputStream);
    }
    
    public void setErrStream(final OutputStream outputStream, final boolean b) {
        this.setExtOutputStream(outputStream, b);
    }
    
    public InputStream getErrStream() throws IOException {
        return this.getExtInputStream();
    }
}
