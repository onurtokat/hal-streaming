// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.collection.GenTraversableOnce;
import scala.collection.immutable.Iterable$;
import scala.Function1;
import scala.collection.Iterable;
import scala.collection.immutable.Map;

public abstract class PivotDecoder$class
{
    public static Iterable apply(final PivotDecoder $this, final Map m) {
        return m.withFilter((Function1<Object, Object>)new PivotDecoder$$anonfun$apply.PivotDecoder$$anonfun$apply$5($this)).flatMap((Function1<Object, GenTraversableOnce<Object>>)new PivotDecoder$$anonfun$apply.PivotDecoder$$anonfun$apply$6($this), Iterable$.MODULE$.canBuildFrom());
    }
    
    public static Function1 split(final PivotDecoder $this, final Function1 fn) {
        return (Function1)new PivotDecoder$$anonfun$split.PivotDecoder$$anonfun$split$1($this, fn);
    }
    
    public static void $init$(final PivotDecoder $this) {
    }
}
