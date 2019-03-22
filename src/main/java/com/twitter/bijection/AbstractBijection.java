// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.Function1;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes = "\u0006\u0001E2Q!\u0001\u0002\u0002\u0002%\u0011\u0011#\u00112tiJ\f7\r\u001e\"jU\u0016\u001cG/[8o\u0015\t\u0019A!A\u0005cS*,7\r^5p]*\u0011QAB\u0001\bi^LG\u000f^3s\u0015\u00059\u0011aA2p[\u000e\u0001Qc\u0001\u0006\u0018CM\u0019\u0001aC\t\u0011\u00051yQ\"A\u0007\u000b\u00039\tQa]2bY\u0006L!\u0001E\u0007\u0003\r\u0005s\u0017PU3g!\u0011\u00112#\u0006\u0011\u000e\u0003\tI!\u0001\u0006\u0002\u0003\u0013\tK'.Z2uS>t\u0007C\u0001\f\u0018\u0019\u0001!Q\u0001\u0007\u0001C\u0002e\u0011\u0011!Q\t\u00035u\u0001\"\u0001D\u000e\n\u0005qi!a\u0002(pi\"Lgn\u001a\t\u0003\u0019yI!aH\u0007\u0003\u0007\u0005s\u0017\u0010\u0005\u0002\u0017C\u0011)!\u0005\u0001b\u00013\t\t!\tC\u0003%\u0001\u0011\u0005Q%\u0001\u0004=S:LGO\u0010\u000b\u0002MA!!\u0003A\u000b!\u0011\u0015A\u0003A\"\u0011*\u0003\u0015\t\u0007\u000f\u001d7z)\t\u0001#\u0006C\u0003,O\u0001\u0007Q#A\u0001b\u0011\u0015i\u0003A\"\u0011/\u0003\u0019IgN^3siR\u0011Qc\f\u0005\u0006a1\u0002\r\u0001I\u0001\u0002E\u0002")
public abstract class AbstractBijection<A, B> implements Bijection<A, B>
{
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
    
    @Override
    public abstract B apply(final A p0);
    
    @Override
    public abstract A invert(final B p0);
    
    public AbstractBijection() {
        Bijection$class.$init$(this);
    }
}
