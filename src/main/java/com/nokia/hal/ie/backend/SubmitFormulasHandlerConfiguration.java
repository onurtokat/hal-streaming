// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.ie.backend;

import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import com.nokia.hal.nbi.colmapping.HalConnectorInsightsEditorNbiApi;
import com.nokia.hal.nbi.colmapping.InsightsEditorNbiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.annotation.Configuration;

@Configuration
@PropertySources({ @PropertySource({ "classpath:application.properties" }), @PropertySource({ "classpath:mas-boot.properties" }) })
public class SubmitFormulasHandlerConfiguration
{
    @Value("${nbi.host:localhost}")
    private String nbiHost;
    @Value("${nbi.username:data_superuser}")
    private String nbiUsername;
    @Value("${nbi.password:password}")
    private String nbiPassword;
    @Value("${https.port:8081}")
    private String httpsPort;
    
    @Bean
    InsightsEditorNbiApi getNbiApi() {
        return new HalConnectorInsightsEditorNbiApi(this.nbiHost, this.nbiUsername, this.nbiPassword, this.httpsPort);
    }
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
