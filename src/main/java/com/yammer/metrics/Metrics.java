// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics;

import com.yammer.metrics.reporting.JmxReporter;
import com.yammer.metrics.core.Timer;
import com.yammer.metrics.core.Meter;
import java.util.concurrent.TimeUnit;
import com.yammer.metrics.core.Histogram;
import com.yammer.metrics.core.Counter;
import com.yammer.metrics.core.MetricName;
import com.yammer.metrics.core.Gauge;
import com.yammer.metrics.core.MetricsRegistry;

public class Metrics
{
    private static final MetricsRegistry DEFAULT_REGISTRY;
    private static final Thread SHUTDOWN_HOOK;
    
    public static <T> Gauge<T> newGauge(final Class<?> klass, final String name, final Gauge<T> metric) {
        return Metrics.DEFAULT_REGISTRY.newGauge(klass, name, metric);
    }
    
    public static <T> Gauge<T> newGauge(final Class<?> klass, final String name, final String scope, final Gauge<T> metric) {
        return Metrics.DEFAULT_REGISTRY.newGauge(klass, name, scope, metric);
    }
    
    public static <T> Gauge<T> newGauge(final MetricName metricName, final Gauge<T> metric) {
        return Metrics.DEFAULT_REGISTRY.newGauge(metricName, metric);
    }
    
    public static Counter newCounter(final Class<?> klass, final String name) {
        return Metrics.DEFAULT_REGISTRY.newCounter(klass, name);
    }
    
    public static Counter newCounter(final Class<?> klass, final String name, final String scope) {
        return Metrics.DEFAULT_REGISTRY.newCounter(klass, name, scope);
    }
    
    public static Counter newCounter(final MetricName metricName) {
        return Metrics.DEFAULT_REGISTRY.newCounter(metricName);
    }
    
    public static Histogram newHistogram(final Class<?> klass, final String name, final boolean biased) {
        return Metrics.DEFAULT_REGISTRY.newHistogram(klass, name, biased);
    }
    
    public static Histogram newHistogram(final Class<?> klass, final String name, final String scope, final boolean biased) {
        return Metrics.DEFAULT_REGISTRY.newHistogram(klass, name, scope, biased);
    }
    
    public static Histogram newHistogram(final MetricName metricName, final boolean biased) {
        return Metrics.DEFAULT_REGISTRY.newHistogram(metricName, biased);
    }
    
    public static Histogram newHistogram(final Class<?> klass, final String name) {
        return Metrics.DEFAULT_REGISTRY.newHistogram(klass, name);
    }
    
    public static Histogram newHistogram(final Class<?> klass, final String name, final String scope) {
        return Metrics.DEFAULT_REGISTRY.newHistogram(klass, name, scope);
    }
    
    public static Histogram newHistogram(final MetricName metricName) {
        return newHistogram(metricName, false);
    }
    
    public static Meter newMeter(final Class<?> klass, final String name, final String eventType, final TimeUnit unit) {
        return Metrics.DEFAULT_REGISTRY.newMeter(klass, name, eventType, unit);
    }
    
    public static Meter newMeter(final Class<?> klass, final String name, final String scope, final String eventType, final TimeUnit unit) {
        return Metrics.DEFAULT_REGISTRY.newMeter(klass, name, scope, eventType, unit);
    }
    
    public static Meter newMeter(final MetricName metricName, final String eventType, final TimeUnit unit) {
        return Metrics.DEFAULT_REGISTRY.newMeter(metricName, eventType, unit);
    }
    
    public static Timer newTimer(final Class<?> klass, final String name, final TimeUnit durationUnit, final TimeUnit rateUnit) {
        return Metrics.DEFAULT_REGISTRY.newTimer(klass, name, durationUnit, rateUnit);
    }
    
    public static Timer newTimer(final Class<?> klass, final String name) {
        return Metrics.DEFAULT_REGISTRY.newTimer(klass, name);
    }
    
    public static Timer newTimer(final Class<?> klass, final String name, final String scope, final TimeUnit durationUnit, final TimeUnit rateUnit) {
        return Metrics.DEFAULT_REGISTRY.newTimer(klass, name, scope, durationUnit, rateUnit);
    }
    
    public static Timer newTimer(final Class<?> klass, final String name, final String scope) {
        return Metrics.DEFAULT_REGISTRY.newTimer(klass, name, scope);
    }
    
    public static Timer newTimer(final MetricName metricName, final TimeUnit durationUnit, final TimeUnit rateUnit) {
        return Metrics.DEFAULT_REGISTRY.newTimer(metricName, durationUnit, rateUnit);
    }
    
    public static MetricsRegistry defaultRegistry() {
        return Metrics.DEFAULT_REGISTRY;
    }
    
    public static void shutdown() {
        Metrics.DEFAULT_REGISTRY.shutdown();
        JmxReporter.shutdownDefault();
        Runtime.getRuntime().removeShutdownHook(Metrics.SHUTDOWN_HOOK);
    }
    
    static {
        DEFAULT_REGISTRY = new MetricsRegistry();
        SHUTDOWN_HOOK = new Thread() {
            @Override
            public void run() {
                JmxReporter.shutdownDefault();
            }
        };
        JmxReporter.startDefault(Metrics.DEFAULT_REGISTRY);
        Runtime.getRuntime().addShutdownHook(Metrics.SHUTDOWN_HOOK);
    }
}
