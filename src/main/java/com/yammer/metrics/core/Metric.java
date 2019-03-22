// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics.core;

public interface Metric
{
     <T> void processWith(final MetricProcessor<T> p0, final MetricName p1, final T p2) throws Exception;
}
