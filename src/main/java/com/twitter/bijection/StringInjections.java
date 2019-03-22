// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import java.util.UUID;
import java.net.URL;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes = "\u0006\u0001m3q!\u0001\u0002\u0011\u0002\u0007\u0005\u0011B\u0001\tTiJLgnZ%oU\u0016\u001cG/[8og*\u00111\u0001B\u0001\nE&TWm\u0019;j_:T!!\u0002\u0004\u0002\u000fQ<\u0018\u000e\u001e;fe*\tq!A\u0002d_6\u001c\u0001aE\u0002\u0001\u0015A\u0001\"a\u0003\b\u000e\u00031Q\u0011!D\u0001\u0006g\u000e\fG.Y\u0005\u0003\u001f1\u0011a!\u00118z%\u00164\u0007CA\t\u0013\u001b\u0005\u0011\u0011BA\n\u0003\u0005EqU/\\3sS\u000eLeN[3di&|gn\u001d\u0005\u0006+\u0001!\tAF\u0001\u0007I%t\u0017\u000e\u001e\u0013\u0015\u0003]\u0001\"a\u0003\r\n\u0005ea!\u0001B+oSRDqa\u0007\u0001C\u0002\u0013\rA$\u0001\u0003vi\u001aDT#A\u000f\u0011\tEq\u0002eJ\u0005\u0003?\t\u0011\u0011\"\u00138kK\u000e$\u0018n\u001c8\u0011\u0005\u0005\"cBA\u0006#\u0013\t\u0019C\"\u0001\u0004Qe\u0016$WMZ\u0005\u0003K\u0019\u0012aa\u0015;sS:<'BA\u0012\r!\rY\u0001FK\u0005\u0003S1\u0011Q!\u0011:sCf\u0004\"aC\u0016\n\u00051b!\u0001\u0002\"zi\u0016DaA\f\u0001!\u0002\u0013i\u0012!B;uMb\u0002\u0003\"\u0002\u0019\u0001\t\u0003\t\u0014\u0001D<ji\",enY8eS:<GCA\u000f3\u0011\u0015\u0019t\u00061\u0001!\u0003!)gnY8eS:<\u0007bB\u001b\u0001\u0005\u0004%\u0019AN\u0001\u000bkJd'g\u0015;sS:<W#A\u001c\u0011\tEq\u0002\b\t\t\u0003syj\u0011A\u000f\u0006\u0003wq\n1A\\3u\u0015\u0005i\u0014\u0001\u00026bm\u0006L!a\u0010\u001e\u0003\u0007U\u0013F\n\u0003\u0004B\u0001\u0001\u0006IaN\u0001\fkJd'g\u0015;sS:<\u0007\u0005C\u0004D\u0001\t\u0007I1\u0001#\u0002\u0017U,\u0018\u000e\u001a\u001aTiJLgnZ\u000b\u0002\u000bB!\u0011C\b$!!\t9%*D\u0001I\u0015\tIE(\u0001\u0003vi&d\u0017BA&I\u0005\u0011)V+\u0013#\t\r5\u0003\u0001\u0015!\u0003F\u00031)X/\u001b33'R\u0014\u0018N\\4!\u0011\u001dy\u0005A1A\u0005\u0004A\u000bqc\u001d;sS:<''\u0016:m\u000b:\u001cw\u000eZ3e'R\u0014\u0018N\\4\u0016\u0003E\u0003B!\u0005\u0010!%B\u00111K\u0016\b\u0003#QK!!\u0016\u0002\u0002\u0017M#(/\u001b8h\u0007>$WmY\u0005\u0003/b\u0013\u0001#\u0016*M\u000b:\u001cw\u000eZ3e'R\u0014\u0018N\\4\u000b\u0005U\u0013\u0001B\u0002.\u0001A\u0003%\u0011+\u0001\rtiJLgn\u001a\u001aVe2,enY8eK\u0012\u001cFO]5oO\u0002\u0002")
public interface StringInjections extends NumericInjections
{
    void com$twitter$bijection$StringInjections$_setter_$utf8_$eq(final Injection p0);
    
    void com$twitter$bijection$StringInjections$_setter_$url2String_$eq(final Injection p0);
    
    void com$twitter$bijection$StringInjections$_setter_$uuid2String_$eq(final Injection p0);
    
    void com$twitter$bijection$StringInjections$_setter_$string2UrlEncodedString_$eq(final Injection p0);
    
    Injection<String, byte[]> utf8();
    
    Injection<String, byte[]> withEncoding(final String p0);
    
    Injection<URL, String> url2String();
    
    Injection<UUID, String> uuid2String();
    
    Injection<String, String> string2UrlEncodedString();
}
