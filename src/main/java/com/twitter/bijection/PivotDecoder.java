// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.Tuple2;
import scala.reflect.ScalaSignature;
import java.io.Serializable;
import scala.collection.Iterable;
import scala.collection.immutable.Map;
import scala.Function1;

@ScalaSignature(bytes = "\u0006\u0001}3q!\u0001\u0002\u0011\u0002\u0007\u0005\u0011B\u0001\u0007QSZ|G\u000fR3d_\u0012,'O\u0003\u0002\u0004\t\u0005I!-\u001b6fGRLwN\u001c\u0006\u0003\u000b\u0019\tq\u0001^<jiR,'OC\u0001\b\u0003\r\u0019w.\\\u0002\u0001+\u0011Qq'H\u001a\u0014\t\u0001Y\u0011#\u000f\t\u0003\u0019=i\u0011!\u0004\u0006\u0002\u001d\u0005)1oY1mC&\u0011\u0001#\u0004\u0002\u0007\u0003:L(+\u001a4\u0011\t1\u0011B#N\u0005\u0003'5\u0011\u0011BR;oGRLwN\\\u0019\u0011\tUA2D\n\b\u0003\u0019YI!aF\u0007\u0002\rA\u0013X\rZ3g\u0013\tI\"DA\u0002NCBT!aF\u0007\u0011\u0005qiB\u0002\u0001\u0003\u0006=\u0001\u0011\ra\b\u0002\u0003\u0017F\n\"\u0001I\u0012\u0011\u00051\t\u0013B\u0001\u0012\u000e\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"\u0001\u0004\u0013\n\u0005\u0015j!aA!osB\u0019qe\f\u001a\u000f\u0005!jcBA\u0015-\u001b\u0005Q#BA\u0016\t\u0003\u0019a$o\\8u}%\ta\"\u0003\u0002/\u001b\u00059\u0001/Y2lC\u001e,\u0017B\u0001\u00192\u0005!IE/\u001a:bE2,'B\u0001\u0018\u000e!\ta2\u0007B\u00035\u0001\t\u0007qD\u0001\u0002LeA\u0019qe\f\u001c\u0011\u0005q9D!\u0002\u001d\u0001\u0005\u0004y\"!A&\u0011\u0005izT\"A\u001e\u000b\u0005qj\u0014AA5p\u0015\u0005q\u0014\u0001\u00026bm\u0006L!\u0001Q\u001e\u0003\u0019M+'/[1mSj\f'\r\\3\t\u000b\t\u0003A\u0011A\"\u0002\r\u0011Jg.\u001b;%)\u0005!\u0005C\u0001\u0007F\u0013\t1UB\u0001\u0003V]&$\b\"\u0002%\u0001\r\u0003I\u0015a\u00013fGV\t!\n\u0005\u0003\r%-3\u0004\u0003\u0002\u0007M7IJ!!T\u0007\u0003\rQ+\b\u000f\\33\u0011\u0015y\u0005\u0001\"\u0001Q\u0003\u0015\t\u0007\u000f\u001d7z)\t)\u0014\u000bC\u0003S\u001d\u0002\u0007A#A\u0001n\u0011\u0015!\u0006\u0001\"\u0001V\u0003\u0015\u0019\b\u000f\\5u+\t1&\f\u0006\u0002X9B!ABE\u000eY!\u0011a!CM-\u0011\u0005qQF!B.T\u0005\u0004y\"!\u0001,\t\u000bu\u001b\u0006\u0019\u00010\u0002\u0005\u0019t\u0007\u0003\u0002\u0007\u0013me\u0003")
public interface PivotDecoder<K, K1, K2> extends Function1<Map<K1, Iterable<K2>>, Iterable<K>>, Serializable
{
    Function1<Tuple2<K1, K2>, K> dec();
    
    Iterable<K> apply(final Map<K1, Iterable<K2>> p0);
    
     <V> Function1<K1, Function1<K2, V>> split(final Function1<K, V> p0);
}
