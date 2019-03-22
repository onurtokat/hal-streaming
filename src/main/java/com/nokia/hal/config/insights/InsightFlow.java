// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.config.insights;

public enum InsightFlow
{
    STREAMING("streaming"), 
    SLIDING_WINDOW("sliding_window"), 
    DAILY("daily");
    
    String metadataValue;
    
    private InsightFlow(final String metadataValue) {
        this.metadataValue = metadataValue;
    }
    
    public String getMetadataValue() {
        return this.metadataValue;
    }
    
    public static InsightFlow fromString(final String aValue) {
        if (aValue != null) {
            for (final InsightFlow insightFlow : values()) {
                if (insightFlow.getMetadataValue().toLowerCase().equals(aValue.toLowerCase())) {
                    return insightFlow;
                }
            }
        }
        throw new IllegalArgumentException("Cannot instantiate enum InsightFlow with string: " + aValue);
    }
}
