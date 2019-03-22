// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.commons.plugin.dto.augmentation.evaluators;

public class SystemCurrentTimeMSEvaluator implements AugmentingParamValueEvaluator<Long>
{
    static final String SYSTEM_TIMESTAMP_EXPR = "$systemTimestamp";
    
    @Override
    public Long eval() {
        return System.currentTimeMillis();
    }
}
