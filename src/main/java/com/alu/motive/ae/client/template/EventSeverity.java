// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.ae.client.template;

public enum EventSeverity
{
    CRITICAL(3), 
    MAJOR(2), 
    MINOR(1), 
    WARNING(0);
    
    private final int numericCode;
    
    private EventSeverity(final int numericCode) {
        this.numericCode = numericCode;
    }
    
    public int getNumericCode() {
        return this.numericCode;
    }
    
    public static EventSeverity fromString(final String value) {
        for (final EventSeverity severity : values()) {
            if (severity.name().equalsIgnoreCase(value)) {
                return severity;
            }
        }
        return null;
    }
}
