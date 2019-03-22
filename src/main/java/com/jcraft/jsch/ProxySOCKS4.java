// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

import java.net.UnknownHostException;
import java.net.InetAddress;
import java.net.Socket;
import java.io.OutputStream;
import java.io.InputStream;

public class ProxySOCKS4 implements Proxy
{
    private static int DEFAULTPORT;
    private String proxy_host;
    private int proxy_port;
    private InputStream in;
    private OutputStream out;
    private Socket socket;
    private String user;
    private String passwd;
    
    public ProxySOCKS4(final String s) {
        int proxy_port = ProxySOCKS4.DEFAULTPORT;
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
    
    public ProxySOCKS4(final String proxy_host, final int proxy_port) {
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
            final byte[] array = new byte[1024];
            int n2 = 0;
            array[n2++] = 4;
            array[n2++] = 1;
            array[n2++] = (byte)(n >>> 8);
            array[n2++] = (byte)(n & 0xFF);
            try {
                final byte[] address = InetAddress.getByName(s).getAddress();
                for (int i = 0; i < address.length; ++i) {
                    array[n2++] = address[i];
                }
            }
            catch (UnknownHostException ex) {
                throw new JSchException("ProxySOCKS4: " + ex.toString(), ex);
            }
            if (this.user != null) {
                System.arraycopy(this.user.getBytes(), 0, array, n2, this.user.length());
                n2 += this.user.length();
            }
            array[n2++] = 0;
            this.out.write(array, 0, n2);
            int read;
            for (int n3 = 8, j = 0; j < n3; j += read) {
                read = this.in.read(array, j, n3 - j);
                if (read <= 0) {
                    throw new JSchException("ProxySOCKS4: stream is closed");
                }
            }
            if (array[0] != 0) {
                throw new JSchException("ProxySOCKS4: server returns VN " + array[0]);
            }
            if (array[1] != 90) {
                try {
                    this.socket.close();
                }
                catch (Exception ex4) {}
                throw new JSchException("ProxySOCKS4: server returns CD " + array[1]);
            }
        }
        catch (RuntimeException ex2) {
            throw ex2;
        }
        catch (Exception ex3) {
            try {
                if (this.socket != null) {
                    this.socket.close();
                }
            }
            catch (Exception ex5) {}
            throw new JSchException("ProxySOCKS4: " + ex3.toString());
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
        return ProxySOCKS4.DEFAULTPORT;
    }
    
    static {
        ProxySOCKS4.DEFAULTPORT = 1080;
    }
}
