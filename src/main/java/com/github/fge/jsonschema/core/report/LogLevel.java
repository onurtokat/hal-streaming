// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.report;

public enum LogLevel
{
    DEBUG("debug"), 
    INFO("info"), 
    WARNING("warning"), 
    ERROR("error"), 
    FATAL("fatal"), 
    NONE("none");
    
    private final String s;
    
    private LogLevel(final String s) {
        this.s = s;
    }
    
    @Override
    public String toString() {
        return this.s;
    }
}
