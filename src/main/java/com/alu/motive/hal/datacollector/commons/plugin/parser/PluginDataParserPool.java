// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.commons.plugin.parser;

import org.slf4j.LoggerFactory;
import org.apache.commons.pool.PoolableObjectFactory;
import com.alu.motive.hal.datacollector.commons.configuration.DataCollectorConfigurationService;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.slf4j.Logger;

public class PluginDataParserPool
{
    private static final Logger log;
    static final int DEFAULT_MAX_IDLE_PARSERS = 2000;
    static final int DEFAULT_MAX_ACTIVE_PARSERS = 4000;
    private final PluginDataParserFactory factory;
    private final GenericObjectPool<PluginDataParser> pool;
    
    public PluginDataParserPool(final String pluginName, final DataCollectorConfigurationService cfgService) throws Exception {
        int maxIdleParsers = cfgService.getInteger("plugins/" + pluginName + "/parser/pool/maxIdleParsersInPool");
        int maxActiveParsers = cfgService.getInteger("plugins/" + pluginName + "/parser/pool/maxActiveParsersInPool");
        final String methodWithParams = "PluginDataParserPool(maxIdle=" + maxIdleParsers + ", maxActive=" + maxActiveParsers + "): ";
        this.factory = new PluginDataParserFactory(pluginName, cfgService);
        if (maxActiveParsers <= 0 || maxIdleParsers <= 0) {
            maxIdleParsers = 2000;
            maxActiveParsers = 4000;
            PluginDataParserPool.log.warn(methodWithParams + ((maxIdleParsers == 0) ? "Max Idle Parser is 0" : "Max Active Parsers is 0") + " - so setting the pool setting to default values [maxIdle:" + maxIdleParsers + ", maxActive:" + maxActiveParsers + "]");
        }
        PluginDataParserPool.log.info(methodWithParams + "Creating parser pool with [maxIdle:" + maxIdleParsers + ", maxActive:" + maxActiveParsers + "] parsers");
        this.pool = (GenericObjectPool<PluginDataParser>)new GenericObjectPool(this.factory, maxActiveParsers, (byte)1, 5000L, maxIdleParsers);
        for (int i = 0; i < maxIdleParsers; ++i) {
            try {
                this.pool.addObject();
            }
            catch (Exception ex) {
                PluginDataParserPool.log.error("{} Failed to add parser to the pool: ", methodWithParams, ex);
                throw ex;
            }
        }
    }
    
    public void reconfigurePool(final int maxIdleParsers, final int maxActiveParsers) {
        if (maxIdleParsers > 0 && this.pool.getMaxIdle() != maxIdleParsers) {
            this.pool.setMaxIdle(maxIdleParsers);
        }
        if (maxActiveParsers > 0 && this.pool.getMaxActive() != maxActiveParsers) {
            this.pool.setMaxActive(maxActiveParsers);
        }
    }
    
    public PluginDataParser borrowParser() throws Exception {
        final PluginDataParser parser = (PluginDataParser)this.pool.borrowObject();
        PluginDataParserPool.log.debug("Borrow parser " + parser.getClass().getName() + " from pool.");
        return parser;
    }
    
    public void returnParser(final PluginDataParser parser) throws Exception {
        PluginDataParserPool.log.debug("Returning parser " + parser.getClass().getName() + " to pool.");
        this.pool.returnObject(parser);
    }
    
    static {
        log = LoggerFactory.getLogger(PluginDataParserPool.class);
    }
}
