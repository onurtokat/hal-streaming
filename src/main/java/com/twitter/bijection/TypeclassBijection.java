// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.Product$class;
import scala.runtime.ScalaRunTime$;
import scala.collection.Iterator;
import scala.runtime.BoxesRunTime;
import scala.Product;
import scala.Some;
import scala.None$;
import scala.Tuple2;
import scala.Option;
import scala.Serializable;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes = "\u0006\u0001\tMv!B\u0001\u0003\u0011\u0003I\u0011A\u0005+za\u0016\u001cG.Y:t\u0005&TWm\u0019;j_:T!a\u0001\u0003\u0002\u0013\tL'.Z2uS>t'BA\u0003\u0007\u0003\u001d!x/\u001b;uKJT\u0011aB\u0001\u0004G>l7\u0001\u0001\t\u0003\u0015-i\u0011A\u0001\u0004\u0006\u0019\tA\t!\u0004\u0002\u0013)f\u0004Xm\u00197bgN\u0014\u0015N[3di&|gn\u0005\u0002\f\u001dA\u0011qBE\u0007\u0002!)\t\u0011#A\u0003tG\u0006d\u0017-\u0003\u0002\u0014!\t1\u0011I\\=SK\u001aDQ!F\u0006\u0005\u0002Y\ta\u0001P5oSRtD#A\u0005\u0007\taY\u0011!\u0007\u0002\u000e%&\u001c\u0007\u000eV=qK\u000ed\u0017m]:\u0016\u0007iyBf\u0005\u0002\u0018\u001d!AAd\u0006B\u0001B\u0003%Q$A\u0001u!\rqrd\u000b\u0007\u0001\t\u0015\u0001sC1\u0001\"\u0005\u0005!VC\u0001\u0012*#\t\u0019c\u0005\u0005\u0002\u0010I%\u0011Q\u0005\u0005\u0002\b\u001d>$\b.\u001b8h!\tyq%\u0003\u0002)!\t\u0019\u0011I\\=\u0005\u000b)z\"\u0019\u0001\u0012\u0003\u0003}\u0003\"A\b\u0017\u0005\u000b5:\"\u0019\u0001\u0012\u0003\u0003\u0005CQ!F\f\u0005\u0002=\"\"\u0001M\u001a\u0011\tE:\"gK\u0007\u0002\u0017A\u0011ad\b\u0005\u000699\u0002\r!\b\u0005\u0006k]!\tAN\u0001\tE&TWm\u0019;U_V\u0011qG\u000f\u000b\u0004qq2\u0006c\u0001\u0010 sA\u0011aD\u000f\u0003\u0006wQ\u0012\rA\t\u0002\u0002\u0005\")Q\b\u000ea\u0002}\u0005)Ao\u0019\"jUB\u0019!b\u0010\u001a\u0007\u000f1\u0011\u0001\u0013aI\u0001\u0001V\u0011\u0011iR\n\u0003\u007f9AQaQ \u0007\u0002\u0011\u000bQ!\u00199qYf,2!\u0012)L)\r1E*\u0015\t\u0004=\u001dSE!\u0002\u0011@\u0005\u0004AUC\u0001\u0012J\t\u0015QsI1\u0001#!\tq2\nB\u0003<\u0005\n\u0007!\u0005C\u0003N\u0005\u0002\u0007a*\u0001\u0002uGB\u0019adR(\u0011\u0005y\u0001F!B\u0017C\u0005\u0004\u0011\u0003\"\u0002*C\u0001\u0004\u0019\u0016a\u00012jUB!!\u0002V(K\u0013\t)&AA\u0005CS*,7\r^5p]\")!\u000b\u000ea\u0002/B!!\u0002W\u0016:\u0013\tI&AA\tJ[Bd\u0017nY5u\u0005&TWm\u0019;j_:DqaW\u0006\u0002\u0002\u0013\rA,A\u0007SS\u000eDG+\u001f9fG2\f7o]\u000b\u0004;\u0002$GC\u00010f!\u0011\ttcX2\u0011\u0005y\u0001G!\u0002\u0011[\u0005\u0004\tWC\u0001\u0012c\t\u0015Q\u0003M1\u0001#!\tqB\rB\u0003.5\n\u0007!\u0005C\u0003\u001d5\u0002\u0007a\rE\u0002\u001fA\u000e<Q\u0001[\u0006\t\u0002%\fQCQ5kK\u000e$\u0018n\u001c8B]\u0012$\u0016\u0010]3dY\u0006\u001c8\u000f\u0005\u00022U\u001a)1n\u0003E\u0001Y\n)\")\u001b6fGRLwN\\!oIRK\b/Z2mCN\u001c8c\u00016\u000f[B\u0011qB\\\u0005\u0003_B\u0011AbU3sS\u0006d\u0017N_1cY\u0016DQ!\u00066\u0005\u0002E$\u0012!\u001b\u0005\u0006g*$\u0019\u0001^\u0001\u0004O\u0016$XcB;\u0002x\u0006}(1\u0001\u000b\u0006m\n\u0015!\u0011\u0002\t\tc]\f)0!@\u0003\u0002\u0019!1n\u0003!y+\u001dI\u0018\u0011DA\u0005\u0003\u0007\u0019Ba\u001e\b{[B\u0011qb_\u0005\u0003yB\u0011q\u0001\u0015:pIV\u001cG\u000f\u0003\u0005So\nU\r\u0011\"\u0001\u007f+\u0005y\bC\u0002\u0006Y\u0003\u0003\t9\u0001E\u0002\u001f\u0003\u0007!a!!\u0002x\u0005\u0004\u0011#A\u0001+p!\rq\u0012\u0011\u0002\u0003\u0007\u0003\u00179(\u0019\u0001\u0012\u0003\t\u0019\u0013x.\u001c\u0005\n\u0003\u001f9(\u0011#Q\u0001\n}\fAAY5kA!Q\u00111C<\u0003\u0016\u0004%\t!!\u0006\u0002\u0013QL\b/Z2mCN\u001cXCAA\f!\u0015q\u0012\u0011DA\u0004\t\u0019\u0001sO1\u0001\u0002\u001cU\u0019!%!\b\u0005\r)\nIB1\u0001#\u0011)\t\tc\u001eB\tB\u0003%\u0011qC\u0001\u000bif\u0004Xm\u00197bgN\u0004\u0003BB\u000bx\t\u0003\t)\u0003\u0006\u0004\u0002(\u0005-\u0012Q\u0006\t\tc]\fI#a\u0002\u0002\u0002A\u0019a$!\u0007\t\rI\u000b\u0019\u00031\u0001\u0000\u0011!\t\u0019\"a\tA\u0002\u0005]\u0001BB\"x\t\u0003\t\t\u0004\u0006\u0003\u00024\u0005U\u0002#\u0002\u0010\u0002\u001a\u0005\u0005\u0001bB'\u00020\u0001\u0007\u0011q\u0007\t\u0005\u0015}\nI\u0003C\u0005\u0002<]\f\t\u0011\"\u0001\u0002>\u0005!1m\u001c9z+!\ty$!\u0012\u0002N\u0005ECCBA!\u0003'\n9\u0006\u0005\u00052o\u0006\r\u00131JA(!\rq\u0012Q\t\u0003\bA\u0005e\"\u0019AA$+\r\u0011\u0013\u0011\n\u0003\u0007U\u0005\u0015#\u0019\u0001\u0012\u0011\u0007y\ti\u0005B\u0004\u0002\f\u0005e\"\u0019\u0001\u0012\u0011\u0007y\t\t\u0006B\u0004\u0002\u0006\u0005e\"\u0019\u0001\u0012\t\u0013I\u000bI\u0004%AA\u0002\u0005U\u0003C\u0002\u0006Y\u0003\u001f\nY\u0005\u0003\u0006\u0002\u0014\u0005e\u0002\u0013!a\u0001\u00033\u0002RAHA#\u0003\u0017B\u0011\"!\u0018x#\u0003%\t!a\u0018\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cUA\u0011\u0011MA<\u0003{\ny(\u0006\u0002\u0002d)\u001aq0!\u001a,\u0005\u0005\u001d\u0004\u0003BA5\u0003gj!!a\u001b\u000b\t\u00055\u0014qN\u0001\nk:\u001c\u0007.Z2lK\u0012T1!!\u001d\u0011\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003k\nYGA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016$q\u0001IA.\u0005\u0004\tI(F\u0002#\u0003w\"aAKA<\u0005\u0004\u0011CaBA\u0006\u00037\u0012\rA\t\u0003\b\u0003\u000b\tYF1\u0001#\u0011%\t\u0019i^I\u0001\n\u0003\t))\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u0016\u0011\u0005\u001d\u00151RAI\u0003'+\"!!#+\t\u0005]\u0011Q\r\u0003\bA\u0005\u0005%\u0019AAG+\r\u0011\u0013q\u0012\u0003\u0007U\u0005-%\u0019\u0001\u0012\u0005\u000f\u0005-\u0011\u0011\u0011b\u0001E\u00119\u0011QAAA\u0005\u0004\u0011\u0003\"CALo\u0006\u0005I\u0011IAM\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\u0011\u00111\u0014\t\u0005\u0003;\u000b9+\u0004\u0002\u0002 *!\u0011\u0011UAR\u0003\u0011a\u0017M\\4\u000b\u0005\u0005\u0015\u0016\u0001\u00026bm\u0006LA!!+\u0002 \n11\u000b\u001e:j]\u001eD\u0011\"!,x\u0003\u0003%\t!a,\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0005\u0005E\u0006cA\b\u00024&\u0019\u0011Q\u0017\t\u0003\u0007%sG\u000fC\u0005\u0002:^\f\t\u0011\"\u0001\u0002<\u0006q\u0001O]8ek\u000e$X\t\\3nK:$Hc\u0001\u0014\u0002>\"Q\u0011qXA\\\u0003\u0003\u0005\r!!-\u0002\u0007a$\u0013\u0007C\u0005\u0002D^\f\t\u0011\"\u0011\u0002F\u0006y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0002HB)\u0011\u0011ZAhM5\u0011\u00111\u001a\u0006\u0004\u0003\u001b\u0004\u0012AC2pY2,7\r^5p]&!\u0011\u0011[Af\u0005!IE/\u001a:bi>\u0014\b\"CAko\u0006\u0005I\u0011AAl\u0003!\u0019\u0017M\\#rk\u0006dG\u0003BAm\u0003?\u00042aDAn\u0013\r\ti\u000e\u0005\u0002\b\u0005>|G.Z1o\u0011%\ty,a5\u0002\u0002\u0003\u0007a\u0005C\u0005\u0002d^\f\t\u0011\"\u0011\u0002f\u0006A\u0001.Y:i\u0007>$W\r\u0006\u0002\u00022\"I\u0011\u0011^<\u0002\u0002\u0013\u0005\u00131^\u0001\ti>\u001cFO]5oOR\u0011\u00111\u0014\u0005\n\u0003_<\u0018\u0011!C!\u0003c\fa!Z9vC2\u001cH\u0003BAm\u0003gD\u0011\"a0\u0002n\u0006\u0005\t\u0019\u0001\u0014\u0011\u0007y\t9\u0010\u0002\u0004!e\n\u0007\u0011\u0011`\u000b\u0004E\u0005mHA\u0002\u0016\u0002x\n\u0007!\u0005E\u0002\u001f\u0003\u007f$a!a\u0003s\u0005\u0004\u0011\u0003c\u0001\u0010\u0003\u0004\u00111\u0011Q\u0001:C\u0002\tBaA\u0015:A\u0004\t\u001d\u0001C\u0002\u0006Y\u0005\u0003\ti\u0010C\u0004\u0002\u0014I\u0004\u001dAa\u0003\u0011\u000by\t90!@\t\u0011\rS\u0017\u0011!CA\u0005\u001f)\u0002B!\u0005\u0003\u0018\t}!1\u0005\u000b\u0007\u0005'\u0011)C!\u000b\u0011\u0011E:(Q\u0003B\u000f\u0005C\u00012A\bB\f\t\u001d\u0001#Q\u0002b\u0001\u00053)2A\tB\u000e\t\u0019Q#q\u0003b\u0001EA\u0019aDa\b\u0005\u000f\u0005-!Q\u0002b\u0001EA\u0019aDa\t\u0005\u000f\u0005\u0015!Q\u0002b\u0001E!9!K!\u0004A\u0002\t\u001d\u0002C\u0002\u0006Y\u0005C\u0011i\u0002\u0003\u0005\u0002\u0014\t5\u0001\u0019\u0001B\u0016!\u0015q\"q\u0003B\u000f\u0011%\u0011yC[A\u0001\n\u0003\u0013\t$A\u0004v]\u0006\u0004\b\u000f\\=\u0016\u0011\tM\"Q\nB%\u0005\u000b\"BA!\u000e\u0003TA)qBa\u000e\u0003<%\u0019!\u0011\b\t\u0003\r=\u0003H/[8o!\u001dy!Q\bB!\u0005\u0017J1Aa\u0010\u0011\u0005\u0019!V\u000f\u001d7feA1!\u0002\u0017B\"\u0005\u000f\u00022A\bB#\t\u001d\t)A!\fC\u0002\t\u00022A\bB%\t\u001d\tYA!\fC\u0002\t\u0002RA\bB'\u0005\u000f\"q\u0001\tB\u0017\u0005\u0004\u0011y%F\u0002#\u0005#\"aA\u000bB'\u0005\u0004\u0011\u0003B\u0003B+\u0005[\t\t\u00111\u0001\u0003X\u0005\u0019\u0001\u0010\n\u0019\u0011\u0011E:(\u0011\fB$\u0005\u0007\u00022A\bB'\u0011%\u0011iF[A\u0001\n\u0013\u0011y&A\u0006sK\u0006$'+Z:pYZ,GC\u0001B1!\u0011\tiJa\u0019\n\t\t\u0015\u0014q\u0014\u0002\u0007\u001f\nTWm\u0019;\t\u000f\t%4\u0002\"\u0001\u0003l\u0005\u0011B/\u001f9fG2\f7o\u001d\"jU\u0016\u001cG/[8o+!\u0011iG!\u001d\u0003\b\neD\u0003\u0003B8\u0005w\u0012\tI!#\u0011\u000by\u0011\tHa\u001e\u0005\u000f\u0001\u00129G1\u0001\u0003tU\u0019!E!\u001e\u0005\r)\u0012\tH1\u0001#!\rq\"\u0011\u0010\u0003\u0007w\t\u001d$\u0019\u0001\u0012\t\u000fu\u00129\u0007q\u0001\u0003~A!!b\u0010B@!\rq\"\u0011\u000f\u0005\t\u0003'\u00119\u0007q\u0001\u0003\u0004B)aD!\u001d\u0003\u0006B\u0019aDa\"\u0005\r5\u00129G1\u0001#\u0011\u001d\u0011&q\ra\u0002\u0005\u0017\u0003bA\u0003-\u0003\u0006\n]\u0004b\u0002BH\u0017\u0011\u0005!\u0011S\u0001\nI\u0016\u0014\u0018N^3G_J,bAa%\u0003\u0018\n}EC\u0002BK\u0005C\u00139\u000bE\u0003\u001f\u0005/\u0013i\nB\u0004!\u0005\u001b\u0013\rA!'\u0016\u0007\t\u0012Y\n\u0002\u0004+\u0005/\u0013\rA\t\t\u0004=\t}EaBA\u0003\u0005\u001b\u0013\rA\t\u0005\b{\t5\u00059\u0001BR!\u0011QqH!*\u0011\u0007y\u00119\n\u0003\u0005\u0003*\n5\u00059\u0001BV\u0003\u0011\u0011\u0017\r^21\t\t5&\u0011\u0017\t\tc]\u0014)Ka,\u0003\u001eB\u0019aD!-\u0005\u0017\u0005-!qUA\u0001\u0002\u0003\u0015\tA\t")
public interface TypeclassBijection<T>
{
     <A, B> T apply(final T p0, final Bijection<A, B> p1);
    
    public static class RichTypeclass<T, A>
    {
        private final T t;
        
        public <B> T bijectTo(final TypeclassBijection<T> tcBij, final ImplicitBijection<A, B> bij) {
            return tcBij.apply(this.t, bij.bijection());
        }
        
        public RichTypeclass(final T t) {
            this.t = t;
        }
    }
    
    public static class BijectionAndTypeclass$ implements Serializable
    {
        public static final BijectionAndTypeclass$ MODULE$;
        
        static {
            new BijectionAndTypeclass$();
        }
        
        public <T, From, To> BijectionAndTypeclass<T, From, To> get(final ImplicitBijection<To, From> bij, final T typeclass) {
            return new BijectionAndTypeclass<T, From, To>(bij, typeclass);
        }
        
        public <T, From, To> BijectionAndTypeclass<T, From, To> apply(final ImplicitBijection<To, From> bij, final T typeclass) {
            return new BijectionAndTypeclass<T, From, To>(bij, typeclass);
        }
        
        public <T, From, To> Option<Tuple2<ImplicitBijection<To, From>, T>> unapply(final BijectionAndTypeclass<T, From, To> x$0) {
            return (Option<Tuple2<ImplicitBijection<To, From>, T>>)((x$0 == null) ? None$.MODULE$ : new Some<Object>(new Tuple2<ImplicitBijection<To, From>, T>((T1)x$0.bij(), (T2)x$0.typeclass())));
        }
        
        private Object readResolve() {
            return BijectionAndTypeclass$.MODULE$;
        }
        
        public BijectionAndTypeclass$() {
            MODULE$ = this;
        }
    }
    
    public static class BijectionAndTypeclass<T, From, To> implements Product, Serializable
    {
        private final ImplicitBijection<To, From> bij;
        private final T typeclass;
        
        public ImplicitBijection<To, From> bij() {
            return this.bij;
        }
        
        public T typeclass() {
            return this.typeclass;
        }
        
        public T apply(final TypeclassBijection<T> tc) {
            return TypeclassBijection$.MODULE$.typeclassBijection(tc, this.typeclass(), (ImplicitBijection<Object, Object>)ImplicitBijection$.MODULE$.reverse((Bijection<B, A>)this.bij().bijection()));
        }
        
        public <T, From, To> BijectionAndTypeclass<T, From, To> copy(final ImplicitBijection<To, From> bij, final T typeclass) {
            return new BijectionAndTypeclass<T, From, To>(bij, typeclass);
        }
        
        public <T, From, To> ImplicitBijection<To, From> copy$default$1() {
            return this.bij();
        }
        
        public <T, From, To> T copy$default$2() {
            return this.typeclass();
        }
        
        @Override
        public String productPrefix() {
            return "BijectionAndTypeclass";
        }
        
        @Override
        public int productArity() {
            return 2;
        }
        
        @Override
        public Object productElement(final int x$1) {
            Object o = null;
            switch (x$1) {
                default: {
                    throw new IndexOutOfBoundsException(BoxesRunTime.boxToInteger(x$1).toString());
                }
                case 1: {
                    o = this.typeclass();
                    break;
                }
                case 0: {
                    o = this.bij();
                    break;
                }
            }
            return o;
        }
        
        @Override
        public Iterator<Object> productIterator() {
            return ScalaRunTime$.MODULE$.typedProductIterator(this);
        }
        
        @Override
        public boolean canEqual(final Object x$1) {
            return x$1 instanceof BijectionAndTypeclass;
        }
        
        @Override
        public int hashCode() {
            return ScalaRunTime$.MODULE$._hashCode(this);
        }
        
        @Override
        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }
        
        @Override
        public boolean equals(final Object x$1) {
            if (this != x$1) {
                if (x$1 instanceof BijectionAndTypeclass) {
                    final BijectionAndTypeclass bijectionAndTypeclass = (BijectionAndTypeclass)x$1;
                    final ImplicitBijection<To, From> bij = this.bij();
                    final ImplicitBijection bij2 = bijectionAndTypeclass.bij();
                    boolean b = false;
                    Label_0092: {
                        Label_0091: {
                            if (bij == null) {
                                if (bij2 != null) {
                                    break Label_0091;
                                }
                            }
                            else if (!bij.equals(bij2)) {
                                break Label_0091;
                            }
                            if (BoxesRunTime.equals(this.typeclass(), bijectionAndTypeclass.typeclass()) && bijectionAndTypeclass.canEqual(this)) {
                                b = true;
                                break Label_0092;
                            }
                        }
                        b = false;
                    }
                    if (b) {
                        return true;
                    }
                }
                return false;
            }
            return true;
        }
        
        public BijectionAndTypeclass(final ImplicitBijection<To, From> bij, final T typeclass) {
            this.bij = bij;
            this.typeclass = typeclass;
            Product$class.$init$(this);
        }
    }
}
