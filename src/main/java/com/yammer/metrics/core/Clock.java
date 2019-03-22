// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics.core;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public abstract class Clock
{
    private static final Clock DEFAULT;
    
    public abstract long tick();
    
    public long time() {
        return System.currentTimeMillis();
    }
    
    public static Clock defaultClock() {
        return Clock.DEFAULT;
    }
    
    static {
        DEFAULT = new UserTimeClock();
    }
    
    public static class UserTimeClock extends Clock
    {
        @Override
        public long tick() {
            return System.nanoTime();
        }
    }
    
    public static class CpuTimeClock extends Clock
    {
        private static final ThreadMXBean THREAD_MX_BEAN;
        
        @Override
        public long tick() {
            return CpuTimeClock.THREAD_MX_BEAN.getCurrentThreadCpuTime();
        }
        
        static {
            THREAD_MX_BEAN = ManagementFactory.getThreadMXBean();
        }
    }
}
