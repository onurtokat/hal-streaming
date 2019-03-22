// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.Product$class;
import scala.collection.immutable.Map;
import scala.Option;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.Serializable;
import scala.Product;

@ScalaSignature(bytes = "\u0006\u0001\tEc\u0001B\u0001\u0003\u0005&\u0011!\"\u00128hY&\u001c\b.\u00138u\u0015\t\u0019A!A\u0005cS*,7\r^5p]*\u0011QAB\u0001\bi^LG\u000f^3s\u0015\u00059\u0011aA2p[\u000e\u00011\u0003\u0002\u0001\u000b!M\u0001\"a\u0003\b\u000e\u00031Q\u0011!D\u0001\u0006g\u000e\fG.Y\u0005\u0003\u001f1\u0011a!\u00118z-\u0006d\u0007CA\u0006\u0012\u0013\t\u0011BBA\u0004Qe>$Wo\u0019;\u0011\u0005-!\u0012BA\u000b\r\u00051\u0019VM]5bY&T\u0018M\u00197f\u0011!9\u0002A!f\u0001\n\u0003A\u0012aA4fiV\t\u0011\u0004\u0005\u0002\u001b;9\u00111bG\u0005\u000391\ta\u0001\u0015:fI\u00164\u0017B\u0001\u0010 \u0005\u0019\u0019FO]5oO*\u0011A\u0004\u0004\u0005\tC\u0001\u0011\t\u0012)A\u00053\u0005!q-\u001a;!\u0011\u0015\u0019\u0003\u0001\"\u0001%\u0003\u0019a\u0014N\\5u}Q\u0011Qe\n\t\u0003M\u0001i\u0011A\u0001\u0005\u0006/\t\u0002\r!\u0007\u0005\bS\u0001\t\t\u0011\"\u0001+\u0003\u0011\u0019w\u000e]=\u0015\u0005\u0015Z\u0003bB\f)!\u0003\u0005\r!\u0007\u0005\b[\u0001\t\n\u0011\"\u0001/\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\u0012a\f\u0016\u00033AZ\u0013!\r\t\u0003e]j\u0011a\r\u0006\u0003iU\n\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0005Yb\u0011AC1o]>$\u0018\r^5p]&\u0011\u0001h\r\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0007b\u0002\u001e\u0001\u0003\u0003%\teO\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0003q\u0002\"!\u0010\"\u000e\u0003yR!a\u0010!\u0002\t1\fgn\u001a\u0006\u0002\u0003\u0006!!.\u0019<b\u0013\tqb\bC\u0004E\u0001\u0005\u0005I\u0011A#\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0003\u0019\u0003\"aC$\n\u0005!c!aA%oi\"9!\nAA\u0001\n\u0003Y\u0015A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0003\u0019>\u0003\"aC'\n\u00059c!aA!os\"9\u0001+SA\u0001\u0002\u00041\u0015a\u0001=%c!9!\u000bAA\u0001\n\u0003\u001a\u0016a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0003Q\u00032!\u0016-M\u001b\u00051&BA,\r\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u00033Z\u0013\u0001\"\u0013;fe\u0006$xN\u001d\u0005\b7\u0002\t\t\u0011\"\u0001]\u0003!\u0019\u0017M\\#rk\u0006dGCA/a!\tYa,\u0003\u0002`\u0019\t9!i\\8mK\u0006t\u0007b\u0002)[\u0003\u0003\u0005\r\u0001\u0014\u0005\bE\u0002\t\t\u0011\"\u0011d\u0003!A\u0017m\u001d5D_\u0012,G#\u0001$\t\u000f\u0015\u0004\u0011\u0011!C!M\u00061Q-];bYN$\"!X4\t\u000fA#\u0017\u0011!a\u0001\u0019\"9\u0011\u000eAA\u0001\n\u0003R\u0017\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0003q:Q\u0001\u001c\u0002\t\u00025\f!\"\u00128hY&\u001c\b.\u00138u!\t1cNB\u0003\u0002\u0005!\u0005qnE\u0002oaN\u0001\"aC9\n\u0005Id!AB!osJ+g\rC\u0003$]\u0012\u0005A\u000fF\u0001n\u0011\u001d1hN1A\u0005\u0004]\faBY5kK\u000e$\u0018n\u001c8U_&sG/F\u0001y!\u00111\u0013PR\u0013\n\u0005i\u0014!!\u0003\"jU\u0016\u001cG/[8o\u0011\u0019ah\u000e)A\u0005q\u0006y!-\u001b6fGRLwN\u001c+p\u0013:$\b\u0005\u0003\u0006Q]B\u0005\t1!Q\u0001\ny\u0004raC@G\r\u001a3e)C\u0002\u0002\u00021\u0011a\u0001V;qY\u0016,\u0004\u0002CA\u0003]\n\u0007I\u0011A#\u0002\u0003QDq!!\u0003oA\u0003%a)\u0001\u0002uA!A\u0011Q\u00028C\u0002\u0013\u0005Q)A\u0001e\u0011\u001d\t\tB\u001cQ\u0001\n\u0019\u000b!\u0001\u001a\u0011\t\u0011\u0005UaN1A\u0005\u0002\u0015\u000b\u0011a\u001b\u0005\b\u00033q\u0007\u0015!\u0003G\u0003\tY\u0007\u0005\u0003\u0005\u0002\u001e9\u0014\r\u0011\"\u0001F\u0003\u0005i\u0007bBA\u0011]\u0002\u0006IAR\u0001\u0003[\u0002B\u0001\"!\no\u0005\u0004%\t!R\u0001\u0002O\"9\u0011\u0011\u00068!\u0002\u00131\u0015AA4!\u0011%\tiC\u001cb\u0001\n\u0003\ty#A\u0003v]&$8/\u0006\u0002\u00022A1\u00111GA\u001d\rrj!!!\u000e\u000b\u0007\u0005]b+A\u0005j[6,H/\u00192mK&!\u00111HA\u001b\u0005\ri\u0015\r\u001d\u0005\t\u0003\u007fq\u0007\u0015!\u0003\u00022\u00051QO\\5ug\u0002B\u0011\"a\u0011o\u0005\u0004%\t!a\f\u0002\tQ,gn\u001d\u0005\t\u0003\u000fr\u0007\u0015!\u0003\u00022\u0005)A/\u001a8tA!I\u00111\n8C\u0002\u0013\u0005\u0011qF\u0001\u0006i\u0016,gn\u001d\u0005\t\u0003\u001fr\u0007\u0015!\u0003\u00022\u00051A/Z3og\u0002B\u0011\"a\u0015o\u0005\u0004%\t!a\f\u0002\u000fQ,g.\\;mi\"A\u0011q\u000b8!\u0002\u0013\t\t$\u0001\u0005uK:lW\u000f\u001c;!\u0011%\tYF\u001cb\u0001\n\u0003\ty#A\u0002bY2D\u0001\"a\u0018oA\u0003%\u0011\u0011G\u0001\u0005C2d\u0007\u0005C\u0005\u0002d9\u0014\r\u0011\"\u0001\u0002f\u0005Aqo\u001c:ee9,X.\u0006\u0002\u0002hA)!$!\u001b\u001a\r&\u0019\u00111H\u0010\t\u0011\u00055d\u000e)A\u0005\u0003O\n\u0011b^8sIJrW/\u001c\u0011\t\u0011\u0005EdN1A\u0005\u0002m\n\u0011a\u001d\u0005\b\u0003kr\u0007\u0015!\u0003=\u0003\t\u0019\b\u0005C\u0004\u0002z9$I!a\u001f\u0002\u0013Q|WI\\4mSNDG\u0003BA?\u0003\u0007\u0003BaCA@3%\u0019\u0011\u0011\u0011\u0007\u0003\r=\u0003H/[8o\u0011\u001d\t))a\u001eA\u0002\u0019\u000b1A\\;n\u0011\u001d\tII\u001cC\u0005\u0003\u0017\u000ba\u0001Z5wS\u0012,GCBA?\u0003\u001b\u000by\tC\u0004\u0002\u0006\u0006\u001d\u0005\u0019\u0001$\t\u000f\u0005E\u0015q\u0011a\u0001\r\u0006\u0019A-\u001b<\t\u000f\u0005Ue\u000e\"\u0003\u0002\u0018\u0006YaM]8n\u000b:<G.[:i)\u0011\tI*a'\u0011\t-\tyH\u0012\u0005\b\u0003;\u000b\u0019\n1\u0001\u001a\u0003\r\u0019HO\u001d\u0005\b\u0003CsG\u0011BAR\u0003-qW/\u001c7jgR\u0014\u0014N\u001c;\u0015\u0007\u0019\u000b)\u000b\u0003\u0005\u0002(\u0006}\u0005\u0019AAU\u0003\u001dqW/\u001c2feN\u0004R!a+\u0002<\u001asA!!,\u00028:!\u0011qVA[\u001b\t\t\tLC\u0002\u00024\"\ta\u0001\u0010:p_Rt\u0014\"A\u0007\n\u0007\u0005eF\"A\u0004qC\u000e\\\u0017mZ3\n\t\u0005u\u0016q\u0018\u0002\u0005\u0019&\u001cHOC\u0002\u0002:2Aq!a1o\t\u0013\t)-\u0001\u0003g_2$Gc\u0001$\u0002H\"A\u0011qUAa\u0001\u0004\tI\u000bC\u0004\u0002L:$I!!4\u0002\u000f\u0019|G\u000eZ\u00191aQ\u0019a)a4\t\u0011\u0005\u001d\u0016\u0011\u001aa\u0001\u0003SC\u0011\"a5o\u0003\u0003%\t)!6\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u0007\u0015\n9\u000e\u0003\u0004\u0018\u0003#\u0004\r!\u0007\u0005\n\u00037t\u0017\u0011!CA\u0003;\fq!\u001e8baBd\u0017\u0010\u0006\u0003\u0002~\u0005}\u0007\"CAq\u00033\f\t\u00111\u0001&\u0003\rAH\u0005\r\u0005\n\u0003Kt\u0017\u0011!C\u0005\u0003O\f1B]3bIJ+7o\u001c7wKR\u0011\u0011\u0011\u001e\t\u0004{\u0005-\u0018bAAw}\t1qJ\u00196fGRD\u0011\"!=o\u0003\u0003%)!a=\u0002\u001d\r|\u0007/\u001f\u0013fqR,gn]5p]R!\u0011Q_A})\r)\u0013q\u001f\u0005\t/\u0005=\b\u0013!a\u00013!9\u00111`Ax\u0001\u0004)\u0013!\u0002\u0013uQ&\u001c\b\"CA\u0000]F\u0005IQ\u0001B\u0001\u0003a\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE\"S\r\u001f;f]NLwN\u001c\u000b\u0004_\t\r\u0001bBA~\u0003{\u0004\r!\n\u0005\n\u0005\u000fq\u0017\u0011!C\u0003\u0005\u0013\tq\u0003\u001d:pIV\u001cG\u000f\u0015:fM&DH%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0007q\u0012Y\u0001C\u0004\u0002|\n\u0015\u0001\u0019A\u0013\t\u0013\t=a.!A\u0005\u0006\tE\u0011A\u00069s_\u0012,8\r^!sSRLH%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0007\u0019\u0013\u0019\u0002C\u0004\u0002|\n5\u0001\u0019A\u0013\t\u0013\t]a.!A\u0005\u0006\te\u0011\u0001\u00079s_\u0012,8\r^#mK6,g\u000e\u001e\u0013fqR,gn]5p]R!!1\u0004B\u0010)\ra%Q\u0004\u0005\t!\nU\u0011\u0011!a\u0001\r\"9\u00111 B\u000b\u0001\u0004)\u0003\"\u0003B\u0012]\u0006\u0005IQ\u0001B\u0013\u0003e\u0001(o\u001c3vGRLE/\u001a:bi>\u0014H%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0007Q\u00139\u0003C\u0004\u0002|\n\u0005\u0002\u0019A\u0013\t\u0013\t-b.!A\u0005\u0006\t5\u0012AE2b]\u0016\u000bX/\u00197%Kb$XM\\:j_:$BAa\f\u00034Q\u0019QL!\r\t\u0011A\u0013I#!AA\u00021Cq!a?\u0003*\u0001\u0007Q\u0005C\u0005\u000389\f\t\u0011\"\u0002\u0003:\u0005\u0011\u0002.Y:i\u0007>$W\rJ3yi\u0016t7/[8o)\r\u0019'1\b\u0005\b\u0003w\u0014)\u00041\u0001&\u0011%\u0011yD\\A\u0001\n\u000b\u0011\t%\u0001\tfcV\fGn\u001d\u0013fqR,gn]5p]R!!1\tB$)\ri&Q\t\u0005\t!\nu\u0012\u0011!a\u0001\u0019\"9\u00111 B\u001f\u0001\u0004)\u0003\"\u0003B&]\u0006\u0005IQ\u0001B'\u0003I!xn\u0015;sS:<G%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0007)\u0014y\u0005C\u0004\u0002|\n%\u0003\u0019A\u0013")
public final class EnglishInt implements Product, Serializable
{
    private final String get;
    
    public static String toString$extension(final String $this) {
        return EnglishInt$.MODULE$.toString$extension($this);
    }
    
    public static boolean equals$extension(final String $this, final Object x$1) {
        return EnglishInt$.MODULE$.equals$extension($this, x$1);
    }
    
    public static int hashCode$extension(final String $this) {
        return EnglishInt$.MODULE$.hashCode$extension($this);
    }
    
    public static boolean canEqual$extension(final String $this, final Object x$1) {
        return EnglishInt$.MODULE$.canEqual$extension($this, x$1);
    }
    
    public static Iterator<Object> productIterator$extension(final String $this) {
        return EnglishInt$.MODULE$.productIterator$extension($this);
    }
    
    public static Object productElement$extension(final String $this, final int x$1) {
        return EnglishInt$.MODULE$.productElement$extension($this, x$1);
    }
    
    public static int productArity$extension(final String $this) {
        return EnglishInt$.MODULE$.productArity$extension($this);
    }
    
    public static String productPrefix$extension(final String $this) {
        return EnglishInt$.MODULE$.productPrefix$extension($this);
    }
    
    public static String copy$default$1$extension(final String $this) {
        return EnglishInt$.MODULE$.copy$default$1$extension($this);
    }
    
    public static String copy$extension(final String $this, final String get) {
        return EnglishInt$.MODULE$.copy$extension($this, get);
    }
    
    public static Option<String> unapply(final String x$0) {
        return EnglishInt$.MODULE$.unapply(x$0);
    }
    
    public static String apply(final String get) {
        return EnglishInt$.MODULE$.apply(get);
    }
    
    public static String s() {
        return EnglishInt$.MODULE$.s();
    }
    
    public static Map<String, Object> word2num() {
        return EnglishInt$.MODULE$.word2num();
    }
    
    public static Map<Object, String> all() {
        return EnglishInt$.MODULE$.all();
    }
    
    public static Map<Object, String> tenmult() {
        return EnglishInt$.MODULE$.tenmult();
    }
    
    public static Map<Object, String> teens() {
        return EnglishInt$.MODULE$.teens();
    }
    
    public static Map<Object, String> tens() {
        return EnglishInt$.MODULE$.tens();
    }
    
    public static Map<Object, String> units() {
        return EnglishInt$.MODULE$.units();
    }
    
    public static int g() {
        return EnglishInt$.MODULE$.g();
    }
    
    public static int m() {
        return EnglishInt$.MODULE$.m();
    }
    
    public static int k() {
        return EnglishInt$.MODULE$.k();
    }
    
    public static int d() {
        return EnglishInt$.MODULE$.d();
    }
    
    public static int t() {
        return EnglishInt$.MODULE$.t();
    }
    
    public static Bijection<Object, String> bijectionToInt() {
        return EnglishInt$.MODULE$.bijectionToInt();
    }
    
    public String get() {
        return this.get;
    }
    
    public String copy(final String get) {
        return EnglishInt$.MODULE$.copy$extension(this.get(), get);
    }
    
    public String copy$default$1() {
        return EnglishInt$.MODULE$.copy$default$1$extension(this.get());
    }
    
    @Override
    public String productPrefix() {
        return EnglishInt$.MODULE$.productPrefix$extension(this.get());
    }
    
    @Override
    public int productArity() {
        return EnglishInt$.MODULE$.productArity$extension(this.get());
    }
    
    @Override
    public Object productElement(final int x$1) {
        return EnglishInt$.MODULE$.productElement$extension(this.get(), x$1);
    }
    
    @Override
    public Iterator<Object> productIterator() {
        return EnglishInt$.MODULE$.productIterator$extension(this.get());
    }
    
    @Override
    public boolean canEqual(final Object x$1) {
        return EnglishInt$.MODULE$.canEqual$extension(this.get(), x$1);
    }
    
    @Override
    public int hashCode() {
        return EnglishInt$.MODULE$.hashCode$extension(this.get());
    }
    
    @Override
    public boolean equals(final Object x$1) {
        return EnglishInt$.MODULE$.equals$extension(this.get(), x$1);
    }
    
    @Override
    public String toString() {
        return EnglishInt$.MODULE$.toString$extension(this.get());
    }
    
    public EnglishInt(final String get) {
        this.get = get;
        Product$class.$init$(this);
    }
}
