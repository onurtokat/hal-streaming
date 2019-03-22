// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.Tuple2;
import scala.runtime.AbstractFunction2;
import scala.runtime.Statics;
import scala.runtime.ScalaRunTime$;
import scala.runtime.BoxesRunTime;
import scala.Some;
import scala.None$;
import scala.Option;
import scala.runtime.AbstractFunction1;
import scala.Product$class;
import scala.collection.Iterator;
import scala.Serializable;
import scala.Product;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes = "\u0006\u0001\r}t!B\u0001\u0003\u0011\u0003I\u0011!\u0004(v[\n,'oU=ti\u0016l7O\u0003\u0002\u0004\t\u0005I!-\u001b6fGRLwN\u001c\u0006\u0003\u000b\u0019\tq\u0001^<jiR,'OC\u0001\b\u0003\r\u0019w.\\\u0002\u0001!\tQ1\"D\u0001\u0003\r\u0015a!\u0001#\u0001\u000e\u00055qU/\u001c2feNK8\u000f^3ngN\u00111B\u0004\t\u0003\u001fIi\u0011\u0001\u0005\u0006\u0002#\u0005)1oY1mC&\u00111\u0003\u0005\u0002\u0007\u0003:L(+\u001a4\t\u000bUYA\u0011\u0001\f\u0002\rqJg.\u001b;?)\u0005Ia\u0001\u0002\r\f\u0005f\u0011ABQ5oCJL8\u000b\u001e:j]\u001e\u001cBa\u0006\u000e\u001eAA\u0011qbG\u0005\u00039A\u0011a!\u00118z-\u0006d\u0007CA\b\u001f\u0013\ty\u0002CA\u0004Qe>$Wo\u0019;\u0011\u0005=\t\u0013B\u0001\u0012\u0011\u00051\u0019VM]5bY&T\u0018M\u00197f\u0011!!sC!f\u0001\n\u0003)\u0013aA4fiV\ta\u0005\u0005\u0002(U9\u0011q\u0002K\u0005\u0003SA\ta\u0001\u0015:fI\u00164\u0017BA\u0016-\u0005\u0019\u0019FO]5oO*\u0011\u0011\u0006\u0005\u0005\t]]\u0011\t\u0012)A\u0005M\u0005!q-\u001a;!\u0011\u0015)r\u0003\"\u00011)\t\t4\u0007\u0005\u00023/5\t1\u0002C\u0003%_\u0001\u0007a\u0005C\u00046/\u0005\u0005I\u0011\u0001\u001c\u0002\t\r|\u0007/\u001f\u000b\u0003c]Bq\u0001\n\u001b\u0011\u0002\u0003\u0007a\u0005C\u0004:/E\u0005I\u0011\u0001\u001e\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\t1H\u000b\u0002'y-\nQ\b\u0005\u0002?\u00076\tqH\u0003\u0002A\u0003\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0003\u0005B\t!\"\u00198o_R\fG/[8o\u0013\t!uHA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016DqAR\f\u0002\u0002\u0013\u0005s)A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0002\u0011B\u0011\u0011JT\u0007\u0002\u0015*\u00111\nT\u0001\u0005Y\u0006twMC\u0001N\u0003\u0011Q\u0017M^1\n\u0005-R\u0005b\u0002)\u0018\u0003\u0003%\t!U\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0002%B\u0011qbU\u0005\u0003)B\u00111!\u00138u\u0011\u001d1v#!A\u0005\u0002]\u000ba\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0002Y7B\u0011q\"W\u0005\u00035B\u00111!\u00118z\u0011\u001daV+!AA\u0002I\u000b1\u0001\u001f\u00132\u0011\u001dqv#!A\u0005B}\u000bq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0002AB\u0019\u0011\r\u001a-\u000e\u0003\tT!a\u0019\t\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0002fE\nA\u0011\n^3sCR|'\u000fC\u0004h/\u0005\u0005I\u0011\u00015\u0002\u0011\r\fg.R9vC2$\"!\u001b7\u0011\u0005=Q\u0017BA6\u0011\u0005\u001d\u0011un\u001c7fC:Dq\u0001\u00184\u0002\u0002\u0003\u0007\u0001\fC\u0004o/\u0005\u0005I\u0011I8\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012A\u0015\u0005\bc^\t\t\u0011\"\u0011s\u0003\u0019)\u0017/^1mgR\u0011\u0011n\u001d\u0005\b9B\f\t\u00111\u0001Y\u0011\u001d)x#!A\u0005BY\f\u0001\u0002^8TiJLgn\u001a\u000b\u0002\u0011\u001e9\u0001pCA\u0001\u0012\u0003I\u0018\u0001\u0004\"j]\u0006\u0014\u0018p\u0015;sS:<\u0007C\u0001\u001a{\r\u001dA2\"!A\t\u0002m\u001c2A\u001f?!!\u0015i\u0018\u0011\u0001\u00142\u001b\u0005q(BA@\u0011\u0003\u001d\u0011XO\u001c;j[\u0016L1!a\u0001\u007f\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|g.\r\u0005\u0007+i$\t!a\u0002\u0015\u0003eDq!\u001e>\u0002\u0002\u0013\u0015c\u000fC\u0005\u0002\u000ei\f\t\u0011\"!\u0002\u0010\u0005)\u0011\r\u001d9msR\u0019\u0011'!\u0005\t\r\u0011\nY\u00011\u0001'\u0011%\t)B_A\u0001\n\u0003\u000b9\"A\u0004v]\u0006\u0004\b\u000f\\=\u0015\t\u0005e\u0011q\u0004\t\u0005\u001f\u0005ma%C\u0002\u0002\u001eA\u0011aa\u00149uS>t\u0007\"CA\u0011\u0003'\t\t\u00111\u00012\u0003\rAH\u0005\r\u0005\n\u0003KQ\u0018\u0011!C\u0005\u0003O\t1B]3bIJ+7o\u001c7wKR\u0011\u0011\u0011\u0006\t\u0004\u0013\u0006-\u0012bAA\u0017\u0015\n1qJ\u00196fGRD\u0011\"!\r{\u0003\u0003%)!a\r\u0002\u001d\r|\u0007/\u001f\u0013fqR,gn]5p]R!\u0011QGA\u001d)\r\t\u0014q\u0007\u0005\tI\u0005=\u0002\u0013!a\u0001M!9\u00111HA\u0018\u0001\u0004\t\u0014!\u0002\u0013uQ&\u001c\b\"CA uF\u0005IQAA!\u0003a\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE\"S\r\u001f;f]NLwN\u001c\u000b\u0004w\u0005\r\u0003bBA\u001e\u0003{\u0001\r!\r\u0005\n\u0003\u000fR\u0018\u0011!C\u0003\u0003\u0013\nq\u0003\u001d:pIV\u001cG\u000f\u0015:fM&DH%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0007!\u000bY\u0005C\u0004\u0002<\u0005\u0015\u0003\u0019A\u0019\t\u0013\u0005=#0!A\u0005\u0006\u0005E\u0013A\u00069s_\u0012,8\r^!sSRLH%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0007I\u000b\u0019\u0006C\u0004\u0002<\u00055\u0003\u0019A\u0019\t\u0013\u0005]#0!A\u0005\u0006\u0005e\u0013\u0001\u00079s_\u0012,8\r^#mK6,g\u000e\u001e\u0013fqR,gn]5p]R!\u00111LA0)\rA\u0016Q\f\u0005\t9\u0006U\u0013\u0011!a\u0001%\"9\u00111HA+\u0001\u0004\t\u0004\"CA2u\u0006\u0005IQAA3\u0003e\u0001(o\u001c3vGRLE/\u001a:bi>\u0014H%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0007\u0001\f9\u0007C\u0004\u0002<\u0005\u0005\u0004\u0019A\u0019\t\u0013\u0005-$0!A\u0005\u0006\u00055\u0014AE2b]\u0016\u000bX/\u00197%Kb$XM\\:j_:$B!a\u001c\u0002tQ\u0019\u0011.!\u001d\t\u0011q\u000bI'!AA\u0002aCq!a\u000f\u0002j\u0001\u0007\u0011\u0007C\u0005\u0002xi\f\t\u0011\"\u0002\u0002z\u0005\u0011\u0002.Y:i\u0007>$W\rJ3yi\u0016t7/[8o)\ry\u00171\u0010\u0005\b\u0003w\t)\b1\u00012\u0011%\tyH_A\u0001\n\u000b\t\t)\u0001\tfcV\fGn\u001d\u0013fqR,gn]5p]R!\u00111QAD)\rI\u0017Q\u0011\u0005\t9\u0006u\u0014\u0011!a\u00011\"9\u00111HA?\u0001\u0004\t\u0004\"CAFu\u0006\u0005IQAAG\u0003I!xn\u0015;sS:<G%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0007Y\fy\tC\u0004\u0002<\u0005%\u0005\u0019A\u0019\u0007\r\u0005M5BQAK\u0005%AU\r_*ue&twmE\u0003\u0002\u0012ji\u0002\u0005C\u0005%\u0003#\u0013)\u001a!C\u0001K!Ia&!%\u0003\u0012\u0003\u0006IA\n\u0005\b+\u0005EE\u0011AAO)\u0011\ty*!)\u0011\u0007I\n\t\n\u0003\u0004%\u00037\u0003\rA\n\u0005\nk\u0005E\u0015\u0011!C\u0001\u0003K#B!a(\u0002(\"AA%a)\u0011\u0002\u0003\u0007a\u0005\u0003\u0005:\u0003#\u000b\n\u0011\"\u0001;\u0011!1\u0015\u0011SA\u0001\n\u0003:\u0005\u0002\u0003)\u0002\u0012\u0006\u0005I\u0011A)\t\u0013Y\u000b\t*!A\u0005\u0002\u0005EFc\u0001-\u00024\"AA,a,\u0002\u0002\u0003\u0007!\u000b\u0003\u0005_\u0003#\u000b\t\u0011\"\u0011`\u0011%9\u0017\u0011SA\u0001\n\u0003\tI\fF\u0002j\u0003wC\u0001\u0002XA\\\u0003\u0003\u0005\r\u0001\u0017\u0005\t]\u0006E\u0015\u0011!C!_\"I\u0011/!%\u0002\u0002\u0013\u0005\u0013\u0011\u0019\u000b\u0004S\u0006\r\u0007\u0002\u0003/\u0002@\u0006\u0005\t\u0019\u0001-\t\u0011U\f\t*!A\u0005BY<\u0011\"!3\f\u0003\u0003E\t!a3\u0002\u0013!+\u0007p\u0015;sS:<\u0007c\u0001\u001a\u0002N\u001aI\u00111S\u0006\u0002\u0002#\u0005\u0011qZ\n\u0006\u0003\u001b\f\t\u000e\t\t\u0007{\u0006\u0005a%a(\t\u000fU\ti\r\"\u0001\u0002VR\u0011\u00111\u001a\u0005\tk\u00065\u0017\u0011!C#m\"Q\u0011QBAg\u0003\u0003%\t)a7\u0015\t\u0005}\u0015Q\u001c\u0005\u0007I\u0005e\u0007\u0019\u0001\u0014\t\u0015\u0005U\u0011QZA\u0001\n\u0003\u000b\t\u000f\u0006\u0003\u0002\u001a\u0005\r\bBCA\u0011\u0003?\f\t\u00111\u0001\u0002 \"Q\u0011QEAg\u0003\u0003%I!a\n\t\u0015\u0005E\u0012QZA\u0001\n\u000b\tI\u000f\u0006\u0003\u0002l\u0006=H\u0003BAP\u0003[D\u0001\u0002JAt!\u0003\u0005\rA\n\u0005\t\u0003w\t9\u000f1\u0001\u0002 \"Q\u0011qHAg#\u0003%)!a=\u0015\u0007m\n)\u0010\u0003\u0005\u0002<\u0005E\b\u0019AAP\u0011)\t9%!4\u0002\u0002\u0013\u0015\u0011\u0011 \u000b\u0004\u0011\u0006m\b\u0002CA\u001e\u0003o\u0004\r!a(\t\u0015\u0005=\u0013QZA\u0001\n\u000b\ty\u0010F\u0002S\u0005\u0003A\u0001\"a\u000f\u0002~\u0002\u0007\u0011q\u0014\u0005\u000b\u0003/\ni-!A\u0005\u0006\t\u0015A\u0003\u0002B\u0004\u0005\u0017!2\u0001\u0017B\u0005\u0011!a&1AA\u0001\u0002\u0004\u0011\u0006\u0002CA\u001e\u0005\u0007\u0001\r!a(\t\u0015\u0005\r\u0014QZA\u0001\n\u000b\u0011y\u0001F\u0002a\u0005#A\u0001\"a\u000f\u0003\u000e\u0001\u0007\u0011q\u0014\u0005\u000b\u0003W\ni-!A\u0005\u0006\tUA\u0003\u0002B\f\u00057!2!\u001bB\r\u0011!a&1CA\u0001\u0002\u0004A\u0006\u0002CA\u001e\u0005'\u0001\r!a(\t\u0015\u0005]\u0014QZA\u0001\n\u000b\u0011y\u0002F\u0002p\u0005CA\u0001\"a\u000f\u0003\u001e\u0001\u0007\u0011q\u0014\u0005\u000b\u0003\u007f\ni-!A\u0005\u0006\t\u0015B\u0003\u0002B\u0014\u0005W!2!\u001bB\u0015\u0011!a&1EA\u0001\u0002\u0004A\u0006\u0002CA\u001e\u0005G\u0001\r!a(\t\u0015\u0005-\u0015QZA\u0001\n\u000b\u0011y\u0003F\u0002w\u0005cA\u0001\"a\u000f\u0003.\u0001\u0007\u0011q\u0014\u0004\u0007\u0005kY!Ia\u000e\u0003\u0017=\u001bG/\u00197TiJLgnZ\n\u0006\u0005gQR\u0004\t\u0005\nI\tM\"Q3A\u0005\u0002\u0015B\u0011B\fB\u001a\u0005#\u0005\u000b\u0011\u0002\u0014\t\u000fU\u0011\u0019\u0004\"\u0001\u0003@Q!!\u0011\tB\"!\r\u0011$1\u0007\u0005\u0007I\tu\u0002\u0019\u0001\u0014\t\u0013U\u0012\u0019$!A\u0005\u0002\t\u001dC\u0003\u0002B!\u0005\u0013B\u0001\u0002\nB#!\u0003\u0005\rA\n\u0005\ts\tM\u0012\u0013!C\u0001u!AaIa\r\u0002\u0002\u0013\u0005s\t\u0003\u0005Q\u0005g\t\t\u0011\"\u0001R\u0011%1&1GA\u0001\n\u0003\u0011\u0019\u0006F\u0002Y\u0005+B\u0001\u0002\u0018B)\u0003\u0003\u0005\rA\u0015\u0005\t=\nM\u0012\u0011!C!?\"IqMa\r\u0002\u0002\u0013\u0005!1\f\u000b\u0004S\nu\u0003\u0002\u0003/\u0003Z\u0005\u0005\t\u0019\u0001-\t\u00119\u0014\u0019$!A\u0005B=D\u0011\"\u001dB\u001a\u0003\u0003%\tEa\u0019\u0015\u0007%\u0014)\u0007\u0003\u0005]\u0005C\n\t\u00111\u0001Y\u0011!)(1GA\u0001\n\u00032x!\u0003B6\u0017\u0005\u0005\t\u0012\u0001B7\u0003-y5\r^1m'R\u0014\u0018N\\4\u0011\u0007I\u0012yGB\u0005\u00036-\t\t\u0011#\u0001\u0003rM)!q\u000eB:AA1Q0!\u0001'\u0005\u0003Bq!\u0006B8\t\u0003\u00119\b\u0006\u0002\u0003n!AQOa\u001c\u0002\u0002\u0013\u0015c\u000f\u0003\u0006\u0002\u000e\t=\u0014\u0011!CA\u0005{\"BA!\u0011\u0003\u0000!1AEa\u001fA\u0002\u0019B!\"!\u0006\u0003p\u0005\u0005I\u0011\u0011BB)\u0011\tIB!\"\t\u0015\u0005\u0005\"\u0011QA\u0001\u0002\u0004\u0011\t\u0005\u0003\u0006\u0002&\t=\u0014\u0011!C\u0005\u0003OA!\"!\r\u0003p\u0005\u0005IQ\u0001BF)\u0011\u0011iI!%\u0015\t\t\u0005#q\u0012\u0005\tI\t%\u0005\u0013!a\u0001M!A\u00111\bBE\u0001\u0004\u0011\t\u0005\u0003\u0006\u0002@\t=\u0014\u0013!C\u0003\u0005+#2a\u000fBL\u0011!\tYDa%A\u0002\t\u0005\u0003BCA$\u0005_\n\t\u0011\"\u0002\u0003\u001cR\u0019\u0001J!(\t\u0011\u0005m\"\u0011\u0014a\u0001\u0005\u0003B!\"a\u0014\u0003p\u0005\u0005IQ\u0001BQ)\r\u0011&1\u0015\u0005\t\u0003w\u0011y\n1\u0001\u0003B!Q\u0011q\u000bB8\u0003\u0003%)Aa*\u0015\t\t%&Q\u0016\u000b\u00041\n-\u0006\u0002\u0003/\u0003&\u0006\u0005\t\u0019\u0001*\t\u0011\u0005m\"Q\u0015a\u0001\u0005\u0003B!\"a\u0019\u0003p\u0005\u0005IQ\u0001BY)\r\u0001'1\u0017\u0005\t\u0003w\u0011y\u000b1\u0001\u0003B!Q\u00111\u000eB8\u0003\u0003%)Aa.\u0015\t\te&Q\u0018\u000b\u0004S\nm\u0006\u0002\u0003/\u00036\u0006\u0005\t\u0019\u0001-\t\u0011\u0005m\"Q\u0017a\u0001\u0005\u0003B!\"a\u001e\u0003p\u0005\u0005IQ\u0001Ba)\ry'1\u0019\u0005\t\u0003w\u0011y\f1\u0001\u0003B!Q\u0011q\u0010B8\u0003\u0003%)Aa2\u0015\t\t%'Q\u001a\u000b\u0004S\n-\u0007\u0002\u0003/\u0003F\u0006\u0005\t\u0019\u0001-\t\u0011\u0005m\"Q\u0019a\u0001\u0005\u0003B!\"a#\u0003p\u0005\u0005IQ\u0001Bi)\r1(1\u001b\u0005\t\u0003w\u0011y\r1\u0001\u0003B\u00191!q[\u0006A\u00053\u0014q\"\u0011:cSR\u0014\u0015m]3TiJLgnZ\n\u0006\u0005+tQ\u0004\t\u0005\nI\tU'Q3A\u0005\u0002\u0015B\u0011B\fBk\u0005#\u0005\u000b\u0011\u0002\u0014\t\u0015\t\u0005(Q\u001bBK\u0002\u0013\u0005\u0011+\u0001\u0003cCN,\u0007B\u0003Bs\u0005+\u0014\t\u0012)A\u0005%\u0006)!-Y:fA!9QC!6\u0005\u0002\t%HC\u0002Bv\u0005[\u0014y\u000fE\u00023\u0005+Da\u0001\nBt\u0001\u00041\u0003b\u0002Bq\u0005O\u0004\rA\u0015\u0005\nk\tU\u0017\u0011!C\u0001\u0005g$bAa;\u0003v\n]\b\u0002\u0003\u0013\u0003rB\u0005\t\u0019\u0001\u0014\t\u0013\t\u0005(\u0011\u001fI\u0001\u0002\u0004\u0011\u0006\u0002C\u001d\u0003VF\u0005I\u0011\u0001\u001e\t\u0015\tu(Q[I\u0001\n\u0003\u0011y0\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u0016\u0005\r\u0005!F\u0001*=\u0011!1%Q[A\u0001\n\u0003:\u0005\u0002\u0003)\u0003V\u0006\u0005I\u0011A)\t\u0013Y\u0013).!A\u0005\u0002\r%Ac\u0001-\u0004\f!AAla\u0002\u0002\u0002\u0003\u0007!\u000b\u0003\u0005_\u0005+\f\t\u0011\"\u0011`\u0011%9'Q[A\u0001\n\u0003\u0019\t\u0002F\u0002j\u0007'A\u0001\u0002XB\b\u0003\u0003\u0005\r\u0001\u0017\u0005\t]\nU\u0017\u0011!C!_\"AQO!6\u0002\u0002\u0013\u0005c\u000fC\u0005r\u0005+\f\t\u0011\"\u0011\u0004\u001cQ\u0019\u0011n!\b\t\u0011q\u001bI\"!AA\u0002a;\u0011b!\t\f\u0003\u0003E\taa\t\u0002\u001f\u0005\u0013(-\u001b;CCN,7\u000b\u001e:j]\u001e\u00042AMB\u0013\r%\u00119nCA\u0001\u0012\u0003\u00199cE\u0003\u0004&\r%\u0002\u0005E\u0004~\u0007W1#Ka;\n\u0007\r5bPA\tBEN$(/Y2u\rVt7\r^5p]JBq!FB\u0013\t\u0003\u0019\t\u0004\u0006\u0002\u0004$!AQo!\n\u0002\u0002\u0013\u0015c\u000f\u0003\u0006\u0002\u000e\r\u0015\u0012\u0011!CA\u0007o!bAa;\u0004:\rm\u0002B\u0002\u0013\u00046\u0001\u0007a\u0005C\u0004\u0003b\u000eU\u0002\u0019\u0001*\t\u0015\u0005U1QEA\u0001\n\u0003\u001by\u0004\u0006\u0003\u0004B\r%\u0003#B\b\u0002\u001c\r\r\u0003#B\b\u0004F\u0019\u0012\u0016bAB$!\t1A+\u001e9mKJB!\"!\t\u0004>\u0005\u0005\t\u0019\u0001Bv\u0011)\t)c!\n\u0002\u0002\u0013%\u0011q\u0005\u0005\n\u0007\u001fZ!\u0019!C\u0002\u0007#\naAY5oCJLXCAB*!\u0015Q1Q\u000b*2\u0013\r\u00199F\u0001\u0002\n\u0005&TWm\u0019;j_:D\u0001ba\u0017\fA\u0003%11K\u0001\bE&t\u0017M]=!\u0011%\u0019yf\u0003b\u0001\n\u0007\u0019\t'A\u0006iKb\fG-Z2j[\u0006dWCAB2!\u0019Q1Q\u000b*\u0002 \"A1qM\u0006!\u0002\u0013\u0019\u0019'\u0001\u0007iKb\fG-Z2j[\u0006d\u0007\u0005C\u0005\u0004l-\u0011\r\u0011b\u0001\u0004n\u0005)qn\u0019;bYV\u00111q\u000e\t\u0007\u0015\rU#K!\u0011\t\u0011\rM4\u0002)A\u0005\u0007_\naa\\2uC2\u0004\u0003bBB<\u0017\u0011\u00051\u0011P\u0001\nCJ\u0014\u0017\u000e\u001e2bg\u0016$Baa\u001f\u0004~A1!b!\u0016S\u0005WDqA!9\u0004v\u0001\u0007!\u000b")
public final class NumberSystems
{
    public static Bijection<Object, ArbitBaseString> arbitbase(final int base) {
        return NumberSystems$.MODULE$.arbitbase(base);
    }
    
    public static Bijection<Object, String> octal() {
        return NumberSystems$.MODULE$.octal();
    }
    
    public static Bijection<Object, String> hexadecimal() {
        return NumberSystems$.MODULE$.hexadecimal();
    }
    
    public static Bijection<Object, String> binary() {
        return NumberSystems$.MODULE$.binary();
    }
    
    public static final class BinaryString implements Product, Serializable
    {
        private final String get;
        
        public String get() {
            return this.get;
        }
        
        public String copy(final String get) {
            return BinaryString$.MODULE$.copy$extension(this.get(), get);
        }
        
        public String copy$default$1() {
            return BinaryString$.MODULE$.copy$default$1$extension(this.get());
        }
        
        @Override
        public String productPrefix() {
            return BinaryString$.MODULE$.productPrefix$extension(this.get());
        }
        
        @Override
        public int productArity() {
            return BinaryString$.MODULE$.productArity$extension(this.get());
        }
        
        @Override
        public Object productElement(final int x$1) {
            return BinaryString$.MODULE$.productElement$extension(this.get(), x$1);
        }
        
        @Override
        public Iterator<Object> productIterator() {
            return BinaryString$.MODULE$.productIterator$extension(this.get());
        }
        
        @Override
        public boolean canEqual(final Object x$1) {
            return BinaryString$.MODULE$.canEqual$extension(this.get(), x$1);
        }
        
        @Override
        public int hashCode() {
            return BinaryString$.MODULE$.hashCode$extension(this.get());
        }
        
        @Override
        public boolean equals(final Object x$1) {
            return BinaryString$.MODULE$.equals$extension(this.get(), x$1);
        }
        
        @Override
        public String toString() {
            return BinaryString$.MODULE$.toString$extension(this.get());
        }
        
        public BinaryString(final String get) {
            this.get = get;
            Product$class.$init$(this);
        }
    }
    
    public static class BinaryString$ extends AbstractFunction1<String, String> implements Serializable
    {
        public static final BinaryString$ MODULE$;
        
        static {
            new BinaryString$();
        }
        
        @Override
        public final String toString() {
            return "BinaryString";
        }
        
        @Override
        public String apply(final String get) {
            return get;
        }
        
        public Option<String> unapply(final String x$0) {
            return (Option<String>)((new BinaryString(x$0) == null) ? None$.MODULE$ : new Some<Object>(x$0));
        }
        
        private Object readResolve() {
            return BinaryString$.MODULE$;
        }
        
        public final String copy$extension(final String $this, final String get) {
            return get;
        }
        
        public final String copy$default$1$extension(final String $this) {
            return $this;
        }
        
        public final String productPrefix$extension(final String $this) {
            return "BinaryString";
        }
        
        public final int productArity$extension(final String $this) {
            return 1;
        }
        
        public final Object productElement$extension(final String $this, final int x$1) {
            switch (x$1) {
                default: {
                    throw new IndexOutOfBoundsException(BoxesRunTime.boxToInteger(x$1).toString());
                }
                case 0: {
                    return $this;
                }
            }
        }
        
        public final Iterator<Object> productIterator$extension(final String $this) {
            return ScalaRunTime$.MODULE$.typedProductIterator(new BinaryString($this));
        }
        
        public final boolean canEqual$extension(final String $this, final Object x$1) {
            return x$1 instanceof String;
        }
        
        public final int hashCode$extension(final String $this) {
            return $this.hashCode();
        }
        
        public final boolean equals$extension(final String $this, final Object x$1) {
            if (x$1 instanceof BinaryString) {
                final String s = (x$1 == null) ? null : ((BinaryString)x$1).get();
                boolean b = false;
                Label_0071: {
                    Label_0070: {
                        if ($this == null) {
                            if (s != null) {
                                break Label_0070;
                            }
                        }
                        else if (!$this.equals(s)) {
                            break Label_0070;
                        }
                        b = true;
                        break Label_0071;
                    }
                    b = false;
                }
                if (b) {
                    return true;
                }
            }
            return false;
        }
        
        public final String toString$extension(final String $this) {
            return ScalaRunTime$.MODULE$._toString(new BinaryString($this));
        }
        
        public BinaryString$() {
            MODULE$ = this;
        }
    }
    
    public static final class HexString implements Product, Serializable
    {
        private final String get;
        
        public String get() {
            return this.get;
        }
        
        public String copy(final String get) {
            return HexString$.MODULE$.copy$extension(this.get(), get);
        }
        
        public String copy$default$1() {
            return HexString$.MODULE$.copy$default$1$extension(this.get());
        }
        
        @Override
        public String productPrefix() {
            return HexString$.MODULE$.productPrefix$extension(this.get());
        }
        
        @Override
        public int productArity() {
            return HexString$.MODULE$.productArity$extension(this.get());
        }
        
        @Override
        public Object productElement(final int x$1) {
            return HexString$.MODULE$.productElement$extension(this.get(), x$1);
        }
        
        @Override
        public Iterator<Object> productIterator() {
            return HexString$.MODULE$.productIterator$extension(this.get());
        }
        
        @Override
        public boolean canEqual(final Object x$1) {
            return HexString$.MODULE$.canEqual$extension(this.get(), x$1);
        }
        
        @Override
        public int hashCode() {
            return HexString$.MODULE$.hashCode$extension(this.get());
        }
        
        @Override
        public boolean equals(final Object x$1) {
            return HexString$.MODULE$.equals$extension(this.get(), x$1);
        }
        
        @Override
        public String toString() {
            return HexString$.MODULE$.toString$extension(this.get());
        }
        
        public HexString(final String get) {
            this.get = get;
            Product$class.$init$(this);
        }
    }
    
    public static class HexString$ extends AbstractFunction1<String, String> implements Serializable
    {
        public static final HexString$ MODULE$;
        
        static {
            new HexString$();
        }
        
        @Override
        public final String toString() {
            return "HexString";
        }
        
        @Override
        public String apply(final String get) {
            return get;
        }
        
        public Option<String> unapply(final String x$0) {
            return (Option<String>)((new HexString(x$0) == null) ? None$.MODULE$ : new Some<Object>(x$0));
        }
        
        private Object readResolve() {
            return HexString$.MODULE$;
        }
        
        public final String copy$extension(final String $this, final String get) {
            return get;
        }
        
        public final String copy$default$1$extension(final String $this) {
            return $this;
        }
        
        public final String productPrefix$extension(final String $this) {
            return "HexString";
        }
        
        public final int productArity$extension(final String $this) {
            return 1;
        }
        
        public final Object productElement$extension(final String $this, final int x$1) {
            switch (x$1) {
                default: {
                    throw new IndexOutOfBoundsException(BoxesRunTime.boxToInteger(x$1).toString());
                }
                case 0: {
                    return $this;
                }
            }
        }
        
        public final Iterator<Object> productIterator$extension(final String $this) {
            return ScalaRunTime$.MODULE$.typedProductIterator(new HexString($this));
        }
        
        public final boolean canEqual$extension(final String $this, final Object x$1) {
            return x$1 instanceof String;
        }
        
        public final int hashCode$extension(final String $this) {
            return $this.hashCode();
        }
        
        public final boolean equals$extension(final String $this, final Object x$1) {
            if (x$1 instanceof HexString) {
                final String s = (x$1 == null) ? null : ((HexString)x$1).get();
                boolean b = false;
                Label_0071: {
                    Label_0070: {
                        if ($this == null) {
                            if (s != null) {
                                break Label_0070;
                            }
                        }
                        else if (!$this.equals(s)) {
                            break Label_0070;
                        }
                        b = true;
                        break Label_0071;
                    }
                    b = false;
                }
                if (b) {
                    return true;
                }
            }
            return false;
        }
        
        public final String toString$extension(final String $this) {
            return ScalaRunTime$.MODULE$._toString(new HexString($this));
        }
        
        public HexString$() {
            MODULE$ = this;
        }
    }
    
    public static final class OctalString implements Product, Serializable
    {
        private final String get;
        
        public String get() {
            return this.get;
        }
        
        public String copy(final String get) {
            return OctalString$.MODULE$.copy$extension(this.get(), get);
        }
        
        public String copy$default$1() {
            return OctalString$.MODULE$.copy$default$1$extension(this.get());
        }
        
        @Override
        public String productPrefix() {
            return OctalString$.MODULE$.productPrefix$extension(this.get());
        }
        
        @Override
        public int productArity() {
            return OctalString$.MODULE$.productArity$extension(this.get());
        }
        
        @Override
        public Object productElement(final int x$1) {
            return OctalString$.MODULE$.productElement$extension(this.get(), x$1);
        }
        
        @Override
        public Iterator<Object> productIterator() {
            return OctalString$.MODULE$.productIterator$extension(this.get());
        }
        
        @Override
        public boolean canEqual(final Object x$1) {
            return OctalString$.MODULE$.canEqual$extension(this.get(), x$1);
        }
        
        @Override
        public int hashCode() {
            return OctalString$.MODULE$.hashCode$extension(this.get());
        }
        
        @Override
        public boolean equals(final Object x$1) {
            return OctalString$.MODULE$.equals$extension(this.get(), x$1);
        }
        
        @Override
        public String toString() {
            return OctalString$.MODULE$.toString$extension(this.get());
        }
        
        public OctalString(final String get) {
            this.get = get;
            Product$class.$init$(this);
        }
    }
    
    public static class OctalString$ extends AbstractFunction1<String, String> implements Serializable
    {
        public static final OctalString$ MODULE$;
        
        static {
            new OctalString$();
        }
        
        @Override
        public final String toString() {
            return "OctalString";
        }
        
        @Override
        public String apply(final String get) {
            return get;
        }
        
        public Option<String> unapply(final String x$0) {
            return (Option<String>)((new OctalString(x$0) == null) ? None$.MODULE$ : new Some<Object>(x$0));
        }
        
        private Object readResolve() {
            return OctalString$.MODULE$;
        }
        
        public final String copy$extension(final String $this, final String get) {
            return get;
        }
        
        public final String copy$default$1$extension(final String $this) {
            return $this;
        }
        
        public final String productPrefix$extension(final String $this) {
            return "OctalString";
        }
        
        public final int productArity$extension(final String $this) {
            return 1;
        }
        
        public final Object productElement$extension(final String $this, final int x$1) {
            switch (x$1) {
                default: {
                    throw new IndexOutOfBoundsException(BoxesRunTime.boxToInteger(x$1).toString());
                }
                case 0: {
                    return $this;
                }
            }
        }
        
        public final Iterator<Object> productIterator$extension(final String $this) {
            return ScalaRunTime$.MODULE$.typedProductIterator(new OctalString($this));
        }
        
        public final boolean canEqual$extension(final String $this, final Object x$1) {
            return x$1 instanceof String;
        }
        
        public final int hashCode$extension(final String $this) {
            return $this.hashCode();
        }
        
        public final boolean equals$extension(final String $this, final Object x$1) {
            if (x$1 instanceof OctalString) {
                final String s = (x$1 == null) ? null : ((OctalString)x$1).get();
                boolean b = false;
                Label_0071: {
                    Label_0070: {
                        if ($this == null) {
                            if (s != null) {
                                break Label_0070;
                            }
                        }
                        else if (!$this.equals(s)) {
                            break Label_0070;
                        }
                        b = true;
                        break Label_0071;
                    }
                    b = false;
                }
                if (b) {
                    return true;
                }
            }
            return false;
        }
        
        public final String toString$extension(final String $this) {
            return ScalaRunTime$.MODULE$._toString(new OctalString($this));
        }
        
        public OctalString$() {
            MODULE$ = this;
        }
    }
    
    public static class ArbitBaseString implements Product, Serializable
    {
        private final String get;
        private final int base;
        
        public String get() {
            return this.get;
        }
        
        public int base() {
            return this.base;
        }
        
        public ArbitBaseString copy(final String get, final int base) {
            return new ArbitBaseString(get, base);
        }
        
        public String copy$default$1() {
            return this.get();
        }
        
        public int copy$default$2() {
            return this.base();
        }
        
        @Override
        public String productPrefix() {
            return "ArbitBaseString";
        }
        
        @Override
        public int productArity() {
            return 2;
        }
        
        @Override
        public Object productElement(final int x$1) {
            java.io.Serializable s = null;
            switch (x$1) {
                default: {
                    throw new IndexOutOfBoundsException(BoxesRunTime.boxToInteger(x$1).toString());
                }
                case 1: {
                    s = BoxesRunTime.boxToInteger(this.base());
                    break;
                }
                case 0: {
                    s = this.get();
                    break;
                }
            }
            return s;
        }
        
        @Override
        public Iterator<Object> productIterator() {
            return ScalaRunTime$.MODULE$.typedProductIterator(this);
        }
        
        @Override
        public boolean canEqual(final Object x$1) {
            return x$1 instanceof ArbitBaseString;
        }
        
        @Override
        public int hashCode() {
            return Statics.finalizeHash(Statics.mix(Statics.mix(-889275714, Statics.anyHash(this.get())), this.base()), 2);
        }
        
        @Override
        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }
        
        @Override
        public boolean equals(final Object x$1) {
            if (this != x$1) {
                if (x$1 instanceof ArbitBaseString) {
                    final ArbitBaseString arbitBaseString = (ArbitBaseString)x$1;
                    final String value = this.get();
                    final String value2 = arbitBaseString.get();
                    boolean b = false;
                    Label_0089: {
                        Label_0088: {
                            if (value == null) {
                                if (value2 != null) {
                                    break Label_0088;
                                }
                            }
                            else if (!value.equals(value2)) {
                                break Label_0088;
                            }
                            if (this.base() == arbitBaseString.base() && arbitBaseString.canEqual(this)) {
                                b = true;
                                break Label_0089;
                            }
                        }
                        b = false;
                    }
                    if (b) {
                        return true;
                    }
                }
                return false;
            }
            return true;
        }
        
        public ArbitBaseString(final String get, final int base) {
            this.get = get;
            this.base = base;
            Product$class.$init$(this);
        }
    }
    
    public static class ArbitBaseString$ extends AbstractFunction2<String, Object, ArbitBaseString> implements Serializable
    {
        public static final ArbitBaseString$ MODULE$;
        
        static {
            new ArbitBaseString$();
        }
        
        @Override
        public final String toString() {
            return "ArbitBaseString";
        }
        
        public ArbitBaseString apply(final String get, final int base) {
            return new ArbitBaseString(get, base);
        }
        
        public Option<Tuple2<String, Object>> unapply(final ArbitBaseString x$0) {
            return (Option<Tuple2<String, Object>>)((x$0 == null) ? None$.MODULE$ : new Some<Object>(new Tuple2<String, Object>((T1)x$0.get(), (T2)BoxesRunTime.boxToInteger(x$0.base()))));
        }
        
        private Object readResolve() {
            return ArbitBaseString$.MODULE$;
        }
        
        public ArbitBaseString$() {
            MODULE$ = this;
        }
    }
}
