// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.collection.immutable.IndexedSeq;
import scala.collection.TraversableOnce;
import scala.runtime.Nothing$;
import scala.collection.generic.CanBuildFrom;
import scala.Option;
import scala.collection.Iterable;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.StringOps;
import scala.Predef$;
import scala.collection.immutable.List;

public final class StringJoinBijection$
{
    public static final StringJoinBijection$ MODULE$;
    private final String DEFAULT_SEP;
    
    static {
        new StringJoinBijection$();
    }
    
    public String DEFAULT_SEP() {
        return this.DEFAULT_SEP;
    }
    
    public List<String> split(String str, String sep, List<String> acc) {
        while (true) {
            final int index = str.indexOf(sep);
            if (-1 == index) {
                break;
            }
            final int n = index;
            final String substring = str.substring(n + new StringOps(Predef$.MODULE$.augmentString(sep)).size());
            final String s = sep;
            acc = acc.$colon$colon(str.substring(0, n));
            sep = s;
            str = substring;
        }
        return acc.$colon$colon(str).reverse();
    }
    
    public List<String> split$default$3() {
        return (List<String>)Nil$.MODULE$;
    }
    
    public Bijection<Iterable<String>, Option<String>> apply(final String separator) {
        return (Bijection<Iterable<String>, Option<String>>)new StringJoinBijection$$anon.StringJoinBijection$$anon$2(separator);
    }
    
    public String apply$default$1() {
        return this.DEFAULT_SEP();
    }
    
    public <N, B extends TraversableOnce<N>> Bijection<B, String> nonEmptyValues(final String separator, final ImplicitBijection<N, String> bij, final CanBuildFrom<Nothing$, N, B> ab) {
        return Bijection$.MODULE$.toContainer((ImplicitBijection<N, Object>)bij, (CanBuildFrom<Nothing$, Object, IndexedSeq<Object>>)Predef$.MODULE$.fallbackStringCanBuildFrom(), ab).andThen((Bijection<IndexedSeq<Object>, Option<String>>)this.apply(separator)).andThen(Bijection$.MODULE$.filterDefault("").inverse());
    }
    
    public <N, B extends TraversableOnce<N>> String nonEmptyValues$default$1() {
        return this.DEFAULT_SEP();
    }
    
    public <A, B extends TraversableOnce<A>> Bijection<B, Option<String>> viaContainer(final String separator, final Bijection<A, String> bij, final CanBuildFrom<Nothing$, A, B> ab) {
        return Bijection$.MODULE$.toContainer((ImplicitBijection<A, Object>)ImplicitBijection$.MODULE$.forward((Bijection<A, B>)bij), (CanBuildFrom<Nothing$, Object, IndexedSeq<Object>>)Predef$.MODULE$.fallbackStringCanBuildFrom(), ab).andThen((Bijection<IndexedSeq<Object>, Option<String>>)this.apply(separator));
    }
    
    public <A, B extends TraversableOnce<A>> String viaContainer$default$1() {
        return this.DEFAULT_SEP();
    }
    
    private StringJoinBijection$() {
        MODULE$ = this;
        this.DEFAULT_SEP = ":";
    }
}
