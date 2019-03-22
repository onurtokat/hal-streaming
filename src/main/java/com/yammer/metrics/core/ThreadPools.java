// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics.core;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.Iterator;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ConcurrentMap;

class ThreadPools
{
    private final ConcurrentMap<String, ScheduledExecutorService> threadPools;
    
    ThreadPools() {
        this.threadPools = new ConcurrentHashMap<String, ScheduledExecutorService>(100);
    }
    
    ScheduledExecutorService newScheduledThreadPool(final int poolSize, final String name) {
        final ScheduledExecutorService existing = this.threadPools.get(name);
        if (isValidExecutor(existing)) {
            return existing;
        }
        synchronized (this) {
            final ScheduledExecutorService lastChance = this.threadPools.get(name);
            if (isValidExecutor(lastChance)) {
                return lastChance;
            }
            final ScheduledExecutorService service = Executors.newScheduledThreadPool(poolSize, new NamedThreadFactory(name));
            this.threadPools.put(name, service);
            return service;
        }
    }
    
    private static boolean isValidExecutor(final ExecutorService executor) {
        return executor != null && !executor.isShutdown() && !executor.isTerminated();
    }
    
    void shutdown() {
        synchronized (this) {
            for (final ExecutorService executor : this.threadPools.values()) {
                executor.shutdown();
            }
            this.threadPools.clear();
        }
    }
    
    private static class NamedThreadFactory implements ThreadFactory
    {
        private final ThreadGroup group;
        private final AtomicInteger threadNumber;
        private final String namePrefix;
        
        NamedThreadFactory(final String name) {
            this.threadNumber = new AtomicInteger(1);
            final SecurityManager s = System.getSecurityManager();
            this.group = ((s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup());
            this.namePrefix = "metrics-" + name + "-thread-";
        }
        
        @Override
        public Thread newThread(final Runnable r) {
            final Thread t = new Thread(this.group, r, this.namePrefix + this.threadNumber.getAndIncrement(), 0L);
            t.setDaemon(true);
            if (t.getPriority() != 5) {
                t.setPriority(5);
            }
            return t;
        }
    }
}
