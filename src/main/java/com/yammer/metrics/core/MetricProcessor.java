// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics.core;

public interface MetricProcessor<T>
{
    void processMeter(final MetricName p0, final Metered p1, final T p2) throws Exception;
    
    void processCounter(final MetricName p0, final Counter p1, final T p2) throws Exception;
    
    void processHistogram(final MetricName p0, final Histogram p1, final T p2) throws Exception;
    
    void processTimer(final MetricName p0, final Timer p1, final T p2) throws Exception;
    
    void processGauge(final MetricName p0, final Gauge<?> p1, final T p2) throws Exception;
}
