// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.ie.backend;

import org.slf4j.LoggerFactory;
import com.nokia.hal.config.insights.avro.AvroSchemaFileGenerator;
import com.nokia.hal.nbi.connector.model.SubmittedFormula;
import java.util.List;
import com.nokia.hal.config.insights.hql.HqlFileGenerator;
import com.nokia.hal.nbi.colmapping.NewMappingsNeededDecisionMakerForMemory;
import com.nokia.hal.nbi.colmapping.NewMappingsNeededDecisionMaker;
import com.nokia.hal.nbi.colmapping.HalConnectorInsightsEditorNbiApi;
import com.nokia.hal.nbi.colmapping.InsightsEditorNbiApi;
import com.nokia.hal.nbi.colmapping.PeriodType;
import com.nokia.hal.nbi.colmapping.ColumnsMappingForNbi;
import org.springframework.context.annotation.Bean;
import com.nokia.hal.nbi.colmapping.ZooKeeperColumnsMapNbiStore;
import com.nokia.hal.nbi.colmapping.ColumnsMappingNbiStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import com.motive.mas.configuration.service.api.ConfigurationService;
import org.slf4j.Logger;
import com.motive.mas.configuration.service.api.ConfigurationServiceContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.annotation.Configuration;

@Configuration
@PropertySources({ @PropertySource({ "classpath:application.properties" }), @PropertySource({ "classpath:mas-boot.properties" }) })
@Import({ ConfigurationServiceContext.class })
public class StreamingNewInsightsProcessingHandlerConfiguration
{
    private static final Logger LOG;
    @Autowired
    private ConfigurationService masCS;
    @Value("${nbi.host:localhost}")
    private String nbiHost;
    @Value("${nbi.username:data_superuser}")
    private String nbiUsername;
    @Value("${nbi.password:password}")
    private String nbiPassword;
    @Value("${https.port:8081}")
    private String httpsPort;
    
    @Bean
    ColumnsMappingNbiStore getMappingsStorage() {
        final ZooKeeperColumnsMapNbiStore zoo = new ZooKeeperColumnsMapNbiStore();
        zoo.setMasConfigurationService(this.masCS);
        return zoo;
    }
    
    @Bean
    ColumnsMappingForNbi getColumnsMapper() {
        final ColumnsMappingForNbi colMapper = new ColumnsMappingForNbi();
        colMapper.setMappingsStorage(this.getMappingsStorage());
        colMapper.addFilter("WiFi", PeriodType.STREAMING);
        colMapper.addFilter("WiFi", PeriodType.SLIDING_WINDOW);
        return colMapper;
    }
    
    @Bean
    InsightsEditorNbiApi getNbiApi() {
        StreamingNewInsightsProcessingHandlerConfiguration.LOG.debug("Setting for nbi connector host: {}, user: {}, password: {}, port: {}", this.nbiHost, this.nbiUsername, this.nbiPassword, this.httpsPort);
        return new HalConnectorInsightsEditorNbiApi(this.nbiHost, this.nbiUsername, this.nbiPassword, this.httpsPort);
    }
    
    @Bean
    NewMappingsNeededDecisionMaker getDecisionMaker() {
        final NewMappingsNeededDecisionMaker dm = new NewMappingsNeededDecisionMakerForMemory(this.getNbiApi());
        dm.addFilter("WiFi", PeriodType.STREAMING);
        dm.addFilter("WiFi", PeriodType.SLIDING_WINDOW);
        return dm;
    }
    
    @Bean
    HqlFileGenerator getHqlFileGenerator() {
        return new HqlFileGenerator(null, 50, 50, 50);
    }
    
    @Bean
    AvroSchemaFileGenerator getAvroGenerator() {
        return new AvroSchemaFileGenerator();
    }
    
    static {
        LOG = LoggerFactory.getLogger(StreamingNewInsightsProcessingHandlerConfiguration.class);
    }
}
