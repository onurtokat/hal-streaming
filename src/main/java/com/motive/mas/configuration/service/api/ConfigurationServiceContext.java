// 
// Decompiled by Procyon v0.5.30
// 

package com.motive.mas.configuration.service.api;

import com.motive.mas.configuration.service.api.impl.ConfigurationServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Configuration;

@Configuration
@PropertySource({ "classpath:mas-boot.properties" })
public class ConfigurationServiceContext
{
    private static ConfigurationService configurationService;
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    
    @Bean(initMethod = "init", destroyMethod = "close")
    public ConfigurationService configurationService() {
        if (ConfigurationServiceContext.configurationService == null) {
            ConfigurationServiceContext.configurationService = new ConfigurationServiceImpl();
        }
        return ConfigurationServiceContext.configurationService;
    }
}
