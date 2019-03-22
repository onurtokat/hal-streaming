// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.commons.plugin.dto.augmentation.evaluators;

public class AugmentingParamValueEvaluatorFactory
{
    private final Object parameterValue;
    
    public AugmentingParamValueEvaluatorFactory(final Object paramValue) {
        this.parameterValue = paramValue;
    }
    
    public AugmentingParamValueEvaluator<?> createEvaluator() {
        if (this.parameterValue != null && this.parameterValue instanceof String) {
            final String unevaluatedString = (String)this.parameterValue;
            if (unevaluatedString.equalsIgnoreCase("$systemTimestamp")) {
                return new SystemCurrentTimeMSEvaluator();
            }
            if (unevaluatedString.equalsIgnoreCase("$unixTime")) {
                return new UnixTimeEvaluator();
            }
            if (unevaluatedString.startsWith("date(")) {
                return new DateEvaluator(System.currentTimeMillis(), unevaluatedString);
            }
            if (unevaluatedString.startsWith("minChunk(")) {
                return new MinutesChunkEvaluator(System.currentTimeMillis(), unevaluatedString);
            }
            if (unevaluatedString.startsWith("concat(")) {
                return new ConcatenateEvaluator(unevaluatedString);
            }
            if (unevaluatedString.equalsIgnoreCase("$hour")) {
                return new HourEvaluator();
            }
        }
        return new ValueCopyEvaluator(this.parameterValue);
    }
}
