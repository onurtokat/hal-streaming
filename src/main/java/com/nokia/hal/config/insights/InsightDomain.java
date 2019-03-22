// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.config.insights;

public enum InsightDomain
{
    WIFI("WiFi");
    
    String metadataValue;
    
    private InsightDomain(final String metadataValue) {
        this.metadataValue = metadataValue;
    }
    
    public String getMetadataValue() {
        return this.metadataValue;
    }
    
    public static InsightDomain fromString(final String aValue) {
        if (aValue != null) {
            for (final InsightDomain insightDomain : values()) {
                if (insightDomain.getMetadataValue().toLowerCase().equals(aValue.toLowerCase())) {
                    return insightDomain;
                }
            }
        }
        throw new IllegalArgumentException("Cannot instantiate enum InsightDomain with string: " + aValue);
    }
}
