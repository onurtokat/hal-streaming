// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics.util;

import java.util.Iterator;
import java.util.Set;
import com.yammer.metrics.core.VirtualMachineMetrics;
import com.yammer.metrics.core.HealthCheck;

public class DeadlockHealthCheck extends HealthCheck
{
    private final VirtualMachineMetrics vm;
    
    public DeadlockHealthCheck(final VirtualMachineMetrics vm) {
        super("deadlocks");
        this.vm = vm;
    }
    
    public DeadlockHealthCheck() {
        this(VirtualMachineMetrics.getInstance());
    }
    
    @Override
    protected Result check() throws Exception {
        final Set<String> threads = this.vm.deadlockedThreads();
        if (threads.isEmpty()) {
            return Result.healthy();
        }
        final StringBuilder builder = new StringBuilder("Deadlocked threads detected:\n");
        for (final String thread : threads) {
            builder.append(thread).append('\n');
        }
        return Result.unhealthy(builder.toString());
    }
}
