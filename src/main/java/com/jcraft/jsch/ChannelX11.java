// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

import java.io.IOException;
import java.net.Socket;
import java.util.Hashtable;

class ChannelX11 extends Channel
{
    private static final int LOCAL_WINDOW_SIZE_MAX = 131072;
    private static final int LOCAL_MAXIMUM_PACKET_SIZE = 16384;
    private static final int TIMEOUT = 10000;
    private static String host;
    private static int port;
    private boolean init;
    static byte[] cookie;
    private static byte[] cookie_hex;
    private static Hashtable faked_cookie_pool;
    private static Hashtable faked_cookie_hex_pool;
    private static byte[] table;
    private Socket socket;
    private byte[] cache;
    
    static int revtable(final byte b) {
        for (int i = 0; i < ChannelX11.table.length; ++i) {
            if (ChannelX11.table[i] == b) {
                return i;
            }
        }
        return 0;
    }
    
    static void setCookie(final String s) {
        ChannelX11.cookie_hex = s.getBytes();
        ChannelX11.cookie = new byte[16];
        for (int i = 0; i < 16; ++i) {
            ChannelX11.cookie[i] = (byte)((revtable(ChannelX11.cookie_hex[i * 2]) << 4 & 0xF0) | (revtable(ChannelX11.cookie_hex[i * 2 + 1]) & 0xF));
        }
    }
    
    static void setHost(final String host) {
        ChannelX11.host = host;
    }
    
    static void setPort(final int port) {
        ChannelX11.port = port;
    }
    
    static byte[] getFakedCookie(final Session session) {
        synchronized (ChannelX11.faked_cookie_hex_pool) {
            byte[] array = ChannelX11.faked_cookie_hex_pool.get(session);
            if (array == null) {
                final Random random = Session.random;
                final byte[] array2 = new byte[16];
                synchronized (random) {
                    random.fill(array2, 0, 16);
                }
                ChannelX11.faked_cookie_pool.put(session, array2);
                final byte[] array3 = new byte[32];
                for (int i = 0; i < 16; ++i) {
                    array3[2 * i] = ChannelX11.table[array2[i] >>> 4 & 0xF];
                    array3[2 * i + 1] = ChannelX11.table[array2[i] & 0xF];
                }
                ChannelX11.faked_cookie_hex_pool.put(session, array3);
                array = array3;
            }
            return array;
        }
    }
    
    ChannelX11() {
        this.init = true;
        this.socket = null;
        this.cache = new byte[0];
        this.setLocalWindowSizeMax(131072);
        this.setLocalWindowSize(131072);
        this.setLocalPacketSize(16384);
        this.type = "x11".getBytes();
        this.connected = true;
    }
    
    public void run() {
        try {
            (this.socket = Util.createSocket(ChannelX11.host, ChannelX11.port, 10000)).setTcpNoDelay(true);
            (this.io = new IO()).setInputStream(this.socket.getInputStream());
            this.io.setOutputStream(this.socket.getOutputStream());
            this.sendOpenConfirmation();
        }
        catch (Exception ex) {
            this.sendOpenFailure(1);
            this.close = true;
            this.disconnect();
            return;
        }
        this.thread = Thread.currentThread();
        final Buffer buffer = new Buffer(this.rmpsize);
        final Packet packet = new Packet(buffer);
        try {
            while (this.thread != null && this.io != null && this.io.in != null) {
                final int read = this.io.in.read(buffer.buffer, 14, buffer.buffer.length - 14 - 32 - 20);
                if (read <= 0) {
                    this.eof();
                    break;
                }
                if (this.close) {
                    break;
                }
                packet.reset();
                buffer.putByte((byte)94);
                buffer.putInt(this.recipient);
                buffer.putInt(read);
                buffer.skip(read);
                this.getSession().write(packet, this, read);
            }
        }
        catch (Exception ex2) {}
        this.disconnect();
    }
    
    private byte[] addCache(final byte[] array, final int n, final int n2) {
        final byte[] cache = new byte[this.cache.length + n2];
        System.arraycopy(array, n, cache, this.cache.length, n2);
        if (this.cache.length > 0) {
            System.arraycopy(this.cache, 0, cache, 0, this.cache.length);
        }
        return this.cache = cache;
    }
    
    void write(byte[] addCache, int n, int length) throws IOException {
        if (!this.init) {
            this.io.put(addCache, n, length);
            return;
        }
        Session session;
        try {
            session = this.getSession();
        }
        catch (JSchException ex) {
            throw new IOException(ex.toString());
        }
        addCache = this.addCache(addCache, n, length);
        n = 0;
        length = addCache.length;
        if (length < 9) {
            return;
        }
        int n2 = (addCache[n + 6] & 0xFF) * 256 + (addCache[n + 7] & 0xFF);
        int n3 = (addCache[n + 8] & 0xFF) * 256 + (addCache[n + 9] & 0xFF);
        if ((addCache[n] & 0xFF) != 0x42) {
            if ((addCache[n] & 0xFF) == 0x6C) {
                n2 = ((n2 >>> 8 & 0xFF) | (n2 << 8 & 0xFF00));
                n3 = ((n3 >>> 8 & 0xFF) | (n3 << 8 & 0xFF00));
            }
        }
        if (length < 12 + n2 + (-n2 & 0x3) + n3) {
            return;
        }
        final byte[] array = new byte[n3];
        System.arraycopy(addCache, n + 12 + n2 + (-n2 & 0x3), array, 0, n3);
        byte[] array2 = null;
        synchronized (ChannelX11.faked_cookie_pool) {
            array2 = ChannelX11.faked_cookie_pool.get(session);
        }
        if (equals(array, array2)) {
            if (ChannelX11.cookie != null) {
                System.arraycopy(ChannelX11.cookie, 0, addCache, n + 12 + n2 + (-n2 & 0x3), n3);
            }
        }
        else {
            this.thread = null;
            this.eof();
            this.io.close();
            this.disconnect();
        }
        this.init = false;
        this.io.put(addCache, n, length);
        this.cache = null;
    }
    
    private static boolean equals(final byte[] array, final byte[] array2) {
        if (array.length != array2.length) {
            return false;
        }
        for (int i = 0; i < array.length; ++i) {
            if (array[i] != array2[i]) {
                return false;
            }
        }
        return true;
    }
    
    static {
        ChannelX11.host = "127.0.0.1";
        ChannelX11.port = 6000;
        ChannelX11.cookie = null;
        ChannelX11.cookie_hex = null;
        ChannelX11.faked_cookie_pool = new Hashtable();
        ChannelX11.faked_cookie_hex_pool = new Hashtable();
        ChannelX11.table = new byte[] { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };
    }
}
