// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics;

import java.util.Map;
import com.yammer.metrics.core.HealthCheck;
import com.yammer.metrics.core.HealthCheckRegistry;

public class HealthChecks
{
    private static final HealthCheckRegistry DEFAULT_REGISTRY;
    
    public static void register(final HealthCheck healthCheck) {
        HealthChecks.DEFAULT_REGISTRY.register(healthCheck);
    }
    
    public static Map<String, HealthCheck.Result> runHealthChecks() {
        return HealthChecks.DEFAULT_REGISTRY.runHealthChecks();
    }
    
    public static HealthCheckRegistry defaultRegistry() {
        return HealthChecks.DEFAULT_REGISTRY;
    }
    
    static {
        DEFAULT_REGISTRY = new HealthCheckRegistry();
    }
}
