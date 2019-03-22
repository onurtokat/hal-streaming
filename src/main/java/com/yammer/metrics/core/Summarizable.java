// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics.core;

public interface Summarizable
{
    double max();
    
    double min();
    
    double mean();
    
    double stdDev();
    
    double sum();
}
