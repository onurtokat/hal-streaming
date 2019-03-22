// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics.core;

public abstract class Gauge<T> implements Metric
{
    public abstract T value();
    
    @Override
    public <U> void processWith(final MetricProcessor<U> processor, final MetricName name, final U context) throws Exception {
        processor.processGauge(name, this, context);
    }
}
