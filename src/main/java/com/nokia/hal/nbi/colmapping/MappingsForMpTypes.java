// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.colmapping;

import java.util.Map;
import java.util.HashMap;

public class MappingsForMpTypes extends HashMap<String, MappingsForPeriodTypes>
{
    private static final long serialVersionUID = 1L;
    
    protected void addExtInsightInfo(final ColumnsMappingForNbi.ExtendedInsightInfo newElem) {
        MappingsForPeriodTypes mappings = ((HashMap<K, MappingsForPeriodTypes>)this).get(newElem.mpType);
        if (mappings == null) {
            mappings = new MappingsForPeriodTypes();
        }
        mappings.addExtInsightInfo(newElem);
        this.put(newElem.mpType, mappings);
    }
    
    protected void merge(final MappingsForMpTypes other) {
        final String otherMpType;
        final MappingsForPeriodTypes myPtMappings;
        other.entrySet().stream().forEach(entry -> {
            otherMpType = entry.getKey();
            myPtMappings = ((HashMap<K, MappingsForPeriodTypes>)this).get(otherMpType);
            if (myPtMappings == null) {
                this.put(otherMpType, (MappingsForPeriodTypes)entry.getValue());
            }
            else {
                myPtMappings.merge((MappingsForPeriodTypes)entry.getValue(), otherMpType);
            }
        });
    }
}
