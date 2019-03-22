// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics.core;

import java.util.concurrent.TimeUnit;

public interface Metered extends Metric
{
    TimeUnit rateUnit();
    
    String eventType();
    
    long count();
    
    double fifteenMinuteRate();
    
    double fiveMinuteRate();
    
    double meanRate();
    
    double oneMinuteRate();
}
