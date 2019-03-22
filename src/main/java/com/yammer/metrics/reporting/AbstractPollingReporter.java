// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics.reporting;

import java.util.concurrent.TimeUnit;
import com.yammer.metrics.core.MetricsRegistry;
import java.util.concurrent.ScheduledExecutorService;

public abstract class AbstractPollingReporter extends AbstractReporter implements Runnable
{
    private final ScheduledExecutorService executor;
    
    protected AbstractPollingReporter(final MetricsRegistry registry, final String name) {
        super(registry);
        this.executor = registry.newScheduledThreadPool(1, name);
    }
    
    public void start(final long period, final TimeUnit unit) {
        this.executor.scheduleWithFixedDelay(this, period, period, unit);
    }
    
    public void shutdown(final long timeout, final TimeUnit unit) throws InterruptedException {
        this.executor.shutdown();
        this.executor.awaitTermination(timeout, unit);
    }
    
    @Override
    public void shutdown() {
        this.executor.shutdown();
        super.shutdown();
    }
    
    @Override
    public abstract void run();
}
