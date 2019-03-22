// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.Function1;

public abstract class Bijection$class
{
    public static Object invert(final Bijection $this, final Object b) {
        return $this.inverse().apply(b);
    }
    
    public static Bijection inverse(final Bijection $this) {
        return (Bijection)new Bijection$$anon.Bijection$$anon$1($this);
    }
    
    public static Bijection andThen(final Bijection $this, final Bijection g) {
        return (Bijection)new Bijection$$anon.Bijection$$anon$2($this, g);
    }
    
    public static Injection andThen(final Bijection $this, final Injection g) {
        return g.compose($this);
    }
    
    public static Function1 andThen(final Bijection $this, final Function1 g) {
        return g.compose($this.toFunction());
    }
    
    public static Bijection compose(final Bijection $this, final Bijection g) {
        return g.andThen($this);
    }
    
    public static Injection compose(final Bijection $this, final Injection g) {
        return g.andThen($this);
    }
    
    public static Function1 compose(final Bijection $this, final Function1 g) {
        return g.andThen($this.toFunction());
    }
    
    public static Function1 toFunction(final Bijection $this) {
        return new BijectionFn($this);
    }
    
    public static void $init$(final Bijection $this) {
    }
}
