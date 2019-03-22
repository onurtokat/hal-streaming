// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.configuration.service.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;
import com.motive.mas.configuration.service.api.Node;
import java.util.HashMap;
import java.util.Map;
import com.alu.motive.hal.datacollector.commons.configuration.NonexistentConfiguration;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.motive.mas.configuration.service.api.ConfigurationService;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import com.alu.motive.hal.datacollector.commons.configuration.DataCollectorConfigurationService;

@Component
public class DCCfgMASServiceImpl implements DataCollectorConfigurationService
{
    Logger logger;
    @Autowired
    private ConfigurationService masCS;
    
    public DCCfgMASServiceImpl() {
        this.logger = LoggerFactory.getLogger(DCCfgMASServiceImpl.class);
    }
    
    @Override
    public String getPluginProperty(final String pluginName, final String property) {
        final String propertyZookeeperPath = "/sbi/plugins/" + pluginName + "/" + property;
        final byte[] bytes = this.masCS.get(propertyZookeeperPath);
        this.logger.debug("Read configuration property '" + propertyZookeeperPath + "' and received " + new String(bytes));
        return new String(bytes);
    }
    
    @Override
    public String getDCProperty(final String property) {
        final String propertyZookeeperPath = "/sbi/" + property;
        final byte[] bytes = this.masCS.get(propertyZookeeperPath);
        final String retVal = (bytes != null) ? new String(bytes) : null;
        this.logger.debug("Read configuration property '" + propertyZookeeperPath + "' and received " + retVal);
        return retVal;
    }
    
    @Override
    public String getNotNullProperty(final String property) throws NonexistentConfiguration {
        final String prop = this.getDCProperty(property);
        if (prop == null) {
            throw new NonexistentConfiguration("null property " + property);
        }
        return prop;
    }
    
    @Override
    public Map<String, String> getList(final String path) throws NonexistentConfiguration {
        final Map<String, String> map = new HashMap<String, String>();
        final Map<String, ?> childrenTree = this.getChildrenTree(path);
        final String propertyZookeeperPath = "/sbi/" + path;
        if (childrenTree != null && !childrenTree.isEmpty()) {
            for (final String childPath : childrenTree.keySet()) {
                if (!childPath.substring(propertyZookeeperPath.length() + "/".length()).contains("/")) {
                    final Node node = (Node)childrenTree.get(childPath);
                    final String nodeName = childPath.substring(childPath.lastIndexOf("/") + 1);
                    final String nodeValue = new String(node.getData());
                    map.put(nodeName, nodeValue);
                }
            }
        }
        this.logger.debug("Found children in path '{}': {}", path, map);
        return map;
    }
    
    private Map<String, ?> getChildrenTree(final String path) throws NonexistentConfiguration {
        Map<String, Node> map = null;
        final String propertyZookeeperPath = "/sbi/" + path;
        map = this.masCS.getAllNodes(propertyZookeeperPath);
        if (map == null) {
            throw new NonexistentConfiguration(propertyZookeeperPath);
        }
        this.logger.debug("Read all nodes from '" + propertyZookeeperPath + "' and received " + map);
        return map;
    }
    
    @Override
    public Set<String> getConfiguredPlugins() throws NonexistentConfiguration {
        final Set<String> configuredPlugins = new HashSet<String>();
        final Map<String, ?> allNodes = this.getChildrenTree("plugins");
        final String pluginsParentPath = "/sbi/plugins/";
        for (String nodePath : allNodes.keySet()) {
            nodePath = nodePath.substring(pluginsParentPath.length());
            if (nodePath != null && nodePath.length() > 0) {
                final int firstSlashIdx = nodePath.indexOf("/");
                if (firstSlashIdx > -1) {
                    configuredPlugins.add(nodePath.substring(0, firstSlashIdx));
                }
                else {
                    configuredPlugins.add(nodePath);
                }
            }
        }
        this.logger.debug("Plugins configured found: " + configuredPlugins);
        return configuredPlugins;
    }
    
    @Override
    public Integer getInteger(final String propertyName) throws NumberFormatException {
        final String prop = this.getDCProperty(propertyName);
        return Integer.valueOf(prop);
    }
}
