// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.Tuple2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes = "\u0006\u0001A:Q!\u0001\u0002\t\u0002%\tQbU<ba\nK'.Z2uS>t'BA\u0002\u0005\u0003%\u0011\u0017N[3di&|gN\u0003\u0002\u0006\r\u00059Ao^5ui\u0016\u0014(\"A\u0004\u0002\u0007\r|Wn\u0001\u0001\u0011\u0005)YQ\"\u0001\u0002\u0007\u000b1\u0011\u0001\u0012A\u0007\u0003\u001bM;\u0018\r\u001d\"jU\u0016\u001cG/[8o'\tYa\u0002\u0005\u0002\u0010%5\t\u0001CC\u0001\u0012\u0003\u0015\u00198-\u00197b\u0013\t\u0019\u0002C\u0001\u0004B]f\u0014VM\u001a\u0005\u0006+-!\tAF\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003%AQ\u0001G\u0006\u0005\u0002e\tQ!\u00199qYf,2AG\u0012.+\u0005Y\u0002\u0003\u0002\u0006\u001d==J!!\b\u0002\u0003#\u0005\u00137\u000f\u001e:bGR\u0014\u0015N[3di&|g\u000e\u0005\u0003\u0010?\u0005b\u0013B\u0001\u0011\u0011\u0005\u0019!V\u000f\u001d7feA\u0011!e\t\u0007\u0001\t\u0015!sC1\u0001&\u0005\u0005!\u0016C\u0001\u0014*!\tyq%\u0003\u0002)!\t9aj\u001c;iS:<\u0007CA\b+\u0013\tY\u0003CA\u0002B]f\u0004\"AI\u0017\u0005\u000b9:\"\u0019A\u0013\u0003\u0003U\u0003BaD\u0010-C\u0001")
public final class SwapBijection
{
    public static <T, U> AbstractBijection<Tuple2<T, U>, Tuple2<U, T>> apply() {
        return SwapBijection$.MODULE$.apply();
    }
}
