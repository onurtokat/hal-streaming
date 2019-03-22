// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics.util;

import com.yammer.metrics.core.Gauge;

public abstract class RatioGauge extends Gauge<Double>
{
    protected abstract double getNumerator();
    
    protected abstract double getDenominator();
    
    @Override
    public Double value() {
        final double d = this.getDenominator();
        if (Double.isNaN(d) || Double.isInfinite(d) || d == 0.0) {
            return Double.NaN;
        }
        return this.getNumerator() / d;
    }
}
