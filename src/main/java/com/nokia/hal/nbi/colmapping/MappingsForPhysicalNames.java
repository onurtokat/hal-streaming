// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.colmapping;

import org.slf4j.LoggerFactory;
import java.util.Map;
import org.slf4j.Logger;
import java.util.HashMap;

public class MappingsForPhysicalNames extends HashMap<String, InsightAdditionalInfo>
{
    private static final Logger LOG;
    private static final long serialVersionUID = 1L;
    
    public void addExtInsightInfo(final ColumnsMappingForNbi.ExtendedInsightInfo newElem) {
        final InsightAdditionalInfo addInfo = ((HashMap<K, InsightAdditionalInfo>)this).get(newElem.physicalColName);
        if (addInfo == null) {
            this.put(newElem.physicalColName, newElem.addInfo);
        }
        else {
            MappingsForPhysicalNames.LOG.warn("Adding mapping for Insight '{}/{}/{}' which already existed. This is unexpected. There should be only one mapping for the tuple (MPType, Flow, PhysicalColumn).", newElem.mpType, newElem.flow.toString(), newElem.physicalColName);
        }
    }
    
    protected void merge(final MappingsForPhysicalNames other, final String mpType, final PeriodType flow) {
        final InsightAdditionalInfo \u0131nsightAdditionalInfo;
        other.entrySet().stream().filter(entry -> this.get(entry.getKey()) == null).forEach(entry -> \u0131nsightAdditionalInfo = this.put(entry.getKey(), (InsightAdditionalInfo)entry.getValue()));
        other.entrySet().stream().filter(entry -> this.get(entry.getKey()) != null).forEach(entry -> MappingsForPhysicalNames.LOG.warn("Merging mappings for Insight '{}/{}/{}' have conflicting. This is unexpected. There should be only one mapping for the tuple (MPType, Flow, PhysicalColumn).", mpType, flow.toString(), entry.getKey()));
    }
    
    static {
        LOG = LoggerFactory.getLogger(MappingsForPhysicalNames.class);
    }
}
