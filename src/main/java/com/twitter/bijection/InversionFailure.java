// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.Product$class;
import scala.collection.Seq;
import scala.collection.immutable.StringOps;
import scala.Predef$;
import scala.runtime.ScalaRunTime$;
import scala.collection.Iterator;
import scala.runtime.BoxesRunTime;
import scala.util.Try;
import scala.PartialFunction;
import scala.Tuple2;
import scala.Option;
import scala.reflect.ScalaSignature;
import scala.Serializable;
import scala.Product;

@ScalaSignature(bytes = "\u0006\u0001\u0005\u001du!B\u0001\u0003\u0011\u0003I\u0011\u0001E%om\u0016\u00148/[8o\r\u0006LG.\u001e:f\u0015\t\u0019A!A\u0005cS*,7\r^5p]*\u0011QAB\u0001\bi^LG\u000f^3s\u0015\u00059\u0011aA2p[\u000e\u0001\u0001C\u0001\u0006\f\u001b\u0005\u0011a!\u0002\u0007\u0003\u0011\u0003i!\u0001E%om\u0016\u00148/[8o\r\u0006LG.\u001e:f'\rYa\u0002\u0006\t\u0003\u001fIi\u0011\u0001\u0005\u0006\u0002#\u0005)1oY1mC&\u00111\u0003\u0005\u0002\u0007\u0003:L(+\u001a4\u0011\u0005=)\u0012B\u0001\f\u0011\u00051\u0019VM]5bY&T\u0018M\u00197f\u0011\u0015A2\u0002\"\u0001\u001a\u0003\u0019a\u0014N\\5u}Q\t\u0011\u0002C\u0003\u001c\u0017\u0011\u0005A$A\u0003baBd\u00170F\u0002\u001e\u0003/!2AHA\b!\tQqD\u0002\u0003\r\u0005\u0001\u00033\u0003B\u0010\"[Q\u0001\"A\t\u0016\u000f\u0005\rBcB\u0001\u0013(\u001b\u0005)#B\u0001\u0014\t\u0003\u0019a$o\\8u}%\t\u0011#\u0003\u0002*!\u00059\u0001/Y2lC\u001e,\u0017BA\u0016-\u0005u)fn];qa>\u0014H/\u001a3Pa\u0016\u0014\u0018\r^5p]\u0016C8-\u001a9uS>t'BA\u0015\u0011!\tya&\u0003\u00020!\t9\u0001K]8ek\u000e$\b\u0002C\u0019 \u0005+\u0007I\u0011\u0001\u001a\u0002\r\u0019\f\u0017\u000e\\3e+\u0005\u0019\u0004CA\b5\u0013\t)\u0004CA\u0002B]fD\u0001bN\u0010\u0003\u0012\u0003\u0006IaM\u0001\bM\u0006LG.\u001a3!\u0011!ItD!f\u0001\n\u0003Q\u0014AA3y+\u0005Y\u0004C\u0001\u0012=\u0013\tiDFA\u0005UQJ|w/\u00192mK\"Aqh\bB\tB\u0003%1(A\u0002fq\u0002BQ\u0001G\u0010\u0005\u0002\u0005#2A\b\"D\u0011\u0015\t\u0004\t1\u00014\u0011\u0015I\u0004\t1\u0001<\u0011\u001d)u$!A\u0005\u0002\u0019\u000bAaY8qsR\u0019ad\u0012%\t\u000fE\"\u0005\u0013!a\u0001g!9\u0011\b\u0012I\u0001\u0002\u0004Y\u0004b\u0002& #\u0003%\taS\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\u0005a%FA\u001aNW\u0005q\u0005CA(U\u001b\u0005\u0001&BA)S\u0003%)hn\u00195fG.,GM\u0003\u0002T!\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005U\u0003&!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\"9qkHI\u0001\n\u0003A\u0016AD2paf$C-\u001a4bk2$HEM\u000b\u00023*\u00121(\u0014\u0005\b7~\t\t\u0011\"\u0011]\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\tQ\f\u0005\u0002_G6\tqL\u0003\u0002aC\u0006!A.\u00198h\u0015\u0005\u0011\u0017\u0001\u00026bm\u0006L!\u0001Z0\u0003\rM#(/\u001b8h\u0011\u001d1w$!A\u0005\u0002\u001d\fA\u0002\u001d:pIV\u001cG/\u0011:jif,\u0012\u0001\u001b\t\u0003\u001f%L!A\u001b\t\u0003\u0007%sG\u000fC\u0004m?\u0005\u0005I\u0011A7\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u00111G\u001c\u0005\b_.\f\t\u00111\u0001i\u0003\rAH%\r\u0005\bc~\t\t\u0011\"\u0011s\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014X#A:\u0011\u0007Q<8'D\u0001v\u0015\t1\b#\u0001\u0006d_2dWm\u0019;j_:L!\u0001_;\u0003\u0011%#XM]1u_JDqA_\u0010\u0002\u0002\u0013\u000510\u0001\u0005dC:,\u0015/^1m)\tax\u0010\u0005\u0002\u0010{&\u0011a\u0010\u0005\u0002\b\u0005>|G.Z1o\u0011\u001dy\u00170!AA\u0002MB\u0011\"a\u0001 \u0003\u0003%\t%!\u0002\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012\u0001\u001b\u0005\n\u0003\u0013y\u0012\u0011!C!\u0003\u0017\ta!Z9vC2\u001cHc\u0001?\u0002\u000e!Aq.a\u0002\u0002\u0002\u0003\u00071\u0007C\u0004\u0002\u0012i\u0001\r!a\u0005\u0002\u0003\t\u0004B!!\u0006\u0002\u00181\u0001AaBA\r5\t\u0007\u00111\u0004\u0002\u0002\u0005F\u0019\u0011QD\u001a\u0011\u0007=\ty\"C\u0002\u0002\"A\u0011qAT8uQ&tw\rC\u0004\u0002&-!\t!a\n\u0002\u001b\u0019\f\u0017\u000e\\3e\u0003R$X-\u001c9u+\u0019\tI#!\u000f\u0002BQ!\u00111FA\u001f!\u0019\ti#a\r\u000285\u0011\u0011q\u0006\u0006\u0004\u0003c\u0001\u0012\u0001B;uS2LA!!\u000e\u00020\t\u0019AK]=\u0011\t\u0005U\u0011\u0011\b\u0003\t\u0003w\t\u0019C1\u0001\u0002\u001c\t\t\u0011\t\u0003\u0005\u0002\u0012\u0005\r\u0002\u0019AA !\u0011\t)\"!\u0011\u0005\u0011\u0005e\u00111\u0005b\u0001\u00037Aq!!\u0012\f\t\u0003\t9%\u0001\bqCJ$\u0018.\u00197GC&dWO]3\u0016\r\u0005%\u0013QKA.)\u0011\tY%a\u0016\u0011\r=\tieOA)\u0013\r\ty\u0005\u0005\u0002\u0010!\u0006\u0014H/[1m\rVt7\r^5p]B1\u0011QFA\u001a\u0003'\u0002B!!\u0006\u0002V\u0011A\u00111HA\"\u0005\u0004\tY\u0002\u0003\u0005\u0002\u0012\u0005\r\u0003\u0019AA-!\u0011\t)\"a\u0017\u0005\u0011\u0005e\u00111\tb\u0001\u00037A\u0001bG\u0006\u0002\u0002\u0013\u0005\u0015q\f\u000b\u0006=\u0005\u0005\u00141\r\u0005\u0007c\u0005u\u0003\u0019A\u001a\t\re\ni\u00061\u0001<\u0011%\t9gCA\u0001\n\u0003\u000bI'A\u0004v]\u0006\u0004\b\u000f\\=\u0015\t\u0005-\u0014q\u000f\t\u0006\u001f\u00055\u0014\u0011O\u0005\u0004\u0003_\u0002\"AB(qi&|g\u000eE\u0003\u0010\u0003g\u001a4(C\u0002\u0002vA\u0011a\u0001V;qY\u0016\u0014\u0004\"CA=\u0003K\n\t\u00111\u0001\u001f\u0003\rAH\u0005\r\u0005\n\u0003{Z\u0011\u0011!C\u0005\u0003\u007f\n1B]3bIJ+7o\u001c7wKR\u0011\u0011\u0011\u0011\t\u0004=\u0006\r\u0015bAAC?\n1qJ\u00196fGR\u0004")
public class InversionFailure extends UnsupportedOperationException implements Product, Serializable
{
    private final Object failed;
    private final Throwable ex;
    
    public static Option<Tuple2<Object, Throwable>> unapply(final InversionFailure x$0) {
        return InversionFailure$.MODULE$.unapply(x$0);
    }
    
    public static InversionFailure apply(final Object failed, final Throwable ex) {
        return InversionFailure$.MODULE$.apply(failed, ex);
    }
    
    public static <A, B> PartialFunction<Throwable, Try<A>> partialFailure(final B b) {
        return InversionFailure$.MODULE$.partialFailure(b);
    }
    
    public static <A, B> Try<A> failedAttempt(final B b) {
        return InversionFailure$.MODULE$.failedAttempt(b);
    }
    
    public static <B> InversionFailure apply(final B b) {
        return InversionFailure$.MODULE$.apply(b);
    }
    
    public Object failed() {
        return this.failed;
    }
    
    public Throwable ex() {
        return this.ex;
    }
    
    public InversionFailure copy(final Object failed, final Throwable ex) {
        return new InversionFailure(failed, ex);
    }
    
    public Object copy$default$1() {
        return this.failed();
    }
    
    public Throwable copy$default$2() {
        return this.ex();
    }
    
    @Override
    public String productPrefix() {
        return "InversionFailure";
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
                o = this.ex();
                break;
            }
            case 0: {
                o = this.failed();
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
        return x$1 instanceof InversionFailure;
    }
    
    @Override
    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }
    
    @Override
    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof InversionFailure) {
                final InversionFailure \u0131nversionFailure = (InversionFailure)x$1;
                boolean b = false;
                Label_0092: {
                    Label_0091: {
                        if (BoxesRunTime.equals(this.failed(), \u0131nversionFailure.failed())) {
                            final Throwable ex = this.ex();
                            final Throwable ex2 = \u0131nversionFailure.ex();
                            if (ex == null) {
                                if (ex2 != null) {
                                    break Label_0091;
                                }
                            }
                            else if (!ex.equals(ex2)) {
                                break Label_0091;
                            }
                            if (\u0131nversionFailure.canEqual(this)) {
                                b = true;
                                break Label_0092;
                            }
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
    
    public InversionFailure(final Object failed, final Throwable ex) {
        this.failed = failed;
        this.ex = ex;
        super(new StringOps(Predef$.MODULE$.augmentString("Failed to invert: %s")).format(Predef$.MODULE$.genericWrapArray(new Object[] { failed })), ex);
        Product$class.$init$(this);
    }
}
