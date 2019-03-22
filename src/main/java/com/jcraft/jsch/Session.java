// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

import java.util.Vector;
import java.util.Enumeration;
import java.util.Properties;
import java.io.InterruptedIOException;
import java.util.Arrays;
import java.io.IOException;
import java.util.Hashtable;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.Socket;

public class Session implements Runnable
{
    private static final String version = "JSCH-0.1.42";
    static final int SSH_MSG_DISCONNECT = 1;
    static final int SSH_MSG_IGNORE = 2;
    static final int SSH_MSG_UNIMPLEMENTED = 3;
    static final int SSH_MSG_DEBUG = 4;
    static final int SSH_MSG_SERVICE_REQUEST = 5;
    static final int SSH_MSG_SERVICE_ACCEPT = 6;
    static final int SSH_MSG_KEXINIT = 20;
    static final int SSH_MSG_NEWKEYS = 21;
    static final int SSH_MSG_KEXDH_INIT = 30;
    static final int SSH_MSG_KEXDH_REPLY = 31;
    static final int SSH_MSG_KEX_DH_GEX_GROUP = 31;
    static final int SSH_MSG_KEX_DH_GEX_INIT = 32;
    static final int SSH_MSG_KEX_DH_GEX_REPLY = 33;
    static final int SSH_MSG_KEX_DH_GEX_REQUEST = 34;
    static final int SSH_MSG_GLOBAL_REQUEST = 80;
    static final int SSH_MSG_REQUEST_SUCCESS = 81;
    static final int SSH_MSG_REQUEST_FAILURE = 82;
    static final int SSH_MSG_CHANNEL_OPEN = 90;
    static final int SSH_MSG_CHANNEL_OPEN_CONFIRMATION = 91;
    static final int SSH_MSG_CHANNEL_OPEN_FAILURE = 92;
    static final int SSH_MSG_CHANNEL_WINDOW_ADJUST = 93;
    static final int SSH_MSG_CHANNEL_DATA = 94;
    static final int SSH_MSG_CHANNEL_EXTENDED_DATA = 95;
    static final int SSH_MSG_CHANNEL_EOF = 96;
    static final int SSH_MSG_CHANNEL_CLOSE = 97;
    static final int SSH_MSG_CHANNEL_REQUEST = 98;
    static final int SSH_MSG_CHANNEL_SUCCESS = 99;
    static final int SSH_MSG_CHANNEL_FAILURE = 100;
    private byte[] V_S;
    private byte[] V_C;
    private byte[] I_C;
    private byte[] I_S;
    private byte[] K_S;
    private byte[] session_id;
    private byte[] IVc2s;
    private byte[] IVs2c;
    private byte[] Ec2s;
    private byte[] Es2c;
    private byte[] MACc2s;
    private byte[] MACs2c;
    private int seqi;
    private int seqo;
    String[] guess;
    private Cipher s2ccipher;
    private Cipher c2scipher;
    private MAC s2cmac;
    private MAC c2smac;
    private byte[] s2cmac_result1;
    private byte[] s2cmac_result2;
    private Compression deflater;
    private Compression inflater;
    private IO io;
    private Socket socket;
    private int timeout;
    private boolean isConnected;
    private boolean isAuthed;
    private Thread connectThread;
    private Object lock;
    boolean x11_forwarding;
    boolean agent_forwarding;
    InputStream in;
    OutputStream out;
    static Random random;
    Buffer buf;
    Packet packet;
    SocketFactory socket_factory;
    private Hashtable config;
    private Proxy proxy;
    private UserInfo userinfo;
    private String hostKeyAlias;
    private int serverAliveInterval;
    private int serverAliveCountMax;
    protected boolean daemon_thread;
    String host;
    int port;
    String username;
    byte[] password;
    JSch jsch;
    private boolean in_kex;
    int[] uncompress_len;
    private int s2ccipher_size;
    private int c2scipher_size;
    Runnable thread;
    private GlobalRequestReply grr;
    private static final byte[] keepalivemsg;
    private HostKey hostkey;
    
    Session(final JSch jsch) throws JSchException {
        this.V_C = "SSH-2.0-JSCH-0.1.42".getBytes();
        this.seqi = 0;
        this.seqo = 0;
        this.guess = null;
        this.timeout = 0;
        this.isConnected = false;
        this.isAuthed = false;
        this.connectThread = null;
        this.lock = new Object();
        this.x11_forwarding = false;
        this.agent_forwarding = false;
        this.in = null;
        this.out = null;
        this.socket_factory = null;
        this.config = null;
        this.proxy = null;
        this.hostKeyAlias = null;
        this.serverAliveInterval = 0;
        this.serverAliveCountMax = 1;
        this.daemon_thread = false;
        this.host = "127.0.0.1";
        this.port = 22;
        this.username = null;
        this.password = null;
        this.in_kex = false;
        this.uncompress_len = new int[1];
        this.s2ccipher_size = 8;
        this.c2scipher_size = 8;
        this.grr = new GlobalRequestReply();
        this.hostkey = null;
        this.jsch = jsch;
        this.buf = new Buffer();
        this.packet = new Packet(this.buf);
    }
    
    public void connect() throws JSchException {
        this.connect(this.timeout);
    }
    
    public void connect(final int soTimeout) throws JSchException {
        if (this.isConnected) {
            throw new JSchException("session is already connected");
        }
        this.io = new IO();
        if (Session.random == null) {
            try {
                Session.random = (Random)Class.forName(this.getConfig("random")).newInstance();
            }
            catch (Exception ex) {
                throw new JSchException(ex.toString(), ex);
            }
        }
        Packet.setRandom(Session.random);
        if (JSch.getLogger().isEnabled(1)) {
            JSch.getLogger().log(1, "Connecting to " + this.host + " port " + this.port);
        }
        try {
            if (this.proxy == null) {
                InputStream \u0131nputStream;
                OutputStream outputStream;
                if (this.socket_factory == null) {
                    this.socket = Util.createSocket(this.host, this.port, soTimeout);
                    \u0131nputStream = this.socket.getInputStream();
                    outputStream = this.socket.getOutputStream();
                }
                else {
                    this.socket = this.socket_factory.createSocket(this.host, this.port);
                    \u0131nputStream = this.socket_factory.getInputStream(this.socket);
                    outputStream = this.socket_factory.getOutputStream(this.socket);
                }
                this.socket.setTcpNoDelay(true);
                this.io.setInputStream(\u0131nputStream);
                this.io.setOutputStream(outputStream);
            }
            else {
                synchronized (this.proxy) {
                    this.proxy.connect(this.socket_factory, this.host, this.port, soTimeout);
                    this.io.setInputStream(this.proxy.getInputStream());
                    this.io.setOutputStream(this.proxy.getOutputStream());
                    this.socket = this.proxy.getSocket();
                }
            }
            if (soTimeout > 0 && this.socket != null) {
                this.socket.setSoTimeout(soTimeout);
            }
            this.isConnected = true;
            if (JSch.getLogger().isEnabled(1)) {
                JSch.getLogger().log(1, "Connection established");
            }
            this.jsch.addSession(this);
            final byte[] array = new byte[this.V_C.length + 1];
            System.arraycopy(this.V_C, 0, array, 0, this.V_C.length);
            array[array.length - 1] = 10;
            this.io.put(array, 0, array.length);
            int i;
            while (true) {
                i = 0;
                int byte1 = 0;
                while (i < this.buf.buffer.length) {
                    byte1 = this.io.getByte();
                    if (byte1 < 0) {
                        break;
                    }
                    this.buf.buffer[i] = (byte)byte1;
                    ++i;
                    if (byte1 == 10) {
                        break;
                    }
                }
                if (byte1 < 0) {
                    throw new JSchException("connection is closed by foreign host");
                }
                if (this.buf.buffer[i - 1] == 10 && --i > 0 && this.buf.buffer[i - 1] == 13) {
                    --i;
                }
                if (i <= 3) {
                    continue;
                }
                if (i == this.buf.buffer.length) {
                    break;
                }
                if (this.buf.buffer[0] != 83 || this.buf.buffer[1] != 83 || this.buf.buffer[2] != 72) {
                    continue;
                }
                if (this.buf.buffer[3] != 45) {
                    continue;
                }
                break;
            }
            if (i == this.buf.buffer.length || i < 7 || (this.buf.buffer[4] == 49 && this.buf.buffer[6] != 57)) {
                throw new JSchException("invalid server's version string");
            }
            this.V_S = new byte[i];
            System.arraycopy(this.buf.buffer, 0, this.V_S, 0, i);
            if (JSch.getLogger().isEnabled(1)) {
                JSch.getLogger().log(1, "Remote version string: " + new String(this.V_S));
                JSch.getLogger().log(1, "Local version string: " + new String(this.V_C));
            }
            this.send_kexinit();
            this.buf = this.read(this.buf);
            if (this.buf.getCommand() != 20) {
                throw new JSchException("invalid protocol: " + this.buf.getCommand());
            }
            if (JSch.getLogger().isEnabled(1)) {
                JSch.getLogger().log(1, "SSH_MSG_KEXINIT received");
            }
            final KeyExchange receive_kexinit = this.receive_kexinit(this.buf);
            do {
                this.buf = this.read(this.buf);
                if (receive_kexinit.getState() != this.buf.getCommand()) {
                    this.in_kex = false;
                    throw new JSchException("invalid protocol(kex): " + this.buf.getCommand());
                }
                final boolean next = receive_kexinit.next(this.buf);
                if (!next) {
                    this.in_kex = false;
                    throw new JSchException("verify: " + next);
                }
            } while (receive_kexinit.getState() != 0);
            try {
                this.checkHost(this.host, this.port, receive_kexinit);
            }
            catch (JSchException ex2) {
                this.in_kex = false;
                throw ex2;
            }
            this.send_newkeys();
            this.buf = this.read(this.buf);
            if (this.buf.getCommand() != 21) {
                this.in_kex = false;
                throw new JSchException("invalid protocol(newkyes): " + this.buf.getCommand());
            }
            if (JSch.getLogger().isEnabled(1)) {
                JSch.getLogger().log(1, "SSH_MSG_NEWKEYS received");
            }
            this.receive_newkeys(this.buf, receive_kexinit);
            boolean b = false;
            UserAuth userAuth;
            try {
                userAuth = (UserAuth)Class.forName(this.getConfig("userauth.none")).newInstance();
            }
            catch (Exception ex3) {
                throw new JSchException(ex3.toString(), ex3);
            }
            boolean b2 = userAuth.start(this);
            final String config = this.getConfig("PreferredAuthentications");
            final String[] split = Util.split(config, ",");
            String lowerCase = null;
            if (!b2) {
                final String methods = ((UserAuthNone)userAuth).getMethods();
                if (methods != null) {
                    lowerCase = methods.toLowerCase();
                }
                else {
                    lowerCase = config;
                }
            }
            String[] array2 = Util.split(lowerCase, ",");
            int n = 0;
            while (!b2 && split != null && n < split.length) {
                final String s = split[n++];
                boolean b3 = false;
                for (int j = 0; j < array2.length; ++j) {
                    if (array2[j].equals(s)) {
                        b3 = true;
                        break;
                    }
                }
                if (!b3) {
                    continue;
                }
                if (JSch.getLogger().isEnabled(1)) {
                    String s2 = "Authentications that can continue: ";
                    for (int k = n - 1; k < split.length; ++k) {
                        s2 += split[k];
                        if (k + 1 < split.length) {
                            s2 += ",";
                        }
                    }
                    JSch.getLogger().log(1, s2);
                    JSch.getLogger().log(1, "Next authentication method: " + s);
                }
                UserAuth userAuth2 = null;
                try {
                    if (this.getConfig("userauth." + s) != null) {
                        userAuth2 = (UserAuth)Class.forName(this.getConfig("userauth." + s)).newInstance();
                    }
                }
                catch (Exception ex7) {
                    if (JSch.getLogger().isEnabled(2)) {
                        JSch.getLogger().log(2, "failed to load " + s + " method");
                    }
                }
                if (userAuth2 == null) {
                    continue;
                }
                b = false;
                try {
                    b2 = userAuth2.start(this);
                    if (!b2 || !JSch.getLogger().isEnabled(1)) {
                        continue;
                    }
                    JSch.getLogger().log(1, "Authentication succeeded (" + s + ").");
                }
                catch (JSchAuthCancelException ex8) {
                    b = true;
                }
                catch (JSchPartialAuthException ex4) {
                    array2 = Util.split(ex4.getMethods(), ",");
                    n = 0;
                    b = false;
                }
                catch (RuntimeException ex5) {
                    throw ex5;
                }
                catch (Exception ex9) {
                    break;
                }
            }
            if (!b2) {
                if (b) {
                    throw new JSchException("Auth cancel");
                }
                throw new JSchException("Auth fail");
            }
            else {
                if (soTimeout > 0 || this.timeout > 0) {
                    this.socket.setSoTimeout(this.timeout);
                }
                this.isAuthed = true;
                synchronized (this.lock) {
                    if (this.isConnected) {
                        (this.connectThread = new Thread(this)).setName("Connect thread " + this.host + " session");
                        if (this.daemon_thread) {
                            this.connectThread.setDaemon(this.daemon_thread);
                        }
                        this.connectThread.start();
                    }
                }
            }
        }
        catch (Exception ex6) {
            this.in_kex = false;
            if (this.isConnected) {
                try {
                    this.packet.reset();
                    this.buf.putByte((byte)1);
                    this.buf.putInt(3);
                    this.buf.putString(ex6.toString().getBytes());
                    this.buf.putString("en".getBytes());
                    this.write(this.packet);
                    this.disconnect();
                }
                catch (Exception ex10) {}
            }
            this.isConnected = false;
            if (ex6 instanceof RuntimeException) {
                throw (RuntimeException)ex6;
            }
            if (ex6 instanceof JSchException) {
                throw (JSchException)ex6;
            }
            throw new JSchException("Session.connect: " + ex6);
        }
        finally {
            Util.bzero(this.password);
            this.password = null;
        }
    }
    
    private KeyExchange receive_kexinit(final Buffer buffer) throws Exception {
        final int \u0131nt = buffer.getInt();
        if (\u0131nt != buffer.getLength()) {
            buffer.getByte();
            this.I_S = new byte[buffer.index - 5];
        }
        else {
            this.I_S = new byte[\u0131nt - 1 - buffer.getByte()];
        }
        System.arraycopy(buffer.buffer, buffer.s, this.I_S, 0, this.I_S.length);
        if (!this.in_kex) {
            this.send_kexinit();
        }
        this.guess = KeyExchange.guess(this.I_S, this.I_C);
        if (this.guess == null) {
            throw new JSchException("Algorithm negotiation fail");
        }
        if (!this.isAuthed && (this.guess[2].equals("none") || this.guess[3].equals("none"))) {
            throw new JSchException("NONE Cipher should not be chosen before authentification is successed.");
        }
        KeyExchange keyExchange;
        try {
            keyExchange = (KeyExchange)Class.forName(this.getConfig(this.guess[0])).newInstance();
        }
        catch (Exception ex) {
            throw new JSchException(ex.toString(), ex);
        }
        keyExchange.init(this, this.V_S, this.V_C, this.I_S, this.I_C);
        return keyExchange;
    }
    
    public void rekey() throws Exception {
        this.send_kexinit();
    }
    
    private void send_kexinit() throws Exception {
        if (this.in_kex) {
            return;
        }
        String s = this.getConfig("cipher.c2s");
        String s2 = this.getConfig("cipher.s2c");
        final String[] checkCiphers = this.checkCiphers(this.getConfig("CheckCiphers"));
        if (checkCiphers != null && checkCiphers.length > 0) {
            s = Util.diffString(s, checkCiphers);
            s2 = Util.diffString(s2, checkCiphers);
            if (s == null || s2 == null) {
                throw new JSchException("There are not any available ciphers.");
            }
        }
        this.in_kex = true;
        final Buffer buffer = new Buffer();
        final Packet packet = new Packet(buffer);
        packet.reset();
        buffer.putByte((byte)20);
        synchronized (Session.random) {
            Session.random.fill(buffer.buffer, buffer.index, 16);
            buffer.skip(16);
        }
        buffer.putString(this.getConfig("kex").getBytes());
        buffer.putString(this.getConfig("server_host_key").getBytes());
        buffer.putString(s.getBytes());
        buffer.putString(s2.getBytes());
        buffer.putString(this.getConfig("mac.c2s").getBytes());
        buffer.putString(this.getConfig("mac.s2c").getBytes());
        buffer.putString(this.getConfig("compression.c2s").getBytes());
        buffer.putString(this.getConfig("compression.s2c").getBytes());
        buffer.putString(this.getConfig("lang.c2s").getBytes());
        buffer.putString(this.getConfig("lang.s2c").getBytes());
        buffer.putByte((byte)0);
        buffer.putInt(0);
        buffer.setOffSet(5);
        buffer.getByte(this.I_C = new byte[buffer.getLength()]);
        this.write(packet);
        if (JSch.getLogger().isEnabled(1)) {
            JSch.getLogger().log(1, "SSH_MSG_KEXINIT sent");
        }
    }
    
    private void send_newkeys() throws Exception {
        this.packet.reset();
        this.buf.putByte((byte)21);
        this.write(this.packet);
        if (JSch.getLogger().isEnabled(1)) {
            JSch.getLogger().log(1, "SSH_MSG_NEWKEYS sent");
        }
    }
    
    private void checkHost(String s, final int n, final KeyExchange keyExchange) throws JSchException {
        final String config = this.getConfig("StrictHostKeyChecking");
        if (this.hostKeyAlias != null) {
            s = this.hostKeyAlias;
        }
        final byte[] hostKey = keyExchange.getHostKey();
        final String keyType = keyExchange.getKeyType();
        final String fingerPrint = keyExchange.getFingerPrint();
        if (this.hostKeyAlias == null && n != 22) {
            s = "[" + s + "]:" + n;
        }
        final HostKeyRepository hostKeyRepository = this.jsch.getHostKeyRepository();
        int check = 0;
        synchronized (hostKeyRepository) {
            check = hostKeyRepository.check(s, hostKey);
        }
        int n2 = 0;
        if ((config.equals("ask") || config.equals("yes")) && check == 2) {
            String knownHostsRepositoryID = null;
            synchronized (hostKeyRepository) {
                knownHostsRepositoryID = hostKeyRepository.getKnownHostsRepositoryID();
            }
            if (knownHostsRepositoryID == null) {
                knownHostsRepositoryID = "known_hosts";
            }
            boolean promptYesNo = false;
            if (this.userinfo != null) {
                final String string = "WARNING: REMOTE HOST IDENTIFICATION HAS CHANGED!\nIT IS POSSIBLE THAT SOMEONE IS DOING SOMETHING NASTY!\nSomeone could be eavesdropping on you right now (man-in-the-middle attack)!\nIt is also possible that the " + keyType + " host key has just been changed.\n" + "The fingerprint for the " + keyType + " key sent by the remote host is\n" + fingerPrint + ".\n" + "Please contact your system administrator.\n" + "Add correct host key in " + knownHostsRepositoryID + " to get rid of this message.";
                if (config.equals("ask")) {
                    promptYesNo = this.userinfo.promptYesNo(string + "\nDo you want to delete the old key and insert the new key?");
                }
                else {
                    this.userinfo.showMessage(string);
                }
            }
            if (!promptYesNo) {
                throw new JSchException("HostKey has been changed: " + s);
            }
            synchronized (hostKeyRepository) {
                hostKeyRepository.remove(s, keyType.equals("DSA") ? "ssh-dss" : "ssh-rsa", null);
                n2 = 1;
            }
        }
        if ((config.equals("ask") || config.equals("yes")) && check && n2 == 0) {
            if (config.equals("yes")) {
                throw new JSchException("reject HostKey: " + this.host);
            }
            if (this.userinfo != null) {
                if (!this.userinfo.promptYesNo("The authenticity of host '" + this.host + "' can't be established.\n" + keyType + " key fingerprint is " + fingerPrint + ".\n" + "Are you sure you want to continue connecting?")) {
                    throw new JSchException("reject HostKey: " + this.host);
                }
                n2 = 1;
            }
            else {
                if (check == 1) {
                    throw new JSchException("UnknownHostKey: " + this.host + ". " + keyType + " key fingerprint is " + fingerPrint);
                }
                throw new JSchException("HostKey has been changed: " + this.host);
            }
        }
        if (config.equals("no") && check) {
            n2 = 1;
        }
        if (check == 0 && JSch.getLogger().isEnabled(1)) {
            JSch.getLogger().log(1, "Host '" + this.host + "' is known and mathces the " + keyType + " host key");
        }
        if (n2 != 0 && JSch.getLogger().isEnabled(2)) {
            JSch.getLogger().log(2, "Permanently added '" + this.host + "' (" + keyType + ") to the list of known hosts.");
        }
        if (this.getConfig("HashKnownHosts").equals("yes") && hostKeyRepository instanceof KnownHosts) {
            this.hostkey = ((KnownHosts)hostKeyRepository).createHashedHostKey(s, hostKey);
        }
        else {
            this.hostkey = new HostKey(s, hostKey);
        }
        if (n2 != 0) {
            synchronized (hostKeyRepository) {
                hostKeyRepository.add(this.hostkey, this.userinfo);
            }
        }
    }
    
    public Channel openChannel(final String s) throws JSchException {
        if (!this.isConnected) {
            throw new JSchException("session is down");
        }
        try {
            final Channel channel = Channel.getChannel(s);
            this.addChannel(channel);
            channel.init();
            return channel;
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public void encode(final Packet packet) throws Exception {
        if (this.deflater != null) {
            packet.buffer.index = this.deflater.compress(packet.buffer.buffer, 5, packet.buffer.index);
        }
        Label_0112: {
            if (this.c2scipher != null) {
                packet.padding(this.c2scipher_size);
                final byte b = packet.buffer.buffer[4];
                synchronized (Session.random) {
                    Session.random.fill(packet.buffer.buffer, packet.buffer.index - b, b);
                    break Label_0112;
                }
            }
            packet.padding(8);
        }
        if (this.c2smac != null) {
            this.c2smac.update(this.seqo);
            this.c2smac.update(packet.buffer.buffer, 0, packet.buffer.index);
            this.c2smac.doFinal(packet.buffer.buffer, packet.buffer.index);
        }
        if (this.c2scipher != null) {
            final byte[] buffer = packet.buffer.buffer;
            this.c2scipher.update(buffer, 0, packet.buffer.index, buffer, 0);
        }
        if (this.c2smac != null) {
            packet.buffer.skip(this.c2smac.getBlockSize());
        }
    }
    
    public Buffer read(final Buffer buffer) throws Exception {
        while (true) {
            buffer.reset();
            this.io.getByte(buffer.buffer, buffer.index, this.s2ccipher_size);
            buffer.index += this.s2ccipher_size;
            if (this.s2ccipher != null) {
                this.s2ccipher.update(buffer.buffer, 0, this.s2ccipher_size, buffer.buffer, 0);
            }
            final int n = (buffer.buffer[0] << 24 & 0xFF000000) | (buffer.buffer[1] << 16 & 0xFF0000) | (buffer.buffer[2] << 8 & 0xFF00) | (buffer.buffer[3] & 0xFF);
            if (n < 5 || n > 32764) {
                throw new IOException("invalid data");
            }
            final int n2 = n + 4 - this.s2ccipher_size;
            if (buffer.index + n2 > buffer.buffer.length) {
                final byte[] buffer2 = new byte[buffer.index + n2];
                System.arraycopy(buffer.buffer, 0, buffer2, 0, buffer.index);
                buffer.buffer = buffer2;
            }
            if (n2 % this.s2ccipher_size != 0) {
                final String string = "Bad packet length " + n2;
                if (JSch.getLogger().isEnabled(4)) {
                    JSch.getLogger().log(4, string);
                }
                this.packet.reset();
                buffer.putByte((byte)1);
                buffer.putInt(3);
                buffer.putString(string.getBytes());
                buffer.putString("en".getBytes());
                this.write(this.packet);
                this.disconnect();
                throw new JSchException("SSH_MSG_DISCONNECT: " + string);
            }
            if (n2 > 0) {
                this.io.getByte(buffer.buffer, buffer.index, n2);
                buffer.index += n2;
                if (this.s2ccipher != null) {
                    this.s2ccipher.update(buffer.buffer, this.s2ccipher_size, n2, buffer.buffer, this.s2ccipher_size);
                }
            }
            if (this.s2cmac != null) {
                this.s2cmac.update(this.seqi);
                this.s2cmac.update(buffer.buffer, 0, buffer.index);
                this.s2cmac.doFinal(this.s2cmac_result1, 0);
                this.io.getByte(this.s2cmac_result2, 0, this.s2cmac_result2.length);
                if (!Arrays.equals(this.s2cmac_result1, this.s2cmac_result2)) {
                    throw new IOException("MAC Error");
                }
            }
            ++this.seqi;
            if (this.inflater != null) {
                this.uncompress_len[0] = buffer.index - 5 - buffer.buffer[4];
                final byte[] uncompress = this.inflater.uncompress(buffer.buffer, 5, this.uncompress_len);
                if (uncompress == null) {
                    System.err.println("fail in inflater");
                    break;
                }
                buffer.buffer = uncompress;
                buffer.index = 5 + this.uncompress_len[0];
            }
            final int n3 = buffer.getCommand() & 0xFF;
            if (n3 == 1) {
                buffer.rewind();
                buffer.getInt();
                buffer.getShort();
                throw new JSchException("SSH_MSG_DISCONNECT: " + buffer.getInt() + " " + new String(buffer.getString()) + " " + new String(buffer.getString()));
            }
            if (n3 == 2) {
                continue;
            }
            if (n3 == 3) {
                buffer.rewind();
                buffer.getInt();
                buffer.getShort();
                final int \u0131nt = buffer.getInt();
                if (!JSch.getLogger().isEnabled(1)) {
                    continue;
                }
                JSch.getLogger().log(1, "Received SSH_MSG_UNIMPLEMENTED for " + \u0131nt);
            }
            else if (n3 == 4) {
                buffer.rewind();
                buffer.getInt();
                buffer.getShort();
            }
            else if (n3 == 93) {
                buffer.rewind();
                buffer.getInt();
                buffer.getShort();
                final Channel channel = Channel.getChannel(buffer.getInt(), this);
                if (channel == null) {
                    continue;
                }
                channel.addRemoteWindowSize(buffer.getInt());
            }
            else {
                if (n3 != 52) {
                    break;
                }
                this.isAuthed = true;
                if (this.inflater == null && this.deflater == null) {
                    this.initDeflater(this.guess[6]);
                    this.initInflater(this.guess[7]);
                    break;
                }
                break;
            }
        }
        buffer.rewind();
        return buffer;
    }
    
    byte[] getSessionId() {
        return this.session_id;
    }
    
    private void receive_newkeys(final Buffer buffer, final KeyExchange keyExchange) throws Exception {
        this.updateKeys(keyExchange);
        this.in_kex = false;
    }
    
    private void updateKeys(final KeyExchange keyExchange) throws Exception {
        final byte[] k = keyExchange.getK();
        final byte[] h = keyExchange.getH();
        final HASH hash = keyExchange.getHash();
        if (this.session_id == null) {
            System.arraycopy(h, 0, this.session_id = new byte[h.length], 0, h.length);
        }
        this.buf.reset();
        this.buf.putMPInt(k);
        this.buf.putByte(h);
        this.buf.putByte((byte)65);
        this.buf.putByte(this.session_id);
        hash.update(this.buf.buffer, 0, this.buf.index);
        this.IVc2s = hash.digest();
        final int n = this.buf.index - this.session_id.length - 1;
        final byte[] buffer = this.buf.buffer;
        final int n2 = n;
        ++buffer[n2];
        hash.update(this.buf.buffer, 0, this.buf.index);
        this.IVs2c = hash.digest();
        final byte[] buffer2 = this.buf.buffer;
        final int n3 = n;
        ++buffer2[n3];
        hash.update(this.buf.buffer, 0, this.buf.index);
        this.Ec2s = hash.digest();
        final byte[] buffer3 = this.buf.buffer;
        final int n4 = n;
        ++buffer3[n4];
        hash.update(this.buf.buffer, 0, this.buf.index);
        this.Es2c = hash.digest();
        final byte[] buffer4 = this.buf.buffer;
        final int n5 = n;
        ++buffer4[n5];
        hash.update(this.buf.buffer, 0, this.buf.index);
        this.MACc2s = hash.digest();
        final byte[] buffer5 = this.buf.buffer;
        final int n6 = n;
        ++buffer5[n6];
        hash.update(this.buf.buffer, 0, this.buf.index);
        this.MACs2c = hash.digest();
        try {
            this.s2ccipher = (Cipher)Class.forName(this.getConfig(this.guess[3])).newInstance();
            while (this.s2ccipher.getBlockSize() > this.Es2c.length) {
                this.buf.reset();
                this.buf.putMPInt(k);
                this.buf.putByte(h);
                this.buf.putByte(this.Es2c);
                hash.update(this.buf.buffer, 0, this.buf.index);
                final byte[] digest = hash.digest();
                final byte[] es2c = new byte[this.Es2c.length + digest.length];
                System.arraycopy(this.Es2c, 0, es2c, 0, this.Es2c.length);
                System.arraycopy(digest, 0, es2c, this.Es2c.length, digest.length);
                this.Es2c = es2c;
            }
            this.s2ccipher.init(1, this.Es2c, this.IVs2c);
            this.s2ccipher_size = this.s2ccipher.getIVSize();
            (this.s2cmac = (MAC)Class.forName(this.getConfig(this.guess[5])).newInstance()).init(this.MACs2c);
            this.s2cmac_result1 = new byte[this.s2cmac.getBlockSize()];
            this.s2cmac_result2 = new byte[this.s2cmac.getBlockSize()];
            this.c2scipher = (Cipher)Class.forName(this.getConfig(this.guess[2])).newInstance();
            while (this.c2scipher.getBlockSize() > this.Ec2s.length) {
                this.buf.reset();
                this.buf.putMPInt(k);
                this.buf.putByte(h);
                this.buf.putByte(this.Ec2s);
                hash.update(this.buf.buffer, 0, this.buf.index);
                final byte[] digest2 = hash.digest();
                final byte[] ec2s = new byte[this.Ec2s.length + digest2.length];
                System.arraycopy(this.Ec2s, 0, ec2s, 0, this.Ec2s.length);
                System.arraycopy(digest2, 0, ec2s, this.Ec2s.length, digest2.length);
                this.Ec2s = ec2s;
            }
            this.c2scipher.init(0, this.Ec2s, this.IVc2s);
            this.c2scipher_size = this.c2scipher.getIVSize();
            (this.c2smac = (MAC)Class.forName(this.getConfig(this.guess[4])).newInstance()).init(this.MACc2s);
            this.initDeflater(this.guess[6]);
            this.initInflater(this.guess[7]);
        }
        catch (Exception ex) {
            if (ex instanceof JSchException) {
                throw ex;
            }
            throw new JSchException(ex.toString(), ex);
        }
    }
    
    void write(final Packet packet, final Channel channel, int n) throws Exception {
        while (true) {
            if (this.in_kex) {
                try {
                    Thread.sleep(10L);
                }
                catch (InterruptedException ex) {}
            }
            else {
                synchronized (channel) {
                    if (channel.rwsize >= n) {
                        channel.rwsize -= n;
                        break;
                    }
                }
                if (channel.close || !channel.isConnected()) {
                    throw new IOException("channel is broken");
                }
                boolean b = false;
                int shift = 0;
                byte command = 0;
                int recipient = -1;
                synchronized (channel) {
                    if (channel.rwsize > 0) {
                        int rwsize = channel.rwsize;
                        if (rwsize > n) {
                            rwsize = n;
                        }
                        if (rwsize != n) {
                            shift = packet.shift(rwsize, (this.c2smac != null) ? this.c2smac.getBlockSize() : 0);
                        }
                        command = packet.buffer.getCommand();
                        recipient = channel.getRecipient();
                        n -= rwsize;
                        channel.rwsize -= rwsize;
                        b = true;
                    }
                }
                if (b) {
                    this._write(packet);
                    if (n == 0) {
                        return;
                    }
                    packet.unshift(command, recipient, shift, n);
                }
                synchronized (channel) {
                    if (this.in_kex) {
                        continue;
                    }
                    if (channel.rwsize < n) {
                        try {
                            ++channel.notifyme;
                            channel.wait(100L);
                        }
                        catch (InterruptedException ex2) {}
                        finally {
                            --channel.notifyme;
                        }
                        continue;
                    }
                    channel.rwsize -= n;
                }
                break;
            }
        }
        this._write(packet);
    }
    
    public void write(final Packet packet) throws Exception {
        while (this.in_kex) {
            final byte command = packet.buffer.getCommand();
            if (command == 20 || command == 21 || command == 30 || command == 31 || command == 31 || command == 32 || command == 33 || command == 34) {
                break;
            }
            if (command == 1) {
                break;
            }
            try {
                Thread.sleep(10L);
            }
            catch (InterruptedException ex) {}
        }
        this._write(packet);
    }
    
    private void _write(final Packet packet) throws Exception {
        synchronized (this.lock) {
            this.encode(packet);
            if (this.io != null) {
                this.io.put(packet);
                ++this.seqo;
            }
        }
    }
    
    public void run() {
        this.thread = this;
        Buffer read = new Buffer();
        final Packet packet = new Packet(read);
        final int[] array = { 0 };
        final int[] array2 = { 0 };
        KeyExchange receive_kexinit = null;
        int n = 0;
        try {
            while (this.isConnected && this.thread != null) {
                try {
                    read = this.read(read);
                    n = 0;
                }
                catch (InterruptedIOException ex) {
                    if (!this.in_kex && n < this.serverAliveCountMax) {
                        this.sendKeepAliveMsg();
                        ++n;
                        continue;
                    }
                    throw ex;
                }
                final int n2 = read.getCommand() & 0xFF;
                if (receive_kexinit != null && receive_kexinit.getState() == n2) {
                    final boolean next = receive_kexinit.next(read);
                    if (!next) {
                        throw new JSchException("verify: " + next);
                    }
                    continue;
                }
                else {
                    Label_1240: {
                        switch (n2) {
                            case 20: {
                                receive_kexinit = this.receive_kexinit(read);
                                continue;
                            }
                            case 21: {
                                this.send_newkeys();
                                this.receive_newkeys(read, receive_kexinit);
                                receive_kexinit = null;
                                continue;
                            }
                            case 94: {
                                read.getInt();
                                read.getByte();
                                read.getByte();
                                final Channel channel = Channel.getChannel(read.getInt(), this);
                                final byte[] string = read.getString(array, array2);
                                if (channel == null) {
                                    continue;
                                }
                                if (array2[0] == 0) {
                                    continue;
                                }
                                try {
                                    channel.write(string, array[0], array2[0]);
                                }
                                catch (Exception ex3) {
                                    try {
                                        channel.disconnect();
                                    }
                                    catch (Exception ex4) {}
                                }
                                channel.setLocalWindowSize(channel.lwsize - array2[0]);
                                if (channel.lwsize < channel.lwsize_max / 2) {
                                    packet.reset();
                                    read.putByte((byte)93);
                                    read.putInt(channel.getRecipient());
                                    read.putInt(channel.lwsize_max - channel.lwsize);
                                    this.write(packet);
                                    channel.setLocalWindowSize(channel.lwsize_max);
                                    continue;
                                }
                                continue;
                            }
                            case 95: {
                                read.getInt();
                                read.getShort();
                                final Channel channel2 = Channel.getChannel(read.getInt(), this);
                                read.getInt();
                                final byte[] string2 = read.getString(array, array2);
                                if (channel2 == null) {
                                    continue;
                                }
                                if (array2[0] == 0) {
                                    continue;
                                }
                                channel2.write_ext(string2, array[0], array2[0]);
                                channel2.setLocalWindowSize(channel2.lwsize - array2[0]);
                                if (channel2.lwsize < channel2.lwsize_max / 2) {
                                    packet.reset();
                                    read.putByte((byte)93);
                                    read.putInt(channel2.getRecipient());
                                    read.putInt(channel2.lwsize_max - channel2.lwsize);
                                    this.write(packet);
                                    channel2.setLocalWindowSize(channel2.lwsize_max);
                                    continue;
                                }
                                continue;
                            }
                            case 93: {
                                read.getInt();
                                read.getShort();
                                final Channel channel3 = Channel.getChannel(read.getInt(), this);
                                if (channel3 == null) {
                                    continue;
                                }
                                channel3.addRemoteWindowSize(read.getInt());
                                continue;
                            }
                            case 96: {
                                read.getInt();
                                read.getShort();
                                final Channel channel4 = Channel.getChannel(read.getInt(), this);
                                if (channel4 != null) {
                                    channel4.eof_remote();
                                    continue;
                                }
                                continue;
                            }
                            case 97: {
                                read.getInt();
                                read.getShort();
                                final Channel channel5 = Channel.getChannel(read.getInt(), this);
                                if (channel5 != null) {
                                    channel5.disconnect();
                                    continue;
                                }
                                continue;
                            }
                            case 91: {
                                read.getInt();
                                read.getShort();
                                final Channel channel6 = Channel.getChannel(read.getInt(), this);
                                if (channel6 == null) {}
                                final int \u0131nt = read.getInt();
                                final int \u0131nt2 = read.getInt();
                                final int \u0131nt3 = read.getInt();
                                channel6.setRemoteWindowSize(\u0131nt2);
                                channel6.setRemotePacketSize(\u0131nt3);
                                channel6.setRecipient(\u0131nt);
                                continue;
                            }
                            case 92: {
                                read.getInt();
                                read.getShort();
                                final Channel channel7 = Channel.getChannel(read.getInt(), this);
                                if (channel7 == null) {}
                                channel7.exitstatus = read.getInt();
                                channel7.close = true;
                                channel7.eof_remote = true;
                                channel7.setRecipient(0);
                                continue;
                            }
                            case 98: {
                                read.getInt();
                                read.getShort();
                                final int \u0131nt4 = read.getInt();
                                final byte[] string3 = read.getString();
                                final boolean b = read.getByte() != 0;
                                final Channel channel8 = Channel.getChannel(\u0131nt4, this);
                                if (channel8 == null) {
                                    continue;
                                }
                                byte b2 = 100;
                                if (new String(string3).equals("exit-status")) {
                                    channel8.setExitStatus(read.getInt());
                                    b2 = 99;
                                }
                                if (b) {
                                    packet.reset();
                                    read.putByte(b2);
                                    read.putInt(channel8.getRecipient());
                                    this.write(packet);
                                    continue;
                                }
                                continue;
                            }
                            case 90: {
                                read.getInt();
                                read.getShort();
                                final String s = new String(read.getString());
                                if (!"forwarded-tcpip".equals(s) && (!"x11".equals(s) || !this.x11_forwarding) && (!"auth-agent@openssh.com".equals(s) || !this.agent_forwarding)) {
                                    packet.reset();
                                    read.putByte((byte)92);
                                    read.putInt(read.getInt());
                                    read.putInt(1);
                                    read.putString("".getBytes());
                                    read.putString("".getBytes());
                                    this.write(packet);
                                    break Label_1240;
                                }
                                final Channel channel9 = Channel.getChannel(s);
                                this.addChannel(channel9);
                                channel9.getData(read);
                                channel9.init();
                                final Thread thread = new Thread(channel9);
                                thread.setName("Channel " + s + " " + this.host);
                                if (this.daemon_thread) {
                                    thread.setDaemon(this.daemon_thread);
                                }
                                thread.start();
                                continue;
                            }
                            case 99: {
                                read.getInt();
                                read.getShort();
                                final Channel channel10 = Channel.getChannel(read.getInt(), this);
                                if (channel10 == null) {
                                    continue;
                                }
                                channel10.reply = 1;
                                continue;
                            }
                            case 100: {
                                read.getInt();
                                read.getShort();
                                final Channel channel11 = Channel.getChannel(read.getInt(), this);
                                if (channel11 == null) {
                                    continue;
                                }
                                channel11.reply = 0;
                                continue;
                            }
                            case 80: {
                                read.getInt();
                                read.getShort();
                                read.getString();
                                if (read.getByte() != 0) {
                                    packet.reset();
                                    read.putByte((byte)82);
                                    this.write(packet);
                                    continue;
                                }
                                continue;
                            }
                            case 81:
                            case 82: {
                                final Thread thread2 = this.grr.getThread();
                                if (thread2 != null) {
                                    this.grr.setReply((n2 == 81) ? 1 : 0);
                                    thread2.interrupt();
                                    continue;
                                }
                                continue;
                            }
                            default: {
                                throw new IOException("Unknown SSH message type " + n2);
                            }
                        }
                    }
                }
            }
        }
        catch (Exception ex2) {
            if (JSch.getLogger().isEnabled(1)) {
                JSch.getLogger().log(1, "Caught an exception, leaving main loop due to " + ex2.getMessage());
            }
        }
        try {
            this.disconnect();
        }
        catch (NullPointerException ex5) {}
        catch (Exception ex6) {}
        this.isConnected = false;
    }
    
    public void disconnect() {
        if (!this.isConnected) {
            return;
        }
        if (JSch.getLogger().isEnabled(1)) {
            JSch.getLogger().log(1, "Disconnecting from " + this.host + " port " + this.port);
        }
        Channel.disconnect(this);
        this.isConnected = false;
        PortWatcher.delPort(this);
        ChannelForwardedTCPIP.delPort(this);
        synchronized (this.lock) {
            if (this.connectThread != null) {
                Thread.yield();
                this.connectThread.interrupt();
                this.connectThread = null;
            }
        }
        this.thread = null;
        try {
            if (this.io != null) {
                if (this.io.in != null) {
                    this.io.in.close();
                }
                if (this.io.out != null) {
                    this.io.out.close();
                }
                if (this.io.out_ext != null) {
                    this.io.out_ext.close();
                }
            }
            if (this.proxy == null) {
                if (this.socket != null) {
                    this.socket.close();
                }
            }
            else {
                synchronized (this.proxy) {
                    this.proxy.close();
                }
                this.proxy = null;
            }
        }
        catch (Exception ex) {}
        this.io = null;
        this.socket = null;
        this.jsch.removeSession(this);
    }
    
    public int setPortForwardingL(final int n, final String s, final int n2) throws JSchException {
        return this.setPortForwardingL("127.0.0.1", n, s, n2);
    }
    
    public int setPortForwardingL(final String s, final int n, final String s2, final int n2) throws JSchException {
        return this.setPortForwardingL(s, n, s2, n2, null);
    }
    
    public int setPortForwardingL(final String s, final int n, final String s2, final int n2, final ServerSocketFactory serverSocketFactory) throws JSchException {
        final PortWatcher addPort = PortWatcher.addPort(this, s, n, s2, n2, serverSocketFactory);
        final Thread thread = new Thread(addPort);
        thread.setName("PortWatcher Thread for " + s2);
        if (this.daemon_thread) {
            thread.setDaemon(this.daemon_thread);
        }
        thread.start();
        return addPort.lport;
    }
    
    public void delPortForwardingL(final int n) throws JSchException {
        this.delPortForwardingL("127.0.0.1", n);
    }
    
    public void delPortForwardingL(final String s, final int n) throws JSchException {
        PortWatcher.delPort(this, s, n);
    }
    
    public String[] getPortForwardingL() throws JSchException {
        return PortWatcher.getPortForwarding(this);
    }
    
    public void setPortForwardingR(final int n, final String s, final int n2) throws JSchException {
        this.setPortForwardingR(null, n, s, n2, null);
    }
    
    public void setPortForwardingR(final String s, final int n, final String s2, final int n2) throws JSchException {
        this.setPortForwardingR(s, n, s2, n2, null);
    }
    
    public void setPortForwardingR(final int n, final String s, final int n2, final SocketFactory socketFactory) throws JSchException {
        this.setPortForwardingR(null, n, s, n2, socketFactory);
    }
    
    public void setPortForwardingR(final String s, final int n, final String s2, final int n2, final SocketFactory socketFactory) throws JSchException {
        ChannelForwardedTCPIP.addPort(this, s, n, s2, n2, socketFactory);
        this.setPortForwarding(s, n);
    }
    
    public void setPortForwardingR(final int n, final String s) throws JSchException {
        this.setPortForwardingR(null, n, s, null);
    }
    
    public void setPortForwardingR(final int n, final String s, final Object[] array) throws JSchException {
        this.setPortForwardingR(null, n, s, array);
    }
    
    public void setPortForwardingR(final String s, final int n, final String s2, final Object[] array) throws JSchException {
        ChannelForwardedTCPIP.addPort(this, s, n, s2, array);
        this.setPortForwarding(s, n);
    }
    
    private void setPortForwarding(final String s, final int n) throws JSchException {
        synchronized (this.grr) {
            final Buffer buffer = new Buffer(100);
            final Packet packet = new Packet(buffer);
            final String normalize = ChannelForwardedTCPIP.normalize(s);
            try {
                packet.reset();
                buffer.putByte((byte)80);
                buffer.putString("tcpip-forward".getBytes());
                buffer.putByte((byte)1);
                buffer.putString(normalize.getBytes());
                buffer.putInt(n);
                this.write(packet);
            }
            catch (Exception ex) {
                if (ex instanceof Throwable) {
                    throw new JSchException(ex.toString(), ex);
                }
                throw new JSchException(ex.toString());
            }
            this.grr.setThread(Thread.currentThread());
            try {
                Thread.sleep(10000L);
            }
            catch (Exception ex2) {}
            final int reply = this.grr.getReply();
            this.grr.setThread(null);
            if (reply == 0) {
                throw new JSchException("remote port forwarding failed for listen port " + n);
            }
        }
    }
    
    public void delPortForwardingR(final int n) throws JSchException {
        ChannelForwardedTCPIP.delPort(this, n);
    }
    
    private void initDeflater(final String s) throws JSchException {
        if (s.equals("none")) {
            this.deflater = null;
            return;
        }
        final String config = this.getConfig(s);
        if (config != null) {
            if (!s.equals("zlib")) {
                if (!this.isAuthed || !s.equals("zlib@openssh.com")) {
                    return;
                }
            }
            try {
                this.deflater = (Compression)Class.forName(config).newInstance();
                int \u0131nt = 6;
                try {
                    \u0131nt = Integer.parseInt(this.getConfig("compression_level"));
                }
                catch (Exception ex2) {}
                this.deflater.init(1, \u0131nt);
            }
            catch (Exception ex) {
                throw new JSchException(ex.toString(), ex);
            }
        }
    }
    
    private void initInflater(final String s) throws JSchException {
        if (s.equals("none")) {
            this.inflater = null;
            return;
        }
        final String config = this.getConfig(s);
        if (config != null) {
            if (!s.equals("zlib")) {
                if (!this.isAuthed || !s.equals("zlib@openssh.com")) {
                    return;
                }
            }
            try {
                (this.inflater = (Compression)Class.forName(config).newInstance()).init(0, 0);
            }
            catch (Exception ex) {
                throw new JSchException(ex.toString(), ex);
            }
        }
    }
    
    void addChannel(final Channel channel) {
        channel.setSession(this);
    }
    
    public void setProxy(final Proxy proxy) {
        this.proxy = proxy;
    }
    
    public void setHost(final String host) {
        this.host = host;
    }
    
    public void setPort(final int port) {
        this.port = port;
    }
    
    void setUserName(final String username) {
        this.username = username;
    }
    
    public void setUserInfo(final UserInfo userinfo) {
        this.userinfo = userinfo;
    }
    
    public UserInfo getUserInfo() {
        return this.userinfo;
    }
    
    public void setInputStream(final InputStream in) {
        this.in = in;
    }
    
    public void setOutputStream(final OutputStream out) {
        this.out = out;
    }
    
    public void setX11Host(final String host) {
        ChannelX11.setHost(host);
    }
    
    public void setX11Port(final int port) {
        ChannelX11.setPort(port);
    }
    
    public void setX11Cookie(final String cookie) {
        ChannelX11.setCookie(cookie);
    }
    
    public void setPassword(final String s) {
        if (s != null) {
            this.password = Util.str2byte(s);
        }
    }
    
    public void setPassword(final byte[] array) {
        if (array != null) {
            System.arraycopy(array, 0, this.password = new byte[array.length], 0, array.length);
        }
    }
    
    public void setConfig(final Properties config) {
        this.setConfig((Hashtable)config);
    }
    
    public void setConfig(final Hashtable hashtable) {
        synchronized (this.lock) {
            if (this.config == null) {
                this.config = new Hashtable();
            }
            final Enumeration<String> keys = (Enumeration<String>)hashtable.keys();
            while (keys.hasMoreElements()) {
                final String s = keys.nextElement();
                this.config.put(s, hashtable.get(s));
            }
        }
    }
    
    public void setConfig(final String s, final String s2) {
        synchronized (this.lock) {
            if (this.config == null) {
                this.config = new Hashtable();
            }
            this.config.put(s, s2);
        }
    }
    
    public String getConfig(final String s) {
        if (this.config != null) {
            final String value = this.config.get(s);
            if (value instanceof String) {
                return value;
            }
        }
        final String config = JSch.getConfig(s);
        if (config instanceof String) {
            return config;
        }
        return null;
    }
    
    public void setSocketFactory(final SocketFactory socket_factory) {
        this.socket_factory = socket_factory;
    }
    
    public boolean isConnected() {
        return this.isConnected;
    }
    
    public int getTimeout() {
        return this.timeout;
    }
    
    public void setTimeout(final int timeout) throws JSchException {
        if (this.socket != null) {
            try {
                this.socket.setSoTimeout(timeout);
                this.timeout = timeout;
            }
            catch (Exception ex) {
                if (ex instanceof Throwable) {
                    throw new JSchException(ex.toString(), ex);
                }
                throw new JSchException(ex.toString());
            }
            return;
        }
        if (timeout < 0) {
            throw new JSchException("invalid timeout value");
        }
        this.timeout = timeout;
    }
    
    public String getServerVersion() {
        return new String(this.V_S);
    }
    
    public String getClientVersion() {
        return new String(this.V_C);
    }
    
    public void setClientVersion(final String s) {
        this.V_C = s.getBytes();
    }
    
    public void sendIgnore() throws Exception {
        final Buffer buffer = new Buffer();
        final Packet packet = new Packet(buffer);
        packet.reset();
        buffer.putByte((byte)2);
        this.write(packet);
    }
    
    public void sendKeepAliveMsg() throws Exception {
        final Buffer buffer = new Buffer();
        final Packet packet = new Packet(buffer);
        packet.reset();
        buffer.putByte((byte)80);
        buffer.putString(Session.keepalivemsg);
        buffer.putByte((byte)1);
        this.write(packet);
    }
    
    public HostKey getHostKey() {
        return this.hostkey;
    }
    
    public String getHost() {
        return this.host;
    }
    
    public String getUserName() {
        return this.username;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public void setHostKeyAlias(final String hostKeyAlias) {
        this.hostKeyAlias = hostKeyAlias;
    }
    
    public String getHostKeyAlias() {
        return this.hostKeyAlias;
    }
    
    public void setServerAliveInterval(final int n) throws JSchException {
        this.setTimeout(n);
        this.serverAliveInterval = n;
    }
    
    public void setServerAliveCountMax(final int serverAliveCountMax) {
        this.serverAliveCountMax = serverAliveCountMax;
    }
    
    public int getServerAliveInterval() {
        return this.serverAliveInterval;
    }
    
    public int getServerAliveCountMax() {
        return this.serverAliveCountMax;
    }
    
    public void setDaemonThread(final boolean daemon_thread) {
        this.daemon_thread = daemon_thread;
    }
    
    private String[] checkCiphers(final String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        if (JSch.getLogger().isEnabled(1)) {
            JSch.getLogger().log(1, "CheckCiphers: " + s);
        }
        final Vector vector = new Vector<String>();
        final String[] split = Util.split(s, ",");
        for (int i = 0; i < split.length; ++i) {
            if (!checkCipher(this.getConfig(split[i]))) {
                vector.addElement(split[i]);
            }
        }
        if (vector.size() == 0) {
            return null;
        }
        final String[] array = new String[vector.size()];
        System.arraycopy(vector.toArray(), 0, array, 0, vector.size());
        if (JSch.getLogger().isEnabled(1)) {
            for (int j = 0; j < array.length; ++j) {
                JSch.getLogger().log(1, array[j] + " is not available.");
            }
        }
        return array;
    }
    
    static boolean checkCipher(final String s) {
        try {
            final Cipher cipher = (Cipher)Class.forName(s).newInstance();
            cipher.init(0, new byte[cipher.getBlockSize()], new byte[cipher.getIVSize()]);
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    static {
        keepalivemsg = "keepalive@jcraft.com".getBytes();
    }
    
    private class GlobalRequestReply
    {
        private Thread thread;
        private int reply;
        
        private GlobalRequestReply() {
            this.thread = null;
            this.reply = -1;
        }
        
        void setThread(final Thread thread) {
            this.thread = thread;
            this.reply = -1;
        }
        
        Thread getThread() {
            return this.thread;
        }
        
        void setReply(final int reply) {
            this.reply = reply;
        }
        
        int getReply() {
            return this.reply;
        }
    }
}
