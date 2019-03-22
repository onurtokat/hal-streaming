// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics.core;

public interface MetricPredicate
{
    public static final MetricPredicate ALL = new MetricPredicate() {
        @Override
        public boolean matches(final MetricName name, final Metric metric) {
            return true;
        }
    };
    
    boolean matches(final MetricName p0, final Metric p1);
}
