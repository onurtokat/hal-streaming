// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.collection.Iterable;
import scala.Option;
import scala.runtime.Nothing$;
import scala.collection.generic.CanBuildFrom;
import scala.collection.immutable.List;
import scala.collection.TraversableOnce;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes = "\u0006\u0001\u0005ms!B\u0001\u0003\u0011\u0003I\u0011aE*ue&twMS8j]\nK'.Z2uS>t'BA\u0002\u0005\u0003%\u0011\u0017N[3di&|gN\u0003\u0002\u0006\r\u00059Ao^5ui\u0016\u0014(\"A\u0004\u0002\u0007\r|Wn\u0001\u0001\u0011\u0005)YQ\"\u0001\u0002\u0007\u000b1\u0011\u0001\u0012A\u0007\u0003'M#(/\u001b8h\u0015>LgNQ5kK\u000e$\u0018n\u001c8\u0014\u0005-q\u0001CA\b\u0013\u001b\u0005\u0001\"\"A\t\u0002\u000bM\u001c\u0017\r\\1\n\u0005M\u0001\"AB!osJ+g\rC\u0003\u0016\u0017\u0011\u0005a#\u0001\u0004=S:LGO\u0010\u000b\u0002\u0013!9\u0001d\u0003b\u0001\n\u0003I\u0012a\u0003#F\r\u0006+F\nV0T\u000bB+\u0012A\u0007\t\u00037\u0001j\u0011\u0001\b\u0006\u0003;y\tA\u0001\\1oO*\tq$\u0001\u0003kCZ\f\u0017BA\u0011\u001d\u0005\u0019\u0019FO]5oO\"11e\u0003Q\u0001\ni\tA\u0002R#G\u0003VcEkX*F!\u0002Ba!J\u0006\u0005\u0002\t1\u0013!B:qY&$H\u0003B\u0014:wu\u00022\u0001\u000b\u00194\u001d\tIcF\u0004\u0002+[5\t1F\u0003\u0002-\u0011\u00051AH]8pizJ\u0011!E\u0005\u0003_A\tq\u0001]1dW\u0006<W-\u0003\u00022e\t!A*[:u\u0015\ty\u0003\u0003\u0005\u00025o9\u0011q\"N\u0005\u0003mA\ta\u0001\u0015:fI\u00164\u0017BA\u00119\u0015\t1\u0004\u0003C\u0003;I\u0001\u00071'A\u0002tiJDQ\u0001\u0010\u0013A\u0002M\n1a]3q\u0011\u001dqD\u0005%AA\u0002\u001d\n1!Y2dQ\t!\u0003\t\u0005\u0002B\t6\t!I\u0003\u0002D!\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005\u0015\u0013%a\u0002;bS2\u0014Xm\u0019\u0005\u0006\u000f.!\t\u0001S\u0001\u0006CB\u0004H.\u001f\u000b\u0003\u0013J\u0003BA\u0003&M\u001f&\u00111J\u0001\u0002\n\u0005&TWm\u0019;j_:\u00042\u0001K'4\u0013\tq%G\u0001\u0005Ji\u0016\u0014\u0018M\u00197f!\ry\u0001kM\u0005\u0003#B\u0011aa\u00149uS>t\u0007bB*G!\u0003\u0005\raM\u0001\ng\u0016\u0004\u0018M]1u_JDQ!V\u0006\u0005\u0002Y\u000baB\\8o\u000b6\u0004H/\u001f,bYV,7/F\u0002XMr#\"\u0001W>\u0015\u0007ec\u0017\u000f\u0005\u0003\u000b\u0015j\u001b\u0004CA.]\u0019\u0001!Q!\u0018+C\u0002y\u0013\u0011AQ\t\u0003?\n\u0004\"a\u00041\n\u0005\u0005\u0004\"a\u0002(pi\"Lgn\u001a\t\u0004Q\r,\u0017B\u000133\u0005=!&/\u0019<feN\f'\r\\3P]\u000e,\u0007CA.g\t\u00159GK1\u0001i\u0005\u0005q\u0015CA0j!\ty!.\u0003\u0002l!\t\u0019\u0011I\\=\t\u000b5$\u00069\u00018\u0002\u0007\tL'\u000e\u0005\u0003\u000b_\u0016\u001c\u0014B\u00019\u0003\u0005EIU\u000e\u001d7jG&$()\u001b6fGRLwN\u001c\u0005\u0006eR\u0003\u001da]\u0001\u0003C\n\u0004R\u0001^=`Kjk\u0011!\u001e\u0006\u0003m^\fqaZ3oKJL7M\u0003\u0002y!\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\u0005i,(\u0001D\"b]\n+\u0018\u000e\u001c3Ge>l\u0007bB*U!\u0003\u0005\ra\r\u0005\u0006{.!\tA`\u0001\rm&\f7i\u001c8uC&tWM]\u000b\u0006\u007f\u0006=\u0011q\u0001\u000b\u0005\u0003\u0003\tY\u0002\u0006\u0004\u0002\u0004\u0005M\u0011q\u0003\t\u0006\u0015)\u000b)a\u0014\t\u00047\u0006\u001dAAB/}\u0005\u0004\tI!E\u0002`\u0003\u0017\u0001B\u0001K2\u0002\u000eA\u00191,a\u0004\u0005\r\u0005EAP1\u0001i\u0005\u0005\t\u0005BB7}\u0001\b\t)\u0002E\u0003\u000b\u0015\u000651\u0007\u0003\u0004sy\u0002\u000f\u0011\u0011\u0004\t\bif|\u0016QBA\u0003\u0011\u001d\u0019F\u0010%AA\u0002MB\u0011\"a\b\f#\u0003%\t!!\t\u0002\u001fM\u0004H.\u001b;%I\u00164\u0017-\u001e7uIM*\"!a\t+\u0007\u001d\n)c\u000b\u0002\u0002(A!\u0011\u0011FA\u0018\u001b\t\tYCC\u0002\u0002.\t\u000b\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\n\t\u0005E\u00121\u0006\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0007\"CA\u001b\u0017E\u0005I\u0011AA\u001c\u0003=\t\u0007\u000f\u001d7zI\u0011,g-Y;mi\u0012\nTCAA\u001dU\r\u0019\u0014Q\u0005\u0005\n\u0003{Y\u0011\u0013!C\u0001\u0003\u007f\t\u0001D\\8o\u000b6\u0004H/\u001f,bYV,7\u000f\n3fM\u0006,H\u000e\u001e\u00132+\u0019\t9$!\u0011\u0002D\u00111q-a\u000fC\u0002!$q!XA\u001e\u0005\u0004\t)%E\u0002`\u0003\u000f\u0002B\u0001K2\u0002JA\u00191,!\u0011\t\u0013\u000553\"%A\u0005\u0002\u0005=\u0013A\u0006<jC\u000e{g\u000e^1j]\u0016\u0014H\u0005Z3gCVdG\u000fJ\u0019\u0016\r\u0005]\u0012\u0011KA*\t\u001d\t\t\"a\u0013C\u0002!$q!XA&\u0005\u0004\t)&E\u0002`\u0003/\u0002B\u0001K2\u0002ZA\u00191,!\u0015")
public final class StringJoinBijection
{
    public static <A, B extends TraversableOnce<A>> String viaContainer$default$1() {
        return StringJoinBijection$.MODULE$.viaContainer$default$1();
    }
    
    public static <N, B extends TraversableOnce<N>> String nonEmptyValues$default$1() {
        return StringJoinBijection$.MODULE$.nonEmptyValues$default$1();
    }
    
    public static String apply$default$1() {
        return StringJoinBijection$.MODULE$.apply$default$1();
    }
    
    public static List<String> split$default$3() {
        return StringJoinBijection$.MODULE$.split$default$3();
    }
    
    public static <A, B extends TraversableOnce<A>> Bijection<B, Option<String>> viaContainer(final String separator, final Bijection<A, String> bij, final CanBuildFrom<Nothing$, A, B> ab) {
        return StringJoinBijection$.MODULE$.viaContainer(separator, bij, ab);
    }
    
    public static <N, B extends TraversableOnce<N>> Bijection<B, String> nonEmptyValues(final String separator, final ImplicitBijection<N, String> bij, final CanBuildFrom<Nothing$, N, B> ab) {
        return StringJoinBijection$.MODULE$.nonEmptyValues(separator, bij, ab);
    }
    
    public static Bijection<Iterable<String>, Option<String>> apply(final String separator) {
        return StringJoinBijection$.MODULE$.apply(separator);
    }
    
    public static String DEFAULT_SEP() {
        return StringJoinBijection$.MODULE$.DEFAULT_SEP();
    }
}
