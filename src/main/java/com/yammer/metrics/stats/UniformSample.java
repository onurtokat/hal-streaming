// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics.stats;

import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.concurrent.atomic.AtomicLong;

public class UniformSample implements Sample
{
    private static final int BITS_PER_LONG = 63;
    private final AtomicLong count;
    private final AtomicLongArray values;
    
    public UniformSample(final int reservoirSize) {
        this.count = new AtomicLong();
        this.values = new AtomicLongArray(reservoirSize);
        this.clear();
    }
    
    @Override
    public void clear() {
        for (int i = 0; i < this.values.length(); ++i) {
            this.values.set(i, 0L);
        }
        this.count.set(0L);
    }
    
    @Override
    public int size() {
        final long c = this.count.get();
        if (c > this.values.length()) {
            return this.values.length();
        }
        return (int)c;
    }
    
    @Override
    public void update(final long value) {
        final long c = this.count.incrementAndGet();
        if (c <= this.values.length()) {
            this.values.set((int)c - 1, value);
        }
        else {
            final long r = nextLong(c);
            if (r < this.values.length()) {
                this.values.set((int)r, value);
            }
        }
    }
    
    private static long nextLong(final long n) {
        long bits;
        long val;
        do {
            bits = (ThreadLocalRandom.current().nextLong() & Long.MAX_VALUE);
            val = bits % n;
        } while (bits - val + (n - 1L) < 0L);
        return val;
    }
    
    @Override
    public Snapshot getSnapshot() {
        final int s = this.size();
        final List<Long> copy = new ArrayList<Long>(s);
        for (int i = 0; i < s; ++i) {
            copy.add(this.values.get(i));
        }
        return new Snapshot(copy);
    }
}
