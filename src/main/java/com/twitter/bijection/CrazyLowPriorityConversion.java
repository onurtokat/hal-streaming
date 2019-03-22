// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.util.Try;
import scala.Option;
import scala.reflect.ScalaSignature;
import java.io.Serializable;

@ScalaSignature(bytes = "\u0006\u000153q!\u0001\u0002\u0011\u0002\u0007\u0005\u0011B\u0001\u000eDe\u0006T\u0018\u0010T8x!JLwN]5us\u000e{gN^3sg&|gN\u0003\u0002\u0004\t\u0005I!-\u001b6fGRLwN\u001c\u0006\u0003\u000b\u0019\tq\u0001^<jiR,'OC\u0001\b\u0003\r\u0019w.\\\u0002\u0001'\r\u0001!\u0002\u0005\t\u0003\u00179i\u0011\u0001\u0004\u0006\u0002\u001b\u0005)1oY1mC&\u0011q\u0002\u0004\u0002\u0007\u0003:L(+\u001a4\u0011\u0005E1R\"\u0001\n\u000b\u0005M!\u0012AA5p\u0015\u0005)\u0012\u0001\u00026bm\u0006L!a\u0006\n\u0003\u0019M+'/[1mSj\f'\r\\3\t\u000be\u0001A\u0011\u0001\u000e\u0002\r\u0011Jg.\u001b;%)\u0005Y\u0002CA\u0006\u001d\u0013\tiBB\u0001\u0003V]&$\b\"B\u0010\u0001\t\u0007\u0001\u0013a\u00064s_6LeN[3di&|gn\u00149u\u0013:4XM]:f+\r\t\u0003&\u000e\u000b\u0003E]\u0002Ba\t\u0013'c5\t!!\u0003\u0002&\u0005\tQ1i\u001c8wKJ\u001c\u0018n\u001c8\u0011\u0005\u001dBC\u0002\u0001\u0003\u0006Sy\u0011\rA\u000b\u0002\u0002\u0003F\u00111F\f\t\u0003\u00171J!!\f\u0007\u0003\u000f9{G\u000f[5oOB\u00111bL\u0005\u0003a1\u00111!\u00118z!\rY!\u0007N\u0005\u0003g1\u0011aa\u00149uS>t\u0007CA\u00146\t\u00151dD1\u0001+\u0005\u0005\u0011\u0005\"\u0002\u001d\u001f\u0001\bI\u0014aA5oUB!1E\u000f\u001b'\u0013\tY$AA\u0005J]*,7\r^5p]\")Q\b\u0001C\u0002}\u0005!bM]8n\u0013:TWm\u0019;j_:LeN^3sg\u0016,2a\u0010\"K)\t\u00015\n\u0005\u0003$I\u0005\u001b\u0005CA\u0014C\t\u0015ICH1\u0001+!\r!u)S\u0007\u0002\u000b*\u0011a\tD\u0001\u0005kRLG.\u0003\u0002I\u000b\n\u0019AK]=\u0011\u0005\u001dRE!\u0002\u001c=\u0005\u0004Q\u0003\"\u0002\u001d=\u0001\ba\u0005\u0003B\u0012;\u0013\u0006\u0003")
public interface CrazyLowPriorityConversion extends Serializable
{
     <A, B> Conversion<A, Option<B>> fromInjectionOptInverse(final Injection<B, A> p0);
    
     <A, B> Conversion<A, Try<B>> fromInjectionInverse(final Injection<B, A> p0);
}
