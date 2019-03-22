// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.config.insights.hql;

import org.slf4j.LoggerFactory;
import org.apache.hadoop.fs.PathFilter;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FileStatus;
import java.io.IOException;
import com.nokia.hal.nbi.colmapping.ColumnsMappingForNbi;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.conf.Configuration;
import com.nokia.hal.config.insights.InsightMonitoredPointType;
import com.nokia.hal.config.insights.InsightFlow;
import com.nokia.hal.config.insights.InsightCategory;
import com.nokia.hal.config.insights.InsightDomain;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.ArrayList;
import com.nokia.hal.nbi.connector.model.SubmittedFormula;
import java.util.List;
import org.slf4j.Logger;

public class ViewGenerator
{
    private static final Logger LOG;
    private static final String VIEWS_TEMPLATE_FILENAME_SUFFIX = "_view_template.hql";
    
    public static Boolean isPlaceholder(final String line) {
        return PlaceholderForViews.isPlaceholder(line);
    }
    
    private static String generateHqlLine(final String line, final List<SubmittedFormula> formulas) {
        String output = null;
        if (isPlaceholder(line)) {
            output = translatePlaceholder(line, formulas);
        }
        else {
            output = line;
        }
        return output;
    }
    
    public static String generate(final String viewTemplate, List<SubmittedFormula> formulas) {
        if (formulas == null) {
            formulas = new ArrayList<SubmittedFormula>();
        }
        final List<SubmittedFormula> submittedFormulas = formulas;
        return Arrays.stream(viewTemplate.split("\n")).map(line -> generateHqlLine(line, submittedFormulas)).filter(line -> !line.isEmpty()).collect((Collector<? super Object, ?, String>)Collectors.joining("\n", "", ""));
    }
    
    private static String translatePlaceholder(final String line, final List<SubmittedFormula> formulas) {
        try {
            final PlaceholderForViews ph = new PlaceholderForViews(line);
            return generateAliasFor(ph.getDomain(), ph.getInsightsCategory(), ph.getFlow(), ph.getMonitoredPointType(), formulas);
        }
        catch (IllegalArgumentException ex) {
            ViewGenerator.LOG.error("found error in placeholder line: " + line);
            throw new HalInvalidPlaceholderForViewsException(ex.getMessage());
        }
        catch (HalInvalidPlaceholderForViewsException ex2) {
            ViewGenerator.LOG.error("found error in placeholder line: " + line);
            throw ex2;
        }
    }
    
    private static String generateAliasFor(final InsightDomain domain, final InsightCategory insightsCategory, final InsightFlow flow, final InsightMonitoredPointType monitoredPointType, final List<SubmittedFormula> formulas) {
        String result = "";
        try {
            final List<String> aliases = formulas.stream().filter(formula -> InsightDomain.fromString(formula.getDomain()) == domain && InsightFlow.fromString(formula.getFlow()) == flow && InsightMonitoredPointType.fromString(formula.getMonitoredPointType()) == monitoredPointType && InsightCategory.fromString(formula.getCategory()) == insightsCategory && formula.getToBeExported()).map(formula -> formula.getPhysicalTarget() + " as " + formula.getInsightId()).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
            if (!aliases.isEmpty()) {
                result = aliases.stream().collect((Collector<? super Object, ?, String>)Collectors.joining(",", ",", ""));
            }
            else {
                ViewGenerator.LOG.info(String.format("no aliases found to build view for domain: '%s', category: '%s', flow: '%s', monitoredPointType: '%s'", domain.getMetadataValue(), insightsCategory.getMetadataValue(), flow.getMetadataValue(), monitoredPointType.getMetadataValue()));
            }
        }
        catch (IllegalArgumentException ex) {
            ViewGenerator.LOG.error("found problem in formula: " + ex.getMessage());
            throw new HalInvalidFormulaFound(ex.getMessage());
        }
        return result;
    }
    
    public static List<String> createViewsStatements(final List<SubmittedFormula> formulas, final String viewsTemplateFolder) {
        final List<String> createViewStatements = new ArrayList<String>();
        try {
            ViewGenerator.LOG.info("start view templates reading");
            final Configuration conf = new Configuration();
            final FileSystem fs = FileSystem.get(conf);
            final FileStatus[] fileStatuses = getFilesForFilter(fs, viewsTemplateFolder, "_view_template.hql");
            Boolean viewTemplatesFound = Boolean.FALSE;
            for (final FileStatus fileStatus : fileStatuses) {
                final Path path = fileStatus.getPath();
                final String template = getFileContent(fs, path);
                ViewGenerator.LOG.info("reading views template file: " + path.getName());
                viewTemplatesFound = Boolean.TRUE;
                String stmt = generate(template, formulas);
                if (!stmt.isEmpty()) {
                    if (!stmt.endsWith(";")) {
                        stmt += ";";
                    }
                    createViewStatements.add(stmt);
                }
            }
            if (!viewTemplatesFound) {
                ViewGenerator.LOG.warn("no view templates found in HDFS folder: " + viewsTemplateFolder);
            }
        }
        catch (IOException ex) {
            ViewGenerator.LOG.error("error accessing the view templates files on HDFS: " + viewsTemplateFolder);
            ColumnsMappingForNbi.logException(ViewGenerator.LOG, ex);
        }
        finally {
            ViewGenerator.LOG.info("finished view templates reading");
        }
        return createViewStatements;
    }
    
    private static String getFileContent(final FileSystem fs, final Path path) throws IOException {
        try {
            final BufferedReader br = new BufferedReader(new InputStreamReader(fs.open(path)));
            final String data = br.lines().collect(Collectors.joining("\n"));
            br.close();
            return data;
        }
        catch (IOException ex) {
            ViewGenerator.LOG.error("error accessing hdfs file: " + path.getName());
            throw ex;
        }
    }
    
    private static FileStatus[] getFilesForFilter(final FileSystem fileSystem, final String folderName, final String suffix) throws IOException {
        ViewGenerator.LOG.debug("getting files from the '{}' folder having the name ending with '{}'", folderName, suffix);
        final FileStatus[] files = fileSystem.listStatus(new Path(folderName), createFilter(suffix));
        if (files == null) {
            throw new RuntimeException(String.format("The folder %s does not exists", folderName));
        }
        return files;
    }
    
    private static PathFilter createFilter(final String sufffix) {
        return new PathFilter() {
            @Override
            public boolean accept(final Path path) {
                return path.getName().endsWith(sufffix);
            }
        };
    }
    
    static {
        LOG = LoggerFactory.getLogger(ViewGenerator.class);
    }
}
