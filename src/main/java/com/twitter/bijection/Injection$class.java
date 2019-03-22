// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.Function1;

public abstract class Injection$class
{
    public static Injection andThen(final Injection $this, final Injection g) {
        return (Injection)new Injection$$anon.Injection$$anon$1($this, g);
    }
    
    public static Injection andThen(final Injection $this, final Bijection bij) {
        return (Injection)new Injection$$anon.Injection$$anon$2($this, bij);
    }
    
    public static Function1 andThen(final Injection $this, final Function1 g) {
        return g.compose($this.toFunction());
    }
    
    public static Injection compose(final Injection $this, final Injection g) {
        return g.andThen($this);
    }
    
    public static Injection compose(final Injection $this, final Bijection bij) {
        return (Injection)new Injection$$anon.Injection$$anon$3($this, bij);
    }
    
    public static Function1 compose(final Injection $this, final Function1 g) {
        return g.andThen($this.toFunction());
    }
    
    public static Function1 toFunction(final Injection $this) {
        return new InjectionFn($this);
    }
    
    public static void $init$(final Injection $this) {
    }
}
