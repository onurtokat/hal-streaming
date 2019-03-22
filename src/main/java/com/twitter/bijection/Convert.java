// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.Product$class;
import scala.Option;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.Serializable;
import scala.Product;

@ScalaSignature(bytes = "\u0006\u0001\t\u0005b\u0001B\u0001\u0003\u0005&\u0011qaQ8om\u0016\u0014HO\u0003\u0002\u0004\t\u0005I!-\u001b6fGRLwN\u001c\u0006\u0003\u000b\u0019\tq\u0001^<jiR,'OC\u0001\b\u0003\r\u0019w.\\\u0002\u0001+\tQAd\u0005\u0003\u0001\u0017E!\u0002C\u0001\u0007\u0010\u001b\u0005i!\"\u0001\b\u0002\u000bM\u001c\u0017\r\\1\n\u0005Ai!AB!osZ\u000bG\u000e\u0005\u0002\r%%\u00111#\u0004\u0002\b!J|G-^2u!\taQ#\u0003\u0002\u0017\u001b\ta1+\u001a:jC2L'0\u00192mK\"A\u0001\u0004\u0001BK\u0002\u0013\u0005\u0011$A\u0001b+\u0005Q\u0002CA\u000e\u001d\u0019\u0001!Q!\b\u0001C\u0002y\u0011\u0011!Q\t\u0003?\t\u0002\"\u0001\u0004\u0011\n\u0005\u0005j!a\u0002(pi\"Lgn\u001a\t\u0003\u0019\rJ!\u0001J\u0007\u0003\u0007\u0005s\u0017\u0010\u0003\u0005'\u0001\tE\t\u0015!\u0003\u001b\u0003\t\t\u0007\u0005C\u0003)\u0001\u0011\u0005\u0011&\u0001\u0004=S:LGO\u0010\u000b\u0003U1\u00022a\u000b\u0001\u001b\u001b\u0005\u0011\u0001\"\u0002\r(\u0001\u0004Q\u0002\"\u0002\u0018\u0001\t\u0003y\u0013AA1t+\t\u0001$\u0007\u0006\u00022iA\u00111D\r\u0003\u0006g5\u0012\rA\b\u0002\u0002\u0005\")Q'\fa\u0002m\u0005!1m\u001c8w!\u0011YsGG\u0019\n\u0005a\u0012!AC\"p]Z,'o]5p]\"9!\bAA\u0001\n\u0003Y\u0014\u0001B2paf,\"\u0001P \u0015\u0005u\u0002\u0005cA\u0016\u0001}A\u00111d\u0010\u0003\u0006;e\u0012\rA\b\u0005\b1e\u0002\n\u00111\u0001?\u0011\u001d\u0011\u0005!%A\u0005\u0002\r\u000babY8qs\u0012\"WMZ1vYR$\u0013'\u0006\u0002E\u001fV\tQI\u000b\u0002\u001b\r.\nq\t\u0005\u0002I\u001b6\t\u0011J\u0003\u0002K\u0017\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0003\u00196\t!\"\u00198o_R\fG/[8o\u0013\tq\u0015JA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016$Q!H!C\u0002yAq!\u0015\u0001\u0002\u0002\u0013\u0005#+A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0002'B\u0011A+W\u0007\u0002+*\u0011akV\u0001\u0005Y\u0006twMC\u0001Y\u0003\u0011Q\u0017M^1\n\u0005i+&AB*ue&tw\rC\u0004]\u0001\u0005\u0005I\u0011A/\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0003y\u0003\"\u0001D0\n\u0005\u0001l!aA%oi\"9!\rAA\u0001\n\u0003\u0019\u0017A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0003E\u0011Dq!Z1\u0002\u0002\u0003\u0007a,A\u0002yIEBqa\u001a\u0001\u0002\u0002\u0013\u0005\u0003.A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\u0005I\u0007c\u00016nE5\t1N\u0003\u0002m\u001b\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\u00059\\'\u0001C%uKJ\fGo\u001c:\t\u000fA\u0004\u0011\u0011!C\u0001c\u0006A1-\u00198FcV\fG\u000e\u0006\u0002skB\u0011Ab]\u0005\u0003i6\u0011qAQ8pY\u0016\fg\u000eC\u0004f_\u0006\u0005\t\u0019\u0001\u0012\t\u000f]\u0004\u0011\u0011!C!q\u0006A\u0001.Y:i\u0007>$W\rF\u0001_\u0011\u001dQ\b!!A\u0005Bm\fa!Z9vC2\u001cHC\u0001:}\u0011\u001d)\u00170!AA\u0002\tBqA \u0001\u0002\u0002\u0013\u0005s0\u0001\u0005u_N#(/\u001b8h)\u0005\u0019v!CA\u0002\u0005\u0005\u0005\t\u0012AA\u0003\u0003\u001d\u0019uN\u001c<feR\u00042aKA\u0004\r!\t!!!A\t\u0002\u0005%1#BA\u0004\u0003\u0017!\u0002c\u0001\u0007\u0002\u000e%\u0019\u0011qB\u0007\u0003\r\u0005s\u0017PU3g\u0011\u001dA\u0013q\u0001C\u0001\u0003'!\"!!\u0002\t\u0011y\f9!!A\u0005F}D!\"!\u0007\u0002\b\u0005\u0005I\u0011QA\u000e\u0003\u0015\t\u0007\u000f\u001d7z+\u0011\ti\"a\t\u0015\t\u0005}\u0011Q\u0005\t\u0005W\u0001\t\t\u0003E\u0002\u001c\u0003G!a!HA\f\u0005\u0004q\u0002b\u0002\r\u0002\u0018\u0001\u0007\u0011\u0011\u0005\u0005\u000b\u0003S\t9!!A\u0005\u0002\u0006-\u0012aB;oCB\u0004H._\u000b\u0005\u0003[\t9\u0004\u0006\u0003\u00020\u0005e\u0002#\u0002\u0007\u00022\u0005U\u0012bAA\u001a\u001b\t1q\n\u001d;j_:\u00042aGA\u001c\t\u0019i\u0012q\u0005b\u0001=!Q\u00111HA\u0014\u0003\u0003\u0005\r!!\u0010\u0002\u0007a$\u0003\u0007\u0005\u0003,\u0001\u0005U\u0002BCA!\u0003\u000f\t\t\u0011\"\u0003\u0002D\u0005Y!/Z1e%\u0016\u001cx\u000e\u001c<f)\t\t)\u0005E\u0002U\u0003\u000fJ1!!\u0013V\u0005\u0019y%M[3di\"A\u0011QJA\u0004\t\u000b\ty%\u0001\u0007bg\u0012*\u0007\u0010^3og&|g.\u0006\u0004\u0002R\u0005]\u0013q\f\u000b\u0005\u0003'\n\t\u0007\u0006\u0003\u0002V\u0005e\u0003cA\u000e\u0002X\u001111'a\u0013C\u0002yAq!NA&\u0001\b\tY\u0006\u0005\u0004,o\u0005u\u0013Q\u000b\t\u00047\u0005}CAB\u000f\u0002L\t\u0007a\u0004\u0003\u0005\u0002d\u0005-\u0003\u0019AA3\u0003\u0015!C\u000f[5t!\u0011Y\u0003!!\u0018\t\u0015\u0005%\u0014qAA\u0001\n\u000b\tY'\u0001\bd_BLH%\u001a=uK:\u001c\u0018n\u001c8\u0016\r\u00055\u0014QOA@)\u0011\ty'!\u001f\u0015\t\u0005E\u0014q\u000f\t\u0005W\u0001\t\u0019\bE\u0002\u001c\u0003k\"a!HA4\u0005\u0004q\u0002\"\u0003\r\u0002hA\u0005\t\u0019AA:\u0011!\t\u0019'a\u001aA\u0002\u0005m\u0004\u0003B\u0016\u0001\u0003{\u00022aGA@\t\u0019i\u0012q\rb\u0001=!Q\u00111QA\u0004#\u0003%)!!\"\u00021\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%c\u0011*\u0007\u0010^3og&|g.\u0006\u0004\u0002\b\u0006M\u0015Q\u0012\u000b\u0005\u0003\u0013\u000byIK\u0002\u0002\f\u001a\u00032aGAG\t\u0019i\u0012\u0011\u0011b\u0001=!A\u00111MAA\u0001\u0004\t\t\n\u0005\u0003,\u0001\u0005-EAB\u000f\u0002\u0002\n\u0007a\u0004\u0003\u0006\u0002\u0018\u0006\u001d\u0011\u0011!C\u0003\u00033\u000bq\u0003\u001d:pIV\u001cG\u000f\u0015:fM&DH%\u001a=uK:\u001c\u0018n\u001c8\u0016\t\u0005m\u00151\u0015\u000b\u0004'\u0006u\u0005\u0002CA2\u0003+\u0003\r!a(\u0011\t-\u0002\u0011\u0011\u0015\t\u00047\u0005\rFAB\u000f\u0002\u0016\n\u0007a\u0004\u0003\u0006\u0002(\u0006\u001d\u0011\u0011!C\u0003\u0003S\u000ba\u0003\u001d:pIV\u001cG/\u0011:jif$S\r\u001f;f]NLwN\\\u000b\u0005\u0003W\u000b\u0019\fF\u0002_\u0003[C\u0001\"a\u0019\u0002&\u0002\u0007\u0011q\u0016\t\u0005W\u0001\t\t\fE\u0002\u001c\u0003g#a!HAS\u0005\u0004q\u0002BCA\\\u0003\u000f\t\t\u0011\"\u0002\u0002:\u0006A\u0002O]8ek\u000e$X\t\\3nK:$H%\u001a=uK:\u001c\u0018n\u001c8\u0016\t\u0005m\u0016q\u0019\u000b\u0005\u0003{\u000b\t\rF\u0002#\u0003\u007fC\u0001\"ZA[\u0003\u0003\u0005\rA\u0018\u0005\t\u0003G\n)\f1\u0001\u0002DB!1\u0006AAc!\rY\u0012q\u0019\u0003\u0007;\u0005U&\u0019\u0001\u0010\t\u0015\u0005-\u0017qAA\u0001\n\u000b\ti-A\rqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8sI\u0015DH/\u001a8tS>tW\u0003BAh\u0003/$2![Ai\u0011!\t\u0019'!3A\u0002\u0005M\u0007\u0003B\u0016\u0001\u0003+\u00042aGAl\t\u0019i\u0012\u0011\u001ab\u0001=!Q\u00111\\A\u0004\u0003\u0003%)!!8\u0002%\r\fg.R9vC2$S\r\u001f;f]NLwN\\\u000b\u0005\u0003?\fY\u000f\u0006\u0003\u0002b\u0006\u0015Hc\u0001:\u0002d\"AQ-!7\u0002\u0002\u0003\u0007!\u0005\u0003\u0005\u0002d\u0005e\u0007\u0019AAt!\u0011Y\u0003!!;\u0011\u0007m\tY\u000f\u0002\u0004\u001e\u00033\u0014\rA\b\u0005\u000b\u0003_\f9!!A\u0005\u0006\u0005E\u0018A\u00055bg\"\u001cu\u000eZ3%Kb$XM\\:j_:,B!a=\u0002|R\u0019\u00010!>\t\u0011\u0005\r\u0014Q\u001ea\u0001\u0003o\u0004Ba\u000b\u0001\u0002zB\u00191$a?\u0005\ru\tiO1\u0001\u001f\u0011)\ty0a\u0002\u0002\u0002\u0013\u0015!\u0011A\u0001\u0011KF,\u0018\r\\:%Kb$XM\\:j_:,BAa\u0001\u0003\u0010Q!!Q\u0001B\u0005)\r\u0011(q\u0001\u0005\tK\u0006u\u0018\u0011!a\u0001E!A\u00111MA\u007f\u0001\u0004\u0011Y\u0001\u0005\u0003,\u0001\t5\u0001cA\u000e\u0003\u0010\u00111Q$!@C\u0002yA!Ba\u0005\u0002\b\u0005\u0005IQ\u0001B\u000b\u0003I!xn\u0015;sS:<G%\u001a=uK:\u001c\u0018n\u001c8\u0016\t\t]!q\u0004\u000b\u0004\u007f\ne\u0001\u0002CA2\u0005#\u0001\rAa\u0007\u0011\t-\u0002!Q\u0004\t\u00047\t}AAB\u000f\u0003\u0012\t\u0007a\u0004")
public final class Convert<A> implements Product, Serializable
{
    private final A a;
    
    public static <A> String toString$extension(final A $this) {
        return Convert$.MODULE$.toString$extension($this);
    }
    
    public static <A> boolean equals$extension(final A $this, final Object x$1) {
        return Convert$.MODULE$.equals$extension($this, x$1);
    }
    
    public static <A> int hashCode$extension(final A $this) {
        return Convert$.MODULE$.hashCode$extension($this);
    }
    
    public static <A> boolean canEqual$extension(final A $this, final Object x$1) {
        return Convert$.MODULE$.canEqual$extension($this, x$1);
    }
    
    public static <A> Iterator<Object> productIterator$extension(final A $this) {
        return Convert$.MODULE$.productIterator$extension($this);
    }
    
    public static <A> Object productElement$extension(final A $this, final int x$1) {
        return Convert$.MODULE$.productElement$extension($this, x$1);
    }
    
    public static <A> int productArity$extension(final A $this) {
        return Convert$.MODULE$.productArity$extension($this);
    }
    
    public static <A> String productPrefix$extension(final A $this) {
        return Convert$.MODULE$.productPrefix$extension($this);
    }
    
    public static <A, A> A copy$default$1$extension(final A $this) {
        return Convert$.MODULE$.copy$default$1$extension($this);
    }
    
    public static Object copy$extension(final Object $this, final Object a) {
        return Convert$.MODULE$.copy$extension($this, a);
    }
    
    public static <B, A> B as$extension(final A $this, final Conversion<A, B> conv) {
        return Convert$.MODULE$.as$extension($this, conv);
    }
    
    public static <A> Option<A> unapply(final A x$0) {
        return Convert$.MODULE$.unapply(x$0);
    }
    
    public static Object apply(final Object a) {
        return Convert$.MODULE$.apply(a);
    }
    
    public A a() {
        return this.a;
    }
    
    public <B> B as(final Conversion<A, B> conv) {
        return Convert$.MODULE$.as$extension(this.a(), conv);
    }
    
    public <A> A copy(final A a) {
        return Convert$.MODULE$.copy$extension(this.a(), a);
    }
    
    public <A> A copy$default$1() {
        return Convert$.MODULE$.copy$default$1$extension(this.a());
    }
    
    @Override
    public String productPrefix() {
        return Convert$.MODULE$.productPrefix$extension(this.a());
    }
    
    @Override
    public int productArity() {
        return Convert$.MODULE$.productArity$extension(this.a());
    }
    
    @Override
    public Object productElement(final int x$1) {
        return Convert$.MODULE$.productElement$extension(this.a(), x$1);
    }
    
    @Override
    public Iterator<Object> productIterator() {
        return Convert$.MODULE$.productIterator$extension(this.a());
    }
    
    @Override
    public boolean canEqual(final Object x$1) {
        return Convert$.MODULE$.canEqual$extension(this.a(), x$1);
    }
    
    @Override
    public int hashCode() {
        return Convert$.MODULE$.hashCode$extension(this.a());
    }
    
    @Override
    public boolean equals(final Object x$1) {
        return Convert$.MODULE$.equals$extension(this.a(), x$1);
    }
    
    @Override
    public String toString() {
        return Convert$.MODULE$.toString$extension(this.a());
    }
    
    public Convert(final A a) {
        this.a = a;
        Product$class.$init$(this);
    }
}
