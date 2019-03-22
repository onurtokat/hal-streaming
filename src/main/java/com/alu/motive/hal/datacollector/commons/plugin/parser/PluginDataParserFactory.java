// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.commons.plugin.parser;

import com.alu.motive.hal.datacollector.commons.configuration.NonexistentConfiguration;
import com.alu.motive.hal.datacollector.commons.configuration.DataCollectorConfigurationService;
import org.apache.commons.pool.BasePoolableObjectFactory;

class PluginDataParserFactory extends BasePoolableObjectFactory
{
    private Class<? extends PluginDataParser> parserClass;
    private String pluginName;
    private DataCollectorConfigurationService cfgService;
    
    public PluginDataParserFactory(final String pluginName, final DataCollectorConfigurationService cfgService) throws NonexistentConfiguration, ClassNotFoundException {
        this.parserClass = null;
        final String parserClassConf = cfgService.getNotNullProperty("plugins/" + pluginName + "/parser/class");
        final Class<? extends PluginDataParser> parserClass = (Class<? extends PluginDataParser>)Class.forName(parserClassConf);
        this.parserClass = parserClass;
        this.pluginName = pluginName;
        this.cfgService = cfgService;
    }
    
    @Override
    public synchronized Object makeObject() throws Exception {
        final PluginDataParser parser = (PluginDataParser)this.parserClass.newInstance();
        parser.setPluginName(this.pluginName);
        parser.configure(this.cfgService);
        return parser;
    }
}
