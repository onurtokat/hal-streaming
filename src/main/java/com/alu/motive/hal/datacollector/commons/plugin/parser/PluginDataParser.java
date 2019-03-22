// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.commons.plugin.parser;

import com.alu.motive.hal.datacollector.commons.configuration.DataCollectorConfigurationService;
import com.alu.motive.hal.datacollector.commons.plugin.dto.DataCollectorDTO;

public interface PluginDataParser<T extends DataCollectorDTO>
{
    void configure(final DataCollectorConfigurationService p0);
    
    T parse(final Object p0) throws ParseException;
    
    void setPluginName(final String p0);
}
