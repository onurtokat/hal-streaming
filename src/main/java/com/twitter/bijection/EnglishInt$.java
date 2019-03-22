// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.collection.generic.GenMapFactory;
import scala.collection.generic.CanBuildFrom;
import scala.collection.immutable.Map$;
import scala.collection.GenTraversableOnce;
import scala.Predef;
import scala.runtime.ScalaRunTime$;
import scala.collection.Iterator;
import scala.Tuple2;
import scala.math.package$;
import scala.collection.Seq;
import scala.collection.TraversableLike;
import scala.Tuple3;
import scala.Product;
import scala.Function2;
import scala.Function1;
import scala.collection.immutable.List$;
import scala.collection.immutable.Set;
import scala.collection.immutable.List;
import scala.Predef$;
import scala.MatchError;
import scala.collection.mutable.StringBuilder;
import scala.Tuple2$mcII$sp;
import scala.Some;
import scala.runtime.BoxesRunTime;
import scala.None$;
import scala.Option;
import scala.collection.immutable.Map;
import scala.Tuple5;
import scala.Serializable;

public final class EnglishInt$ implements Serializable
{
    public static final EnglishInt$ MODULE$;
    private final Bijection<Object, String> bijectionToInt;
    private final /* synthetic */ Tuple5 x$1;
    private final int t;
    private final int d;
    private final int k;
    private final int m;
    private final int g;
    private final Map<Object, String> units;
    private final Map<Object, String> tens;
    private final Map<Object, String> teens;
    private final Map<Object, String> tenmult;
    private final Map<Object, String> all;
    private final Map<String, Object> word2num;
    private final String s;
    
    static {
        new EnglishInt$();
    }
    
    public Bijection<Object, String> bijectionToInt() {
        return this.bijectionToInt;
    }
    
    public int t() {
        return this.t;
    }
    
    public int d() {
        return this.d;
    }
    
    public int k() {
        return this.k;
    }
    
    public int m() {
        return this.m;
    }
    
    public int g() {
        return this.g;
    }
    
    public Map<Object, String> units() {
        return this.units;
    }
    
    public Map<Object, String> tens() {
        return this.tens;
    }
    
    public Map<Object, String> teens() {
        return this.teens;
    }
    
    public Map<Object, String> tenmult() {
        return this.tenmult;
    }
    
    public Map<Object, String> all() {
        return this.all;
    }
    
    public Map<String, Object> word2num() {
        return this.word2num;
    }
    
    public String s() {
        return this.s;
    }
    
    public Option<String> com$twitter$bijection$EnglishInt$$toEnglish(final int num) {
        return (Option<String>)((num < 0) ? None$.MODULE$ : ((num > this.g()) ? None$.MODULE$ : ((num < 20) ? new Some<Object>(this.all().apply(BoxesRunTime.boxToInteger(num))) : ((num < this.d()) ? this.divide(num, this.t()) : ((num < this.k()) ? this.divide(num, this.d()) : ((num < this.m()) ? this.divide(num, this.k()) : ((num < this.g()) ? this.divide(num, this.m()) : None$.MODULE$)))))));
    }
    
    private Option<String> divide(final int num, final int div) {
        final Tuple2$mcII$sp obj = new Tuple2$mcII$sp(num / div, num % div);
        if (obj != null) {
            final int quo = obj._1$mcI$sp();
            final int rem = obj._2$mcI$sp();
            final Tuple2$mcII$sp tuple2$mcII$sp = new Tuple2$mcII$sp(quo, rem);
            final int quo2 = tuple2$mcII$sp._1$mcI$sp();
            final int rem2 = tuple2$mcII$sp._2$mcI$sp();
            Option<String> option;
            if (div == this.t()) {
                final Some<String> some;
                option = some;
                some = new Some<String>(new StringBuilder().append((Object)this.tens().apply(BoxesRunTime.boxToInteger(quo2 * 10))).append((Object)((rem2 > 0) ? new StringBuilder().append((Object)this.s()).append(this.units().apply(BoxesRunTime.boxToInteger(rem2))).toString() : "")).toString());
            }
            else {
                final Option quoEng = this.com$twitter$bijection$EnglishInt$$toEnglish(quo2);
                final Option remEng = this.com$twitter$bijection$EnglishInt$$toEnglish(rem2);
                final Some<String> some2;
                option = some2;
                some2 = new Some<String>(new StringBuilder().append((Object)quoEng.get()).append((Object)this.s()).append(this.tenmult().apply(BoxesRunTime.boxToInteger(div))).append((Object)((rem2 > 0) ? new StringBuilder().append((Object)this.s()).append(remEng.get()).toString() : "")).toString());
            }
            return option;
        }
        throw new MatchError(obj);
    }
    
    public Option<Object> com$twitter$bijection$EnglishInt$$fromEnglish(final String str) {
        final List list = Predef$.MODULE$.refArrayOps(str.split(this.s())).toList();
        final boolean valid = BoxesRunTime.unboxToBoolean(list.map((Function1<?, Object>)new EnglishInt$$anonfun.EnglishInt$$anonfun$4((Set)this.word2num().keySet()), List$.MODULE$.canBuildFrom()).foldLeft(BoxesRunTime.boxToBoolean(true), (Function2<Boolean, Object, Boolean>)new EnglishInt$$anonfun.EnglishInt$$anonfun$5()));
        Product product;
        if (valid) {
            final int ans = this.numlist2int(list.map((Function1<?, Object>)new EnglishInt$$anonfun.EnglishInt$$anonfun$6(), List$.MODULE$.canBuildFrom()));
            Label_0166: {
                if (this.com$twitter$bijection$EnglishInt$$toEnglish(ans).isDefined()) {
                    final String value = this.com$twitter$bijection$EnglishInt$$toEnglish(ans).get();
                    if (value == null) {
                        if (str != null) {
                            break Label_0166;
                        }
                    }
                    else if (!value.equals(str)) {
                        break Label_0166;
                    }
                    product = new Some<Object>(BoxesRunTime.boxToInteger(ans));
                    return (Option<Object>)product;
                }
            }
            product = None$.MODULE$;
        }
        else {
            product = None$.MODULE$;
        }
        return (Option<Object>)product;
    }
    
    private int numlist2int(final List<Object> numbers) {
        final Tuple3<Integer, Integer, Integer> obj = new Tuple3<Integer, Integer, Integer>(BoxesRunTime.boxToInteger(numbers.indexOf(BoxesRunTime.boxToInteger(this.d()))), BoxesRunTime.boxToInteger(numbers.indexOf(BoxesRunTime.boxToInteger(this.k()))), BoxesRunTime.boxToInteger(numbers.indexOf(BoxesRunTime.boxToInteger(this.m()))));
        if (obj != null) {
            final int id = BoxesRunTime.unboxToInt(obj._1());
            final int ik = BoxesRunTime.unboxToInt(obj._2());
            final int im = BoxesRunTime.unboxToInt(obj._3());
            final Tuple3<Integer, Integer, Integer> tuple3 = new Tuple3<Integer, Integer, Integer>(BoxesRunTime.boxToInteger(id), BoxesRunTime.boxToInteger(ik), BoxesRunTime.boxToInteger(im));
            final int id2 = BoxesRunTime.unboxToInt(tuple3._1());
            final int ik2 = BoxesRunTime.unboxToInt(tuple3._2());
            final int im2 = BoxesRunTime.unboxToInt(tuple3._3());
            final boolean has_100 = id2 > -1;
            final boolean has_higher = ik2 > -1 || im2 > -1;
            final boolean hundred_before_higher = has_100 && has_higher && (id2 < ik2 || id2 < im2);
            int fold;
            if (hundred_before_higher) {
                final List ilist = ((TraversableLike<Object, List>)List$.MODULE$.apply(Predef$.MODULE$.wrapIntArray(new int[] { ik2, im2 })).filter((Function1)new EnglishInt$$anonfun.EnglishInt$$anonfun$1())).filter((Function1<Object, Object>)new EnglishInt$$anonfun.EnglishInt$$anonfun$2(id2));
                final int ix = (ilist.size() > 1) ? package$.MODULE$.min(BoxesRunTime.unboxToInt(ilist.apply(0)), BoxesRunTime.unboxToInt(ilist.apply(1))) : BoxesRunTime.unboxToInt(ilist.apply(0));
                final Tuple2<List<Object>, List<Object>> split = numbers.splitAt(id2 - 1);
                if (split == null) {
                    throw new MatchError(split);
                }
                final List hprev = split._1();
                final List hnext = split._2();
                final Tuple2<List, List> tuple4 = new Tuple2<List, List>(hprev, hnext);
                final List hprev2 = tuple4._1();
                final List hnext2 = tuple4._2();
                final Tuple2 split2 = hnext2.splitAt(ix - id2 + 2);
                if (split2 == null) {
                    throw new MatchError(split2);
                }
                final List prev = split2._1();
                final List next = (List)split2._2();
                final Tuple2<List, List> tuple5 = new Tuple2<List, List>(prev, next);
                final List prev2 = tuple5._1();
                final List next2 = tuple5._2();
                fold = this.fold(hprev2) + this.fold100(prev2) + this.numlist2int(next2);
            }
            else {
                fold = this.fold(numbers);
            }
            return fold;
        }
        throw new MatchError(obj);
    }
    
    private int fold(final List<Object> numbers) {
        final Tuple2 res = numbers.foldLeft(new Tuple2$mcII$sp(0, 0), (Function2<Tuple2$mcII$sp, Object, Tuple2$mcII$sp>)new EnglishInt$$anonfun.EnglishInt$$anonfun$7());
        return res._1$mcI$sp() + res._2$mcI$sp();
    }
    
    private int fold100(final List<Object> numbers) {
        final Tuple2 res = numbers.foldLeft(new Tuple2$mcII$sp(0, 0), (Function2<Tuple2$mcII$sp, Object, Tuple2$mcII$sp>)new EnglishInt$$anonfun.EnglishInt$$anonfun$8());
        return res._1$mcI$sp() + res._2$mcI$sp();
    }
    
    public String apply(final String get) {
        return get;
    }
    
    public Option<String> unapply(final String x$0) {
        return (Option<String>)((new EnglishInt(x$0) == null) ? None$.MODULE$ : new Some<Object>(x$0));
    }
    
    private Object readResolve() {
        return EnglishInt$.MODULE$;
    }
    
    public final String copy$extension(final String $this, final String get) {
        return get;
    }
    
    public final String copy$default$1$extension(final String $this) {
        return $this;
    }
    
    public final String productPrefix$extension(final String $this) {
        return "EnglishInt";
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
        return ScalaRunTime$.MODULE$.typedProductIterator(new EnglishInt($this));
    }
    
    public final boolean canEqual$extension(final String $this, final Object x$1) {
        return x$1 instanceof String;
    }
    
    public final int hashCode$extension(final String $this) {
        return $this.hashCode();
    }
    
    public final boolean equals$extension(final String $this, final Object x$1) {
        if (x$1 instanceof EnglishInt) {
            final String s = (x$1 == null) ? null : ((EnglishInt)x$1).get();
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
        return ScalaRunTime$.MODULE$._toString(new EnglishInt($this));
    }
    
    private EnglishInt$() {
        MODULE$ = this;
        this.bijectionToInt = (Bijection<Object, String>)new EnglishInt$$anon.EnglishInt$$anon$1();
        final Tuple5<Integer, Integer, Integer, Integer, Integer> obj = new Tuple5<Integer, Integer, Integer, Integer, Integer>(BoxesRunTime.boxToInteger(10), BoxesRunTime.boxToInteger(100), BoxesRunTime.boxToInteger(1000), BoxesRunTime.boxToInteger(1000000), BoxesRunTime.boxToInteger(1000000000));
        if (obj != null) {
            final int t = BoxesRunTime.unboxToInt(obj._1());
            final int d = BoxesRunTime.unboxToInt(obj._2());
            final int k = BoxesRunTime.unboxToInt(obj._3());
            final int m = BoxesRunTime.unboxToInt(obj._4());
            final int g = BoxesRunTime.unboxToInt(obj._5());
            this.x$1 = new Tuple5((T1)BoxesRunTime.boxToInteger(t), (T2)BoxesRunTime.boxToInteger(d), (T3)BoxesRunTime.boxToInteger(k), (T4)BoxesRunTime.boxToInteger(m), (T5)BoxesRunTime.boxToInteger(g));
            this.t = BoxesRunTime.unboxToInt(this.x$1._1());
            this.d = BoxesRunTime.unboxToInt(this.x$1._2());
            this.k = BoxesRunTime.unboxToInt(this.x$1._3());
            this.m = BoxesRunTime.unboxToInt(this.x$1._4());
            this.g = BoxesRunTime.unboxToInt(this.x$1._5());
            this.units = ((GenMapFactory<Map<Object, String>>)Predef$.MODULE$.Map()).apply((Seq<Tuple2<Object, Object>>)Predef$.MODULE$.wrapRefArray(new Tuple2[] { Predef.ArrowAssoc$.MODULE$.$minus$greater$extension((Object)Predef$.MODULE$.ArrowAssoc((A)BoxesRunTime.boxToInteger(0)), "zero"), Predef.ArrowAssoc$.MODULE$.$minus$greater$extension((Object)Predef$.MODULE$.ArrowAssoc((A)BoxesRunTime.boxToInteger(1)), "one"), Predef.ArrowAssoc$.MODULE$.$minus$greater$extension((Object)Predef$.MODULE$.ArrowAssoc((A)BoxesRunTime.boxToInteger(2)), "two"), Predef.ArrowAssoc$.MODULE$.$minus$greater$extension((Object)Predef$.MODULE$.ArrowAssoc((A)BoxesRunTime.boxToInteger(3)), "three"), Predef.ArrowAssoc$.MODULE$.$minus$greater$extension((Object)Predef$.MODULE$.ArrowAssoc((A)BoxesRunTime.boxToInteger(4)), "four"), Predef.ArrowAssoc$.MODULE$.$minus$greater$extension((Object)Predef$.MODULE$.ArrowAssoc((A)BoxesRunTime.boxToInteger(5)), "five"), Predef.ArrowAssoc$.MODULE$.$minus$greater$extension((Object)Predef$.MODULE$.ArrowAssoc((A)BoxesRunTime.boxToInteger(6)), "six"), Predef.ArrowAssoc$.MODULE$.$minus$greater$extension((Object)Predef$.MODULE$.ArrowAssoc((A)BoxesRunTime.boxToInteger(7)), "seven"), Predef.ArrowAssoc$.MODULE$.$minus$greater$extension((Object)Predef$.MODULE$.ArrowAssoc((A)BoxesRunTime.boxToInteger(8)), "eight"), Predef.ArrowAssoc$.MODULE$.$minus$greater$extension((Object)Predef$.MODULE$.ArrowAssoc((A)BoxesRunTime.boxToInteger(9)), "nine") }));
            this.tens = ((GenMapFactory<Map<Object, String>>)Predef$.MODULE$.Map()).apply((Seq<Tuple2<Object, Object>>)Predef$.MODULE$.wrapRefArray(new Tuple2[] { Predef.ArrowAssoc$.MODULE$.$minus$greater$extension((Object)Predef$.MODULE$.ArrowAssoc((A)BoxesRunTime.boxToInteger(this.t())), "ten"), Predef.ArrowAssoc$.MODULE$.$minus$greater$extension((Object)Predef$.MODULE$.ArrowAssoc((A)BoxesRunTime.boxToInteger(20)), "twenty"), Predef.ArrowAssoc$.MODULE$.$minus$greater$extension((Object)Predef$.MODULE$.ArrowAssoc((A)BoxesRunTime.boxToInteger(30)), "thirty"), Predef.ArrowAssoc$.MODULE$.$minus$greater$extension((Object)Predef$.MODULE$.ArrowAssoc((A)BoxesRunTime.boxToInteger(40)), "forty"), Predef.ArrowAssoc$.MODULE$.$minus$greater$extension((Object)Predef$.MODULE$.ArrowAssoc((A)BoxesRunTime.boxToInteger(50)), "fifty"), Predef.ArrowAssoc$.MODULE$.$minus$greater$extension((Object)Predef$.MODULE$.ArrowAssoc((A)BoxesRunTime.boxToInteger(60)), "sixty"), Predef.ArrowAssoc$.MODULE$.$minus$greater$extension((Object)Predef$.MODULE$.ArrowAssoc((A)BoxesRunTime.boxToInteger(70)), "seventy"), Predef.ArrowAssoc$.MODULE$.$minus$greater$extension((Object)Predef$.MODULE$.ArrowAssoc((A)BoxesRunTime.boxToInteger(80)), "eighty"), Predef.ArrowAssoc$.MODULE$.$minus$greater$extension((Object)Predef$.MODULE$.ArrowAssoc((A)BoxesRunTime.boxToInteger(90)), "ninety") }));
            this.teens = ((GenMapFactory<Map<Object, String>>)Predef$.MODULE$.Map()).apply((Seq<Tuple2<Object, Object>>)Predef$.MODULE$.wrapRefArray(new Tuple2[] { Predef.ArrowAssoc$.MODULE$.$minus$greater$extension((Object)Predef$.MODULE$.ArrowAssoc((A)BoxesRunTime.boxToInteger(this.t())), "ten"), Predef.ArrowAssoc$.MODULE$.$minus$greater$extension((Object)Predef$.MODULE$.ArrowAssoc((A)BoxesRunTime.boxToInteger(11)), "eleven"), Predef.ArrowAssoc$.MODULE$.$minus$greater$extension((Object)Predef$.MODULE$.ArrowAssoc((A)BoxesRunTime.boxToInteger(12)), "twelve"), Predef.ArrowAssoc$.MODULE$.$minus$greater$extension((Object)Predef$.MODULE$.ArrowAssoc((A)BoxesRunTime.boxToInteger(13)), "thirteen"), Predef.ArrowAssoc$.MODULE$.$minus$greater$extension((Object)Predef$.MODULE$.ArrowAssoc((A)BoxesRunTime.boxToInteger(14)), "fourteen"), Predef.ArrowAssoc$.MODULE$.$minus$greater$extension((Object)Predef$.MODULE$.ArrowAssoc((A)BoxesRunTime.boxToInteger(15)), "fifteen"), Predef.ArrowAssoc$.MODULE$.$minus$greater$extension((Object)Predef$.MODULE$.ArrowAssoc((A)BoxesRunTime.boxToInteger(16)), "sixteen"), Predef.ArrowAssoc$.MODULE$.$minus$greater$extension((Object)Predef$.MODULE$.ArrowAssoc((A)BoxesRunTime.boxToInteger(17)), "seventeen"), Predef.ArrowAssoc$.MODULE$.$minus$greater$extension((Object)Predef$.MODULE$.ArrowAssoc((A)BoxesRunTime.boxToInteger(18)), "eighteen"), Predef.ArrowAssoc$.MODULE$.$minus$greater$extension((Object)Predef$.MODULE$.ArrowAssoc((A)BoxesRunTime.boxToInteger(19)), "nineteen") }));
            this.tenmult = ((GenMapFactory<Map<Object, String>>)Predef$.MODULE$.Map()).apply((Seq<Tuple2<Object, Object>>)Predef$.MODULE$.wrapRefArray(new Tuple2[] { Predef.ArrowAssoc$.MODULE$.$minus$greater$extension((Object)Predef$.MODULE$.ArrowAssoc((A)BoxesRunTime.boxToInteger(this.d())), "hundred"), Predef.ArrowAssoc$.MODULE$.$minus$greater$extension((Object)Predef$.MODULE$.ArrowAssoc((A)BoxesRunTime.boxToInteger(this.k())), "thousand"), Predef.ArrowAssoc$.MODULE$.$minus$greater$extension((Object)Predef$.MODULE$.ArrowAssoc((A)BoxesRunTime.boxToInteger(this.m())), "million"), Predef.ArrowAssoc$.MODULE$.$minus$greater$extension((Object)Predef$.MODULE$.ArrowAssoc((A)BoxesRunTime.boxToInteger(this.g())), "billion") }));
            this.all = this.units().$plus$plus((GenTraversableOnce<Tuple2<Object, Object>>)this.tens()).$plus$plus((GenTraversableOnce<Tuple2<Object, Object>>)this.teens()).$plus$plus((GenTraversableOnce<Tuple2<Object, String>>)this.tenmult());
            this.word2num = this.units().$plus$plus((GenTraversableOnce<Tuple2<Object, Object>>)this.tens()).$plus$plus((GenTraversableOnce<Tuple2<Object, Object>>)this.teens()).$plus$plus((GenTraversableOnce<Tuple2<Object, Object>>)this.tenmult()).map((Function1<Tuple2<Object, Object>, Tuple2<Object, Object>>)new EnglishInt$$anonfun.EnglishInt$$anonfun$3(), (CanBuildFrom<Map<?, ?>, Tuple2<Object, Object>, Map<String, Object>>)Map$.MODULE$.canBuildFrom());
            this.s = " ";
            return;
        }
        throw new MatchError(obj);
    }
}
