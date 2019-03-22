// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics.core;

import com.yammer.metrics.stats.ExponentiallyDecayingSample;
import com.yammer.metrics.stats.UniformSample;
import com.yammer.metrics.stats.Snapshot;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicLong;
import com.yammer.metrics.stats.Sample;

public class Histogram implements Metric, Sampling, Summarizable
{
    private static final int DEFAULT_SAMPLE_SIZE = 1028;
    private static final double DEFAULT_ALPHA = 0.015;
    private final Sample sample;
    private final AtomicLong min;
    private final AtomicLong max;
    private final AtomicLong sum;
    private final AtomicReference<double[]> variance;
    private final AtomicLong count;
    
    Histogram(final SampleType type) {
        this(type.newSample());
    }
    
    Histogram(final Sample sample) {
        this.min = new AtomicLong();
        this.max = new AtomicLong();
        this.sum = new AtomicLong();
        this.variance = new AtomicReference<double[]>(new double[] { -1.0, 0.0 });
        this.count = new AtomicLong();
        this.sample = sample;
        this.clear();
    }
    
    public void clear() {
        this.sample.clear();
        this.count.set(0L);
        this.max.set(Long.MIN_VALUE);
        this.min.set(Long.MAX_VALUE);
        this.sum.set(0L);
        this.variance.set(new double[] { -1.0, 0.0 });
    }
    
    public void update(final int value) {
        this.update((long)value);
    }
    
    public void update(final long value) {
        this.count.incrementAndGet();
        this.sample.update(value);
        this.setMax(value);
        this.setMin(value);
        this.sum.getAndAdd(value);
        this.updateVariance(value);
    }
    
    public long count() {
        return this.count.get();
    }
    
    @Override
    public double max() {
        if (this.count() > 0L) {
            return this.max.get();
        }
        return 0.0;
    }
    
    @Override
    public double min() {
        if (this.count() > 0L) {
            return this.min.get();
        }
        return 0.0;
    }
    
    @Override
    public double mean() {
        if (this.count() > 0L) {
            return this.sum.get() / this.count();
        }
        return 0.0;
    }
    
    @Override
    public double stdDev() {
        if (this.count() > 0L) {
            return Math.sqrt(this.variance());
        }
        return 0.0;
    }
    
    @Override
    public double sum() {
        return this.sum.get();
    }
    
    @Override
    public Snapshot getSnapshot() {
        return this.sample.getSnapshot();
    }
    
    private double variance() {
        if (this.count() <= 1L) {
            return 0.0;
        }
        return this.variance.get()[1] / (this.count() - 1L);
    }
    
    private void setMax(final long potentialMax) {
        long currentMax;
        for (boolean done = false; !done; done = (currentMax >= potentialMax || this.max.compareAndSet(currentMax, potentialMax))) {
            currentMax = this.max.get();
        }
    }
    
    private void setMin(final long potentialMin) {
        long currentMin;
        for (boolean done = false; !done; done = (currentMin <= potentialMin || this.min.compareAndSet(currentMin, potentialMin))) {
            currentMin = this.min.get();
        }
    }
    
    private void updateVariance(final long value) {
        double[] oldValues;
        double[] newValues;
        do {
            oldValues = this.variance.get();
            newValues = new double[2];
            if (oldValues[0] == -1.0) {
                newValues[0] = value;
                newValues[1] = 0.0;
            }
            else {
                final double oldM = oldValues[0];
                final double oldS = oldValues[1];
                final double newM = oldM + (value - oldM) / this.count();
                final double newS = oldS + (value - oldM) * (value - newM);
                newValues[0] = newM;
                newValues[1] = newS;
            }
        } while (!this.variance.compareAndSet(oldValues, newValues));
    }
    
    @Override
    public <T> void processWith(final MetricProcessor<T> processor, final MetricName name, final T context) throws Exception {
        processor.processHistogram(name, this, context);
    }
    
    enum SampleType
    {
        UNIFORM {
            @Override
            public Sample newSample() {
                return new UniformSample(1028);
            }
        }, 
        BIASED {
            @Override
            public Sample newSample() {
                return new ExponentiallyDecayingSample(1028, 0.015);
            }
        };
        
        public abstract Sample newSample();
    }
}
