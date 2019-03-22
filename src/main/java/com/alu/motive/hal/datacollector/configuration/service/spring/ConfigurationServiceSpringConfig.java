// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.configuration.service.spring;

import org.springframework.context.annotation.Bean;
import com.alu.motive.hal.datacollector.configuration.service.impl.DCCfgMASServiceImpl;
import com.alu.motive.hal.datacollector.commons.configuration.DataCollectorConfigurationService;
import com.motive.mas.configuration.service.api.ConfigurationServiceContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Configuration;

@Configuration
@PropertySource({ "classpath:mas-boot.properties" })
@Import({ ConfigurationServiceContext.class })
public class ConfigurationServiceSpringConfig
{
    @Bean
    public DataCollectorConfigurationService dataCollectorConfigurationService() {
        return new DCCfgMASServiceImpl();
    }
}
