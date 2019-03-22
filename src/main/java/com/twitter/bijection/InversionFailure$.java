// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.Some;
import scala.None$;
import scala.Tuple2;
import scala.Option;
import scala.PartialFunction;
import scala.util.Failure;
import scala.util.Try;
import scala.Serializable;

public final class InversionFailure$ implements Serializable
{
    public static final InversionFailure$ MODULE$;
    
    static {
        new InversionFailure$();
    }
    
    public <B> InversionFailure apply(final B b) {
        return new InversionFailure(b, new UnsupportedOperationException());
    }
    
    public <A, B> Try<A> failedAttempt(final B b) {
        return new Failure<A>(this.apply(b));
    }
    
    public <A, B> PartialFunction<Throwable, Try<A>> partialFailure(final B b) {
        return (PartialFunction<Throwable, Try<A>>)new InversionFailure$$anonfun$partialFailure.InversionFailure$$anonfun$partialFailure$1((Object)b);
    }
    
    public InversionFailure apply(final Object failed, final Throwable ex) {
        return new InversionFailure(failed, ex);
    }
    
    public Option<Tuple2<Object, Throwable>> unapply(final InversionFailure x$0) {
        return (Option<Tuple2<Object, Throwable>>)((x$0 == null) ? None$.MODULE$ : new Some<Object>(new Tuple2<Object, Throwable>((T1)x$0.failed(), (T2)x$0.ex())));
    }
    
    private Object readResolve() {
        return InversionFailure$.MODULE$;
    }
    
    private InversionFailure$() {
        MODULE$ = this;
    }
}
