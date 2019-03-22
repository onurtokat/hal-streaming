// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.Product;
import scala.runtime.ScalaRunTime$;
import scala.collection.Iterator;
import scala.runtime.BoxesRunTime;
import scala.Some;
import scala.None$;
import scala.Option;
import scala.Serializable;

public final class Convert$ implements Serializable
{
    public static final Convert$ MODULE$;
    
    static {
        new Convert$();
    }
    
    @Override
    public final String toString() {
        return "Convert";
    }
    
    public <A> A apply(final A a) {
        return a;
    }
    
    public <A> Option<A> unapply(final A x$0) {
        return (Option<A>)((new Convert(x$0) == null) ? None$.MODULE$ : new Some<Object>(x$0));
    }
    
    private Object readResolve() {
        return Convert$.MODULE$;
    }
    
    public final <B, A> B as$extension(final A $this, final Conversion<A, B> conv) {
        return conv.apply($this);
    }
    
    public final <A, A> A copy$extension(final A $this, final A a) {
        return a;
    }
    
    public final <A, A> A copy$default$1$extension(final A $this) {
        return $this;
    }
    
    public final <A> String productPrefix$extension(final A $this) {
        return "Convert";
    }
    
    public final <A> int productArity$extension(final A $this) {
        return 1;
    }
    
    public final <A> Object productElement$extension(final A $this, final int x$1) {
        switch (x$1) {
            default: {
                throw new IndexOutOfBoundsException(BoxesRunTime.boxToInteger(x$1).toString());
            }
            case 0: {
                return $this;
            }
        }
    }
    
    public final <A> Iterator<Object> productIterator$extension(final A $this) {
        return ScalaRunTime$.MODULE$.typedProductIterator(new Convert<Object>($this));
    }
    
    public final <A> boolean canEqual$extension(final A $this, final Object x$1) {
        return x$1 instanceof Object;
    }
    
    public final <A> int hashCode$extension(final A $this) {
        return $this.hashCode();
    }
    
    public final <A> boolean equals$extension(final A $this, final Object x$1) {
        return x$1 instanceof Convert && BoxesRunTime.equals($this, (x$1 == null) ? null : ((Convert)x$1).a());
    }
    
    public final <A> String toString$extension(final A $this) {
        return ScalaRunTime$.MODULE$._toString(new Convert<Object>($this));
    }
    
    private Convert$() {
        MODULE$ = this;
    }
}
