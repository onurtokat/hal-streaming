// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics.core;

import java.util.Iterator;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class HealthCheckRegistry
{
    private final ConcurrentMap<String, HealthCheck> healthChecks;
    
    public HealthCheckRegistry() {
        this.healthChecks = new ConcurrentHashMap<String, HealthCheck>();
    }
    
    public void register(final HealthCheck healthCheck) {
        this.healthChecks.putIfAbsent(healthCheck.getName(), healthCheck);
    }
    
    public void unregister(final String name) {
        this.healthChecks.remove(name);
    }
    
    public void unregister(final HealthCheck healthCheck) {
        this.unregister(healthCheck.getName());
    }
    
    public SortedMap<String, HealthCheck.Result> runHealthChecks() {
        final SortedMap<String, HealthCheck.Result> results = new TreeMap<String, HealthCheck.Result>();
        for (final Map.Entry<String, HealthCheck> entry : this.healthChecks.entrySet()) {
            final HealthCheck.Result result = entry.getValue().execute();
            results.put(entry.getKey(), result);
        }
        return Collections.unmodifiableSortedMap((SortedMap<String, ? extends HealthCheck.Result>)results);
    }
}
