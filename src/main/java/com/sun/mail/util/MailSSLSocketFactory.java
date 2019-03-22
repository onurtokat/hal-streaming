// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.mail.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.KeyStore;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.net.UnknownHostException;
import java.net.InetAddress;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import javax.net.ssl.SSLSocket;
import java.security.KeyManagementException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import javax.net.ssl.TrustManager;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

public class MailSSLSocketFactory extends SSLSocketFactory
{
    private boolean trustAllHosts;
    private String[] trustedHosts;
    private SSLContext sslcontext;
    private KeyManager[] keyManagers;
    private TrustManager[] trustManagers;
    private SecureRandom secureRandom;
    private SSLSocketFactory adapteeFactory;
    
    public MailSSLSocketFactory() throws GeneralSecurityException {
        this("TLS");
    }
    
    public MailSSLSocketFactory(final String protocol) throws GeneralSecurityException {
        this.trustedHosts = null;
        this.adapteeFactory = null;
        this.trustAllHosts = false;
        this.sslcontext = SSLContext.getInstance(protocol);
        this.keyManagers = null;
        this.trustManagers = new TrustManager[] { new MailTrustManager() };
        this.secureRandom = null;
        this.newAdapteeFactory();
    }
    
    private synchronized void newAdapteeFactory() throws KeyManagementException {
        this.sslcontext.init(this.keyManagers, this.trustManagers, this.secureRandom);
        this.adapteeFactory = this.sslcontext.getSocketFactory();
    }
    
    public synchronized KeyManager[] getKeyManagers() {
        return this.keyManagers.clone();
    }
    
    public synchronized void setKeyManagers(final KeyManager[] keyManagers) throws GeneralSecurityException {
        this.keyManagers = keyManagers.clone();
        this.newAdapteeFactory();
    }
    
    public synchronized SecureRandom getSecureRandom() {
        return this.secureRandom;
    }
    
    public synchronized void setSecureRandom(final SecureRandom secureRandom) throws GeneralSecurityException {
        this.secureRandom = secureRandom;
        this.newAdapteeFactory();
    }
    
    public synchronized TrustManager[] getTrustManagers() {
        return this.trustManagers;
    }
    
    public synchronized void setTrustManagers(final TrustManager[] trustManagers) throws GeneralSecurityException {
        this.trustManagers = trustManagers;
        this.newAdapteeFactory();
    }
    
    public synchronized boolean isTrustAllHosts() {
        return this.trustAllHosts;
    }
    
    public synchronized void setTrustAllHosts(final boolean trustAllHosts) {
        this.trustAllHosts = trustAllHosts;
    }
    
    public synchronized String[] getTrustedHosts() {
        return this.trustedHosts.clone();
    }
    
    public synchronized void setTrustedHosts(final String[] trustedHosts) {
        this.trustedHosts = trustedHosts.clone();
    }
    
    public synchronized boolean isServerTrusted(final String server, final SSLSocket sslSocket) {
        return this.trustAllHosts || this.trustedHosts == null || Arrays.asList(this.trustedHosts).contains(server);
    }
    
    public synchronized Socket createSocket(final Socket socket, final String s, final int i, final boolean flag) throws IOException {
        return this.adapteeFactory.createSocket(socket, s, i, flag);
    }
    
    public synchronized String[] getDefaultCipherSuites() {
        return this.adapteeFactory.getDefaultCipherSuites();
    }
    
    public synchronized String[] getSupportedCipherSuites() {
        return this.adapteeFactory.getSupportedCipherSuites();
    }
    
    public synchronized Socket createSocket() throws IOException {
        return this.adapteeFactory.createSocket();
    }
    
    public synchronized Socket createSocket(final InetAddress inetaddress, final int i, final InetAddress inetaddress1, final int j) throws IOException {
        return this.adapteeFactory.createSocket(inetaddress, i, inetaddress1, j);
    }
    
    public synchronized Socket createSocket(final InetAddress inetaddress, final int i) throws IOException {
        return this.adapteeFactory.createSocket(inetaddress, i);
    }
    
    public synchronized Socket createSocket(final String s, final int i, final InetAddress inetaddress, final int j) throws IOException, UnknownHostException {
        return this.adapteeFactory.createSocket(s, i, inetaddress, j);
    }
    
    public synchronized Socket createSocket(final String s, final int i) throws IOException, UnknownHostException {
        return this.adapteeFactory.createSocket(s, i);
    }
    
    private class MailTrustManager implements X509TrustManager
    {
        private X509TrustManager adapteeTrustManager;
        
        private MailTrustManager() throws GeneralSecurityException {
            this.adapteeTrustManager = null;
            final TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
            tmf.init((KeyStore)null);
            this.adapteeTrustManager = (X509TrustManager)tmf.getTrustManagers()[0];
        }
        
        public void checkClientTrusted(final X509Certificate[] certs, final String authType) throws CertificateException {
            if (!MailSSLSocketFactory.this.trustAllHosts && MailSSLSocketFactory.this.trustedHosts == null) {
                this.adapteeTrustManager.checkClientTrusted(certs, authType);
            }
        }
        
        public void checkServerTrusted(final X509Certificate[] certs, final String authType) throws CertificateException {
            if (!MailSSLSocketFactory.this.trustAllHosts && MailSSLSocketFactory.this.trustedHosts == null) {
                this.adapteeTrustManager.checkServerTrusted(certs, authType);
            }
        }
        
        public X509Certificate[] getAcceptedIssuers() {
            return this.adapteeTrustManager.getAcceptedIssuers();
        }
    }
}
