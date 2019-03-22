// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.Product$class;
import scala.Function1;
import scala.Option;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.Serializable;
import scala.Product;

@ScalaSignature(bytes = "\u0006\u0001\u0005\u001de\u0001B\u0001\u0003\u0005&\u0011Ab\u0012.jaB,GMQ=uKNT!a\u0001\u0003\u0002\u0013\tL'.Z2uS>t'BA\u0003\u0007\u0003\u001d!x/\u001b;uKJT\u0011aB\u0001\u0004G>l7\u0001A\n\u0005\u0001)\u00012\u0003\u0005\u0002\f\u001d5\tABC\u0001\u000e\u0003\u0015\u00198-\u00197b\u0013\tyAB\u0001\u0004B]f4\u0016\r\u001c\t\u0003\u0017EI!A\u0005\u0007\u0003\u000fA\u0013x\u000eZ;diB\u00111\u0002F\u0005\u0003+1\u0011AbU3sS\u0006d\u0017N_1cY\u0016D\u0001b\u0006\u0001\u0003\u0016\u0004%\t\u0001G\u0001\u0006Ef$Xm]\u000b\u00023A\u00191B\u0007\u000f\n\u0005ma!!B!se\u0006L\bCA\u0006\u001e\u0013\tqBB\u0001\u0003CsR,\u0007\u0002\u0003\u0011\u0001\u0005#\u0005\u000b\u0011B\r\u0002\r\tLH/Z:!\u0011\u0015\u0011\u0003\u0001\"\u0001$\u0003\u0019a\u0014N\\5u}Q\u0011AE\n\t\u0003K\u0001i\u0011A\u0001\u0005\u0006/\u0005\u0002\r!\u0007\u0005\u0006Q\u0001!\t%K\u0001\ti>\u001cFO]5oOR\t!\u0006\u0005\u0002,]9\u00111\u0002L\u0005\u0003[1\ta\u0001\u0015:fI\u00164\u0017BA\u00181\u0005\u0019\u0019FO]5oO*\u0011Q\u0006\u0004\u0005\be\u0001\t\t\u0011\"\u00014\u0003\u0011\u0019w\u000e]=\u0015\u0005\u0011\"\u0004bB\f2!\u0003\u0005\r!\u0007\u0005\bm\u0001\t\n\u0011\"\u00018\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\u0012\u0001\u000f\u0016\u00033eZ\u0013A\u000f\t\u0003w\u0001k\u0011\u0001\u0010\u0006\u0003{y\n\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0005}b\u0011AC1o]>$\u0018\r^5p]&\u0011\u0011\t\u0010\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0007bB\"\u0001\u0003\u0003%\t\u0005R\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0003\u0015\u0003\"AR&\u000e\u0003\u001dS!\u0001S%\u0002\t1\fgn\u001a\u0006\u0002\u0015\u0006!!.\u0019<b\u0013\tys\tC\u0004N\u0001\u0005\u0005I\u0011\u0001(\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0003=\u0003\"a\u0003)\n\u0005Ec!aA%oi\"91\u000bAA\u0001\n\u0003!\u0016A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0003+b\u0003\"a\u0003,\n\u0005]c!aA!os\"9\u0011LUA\u0001\u0002\u0004y\u0015a\u0001=%c!91\fAA\u0001\n\u0003b\u0016a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0003u\u00032AX1V\u001b\u0005y&B\u00011\r\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0003E~\u0013\u0001\"\u0013;fe\u0006$xN\u001d\u0005\bI\u0002\t\t\u0011\"\u0001f\u0003!\u0019\u0017M\\#rk\u0006dGC\u00014j!\tYq-\u0003\u0002i\u0019\t9!i\\8mK\u0006t\u0007bB-d\u0003\u0003\u0005\r!\u0016\u0005\bW\u0002\t\t\u0011\"\u0011m\u0003!A\u0017m\u001d5D_\u0012,G#A(\t\u000f9\u0004\u0011\u0011!C!_\u00061Q-];bYN$\"A\u001a9\t\u000fek\u0017\u0011!a\u0001+\u001e9!OAA\u0001\u0012\u0003\u0019\u0018\u0001D$[SB\u0004X\r\u001a\"zi\u0016\u001c\bCA\u0013u\r\u001d\t!!!A\t\u0002U\u001c2\u0001\u001e<\u0014!\u00119(0\u0007\u0013\u000e\u0003aT!!\u001f\u0007\u0002\u000fI,h\u000e^5nK&\u00111\u0010\u001f\u0002\u0012\u0003\n\u001cHO]1di\u001a+hn\u0019;j_:\f\u0004\"\u0002\u0012u\t\u0003iH#A:\t\u000f!\"\u0018\u0011!C#\u007fR\tQ\tC\u0005\u0002\u0004Q\f\t\u0011\"!\u0002\u0006\u0005)\u0011\r\u001d9msR\u0019A%a\u0002\t\r]\t\t\u00011\u0001\u001a\u0011%\tY\u0001^A\u0001\n\u0003\u000bi!A\u0004v]\u0006\u0004\b\u000f\\=\u0015\t\u0005=\u0011Q\u0003\t\u0005\u0017\u0005E\u0011$C\u0002\u0002\u00141\u0011aa\u00149uS>t\u0007\"CA\f\u0003\u0013\t\t\u00111\u0001%\u0003\rAH\u0005\r\u0005\n\u00037!\u0018\u0011!C\u0005\u0003;\t1B]3bIJ+7o\u001c7wKR\u0011\u0011q\u0004\t\u0004\r\u0006\u0005\u0012bAA\u0012\u000f\n1qJ\u00196fGRDq!a\nu\t\u000b\tI#\u0001\nu_N#(/\u001b8hI\u0015DH/\u001a8tS>tGcA\u0015\u0002,!9\u0011QFA\u0013\u0001\u0004!\u0013!\u0002\u0013uQ&\u001c\b\"CA\u0019i\u0006\u0005IQAA\u001a\u00039\u0019w\u000e]=%Kb$XM\\:j_:$B!!\u000e\u0002:Q\u0019A%a\u000e\t\u0011]\ty\u0003%AA\u0002eAq!!\f\u00020\u0001\u0007A\u0005C\u0005\u0002>Q\f\n\u0011\"\u0002\u0002@\u0005A2m\u001c9zI\u0011,g-Y;mi\u0012\nD%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0007a\n\t\u0005C\u0004\u0002.\u0005m\u0002\u0019\u0001\u0013\t\u0013\u0005\u0015C/!A\u0005\u0006\u0005\u001d\u0013a\u00069s_\u0012,8\r\u001e)sK\u001aL\u0007\u0010J3yi\u0016t7/[8o)\r)\u0015\u0011\n\u0005\b\u0003[\t\u0019\u00051\u0001%\u0011%\ti\u0005^A\u0001\n\u000b\ty%\u0001\fqe>$Wo\u0019;Be&$\u0018\u0010J3yi\u0016t7/[8o)\ry\u0015\u0011\u000b\u0005\b\u0003[\tY\u00051\u0001%\u0011%\t)\u0006^A\u0001\n\u000b\t9&\u0001\rqe>$Wo\u0019;FY\u0016lWM\u001c;%Kb$XM\\:j_:$B!!\u0017\u0002^Q\u0019Q+a\u0017\t\u0011e\u000b\u0019&!AA\u0002=Cq!!\f\u0002T\u0001\u0007A\u0005C\u0005\u0002bQ\f\t\u0011\"\u0002\u0002d\u0005I\u0002O]8ek\u000e$\u0018\n^3sCR|'\u000fJ3yi\u0016t7/[8o)\ri\u0016Q\r\u0005\b\u0003[\ty\u00061\u0001%\u0011%\tI\u0007^A\u0001\n\u000b\tY'\u0001\ndC:,\u0015/^1mI\u0015DH/\u001a8tS>tG\u0003BA7\u0003c\"2AZA8\u0011!I\u0016qMA\u0001\u0002\u0004)\u0006bBA\u0017\u0003O\u0002\r\u0001\n\u0005\n\u0003k\"\u0018\u0011!C\u0003\u0003o\n!\u0003[1tQ\u000e{G-\u001a\u0013fqR,gn]5p]R\u0019A.!\u001f\t\u000f\u00055\u00121\u000fa\u0001I!I\u0011Q\u0010;\u0002\u0002\u0013\u0015\u0011qP\u0001\u0011KF,\u0018\r\\:%Kb$XM\\:j_:$B!!!\u0002\u0006R\u0019a-a!\t\u0011e\u000bY(!AA\u0002UCq!!\f\u0002|\u0001\u0007A\u0005")
public final class GZippedBytes implements Product, Serializable
{
    private final byte[] bytes;
    
    public static boolean equals$extension(final byte[] $this, final Object x$1) {
        return GZippedBytes$.MODULE$.equals$extension($this, x$1);
    }
    
    public static int hashCode$extension(final byte[] $this) {
        return GZippedBytes$.MODULE$.hashCode$extension($this);
    }
    
    public static boolean canEqual$extension(final byte[] $this, final Object x$1) {
        return GZippedBytes$.MODULE$.canEqual$extension($this, x$1);
    }
    
    public static Iterator<Object> productIterator$extension(final byte[] $this) {
        return GZippedBytes$.MODULE$.productIterator$extension($this);
    }
    
    public static Object productElement$extension(final byte[] $this, final int x$1) {
        return GZippedBytes$.MODULE$.productElement$extension($this, x$1);
    }
    
    public static int productArity$extension(final byte[] $this) {
        return GZippedBytes$.MODULE$.productArity$extension($this);
    }
    
    public static String productPrefix$extension(final byte[] $this) {
        return GZippedBytes$.MODULE$.productPrefix$extension($this);
    }
    
    public static byte[] copy$default$1$extension(final byte[] $this) {
        return GZippedBytes$.MODULE$.copy$default$1$extension($this);
    }
    
    public static byte[] copy$extension(final byte[] $this, final byte[] bytes) {
        return GZippedBytes$.MODULE$.copy$extension($this, bytes);
    }
    
    public static String toString$extension(final byte[] $this) {
        return GZippedBytes$.MODULE$.toString$extension($this);
    }
    
    public static Option<byte[]> unapply(final byte[] x$0) {
        return GZippedBytes$.MODULE$.unapply(x$0);
    }
    
    public static byte[] apply(final byte[] bytes) {
        return GZippedBytes$.MODULE$.apply(bytes);
    }
    
    public static <A> Function1<byte[], A> andThen(final Function1<byte[], A> g) {
        return GZippedBytes$.MODULE$.andThen(g);
    }
    
    public static <A> Function1<A, byte[]> compose(final Function1<A, byte[]> g) {
        return GZippedBytes$.MODULE$.compose(g);
    }
    
    public byte[] bytes() {
        return this.bytes;
    }
    
    @Override
    public String toString() {
        return GZippedBytes$.MODULE$.toString$extension(this.bytes());
    }
    
    public byte[] copy(final byte[] bytes) {
        return GZippedBytes$.MODULE$.copy$extension(this.bytes(), bytes);
    }
    
    public byte[] copy$default$1() {
        return GZippedBytes$.MODULE$.copy$default$1$extension(this.bytes());
    }
    
    @Override
    public String productPrefix() {
        return GZippedBytes$.MODULE$.productPrefix$extension(this.bytes());
    }
    
    @Override
    public int productArity() {
        return GZippedBytes$.MODULE$.productArity$extension(this.bytes());
    }
    
    @Override
    public Object productElement(final int x$1) {
        return GZippedBytes$.MODULE$.productElement$extension(this.bytes(), x$1);
    }
    
    @Override
    public Iterator<Object> productIterator() {
        return GZippedBytes$.MODULE$.productIterator$extension(this.bytes());
    }
    
    @Override
    public boolean canEqual(final Object x$1) {
        return GZippedBytes$.MODULE$.canEqual$extension(this.bytes(), x$1);
    }
    
    @Override
    public int hashCode() {
        return GZippedBytes$.MODULE$.hashCode$extension(this.bytes());
    }
    
    @Override
    public boolean equals(final Object x$1) {
        return GZippedBytes$.MODULE$.equals$extension(this.bytes(), x$1);
    }
    
    public GZippedBytes(final byte[] bytes) {
        this.bytes = bytes;
        Product$class.$init$(this);
    }
}
