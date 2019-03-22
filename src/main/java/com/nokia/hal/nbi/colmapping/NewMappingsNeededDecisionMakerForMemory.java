// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.colmapping;

public class NewMappingsNeededDecisionMakerForMemory extends NewMappingsNeededDecisionMakerAbstract
{
    protected Long lastSubmissionTime;
    
    public NewMappingsNeededDecisionMakerForMemory(final InsightsEditorNbiApi nbiApi) {
        super(nbiApi);
        this.lastSubmissionTime = -1L;
    }
    
    @Override
    public void updateLastSubmissionTime() {
        this.lastSubmissionTime = this.lastSubmissionTimeFromIE;
    }
    
    public Long getLastSubmissionTime() {
        return this.lastSubmissionTime;
    }
}
