// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.colmapping;

public class NewMappingsNeededDecisionMakerAlwaysTrue extends NewMappingsNeededDecisionMakerAbstract
{
    public NewMappingsNeededDecisionMakerAlwaysTrue() {
        super(null);
    }
    
    @Override
    public void updateLastSubmissionTime() {
    }
    
    @Override
    protected Long getLastSubmissionTime() {
        return 0L;
    }
    
    @Override
    public Boolean newMappingsNeeded() {
        return Boolean.TRUE;
    }
}
