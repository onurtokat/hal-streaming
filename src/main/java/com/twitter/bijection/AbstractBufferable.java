// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.Tuple2;
import java.nio.ByteBuffer;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes = "\u0006\u0001\u00112Q!\u0001\u0002\u0002\u0002%\u0011!#\u00112tiJ\f7\r\u001e\"vM\u001a,'/\u00192mK*\u00111\u0001B\u0001\nE&TWm\u0019;j_:T!!\u0002\u0004\u0002\u000fQ<\u0018\u000e\u001e;fe*\tq!A\u0002d_6\u001c\u0001!\u0006\u0002\u000b/M\u0019\u0001aC\t\u0011\u00051yQ\"A\u0007\u000b\u00039\tQa]2bY\u0006L!\u0001E\u0007\u0003\r\u0005s\u0017PU3g!\r\u00112#F\u0007\u0002\u0005%\u0011AC\u0001\u0002\u000b\u0005V4g-\u001a:bE2,\u0007C\u0001\f\u0018\u0019\u0001!Q\u0001\u0007\u0001C\u0002e\u0011\u0011\u0001V\t\u00035u\u0001\"\u0001D\u000e\n\u0005qi!a\u0002(pi\"Lgn\u001a\t\u0003\u0019yI!aH\u0007\u0003\u0007\u0005s\u0017\u0010C\u0003\"\u0001\u0011\u0005!%\u0001\u0004=S:LGO\u0010\u000b\u0002GA\u0019!\u0003A\u000b")
public abstract class AbstractBufferable<T> implements Bufferable<T>
{
    @Override
    public Tuple2<ByteBuffer, T> unsafeGet(final ByteBuffer from) {
        return (Tuple2<ByteBuffer, T>)Bufferable$class.unsafeGet(this, from);
    }
    
    public AbstractBufferable() {
        Bufferable$class.$init$(this);
    }
}
