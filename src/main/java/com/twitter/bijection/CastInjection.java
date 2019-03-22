// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.reflect.ClassTag;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes = "\u0006\u0001U:Q!\u0001\u0002\t\u0002%\tQbQ1ti&s'.Z2uS>t'BA\u0002\u0005\u0003%\u0011\u0017N[3di&|gN\u0003\u0002\u0006\r\u00059Ao^5ui\u0016\u0014(\"A\u0004\u0002\u0007\r|Wn\u0001\u0001\u0011\u0005)YQ\"\u0001\u0002\u0007\u000b1\u0011\u0001\u0012A\u0007\u0003\u001b\r\u000b7\u000f^%oU\u0016\u001cG/[8o'\tYa\u0002\u0005\u0002\u0010%5\t\u0001CC\u0001\u0012\u0003\u0015\u00198-\u00197b\u0013\t\u0019\u0002C\u0001\u0004B]f\u0014VM\u001a\u0005\u0006+-!\tAF\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003%AQ\u0001G\u0006\u0005\u0002e\t!a\u001c4\u0016\u0007i\u0001#\u0006\u0006\u0002\u001c[A!!\u0002\b\u0010*\u0013\ti\"AA\u0005J]*,7\r^5p]B\u0011q\u0004\t\u0007\u0001\t\u0015\tsC1\u0001#\u0005\u0005\t\u0015CA\u0012'!\tyA%\u0003\u0002&!\t9aj\u001c;iS:<\u0007CA\b(\u0013\tA\u0003CA\u0002B]f\u0004\"a\b\u0016\u0005\u000b-:\"\u0019\u0001\u0017\u0003\u0003\t\u000b\"A\b\u0014\t\u000b9:\u00029A\u0018\u0002\u0007\rlg\rE\u00021gyi\u0011!\r\u0006\u0003eA\tqA]3gY\u0016\u001cG/\u0003\u00025c\tA1\t\\1tgR\u000bw\r")
public final class CastInjection
{
    public static <A, B> Injection<A, B> of(final ClassTag<A> cmf) {
        return CastInjection$.MODULE$.of(cmf);
    }
}
