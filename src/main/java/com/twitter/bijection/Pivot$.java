// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.Function1;
import scala.Tuple2;
import java.io.Serializable;

public final class Pivot$ implements Serializable
{
    public static final Pivot$ MODULE$;
    
    static {
        new Pivot$();
    }
    
    public <K, K1, K2> Pivot<K, K1, K2> apply(final Bijection<K, Tuple2<K1, K2>> bijection) {
        return new PivotImpl<K, K1, K2>(bijection);
    }
    
    public <K, K1, K2> Pivot<K, K1, K2> of(final ImplicitBijection<K, Tuple2<K1, K2>> impbij) {
        return new PivotImpl<K, K1, K2>(impbij.bijection());
    }
    
    public <K, V> Pivot<Tuple2<K, V>, V, K> swap() {
        return this.apply((Bijection<Tuple2<K, V>, Tuple2<V, K>>)SwapBijection$.MODULE$.apply());
    }
    
    public <K, K1, K2> PivotEncoder<K, K1, K2> encoder(final Function1<K, Tuple2<K1, K2>> fn) {
        return (PivotEncoder<K, K1, K2>)new Pivot$$anon.Pivot$$anon$1((Function1)fn);
    }
    
    public <K, K1, K2> PivotDecoder<K, K1, K2> decoder(final Function1<Tuple2<K1, K2>, K> fn) {
        return (PivotDecoder<K, K1, K2>)new Pivot$$anon.Pivot$$anon$2((Function1)fn);
    }
    
    private Object readResolve() {
        return Pivot$.MODULE$;
    }
    
    private Pivot$() {
        MODULE$ = this;
    }
}
