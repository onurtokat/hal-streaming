// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics.stats;

import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import com.yammer.metrics.core.Clock;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.ConcurrentSkipListMap;

public class ExponentiallyDecayingSample implements Sample
{
    private static final long RESCALE_THRESHOLD;
    private final ConcurrentSkipListMap<Double, Long> values;
    private final ReentrantReadWriteLock lock;
    private final double alpha;
    private final int reservoirSize;
    private final AtomicLong count;
    private volatile long startTime;
    private final AtomicLong nextScaleTime;
    private final Clock clock;
    
    public ExponentiallyDecayingSample(final int reservoirSize, final double alpha) {
        this(reservoirSize, alpha, Clock.defaultClock());
    }
    
    public ExponentiallyDecayingSample(final int reservoirSize, final double alpha, final Clock clock) {
        this.count = new AtomicLong(0L);
        this.nextScaleTime = new AtomicLong(0L);
        this.values = new ConcurrentSkipListMap<Double, Long>();
        this.lock = new ReentrantReadWriteLock();
        this.alpha = alpha;
        this.reservoirSize = reservoirSize;
        this.clock = clock;
        this.clear();
    }
    
    @Override
    public void clear() {
        this.lockForRescale();
        try {
            this.values.clear();
            this.count.set(0L);
            this.startTime = this.currentTimeInSeconds();
            this.nextScaleTime.set(this.clock.tick() + ExponentiallyDecayingSample.RESCALE_THRESHOLD);
        }
        finally {
            this.unlockForRescale();
        }
    }
    
    @Override
    public int size() {
        return (int)Math.min(this.reservoirSize, this.count.get());
    }
    
    @Override
    public void update(final long value) {
        this.update(value, this.currentTimeInSeconds());
    }
    
    public void update(final long value, final long timestamp) {
        this.rescaleIfNeeded();
        this.lockForRegularUsage();
        try {
            final double priority = this.weight(timestamp - this.startTime) / ThreadLocalRandom.current().nextDouble();
            final long newCount = this.count.incrementAndGet();
            if (newCount <= this.reservoirSize) {
                this.values.put(priority, value);
            }
            else {
                Double first = this.values.firstKey();
                if (first < priority && this.values.putIfAbsent(priority, value) == null) {
                    while (this.values.remove(first) == null) {
                        first = this.values.firstKey();
                    }
                }
            }
        }
        finally {
            this.unlockForRegularUsage();
        }
    }
    
    private void rescaleIfNeeded() {
        final long now = this.clock.tick();
        final long next = this.nextScaleTime.get();
        if (now >= next) {
            this.rescale(now, next);
        }
    }
    
    @Override
    public Snapshot getSnapshot() {
        this.lockForRegularUsage();
        try {
            return new Snapshot(this.values.values());
        }
        finally {
            this.unlockForRegularUsage();
        }
    }
    
    private long currentTimeInSeconds() {
        return TimeUnit.MILLISECONDS.toSeconds(this.clock.time());
    }
    
    private double weight(final long t) {
        return Math.exp(this.alpha * t);
    }
    
    private void rescale(final long now, final long next) {
        if (this.nextScaleTime.compareAndSet(next, now + ExponentiallyDecayingSample.RESCALE_THRESHOLD)) {
            this.lockForRescale();
            try {
                final long oldStartTime = this.startTime;
                this.startTime = this.currentTimeInSeconds();
                final ArrayList<Double> keys = new ArrayList<Double>(this.values.keySet());
                for (final Double key : keys) {
                    final Long value = this.values.remove(key);
                    this.values.put(key * Math.exp(-this.alpha * (this.startTime - oldStartTime)), value);
                }
                this.count.set(this.values.size());
            }
            finally {
                this.unlockForRescale();
            }
        }
    }
    
    private void unlockForRescale() {
        this.lock.writeLock().unlock();
    }
    
    private void lockForRescale() {
        this.lock.writeLock().lock();
    }
    
    private void lockForRegularUsage() {
        this.lock.readLock().lock();
    }
    
    private void unlockForRegularUsage() {
        this.lock.readLock().unlock();
    }
    
    static {
        RESCALE_THRESHOLD = TimeUnit.HOURS.toNanos(1L);
    }
}
