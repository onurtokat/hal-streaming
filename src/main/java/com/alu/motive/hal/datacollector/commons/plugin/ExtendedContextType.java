// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.commons.plugin;

public enum ExtendedContextType
{
    PARAMETER("parameter"), 
    HEADER("header");
    
    private String type;
    
    private ExtendedContextType(final String type) {
        this.type = type;
    }
    
    public static ExtendedContextType fromString(final String type) throws UnsupportedExtendedContextTypeException {
        if (type != null) {
            for (final ExtendedContextType b : values()) {
                if (type.equalsIgnoreCase(b.type)) {
                    return b;
                }
            }
        }
        throw new UnsupportedExtendedContextTypeException("type");
    }
    
    @Override
    public String toString() {
        return this.type;
    }
}
