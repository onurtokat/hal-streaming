// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

class JSchAuthCancelException extends JSchException
{
    String method;
    
    JSchAuthCancelException() {
    }
    
    JSchAuthCancelException(final String method) {
        super(method);
        this.method = method;
    }
    
    public String getMethod() {
        return this.method;
    }
}
