// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.colmapping;

import org.slf4j.LoggerFactory;
import com.nokia.hal.nbi.colmapping.exception.HalColumnsMappingHdfsAccessException;
import com.nokia.hal.nbi.connector.model.FlowsStates;
import com.nokia.hal.nbi.colmapping.exception.HalColumnsMappingInsightsEditorCommunicationException;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.List;
import com.nokia.hal.nbi.connector.model.FlowsStatesItem;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;
import org.slf4j.Logger;

public abstract class NewMappingsNeededDecisionMakerAbstract implements NewMappingsNeededDecisionMaker
{
    private static final Logger LOG;
    protected static final String NO_FILTERS_SET = "no filters set";
    protected static final String NO_FLOWS_RETURNED_BY_INSIGHTS_EDITOR_NBI = "no flows returned by Insights Editor nbi";
    protected static final String FAILED_TO_GET_FLOWS_STATES_FROM_INSIGHTS_EDITOR_NBI = "failed to get flows states from Insights Editor NBI";
    protected InsightsEditorNbiApi nbiApi;
    protected Map<String, Set<PeriodType>> filters;
    protected Long lastSubmissionTimeFromIE;
    
    NewMappingsNeededDecisionMakerAbstract(final InsightsEditorNbiApi nbiApi) {
        this.filters = new HashMap<String, Set<PeriodType>>();
        this.lastSubmissionTimeFromIE = -1L;
        this.nbiApi = nbiApi;
    }
    
    @Override
    public Boolean newMappingsNeeded() {
        Boolean newMappingsNeeded = Boolean.FALSE;
        if (this.filters == null || this.filters.isEmpty()) {
            throw new IllegalStateException("no filters set");
        }
        try {
            final Long submissionTimeFromIE = this.getLastSubmissionTimeFromIE();
            NewMappingsNeededDecisionMakerAbstract.LOG.debug("last submission time retrieved from IE nbi is: {}", submissionTimeFromIE);
            final Long savedSubmissionTime = this.getLastSubmissionTime();
            NewMappingsNeededDecisionMakerAbstract.LOG.debug("last saved submission time is: {}", savedSubmissionTime);
            newMappingsNeeded = (submissionTimeFromIE > 0L && (savedSubmissionTime == -1L || submissionTimeFromIE > savedSubmissionTime));
            this.lastSubmissionTimeFromIE = submissionTimeFromIE;
        }
        catch (Exception ex) {
            ColumnsMappingForNbi.logException(NewMappingsNeededDecisionMakerAbstract.LOG, ex);
            NewMappingsNeededDecisionMakerAbstract.LOG.error("Cannot decide whether new mappings are required. No update to NBI mappings will be performed.");
        }
        return newMappingsNeeded;
    }
    
    @Override
    public void setFilters(final Map<String, Set<PeriodType>> filters) {
        this.filters = filters;
    }
    
    @Override
    public abstract void updateLastSubmissionTime();
    
    protected Boolean isMyResponsibility(final FlowsStatesItem fsItem) {
        Boolean outcome = Boolean.FALSE;
        if (fsItem != null && fsItem.getDomain() != null && fsItem.getFlow() != null) {
            if (this.filters != null && this.filters.size() > 0) {
                final Set<PeriodType> periods = this.filters.get(fsItem.getDomain());
                if (periods != null) {
                    outcome = periods.contains(PeriodType.fromString(fsItem.getFlow()));
                }
            }
            else {
                NewMappingsNeededDecisionMakerAbstract.LOG.error("no (domain, flow) pairs specified to check if new mappings are needed. This is no doubt a coding error.");
            }
        }
        return outcome;
    }
    
    protected Long getLastSubmissionTimeFromIE() throws HalColumnsMappingInsightsEditorCommunicationException {
        Long submissionTs = 0L;
        try {
            NewMappingsNeededDecisionMakerAbstract.LOG.info("calling insights editor nbi getFlowsState()");
            final FlowsStates states = this.nbiApi.getFlowsState(null, null);
            final List<FlowsStatesItem> filteredFlowStates = states.stream().filter(fs -> this.isMyResponsibility(fs)).collect((Collector<? super FlowsStatesItem, ?, List<FlowsStatesItem>>)Collectors.toList());
            if (filteredFlowStates.size() >= 1) {
                submissionTs = filteredFlowStates.get(0).getSubmissionTS();
            }
            else {
                NewMappingsNeededDecisionMakerAbstract.LOG.error("no flows returned by Insights Editor nbi");
            }
        }
        catch (Exception ex) {
            NewMappingsNeededDecisionMakerAbstract.LOG.error("failed to get flows states from Insights Editor NBI");
            ColumnsMappingForNbi.logException(NewMappingsNeededDecisionMakerAbstract.LOG, ex);
            throw new HalColumnsMappingInsightsEditorCommunicationException("failed to get flows states from Insights Editor NBI");
        }
        return submissionTs;
    }
    
    protected abstract Long getLastSubmissionTime() throws HalColumnsMappingHdfsAccessException;
    
    @Override
    public void addFilter(final String domain, final PeriodType flow) {
        ColumnsMappingForNbi.addFilter(this.filters, domain, flow);
    }
    
    static {
        LOG = LoggerFactory.getLogger(NewMappingsNeededDecisionMakerAbstract.class);
    }
}
