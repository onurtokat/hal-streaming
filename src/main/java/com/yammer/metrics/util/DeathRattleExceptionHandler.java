// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics.util;

import org.slf4j.LoggerFactory;
import com.yammer.metrics.core.Counter;
import org.slf4j.Logger;

public class DeathRattleExceptionHandler implements Thread.UncaughtExceptionHandler
{
    private static final Logger LOGGER;
    private final Counter counter;
    
    public DeathRattleExceptionHandler(final Counter counter) {
        this.counter = counter;
    }
    
    @Override
    public void uncaughtException(final Thread t, final Throwable e) {
        this.counter.inc();
        DeathRattleExceptionHandler.LOGGER.error("Uncaught exception on thread {}", t, e);
    }
    
    static {
        LOGGER = LoggerFactory.getLogger(DeathRattleExceptionHandler.class);
    }
}
