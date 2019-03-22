// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics.core;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.SortedMap;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class MetricsRegistry
{
    private static final int EXPECTED_METRIC_COUNT = 1024;
    private final Clock clock;
    private final ConcurrentMap<MetricName, Metric> metrics;
    private final ThreadPools threadPools;
    private final List<MetricsRegistryListener> listeners;
    
    public MetricsRegistry() {
        this(Clock.defaultClock());
    }
    
    public MetricsRegistry(final Clock clock) {
        this.clock = clock;
        this.metrics = this.newMetricsMap();
        this.threadPools = new ThreadPools();
        this.listeners = new CopyOnWriteArrayList<MetricsRegistryListener>();
    }
    
    public <T> Gauge<T> newGauge(final Class<?> klass, final String name, final Gauge<T> metric) {
        return this.newGauge(klass, name, null, metric);
    }
    
    public <T> Gauge<T> newGauge(final Class<?> klass, final String name, final String scope, final Gauge<T> metric) {
        return this.newGauge(this.createName(klass, name, scope), metric);
    }
    
    public <T> Gauge<T> newGauge(final MetricName metricName, final Gauge<T> metric) {
        return this.getOrAdd(metricName, metric);
    }
    
    public Counter newCounter(final Class<?> klass, final String name) {
        return this.newCounter(klass, name, null);
    }
    
    public Counter newCounter(final Class<?> klass, final String name, final String scope) {
        return this.newCounter(this.createName(klass, name, scope));
    }
    
    public Counter newCounter(final MetricName metricName) {
        return this.getOrAdd(metricName, new Counter());
    }
    
    public Histogram newHistogram(final Class<?> klass, final String name, final boolean biased) {
        return this.newHistogram(klass, name, null, biased);
    }
    
    public Histogram newHistogram(final Class<?> klass, final String name, final String scope, final boolean biased) {
        return this.newHistogram(this.createName(klass, name, scope), biased);
    }
    
    public Histogram newHistogram(final Class<?> klass, final String name) {
        return this.newHistogram(klass, name, false);
    }
    
    public Histogram newHistogram(final Class<?> klass, final String name, final String scope) {
        return this.newHistogram(klass, name, scope, false);
    }
    
    public Histogram newHistogram(final MetricName metricName, final boolean biased) {
        return this.getOrAdd(metricName, new Histogram(biased ? Histogram.SampleType.BIASED : Histogram.SampleType.UNIFORM));
    }
    
    public Meter newMeter(final Class<?> klass, final String name, final String eventType, final TimeUnit unit) {
        return this.newMeter(klass, name, null, eventType, unit);
    }
    
    public Meter newMeter(final Class<?> klass, final String name, final String scope, final String eventType, final TimeUnit unit) {
        return this.newMeter(this.createName(klass, name, scope), eventType, unit);
    }
    
    public Meter newMeter(final MetricName metricName, final String eventType, final TimeUnit unit) {
        final Metric existingMetric = this.metrics.get(metricName);
        if (existingMetric != null) {
            return (Meter)existingMetric;
        }
        return this.getOrAdd(metricName, new Meter(this.newMeterTickThreadPool(), eventType, unit, this.clock));
    }
    
    public Timer newTimer(final Class<?> klass, final String name) {
        return this.newTimer(klass, name, null, TimeUnit.MILLISECONDS, TimeUnit.SECONDS);
    }
    
    public Timer newTimer(final Class<?> klass, final String name, final TimeUnit durationUnit, final TimeUnit rateUnit) {
        return this.newTimer(klass, name, null, durationUnit, rateUnit);
    }
    
    public Timer newTimer(final Class<?> klass, final String name, final String scope) {
        return this.newTimer(klass, name, scope, TimeUnit.MILLISECONDS, TimeUnit.SECONDS);
    }
    
    public Timer newTimer(final Class<?> klass, final String name, final String scope, final TimeUnit durationUnit, final TimeUnit rateUnit) {
        return this.newTimer(this.createName(klass, name, scope), durationUnit, rateUnit);
    }
    
    public Timer newTimer(final MetricName metricName, final TimeUnit durationUnit, final TimeUnit rateUnit) {
        final Metric existingMetric = this.metrics.get(metricName);
        if (existingMetric != null) {
            return (Timer)existingMetric;
        }
        return this.getOrAdd(metricName, new Timer(this.newMeterTickThreadPool(), durationUnit, rateUnit, this.clock));
    }
    
    public Map<MetricName, Metric> allMetrics() {
        return Collections.unmodifiableMap((Map<? extends MetricName, ? extends Metric>)this.metrics);
    }
    
    public SortedMap<String, SortedMap<MetricName, Metric>> groupedMetrics() {
        return this.groupedMetrics(MetricPredicate.ALL);
    }
    
    public SortedMap<String, SortedMap<MetricName, Metric>> groupedMetrics(final MetricPredicate predicate) {
        final SortedMap<String, SortedMap<MetricName, Metric>> groups = new TreeMap<String, SortedMap<MetricName, Metric>>();
        for (final Map.Entry<MetricName, Metric> entry : this.metrics.entrySet()) {
            final String qualifiedTypeName = entry.getKey().getGroup() + "." + entry.getKey().getType();
            if (predicate.matches(entry.getKey(), entry.getValue())) {
                String scopedName;
                if (entry.getKey().hasScope()) {
                    scopedName = qualifiedTypeName + "." + entry.getKey().getScope();
                }
                else {
                    scopedName = qualifiedTypeName;
                }
                SortedMap<MetricName, Metric> group = groups.get(scopedName);
                if (group == null) {
                    group = new TreeMap<MetricName, Metric>();
                    groups.put(scopedName, group);
                }
                group.put(entry.getKey(), entry.getValue());
            }
        }
        return Collections.unmodifiableSortedMap((SortedMap<String, ? extends SortedMap<MetricName, Metric>>)groups);
    }
    
    public void shutdown() {
        this.threadPools.shutdown();
    }
    
    public ScheduledExecutorService newScheduledThreadPool(final int poolSize, final String name) {
        return this.threadPools.newScheduledThreadPool(poolSize, name);
    }
    
    public void removeMetric(final Class<?> klass, final String name) {
        this.removeMetric(klass, name, null);
    }
    
    public void removeMetric(final Class<?> klass, final String name, final String scope) {
        this.removeMetric(this.createName(klass, name, scope));
    }
    
    public void removeMetric(final MetricName name) {
        final Metric metric = this.metrics.remove(name);
        if (metric != null) {
            if (metric instanceof Stoppable) {
                ((Stoppable)metric).stop();
            }
            this.notifyMetricRemoved(name);
        }
    }
    
    public void addListener(final MetricsRegistryListener listener) {
        this.listeners.add(listener);
        for (final Map.Entry<MetricName, Metric> entry : this.metrics.entrySet()) {
            listener.onMetricAdded(entry.getKey(), entry.getValue());
        }
    }
    
    public void removeListener(final MetricsRegistryListener listener) {
        this.listeners.remove(listener);
    }
    
    protected MetricName createName(final Class<?> klass, final String name, final String scope) {
        return new MetricName(klass, name, scope);
    }
    
    protected ConcurrentMap<MetricName, Metric> newMetricsMap() {
        return new ConcurrentHashMap<MetricName, Metric>(1024);
    }
    
    protected final <T extends Metric> T getOrAdd(final MetricName name, final T metric) {
        final Metric existingMetric = this.metrics.get(name);
        if (existingMetric != null) {
            return (T)existingMetric;
        }
        final Metric justAddedMetric = this.metrics.putIfAbsent(name, metric);
        if (justAddedMetric == null) {
            this.notifyMetricAdded(name, metric);
            return metric;
        }
        if (metric instanceof Stoppable) {
            ((Stoppable)metric).stop();
        }
        return (T)justAddedMetric;
    }
    
    private ScheduledExecutorService newMeterTickThreadPool() {
        return this.threadPools.newScheduledThreadPool(2, "meter-tick");
    }
    
    private void notifyMetricRemoved(final MetricName name) {
        for (final MetricsRegistryListener listener : this.listeners) {
            listener.onMetricRemoved(name);
        }
    }
    
    private void notifyMetricAdded(final MetricName name, final Metric metric) {
        for (final MetricsRegistryListener listener : this.listeners) {
            listener.onMetricAdded(name, metric);
        }
    }
}
