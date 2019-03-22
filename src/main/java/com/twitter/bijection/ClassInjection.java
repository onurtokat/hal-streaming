// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.Function1;
import scala.util.Try;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes = "\u0006\u0001\t3A!\u0001\u0002\u0001\u0013\tq1\t\\1tg&s'.Z2uS>t'BA\u0002\u0005\u0003%\u0011\u0017N[3di&|gN\u0003\u0002\u0006\r\u00059Ao^5ui\u0016\u0014(\"A\u0004\u0002\u0007\r|Wn\u0001\u0001\u0016\u0005)Y2C\u0001\u0001\f!\u0011aQb\u0004\u0013\u000e\u0003\tI!A\u0004\u0002\u0003#\u0005\u00137\u000f\u001e:bGRLeN[3di&|g\u000eE\u0002\u0011-eq!!\u0005\u000b\u000e\u0003IQ\u0011aE\u0001\u0006g\u000e\fG.Y\u0005\u0003+I\ta\u0001\u0015:fI\u00164\u0017BA\f\u0019\u0005\u0015\u0019E.Y:t\u0015\t)\"\u0003\u0005\u0002\u001b71\u0001A!\u0002\u000f\u0001\u0005\u0004i\"!\u0001+\u0012\u0005y\t\u0003CA\t \u0013\t\u0001#CA\u0004O_RD\u0017N\\4\u0011\u0005E\u0011\u0013BA\u0012\u0013\u0005\r\te.\u001f\t\u0003!\u0015J!A\n\r\u0003\rM#(/\u001b8h\u0011\u0015A\u0003\u0001\"\u0001*\u0003\u0019a\u0014N\\5u}Q\t!\u0006E\u0002\r\u0001eAQ\u0001\f\u0001\u0005B5\nQ!\u00199qYf$\"AL\u001b\u0011\u0005=\"T\"\u0001\u0019\u000b\u0005E\u0012\u0014\u0001\u00027b]\u001eT\u0011aM\u0001\u0005U\u00064\u0018-\u0003\u0002'a!)ag\u000ba\u0001\u001f\u0005\t1\u000eC\u00039\u0001\u0011\u0005\u0013(\u0001\u0004j]Z,'\u000f\u001e\u000b\u0003u\u0001\u00032a\u000f \u0010\u001b\u0005a$BA\u001f\u0013\u0003\u0011)H/\u001b7\n\u0005}b$a\u0001+ss\")\u0011i\u000ea\u0001I\u0005\t1\u000f")
public class ClassInjection<T> extends AbstractInjection<Class<T>, String>
{
    @Override
    public String apply(final Class<T> k) {
        return k.getName();
    }
    
    @Override
    public Try<Class<T>> invert(final String s) {
        return Inversion$.MODULE$.attempt(s, (Function1<String, Class<T>>)new ClassInjection$$anonfun$invert.ClassInjection$$anonfun$invert$1(this));
    }
}
