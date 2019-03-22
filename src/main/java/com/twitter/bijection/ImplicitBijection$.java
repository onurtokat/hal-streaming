// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

public final class ImplicitBijection$ implements LowPriorityImplicitBijection
{
    public static final ImplicitBijection$ MODULE$;
    
    static {
        new ImplicitBijection$();
    }
    
    @Override
    public <A, B> ImplicitBijection<A, B> reverse(final Bijection<B, A> bij) {
        return (ImplicitBijection<A, B>)LowPriorityImplicitBijection$class.reverse(this, bij);
    }
    
    public <A, B> ImplicitBijection<A, B> forward(final Bijection<A, B> bij) {
        return new Forward<A, B>(bij);
    }
    
    private Object readResolve() {
        return ImplicitBijection$.MODULE$;
    }
    
    private ImplicitBijection$() {
        LowPriorityImplicitBijection$class.$init$(MODULE$ = this);
    }
}
