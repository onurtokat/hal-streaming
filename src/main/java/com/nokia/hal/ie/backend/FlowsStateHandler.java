// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.ie.backend;

import com.nokia.hal.nbi.colmapping.exception.HalColumnsMappingInsightsEditorCommunicationException;
import com.nokia.hal.nbi.connector.model.FlowsStates;
import com.nokia.hal.nbi.connector.model.FlowsStatesItem;
import java.util.function.Consumer;
import com.nokia.hal.config.insights.InsightDomain;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.nokia.hal.nbi.colmapping.InsightsEditorNbiApi;

public class FlowsStateHandler
{
    private InsightsEditorNbiApi nbiApi;
    private AnnotationConfigApplicationContext ctx;
    
    public FlowsStateHandler() {
        this.ctx = new AnnotationConfigApplicationContext((Class<?>[])new Class[] { SubmitFormulasHandlerConfiguration.class });
        this.nbiApi = this.ctx.getBean(InsightsEditorNbiApi.class);
    }
    
    public static void main(final String[] args) throws HalColumnsMappingInsightsEditorCommunicationException {
        System.out.println("calling flowsState GET on hal-nbi");
        final FlowsStateHandler handler = new FlowsStateHandler();
        final FlowsStates states = handler.nbiApi.getFlowsState(InsightDomain.WIFI.getMetadataValue(), null);
        states.stream().forEach(System.out::println);
        System.out.println("finished calling flowsState GET on hal-nbi");
    }
}
