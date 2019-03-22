// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.colmapping;

import com.nokia.hal.nbi.connector.model.FlowsStates;
import com.nokia.hal.nbi.colmapping.exception.HalColumnsMappingInsightsEditorCommunicationException;
import com.nokia.hal.nbi.connector.model.SubmittedFormulas;

public interface InsightsEditorNbiApi
{
    SubmittedFormulas getFormulas(final String p0, final String p1) throws HalColumnsMappingInsightsEditorCommunicationException;
    
    FlowsStates getFlowsState(final String p0, final String p1) throws HalColumnsMappingInsightsEditorCommunicationException;
    
    void submitFormulas(final String p0, final String p1) throws HalColumnsMappingInsightsEditorCommunicationException;
}
