// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

import java.io.PipedOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.OutputStream;
import java.io.InputStream;
import java.util.Vector;

public abstract class Channel implements Runnable
{
    static final int SSH_MSG_CHANNEL_OPEN_CONFIRMATION = 91;
    static final int SSH_MSG_CHANNEL_OPEN_FAILURE = 92;
    static final int SSH_MSG_CHANNEL_WINDOW_ADJUST = 93;
    static final int SSH_OPEN_ADMINISTRATIVELY_PROHIBITED = 1;
    static final int SSH_OPEN_CONNECT_FAILED = 2;
    static final int SSH_OPEN_UNKNOWN_CHANNEL_TYPE = 3;
    static final int SSH_OPEN_RESOURCE_SHORTAGE = 4;
    static int index;
    private static Vector pool;
    int id;
    int recipient;
    byte[] type;
    int lwsize_max;
    int lwsize;
    int lmpsize;
    int rwsize;
    int rmpsize;
    IO io;
    Thread thread;
    boolean eof_local;
    boolean eof_remote;
    boolean close;
    boolean connected;
    int exitstatus;
    int reply;
    int connectTimeout;
    private Session session;
    int notifyme;
    
    static Channel getChannel(final String s) {
        if (s.equals("session")) {
            return new ChannelSession();
        }
        if (s.equals("shell")) {
            return new ChannelShell();
        }
        if (s.equals("exec")) {
            return new ChannelExec();
        }
        if (s.equals("x11")) {
            return new ChannelX11();
        }
        if (s.equals("auth-agent@openssh.com")) {
            return new ChannelAgentForwarding();
        }
        if (s.equals("direct-tcpip")) {
            return new ChannelDirectTCPIP();
        }
        if (s.equals("forwarded-tcpip")) {
            return new ChannelForwardedTCPIP();
        }
        if (s.equals("sftp")) {
            return new ChannelSftp();
        }
        if (s.equals("subsystem")) {
            return new ChannelSubsystem();
        }
        return null;
    }
    
    static Channel getChannel(final int n, final Session session) {
        synchronized (Channel.pool) {
            for (int i = 0; i < Channel.pool.size(); ++i) {
                final Channel channel = Channel.pool.elementAt(i);
                if (channel.id == n && channel.session == session) {
                    return channel;
                }
            }
        }
        return null;
    }
    
    static void del(final Channel channel) {
        synchronized (Channel.pool) {
            Channel.pool.removeElement(channel);
        }
    }
    
    Channel() {
        this.recipient = -1;
        this.type = "foo".getBytes();
        this.lwsize_max = 1048576;
        this.lwsize = this.lwsize_max;
        this.lmpsize = 16384;
        this.rwsize = 0;
        this.rmpsize = 0;
        this.io = null;
        this.thread = null;
        this.eof_local = false;
        this.eof_remote = false;
        this.close = false;
        this.connected = false;
        this.exitstatus = -1;
        this.reply = 0;
        this.connectTimeout = 0;
        this.notifyme = 0;
        synchronized (Channel.pool) {
            this.id = Channel.index++;
            Channel.pool.addElement(this);
        }
    }
    
    void setRecipient(final int recipient) {
        this.recipient = recipient;
    }
    
    int getRecipient() {
        return this.recipient;
    }
    
    void init() throws JSchException {
    }
    
    public void connect() throws JSchException {
        this.connect(0);
    }
    
    public void connect(final int connectTimeout) throws JSchException {
        final Session session = this.getSession();
        if (!session.isConnected()) {
            throw new JSchException("session is down");
        }
        this.connectTimeout = connectTimeout;
        try {
            final Buffer buffer = new Buffer(100);
            final Packet packet = new Packet(buffer);
            packet.reset();
            buffer.putByte((byte)90);
            buffer.putString(this.type);
            buffer.putInt(this.id);
            buffer.putInt(this.lwsize);
            buffer.putInt(this.lmpsize);
            session.write(packet);
            int n = 1000;
            final long currentTimeMillis = System.currentTimeMillis();
            final long n2 = connectTimeout;
            while (this.getRecipient() == -1 && session.isConnected() && n > 0) {
                if (n2 > 0L && System.currentTimeMillis() - currentTimeMillis > n2) {
                    n = 0;
                }
                else {
                    try {
                        Thread.sleep(50L);
                    }
                    catch (Exception ex2) {}
                    --n;
                }
            }
            if (!session.isConnected()) {
                throw new JSchException("session is down");
            }
            if (n == 0) {
                throw new JSchException("channel is not opened.");
            }
            if (this.isClosed()) {
                throw new JSchException("channel is not opened.");
            }
            this.connected = true;
            this.start();
        }
        catch (Exception ex) {
            this.connected = false;
            if (ex instanceof JSchException) {
                throw (JSchException)ex;
            }
            throw new JSchException(ex.toString(), ex);
        }
    }
    
    public void setXForwarding(final boolean b) {
    }
    
    public void start() throws JSchException {
    }
    
    public boolean isEOF() {
        return this.eof_remote;
    }
    
    void getData(final Buffer buffer) {
        this.setRecipient(buffer.getInt());
        this.setRemoteWindowSize(buffer.getInt());
        this.setRemotePacketSize(buffer.getInt());
    }
    
    public void setInputStream(final InputStream \u0131nputStream) {
        this.io.setInputStream(\u0131nputStream, false);
    }
    
    public void setInputStream(final InputStream \u0131nputStream, final boolean b) {
        this.io.setInputStream(\u0131nputStream, b);
    }
    
    public void setOutputStream(final OutputStream outputStream) {
        this.io.setOutputStream(outputStream, false);
    }
    
    public void setOutputStream(final OutputStream outputStream, final boolean b) {
        this.io.setOutputStream(outputStream, b);
    }
    
    public void setExtOutputStream(final OutputStream outputStream) {
        this.io.setExtOutputStream(outputStream, false);
    }
    
    public void setExtOutputStream(final OutputStream outputStream, final boolean b) {
        this.io.setExtOutputStream(outputStream, b);
    }
    
    public InputStream getInputStream() throws IOException {
        final MyPipedInputStream myPipedInputStream = new MyPipedInputStream(32768);
        this.io.setOutputStream(new PassiveOutputStream(myPipedInputStream), false);
        return myPipedInputStream;
    }
    
    public InputStream getExtInputStream() throws IOException {
        final MyPipedInputStream myPipedInputStream = new MyPipedInputStream(32768);
        this.io.setExtOutputStream(new PassiveOutputStream(myPipedInputStream), false);
        return myPipedInputStream;
    }
    
    public OutputStream getOutputStream() throws IOException {
        return new OutputStream() {
            private int dataLen = 0;
            private Buffer buffer = null;
            private Packet packet = null;
            private boolean closed = false;
            byte[] b = new byte[1];
            
            private synchronized void init() throws IOException {
                this.buffer = new Buffer(Channel.this.rmpsize);
                this.packet = new Packet(this.buffer);
                if (this.buffer.buffer.length - 14 - 32 - 20 <= 0) {
                    this.buffer = null;
                    this.packet = null;
                    throw new IOException("failed to initialize the channel.");
                }
            }
            
            public void write(final int n) throws IOException {
                this.b[0] = (byte)n;
                this.write(this.b, 0, 1);
            }
            
            public void write(final byte[] array, int n, int i) throws IOException {
                if (this.packet == null) {
                    this.init();
                }
                if (this.closed) {
                    throw new IOException("Already closed");
                }
                final byte[] buffer = this.buffer.buffer;
                final int length = buffer.length;
                while (i > 0) {
                    int n2;
                    if ((n2 = i) > length - (14 + this.dataLen) - 32 - 20) {
                        n2 = length - (14 + this.dataLen) - 32 - 20;
                    }
                    if (n2 <= 0) {
                        this.flush();
                    }
                    else {
                        System.arraycopy(array, n, buffer, 14 + this.dataLen, n2);
                        this.dataLen += n2;
                        n += n2;
                        i -= n2;
                    }
                }
            }
            
            public void flush() throws IOException {
                if (this.closed) {
                    throw new IOException("Already closed");
                }
                if (this.dataLen == 0) {
                    return;
                }
                this.packet.reset();
                this.buffer.putByte((byte)94);
                this.buffer.putInt(Channel.this.recipient);
                this.buffer.putInt(this.dataLen);
                this.buffer.skip(this.dataLen);
                try {
                    final int dataLen = this.dataLen;
                    this.dataLen = 0;
                    Channel.this.getSession().write(this.packet, Channel.this, dataLen);
                }
                catch (Exception ex) {
                    this.close();
                    throw new IOException(ex.toString());
                }
            }
            
            public void close() throws IOException {
                if (this.packet == null) {
                    try {
                        this.init();
                    }
                    catch (IOException ex) {
                        return;
                    }
                }
                if (this.closed) {
                    return;
                }
                if (this.dataLen > 0) {
                    this.flush();
                }
                Channel.this.eof();
                this.closed = true;
            }
        };
    }
    
    void setLocalWindowSizeMax(final int lwsize_max) {
        this.lwsize_max = lwsize_max;
    }
    
    void setLocalWindowSize(final int lwsize) {
        this.lwsize = lwsize;
    }
    
    void setLocalPacketSize(final int lmpsize) {
        this.lmpsize = lmpsize;
    }
    
    synchronized void setRemoteWindowSize(final int rwsize) {
        this.rwsize = rwsize;
    }
    
    synchronized void addRemoteWindowSize(final int n) {
        this.rwsize += n;
        if (this.notifyme > 0) {
            this.notifyAll();
        }
    }
    
    void setRemotePacketSize(final int rmpsize) {
        this.rmpsize = rmpsize;
    }
    
    public void run() {
    }
    
    void write(final byte[] array) throws IOException {
        this.write(array, 0, array.length);
    }
    
    void write(final byte[] array, final int n, final int n2) throws IOException {
        try {
            this.io.put(array, n, n2);
        }
        catch (NullPointerException ex) {}
    }
    
    void write_ext(final byte[] array, final int n, final int n2) throws IOException {
        try {
            this.io.put_ext(array, n, n2);
        }
        catch (NullPointerException ex) {}
    }
    
    void eof_remote() {
        this.eof_remote = true;
        try {
            this.io.out_close();
        }
        catch (NullPointerException ex) {}
    }
    
    void eof() {
        if (this.close) {
            return;
        }
        if (this.eof_local) {
            return;
        }
        this.eof_local = true;
        try {
            final Buffer buffer = new Buffer(100);
            final Packet packet = new Packet(buffer);
            packet.reset();
            buffer.putByte((byte)96);
            buffer.putInt(this.getRecipient());
            this.getSession().write(packet);
        }
        catch (Exception ex) {}
    }
    
    void close() {
        if (this.close) {
            return;
        }
        this.close = true;
        final boolean b = true;
        this.eof_remote = b;
        this.eof_local = b;
        try {
            final Buffer buffer = new Buffer(100);
            final Packet packet = new Packet(buffer);
            packet.reset();
            buffer.putByte((byte)97);
            buffer.putInt(this.getRecipient());
            this.getSession().write(packet);
        }
        catch (Exception ex) {}
    }
    
    public boolean isClosed() {
        return this.close;
    }
    
    static void disconnect(final Session session) {
        Channel[] array = null;
        int n = 0;
        synchronized (Channel.pool) {
            array = new Channel[Channel.pool.size()];
            for (int i = 0; i < Channel.pool.size(); ++i) {
                try {
                    final Channel channel = Channel.pool.elementAt(i);
                    if (channel.session == session) {
                        array[n++] = channel;
                    }
                }
                catch (Exception ex) {}
            }
        }
        for (int j = 0; j < n; ++j) {
            array[j].disconnect();
        }
    }
    
    public void disconnect() {
        synchronized (this) {
            if (!this.connected) {
                return;
            }
            this.connected = false;
        }
        try {
            this.close();
            final boolean b = true;
            this.eof_local = b;
            this.eof_remote = b;
            this.thread = null;
            try {
                if (this.io != null) {
                    this.io.close();
                }
            }
            catch (Exception ex) {}
        }
        finally {
            del(this);
        }
    }
    
    public boolean isConnected() {
        final Session session = this.session;
        return session != null && session.isConnected() && this.connected;
    }
    
    public void sendSignal(final String signal) throws Exception {
        final RequestSignal requestSignal = new RequestSignal();
        requestSignal.setSignal(signal);
        requestSignal.request(this.getSession(), this);
    }
    
    void setExitStatus(final int exitstatus) {
        this.exitstatus = exitstatus;
    }
    
    public int getExitStatus() {
        return this.exitstatus;
    }
    
    void setSession(final Session session) {
        this.session = session;
    }
    
    public Session getSession() throws JSchException {
        final Session session = this.session;
        if (session == null) {
            throw new JSchException("session is not available");
        }
        return session;
    }
    
    public int getId() {
        return this.id;
    }
    
    protected void sendOpenConfirmation() throws Exception {
        final Buffer buffer = new Buffer(100);
        final Packet packet = new Packet(buffer);
        packet.reset();
        buffer.putByte((byte)91);
        buffer.putInt(this.getRecipient());
        buffer.putInt(this.id);
        buffer.putInt(this.lwsize);
        buffer.putInt(this.lmpsize);
        this.getSession().write(packet);
    }
    
    protected void sendOpenFailure(final int n) {
        try {
            final Buffer buffer = new Buffer(100);
            final Packet packet = new Packet(buffer);
            packet.reset();
            buffer.putByte((byte)92);
            buffer.putInt(this.getRecipient());
            buffer.putInt(n);
            buffer.putString("open failed".getBytes());
            buffer.putString("".getBytes());
            this.getSession().write(packet);
        }
        catch (Exception ex) {}
    }
    
    static {
        Channel.index = 0;
        Channel.pool = new Vector();
    }
    
    class MyPipedInputStream extends PipedInputStream
    {
        MyPipedInputStream() throws IOException {
        }
        
        MyPipedInputStream(final int n) throws IOException {
            this.buffer = new byte[n];
        }
        
        MyPipedInputStream(final PipedOutputStream pipedOutputStream) throws IOException {
            super(pipedOutputStream);
        }
        
        MyPipedInputStream(final PipedOutputStream pipedOutputStream, final int n) throws IOException {
            super(pipedOutputStream);
            this.buffer = new byte[n];
        }
    }
    
    class PassiveInputStream extends MyPipedInputStream
    {
        PipedOutputStream out;
        
        PassiveInputStream(final PipedOutputStream out, final int n) throws IOException {
            super(out, n);
            this.out = out;
        }
        
        PassiveInputStream(final PipedOutputStream out) throws IOException {
            super(out);
            this.out = out;
        }
        
        public void close() throws IOException {
            if (this.out != null) {
                this.out.close();
            }
            this.out = null;
        }
    }
    
    class PassiveOutputStream extends PipedOutputStream
    {
        PassiveOutputStream(final PipedInputStream pipedInputStream) throws IOException {
            super(pipedInputStream);
        }
    }
}
