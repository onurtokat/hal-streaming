// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics.core;

import java.util.concurrent.TimeUnit;

public class TimerContext
{
    private final Timer timer;
    private final Clock clock;
    private final long startTime;
    
    TimerContext(final Timer timer, final Clock clock) {
        this.timer = timer;
        this.clock = clock;
        this.startTime = clock.tick();
    }
    
    public void stop() {
        this.timer.update(this.clock.tick() - this.startTime, TimeUnit.NANOSECONDS);
    }
}
