// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.util.Success;
import scala.runtime.BoxesRunTime;
import scala.Function0;
import scala.util.Try$;
import scala.util.Try;
import scala.Function1;

public final class Inversion$
{
    public static final Inversion$ MODULE$;
    
    static {
        new Inversion$();
    }
    
    public <A, B> Try<A> attempt(final B b, final Function1<B, A> inv) {
        return Try$.MODULE$.apply((Function0<Object>)new Inversion$$anonfun$attempt.Inversion$$anonfun$attempt$1((Object)b, (Function1)inv)).recoverWith(InversionFailure$.MODULE$.partialFailure(b));
    }
    
    public <A, B> Try<A> attemptWhen(final B b, final Function1<B, Object> test, final Function1<B, A> inv) {
        return BoxesRunTime.unboxToBoolean(test.apply(b)) ? new Success<A>(inv.apply(b)) : InversionFailure$.MODULE$.failedAttempt(b);
    }
    
    private Inversion$() {
        MODULE$ = this;
    }
}
