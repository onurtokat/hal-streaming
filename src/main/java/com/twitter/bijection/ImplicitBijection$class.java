// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

public abstract class ImplicitBijection$class
{
    public static Object apply(final ImplicitBijection $this, final Object a) {
        return $this.bijection().apply(a);
    }
    
    public static Object invert(final ImplicitBijection $this, final Object b) {
        return $this.bijection().invert(b);
    }
    
    public static void $init$(final ImplicitBijection $this) {
    }
}
