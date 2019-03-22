// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.Function1;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes = "\u0006\u0001\u00013A!\u0001\u0002\u0001\u0013\t\t\u0012\nZ3oi&$\u0018PQ5kK\u000e$\u0018n\u001c8\u000b\u0005\r!\u0011!\u00032jU\u0016\u001cG/[8o\u0015\t)a!A\u0004uo&$H/\u001a:\u000b\u0003\u001d\t1aY8n\u0007\u0001)\"AC\f\u0014\u0007\u0001Y\u0011\u0003\u0005\u0002\r\u001f5\tQBC\u0001\u000f\u0003\u0015\u00198-\u00197b\u0013\t\u0001RB\u0001\u0004B]f\u0014VM\u001a\t\u0005%M)R#D\u0001\u0003\u0013\t!\"AA\u0005CS*,7\r^5p]B\u0011ac\u0006\u0007\u0001\t\u0015A\u0002A1\u0001\u001a\u0005\u0005\t\u0015C\u0001\u000e\u001e!\ta1$\u0003\u0002\u001d\u001b\t9aj\u001c;iS:<\u0007C\u0001\u0007\u001f\u0013\tyRBA\u0002B]fDQ!\t\u0001\u0005\u0002\t\na\u0001P5oSRtD#A\u0012\u0011\u0007I\u0001Q\u0003C\u0003&\u0001\u0011\u0005c%A\u0003baBd\u0017\u0010\u0006\u0002\u0016O!)\u0001\u0006\na\u0001+\u0005\t\u0011\rC\u0004+\u0001\t\u0007I\u0011I\u0016\u0002\u000f%tg/\u001a:tKV\t1\u0005\u0003\u0004.\u0001\u0001\u0006IaI\u0001\tS:4XM]:fA!)q\u0006\u0001C!a\u00059\u0011M\u001c3UQ\u0016tWCA\u00195)\t\u0011d\u0007\u0005\u0003\u0013'U\u0019\u0004C\u0001\f5\t\u0015)dF1\u0001\u001a\u0005\u0005!\u0006\"B\u001c/\u0001\u0004\u0011\u0014!A4\t\u000be\u0002A\u0011\t\u001e\u0002\u000f\r|W\u000e]8tKV\u00111H\u0010\u000b\u0003y}\u0002BAE\n>+A\u0011aC\u0010\u0003\u0006ka\u0012\r!\u0007\u0005\u0006oa\u0002\r\u0001\u0010")
public class IdentityBijection<A> implements Bijection<A, A>
{
    private final IdentityBijection<A> inverse;
    
    @Override
    public A invert(final A b) {
        return (A)Bijection$class.invert(this, b);
    }
    
    @Override
    public <C> Injection<A, C> andThen(final Injection<A, C> g) {
        return (Injection<A, C>)Bijection$class.andThen(this, g);
    }
    
    @Override
    public <C> Function1<A, C> andThen(final Function1<A, C> g) {
        return (Function1<A, C>)Bijection$class.andThen(this, g);
    }
    
    @Override
    public <T> Injection<T, A> compose(final Injection<T, A> g) {
        return (Injection<T, A>)Bijection$class.compose(this, g);
    }
    
    @Override
    public <T> Function1<T, A> compose(final Function1<T, A> g) {
        return (Function1<T, A>)Bijection$class.compose(this, g);
    }
    
    @Override
    public Function1<A, A> toFunction() {
        return (Function1<A, A>)Bijection$class.toFunction(this);
    }
    
    @Override
    public A apply(final A a) {
        return a;
    }
    
    @Override
    public IdentityBijection<A> inverse() {
        return this.inverse;
    }
    
    @Override
    public <T> Bijection<A, T> andThen(final Bijection<A, T> g) {
        return g;
    }
    
    @Override
    public <T> Bijection<T, A> compose(final Bijection<T, A> g) {
        return g;
    }
    
    public IdentityBijection() {
        Bijection$class.$init$(this);
        this.inverse = this;
    }
}
