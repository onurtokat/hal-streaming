// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.reflect.ClassTag;

public final class CastInjection$
{
    public static final CastInjection$ MODULE$;
    
    static {
        new CastInjection$();
    }
    
    public <A, B> Injection<A, B> of(final ClassTag<A> cmf) {
        return (Injection<A, B>)new CastInjection$$anon.CastInjection$$anon$1((ClassTag)cmf);
    }
    
    private CastInjection$() {
        MODULE$ = this;
    }
}
