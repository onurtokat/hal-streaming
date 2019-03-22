// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

public abstract class LowPriorityImplicitBijection$class
{
    public static ImplicitBijection reverse(final LowPriorityImplicitBijection $this, final Bijection bij) {
        return new Reverse(bij);
    }
    
    public static void $init$(final LowPriorityImplicitBijection $this) {
    }
}
