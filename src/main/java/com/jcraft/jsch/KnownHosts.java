// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.Vector;

public class KnownHosts implements HostKeyRepository
{
    private static final String _known_hosts = "known_hosts";
    private JSch jsch;
    private String known_hosts;
    private Vector pool;
    private MAC hmacsha1;
    private static final byte[] space;
    private static final byte[] cr;
    
    KnownHosts(final JSch jsch) {
        this.jsch = null;
        this.known_hosts = null;
        this.pool = null;
        this.hmacsha1 = null;
        this.jsch = jsch;
        this.pool = new Vector();
    }
    
    void setKnownHosts(final String known_hosts) throws JSchException {
        try {
            this.known_hosts = known_hosts;
            this.setKnownHosts(new FileInputStream(known_hosts));
        }
        catch (FileNotFoundException ex) {}
    }
    
    void setKnownHosts(final InputStream \u0131nputStream) throws JSchException {
        this.pool.removeAllElements();
        final StringBuffer sb = new StringBuffer();
        final boolean b = false;
        try {
            byte[] array = new byte[1024];
        Block_4:
            while (true) {
                int n = 0;
                while (true) {
                    final int read = \u0131nputStream.read();
                    if (read == -1) {
                        if (n == 0) {
                            break Block_4;
                        }
                        break;
                    }
                    else {
                        if (read == 13) {
                            continue;
                        }
                        if (read == 10) {
                            break;
                        }
                        if (array.length <= n) {
                            if (n > 10240) {
                                break;
                            }
                            final byte[] array2 = new byte[array.length * 2];
                            System.arraycopy(array, 0, array2, 0, array.length);
                            array = array2;
                        }
                        array[n++] = (byte)read;
                    }
                }
                int i = 0;
                while (i < n) {
                    final byte b2 = array[i];
                    if (b2 == 32 || b2 == 9) {
                        ++i;
                    }
                    else {
                        if (b2 == 35) {
                            this.addInvalidLine(new String(array, 0, n));
                            continue Block_4;
                        }
                        break;
                    }
                }
                if (i >= n) {
                    this.addInvalidLine(new String(array, 0, n));
                }
                else {
                    sb.setLength(0);
                    while (i < n) {
                        final byte b3 = array[i++];
                        if (b3 == 32) {
                            break;
                        }
                        if (b3 == 9) {
                            break;
                        }
                        sb.append((char)b3);
                    }
                    final String string = sb.toString();
                    if (i >= n || string.length() == 0) {
                        this.addInvalidLine(new String(array, 0, n));
                    }
                    else {
                        sb.setLength(0);
                        int n2 = -1;
                        while (i < n) {
                            final byte b4 = array[i++];
                            if (b4 == 32) {
                                break;
                            }
                            if (b4 == 9) {
                                break;
                            }
                            sb.append((char)b4);
                        }
                        if (sb.toString().equals("ssh-dss")) {
                            n2 = 1;
                        }
                        else if (sb.toString().equals("ssh-rsa")) {
                            n2 = 2;
                        }
                        else {
                            i = n;
                        }
                        if (i >= n) {
                            this.addInvalidLine(new String(array, 0, n));
                        }
                        else {
                            sb.setLength(0);
                            while (i < n) {
                                final byte b5 = array[i++];
                                if (b5 == 13) {
                                    continue;
                                }
                                if (b5 == 10) {
                                    break;
                                }
                                sb.append((char)b5);
                            }
                            final String string2 = sb.toString();
                            if (string2.length() == 0) {
                                this.addInvalidLine(new String(array, 0, n));
                            }
                            else {
                                this.pool.addElement(new HashedHostKey(string, n2, Util.fromBase64(string2.getBytes(), 0, string2.length())));
                            }
                        }
                    }
                }
            }
            \u0131nputStream.close();
            if (b) {
                throw new JSchException("KnownHosts: invalid format");
            }
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
    
    private void addInvalidLine(final String s) throws JSchException {
        this.pool.addElement(new HostKey(s, 3, null));
    }
    
    String getKnownHostsFile() {
        return this.known_hosts;
    }
    
    public String getKnownHostsRepositoryID() {
        return this.known_hosts;
    }
    
    public int check(final String s, final byte[] array) {
        int n = 1;
        if (s == null) {
            return n;
        }
        final int type = this.getType(array);
        synchronized (this.pool) {
            for (int i = 0; i < this.pool.size(); ++i) {
                final HostKey hostKey = this.pool.elementAt(i);
                if (hostKey.isMatched(s) && hostKey.type == type) {
                    if (Util.array_equals(hostKey.key, array)) {
                        return 0;
                    }
                    n = 2;
                }
            }
        }
        return n;
    }
    
    public void add(final HostKey hostKey, final UserInfo userInfo) {
        final int type = hostKey.type;
        final String host = hostKey.getHost();
        final byte[] key = hostKey.key;
        synchronized (this.pool) {
            for (int i = 0; i < this.pool.size(); ++i) {
                final HostKey hostKey2 = this.pool.elementAt(i);
                if (!hostKey2.isMatched(host) || hostKey2.type == type) {}
            }
        }
        this.pool.addElement(hostKey);
        final String knownHostsRepositoryID = this.getKnownHostsRepositoryID();
        if (knownHostsRepositoryID != null) {
            int n = 1;
            final File file = new File(knownHostsRepositoryID);
            if (!file.exists()) {
                n = 0;
                if (userInfo != null) {
                    n = (userInfo.promptYesNo(knownHostsRepositoryID + " does not exist.\n" + "Are you sure you want to create it?") ? 1 : 0);
                    final File parentFile = file.getParentFile();
                    if (n != 0 && parentFile != null && !parentFile.exists()) {
                        n = (userInfo.promptYesNo("The parent directory " + parentFile + " does not exist.\n" + "Are you sure you want to create it?") ? 1 : 0);
                        if (n != 0) {
                            if (!parentFile.mkdirs()) {
                                userInfo.showMessage(parentFile + " has not been created.");
                                n = 0;
                            }
                            else {
                                userInfo.showMessage(parentFile + " has been succesfully created.\nPlease check its access permission.");
                            }
                        }
                    }
                    if (parentFile == null) {
                        n = 0;
                    }
                }
            }
            if (n != 0) {
                try {
                    this.sync(knownHostsRepositoryID);
                }
                catch (Exception ex) {
                    System.err.println("sync known_hosts: " + ex);
                }
            }
        }
    }
    
    public HostKey[] getHostKey() {
        return this.getHostKey(null, null);
    }
    
    public HostKey[] getHostKey(final String s, final String s2) {
        synchronized (this.pool) {
            int n = 0;
            for (int i = 0; i < this.pool.size(); ++i) {
                final HostKey hostKey = this.pool.elementAt(i);
                if (hostKey.type != 3) {
                    if (s == null || (hostKey.isMatched(s) && (s2 == null || hostKey.getType().equals(s2)))) {
                        ++n;
                    }
                }
            }
            if (n == 0) {
                return null;
            }
            final HostKey[] array = new HostKey[n];
            int n2 = 0;
            for (int j = 0; j < this.pool.size(); ++j) {
                final HostKey hostKey2 = this.pool.elementAt(j);
                if (hostKey2.type != 3) {
                    if (s == null || (hostKey2.isMatched(s) && (s2 == null || hostKey2.getType().equals(s2)))) {
                        array[n2++] = hostKey2;
                    }
                }
            }
            return array;
        }
    }
    
    public void remove(final String s, final String s2) {
        this.remove(s, s2, null);
    }
    
    public void remove(final String s, final String s2, final byte[] array) {
        boolean b = false;
        synchronized (this.pool) {
            for (int i = 0; i < this.pool.size(); ++i) {
                final HostKey hostKey = this.pool.elementAt(i);
                if (s == null || (hostKey.isMatched(s) && (s2 == null || (hostKey.getType().equals(s2) && (array == null || Util.array_equals(array, hostKey.key)))))) {
                    final String host = hostKey.getHost();
                    if (host.equals(s) || (hostKey instanceof HashedHostKey && ((HashedHostKey)hostKey).isHashed())) {
                        this.pool.removeElement(hostKey);
                    }
                    else {
                        hostKey.host = this.deleteSubString(host, s);
                    }
                    b = true;
                }
            }
        }
        if (b) {
            try {
                this.sync();
            }
            catch (Exception ex) {}
        }
    }
    
    protected void sync() throws IOException {
        if (this.known_hosts != null) {
            this.sync(this.known_hosts);
        }
    }
    
    protected synchronized void sync(final String s) throws IOException {
        if (s == null) {
            return;
        }
        final FileOutputStream fileOutputStream = new FileOutputStream(s);
        this.dump(fileOutputStream);
        fileOutputStream.close();
    }
    
    void dump(final OutputStream outputStream) throws IOException {
        try {
            synchronized (this.pool) {
                for (int i = 0; i < this.pool.size(); ++i) {
                    final HostKey hostKey = this.pool.elementAt(i);
                    final String host = hostKey.getHost();
                    final String type = hostKey.getType();
                    if (type.equals("UNKNOWN")) {
                        outputStream.write(host.getBytes());
                        outputStream.write(KnownHosts.cr);
                    }
                    else {
                        outputStream.write(host.getBytes());
                        outputStream.write(KnownHosts.space);
                        outputStream.write(type.getBytes());
                        outputStream.write(KnownHosts.space);
                        outputStream.write(hostKey.getKey().getBytes());
                        outputStream.write(KnownHosts.cr);
                    }
                }
            }
        }
        catch (Exception ex) {
            System.err.println(ex);
        }
    }
    
    private int getType(final byte[] array) {
        if (array[8] == 100) {
            return 1;
        }
        if (array[8] == 114) {
            return 2;
        }
        return 3;
    }
    
    private String deleteSubString(final String s, final String s2) {
        int i = 0;
        final int length = s2.length();
        int length2;
        int index;
        for (length2 = s.length(); i < length2; i = index + 1) {
            index = s.indexOf(44, i);
            if (index == -1) {
                break;
            }
            if (s2.equals(s.substring(i, index))) {
                return s.substring(0, i) + s.substring(index + 1);
            }
        }
        if (s.endsWith(s2) && length2 - i == length) {
            return s.substring(0, (length == length2) ? 0 : (length2 - length - 1));
        }
        return s;
    }
    
    private synchronized MAC getHMACSHA1() {
        if (this.hmacsha1 == null) {
            try {
                this.hmacsha1 = (MAC)Class.forName(JSch.getConfig("hmac-sha1")).newInstance();
            }
            catch (Exception ex) {
                System.err.println("hmacsha1: " + ex);
            }
        }
        return this.hmacsha1;
    }
    
    HostKey createHashedHostKey(final String s, final byte[] array) throws JSchException {
        final HashedHostKey hashedHostKey = new HashedHostKey(s, array);
        hashedHostKey.hash();
        return hashedHostKey;
    }
    
    static {
        space = new byte[] { 32 };
        cr = "\n".getBytes();
    }
    
    class HashedHostKey extends HostKey
    {
        private static final String HASH_MAGIC = "|1|";
        private static final String HASH_DELIM = "|";
        private boolean hashed;
        byte[] salt;
        byte[] hash;
        
        HashedHostKey(final KnownHosts knownHosts, final String s, final byte[] array) throws JSchException {
            this(knownHosts, s, 0, array);
        }
        
        HashedHostKey(final String s, final int n, final byte[] array) throws JSchException {
            super(s, n, array);
            this.hashed = false;
            this.salt = null;
            this.hash = null;
            if (this.host.startsWith("|1|") && this.host.substring("|1|".length()).indexOf("|") > 0) {
                final String substring = this.host.substring("|1|".length());
                final String substring2 = substring.substring(0, substring.indexOf("|"));
                final String substring3 = substring.substring(substring.indexOf("|") + 1);
                this.salt = Util.fromBase64(substring2.getBytes(), 0, substring2.length());
                this.hash = Util.fromBase64(substring3.getBytes(), 0, substring3.length());
                if (this.salt.length != 20 || this.hash.length != 20) {
                    this.salt = null;
                    this.hash = null;
                    return;
                }
                this.hashed = true;
            }
        }
        
        boolean isMatched(final String s) {
            if (!this.hashed) {
                return super.isMatched(s);
            }
            final MAC access$000 = KnownHosts.this.getHMACSHA1();
            try {
                synchronized (access$000) {
                    access$000.init(this.salt);
                    final byte[] bytes = s.getBytes();
                    access$000.update(bytes, 0, bytes.length);
                    final byte[] array = new byte[access$000.getBlockSize()];
                    access$000.doFinal(array, 0);
                    return Util.array_equals(this.hash, array);
                }
            }
            catch (Exception ex) {
                System.out.println(ex);
                return false;
            }
        }
        
        boolean isHashed() {
            return this.hashed;
        }
        
        void hash() {
            if (this.hashed) {
                return;
            }
            final MAC access$000 = KnownHosts.this.getHMACSHA1();
            if (this.salt == null) {
                final Random random = Session.random;
                synchronized (random) {
                    random.fill(this.salt = new byte[access$000.getBlockSize()], 0, this.salt.length);
                }
            }
            try {
                synchronized (access$000) {
                    access$000.init(this.salt);
                    final byte[] bytes = this.host.getBytes();
                    access$000.update(bytes, 0, bytes.length);
                    access$000.doFinal(this.hash = new byte[access$000.getBlockSize()], 0);
                }
            }
            catch (Exception ex) {}
            this.host = "|1|" + new String(Util.toBase64(this.salt, 0, this.salt.length)) + "|" + new String(Util.toBase64(this.hash, 0, this.hash.length));
            this.hashed = true;
        }
    }
}
