// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.config.insights;

public enum InsightCategory
{
    COUNTER("Counter"), 
    KPI("KPI"), 
    KQI("KQI"), 
    OBSERVATION("Observation");
    
    String metadataValue;
    
    private InsightCategory(final String metadataValue) {
        this.metadataValue = metadataValue;
    }
    
    public String getMetadataValue() {
        return this.metadataValue;
    }
    
    public static InsightCategory fromString(final String aValue) {
        if (aValue != null) {
            for (final InsightCategory insightCategory : values()) {
                if (insightCategory.getMetadataValue().toLowerCase().equals(aValue.toLowerCase())) {
                    return insightCategory;
                }
            }
        }
        throw new IllegalArgumentException("Cannot instantiate enum InsightCategory with string: " + aValue);
    }
}
