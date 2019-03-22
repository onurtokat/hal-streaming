// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.Function1;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes = "\u0006\u0001\r4A!\u0001\u0002\u0001\u0013\tq1\t\\1tg\nK'.Z2uS>t'BA\u0002\u0005\u0003%\u0011\u0017N[3di&|gN\u0003\u0002\u0006\r\u00059Ao^5ui\u0016\u0014(\"A\u0004\u0002\u0007\r|Wn\u0001\u0001\u0016\u0005)q2c\u0001\u0001\f#A\u0011AbD\u0007\u0002\u001b)\ta\"A\u0003tG\u0006d\u0017-\u0003\u0002\u0011\u001b\t1\u0011I\\=SK\u001a\u0004BAE\n\u0016O5\t!!\u0003\u0002\u0015\u0005\tI!)\u001b6fGRLwN\u001c\t\u0004-eabB\u0001\u0007\u0018\u0013\tAR\"\u0001\u0004Qe\u0016$WMZ\u0005\u00035m\u0011Qa\u00117bgNT!\u0001G\u0007\u0011\u0005uqB\u0002\u0001\u0003\u0006?\u0001\u0011\r\u0001\t\u0002\u0002)F\u0011\u0011\u0005\n\t\u0003\u0019\tJ!aI\u0007\u0003\u000f9{G\u000f[5oOB\u0011A\"J\u0005\u0003M5\u00111!\u00118z!\u0011A3FL\u0019\u000f\u0005II\u0013B\u0001\u0016\u0003\u0003\u001d\u0001\u0018mY6bO\u0016L!\u0001L\u0017\u0003\r\u0011\nG\u000fJ1u\u0015\tQ#\u0001\u0005\u0002\u0017_%\u0011\u0001g\u0007\u0002\u0007'R\u0014\u0018N\\4\u0011\u0007I\u0011T#\u0003\u00024\u0005\t\u0019!+\u001a9\t\u000bU\u0002A\u0011\u0001\u001c\u0002\rqJg.\u001b;?)\u00059\u0004c\u0001\n\u00019!)\u0011\b\u0001C!u\u0005)\u0011\r\u001d9msR\u00111h\u0011\t\u0005Q-b\u0014\u0007\u0005\u0002>\u00056\taH\u0003\u0002@\u0001\u0006!A.\u00198h\u0015\u0005\t\u0015\u0001\u00026bm\u0006L!\u0001\r \t\u000b\u0011C\u0004\u0019A\u000b\u0002\u0003-DQA\u0012\u0001\u0005B\u001d\u000ba!\u001b8wKJ$HCA\u000bI\u0011\u0015IU\t1\u0001(\u0003\u0005\u0019x!B&\u0003\u0011\u0003a\u0015AD\"mCN\u001c()\u001b6fGRLwN\u001c\t\u0003%53Q!\u0001\u0002\t\u00029\u001b2!T\u0006P!\ta\u0001+\u0003\u0002R\u001b\ta1+\u001a:jC2L'0\u00192mK\")Q'\u0014C\u0001'R\tA\nC\u0003:\u001b\u0012\u0005Q+\u0006\u0002W5R\tq\u000b\u0005\u0003\u0013'a[\u0006c\u0001\f\u001a3B\u0011QD\u0017\u0003\u0006?Q\u0013\r\u0001\t\t\u0005Q-rC\fE\u0002\u0013eaCqAX'\u0002\u0002\u0013%q,A\u0006sK\u0006$'+Z:pYZ,G#\u00011\u0011\u0005u\n\u0017B\u00012?\u0005\u0019y%M[3di\u0002")
public class ClassBijection<T> implements Bijection<Class<T>, String>
{
    @Override
    public Bijection<String, Class<T>> inverse() {
        return (Bijection<String, Class<T>>)Bijection$class.inverse(this);
    }
    
    @Override
    public <C> Bijection<Class<T>, C> andThen(final Bijection<String, C> g) {
        return (Bijection<Class<T>, C>)Bijection$class.andThen(this, g);
    }
    
    @Override
    public <C> Injection<Class<T>, C> andThen(final Injection<String, C> g) {
        return (Injection<Class<T>, C>)Bijection$class.andThen(this, g);
    }
    
    @Override
    public <C> Function1<Class<T>, C> andThen(final Function1<String, C> g) {
        return (Function1<Class<T>, C>)Bijection$class.andThen(this, g);
    }
    
    @Override
    public <T> Bijection<T, String> compose(final Bijection<T, Class<T>> g) {
        return (Bijection<T, String>)Bijection$class.compose(this, g);
    }
    
    @Override
    public <T> Injection<T, String> compose(final Injection<T, Class<T>> g) {
        return (Injection<T, String>)Bijection$class.compose(this, g);
    }
    
    @Override
    public <T> Function1<T, String> compose(final Function1<T, Class<T>> g) {
        return (Function1<T, String>)Bijection$class.compose(this, g);
    }
    
    @Override
    public Function1<Class<T>, String> toFunction() {
        return (Function1<Class<T>, String>)Bijection$class.toFunction(this);
    }
    
    @Override
    public String apply(final Class<T> k) {
        return package.Tag$.MODULE$.apply(k.getName());
    }
    
    @Override
    public Class<T> invert(final String s) {
        return (Class<T>)Class.forName(s);
    }
    
    public ClassBijection() {
        Bijection$class.$init$(this);
    }
}
