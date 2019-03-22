// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import java.util.Date;
import java.util.UUID;
import java.math.BigInteger;
import scala.math.BigInt;
import scala.Tuple2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes = "\u0006\u0001\u0005-daB\u0001\u0003!\u0003\r\t!\u0003\u0002\u0012\u001dVlWM]5d\u0005&TWm\u0019;j_:\u001c(BA\u0002\u0005\u0003%\u0011\u0017N[3di&|gN\u0003\u0002\u0006\r\u00059Ao^5ui\u0016\u0014(\"A\u0004\u0002\u0007\r|Wn\u0001\u0001\u0014\u0007\u0001Q\u0001\u0003\u0005\u0002\f\u001d5\tABC\u0001\u000e\u0003\u0015\u00198-\u00197b\u0013\tyAB\u0001\u0004B]f\u0014VM\u001a\t\u0003#Ii\u0011AA\u0005\u0003'\t\u0011\u0001dR3oKJ\fG/\u001a3UkBdWMQ5kK\u000e$\u0018n\u001c8t\u0011\u0015)\u0002\u0001\"\u0001\u0017\u0003\u0019!\u0013N\\5uIQ\tq\u0003\u0005\u0002\f1%\u0011\u0011\u0004\u0004\u0002\u0005+:LG\u000fC\u0004\u001c\u0001\t\u0007I1\u0001\u000f\u0002\u0015\tLH/\u001a\u001aC_b,G-F\u0001\u001e!\u0011\tb\u0004I\u0012\n\u0005}\u0011!!\u0003\"jU\u0016\u001cG/[8o!\tY\u0011%\u0003\u0002#\u0019\t!!)\u001f;f!\t!\u0013&D\u0001&\u0015\t1s%\u0001\u0003mC:<'\"\u0001\u0015\u0002\t)\fg/Y\u0005\u0003E\u0015Baa\u000b\u0001!\u0002\u0013i\u0012a\u00032zi\u0016\u0014$i\u001c=fI\u0002Bq!\f\u0001C\u0002\u0013\ra&A\u0006tQ>\u0014HO\r\"pq\u0016$W#A\u0018\u0011\tEq\u0002g\r\t\u0003\u0017EJ!A\r\u0007\u0003\u000bMCwN\u001d;\u0011\u0005\u0011\"\u0014B\u0001\u001a&\u0011\u00191\u0004\u0001)A\u0005_\u0005a1\u000f[8siJ\u0012u\u000e_3eA!9\u0001\b\u0001b\u0001\n\u0007I\u0014AD:i_J$(GQ=uK\nKH/Z\u000b\u0002uA!\u0011C\b\u0019<!\u0011YA\b\t\u0011\n\u0005ub!A\u0002+va2,'\u0007\u0003\u0004@\u0001\u0001\u0006IAO\u0001\u0010g\"|'\u000f\u001e\u001aCsR,')\u001f;fA!9\u0011\t\u0001b\u0001\n\u0007\u0011\u0015!C5oiJ\u0012u\u000e_3e+\u0005\u0019\u0005\u0003B\t\u001f\t\u001e\u0003\"aC#\n\u0005\u0019c!aA%oiB\u0011A\u0005S\u0005\u0003\u0013\u0016\u0012q!\u00138uK\u001e,'\u000f\u0003\u0004L\u0001\u0001\u0006IaQ\u0001\u000bS:$(GQ8yK\u0012\u0004\u0003bB'\u0001\u0005\u0004%\u0019AT\u0001\u000fS:$(g\u00155peR\u001c\u0006n\u001c:u+\u0005y\u0005\u0003B\t\u001f\tB\u0003Ba\u0003\u001f1a!1!\u000b\u0001Q\u0001\n=\u000bq\"\u001b8ueMCwN\u001d;TQ>\u0014H\u000f\t\u0005\b)\u0002\u0011\r\u0011b\u0001V\u0003)awN\\43\u0005>DX\rZ\u000b\u0002-B!\u0011CH,[!\tY\u0001,\u0003\u0002Z\u0019\t!Aj\u001c8h!\t!3,\u0003\u0002ZK!1Q\f\u0001Q\u0001\nY\u000b1\u0002\\8oOJ\u0012u\u000e_3eA!9q\f\u0001b\u0001\n\u0007\u0001\u0017a\u00037p]\u001e\u0014\u0014J\u001c;J]R,\u0012!\u0019\t\u0005#y9&\r\u0005\u0003\fy\u0011#\u0005B\u00023\u0001A\u0003%\u0011-\u0001\u0007m_:<''\u00138u\u0013:$\b\u0005C\u0004g\u0001\t\u0007I1A4\u0002\u0017\u0019dw.\u0019;3\u0005>DX\rZ\u000b\u0002QB!\u0011CH5m!\tY!.\u0003\u0002l\u0019\t)a\t\\8biB\u0011A%\\\u0005\u0003W\u0016Baa\u001c\u0001!\u0002\u0013A\u0017\u0001\u00044m_\u0006$(GQ8yK\u0012\u0004\u0003bB9\u0001\u0005\u0004%\u0019A]\u0001\rI>,(\r\\33\u0005>DX\rZ\u000b\u0002gB!\u0011C\b;x!\tYQ/\u0003\u0002w\u0019\t1Ai\\;cY\u0016\u0004\"\u0001\n=\n\u0005Y,\u0003B\u0002>\u0001A\u0003%1/A\u0007e_V\u0014G.\u001a\u001aC_b,G\r\t\u0005\by\u0002\u0011\r\u0011\"\u0001~\u0003A1Gn\\1ue%sG/S#F\u000b^*D'F\u0001\u007f!\u0011\tb$\u001b#\t\u000f\u0005\u0005\u0001\u0001)A\u0005}\u0006\tb\r\\8biJJe\u000e^%F\u000b\u0016;T\u0007\u000e\u0011\t\u0013\u0005\u0015\u0001A1A\u0005\u0002\u0005\u001d\u0011A\u00053pk\ndWM\r'p]\u001eLU)R#8kQ*\"!!\u0003\u0011\tEqBo\u0016\u0005\t\u0003\u001b\u0001\u0001\u0015!\u0003\u0002\n\u0005\u0019Bm\\;cY\u0016\u0014Dj\u001c8h\u0013\u0016+UiN\u001b5A!I\u0011\u0011\u0003\u0001C\u0002\u0013\r\u00111C\u0001\u0012E&<\u0017J\u001c;3\u0005&<\u0017J\u001c;fO\u0016\u0014XCAA\u000b!\u0019\tb$a\u0006\u00020A!\u0011\u0011DA\u0015\u001d\u0011\tY\"!\n\u000f\t\u0005u\u00111E\u0007\u0003\u0003?Q1!!\t\t\u0003\u0019a$o\\8u}%\tQ\"C\u0002\u0002(1\tq\u0001]1dW\u0006<W-\u0003\u0003\u0002,\u00055\"A\u0002\"jO&sGOC\u0002\u0002(1\u0001B!!\r\u000285\u0011\u00111\u0007\u0006\u0004\u0003k9\u0013\u0001B7bi\"LA!!\u000f\u00024\tQ!)[4J]R,w-\u001a:\t\u0011\u0005u\u0002\u0001)A\u0005\u0003+\t!CY5h\u0013:$(GQ5h\u0013:$XmZ3sA!I\u0011\u0011\t\u0001C\u0002\u0013\r\u00111I\u0001\rk&$'\u0007T8oO2{gnZ\u000b\u0003\u0003\u000b\u0002b!\u0005\u0010\u0002H\u0005M\u0003\u0003BA%\u0003\u001fj!!a\u0013\u000b\u0007\u00055s%\u0001\u0003vi&d\u0017\u0002BA)\u0003\u0017\u0012A!V+J\tB!1\u0002P,X\u0011!\t9\u0006\u0001Q\u0001\n\u0005\u0015\u0013!D;jIJbuN\\4M_:<\u0007\u0005C\u0005\u0002\\\u0001\u0011\r\u0011b\u0001\u0002^\u0005IA-\u0019;fe1{gnZ\u000b\u0003\u0003?\u0002R!\u0005\u0010\u0002b]\u0003B!!\u0013\u0002d%!\u0011QMA&\u0005\u0011!\u0015\r^3\t\u0011\u0005%\u0004\u0001)A\u0005\u0003?\n!\u0002Z1uKJbuN\\4!\u0001")
public interface NumericBijections extends GeneratedTupleBijections
{
    void com$twitter$bijection$NumericBijections$_setter_$byte2Boxed_$eq(final Bijection p0);
    
    void com$twitter$bijection$NumericBijections$_setter_$short2Boxed_$eq(final Bijection p0);
    
    void com$twitter$bijection$NumericBijections$_setter_$short2ByteByte_$eq(final Bijection p0);
    
    void com$twitter$bijection$NumericBijections$_setter_$int2Boxed_$eq(final Bijection p0);
    
    void com$twitter$bijection$NumericBijections$_setter_$int2ShortShort_$eq(final Bijection p0);
    
    void com$twitter$bijection$NumericBijections$_setter_$long2Boxed_$eq(final Bijection p0);
    
    void com$twitter$bijection$NumericBijections$_setter_$long2IntInt_$eq(final Bijection p0);
    
    void com$twitter$bijection$NumericBijections$_setter_$float2Boxed_$eq(final Bijection p0);
    
    void com$twitter$bijection$NumericBijections$_setter_$double2Boxed_$eq(final Bijection p0);
    
    void com$twitter$bijection$NumericBijections$_setter_$float2IntIEEE754_$eq(final Bijection p0);
    
    void com$twitter$bijection$NumericBijections$_setter_$double2LongIEEE754_$eq(final Bijection p0);
    
    void com$twitter$bijection$NumericBijections$_setter_$bigInt2BigInteger_$eq(final Bijection p0);
    
    void com$twitter$bijection$NumericBijections$_setter_$uid2LongLong_$eq(final Bijection p0);
    
    void com$twitter$bijection$NumericBijections$_setter_$date2Long_$eq(final Bijection p0);
    
    Bijection<Object, Byte> byte2Boxed();
    
    Bijection<Object, Short> short2Boxed();
    
    Bijection<Object, Tuple2<Object, Object>> short2ByteByte();
    
    Bijection<Object, Integer> int2Boxed();
    
    Bijection<Object, Tuple2<Object, Object>> int2ShortShort();
    
    Bijection<Object, Long> long2Boxed();
    
    Bijection<Object, Tuple2<Object, Object>> long2IntInt();
    
    Bijection<Object, Float> float2Boxed();
    
    Bijection<Object, Double> double2Boxed();
    
    Bijection<Object, Object> float2IntIEEE754();
    
    Bijection<Object, Object> double2LongIEEE754();
    
    Bijection<BigInt, BigInteger> bigInt2BigInteger();
    
    Bijection<UUID, Tuple2<Object, Object>> uid2LongLong();
    
    Bijection<Date, Object> date2Long();
}
