// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.runtime.ScalaRunTime$;
import scala.runtime.BoxesRunTime;
import scala.Some;
import scala.None$;
import scala.Option;
import scala.runtime.AbstractFunction1;
import scala.Product$class;
import scala.collection.Iterator;
import scala.Serializable;
import scala.Product;
import java.net.URL;
import java.util.UUID;
import scala.math.BigInt;
import scala.Tuple2;
import scala.Tuple3;
import scala.Tuple4;
import scala.Tuple5;
import scala.Tuple6;
import scala.Tuple7;
import scala.Tuple8;
import scala.Tuple9;
import scala.Tuple10;
import scala.Tuple11;
import scala.Tuple12;
import scala.Tuple13;
import scala.Tuple14;
import scala.Tuple15;
import scala.Tuple16;
import scala.Tuple17;
import scala.Tuple18;
import scala.Tuple19;
import scala.Tuple20;
import scala.Tuple21;
import scala.collection.immutable.List;
import scala.Tuple22;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes = "\u0006\u0001\u0005]u!B\u0001\u0003\u0011\u0003I\u0011aC*ue&twmQ8eK\u000eT!a\u0001\u0003\u0002\u0013\tL'.Z2uS>t'BA\u0003\u0007\u0003\u001d!x/\u001b;uKJT\u0011aB\u0001\u0004G>l7\u0001\u0001\t\u0003\u0015-i\u0011A\u0001\u0004\u0006\u0019\tA\t!\u0004\u0002\f'R\u0014\u0018N\\4D_\u0012,7mE\u0002\f\u001dQ\u0001\"a\u0004\n\u000e\u0003AQ\u0011!E\u0001\u0006g\u000e\fG.Y\u0005\u0003'A\u0011a!\u00118z%\u00164\u0007C\u0001\u0006\u0016\u0013\t1\"A\u0001\tTiJLgnZ%oU\u0016\u001cG/[8og\")\u0001d\u0003C\u00013\u00051A(\u001b8jiz\"\u0012!\u0003\u0004\u00057-\u0011ED\u0001\tV%2+enY8eK\u0012\u001cFO]5oON!!$\b\u0011$!\tya$\u0003\u0002 !\t1\u0011I\\=WC2\u0004\"aD\u0011\n\u0005\t\u0002\"a\u0002)s_\u0012,8\r\u001e\t\u0003\u001f\u0011J!!\n\t\u0003\u0019M+'/[1mSj\f'\r\\3\t\u0011\u001dR\"Q3A\u0005\u0002!\nQ\"\u001a8d_\u0012,Gm\u0015;sS:<W#A\u0015\u0011\u0005)jcBA\b,\u0013\ta\u0003#\u0001\u0004Qe\u0016$WMZ\u0005\u0003]=\u0012aa\u0015;sS:<'B\u0001\u0017\u0011\u0011!\t$D!E!\u0002\u0013I\u0013AD3oG>$W\rZ*ue&tw\r\t\u0005\u00061i!\ta\r\u000b\u0003iY\u0002\"!\u000e\u000e\u000e\u0003-AQa\n\u001aA\u0002%Bq\u0001\u000f\u000e\u0002\u0002\u0013\u0005\u0011(\u0001\u0003d_BLHC\u0001\u001b;\u0011\u001d9s\u0007%AA\u0002%Bq\u0001\u0010\u000e\u0012\u0002\u0013\u0005Q(\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0003yR#!K ,\u0003\u0001\u0003\"!\u0011$\u000e\u0003\tS!a\u0011#\u0002\u0013Ut7\r[3dW\u0016$'BA#\u0011\u0003)\tgN\\8uCRLwN\\\u0005\u0003\u000f\n\u0013\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u0011\u001dI%$!A\u0005B)\u000bQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DX#A&\u0011\u00051\u000bV\"A'\u000b\u00059{\u0015\u0001\u00027b]\u001eT\u0011\u0001U\u0001\u0005U\u00064\u0018-\u0003\u0002/\u001b\"91KGA\u0001\n\u0003!\u0016\u0001\u00049s_\u0012,8\r^!sSRLX#A+\u0011\u0005=1\u0016BA,\u0011\u0005\rIe\u000e\u001e\u0005\b3j\t\t\u0011\"\u0001[\u00039\u0001(o\u001c3vGR,E.Z7f]R$\"a\u00170\u0011\u0005=a\u0016BA/\u0011\u0005\r\te.\u001f\u0005\b?b\u000b\t\u00111\u0001V\u0003\rAH%\r\u0005\bCj\t\t\u0011\"\u0011c\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014X#A2\u0011\u0007\u0011<7,D\u0001f\u0015\t1\u0007#\u0001\u0006d_2dWm\u0019;j_:L!\u0001[3\u0003\u0011%#XM]1u_JDqA\u001b\u000e\u0002\u0002\u0013\u00051.\u0001\u0005dC:,\u0015/^1m)\taw\u000e\u0005\u0002\u0010[&\u0011a\u000e\u0005\u0002\b\u0005>|G.Z1o\u0011\u001dy\u0016.!AA\u0002mCq!\u001d\u000e\u0002\u0002\u0013\u0005#/\u0001\u0005iCND7i\u001c3f)\u0005)\u0006b\u0002;\u001b\u0003\u0003%\t%^\u0001\u0007KF,\u0018\r\\:\u0015\u000514\bbB0t\u0003\u0003\u0005\ra\u0017\u0005\bqj\t\t\u0011\"\u0011z\u0003!!xn\u0015;sS:<G#A&\b\u000fm\\\u0011\u0011!E\u0001y\u0006\u0001RK\u0015'F]\u000e|G-\u001a3TiJLgn\u001a\t\u0003ku4qaG\u0006\u0002\u0002#\u0005apE\u0002~\u007f\u000e\u0002b!!\u0001\u0002\b%\"TBAA\u0002\u0015\r\t)\u0001E\u0001\beVtG/[7f\u0013\u0011\tI!a\u0001\u0003#\u0005\u00137\u000f\u001e:bGR4UO\\2uS>t\u0017\u0007\u0003\u0004\u0019{\u0012\u0005\u0011Q\u0002\u000b\u0002y\"9\u00010`A\u0001\n\u000bJ\b\"CA\n{\u0006\u0005I\u0011QA\u000b\u0003\u0015\t\u0007\u000f\u001d7z)\r!\u0014q\u0003\u0005\u0007O\u0005E\u0001\u0019A\u0015\t\u0013\u0005mQ0!A\u0005\u0002\u0006u\u0011aB;oCB\u0004H.\u001f\u000b\u0005\u0003?\t)\u0003\u0005\u0003\u0010\u0003CI\u0013bAA\u0012!\t1q\n\u001d;j_:D\u0011\"a\n\u0002\u001a\u0005\u0005\t\u0019\u0001\u001b\u0002\u0007a$\u0003\u0007C\u0005\u0002,u\f\t\u0011\"\u0003\u0002.\u0005Y!/Z1e%\u0016\u001cx\u000e\u001c<f)\t\ty\u0003E\u0002M\u0003cI1!a\rN\u0005\u0019y%M[3di\"I\u0011qG?\u0002\u0002\u0013\u0015\u0011\u0011H\u0001\u000fG>\u0004\u0018\u0010J3yi\u0016t7/[8o)\u0011\tY$a\u0010\u0015\u0007Q\ni\u0004\u0003\u0005(\u0003k\u0001\n\u00111\u0001*\u0011\u001d\t\t%!\u000eA\u0002Q\nQ\u0001\n;iSND\u0011\"!\u0012~#\u0003%)!a\u0012\u00021\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%c\u0011*\u0007\u0010^3og&|g\u000eF\u0002?\u0003\u0013Bq!!\u0011\u0002D\u0001\u0007A\u0007C\u0005\u0002Nu\f\t\u0011\"\u0002\u0002P\u00059\u0002O]8ek\u000e$\bK]3gSb$S\r\u001f;f]NLwN\u001c\u000b\u0004\u0017\u0006E\u0003bBA!\u0003\u0017\u0002\r\u0001\u000e\u0005\n\u0003+j\u0018\u0011!C\u0003\u0003/\na\u0003\u001d:pIV\u001cG/\u0011:jif$S\r\u001f;f]NLwN\u001c\u000b\u0004+\u0006e\u0003bBA!\u0003'\u0002\r\u0001\u000e\u0005\n\u0003;j\u0018\u0011!C\u0003\u0003?\n\u0001\u0004\u001d:pIV\u001cG/\u00127f[\u0016tG\u000fJ3yi\u0016t7/[8o)\u0011\t\t'!\u001a\u0015\u0007m\u000b\u0019\u0007\u0003\u0005`\u00037\n\t\u00111\u0001V\u0011\u001d\t\t%a\u0017A\u0002QB\u0011\"!\u001b~\u0003\u0003%)!a\u001b\u00023A\u0014x\u000eZ;di&#XM]1u_J$S\r\u001f;f]NLwN\u001c\u000b\u0004G\u00065\u0004bBA!\u0003O\u0002\r\u0001\u000e\u0005\n\u0003cj\u0018\u0011!C\u0003\u0003g\n!cY1o\u000bF,\u0018\r\u001c\u0013fqR,gn]5p]R!\u0011QOA=)\ra\u0017q\u000f\u0005\t?\u0006=\u0014\u0011!a\u00017\"9\u0011\u0011IA8\u0001\u0004!\u0004\"CA?{\u0006\u0005IQAA@\u0003IA\u0017m\u001d5D_\u0012,G%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0007I\f\t\tC\u0004\u0002B\u0005m\u0004\u0019\u0001\u001b\t\u0013\u0005\u0015U0!A\u0005\u0006\u0005\u001d\u0015\u0001E3rk\u0006d7\u000fJ3yi\u0016t7/[8o)\u0011\tI)!$\u0015\u00071\fY\t\u0003\u0005`\u0003\u0007\u000b\t\u00111\u0001\\\u0011\u001d\t\t%a!A\u0002QB\u0011\"!%~\u0003\u0003%)!a%\u0002%Q|7\u000b\u001e:j]\u001e$S\r\u001f;f]NLwN\u001c\u000b\u0004s\u0006U\u0005bBA!\u0003\u001f\u0003\r\u0001\u000e")
public final class StringCodec
{
    public static <A, B> Injection<A, B> fromImplicitBijection(final ImplicitBijection<A, B> bij) {
        return StringCodec$.MODULE$.fromImplicitBijection(bij);
    }
    
    public static <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W> Injection<Tuple22<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V>, List<W>> tuple22ToList(final Injection<A, W> ba, final Injection<B, W> bb, final Injection<C, W> bc, final Injection<D, W> bd, final Injection<E, W> be, final Injection<F, W> bf, final Injection<G, W> bg, final Injection<H, W> bh, final Injection<I, W> bi, final Injection<J, W> bj, final Injection<K, W> bk, final Injection<L, W> bl, final Injection<M, W> bm, final Injection<N, W> bn, final Injection<O, W> bo, final Injection<P, W> bp, final Injection<Q, W> bq, final Injection<R, W> br, final Injection<S, W> bs, final Injection<T, W> bt, final Injection<U, W> bu, final Injection<V, W> bv) {
        return StringCodec$.MODULE$.tuple22ToList(ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq, br, bs, bt, bu, bv);
    }
    
    public static <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V> Injection<Tuple21<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U>, List<V>> tuple21ToList(final Injection<A, V> ba, final Injection<B, V> bb, final Injection<C, V> bc, final Injection<D, V> bd, final Injection<E, V> be, final Injection<F, V> bf, final Injection<G, V> bg, final Injection<H, V> bh, final Injection<I, V> bi, final Injection<J, V> bj, final Injection<K, V> bk, final Injection<L, V> bl, final Injection<M, V> bm, final Injection<N, V> bn, final Injection<O, V> bo, final Injection<P, V> bp, final Injection<Q, V> bq, final Injection<R, V> br, final Injection<S, V> bs, final Injection<T, V> bt, final Injection<U, V> bu) {
        return StringCodec$.MODULE$.tuple21ToList(ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq, br, bs, bt, bu);
    }
    
    public static <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U> Injection<Tuple20<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T>, List<U>> tuple20ToList(final Injection<A, U> ba, final Injection<B, U> bb, final Injection<C, U> bc, final Injection<D, U> bd, final Injection<E, U> be, final Injection<F, U> bf, final Injection<G, U> bg, final Injection<H, U> bh, final Injection<I, U> bi, final Injection<J, U> bj, final Injection<K, U> bk, final Injection<L, U> bl, final Injection<M, U> bm, final Injection<N, U> bn, final Injection<O, U> bo, final Injection<P, U> bp, final Injection<Q, U> bq, final Injection<R, U> br, final Injection<S, U> bs, final Injection<T, U> bt) {
        return StringCodec$.MODULE$.tuple20ToList(ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq, br, bs, bt);
    }
    
    public static <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T> Injection<Tuple19<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S>, List<T>> tuple19ToList(final Injection<A, T> ba, final Injection<B, T> bb, final Injection<C, T> bc, final Injection<D, T> bd, final Injection<E, T> be, final Injection<F, T> bf, final Injection<G, T> bg, final Injection<H, T> bh, final Injection<I, T> bi, final Injection<J, T> bj, final Injection<K, T> bk, final Injection<L, T> bl, final Injection<M, T> bm, final Injection<N, T> bn, final Injection<O, T> bo, final Injection<P, T> bp, final Injection<Q, T> bq, final Injection<R, T> br, final Injection<S, T> bs) {
        return StringCodec$.MODULE$.tuple19ToList(ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq, br, bs);
    }
    
    public static <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S> Injection<Tuple18<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R>, List<S>> tuple18ToList(final Injection<A, S> ba, final Injection<B, S> bb, final Injection<C, S> bc, final Injection<D, S> bd, final Injection<E, S> be, final Injection<F, S> bf, final Injection<G, S> bg, final Injection<H, S> bh, final Injection<I, S> bi, final Injection<J, S> bj, final Injection<K, S> bk, final Injection<L, S> bl, final Injection<M, S> bm, final Injection<N, S> bn, final Injection<O, S> bo, final Injection<P, S> bp, final Injection<Q, S> bq, final Injection<R, S> br) {
        return StringCodec$.MODULE$.tuple18ToList(ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq, br);
    }
    
    public static <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R> Injection<Tuple17<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q>, List<R>> tuple17ToList(final Injection<A, R> ba, final Injection<B, R> bb, final Injection<C, R> bc, final Injection<D, R> bd, final Injection<E, R> be, final Injection<F, R> bf, final Injection<G, R> bg, final Injection<H, R> bh, final Injection<I, R> bi, final Injection<J, R> bj, final Injection<K, R> bk, final Injection<L, R> bl, final Injection<M, R> bm, final Injection<N, R> bn, final Injection<O, R> bo, final Injection<P, R> bp, final Injection<Q, R> bq) {
        return StringCodec$.MODULE$.tuple17ToList(ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq);
    }
    
    public static <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q> Injection<Tuple16<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P>, List<Q>> tuple16ToList(final Injection<A, Q> ba, final Injection<B, Q> bb, final Injection<C, Q> bc, final Injection<D, Q> bd, final Injection<E, Q> be, final Injection<F, Q> bf, final Injection<G, Q> bg, final Injection<H, Q> bh, final Injection<I, Q> bi, final Injection<J, Q> bj, final Injection<K, Q> bk, final Injection<L, Q> bl, final Injection<M, Q> bm, final Injection<N, Q> bn, final Injection<O, Q> bo, final Injection<P, Q> bp) {
        return StringCodec$.MODULE$.tuple16ToList(ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp);
    }
    
    public static <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P> Injection<Tuple15<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O>, List<P>> tuple15ToList(final Injection<A, P> ba, final Injection<B, P> bb, final Injection<C, P> bc, final Injection<D, P> bd, final Injection<E, P> be, final Injection<F, P> bf, final Injection<G, P> bg, final Injection<H, P> bh, final Injection<I, P> bi, final Injection<J, P> bj, final Injection<K, P> bk, final Injection<L, P> bl, final Injection<M, P> bm, final Injection<N, P> bn, final Injection<O, P> bo) {
        return StringCodec$.MODULE$.tuple15ToList(ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo);
    }
    
    public static <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O> Injection<Tuple14<A, B, C, D, E, F, G, H, I, J, K, L, M, N>, List<O>> tuple14ToList(final Injection<A, O> ba, final Injection<B, O> bb, final Injection<C, O> bc, final Injection<D, O> bd, final Injection<E, O> be, final Injection<F, O> bf, final Injection<G, O> bg, final Injection<H, O> bh, final Injection<I, O> bi, final Injection<J, O> bj, final Injection<K, O> bk, final Injection<L, O> bl, final Injection<M, O> bm, final Injection<N, O> bn) {
        return StringCodec$.MODULE$.tuple14ToList(ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn);
    }
    
    public static <A, B, C, D, E, F, G, H, I, J, K, L, M, N> Injection<Tuple13<A, B, C, D, E, F, G, H, I, J, K, L, M>, List<N>> tuple13ToList(final Injection<A, N> ba, final Injection<B, N> bb, final Injection<C, N> bc, final Injection<D, N> bd, final Injection<E, N> be, final Injection<F, N> bf, final Injection<G, N> bg, final Injection<H, N> bh, final Injection<I, N> bi, final Injection<J, N> bj, final Injection<K, N> bk, final Injection<L, N> bl, final Injection<M, N> bm) {
        return StringCodec$.MODULE$.tuple13ToList(ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm);
    }
    
    public static <A, B, C, D, E, F, G, H, I, J, K, L, M> Injection<Tuple12<A, B, C, D, E, F, G, H, I, J, K, L>, List<M>> tuple12ToList(final Injection<A, M> ba, final Injection<B, M> bb, final Injection<C, M> bc, final Injection<D, M> bd, final Injection<E, M> be, final Injection<F, M> bf, final Injection<G, M> bg, final Injection<H, M> bh, final Injection<I, M> bi, final Injection<J, M> bj, final Injection<K, M> bk, final Injection<L, M> bl) {
        return StringCodec$.MODULE$.tuple12ToList(ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl);
    }
    
    public static <A, B, C, D, E, F, G, H, I, J, K, L> Injection<Tuple11<A, B, C, D, E, F, G, H, I, J, K>, List<L>> tuple11ToList(final Injection<A, L> ba, final Injection<B, L> bb, final Injection<C, L> bc, final Injection<D, L> bd, final Injection<E, L> be, final Injection<F, L> bf, final Injection<G, L> bg, final Injection<H, L> bh, final Injection<I, L> bi, final Injection<J, L> bj, final Injection<K, L> bk) {
        return StringCodec$.MODULE$.tuple11ToList(ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk);
    }
    
    public static <A, B, C, D, E, F, G, H, I, J, K> Injection<Tuple10<A, B, C, D, E, F, G, H, I, J>, List<K>> tuple10ToList(final Injection<A, K> ba, final Injection<B, K> bb, final Injection<C, K> bc, final Injection<D, K> bd, final Injection<E, K> be, final Injection<F, K> bf, final Injection<G, K> bg, final Injection<H, K> bh, final Injection<I, K> bi, final Injection<J, K> bj) {
        return StringCodec$.MODULE$.tuple10ToList(ba, bb, bc, bd, be, bf, bg, bh, bi, bj);
    }
    
    public static <A, B, C, D, E, F, G, H, I, J> Injection<Tuple9<A, B, C, D, E, F, G, H, I>, List<J>> tuple9ToList(final Injection<A, J> ba, final Injection<B, J> bb, final Injection<C, J> bc, final Injection<D, J> bd, final Injection<E, J> be, final Injection<F, J> bf, final Injection<G, J> bg, final Injection<H, J> bh, final Injection<I, J> bi) {
        return StringCodec$.MODULE$.tuple9ToList(ba, bb, bc, bd, be, bf, bg, bh, bi);
    }
    
    public static <A, B, C, D, E, F, G, H, I> Injection<Tuple8<A, B, C, D, E, F, G, H>, List<I>> tuple8ToList(final Injection<A, I> ba, final Injection<B, I> bb, final Injection<C, I> bc, final Injection<D, I> bd, final Injection<E, I> be, final Injection<F, I> bf, final Injection<G, I> bg, final Injection<H, I> bh) {
        return StringCodec$.MODULE$.tuple8ToList(ba, bb, bc, bd, be, bf, bg, bh);
    }
    
    public static <A, B, C, D, E, F, G, H> Injection<Tuple7<A, B, C, D, E, F, G>, List<H>> tuple7ToList(final Injection<A, H> ba, final Injection<B, H> bb, final Injection<C, H> bc, final Injection<D, H> bd, final Injection<E, H> be, final Injection<F, H> bf, final Injection<G, H> bg) {
        return StringCodec$.MODULE$.tuple7ToList(ba, bb, bc, bd, be, bf, bg);
    }
    
    public static <A, B, C, D, E, F, G> Injection<Tuple6<A, B, C, D, E, F>, List<G>> tuple6ToList(final Injection<A, G> ba, final Injection<B, G> bb, final Injection<C, G> bc, final Injection<D, G> bd, final Injection<E, G> be, final Injection<F, G> bf) {
        return StringCodec$.MODULE$.tuple6ToList(ba, bb, bc, bd, be, bf);
    }
    
    public static <A, B, C, D, E, F> Injection<Tuple5<A, B, C, D, E>, List<F>> tuple5ToList(final Injection<A, F> ba, final Injection<B, F> bb, final Injection<C, F> bc, final Injection<D, F> bd, final Injection<E, F> be) {
        return StringCodec$.MODULE$.tuple5ToList(ba, bb, bc, bd, be);
    }
    
    public static <A, B, C, D, E> Injection<Tuple4<A, B, C, D>, List<E>> tuple4ToList(final Injection<A, E> ba, final Injection<B, E> bb, final Injection<C, E> bc, final Injection<D, E> bd) {
        return StringCodec$.MODULE$.tuple4ToList(ba, bb, bc, bd);
    }
    
    public static <A, B, C, D> Injection<Tuple3<A, B, C>, List<D>> tuple3ToList(final Injection<A, D> ba, final Injection<B, D> bb, final Injection<C, D> bc) {
        return StringCodec$.MODULE$.tuple3ToList(ba, bb, bc);
    }
    
    public static <A, B, C> Injection<Tuple2<A, B>, List<C>> tuple2ToList(final Injection<A, C> ba, final Injection<B, C> bb) {
        return StringCodec$.MODULE$.tuple2ToList(ba, bb);
    }
    
    public static <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, V1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2, S2, T2, U2, V2> Injection<Tuple22<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, V1>, Tuple22<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2, S2, T2, U2, V2>> tuple22(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd, final Injection<E1, E2> be, final Injection<F1, F2> bf, final Injection<G1, G2> bg, final Injection<H1, H2> bh, final Injection<I1, I2> bi, final Injection<J1, J2> bj, final Injection<K1, K2> bk, final Injection<L1, L2> bl, final Injection<M1, M2> bm, final Injection<N1, N2> bn, final Injection<O1, O2> bo, final Injection<P1, P2> bp, final Injection<Q1, Q2> bq, final Injection<R1, R2> br, final Injection<S1, S2> bs, final Injection<T1, T2> bt, final Injection<U1, U2> bu, final Injection<V1, V2> bv) {
        return StringCodec$.MODULE$.tuple22(ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq, br, bs, bt, bu, bv);
    }
    
    public static <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2, S2, T2, U2> Injection<Tuple21<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1>, Tuple21<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2, S2, T2, U2>> tuple21(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd, final Injection<E1, E2> be, final Injection<F1, F2> bf, final Injection<G1, G2> bg, final Injection<H1, H2> bh, final Injection<I1, I2> bi, final Injection<J1, J2> bj, final Injection<K1, K2> bk, final Injection<L1, L2> bl, final Injection<M1, M2> bm, final Injection<N1, N2> bn, final Injection<O1, O2> bo, final Injection<P1, P2> bp, final Injection<Q1, Q2> bq, final Injection<R1, R2> br, final Injection<S1, S2> bs, final Injection<T1, T2> bt, final Injection<U1, U2> bu) {
        return StringCodec$.MODULE$.tuple21(ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq, br, bs, bt, bu);
    }
    
    public static <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2, S2, T2> Injection<Tuple20<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1>, Tuple20<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2, S2, T2>> tuple20(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd, final Injection<E1, E2> be, final Injection<F1, F2> bf, final Injection<G1, G2> bg, final Injection<H1, H2> bh, final Injection<I1, I2> bi, final Injection<J1, J2> bj, final Injection<K1, K2> bk, final Injection<L1, L2> bl, final Injection<M1, M2> bm, final Injection<N1, N2> bn, final Injection<O1, O2> bo, final Injection<P1, P2> bp, final Injection<Q1, Q2> bq, final Injection<R1, R2> br, final Injection<S1, S2> bs, final Injection<T1, T2> bt) {
        return StringCodec$.MODULE$.tuple20(ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq, br, bs, bt);
    }
    
    public static <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2, S2> Injection<Tuple19<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1>, Tuple19<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2, S2>> tuple19(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd, final Injection<E1, E2> be, final Injection<F1, F2> bf, final Injection<G1, G2> bg, final Injection<H1, H2> bh, final Injection<I1, I2> bi, final Injection<J1, J2> bj, final Injection<K1, K2> bk, final Injection<L1, L2> bl, final Injection<M1, M2> bm, final Injection<N1, N2> bn, final Injection<O1, O2> bo, final Injection<P1, P2> bp, final Injection<Q1, Q2> bq, final Injection<R1, R2> br, final Injection<S1, S2> bs) {
        return StringCodec$.MODULE$.tuple19(ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq, br, bs);
    }
    
    public static <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2> Injection<Tuple18<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1>, Tuple18<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2>> tuple18(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd, final Injection<E1, E2> be, final Injection<F1, F2> bf, final Injection<G1, G2> bg, final Injection<H1, H2> bh, final Injection<I1, I2> bi, final Injection<J1, J2> bj, final Injection<K1, K2> bk, final Injection<L1, L2> bl, final Injection<M1, M2> bm, final Injection<N1, N2> bn, final Injection<O1, O2> bo, final Injection<P1, P2> bp, final Injection<Q1, Q2> bq, final Injection<R1, R2> br) {
        return StringCodec$.MODULE$.tuple18(ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq, br);
    }
    
    public static <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2> Injection<Tuple17<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1>, Tuple17<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2>> tuple17(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd, final Injection<E1, E2> be, final Injection<F1, F2> bf, final Injection<G1, G2> bg, final Injection<H1, H2> bh, final Injection<I1, I2> bi, final Injection<J1, J2> bj, final Injection<K1, K2> bk, final Injection<L1, L2> bl, final Injection<M1, M2> bm, final Injection<N1, N2> bn, final Injection<O1, O2> bo, final Injection<P1, P2> bp, final Injection<Q1, Q2> bq) {
        return StringCodec$.MODULE$.tuple17(ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq);
    }
    
    public static <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2> Injection<Tuple16<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1>, Tuple16<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2>> tuple16(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd, final Injection<E1, E2> be, final Injection<F1, F2> bf, final Injection<G1, G2> bg, final Injection<H1, H2> bh, final Injection<I1, I2> bi, final Injection<J1, J2> bj, final Injection<K1, K2> bk, final Injection<L1, L2> bl, final Injection<M1, M2> bm, final Injection<N1, N2> bn, final Injection<O1, O2> bo, final Injection<P1, P2> bp) {
        return StringCodec$.MODULE$.tuple16(ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp);
    }
    
    public static <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2> Injection<Tuple15<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1>, Tuple15<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2>> tuple15(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd, final Injection<E1, E2> be, final Injection<F1, F2> bf, final Injection<G1, G2> bg, final Injection<H1, H2> bh, final Injection<I1, I2> bi, final Injection<J1, J2> bj, final Injection<K1, K2> bk, final Injection<L1, L2> bl, final Injection<M1, M2> bm, final Injection<N1, N2> bn, final Injection<O1, O2> bo) {
        return StringCodec$.MODULE$.tuple15(ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo);
    }
    
    public static <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2> Injection<Tuple14<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1>, Tuple14<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2>> tuple14(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd, final Injection<E1, E2> be, final Injection<F1, F2> bf, final Injection<G1, G2> bg, final Injection<H1, H2> bh, final Injection<I1, I2> bi, final Injection<J1, J2> bj, final Injection<K1, K2> bk, final Injection<L1, L2> bl, final Injection<M1, M2> bm, final Injection<N1, N2> bn) {
        return StringCodec$.MODULE$.tuple14(ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn);
    }
    
    public static <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2> Injection<Tuple13<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1>, Tuple13<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2>> tuple13(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd, final Injection<E1, E2> be, final Injection<F1, F2> bf, final Injection<G1, G2> bg, final Injection<H1, H2> bh, final Injection<I1, I2> bi, final Injection<J1, J2> bj, final Injection<K1, K2> bk, final Injection<L1, L2> bl, final Injection<M1, M2> bm) {
        return StringCodec$.MODULE$.tuple13(ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm);
    }
    
    public static <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2> Injection<Tuple12<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1>, Tuple12<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2>> tuple12(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd, final Injection<E1, E2> be, final Injection<F1, F2> bf, final Injection<G1, G2> bg, final Injection<H1, H2> bh, final Injection<I1, I2> bi, final Injection<J1, J2> bj, final Injection<K1, K2> bk, final Injection<L1, L2> bl) {
        return StringCodec$.MODULE$.tuple12(ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl);
    }
    
    public static <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2> Injection<Tuple11<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1>, Tuple11<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2>> tuple11(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd, final Injection<E1, E2> be, final Injection<F1, F2> bf, final Injection<G1, G2> bg, final Injection<H1, H2> bh, final Injection<I1, I2> bi, final Injection<J1, J2> bj, final Injection<K1, K2> bk) {
        return StringCodec$.MODULE$.tuple11(ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk);
    }
    
    public static <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2> Injection<Tuple10<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1>, Tuple10<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2>> tuple10(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd, final Injection<E1, E2> be, final Injection<F1, F2> bf, final Injection<G1, G2> bg, final Injection<H1, H2> bh, final Injection<I1, I2> bi, final Injection<J1, J2> bj) {
        return StringCodec$.MODULE$.tuple10(ba, bb, bc, bd, be, bf, bg, bh, bi, bj);
    }
    
    public static <A1, B1, C1, D1, E1, F1, G1, H1, I1, A2, B2, C2, D2, E2, F2, G2, H2, I2> Injection<Tuple9<A1, B1, C1, D1, E1, F1, G1, H1, I1>, Tuple9<A2, B2, C2, D2, E2, F2, G2, H2, I2>> tuple9(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd, final Injection<E1, E2> be, final Injection<F1, F2> bf, final Injection<G1, G2> bg, final Injection<H1, H2> bh, final Injection<I1, I2> bi) {
        return StringCodec$.MODULE$.tuple9(ba, bb, bc, bd, be, bf, bg, bh, bi);
    }
    
    public static <A1, B1, C1, D1, E1, F1, G1, H1, A2, B2, C2, D2, E2, F2, G2, H2> Injection<Tuple8<A1, B1, C1, D1, E1, F1, G1, H1>, Tuple8<A2, B2, C2, D2, E2, F2, G2, H2>> tuple8(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd, final Injection<E1, E2> be, final Injection<F1, F2> bf, final Injection<G1, G2> bg, final Injection<H1, H2> bh) {
        return StringCodec$.MODULE$.tuple8(ba, bb, bc, bd, be, bf, bg, bh);
    }
    
    public static <A1, B1, C1, D1, E1, F1, G1, A2, B2, C2, D2, E2, F2, G2> Injection<Tuple7<A1, B1, C1, D1, E1, F1, G1>, Tuple7<A2, B2, C2, D2, E2, F2, G2>> tuple7(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd, final Injection<E1, E2> be, final Injection<F1, F2> bf, final Injection<G1, G2> bg) {
        return StringCodec$.MODULE$.tuple7(ba, bb, bc, bd, be, bf, bg);
    }
    
    public static <A1, B1, C1, D1, E1, F1, A2, B2, C2, D2, E2, F2> Injection<Tuple6<A1, B1, C1, D1, E1, F1>, Tuple6<A2, B2, C2, D2, E2, F2>> tuple6(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd, final Injection<E1, E2> be, final Injection<F1, F2> bf) {
        return StringCodec$.MODULE$.tuple6(ba, bb, bc, bd, be, bf);
    }
    
    public static <A1, B1, C1, D1, E1, A2, B2, C2, D2, E2> Injection<Tuple5<A1, B1, C1, D1, E1>, Tuple5<A2, B2, C2, D2, E2>> tuple5(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd, final Injection<E1, E2> be) {
        return StringCodec$.MODULE$.tuple5(ba, bb, bc, bd, be);
    }
    
    public static <A1, B1, C1, D1, A2, B2, C2, D2> Injection<Tuple4<A1, B1, C1, D1>, Tuple4<A2, B2, C2, D2>> tuple4(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd) {
        return StringCodec$.MODULE$.tuple4(ba, bb, bc, bd);
    }
    
    public static <A1, B1, C1, A2, B2, C2> Injection<Tuple3<A1, B1, C1>, Tuple3<A2, B2, C2>> tuple3(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc) {
        return StringCodec$.MODULE$.tuple3(ba, bb, bc);
    }
    
    public static <A1, B1, A2, B2> Injection<Tuple2<A1, B1>, Tuple2<A2, B2>> tuple2(final Injection<A1, A2> ba, final Injection<B1, B2> bb) {
        return StringCodec$.MODULE$.tuple2(ba, bb);
    }
    
    public static void com$twitter$bijection$NumericInjections$_setter_$long2BigEndian_$eq(final Injection x$1) {
        StringCodec$.MODULE$.com$twitter$bijection$NumericInjections$_setter_$long2BigEndian_$eq(x$1);
    }
    
    public static void com$twitter$bijection$NumericInjections$_setter_$int2BigEndian_$eq(final Injection x$1) {
        StringCodec$.MODULE$.com$twitter$bijection$NumericInjections$_setter_$int2BigEndian_$eq(x$1);
    }
    
    public static void com$twitter$bijection$NumericInjections$_setter_$short2BigEndian_$eq(final Injection x$1) {
        StringCodec$.MODULE$.com$twitter$bijection$NumericInjections$_setter_$short2BigEndian_$eq(x$1);
    }
    
    public static void com$twitter$bijection$NumericInjections$_setter_$jdouble2String_$eq(final Injection x$1) {
        StringCodec$.MODULE$.com$twitter$bijection$NumericInjections$_setter_$jdouble2String_$eq(x$1);
    }
    
    public static void com$twitter$bijection$NumericInjections$_setter_$double2String_$eq(final Injection x$1) {
        StringCodec$.MODULE$.com$twitter$bijection$NumericInjections$_setter_$double2String_$eq(x$1);
    }
    
    public static void com$twitter$bijection$NumericInjections$_setter_$jfloat2String_$eq(final Injection x$1) {
        StringCodec$.MODULE$.com$twitter$bijection$NumericInjections$_setter_$jfloat2String_$eq(x$1);
    }
    
    public static void com$twitter$bijection$NumericInjections$_setter_$float2String_$eq(final Injection x$1) {
        StringCodec$.MODULE$.com$twitter$bijection$NumericInjections$_setter_$float2String_$eq(x$1);
    }
    
    public static void com$twitter$bijection$NumericInjections$_setter_$jlong2String_$eq(final Injection x$1) {
        StringCodec$.MODULE$.com$twitter$bijection$NumericInjections$_setter_$jlong2String_$eq(x$1);
    }
    
    public static void com$twitter$bijection$NumericInjections$_setter_$long2String_$eq(final Injection x$1) {
        StringCodec$.MODULE$.com$twitter$bijection$NumericInjections$_setter_$long2String_$eq(x$1);
    }
    
    public static void com$twitter$bijection$NumericInjections$_setter_$jint2String_$eq(final Injection x$1) {
        StringCodec$.MODULE$.com$twitter$bijection$NumericInjections$_setter_$jint2String_$eq(x$1);
    }
    
    public static void com$twitter$bijection$NumericInjections$_setter_$int2String_$eq(final Injection x$1) {
        StringCodec$.MODULE$.com$twitter$bijection$NumericInjections$_setter_$int2String_$eq(x$1);
    }
    
    public static void com$twitter$bijection$NumericInjections$_setter_$jshort2String_$eq(final Injection x$1) {
        StringCodec$.MODULE$.com$twitter$bijection$NumericInjections$_setter_$jshort2String_$eq(x$1);
    }
    
    public static void com$twitter$bijection$NumericInjections$_setter_$short2String_$eq(final Injection x$1) {
        StringCodec$.MODULE$.com$twitter$bijection$NumericInjections$_setter_$short2String_$eq(x$1);
    }
    
    public static void com$twitter$bijection$NumericInjections$_setter_$jbyte2String_$eq(final Injection x$1) {
        StringCodec$.MODULE$.com$twitter$bijection$NumericInjections$_setter_$jbyte2String_$eq(x$1);
    }
    
    public static void com$twitter$bijection$NumericInjections$_setter_$byte2String_$eq(final Injection x$1) {
        StringCodec$.MODULE$.com$twitter$bijection$NumericInjections$_setter_$byte2String_$eq(x$1);
    }
    
    public static void com$twitter$bijection$NumericInjections$_setter_$int2Double_$eq(final Injection x$1) {
        StringCodec$.MODULE$.com$twitter$bijection$NumericInjections$_setter_$int2Double_$eq(x$1);
    }
    
    public static void com$twitter$bijection$NumericInjections$_setter_$float2Double_$eq(final Injection x$1) {
        StringCodec$.MODULE$.com$twitter$bijection$NumericInjections$_setter_$float2Double_$eq(x$1);
    }
    
    public static void com$twitter$bijection$NumericInjections$_setter_$long2BigInt_$eq(final Injection x$1) {
        StringCodec$.MODULE$.com$twitter$bijection$NumericInjections$_setter_$long2BigInt_$eq(x$1);
    }
    
    public static void com$twitter$bijection$NumericInjections$_setter_$int2Long_$eq(final Injection x$1) {
        StringCodec$.MODULE$.com$twitter$bijection$NumericInjections$_setter_$int2Long_$eq(x$1);
    }
    
    public static void com$twitter$bijection$NumericInjections$_setter_$short2Int_$eq(final Injection x$1) {
        StringCodec$.MODULE$.com$twitter$bijection$NumericInjections$_setter_$short2Int_$eq(x$1);
    }
    
    public static void com$twitter$bijection$NumericInjections$_setter_$byte2Short_$eq(final Injection x$1) {
        StringCodec$.MODULE$.com$twitter$bijection$NumericInjections$_setter_$byte2Short_$eq(x$1);
    }
    
    public static Injection<Object, byte[]> double2BigEndian() {
        return StringCodec$.MODULE$.double2BigEndian();
    }
    
    public static Injection<Object, byte[]> float2BigEndian() {
        return StringCodec$.MODULE$.float2BigEndian();
    }
    
    public static Injection<Object, byte[]> long2BigEndian() {
        return StringCodec$.MODULE$.long2BigEndian();
    }
    
    public static Injection<Object, byte[]> int2BigEndian() {
        return StringCodec$.MODULE$.int2BigEndian();
    }
    
    public static Injection<Object, byte[]> short2BigEndian() {
        return StringCodec$.MODULE$.short2BigEndian();
    }
    
    public static Injection<Double, String> jdouble2String() {
        return StringCodec$.MODULE$.jdouble2String();
    }
    
    public static Injection<Object, String> double2String() {
        return StringCodec$.MODULE$.double2String();
    }
    
    public static Injection<Float, String> jfloat2String() {
        return StringCodec$.MODULE$.jfloat2String();
    }
    
    public static Injection<Object, String> float2String() {
        return StringCodec$.MODULE$.float2String();
    }
    
    public static Injection<Long, String> jlong2String() {
        return StringCodec$.MODULE$.jlong2String();
    }
    
    public static Injection<Object, String> long2String() {
        return StringCodec$.MODULE$.long2String();
    }
    
    public static Injection<Integer, String> jint2String() {
        return StringCodec$.MODULE$.jint2String();
    }
    
    public static Injection<Object, String> int2String() {
        return StringCodec$.MODULE$.int2String();
    }
    
    public static Injection<Short, String> jshort2String() {
        return StringCodec$.MODULE$.jshort2String();
    }
    
    public static Injection<Object, String> short2String() {
        return StringCodec$.MODULE$.short2String();
    }
    
    public static Injection<Byte, String> jbyte2String() {
        return StringCodec$.MODULE$.jbyte2String();
    }
    
    public static Injection<Object, String> byte2String() {
        return StringCodec$.MODULE$.byte2String();
    }
    
    public static Injection<Object, Object> int2Double() {
        return StringCodec$.MODULE$.int2Double();
    }
    
    public static Injection<Object, Object> float2Double() {
        return StringCodec$.MODULE$.float2Double();
    }
    
    public static Injection<Object, BigInt> long2BigInt() {
        return StringCodec$.MODULE$.long2BigInt();
    }
    
    public static Injection<Object, Object> int2Long() {
        return StringCodec$.MODULE$.int2Long();
    }
    
    public static Injection<Object, Object> short2Int() {
        return StringCodec$.MODULE$.short2Int();
    }
    
    public static Injection<Object, Object> byte2Short() {
        return StringCodec$.MODULE$.byte2Short();
    }
    
    public static Injection<String, byte[]> withEncoding(final String encoding) {
        return StringCodec$.MODULE$.withEncoding(encoding);
    }
    
    public static void com$twitter$bijection$StringInjections$_setter_$string2UrlEncodedString_$eq(final Injection x$1) {
        StringCodec$.MODULE$.com$twitter$bijection$StringInjections$_setter_$string2UrlEncodedString_$eq(x$1);
    }
    
    public static void com$twitter$bijection$StringInjections$_setter_$uuid2String_$eq(final Injection x$1) {
        StringCodec$.MODULE$.com$twitter$bijection$StringInjections$_setter_$uuid2String_$eq(x$1);
    }
    
    public static void com$twitter$bijection$StringInjections$_setter_$url2String_$eq(final Injection x$1) {
        StringCodec$.MODULE$.com$twitter$bijection$StringInjections$_setter_$url2String_$eq(x$1);
    }
    
    public static void com$twitter$bijection$StringInjections$_setter_$utf8_$eq(final Injection x$1) {
        StringCodec$.MODULE$.com$twitter$bijection$StringInjections$_setter_$utf8_$eq(x$1);
    }
    
    public static Injection<String, String> string2UrlEncodedString() {
        return StringCodec$.MODULE$.string2UrlEncodedString();
    }
    
    public static Injection<UUID, String> uuid2String() {
        return StringCodec$.MODULE$.uuid2String();
    }
    
    public static Injection<URL, String> url2String() {
        return StringCodec$.MODULE$.url2String();
    }
    
    public static Injection<String, byte[]> utf8() {
        return StringCodec$.MODULE$.utf8();
    }
    
    public static final class URLEncodedString implements Product, Serializable
    {
        private final String encodedString;
        
        public String encodedString() {
            return this.encodedString;
        }
        
        public String copy(final String encodedString) {
            return URLEncodedString$.MODULE$.copy$extension(this.encodedString(), encodedString);
        }
        
        public String copy$default$1() {
            return URLEncodedString$.MODULE$.copy$default$1$extension(this.encodedString());
        }
        
        @Override
        public String productPrefix() {
            return URLEncodedString$.MODULE$.productPrefix$extension(this.encodedString());
        }
        
        @Override
        public int productArity() {
            return URLEncodedString$.MODULE$.productArity$extension(this.encodedString());
        }
        
        @Override
        public Object productElement(final int x$1) {
            return URLEncodedString$.MODULE$.productElement$extension(this.encodedString(), x$1);
        }
        
        @Override
        public Iterator<Object> productIterator() {
            return URLEncodedString$.MODULE$.productIterator$extension(this.encodedString());
        }
        
        @Override
        public boolean canEqual(final Object x$1) {
            return URLEncodedString$.MODULE$.canEqual$extension(this.encodedString(), x$1);
        }
        
        @Override
        public int hashCode() {
            return URLEncodedString$.MODULE$.hashCode$extension(this.encodedString());
        }
        
        @Override
        public boolean equals(final Object x$1) {
            return URLEncodedString$.MODULE$.equals$extension(this.encodedString(), x$1);
        }
        
        @Override
        public String toString() {
            return URLEncodedString$.MODULE$.toString$extension(this.encodedString());
        }
        
        public URLEncodedString(final String encodedString) {
            this.encodedString = encodedString;
            Product$class.$init$(this);
        }
    }
    
    public static class URLEncodedString$ extends AbstractFunction1<String, String> implements Serializable
    {
        public static final URLEncodedString$ MODULE$;
        
        static {
            new URLEncodedString$();
        }
        
        @Override
        public final String toString() {
            return "URLEncodedString";
        }
        
        @Override
        public String apply(final String encodedString) {
            return encodedString;
        }
        
        public Option<String> unapply(final String x$0) {
            return (Option<String>)((new URLEncodedString(x$0) == null) ? None$.MODULE$ : new Some<Object>(x$0));
        }
        
        private Object readResolve() {
            return URLEncodedString$.MODULE$;
        }
        
        public final String copy$extension(final String $this, final String encodedString) {
            return encodedString;
        }
        
        public final String copy$default$1$extension(final String $this) {
            return $this;
        }
        
        public final String productPrefix$extension(final String $this) {
            return "URLEncodedString";
        }
        
        public final int productArity$extension(final String $this) {
            return 1;
        }
        
        public final Object productElement$extension(final String $this, final int x$1) {
            switch (x$1) {
                default: {
                    throw new IndexOutOfBoundsException(BoxesRunTime.boxToInteger(x$1).toString());
                }
                case 0: {
                    return $this;
                }
            }
        }
        
        public final Iterator<Object> productIterator$extension(final String $this) {
            return ScalaRunTime$.MODULE$.typedProductIterator(new URLEncodedString($this));
        }
        
        public final boolean canEqual$extension(final String $this, final Object x$1) {
            return x$1 instanceof String;
        }
        
        public final int hashCode$extension(final String $this) {
            return $this.hashCode();
        }
        
        public final boolean equals$extension(final String $this, final Object x$1) {
            if (x$1 instanceof URLEncodedString) {
                final String s = (x$1 == null) ? null : ((URLEncodedString)x$1).encodedString();
                boolean b = false;
                Label_0071: {
                    Label_0070: {
                        if ($this == null) {
                            if (s != null) {
                                break Label_0070;
                            }
                        }
                        else if (!$this.equals(s)) {
                            break Label_0070;
                        }
                        b = true;
                        break Label_0071;
                    }
                    b = false;
                }
                if (b) {
                    return true;
                }
            }
            return false;
        }
        
        public final String toString$extension(final String $this) {
            return ScalaRunTime$.MODULE$._toString(new URLEncodedString($this));
        }
        
        public URLEncodedString$() {
            MODULE$ = this;
        }
    }
}
