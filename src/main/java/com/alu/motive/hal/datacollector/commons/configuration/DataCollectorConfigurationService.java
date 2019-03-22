// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.commons.configuration;

import java.util.Map;
import java.util.Set;

public interface DataCollectorConfigurationService
{
    public static final String SBI_CONFIGURATIONS_PATH = "/sbi";
    public static final String SBI_PLUGINS_CONFIGURATION_PATH = "/sbi/plugins";
    
    String getDCProperty(final String p0);
    
    Integer getInteger(final String p0) throws NumberFormatException;
    
    String getNotNullProperty(final String p0) throws NonexistentConfiguration;
    
    Set<String> getConfiguredPlugins() throws NonexistentConfiguration;
    
    Map<String, String> getList(final String p0) throws NonexistentConfiguration;
    
    String getPluginProperty(final String p0, final String p1);
}
