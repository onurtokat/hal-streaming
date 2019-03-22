// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.Function1;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes = "\u0006\u0001\u00013Q!\u0001\u0002\u0002\u0002%\u0011\u0011cU;cG2\f7o\u001d\"jU\u0016\u001cG/[8o\u0015\t\u0019A!A\u0005cS*,7\r^5p]*\u0011QAB\u0001\bi^LG\u000f^3s\u0015\u00059\u0011aA2p[\u000e\u0001Qc\u0001\u0006\u0018CM\u0019\u0001aC\t\u0011\u00051yQ\"A\u0007\u000b\u00039\tQa]2bY\u0006L!\u0001E\u0007\u0003\r\u0005s\u0017PU3g!\u0011\u00112#\u0006\u0011\u000e\u0003\tI!\u0001\u0006\u0002\u0003\u0013\tK'.Z2uS>t\u0007C\u0001\f\u0018\u0019\u0001!Q\u0001\u0007\u0001C\u0002e\u0011\u0011!Q\t\u00035u\u0001\"\u0001D\u000e\n\u0005qi!a\u0002(pi\"Lgn\u001a\t\u0003\u0019yI!aH\u0007\u0003\u0007\u0005s\u0017\u0010\u0005\u0002\u0017C\u0011)!\u0005\u0001b\u0001G\t\t!)\u0005\u0002\u001b+!AQ\u0005\u0001B\u0001B\u0003%a%A\u0002dY\n\u00042a\n\u0016!\u001d\ta\u0001&\u0003\u0002*\u001b\u00051\u0001K]3eK\u001aL!a\u000b\u0017\u0003\u000b\rc\u0017m]:\u000b\u0005%j\u0001\"\u0002\u0018\u0001\t\u0003y\u0013A\u0002\u001fj]&$h\b\u0006\u00021cA!!\u0003A\u000b!\u0011\u0015)S\u00061\u0001'\u0011\u0015\u0019\u0004A\"\u00055\u0003\u001d\t\u0007\u000f\u001d7zM:$\"\u0001I\u001b\t\u000bY\u0012\u0004\u0019A\u000b\u0002\u0003\u0005DQ\u0001\u000f\u0001\u0005\u0002e\nQ!\u00199qYf$\"\u0001\t\u001e\t\u000bY:\u0004\u0019A\u000b\t\u000bq\u0002A\u0011I\u001f\u0002\r%tg/\u001a:u)\t)b\bC\u0003@w\u0001\u0007\u0001%A\u0001c\u0001")
public abstract class SubclassBijection<A, B extends A> implements Bijection<A, B>
{
    private final Class<B> clb;
    
    @Override
    public Bijection<B, A> inverse() {
        return (Bijection<B, A>)Bijection$class.inverse(this);
    }
    
    @Override
    public <C> Bijection<A, C> andThen(final Bijection<B, C> g) {
        return (Bijection<A, C>)Bijection$class.andThen(this, g);
    }
    
    @Override
    public <C> Injection<A, C> andThen(final Injection<B, C> g) {
        return (Injection<A, C>)Bijection$class.andThen(this, g);
    }
    
    @Override
    public <C> Function1<A, C> andThen(final Function1<B, C> g) {
        return (Function1<A, C>)Bijection$class.andThen(this, g);
    }
    
    @Override
    public <T> Bijection<T, B> compose(final Bijection<T, A> g) {
        return (Bijection<T, B>)Bijection$class.compose(this, g);
    }
    
    @Override
    public <T> Injection<T, B> compose(final Injection<T, A> g) {
        return (Injection<T, B>)Bijection$class.compose(this, g);
    }
    
    @Override
    public <T> Function1<T, B> compose(final Function1<T, A> g) {
        return (Function1<T, B>)Bijection$class.compose(this, g);
    }
    
    @Override
    public Function1<A, B> toFunction() {
        return (Function1<A, B>)Bijection$class.toFunction(this);
    }
    
    public abstract B applyfn(final A p0);
    
    @Override
    public B apply(final A a) {
        return (B)(this.clb.isAssignableFrom(a.getClass()) ? a : this.applyfn(a));
    }
    
    @Override
    public A invert(final B b) {
        return b;
    }
    
    public SubclassBijection(final Class<B> clb) {
        this.clb = clb;
        Bijection$class.$init$(this);
    }
}
