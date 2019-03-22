// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.collection.mutable.Buffer$;
import scala.collection.mutable.Buffer;
import scala.collection.immutable.Map$;
import scala.collection.immutable.Map;
import scala.collection.immutable.Vector$;
import scala.collection.immutable.Vector;
import scala.Predef$;
import scala.collection.IndexedSeq;
import scala.collection.immutable.Set$;
import scala.collection.immutable.Set;
import scala.collection.immutable.List$;
import scala.collection.immutable.List;
import scala.Function0;
import scala.util.Try$;
import scala.runtime.Nothing$;
import scala.collection.generic.CanBuildFrom;
import scala.collection.Traversable;
import scala.util.Either;
import scala.Option;
import scala.Function1;
import scala.Function2;
import scala.reflect.ClassTag$;
import scala.Array$;
import scala.util.Try;
import java.nio.ByteBuffer;
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
import scala.Tuple2;
import scala.Symbol;
import java.io.Serializable;

public final class Bufferable$ implements GeneratedTupleBufferable, Serializable
{
    public static final Bufferable$ MODULE$;
    private final int DEFAULT_SIZE;
    private final Bufferable<Object> booleanBufferable;
    private final Bufferable<Object> byteBufferable;
    private final Bufferable<Object> charBufferable;
    private final Bufferable<Object> shortBufferable;
    private final Bufferable<Object> intBufferable;
    private final Bufferable<Object> longBufferable;
    private final Bufferable<Object> floatBufferable;
    private final Bufferable<Object> doubleBufferable;
    private final Bufferable<byte[]> byteArray;
    private final Bufferable<String> stringBufferable;
    private final Bufferable<Symbol> symbolBufferable;
    
    static {
        new Bufferable$();
    }
    
    @Override
    public <A, B> Bufferable<Tuple2<A, B>> tuple2(final Bufferable<A> ba, final Bufferable<B> bb) {
        return (Bufferable<Tuple2<A, B>>)GeneratedTupleBufferable$class.tuple2(this, ba, bb);
    }
    
    @Override
    public <A, B, C> Bufferable<Tuple3<A, B, C>> tuple3(final Bufferable<A> ba, final Bufferable<B> bb, final Bufferable<C> bc) {
        return (Bufferable<Tuple3<A, B, C>>)GeneratedTupleBufferable$class.tuple3(this, ba, bb, bc);
    }
    
    @Override
    public <A, B, C, D> Bufferable<Tuple4<A, B, C, D>> tuple4(final Bufferable<A> ba, final Bufferable<B> bb, final Bufferable<C> bc, final Bufferable<D> bd) {
        return (Bufferable<Tuple4<A, B, C, D>>)GeneratedTupleBufferable$class.tuple4(this, ba, bb, bc, bd);
    }
    
    @Override
    public <A, B, C, D, E> Bufferable<Tuple5<A, B, C, D, E>> tuple5(final Bufferable<A> ba, final Bufferable<B> bb, final Bufferable<C> bc, final Bufferable<D> bd, final Bufferable<E> be) {
        return (Bufferable<Tuple5<A, B, C, D, E>>)GeneratedTupleBufferable$class.tuple5(this, ba, bb, bc, bd, be);
    }
    
    @Override
    public <A, B, C, D, E, F> Bufferable<Tuple6<A, B, C, D, E, F>> tuple6(final Bufferable<A> ba, final Bufferable<B> bb, final Bufferable<C> bc, final Bufferable<D> bd, final Bufferable<E> be, final Bufferable<F> bf) {
        return (Bufferable<Tuple6<A, B, C, D, E, F>>)GeneratedTupleBufferable$class.tuple6(this, ba, bb, bc, bd, be, bf);
    }
    
    @Override
    public <A, B, C, D, E, F, G> Bufferable<Tuple7<A, B, C, D, E, F, G>> tuple7(final Bufferable<A> ba, final Bufferable<B> bb, final Bufferable<C> bc, final Bufferable<D> bd, final Bufferable<E> be, final Bufferable<F> bf, final Bufferable<G> bg) {
        return (Bufferable<Tuple7<A, B, C, D, E, F, G>>)GeneratedTupleBufferable$class.tuple7(this, ba, bb, bc, bd, be, bf, bg);
    }
    
    @Override
    public <A, B, C, D, E, F, G, H> Bufferable<Tuple8<A, B, C, D, E, F, G, H>> tuple8(final Bufferable<A> ba, final Bufferable<B> bb, final Bufferable<C> bc, final Bufferable<D> bd, final Bufferable<E> be, final Bufferable<F> bf, final Bufferable<G> bg, final Bufferable<H> bh) {
        return (Bufferable<Tuple8<A, B, C, D, E, F, G, H>>)GeneratedTupleBufferable$class.tuple8(this, ba, bb, bc, bd, be, bf, bg, bh);
    }
    
    @Override
    public <A, B, C, D, E, F, G, H, I> Bufferable<Tuple9<A, B, C, D, E, F, G, H, I>> tuple9(final Bufferable<A> ba, final Bufferable<B> bb, final Bufferable<C> bc, final Bufferable<D> bd, final Bufferable<E> be, final Bufferable<F> bf, final Bufferable<G> bg, final Bufferable<H> bh, final Bufferable<I> bi) {
        return (Bufferable<Tuple9<A, B, C, D, E, F, G, H, I>>)GeneratedTupleBufferable$class.tuple9(this, ba, bb, bc, bd, be, bf, bg, bh, bi);
    }
    
    @Override
    public <A, B, C, D, E, F, G, H, I, J> Bufferable<Tuple10<A, B, C, D, E, F, G, H, I, J>> tuple10(final Bufferable<A> ba, final Bufferable<B> bb, final Bufferable<C> bc, final Bufferable<D> bd, final Bufferable<E> be, final Bufferable<F> bf, final Bufferable<G> bg, final Bufferable<H> bh, final Bufferable<I> bi, final Bufferable<J> bj) {
        return (Bufferable<Tuple10<A, B, C, D, E, F, G, H, I, J>>)GeneratedTupleBufferable$class.tuple10(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj);
    }
    
    @Override
    public <A, B, C, D, E, F, G, H, I, J, K> Bufferable<Tuple11<A, B, C, D, E, F, G, H, I, J, K>> tuple11(final Bufferable<A> ba, final Bufferable<B> bb, final Bufferable<C> bc, final Bufferable<D> bd, final Bufferable<E> be, final Bufferable<F> bf, final Bufferable<G> bg, final Bufferable<H> bh, final Bufferable<I> bi, final Bufferable<J> bj, final Bufferable<K> bk) {
        return (Bufferable<Tuple11<A, B, C, D, E, F, G, H, I, J, K>>)GeneratedTupleBufferable$class.tuple11(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk);
    }
    
    @Override
    public <A, B, C, D, E, F, G, H, I, J, K, L> Bufferable<Tuple12<A, B, C, D, E, F, G, H, I, J, K, L>> tuple12(final Bufferable<A> ba, final Bufferable<B> bb, final Bufferable<C> bc, final Bufferable<D> bd, final Bufferable<E> be, final Bufferable<F> bf, final Bufferable<G> bg, final Bufferable<H> bh, final Bufferable<I> bi, final Bufferable<J> bj, final Bufferable<K> bk, final Bufferable<L> bl) {
        return (Bufferable<Tuple12<A, B, C, D, E, F, G, H, I, J, K, L>>)GeneratedTupleBufferable$class.tuple12(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl);
    }
    
    @Override
    public <A, B, C, D, E, F, G, H, I, J, K, L, M> Bufferable<Tuple13<A, B, C, D, E, F, G, H, I, J, K, L, M>> tuple13(final Bufferable<A> ba, final Bufferable<B> bb, final Bufferable<C> bc, final Bufferable<D> bd, final Bufferable<E> be, final Bufferable<F> bf, final Bufferable<G> bg, final Bufferable<H> bh, final Bufferable<I> bi, final Bufferable<J> bj, final Bufferable<K> bk, final Bufferable<L> bl, final Bufferable<M> bm) {
        return (Bufferable<Tuple13<A, B, C, D, E, F, G, H, I, J, K, L, M>>)GeneratedTupleBufferable$class.tuple13(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm);
    }
    
    @Override
    public <A, B, C, D, E, F, G, H, I, J, K, L, M, N> Bufferable<Tuple14<A, B, C, D, E, F, G, H, I, J, K, L, M, N>> tuple14(final Bufferable<A> ba, final Bufferable<B> bb, final Bufferable<C> bc, final Bufferable<D> bd, final Bufferable<E> be, final Bufferable<F> bf, final Bufferable<G> bg, final Bufferable<H> bh, final Bufferable<I> bi, final Bufferable<J> bj, final Bufferable<K> bk, final Bufferable<L> bl, final Bufferable<M> bm, final Bufferable<N> bn) {
        return (Bufferable<Tuple14<A, B, C, D, E, F, G, H, I, J, K, L, M, N>>)GeneratedTupleBufferable$class.tuple14(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn);
    }
    
    @Override
    public <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O> Bufferable<Tuple15<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O>> tuple15(final Bufferable<A> ba, final Bufferable<B> bb, final Bufferable<C> bc, final Bufferable<D> bd, final Bufferable<E> be, final Bufferable<F> bf, final Bufferable<G> bg, final Bufferable<H> bh, final Bufferable<I> bi, final Bufferable<J> bj, final Bufferable<K> bk, final Bufferable<L> bl, final Bufferable<M> bm, final Bufferable<N> bn, final Bufferable<O> bo) {
        return (Bufferable<Tuple15<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O>>)GeneratedTupleBufferable$class.tuple15(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo);
    }
    
    @Override
    public <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P> Bufferable<Tuple16<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P>> tuple16(final Bufferable<A> ba, final Bufferable<B> bb, final Bufferable<C> bc, final Bufferable<D> bd, final Bufferable<E> be, final Bufferable<F> bf, final Bufferable<G> bg, final Bufferable<H> bh, final Bufferable<I> bi, final Bufferable<J> bj, final Bufferable<K> bk, final Bufferable<L> bl, final Bufferable<M> bm, final Bufferable<N> bn, final Bufferable<O> bo, final Bufferable<P> bp) {
        return (Bufferable<Tuple16<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P>>)GeneratedTupleBufferable$class.tuple16(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp);
    }
    
    @Override
    public <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q> Bufferable<Tuple17<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q>> tuple17(final Bufferable<A> ba, final Bufferable<B> bb, final Bufferable<C> bc, final Bufferable<D> bd, final Bufferable<E> be, final Bufferable<F> bf, final Bufferable<G> bg, final Bufferable<H> bh, final Bufferable<I> bi, final Bufferable<J> bj, final Bufferable<K> bk, final Bufferable<L> bl, final Bufferable<M> bm, final Bufferable<N> bn, final Bufferable<O> bo, final Bufferable<P> bp, final Bufferable<Q> bq) {
        return (Bufferable<Tuple17<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q>>)GeneratedTupleBufferable$class.tuple17(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq);
    }
    
    @Override
    public <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R> Bufferable<Tuple18<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R>> tuple18(final Bufferable<A> ba, final Bufferable<B> bb, final Bufferable<C> bc, final Bufferable<D> bd, final Bufferable<E> be, final Bufferable<F> bf, final Bufferable<G> bg, final Bufferable<H> bh, final Bufferable<I> bi, final Bufferable<J> bj, final Bufferable<K> bk, final Bufferable<L> bl, final Bufferable<M> bm, final Bufferable<N> bn, final Bufferable<O> bo, final Bufferable<P> bp, final Bufferable<Q> bq, final Bufferable<R> br) {
        return (Bufferable<Tuple18<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R>>)GeneratedTupleBufferable$class.tuple18(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq, br);
    }
    
    @Override
    public <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S> Bufferable<Tuple19<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S>> tuple19(final Bufferable<A> ba, final Bufferable<B> bb, final Bufferable<C> bc, final Bufferable<D> bd, final Bufferable<E> be, final Bufferable<F> bf, final Bufferable<G> bg, final Bufferable<H> bh, final Bufferable<I> bi, final Bufferable<J> bj, final Bufferable<K> bk, final Bufferable<L> bl, final Bufferable<M> bm, final Bufferable<N> bn, final Bufferable<O> bo, final Bufferable<P> bp, final Bufferable<Q> bq, final Bufferable<R> br, final Bufferable<S> bs) {
        return (Bufferable<Tuple19<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S>>)GeneratedTupleBufferable$class.tuple19(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq, br, bs);
    }
    
    @Override
    public <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T> Bufferable<Tuple20<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T>> tuple20(final Bufferable<A> ba, final Bufferable<B> bb, final Bufferable<C> bc, final Bufferable<D> bd, final Bufferable<E> be, final Bufferable<F> bf, final Bufferable<G> bg, final Bufferable<H> bh, final Bufferable<I> bi, final Bufferable<J> bj, final Bufferable<K> bk, final Bufferable<L> bl, final Bufferable<M> bm, final Bufferable<N> bn, final Bufferable<O> bo, final Bufferable<P> bp, final Bufferable<Q> bq, final Bufferable<R> br, final Bufferable<S> bs, final Bufferable<T> bt) {
        return (Bufferable<Tuple20<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T>>)GeneratedTupleBufferable$class.tuple20(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq, br, bs, bt);
    }
    
    @Override
    public <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U> Bufferable<Tuple21<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U>> tuple21(final Bufferable<A> ba, final Bufferable<B> bb, final Bufferable<C> bc, final Bufferable<D> bd, final Bufferable<E> be, final Bufferable<F> bf, final Bufferable<G> bg, final Bufferable<H> bh, final Bufferable<I> bi, final Bufferable<J> bj, final Bufferable<K> bk, final Bufferable<L> bl, final Bufferable<M> bm, final Bufferable<N> bn, final Bufferable<O> bo, final Bufferable<P> bp, final Bufferable<Q> bq, final Bufferable<R> br, final Bufferable<S> bs, final Bufferable<T> bt, final Bufferable<U> bu) {
        return (Bufferable<Tuple21<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U>>)GeneratedTupleBufferable$class.tuple21(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq, br, bs, bt, bu);
    }
    
    @Override
    public <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V> Bufferable<Tuple22<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V>> tuple22(final Bufferable<A> ba, final Bufferable<B> bb, final Bufferable<C> bc, final Bufferable<D> bd, final Bufferable<E> be, final Bufferable<F> bf, final Bufferable<G> bg, final Bufferable<H> bh, final Bufferable<I> bi, final Bufferable<J> bj, final Bufferable<K> bk, final Bufferable<L> bl, final Bufferable<M> bm, final Bufferable<N> bn, final Bufferable<O> bo, final Bufferable<P> bp, final Bufferable<Q> bq, final Bufferable<R> br, final Bufferable<S> bs, final Bufferable<T> bt, final Bufferable<U> bu, final Bufferable<V> bv) {
        return (Bufferable<Tuple22<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V>>)GeneratedTupleBufferable$class.tuple22(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq, br, bs, bt, bu, bv);
    }
    
    public int DEFAULT_SIZE() {
        return this.DEFAULT_SIZE;
    }
    
    public <T> Bufferable<T> on(final Bufferable<T> buf) {
        return buf;
    }
    
    public <T> T deepCopy(final T t, final Bufferable<T> buf) {
        final Injection inj = this.injectionOf(buf);
        return inj.invert(inj.apply(t)).get();
    }
    
    public <T> ByteBuffer put(final ByteBuffer into, final T t, final Bufferable<T> buf) {
        return buf.put(into, t);
    }
    
    public <T> Try<Tuple2<ByteBuffer, T>> get(final ByteBuffer from, final Bufferable<T> buf) {
        return buf.get(from);
    }
    
    public byte[] getBytes(final ByteBuffer from, final int start) {
        final ByteBuffer fromd = from.duplicate();
        final int current = fromd.position();
        fromd.position(start);
        final byte[] result = (byte[])Array$.MODULE$.ofDim(current - start, ClassTag$.MODULE$.Byte());
        fromd.get(result);
        return result;
    }
    
    public int getBytes$default$2() {
        return 0;
    }
    
    public <A, B> Bufferable<A> viaBijection(final Bufferable<B> buf, final ImplicitBijection<A, B> bij) {
        return this.build((Function2<ByteBuffer, A, ByteBuffer>)new Bufferable$$anonfun$viaBijection.Bufferable$$anonfun$viaBijection$1((Bufferable)buf, (ImplicitBijection)bij), (Function1<ByteBuffer, Try<Tuple2<ByteBuffer, A>>>)new Bufferable$$anonfun$viaBijection.Bufferable$$anonfun$viaBijection$2((Bufferable)buf, (ImplicitBijection)bij));
    }
    
    public <A, B> Bufferable<A> viaInjection(final Bufferable<B> buf, final Injection<A, B> inj) {
        return this.build((Function2<ByteBuffer, A, ByteBuffer>)new Bufferable$$anonfun$viaInjection.Bufferable$$anonfun$viaInjection$1((Bufferable)buf, (Injection)inj), (Function1<ByteBuffer, Try<Tuple2<ByteBuffer, A>>>)new Bufferable$$anonfun$viaInjection.Bufferable$$anonfun$viaInjection$2((Bufferable)buf, (Injection)inj));
    }
    
    public <T> Injection<T, byte[]> injectionOf(final Bufferable<T> buf) {
        return Injection$.MODULE$.build((Function1<T, byte[]>)new Bufferable$$anonfun$injectionOf.Bufferable$$anonfun$injectionOf$1((Bufferable)buf), (Function1<byte[], Try<T>>)new Bufferable$$anonfun$injectionOf.Bufferable$$anonfun$injectionOf$2((Bufferable)buf));
    }
    
    public ByteBuffer reallocate(final ByteBuffer bb) {
        final int newCapacity = (bb.capacity() > this.DEFAULT_SIZE() / 2) ? (bb.capacity() * 2) : this.DEFAULT_SIZE();
        final ByteBuffer newBb = bb.isDirect() ? ByteBuffer.allocateDirect(newCapacity) : ByteBuffer.allocate(newCapacity);
        final ByteBuffer tmpBb = bb.duplicate();
        final int currentPos = tmpBb.position();
        tmpBb.position(0);
        tmpBb.limit(currentPos);
        newBb.put(tmpBb);
        return newBb;
    }
    
    public ByteBuffer reallocatingPut(final ByteBuffer bb, final Function1<ByteBuffer, ByteBuffer> putfn) {
        final ByteBuffer init = bb.duplicate();
        return putfn.apply(init);
        return putfn.apply(init);
    }
    
    public <T> Bufferable<T> build(final Function2<ByteBuffer, T, ByteBuffer> putfn, final Function1<ByteBuffer, Try<Tuple2<ByteBuffer, T>>> getfn) {
        return (Bufferable<T>)new Bufferable$$anon.Bufferable$$anon$1((Function2)putfn, (Function1)getfn);
    }
    
    public <T> Bufferable<T> buildCatchDuplicate(final Function2<ByteBuffer, T, ByteBuffer> putfn, final Function1<ByteBuffer, T> getfn) {
        return (Bufferable<T>)new Bufferable$$anon.Bufferable$$anon$2((Function2)putfn, (Function1)getfn);
    }
    
    public Bufferable<Object> booleanBufferable() {
        return this.booleanBufferable;
    }
    
    public Bufferable<Object> byteBufferable() {
        return this.byteBufferable;
    }
    
    public Bufferable<Object> charBufferable() {
        return this.charBufferable;
    }
    
    public Bufferable<Object> shortBufferable() {
        return this.shortBufferable;
    }
    
    public Bufferable<Object> intBufferable() {
        return this.intBufferable;
    }
    
    public Bufferable<Object> longBufferable() {
        return this.longBufferable;
    }
    
    public Bufferable<Object> floatBufferable() {
        return this.floatBufferable;
    }
    
    public Bufferable<Object> doubleBufferable() {
        return this.doubleBufferable;
    }
    
    public Bufferable<byte[]> byteArray() {
        return this.byteArray;
    }
    
    public Bufferable<String> stringBufferable() {
        return this.stringBufferable;
    }
    
    public Bufferable<Symbol> symbolBufferable() {
        return this.symbolBufferable;
    }
    
    public <T> Bufferable<Option<T>> option(final Bufferable<T> buf) {
        return this.build((Function2<ByteBuffer, Option<T>, ByteBuffer>)new Bufferable$$anonfun$option.Bufferable$$anonfun$option$1((Bufferable)buf), (Function1<ByteBuffer, Try<Tuple2<ByteBuffer, Option<T>>>>)new Bufferable$$anonfun$option.Bufferable$$anonfun$option$2((Bufferable)buf));
    }
    
    public <L, R> Bufferable<Either<L, R>> either(final Bufferable<L> bufl, final Bufferable<R> bufr) {
        return this.build((Function2<ByteBuffer, Either<L, R>, ByteBuffer>)new Bufferable$$anonfun$either.Bufferable$$anonfun$either$1((Bufferable)bufl, (Bufferable)bufr), (Function1<ByteBuffer, Try<Tuple2<ByteBuffer, Either<L, R>>>>)new Bufferable$$anonfun$either.Bufferable$$anonfun$either$2((Bufferable)bufl, (Bufferable)bufr));
    }
    
    public <T> ByteBuffer putCollection(final ByteBuffer bb, final Traversable<T> l, final Bufferable<T> buf) {
        final int size = l.size();
        final ByteBuffer nextBb = this.reallocatingPut(bb, (Function1<ByteBuffer, ByteBuffer>)new Bufferable$$anonfun.Bufferable$$anonfun$23(size));
        return l.foldLeft(nextBb, (Function2<ByteBuffer, T, ByteBuffer>)new Bufferable$$anonfun$putCollection.Bufferable$$anonfun$putCollection$1((Bufferable)buf));
    }
    
    public <T, C> Try<Tuple2<ByteBuffer, C>> getCollection(final ByteBuffer initbb, final CanBuildFrom<Nothing$, T, C> cbf, final Bufferable<T> buf) {
        return Try$.MODULE$.apply((Function0<Tuple2<ByteBuffer, C>>)new Bufferable$$anonfun$getCollection.Bufferable$$anonfun$getCollection$1(initbb, (CanBuildFrom)cbf, (Bufferable)buf));
    }
    
    public <C extends Traversable<T>, T> Bufferable<C> collection(final Bufferable<T> buf, final CanBuildFrom<Nothing$, T, C> cbf) {
        return this.build((Function2<ByteBuffer, C, ByteBuffer>)new Bufferable$$anonfun$collection.Bufferable$$anonfun$collection$1((Bufferable)buf), (Function1<ByteBuffer, Try<Tuple2<ByteBuffer, C>>>)new Bufferable$$anonfun$collection.Bufferable$$anonfun$collection$2((Bufferable)buf, (CanBuildFrom)cbf));
    }
    
    public <T> Bufferable<List<T>> list(final Bufferable<T> buf) {
        return this.collection(buf, (CanBuildFrom<Nothing$, T, List<T>>)List$.MODULE$.canBuildFrom());
    }
    
    public <T> Bufferable<Set<T>> set(final Bufferable<T> buf) {
        return this.collection(buf, (CanBuildFrom<Nothing$, T, Set<T>>)Set$.MODULE$.canBuildFrom());
    }
    
    public <T> Bufferable<IndexedSeq<T>> indexedSeq(final Bufferable<T> buf) {
        return this.collection(buf, (CanBuildFrom<Nothing$, T, IndexedSeq<T>>)Predef$.MODULE$.fallbackStringCanBuildFrom());
    }
    
    public <T> Bufferable<Vector<T>> vector(final Bufferable<T> buf) {
        return this.collection(buf, (CanBuildFrom<Nothing$, T, Vector<T>>)Vector$.MODULE$.canBuildFrom());
    }
    
    public <K, V> Bufferable<Map<K, V>> map(final Bufferable<K> bufk, final Bufferable<V> bufv) {
        return this.collection(this.tuple2(bufk, bufv), (CanBuildFrom<Nothing$, Tuple2<K, V>, Map<K, V>>)Map$.MODULE$.canBuildFrom());
    }
    
    public <K, V> Bufferable<scala.collection.mutable.Map<K, V>> mmap(final Bufferable<K> bufk, final Bufferable<V> bufv) {
        return this.collection(this.tuple2(bufk, bufv), (CanBuildFrom<Nothing$, Tuple2<K, V>, scala.collection.mutable.Map<K, V>>)scala.collection.mutable.Map$.MODULE$.canBuildFrom());
    }
    
    public <T> Bufferable<Buffer<T>> buffer(final Bufferable<T> buf) {
        return this.collection(buf, (CanBuildFrom<Nothing$, T, Buffer<T>>)Buffer$.MODULE$.canBuildFrom());
    }
    
    public <T> Bufferable<scala.collection.mutable.Set<T>> mset(final Bufferable<T> buf) {
        return this.collection(buf, (CanBuildFrom<Nothing$, T, scala.collection.mutable.Set<T>>)scala.collection.mutable.Set$.MODULE$.canBuildFrom());
    }
    
    public <T> Bufferable<Object> array(final Bufferable<T> buf, final CanBuildFrom<Nothing$, T, Object> cbf) {
        return this.build((Function2<ByteBuffer, Object, ByteBuffer>)new Bufferable$$anonfun$array.Bufferable$$anonfun$array$1((Bufferable)buf), (Function1<ByteBuffer, Try<Tuple2<ByteBuffer, Object>>>)new Bufferable$$anonfun$array.Bufferable$$anonfun$array$2((Bufferable)buf, (CanBuildFrom)cbf));
    }
    
    private Object readResolve() {
        return Bufferable$.MODULE$;
    }
    
    private Bufferable$() {
        GeneratedTupleBufferable$class.$init$(MODULE$ = this);
        this.DEFAULT_SIZE = 1024;
        this.booleanBufferable = this.buildCatchDuplicate((Function2<ByteBuffer, Object, ByteBuffer>)new Bufferable$$anonfun.Bufferable$$anonfun$1(), (Function1<ByteBuffer, Object>)new Bufferable$$anonfun.Bufferable$$anonfun$2());
        this.byteBufferable = this.buildCatchDuplicate((Function2<ByteBuffer, Object, ByteBuffer>)new Bufferable$$anonfun.Bufferable$$anonfun$3(), (Function1<ByteBuffer, Object>)new Bufferable$$anonfun.Bufferable$$anonfun$4());
        this.charBufferable = this.buildCatchDuplicate((Function2<ByteBuffer, Object, ByteBuffer>)new Bufferable$$anonfun.Bufferable$$anonfun$5(), (Function1<ByteBuffer, Object>)new Bufferable$$anonfun.Bufferable$$anonfun$6());
        this.shortBufferable = this.buildCatchDuplicate((Function2<ByteBuffer, Object, ByteBuffer>)new Bufferable$$anonfun.Bufferable$$anonfun$7(), (Function1<ByteBuffer, Object>)new Bufferable$$anonfun.Bufferable$$anonfun$8());
        this.intBufferable = this.buildCatchDuplicate((Function2<ByteBuffer, Object, ByteBuffer>)new Bufferable$$anonfun.Bufferable$$anonfun$9(), (Function1<ByteBuffer, Object>)new Bufferable$$anonfun.Bufferable$$anonfun$10());
        this.longBufferable = this.buildCatchDuplicate((Function2<ByteBuffer, Object, ByteBuffer>)new Bufferable$$anonfun.Bufferable$$anonfun$11(), (Function1<ByteBuffer, Object>)new Bufferable$$anonfun.Bufferable$$anonfun$12());
        this.floatBufferable = this.buildCatchDuplicate((Function2<ByteBuffer, Object, ByteBuffer>)new Bufferable$$anonfun.Bufferable$$anonfun$13(), (Function1<ByteBuffer, Object>)new Bufferable$$anonfun.Bufferable$$anonfun$14());
        this.doubleBufferable = this.buildCatchDuplicate((Function2<ByteBuffer, Object, ByteBuffer>)new Bufferable$$anonfun.Bufferable$$anonfun$15(), (Function1<ByteBuffer, Object>)new Bufferable$$anonfun.Bufferable$$anonfun$16());
        this.byteArray = this.buildCatchDuplicate((Function2<ByteBuffer, byte[], ByteBuffer>)new Bufferable$$anonfun.Bufferable$$anonfun$17(), (Function1<ByteBuffer, byte[]>)new Bufferable$$anonfun.Bufferable$$anonfun$19());
        this.stringBufferable = this.viaInjection(this.byteArray(), Injection$.MODULE$.utf8());
        this.symbolBufferable = this.viaBijection(this.stringBufferable(), (ImplicitBijection<Symbol, String>)ImplicitBijection$.MODULE$.forward((Bijection<A, B>)Bijection$.MODULE$.symbol2String()));
    }
}
