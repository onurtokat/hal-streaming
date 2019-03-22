// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

public abstract class StringInjections$class
{
    public static Injection withEncoding(final StringInjections $this, final String encoding) {
        return (Injection)new StringInjections$$anon.StringInjections$$anon$1($this, encoding);
    }
    
    public static void $init$(final StringInjections $this) {
        $this.com$twitter$bijection$StringInjections$_setter_$utf8_$eq($this.withEncoding("UTF-8"));
        $this.com$twitter$bijection$StringInjections$_setter_$url2String_$eq((Injection)new StringInjections$$anon.StringInjections$$anon$2($this));
        $this.com$twitter$bijection$StringInjections$_setter_$uuid2String_$eq((Injection)new StringInjections$$anon.StringInjections$$anon$3($this));
        $this.com$twitter$bijection$StringInjections$_setter_$string2UrlEncodedString_$eq((Injection)new StringInjections$$anon.StringInjections$$anon$4($this));
    }
}
