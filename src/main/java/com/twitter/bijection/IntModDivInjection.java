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
import scala.Tuple2$mcII$sp;
import scala.Function1;
import scala.reflect.ScalaSignature;
import scala.Tuple2;

@ScalaSignature(bytes = "\u0006\u0001q2A!\u0001\u0002\u0001\u0013\t\u0011\u0012J\u001c;N_\u0012$\u0015N^%oU\u0016\u001cG/[8o\u0015\t\u0019A!A\u0005cS*,7\r^5p]*\u0011QAB\u0001\bi^LG\u000f^3s\u0015\u00059\u0011aA2p[\u000e\u00011c\u0001\u0001\u000b!A\u00111BD\u0007\u0002\u0019)\tQ\"A\u0003tG\u0006d\u0017-\u0003\u0002\u0010\u0019\t1\u0011I\\=SK\u001a\u0004B!\u0005\n\u0015/5\t!!\u0003\u0002\u0014\u0005\tI\u0011J\u001c6fGRLwN\u001c\t\u0003\u0017UI!A\u0006\u0007\u0003\u0007%sG\u000f\u0005\u0003\f1Q!\u0012BA\r\r\u0005\u0019!V\u000f\u001d7fe!A1\u0004\u0001BC\u0002\u0013\u0005A$A\u0004n_\u0012,H.^:\u0016\u0003QA\u0001B\b\u0001\u0003\u0002\u0003\u0006I\u0001F\u0001\t[>$W\u000f\\;tA!)\u0001\u0005\u0001C\u0001C\u00051A(\u001b8jiz\"\"AI\u0012\u0011\u0005E\u0001\u0001\"B\u000e \u0001\u0004!\u0002\"B\u0013\u0001\t\u00032\u0013!B1qa2LHCA\f(\u0011\u0015AC\u00051\u0001\u0015\u0003\u0005q\u0007b\u0002\u0016\u0001\u0005\u0004%I\u0001H\u0001\u0007[\u0006DH)\u001b<\t\r1\u0002\u0001\u0015!\u0003\u0015\u0003\u001di\u0017\r\u001f#jm\u0002BqA\f\u0001C\u0002\u0013%A$\u0001\u0004nS:$\u0015N\u001e\u0005\u0007a\u0001\u0001\u000b\u0011\u0002\u000b\u0002\u000f5Lg\u000eR5wA!)!\u0007\u0001C!g\u00051\u0011N\u001c<feR$\"\u0001\u000e\u001e\u0011\u0007UBD#D\u00017\u0015\t9D\"\u0001\u0003vi&d\u0017BA\u001d7\u0005\r!&/\u001f\u0005\u0006wE\u0002\raF\u0001\u0007[>$G-\u001b<")
public class IntModDivInjection implements Injection<Object, Tuple2<Object, Object>>
{
    private final int modulus;
    private final int maxDiv;
    private final int minDiv;
    
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
    
    public int modulus() {
        return this.modulus;
    }
    
    public Tuple2<Object, Object> apply(final int n) {
        final int cmod = n % this.modulus();
        final int mod = (cmod < 0) ? (cmod + this.modulus()) : cmod;
        final int div = n / this.modulus();
        final int toNegInf = (n < 0 && mod != 0) ? (div - 1) : div;
        return new Tuple2$mcII$sp(mod, toNegInf);
    }
    
    private int maxDiv() {
        return this.maxDiv;
    }
    
    private int minDiv() {
        return this.minDiv;
    }
    
    @Override
    public Try<Object> invert(final Tuple2<Object, Object> moddiv) {
        if (moddiv != null) {
            final int mod = moddiv._1$mcI$sp();
            final int div = moddiv._2$mcI$sp();
            final Tuple2$mcII$sp tuple2$mcII$sp = new Tuple2$mcII$sp(mod, div);
            final int mod2 = tuple2$mcII$sp._1$mcI$sp();
            final int div2 = tuple2$mcII$sp._2$mcI$sp();
            final int res = div2 * this.modulus() + mod2;
            return (mod2 >= 0 && mod2 < this.modulus() && div2 <= this.maxDiv() && div2 >= this.minDiv() && res >= 0 == div2 >= 0) ? new Success<Object>(BoxesRunTime.boxToInteger(res)) : InversionFailure$.MODULE$.failedAttempt(moddiv);
        }
        throw new MatchError(moddiv);
    }
    
    public IntModDivInjection(final int modulus) {
        this.modulus = modulus;
        Injection$class.$init$(this);
        Predef$.MODULE$.require(modulus > 0, (Function0<Object>)new IntModDivInjection$$anonfun.IntModDivInjection$$anonfun$1(this));
        this.maxDiv = Integer.MAX_VALUE / modulus;
        this.minDiv = Integer.MIN_VALUE / modulus - 1;
    }
}
