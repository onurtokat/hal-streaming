// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.Tuple2;
import scala.reflect.ScalaSignature;
import java.io.Serializable;
import scala.collection.immutable.Map;
import scala.collection.Iterable;
import scala.Function1;

@ScalaSignature(bytes = "\u0006\u0001}3q!\u0001\u0002\u0011\u0002\u0007\u0005\u0011B\u0001\u0007QSZ|G/\u00128d_\u0012,'O\u0003\u0002\u0004\t\u0005I!-\u001b6fGRLwN\u001c\u0006\u0003\u000b\u0019\tq\u0001^<jiR,'OC\u0001\b\u0003\r\u0019w.\\\u0002\u0001+\u0011Q!eM\u001c\u0014\t\u0001Y\u0011#\u000f\t\u0003\u0019=i\u0011!\u0004\u0006\u0002\u001d\u0005)1oY1mC&\u0011\u0001#\u0004\u0002\u0007\u0003:L(+\u001a4\u0011\t1\u0011BcK\u0005\u0003'5\u0011\u0011BR;oGRLwN\\\u0019\u0011\u0007Ui\u0002E\u0004\u0002\u001779\u0011qCG\u0007\u00021)\u0011\u0011\u0004C\u0001\u0007yI|w\u000e\u001e \n\u00039I!\u0001H\u0007\u0002\u000fA\f7m[1hK&\u0011ad\b\u0002\t\u0013R,'/\u00192mK*\u0011A$\u0004\t\u0003C\tb\u0001\u0001B\u0003$\u0001\t\u0007AEA\u0001L#\t)\u0003\u0006\u0005\u0002\rM%\u0011q%\u0004\u0002\b\u001d>$\b.\u001b8h!\ta\u0011&\u0003\u0002+\u001b\t\u0019\u0011I\\=\u0011\t1z#'\u000e\b\u0003\u00195J!AL\u0007\u0002\rA\u0013X\rZ3g\u0013\t\u0001\u0014GA\u0002NCBT!AL\u0007\u0011\u0005\u0005\u001aD!\u0002\u001b\u0001\u0005\u0004!#AA&2!\r)RD\u000e\t\u0003C]\"Q\u0001\u000f\u0001C\u0002\u0011\u0012!a\u0013\u001a\u0011\u0005izT\"A\u001e\u000b\u0005qj\u0014AA5p\u0015\u0005q\u0014\u0001\u00026bm\u0006L!\u0001Q\u001e\u0003\u0019M+'/[1mSj\f'\r\\3\t\u000b\t\u0003A\u0011A\"\u0002\r\u0011Jg.\u001b;%)\u0005!\u0005C\u0001\u0007F\u0013\t1UB\u0001\u0003V]&$\b\"\u0002%\u0001\r\u0003I\u0015aA3oGV\t!\n\u0005\u0003\r%\u0001Z\u0005\u0003\u0002\u0007MeYJ!!T\u0007\u0003\rQ+\b\u000f\\33\u0011\u0015y\u0005\u0001\"\u0001Q\u0003\u0015\t\u0007\u000f\u001d7z)\tY\u0013\u000bC\u0003S\u001d\u0002\u0007A#A\u0003qC&\u00148\u000fC\u0003U\u0001\u0011\u0005Q+A\u0004v]N\u0004H.\u001b;\u0016\u0005YKFCA,\\!\u0011a!\u0003\t-\u0011\u0005\u0005JF!\u0002.T\u0005\u0004!#!\u0001,\t\u000bq\u001b\u0006\u0019A/\u0002\u0005\u0019t\u0007\u0003\u0002\u0007\u0013ey\u0003B\u0001\u0004\n71\u0002")
public interface PivotEncoder<K, K1, K2> extends Function1<Iterable<K>, Map<K1, Iterable<K2>>>, Serializable
{
    Function1<K, Tuple2<K1, K2>> enc();
    
    Map<K1, Iterable<K2>> apply(final Iterable<K> p0);
    
     <V> Function1<K, V> unsplit(final Function1<K1, Function1<K2, V>> p0);
}
