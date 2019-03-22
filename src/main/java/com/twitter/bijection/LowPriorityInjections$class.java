// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

public abstract class LowPriorityInjections$class
{
    public static Injection fromImplicitBijection(final LowPriorityInjections $this, final ImplicitBijection bij) {
        return (Injection)new LowPriorityInjections$$anon.LowPriorityInjections$$anon$4($this, bij);
    }
    
    public static void $init$(final LowPriorityInjections $this) {
    }
}
