// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics.core;

import com.yammer.metrics.stats.Snapshot;

public interface Sampling
{
    Snapshot getSnapshot();
}
