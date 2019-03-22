// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics.stats;

public interface Sample
{
    void clear();
    
    int size();
    
    void update(final long p0);
    
    Snapshot getSnapshot();
}
