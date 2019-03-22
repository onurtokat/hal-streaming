// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.Symbol;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes = "\u0006\u000112q!\u0001\u0002\u0011\u0002\u0007\u0005\u0011B\u0001\tTiJLgn\u001a\"jU\u0016\u001cG/[8og*\u00111\u0001B\u0001\nE&TWm\u0019;j_:T!!\u0002\u0004\u0002\u000fQ<\u0018\u000e\u001e;fe*\tq!A\u0002d_6\u001c\u0001aE\u0002\u0001\u0015A\u0001\"a\u0003\b\u000e\u00031Q\u0011!D\u0001\u0006g\u000e\fG.Y\u0005\u0003\u001f1\u0011a!\u00118z%\u00164\u0007CA\t\u0013\u001b\u0005\u0011\u0011BA\n\u0003\u0005EqU/\\3sS\u000e\u0014\u0015N[3di&|gn\u001d\u0005\u0006+\u0001!\tAF\u0001\u0007I%t\u0017\u000e\u001e\u0013\u0015\u0003]\u0001\"a\u0003\r\n\u0005ea!\u0001B+oSRDqa\u0007\u0001C\u0002\u0013\rA$A\u0007ts6\u0014w\u000e\u001c\u001aTiJLgnZ\u000b\u0002;A!\u0011C\b\u0011$\u0013\ty\"AA\u0005CS*,7\r^5p]B\u00111\"I\u0005\u0003E1\u0011aaU=nE>d\u0007C\u0001\u0013(\u001d\tYQ%\u0003\u0002'\u0019\u00051\u0001K]3eK\u001aL!\u0001K\u0015\u0003\rM#(/\u001b8h\u0015\t1C\u0002\u0003\u0004,\u0001\u0001\u0006I!H\u0001\u000fgfl'm\u001c73'R\u0014\u0018N\\4!\u0001")
public interface StringBijections extends NumericBijections
{
    void com$twitter$bijection$StringBijections$_setter_$symbol2String_$eq(final Bijection p0);
    
    Bijection<Symbol, String> symbol2String();
}
