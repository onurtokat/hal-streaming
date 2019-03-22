// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.Product$class;
import scala.runtime.ScalaRunTime$;
import scala.collection.Iterator;
import scala.runtime.BoxesRunTime;
import scala.Option;
import scala.reflect.ScalaSignature;
import scala.Serializable;
import scala.Product;

@ScalaSignature(bytes = "\u0006\u0001\u0005uc\u0001B\u0001\u0003\u0001&\u0011qAU3wKJ\u001cXM\u0003\u0002\u0004\t\u0005I!-\u001b6fGRLwN\u001c\u0006\u0003\u000b\u0019\tq\u0001^<jiR,'OC\u0001\b\u0003\r\u0019w.\\\u0002\u0001+\rQq#I\n\u0006\u0001-\t2E\n\t\u0003\u0019=i\u0011!\u0004\u0006\u0002\u001d\u0005)1oY1mC&\u0011\u0001#\u0004\u0002\u0007\u0003:L(+\u001a4\u0011\tI\u0019R\u0003I\u0007\u0002\u0005%\u0011AC\u0001\u0002\u0012\u00136\u0004H.[2ji\nK'.Z2uS>t\u0007C\u0001\f\u0018\u0019\u0001!Q\u0001\u0007\u0001C\u0002e\u0011\u0011!Q\t\u00035u\u0001\"\u0001D\u000e\n\u0005qi!a\u0002(pi\"Lgn\u001a\t\u0003\u0019yI!aH\u0007\u0003\u0007\u0005s\u0017\u0010\u0005\u0002\u0017C\u0011)!\u0005\u0001b\u00013\t\t!\t\u0005\u0002\rI%\u0011Q%\u0004\u0002\b!J|G-^2u!\taq%\u0003\u0002)\u001b\ta1+\u001a:jC2L'0\u00192mK\"A!\u0006\u0001BK\u0002\u0013\u00051&A\u0002j]Z,\u0012\u0001\f\t\u0005%5\u0002S#\u0003\u0002/\u0005\tI!)\u001b6fGRLwN\u001c\u0005\ta\u0001\u0011\t\u0012)A\u0005Y\u0005!\u0011N\u001c<!\u0011\u0015\u0011\u0004\u0001\"\u00014\u0003\u0019a\u0014N\\5u}Q\u0011A'\u000e\t\u0005%\u0001)\u0002\u0005C\u0003+c\u0001\u0007A\u0006C\u0004\u0004\u0001\t\u0007I\u0011A\u001c\u0016\u0003a\u0002BAE\u0017\u0016A!1!\b\u0001Q\u0001\na\n!BY5kK\u000e$\u0018n\u001c8!\u0011\u001da\u0004!!A\u0005\u0002u\nAaY8qsV\u0019a(Q\"\u0015\u0005}\"\u0005\u0003\u0002\n\u0001\u0001\n\u0003\"AF!\u0005\u000baY$\u0019A\r\u0011\u0005Y\u0019E!\u0002\u0012<\u0005\u0004I\u0002b\u0002\u0016<!\u0003\u0005\r!\u0012\t\u0005%5\u0012\u0005\tC\u0004H\u0001E\u0005I\u0011\u0001%\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\u0019\u0011\nV+\u0016\u0003)S#\u0001L&,\u00031\u0003\"!\u0014*\u000e\u00039S!a\u0014)\u0002\u0013Ut7\r[3dW\u0016$'BA)\u000e\u0003)\tgN\\8uCRLwN\\\u0005\u0003':\u0013\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\t\u0015AbI1\u0001\u001a\t\u0015\u0011cI1\u0001\u001a\u0011\u001d9\u0006!!A\u0005Ba\u000bQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DX#A-\u0011\u0005i{V\"A.\u000b\u0005qk\u0016\u0001\u00027b]\u001eT\u0011AX\u0001\u0005U\u00064\u0018-\u0003\u0002a7\n11\u000b\u001e:j]\u001eDqA\u0019\u0001\u0002\u0002\u0013\u00051-\u0001\u0007qe>$Wo\u0019;Be&$\u00180F\u0001e!\taQ-\u0003\u0002g\u001b\t\u0019\u0011J\u001c;\t\u000f!\u0004\u0011\u0011!C\u0001S\u0006q\u0001O]8ek\u000e$X\t\\3nK:$HCA\u000fk\u0011\u001dYw-!AA\u0002\u0011\f1\u0001\u001f\u00132\u0011\u001di\u0007!!A\u0005B9\fq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0002_B\u0019\u0001o]\u000f\u000e\u0003ET!A]\u0007\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0002uc\nA\u0011\n^3sCR|'\u000fC\u0004w\u0001\u0005\u0005I\u0011A<\u0002\u0011\r\fg.R9vC2$\"\u0001_>\u0011\u00051I\u0018B\u0001>\u000e\u0005\u001d\u0011un\u001c7fC:Dqa[;\u0002\u0002\u0003\u0007Q\u0004C\u0004~\u0001\u0005\u0005I\u0011\t@\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012\u0001\u001a\u0005\n\u0003\u0003\u0001\u0011\u0011!C!\u0003\u0007\t\u0001\u0002^8TiJLgn\u001a\u000b\u00023\"I\u0011q\u0001\u0001\u0002\u0002\u0013\u0005\u0013\u0011B\u0001\u0007KF,\u0018\r\\:\u0015\u0007a\fY\u0001\u0003\u0005l\u0003\u000b\t\t\u00111\u0001\u001e\u000f%\tyAAA\u0001\u0012\u0003\t\t\"A\u0004SKZ,'o]3\u0011\u0007I\t\u0019B\u0002\u0005\u0002\u0005\u0005\u0005\t\u0012AA\u000b'\u0011\t\u0019b\u0003\u0014\t\u000fI\n\u0019\u0002\"\u0001\u0002\u001aQ\u0011\u0011\u0011\u0003\u0005\u000b\u0003\u0003\t\u0019\"!A\u0005F\u0005\r\u0001BCA\u0010\u0003'\t\t\u0011\"!\u0002\"\u0005)\u0011\r\u001d9msV1\u00111EA\u0015\u0003[!B!!\n\u00020A1!\u0003AA\u0014\u0003W\u00012AFA\u0015\t\u0019A\u0012Q\u0004b\u00013A\u0019a#!\f\u0005\r\t\niB1\u0001\u001a\u0011\u001dQ\u0013Q\u0004a\u0001\u0003c\u0001bAE\u0017\u0002,\u0005\u001d\u0002BCA\u001b\u0003'\t\t\u0011\"!\u00028\u00059QO\\1qa2LXCBA\u001d\u0003\u0013\n)\u0005\u0006\u0003\u0002<\u0005-\u0003#\u0002\u0007\u0002>\u0005\u0005\u0013bAA \u001b\t1q\n\u001d;j_:\u0004bAE\u0017\u0002D\u0005\u001d\u0003c\u0001\f\u0002F\u00111!%a\rC\u0002e\u00012AFA%\t\u0019A\u00121\u0007b\u00013!Q\u0011QJA\u001a\u0003\u0003\u0005\r!a\u0014\u0002\u0007a$\u0003\u0007\u0005\u0004\u0013\u0001\u0005\u001d\u00131\t\u0005\u000b\u0003'\n\u0019\"!A\u0005\n\u0005U\u0013a\u0003:fC\u0012\u0014Vm]8mm\u0016$\"!a\u0016\u0011\u0007i\u000bI&C\u0002\u0002\\m\u0013aa\u00142kK\u000e$\b")
public class Reverse<A, B> implements ImplicitBijection<A, B>, Product, Serializable
{
    private final Bijection<B, A> inv;
    private final Bijection<A, B> bijection;
    
    public static <A, B> Option<Bijection<B, A>> unapply(final Reverse<A, B> x$0) {
        return Reverse$.MODULE$.unapply(x$0);
    }
    
    @Override
    public B apply(final A a) {
        return (B)ImplicitBijection$class.apply(this, a);
    }
    
    @Override
    public A invert(final B b) {
        return (A)ImplicitBijection$class.invert(this, b);
    }
    
    public Bijection<B, A> inv() {
        return this.inv;
    }
    
    @Override
    public Bijection<A, B> bijection() {
        return this.bijection;
    }
    
    public <A, B> Reverse<A, B> copy(final Bijection<B, A> inv) {
        return new Reverse<A, B>(inv);
    }
    
    public <A, B> Bijection<B, A> copy$default$1() {
        return this.inv();
    }
    
    @Override
    public String productPrefix() {
        return "Reverse";
    }
    
    @Override
    public int productArity() {
        return 1;
    }
    
    @Override
    public Object productElement(final int x$1) {
        switch (x$1) {
            default: {
                throw new IndexOutOfBoundsException(BoxesRunTime.boxToInteger(x$1).toString());
            }
            case 0: {
                return this.inv();
            }
        }
    }
    
    @Override
    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }
    
    @Override
    public boolean canEqual(final Object x$1) {
        return x$1 instanceof Reverse;
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
            if (x$1 instanceof Reverse) {
                final Reverse reverse = (Reverse)x$1;
                final Bijection<B, A> inv = this.inv();
                final Bijection inv2 = reverse.inv();
                boolean b = false;
                Label_0077: {
                    Label_0076: {
                        if (inv == null) {
                            if (inv2 != null) {
                                break Label_0076;
                            }
                        }
                        else if (!inv.equals(inv2)) {
                            break Label_0076;
                        }
                        if (reverse.canEqual(this)) {
                            b = true;
                            break Label_0077;
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
    
    public Reverse(final Bijection<B, A> inv) {
        this.inv = inv;
        ImplicitBijection$class.$init$(this);
        Product$class.$init$(this);
        this.bijection = inv.inverse();
    }
}
