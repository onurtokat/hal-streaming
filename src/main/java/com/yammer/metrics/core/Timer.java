// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics.core;

import com.yammer.metrics.stats.Snapshot;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Timer implements Metered, Stoppable, Sampling, Summarizable
{
    private final TimeUnit durationUnit;
    private final TimeUnit rateUnit;
    private final Meter meter;
    private final Histogram histogram;
    private final Clock clock;
    
    Timer(final ScheduledExecutorService tickThread, final TimeUnit durationUnit, final TimeUnit rateUnit) {
        this(tickThread, durationUnit, rateUnit, Clock.defaultClock());
    }
    
    Timer(final ScheduledExecutorService tickThread, final TimeUnit durationUnit, final TimeUnit rateUnit, final Clock clock) {
        this.histogram = new Histogram(Histogram.SampleType.BIASED);
        this.durationUnit = durationUnit;
        this.rateUnit = rateUnit;
        this.meter = new Meter(tickThread, "calls", rateUnit, clock);
        this.clock = clock;
        this.clear();
    }
    
    public TimeUnit durationUnit() {
        return this.durationUnit;
    }
    
    @Override
    public TimeUnit rateUnit() {
        return this.rateUnit;
    }
    
    public void clear() {
        this.histogram.clear();
    }
    
    public void update(final long duration, final TimeUnit unit) {
        this.update(unit.toNanos(duration));
    }
    
    public <T> T time(final Callable<T> event) throws Exception {
        final long startTime = this.clock.tick();
        try {
            return event.call();
        }
        finally {
            this.update(this.clock.tick() - startTime);
        }
    }
    
    public TimerContext time() {
        return new TimerContext(this, this.clock);
    }
    
    @Override
    public long count() {
        return this.histogram.count();
    }
    
    @Override
    public double fifteenMinuteRate() {
        return this.meter.fifteenMinuteRate();
    }
    
    @Override
    public double fiveMinuteRate() {
        return this.meter.fiveMinuteRate();
    }
    
    @Override
    public double meanRate() {
        return this.meter.meanRate();
    }
    
    @Override
    public double oneMinuteRate() {
        return this.meter.oneMinuteRate();
    }
    
    @Override
    public double max() {
        return this.convertFromNS(this.histogram.max());
    }
    
    @Override
    public double min() {
        return this.convertFromNS(this.histogram.min());
    }
    
    @Override
    public double mean() {
        return this.convertFromNS(this.histogram.mean());
    }
    
    @Override
    public double stdDev() {
        return this.convertFromNS(this.histogram.stdDev());
    }
    
    @Override
    public double sum() {
        return this.convertFromNS(this.histogram.sum());
    }
    
    @Override
    public Snapshot getSnapshot() {
        final double[] values = this.histogram.getSnapshot().getValues();
        final double[] converted = new double[values.length];
        for (int i = 0; i < values.length; ++i) {
            converted[i] = this.convertFromNS(values[i]);
        }
        return new Snapshot(converted);
    }
    
    @Override
    public String eventType() {
        return this.meter.eventType();
    }
    
    private void update(final long duration) {
        if (duration >= 0L) {
            this.histogram.update(duration);
            this.meter.mark();
        }
    }
    
    private double convertFromNS(final double ns) {
        return ns / TimeUnit.NANOSECONDS.convert(1L, this.durationUnit);
    }
    
    @Override
    public void stop() {
        this.meter.stop();
    }
    
    @Override
    public <T> void processWith(final MetricProcessor<T> processor, final MetricName name, final T context) throws Exception {
        processor.processTimer(name, this, context);
    }
}
