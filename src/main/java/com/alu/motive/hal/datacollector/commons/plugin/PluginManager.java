// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.commons.plugin;

import org.slf4j.LoggerFactory;
import java.util.Date;
import java.text.SimpleDateFormat;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONArray;
import com.alu.motive.hal.datacollector.commons.plugin.exception.InvalidArgumentException;
import java.util.HashSet;
import com.alu.motive.hal.datacollector.commons.configuration.NonexistentConfiguration;
import java.util.Iterator;
import org.codehaus.jettison.json.JSONException;
import com.alu.motive.hal.datacollector.commons.plugin.impl.PluginImpl;
import org.springframework.context.ApplicationContext;
import com.alu.motive.hal.datacollector.commons.configuration.DataCollectorConfigurationService;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map;
import org.slf4j.Logger;

public class PluginManager
{
    public static final String GROUP_NAME_VALUE_SEPARATOR_IN_CONTEXT = "=";
    private static Logger logger;
    private long creationTimestamp;
    private Map<String, Set<Plugin>> pluginsMap;
    private ArrayList<ExtendedPluginContext> cGroupContextHeaders;
    private ArrayList<ExtendedPluginContext> cGroupContextParams;
    
    public PluginManager() {
        this.creationTimestamp = System.currentTimeMillis();
        this.pluginsMap = new HashMap<String, Set<Plugin>>();
        this.cGroupContextHeaders = new ArrayList<ExtendedPluginContext>();
        this.cGroupContextParams = new ArrayList<ExtendedPluginContext>();
    }
    
    public void loadPlugins(final DataCollectorConfigurationService configurationService, final ApplicationContext springContext) throws NonexistentConfiguration {
        final Set<String> configuredPluginsNames = this.obtainConfiguredPluginNames(configurationService);
        final Map<String, Set<Plugin>> configuredPluginsMap = new HashMap<String, Set<Plugin>>(configuredPluginsNames.size());
        for (final String pluginName : configuredPluginsNames) {
            try {
                final Plugin plugin = new PluginImpl(pluginName);
                plugin.autoConfigure(configurationService, springContext);
                this.configureExtendedContext(plugin);
                this.add2ConfiguredPlugins(configuredPluginsMap, plugin);
                PluginManager.logger.info("configured plugin: {}", plugin.getName());
            }
            catch (Exception e) {
                PluginManager.logger.error("Could not load plugin " + pluginName + ". ", e);
            }
        }
        this.pluginsMap = configuredPluginsMap;
        try {
            PluginManager.logger.debug("Loaded plugins: " + this.getJsonRepresentationOfInstalledPlugins());
        }
        catch (JSONException e2) {
            PluginManager.logger.debug("Could not log the loaded plugins.", e2);
        }
    }
    
    private void add2ConfiguredPlugins(final Map<String, Set<Plugin>> newPluginsMap, final Plugin plugin) {
        Set<Plugin> plugins4Context = newPluginsMap.get(plugin.getContext());
        if (plugins4Context == null) {
            plugins4Context = new HashSet<Plugin>();
        }
        plugins4Context.add(plugin);
        newPluginsMap.put(plugin.getContext(), plugins4Context);
    }
    
    private Set<String> obtainConfiguredPluginNames(final DataCollectorConfigurationService configurationService) throws NonexistentConfiguration {
        final Set<String> configuredPluginsNames = configurationService.getConfiguredPlugins();
        if (null == configuredPluginsNames || configuredPluginsNames.isEmpty()) {
            throw new NonexistentConfiguration("Can't create plugins if no configurations are given");
        }
        return configuredPluginsNames;
    }
    
    private void configureExtendedContext(final Plugin plugin) {
        if (plugin.hasExtendedContext()) {
            final ExtendedPluginContext extendedContext = plugin.getExtendedContext();
            PluginManager.logger.debug("plugin " + plugin.getName() + " has extended context: " + extendedContext);
            if (ExtendedContextType.HEADER.equals(extendedContext.getParamType())) {
                this.cGroupContextHeaders.add(extendedContext);
            }
            else if (ExtendedContextType.PARAMETER.equals(extendedContext.getParamType())) {
                this.cGroupContextParams.add(extendedContext);
            }
            else {
                PluginManager.logger.error("Invalid extended context configuration for plugin {}", plugin.getName());
            }
        }
    }
    
    public Set<Plugin> getAllPlugins() {
        final Set<Plugin> plugins = new HashSet<Plugin>();
        for (final Set<Plugin> plugins4Context : this.pluginsMap.values()) {
            if (plugins4Context != null && !plugins4Context.isEmpty()) {
                for (final Plugin plugin : plugins4Context) {
                    plugins.add(plugin);
                }
            }
        }
        return plugins;
    }
    
    public Set<Plugin> getPlugins(final String pluginContext) throws InvalidArgumentException {
        final Set<Plugin> plugins = this.pluginsMap.get(pluginContext);
        if (plugins == null || plugins.isEmpty()) {
            throw new InvalidArgumentException("pluginContext", pluginContext, "There is no plugin registered for the context: " + pluginContext);
        }
        return plugins;
    }
    
    public void terminateDataCollectors() {
        for (final Set<Plugin> plugins4Context : this.pluginsMap.values()) {
            if (plugins4Context != null) {
                for (final Plugin plugin : plugins4Context) {
                    if (plugin != null) {
                        plugin.stop();
                    }
                }
            }
        }
    }
    
    public ArrayList<ExtendedPluginContext> getcGroupContextHeaders() {
        return this.cGroupContextHeaders;
    }
    
    public ArrayList<ExtendedPluginContext> getcGroupContextParams() {
        return this.cGroupContextParams;
    }
    
    private String getJsonRepresentationOfInstalledPlugins() throws JSONException {
        final JSONArray list = new JSONArray();
        for (final Set<Plugin> plugins : this.pluginsMap.values()) {
            if (plugins != null) {
                for (final Plugin p : plugins) {
                    final JSONObject obj = new JSONObject();
                    obj.put("name", p.getName());
                    obj.put("context", p.getContext());
                    obj.put("context", p.getContext());
                    obj.put("isEnabled", p.isEnabled());
                    list.put(obj);
                }
            }
        }
        final JSONObject message = new JSONObject();
        message.put("plugins", list);
        return message.toString();
    }
    
    @Override
    protected void finalize() throws Throwable {
        PluginManager.logger.debug("Finalizing plugin manager created at {}.", new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(new Date(this.creationTimestamp)));
        this.terminateDataCollectors();
        this.pluginsMap = null;
        this.cGroupContextHeaders = null;
        this.cGroupContextParams = null;
        super.finalize();
    }
    
    static {
        PluginManager.logger = LoggerFactory.getLogger(PluginManager.class);
    }
}
