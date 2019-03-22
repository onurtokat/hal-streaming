// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.ie.backend;

import com.nokia.hal.nbi.colmapping.exception.HalColumnsMappingInsightsEditorCommunicationException;
import com.nokia.hal.config.insights.InsightDomain;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.nokia.hal.nbi.colmapping.InsightsEditorNbiApi;

public class SubmitFormulasHandler
{
    private InsightsEditorNbiApi nbiApi;
    private AnnotationConfigApplicationContext ctx;
    
    public SubmitFormulasHandler() {
        this.ctx = new AnnotationConfigApplicationContext((Class<?>[])new Class[] { SubmitFormulasHandlerConfiguration.class });
        this.nbiApi = this.ctx.getBean(InsightsEditorNbiApi.class);
    }
    
    public static void main(final String[] args) throws HalColumnsMappingInsightsEditorCommunicationException {
        System.out.println("calling submit formulas on hal-nbi");
        final SubmitFormulasHandler handler = new SubmitFormulasHandler();
        handler.nbiApi.submitFormulas(InsightDomain.WIFI.getMetadataValue(), null);
        System.out.println("finished calling submit formulas on hal-nbi");
    }
}
