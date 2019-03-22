// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics.util;

public abstract class PercentGauge extends RatioGauge
{
    private static final int ONE_HUNDRED = 100;
    
    @Override
    public Double value() {
        return super.value() * 100.0;
    }
}
