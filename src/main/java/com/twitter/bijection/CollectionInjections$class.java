// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.collection.immutable.Vector;
import scala.collection.immutable.Vector$;
import scala.collection.immutable.List;
import scala.collection.immutable.List$;
import scala.runtime.Nothing$;
import scala.collection.generic.CanBuildFrom;
import scala.Tuple2;
import scala.collection.immutable.Map;
import scala.collection.immutable.Set;
import scala.Function2;
import scala.collection.immutable.Map$;
import scala.collection.immutable.Set$;

public abstract class CollectionInjections$class
{
    public static Injection optionInjection(final CollectionInjections $this, final Injection inj) {
        return (Injection)new CollectionInjections$$anon.CollectionInjections$$anon$1($this, inj);
    }
    
    public static Injection option2List(final CollectionInjections $this, final Injection inj) {
        return (Injection)new CollectionInjections$$anon.CollectionInjections$$anon$2($this, inj);
    }
    
    public static Injection map2Set(final CollectionInjections $this, final Injection inj) {
        return $this.toContainer((Function2<Set<Object>, Map<Object, Object>, Object>)new CollectionInjections$$anonfun$map2Set.CollectionInjections$$anonfun$map2Set$1($this), inj, (CanBuildFrom<Nothing$, Object, Set<Object>>)Set$.MODULE$.canBuildFrom(), (CanBuildFrom<Nothing$, Tuple2<Object, Object>, Map<Object, Object>>)Map$.MODULE$.canBuildFrom());
    }
    
    public static Injection set2List(final CollectionInjections $this, final Injection inj) {
        return $this.toContainer((Function2<List<Object>, Set<Object>, Object>)new CollectionInjections$$anonfun$set2List.CollectionInjections$$anonfun$set2List$1($this), inj, (CanBuildFrom<Nothing$, Object, List<Object>>)List$.MODULE$.canBuildFrom(), (CanBuildFrom<Nothing$, Object, Set<Object>>)Set$.MODULE$.canBuildFrom());
    }
    
    public static Injection set2Vector(final CollectionInjections $this, final Injection inj) {
        return $this.toContainer((Function2<Vector<Object>, Set<Object>, Object>)new CollectionInjections$$anonfun$set2Vector.CollectionInjections$$anonfun$set2Vector$1($this), inj, (CanBuildFrom<Nothing$, Object, Vector<Object>>)Vector$.MODULE$.canBuildFrom(), (CanBuildFrom<Nothing$, Object, Set<Object>>)Set$.MODULE$.canBuildFrom());
    }
    
    public static Injection list2List(final CollectionInjections $this, final Injection inj) {
        return $this.toContainer((Function2<List<Object>, List<Object>, Object>)new CollectionInjections$$anonfun$list2List.CollectionInjections$$anonfun$list2List$1($this), inj, (CanBuildFrom<Nothing$, Object, List<Object>>)List$.MODULE$.canBuildFrom(), (CanBuildFrom<Nothing$, Object, List<Object>>)List$.MODULE$.canBuildFrom());
    }
    
    public static Injection set2Set(final CollectionInjections $this, final Injection inj) {
        return $this.toContainer((Function2<Set<Object>, Set<Object>, Object>)new CollectionInjections$$anonfun$set2Set.CollectionInjections$$anonfun$set2Set$1($this), inj, (CanBuildFrom<Nothing$, Object, Set<Object>>)Set$.MODULE$.canBuildFrom(), (CanBuildFrom<Nothing$, Object, Set<Object>>)Set$.MODULE$.canBuildFrom());
    }
    
    public static Injection toContainer(final CollectionInjections $this, final Function2 goodInv, final Injection inj, final CanBuildFrom cd, final CanBuildFrom dc) {
        return (Injection)new CollectionInjections$$anon.CollectionInjections$$anon$3($this, goodInv, inj, cd, dc);
    }
    
    public static void $init$(final CollectionInjections $this) {
    }
}
