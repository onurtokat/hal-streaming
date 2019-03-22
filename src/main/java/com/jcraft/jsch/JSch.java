// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

import java.util.Enumeration;
import java.io.InputStream;
import java.util.Vector;
import java.util.Hashtable;

public class JSch
{
    static Hashtable config;
    Vector pool;
    Vector identities;
    private HostKeyRepository known_hosts;
    private static final Logger DEVNULL;
    static Logger logger;
    
    public JSch() {
        this.pool = new Vector();
        this.identities = new Vector();
        this.known_hosts = null;
        try {
            final String s = ((Hashtable<K, String>)System.getProperties()).get("os.name");
            if (s != null && s.equals("Mac OS X")) {
                JSch.config.put("hmac-sha1", "com.jcraft.jsch.jcraft.HMACSHA1");
                JSch.config.put("hmac-md5", "com.jcraft.jsch.jcraft.HMACMD5");
                JSch.config.put("hmac-md5-96", "com.jcraft.jsch.jcraft.HMACMD596");
                JSch.config.put("hmac-sha1-96", "com.jcraft.jsch.jcraft.HMACSHA196");
            }
        }
        catch (Exception ex) {}
    }
    
    public Session getSession(final String s, final String s2) throws JSchException {
        return this.getSession(s, s2, 22);
    }
    
    public Session getSession(final String userName, final String host, final int port) throws JSchException {
        if (userName == null) {
            throw new JSchException("username must not be null.");
        }
        if (host == null) {
            throw new JSchException("host must not be null.");
        }
        final Session session = new Session(this);
        session.setUserName(userName);
        session.setHost(host);
        session.setPort(port);
        return session;
    }
    
    protected void addSession(final Session session) {
        synchronized (this.pool) {
            this.pool.addElement(session);
        }
    }
    
    protected boolean removeSession(final Session session) {
        synchronized (this.pool) {
            return this.pool.remove(session);
        }
    }
    
    public void setHostKeyRepository(final HostKeyRepository known_hosts) {
        this.known_hosts = known_hosts;
    }
    
    public void setKnownHosts(final String knownHosts) throws JSchException {
        if (this.known_hosts == null) {
            this.known_hosts = new KnownHosts(this);
        }
        if (this.known_hosts instanceof KnownHosts) {
            synchronized (this.known_hosts) {
                ((KnownHosts)this.known_hosts).setKnownHosts(knownHosts);
            }
        }
    }
    
    public void setKnownHosts(final InputStream knownHosts) throws JSchException {
        if (this.known_hosts == null) {
            this.known_hosts = new KnownHosts(this);
        }
        if (this.known_hosts instanceof KnownHosts) {
            synchronized (this.known_hosts) {
                ((KnownHosts)this.known_hosts).setKnownHosts(knownHosts);
            }
        }
    }
    
    public HostKeyRepository getHostKeyRepository() {
        if (this.known_hosts == null) {
            this.known_hosts = new KnownHosts(this);
        }
        return this.known_hosts;
    }
    
    public void addIdentity(final String s) throws JSchException {
        this.addIdentity(s, (byte[])null);
    }
    
    public void addIdentity(final String s, final String s2) throws JSchException {
        byte[] str2byte = null;
        if (s2 != null) {
            str2byte = Util.str2byte(s2);
        }
        this.addIdentity(s, str2byte);
        if (str2byte != null) {
            Util.bzero(str2byte);
        }
    }
    
    public void addIdentity(final String s, final byte[] array) throws JSchException {
        this.addIdentity(IdentityFile.newInstance(s, null, this), array);
    }
    
    public void addIdentity(final String s, final String s2, final byte[] array) throws JSchException {
        this.addIdentity(IdentityFile.newInstance(s, s2, this), array);
    }
    
    public void addIdentity(final String s, final byte[] array, final byte[] array2, final byte[] array3) throws JSchException {
        this.addIdentity(IdentityFile.newInstance(s, array, array2, this), array3);
    }
    
    public void addIdentity(final Identity \u0131dentity, byte[] passphrase) throws JSchException {
        if (passphrase != null) {
            try {
                final byte[] array = new byte[passphrase.length];
                System.arraycopy(passphrase, 0, array, 0, passphrase.length);
                passphrase = array;
                \u0131dentity.setPassphrase(passphrase);
            }
            finally {
                Util.bzero(passphrase);
            }
        }
        synchronized (this.identities) {
            if (!this.identities.contains(\u0131dentity)) {
                this.identities.addElement(\u0131dentity);
            }
        }
    }
    
    public void removeIdentity(final String s) throws JSchException {
        synchronized (this.identities) {
            for (int i = 0; i < this.identities.size(); ++i) {
                final Identity \u0131dentity = this.identities.elementAt(i);
                if (\u0131dentity.getName().equals(s)) {
                    this.identities.removeElement(\u0131dentity);
                    \u0131dentity.clear();
                    break;
                }
            }
        }
    }
    
    public Vector getIdentityNames() throws JSchException {
        final Vector<String> vector = new Vector<String>();
        synchronized (this.identities) {
            for (int i = 0; i < this.identities.size(); ++i) {
                vector.addElement(((Identity)this.identities.elementAt(i)).getName());
            }
        }
        return vector;
    }
    
    public void removeAllIdentity() throws JSchException {
        synchronized (this.identities) {
            final Vector \u0131dentityNames = this.getIdentityNames();
            for (int i = 0; i < \u0131dentityNames.size(); ++i) {
                this.removeIdentity(\u0131dentityNames.elementAt(i));
            }
        }
    }
    
    public static String getConfig(final String s) {
        synchronized (JSch.config) {
            return JSch.config.get(s);
        }
    }
    
    public static void setConfig(final Hashtable hashtable) {
        synchronized (JSch.config) {
            final Enumeration<String> keys = (Enumeration<String>)hashtable.keys();
            while (keys.hasMoreElements()) {
                final String s = keys.nextElement();
                JSch.config.put(s, hashtable.get(s));
            }
        }
    }
    
    public static void setConfig(final String s, final String s2) {
        JSch.config.put(s, s2);
    }
    
    public static void setLogger(final Logger logger) {
        if (logger == null) {
            JSch.logger = JSch.DEVNULL;
        }
        JSch.logger = logger;
    }
    
    static Logger getLogger() {
        return JSch.logger;
    }
    
    static {
        (JSch.config = new Hashtable()).put("kex", "diffie-hellman-group1-sha1,diffie-hellman-group-exchange-sha1");
        JSch.config.put("server_host_key", "ssh-rsa,ssh-dss");
        JSch.config.put("cipher.s2c", "aes128-ctr,aes128-cbc,3des-ctr,3des-cbc,blowfish-cbc,aes192-cbc,aes256-cbc");
        JSch.config.put("cipher.c2s", "aes128-ctr,aes128-cbc,3des-ctr,3des-cbc,blowfish-cbc,aes192-cbc,aes256-cbc");
        JSch.config.put("mac.s2c", "hmac-md5,hmac-sha1,hmac-sha1-96,hmac-md5-96");
        JSch.config.put("mac.c2s", "hmac-md5,hmac-sha1,hmac-sha1-96,hmac-md5-96");
        JSch.config.put("compression.s2c", "none");
        JSch.config.put("compression.c2s", "none");
        JSch.config.put("lang.s2c", "");
        JSch.config.put("lang.c2s", "");
        JSch.config.put("compression_level", "6");
        JSch.config.put("diffie-hellman-group-exchange-sha1", "com.jcraft.jsch.DHGEX");
        JSch.config.put("diffie-hellman-group1-sha1", "com.jcraft.jsch.DHG1");
        JSch.config.put("dh", "com.jcraft.jsch.jce.DH");
        JSch.config.put("3des-cbc", "com.jcraft.jsch.jce.TripleDESCBC");
        JSch.config.put("blowfish-cbc", "com.jcraft.jsch.jce.BlowfishCBC");
        JSch.config.put("hmac-sha1", "com.jcraft.jsch.jce.HMACSHA1");
        JSch.config.put("hmac-sha1-96", "com.jcraft.jsch.jce.HMACSHA196");
        JSch.config.put("hmac-md5", "com.jcraft.jsch.jce.HMACMD5");
        JSch.config.put("hmac-md5-96", "com.jcraft.jsch.jce.HMACMD596");
        JSch.config.put("sha-1", "com.jcraft.jsch.jce.SHA1");
        JSch.config.put("md5", "com.jcraft.jsch.jce.MD5");
        JSch.config.put("signature.dss", "com.jcraft.jsch.jce.SignatureDSA");
        JSch.config.put("signature.rsa", "com.jcraft.jsch.jce.SignatureRSA");
        JSch.config.put("keypairgen.dsa", "com.jcraft.jsch.jce.KeyPairGenDSA");
        JSch.config.put("keypairgen.rsa", "com.jcraft.jsch.jce.KeyPairGenRSA");
        JSch.config.put("random", "com.jcraft.jsch.jce.Random");
        JSch.config.put("none", "com.jcraft.jsch.CipherNone");
        JSch.config.put("aes128-cbc", "com.jcraft.jsch.jce.AES128CBC");
        JSch.config.put("aes192-cbc", "com.jcraft.jsch.jce.AES192CBC");
        JSch.config.put("aes256-cbc", "com.jcraft.jsch.jce.AES256CBC");
        JSch.config.put("aes128-ctr", "com.jcraft.jsch.jce.AES128CTR");
        JSch.config.put("aes192-ctr", "com.jcraft.jsch.jce.AES192CTR");
        JSch.config.put("aes256-ctr", "com.jcraft.jsch.jce.AES256CTR");
        JSch.config.put("3des-ctr", "com.jcraft.jsch.jce.TripleDESCTR");
        JSch.config.put("arcfour", "com.jcraft.jsch.jce.ARCFOUR");
        JSch.config.put("arcfour128", "com.jcraft.jsch.jce.ARCFOUR128");
        JSch.config.put("arcfour256", "com.jcraft.jsch.jce.ARCFOUR256");
        JSch.config.put("userauth.none", "com.jcraft.jsch.UserAuthNone");
        JSch.config.put("userauth.password", "com.jcraft.jsch.UserAuthPassword");
        JSch.config.put("userauth.keyboard-interactive", "com.jcraft.jsch.UserAuthKeyboardInteractive");
        JSch.config.put("userauth.publickey", "com.jcraft.jsch.UserAuthPublicKey");
        JSch.config.put("userauth.gssapi-with-mic", "com.jcraft.jsch.UserAuthGSSAPIWithMIC");
        JSch.config.put("gssapi-with-mic.krb5", "com.jcraft.jsch.jgss.GSSContextKrb5");
        JSch.config.put("zlib", "com.jcraft.jsch.jcraft.Compression");
        JSch.config.put("zlib@openssh.com", "com.jcraft.jsch.jcraft.Compression");
        JSch.config.put("StrictHostKeyChecking", "ask");
        JSch.config.put("HashKnownHosts", "no");
        JSch.config.put("PreferredAuthentications", "gssapi-with-mic,publickey,keyboard-interactive,password");
        JSch.config.put("CheckCiphers", "aes256-ctr,aes192-ctr,aes128-ctr,aes256-cbc,aes192-cbc,aes128-cbc,3des-ctr,arcfour,arcfour128,arcfour256");
        DEVNULL = new Logger() {
            public boolean isEnabled(final int n) {
                return false;
            }
            
            public void log(final int n, final String s) {
            }
        };
        JSch.logger = JSch.DEVNULL;
    }
}
