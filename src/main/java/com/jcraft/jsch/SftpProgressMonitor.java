// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

public interface SftpProgressMonitor
{
    public static final int PUT = 0;
    public static final int GET = 1;
    
    void init(final int p0, final String p1, final String p2, final long p3);
    
    boolean count(final long p0);
    
    void end();
}
