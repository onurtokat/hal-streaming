// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.ae.client.action.export.model;

import com.alu.motive.ae.client.template.AETemplates;

public class MappingsBuilderInputData
{
    private final AETemplates aeTemplates;
    private final String caseValue;
    
    public MappingsBuilderInputData(final String caseValue, final AETemplates aeTemplates) {
        this.caseValue = caseValue;
        this.aeTemplates = aeTemplates;
    }
    
    public String getCaseValue() {
        return this.caseValue;
    }
    
    public AETemplates getAETemplates() {
        return this.aeTemplates;
    }
}
