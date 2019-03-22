// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.commons.plugin.persistor;

import org.slf4j.Logger;
import com.alu.motive.hal.datacollector.commons.configuration.DataCollectorConfigurationService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public abstract class GenericPersistor implements Persistor, ApplicationContextAware
{
    private ApplicationContext springContext;
    private String pluginContext;
    private String pluginName;
    private DataCollectorConfigurationService cfgService;
    
    public GenericPersistor(final DataCollectorConfigurationService cfgService) {
        this.cfgService = cfgService;
    }
    
    public String getPluginContext() {
        return this.pluginContext;
    }
    
    public void setPluginContext(final String pluginContext) {
        this.pluginContext = pluginContext;
    }
    
    public void setPluginName(final String pluginName) {
        this.pluginName = pluginName;
    }
    
    public String getPluginName() {
        return this.pluginName;
    }
    
    @Override
    public void setApplicationContext(final ApplicationContext springContext) {
        this.springContext = springContext;
    }
    
    public ApplicationContext getApplicationContext() {
        return this.springContext;
    }
    
    public DataCollectorConfigurationService getCfgService() {
        return this.cfgService;
    }
    
    protected abstract Logger getLogger();
}
