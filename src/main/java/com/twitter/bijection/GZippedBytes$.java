// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.Product;
import scala.runtime.ScalaRunTime$;
import scala.collection.Iterator;
import scala.runtime.BoxesRunTime;
import scala.Predef$;
import scala.Some;
import scala.None$;
import scala.Option;
import scala.Serializable;
import scala.runtime.AbstractFunction1;

public final class GZippedBytes$ extends AbstractFunction1<byte[], byte[]> implements Serializable
{
    public static final GZippedBytes$ MODULE$;
    
    static {
        new GZippedBytes$();
    }
    
    @Override
    public final String toString() {
        return "GZippedBytes";
    }
    
    @Override
    public byte[] apply(final byte[] bytes) {
        return bytes;
    }
    
    public Option<byte[]> unapply(final byte[] x$0) {
        return (Option<byte[]>)((new GZippedBytes(x$0) == null) ? None$.MODULE$ : new Some<Object>(x$0));
    }
    
    private Object readResolve() {
        return GZippedBytes$.MODULE$;
    }
    
    public final String toString$extension(final byte[] $this) {
        return Predef$.MODULE$.byteArrayOps($this).mkString("GZippedBytes(", ", ", ")");
    }
    
    public final byte[] copy$extension(final byte[] $this, final byte[] bytes) {
        return bytes;
    }
    
    public final byte[] copy$default$1$extension(final byte[] $this) {
        return $this;
    }
    
    public final String productPrefix$extension(final byte[] $this) {
        return "GZippedBytes";
    }
    
    public final int productArity$extension(final byte[] $this) {
        return 1;
    }
    
    public final Object productElement$extension(final byte[] $this, final int x$1) {
        switch (x$1) {
            default: {
                throw new IndexOutOfBoundsException(BoxesRunTime.boxToInteger(x$1).toString());
            }
            case 0: {
                return $this;
            }
        }
    }
    
    public final Iterator<Object> productIterator$extension(final byte[] $this) {
        return ScalaRunTime$.MODULE$.typedProductIterator(new GZippedBytes($this));
    }
    
    public final boolean canEqual$extension(final byte[] $this, final Object x$1) {
        return x$1 instanceof byte[];
    }
    
    public final int hashCode$extension(final byte[] $this) {
        return $this.hashCode();
    }
    
    public final boolean equals$extension(final byte[] $this, final Object x$1) {
        return x$1 instanceof GZippedBytes && $this == ((x$1 == null) ? null : ((GZippedBytes)x$1).bytes());
    }
    
    private GZippedBytes$() {
        MODULE$ = this;
    }
}
