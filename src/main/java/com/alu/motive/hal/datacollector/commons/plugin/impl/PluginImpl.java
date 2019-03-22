// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.commons.plugin.impl;

import org.slf4j.LoggerFactory;
import com.alu.motive.hal.datacollector.commons.plugin.dto.DataCollectorDTO;
import java.util.Date;
import java.text.SimpleDateFormat;
import com.alu.motive.hal.datacollector.commons.plugin.parser.PluginDataParser;
import java.util.Iterator;
import java.util.Map;
import com.alu.motive.hal.datacollector.commons.plugin.ExtendedContextType;
import com.alu.motive.hal.datacollector.commons.configuration.NonexistentConfiguration;
import com.alu.motive.hal.datacollector.commons.plugin.persistor.GenericPersistor;
import org.springframework.context.ApplicationContext;
import com.alu.motive.hal.datacollector.commons.configuration.DataCollectorConfigurationService;
import com.alu.motive.hal.datacollector.commons.plugin.dto.augmentation.AugmentingConfiguration;
import com.alu.motive.hal.datacollector.commons.plugin.persistor.Persistor;
import com.alu.motive.hal.datacollector.commons.plugin.dto.DTOConverter;
import com.alu.motive.hal.datacollector.commons.plugin.parser.PluginDataParserPool;
import com.alu.motive.hal.datacollector.commons.plugin.ExtendedPluginContext;
import org.slf4j.Logger;
import com.alu.motive.hal.datacollector.commons.plugin.Plugin;

public class PluginImpl implements Plugin
{
    private static final Logger logger;
    private final long creationTime;
    private final String pluginName;
    private String contextPath;
    private ExtendedPluginContext extendedContext;
    private PluginDataParserPool parsersPool;
    private DTOConverter dtoConverter;
    private Persistor persistor;
    AugmentingConfiguration augmentingConfiguration;
    boolean enabled;
    
    public PluginImpl(final String name) {
        this.creationTime = System.currentTimeMillis();
        this.enabled = false;
        this.pluginName = name;
    }
    
    @Override
    public void autoConfigure(final DataCollectorConfigurationService configurationService, final ApplicationContext springContext) throws Exception {
        this.contextPath = configurationService.getNotNullProperty("plugins/" + this.pluginName + "/context");
        final String isEnabled = configurationService.getDCProperty("plugins/" + this.pluginName + "/enabled");
        this.enabled = (isEnabled == null || Boolean.parseBoolean(isEnabled));
        this.configureExtendedContext(configurationService);
        this.configureParsersPools(configurationService);
        this.configureConverter(configurationService);
        this.configurePersistor(configurationService, springContext);
        this.configureAugmentation(configurationService);
    }
    
    private void configureAugmentation(final DataCollectorConfigurationService configurationService) {
        (this.augmentingConfiguration = new AugmentingConfiguration(this.pluginName)).configureAugmentingRequestParams(configurationService);
        this.augmentingConfiguration.configureAugmentingHeaderParams(configurationService);
        this.augmentingConfiguration.configureAugmentingRuntimeParams(configurationService);
    }
    
    private void configurePersistor(final DataCollectorConfigurationService configurationService, final ApplicationContext springContext) throws Exception {
        final String persistorClass = configurationService.getNotNullProperty("plugins/" + this.pluginName + "/output/class");
        final Class<?> clazz = Class.forName(persistorClass);
        final GenericPersistor persistor = (GenericPersistor)clazz.getConstructor(DataCollectorConfigurationService.class).newInstance(configurationService);
        persistor.setApplicationContext(springContext);
        persistor.setPluginContext(this.getContext());
        persistor.setPluginName(this.pluginName);
        persistor.autoConfigure();
        this.persistor = persistor;
    }
    
    private void configureConverter(final DataCollectorConfigurationService cfgService) throws Exception {
        final String converterClass = cfgService.getDCProperty("plugins/" + this.pluginName + "/dtoConverter/class");
        try {
            if (converterClass != null) {
                final Class<?> clazz = Class.forName(converterClass);
                this.dtoConverter = (DTOConverter)clazz.newInstance();
            }
        }
        catch (Exception e) {
            throw new Exception("Could not configure converter " + converterClass + "for plugin " + this.pluginName, e);
        }
    }
    
    private void configureExtendedContext(final DataCollectorConfigurationService configurationService) {
        final String extCtxCfgPath = "plugins/" + this.pluginName + "/context/extendedCtx";
        try {
            configurationService.getList(extCtxCfgPath);
        }
        catch (NonexistentConfiguration e) {
            PluginImpl.logger.info("No extended context configured for plugin " + this.pluginName);
            PluginImpl.logger.debug("No extended context configured for plugin " + this.pluginName, e);
            return;
        }
        String cgroupName = null;
        String cgroupValue = null;
        ExtendedContextType cgroupType = null;
        for (final ExtendedContextType type : ExtendedContextType.values()) {
            final String extCtxTypeCfgPath = extCtxCfgPath + "/" + type;
            try {
                final Map<String, String> typeParameters = configurationService.getList(extCtxTypeCfgPath);
                if (typeParameters != null && !typeParameters.isEmpty()) {
                    for (final String paramName : typeParameters.keySet()) {
                        final String paramValue = configurationService.getDCProperty(extCtxTypeCfgPath + "/" + paramName);
                        if (paramValue != null) {
                            cgroupName = paramName;
                            cgroupValue = paramValue;
                            cgroupType = type;
                            break;
                        }
                    }
                }
                if (cgroupName != null && cgroupValue != null) {
                    this.extendedContext = new ExtendedPluginContext(this.contextPath, cgroupType, cgroupName, cgroupValue);
                    break;
                }
            }
            catch (NonexistentConfiguration e2) {
                PluginImpl.logger.info("No " + type.toString() + " parameter configured for " + this.pluginName);
                PluginImpl.logger.debug("No " + type.toString() + " parameter configured for " + this.pluginName, e2);
            }
        }
    }
    
    @Override
    public String getContext() {
        String pluginKey = null;
        if (this.hasExtendedContext()) {
            pluginKey = this.getExtendedContext().getContext();
        }
        else {
            pluginKey = this.contextPath;
        }
        return pluginKey;
    }
    
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
    
    private void configureParsersPools(final DataCollectorConfigurationService cfgService) throws Exception {
        if (this.parsersPool == null) {
            this.parsersPool = new PluginDataParserPool(this.pluginName, cfgService);
        }
        else {
            final int maxIdleParsers = cfgService.getInteger("plugins/" + this.pluginName + "/parser/pool/maxIdleParsersInPool");
            final int maxActiveParsers = cfgService.getInteger("plugins/" + this.pluginName + "/parser/pool/maxActiveParsersInPool");
            this.parsersPool.reconfigurePool(maxIdleParsers, maxActiveParsers);
        }
    }
    
    @Override
    public String getName() {
        return this.pluginName;
    }
    
    @Override
    public boolean hasExtendedContext() {
        return this.extendedContext != null;
    }
    
    @Override
    public ExtendedPluginContext getExtendedContext() {
        return this.extendedContext;
    }
    
    @Override
    public PluginDataParser borrowParserFromPool() throws Exception {
        return this.parsersPool.borrowParser();
    }
    
    @Override
    public void returnParser2Pool(final PluginDataParser parser) throws Exception {
        this.parsersPool.returnParser(parser);
    }
    
    @Override
    public void stop() {
        this.enabled = false;
    }
    
    @Override
    public AugmentingConfiguration getAugmentingConfiguration() {
        return this.augmentingConfiguration;
    }
    
    @Override
    protected void finalize() throws Throwable {
        PluginImpl.logger.debug("Finalizing plugin: {}-{} created at {}", this.getName(), this.getContext(), new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(new Date(this.creationTime)));
        this.enabled = false;
        this.persistor = null;
        this.augmentingConfiguration = null;
        this.parsersPool = null;
        super.finalize();
    }
    
    @Override
    public void persist(final DataCollectorDTO dto) throws Exception {
        PluginImpl.logger.info("Persisting to data store...");
        DataCollectorDTO dto2Persist = dto;
        if (this.dtoConverter != null) {
            PluginImpl.logger.info("Converting {} using {}.", dto.getClass().getName(), this.dtoConverter.getClass().getName());
            dto2Persist = this.dtoConverter.convert(dto);
        }
        this.persistor.persist(dto2Persist);
        PluginImpl.logger.debug("Persisted dcDTO: {} in DataStore. ", dto2Persist.toString());
    }
    
    static {
        logger = LoggerFactory.getLogger(PluginImpl.class);
    }
}
