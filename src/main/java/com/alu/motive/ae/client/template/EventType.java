// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.ae.client.template;

import java.util.AbstractMap;
import java.util.LinkedHashMap;
import com.alu.motive.ae.client.util.AEVariables;
import java.util.Map;

public enum EventType
{
    GP("GP", buildMap(new AbstractMap.SimpleEntry(AEVariables.GP_COLUMN, ModelType.DAY_PARTITION), new AbstractMap.SimpleEntry(AEVariables.START_GP_COLUMN, ModelType.START_GP_PARTITION))), 
    DAILY("DY", buildMap(new AbstractMap.SimpleEntry(AEVariables.DAY_COLUMN, ModelType.DAY_PARTITION))), 
    WEEKLY("WK", buildMap(new AbstractMap.SimpleEntry(AEVariables.WEEK_COLUMN, ModelType.WEEK_PARTITION))), 
    MONTHLY("MN", buildMap(new AbstractMap.SimpleEntry(AEVariables.MONTH_COLUMN, ModelType.MONTH_PARTITION)));
    
    private final String label;
    private final Map<AEVariables, ModelType> variables;
    
    private EventType(final String label, final Map<AEVariables, ModelType> variables) {
        this.label = label;
        this.variables = variables;
    }
    
    public Map<AEVariables, ModelType> getVariables() {
        return this.variables;
    }
    
    public static EventType fromString(final String aValue) {
        for (final EventType eventType : values()) {
            if (eventType.equals(aValue)) {
                return eventType;
            }
        }
        return null;
    }
    
    private boolean equals(final String aValue) {
        return this.label.equalsIgnoreCase(aValue) || ("\"" + this.label + "\"").equalsIgnoreCase(aValue);
    }
    
    private static Map<AEVariables, ModelType> buildMap(final Map.Entry<AEVariables, ModelType>... entries) {
        final Map<AEVariables, ModelType> variables = new LinkedHashMap<AEVariables, ModelType>();
        for (final Map.Entry<AEVariables, ModelType> entry : entries) {
            variables.put(entry.getKey(), entry.getValue());
        }
        return variables;
    }
}
