// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.Function1;
import scala.util.Try;
import scala.Option;

public final class Conversion$ implements LowPriorityConversion
{
    public static final Conversion$ MODULE$;
    
    static {
        new Conversion$();
    }
    
    @Override
    public <A, B> Object fromBijection(final ImplicitBijection<A, B> fn) {
        return LowPriorityConversion$class.fromBijection(this, fn);
    }
    
    @Override
    public <A, B> Object fromBijectionInv(final ImplicitBijection<B, A> fn) {
        return NotSuperLowPriorityConversion$class.fromBijectionInv(this, fn);
    }
    
    @Override
    public <A, B> Object fromInjection(final Injection<A, B> fn) {
        return SuperLowPriorityConversion$class.fromInjection(this, fn);
    }
    
    @Override
    public <A, B> Conversion<A, Option<B>> fromInjectionOptInverse(final Injection<B, A> inj) {
        return (Conversion<A, Option<B>>)CrazyLowPriorityConversion$class.fromInjectionOptInverse(this, inj);
    }
    
    @Override
    public <A, B> Conversion<A, Try<B>> fromInjectionInverse(final Injection<B, A> inj) {
        return (Conversion<A, Try<B>>)CrazyLowPriorityConversion$class.fromInjectionInverse(this, inj);
    }
    
    public <A> A asMethod(final A a) {
        return a;
    }
    
    public <A, B> Object fromFunction(final Function1<A, B> fn) {
        return new Conversion$$anon.Conversion$$anon$6((Function1)fn);
    }
    
    private Object readResolve() {
        return Conversion$.MODULE$;
    }
    
    private Conversion$() {
        CrazyLowPriorityConversion$class.$init$(MODULE$ = this);
        SuperLowPriorityConversion$class.$init$(this);
        NotSuperLowPriorityConversion$class.$init$(this);
        LowPriorityConversion$class.$init$(this);
    }
}
