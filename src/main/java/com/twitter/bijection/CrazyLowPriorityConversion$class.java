// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

public abstract class CrazyLowPriorityConversion$class
{
    public static Conversion fromInjectionOptInverse(final CrazyLowPriorityConversion $this, final Injection inj) {
        return (Conversion)new CrazyLowPriorityConversion$$anon.CrazyLowPriorityConversion$$anon$1($this, inj);
    }
    
    public static Conversion fromInjectionInverse(final CrazyLowPriorityConversion $this, final Injection inj) {
        return (Conversion)new CrazyLowPriorityConversion$$anon.CrazyLowPriorityConversion$$anon$2($this, inj);
    }
    
    public static void $init$(final CrazyLowPriorityConversion $this) {
    }
}
