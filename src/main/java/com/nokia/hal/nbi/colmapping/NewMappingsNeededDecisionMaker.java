// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.colmapping;

import java.util.Set;
import java.util.Map;

public interface NewMappingsNeededDecisionMaker
{
    Boolean newMappingsNeeded();
    
    void updateLastSubmissionTime();
    
    void setFilters(final Map<String, Set<PeriodType>> p0);
    
    void addFilter(final String p0, final PeriodType p1);
}
