// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.commons.plugin.dto.augmentation.evaluators;

public class MinutesChunkEvaluator implements AugmentingParamValueEvaluator<Integer>
{
    static final String MIN_CHUNK_FUNCTION = "minChunk(";
    private static final int SECONDS_IN_HOUR = 3600;
    private static final int SECONDS_IN_MINUTE = 60;
    private static final int MILLIS_IN_SECCOND = 1000;
    private long timestamp;
    private int intervalLength;
    
    public MinutesChunkEvaluator(final long timestamp, final String function) {
        this.intervalLength = 15;
        this.timestamp = timestamp;
        final String param = function.substring(function.indexOf("(") + 1, function.lastIndexOf(")"));
        this.intervalLength = Integer.valueOf(param.trim());
    }
    
    @Override
    public Integer eval() {
        final int chunkLengthMillis = this.intervalLength * 60 * 1000;
        final long lastHourMillis = this.timestamp % 3600000L;
        final Integer minChunk = (int)(lastHourMillis / chunkLengthMillis) + 1;
        return minChunk;
    }
}
