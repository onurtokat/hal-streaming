// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

public final class NumberSystems$
{
    public static final NumberSystems$ MODULE$;
    private final Bijection<Object, String> binary;
    private final Bijection<Object, String> hexadecimal;
    private final Bijection<Object, String> octal;
    
    static {
        new NumberSystems$();
    }
    
    public Bijection<Object, String> binary() {
        return this.binary;
    }
    
    public Bijection<Object, String> hexadecimal() {
        return this.hexadecimal;
    }
    
    public Bijection<Object, String> octal() {
        return this.octal;
    }
    
    public Bijection<Object, NumberSystems.ArbitBaseString> arbitbase(final int base) {
        return (Bijection<Object, NumberSystems.ArbitBaseString>)new NumberSystems$$anon.NumberSystems$$anon$4(base);
    }
    
    private NumberSystems$() {
        MODULE$ = this;
        this.binary = (Bijection<Object, String>)new NumberSystems$$anon.NumberSystems$$anon$1();
        this.hexadecimal = (Bijection<Object, String>)new NumberSystems$$anon.NumberSystems$$anon$2();
        this.octal = (Bijection<Object, String>)new NumberSystems$$anon.NumberSystems$$anon$3();
    }
}
