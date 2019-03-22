// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.commons.plugin.dto.augmentation.evaluators;

public class UnixTimeEvaluator implements AugmentingParamValueEvaluator<Long>
{
    static final String EXPRESSION = "$unixTime";
    
    @Override
    public Long eval() {
        return System.currentTimeMillis() / 1000L;
    }
}
