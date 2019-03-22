// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;
import java.io.FileInputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.io.InputStream;
import java.util.Hashtable;

public class ChannelSftp extends ChannelSession
{
    private static final byte SSH_FXP_INIT = 1;
    private static final byte SSH_FXP_VERSION = 2;
    private static final byte SSH_FXP_OPEN = 3;
    private static final byte SSH_FXP_CLOSE = 4;
    private static final byte SSH_FXP_READ = 5;
    private static final byte SSH_FXP_WRITE = 6;
    private static final byte SSH_FXP_LSTAT = 7;
    private static final byte SSH_FXP_FSTAT = 8;
    private static final byte SSH_FXP_SETSTAT = 9;
    private static final byte SSH_FXP_FSETSTAT = 10;
    private static final byte SSH_FXP_OPENDIR = 11;
    private static final byte SSH_FXP_READDIR = 12;
    private static final byte SSH_FXP_REMOVE = 13;
    private static final byte SSH_FXP_MKDIR = 14;
    private static final byte SSH_FXP_RMDIR = 15;
    private static final byte SSH_FXP_REALPATH = 16;
    private static final byte SSH_FXP_STAT = 17;
    private static final byte SSH_FXP_RENAME = 18;
    private static final byte SSH_FXP_READLINK = 19;
    private static final byte SSH_FXP_SYMLINK = 20;
    private static final byte SSH_FXP_STATUS = 101;
    private static final byte SSH_FXP_HANDLE = 102;
    private static final byte SSH_FXP_DATA = 103;
    private static final byte SSH_FXP_NAME = 104;
    private static final byte SSH_FXP_ATTRS = 105;
    private static final byte SSH_FXP_EXTENDED = -56;
    private static final byte SSH_FXP_EXTENDED_REPLY = -55;
    private static final int SSH_FXF_READ = 1;
    private static final int SSH_FXF_WRITE = 2;
    private static final int SSH_FXF_APPEND = 4;
    private static final int SSH_FXF_CREAT = 8;
    private static final int SSH_FXF_TRUNC = 16;
    private static final int SSH_FXF_EXCL = 32;
    private static final int SSH_FILEXFER_ATTR_SIZE = 1;
    private static final int SSH_FILEXFER_ATTR_UIDGID = 2;
    private static final int SSH_FILEXFER_ATTR_PERMISSIONS = 4;
    private static final int SSH_FILEXFER_ATTR_ACMODTIME = 8;
    private static final int SSH_FILEXFER_ATTR_EXTENDED = Integer.MIN_VALUE;
    public static final int SSH_FX_OK = 0;
    public static final int SSH_FX_EOF = 1;
    public static final int SSH_FX_NO_SUCH_FILE = 2;
    public static final int SSH_FX_PERMISSION_DENIED = 3;
    public static final int SSH_FX_FAILURE = 4;
    public static final int SSH_FX_BAD_MESSAGE = 5;
    public static final int SSH_FX_NO_CONNECTION = 6;
    public static final int SSH_FX_CONNECTION_LOST = 7;
    public static final int SSH_FX_OP_UNSUPPORTED = 8;
    private static final int MAX_MSG_LENGTH = 262144;
    public static final int OVERWRITE = 0;
    public static final int RESUME = 1;
    public static final int APPEND = 2;
    private boolean interactive;
    private int seq;
    private int[] ackid;
    private Buffer buf;
    private Packet packet;
    private int client_version;
    private int server_version;
    private String version;
    private Hashtable extensions;
    private InputStream io_in;
    private static final String file_separator;
    private static final char file_separatorc;
    private static boolean fs_is_bs;
    private String cwd;
    private String home;
    private String lcwd;
    private static final String UTF8 = "UTF-8";
    private String fEncoding;
    private boolean fEncoding_is_utf8;
    
    public ChannelSftp() {
        this.interactive = false;
        this.seq = 1;
        this.ackid = new int[1];
        this.packet = new Packet(this.buf);
        this.client_version = 3;
        this.server_version = 3;
        this.version = String.valueOf(this.client_version);
        this.extensions = null;
        this.io_in = null;
        this.fEncoding = "UTF-8";
        this.fEncoding_is_utf8 = true;
    }
    
    void init() {
    }
    
    public void start() throws JSchException {
        try {
            final PipedOutputStream outputStream = new PipedOutputStream();
            this.io.setOutputStream(outputStream);
            this.io.setInputStream(new MyPipedInputStream(this, outputStream, 32768));
            this.io_in = this.io.in;
            if (this.io_in == null) {
                throw new JSchException("channel is down");
            }
            new RequestSftp().request(this.getSession(), this);
            this.buf = new Buffer(this.rmpsize);
            this.packet = new Packet(this.buf);
            this.sendINIT();
            final Header header = this.header(this.buf, new Header());
            int i = header.length;
            if (i > 262144) {
                throw new SftpException(4, "Received message is too long: " + i);
            }
            final int type = header.type;
            this.server_version = header.rid;
            if (i > 0) {
                this.extensions = new Hashtable();
                this.fill(this.buf, i);
                while (i > 0) {
                    final byte[] string = this.buf.getString();
                    final int n = i - (4 + string.length);
                    final byte[] string2 = this.buf.getString();
                    i = n - (4 + string2.length);
                    this.extensions.put(new String(string), new String(string2));
                }
            }
            this.lcwd = new File(".").getCanonicalPath();
        }
        catch (Exception ex) {
            if (ex instanceof JSchException) {
                throw (JSchException)ex;
            }
            if (ex instanceof Throwable) {
                throw new JSchException(ex.toString(), ex);
            }
            throw new JSchException(ex.toString());
        }
    }
    
    public void quit() {
        this.disconnect();
    }
    
    public void exit() {
        this.disconnect();
    }
    
    public void lcd(String lcwd) throws SftpException {
        lcwd = this.localAbsolutePath(lcwd);
        if (new File(lcwd).isDirectory()) {
            try {
                lcwd = new File(lcwd).getCanonicalPath();
            }
            catch (Exception ex) {}
            this.lcwd = lcwd;
            return;
        }
        throw new SftpException(2, "No such directory");
    }
    
    public void cd(String s) throws SftpException {
        try {
            s = this.remoteAbsolutePath(s);
            s = this.isUnique(s);
            final byte[] realpath = this._realpath(s);
            final SftpATTRS stat = this._stat(realpath);
            if ((stat.getFlags() & 0x4) == 0x0) {
                throw new SftpException(4, "Can't change directory: " + s);
            }
            if (!stat.isDir()) {
                throw new SftpException(4, "Can't change directory: " + s);
            }
            this.setCwd(Util.byte2str(realpath, this.fEncoding));
        }
        catch (Exception ex) {
            if (ex instanceof SftpException) {
                throw (SftpException)ex;
            }
            if (ex instanceof Throwable) {
                throw new SftpException(4, "", ex);
            }
            throw new SftpException(4, "");
        }
    }
    
    public void put(final String s, final String s2) throws SftpException {
        this.put(s, s2, null, 0);
    }
    
    public void put(final String s, final String s2, final int n) throws SftpException {
        this.put(s, s2, null, n);
    }
    
    public void put(final String s, final String s2, final SftpProgressMonitor sftpProgressMonitor) throws SftpException {
        this.put(s, s2, sftpProgressMonitor, 0);
    }
    
    public void put(String localAbsolutePath, String s, final SftpProgressMonitor sftpProgressMonitor, final int n) throws SftpException {
        localAbsolutePath = this.localAbsolutePath(localAbsolutePath);
        s = this.remoteAbsolutePath(s);
        try {
            final Vector glob_remote = this.glob_remote(s);
            final int size = glob_remote.size();
            if (size != 1) {
                if (size == 0) {
                    if (this.isPattern(s)) {
                        throw new SftpException(4, s);
                    }
                    s = Util.unquote(s);
                }
                throw new SftpException(4, glob_remote.toString());
            }
            s = glob_remote.elementAt(0);
            final boolean remoteDir = this.isRemoteDir(s);
            final Vector glob_local = this.glob_local(localAbsolutePath);
            final int size2 = glob_local.size();
            StringBuffer sb = null;
            if (remoteDir) {
                if (!s.endsWith("/")) {
                    s += "/";
                }
                sb = new StringBuffer(s);
            }
            else if (size2 > 1) {
                throw new SftpException(4, "Copying multiple files, but the destination is missing or a file.");
            }
            for (int i = 0; i < size2; ++i) {
                final String s2 = glob_local.elementAt(i);
                String string;
                if (remoteDir) {
                    int lastIndex = s2.lastIndexOf(ChannelSftp.file_separatorc);
                    if (ChannelSftp.fs_is_bs) {
                        final int lastIndex2 = s2.lastIndexOf(47);
                        if (lastIndex2 != -1 && lastIndex2 > lastIndex) {
                            lastIndex = lastIndex2;
                        }
                    }
                    if (lastIndex == -1) {
                        sb.append(s2);
                    }
                    else {
                        sb.append(s2.substring(lastIndex + 1));
                    }
                    string = sb.toString();
                    sb.delete(s.length(), string.length());
                }
                else {
                    string = s;
                }
                long size3 = 0L;
                if (n == 1) {
                    try {
                        size3 = this._stat(string).getSize();
                    }
                    catch (Exception ex2) {}
                    final long length = new File(s2).length();
                    if (length < size3) {
                        throw new SftpException(4, "failed to resume for " + string);
                    }
                    if (length == size3) {
                        return;
                    }
                }
                if (sftpProgressMonitor != null) {
                    sftpProgressMonitor.init(0, s2, string, new File(s2).length());
                    if (n == 1) {
                        sftpProgressMonitor.count(size3);
                    }
                }
                InputStream \u0131nputStream = null;
                try {
                    \u0131nputStream = new FileInputStream(s2);
                    this._put(\u0131nputStream, string, sftpProgressMonitor, n);
                }
                finally {
                    if (\u0131nputStream != null) {
                        ((FileInputStream)\u0131nputStream).close();
                    }
                }
            }
        }
        catch (Exception ex) {
            if (ex instanceof SftpException) {
                throw (SftpException)ex;
            }
            if (ex instanceof Throwable) {
                throw new SftpException(4, ex.toString(), ex);
            }
            throw new SftpException(4, ex.toString());
        }
    }
    
    public void put(final InputStream \u0131nputStream, final String s) throws SftpException {
        this.put(\u0131nputStream, s, null, 0);
    }
    
    public void put(final InputStream \u0131nputStream, final String s, final int n) throws SftpException {
        this.put(\u0131nputStream, s, null, n);
    }
    
    public void put(final InputStream \u0131nputStream, final String s, final SftpProgressMonitor sftpProgressMonitor) throws SftpException {
        this.put(\u0131nputStream, s, sftpProgressMonitor, 0);
    }
    
    public void put(final InputStream \u0131nputStream, String s, final SftpProgressMonitor sftpProgressMonitor, final int n) throws SftpException {
        try {
            s = this.remoteAbsolutePath(s);
            final Vector glob_remote = this.glob_remote(s);
            final int size = glob_remote.size();
            if (size != 1) {
                if (size == 0) {
                    if (this.isPattern(s)) {
                        throw new SftpException(4, s);
                    }
                    s = Util.unquote(s);
                }
                throw new SftpException(4, glob_remote.toString());
            }
            s = glob_remote.elementAt(0);
            if (this.isRemoteDir(s)) {
                throw new SftpException(4, s + " is a directory");
            }
            this._put(\u0131nputStream, s, sftpProgressMonitor, n);
        }
        catch (Exception ex) {
            if (ex instanceof SftpException) {
                throw (SftpException)ex;
            }
            if (ex instanceof Throwable) {
                throw new SftpException(4, ex.toString(), ex);
            }
            throw new SftpException(4, ex.toString());
        }
    }
    
    public void _put(final InputStream \u0131nputStream, final String s, final SftpProgressMonitor sftpProgressMonitor, final int n) throws SftpException {
        try {
            final byte[] str2byte = Util.str2byte(s, this.fEncoding);
            long size = 0L;
            Label_0045: {
                if (n != 1) {
                    if (n != 2) {
                        break Label_0045;
                    }
                }
                try {
                    size = this._stat(str2byte).getSize();
                }
                catch (Exception ex2) {}
            }
            if (n == 1 && size > 0L && \u0131nputStream.skip(size) < size) {
                throw new SftpException(4, "failed to resume for " + s);
            }
            if (n == 0) {
                this.sendOPENW(str2byte);
            }
            else {
                this.sendOPENA(str2byte);
            }
            final Header header = this.header(this.buf, new Header());
            final int length = header.length;
            final int type = header.type;
            this.fill(this.buf, length);
            if (type != 101 && type != 102) {
                throw new SftpException(4, "invalid type=" + type);
            }
            if (type == 101) {
                this.throwStatusError(this.buf, this.buf.getInt());
            }
            final byte[] string = this.buf.getString();
            byte[] buffer = null;
            final boolean b = true;
            if (!b) {
                buffer = new byte[this.buf.buffer.length - (39 + string.length + 32 + 20)];
            }
            long n2 = 0L;
            if (n == 1 || n == 2) {
                n2 += size;
            }
            final int seq = this.seq;
            final int seq2 = this.seq;
            int n3 = 0;
            int n4;
            do {
                int n5 = 0;
                n4 = 0;
                int n6;
                if (!b) {
                    n6 = buffer.length - n5;
                }
                else {
                    buffer = this.buf.buffer;
                    n5 = 39 + string.length;
                    n6 = this.buf.buffer.length - n5 - 32 - 20;
                }
                int read;
                do {
                    read = \u0131nputStream.read(buffer, n5, n6);
                    if (read > 0) {
                        n5 += read;
                        n6 -= read;
                        n4 += read;
                    }
                } while (n6 > 0 && read > 0);
                if (n4 <= 0) {
                    break;
                }
                int i = n4;
                while (i > 0) {
                    i -= this.sendWRITE(string, n2, buffer, 0, i);
                    if (this.seq - 1 != seq) {
                        if (this.io_in.available() < 1024) {
                            continue;
                        }
                    }
                    while (this.io_in.available() > 0 && this.checkStatus(this.ackid, header)) {
                        final int n7 = this.ackid[0];
                        if (seq > n7 || n7 > this.seq - 1) {
                            if (n7 != this.seq) {
                                throw new SftpException(4, "ack error: startid=" + seq + " seq=" + this.seq + " _ackid=" + n7);
                            }
                            System.err.println("ack error: startid=" + seq + " seq=" + this.seq + " _ackid=" + n7);
                        }
                        ++n3;
                    }
                }
                n2 += n4;
            } while (sftpProgressMonitor == null || sftpProgressMonitor.count(n4));
            while (this.seq - seq > n3 && this.checkStatus(null, header)) {
                ++n3;
            }
            if (sftpProgressMonitor != null) {
                sftpProgressMonitor.end();
            }
            this._sendCLOSE(string, header);
        }
        catch (Exception ex) {
            if (ex instanceof SftpException) {
                throw (SftpException)ex;
            }
            if (ex instanceof Throwable) {
                throw new SftpException(4, ex.toString(), ex);
            }
            throw new SftpException(4, ex.toString());
        }
    }
    
    public OutputStream put(final String s) throws SftpException {
        return this.put(s, (SftpProgressMonitor)null, 0);
    }
    
    public OutputStream put(final String s, final int n) throws SftpException {
        return this.put(s, (SftpProgressMonitor)null, n);
    }
    
    public OutputStream put(final String s, final SftpProgressMonitor sftpProgressMonitor, final int n) throws SftpException {
        return this.put(s, sftpProgressMonitor, n, 0L);
    }
    
    public OutputStream put(String s, final SftpProgressMonitor sftpProgressMonitor, final int n, long n2) throws SftpException {
        s = this.remoteAbsolutePath(s);
        try {
            s = this.isUnique(s);
            if (this.isRemoteDir(s)) {
                throw new SftpException(4, s + " is a directory");
            }
            final byte[] str2byte = Util.str2byte(s, this.fEncoding);
            long size = 0L;
            Label_0091: {
                if (n != 1) {
                    if (n != 2) {
                        break Label_0091;
                    }
                }
                try {
                    size = this._stat(str2byte).getSize();
                }
                catch (Exception ex2) {}
            }
            if (n == 0) {
                this.sendOPENW(str2byte);
            }
            else {
                this.sendOPENA(str2byte);
            }
            final Header header = this.header(this.buf, new Header());
            final int length = header.length;
            final int type = header.type;
            this.fill(this.buf, length);
            if (type != 101 && type != 102) {
                throw new SftpException(4, "");
            }
            if (type == 101) {
                this.throwStatusError(this.buf, this.buf.getInt());
            }
            final byte[] string = this.buf.getString();
            if (n == 1 || n == 2) {
                n2 += size;
            }
            return new OutputStream() {
                private boolean init = true;
                private boolean isClosed = false;
                private int[] ackid = new int[1];
                private int startid = 0;
                private int _ackid = 0;
                private int ackcount = 0;
                private int writecount = 0;
                private Header header = new Header();
                byte[] _data = new byte[1];
                private final /* synthetic */ long[] val$_offset = { n2 };
                
                public void write(final byte[] array) throws IOException {
                    this.write(array, 0, array.length);
                }
                
                public void write(final byte[] array, int n, final int n2) throws IOException {
                    if (this.init) {
                        this.startid = ChannelSftp.this.seq;
                        this._ackid = ChannelSftp.this.seq;
                        this.init = false;
                    }
                    if (this.isClosed) {
                        throw new IOException("stream already closed");
                    }
                    try {
                        int i = n2;
                        while (i > 0) {
                            final int access$100 = ChannelSftp.this.sendWRITE(string, this.val$_offset[0], array, n, i);
                            ++this.writecount;
                            final long[] val$_offset = this.val$_offset;
                            final int n3 = 0;
                            val$_offset[n3] += access$100;
                            n += access$100;
                            i -= access$100;
                            if (ChannelSftp.this.seq - 1 != this.startid) {
                                if (ChannelSftp.this.io_in.available() < 1024) {
                                    continue;
                                }
                            }
                            while (ChannelSftp.this.io_in.available() > 0 && ChannelSftp.this.checkStatus(this.ackid, this.header)) {
                                this._ackid = this.ackid[0];
                                if (this.startid > this._ackid || this._ackid > ChannelSftp.this.seq - 1) {
                                    throw new SftpException(4, "");
                                }
                                ++this.ackcount;
                            }
                        }
                        if (sftpProgressMonitor != null && !sftpProgressMonitor.count(n2)) {
                            this.close();
                            throw new IOException("canceled");
                        }
                    }
                    catch (IOException ex) {
                        throw ex;
                    }
                    catch (Exception ex2) {
                        throw new IOException(ex2.toString());
                    }
                }
                
                public void write(final int n) throws IOException {
                    this._data[0] = (byte)n;
                    this.write(this._data, 0, 1);
                }
                
                public void flush() throws IOException {
                    if (this.isClosed) {
                        throw new IOException("stream already closed");
                    }
                    if (!this.init) {
                        try {
                            while (this.writecount > this.ackcount && ChannelSftp.this.checkStatus(null, this.header)) {
                                ++this.ackcount;
                            }
                        }
                        catch (SftpException ex) {
                            throw new IOException(ex.toString());
                        }
                    }
                }
                
                public void close() throws IOException {
                    if (this.isClosed) {
                        return;
                    }
                    this.flush();
                    if (sftpProgressMonitor != null) {
                        sftpProgressMonitor.end();
                    }
                    try {
                        ChannelSftp.this._sendCLOSE(string, this.header);
                    }
                    catch (IOException ex) {
                        throw ex;
                    }
                    catch (Exception ex2) {
                        throw new IOException(ex2.toString());
                    }
                    this.isClosed = true;
                }
            };
        }
        catch (Exception ex) {
            if (ex instanceof SftpException) {
                throw (SftpException)ex;
            }
            if (ex instanceof Throwable) {
                throw new SftpException(4, "", ex);
            }
            throw new SftpException(4, "");
        }
    }
    
    public void get(final String s, final String s2) throws SftpException {
        this.get(s, s2, null, 0);
    }
    
    public void get(final String s, final String s2, final SftpProgressMonitor sftpProgressMonitor) throws SftpException {
        this.get(s, s2, sftpProgressMonitor, 0);
    }
    
    public void get(String remoteAbsolutePath, String s, final SftpProgressMonitor sftpProgressMonitor, final int n) throws SftpException {
        remoteAbsolutePath = this.remoteAbsolutePath(remoteAbsolutePath);
        s = this.localAbsolutePath(s);
        try {
            final Vector glob_remote = this.glob_remote(remoteAbsolutePath);
            final int size = glob_remote.size();
            if (size == 0) {
                throw new SftpException(2, "No such file");
            }
            final boolean directory = new File(s).isDirectory();
            StringBuffer sb = null;
            if (directory) {
                if (!s.endsWith(ChannelSftp.file_separator)) {
                    s += ChannelSftp.file_separator;
                }
                sb = new StringBuffer(s);
            }
            else if (size > 1) {
                throw new SftpException(4, "Copying multiple files, but destination is missing or a file.");
            }
            for (int i = 0; i < size; ++i) {
                final String s2 = glob_remote.elementAt(i);
                final SftpATTRS stat = this._stat(s2);
                if (stat.isDir()) {
                    throw new SftpException(4, "not supported to get directory " + s2);
                }
                String string;
                if (directory) {
                    final int lastIndex = s2.lastIndexOf(47);
                    if (lastIndex == -1) {
                        sb.append(s2);
                    }
                    else {
                        sb.append(s2.substring(lastIndex + 1));
                    }
                    string = sb.toString();
                    sb.delete(s.length(), string.length());
                }
                else {
                    string = s;
                }
                if (n == 1) {
                    final long size2 = stat.getSize();
                    final long length = new File(string).length();
                    if (length > size2) {
                        throw new SftpException(4, "failed to resume for " + string);
                    }
                    if (length == size2) {
                        return;
                    }
                }
                if (sftpProgressMonitor != null) {
                    sftpProgressMonitor.init(1, s2, string, stat.getSize());
                    if (n == 1) {
                        sftpProgressMonitor.count(new File(string).length());
                    }
                }
                OutputStream outputStream = null;
                try {
                    if (n == 0) {
                        outputStream = new FileOutputStream(string);
                    }
                    else {
                        outputStream = new FileOutputStream(string, true);
                    }
                    this._get(s2, outputStream, sftpProgressMonitor, n, new File(string).length());
                }
                finally {
                    if (outputStream != null) {
                        ((FileOutputStream)outputStream).close();
                    }
                }
            }
        }
        catch (Exception ex) {
            if (ex instanceof SftpException) {
                throw (SftpException)ex;
            }
            if (ex instanceof Throwable) {
                throw new SftpException(4, "", ex);
            }
            throw new SftpException(4, "");
        }
    }
    
    public void get(final String s, final OutputStream outputStream) throws SftpException {
        this.get(s, outputStream, null, 0, 0L);
    }
    
    public void get(final String s, final OutputStream outputStream, final SftpProgressMonitor sftpProgressMonitor) throws SftpException {
        this.get(s, outputStream, sftpProgressMonitor, 0, 0L);
    }
    
    public void get(String s, final OutputStream outputStream, final SftpProgressMonitor sftpProgressMonitor, final int n, final long n2) throws SftpException {
        try {
            s = this.remoteAbsolutePath(s);
            s = this.isUnique(s);
            if (sftpProgressMonitor != null) {
                sftpProgressMonitor.init(1, s, "??", this._stat(s).getSize());
                if (n == 1) {
                    sftpProgressMonitor.count(n2);
                }
            }
            this._get(s, outputStream, sftpProgressMonitor, n, n2);
        }
        catch (Exception ex) {
            if (ex instanceof SftpException) {
                throw (SftpException)ex;
            }
            if (ex instanceof Throwable) {
                throw new SftpException(4, "", ex);
            }
            throw new SftpException(4, "");
        }
    }
    
    private void _get(final String s, final OutputStream outputStream, final SftpProgressMonitor sftpProgressMonitor, final int n, final long n2) throws SftpException {
        final byte[] str2byte = Util.str2byte(s, this.fEncoding);
        try {
            this.sendOPENR(str2byte);
            Header header = this.header(this.buf, new Header());
            final int length = header.length;
            final int type = header.type;
            this.fill(this.buf, length);
            if (type != 101 && type != 102) {
                throw new SftpException(4, "");
            }
            if (type == 101) {
                this.throwStatusError(this.buf, this.buf.getInt());
            }
            final byte[] string = this.buf.getString();
            long n3 = 0L;
            if (n == 1) {
                n3 += n2;
            }
        Label_0480:
            while (true) {
                int n4 = this.buf.buffer.length - 13;
                if (this.server_version == 0) {
                    n4 = 1024;
                }
                this.sendREAD(string, n3, n4);
                header = this.header(this.buf, header);
                int length2 = header.length;
                final int type2 = header.type;
                if (type2 == 101) {
                    this.fill(this.buf, length2);
                    final int \u0131nt = this.buf.getInt();
                    if (\u0131nt == 1) {
                        break;
                    }
                    this.throwStatusError(this.buf, \u0131nt);
                }
                if (type2 != 103) {
                    break;
                }
                this.buf.rewind();
                this.fill(this.buf.buffer, 0, 4);
                length2 -= 4;
                int i = this.buf.getInt();
                while (i > 0) {
                    int length3 = i;
                    if (length3 > this.buf.buffer.length) {
                        length3 = this.buf.buffer.length;
                    }
                    final int read = this.io_in.read(this.buf.buffer, 0, length3);
                    if (read < 0) {
                        break Label_0480;
                    }
                    final int n5 = read;
                    outputStream.write(this.buf.buffer, 0, n5);
                    n3 += n5;
                    i -= n5;
                    if (sftpProgressMonitor != null && !sftpProgressMonitor.count(n5)) {
                        while (i > 0) {
                            final int read2 = this.io_in.read(this.buf.buffer, 0, (this.buf.buffer.length < i) ? this.buf.buffer.length : i);
                            if (read2 <= 0) {
                                break;
                            }
                            i -= read2;
                        }
                        break Label_0480;
                    }
                }
            }
            outputStream.flush();
            if (sftpProgressMonitor != null) {
                sftpProgressMonitor.end();
            }
            this._sendCLOSE(string, header);
        }
        catch (Exception ex) {
            if (ex instanceof SftpException) {
                throw (SftpException)ex;
            }
            if (ex instanceof Throwable) {
                throw new SftpException(4, "", ex);
            }
            throw new SftpException(4, "");
        }
    }
    
    public InputStream get(final String s) throws SftpException {
        return this.get(s, null, 0L);
    }
    
    public InputStream get(final String s, final SftpProgressMonitor sftpProgressMonitor) throws SftpException {
        return this.get(s, sftpProgressMonitor, 0L);
    }
    
    public InputStream get(final String s, final int n) throws SftpException {
        return this.get(s, null, 0L);
    }
    
    public InputStream get(final String s, final SftpProgressMonitor sftpProgressMonitor, final int n) throws SftpException {
        return this.get(s, sftpProgressMonitor, 0L);
    }
    
    public InputStream get(String s, final SftpProgressMonitor sftpProgressMonitor, final long n) throws SftpException {
        s = this.remoteAbsolutePath(s);
        try {
            s = this.isUnique(s);
            final byte[] str2byte = Util.str2byte(s, this.fEncoding);
            final SftpATTRS stat = this._stat(str2byte);
            if (sftpProgressMonitor != null) {
                sftpProgressMonitor.init(1, s, "??", stat.getSize());
            }
            this.sendOPENR(str2byte);
            final Header header = this.header(this.buf, new Header());
            final int length = header.length;
            final int type = header.type;
            this.fill(this.buf, length);
            if (type != 101 && type != 102) {
                throw new SftpException(4, "");
            }
            if (type == 101) {
                this.throwStatusError(this.buf, this.buf.getInt());
            }
            return new InputStream() {
                long offset = n;
                boolean closed = false;
                int rest_length = 0;
                byte[] _data = new byte[1];
                byte[] rest_byte = new byte[1024];
                Header header = new Header();
                private final /* synthetic */ byte[] val$handle = ChannelSftp.this.buf.getString();
                
                public int read() throws IOException {
                    if (this.closed) {
                        return -1;
                    }
                    if (this.read(this._data, 0, 1) == -1) {
                        return -1;
                    }
                    return this._data[0] & 0xFF;
                }
                
                public int read(final byte[] array) throws IOException {
                    if (this.closed) {
                        return -1;
                    }
                    return this.read(array, 0, array.length);
                }
                
                public int read(final byte[] array, final int n, int n2) throws IOException {
                    if (this.closed) {
                        return -1;
                    }
                    if (array == null) {
                        throw new NullPointerException();
                    }
                    if (n < 0 || n2 < 0 || n + n2 > array.length) {
                        throw new IndexOutOfBoundsException();
                    }
                    if (n2 == 0) {
                        return 0;
                    }
                    if (this.rest_length > 0) {
                        int rest_length = this.rest_length;
                        if (rest_length > n2) {
                            rest_length = n2;
                        }
                        System.arraycopy(this.rest_byte, 0, array, n, rest_length);
                        if (rest_length != this.rest_length) {
                            System.arraycopy(this.rest_byte, rest_length, this.rest_byte, 0, this.rest_length - rest_length);
                        }
                        if (sftpProgressMonitor != null && !sftpProgressMonitor.count(rest_length)) {
                            this.close();
                            return -1;
                        }
                        this.rest_length -= rest_length;
                        return rest_length;
                    }
                    else {
                        if (ChannelSftp.this.buf.buffer.length - 13 < n2) {
                            n2 = ChannelSftp.this.buf.buffer.length - 13;
                        }
                        if (ChannelSftp.this.server_version == 0 && n2 > 1024) {
                            n2 = 1024;
                        }
                        try {
                            ChannelSftp.this.sendREAD(this.val$handle, this.offset, n2);
                        }
                        catch (Exception ex) {
                            throw new IOException("error");
                        }
                        this.header = ChannelSftp.this.header(ChannelSftp.this.buf, this.header);
                        this.rest_length = this.header.length;
                        final int type = this.header.type;
                        final int rid = this.header.rid;
                        if (type != 101 && type != 103) {
                            throw new IOException("error");
                        }
                        if (type == 101) {
                            ChannelSftp.this.fill(ChannelSftp.this.buf, this.rest_length);
                            final int \u0131nt = ChannelSftp.this.buf.getInt();
                            this.rest_length = 0;
                            if (\u0131nt == 1) {
                                this.close();
                                return -1;
                            }
                            throw new IOException("error");
                        }
                        else {
                            ChannelSftp.this.buf.rewind();
                            ChannelSftp.this.fill(ChannelSftp.this.buf.buffer, 0, 4);
                            final int \u0131nt2 = ChannelSftp.this.buf.getInt();
                            this.rest_length -= 4;
                            this.offset += this.rest_length;
                            if (\u0131nt2 <= 0) {
                                return 0;
                            }
                            int rest_length2 = this.rest_length;
                            if (rest_length2 > n2) {
                                rest_length2 = n2;
                            }
                            final int read = ChannelSftp.this.io_in.read(array, n, rest_length2);
                            if (read < 0) {
                                return -1;
                            }
                            this.rest_length -= read;
                            if (this.rest_length > 0) {
                                if (this.rest_byte.length < this.rest_length) {
                                    this.rest_byte = new byte[this.rest_length];
                                }
                                int n3 = 0;
                                int read2;
                                for (int i = this.rest_length; i > 0; i -= read2) {
                                    read2 = ChannelSftp.this.io_in.read(this.rest_byte, n3, i);
                                    if (read2 <= 0) {
                                        break;
                                    }
                                    n3 += read2;
                                }
                            }
                            if (sftpProgressMonitor != null && !sftpProgressMonitor.count(read)) {
                                this.close();
                                return -1;
                            }
                            return read;
                        }
                    }
                }
                
                public void close() throws IOException {
                    if (this.closed) {
                        return;
                    }
                    this.closed = true;
                    if (sftpProgressMonitor != null) {
                        sftpProgressMonitor.end();
                    }
                    try {
                        ChannelSftp.this._sendCLOSE(this.val$handle, this.header);
                    }
                    catch (Exception ex) {
                        throw new IOException("error");
                    }
                }
            };
        }
        catch (Exception ex) {
            if (ex instanceof SftpException) {
                throw (SftpException)ex;
            }
            if (ex instanceof Throwable) {
                throw new SftpException(4, "", ex);
            }
            throw new SftpException(4, "");
        }
    }
    
    public Vector ls(String remoteAbsolutePath) throws SftpException {
        try {
            remoteAbsolutePath = this.remoteAbsolutePath(remoteAbsolutePath);
            final Vector<LsEntry> vector = new Vector<LsEntry>();
            final int lastIndex = remoteAbsolutePath.lastIndexOf(47);
            final String substring = remoteAbsolutePath.substring(0, (lastIndex == 0) ? 1 : lastIndex);
            final String substring2 = remoteAbsolutePath.substring(lastIndex + 1);
            String unquote = Util.unquote(substring);
            final byte[][] array = { null };
            final boolean pattern = this.isPattern(substring2, array);
            byte[] array2;
            if (pattern) {
                array2 = array[0];
            }
            else {
                final String unquote2 = Util.unquote(remoteAbsolutePath);
                if (this._stat(unquote2).isDir()) {
                    array2 = null;
                    unquote = unquote2;
                }
                else if (this.fEncoding_is_utf8) {
                    array2 = Util.unquote(array[0]);
                }
                else {
                    array2 = Util.str2byte(Util.unquote(substring2), this.fEncoding);
                }
            }
            this.sendOPENDIR(Util.str2byte(unquote, this.fEncoding));
            Header header = this.header(this.buf, new Header());
            final int length = header.length;
            final int type = header.type;
            this.fill(this.buf, length);
            if (type != 101 && type != 102) {
                throw new SftpException(4, "");
            }
            if (type == 101) {
                this.throwStatusError(this.buf, this.buf.getInt());
            }
            final byte[] string = this.buf.getString();
            while (true) {
                this.sendREADDIR(string);
                header = this.header(this.buf, header);
                int length2 = header.length;
                final int type2 = header.type;
                if (type2 != 101 && type2 != 104) {
                    throw new SftpException(4, "");
                }
                if (type2 == 101) {
                    this.fill(this.buf, length2);
                    final int \u0131nt = this.buf.getInt();
                    if (\u0131nt == 1) {
                        this._sendCLOSE(string, header);
                        return vector;
                    }
                    this.throwStatusError(this.buf, \u0131nt);
                }
                this.buf.rewind();
                this.fill(this.buf.buffer, 0, 4);
                length2 -= 4;
                int i = this.buf.getInt();
                this.buf.reset();
                while (i > 0) {
                    if (length2 > 0) {
                        this.buf.shift();
                        final int fill = this.fill(this.buf.buffer, this.buf.index, (this.buf.buffer.length > this.buf.index + length2) ? length2 : (this.buf.buffer.length - this.buf.index));
                        final Buffer buf = this.buf;
                        buf.index += fill;
                        length2 -= fill;
                    }
                    final byte[] string2 = this.buf.getString();
                    byte[] string3 = null;
                    if (this.server_version <= 3) {
                        string3 = this.buf.getString();
                    }
                    final SftpATTRS attr = SftpATTRS.getATTR(this.buf);
                    String s = null;
                    boolean b;
                    if (array2 == null) {
                        b = true;
                    }
                    else if (!pattern) {
                        b = Util.array_equals(array2, string2);
                    }
                    else {
                        byte[] str2byte = string2;
                        if (!this.fEncoding_is_utf8) {
                            s = Util.byte2str(str2byte, this.fEncoding);
                            str2byte = Util.str2byte(s, "UTF-8");
                        }
                        b = Util.glob(array2, str2byte);
                    }
                    if (b) {
                        if (s == null) {
                            s = Util.byte2str(string2, this.fEncoding);
                        }
                        String s2;
                        if (string3 == null) {
                            s2 = attr.toString() + " " + s;
                        }
                        else {
                            s2 = Util.byte2str(string3, this.fEncoding);
                        }
                        vector.addElement(new LsEntry(s, s2, attr));
                    }
                    --i;
                }
            }
        }
        catch (Exception ex) {
            if (ex instanceof SftpException) {
                throw (SftpException)ex;
            }
            if (ex instanceof Throwable) {
                throw new SftpException(4, "", ex);
            }
            throw new SftpException(4, "");
        }
    }
    
    public String readlink(String s) throws SftpException {
        try {
            if (this.server_version < 3) {
                throw new SftpException(8, "The remote sshd is too old to support symlink operation.");
            }
            s = this.remoteAbsolutePath(s);
            s = this.isUnique(s);
            this.sendREADLINK(Util.str2byte(s, this.fEncoding));
            final Header header = this.header(this.buf, new Header());
            final int length = header.length;
            final int type = header.type;
            this.fill(this.buf, length);
            if (type != 101 && type != 104) {
                throw new SftpException(4, "");
            }
            if (type == 104) {
                final int \u0131nt = this.buf.getInt();
                byte[] string = null;
                for (int i = 0; i < \u0131nt; ++i) {
                    string = this.buf.getString();
                    if (this.server_version <= 3) {
                        this.buf.getString();
                    }
                    SftpATTRS.getATTR(this.buf);
                }
                return Util.byte2str(string, this.fEncoding);
            }
            this.throwStatusError(this.buf, this.buf.getInt());
        }
        catch (Exception ex) {
            if (ex instanceof SftpException) {
                throw (SftpException)ex;
            }
            if (ex instanceof Throwable) {
                throw new SftpException(4, "", ex);
            }
            throw new SftpException(4, "");
        }
        return null;
    }
    
    public void symlink(String s, String s2) throws SftpException {
        if (this.server_version < 3) {
            throw new SftpException(8, "The remote sshd is too old to support symlink operation.");
        }
        try {
            s = this.remoteAbsolutePath(s);
            s2 = this.remoteAbsolutePath(s2);
            s = this.isUnique(s);
            if (this.isPattern(s2)) {
                throw new SftpException(4, s2);
            }
            s2 = Util.unquote(s2);
            this.sendSYMLINK(Util.str2byte(s, this.fEncoding), Util.str2byte(s2, this.fEncoding));
            final Header header = this.header(this.buf, new Header());
            final int length = header.length;
            final int type = header.type;
            this.fill(this.buf, length);
            if (type != 101) {
                throw new SftpException(4, "");
            }
            final int \u0131nt = this.buf.getInt();
            if (\u0131nt == 0) {
                return;
            }
            this.throwStatusError(this.buf, \u0131nt);
        }
        catch (Exception ex) {
            if (ex instanceof SftpException) {
                throw (SftpException)ex;
            }
            if (ex instanceof Throwable) {
                throw new SftpException(4, "", ex);
            }
            throw new SftpException(4, "");
        }
    }
    
    public void rename(String s, String s2) throws SftpException {
        if (this.server_version < 2) {
            throw new SftpException(8, "The remote sshd is too old to support rename operation.");
        }
        try {
            s = this.remoteAbsolutePath(s);
            s2 = this.remoteAbsolutePath(s2);
            s = this.isUnique(s);
            final Vector glob_remote = this.glob_remote(s2);
            final int size = glob_remote.size();
            if (size >= 2) {
                throw new SftpException(4, glob_remote.toString());
            }
            if (size == 1) {
                s2 = glob_remote.elementAt(0);
            }
            else {
                if (this.isPattern(s2)) {
                    throw new SftpException(4, s2);
                }
                s2 = Util.unquote(s2);
            }
            this.sendRENAME(Util.str2byte(s, this.fEncoding), Util.str2byte(s2, this.fEncoding));
            final Header header = this.header(this.buf, new Header());
            final int length = header.length;
            final int type = header.type;
            this.fill(this.buf, length);
            if (type != 101) {
                throw new SftpException(4, "");
            }
            final int \u0131nt = this.buf.getInt();
            if (\u0131nt == 0) {
                return;
            }
            this.throwStatusError(this.buf, \u0131nt);
        }
        catch (Exception ex) {
            if (ex instanceof SftpException) {
                throw (SftpException)ex;
            }
            if (ex instanceof Throwable) {
                throw new SftpException(4, "", ex);
            }
            throw new SftpException(4, "");
        }
    }
    
    public void rm(String remoteAbsolutePath) throws SftpException {
        try {
            remoteAbsolutePath = this.remoteAbsolutePath(remoteAbsolutePath);
            final Vector glob_remote = this.glob_remote(remoteAbsolutePath);
            final int size = glob_remote.size();
            Header header = new Header();
            for (int i = 0; i < size; ++i) {
                remoteAbsolutePath = glob_remote.elementAt(i);
                this.sendREMOVE(Util.str2byte(remoteAbsolutePath, this.fEncoding));
                header = this.header(this.buf, header);
                final int length = header.length;
                final int type = header.type;
                this.fill(this.buf, length);
                if (type != 101) {
                    throw new SftpException(4, "");
                }
                final int \u0131nt = this.buf.getInt();
                if (\u0131nt != 0) {
                    this.throwStatusError(this.buf, \u0131nt);
                }
            }
        }
        catch (Exception ex) {
            if (ex instanceof SftpException) {
                throw (SftpException)ex;
            }
            if (ex instanceof Throwable) {
                throw new SftpException(4, "", ex);
            }
            throw new SftpException(4, "");
        }
    }
    
    private boolean isRemoteDir(final String s) {
        try {
            this.sendSTAT(Util.str2byte(s, this.fEncoding));
            final Header header = this.header(this.buf, new Header());
            final int length = header.length;
            final int type = header.type;
            this.fill(this.buf, length);
            return type == 105 && SftpATTRS.getATTR(this.buf).isDir();
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    public void chgrp(final int n, String remoteAbsolutePath) throws SftpException {
        try {
            remoteAbsolutePath = this.remoteAbsolutePath(remoteAbsolutePath);
            final Vector glob_remote = this.glob_remote(remoteAbsolutePath);
            for (int size = glob_remote.size(), i = 0; i < size; ++i) {
                remoteAbsolutePath = glob_remote.elementAt(i);
                final SftpATTRS stat = this._stat(remoteAbsolutePath);
                stat.setFLAGS(0);
                stat.setUIDGID(stat.uid, n);
                this._setStat(remoteAbsolutePath, stat);
            }
        }
        catch (Exception ex) {
            if (ex instanceof SftpException) {
                throw (SftpException)ex;
            }
            if (ex instanceof Throwable) {
                throw new SftpException(4, "", ex);
            }
            throw new SftpException(4, "");
        }
    }
    
    public void chown(final int n, String remoteAbsolutePath) throws SftpException {
        try {
            remoteAbsolutePath = this.remoteAbsolutePath(remoteAbsolutePath);
            final Vector glob_remote = this.glob_remote(remoteAbsolutePath);
            for (int size = glob_remote.size(), i = 0; i < size; ++i) {
                remoteAbsolutePath = glob_remote.elementAt(i);
                final SftpATTRS stat = this._stat(remoteAbsolutePath);
                stat.setFLAGS(0);
                stat.setUIDGID(n, stat.gid);
                this._setStat(remoteAbsolutePath, stat);
            }
        }
        catch (Exception ex) {
            if (ex instanceof SftpException) {
                throw (SftpException)ex;
            }
            if (ex instanceof Throwable) {
                throw new SftpException(4, "", ex);
            }
            throw new SftpException(4, "");
        }
    }
    
    public void chmod(final int perm\u0131ss\u0131ons, String remoteAbsolutePath) throws SftpException {
        try {
            remoteAbsolutePath = this.remoteAbsolutePath(remoteAbsolutePath);
            final Vector glob_remote = this.glob_remote(remoteAbsolutePath);
            for (int size = glob_remote.size(), i = 0; i < size; ++i) {
                remoteAbsolutePath = glob_remote.elementAt(i);
                final SftpATTRS stat = this._stat(remoteAbsolutePath);
                stat.setFLAGS(0);
                stat.setPERMISSIONS(perm\u0131ss\u0131ons);
                this._setStat(remoteAbsolutePath, stat);
            }
        }
        catch (Exception ex) {
            if (ex instanceof SftpException) {
                throw (SftpException)ex;
            }
            if (ex instanceof Throwable) {
                throw new SftpException(4, "", ex);
            }
            throw new SftpException(4, "");
        }
    }
    
    public void setMtime(String remoteAbsolutePath, final int n) throws SftpException {
        try {
            remoteAbsolutePath = this.remoteAbsolutePath(remoteAbsolutePath);
            final Vector glob_remote = this.glob_remote(remoteAbsolutePath);
            for (int size = glob_remote.size(), i = 0; i < size; ++i) {
                remoteAbsolutePath = glob_remote.elementAt(i);
                final SftpATTRS stat = this._stat(remoteAbsolutePath);
                stat.setFLAGS(0);
                stat.setACMODTIME(stat.getATime(), n);
                this._setStat(remoteAbsolutePath, stat);
            }
        }
        catch (Exception ex) {
            if (ex instanceof SftpException) {
                throw (SftpException)ex;
            }
            if (ex instanceof Throwable) {
                throw new SftpException(4, "", ex);
            }
            throw new SftpException(4, "");
        }
    }
    
    public void rmdir(String remoteAbsolutePath) throws SftpException {
        try {
            remoteAbsolutePath = this.remoteAbsolutePath(remoteAbsolutePath);
            final Vector glob_remote = this.glob_remote(remoteAbsolutePath);
            final int size = glob_remote.size();
            Header header = new Header();
            for (int i = 0; i < size; ++i) {
                remoteAbsolutePath = glob_remote.elementAt(i);
                this.sendRMDIR(Util.str2byte(remoteAbsolutePath, this.fEncoding));
                header = this.header(this.buf, header);
                final int length = header.length;
                final int type = header.type;
                this.fill(this.buf, length);
                if (type != 101) {
                    throw new SftpException(4, "");
                }
                final int \u0131nt = this.buf.getInt();
                if (\u0131nt != 0) {
                    this.throwStatusError(this.buf, \u0131nt);
                }
            }
        }
        catch (Exception ex) {
            if (ex instanceof SftpException) {
                throw (SftpException)ex;
            }
            if (ex instanceof Throwable) {
                throw new SftpException(4, "", ex);
            }
            throw new SftpException(4, "");
        }
    }
    
    public void mkdir(String remoteAbsolutePath) throws SftpException {
        try {
            remoteAbsolutePath = this.remoteAbsolutePath(remoteAbsolutePath);
            this.sendMKDIR(Util.str2byte(remoteAbsolutePath, this.fEncoding), null);
            final Header header = this.header(this.buf, new Header());
            final int length = header.length;
            final int type = header.type;
            this.fill(this.buf, length);
            if (type != 101) {
                throw new SftpException(4, "");
            }
            final int \u0131nt = this.buf.getInt();
            if (\u0131nt == 0) {
                return;
            }
            this.throwStatusError(this.buf, \u0131nt);
        }
        catch (Exception ex) {
            if (ex instanceof SftpException) {
                throw (SftpException)ex;
            }
            if (ex instanceof Throwable) {
                throw new SftpException(4, "", ex);
            }
            throw new SftpException(4, "");
        }
    }
    
    public SftpATTRS stat(String s) throws SftpException {
        try {
            s = this.remoteAbsolutePath(s);
            s = this.isUnique(s);
            return this._stat(s);
        }
        catch (Exception ex) {
            if (ex instanceof SftpException) {
                throw (SftpException)ex;
            }
            if (ex instanceof Throwable) {
                throw new SftpException(4, "", ex);
            }
            throw new SftpException(4, "");
        }
    }
    
    private SftpATTRS _stat(final byte[] array) throws SftpException {
        try {
            this.sendSTAT(array);
            final Header header = this.header(this.buf, new Header());
            final int length = header.length;
            final int type = header.type;
            this.fill(this.buf, length);
            if (type != 105) {
                if (type == 101) {
                    this.throwStatusError(this.buf, this.buf.getInt());
                }
                throw new SftpException(4, "");
            }
            return SftpATTRS.getATTR(this.buf);
        }
        catch (Exception ex) {
            if (ex instanceof SftpException) {
                throw (SftpException)ex;
            }
            if (ex instanceof Throwable) {
                throw new SftpException(4, "", ex);
            }
            throw new SftpException(4, "");
        }
    }
    
    private SftpATTRS _stat(final String s) throws SftpException {
        return this._stat(Util.str2byte(s, this.fEncoding));
    }
    
    public SftpATTRS lstat(String s) throws SftpException {
        try {
            s = this.remoteAbsolutePath(s);
            s = this.isUnique(s);
            return this._lstat(s);
        }
        catch (Exception ex) {
            if (ex instanceof SftpException) {
                throw (SftpException)ex;
            }
            if (ex instanceof Throwable) {
                throw new SftpException(4, "", ex);
            }
            throw new SftpException(4, "");
        }
    }
    
    private SftpATTRS _lstat(final String s) throws SftpException {
        try {
            this.sendLSTAT(Util.str2byte(s, this.fEncoding));
            final Header header = this.header(this.buf, new Header());
            final int length = header.length;
            final int type = header.type;
            this.fill(this.buf, length);
            if (type != 105) {
                if (type == 101) {
                    this.throwStatusError(this.buf, this.buf.getInt());
                }
                throw new SftpException(4, "");
            }
            return SftpATTRS.getATTR(this.buf);
        }
        catch (Exception ex) {
            if (ex instanceof SftpException) {
                throw (SftpException)ex;
            }
            if (ex instanceof Throwable) {
                throw new SftpException(4, "", ex);
            }
            throw new SftpException(4, "");
        }
    }
    
    private byte[] _realpath(final String s) throws SftpException, IOException, Exception {
        this.sendREALPATH(Util.str2byte(s, this.fEncoding));
        final Header header = this.header(this.buf, new Header());
        final int length = header.length;
        final int type = header.type;
        this.fill(this.buf, length);
        if (type != 101 && type != 104) {
            throw new SftpException(4, "");
        }
        if (type == 101) {
            this.throwStatusError(this.buf, this.buf.getInt());
        }
        int \u0131nt = this.buf.getInt();
        byte[] string = null;
        while (\u0131nt-- > 0) {
            string = this.buf.getString();
            if (this.server_version <= 3) {
                this.buf.getString();
            }
            SftpATTRS.getATTR(this.buf);
        }
        return string;
    }
    
    public void setStat(String remoteAbsolutePath, final SftpATTRS sftpATTRS) throws SftpException {
        try {
            remoteAbsolutePath = this.remoteAbsolutePath(remoteAbsolutePath);
            final Vector glob_remote = this.glob_remote(remoteAbsolutePath);
            for (int size = glob_remote.size(), i = 0; i < size; ++i) {
                remoteAbsolutePath = glob_remote.elementAt(i);
                this._setStat(remoteAbsolutePath, sftpATTRS);
            }
        }
        catch (Exception ex) {
            if (ex instanceof SftpException) {
                throw (SftpException)ex;
            }
            if (ex instanceof Throwable) {
                throw new SftpException(4, "", ex);
            }
            throw new SftpException(4, "");
        }
    }
    
    private void _setStat(final String s, final SftpATTRS sftpATTRS) throws SftpException {
        try {
            this.sendSETSTAT(Util.str2byte(s, this.fEncoding), sftpATTRS);
            final Header header = this.header(this.buf, new Header());
            final int length = header.length;
            final int type = header.type;
            this.fill(this.buf, length);
            if (type != 101) {
                throw new SftpException(4, "");
            }
            final int \u0131nt = this.buf.getInt();
            if (\u0131nt != 0) {
                this.throwStatusError(this.buf, \u0131nt);
            }
        }
        catch (Exception ex) {
            if (ex instanceof SftpException) {
                throw (SftpException)ex;
            }
            if (ex instanceof Throwable) {
                throw new SftpException(4, "", ex);
            }
            throw new SftpException(4, "");
        }
    }
    
    public String pwd() throws SftpException {
        return this.getCwd();
    }
    
    public String lpwd() {
        return this.lcwd;
    }
    
    public String version() {
        return this.version;
    }
    
    public String getHome() throws SftpException {
        if (this.home == null) {
            try {
                this.home = Util.byte2str(this._realpath(""), this.fEncoding);
            }
            catch (Exception ex) {
                if (ex instanceof SftpException) {
                    throw (SftpException)ex;
                }
                if (ex instanceof Throwable) {
                    throw new SftpException(4, "", ex);
                }
                throw new SftpException(4, "");
            }
        }
        return this.home;
    }
    
    private String getCwd() throws SftpException {
        if (this.cwd == null) {
            this.cwd = this.getHome();
        }
        return this.cwd;
    }
    
    private void setCwd(final String cwd) {
        this.cwd = cwd;
    }
    
    private void read(final byte[] array, int n, int i) throws IOException, SftpException {
        while (i > 0) {
            final int read = this.io_in.read(array, n, i);
            if (read <= 0) {
                throw new SftpException(4, "");
            }
            n += read;
            i -= read;
        }
    }
    
    private boolean checkStatus(final int[] array, Header header) throws IOException, SftpException {
        header = this.header(this.buf, header);
        final int length = header.length;
        final int type = header.type;
        if (array != null) {
            array[0] = header.rid;
        }
        this.fill(this.buf, length);
        if (type != 101) {
            throw new SftpException(4, "");
        }
        final int \u0131nt = this.buf.getInt();
        if (\u0131nt != 0) {
            this.throwStatusError(this.buf, \u0131nt);
        }
        return true;
    }
    
    private boolean _sendCLOSE(final byte[] array, final Header header) throws Exception {
        this.sendCLOSE(array);
        return this.checkStatus(null, header);
    }
    
    private void sendINIT() throws Exception {
        this.packet.reset();
        this.putHEAD((byte)1, 5);
        this.buf.putInt(3);
        this.getSession().write(this.packet, this, 9);
    }
    
    private void sendREALPATH(final byte[] array) throws Exception {
        this.sendPacketPath((byte)16, array);
    }
    
    private void sendSTAT(final byte[] array) throws Exception {
        this.sendPacketPath((byte)17, array);
    }
    
    private void sendLSTAT(final byte[] array) throws Exception {
        this.sendPacketPath((byte)7, array);
    }
    
    private void sendFSTAT(final byte[] array) throws Exception {
        this.sendPacketPath((byte)8, array);
    }
    
    private void sendSETSTAT(final byte[] array, final SftpATTRS sftpATTRS) throws Exception {
        this.packet.reset();
        this.putHEAD((byte)9, 9 + array.length + sftpATTRS.length());
        this.buf.putInt(this.seq++);
        this.buf.putString(array);
        sftpATTRS.dump(this.buf);
        this.getSession().write(this.packet, this, 9 + array.length + sftpATTRS.length() + 4);
    }
    
    private void sendREMOVE(final byte[] array) throws Exception {
        this.sendPacketPath((byte)13, array);
    }
    
    private void sendMKDIR(final byte[] array, final SftpATTRS sftpATTRS) throws Exception {
        this.packet.reset();
        this.putHEAD((byte)14, 9 + array.length + ((sftpATTRS != null) ? sftpATTRS.length() : 4));
        this.buf.putInt(this.seq++);
        this.buf.putString(array);
        if (sftpATTRS != null) {
            sftpATTRS.dump(this.buf);
        }
        else {
            this.buf.putInt(0);
        }
        this.getSession().write(this.packet, this, 9 + array.length + ((sftpATTRS != null) ? sftpATTRS.length() : 4) + 4);
    }
    
    private void sendRMDIR(final byte[] array) throws Exception {
        this.sendPacketPath((byte)15, array);
    }
    
    private void sendSYMLINK(final byte[] array, final byte[] array2) throws Exception {
        this.sendPacketPath((byte)20, array, array2);
    }
    
    private void sendREADLINK(final byte[] array) throws Exception {
        this.sendPacketPath((byte)19, array);
    }
    
    private void sendOPENDIR(final byte[] array) throws Exception {
        this.sendPacketPath((byte)11, array);
    }
    
    private void sendREADDIR(final byte[] array) throws Exception {
        this.sendPacketPath((byte)12, array);
    }
    
    private void sendRENAME(final byte[] array, final byte[] array2) throws Exception {
        this.sendPacketPath((byte)18, array, array2);
    }
    
    private void sendCLOSE(final byte[] array) throws Exception {
        this.sendPacketPath((byte)4, array);
    }
    
    private void sendOPENR(final byte[] array) throws Exception {
        this.sendOPEN(array, 1);
    }
    
    private void sendOPENW(final byte[] array) throws Exception {
        this.sendOPEN(array, 26);
    }
    
    private void sendOPENA(final byte[] array) throws Exception {
        this.sendOPEN(array, 10);
    }
    
    private void sendOPEN(final byte[] array, final int n) throws Exception {
        this.packet.reset();
        this.putHEAD((byte)3, 17 + array.length);
        this.buf.putInt(this.seq++);
        this.buf.putString(array);
        this.buf.putInt(n);
        this.buf.putInt(0);
        this.getSession().write(this.packet, this, 17 + array.length + 4);
    }
    
    private void sendPacketPath(final byte b, final byte[] array) throws Exception {
        this.packet.reset();
        this.putHEAD(b, 9 + array.length);
        this.buf.putInt(this.seq++);
        this.buf.putString(array);
        this.getSession().write(this.packet, this, 9 + array.length + 4);
    }
    
    private void sendPacketPath(final byte b, final byte[] array, final byte[] array2) throws Exception {
        this.packet.reset();
        this.putHEAD(b, 13 + array.length + array2.length);
        this.buf.putInt(this.seq++);
        this.buf.putString(array);
        this.buf.putString(array2);
        this.getSession().write(this.packet, this, 13 + array.length + array2.length + 4);
    }
    
    private int sendWRITE(final byte[] array, final long n, final byte[] array2, final int n2, final int n3) throws Exception {
        int n4 = n3;
        this.packet.reset();
        if (this.buf.buffer.length < this.buf.index + 13 + 21 + array.length + n3 + 32 + 20) {
            n4 = this.buf.buffer.length - (this.buf.index + 13 + 21 + array.length + 32 + 20);
        }
        this.putHEAD((byte)6, 21 + array.length + n4);
        this.buf.putInt(this.seq++);
        this.buf.putString(array);
        this.buf.putLong(n);
        if (this.buf.buffer != array2) {
            this.buf.putString(array2, n2, n4);
        }
        else {
            this.buf.putInt(n4);
            this.buf.skip(n4);
        }
        this.getSession().write(this.packet, this, 21 + array.length + n4 + 4);
        return n4;
    }
    
    private void sendREAD(final byte[] array, final long n, final int n2) throws Exception {
        this.packet.reset();
        this.putHEAD((byte)5, 21 + array.length);
        this.buf.putInt(this.seq++);
        this.buf.putString(array);
        this.buf.putLong(n);
        this.buf.putInt(n2);
        this.getSession().write(this.packet, this, 21 + array.length + 4);
    }
    
    private void putHEAD(final byte b, final int n) throws Exception {
        this.buf.putByte((byte)94);
        this.buf.putInt(this.recipient);
        this.buf.putInt(n + 4);
        this.buf.putInt(n);
        this.buf.putByte(b);
    }
    
    private Vector glob_remote(final String s) throws Exception {
        final Vector<String> vector = new Vector<String>();
        final int lastIndex = s.lastIndexOf(47);
        if (lastIndex < 0) {
            vector.addElement(Util.unquote(s));
            return vector;
        }
        final String substring = s.substring(0, (lastIndex == 0) ? 1 : lastIndex);
        final String substring2 = s.substring(lastIndex + 1);
        String s2 = Util.unquote(substring);
        final byte[][] array = { null };
        if (!this.isPattern(substring2, array)) {
            if (s2.length() != 1) {
                s2 += "/";
            }
            vector.addElement(s2 + Util.unquote(substring2));
            return vector;
        }
        final byte[] array2 = array[0];
        this.sendOPENDIR(Util.str2byte(s2, this.fEncoding));
        Header header = this.header(this.buf, new Header());
        final int length = header.length;
        final int type = header.type;
        this.fill(this.buf, length);
        if (type != 101 && type != 102) {
            throw new SftpException(4, "");
        }
        if (type == 101) {
            this.throwStatusError(this.buf, this.buf.getInt());
        }
        final byte[] string = this.buf.getString();
        String string2 = null;
        while (true) {
            this.sendREADDIR(string);
            header = this.header(this.buf, header);
            int length2 = header.length;
            final int type2 = header.type;
            if (type2 != 101 && type2 != 104) {
                throw new SftpException(4, "");
            }
            if (type2 == 101) {
                this.fill(this.buf, length2);
                if (this._sendCLOSE(string, header)) {
                    return vector;
                }
                return null;
            }
            else {
                this.buf.rewind();
                this.fill(this.buf.buffer, 0, 4);
                length2 -= 4;
                int i = this.buf.getInt();
                this.buf.reset();
                while (i > 0) {
                    if (length2 > 0) {
                        this.buf.shift();
                        final int read = this.io_in.read(this.buf.buffer, this.buf.index, (this.buf.buffer.length > this.buf.index + length2) ? length2 : (this.buf.buffer.length - this.buf.index));
                        if (read <= 0) {
                            break;
                        }
                        final Buffer buf = this.buf;
                        buf.index += read;
                        length2 -= read;
                    }
                    final byte[] string3 = this.buf.getString();
                    if (this.server_version <= 3) {
                        this.buf.getString();
                    }
                    SftpATTRS.getATTR(this.buf);
                    byte[] str2byte = string3;
                    String s3 = null;
                    if (!this.fEncoding_is_utf8) {
                        s3 = Util.byte2str(string3, this.fEncoding);
                        str2byte = Util.str2byte(s3, "UTF-8");
                    }
                    if (Util.glob(array2, str2byte)) {
                        if (s3 == null) {
                            s3 = Util.byte2str(string3, this.fEncoding);
                        }
                        if (string2 == null) {
                            string2 = s2;
                            if (!string2.endsWith("/")) {
                                string2 += "/";
                            }
                        }
                        vector.addElement(string2 + s3);
                    }
                    --i;
                }
            }
        }
    }
    
    private boolean isPattern(final byte[] array) {
        int i;
        for (i = array.length - 1; i >= 0; --i) {
            if (array[i] == 42 || array[i] == 63) {
                if (i <= 0 || array[i - 1] != 92) {
                    break;
                }
                if (--i > 0 && array[i - 1] == 92) {
                    break;
                }
            }
        }
        return i >= 0;
    }
    
    private Vector glob_local(final String s) throws Exception {
        final Vector<String> vector = new Vector<String>();
        final byte[] str2byte = Util.str2byte(s, "UTF-8");
        int i;
        for (i = str2byte.length - 1; i >= 0; --i) {
            if (str2byte[i] == 42 || str2byte[i] == 63) {
                if (ChannelSftp.fs_is_bs || i <= 0 || str2byte[i - 1] != 92 || --i <= 0 || str2byte[i - 1] != 92) {
                    break;
                }
                --i;
            }
        }
        if (i < 0) {
            vector.addElement(ChannelSftp.fs_is_bs ? s : Util.unquote(s));
            return vector;
        }
        while (i >= 0 && str2byte[i] != ChannelSftp.file_separatorc && (!ChannelSftp.fs_is_bs || str2byte[i] != 47)) {
            --i;
        }
        if (i < 0) {
            vector.addElement(ChannelSftp.fs_is_bs ? s : Util.unquote(s));
            return vector;
        }
        byte[] array;
        if (i == 0) {
            array = new byte[] { (byte)ChannelSftp.file_separatorc };
        }
        else {
            array = new byte[i];
            System.arraycopy(str2byte, 0, array, 0, i);
        }
        final byte[] array2 = new byte[str2byte.length - i - 1];
        System.arraycopy(str2byte, i + 1, array2, 0, array2.length);
        try {
            final String[] list = new File(Util.byte2str(array, "UTF-8")).list();
            final String string = Util.byte2str(array) + ChannelSftp.file_separator;
            for (int j = 0; j < list.length; ++j) {
                if (Util.glob(array2, Util.str2byte(list[j], "UTF-8"))) {
                    vector.addElement(string + list[j]);
                }
            }
        }
        catch (Exception ex) {}
        return vector;
    }
    
    private void throwStatusError(final Buffer buffer, final int n) throws SftpException {
        if (this.server_version >= 3 && buffer.getLength() >= 4) {
            throw new SftpException(n, Util.byte2str(buffer.getString(), "UTF-8"));
        }
        throw new SftpException(n, "Failure");
    }
    
    private static boolean isLocalAbsolutePath(final String s) {
        return new File(s).isAbsolute();
    }
    
    public void disconnect() {
        super.disconnect();
    }
    
    private boolean isPattern(final String s, final byte[][] array) {
        final byte[] str2byte = Util.str2byte(s, "UTF-8");
        if (array != null) {
            array[0] = str2byte;
        }
        return this.isPattern(str2byte);
    }
    
    private boolean isPattern(final String s) {
        return this.isPattern(s, null);
    }
    
    private void fill(final Buffer buffer, final int n) throws IOException {
        buffer.reset();
        this.fill(buffer.buffer, 0, n);
        buffer.skip(n);
    }
    
    private int fill(final byte[] array, int n, int i) throws IOException {
        final int n2 = n;
        while (i > 0) {
            final int read = this.io_in.read(array, n, i);
            if (read <= 0) {
                throw new IOException("inputstream is closed");
            }
            n += read;
            i -= read;
        }
        return n - n2;
    }
    
    private void skip(long n) throws IOException {
        while (n > 0L) {
            final long skip = this.io_in.skip(n);
            if (skip <= 0L) {
                break;
            }
            n -= skip;
        }
    }
    
    private Header header(final Buffer buffer, final Header header) throws IOException {
        buffer.rewind();
        this.fill(buffer.buffer, 0, 9);
        header.length = buffer.getInt() - 5;
        header.type = (buffer.getByte() & 0xFF);
        header.rid = buffer.getInt();
        return header;
    }
    
    private String remoteAbsolutePath(final String s) throws SftpException {
        if (s.charAt(0) == '/') {
            return s;
        }
        final String cwd = this.getCwd();
        if (cwd.endsWith("/")) {
            return cwd + s;
        }
        return cwd + "/" + s;
    }
    
    private String localAbsolutePath(final String s) {
        if (isLocalAbsolutePath(s)) {
            return s;
        }
        if (this.lcwd.endsWith(ChannelSftp.file_separator)) {
            return this.lcwd + s;
        }
        return this.lcwd + ChannelSftp.file_separator + s;
    }
    
    private String isUnique(final String s) throws SftpException, Exception {
        final Vector glob_remote = this.glob_remote(s);
        if (glob_remote.size() != 1) {
            throw new SftpException(4, s + " is not unique: " + glob_remote.toString());
        }
        return glob_remote.elementAt(0);
    }
    
    public int getServerVersion() throws SftpException {
        if (!this.isConnected()) {
            throw new SftpException(4, "The channel is not connected.");
        }
        return this.server_version;
    }
    
    public void setFilenameEncoding(String fEncoding) throws SftpException {
        if (this.getServerVersion() > 3 && !fEncoding.equals("UTF-8")) {
            throw new SftpException(4, "The encoding can not be changed for this sftp server.");
        }
        if (fEncoding.equals("UTF-8")) {
            fEncoding = "UTF-8";
        }
        this.fEncoding = fEncoding;
        this.fEncoding_is_utf8 = this.fEncoding.equals("UTF-8");
    }
    
    public String getExtension(final String s) {
        if (this.extensions == null) {
            return null;
        }
        return this.extensions.get(s);
    }
    
    public String realpath(final String s) throws SftpException {
        try {
            return Util.byte2str(this._realpath(this.remoteAbsolutePath(s)), this.fEncoding);
        }
        catch (Exception ex) {
            if (ex instanceof SftpException) {
                throw (SftpException)ex;
            }
            if (ex instanceof Throwable) {
                throw new SftpException(4, "", ex);
            }
            throw new SftpException(4, "");
        }
    }
    
    static {
        file_separator = File.separator;
        file_separatorc = File.separatorChar;
        ChannelSftp.fs_is_bs = ((byte)File.separatorChar == 92);
    }
    
    class Header
    {
        int length;
        int type;
        int rid;
    }
    
    public class LsEntry implements Comparable
    {
        private String filename;
        private String longname;
        private SftpATTRS attrs;
        
        LsEntry(final String filename, final String longname, final SftpATTRS attrs) {
            this.setFilename(filename);
            this.setLongname(longname);
            this.setAttrs(attrs);
        }
        
        public String getFilename() {
            return this.filename;
        }
        
        void setFilename(final String filename) {
            this.filename = filename;
        }
        
        public String getLongname() {
            return this.longname;
        }
        
        void setLongname(final String longname) {
            this.longname = longname;
        }
        
        public SftpATTRS getAttrs() {
            return this.attrs;
        }
        
        void setAttrs(final SftpATTRS attrs) {
            this.attrs = attrs;
        }
        
        public String toString() {
            return this.longname;
        }
        
        public int compareTo(final Object o) throws ClassCastException {
            if (o instanceof LsEntry) {
                return this.filename.compareTo(((LsEntry)o).getFilename());
            }
            throw new ClassCastException("a decendent of LsEntry must be given.");
        }
    }
}
