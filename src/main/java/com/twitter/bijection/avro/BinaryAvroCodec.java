// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection.avro;

import com.twitter.bijection.Inversion$;
import scala.util.Try;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.BinaryEncoder;
import java.io.OutputStream;
import org.apache.avro.io.EncoderFactory;
import java.io.ByteArrayOutputStream;
import scala.Function1;
import com.twitter.bijection.Bijection;
import com.twitter.bijection.Injection$class;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import scala.reflect.ScalaSignature;
import com.twitter.bijection.Injection;

@ScalaSignature(bytes = "\u0006\u0001m3A!\u0001\u0002\u0001\u0017\ty!)\u001b8bef\feO]8D_\u0012,7M\u0003\u0002\u0004\t\u0005!\u0011M\u001e:p\u0015\t)a!A\u0005cS*,7\r^5p]*\u0011q\u0001C\u0001\bi^LG\u000f^3s\u0015\u0005I\u0011aA2p[\u000e\u0001QC\u0001\u0007\u001a'\r\u0001Qb\u0005\t\u0003\u001dEi\u0011a\u0004\u0006\u0002!\u0005)1oY1mC&\u0011!c\u0004\u0002\u0007\u0003:L(+\u001a4\u0011\tQ)rCI\u0007\u0002\t%\u0011a\u0003\u0002\u0002\n\u0013:TWm\u0019;j_:\u0004\"\u0001G\r\r\u0001\u0011)!\u0004\u0001b\u00017\t\tA+\u0005\u0002\u001d?A\u0011a\"H\u0005\u0003==\u0011qAT8uQ&tw\r\u0005\u0002\u000fA%\u0011\u0011e\u0004\u0002\u0004\u0003:L\bc\u0001\b$K%\u0011Ae\u0004\u0002\u0006\u0003J\u0014\u0018-\u001f\t\u0003\u001d\u0019J!aJ\b\u0003\t\tKH/\u001a\u0005\tS\u0001\u0011\t\u0011)A\u0005U\u00051qO]5uKJ\u00042aK\u001a\u0018\u001b\u0005a#BA\u0017/\u0003\tIwN\u0003\u0002\u0004_)\u0011\u0001'M\u0001\u0007CB\f7\r[3\u000b\u0003I\n1a\u001c:h\u0013\t!DFA\u0006ECR,Xn\u0016:ji\u0016\u0014\b\u0002\u0003\u001c\u0001\u0005\u0003\u0005\u000b\u0011B\u001c\u0002\rI,\u0017\rZ3s!\rY\u0003hF\u0005\u0003s1\u00121\u0002R1uk6\u0014V-\u00193fe\")1\b\u0001C\u0001y\u00051A(\u001b8jiz\"2!P A!\rq\u0004aF\u0007\u0002\u0005!)\u0011F\u000fa\u0001U!)aG\u000fa\u0001o!)!\t\u0001C\u0001\u0007\u0006)\u0011\r\u001d9msR\u0011!\u0005\u0012\u0005\u0006\u000b\u0006\u0003\raF\u0001\u0002C\")q\t\u0001C\u0001\u0011\u00061\u0011N\u001c<feR$\"!S-\u0011\u0007)3vC\u0004\u0002L):\u0011Aj\u0015\b\u0003\u001bJs!AT)\u000e\u0003=S!\u0001\u0015\u0006\u0002\rq\u0012xn\u001c;?\u0013\u0005I\u0011BA\u0004\t\u0013\t)a!\u0003\u0002V\t\u00059\u0001/Y2lC\u001e,\u0017BA,Y\u0005\u001d\tE\u000f^3naRT!!\u0016\u0003\t\u000bi3\u0005\u0019\u0001\u0012\u0002\u000b\tLH/Z:")
public class BinaryAvroCodec<T> implements Injection<T, byte[]>
{
    private final DatumWriter<T> writer;
    public final DatumReader<T> com$twitter$bijection$avro$BinaryAvroCodec$$reader;
    
    @Override
    public <C> Injection<T, C> andThen(final Injection<byte[], C> g) {
        return (Injection<T, C>)Injection$class.andThen(this, g);
    }
    
    @Override
    public <C> Injection<T, C> andThen(final Bijection<byte[], C> bij) {
        return (Injection<T, C>)Injection$class.andThen(this, bij);
    }
    
    @Override
    public <C> Function1<T, C> andThen(final Function1<byte[], C> g) {
        return (Function1<T, C>)Injection$class.andThen(this, g);
    }
    
    @Override
    public <T> Injection<T, byte[]> compose(final Injection<T, T> g) {
        return (Injection<T, byte[]>)Injection$class.compose(this, g);
    }
    
    @Override
    public <T> Injection<T, byte[]> compose(final Bijection<T, T> bij) {
        return (Injection<T, byte[]>)Injection$class.compose(this, bij);
    }
    
    @Override
    public <T> Function1<T, byte[]> compose(final Function1<T, T> g) {
        return (Function1<T, byte[]>)Injection$class.compose(this, g);
    }
    
    @Override
    public Function1<T, byte[]> toFunction() {
        return (Function1<T, byte[]>)Injection$class.toFunction(this);
    }
    
    @Override
    public byte[] apply(final T a) {
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        final BinaryEncoder binaryEncoder = EncoderFactory.get().binaryEncoder(stream, null);
        this.writer.write(a, binaryEncoder);
        binaryEncoder.flush();
        return stream.toByteArray();
    }
    
    @Override
    public Try<T> invert(final byte[] bytes) {
        return Inversion$.MODULE$.attempt(bytes, (Function1<byte[], T>)new BinaryAvroCodec$$anonfun$invert.BinaryAvroCodec$$anonfun$invert$3(this));
    }
    
    public BinaryAvroCodec(final DatumWriter<T> writer, final DatumReader<T> reader) {
        this.writer = writer;
        this.com$twitter$bijection$avro$BinaryAvroCodec$$reader = reader;
        Injection$class.$init$(this);
    }
}
