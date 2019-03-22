// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.Function0;
import scala.Predef$;
import scala.MatchError;
import scala.util.Success;
import scala.runtime.BoxesRunTime;
import scala.util.Try;
import scala.Tuple2$mcJJ$sp;
import scala.Function1;
import scala.reflect.ScalaSignature;
import scala.Tuple2;

@ScalaSignature(bytes = "\u0006\u0001q2A!\u0001\u0002\u0001\u0013\t\u0019Bj\u001c8h\u001b>$G)\u001b<J]*,7\r^5p]*\u00111\u0001B\u0001\nE&TWm\u0019;j_:T!!\u0002\u0004\u0002\u000fQ<\u0018\u000e\u001e;fe*\tq!A\u0002d_6\u001c\u0001aE\u0002\u0001\u0015A\u0001\"a\u0003\b\u000e\u00031Q\u0011!D\u0001\u0006g\u000e\fG.Y\u0005\u0003\u001f1\u0011a!\u00118z%\u00164\u0007\u0003B\t\u0013)]i\u0011AA\u0005\u0003'\t\u0011\u0011\"\u00138kK\u000e$\u0018n\u001c8\u0011\u0005-)\u0012B\u0001\f\r\u0005\u0011auN\\4\u0011\t-AB\u0003F\u0005\u000331\u0011a\u0001V;qY\u0016\u0014\u0004\u0002C\u000e\u0001\u0005\u000b\u0007I\u0011\u0001\u000f\u0002\u000f5|G-\u001e7vgV\tA\u0003\u0003\u0005\u001f\u0001\t\u0005\t\u0015!\u0003\u0015\u0003!iw\u000eZ;mkN\u0004\u0003\"\u0002\u0011\u0001\t\u0003\t\u0013A\u0002\u001fj]&$h\b\u0006\u0002#GA\u0011\u0011\u0003\u0001\u0005\u00067}\u0001\r\u0001\u0006\u0005\u0006K\u0001!\tEJ\u0001\u0006CB\u0004H.\u001f\u000b\u0003/\u001dBQ\u0001\u000b\u0013A\u0002Q\t\u0011A\u001c\u0005\bU\u0001\u0011\r\u0011\"\u0003\u001d\u0003\u0019i\u0017\r\u001f#jm\"1A\u0006\u0001Q\u0001\nQ\tq!\\1y\t&4\b\u0005C\u0004/\u0001\t\u0007I\u0011\u0002\u000f\u0002\r5Lg\u000eR5w\u0011\u0019\u0001\u0004\u0001)A\u0005)\u00059Q.\u001b8ESZ\u0004\u0003\"\u0002\u001a\u0001\t\u0003\u001a\u0014AB5om\u0016\u0014H\u000f\u0006\u00025uA\u0019Q\u0007\u000f\u000b\u000e\u0003YR!a\u000e\u0007\u0002\tU$\u0018\u000e\\\u0005\u0003sY\u00121\u0001\u0016:z\u0011\u0015Y\u0014\u00071\u0001\u0018\u0003\u0019iw\u000e\u001a3jm\u0002")
public class LongModDivInjection implements Injection<Object, Tuple2<Object, Object>>
{
    private final long modulus;
    private final long maxDiv;
    private final long minDiv;
    
    @Override
    public <C> Injection<Object, C> andThen(final Injection<Tuple2<Object, Object>, C> g) {
        return (Injection<Object, C>)Injection$class.andThen(this, g);
    }
    
    @Override
    public <C> Injection<Object, C> andThen(final Bijection<Tuple2<Object, Object>, C> bij) {
        return (Injection<Object, C>)Injection$class.andThen(this, bij);
    }
    
    @Override
    public <C> Function1<Object, C> andThen(final Function1<Tuple2<Object, Object>, C> g) {
        return (Function1<Object, C>)Injection$class.andThen(this, g);
    }
    
    @Override
    public <T> Injection<T, Tuple2<Object, Object>> compose(final Injection<T, Object> g) {
        return (Injection<T, Tuple2<Object, Object>>)Injection$class.compose(this, g);
    }
    
    @Override
    public <T> Injection<T, Tuple2<Object, Object>> compose(final Bijection<T, Object> bij) {
        return (Injection<T, Tuple2<Object, Object>>)Injection$class.compose(this, bij);
    }
    
    @Override
    public <T> Function1<T, Tuple2<Object, Object>> compose(final Function1<T, Object> g) {
        return (Function1<T, Tuple2<Object, Object>>)Injection$class.compose(this, g);
    }
    
    @Override
    public Function1<Object, Tuple2<Object, Object>> toFunction() {
        return (Function1<Object, Tuple2<Object, Object>>)Injection$class.toFunction(this);
    }
    
    public long modulus() {
        return this.modulus;
    }
    
    public Tuple2<Object, Object> apply(final long n) {
        final long cmod = n % this.modulus();
        final long mod = (cmod < 0L) ? (cmod + this.modulus()) : cmod;
        final long div = n / this.modulus();
        final long toNegInf = (n < 0L && mod != 0L) ? (div - 1L) : div;
        return new Tuple2$mcJJ$sp(mod, toNegInf);
    }
    
    private long maxDiv() {
        return this.maxDiv;
    }
    
    private long minDiv() {
        return this.minDiv;
    }
    
    @Override
    public Try<Object> invert(final Tuple2<Object, Object> moddiv) {
        if (moddiv != null) {
            final long mod = moddiv._1$mcJ$sp();
            final long div = moddiv._2$mcJ$sp();
            final Tuple2$mcJJ$sp tuple2$mcJJ$sp = new Tuple2$mcJJ$sp(mod, div);
            final long mod2 = tuple2$mcJJ$sp._1$mcJ$sp();
            final long div2 = tuple2$mcJJ$sp._2$mcJ$sp();
            final long res = div2 * this.modulus() + mod2;
            return (mod2 >= 0L && mod2 < this.modulus() && div2 <= this.maxDiv() && div2 >= this.minDiv() && res >= 0L == div2 >= 0L) ? new Success<Object>(BoxesRunTime.boxToLong(res)) : InversionFailure$.MODULE$.failedAttempt(moddiv);
        }
        throw new MatchError(moddiv);
    }
    
    public LongModDivInjection(final long modulus) {
        this.modulus = modulus;
        Injection$class.$init$(this);
        Predef$.MODULE$.require(modulus > 0L, (Function0<Object>)new LongModDivInjection$$anonfun.LongModDivInjection$$anonfun$2(this));
        this.maxDiv = Long.MAX_VALUE / modulus;
        this.minDiv = Long.MIN_VALUE / modulus - 1L;
    }
}
