// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.colmapping;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonCreator;

public enum InsightCategory
{
    UNKNOWN("Unknown"), 
    KPI("KPI"), 
    KQI("KQI"), 
    OBSERVATION("Observation");
    
    private String label;
    
    private InsightCategory(final String label) {
        this.label = label;
    }
    
    @JsonCreator
    public static InsightCategory fromString(final String aValue) {
        if (aValue != null) {
            for (final InsightCategory insightCategory : values()) {
                if (insightCategory.label.equalsIgnoreCase(aValue)) {
                    return insightCategory;
                }
            }
        }
        return InsightCategory.UNKNOWN;
    }
    
    @JsonValue
    @Override
    public String toString() {
        return this.label;
    }
}
