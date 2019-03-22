// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics.stats;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class EWMA
{
    private static final int INTERVAL = 5;
    private static final double SECONDS_PER_MINUTE = 60.0;
    private static final int ONE_MINUTE = 1;
    private static final int FIVE_MINUTES = 5;
    private static final int FIFTEEN_MINUTES = 15;
    private static final double M1_ALPHA;
    private static final double M5_ALPHA;
    private static final double M15_ALPHA;
    private volatile boolean initialized;
    private volatile double rate;
    private final AtomicLong uncounted;
    private final double alpha;
    private final double interval;
    
    public static EWMA oneMinuteEWMA() {
        return new EWMA(EWMA.M1_ALPHA, 5L, TimeUnit.SECONDS);
    }
    
    public static EWMA fiveMinuteEWMA() {
        return new EWMA(EWMA.M5_ALPHA, 5L, TimeUnit.SECONDS);
    }
    
    public static EWMA fifteenMinuteEWMA() {
        return new EWMA(EWMA.M15_ALPHA, 5L, TimeUnit.SECONDS);
    }
    
    public EWMA(final double alpha, final long interval, final TimeUnit intervalUnit) {
        this.initialized = false;
        this.rate = 0.0;
        this.uncounted = new AtomicLong();
        this.interval = intervalUnit.toNanos(interval);
        this.alpha = alpha;
    }
    
    public void update(final long n) {
        this.uncounted.addAndGet(n);
    }
    
    public void tick() {
        final long count = this.uncounted.getAndSet(0L);
        final double instantRate = count / this.interval;
        if (this.initialized) {
            this.rate += this.alpha * (instantRate - this.rate);
        }
        else {
            this.rate = instantRate;
            this.initialized = true;
        }
    }
    
    public double rate(final TimeUnit rateUnit) {
        return this.rate * rateUnit.toNanos(1L);
    }
    
    static {
        M1_ALPHA = 1.0 - Math.exp(-0.08333333333333333);
        M5_ALPHA = 1.0 - Math.exp(-0.016666666666666666);
        M15_ALPHA = 1.0 - Math.exp(-0.005555555555555555);
    }
}
