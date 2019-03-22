// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.Tuple2;

public final class SwapBijection$
{
    public static final SwapBijection$ MODULE$;
    
    static {
        new SwapBijection$();
    }
    
    public <T, U> AbstractBijection<Tuple2<T, U>, Tuple2<U, T>> apply() {
        return (AbstractBijection<Tuple2<T, U>, Tuple2<U, T>>)new SwapBijection$$anon.SwapBijection$$anon$8();
    }
    
    private SwapBijection$() {
        MODULE$ = this;
    }
}
