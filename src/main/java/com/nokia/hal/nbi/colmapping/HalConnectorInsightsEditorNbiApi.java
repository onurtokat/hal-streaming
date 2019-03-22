// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.colmapping;

import org.slf4j.LoggerFactory;
import com.nokia.hal.config.insights.InsightDomain;
import com.nokia.hal.nbi.connector.api.SubmittedFormulasdomainPOSTApi;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.StringWriter;
import com.nokia.hal.nbi.connector.model.FlowsStatesResponse;
import com.nokia.hal.nbi.connector.api.FlowsStateGETApi;
import com.nokia.hal.nbi.connector.model.FlowsStates;
import com.nokia.hal.nbi.connector.model.SubmittedFormulasResponse;
import com.nokia.hal.nbi.connector.ApiClient;
import com.nokia.hal.nbi.connector.ApiException;
import com.nokia.hal.nbi.colmapping.exception.HalColumnsMappingInsightsEditorCommunicationException;
import com.nokia.hal.nbi.connector.api.SubmittedFormulasGETApi;
import com.nokia.hal.nbi.connector.Configuration;
import com.nokia.hal.nbi.connector.model.SubmittedFormulas;
import com.nokia.hal.nbi.connector.ConnectorConfig;
import org.slf4j.Logger;

public class HalConnectorInsightsEditorNbiApi implements InsightsEditorNbiApi
{
    private static final Logger LOG;
    private ConnectorConfig nbiConnector;
    
    public HalConnectorInsightsEditorNbiApi(final String nbiHost, final String username, final String password, final String port) {
        final String protocolAndHost = "https://" + nbiHost;
        HalConnectorInsightsEditorNbiApi.LOG.debug("Setting access to nbi with: host={}, user={}, pass={}, port={}", nbiHost, username, password, port);
        this.nbiConnector = ConnectorConfig.getInstance().withHost(protocolAndHost).withPort(port).withUsername(username).withPassword(password);
    }
    
    @Override
    public SubmittedFormulas getFormulas(final String domain, final String flow) throws HalColumnsMappingInsightsEditorCommunicationException {
        SubmittedFormulas formulas = null;
        try {
            this.nbiConnector.setup();
            final ApiClient apiClient = Configuration.getDefaultApiClient();
            final SubmittedFormulasGETApi api = new SubmittedFormulasGETApi(apiClient);
            final SubmittedFormulasResponse fr = api.submittedFormulasGet(domain, flow, null, null);
            HalConnectorInsightsEditorNbiApi.LOG.debug("submittedFormulas: {}", SwaggerInsightsEditorNbiApi.convertToJsonString(HalConnectorInsightsEditorNbiApi.LOG, fr));
            formulas = fr.getEmbedded().getSubmittedFormulas();
        }
        catch (ApiException ex) {
            this.handleApiException(ex, "Cannot retrieve formulas from Insights Editor NBI");
            throw new HalColumnsMappingInsightsEditorCommunicationException(ex.getMessage());
        }
        return formulas;
    }
    
    @Override
    public FlowsStates getFlowsState(final String domain, final String flow) throws HalColumnsMappingInsightsEditorCommunicationException {
        FlowsStates states = null;
        try {
            this.nbiConnector.setup();
            final ApiClient apiClient = Configuration.getDefaultApiClient();
            final FlowsStateGETApi api = new FlowsStateGETApi(apiClient);
            final FlowsStatesResponse statesResponse = api.flowsStateGet(domain, flow);
            if (statesResponse != null) {
                HalConnectorInsightsEditorNbiApi.LOG.debug("flowsState: {}", SwaggerInsightsEditorNbiApi.convertToJsonString(HalConnectorInsightsEditorNbiApi.LOG, statesResponse));
                if (statesResponse.getEmbedded() != null) {
                    states = statesResponse.getEmbedded().getTasks();
                }
                else {
                    HalConnectorInsightsEditorNbiApi.LOG.error("statesResponse.getEmbedded() is null. Unexpected response from FlowsStateGETApi.");
                }
            }
            else {
                HalConnectorInsightsEditorNbiApi.LOG.error("statesResponse is null. Unexpected response from FlowsStateGETApi.");
            }
        }
        catch (ApiException ex) {
            this.handleApiException(ex, "Cannot retrieve Flow States from Insights Editor NBI");
            throw new HalColumnsMappingInsightsEditorCommunicationException(ex.getMessage());
        }
        return states;
    }
    
    private void handleApiException(final ApiException ex, final String errorMsg) {
        HalConnectorInsightsEditorNbiApi.LOG.error(errorMsg);
        final StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        final String stacktrace = sw.toString();
        HalConnectorInsightsEditorNbiApi.LOG.error(stacktrace);
        this.checkForAuthenticationError(ex);
        this.nbiConnector.destroySession();
    }
    
    private void checkForAuthenticationError(final ApiException ex) {
        if (ex.getMessage().contains("Content type \"text/html;charset=UTF-8\" is not supported for type")) {
            HalConnectorInsightsEditorNbiApi.LOG.error("There was a session expiration or a failed authentication on hal-nbi.");
        }
    }
    
    @Override
    public void submitFormulas(final String domain, final String flow) {
        try {
            this.nbiConnector.setup();
            final ApiClient apiClient = Configuration.getDefaultApiClient();
            final SubmittedFormulasdomainPOSTApi api = new SubmittedFormulasdomainPOSTApi(apiClient);
            api.submittedFormulasDomainPost(InsightDomain.WIFI.getMetadataValue(), null);
            HalConnectorInsightsEditorNbiApi.LOG.info("sucessfuly called SubmitFormulas POST from Insights Editor NBI");
        }
        catch (ApiException ex) {
            HalConnectorInsightsEditorNbiApi.LOG.error("Error calling SubmitFormulas POST from Insights Editor NBI");
            final StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            final String stacktrace = sw.toString();
            HalConnectorInsightsEditorNbiApi.LOG.error(stacktrace);
        }
    }
    
    static {
        LOG = LoggerFactory.getLogger(HalConnectorInsightsEditorNbiApi.class);
    }
}
