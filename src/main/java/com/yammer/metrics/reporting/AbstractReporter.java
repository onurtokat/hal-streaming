// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics.reporting;

import com.yammer.metrics.core.MetricsRegistry;

public abstract class AbstractReporter
{
    private final MetricsRegistry metricsRegistry;
    
    protected AbstractReporter(final MetricsRegistry registry) {
        this.metricsRegistry = registry;
    }
    
    public void shutdown() {
    }
    
    protected MetricsRegistry getMetricsRegistry() {
        return this.metricsRegistry;
    }
}
