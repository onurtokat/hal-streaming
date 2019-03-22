// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics.core;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import com.yammer.metrics.stats.EWMA;

public class Meter implements Metered, Stoppable
{
    private static final long INTERVAL = 5L;
    private final EWMA m1Rate;
    private final EWMA m5Rate;
    private final EWMA m15Rate;
    private final AtomicLong count;
    private final long startTime;
    private final TimeUnit rateUnit;
    private final String eventType;
    private final ScheduledFuture<?> future;
    private final Clock clock;
    
    Meter(final ScheduledExecutorService tickThread, final String eventType, final TimeUnit rateUnit, final Clock clock) {
        this.m1Rate = EWMA.oneMinuteEWMA();
        this.m5Rate = EWMA.fiveMinuteEWMA();
        this.m15Rate = EWMA.fifteenMinuteEWMA();
        this.count = new AtomicLong();
        this.rateUnit = rateUnit;
        this.eventType = eventType;
        this.future = tickThread.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Meter.this.tick();
            }
        }, 5L, 5L, TimeUnit.SECONDS);
        this.clock = clock;
        this.startTime = this.clock.tick();
    }
    
    @Override
    public TimeUnit rateUnit() {
        return this.rateUnit;
    }
    
    @Override
    public String eventType() {
        return this.eventType;
    }
    
    void tick() {
        this.m1Rate.tick();
        this.m5Rate.tick();
        this.m15Rate.tick();
    }
    
    public void mark() {
        this.mark(1L);
    }
    
    public void mark(final long n) {
        this.count.addAndGet(n);
        this.m1Rate.update(n);
        this.m5Rate.update(n);
        this.m15Rate.update(n);
    }
    
    @Override
    public long count() {
        return this.count.get();
    }
    
    @Override
    public double fifteenMinuteRate() {
        return this.m15Rate.rate(this.rateUnit);
    }
    
    @Override
    public double fiveMinuteRate() {
        return this.m5Rate.rate(this.rateUnit);
    }
    
    @Override
    public double meanRate() {
        if (this.count() == 0L) {
            return 0.0;
        }
        final long elapsed = this.clock.tick() - this.startTime;
        return this.convertNsRate(this.count() / elapsed);
    }
    
    @Override
    public double oneMinuteRate() {
        return this.m1Rate.rate(this.rateUnit);
    }
    
    private double convertNsRate(final double ratePerNs) {
        return ratePerNs * this.rateUnit.toNanos(1L);
    }
    
    @Override
    public void stop() {
        this.future.cancel(false);
    }
    
    @Override
    public <T> void processWith(final MetricProcessor<T> processor, final MetricName name, final T context) throws Exception {
        processor.processMeter(name, this, context);
    }
}
