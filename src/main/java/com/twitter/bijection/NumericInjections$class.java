// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

public abstract class NumericInjections$class
{
    public static Injection float2BigEndian(final NumericInjections $this) {
        return Injection$.MODULE$.fromBijection(Bijection$.MODULE$.float2IntIEEE754()).andThen($this.int2BigEndian());
    }
    
    public static Injection double2BigEndian(final NumericInjections $this) {
        return Injection$.MODULE$.fromBijection(Bijection$.MODULE$.double2LongIEEE754()).andThen($this.long2BigEndian());
    }
    
    public static void $init$(final NumericInjections $this) {
        $this.com$twitter$bijection$NumericInjections$_setter_$byte2Short_$eq((Injection)new NumericInjections$$anon.NumericInjections$$anon$1($this));
        $this.com$twitter$bijection$NumericInjections$_setter_$short2Int_$eq((Injection)new NumericInjections$$anon.NumericInjections$$anon$2($this));
        $this.com$twitter$bijection$NumericInjections$_setter_$int2Long_$eq((Injection)new NumericInjections$$anon.NumericInjections$$anon$3($this));
        $this.com$twitter$bijection$NumericInjections$_setter_$long2BigInt_$eq((Injection)new NumericInjections$$anon.NumericInjections$$anon$4($this));
        $this.com$twitter$bijection$NumericInjections$_setter_$float2Double_$eq((Injection)new NumericInjections$$anon.NumericInjections$$anon$5($this));
        $this.com$twitter$bijection$NumericInjections$_setter_$int2Double_$eq((Injection)new NumericInjections$$anon.NumericInjections$$anon$6($this));
        $this.com$twitter$bijection$NumericInjections$_setter_$byte2String_$eq((Injection)new NumericInjections$$anon.NumericInjections$$anon$7($this));
        $this.com$twitter$bijection$NumericInjections$_setter_$jbyte2String_$eq((Injection)new NumericInjections$$anon.NumericInjections$$anon$8($this));
        $this.com$twitter$bijection$NumericInjections$_setter_$short2String_$eq((Injection)new NumericInjections$$anon.NumericInjections$$anon$9($this));
        $this.com$twitter$bijection$NumericInjections$_setter_$jshort2String_$eq((Injection)new NumericInjections$$anon.NumericInjections$$anon$10($this));
        $this.com$twitter$bijection$NumericInjections$_setter_$int2String_$eq((Injection)new NumericInjections$$anon.NumericInjections$$anon$11($this));
        $this.com$twitter$bijection$NumericInjections$_setter_$jint2String_$eq((Injection)new NumericInjections$$anon.NumericInjections$$anon$12($this));
        $this.com$twitter$bijection$NumericInjections$_setter_$long2String_$eq((Injection)new NumericInjections$$anon.NumericInjections$$anon$13($this));
        $this.com$twitter$bijection$NumericInjections$_setter_$jlong2String_$eq((Injection)new NumericInjections$$anon.NumericInjections$$anon$14($this));
        $this.com$twitter$bijection$NumericInjections$_setter_$float2String_$eq((Injection)new NumericInjections$$anon.NumericInjections$$anon$15($this));
        $this.com$twitter$bijection$NumericInjections$_setter_$jfloat2String_$eq((Injection)new NumericInjections$$anon.NumericInjections$$anon$16($this));
        $this.com$twitter$bijection$NumericInjections$_setter_$double2String_$eq((Injection)new NumericInjections$$anon.NumericInjections$$anon$17($this));
        $this.com$twitter$bijection$NumericInjections$_setter_$jdouble2String_$eq((Injection)new NumericInjections$$anon.NumericInjections$$anon$18($this));
        $this.com$twitter$bijection$NumericInjections$_setter_$short2BigEndian_$eq((Injection)new NumericInjections$$anon.NumericInjections$$anon$19($this));
        $this.com$twitter$bijection$NumericInjections$_setter_$int2BigEndian_$eq((Injection)new NumericInjections$$anon.NumericInjections$$anon$20($this));
        $this.com$twitter$bijection$NumericInjections$_setter_$long2BigEndian_$eq((Injection)new NumericInjections$$anon.NumericInjections$$anon$21($this));
    }
}
