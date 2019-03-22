// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.collection.immutable.List;
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
import scala.runtime.BoxedUnit;
import scala.math.BigInt;
import java.util.UUID;
import java.net.URL;

public final class StringCodec$ implements StringInjections
{
    public static final StringCodec$ MODULE$;
    private final Injection<String, byte[]> utf8;
    private final Injection<URL, String> url2String;
    private final Injection<UUID, String> uuid2String;
    private final Injection<String, String> string2UrlEncodedString;
    private final Injection<Object, Object> byte2Short;
    private final Injection<Object, Object> short2Int;
    private final Injection<Object, Object> int2Long;
    private final Injection<Object, BigInt> long2BigInt;
    private final Injection<Object, Object> float2Double;
    private final Injection<Object, Object> int2Double;
    private final Injection<Object, String> byte2String;
    private final Injection<Byte, String> jbyte2String;
    private final Injection<Object, String> short2String;
    private final Injection<Short, String> jshort2String;
    private final Injection<Object, String> int2String;
    private final Injection<Integer, String> jint2String;
    private final Injection<Object, String> long2String;
    private final Injection<Long, String> jlong2String;
    private final Injection<Object, String> float2String;
    private final Injection<Float, String> jfloat2String;
    private final Injection<Object, String> double2String;
    private final Injection<Double, String> jdouble2String;
    private final Injection<Object, byte[]> short2BigEndian;
    private final Injection<Object, byte[]> int2BigEndian;
    private final Injection<Object, byte[]> long2BigEndian;
    private final Injection<Object, byte[]> float2BigEndian;
    private final Injection<Object, byte[]> double2BigEndian;
    private volatile byte bitmap$0;
    
    static {
        new StringCodec$();
    }
    
    @Override
    public Injection<String, byte[]> utf8() {
        return this.utf8;
    }
    
    @Override
    public Injection<URL, String> url2String() {
        return this.url2String;
    }
    
    @Override
    public Injection<UUID, String> uuid2String() {
        return this.uuid2String;
    }
    
    @Override
    public Injection<String, String> string2UrlEncodedString() {
        return this.string2UrlEncodedString;
    }
    
    @Override
    public void com$twitter$bijection$StringInjections$_setter_$utf8_$eq(final Injection x$1) {
        this.utf8 = (Injection<String, byte[]>)x$1;
    }
    
    @Override
    public void com$twitter$bijection$StringInjections$_setter_$url2String_$eq(final Injection x$1) {
        this.url2String = (Injection<URL, String>)x$1;
    }
    
    @Override
    public void com$twitter$bijection$StringInjections$_setter_$uuid2String_$eq(final Injection x$1) {
        this.uuid2String = (Injection<UUID, String>)x$1;
    }
    
    @Override
    public void com$twitter$bijection$StringInjections$_setter_$string2UrlEncodedString_$eq(final Injection x$1) {
        this.string2UrlEncodedString = (Injection<String, String>)x$1;
    }
    
    @Override
    public Injection<String, byte[]> withEncoding(final String encoding) {
        return (Injection<String, byte[]>)StringInjections$class.withEncoding(this, encoding);
    }
    
    @Override
    public Injection<Object, Object> byte2Short() {
        return this.byte2Short;
    }
    
    @Override
    public Injection<Object, Object> short2Int() {
        return this.short2Int;
    }
    
    @Override
    public Injection<Object, Object> int2Long() {
        return this.int2Long;
    }
    
    @Override
    public Injection<Object, BigInt> long2BigInt() {
        return this.long2BigInt;
    }
    
    @Override
    public Injection<Object, Object> float2Double() {
        return this.float2Double;
    }
    
    @Override
    public Injection<Object, Object> int2Double() {
        return this.int2Double;
    }
    
    @Override
    public Injection<Object, String> byte2String() {
        return this.byte2String;
    }
    
    @Override
    public Injection<Byte, String> jbyte2String() {
        return this.jbyte2String;
    }
    
    @Override
    public Injection<Object, String> short2String() {
        return this.short2String;
    }
    
    @Override
    public Injection<Short, String> jshort2String() {
        return this.jshort2String;
    }
    
    @Override
    public Injection<Object, String> int2String() {
        return this.int2String;
    }
    
    @Override
    public Injection<Integer, String> jint2String() {
        return this.jint2String;
    }
    
    @Override
    public Injection<Object, String> long2String() {
        return this.long2String;
    }
    
    @Override
    public Injection<Long, String> jlong2String() {
        return this.jlong2String;
    }
    
    @Override
    public Injection<Object, String> float2String() {
        return this.float2String;
    }
    
    @Override
    public Injection<Float, String> jfloat2String() {
        return this.jfloat2String;
    }
    
    @Override
    public Injection<Object, String> double2String() {
        return this.double2String;
    }
    
    @Override
    public Injection<Double, String> jdouble2String() {
        return this.jdouble2String;
    }
    
    @Override
    public Injection<Object, byte[]> short2BigEndian() {
        return this.short2BigEndian;
    }
    
    @Override
    public Injection<Object, byte[]> int2BigEndian() {
        return this.int2BigEndian;
    }
    
    @Override
    public Injection<Object, byte[]> long2BigEndian() {
        return this.long2BigEndian;
    }
    
    private Injection float2BigEndian$lzycompute() {
        synchronized (this) {
            if ((byte)(this.bitmap$0 & 0x1) == 0) {
                this.float2BigEndian = (Injection<Object, byte[]>)NumericInjections$class.float2BigEndian(this);
                this.bitmap$0 |= 0x1;
            }
            final BoxedUnit un\u0131t = BoxedUnit.UNIT;
            return this.float2BigEndian;
        }
    }
    
    @Override
    public Injection<Object, byte[]> float2BigEndian() {
        return ((byte)(this.bitmap$0 & 0x1) == 0) ? this.float2BigEndian$lzycompute() : this.float2BigEndian;
    }
    
    private Injection double2BigEndian$lzycompute() {
        synchronized (this) {
            if ((byte)(this.bitmap$0 & 0x2) == 0) {
                this.double2BigEndian = (Injection<Object, byte[]>)NumericInjections$class.double2BigEndian(this);
                this.bitmap$0 |= 0x2;
            }
            final BoxedUnit un\u0131t = BoxedUnit.UNIT;
            return this.double2BigEndian;
        }
    }
    
    @Override
    public Injection<Object, byte[]> double2BigEndian() {
        return ((byte)(this.bitmap$0 & 0x2) == 0) ? this.double2BigEndian$lzycompute() : this.double2BigEndian;
    }
    
    @Override
    public void com$twitter$bijection$NumericInjections$_setter_$byte2Short_$eq(final Injection x$1) {
        this.byte2Short = (Injection<Object, Object>)x$1;
    }
    
    @Override
    public void com$twitter$bijection$NumericInjections$_setter_$short2Int_$eq(final Injection x$1) {
        this.short2Int = (Injection<Object, Object>)x$1;
    }
    
    @Override
    public void com$twitter$bijection$NumericInjections$_setter_$int2Long_$eq(final Injection x$1) {
        this.int2Long = (Injection<Object, Object>)x$1;
    }
    
    @Override
    public void com$twitter$bijection$NumericInjections$_setter_$long2BigInt_$eq(final Injection x$1) {
        this.long2BigInt = (Injection<Object, BigInt>)x$1;
    }
    
    @Override
    public void com$twitter$bijection$NumericInjections$_setter_$float2Double_$eq(final Injection x$1) {
        this.float2Double = (Injection<Object, Object>)x$1;
    }
    
    @Override
    public void com$twitter$bijection$NumericInjections$_setter_$int2Double_$eq(final Injection x$1) {
        this.int2Double = (Injection<Object, Object>)x$1;
    }
    
    @Override
    public void com$twitter$bijection$NumericInjections$_setter_$byte2String_$eq(final Injection x$1) {
        this.byte2String = (Injection<Object, String>)x$1;
    }
    
    @Override
    public void com$twitter$bijection$NumericInjections$_setter_$jbyte2String_$eq(final Injection x$1) {
        this.jbyte2String = (Injection<Byte, String>)x$1;
    }
    
    @Override
    public void com$twitter$bijection$NumericInjections$_setter_$short2String_$eq(final Injection x$1) {
        this.short2String = (Injection<Object, String>)x$1;
    }
    
    @Override
    public void com$twitter$bijection$NumericInjections$_setter_$jshort2String_$eq(final Injection x$1) {
        this.jshort2String = (Injection<Short, String>)x$1;
    }
    
    @Override
    public void com$twitter$bijection$NumericInjections$_setter_$int2String_$eq(final Injection x$1) {
        this.int2String = (Injection<Object, String>)x$1;
    }
    
    @Override
    public void com$twitter$bijection$NumericInjections$_setter_$jint2String_$eq(final Injection x$1) {
        this.jint2String = (Injection<Integer, String>)x$1;
    }
    
    @Override
    public void com$twitter$bijection$NumericInjections$_setter_$long2String_$eq(final Injection x$1) {
        this.long2String = (Injection<Object, String>)x$1;
    }
    
    @Override
    public void com$twitter$bijection$NumericInjections$_setter_$jlong2String_$eq(final Injection x$1) {
        this.jlong2String = (Injection<Long, String>)x$1;
    }
    
    @Override
    public void com$twitter$bijection$NumericInjections$_setter_$float2String_$eq(final Injection x$1) {
        this.float2String = (Injection<Object, String>)x$1;
    }
    
    @Override
    public void com$twitter$bijection$NumericInjections$_setter_$jfloat2String_$eq(final Injection x$1) {
        this.jfloat2String = (Injection<Float, String>)x$1;
    }
    
    @Override
    public void com$twitter$bijection$NumericInjections$_setter_$double2String_$eq(final Injection x$1) {
        this.double2String = (Injection<Object, String>)x$1;
    }
    
    @Override
    public void com$twitter$bijection$NumericInjections$_setter_$jdouble2String_$eq(final Injection x$1) {
        this.jdouble2String = (Injection<Double, String>)x$1;
    }
    
    @Override
    public void com$twitter$bijection$NumericInjections$_setter_$short2BigEndian_$eq(final Injection x$1) {
        this.short2BigEndian = (Injection<Object, byte[]>)x$1;
    }
    
    @Override
    public void com$twitter$bijection$NumericInjections$_setter_$int2BigEndian_$eq(final Injection x$1) {
        this.int2BigEndian = (Injection<Object, byte[]>)x$1;
    }
    
    @Override
    public void com$twitter$bijection$NumericInjections$_setter_$long2BigEndian_$eq(final Injection x$1) {
        this.long2BigEndian = (Injection<Object, byte[]>)x$1;
    }
    
    @Override
    public <A1, B1, A2, B2> Injection<Tuple2<A1, B1>, Tuple2<A2, B2>> tuple2(final Injection<A1, A2> ba, final Injection<B1, B2> bb) {
        return (Injection<Tuple2<A1, B1>, Tuple2<A2, B2>>)GeneratedTupleInjections$class.tuple2(this, ba, bb);
    }
    
    @Override
    public <A1, B1, C1, A2, B2, C2> Injection<Tuple3<A1, B1, C1>, Tuple3<A2, B2, C2>> tuple3(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc) {
        return (Injection<Tuple3<A1, B1, C1>, Tuple3<A2, B2, C2>>)GeneratedTupleInjections$class.tuple3(this, ba, bb, bc);
    }
    
    @Override
    public <A1, B1, C1, D1, A2, B2, C2, D2> Injection<Tuple4<A1, B1, C1, D1>, Tuple4<A2, B2, C2, D2>> tuple4(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd) {
        return (Injection<Tuple4<A1, B1, C1, D1>, Tuple4<A2, B2, C2, D2>>)GeneratedTupleInjections$class.tuple4(this, ba, bb, bc, bd);
    }
    
    @Override
    public <A1, B1, C1, D1, E1, A2, B2, C2, D2, E2> Injection<Tuple5<A1, B1, C1, D1, E1>, Tuple5<A2, B2, C2, D2, E2>> tuple5(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd, final Injection<E1, E2> be) {
        return (Injection<Tuple5<A1, B1, C1, D1, E1>, Tuple5<A2, B2, C2, D2, E2>>)GeneratedTupleInjections$class.tuple5(this, ba, bb, bc, bd, be);
    }
    
    @Override
    public <A1, B1, C1, D1, E1, F1, A2, B2, C2, D2, E2, F2> Injection<Tuple6<A1, B1, C1, D1, E1, F1>, Tuple6<A2, B2, C2, D2, E2, F2>> tuple6(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd, final Injection<E1, E2> be, final Injection<F1, F2> bf) {
        return (Injection<Tuple6<A1, B1, C1, D1, E1, F1>, Tuple6<A2, B2, C2, D2, E2, F2>>)GeneratedTupleInjections$class.tuple6(this, ba, bb, bc, bd, be, bf);
    }
    
    @Override
    public <A1, B1, C1, D1, E1, F1, G1, A2, B2, C2, D2, E2, F2, G2> Injection<Tuple7<A1, B1, C1, D1, E1, F1, G1>, Tuple7<A2, B2, C2, D2, E2, F2, G2>> tuple7(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd, final Injection<E1, E2> be, final Injection<F1, F2> bf, final Injection<G1, G2> bg) {
        return (Injection<Tuple7<A1, B1, C1, D1, E1, F1, G1>, Tuple7<A2, B2, C2, D2, E2, F2, G2>>)GeneratedTupleInjections$class.tuple7(this, ba, bb, bc, bd, be, bf, bg);
    }
    
    @Override
    public <A1, B1, C1, D1, E1, F1, G1, H1, A2, B2, C2, D2, E2, F2, G2, H2> Injection<Tuple8<A1, B1, C1, D1, E1, F1, G1, H1>, Tuple8<A2, B2, C2, D2, E2, F2, G2, H2>> tuple8(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd, final Injection<E1, E2> be, final Injection<F1, F2> bf, final Injection<G1, G2> bg, final Injection<H1, H2> bh) {
        return (Injection<Tuple8<A1, B1, C1, D1, E1, F1, G1, H1>, Tuple8<A2, B2, C2, D2, E2, F2, G2, H2>>)GeneratedTupleInjections$class.tuple8(this, ba, bb, bc, bd, be, bf, bg, bh);
    }
    
    @Override
    public <A1, B1, C1, D1, E1, F1, G1, H1, I1, A2, B2, C2, D2, E2, F2, G2, H2, I2> Injection<Tuple9<A1, B1, C1, D1, E1, F1, G1, H1, I1>, Tuple9<A2, B2, C2, D2, E2, F2, G2, H2, I2>> tuple9(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd, final Injection<E1, E2> be, final Injection<F1, F2> bf, final Injection<G1, G2> bg, final Injection<H1, H2> bh, final Injection<I1, I2> bi) {
        return (Injection<Tuple9<A1, B1, C1, D1, E1, F1, G1, H1, I1>, Tuple9<A2, B2, C2, D2, E2, F2, G2, H2, I2>>)GeneratedTupleInjections$class.tuple9(this, ba, bb, bc, bd, be, bf, bg, bh, bi);
    }
    
    @Override
    public <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2> Injection<Tuple10<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1>, Tuple10<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2>> tuple10(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd, final Injection<E1, E2> be, final Injection<F1, F2> bf, final Injection<G1, G2> bg, final Injection<H1, H2> bh, final Injection<I1, I2> bi, final Injection<J1, J2> bj) {
        return (Injection<Tuple10<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1>, Tuple10<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2>>)GeneratedTupleInjections$class.tuple10(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj);
    }
    
    @Override
    public <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2> Injection<Tuple11<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1>, Tuple11<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2>> tuple11(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd, final Injection<E1, E2> be, final Injection<F1, F2> bf, final Injection<G1, G2> bg, final Injection<H1, H2> bh, final Injection<I1, I2> bi, final Injection<J1, J2> bj, final Injection<K1, K2> bk) {
        return (Injection<Tuple11<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1>, Tuple11<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2>>)GeneratedTupleInjections$class.tuple11(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk);
    }
    
    @Override
    public <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2> Injection<Tuple12<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1>, Tuple12<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2>> tuple12(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd, final Injection<E1, E2> be, final Injection<F1, F2> bf, final Injection<G1, G2> bg, final Injection<H1, H2> bh, final Injection<I1, I2> bi, final Injection<J1, J2> bj, final Injection<K1, K2> bk, final Injection<L1, L2> bl) {
        return (Injection<Tuple12<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1>, Tuple12<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2>>)GeneratedTupleInjections$class.tuple12(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl);
    }
    
    @Override
    public <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2> Injection<Tuple13<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1>, Tuple13<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2>> tuple13(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd, final Injection<E1, E2> be, final Injection<F1, F2> bf, final Injection<G1, G2> bg, final Injection<H1, H2> bh, final Injection<I1, I2> bi, final Injection<J1, J2> bj, final Injection<K1, K2> bk, final Injection<L1, L2> bl, final Injection<M1, M2> bm) {
        return (Injection<Tuple13<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1>, Tuple13<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2>>)GeneratedTupleInjections$class.tuple13(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm);
    }
    
    @Override
    public <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2> Injection<Tuple14<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1>, Tuple14<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2>> tuple14(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd, final Injection<E1, E2> be, final Injection<F1, F2> bf, final Injection<G1, G2> bg, final Injection<H1, H2> bh, final Injection<I1, I2> bi, final Injection<J1, J2> bj, final Injection<K1, K2> bk, final Injection<L1, L2> bl, final Injection<M1, M2> bm, final Injection<N1, N2> bn) {
        return (Injection<Tuple14<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1>, Tuple14<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2>>)GeneratedTupleInjections$class.tuple14(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn);
    }
    
    @Override
    public <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2> Injection<Tuple15<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1>, Tuple15<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2>> tuple15(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd, final Injection<E1, E2> be, final Injection<F1, F2> bf, final Injection<G1, G2> bg, final Injection<H1, H2> bh, final Injection<I1, I2> bi, final Injection<J1, J2> bj, final Injection<K1, K2> bk, final Injection<L1, L2> bl, final Injection<M1, M2> bm, final Injection<N1, N2> bn, final Injection<O1, O2> bo) {
        return (Injection<Tuple15<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1>, Tuple15<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2>>)GeneratedTupleInjections$class.tuple15(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo);
    }
    
    @Override
    public <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2> Injection<Tuple16<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1>, Tuple16<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2>> tuple16(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd, final Injection<E1, E2> be, final Injection<F1, F2> bf, final Injection<G1, G2> bg, final Injection<H1, H2> bh, final Injection<I1, I2> bi, final Injection<J1, J2> bj, final Injection<K1, K2> bk, final Injection<L1, L2> bl, final Injection<M1, M2> bm, final Injection<N1, N2> bn, final Injection<O1, O2> bo, final Injection<P1, P2> bp) {
        return (Injection<Tuple16<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1>, Tuple16<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2>>)GeneratedTupleInjections$class.tuple16(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp);
    }
    
    @Override
    public <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2> Injection<Tuple17<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1>, Tuple17<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2>> tuple17(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd, final Injection<E1, E2> be, final Injection<F1, F2> bf, final Injection<G1, G2> bg, final Injection<H1, H2> bh, final Injection<I1, I2> bi, final Injection<J1, J2> bj, final Injection<K1, K2> bk, final Injection<L1, L2> bl, final Injection<M1, M2> bm, final Injection<N1, N2> bn, final Injection<O1, O2> bo, final Injection<P1, P2> bp, final Injection<Q1, Q2> bq) {
        return (Injection<Tuple17<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1>, Tuple17<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2>>)GeneratedTupleInjections$class.tuple17(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq);
    }
    
    @Override
    public <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2> Injection<Tuple18<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1>, Tuple18<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2>> tuple18(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd, final Injection<E1, E2> be, final Injection<F1, F2> bf, final Injection<G1, G2> bg, final Injection<H1, H2> bh, final Injection<I1, I2> bi, final Injection<J1, J2> bj, final Injection<K1, K2> bk, final Injection<L1, L2> bl, final Injection<M1, M2> bm, final Injection<N1, N2> bn, final Injection<O1, O2> bo, final Injection<P1, P2> bp, final Injection<Q1, Q2> bq, final Injection<R1, R2> br) {
        return (Injection<Tuple18<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1>, Tuple18<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2>>)GeneratedTupleInjections$class.tuple18(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq, br);
    }
    
    @Override
    public <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2, S2> Injection<Tuple19<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1>, Tuple19<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2, S2>> tuple19(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd, final Injection<E1, E2> be, final Injection<F1, F2> bf, final Injection<G1, G2> bg, final Injection<H1, H2> bh, final Injection<I1, I2> bi, final Injection<J1, J2> bj, final Injection<K1, K2> bk, final Injection<L1, L2> bl, final Injection<M1, M2> bm, final Injection<N1, N2> bn, final Injection<O1, O2> bo, final Injection<P1, P2> bp, final Injection<Q1, Q2> bq, final Injection<R1, R2> br, final Injection<S1, S2> bs) {
        return (Injection<Tuple19<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1>, Tuple19<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2, S2>>)GeneratedTupleInjections$class.tuple19(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq, br, bs);
    }
    
    @Override
    public <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2, S2, T2> Injection<Tuple20<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1>, Tuple20<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2, S2, T2>> tuple20(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd, final Injection<E1, E2> be, final Injection<F1, F2> bf, final Injection<G1, G2> bg, final Injection<H1, H2> bh, final Injection<I1, I2> bi, final Injection<J1, J2> bj, final Injection<K1, K2> bk, final Injection<L1, L2> bl, final Injection<M1, M2> bm, final Injection<N1, N2> bn, final Injection<O1, O2> bo, final Injection<P1, P2> bp, final Injection<Q1, Q2> bq, final Injection<R1, R2> br, final Injection<S1, S2> bs, final Injection<T1, T2> bt) {
        return (Injection<Tuple20<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1>, Tuple20<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2, S2, T2>>)GeneratedTupleInjections$class.tuple20(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq, br, bs, bt);
    }
    
    @Override
    public <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2, S2, T2, U2> Injection<Tuple21<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1>, Tuple21<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2, S2, T2, U2>> tuple21(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd, final Injection<E1, E2> be, final Injection<F1, F2> bf, final Injection<G1, G2> bg, final Injection<H1, H2> bh, final Injection<I1, I2> bi, final Injection<J1, J2> bj, final Injection<K1, K2> bk, final Injection<L1, L2> bl, final Injection<M1, M2> bm, final Injection<N1, N2> bn, final Injection<O1, O2> bo, final Injection<P1, P2> bp, final Injection<Q1, Q2> bq, final Injection<R1, R2> br, final Injection<S1, S2> bs, final Injection<T1, T2> bt, final Injection<U1, U2> bu) {
        return (Injection<Tuple21<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1>, Tuple21<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2, S2, T2, U2>>)GeneratedTupleInjections$class.tuple21(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq, br, bs, bt, bu);
    }
    
    @Override
    public <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, V1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2, S2, T2, U2, V2> Injection<Tuple22<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, V1>, Tuple22<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2, S2, T2, U2, V2>> tuple22(final Injection<A1, A2> ba, final Injection<B1, B2> bb, final Injection<C1, C2> bc, final Injection<D1, D2> bd, final Injection<E1, E2> be, final Injection<F1, F2> bf, final Injection<G1, G2> bg, final Injection<H1, H2> bh, final Injection<I1, I2> bi, final Injection<J1, J2> bj, final Injection<K1, K2> bk, final Injection<L1, L2> bl, final Injection<M1, M2> bm, final Injection<N1, N2> bn, final Injection<O1, O2> bo, final Injection<P1, P2> bp, final Injection<Q1, Q2> bq, final Injection<R1, R2> br, final Injection<S1, S2> bs, final Injection<T1, T2> bt, final Injection<U1, U2> bu, final Injection<V1, V2> bv) {
        return (Injection<Tuple22<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, V1>, Tuple22<A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2, S2, T2, U2, V2>>)GeneratedTupleInjections$class.tuple22(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq, br, bs, bt, bu, bv);
    }
    
    @Override
    public <A, B, C> Injection<Tuple2<A, B>, List<C>> tuple2ToList(final Injection<A, C> ba, final Injection<B, C> bb) {
        return (Injection<Tuple2<A, B>, List<C>>)GeneratedTupleCollectionInjections$class.tuple2ToList(this, ba, bb);
    }
    
    @Override
    public <A, B, C, D> Injection<Tuple3<A, B, C>, List<D>> tuple3ToList(final Injection<A, D> ba, final Injection<B, D> bb, final Injection<C, D> bc) {
        return (Injection<Tuple3<A, B, C>, List<D>>)GeneratedTupleCollectionInjections$class.tuple3ToList(this, ba, bb, bc);
    }
    
    @Override
    public <A, B, C, D, E> Injection<Tuple4<A, B, C, D>, List<E>> tuple4ToList(final Injection<A, E> ba, final Injection<B, E> bb, final Injection<C, E> bc, final Injection<D, E> bd) {
        return (Injection<Tuple4<A, B, C, D>, List<E>>)GeneratedTupleCollectionInjections$class.tuple4ToList(this, ba, bb, bc, bd);
    }
    
    @Override
    public <A, B, C, D, E, F> Injection<Tuple5<A, B, C, D, E>, List<F>> tuple5ToList(final Injection<A, F> ba, final Injection<B, F> bb, final Injection<C, F> bc, final Injection<D, F> bd, final Injection<E, F> be) {
        return (Injection<Tuple5<A, B, C, D, E>, List<F>>)GeneratedTupleCollectionInjections$class.tuple5ToList(this, ba, bb, bc, bd, be);
    }
    
    @Override
    public <A, B, C, D, E, F, G> Injection<Tuple6<A, B, C, D, E, F>, List<G>> tuple6ToList(final Injection<A, G> ba, final Injection<B, G> bb, final Injection<C, G> bc, final Injection<D, G> bd, final Injection<E, G> be, final Injection<F, G> bf) {
        return (Injection<Tuple6<A, B, C, D, E, F>, List<G>>)GeneratedTupleCollectionInjections$class.tuple6ToList(this, ba, bb, bc, bd, be, bf);
    }
    
    @Override
    public <A, B, C, D, E, F, G, H> Injection<Tuple7<A, B, C, D, E, F, G>, List<H>> tuple7ToList(final Injection<A, H> ba, final Injection<B, H> bb, final Injection<C, H> bc, final Injection<D, H> bd, final Injection<E, H> be, final Injection<F, H> bf, final Injection<G, H> bg) {
        return (Injection<Tuple7<A, B, C, D, E, F, G>, List<H>>)GeneratedTupleCollectionInjections$class.tuple7ToList(this, ba, bb, bc, bd, be, bf, bg);
    }
    
    @Override
    public <A, B, C, D, E, F, G, H, I> Injection<Tuple8<A, B, C, D, E, F, G, H>, List<I>> tuple8ToList(final Injection<A, I> ba, final Injection<B, I> bb, final Injection<C, I> bc, final Injection<D, I> bd, final Injection<E, I> be, final Injection<F, I> bf, final Injection<G, I> bg, final Injection<H, I> bh) {
        return (Injection<Tuple8<A, B, C, D, E, F, G, H>, List<I>>)GeneratedTupleCollectionInjections$class.tuple8ToList(this, ba, bb, bc, bd, be, bf, bg, bh);
    }
    
    @Override
    public <A, B, C, D, E, F, G, H, I, J> Injection<Tuple9<A, B, C, D, E, F, G, H, I>, List<J>> tuple9ToList(final Injection<A, J> ba, final Injection<B, J> bb, final Injection<C, J> bc, final Injection<D, J> bd, final Injection<E, J> be, final Injection<F, J> bf, final Injection<G, J> bg, final Injection<H, J> bh, final Injection<I, J> bi) {
        return (Injection<Tuple9<A, B, C, D, E, F, G, H, I>, List<J>>)GeneratedTupleCollectionInjections$class.tuple9ToList(this, ba, bb, bc, bd, be, bf, bg, bh, bi);
    }
    
    @Override
    public <A, B, C, D, E, F, G, H, I, J, K> Injection<Tuple10<A, B, C, D, E, F, G, H, I, J>, List<K>> tuple10ToList(final Injection<A, K> ba, final Injection<B, K> bb, final Injection<C, K> bc, final Injection<D, K> bd, final Injection<E, K> be, final Injection<F, K> bf, final Injection<G, K> bg, final Injection<H, K> bh, final Injection<I, K> bi, final Injection<J, K> bj) {
        return (Injection<Tuple10<A, B, C, D, E, F, G, H, I, J>, List<K>>)GeneratedTupleCollectionInjections$class.tuple10ToList(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj);
    }
    
    @Override
    public <A, B, C, D, E, F, G, H, I, J, K, L> Injection<Tuple11<A, B, C, D, E, F, G, H, I, J, K>, List<L>> tuple11ToList(final Injection<A, L> ba, final Injection<B, L> bb, final Injection<C, L> bc, final Injection<D, L> bd, final Injection<E, L> be, final Injection<F, L> bf, final Injection<G, L> bg, final Injection<H, L> bh, final Injection<I, L> bi, final Injection<J, L> bj, final Injection<K, L> bk) {
        return (Injection<Tuple11<A, B, C, D, E, F, G, H, I, J, K>, List<L>>)GeneratedTupleCollectionInjections$class.tuple11ToList(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk);
    }
    
    @Override
    public <A, B, C, D, E, F, G, H, I, J, K, L, M> Injection<Tuple12<A, B, C, D, E, F, G, H, I, J, K, L>, List<M>> tuple12ToList(final Injection<A, M> ba, final Injection<B, M> bb, final Injection<C, M> bc, final Injection<D, M> bd, final Injection<E, M> be, final Injection<F, M> bf, final Injection<G, M> bg, final Injection<H, M> bh, final Injection<I, M> bi, final Injection<J, M> bj, final Injection<K, M> bk, final Injection<L, M> bl) {
        return (Injection<Tuple12<A, B, C, D, E, F, G, H, I, J, K, L>, List<M>>)GeneratedTupleCollectionInjections$class.tuple12ToList(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl);
    }
    
    @Override
    public <A, B, C, D, E, F, G, H, I, J, K, L, M, N> Injection<Tuple13<A, B, C, D, E, F, G, H, I, J, K, L, M>, List<N>> tuple13ToList(final Injection<A, N> ba, final Injection<B, N> bb, final Injection<C, N> bc, final Injection<D, N> bd, final Injection<E, N> be, final Injection<F, N> bf, final Injection<G, N> bg, final Injection<H, N> bh, final Injection<I, N> bi, final Injection<J, N> bj, final Injection<K, N> bk, final Injection<L, N> bl, final Injection<M, N> bm) {
        return (Injection<Tuple13<A, B, C, D, E, F, G, H, I, J, K, L, M>, List<N>>)GeneratedTupleCollectionInjections$class.tuple13ToList(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm);
    }
    
    @Override
    public <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O> Injection<Tuple14<A, B, C, D, E, F, G, H, I, J, K, L, M, N>, List<O>> tuple14ToList(final Injection<A, O> ba, final Injection<B, O> bb, final Injection<C, O> bc, final Injection<D, O> bd, final Injection<E, O> be, final Injection<F, O> bf, final Injection<G, O> bg, final Injection<H, O> bh, final Injection<I, O> bi, final Injection<J, O> bj, final Injection<K, O> bk, final Injection<L, O> bl, final Injection<M, O> bm, final Injection<N, O> bn) {
        return (Injection<Tuple14<A, B, C, D, E, F, G, H, I, J, K, L, M, N>, List<O>>)GeneratedTupleCollectionInjections$class.tuple14ToList(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn);
    }
    
    @Override
    public <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P> Injection<Tuple15<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O>, List<P>> tuple15ToList(final Injection<A, P> ba, final Injection<B, P> bb, final Injection<C, P> bc, final Injection<D, P> bd, final Injection<E, P> be, final Injection<F, P> bf, final Injection<G, P> bg, final Injection<H, P> bh, final Injection<I, P> bi, final Injection<J, P> bj, final Injection<K, P> bk, final Injection<L, P> bl, final Injection<M, P> bm, final Injection<N, P> bn, final Injection<O, P> bo) {
        return (Injection<Tuple15<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O>, List<P>>)GeneratedTupleCollectionInjections$class.tuple15ToList(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo);
    }
    
    @Override
    public <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q> Injection<Tuple16<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P>, List<Q>> tuple16ToList(final Injection<A, Q> ba, final Injection<B, Q> bb, final Injection<C, Q> bc, final Injection<D, Q> bd, final Injection<E, Q> be, final Injection<F, Q> bf, final Injection<G, Q> bg, final Injection<H, Q> bh, final Injection<I, Q> bi, final Injection<J, Q> bj, final Injection<K, Q> bk, final Injection<L, Q> bl, final Injection<M, Q> bm, final Injection<N, Q> bn, final Injection<O, Q> bo, final Injection<P, Q> bp) {
        return (Injection<Tuple16<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P>, List<Q>>)GeneratedTupleCollectionInjections$class.tuple16ToList(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp);
    }
    
    @Override
    public <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R> Injection<Tuple17<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q>, List<R>> tuple17ToList(final Injection<A, R> ba, final Injection<B, R> bb, final Injection<C, R> bc, final Injection<D, R> bd, final Injection<E, R> be, final Injection<F, R> bf, final Injection<G, R> bg, final Injection<H, R> bh, final Injection<I, R> bi, final Injection<J, R> bj, final Injection<K, R> bk, final Injection<L, R> bl, final Injection<M, R> bm, final Injection<N, R> bn, final Injection<O, R> bo, final Injection<P, R> bp, final Injection<Q, R> bq) {
        return (Injection<Tuple17<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q>, List<R>>)GeneratedTupleCollectionInjections$class.tuple17ToList(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq);
    }
    
    @Override
    public <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S> Injection<Tuple18<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R>, List<S>> tuple18ToList(final Injection<A, S> ba, final Injection<B, S> bb, final Injection<C, S> bc, final Injection<D, S> bd, final Injection<E, S> be, final Injection<F, S> bf, final Injection<G, S> bg, final Injection<H, S> bh, final Injection<I, S> bi, final Injection<J, S> bj, final Injection<K, S> bk, final Injection<L, S> bl, final Injection<M, S> bm, final Injection<N, S> bn, final Injection<O, S> bo, final Injection<P, S> bp, final Injection<Q, S> bq, final Injection<R, S> br) {
        return (Injection<Tuple18<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R>, List<S>>)GeneratedTupleCollectionInjections$class.tuple18ToList(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq, br);
    }
    
    @Override
    public <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T> Injection<Tuple19<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S>, List<T>> tuple19ToList(final Injection<A, T> ba, final Injection<B, T> bb, final Injection<C, T> bc, final Injection<D, T> bd, final Injection<E, T> be, final Injection<F, T> bf, final Injection<G, T> bg, final Injection<H, T> bh, final Injection<I, T> bi, final Injection<J, T> bj, final Injection<K, T> bk, final Injection<L, T> bl, final Injection<M, T> bm, final Injection<N, T> bn, final Injection<O, T> bo, final Injection<P, T> bp, final Injection<Q, T> bq, final Injection<R, T> br, final Injection<S, T> bs) {
        return (Injection<Tuple19<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S>, List<T>>)GeneratedTupleCollectionInjections$class.tuple19ToList(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq, br, bs);
    }
    
    @Override
    public <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U> Injection<Tuple20<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T>, List<U>> tuple20ToList(final Injection<A, U> ba, final Injection<B, U> bb, final Injection<C, U> bc, final Injection<D, U> bd, final Injection<E, U> be, final Injection<F, U> bf, final Injection<G, U> bg, final Injection<H, U> bh, final Injection<I, U> bi, final Injection<J, U> bj, final Injection<K, U> bk, final Injection<L, U> bl, final Injection<M, U> bm, final Injection<N, U> bn, final Injection<O, U> bo, final Injection<P, U> bp, final Injection<Q, U> bq, final Injection<R, U> br, final Injection<S, U> bs, final Injection<T, U> bt) {
        return (Injection<Tuple20<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T>, List<U>>)GeneratedTupleCollectionInjections$class.tuple20ToList(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq, br, bs, bt);
    }
    
    @Override
    public <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V> Injection<Tuple21<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U>, List<V>> tuple21ToList(final Injection<A, V> ba, final Injection<B, V> bb, final Injection<C, V> bc, final Injection<D, V> bd, final Injection<E, V> be, final Injection<F, V> bf, final Injection<G, V> bg, final Injection<H, V> bh, final Injection<I, V> bi, final Injection<J, V> bj, final Injection<K, V> bk, final Injection<L, V> bl, final Injection<M, V> bm, final Injection<N, V> bn, final Injection<O, V> bo, final Injection<P, V> bp, final Injection<Q, V> bq, final Injection<R, V> br, final Injection<S, V> bs, final Injection<T, V> bt, final Injection<U, V> bu) {
        return (Injection<Tuple21<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U>, List<V>>)GeneratedTupleCollectionInjections$class.tuple21ToList(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq, br, bs, bt, bu);
    }
    
    @Override
    public <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W> Injection<Tuple22<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V>, List<W>> tuple22ToList(final Injection<A, W> ba, final Injection<B, W> bb, final Injection<C, W> bc, final Injection<D, W> bd, final Injection<E, W> be, final Injection<F, W> bf, final Injection<G, W> bg, final Injection<H, W> bh, final Injection<I, W> bi, final Injection<J, W> bj, final Injection<K, W> bk, final Injection<L, W> bl, final Injection<M, W> bm, final Injection<N, W> bn, final Injection<O, W> bo, final Injection<P, W> bp, final Injection<Q, W> bq, final Injection<R, W> br, final Injection<S, W> bs, final Injection<T, W> bt, final Injection<U, W> bu, final Injection<V, W> bv) {
        return (Injection<Tuple22<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V>, List<W>>)GeneratedTupleCollectionInjections$class.tuple22ToList(this, ba, bb, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq, br, bs, bt, bu, bv);
    }
    
    @Override
    public <A, B> Injection<A, B> fromImplicitBijection(final ImplicitBijection<A, B> bij) {
        return (Injection<A, B>)LowPriorityInjections$class.fromImplicitBijection(this, bij);
    }
    
    private StringCodec$() {
        LowPriorityInjections$class.$init$(MODULE$ = this);
        GeneratedTupleCollectionInjections$class.$init$(this);
        GeneratedTupleInjections$class.$init$(this);
        NumericInjections$class.$init$(this);
        StringInjections$class.$init$(this);
    }
}
