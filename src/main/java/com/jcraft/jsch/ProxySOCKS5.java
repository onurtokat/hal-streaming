// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

import java.io.IOException;
import java.net.Socket;
import java.io.OutputStream;
import java.io.InputStream;

public class ProxySOCKS5 implements Proxy
{
    private static int DEFAULTPORT;
    private String proxy_host;
    private int proxy_port;
    private InputStream in;
    private OutputStream out;
    private Socket socket;
    private String user;
    private String passwd;
    
    public ProxySOCKS5(final String s) {
        int proxy_port = ProxySOCKS5.DEFAULTPORT;
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
    
    public ProxySOCKS5(final String proxy_host, final int proxy_port) {
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
            array[n2++] = 5;
            array[n2++] = 2;
            array[n2++] = 0;
            array[n2++] = 2;
            this.out.write(array, 0, n2);
            this.fill(this.in, array, 2);
            boolean b = false;
            switch (array[1] & 0xFF) {
                case 0: {
                    b = true;
                    break;
                }
                case 2: {
                    if (this.user == null) {
                        break;
                    }
                    if (this.passwd == null) {
                        break;
                    }
                    int n3 = 0;
                    array[n3++] = 1;
                    array[n3++] = (byte)this.user.length();
                    System.arraycopy(this.user.getBytes(), 0, array, n3, this.user.length());
                    int n4 = n3 + this.user.length();
                    array[n4++] = (byte)this.passwd.length();
                    System.arraycopy(this.passwd.getBytes(), 0, array, n4, this.passwd.length());
                    this.out.write(array, 0, n4 + this.passwd.length());
                    this.fill(this.in, array, 2);
                    if (array[1] == 0) {
                        b = true;
                        break;
                    }
                    break;
                }
            }
            if (!b) {
                try {
                    this.socket.close();
                }
                catch (Exception ex3) {}
                throw new JSchException("fail in SOCKS5 proxy");
            }
            int n5 = 0;
            array[n5++] = 5;
            array[n5++] = 1;
            array[n5++] = 0;
            final byte[] bytes = s.getBytes();
            final int length = bytes.length;
            array[n5++] = 3;
            array[n5++] = (byte)length;
            System.arraycopy(bytes, 0, array, n5, length);
            int n6 = n5 + length;
            array[n6++] = (byte)(n >>> 8);
            array[n6++] = (byte)(n & 0xFF);
            this.out.write(array, 0, n6);
            this.fill(this.in, array, 4);
            if (array[1] != 0) {
                try {
                    this.socket.close();
                }
                catch (Exception ex4) {}
                throw new JSchException("ProxySOCKS5: server returns " + array[1]);
            }
            switch (array[3] & 0xFF) {
                case 1: {
                    this.fill(this.in, array, 6);
                    break;
                }
                case 3: {
                    this.fill(this.in, array, 1);
                    this.fill(this.in, array, (array[0] & 0xFF) + 2);
                    break;
                }
                case 4: {
                    this.fill(this.in, array, 18);
                    break;
                }
            }
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
            catch (Exception ex5) {}
            final String string = "ProxySOCKS5: " + ex2.toString();
            if (ex2 instanceof Throwable) {
                throw new JSchException(string, ex2);
            }
            throw new JSchException(string);
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
        return ProxySOCKS5.DEFAULTPORT;
    }
    
    private void fill(final InputStream \u0131nputStream, final byte[] array, final int n) throws JSchException, IOException {
        int read;
        for (int i = 0; i < n; i += read) {
            read = \u0131nputStream.read(array, i, n - i);
            if (read <= 0) {
                throw new JSchException("ProxySOCKS5: stream is closed");
            }
        }
    }
    
    static {
        ProxySOCKS5.DEFAULTPORT = 1080;
    }
}
