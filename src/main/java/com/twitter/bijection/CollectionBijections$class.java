// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.reflect.ClassTag;
import scala.collection.immutable.List;
import scala.collection.immutable.List$;
import scala.collection.immutable.Set;
import scala.collection.immutable.Set$;
import scala.collection.immutable.IndexedSeq;
import scala.Predef$;
import scala.collection.immutable.Vector;
import scala.collection.immutable.Vector$;
import scala.collection.immutable.Map;
import scala.runtime.Nothing$;
import scala.Tuple2;
import scala.collection.immutable.Map$;
import scala.collection.generic.CanBuildFrom;

public abstract class CollectionBijections$class
{
    public static Bijection iterable2java(final CollectionBijections $this) {
        return (Bijection)new CollectionBijections$$anon.CollectionBijections$$anon$1($this);
    }
    
    public static Bijection iterator2java(final CollectionBijections $this) {
        return (Bijection)new CollectionBijections$$anon.CollectionBijections$$anon$2($this);
    }
    
    public static Bijection buffer2java(final CollectionBijections $this) {
        return (Bijection)new CollectionBijections$$anon.CollectionBijections$$anon$3($this);
    }
    
    public static Bijection mset2java(final CollectionBijections $this) {
        return (Bijection)new CollectionBijections$$anon.CollectionBijections$$anon$4($this);
    }
    
    public static Bijection mmap2java(final CollectionBijections $this) {
        return (Bijection)new CollectionBijections$$anon.CollectionBijections$$anon$5($this);
    }
    
    public static Bijection iterable2jcollection(final CollectionBijections $this) {
        return (Bijection)new CollectionBijections$$anon.CollectionBijections$$anon$6($this);
    }
    
    public static Bijection iterator2jenumeration(final CollectionBijections $this) {
        return (Bijection)new CollectionBijections$$anon.CollectionBijections$$anon$7($this);
    }
    
    public static Bijection mmap2jdictionary(final CollectionBijections $this) {
        return (Bijection)new CollectionBijections$$anon.CollectionBijections$$anon$8($this);
    }
    
    public static Bijection seq2Java(final CollectionBijections $this) {
        return (Bijection)new CollectionBijections$$anon.CollectionBijections$$anon$9($this);
    }
    
    public static Bijection set2Java(final CollectionBijections $this) {
        return (Bijection)new CollectionBijections$$anon.CollectionBijections$$anon$10($this);
    }
    
    public static Bijection map2Java(final CollectionBijections $this) {
        return (Bijection)new CollectionBijections$$anon.CollectionBijections$$anon$11($this);
    }
    
    public static Bijection seq2List(final CollectionBijections $this) {
        return (Bijection)new CollectionBijections$$anon.CollectionBijections$$anon$12($this);
    }
    
    public static Bijection seq2IndexedSeq(final CollectionBijections $this) {
        return (Bijection)new CollectionBijections$$anon.CollectionBijections$$anon$13($this);
    }
    
    public static Bijection seq2Map(final CollectionBijections $this) {
        return (Bijection)new CollectionBijections$$anon.CollectionBijections$$anon$14($this);
    }
    
    public static Bijection seq2Set(final CollectionBijections $this) {
        return (Bijection)new CollectionBijections$$anon.CollectionBijections$$anon$15($this);
    }
    
    public static Bijection trav2Vector(final CollectionBijections $this) {
        return (Bijection)new CollectionBijections$$anon.CollectionBijections$$anon$16($this);
    }
    
    public static Bijection seq2Vector(final CollectionBijections $this) {
        return $this.trav2Vector();
    }
    
    public static Bijection indexedSeq2Vector(final CollectionBijections $this) {
        return $this.trav2Vector();
    }
    
    public static Bijection toContainer(final CollectionBijections $this, final ImplicitBijection bij, final CanBuildFrom cd, final CanBuildFrom dc) {
        return (Bijection)new CollectionBijections$$anon.CollectionBijections$$anon$17($this, bij, cd, dc);
    }
    
    public static Bijection betweenMaps(final CollectionBijections $this, final ImplicitBijection kBijection, final ImplicitBijection vBijection) {
        return $this.toContainer((ImplicitBijection<Tuple2<Object, Object>, Tuple2<Object, Object>>)ImplicitBijection$.MODULE$.forward((Bijection<A, B>)$this.tuple2((ImplicitBijection<Object, Object>)kBijection, (ImplicitBijection<Object, Object>)vBijection)), (CanBuildFrom<Nothing$, Tuple2<Object, Object>, Map<Object, Object>>)Map$.MODULE$.canBuildFrom(), (CanBuildFrom<Nothing$, Tuple2<Object, Object>, Map<Object, Object>>)Map$.MODULE$.canBuildFrom());
    }
    
    public static Bijection betweenVectors(final CollectionBijections $this, final ImplicitBijection bij) {
        return $this.toContainer(bij, (CanBuildFrom<Nothing$, Object, Vector<Object>>)Vector$.MODULE$.canBuildFrom(), (CanBuildFrom<Nothing$, Object, Vector<Object>>)Vector$.MODULE$.canBuildFrom());
    }
    
    public static Bijection betweenIndexedSeqs(final CollectionBijections $this, final ImplicitBijection bij) {
        return $this.toContainer(bij, (CanBuildFrom<Nothing$, Object, IndexedSeq<Object>>)Predef$.MODULE$.fallbackStringCanBuildFrom(), (CanBuildFrom<Nothing$, Object, IndexedSeq<Object>>)Predef$.MODULE$.fallbackStringCanBuildFrom());
    }
    
    public static Bijection betweenSets(final CollectionBijections $this, final ImplicitBijection bij) {
        return $this.toContainer(bij, (CanBuildFrom<Nothing$, Object, Set<Object>>)Set$.MODULE$.canBuildFrom(), (CanBuildFrom<Nothing$, Object, Set<Object>>)Set$.MODULE$.canBuildFrom());
    }
    
    public static Bijection betweenSeqs(final CollectionBijections $this, final ImplicitBijection bij) {
        return $this.toContainer(bij, (CanBuildFrom<Nothing$, Object, IndexedSeq<Object>>)Predef$.MODULE$.fallbackStringCanBuildFrom(), (CanBuildFrom<Nothing$, Object, IndexedSeq<Object>>)Predef$.MODULE$.fallbackStringCanBuildFrom());
    }
    
    public static Bijection betweenLists(final CollectionBijections $this, final ImplicitBijection bij) {
        return $this.toContainer(bij, (CanBuildFrom<Nothing$, Object, List<Object>>)List$.MODULE$.canBuildFrom(), (CanBuildFrom<Nothing$, Object, List<Object>>)List$.MODULE$.canBuildFrom());
    }
    
    public static Bijection option(final CollectionBijections $this, final ImplicitBijection bij) {
        return (Bijection)new CollectionBijections$$anon.CollectionBijections$$anon$18($this, bij);
    }
    
    public static Bijection vector2List(final CollectionBijections $this, final ImplicitBijection bij) {
        return $this.toContainer(bij, (CanBuildFrom<Nothing$, Object, List<Object>>)List$.MODULE$.canBuildFrom(), (CanBuildFrom<Nothing$, Object, Vector<Object>>)Vector$.MODULE$.canBuildFrom());
    }
    
    public static Bijection indexedSeq2List(final CollectionBijections $this, final ImplicitBijection bij) {
        return $this.toContainer(bij, (CanBuildFrom<Nothing$, Object, List<Object>>)List$.MODULE$.canBuildFrom(), (CanBuildFrom<Nothing$, Object, IndexedSeq<Object>>)Predef$.MODULE$.fallbackStringCanBuildFrom());
    }
    
    public static Bijection array2Traversable(final CollectionBijections $this, final ClassTag evidence$1) {
        return (Bijection)new CollectionBijections$$anon.CollectionBijections$$anon$19($this, evidence$1);
    }
    
    public static Bijection array2Seq(final CollectionBijections $this, final ClassTag evidence$2) {
        return (Bijection)new CollectionBijections$$anon.CollectionBijections$$anon$20($this, evidence$2);
    }
    
    public static void $init$(final CollectionBijections $this) {
    }
}
