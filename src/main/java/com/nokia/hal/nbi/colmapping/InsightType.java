// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.colmapping;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonCreator;

public enum InsightType
{
    UNKNOWN("unknown"), 
    DOUBLE("double"), 
    INTEGER("integer"), 
    STRING("string"), 
    BIGINT("bigint"), 
    BOOLEAN("boolean");
    
    private String label;
    
    private InsightType(final String label) {
        this.label = label;
    }
    
    @JsonCreator
    public static InsightType fromString(final String aValue) {
        if (aValue != null) {
            for (final InsightType insightType : values()) {
                if (insightType.label.equals(aValue.toLowerCase())) {
                    return insightType;
                }
            }
        }
        return InsightType.UNKNOWN;
    }
    
    @JsonValue
    @Override
    public String toString() {
        return this.label;
    }
}
