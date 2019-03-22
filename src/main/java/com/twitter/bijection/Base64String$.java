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

public final class Base64String$ implements Serializable
{
    public static final Base64String$ MODULE$;
    private final Injection<String, String> unwrap;
    
    static {
        new Base64String$();
    }
    
    public Injection<String, String> unwrap() {
        return this.unwrap;
    }
    
    public String apply(final String str) {
        return str;
    }
    
    public Option<String> unapply(final String x$0) {
        return (Option<String>)((new Base64String(x$0) == null) ? None$.MODULE$ : new Some<Object>(x$0));
    }
    
    private Object readResolve() {
        return Base64String$.MODULE$;
    }
    
    public final String copy$extension(final String $this, final String str) {
        return str;
    }
    
    public final String copy$default$1$extension(final String $this) {
        return $this;
    }
    
    public final String productPrefix$extension(final String $this) {
        return "Base64String";
    }
    
    public final int productArity$extension(final String $this) {
        return 1;
    }
    
    public final Object productElement$extension(final String $this, final int x$1) {
        switch (x$1) {
            default: {
                throw new IndexOutOfBoundsException(BoxesRunTime.boxToInteger(x$1).toString());
            }
            case 0: {
                return $this;
            }
        }
    }
    
    public final Iterator<Object> productIterator$extension(final String $this) {
        return ScalaRunTime$.MODULE$.typedProductIterator(new Base64String($this));
    }
    
    public final boolean canEqual$extension(final String $this, final Object x$1) {
        return x$1 instanceof String;
    }
    
    public final int hashCode$extension(final String $this) {
        return $this.hashCode();
    }
    
    public final boolean equals$extension(final String $this, final Object x$1) {
        if (x$1 instanceof Base64String) {
            final String s = (x$1 == null) ? null : ((Base64String)x$1).str();
            boolean b = false;
            Label_0071: {
                Label_0070: {
                    if ($this == null) {
                        if (s != null) {
                            break Label_0070;
                        }
                    }
                    else if (!$this.equals(s)) {
                        break Label_0070;
                    }
                    b = true;
                    break Label_0071;
                }
                b = false;
            }
            if (b) {
                return true;
            }
        }
        return false;
    }
    
    public final String toString$extension(final String $this) {
        return ScalaRunTime$.MODULE$._toString(new Base64String($this));
    }
    
    private Base64String$() {
        MODULE$ = this;
        this.unwrap = (Injection<String, String>)new Base64String$$anon.Base64String$$anon$2();
    }
}
