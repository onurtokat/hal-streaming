// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.collection.immutable.Map;
import scala.collection.Iterable;
import scala.Function1;
import scala.Tuple2;

public abstract class Pivot$class
{
    public static Pivot andThenPivot(final Pivot $this, final Bijection after) {
        return Pivot$.MODULE$.apply((Bijection<Object, Tuple2<Object, Object>>)$this.pivot().andThen((Bijection<Tuple2<Object, Object>, Tuple2<K1, K2>>)after));
    }
    
    public static Pivot composePivot(final Pivot $this, final Bijection before) {
        return Pivot$.MODULE$.apply((Bijection<Object, Tuple2<Object, Object>>)$this.pivot().compose((Bijection<K, Object>)before));
    }
    
    public static PivotEncoder encoder(final Pivot $this) {
        return Pivot$.MODULE$.encoder((Function1<Object, Tuple2<Object, Object>>)new Pivot$$anonfun$encoder.Pivot$$anonfun$encoder$1($this, (Bijection)$this.pivot()));
    }
    
    public static PivotDecoder decoder(final Pivot $this) {
        return Pivot$.MODULE$.decoder((Function1<Tuple2<Object, Object>, Object>)new Pivot$$anonfun$decoder.Pivot$$anonfun$decoder$1($this, (Bijection)$this.pivot()));
    }
    
    public static Map apply(final Pivot $this, final Iterable pairs) {
        return $this.encoder().apply(pairs);
    }
    
    public static Iterable invert(final Pivot $this, final Map m) {
        return $this.decoder().apply(m);
    }
    
    public static Function1 split(final Pivot $this, final Function1 fn) {
        return $this.decoder().split(fn);
    }
    
    public static Function1 unsplit(final Pivot $this, final Function1 fn) {
        return $this.encoder().unsplit(fn);
    }
    
    public static Pivot wrapOuter(final Pivot $this) {
        return $this.withValue().andThenPivot((Bijection)new Pivot$$anon.Pivot$$anon$3($this));
    }
    
    public static Pivot withValue(final Pivot $this) {
        return Pivot$.MODULE$.apply((Bijection<Object, Tuple2<Object, Object>>)new Pivot$$anon.Pivot$$anon$4($this));
    }
    
    public static void $init$(final Pivot $this) {
    }
}
