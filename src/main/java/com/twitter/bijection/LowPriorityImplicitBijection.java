// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.reflect.ScalaSignature;
import java.io.Serializable;

@ScalaSignature(bytes = "\u0006\u0001e2q!\u0001\u0002\u0011\u0002\u0007\u0005\u0011B\u0001\u000fM_^\u0004&/[8sSRL\u0018*\u001c9mS\u000eLGOQ5kK\u000e$\u0018n\u001c8\u000b\u0005\r!\u0011!\u00032jU\u0016\u001cG/[8o\u0015\t)a!A\u0004uo&$H/\u001a:\u000b\u0003\u001d\t1aY8n\u0007\u0001\u00192\u0001\u0001\u0006\u0011!\tYa\"D\u0001\r\u0015\u0005i\u0011!B:dC2\f\u0017BA\b\r\u0005\u0019\te.\u001f*fMB\u0011\u0011CF\u0007\u0002%)\u00111\u0003F\u0001\u0003S>T\u0011!F\u0001\u0005U\u00064\u0018-\u0003\u0002\u0018%\ta1+\u001a:jC2L'0\u00192mK\")\u0011\u0004\u0001C\u00015\u00051A%\u001b8ji\u0012\"\u0012a\u0007\t\u0003\u0017qI!!\b\u0007\u0003\tUs\u0017\u000e\u001e\u0005\u0006?\u0001!\u0019\u0001I\u0001\be\u00164XM]:f+\r\t\u0003F\r\u000b\u0003EQ\u0002Ba\t\u0013'c5\t!!\u0003\u0002&\u0005\t\t\u0012*\u001c9mS\u000eLGOQ5kK\u000e$\u0018n\u001c8\u0011\u0005\u001dBC\u0002\u0001\u0003\u0006Sy\u0011\rA\u000b\u0002\u0002\u0003F\u00111F\f\t\u0003\u00171J!!\f\u0007\u0003\u000f9{G\u000f[5oOB\u00111bL\u0005\u0003a1\u00111!\u00118z!\t9#\u0007B\u00034=\t\u0007!FA\u0001C\u0011\u0015)d\u0004q\u00017\u0003\r\u0011\u0017N\u001b\t\u0005G]\nd%\u0003\u00029\u0005\tI!)\u001b6fGRLwN\u001c")
public interface LowPriorityImplicitBijection extends Serializable
{
     <A, B> ImplicitBijection<A, B> reverse(final Bijection<B, A> p0);
}
