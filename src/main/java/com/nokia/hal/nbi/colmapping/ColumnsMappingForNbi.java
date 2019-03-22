// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.colmapping;

import org.slf4j.LoggerFactory;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.StringWriter;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Iterator;
import java.util.function.Function;
import com.nokia.hal.nbi.colmapping.exception.HalColumnsMappingSaveToZookeeperException;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.List;
import com.nokia.hal.nbi.connector.model.SubmittedFormulas;
import com.nokia.hal.nbi.connector.model.SubmittedFormula;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;
import org.slf4j.Logger;

public class ColumnsMappingForNbi
{
    protected static final String FORMULA_HAS_NO_TO_BE_EXPORTED_FLAG = "formula '%s' does not have the 'toBeExported' flag";
    protected static final String FORMULA_HAS_AN_INVALID_TYPE = "formula '%s' has an invalid type '%s'";
    protected static final String FORMULA_HAS_NO_TYPE_SPECIFIED = "formula '%s' has no type";
    protected static final String FORMULA_HAS_NO_MONITORED_POINT_TYPE_SPECIFIED = "formula '%s' has no monitored point type";
    protected static final String FORMULA_HAS_AN_INVALID_FLOW = "formula '%s' has an invalid flow '%s'";
    protected static final String FORMULA_HAS_NO_FLOW_SPECIFIED = "formula '%s' has no flow";
    protected static final String FORMULA_HAS_NO_PHYSICAL_COLUMN_NAME = "formula '%s' has no physical column name";
    protected static final String FORMULA_HAS_NO_INSIGHT_ID = "formula has no insight id";
    public static final String WIFI_DOMAIN = "WiFi";
    private static final Logger LOG;
    private ColumnsMappingNbiStore mappingsStorage;
    protected Map<String, Set<PeriodType>> filters;
    
    public ColumnsMappingForNbi(final ColumnsMappingNbiStore store) {
        this.filters = new HashMap<String, Set<PeriodType>>();
        this.mappingsStorage = store;
    }
    
    public ColumnsMappingForNbi() {
        this.filters = new HashMap<String, Set<PeriodType>>();
    }
    
    protected static void addFilter(final Map<String, Set<PeriodType>> existingFilters, final String domain, final PeriodType flow) {
        if (existingFilters == null) {
            ColumnsMappingForNbi.LOG.error("call addFilter method with a null existingFilters. This is no doubt a bug in the code");
            return;
        }
        Set<PeriodType> periods = existingFilters.get(domain);
        if (periods == null) {
            periods = new HashSet<PeriodType>();
            existingFilters.put(domain, periods);
        }
        periods.add(flow);
    }
    
    public void addFilter(final String domain, final PeriodType flow) {
        addFilter(this.filters, domain, flow);
    }
    
    private Boolean isMyResponsibility(final SubmittedFormula f) {
        Boolean outcome = Boolean.FALSE;
        if (f != null && f.getDomain() != null && f.getFlow() != null) {
            final Set<PeriodType> periods = this.filters.get(f.getDomain());
            if (periods != null) {
                outcome = periods.contains(PeriodType.fromString(f.getFlow()));
            }
        }
        return outcome;
    }
    
    public void processNewInsights(final SubmittedFormulas newInsights) throws HalColumnsMappingSaveToZookeeperException, IllegalArgumentException {
        ColumnsMappingForNbi.LOG.info("Started the processing of the new formulas for HAL NBI");
        try {
            ColumnsMappingForNbi.LOG.debug("retrieve {} formulas from IE NBI", (Object)newInsights.size());
            final List<SubmittedFormula> filteredInsights = newInsights.stream().filter(f -> this.isMyResponsibility(f)).collect((Collector<? super SubmittedFormula, ?, List<SubmittedFormula>>)Collectors.toList());
            ColumnsMappingForNbi.LOG.debug("remains {} formulas from after filtering", (Object)filteredInsights.size());
            final MappingsForMpTypes mappings = this.createNewInsightsMappings(null, null, filteredInsights);
            ColumnsMappingForNbi.LOG.info("Generated the following mappings: {}", convertToJsonString(mappings));
            this.save(mappings);
            ColumnsMappingForNbi.LOG.info("Finished the mapping of new formulas for HAL NBI");
        }
        catch (HalColumnsMappingSaveToZookeeperException ex) {
            ColumnsMappingForNbi.LOG.error("creating NBI mappings for the new insights has failed due to access to Zookeeper. Details follow...");
            logException(ColumnsMappingForNbi.LOG, ex);
        }
        catch (IllegalArgumentException ex3) {
            ColumnsMappingForNbi.LOG.error("creating NBI mappings for the new insights has failed due to input parameters. Details follow...");
        }
        catch (Exception ex2) {
            ColumnsMappingForNbi.LOG.error("creating NBI mappings for the new insights has failed due to unexpected behaviour. Details follow...");
            logException(ColumnsMappingForNbi.LOG, ex2);
        }
        finally {
            ColumnsMappingForNbi.LOG.info("Finished the processing of the new formulas for HAL NBI");
        }
    }
    
    public void setMappingsStorage(final ColumnsMappingNbiStore mappingsStorage) {
        this.mappingsStorage = mappingsStorage;
    }
    
    protected static InsightAdditionalInfo createAdditionalInfo(final SubmittedFormula formula) {
        final String insightFriendlyName = formula.getRestName();
        final String insightDomain = formula.getDomain();
        final InsightCategory insightCategory = InsightCategory.fromString(formula.getCategory());
        final InsightType insightType = InsightType.fromString(formula.getType());
        final InsightAdditionalInfo insightAddInfo = new InsightAdditionalInfo();
        insightAddInfo.setAlias(insightFriendlyName);
        insightAddInfo.setCategory(insightCategory);
        insightAddInfo.setDomain(insightDomain);
        insightAddInfo.setType(insightType);
        return insightAddInfo;
    }
    
    private static ExtendedInsightInfo validateFormula(final SubmittedFormula formula) {
        final String insightId = formula.getInsightId();
        if (insightId == null || insightId.isEmpty()) {
            ColumnsMappingForNbi.LOG.error("formula has no insight id");
            throw new IllegalArgumentException("formula has no insight id");
        }
        final String insightPhyColName = formula.getPhysicalTarget();
        if (insightPhyColName == null || insightPhyColName.isEmpty()) {
            ColumnsMappingForNbi.LOG.warn(String.format("formula '%s' has no physical column name", insightId));
        }
        final String flow = formula.getFlow();
        if (flow == null) {
            ColumnsMappingForNbi.LOG.error(String.format("formula '%s' has no flow", insightId));
            throw new IllegalArgumentException(String.format("formula '%s' has no flow", insightId));
        }
        final PeriodType pt = PeriodType.fromString(formula.getFlow());
        if (pt == null) {
            ColumnsMappingForNbi.LOG.error(String.format("formula '%s' has an invalid flow '%s'", insightId, formula.getFlow()));
            throw new IllegalArgumentException(String.format("formula '%s' has an invalid flow '%s'", insightId, formula.getFlow()));
        }
        final String mptype = formula.getMonitoredPointType();
        if (mptype == null || mptype.isEmpty()) {
            ColumnsMappingForNbi.LOG.error(String.format("formula '%s' has no monitored point type", insightId));
            throw new IllegalArgumentException(String.format("formula '%s' has no monitored point type", insightId));
        }
        final String insightTypeStr = formula.getType();
        final InsightType insightType = InsightType.fromString(insightTypeStr);
        if (insightTypeStr == null || insightTypeStr.isEmpty()) {
            ColumnsMappingForNbi.LOG.error(String.format("formula '%s' has no type", insightId));
            throw new IllegalArgumentException(String.format("formula '%s' has no type", insightId));
        }
        if (insightType == null || insightType.equals(InsightType.UNKNOWN)) {
            ColumnsMappingForNbi.LOG.error(String.format("formula '%s' has an invalid type '%s'", insightId, insightTypeStr));
            throw new IllegalArgumentException(String.format("formula '%s' has an invalid type '%s'", insightId, insightTypeStr));
        }
        final Boolean toBeExported = formula.getToBeExported();
        if (toBeExported == null) {
            ColumnsMappingForNbi.LOG.error(String.format("formula '%s' does not have the 'toBeExported' flag", insightId, insightTypeStr));
            throw new IllegalArgumentException(String.format("formula '%s' does not have the 'toBeExported' flag", insightId));
        }
        final ExtendedInsightInfo extInfo = new ExtendedInsightInfo();
        extInfo.addInfo = createAdditionalInfo(formula);
        extInfo.flow = pt;
        extInfo.mpType = mptype;
        extInfo.physicalColName = insightPhyColName;
        extInfo.toBeExported = toBeExported;
        return extInfo;
    }
    
    protected MappingsForMpTypes createNewInsightsMappings(final SubmittedFormulas formulas) {
        return this.createNewInsightsMappings("", null, formulas);
    }
    
    protected static Boolean justMpTypeAndFlow(final ExtendedInsightInfo ins, final String mpType, final PeriodType flow) {
        return mpType == null || mpType.isEmpty() || flow == null || (ins.mpType.equals(mpType) && ins.flow.equals(flow));
    }
    
    protected static Boolean justToBeExported(final ExtendedInsightInfo ins) {
        return ins.toBeExported && ins.physicalColName != null && !ins.physicalColName.isEmpty();
    }
    
    protected MappingsForMpTypes createNewInsightsMappings(final String filterMpType, final PeriodType filterFlow, final List<SubmittedFormula> formulas) {
        final MappingsForMpTypes mappings = formulas.stream().map((Function<? super Object, ?>)ColumnsMappingForNbi::validateFormula).filter(extInfo -> justMpTypeAndFlow(extInfo, filterMpType, filterFlow)).filter(extInfo -> justToBeExported(extInfo)).collect(MappingsForMpTypes::new, MappingsForMpTypes::addExtInsightInfo, MappingsForMpTypes::merge);
        return mappings;
    }
    
    protected void save(final MappingsForMpTypes mappings) throws HalColumnsMappingSaveToZookeeperException {
        try {
            for (final Map.Entry<String, MappingsForPeriodTypes> mptEntry : mappings.entrySet()) {
                for (final Map.Entry<PeriodType, MappingsForPhysicalNames> ptEntry : mptEntry.getValue().entrySet()) {
                    this.mappingsStorage.save(mptEntry.getKey(), ptEntry.getKey(), 0, ptEntry.getValue());
                }
            }
        }
        catch (Exception ex) {
            ColumnsMappingForNbi.LOG.error("Exception received when trying to store the new mappings in Zookeeper");
            logException(ColumnsMappingForNbi.LOG, ex);
            throw new HalColumnsMappingSaveToZookeeperException("Cannot save new custom formulas mappings in Zookeeper");
        }
    }
    
    protected static String convertToJsonString(final MappingsForMpTypes mappings) {
        final ObjectMapper om = new ObjectMapper();
        om.configure(SerializationFeature.INDENT_OUTPUT, true);
        JsonNode tree = null;
        String output = "";
        try {
            final String rawJson = om.writeValueAsString(mappings);
            tree = om.readTree(rawJson);
            output = om.writeValueAsString(tree);
        }
        catch (IOException ex) {
            ColumnsMappingForNbi.LOG.error("Exception caught while trying to generate a json string for the mappings: {}", ex.toString());
        }
        return output;
    }
    
    public static void logException(final Logger logger, final Exception ex) {
        final StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        final String stacktrace = sw.toString();
        logger.error(stacktrace);
    }
    
    public static String logStack(final Exception ex) {
        final StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
    
    static {
        LOG = LoggerFactory.getLogger(ColumnsMappingForNbi.class);
    }
    
    public static class ExtendedInsightInfo
    {
        public InsightAdditionalInfo addInfo;
        public String mpType;
        public PeriodType flow;
        public String physicalColName;
        public Boolean toBeExported;
    }
}
