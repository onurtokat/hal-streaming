// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.ie.backend;

import org.slf4j.LoggerFactory;
import com.nokia.hal.config.insights.InsightFlow;
import com.nokia.hal.config.insights.InsightDomain;
import com.nokia.hal.config.insights.avro.WifiAvroSchemas;
import com.nokia.hal.nbi.connector.model.SubmittedFormulas;
import com.nokia.hal.config.insights.hql.ViewGenerator;
import com.nokia.hal.nbi.connector.model.SubmittedFormula;
import com.nokia.hal.nbi.colmapping.NewMappingsNeededDecisionMakerForMemory;
import java.util.List;
import com.motive.mas.configuration.service.api.ConfigurationService;
import com.nokia.hal.config.insights.avro.AvroSchemaFileGenerator;
import com.nokia.hal.config.insights.hql.HqlFileGenerator;
import com.nokia.hal.nbi.colmapping.ColumnsMappingForNbi;
import com.nokia.hal.nbi.colmapping.NewMappingsNeededDecisionMaker;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.nokia.hal.nbi.colmapping.InsightsEditorNbiApi;
import org.slf4j.Logger;

public class StreamingNewInsightsProcessingHandler
{
    private static final Logger LOG;
    private InsightsEditorNbiApi nbiApi;
    private AnnotationConfigApplicationContext ctx;
    private NewMappingsNeededDecisionMaker decisionMaker;
    private ColumnsMappingForNbi mapper;
    private HqlFileGenerator hqlGenerator;
    private AvroSchemaFileGenerator avroGenerator;
    private ConfigurationService masCS;
    private List<String> hqlTemplateFileNames;
    private String hdfsUrl;
    private String hqlTemplateFileFolder;
    private String hqlFileFolder;
    private String avscTemplateFileFolder;
    private String avscFileFolder;
    private String viewsTemplatesFolder;
    private List<String> createViewStatements;
    
    public StreamingNewInsightsProcessingHandler() {
        this.ctx = new AnnotationConfigApplicationContext((Class<?>[])new Class[] { StreamingNewInsightsProcessingHandlerConfiguration.class });
        this.nbiApi = this.ctx.getBean(InsightsEditorNbiApi.class);
        this.masCS = this.ctx.getBean(ConfigurationService.class);
        this.decisionMaker = this.ctx.getBean(NewMappingsNeededDecisionMakerForMemory.class);
        this.mapper = this.ctx.getBean(ColumnsMappingForNbi.class);
        this.hqlGenerator = this.ctx.getBean(HqlFileGenerator.class);
        this.avroGenerator = this.ctx.getBean(AvroSchemaFileGenerator.class);
    }
    
    public StreamingNewInsightsProcessingHandler setHqlTemplateFileNames(final List<String> hqlTemplateFileNames) {
        this.hqlTemplateFileNames = hqlTemplateFileNames;
        return this;
    }
    
    public StreamingNewInsightsProcessingHandler setHdfsUrl(final String hdfsUrl) {
        this.hdfsUrl = hdfsUrl;
        return this;
    }
    
    public StreamingNewInsightsProcessingHandler setHqlTemplateFileFolder(final String hqlTemplateFileFolder) {
        this.hqlTemplateFileFolder = hqlTemplateFileFolder;
        return this;
    }
    
    public StreamingNewInsightsProcessingHandler setHqlFileFolder(final String hqlFileFolder) {
        this.hqlFileFolder = hqlFileFolder;
        return this;
    }
    
    public StreamingNewInsightsProcessingHandler setAvscTemplateFileFolder(final String avscTemplateFileFolder) {
        this.avscTemplateFileFolder = avscTemplateFileFolder;
        return this;
    }
    
    public StreamingNewInsightsProcessingHandler setAvscFileFolder(final String avscFileFolder) {
        this.avscFileFolder = avscFileFolder;
        return this;
    }
    
    public StreamingNewInsightsProcessingHandler setViewsTemplatesFolder(final String viewsTemplatesFolder) {
        this.viewsTemplatesFolder = viewsTemplatesFolder;
        return this;
    }
    
    public Boolean process() {
        Boolean newInsightsProcessed = Boolean.FALSE;
        try {
            StreamingNewInsightsProcessingHandler.LOG.info("deciding whether there are new Insights");
            if (this.decisionMaker.newMappingsNeeded()) {
                StreamingNewInsightsProcessingHandler.LOG.info("new Insights were created");
                this.decisionMaker.updateLastSubmissionTime();
                final SubmittedFormulas newInsights = this.nbiApi.getFormulas(null, null);
                this.mapper.processNewInsights(newInsights);
                this.hqlGenerator.setFormulas(newInsights);
                this.hqlGenerator.process(this.hdfsUrl, this.hqlTemplateFileFolder, this.hdfsUrl + this.hqlFileFolder, this.hqlTemplateFileNames);
                this.avroGenerator.process(newInsights, this.hdfsUrl, this.avscFileFolder, this.avscTemplateFileFolder, StreamingNewInsightsProcessingHandler::filterStreaming);
                this.createViewStatements = ViewGenerator.createViewsStatements(newInsights, this.viewsTemplatesFolder);
                newInsightsProcessed = Boolean.TRUE;
            }
        }
        catch (Exception ex) {
            StreamingNewInsightsProcessingHandler.LOG.error("error occurred while processing new insights formulas. Details follow...");
            ColumnsMappingForNbi.logException(StreamingNewInsightsProcessingHandler.LOG, ex);
        }
        return newInsightsProcessed;
    }
    
    public List<String> getCreateViewsStatements() {
        return this.createViewStatements;
    }
    
    private static Boolean filterStreaming(final WifiAvroSchemas.SchemaNames sn) {
        return sn.getDomain() == InsightDomain.WIFI && (sn.getFlow() == InsightFlow.SLIDING_WINDOW || sn.getFlow() == InsightFlow.STREAMING);
    }
    
    public void close() {
        StreamingNewInsightsProcessingHandler.LOG.info("Closing the Spring context.");
        this.masCS.unregisterAllListeners();
        this.ctx.close();
    }
    
    static {
        LOG = LoggerFactory.getLogger(StreamingNewInsightsProcessingHandler.class);
    }
}
