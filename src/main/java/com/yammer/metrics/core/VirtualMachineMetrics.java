// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics.core;

import java.lang.management.ManagementFactory;
import javax.management.AttributeList;
import javax.management.JMException;
import javax.management.Attribute;
import javax.management.ObjectName;
import java.lang.management.MonitorInfo;
import java.lang.management.LockInfo;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.util.Collection;
import java.util.ArrayList;
import java.lang.management.ThreadInfo;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Collections;
import java.util.TreeMap;
import java.util.Map;
import java.lang.management.MemoryUsage;
import javax.management.MBeanServer;
import java.lang.management.RuntimeMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ThreadMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.util.List;
import java.lang.management.MemoryMXBean;

public class VirtualMachineMetrics
{
    private static final int MAX_STACK_TRACE_DEPTH = 100;
    private static final VirtualMachineMetrics INSTANCE;
    private final MemoryMXBean memory;
    private final List<MemoryPoolMXBean> memoryPools;
    private final OperatingSystemMXBean os;
    private final ThreadMXBean threads;
    private final List<GarbageCollectorMXBean> garbageCollectors;
    private final RuntimeMXBean runtime;
    private final MBeanServer mBeanServer;
    
    public static VirtualMachineMetrics getInstance() {
        return VirtualMachineMetrics.INSTANCE;
    }
    
    VirtualMachineMetrics(final MemoryMXBean memory, final List<MemoryPoolMXBean> memoryPools, final OperatingSystemMXBean os, final ThreadMXBean threads, final List<GarbageCollectorMXBean> garbageCollectors, final RuntimeMXBean runtime, final MBeanServer mBeanServer) {
        this.memory = memory;
        this.memoryPools = memoryPools;
        this.os = os;
        this.threads = threads;
        this.garbageCollectors = garbageCollectors;
        this.runtime = runtime;
        this.mBeanServer = mBeanServer;
    }
    
    public double totalInit() {
        return this.memory.getHeapMemoryUsage().getInit() + this.memory.getNonHeapMemoryUsage().getInit();
    }
    
    public double totalUsed() {
        return this.memory.getHeapMemoryUsage().getUsed() + this.memory.getNonHeapMemoryUsage().getUsed();
    }
    
    public double totalMax() {
        return this.memory.getHeapMemoryUsage().getMax() + this.memory.getNonHeapMemoryUsage().getMax();
    }
    
    public double totalCommitted() {
        return this.memory.getHeapMemoryUsage().getCommitted() + this.memory.getNonHeapMemoryUsage().getCommitted();
    }
    
    public double heapInit() {
        return this.memory.getHeapMemoryUsage().getInit();
    }
    
    public double heapUsed() {
        return this.memory.getHeapMemoryUsage().getUsed();
    }
    
    public double heapMax() {
        return this.memory.getHeapMemoryUsage().getMax();
    }
    
    public double heapCommitted() {
        return this.memory.getHeapMemoryUsage().getCommitted();
    }
    
    public double heapUsage() {
        final MemoryUsage usage = this.memory.getHeapMemoryUsage();
        return usage.getUsed() / usage.getMax();
    }
    
    public double nonHeapUsage() {
        final MemoryUsage usage = this.memory.getNonHeapMemoryUsage();
        return usage.getUsed() / usage.getMax();
    }
    
    public Map<String, Double> memoryPoolUsage() {
        final Map<String, Double> pools = new TreeMap<String, Double>();
        for (final MemoryPoolMXBean pool : this.memoryPools) {
            final double max = (pool.getUsage().getMax() == -1L) ? pool.getUsage().getCommitted() : ((double)pool.getUsage().getMax());
            pools.put(pool.getName(), pool.getUsage().getUsed() / max);
        }
        return Collections.unmodifiableMap((Map<? extends String, ? extends Double>)pools);
    }
    
    public double fileDescriptorUsage() {
        try {
            final Method getOpenFileDescriptorCount = this.os.getClass().getDeclaredMethod("getOpenFileDescriptorCount", (Class<?>[])new Class[0]);
            getOpenFileDescriptorCount.setAccessible(true);
            final Long openFds = (Long)getOpenFileDescriptorCount.invoke(this.os, new Object[0]);
            final Method getMaxFileDescriptorCount = this.os.getClass().getDeclaredMethod("getMaxFileDescriptorCount", (Class<?>[])new Class[0]);
            getMaxFileDescriptorCount.setAccessible(true);
            final Long maxFds = (Long)getMaxFileDescriptorCount.invoke(this.os, new Object[0]);
            return openFds / maxFds;
        }
        catch (NoSuchMethodException e) {
            return Double.NaN;
        }
        catch (IllegalAccessException e2) {
            return Double.NaN;
        }
        catch (InvocationTargetException e3) {
            return Double.NaN;
        }
    }
    
    public String version() {
        return System.getProperty("java.runtime.version");
    }
    
    public String name() {
        return System.getProperty("java.vm.name");
    }
    
    public long uptime() {
        return TimeUnit.MILLISECONDS.toSeconds(this.runtime.getUptime());
    }
    
    public int threadCount() {
        return this.threads.getThreadCount();
    }
    
    public int daemonThreadCount() {
        return this.threads.getDaemonThreadCount();
    }
    
    public Map<String, GarbageCollectorStats> garbageCollectors() {
        final Map<String, GarbageCollectorStats> stats = new HashMap<String, GarbageCollectorStats>();
        for (final GarbageCollectorMXBean gc : this.garbageCollectors) {
            stats.put(gc.getName(), new GarbageCollectorStats(gc.getCollectionCount(), gc.getCollectionTime()));
        }
        return Collections.unmodifiableMap((Map<? extends String, ? extends GarbageCollectorStats>)stats);
    }
    
    public Set<String> deadlockedThreads() {
        final long[] threadIds = this.threads.findDeadlockedThreads();
        if (threadIds != null) {
            final Set<String> threads = new HashSet<String>();
            for (final ThreadInfo info : this.threads.getThreadInfo(threadIds, 100)) {
                final StringBuilder stackTrace = new StringBuilder();
                for (final StackTraceElement element : info.getStackTrace()) {
                    stackTrace.append("\t at ").append(element.toString()).append('\n');
                }
                threads.add(String.format("%s locked on %s (owned by %s):\n%s", info.getThreadName(), info.getLockName(), info.getLockOwnerName(), stackTrace.toString()));
            }
            return Collections.unmodifiableSet((Set<? extends String>)threads);
        }
        return Collections.emptySet();
    }
    
    public Map<Thread.State, Double> threadStatePercentages() {
        final Map<Thread.State, Double> conditions = new HashMap<Thread.State, Double>();
        for (final Thread.State state : Thread.State.values()) {
            conditions.put(state, 0.0);
        }
        final long[] allThreadIds = this.threads.getAllThreadIds();
        final ThreadInfo[] allThreads = this.threads.getThreadInfo(allThreadIds);
        int liveCount = 0;
        for (final ThreadInfo info : allThreads) {
            if (info != null) {
                final Thread.State state2 = info.getThreadState();
                conditions.put(state2, conditions.get(state2) + 1.0);
                ++liveCount;
            }
        }
        for (final Thread.State state3 : new ArrayList(conditions.keySet())) {
            conditions.put(state3, conditions.get(state3) / liveCount);
        }
        return Collections.unmodifiableMap((Map<? extends Thread.State, ? extends Double>)conditions);
    }
    
    public void threadDump(final OutputStream out) {
        final ThreadInfo[] threads = this.threads.dumpAllThreads(true, true);
        final PrintWriter writer = new PrintWriter(out, true);
        for (int ti = threads.length - 1; ti >= 0; --ti) {
            final ThreadInfo t = threads[ti];
            writer.printf("%s id=%d state=%s", t.getThreadName(), t.getThreadId(), t.getThreadState());
            final LockInfo lock = t.getLockInfo();
            if (lock != null && t.getThreadState() != Thread.State.BLOCKED) {
                writer.printf("\n    - waiting on <0x%08x> (a %s)", lock.getIdentityHashCode(), lock.getClassName());
                writer.printf("\n    - locked <0x%08x> (a %s)", lock.getIdentityHashCode(), lock.getClassName());
            }
            else if (lock != null && t.getThreadState() == Thread.State.BLOCKED) {
                writer.printf("\n    - waiting to lock <0x%08x> (a %s)", lock.getIdentityHashCode(), lock.getClassName());
            }
            if (t.isSuspended()) {
                writer.print(" (suspended)");
            }
            if (t.isInNative()) {
                writer.print(" (running in native)");
            }
            writer.println();
            if (t.getLockOwnerName() != null) {
                writer.printf("     owned by %s id=%d\n", t.getLockOwnerName(), t.getLockOwnerId());
            }
            final StackTraceElement[] elements = t.getStackTrace();
            final MonitorInfo[] monitors = t.getLockedMonitors();
            for (int i = 0; i < elements.length; ++i) {
                final StackTraceElement element = elements[i];
                writer.printf("    at %s\n", element);
                for (int j = 1; j < monitors.length; ++j) {
                    final MonitorInfo monitor = monitors[j];
                    if (monitor.getLockedStackDepth() == i) {
                        writer.printf("      - locked %s\n", monitor);
                    }
                }
            }
            writer.println();
            final LockInfo[] locks = t.getLockedSynchronizers();
            if (locks.length > 0) {
                writer.printf("    Locked synchronizers: count = %d\n", locks.length);
                for (final LockInfo l : locks) {
                    writer.printf("      - %s\n", l);
                }
                writer.println();
            }
        }
        writer.println();
        writer.flush();
    }
    
    public Map<String, BufferPoolStats> getBufferPoolStats() {
        try {
            final String[] attributes = { "Count", "MemoryUsed", "TotalCapacity" };
            final ObjectName direct = new ObjectName("java.nio:type=BufferPool,name=direct");
            final ObjectName mapped = new ObjectName("java.nio:type=BufferPool,name=mapped");
            final AttributeList directAttributes = this.mBeanServer.getAttributes(direct, attributes);
            final AttributeList mappedAttributes = this.mBeanServer.getAttributes(mapped, attributes);
            final Map<String, BufferPoolStats> stats = new TreeMap<String, BufferPoolStats>();
            final BufferPoolStats directStats = new BufferPoolStats((long)((ArrayList<Attribute>)directAttributes).get(0).getValue(), (long)((ArrayList<Attribute>)directAttributes).get(1).getValue(), (long)((ArrayList<Attribute>)directAttributes).get(2).getValue());
            stats.put("direct", directStats);
            final BufferPoolStats mappedStats = new BufferPoolStats((long)((ArrayList<Attribute>)mappedAttributes).get(0).getValue(), (long)((ArrayList<Attribute>)mappedAttributes).get(1).getValue(), (long)((ArrayList<Attribute>)mappedAttributes).get(2).getValue());
            stats.put("mapped", mappedStats);
            return Collections.unmodifiableMap((Map<? extends String, ? extends BufferPoolStats>)stats);
        }
        catch (JMException e) {
            return Collections.emptyMap();
        }
    }
    
    static {
        INSTANCE = new VirtualMachineMetrics(ManagementFactory.getMemoryMXBean(), ManagementFactory.getMemoryPoolMXBeans(), ManagementFactory.getOperatingSystemMXBean(), ManagementFactory.getThreadMXBean(), ManagementFactory.getGarbageCollectorMXBeans(), ManagementFactory.getRuntimeMXBean(), ManagementFactory.getPlatformMBeanServer());
    }
    
    public static class GarbageCollectorStats
    {
        private final long runs;
        private final long timeMS;
        
        private GarbageCollectorStats(final long runs, final long timeMS) {
            this.runs = runs;
            this.timeMS = timeMS;
        }
        
        public long getRuns() {
            return this.runs;
        }
        
        public long getTime(final TimeUnit unit) {
            return unit.convert(this.timeMS, TimeUnit.MILLISECONDS);
        }
    }
    
    public static class BufferPoolStats
    {
        private final long count;
        private final long memoryUsed;
        private final long totalCapacity;
        
        private BufferPoolStats(final long count, final long memoryUsed, final long totalCapacity) {
            this.count = count;
            this.memoryUsed = memoryUsed;
            this.totalCapacity = totalCapacity;
        }
        
        public long getCount() {
            return this.count;
        }
        
        public long getMemoryUsed() {
            return this.memoryUsed;
        }
        
        public long getTotalCapacity() {
            return this.totalCapacity;
        }
    }
}
