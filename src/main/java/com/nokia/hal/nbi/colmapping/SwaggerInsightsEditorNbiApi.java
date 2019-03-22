// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.colmapping;

import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nokia.hal.nbi.connector.model.FlowsStatesResponse;
import com.nokia.hal.nbi.connector.api.FlowsStateGETApi;
import com.nokia.hal.nbi.connector.model.FlowsStates;
import com.nokia.hal.nbi.connector.model.SubmittedFormulasResponse;
import com.nokia.hal.nbi.connector.ApiException;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.StringWriter;
import com.nokia.hal.nbi.connector.api.SubmittedFormulasGETApi;
import com.nokia.hal.nbi.connector.model.SubmittedFormulas;
import com.nokia.hal.nbi.connector.ApiClient;
import org.slf4j.Logger;

public class SwaggerInsightsEditorNbiApi implements InsightsEditorNbiApi
{
    private static final Logger LOG;
    private ApiClient apiClient;
    private String nbiUrl;
    
    public SwaggerInsightsEditorNbiApi(final String nbiUrl) {
        this.apiClient = new ApiClient();
        SwaggerInsightsEditorNbiApi.LOG.info("connecting to NBI api: {}", nbiUrl);
        this.apiClient.setBasePath(nbiUrl);
        this.apiClient.setVerifyingSsl(Boolean.FALSE);
    }
    
    @Override
    public SubmittedFormulas getFormulas(final String domain, final String flow) {
        SubmittedFormulas formulas = null;
        try {
            final SubmittedFormulasGETApi api = new SubmittedFormulasGETApi(this.apiClient);
            final SubmittedFormulasResponse fr = api.submittedFormulasGet(domain, flow, null, null);
            SwaggerInsightsEditorNbiApi.LOG.debug("submittedFormulas: {}", convertToJsonString(SwaggerInsightsEditorNbiApi.LOG, fr));
            formulas = fr.getEmbedded().getSubmittedFormulas();
        }
        catch (ApiException ex) {
            SwaggerInsightsEditorNbiApi.LOG.error("Cannot retrieve formulas from Insights Editor NBI");
            final StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            final String stacktrace = sw.toString();
            SwaggerInsightsEditorNbiApi.LOG.error(stacktrace);
        }
        return formulas;
    }
    
    @Override
    public FlowsStates getFlowsState(final String domain, final String flow) {
        FlowsStates states = null;
        try {
            final FlowsStateGETApi api = new FlowsStateGETApi(this.apiClient);
            final FlowsStatesResponse statesResponse = api.flowsStateGet(domain, flow);
            if (statesResponse != null) {
                SwaggerInsightsEditorNbiApi.LOG.debug("flowsState: {}", convertToJsonString(SwaggerInsightsEditorNbiApi.LOG, statesResponse));
                if (statesResponse.getEmbedded() != null) {
                    states = statesResponse.getEmbedded().getTasks();
                }
                else {
                    SwaggerInsightsEditorNbiApi.LOG.error("statesResponse.getEmbedded() is null. Unexpected response from FlowsStateGETApi.");
                }
            }
            else {
                SwaggerInsightsEditorNbiApi.LOG.error("statesResponse is null. Unexpected response from FlowsStateGETApi.");
            }
        }
        catch (ApiException ex) {
            SwaggerInsightsEditorNbiApi.LOG.error("Cannot retrieve Flow States from Insights Editor NBI");
            final StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            final String stacktrace = sw.toString();
            SwaggerInsightsEditorNbiApi.LOG.error(stacktrace);
        }
        return states;
    }
    
    @Override
    public void submitFormulas(final String domain, final String flow) {
        SwaggerInsightsEditorNbiApi.LOG.info("this method is still not implemeted");
    }
    
    public static String convertToJsonString(final Logger log, final Object mappings) {
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
            log.error("Exception caught while trying to generate a json string for the mappings: {}", ex.toString());
        }
        return output;
    }
    
    static {
        LOG = LoggerFactory.getLogger(SwaggerInsightsEditorNbiApi.class);
    }
}
