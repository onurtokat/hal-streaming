// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

import java.io.IOException;
import java.net.Socket;
import java.io.OutputStream;
import java.io.InputStream;

public class ProxyHTTP implements Proxy
{
    private static int DEFAULTPORT;
    private String proxy_host;
    private int proxy_port;
    private InputStream in;
    private OutputStream out;
    private Socket socket;
    private String user;
    private String passwd;
    
    public ProxyHTTP(final String s) {
        int proxy_port = ProxyHTTP.DEFAULTPORT;
        String substring = s;
        if (s.indexOf(58) != -1) {
            try {
                substring = s.substring(0, s.indexOf(58));
                proxy_port = Integer.parseInt(s.substring(s.indexOf(58) + 1));
            }
            catch (Exception ex) {}
        }
        this.proxy_host = substring;
        this.proxy_port = proxy_port;
    }
    
    public ProxyHTTP(final String proxy_host, final int proxy_port) {
        this.proxy_host = proxy_host;
        this.proxy_port = proxy_port;
    }
    
    public void setUserPasswd(final String user, final String passwd) {
        this.user = user;
        this.passwd = passwd;
    }
    
    public void connect(final SocketFactory socketFactory, final String s, final int n, final int soTimeout) throws JSchException {
        try {
            if (socketFactory == null) {
                this.socket = Util.createSocket(this.proxy_host, this.proxy_port, soTimeout);
                this.in = this.socket.getInputStream();
                this.out = this.socket.getOutputStream();
            }
            else {
                this.socket = socketFactory.createSocket(this.proxy_host, this.proxy_port);
                this.in = socketFactory.getInputStream(this.socket);
                this.out = socketFactory.getOutputStream(this.socket);
            }
            if (soTimeout > 0) {
                this.socket.setSoTimeout(soTimeout);
            }
            this.socket.setTcpNoDelay(true);
            this.out.write(("CONNECT " + s + ":" + n + " HTTP/1.0\r\n").getBytes());
            if (this.user != null && this.passwd != null) {
                final byte[] bytes = (this.user + ":" + this.passwd).getBytes();
                final byte[] base64 = Util.toBase64(bytes, 0, bytes.length);
                this.out.write("Proxy-Authorization: Basic ".getBytes());
                this.out.write(base64);
                this.out.write("\r\n".getBytes());
            }
            this.out.write("\r\n".getBytes());
            this.out.flush();
            int i = 0;
            final StringBuffer sb = new StringBuffer();
            while (i >= 0) {
                i = this.in.read();
                if (i != 13) {
                    sb.append((char)i);
                }
                else {
                    i = this.in.read();
                    if (i != 10) {
                        continue;
                    }
                    break;
                }
            }
            if (i < 0) {
                throw new IOException();
            }
            final String string = sb.toString();
            String substring = "Unknow reason";
            int \u0131nt = -1;
            try {
                i = string.indexOf(32);
                final int index = string.indexOf(32, i + 1);
                \u0131nt = Integer.parseInt(string.substring(i + 1, index));
                substring = string.substring(index + 1);
            }
            catch (Exception ex3) {}
            if (\u0131nt != 200) {
                throw new IOException("proxy error: " + substring);
            }
            int j;
            do {
                j = 0;
                while (i >= 0) {
                    i = this.in.read();
                    if (i != 13) {
                        ++j;
                    }
                    else {
                        i = this.in.read();
                        if (i != 10) {
                            continue;
                        }
                        break;
                    }
                }
                if (i < 0) {
                    throw new IOException();
                }
            } while (j != 0);
        }
        catch (RuntimeException ex) {
            throw ex;
        }
        catch (Exception ex2) {
            try {
                if (this.socket != null) {
                    this.socket.close();
                }
            }
            catch (Exception ex4) {}
            final String string2 = "ProxyHTTP: " + ex2.toString();
            if (ex2 instanceof Throwable) {
                throw new JSchException(string2, ex2);
            }
            throw new JSchException(string2);
        }
    }
    
    public InputStream getInputStream() {
        return this.in;
    }
    
    public OutputStream getOutputStream() {
        return this.out;
    }
    
    public Socket getSocket() {
        return this.socket;
    }
    
    public void close() {
        try {
            if (this.in != null) {
                this.in.close();
            }
            if (this.out != null) {
                this.out.close();
            }
            if (this.socket != null) {
                this.socket.close();
            }
        }
        catch (Exception ex) {}
        this.in = null;
        this.out = null;
        this.socket = null;
    }
    
    public static int getDefaultPort() {
        return ProxyHTTP.DEFAULTPORT;
    }
    
    static {
        ProxyHTTP.DEFAULTPORT = 80;
    }
}
