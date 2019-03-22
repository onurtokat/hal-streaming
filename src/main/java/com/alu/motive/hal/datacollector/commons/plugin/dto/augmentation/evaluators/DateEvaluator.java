// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.commons.plugin.dto.augmentation.evaluators;

import java.util.Date;
import java.text.SimpleDateFormat;

public class DateEvaluator implements AugmentingParamValueEvaluator<String>
{
    static final String DATE_FUNCTION = "date(";
    private long timestamp;
    private String format;
    
    public DateEvaluator(final long timestamp, final String function) {
        this.timestamp = timestamp;
        this.format = function.substring(function.indexOf("(") + 1, function.lastIndexOf(")"));
    }
    
    @Override
    public String eval() {
        final SimpleDateFormat df = new SimpleDateFormat(this.format);
        return df.format(new Date(this.timestamp));
    }
}
