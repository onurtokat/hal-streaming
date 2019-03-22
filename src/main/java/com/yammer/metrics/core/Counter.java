// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics.core;

import java.util.concurrent.atomic.AtomicLong;

public class Counter implements Metric
{
    private final AtomicLong count;
    
    Counter() {
        this.count = new AtomicLong(0L);
    }
    
    public void inc() {
        this.inc(1L);
    }
    
    public void inc(final long n) {
        this.count.addAndGet(n);
    }
    
    public void dec() {
        this.dec(1L);
    }
    
    public void dec(final long n) {
        this.count.addAndGet(0L - n);
    }
    
    public long count() {
        return this.count.get();
    }
    
    public void clear() {
        this.count.set(0L);
    }
    
    @Override
    public <T> void processWith(final MetricProcessor<T> processor, final MetricName name, final T context) throws Exception {
        processor.processCounter(name, this, context);
    }
}
