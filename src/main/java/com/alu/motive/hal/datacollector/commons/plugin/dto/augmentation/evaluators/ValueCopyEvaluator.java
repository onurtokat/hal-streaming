// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.commons.plugin.dto.augmentation.evaluators;

public class ValueCopyEvaluator implements AugmentingParamValueEvaluator<Object>
{
    Object paramValue;
    
    public ValueCopyEvaluator(final Object parameterValue) {
        this.paramValue = parameterValue;
    }
    
    @Override
    public Object eval() {
        return this.paramValue;
    }
}
