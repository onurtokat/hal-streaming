// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.colmapping;

import java.io.IOException;
import java.util.Map;
import com.motive.mas.configuration.service.api.ConfigurationService;

public interface ColumnsMappingNbiStore
{
    void setMasConfigurationService(final ConfigurationService p0);
    
    void save(final String p0, final PeriodType p1, final Integer p2, final Map<String, InsightAdditionalInfo> p3) throws Exception;
    
    Map<String, InsightAdditionalInfo> load(final String p0, final PeriodType p1, final Integer p2) throws IOException;
}
