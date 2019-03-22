// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.Function1;
import scala.collection.Iterable$;
import scala.collection.immutable.Map;
import scala.collection.Iterable;

public abstract class PivotEncoder$class
{
    public static Map apply(final PivotEncoder $this, final Iterable pairs) {
        return pairs.map((Function1<Object, Object>)new PivotEncoder$$anonfun$apply.PivotEncoder$$anonfun$apply$1($this), Iterable$.MODULE$.canBuildFrom()).groupBy((Function1<Object, Object>)new PivotEncoder$$anonfun$apply.PivotEncoder$$anonfun$apply$2($this)).mapValues((Function1<Object, Object>)new PivotEncoder$$anonfun$apply.PivotEncoder$$anonfun$apply$3($this));
    }
    
    public static Function1 unsplit(final PivotEncoder $this, final Function1 fn) {
        return (Function1)new PivotEncoder$$anonfun$unsplit.PivotEncoder$$anonfun$unsplit$1($this, fn);
    }
    
    public static void $init$(final PivotEncoder $this) {
    }
}
