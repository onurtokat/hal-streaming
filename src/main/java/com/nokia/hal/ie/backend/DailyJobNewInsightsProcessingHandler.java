// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.ie.backend;

import java.util.Arrays;
import org.slf4j.LoggerFactory;
import com.nokia.hal.config.insights.InsightFlow;
import com.nokia.hal.config.insights.InsightDomain;
import com.nokia.hal.config.insights.avro.WifiAvroSchemas;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import java.io.IOException;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import com.nokia.hal.config.insights.hql.ViewGenerator;
import java.util.Iterator;
import java.util.ArrayList;
import com.nokia.hal.nbi.colmapping.exception.HalColumnsMappingInsightsEditorCommunicationException;
import com.nokia.hal.nbi.colmapping.exception.HalColumnsMappingSaveToZookeeperException;
import com.nokia.hal.nbi.connector.model.SubmittedFormulas;
import com.nokia.hal.nbi.connector.model.SubmittedFormula;
import com.motive.mas.configuration.service.api.ConfigurationService;
import com.nokia.hal.config.insights.avro.AvroSchemaFileGenerator;
import com.nokia.hal.config.insights.hql.HqlFileGenerator;
import com.nokia.hal.nbi.colmapping.ColumnsMappingForNbi;
import com.nokia.hal.nbi.colmapping.NewMappingsNeededDecisionMakerForHdfs;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.nokia.hal.nbi.colmapping.InsightsEditorNbiApi;
import java.util.List;
import org.slf4j.Logger;

public class DailyJobNewInsightsProcessingHandler
{
    private static final Logger LOG;
    protected static final String viewsUpdateFile = "updateViews.hql";
    private static final List<String> hqlTemplateFileNames;
    private InsightsEditorNbiApi nbiApi;
    private AnnotationConfigApplicationContext ctx;
    private NewMappingsNeededDecisionMakerForHdfs decisionMaker;
    private ColumnsMappingForNbi mapper;
    private HqlFileGenerator hqlGenerator;
    private AvroSchemaFileGenerator avroGenerator;
    private ConfigurationService masCS;
    
    public DailyJobNewInsightsProcessingHandler() {
        this.ctx = new AnnotationConfigApplicationContext((Class<?>[])new Class[] { DailyJobNewInsightsProcessingHandlerConfiguration.class });
        this.nbiApi = this.ctx.getBean(InsightsEditorNbiApi.class);
        this.masCS = this.ctx.getBean(ConfigurationService.class);
        this.decisionMaker = this.ctx.getBean(NewMappingsNeededDecisionMakerForHdfs.class);
        this.mapper = this.ctx.getBean(ColumnsMappingForNbi.class);
        this.hqlGenerator = this.ctx.getBean(HqlFileGenerator.class);
        this.avroGenerator = this.ctx.getBean(AvroSchemaFileGenerator.class);
    }
    
    public void process(final String hdfsURL, final String hqlTemplateFolder, final String hqlFileFolder, final String avscTemplateFolder, final String avscFileFolder, final String viewsTemplateFolder, final String viewsUpdateHqlFile) throws HalColumnsMappingSaveToZookeeperException, HalColumnsMappingInsightsEditorCommunicationException {
        DailyJobNewInsightsProcessingHandler.LOG.info("deciding whether there are new Insights");
        if (this.decisionMaker.newMappingsNeeded()) {
            DailyJobNewInsightsProcessingHandler.LOG.info("new Insights were created");
            this.decisionMaker.updateLastSubmissionTime();
            final SubmittedFormulas newInsights = this.nbiApi.getFormulas(null, null);
            this.mapper.processNewInsights(newInsights);
            this.hqlGenerator.setFormulas(newInsights);
            this.hqlGenerator.process(hdfsURL, hqlTemplateFolder, hqlFileFolder, DailyJobNewInsightsProcessingHandler.hqlTemplateFileNames);
            this.avroGenerator.process(newInsights, hdfsURL, avscFileFolder, avscTemplateFolder, DailyJobNewInsightsProcessingHandler::filterDaily);
            createViewsStatementsAndSaveToHdfs(newInsights, hdfsURL + viewsTemplateFolder, viewsUpdateHqlFile);
        }
    }
    
    private static List<SubmittedFormula> getListFromSubmittedFormulas(final SubmittedFormulas formulas) {
        final List<SubmittedFormula> list = new ArrayList<SubmittedFormula>();
        for (final SubmittedFormula sf : formulas) {
            list.add(sf);
        }
        return list;
    }
    
    private static void createViewsStatementsAndSaveToHdfs(final SubmittedFormulas newInsights, final String viewsTemplateFolder, final String viewsUpdateHqlFile) {
        try {
            DailyJobNewInsightsProcessingHandler.LOG.info("starting friendly names views creation");
            final List<SubmittedFormula> newInsightsAsList = getListFromSubmittedFormulas(newInsights);
            final List<String> createViewsStmts = ViewGenerator.createViewsStatements(newInsightsAsList, viewsTemplateFolder);
            DailyJobNewInsightsProcessingHandler.LOG.debug("generated views hqls are:\n{}", createViewsStmts.stream().collect((Collector<? super Object, ?, String>)Collectors.joining("\n")));
            DailyJobNewInsightsProcessingHandler.LOG.info("saving generated views hqls into HDFS file: " + viewsUpdateHqlFile);
            saveToHdfs(viewsUpdateHqlFile, createViewsStmts);
            DailyJobNewInsightsProcessingHandler.LOG.info("finishing friendly names views creation");
        }
        catch (IOException ex) {
            DailyJobNewInsightsProcessingHandler.LOG.error("creating friendly names views");
            ColumnsMappingForNbi.logException(DailyJobNewInsightsProcessingHandler.LOG, ex);
        }
    }
    
    protected static void saveToHdfs(final String viewsUpdateHqlFile, final List<String> updateViewsStatements) throws IOException {
        final Path fullPath = new Path(viewsUpdateHqlFile);
        DailyJobNewInsightsProcessingHandler.LOG.info("Writing " + viewsUpdateHqlFile + " to HDFS.");
        final Configuration conf = new Configuration();
        final FileSystem fs = FileSystem.get(conf);
        final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fs.create(fullPath, true)));
        final String outputHql = updateViewsStatements.stream().collect((Collector<? super Object, ?, String>)Collectors.joining("\n", "", ""));
        bw.write(outputHql);
        bw.close();
    }
    
    private static Boolean filterDaily(final WifiAvroSchemas.SchemaNames sn) {
        return sn.getDomain() == InsightDomain.WIFI && sn.getFlow() == InsightFlow.DAILY;
    }
    
    public void close() {
        DailyJobNewInsightsProcessingHandler.LOG.info("Closing the Spring context.");
        this.masCS.unregisterAllListeners();
        this.ctx.close();
    }
    
    public static void main(final String[] args) throws HalColumnsMappingSaveToZookeeperException, HalColumnsMappingInsightsEditorCommunicationException {
        final DailyJobNewInsightsProcessingHandler ph = new DailyJobNewInsightsProcessingHandler();
        final String hdfsUrl = args[0];
        final String appFileFolder = args[1];
        final String hqlTemplateFolder = args[2];
        final String avscTemplateFolder = args[3];
        final String avscFileFolder = args[4];
        final String viewsTemplateFolder = args[5];
        final String hqlFileFolder = appFileFolder + "/hqls";
        final String viewsUpdateHqlFile = hqlFileFolder + "/" + "updateViews.hql";
        DailyJobNewInsightsProcessingHandler.LOG.info("input param hdfsUrl: " + hdfsUrl);
        DailyJobNewInsightsProcessingHandler.LOG.info("input param hqlTemplateFolder: " + hqlTemplateFolder);
        DailyJobNewInsightsProcessingHandler.LOG.info("input param app folder: " + appFileFolder);
        DailyJobNewInsightsProcessingHandler.LOG.info("input param avscFileFolder: " + avscFileFolder);
        DailyJobNewInsightsProcessingHandler.LOG.info("input param viewsTemplateFolder: " + viewsTemplateFolder);
        ph.decisionMaker.setHdfsUrl(hdfsUrl).setAppFolder(appFileFolder);
        DailyJobNewInsightsProcessingHandler.LOG.info("Start processing possible new insights");
        ph.process(hdfsUrl, hqlTemplateFolder, hqlFileFolder, avscTemplateFolder, avscFileFolder, viewsTemplateFolder, viewsUpdateHqlFile);
        DailyJobNewInsightsProcessingHandler.LOG.info("Finish processing possible new insights");
        ph.close();
    }
    
    static {
        LOG = LoggerFactory.getLogger(DailyJobNewInsightsProcessingHandler.class);
        hqlTemplateFileNames = Arrays.asList("appendAccessPointKpis.hql", "appendAssocDeviceKpis.hql", "appendDeviceKpis.hql", "appendHomeKpis.hql", "appendRadioKpis.hql");
    }
}
