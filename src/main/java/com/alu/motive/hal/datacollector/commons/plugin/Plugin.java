// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.commons.plugin;

import com.alu.motive.hal.datacollector.commons.plugin.dto.DataCollectorDTO;
import com.alu.motive.hal.datacollector.commons.plugin.dto.augmentation.AugmentingConfiguration;
import com.alu.motive.hal.datacollector.commons.plugin.parser.PluginDataParser;
import com.alu.motive.hal.datacollector.commons.configuration.NonexistentConfiguration;
import org.springframework.context.ApplicationContext;
import com.alu.motive.hal.datacollector.commons.configuration.DataCollectorConfigurationService;

public interface Plugin
{
    String getName();
    
    String getContext();
    
    boolean hasExtendedContext();
    
    ExtendedPluginContext getExtendedContext();
    
    boolean isEnabled();
    
    void autoConfigure(final DataCollectorConfigurationService p0, final ApplicationContext p1) throws NonexistentConfiguration, ClassNotFoundException, Exception;
    
    PluginDataParser borrowParserFromPool() throws Exception;
    
    void returnParser2Pool(final PluginDataParser p0) throws Exception;
    
    void stop();
    
    AugmentingConfiguration getAugmentingConfiguration();
    
    void persist(final DataCollectorDTO p0) throws Exception;
}
