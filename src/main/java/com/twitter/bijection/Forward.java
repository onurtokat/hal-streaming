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

@ScalaSignature(bytes = "\u0006\u0001\u0005Ec\u0001B\u0001\u0003\u0001&\u0011qAR8so\u0006\u0014HM\u0003\u0002\u0004\t\u0005I!-\u001b6fGRLwN\u001c\u0006\u0003\u000b\u0019\tq\u0001^<jiR,'OC\u0001\b\u0003\r\u0019w.\\\u0002\u0001+\rQq#I\n\u0006\u0001-\t2E\n\t\u0003\u0019=i\u0011!\u0004\u0006\u0002\u001d\u0005)1oY1mC&\u0011\u0001#\u0004\u0002\u0007\u0003:L(+\u001a4\u0011\tI\u0019R\u0003I\u0007\u0002\u0005%\u0011AC\u0001\u0002\u0012\u00136\u0004H.[2ji\nK'.Z2uS>t\u0007C\u0001\f\u0018\u0019\u0001!Q\u0001\u0007\u0001C\u0002e\u0011\u0011!Q\t\u00035u\u0001\"\u0001D\u000e\n\u0005qi!a\u0002(pi\"Lgn\u001a\t\u0003\u0019yI!aH\u0007\u0003\u0007\u0005s\u0017\u0010\u0005\u0002\u0017C\u0011)!\u0005\u0001b\u00013\t\t!\t\u0005\u0002\rI%\u0011Q%\u0004\u0002\b!J|G-^2u!\taq%\u0003\u0002)\u001b\ta1+\u001a:jC2L'0\u00192mK\"A1\u0001\u0001BK\u0002\u0013\u0005#&F\u0001,!\u0011\u0011B&\u0006\u0011\n\u00055\u0012!!\u0003\"jU\u0016\u001cG/[8o\u0011!y\u0003A!E!\u0002\u0013Y\u0013A\u00032jU\u0016\u001cG/[8oA!)\u0011\u0007\u0001C\u0001e\u00051A(\u001b8jiz\"\"a\r\u001b\u0011\tI\u0001Q\u0003\t\u0005\u0006\u0007A\u0002\ra\u000b\u0005\bm\u0001\t\t\u0011\"\u00018\u0003\u0011\u0019w\u000e]=\u0016\u0007aZT\b\u0006\u0002:}A!!\u0003\u0001\u001e=!\t12\bB\u0003\u0019k\t\u0007\u0011\u0004\u0005\u0002\u0017{\u0011)!%\u000eb\u00013!91!\u000eI\u0001\u0002\u0004y\u0004\u0003\u0002\n-uqBq!\u0011\u0001\u0012\u0002\u0013\u0005!)\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0007\rsu*F\u0001EU\tYSiK\u0001G!\t9E*D\u0001I\u0015\tI%*A\u0005v]\u000eDWmY6fI*\u00111*D\u0001\u000bC:tw\u000e^1uS>t\u0017BA'I\u0005E)hn\u00195fG.,GMV1sS\u0006t7-\u001a\u0003\u00061\u0001\u0013\r!\u0007\u0003\u0006E\u0001\u0013\r!\u0007\u0005\b#\u0002\t\t\u0011\"\u0011S\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\t1\u000b\u0005\u0002U36\tQK\u0003\u0002W/\u0006!A.\u00198h\u0015\u0005A\u0016\u0001\u00026bm\u0006L!AW+\u0003\rM#(/\u001b8h\u0011\u001da\u0006!!A\u0005\u0002u\u000bA\u0002\u001d:pIV\u001cG/\u0011:jif,\u0012A\u0018\t\u0003\u0019}K!\u0001Y\u0007\u0003\u0007%sG\u000fC\u0004c\u0001\u0005\u0005I\u0011A2\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u0011Q\u0004\u001a\u0005\bK\u0006\f\t\u00111\u0001_\u0003\rAH%\r\u0005\bO\u0002\t\t\u0011\"\u0011i\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014X#A5\u0011\u0007)lW$D\u0001l\u0015\taW\"\u0001\u0006d_2dWm\u0019;j_:L!A\\6\u0003\u0011%#XM]1u_JDq\u0001\u001d\u0001\u0002\u0002\u0013\u0005\u0011/\u0001\u0005dC:,\u0015/^1m)\t\u0011X\u000f\u0005\u0002\rg&\u0011A/\u0004\u0002\b\u0005>|G.Z1o\u0011\u001d)w.!AA\u0002uAqa\u001e\u0001\u0002\u0002\u0013\u0005\u00030\u0001\u0005iCND7i\u001c3f)\u0005q\u0006b\u0002>\u0001\u0003\u0003%\te_\u0001\ti>\u001cFO]5oOR\t1\u000bC\u0004~\u0001\u0005\u0005I\u0011\t@\u0002\r\u0015\fX/\u00197t)\t\u0011x\u0010C\u0004fy\u0006\u0005\t\u0019A\u000f\b\u0013\u0005\r!!!A\t\u0002\u0005\u0015\u0011a\u0002$pe^\f'\u000f\u001a\t\u0004%\u0005\u001da\u0001C\u0001\u0003\u0003\u0003E\t!!\u0003\u0014\t\u0005\u001d1B\n\u0005\bc\u0005\u001dA\u0011AA\u0007)\t\t)\u0001\u0003\u0005{\u0003\u000f\t\t\u0011\"\u0012|\u0011)\t\u0019\"a\u0002\u0002\u0002\u0013\u0005\u0015QC\u0001\u0006CB\u0004H._\u000b\u0007\u0003/\ti\"!\t\u0015\t\u0005e\u00111\u0005\t\u0007%\u0001\tY\"a\b\u0011\u0007Y\ti\u0002\u0002\u0004\u0019\u0003#\u0011\r!\u0007\t\u0004-\u0005\u0005BA\u0002\u0012\u0002\u0012\t\u0007\u0011\u0004C\u0004\u0004\u0003#\u0001\r!!\n\u0011\rIa\u00131DA\u0010\u0011)\tI#a\u0002\u0002\u0002\u0013\u0005\u00151F\u0001\bk:\f\u0007\u000f\u001d7z+\u0019\ti#!\u000f\u0002>Q!\u0011qFA !\u0015a\u0011\u0011GA\u001b\u0013\r\t\u0019$\u0004\u0002\u0007\u001fB$\u0018n\u001c8\u0011\rIa\u0013qGA\u001e!\r1\u0012\u0011\b\u0003\u00071\u0005\u001d\"\u0019A\r\u0011\u0007Y\ti\u0004\u0002\u0004#\u0003O\u0011\r!\u0007\u0005\u000b\u0003\u0003\n9#!AA\u0002\u0005\r\u0013a\u0001=%aA1!\u0003AA\u001c\u0003wA!\"a\u0012\u0002\b\u0005\u0005I\u0011BA%\u0003-\u0011X-\u00193SKN|GN^3\u0015\u0005\u0005-\u0003c\u0001+\u0002N%\u0019\u0011qJ+\u0003\r=\u0013'.Z2u\u0001")
public class Forward<A, B> implements ImplicitBijection<A, B>, Product, Serializable
{
    private final Bijection<A, B> bijection;
    
    public static <A, B> Option<Bijection<A, B>> unapply(final Forward<A, B> x$0) {
        return Forward$.MODULE$.unapply(x$0);
    }
    
    @Override
    public B apply(final A a) {
        return (B)ImplicitBijection$class.apply(this, a);
    }
    
    @Override
    public A invert(final B b) {
        return (A)ImplicitBijection$class.invert(this, b);
    }
    
    @Override
    public Bijection<A, B> bijection() {
        return this.bijection;
    }
    
    public <A, B> Forward<A, B> copy(final Bijection<A, B> bijection) {
        return new Forward<A, B>(bijection);
    }
    
    public <A, B> Bijection<A, B> copy$default$1() {
        return this.bijection();
    }
    
    @Override
    public String productPrefix() {
        return "Forward";
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
                return this.bijection();
            }
        }
    }
    
    @Override
    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }
    
    @Override
    public boolean canEqual(final Object x$1) {
        return x$1 instanceof Forward;
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
            if (x$1 instanceof Forward) {
                final Forward forward = (Forward)x$1;
                final Bijection<A, B> bijection = this.bijection();
                final Bijection bijection2 = forward.bijection();
                boolean b = false;
                Label_0077: {
                    Label_0076: {
                        if (bijection == null) {
                            if (bijection2 != null) {
                                break Label_0076;
                            }
                        }
                        else if (!bijection.equals(bijection2)) {
                            break Label_0076;
                        }
                        if (forward.canEqual(this)) {
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
    
    public Forward(final Bijection<A, B> bijection) {
        this.bijection = bijection;
        ImplicitBijection$class.$init$(this);
        Product$class.$init$(this);
    }
}
