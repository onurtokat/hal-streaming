// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.util.Try;
import scala.Function1;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes = "\u0006\u0001-;Q!\u0001\u0002\t\u0002%\t\u0011\"\u00138wKJ\u001c\u0018n\u001c8\u000b\u0005\r!\u0011!\u00032jU\u0016\u001cG/[8o\u0015\t)a!A\u0004uo&$H/\u001a:\u000b\u0003\u001d\t1aY8n\u0007\u0001\u0001\"AC\u0006\u000e\u0003\t1Q\u0001\u0004\u0002\t\u00025\u0011\u0011\"\u00138wKJ\u001c\u0018n\u001c8\u0014\u0005-q\u0001CA\b\u0013\u001b\u0005\u0001\"\"A\t\u0002\u000bM\u001c\u0017\r\\1\n\u0005M\u0001\"AB!osJ+g\rC\u0003\u0016\u0017\u0011\u0005a#\u0001\u0004=S:LGO\u0010\u000b\u0002\u0013!)\u0001d\u0003C\u00013\u00059\u0011\r\u001e;f[B$Xc\u0001\u000e%gQ\u00111$\u000e\u000b\u000395\u00022!\b\u0011#\u001b\u0005q\"BA\u0010\u0011\u0003\u0011)H/\u001b7\n\u0005\u0005r\"a\u0001+ssB\u00111\u0005\n\u0007\u0001\t\u0015)sC1\u0001'\u0005\u0005\t\u0015CA\u0014+!\ty\u0001&\u0003\u0002*!\t9aj\u001c;iS:<\u0007CA\b,\u0013\ta\u0003CA\u0002B]fDQAL\fA\u0002=\n1!\u001b8w!\u0011y\u0001G\r\u0012\n\u0005E\u0002\"!\u0003$v]\u000e$\u0018n\u001c82!\t\u00193\u0007B\u00035/\t\u0007aEA\u0001C\u0011\u00151t\u00031\u00013\u0003\u0005\u0011\u0007\"\u0002\u001d\f\t\u0003I\u0014aC1ui\u0016l\u0007\u000f^,iK:,2AO D)\tY$\n\u0006\u0002=\tR\u0011Q\b\u0011\t\u0004;\u0001r\u0004CA\u0012@\t\u0015)sG1\u0001'\u0011\u0015qs\u00071\u0001B!\u0011y\u0001G\u0011 \u0011\u0005\r\u001aE!\u0002\u001b8\u0005\u00041\u0003\"B#8\u0001\u00041\u0015\u0001\u0002;fgR\u0004Ba\u0004\u0019C\u000fB\u0011q\u0002S\u0005\u0003\u0013B\u0011qAQ8pY\u0016\fg\u000eC\u00037o\u0001\u0007!\t")
public final class Inversion
{
    public static <A, B> Try<A> attemptWhen(final B b, final Function1<B, Object> test, final Function1<B, A> inv) {
        return Inversion$.MODULE$.attemptWhen(b, test, inv);
    }
    
    public static <A, B> Try<A> attempt(final B b, final Function1<B, A> inv) {
        return Inversion$.MODULE$.attempt(b, inv);
    }
}
