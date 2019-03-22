// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics.util;

import java.util.concurrent.atomic.AtomicInteger;
import com.yammer.metrics.core.Gauge;

public class ToggleGauge extends Gauge<Integer>
{
    private final AtomicInteger value;
    
    public ToggleGauge() {
        this.value = new AtomicInteger(1);
    }
    
    @Override
    public Integer value() {
        try {
            return this.value.get();
        }
        finally {
            this.value.set(0);
        }
    }
}
