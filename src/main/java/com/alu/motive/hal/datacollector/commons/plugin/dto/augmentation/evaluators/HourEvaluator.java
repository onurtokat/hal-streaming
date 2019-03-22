// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.commons.plugin.dto.augmentation.evaluators;

import java.time.LocalDateTime;

public class HourEvaluator implements AugmentingParamValueEvaluator<String>
{
    static final String HOUR_FUNCTION = "$hour";
    
    @Override
    public String eval() {
        return String.format("%02d", LocalDateTime.now().getHour());
    }
}
