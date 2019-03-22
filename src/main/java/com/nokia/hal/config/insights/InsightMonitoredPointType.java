// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.config.insights;

public enum InsightMonitoredPointType
{
    ASSOC_DEVICE("associatedDevice"), 
    ACCESS_POINT("accessPoint"), 
    RADIO("radioInterface"), 
    MGD_DEVICE("gateway"), 
    HOME("homeNetwork");
    
    String metadataValue;
    
    private InsightMonitoredPointType(final String metadataValue) {
        this.metadataValue = metadataValue;
    }
    
    public String getMetadataValue() {
        return this.metadataValue;
    }
    
    public static InsightMonitoredPointType fromString(final String aValue) {
        if (aValue != null) {
            for (final InsightMonitoredPointType insightMonitoredPointType : values()) {
                if (insightMonitoredPointType.getMetadataValue().toLowerCase().equals(aValue.toLowerCase())) {
                    return insightMonitoredPointType;
                }
            }
        }
        throw new IllegalArgumentException("Cannot instantiate enum InsightMonitoredPointType with string: " + aValue);
    }
}
