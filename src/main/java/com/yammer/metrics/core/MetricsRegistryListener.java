// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics.core;

import java.util.EventListener;

public interface MetricsRegistryListener extends EventListener
{
    void onMetricAdded(final MetricName p0, final Metric p1);
    
    void onMetricRemoved(final MetricName p0);
}
