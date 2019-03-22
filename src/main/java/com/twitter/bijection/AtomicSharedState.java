// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import java.util.concurrent.atomic.AtomicReference;
import scala.Function0;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes = "\u0006\u0001u2Q!\u0001\u0002\u0001\u0005!\u0011\u0011#\u0011;p[&\u001c7\u000b[1sK\u0012\u001cF/\u0019;f\u0015\t\u0019A!A\u0005cS*,7\r^5p]*\u0011QAB\u0001\bi^LG\u000f^3s\u0015\u00059\u0011aA2p[V\u0011\u0011\u0002G\n\u0003\u0001)\u0001\"a\u0003\b\u000e\u00031Q\u0011!D\u0001\u0006g\u000e\fG.Y\u0005\u0003\u001f1\u0011a!\u00118z%\u00164\u0007\u0002C\t\u0001\u0005\u0003\u0005\u000b\u0011B\n\u0002\t\r|gn]\u0002\u0001!\rYACF\u0005\u0003+1\u0011\u0011BR;oGRLwN\u001c\u0019\u0011\u0005]AB\u0002\u0001\u0003\u00063\u0001\u0011\rA\u0007\u0002\u0002)F\u00111D\u0003\t\u0003\u0017qI!!\b\u0007\u0003\u000f9{G\u000f[5oO\")q\u0004\u0001C\u0001A\u00051A(\u001b8jiz\"\"!I\u0012\u0011\u0007\t\u0002a#D\u0001\u0003\u0011\u0015\tb\u00041\u0001\u0014\u0011\u0019)\u0003\u0001)A\u0005M\u0005\u0019!/\u001a4\u0011\u0007\u001d\u0002d#D\u0001)\u0015\tI#&\u0001\u0004bi>l\u0017n\u0019\u0006\u0003W1\n!bY8oGV\u0014(/\u001a8u\u0015\tic&\u0001\u0003vi&d'\"A\u0018\u0002\t)\fg/Y\u0005\u0003c!\u0012q\"\u0011;p[&\u001c'+\u001a4fe\u0016t7-\u001a\u0005\u0006g\u0001!\t\u0001N\u0001\u0004O\u0016$X#\u0001\f\t\u000bY\u0002A\u0011A\u001c\u0002\u000fI,G.Z1tKR\u0011\u0001h\u000f\t\u0003\u0017eJ!A\u000f\u0007\u0003\tUs\u0017\u000e\u001e\u0005\u0006yU\u0002\rAF\u0001\u0002i\u0002")
public class AtomicSharedState<T>
{
    private final Function0<T> cons;
    private final AtomicReference<T> ref;
    
    public T get() {
        final Object borrow = this.ref.getAndSet(null);
        return (T)((borrow == null) ? this.cons.apply() : borrow);
    }
    
    public void release(final T t) {
        this.ref.lazySet(t);
    }
    
    public AtomicSharedState(final Function0<T> cons) {
        this.cons = cons;
        this.ref = new AtomicReference<T>(cons.apply());
    }
}
