// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.commons.plugin.dto.augmentation;

public enum AugmentingParameterType
{
    NUMBER("number"), 
    STRING("string"), 
    UNKNOWN("unknown");
    
    private String type;
    
    private AugmentingParameterType(final String type) {
        this.type = type;
    }
    
    public static AugmentingParameterType fromString(final String type) {
        if (type != null) {
            for (final AugmentingParameterType b : values()) {
                if (type.equalsIgnoreCase(b.type)) {
                    return b;
                }
            }
        }
        return AugmentingParameterType.UNKNOWN;
    }
}
