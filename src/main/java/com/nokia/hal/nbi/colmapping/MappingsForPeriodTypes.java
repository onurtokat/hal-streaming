// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.colmapping;

import java.util.Map;
import java.util.HashMap;

public class MappingsForPeriodTypes extends HashMap<PeriodType, MappingsForPhysicalNames>
{
    private static final long serialVersionUID = 1L;
    
    public void addExtInsightInfo(final ColumnsMappingForNbi.ExtendedInsightInfo newElem) {
        MappingsForPhysicalNames mappings = ((HashMap<K, MappingsForPhysicalNames>)this).get(newElem.flow);
        if (mappings == null) {
            mappings = new MappingsForPhysicalNames();
        }
        mappings.addExtInsightInfo(newElem);
        this.put(newElem.flow, mappings);
    }
    
    protected void merge(final MappingsForPeriodTypes other, final String mpType) {
        final PeriodType otherPeriodType;
        final MappingsForPhysicalNames myPhysicalNamesMappings;
        other.entrySet().stream().forEach(entry -> {
            otherPeriodType = entry.getKey();
            myPhysicalNamesMappings = ((HashMap<K, MappingsForPhysicalNames>)this).get(otherPeriodType);
            if (myPhysicalNamesMappings == null) {
                this.put(otherPeriodType, (MappingsForPhysicalNames)entry.getValue());
            }
            else {
                myPhysicalNamesMappings.merge((MappingsForPhysicalNames)entry.getValue(), mpType, otherPeriodType);
            }
        });
    }
}
