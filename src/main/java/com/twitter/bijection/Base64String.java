// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.Product$class;
import scala.Option;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.Serializable;
import scala.Product;

@ScalaSignature(bytes = "\u0006\u0001\u0005\u0005e\u0001B\u0001\u0003\u0005&\u0011ABQ1tKZ\"4\u000b\u001e:j]\u001eT!a\u0001\u0003\u0002\u0013\tL'.Z2uS>t'BA\u0003\u0007\u0003\u001d!x/\u001b;uKJT\u0011aB\u0001\u0004G>l7\u0001A\n\u0005\u0001)\u00012\u0003\u0005\u0002\f\u001d5\tABC\u0001\u000e\u0003\u0015\u00198-\u00197b\u0013\tyAB\u0001\u0004B]f4\u0016\r\u001c\t\u0003\u0017EI!A\u0005\u0007\u0003\u000fA\u0013x\u000eZ;diB\u00111\u0002F\u0005\u0003+1\u0011AbU3sS\u0006d\u0017N_1cY\u0016D\u0001b\u0006\u0001\u0003\u0016\u0004%\t\u0001G\u0001\u0004gR\u0014X#A\r\u0011\u0005iibBA\u0006\u001c\u0013\taB\"\u0001\u0004Qe\u0016$WMZ\u0005\u0003=}\u0011aa\u0015;sS:<'B\u0001\u000f\r\u0011!\t\u0003A!E!\u0002\u0013I\u0012\u0001B:ue\u0002BQa\t\u0001\u0005\u0002\u0011\na\u0001P5oSRtDCA\u0013(!\t1\u0003!D\u0001\u0003\u0011\u00159\"\u00051\u0001\u001a\u0011\u001dI\u0003!!A\u0005\u0002)\nAaY8qsR\u0011Qe\u000b\u0005\b/!\u0002\n\u00111\u0001\u001a\u0011\u001di\u0003!%A\u0005\u00029\nabY8qs\u0012\"WMZ1vYR$\u0013'F\u00010U\tI\u0002gK\u00012!\t\u0011t'D\u00014\u0015\t!T'A\u0005v]\u000eDWmY6fI*\u0011a\u0007D\u0001\u000bC:tw\u000e^1uS>t\u0017B\u0001\u001d4\u0005E)hn\u00195fG.,GMV1sS\u0006t7-\u001a\u0005\bu\u0001\t\t\u0011\"\u0011<\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\tA\b\u0005\u0002>\u00056\taH\u0003\u0002@\u0001\u0006!A.\u00198h\u0015\u0005\t\u0015\u0001\u00026bm\u0006L!A\b \t\u000f\u0011\u0003\u0011\u0011!C\u0001\u000b\u0006a\u0001O]8ek\u000e$\u0018I]5usV\ta\t\u0005\u0002\f\u000f&\u0011\u0001\n\u0004\u0002\u0004\u0013:$\bb\u0002&\u0001\u0003\u0003%\taS\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\tau\n\u0005\u0002\f\u001b&\u0011a\n\u0004\u0002\u0004\u0003:L\bb\u0002)J\u0003\u0003\u0005\rAR\u0001\u0004q\u0012\n\u0004b\u0002*\u0001\u0003\u0003%\teU\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\tA\u000bE\u0002V12k\u0011A\u0016\u0006\u0003/2\t!bY8mY\u0016\u001cG/[8o\u0013\tIfK\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0011\u001dY\u0006!!A\u0005\u0002q\u000b\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0003;\u0002\u0004\"a\u00030\n\u0005}c!a\u0002\"p_2,\u0017M\u001c\u0005\b!j\u000b\t\u00111\u0001M\u0011\u001d\u0011\u0007!!A\u0005B\r\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002\r\"9Q\rAA\u0001\n\u00032\u0017AB3rk\u0006d7\u000f\u0006\u0002^O\"9\u0001\u000bZA\u0001\u0002\u0004a\u0005bB5\u0001\u0003\u0003%\tE[\u0001\ti>\u001cFO]5oOR\tAhB\u0003m\u0005!\u0005Q.\u0001\u0007CCN,g\u0007N*ue&tw\r\u0005\u0002']\u001a)\u0011A\u0001E\u0001_N\u0019a\u000e]\n\u0011\u0005-\t\u0018B\u0001:\r\u0005\u0019\te.\u001f*fM\")1E\u001cC\u0001iR\tQ\u000eC\u0004w]\n\u0007I1A<\u0002\rUtwO]1q+\u0005A\b\u0003\u0002\u0014zKeI!A\u001f\u0002\u0003\u0013%s'.Z2uS>t\u0007B\u0002?oA\u0003%\u00010A\u0004v]^\u0014\u0018\r\u001d\u0011\t\u000fyt\u0017\u0011!CA\u007f\u0006)\u0011\r\u001d9msR\u0019Q%!\u0001\t\u000b]i\b\u0019A\r\t\u0013\u0005\u0015a.!A\u0005\u0002\u0006\u001d\u0011aB;oCB\u0004H.\u001f\u000b\u0005\u0003\u0013\ty\u0001\u0005\u0003\f\u0003\u0017I\u0012bAA\u0007\u0019\t1q\n\u001d;j_:D\u0011\"!\u0005\u0002\u0004\u0005\u0005\t\u0019A\u0013\u0002\u0007a$\u0003\u0007C\u0005\u0002\u00169\f\t\u0011\"\u0003\u0002\u0018\u0005Y!/Z1e%\u0016\u001cx\u000e\u001c<f)\t\tI\u0002E\u0002>\u00037I1!!\b?\u0005\u0019y%M[3di\"I\u0011\u0011\u00058\u0002\u0002\u0013\u0015\u00111E\u0001\u000fG>\u0004\u0018\u0010J3yi\u0016t7/[8o)\u0011\t)#!\u000b\u0015\u0007\u0015\n9\u0003\u0003\u0005\u0018\u0003?\u0001\n\u00111\u0001\u001a\u0011\u001d\tY#a\bA\u0002\u0015\nQ\u0001\n;iSND\u0011\"a\fo#\u0003%)!!\r\u00021\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%c\u0011*\u0007\u0010^3og&|g\u000eF\u00020\u0003gAq!a\u000b\u0002.\u0001\u0007Q\u0005C\u0005\u000289\f\t\u0011\"\u0002\u0002:\u00059\u0002O]8ek\u000e$\bK]3gSb$S\r\u001f;f]NLwN\u001c\u000b\u0004y\u0005m\u0002bBA\u0016\u0003k\u0001\r!\n\u0005\n\u0003\u007fq\u0017\u0011!C\u0003\u0003\u0003\na\u0003\u001d:pIV\u001cG/\u0011:jif$S\r\u001f;f]NLwN\u001c\u000b\u0004\r\u0006\r\u0003bBA\u0016\u0003{\u0001\r!\n\u0005\n\u0003\u000fr\u0017\u0011!C\u0003\u0003\u0013\n\u0001\u0004\u001d:pIV\u001cG/\u00127f[\u0016tG\u000fJ3yi\u0016t7/[8o)\u0011\tY%a\u0014\u0015\u00071\u000bi\u0005\u0003\u0005Q\u0003\u000b\n\t\u00111\u0001G\u0011\u001d\tY#!\u0012A\u0002\u0015B\u0011\"a\u0015o\u0003\u0003%)!!\u0016\u00023A\u0014x\u000eZ;di&#XM]1u_J$S\r\u001f;f]NLwN\u001c\u000b\u0004)\u0006]\u0003bBA\u0016\u0003#\u0002\r!\n\u0005\n\u00037r\u0017\u0011!C\u0003\u0003;\n!cY1o\u000bF,\u0018\r\u001c\u0013fqR,gn]5p]R!\u0011qLA2)\ri\u0016\u0011\r\u0005\t!\u0006e\u0013\u0011!a\u0001\u0019\"9\u00111FA-\u0001\u0004)\u0003\"CA4]\u0006\u0005IQAA5\u0003IA\u0017m\u001d5D_\u0012,G%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0007\r\fY\u0007C\u0004\u0002,\u0005\u0015\u0004\u0019A\u0013\t\u0013\u0005=d.!A\u0005\u0006\u0005E\u0014\u0001E3rk\u0006d7\u000fJ3yi\u0016t7/[8o)\u0011\t\u0019(a\u001e\u0015\u0007u\u000b)\b\u0003\u0005Q\u0003[\n\t\u00111\u0001M\u0011\u001d\tY#!\u001cA\u0002\u0015B\u0011\"a\u001fo\u0003\u0003%)!! \u0002%Q|7\u000b\u001e:j]\u001e$S\r\u001f;f]NLwN\u001c\u000b\u0004U\u0006}\u0004bBA\u0016\u0003s\u0002\r!\n")
public final class Base64String implements Product, Serializable
{
    private final String str;
    
    public static String toString$extension(final String $this) {
        return Base64String$.MODULE$.toString$extension($this);
    }
    
    public static boolean equals$extension(final String $this, final Object x$1) {
        return Base64String$.MODULE$.equals$extension($this, x$1);
    }
    
    public static int hashCode$extension(final String $this) {
        return Base64String$.MODULE$.hashCode$extension($this);
    }
    
    public static boolean canEqual$extension(final String $this, final Object x$1) {
        return Base64String$.MODULE$.canEqual$extension($this, x$1);
    }
    
    public static Iterator<Object> productIterator$extension(final String $this) {
        return Base64String$.MODULE$.productIterator$extension($this);
    }
    
    public static Object productElement$extension(final String $this, final int x$1) {
        return Base64String$.MODULE$.productElement$extension($this, x$1);
    }
    
    public static int productArity$extension(final String $this) {
        return Base64String$.MODULE$.productArity$extension($this);
    }
    
    public static String productPrefix$extension(final String $this) {
        return Base64String$.MODULE$.productPrefix$extension($this);
    }
    
    public static String copy$default$1$extension(final String $this) {
        return Base64String$.MODULE$.copy$default$1$extension($this);
    }
    
    public static String copy$extension(final String $this, final String str) {
        return Base64String$.MODULE$.copy$extension($this, str);
    }
    
    public static Option<String> unapply(final String x$0) {
        return Base64String$.MODULE$.unapply(x$0);
    }
    
    public static String apply(final String str) {
        return Base64String$.MODULE$.apply(str);
    }
    
    public static Injection<String, String> unwrap() {
        return Base64String$.MODULE$.unwrap();
    }
    
    public String str() {
        return this.str;
    }
    
    public String copy(final String str) {
        return Base64String$.MODULE$.copy$extension(this.str(), str);
    }
    
    public String copy$default$1() {
        return Base64String$.MODULE$.copy$default$1$extension(this.str());
    }
    
    @Override
    public String productPrefix() {
        return Base64String$.MODULE$.productPrefix$extension(this.str());
    }
    
    @Override
    public int productArity() {
        return Base64String$.MODULE$.productArity$extension(this.str());
    }
    
    @Override
    public Object productElement(final int x$1) {
        return Base64String$.MODULE$.productElement$extension(this.str(), x$1);
    }
    
    @Override
    public Iterator<Object> productIterator() {
        return Base64String$.MODULE$.productIterator$extension(this.str());
    }
    
    @Override
    public boolean canEqual(final Object x$1) {
        return Base64String$.MODULE$.canEqual$extension(this.str(), x$1);
    }
    
    @Override
    public int hashCode() {
        return Base64String$.MODULE$.hashCode$extension(this.str());
    }
    
    @Override
    public boolean equals(final Object x$1) {
        return Base64String$.MODULE$.equals$extension(this.str(), x$1);
    }
    
    @Override
    public String toString() {
        return Base64String$.MODULE$.toString$extension(this.str());
    }
    
    public Base64String(final String str) {
        this.str = str;
        Product$class.$init$(this);
    }
}
