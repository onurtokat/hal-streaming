// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

class JSchPartialAuthException extends JSchException
{
    String methods;
    
    public JSchPartialAuthException() {
    }
    
    public JSchPartialAuthException(final String methods) {
        super(methods);
        this.methods = methods;
    }
    
    public String getMethods() {
        return this.methods;
    }
}
