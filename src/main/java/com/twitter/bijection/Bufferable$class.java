// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.util.Try;
import scala.MatchError;
import scala.util.Failure;
import scala.util.Success;
import scala.Tuple2;
import java.nio.ByteBuffer;

public abstract class Bufferable$class
{
    public static Tuple2 unsafeGet(final Bufferable $this, final ByteBuffer from) {
        boolean b = false;
        Failure failure = null;
        final Try value = $this.get(from);
        if (value instanceof Success) {
            final Tuple2 tup = ((Success<Tuple2>)value).value();
            return tup;
        }
        if (value instanceof Failure) {
            b = true;
            failure = (Failure)value;
            final Throwable exception = failure.exception();
            if (exception instanceof InversionFailure) {
                final Throwable t = ((InversionFailure)exception).ex();
                throw t;
            }
        }
        if (b) {
            final Throwable t2 = failure.exception();
            throw t2;
        }
        throw new MatchError(value);
    }
    
    public static void $init$(final Bufferable $this) {
    }
}
