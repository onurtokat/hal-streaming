// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.Function1;
import scala.collection.immutable.Map;
import scala.collection.Iterable;
import scala.runtime.BoxedUnit;
import scala.Tuple2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes = "\u0006\u0001Y2A!\u0001\u0002\u0001\u0013\tI\u0001+\u001b<pi&k\u0007\u000f\u001c\u0006\u0003\u0007\u0011\t\u0011BY5kK\u000e$\u0018n\u001c8\u000b\u0005\u00151\u0011a\u0002;xSR$XM\u001d\u0006\u0002\u000f\u0005\u00191m\\7\u0004\u0001U!!bF\u0011%'\r\u00011\"\u0005\t\u0003\u0019=i\u0011!\u0004\u0006\u0002\u001d\u0005)1oY1mC&\u0011\u0001#\u0004\u0002\u0007\u0003:L(+\u001a4\u0011\u000bI\u0019R\u0003I\u0012\u000e\u0003\tI!\u0001\u0006\u0002\u0003\u000bAKgo\u001c;\u0011\u0005Y9B\u0002\u0001\u0003\u00061\u0001\u0011\r!\u0007\u0002\u0002\u0017F\u0011!$\b\t\u0003\u0019mI!\u0001H\u0007\u0003\u000f9{G\u000f[5oOB\u0011ABH\u0005\u0003?5\u00111!\u00118z!\t1\u0012\u0005B\u0003#\u0001\t\u0007\u0011D\u0001\u0002LcA\u0011a\u0003\n\u0003\u0006K\u0001\u0011\r!\u0007\u0002\u0003\u0017JB\u0001b\n\u0001\u0003\u0006\u0004%\t\u0005K\u0001\u0006a&4x\u000e^\u000b\u0002SA!!CK\u000b-\u0013\tY#AA\u0005CS*,7\r^5p]B!A\"\f\u0011$\u0013\tqSB\u0001\u0004UkBdWM\r\u0005\ta\u0001\u0011\t\u0011)A\u0005S\u00051\u0001/\u001b<pi\u0002BQA\r\u0001\u0005\u0002M\na\u0001P5oSRtDC\u0001\u001b6!\u0015\u0011\u0002!\u0006\u0011$\u0011\u00159\u0013\u00071\u0001*\u0001")
public class PivotImpl<K, K1, K2> implements Pivot<K, K1, K2>
{
    private final Bijection<K, Tuple2<K1, K2>> pivot;
    private final PivotEncoder<Object, Object, Object> encoder;
    private final PivotDecoder<Object, Object, Object> decoder;
    private volatile byte bitmap$0;
    
    private PivotEncoder encoder$lzycompute() {
        synchronized (this) {
            if ((byte)(this.bitmap$0 & 0x1) == 0) {
                this.encoder = (PivotEncoder<Object, Object, Object>)Pivot$class.encoder(this);
                this.bitmap$0 |= 0x1;
            }
            final BoxedUnit un\u0131t = BoxedUnit.UNIT;
            return this.encoder;
        }
    }
    
    @Override
    public PivotEncoder<K, K1, K2> encoder() {
        return (PivotEncoder<K, K1, K2>)(((byte)(this.bitmap$0 & 0x1) == 0) ? this.encoder$lzycompute() : this.encoder);
    }
    
    private PivotDecoder decoder$lzycompute() {
        synchronized (this) {
            if ((byte)(this.bitmap$0 & 0x2) == 0) {
                this.decoder = (PivotDecoder<Object, Object, Object>)Pivot$class.decoder(this);
                this.bitmap$0 |= 0x2;
            }
            final BoxedUnit un\u0131t = BoxedUnit.UNIT;
            return this.decoder;
        }
    }
    
    @Override
    public PivotDecoder<K, K1, K2> decoder() {
        return (PivotDecoder<K, K1, K2>)(((byte)(this.bitmap$0 & 0x2) == 0) ? this.decoder$lzycompute() : this.decoder);
    }
    
    @Override
    public <K3, K4> Pivot<K, K3, K4> andThenPivot(final Bijection<Tuple2<K1, K2>, Tuple2<K3, K4>> after) {
        return (Pivot<K, K3, K4>)Pivot$class.andThenPivot(this, after);
    }
    
    @Override
    public <T> Pivot<T, K1, K2> composePivot(final Bijection<T, K> before) {
        return (Pivot<T, K1, K2>)Pivot$class.composePivot(this, before);
    }
    
    @Override
    public Map<K1, Iterable<K2>> apply(final Iterable<K> pairs) {
        return (Map<K1, Iterable<K2>>)Pivot$class.apply(this, pairs);
    }
    
    @Override
    public Iterable<K> invert(final Map<K1, Iterable<K2>> m) {
        return (Iterable<K>)Pivot$class.invert(this, m);
    }
    
    @Override
    public <V> Function1<K1, Function1<K2, V>> split(final Function1<K, V> fn) {
        return (Function1<K1, Function1<K2, V>>)Pivot$class.split(this, fn);
    }
    
    @Override
    public <V> Function1<K, V> unsplit(final Function1<K1, Function1<K2, V>> fn) {
        return (Function1<K, V>)Pivot$class.unsplit(this, fn);
    }
    
    @Override
    public <T> Pivot<Tuple2<K, T>, Tuple2<K1, T>, K2> wrapOuter() {
        return (Pivot<Tuple2<K, T>, Tuple2<K1, T>, K2>)Pivot$class.wrapOuter(this);
    }
    
    @Override
    public <V> Pivot<Tuple2<K, V>, K1, Tuple2<K2, V>> withValue() {
        return (Pivot<Tuple2<K, V>, K1, Tuple2<K2, V>>)Pivot$class.withValue(this);
    }
    
    @Override
    public Bijection<Map<K1, Iterable<K2>>, Iterable<K>> inverse() {
        return (Bijection<Map<K1, Iterable<K2>>, Iterable<K>>)Bijection$class.inverse(this);
    }
    
    @Override
    public <C> Bijection<Iterable<K>, C> andThen(final Bijection<Map<K1, Iterable<K2>>, C> g) {
        return (Bijection<Iterable<K>, C>)Bijection$class.andThen(this, g);
    }
    
    @Override
    public <C> Injection<Iterable<K>, C> andThen(final Injection<Map<K1, Iterable<K2>>, C> g) {
        return (Injection<Iterable<K>, C>)Bijection$class.andThen(this, g);
    }
    
    @Override
    public <C> Function1<Iterable<K>, C> andThen(final Function1<Map<K1, Iterable<K2>>, C> g) {
        return (Function1<Iterable<K>, C>)Bijection$class.andThen(this, g);
    }
    
    @Override
    public <T> Bijection<T, Map<K1, Iterable<K2>>> compose(final Bijection<T, Iterable<K>> g) {
        return (Bijection<T, Map<K1, Iterable<K2>>>)Bijection$class.compose(this, g);
    }
    
    @Override
    public <T> Injection<T, Map<K1, Iterable<K2>>> compose(final Injection<T, Iterable<K>> g) {
        return (Injection<T, Map<K1, Iterable<K2>>>)Bijection$class.compose(this, g);
    }
    
    @Override
    public <T> Function1<T, Map<K1, Iterable<K2>>> compose(final Function1<T, Iterable<K>> g) {
        return (Function1<T, Map<K1, Iterable<K2>>>)Bijection$class.compose(this, g);
    }
    
    @Override
    public Function1<Iterable<K>, Map<K1, Iterable<K2>>> toFunction() {
        return (Function1<Iterable<K>, Map<K1, Iterable<K2>>>)Bijection$class.toFunction(this);
    }
    
    @Override
    public Bijection<K, Tuple2<K1, K2>> pivot() {
        return this.pivot;
    }
    
    public PivotImpl(final Bijection<K, Tuple2<K1, K2>> pivot) {
        this.pivot = pivot;
        Bijection$class.$init$(this);
        Pivot$class.$init$(this);
    }
}
