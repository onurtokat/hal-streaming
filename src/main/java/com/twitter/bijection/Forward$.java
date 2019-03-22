// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.Some;
import scala.None$;
import scala.Option;
import scala.Serializable;

public final class Forward$ implements Serializable
{
    public static final Forward$ MODULE$;
    
    static {
        new Forward$();
    }
    
    @Override
    public final String toString() {
        return "Forward";
    }
    
    public <A, B> Forward<A, B> apply(final Bijection<A, B> bijection) {
        return new Forward<A, B>(bijection);
    }
    
    public <A, B> Option<Bijection<A, B>> unapply(final Forward<A, B> x$0) {
        return (Option<Bijection<A, B>>)((x$0 == null) ? None$.MODULE$ : new Some<Object>(x$0.bijection()));
    }
    
    private Object readResolve() {
        return Forward$.MODULE$;
    }
    
    private Forward$() {
        MODULE$ = this;
    }
}
