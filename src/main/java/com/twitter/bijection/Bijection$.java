// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.Function2;
import scala.Function1;
import scala.Tuple22;
import scala.Tuple21;
import scala.Tuple20;
import scala.Tuple19;
import scala.Tuple18;
import scala.Tuple17;
import scala.Tuple16;
import scala.Tuple15;
import scala.Tuple14;
import scala.Tuple13;
import scala.Tuple12;
import scala.Tuple11;
import scala.Tuple10;
import scala.Tuple9;
import scala.Tuple8;
import scala.Tuple7;
import scala.Tuple6;
import scala.Tuple5;
import scala.Tuple4;
import scala.Tuple3;
import scala.runtime.BoxedUnit;
import scala.reflect.ClassTag;
import scala.Option;
import scala.collection.TraversableOnce;
import scala.runtime.Nothing$;
import scala.collection.generic.CanBuildFrom;
import scala.collection.immutable.Vector;
import scala.collection.Traversable;
import scala.collection.IndexedSeq;
import scala.collection.Seq;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Collection;
import scala.collection.mutable.Map;
import scala.collection.mutable.Set;
import java.util.List;
import scala.collection.mutable.Buffer;
import scala.collection.Iterator;
import scala.collection.Iterable;
import java.util.Date;
import java.util.UUID;
import java.math.BigInteger;
import scala.math.BigInt;
import scala.Tuple2;
import scala.Symbol;
import java.nio.ByteBuffer;
import java.io.Serializable;

public final class Bijection$ implements CollectionBijections, Serializable
{
    public static final Bijection$ MODULE$;
    private final Bijection<byte[], ByteBuffer> bytes2Buffer;
    private final Bijection<byte[], byte[]> bytes2GzippedBytes;
    private final Bijection<byte[], String> bytes2Base64;
    private final Bijection<byte[], String> bytes2GZippedBase64;
    private final Bijection<Symbol, String> symbol2String;
    private final Bijection<Object, Byte> byte2Boxed;
    private final Bijection<Object, Short> short2Boxed;
    private final Bijection<Object, Tuple2<Object, Object>> short2ByteByte;
    private final Bijection<Object, Integer> int2Boxed;
    private final Bijection<Object, Tuple2<Object, Object>> int2ShortShort;
    private final Bijection<Object, Long> long2Boxed;
    private final Bijection<Object, Tuple2<Object, Object>> long2IntInt;
    private final Bijection<Object, Float> float2Boxed;
    private final Bijection<Object, Double> double2Boxed;
    private final Bijection<Object, Object> float2IntIEEE754;
    private final Bijection<Object, Object> double2LongIEEE754;
    private final Bijection<BigInt, BigInteger> bigInt2BigInteger;
    private final Bijection<UUID, Tuple2<Object, Object>> uid2LongLong;
    private final Bijection<Date, Object> date2Long;
    private volatile byte bitmap$0;
    
    static {
        new Bijection$();
    }
    
    @Override
    public <T> Bijection<Iterable<T>, java.lang.Iterable<T>> iterable2java() {
        return (Bijection<Iterable<T>, java.lang.Iterable<T>>)CollectionBijections$class.iterable2java(this);
    }
    
    @Override
    public <T> Bijection<Iterator<T>, java.util.Iterator<T>> iterator2java() {
        return (Bijection<Iterator<T>, java.util.Iterator<T>>)CollectionBijections$class.iterator2java(this);
    }
    
    @Override
    public <T> Bijection<Buffer<T>, List<T>> buffer2java() {
        return (Bijection<Buffer<T>, List<T>>)CollectionBijections$class.buffer2java(this);
    }
    
    @Override
    public <T> Bijection<Set<T>, java.util.Set<T>> mset2java() {
        return (Bijection<Set<T>, java.util.Set<T>>)CollectionBijections$class.mset2java(this);
    }
    
    @Override
    public <K, V> Bijection<Map<K, V>, java.util.Map<K, V>> mmap2java() {
        return (Bijection<Map<K, V>, java.util.Map<K, V>>)CollectionBijections$class.mmap2java(this);
    }
    
    @Override
    public <T> Bijection<Iterable<T>, Collection<T>> iterable2jcollection() {
        return (Bijection<Iterable<T>, Collection<T>>)CollectionBijections$class.iterable2jcollection(this);
    }
    
    @Override
    public <T> Bijection<Iterator<T>, Enumeration<T>> iterator2jenumeration() {
        return (Bijection<Iterator<T>, Enumeration<T>>)CollectionBijections$class.iterator2jenumeration(this);
    }
    
    @Override
    public <K, V> Bijection<Map<K, V>, Dictionary<K, V>> mmap2jdictionary() {
        return (Bijection<Map<K, V>, Dictionary<K, V>>)CollectionBijections$class.mmap2jdictionary(this);
    }
    
    @Override
    public <T> Bijection<Seq<T>, List<T>> seq2Java() {
        return (Bijection<Seq<T>, List<T>>)CollectionBijections$class.seq2Java(this);
    }
    
    @Override
    public <T> Bijection<scala.collection.immutable.Set<T>, java.util.Set<T>> set2Java() {
        return (Bijection<scala.collection.immutable.Set<T>, java.util.Set<T>>)CollectionBijections$class.set2Java(this);
    }
    
    @Override
    public <K, V> Bijection<scala.collection.immutable.Map<K, V>, java.util.Map<K, V>> map2Java() {
        return (Bijection<scala.collection.immutable.Map<K, V>, java.util.Map<K, V>>)CollectionBijections$class.map2Java(this);
    }
    
    @Override
    public <A> Bijection<Seq<A>, scala.collection.immutable.List<A>> seq2List() {
        return (Bijection<Seq<A>, scala.collection.immutable.List<A>>)CollectionBijections$class.seq2List(this);
    }
    
    @Override
    public <A> Bijection<Seq<A>, IndexedSeq<A>> seq2IndexedSeq() {
        return (Bijection<Seq<A>, IndexedSeq<A>>)CollectionBijections$class.seq2IndexedSeq(this);
    }
    
    @Override
    public <K, V> Bijection<Seq<Tuple2<K, V>>, scala.collection.immutable.Map<K, V>> seq2Map() {
        return (Bijection<Seq<Tuple2<K, V>>, scala.collection.immutable.Map<K, V>>)CollectionBijections$class.seq2Map(this);
    }
    
    @Override
    public <T> Bijection<Seq<T>, scala.collection.immutable.Set<T>> seq2Set() {
        return (Bijection<Seq<T>, scala.collection.immutable.Set<T>>)CollectionBijections$class.seq2Set(this);
    }
    
    @Override
    public <T, C extends Traversable<T>> Bijection<C, Vector<T>> trav2Vector() {
        return (Bijection<C, Vector<T>>)CollectionBijections$class.trav2Vector(this);
    }
    
    @Override
    public <T> Bijection<Seq<T>, Vector<T>> seq2Vector() {
        return (Bijection<Seq<T>, Vector<T>>)CollectionBijections$class.seq2Vector(this);
    }
    
    @Override
    public <T> Bijection<IndexedSeq<T>, Vector<T>> indexedSeq2Vector() {
        return (Bijection<IndexedSeq<T>, Vector<T>>)CollectionBijections$class.indexedSeq2Vector(this);
    }
    
    @Override
    public <A, B, C extends TraversableOnce<A>, D extends TraversableOnce<B>> Bijection<C, D> toContainer(final ImplicitBijection<A, B> bij, final CanBuildFrom<Nothing$, B, D> cd, final CanBuildFrom<Nothing$, A, C> dc) {
        return (Bijection<C, D>)CollectionBijections$class.toContainer(this, bij, cd, dc);
    }
    
    @Override
    public <K1, V1, K2, V2> Bijection<scala.collection.immutable.Map<K1, V1>, scala.collection.immutable.Map<K2, V2>> betweenMaps(final ImplicitBijection<K1, K2> kBijection, final ImplicitBijection<V1, V2> vBijection) {
        return (Bijection<scala.collection.immutable.Map<K1, V1>, scala.collection.immutable.Map<K2, V2>>)CollectionBijections$class.betweenMaps(this, kBijection, vBijection);
    }
    
    @Override
    public <T, U> Bijection<Vector<T>, Vector<U>> betweenVectors(final ImplicitBijection<T, U> bij) {
        return (Bijection<Vector<T>, Vector<U>>)CollectionBijections$class.betweenVectors(this, bij);
    }
    
    @Override
    public <T, U> Bijection<IndexedSeq<T>, IndexedSeq<U>> betweenIndexedSeqs(final ImplicitBijection<T, U> bij) {
        return (Bijection<IndexedSeq<T>, IndexedSeq<U>>)CollectionBijections$class.betweenIndexedSeqs(this, bij);
    }
    
    @Override
    public <T, U> Bijection<scala.collection.immutable.Set<T>, scala.collection.immutable.Set<U>> betweenSets(final ImplicitBijection<T, U> bij) {
        return (Bijection<scala.collection.immutable.Set<T>, scala.collection.immutable.Set<U>>)CollectionBijections$class.betweenSets(this, bij);
    }
    
    @Override
    public <T, U> Bijection<Seq<T>, Seq<U>> betweenSeqs(final ImplicitBijection<T, U> bij) {
        return (Bijection<Seq<T>, Seq<U>>)CollectionBijections$class.betweenSeqs(this, bij);
    }
    
    @Override
    public <T, U> Bijection<scala.collection.immutable.List<T>, scala.collection.immutable.List<U>> betweenLists(final ImplicitBijection<T, U> bij) {
        return (Bijection<scala.collection.immutable.List<T>, scala.collection.immutable.List<U>>)CollectionBijections$class.betweenLists(this, bij);
    }
    
    @Override
    public <T, U> Bijection<Option<T>, Option<U>> option(final ImplicitBijection<T, U> bij) {
        return (Bijection<Option<T>, Option<U>>)CollectionBijections$class.option(this, bij);
    }
    
    @Override
    public <A, B> Bijection<Vector<A>, scala.collection.immutable.List<B>> vector2List(final ImplicitBijection<A, B> bij) {
        return (Bijection<Vector<A>, scala.collection.immutable.List<B>>)CollectionBijections$class.vector2List(this, bij);
    }
    
    @Override
    public <A, B> Bijection<IndexedSeq<A>, scala.collection.immutable.List<B>> indexedSeq2List(final ImplicitBijection<A, B> bij) {
        return (Bijection<IndexedSeq<A>, scala.collection.immutable.List<B>>)CollectionBijections$class.indexedSeq2List(this, bij);
    }
    
    @Override
    public <T> Bijection<Object, Traversable<T>> array2Traversable(final ClassTag<T> evidence$1) {
        return (Bijection<Object, Traversable<T>>)CollectionBijections$class.array2Traversable(this, evidence$1);
    }
    
    @Override
    public <T> Bijection<Object, Seq<T>> array2Seq(final ClassTag<T> evidence$2) {
        return (Bijection<Object, Seq<T>>)CollectionBijections$class.array2Seq(this, evidence$2);
    }
    
    @Override
    public Bijection<byte[], ByteBuffer> bytes2Buffer() {
        return this.bytes2Buffer;
    }
    
    private Bijection bytes2GzippedBytes$lzycompute() {
        synchronized (this) {
            if ((byte)(this.bitmap$0 & 0x1) == 0) {
                this.bytes2GzippedBytes = (Bijection<byte[], byte[]>)BinaryBijections$class.bytes2GzippedBytes(this);
                this.bitmap$0 |= 0x1;
            }
            final BoxedUnit un\u0131t = BoxedUnit.UNIT;
            return this.bytes2GzippedBytes;
        }
    }
    
    @Override
    public Bijection<byte[], byte[]> bytes2GzippedBytes() {
        return ((byte)(this.bitmap$0 & 0x1) == 0) ? this.bytes2GzippedBytes$lzycompute() : this.bytes2GzippedBytes;
    }
    
    private Bijection bytes2Base64$lzycompute() {
        synchronized (this) {
            if ((byte)(this.bitmap$0 & 0x2) == 0) {
                this.bytes2Base64 = (Bijection<byte[], String>)BinaryBijections$class.bytes2Base64(this);
                this.bitmap$0 |= 0x2;
            }
            final BoxedUnit un\u0131t = BoxedUnit.UNIT;
            return this.bytes2Base64;
        }
    }
    
    @Override
    public Bijection<byte[], String> bytes2Base64() {
        return ((byte)(this.bitmap$0 & 0x2) == 0) ? this.bytes2Base64$lzycompute() : this.bytes2Base64;
    }
    
    @Override
    public Bijection<byte[], String> bytes2GZippedBase64() {
        return this.bytes2GZippedBase64;
    }
    
    @Override
    public void com$twitter$bijection$BinaryBijections$_setter_$bytes2Buffer_$eq(final Bijection x$1) {
        this.bytes2Buffer = (Bijection<byte[], ByteBuffer>)x$1;
    }
    
    @Override
    public void com$twitter$bijection$BinaryBijections$_setter_$bytes2GZippedBase64_$eq(final Bijection x$1) {
        this.bytes2GZippedBase64 = (Bijection<byte[], String>)x$1;
    }
    
    @Override
    public Bijection<Symbol, String> symbol2String() {
        return this.symbol2String;
    }
    
    @Override
    public void com$twitter$bijection$StringBijections$_setter_$symbol2String_$eq(final Bijection x$1) {
        this.symbol2String = (Bijection<Symbol, String>)x$1;
    }
    
    @Override
    public Bijection<Object, Byte> byte2Boxed() {
        return this.byte2Boxed;
    }
    
    @Override
    public Bijection<Object, Short> short2Boxed() {
        return this.short2Boxed;
    }
    
    @Override
    public Bijection<Object, Tuple2<Object, Object>> short2ByteByte() {
        return this.short2ByteByte;
    }
    
    @Override
    public Bijection<Object, Integer> int2Boxed() {
        return this.int2Boxed;
    }
    
    @Override
    public Bijection<Object, Tuple2<Object, Object>> int2ShortShort() {
        return this.int2ShortShort;
    }
    
    @Override
    public Bijection<Object, Long> long2Boxed() {
        return this.long2Boxed;
    }
    
    @Override
    public Bijection<Object, Tuple2<Object, Object>> long2IntInt() {
        return this.long2IntInt;
    }
    
    @Override
    public Bijection<Object, Float> float2Boxed() {
        return this.float2Boxed;
    }
    
    @Override
    public Bijection<Object, Double> double2Boxed() {
        return this.double2Boxed;
    }
    
    @Override
    public Bijection<Object, Object> float2IntIEEE754() {
        return this.float2IntIEEE754;
    }
    
    @Override
    public Bijection<Object, Object> double2LongIEEE754() {
        return this.double2LongIEEE754;
    }
    
    @Override
    public Bijection<BigInt, BigInteger> bigInt2BigInteger() {
        return this.bigInt2BigInteger;
    }
    
    @Override
    public Bijection<UUID, Tuple2<Object, Object>> uid2LongLong() {
        return this.uid2LongLong;
    }
    
    @Override
    public Bijection<Date, Object> date2Long() {
        return this.date2Long;
    }
    
    @Override
    public void com$twitter$bijection$NumericBijections$_setter_$byte2Boxed_$eq(final Bijection x$1) {
        this.byte2Boxed = (Bijection<Object, Byte>)x$1;
    }
    
    @Override
    public void com$twitter$bijection$NumericBijections$_setter_$short2Boxed_$eq(final Bijection x$1) {
        this.short2Boxed = (Bijection<Object, Short>)x$1;
    }
    
    @Override
    public void com$twitter$bijection$NumericBijections$_setter_$short2ByteByte_$eq(final Bijection x$1) {
        this.short2ByteByte = (Bijection<Object, Tuple2<Object, Object>>)x$1;
    }
    
    @Override
    public void com$twitter$bijection$NumericBijections$_setter_$int2Boxed_$eq(final Bijection x$1) {
        this.int2Boxed = (Bijection<Object, Integer>)x$1;
    }
    
    @Override
    public void com$twitter$bijection$NumericBijections$_setter_$int2ShortShort_$eq(final Bijection x$1) {
        this.int2ShortShort = (Bijection<Object, Tuple2<Object, Object>>)x$1;
    }
    
    @Override
    public void com$twitter$bijection$NumericBijections$_setter_$long2Boxed_$eq(final Bijection x$1) {
        this.long2Boxed = (Bijection<Object, Long>)x$1;
    }
    
    @Override
    public void com$twitter$bijection$NumericBijections$_setter_$long2IntInt_$eq(final Bijection x$1) {
        this.long2IntInt = (Bijection<Object, Tuple2<Object, Object>>)x$1;
    }
    
    @Override
    public void com$twitter$bijection$NumericBijections$_setter_$float2Boxed_$eq(final Bijection x$1) {
        this.float2Boxed = (Bijection<Object, Float>)x$1;
    }
    
    @Override
    public void com$twitter$bijection$NumericBijections$_setter_$double2Boxed_$eq(final Bijection x$1) {
        this.double2Boxed = (Bijection<Object, Double>)x$1;
    }
    
    @Override
    public void com$twitter$bijection$NumericBijections$_setter_$float2IntIEEE754_$eq(final Bijection x$1) {
        this.float2IntIEEE754 = (Bijection<Object, Object>)x$1;
    }
    
    @Override
    public void com$twitter$bijection$NumericBijections$_setter_$double2LongIEEE754_$eq(final Bijection x$1) {
        this.double2LongIEEE754 = (Bijection<Object, Object>)x$1;
    }
    
    @Override
    public void com$twitter$bijection$NumericBijections$_setter_$bigInt2BigInteger_$eq(final Bijection x$1) {
        this.bigInt2BigInteger = (Bijection<BigInt, BigInteger>)x$1;
    }
    
    @Override
    public void com$twitter$bijection$NumericBijections$_setter_$uid2LongLong_$eq(final Bijection x$1) {
        this.uid2LongLong = (Bijection<UUID, Tuple2<Object, Object>>)x$1;
    }
    
    @Override
    public void com$twitter$bijection$NumericBijections$_setter_$date2Long_$eq(final Bijection x$1) {
        this.date2Long = (Bijection<Date, Object>)x$1;
    }
    
    @Override
    public <A1, B1, A2, B2> Bijection<Tuple2<A1, B1>, Tuple2<A2, B2>> tuple2(final ImplicitBijection<A1, A2> ba, final ImplicitBijection<B1, B2> bb) {
        return (Bijection<Tuple2<A1, B1>, Tuple2<A2, B2>>)GeneratedTupleBijections$class.tuple2(this, ba, bb);
    }
    
    @Override
    public <A1, B1, C1, A2, B2, C2> Bijection<Tuple3<A1, B1, C1>, Tuple3<A2, B2, C2>> tuple3(final ImplicitBijection<A1, A2> ba, final ImplicitBijection<B1, B2> bb, final ImplicitBijection<C1, C2> bc) {
        return (Bijection<Tuple3<A1, B1, C1>, Tuple3<A2, B2, C2>>)GeneratedTupleBijections$class.tuple3(this, ba, bb, bc);
    }
    
    @Override
    public <A1, B1, C1, D1, A2, B2, C2, D2> Bijection<Tuple4<A1, B1, C1, D1>, Tuple4<A2, B2, C2, D2>> tuple4(final ImplicitBijection<A1, A2> ba, final ImplicitBijection<B1, B2> bb, final ImplicitBijection<C1, C2> bc, final ImplicitBijection<D1, D2> bd) {
        return (Bijection<Tuple4<A1, B1, C1, D1>, Tuple4<A2, B2, C2, D2>>)GeneratedTupleBijections$class.tuple4(this, ba, bb, bc, bd);
    }
    
    @Override
    public <A1, B1, C1, D1, E1, A2, B2, C2, D2, E2> Bijection<Tuple5<A1, B1, C1, D1, E1>, Tuple5<A2, B2, C2, D2, E2>> tuple5(final ImplicitBijection<A1, A2> ba, final ImplicitBijection<B1, B2> bb, final ImplicitBijection<C1, C2> bc, final ImplicitBijection<D1, D2> bd, final ImplicitBijection<E1, E2> be) {
        return (Bijection<Tuple5<A1, B1, C1, D1, E1>, Tuple5<A2, B2, C2, D2, E2>>)GeneratedTupleBijections$class.tuple5(this, ba, bb, bc, bd, be);
    }
    
    @Override
    public <A1, B1, C1, D1, E1, F1, A2, B2, C2, D2, E2, F2> Bijection<Tuple6<A1, B1, C1, D1, E1, F1>, Tuple6<A2, B2, C2, D2, E2, F2>> tuple6(final ImplicitBijection<A1, A2> ba, final ImplicitBijection<B1, B2> bb, final ImplicitBijection<C1, C2> bc, final ImplicitBijection<D1, D2> bd, final ImplicitBijection<E1, E2> be, final ImplicitBijection<F1, F2> bf) {
        return (Bijection<Tuple6<A1, B1, C1, D1, E1, F1>, Tuple6<A2, B2, C2, D2, E2, F2>>)GeneratedTupleBijections$class.tuple6(this, ba, bb, bc, bd, be, bf);
    }
    
    @Override
    public <A1, B1, C1, D1, E1, F1, G1, A2, B2, C2, D2, E2, F2, G2> Bijection<Tuple7<A1, B1, C1, D1, E1, F1, G1>, Tuple7<A2, B2, C2, D2, E2, F2, G2>> tuple7(final ImplicitBijection<A1, A2> ba, final ImplicitBijection<B1, B2> bb, final ImplicitBijection<C1, C2> bc, final ImplicitBijection<D1, D2> bd, final ImplicitBijection<E1, E2> be, final ImplicitBijection<F1, F2> bf, final ImplicitBijection<G1, G2> bg) {
        return (Bijection<Tuple7<A1, B1, C1, D1, E1, F1, G1>, Tuple7<A2, B2, C2, D2, E2, F2, G2>>)GeneratedTupleBijections$class.tuple7(this, ba, bb, bc, bd, be, bf, bg);
    }
    
    @Override
    public <A1, B1, C1, D1, E1, F1, G1, H1, A2, B2, C2, D2, E2, F2, G2, H2> Bijection<Tuple8<A1, B1, C1, D1, E1, F1, G1, H1>, Tuple8<A2, B2, C2, D2, E2, F2, G2, H2>> tuple8(final ImplicitBijection<A1, A2> ba, final ImplicitBijection<B1, B2> bb, final ImplicitBijection<C1, C2> bc, final ImplicitBijection<D1, D2> bd, final ImplicitBijection<E1, E2> be, final ImplicitBijection<F1, F2> bf, final ImplicitBijection<G1, G2> bg, final ImplicitBijection<H1, H2> bh) {
        return (Bijection<Tuple8<A1, B1, C1, D1, E1, F1, G1, H1>, Tuple8<A2, B2, C2, D2, E2, F2, G2, H2>>)GeneratedTupleBijections$class.tuple8(this, ba, bb, bc, bd, be, bf, bg, bh);
    }
    
    @Override
    public <A1, B1, C1, D1, E1, F1, G1, H1, I1, A2, B2, C2, D2, E2, F2, G2, H2, I2> Bijection<Tuple9<A1, B1, C1, D1, E1, F1, G1, H1, I1>, Tuple9<A2, B2, C2, D2, E2, F2, G2, H2, I2>> tuple9(final ImplicitBijection<A1, A2> ba, final ImplicitBijection<B1, B2> bb, final ImplicitBijection<C1, C2> bc, final ImplicitBijection<D1, D2> bd, final ImplicitBijection<E1, E2> be, final ImplicitBijection<F1, F2> bf, final ImplicitBijection<G1, G2> bg, final ImplicitBijection<H1, H2> bh, final ImplicitBijection<I1, I2> bi) {
        return (Bijection<Tuple9<A1, B1, C1, D1, E1, F1, G1, H1, I1>, Tuple9<A2, B2, C2, D2, E2, F2, G2, H2, I2>>)GeneratedTupleBijections$class.tuple9(this, ba, bb, bc, bd, be, bf, bg, bh, bi);
    }
    
    @Override
    public <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2> Bijection<Tuple10<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1>, Tuple10<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2>> tuple10(final ImplicitBijection<A1, A2> ba, final ImplicitBijection<B1, B2> bb, final ImplicitBijection<C1, C2> bc, final ImplicitBijection<D1, D2> bd, final ImplicitBijection<E1, E2> be, final ImplicitBijection<F1, F2> bf, final ImplicitBijection<G1, G2> bg, final ImplicitBijection<H1, H2> bh, final ImplicitBijection<I1, I2> bi, final ImplicitBijection<J1, J2> bj) {
        return (Bijection<Tuple10<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1>, Tuple10<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2>>)GeneratedTupleBijections$class.tuple10(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj);
    }
    
    @Override
    public <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2> Bijection<Tuple11<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1>, Tuple11<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2>> tuple11(final ImplicitBijection<A1, A2> ba, final ImplicitBijection<B1, B2> bb, final ImplicitBijection<C1, C2> bc, final ImplicitBijection<D1, D2> bd, final ImplicitBijection<E1, E2> be, final ImplicitBijection<F1, F2> bf, final ImplicitBijection<G1, G2> bg, final ImplicitBijection<H1, H2> bh, final ImplicitBijection<I1, I2> bi, final ImplicitBijection<J1, J2> bj, final ImplicitBijection<K1, K2> bk) {
        return (Bijection<Tuple11<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1>, Tuple11<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2>>)GeneratedTupleBijections$class.tuple11(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk);
    }
    
    @Override
    public <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2> Bijection<Tuple12<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1>, Tuple12<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2>> tuple12(final ImplicitBijection<A1, A2> ba, final ImplicitBijection<B1, B2> bb, final ImplicitBijection<C1, C2> bc, final ImplicitBijection<D1, D2> bd, final ImplicitBijection<E1, E2> be, final ImplicitBijection<F1, F2> bf, final ImplicitBijection<G1, G2> bg, final ImplicitBijection<H1, H2> bh, final ImplicitBijection<I1, I2> bi, final ImplicitBijection<J1, J2> bj, final ImplicitBijection<K1, K2> bk, final ImplicitBijection<L1, L2> bl) {
        return (Bijection<Tuple12<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1>, Tuple12<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2>>)GeneratedTupleBijections$class.tuple12(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl);
    }
    
    @Override
    public <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2> Bijection<Tuple13<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1>, Tuple13<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2>> tuple13(final ImplicitBijection<A1, A2> ba, final ImplicitBijection<B1, B2> bb, final ImplicitBijection<C1, C2> bc, final ImplicitBijection<D1, D2> bd, final ImplicitBijection<E1, E2> be, final ImplicitBijection<F1, F2> bf, final ImplicitBijection<G1, G2> bg, final ImplicitBijection<H1, H2> bh, final ImplicitBijection<I1, I2> bi, final ImplicitBijection<J1, J2> bj, final ImplicitBijection<K1, K2> bk, final ImplicitBijection<L1, L2> bl, final ImplicitBijection<M1, M2> bm) {
        return (Bijection<Tuple13<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1>, Tuple13<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2>>)GeneratedTupleBijections$class.tuple13(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm);
    }
    
    @Override
    public <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2> Bijection<Tuple14<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1>, Tuple14<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2>> tuple14(final ImplicitBijection<A1, A2> ba, final ImplicitBijection<B1, B2> bb, final ImplicitBijection<C1, C2> bc, final ImplicitBijection<D1, D2> bd, final ImplicitBijection<E1, E2> be, final ImplicitBijection<F1, F2> bf, final ImplicitBijection<G1, G2> bg, final ImplicitBijection<H1, H2> bh, final ImplicitBijection<I1, I2> bi, final ImplicitBijection<J1, J2> bj, final ImplicitBijection<K1, K2> bk, final ImplicitBijection<L1, L2> bl, final ImplicitBijection<M1, M2> bm, final ImplicitBijection<N1, N2> bn) {
        return (Bijection<Tuple14<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1>, Tuple14<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2>>)GeneratedTupleBijections$class.tuple14(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn);
    }
    
    @Override
    public <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2> Bijection<Tuple15<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1>, Tuple15<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2>> tuple15(final ImplicitBijection<A1, A2> ba, final ImplicitBijection<B1, B2> bb, final ImplicitBijection<C1, C2> bc, final ImplicitBijection<D1, D2> bd, final ImplicitBijection<E1, E2> be, final ImplicitBijection<F1, F2> bf, final ImplicitBijection<G1, G2> bg, final ImplicitBijection<H1, H2> bh, final ImplicitBijection<I1, I2> bi, final ImplicitBijection<J1, J2> bj, final ImplicitBijection<K1, K2> bk, final ImplicitBijection<L1, L2> bl, final ImplicitBijection<M1, M2> bm, final ImplicitBijection<N1, N2> bn, final ImplicitBijection<O1, O2> bo) {
        return (Bijection<Tuple15<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1>, Tuple15<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2>>)GeneratedTupleBijections$class.tuple15(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo);
    }
    
    @Override
    public <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2> Bijection<Tuple16<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1>, Tuple16<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2>> tuple16(final ImplicitBijection<A1, A2> ba, final ImplicitBijection<B1, B2> bb, final ImplicitBijection<C1, C2> bc, final ImplicitBijection<D1, D2> bd, final ImplicitBijection<E1, E2> be, final ImplicitBijection<F1, F2> bf, final ImplicitBijection<G1, G2> bg, final ImplicitBijection<H1, H2> bh, final ImplicitBijection<I1, I2> bi, final ImplicitBijection<J1, J2> bj, final ImplicitBijection<K1, K2> bk, final ImplicitBijection<L1, L2> bl, final ImplicitBijection<M1, M2> bm, final ImplicitBijection<N1, N2> bn, final ImplicitBijection<O1, O2> bo, final ImplicitBijection<P1, P2> bp) {
        return (Bijection<Tuple16<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1>, Tuple16<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2>>)GeneratedTupleBijections$class.tuple16(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp);
    }
    
    @Override
    public <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2> Bijection<Tuple17<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1>, Tuple17<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2>> tuple17(final ImplicitBijection<A1, A2> ba, final ImplicitBijection<B1, B2> bb, final ImplicitBijection<C1, C2> bc, final ImplicitBijection<D1, D2> bd, final ImplicitBijection<E1, E2> be, final ImplicitBijection<F1, F2> bf, final ImplicitBijection<G1, G2> bg, final ImplicitBijection<H1, H2> bh, final ImplicitBijection<I1, I2> bi, final ImplicitBijection<J1, J2> bj, final ImplicitBijection<K1, K2> bk, final ImplicitBijection<L1, L2> bl, final ImplicitBijection<M1, M2> bm, final ImplicitBijection<N1, N2> bn, final ImplicitBijection<O1, O2> bo, final ImplicitBijection<P1, P2> bp, final ImplicitBijection<Q1, Q2> bq) {
        return (Bijection<Tuple17<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1>, Tuple17<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2>>)GeneratedTupleBijections$class.tuple17(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq);
    }
    
    @Override
    public <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2> Bijection<Tuple18<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1>, Tuple18<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2>> tuple18(final ImplicitBijection<A1, A2> ba, final ImplicitBijection<B1, B2> bb, final ImplicitBijection<C1, C2> bc, final ImplicitBijection<D1, D2> bd, final ImplicitBijection<E1, E2> be, final ImplicitBijection<F1, F2> bf, final ImplicitBijection<G1, G2> bg, final ImplicitBijection<H1, H2> bh, final ImplicitBijection<I1, I2> bi, final ImplicitBijection<J1, J2> bj, final ImplicitBijection<K1, K2> bk, final ImplicitBijection<L1, L2> bl, final ImplicitBijection<M1, M2> bm, final ImplicitBijection<N1, N2> bn, final ImplicitBijection<O1, O2> bo, final ImplicitBijection<P1, P2> bp, final ImplicitBijection<Q1, Q2> bq, final ImplicitBijection<R1, R2> br) {
        return (Bijection<Tuple18<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1>, Tuple18<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2>>)GeneratedTupleBijections$class.tuple18(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq, br);
    }
    
    @Override
    public <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2, S2> Bijection<Tuple19<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1>, Tuple19<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2, S2>> tuple19(final ImplicitBijection<A1, A2> ba, final ImplicitBijection<B1, B2> bb, final ImplicitBijection<C1, C2> bc, final ImplicitBijection<D1, D2> bd, final ImplicitBijection<E1, E2> be, final ImplicitBijection<F1, F2> bf, final ImplicitBijection<G1, G2> bg, final ImplicitBijection<H1, H2> bh, final ImplicitBijection<I1, I2> bi, final ImplicitBijection<J1, J2> bj, final ImplicitBijection<K1, K2> bk, final ImplicitBijection<L1, L2> bl, final ImplicitBijection<M1, M2> bm, final ImplicitBijection<N1, N2> bn, final ImplicitBijection<O1, O2> bo, final ImplicitBijection<P1, P2> bp, final ImplicitBijection<Q1, Q2> bq, final ImplicitBijection<R1, R2> br, final ImplicitBijection<S1, S2> bs) {
        return (Bijection<Tuple19<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1>, Tuple19<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2, S2>>)GeneratedTupleBijections$class.tuple19(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq, br, bs);
    }
    
    @Override
    public <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2, S2, T2> Bijection<Tuple20<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1>, Tuple20<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2, S2, T2>> tuple20(final ImplicitBijection<A1, A2> ba, final ImplicitBijection<B1, B2> bb, final ImplicitBijection<C1, C2> bc, final ImplicitBijection<D1, D2> bd, final ImplicitBijection<E1, E2> be, final ImplicitBijection<F1, F2> bf, final ImplicitBijection<G1, G2> bg, final ImplicitBijection<H1, H2> bh, final ImplicitBijection<I1, I2> bi, final ImplicitBijection<J1, J2> bj, final ImplicitBijection<K1, K2> bk, final ImplicitBijection<L1, L2> bl, final ImplicitBijection<M1, M2> bm, final ImplicitBijection<N1, N2> bn, final ImplicitBijection<O1, O2> bo, final ImplicitBijection<P1, P2> bp, final ImplicitBijection<Q1, Q2> bq, final ImplicitBijection<R1, R2> br, final ImplicitBijection<S1, S2> bs, final ImplicitBijection<T1, T2> bt) {
        return (Bijection<Tuple20<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1>, Tuple20<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2, S2, T2>>)GeneratedTupleBijections$class.tuple20(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq, br, bs, bt);
    }
    
    @Override
    public <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2, S2, T2, U2> Bijection<Tuple21<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1>, Tuple21<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2, S2, T2, U2>> tuple21(final ImplicitBijection<A1, A2> ba, final ImplicitBijection<B1, B2> bb, final ImplicitBijection<C1, C2> bc, final ImplicitBijection<D1, D2> bd, final ImplicitBijection<E1, E2> be, final ImplicitBijection<F1, F2> bf, final ImplicitBijection<G1, G2> bg, final ImplicitBijection<H1, H2> bh, final ImplicitBijection<I1, I2> bi, final ImplicitBijection<J1, J2> bj, final ImplicitBijection<K1, K2> bk, final ImplicitBijection<L1, L2> bl, final ImplicitBijection<M1, M2> bm, final ImplicitBijection<N1, N2> bn, final ImplicitBijection<O1, O2> bo, final ImplicitBijection<P1, P2> bp, final ImplicitBijection<Q1, Q2> bq, final ImplicitBijection<R1, R2> br, final ImplicitBijection<S1, S2> bs, final ImplicitBijection<T1, T2> bt, final ImplicitBijection<U1, U2> bu) {
        return (Bijection<Tuple21<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1>, Tuple21<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2, S2, T2, U2>>)GeneratedTupleBijections$class.tuple21(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq, br, bs, bt, bu);
    }
    
    @Override
    public <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, V1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2, S2, T2, U2, V2> Bijection<Tuple22<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, V1>, Tuple22<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2, S2, T2, U2, V2>> tuple22(final ImplicitBijection<A1, A2> ba, final ImplicitBijection<B1, B2> bb, final ImplicitBijection<C1, C2> bc, final ImplicitBijection<D1, D2> bd, final ImplicitBijection<E1, E2> be, final ImplicitBijection<F1, F2> bf, final ImplicitBijection<G1, G2> bg, final ImplicitBijection<H1, H2> bh, final ImplicitBijection<I1, I2> bi, final ImplicitBijection<J1, J2> bj, final ImplicitBijection<K1, K2> bk, final ImplicitBijection<L1, L2> bl, final ImplicitBijection<M1, M2> bm, final ImplicitBijection<N1, N2> bn, final ImplicitBijection<O1, O2> bo, final ImplicitBijection<P1, P2> bp, final ImplicitBijection<Q1, Q2> bq, final ImplicitBijection<R1, R2> br, final ImplicitBijection<S1, S2> bs, final ImplicitBijection<T1, T2> bt, final ImplicitBijection<U1, U2> bu, final ImplicitBijection<V1, V2> bv) {
        return (Bijection<Tuple22<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, V1>, Tuple22<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2, S2, T2, U2, V2>>)GeneratedTupleBijections$class.tuple22(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq, br, bs, bt, bu, bv);
    }
    
    @Override
    public <A, B> Bijection<A, B> fromInjection(final Injection<A, B> inj) {
        return (Bijection<A, B>)LowPriorityBijections$class.fromInjection(this, inj);
    }
    
    public <A, B> Function1<A, B> toFunction(final Bijection<A, B> bij) {
        return bij.toFunction();
    }
    
    public <A, B> B apply(final A a, final ImplicitBijection<A, B> bij) {
        return bij.bijection().apply(a);
    }
    
    public <A, B> A invert(final B b, final ImplicitBijection<A, B> bij) {
        return bij.bijection().invert(b);
    }
    
    public <A, B> Bijection<A, B> build(final Function1<A, B> to, final Function1<B, A> from) {
        return (Bijection<A, B>)new Bijection$$anon.Bijection$$anon$4((Function1)to, (Function1)from);
    }
    
    public <A, B> Bijection<A, B> connect(final ImplicitBijection<A, B> bij) {
        return bij.bijection();
    }
    
    public <A, B, C> Bijection<A, C> connect(final ImplicitBijection<A, B> bij, final ImplicitBijection<B, C> bij2) {
        return bij.bijection().andThen(bij2.bijection());
    }
    
    public <A, B, C, D> Bijection<A, D> connect(final ImplicitBijection<A, B> bij1, final ImplicitBijection<B, C> bij2, final ImplicitBijection<C, D> bij3) {
        return this.connect(bij1, bij2).andThen(bij3.bijection());
    }
    
    public <A, B, C, D, E> Bijection<A, E> connect(final ImplicitBijection<A, B> bij1, final ImplicitBijection<B, C> bij2, final ImplicitBijection<C, D> bij3, final ImplicitBijection<D, E> bij4) {
        return this.connect(bij1, bij2, bij3).andThen(bij4.bijection());
    }
    
    public <A> Bijection<A, A> identity() {
        return new IdentityBijection<A>();
    }
    
    public <A> Bijection<A, Option<A>> filterDefault(final A default) {
        return (Bijection<A, Option<A>>)new Bijection$$anon.Bijection$$anon$5((Object)default);
    }
    
    public <A, B, C, D> Bijection<Function1<A, C>, Function1<B, D>> fnBijection(final ImplicitBijection<A, B> bij1, final ImplicitBijection<C, D> bij2) {
        return (Bijection<Function1<A, C>, Function1<B, D>>)new Bijection$$anon.Bijection$$anon$6((ImplicitBijection)bij1, (ImplicitBijection)bij2);
    }
    
    public <A, B, C, D, E, F> Bijection<Function2<A, C, E>, Function2<B, D, F>> fn2Bijection(final ImplicitBijection<A, B> bab, final ImplicitBijection<C, D> bcd, final ImplicitBijection<E, F> bef) {
        return (Bijection<Function2<A, C, E>, Function2<B, D, F>>)new Bijection$$anon.Bijection$$anon$7((ImplicitBijection)bab, (ImplicitBijection)bcd, (ImplicitBijection)bef);
    }
    
    public <T, U> Bijection<Tuple2<T, U>, Tuple2<U, T>> swap() {
        return (Bijection<Tuple2<T, U>, Tuple2<U, T>>)SwapBijection$.MODULE$.apply();
    }
    
    public <A, B extends A> Bijection<A, B> subclass(final Function1<A, B> afn, final ClassTag<B> ct) {
        return (Bijection<A, B>)new Bijection$$anon.Bijection$$anon$9((Function1)afn, (ClassTag)ct);
    }
    
    private Object readResolve() {
        return Bijection$.MODULE$;
    }
    
    private Bijection$() {
        LowPriorityBijections$class.$init$(MODULE$ = this);
        GeneratedTupleBijections$class.$init$(this);
        NumericBijections$class.$init$(this);
        StringBijections$class.$init$(this);
        BinaryBijections$class.$init$(this);
        CollectionBijections$class.$init$(this);
    }
}
