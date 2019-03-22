// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.colmapping;

import org.springframework.context.annotation.Bean;
import com.motive.mas.configuration.service.api.ConfigurationServiceContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Configuration;

@Configuration
@PropertySource({ "application.properties" })
@Import({ ConfigurationServiceContext.class })
public class ColumnsMappingForNbiConfiguration
{
    @Bean
    ColumnsMappingForNbi getColumnsMapper() {
        return new ColumnsMappingForNbi();
    }
    
    @Bean
    InsightsEditorNbiApi getNbiApi() {
        return new SwaggerInsightsEditorNbiApi("");
    }
}
