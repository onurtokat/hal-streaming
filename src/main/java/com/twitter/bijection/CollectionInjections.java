// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.collection.TraversableOnce;
import scala.runtime.Nothing$;
import scala.collection.generic.CanBuildFrom;
import scala.Function2;
import scala.collection.immutable.Vector;
import scala.collection.immutable.Set;
import scala.collection.immutable.Map;
import scala.Tuple2;
import scala.collection.immutable.List;
import scala.Option;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes = "\u0006\u0001\u0005ueaB\u0001\u0003!\u0003\r\t!\u0003\u0002\u0015\u0007>dG.Z2uS>t\u0017J\u001c6fGRLwN\\:\u000b\u0005\r!\u0011!\u00032jU\u0016\u001cG/[8o\u0015\t)a!A\u0004uo&$H/\u001a:\u000b\u0003\u001d\t1aY8n\u0007\u0001\u00192\u0001\u0001\u0006\u0011!\tYa\"D\u0001\r\u0015\u0005i\u0011!B:dC2\f\u0017BA\b\r\u0005\u0019\te.\u001f*fMB\u0011\u0011CE\u0007\u0002\u0005%\u00111C\u0001\u0002\u0011'R\u0014\u0018N\\4J]*,7\r^5p]NDQ!\u0006\u0001\u0005\u0002Y\ta\u0001J5oSR$C#A\f\u0011\u0005-A\u0012BA\r\r\u0005\u0011)f.\u001b;\t\u000bm\u0001A1\u0001\u000f\u0002\u001f=\u0004H/[8o\u0013:TWm\u0019;j_:,2!\b\u00142)\tq2\u0007\u0005\u0003\u0012?\u0005z\u0013B\u0001\u0011\u0003\u0005%IeN[3di&|g\u000eE\u0002\fE\u0011J!a\t\u0007\u0003\r=\u0003H/[8o!\t)c\u0005\u0004\u0001\u0005\u000b\u001dR\"\u0019\u0001\u0015\u0003\u0003\u0005\u000b\"!\u000b\u0017\u0011\u0005-Q\u0013BA\u0016\r\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"aC\u0017\n\u00059b!aA!osB\u00191B\t\u0019\u0011\u0005\u0015\nD!\u0002\u001a\u001b\u0005\u0004A#!\u0001\"\t\u000bQR\u00029A\u001b\u0002\u0007%t'\u000e\u0005\u0003\u0012?\u0011\u0002\u0004\"B\u001c\u0001\t\u0007A\u0014aC8qi&|gN\r'jgR,2!O\u001fM)\tQd\n\u0005\u0003\u0012?mz\u0004cA\u0006#yA\u0011Q%\u0010\u0003\u0006}Y\u0012\r\u0001\u000b\u0002\u0003-F\u00022\u0001\u0011%L\u001d\t\teI\u0004\u0002C\u000b6\t1I\u0003\u0002E\u0011\u00051AH]8pizJ\u0011!D\u0005\u0003\u000f2\tq\u0001]1dW\u0006<W-\u0003\u0002J\u0015\n!A*[:u\u0015\t9E\u0002\u0005\u0002&\u0019\u0012)QJ\u000eb\u0001Q\t\u0011aK\r\u0005\u0006iY\u0002\u001da\u0014\t\u0005#}a4\nC\u0003R\u0001\u0011\r!+A\u0004nCB\u00144+\u001a;\u0016\tMk\u0006-\u001a\u000b\u0003)\u001a\u0004B!E\u0010VCB!a+\u0017/`\u001d\tYq+\u0003\u0002Y\u0019\u00051\u0001K]3eK\u001aL!AW.\u0003\u00075\u000b\u0007O\u0003\u0002Y\u0019A\u0011Q%\u0018\u0003\u0006=B\u0013\r\u0001\u000b\u0002\u0003\u0017F\u0002\"!\n1\u0005\u000by\u0002&\u0019\u0001\u0015\u0011\u0007Y\u0013G-\u0003\u0002d7\n\u00191+\u001a;\u0011\u0005\u0015*G!B'Q\u0005\u0004A\u0003\"\u0002\u001bQ\u0001\b9\u0007\u0003B\t Q\u0012\u0004BaC5]?&\u0011!\u000e\u0004\u0002\u0007)V\u0004H.\u001a\u001a\t\u000b1\u0004A1A7\u0002\u0011M,GO\r'jgR,2A\u001c:v)\tyg\u000f\u0005\u0003\u0012?A\u001c\bc\u0001,ccB\u0011QE\u001d\u0003\u0006}-\u0014\r\u0001\u000b\t\u0004\u0001\"#\bCA\u0013v\t\u0015i5N1\u0001)\u0011\u0015!4\u000eq\u0001x!\u0011\tr$\u001d;\t\u000be\u0004A1\u0001>\u0002\u0015M,GO\r,fGR|'/\u0006\u0003|\u007f\u0006%Ac\u0001?\u0002\fA)\u0011cH?\u0002\u0002A\u0019aK\u0019@\u0011\u0005\u0015zH!\u0002 y\u0005\u0004A\u0003#\u0002!\u0002\u0004\u0005\u001d\u0011bAA\u0003\u0015\n1a+Z2u_J\u00042!JA\u0005\t\u0015i\u0005P1\u0001)\u0011\u0019!\u0004\u0010q\u0001\u0002\u000eA)\u0011c\b@\u0002\b!9\u0011\u0011\u0003\u0001\u0005\u0004\u0005M\u0011!\u00037jgR\u0014D*[:u+\u0019\t)\"!\b\u0002$Q!\u0011qCA\u0013!\u0019\tr$!\u0007\u0002 A!\u0001\tSA\u000e!\r)\u0013Q\u0004\u0003\u0007}\u0005=!\u0019\u0001\u0015\u0011\t\u0001C\u0015\u0011\u0005\t\u0004K\u0005\rBAB'\u0002\u0010\t\u0007\u0001\u0006C\u00045\u0003\u001f\u0001\u001d!a\n\u0011\rEy\u00121DA\u0011\u0011\u001d\tY\u0003\u0001C\u0002\u0003[\tqa]3ueM+G/\u0006\u0004\u00020\u0005]\u0012Q\b\u000b\u0005\u0003c\ty\u0004\u0005\u0004\u0012?\u0005M\u0012\u0011\b\t\u0005-\n\f)\u0004E\u0002&\u0003o!aAPA\u0015\u0005\u0004A\u0003\u0003\u0002,c\u0003w\u00012!JA\u001f\t\u0019i\u0015\u0011\u0006b\u0001Q!9A'!\u000bA\u0004\u0005\u0005\u0003CB\t \u0003k\tY\u0004C\u0004\u0002F\u0001!\t!a\u0012\u0002\u0017Q|7i\u001c8uC&tWM]\u000b\u000b\u0003\u0013\ny&!\u001c\u0002R\u0005\rD\u0003BA&\u0003\u001b#\u0002\"!\u0014\u0002p\u0005M\u0014q\u0011\t\u0007#}\ty%!\u0019\u0011\u0007\u0015\n\t\u0006\u0002\u0005\u0002T\u0005\r#\u0019AA+\u0005\u0005\u0019\u0015cA\u0015\u0002XA)\u0001)!\u0017\u0002^%\u0019\u00111\f&\u0003\u001fQ\u0013\u0018M^3sg\u0006\u0014G.Z(oG\u0016\u00042!JA0\t\u00199\u00131\tb\u0001QA\u0019Q%a\u0019\u0005\u0011\u0005\u0015\u00141\tb\u0001\u0003O\u0012\u0011\u0001R\t\u0004S\u0005%\u0004#\u0002!\u0002Z\u0005-\u0004cA\u0013\u0002n\u00111!'a\u0011C\u0002!Bq\u0001NA\"\u0001\b\t\t\b\u0005\u0004\u0012?\u0005u\u00131\u000e\u0005\t\u0003k\n\u0019\u0005q\u0001\u0002x\u0005\u00111\r\u001a\t\n\u0003s\n\u0019)KA6\u0003Cj!!a\u001f\u000b\t\u0005u\u0014qP\u0001\bO\u0016tWM]5d\u0015\r\t\t\tD\u0001\u000bG>dG.Z2uS>t\u0017\u0002BAC\u0003w\u0012AbQ1o\u0005VLG\u000e\u001a$s_6D\u0001\"!#\u0002D\u0001\u000f\u00111R\u0001\u0003I\u000e\u0004\u0012\"!\u001f\u0002\u0004&\ni&a\u0014\t\u0011\u0005=\u00151\ta\u0001\u0003#\u000bqaZ8pI&sg\u000fE\u0005\f\u0003'\u000b\t'a\u0014\u0002\u0018&\u0019\u0011Q\u0013\u0007\u0003\u0013\u0019+hn\u0019;j_:\u0014\u0004cA\u0006\u0002\u001a&\u0019\u00111\u0014\u0007\u0003\u000f\t{w\u000e\\3b]\u0002")
public interface CollectionInjections extends StringInjections
{
     <A, B> Injection<Option<A>, Option<B>> optionInjection(final Injection<A, B> p0);
    
     <V1, V2> Injection<Option<V1>, List<V2>> option2List(final Injection<V1, V2> p0);
    
     <K1, V1, V2> Injection<Map<K1, V1>, Set<V2>> map2Set(final Injection<Tuple2<K1, V1>, V2> p0);
    
     <V1, V2> Injection<Set<V1>, List<V2>> set2List(final Injection<V1, V2> p0);
    
     <V1, V2> Injection<Set<V1>, Vector<V2>> set2Vector(final Injection<V1, V2> p0);
    
     <V1, V2> Injection<List<V1>, List<V2>> list2List(final Injection<V1, V2> p0);
    
     <V1, V2> Injection<Set<V1>, Set<V2>> set2Set(final Injection<V1, V2> p0);
    
     <A, B, C extends TraversableOnce<A>, D extends TraversableOnce<B>> Injection<C, D> toContainer(final Function2<D, C, Object> p0, final Injection<A, B> p1, final CanBuildFrom<Nothing$, B, D> p2, final CanBuildFrom<Nothing$, A, C> p3);
}
