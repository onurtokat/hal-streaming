// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.colmapping;

public enum PeriodType
{
    GP("intraday", "GP"), 
    STREAMING("streaming", "streaming"), 
    SLIDING_WINDOW("sliding_window", "sliding_window"), 
    DY("daily", "daily"), 
    WK("weekly", "WK"), 
    MN("monthly", "MN");
    
    private String label;
    private String valueInTemplates;
    
    private PeriodType(final String label, final String actualValueInTemplates) {
        this.label = label;
        this.valueInTemplates = actualValueInTemplates;
    }
    
    @Override
    public String toString() {
        return this.label;
    }
    
    public String getValueInTemplates() {
        return this.valueInTemplates;
    }
    
    public static PeriodType fromString(final String valueInTemplates) {
        if (valueInTemplates.equalsIgnoreCase(PeriodType.GP.getValueInTemplates())) {
            return PeriodType.GP;
        }
        if (valueInTemplates.equalsIgnoreCase(PeriodType.STREAMING.getValueInTemplates())) {
            return PeriodType.STREAMING;
        }
        if (valueInTemplates.equalsIgnoreCase(PeriodType.SLIDING_WINDOW.getValueInTemplates())) {
            return PeriodType.SLIDING_WINDOW;
        }
        if (valueInTemplates.equalsIgnoreCase(PeriodType.DY.getValueInTemplates())) {
            return PeriodType.DY;
        }
        if (valueInTemplates.equalsIgnoreCase(PeriodType.WK.getValueInTemplates())) {
            return PeriodType.WK;
        }
        if (valueInTemplates.equalsIgnoreCase(PeriodType.MN.getValueInTemplates())) {
            return PeriodType.MN;
        }
        return null;
    }
}
