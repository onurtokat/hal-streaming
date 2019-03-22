// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

import java.util.Date;
import java.text.SimpleDateFormat;

public class SftpATTRS
{
    static final int S_ISUID = 2048;
    static final int S_ISGID = 1024;
    static final int S_ISVTX = 512;
    static final int S_IRUSR = 256;
    static final int S_IWUSR = 128;
    static final int S_IXUSR = 64;
    static final int S_IREAD = 256;
    static final int S_IWRITE = 128;
    static final int S_IEXEC = 64;
    static final int S_IRGRP = 32;
    static final int S_IWGRP = 16;
    static final int S_IXGRP = 8;
    static final int S_IROTH = 4;
    static final int S_IWOTH = 2;
    static final int S_IXOTH = 1;
    private static final int pmask = 4095;
    public static final int SSH_FILEXFER_ATTR_SIZE = 1;
    public static final int SSH_FILEXFER_ATTR_UIDGID = 2;
    public static final int SSH_FILEXFER_ATTR_PERMISSIONS = 4;
    public static final int SSH_FILEXFER_ATTR_ACMODTIME = 8;
    public static final int SSH_FILEXFER_ATTR_EXTENDED = Integer.MIN_VALUE;
    static final int S_IFDIR = 16384;
    static final int S_IFLNK = 40960;
    int flags;
    long size;
    int uid;
    int gid;
    int permissions;
    int atime;
    int mtime;
    String[] extended;
    
    public String getPermissionsString() {
        final StringBuffer sb = new StringBuffer(10);
        if (this.isDir()) {
            sb.append('d');
        }
        else if (this.isLink()) {
            sb.append('l');
        }
        else {
            sb.append('-');
        }
        if ((this.permissions & 0x100) != 0x0) {
            sb.append('r');
        }
        else {
            sb.append('-');
        }
        if ((this.permissions & 0x80) != 0x0) {
            sb.append('w');
        }
        else {
            sb.append('-');
        }
        if ((this.permissions & 0x800) != 0x0) {
            sb.append('s');
        }
        else if ((this.permissions & 0x40) != 0x0) {
            sb.append('x');
        }
        else {
            sb.append('-');
        }
        if ((this.permissions & 0x20) != 0x0) {
            sb.append('r');
        }
        else {
            sb.append('-');
        }
        if ((this.permissions & 0x10) != 0x0) {
            sb.append('w');
        }
        else {
            sb.append('-');
        }
        if ((this.permissions & 0x400) != 0x0) {
            sb.append('s');
        }
        else if ((this.permissions & 0x8) != 0x0) {
            sb.append('x');
        }
        else {
            sb.append('-');
        }
        if ((this.permissions & 0x4) != 0x0) {
            sb.append('r');
        }
        else {
            sb.append('-');
        }
        if ((this.permissions & 0x2) != 0x0) {
            sb.append('w');
        }
        else {
            sb.append('-');
        }
        if ((this.permissions & 0x1) != 0x0) {
            sb.append('x');
        }
        else {
            sb.append('-');
        }
        return sb.toString();
    }
    
    public String getAtimeString() {
        return new SimpleDateFormat().format(new Date(this.atime));
    }
    
    public String getMtimeString() {
        return new Date(this.mtime * 1000L).toString();
    }
    
    private SftpATTRS() {
        this.flags = 0;
        this.extended = null;
    }
    
    static SftpATTRS getATTR(final Buffer buffer) {
        final SftpATTRS sftpATTRS = new SftpATTRS();
        sftpATTRS.flags = buffer.getInt();
        if ((sftpATTRS.flags & 0x1) != 0x0) {
            sftpATTRS.size = buffer.getLong();
        }
        if ((sftpATTRS.flags & 0x2) != 0x0) {
            sftpATTRS.uid = buffer.getInt();
            sftpATTRS.gid = buffer.getInt();
        }
        if ((sftpATTRS.flags & 0x4) != 0x0) {
            sftpATTRS.permissions = buffer.getInt();
        }
        if ((sftpATTRS.flags & 0x8) != 0x0) {
            sftpATTRS.atime = buffer.getInt();
        }
        if ((sftpATTRS.flags & 0x8) != 0x0) {
            sftpATTRS.mtime = buffer.getInt();
        }
        if ((sftpATTRS.flags & Integer.MIN_VALUE) != 0x0) {
            final int \u0131nt = buffer.getInt();
            if (\u0131nt > 0) {
                sftpATTRS.extended = new String[\u0131nt * 2];
                for (int i = 0; i < \u0131nt; ++i) {
                    sftpATTRS.extended[i * 2] = new String(buffer.getString());
                    sftpATTRS.extended[i * 2 + 1] = new String(buffer.getString());
                }
            }
        }
        return sftpATTRS;
    }
    
    int length() {
        int n = 4;
        if ((this.flags & 0x1) != 0x0) {
            n += 8;
        }
        if ((this.flags & 0x2) != 0x0) {
            n += 8;
        }
        if ((this.flags & 0x4) != 0x0) {
            n += 4;
        }
        if ((this.flags & 0x8) != 0x0) {
            n += 8;
        }
        if ((this.flags & Integer.MIN_VALUE) != 0x0) {
            n += 4;
            final int n2 = this.extended.length / 2;
            if (n2 > 0) {
                for (int i = 0; i < n2; ++i) {
                    n += 4;
                    int n3 = n + this.extended[i * 2].length();
                    n3 += 4;
                    n = n3 + this.extended[i * 2 + 1].length();
                }
            }
        }
        return n;
    }
    
    void dump(final Buffer buffer) {
        buffer.putInt(this.flags);
        if ((this.flags & 0x1) != 0x0) {
            buffer.putLong(this.size);
        }
        if ((this.flags & 0x2) != 0x0) {
            buffer.putInt(this.uid);
            buffer.putInt(this.gid);
        }
        if ((this.flags & 0x4) != 0x0) {
            buffer.putInt(this.permissions);
        }
        if ((this.flags & 0x8) != 0x0) {
            buffer.putInt(this.atime);
        }
        if ((this.flags & 0x8) != 0x0) {
            buffer.putInt(this.mtime);
        }
        if ((this.flags & Integer.MIN_VALUE) != 0x0) {
            final int n = this.extended.length / 2;
            if (n > 0) {
                for (int i = 0; i < n; ++i) {
                    buffer.putString(this.extended[i * 2].getBytes());
                    buffer.putString(this.extended[i * 2 + 1].getBytes());
                }
            }
        }
    }
    
    void setFLAGS(final int flags) {
        this.flags = flags;
    }
    
    public void setSIZE(final long size) {
        this.flags |= 0x1;
        this.size = size;
    }
    
    public void setUIDGID(final int uid, final int gid) {
        this.flags |= 0x2;
        this.uid = uid;
        this.gid = gid;
    }
    
    public void setACMODTIME(final int atime, final int mtime) {
        this.flags |= 0x8;
        this.atime = atime;
        this.mtime = mtime;
    }
    
    public void setPERMISSIONS(int permissions) {
        this.flags |= 0x4;
        permissions = ((this.permissions & 0xFFFFF000) | (permissions & 0xFFF));
        this.permissions = permissions;
    }
    
    public boolean isDir() {
        return (this.flags & 0x4) != 0x0 && (this.permissions & 0x4000) == 0x4000;
    }
    
    public boolean isLink() {
        return (this.flags & 0x4) != 0x0 && (this.permissions & 0xA000) == 0xA000;
    }
    
    public int getFlags() {
        return this.flags;
    }
    
    public long getSize() {
        return this.size;
    }
    
    public int getUId() {
        return this.uid;
    }
    
    public int getGId() {
        return this.gid;
    }
    
    public int getPermissions() {
        return this.permissions;
    }
    
    public int getATime() {
        return this.atime;
    }
    
    public int getMTime() {
        return this.mtime;
    }
    
    public String[] getExtended() {
        return this.extended;
    }
    
    public String toString() {
        return this.getPermissionsString() + " " + this.getUId() + " " + this.getGId() + " " + this.getSize() + " " + this.getMtimeString();
    }
}
