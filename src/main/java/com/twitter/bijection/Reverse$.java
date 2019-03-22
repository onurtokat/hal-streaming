// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.Some;
import scala.None$;
import scala.Option;
import scala.Serializable;

public final class Reverse$ implements Serializable
{
    public static final Reverse$ MODULE$;
    
    static {
        new Reverse$();
    }
    
    @Override
    public final String toString() {
        return "Reverse";
    }
    
    public <A, B> Reverse<A, B> apply(final Bijection<B, A> inv) {
        return new Reverse<A, B>(inv);
    }
    
    public <A, B> Option<Bijection<B, A>> unapply(final Reverse<A, B> x$0) {
        return (Option<Bijection<B, A>>)((x$0 == null) ? None$.MODULE$ : new Some<Object>(x$0.inv()));
    }
    
    private Object readResolve() {
        return Reverse$.MODULE$;
    }
    
    private Reverse$() {
        MODULE$ = this;
    }
}
