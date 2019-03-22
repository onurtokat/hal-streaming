// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection.avro;

import com.twitter.bijection.Inversion$;
import scala.util.Try;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import scala.runtime.BoxedUnit;
import scala.MatchError;
import scala.None$;
import scala.Some;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericDatumWriter;
import scala.Function1;
import com.twitter.bijection.Bijection;
import com.twitter.bijection.Injection$class;
import org.apache.avro.file.CodecFactory;
import scala.Option;
import org.apache.avro.Schema;
import scala.reflect.ScalaSignature;
import com.twitter.bijection.Injection;
import org.apache.avro.generic.GenericRecord;

@ScalaSignature(bytes = "\u0006\u0001\u00055a\u0001B\u0001\u0003\u0001-\u0011\u0001cR3oKJL7-\u0011<s_\u000e{G-Z2\u000b\u0005\r!\u0011\u0001B1we>T!!\u0002\u0004\u0002\u0013\tL'.Z2uS>t'BA\u0004\t\u0003\u001d!x/\u001b;uKJT\u0011!C\u0001\u0004G>l7\u0001A\u000b\u0003\u0019e\u00192\u0001A\u0007\u0014!\tq\u0011#D\u0001\u0010\u0015\u0005\u0001\u0012!B:dC2\f\u0017B\u0001\n\u0010\u0005\u0019\te.\u001f*fMB!A#F\f+\u001b\u0005!\u0011B\u0001\f\u0005\u0005%IeN[3di&|g\u000e\u0005\u0002\u001931\u0001A!\u0002\u000e\u0001\u0005\u0004Y\"!\u0001+\u0012\u0005qy\u0002C\u0001\b\u001e\u0013\tqrBA\u0004O_RD\u0017N\\4\u0011\u0005\u0001BS\"A\u0011\u000b\u0005\t\u001a\u0013aB4f]\u0016\u0014\u0018n\u0019\u0006\u0003\u0007\u0011R!!\n\u0014\u0002\r\u0005\u0004\u0018m\u00195f\u0015\u00059\u0013aA8sO&\u0011\u0011&\t\u0002\u000e\u000f\u0016tWM]5d%\u0016\u001cwN\u001d3\u0011\u00079YS&\u0003\u0002-\u001f\t)\u0011I\u001d:bsB\u0011aBL\u0005\u0003_=\u0011AAQ=uK\"A\u0011\u0007\u0001B\u0001B\u0003%!'\u0001\u0004tG\",W.\u0019\t\u0003gQj\u0011aI\u0005\u0003k\r\u0012aaU2iK6\f\u0007\u0002C\u001c\u0001\u0005\u0003\u0005\u000b\u0011\u0002\u001d\u0002\u0019\r|G-Z2GC\u000e$xN]=\u0011\u00079I4(\u0003\u0002;\u001f\t1q\n\u001d;j_:\u0004\"\u0001P \u000e\u0003uR!AP\u0012\u0002\t\u0019LG.Z\u0005\u0003\u0001v\u0012AbQ8eK\u000e4\u0015m\u0019;pefDQA\u0011\u0001\u0005\u0002\r\u000ba\u0001P5oSRtDc\u0001#G\u000fB\u0019Q\tA\f\u000e\u0003\tAQ!M!A\u0002IBqaN!\u0011\u0002\u0003\u0007\u0001\bC\u0003J\u0001\u0011\u0005!*A\u0003baBd\u0017\u0010\u0006\u0002+\u0017\")A\n\u0013a\u0001/\u0005\t\u0011\rC\u0003O\u0001\u0011\u0005q*\u0001\u0004j]Z,'\u000f\u001e\u000b\u0003!\u0002\u00042!U/\u0018\u001d\t\u00116L\u0004\u0002T5:\u0011A+\u0017\b\u0003+bk\u0011A\u0016\u0006\u0003/*\ta\u0001\u0010:p_Rt\u0014\"A\u0005\n\u0005\u001dA\u0011BA\u0003\u0007\u0013\taF!A\u0004qC\u000e\\\u0017mZ3\n\u0005y{&aB!ui\u0016l\u0007\u000f\u001e\u0006\u00039\u0012AQ!Y'A\u0002)\nQAY=uKN<qa\u0019\u0002\u0002\u0002#\u0005A-\u0001\tHK:,'/[2BmJ|7i\u001c3fGB\u0011Q)\u001a\u0004\b\u0003\t\t\t\u0011#\u0001g'\r)Wb\u001a\t\u0003\u001d!L!![\b\u0003\u0019M+'/[1mSj\f'\r\\3\t\u000b\t+G\u0011A6\u0015\u0003\u0011Dq!\\3\u0012\u0002\u0013\u0005a.A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$HEM\u000b\u0003_j,\u0012\u0001\u001d\u0016\u0003qE\\\u0013A\u001d\t\u0003gbl\u0011\u0001\u001e\u0006\u0003kZ\f\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0005]|\u0011AC1o]>$\u0018\r^5p]&\u0011\u0011\u0010\u001e\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,G!\u0002\u000em\u0005\u0004Y\u0002b\u0002?f\u0003\u0003%I!`\u0001\fe\u0016\fGMU3t_24X\rF\u0001\u007f!\ry\u0018\u0011B\u0007\u0003\u0003\u0003QA!a\u0001\u0002\u0006\u0005!A.\u00198h\u0015\t\t9!\u0001\u0003kCZ\f\u0017\u0002BA\u0006\u0003\u0003\u0011aa\u00142kK\u000e$\b")
public class GenericAvroCodec<T extends GenericRecord> implements Injection<T, byte[]>
{
    public final Schema com$twitter$bijection$avro$GenericAvroCodec$$schema;
    private final Option<CodecFactory> codecFactory;
    
    public static <T extends GenericRecord> Option<CodecFactory> $lessinit$greater$default$2() {
        return GenericAvroCodec$.MODULE$.$lessinit$greater$default$2();
    }
    
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
        final GenericDatumWriter writer = new GenericDatumWriter(a.getSchema());
        final DataFileWriter fileWriter = new DataFileWriter(writer);
        final Option<CodecFactory> codecFactory = this.codecFactory;
        if (codecFactory instanceof Some) {
            final CodecFactory cf = ((Some<CodecFactory>)codecFactory).x();
            fileWriter.setCodec(cf);
        }
        else {
            if (!None$.MODULE$.equals(codecFactory)) {
                throw new MatchError(codecFactory);
            }
            final BoxedUnit un\u0131t = BoxedUnit.UNIT;
        }
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        fileWriter.create(a.getSchema(), stream);
        fileWriter.append(a);
        fileWriter.flush();
        return stream.toByteArray();
    }
    
    @Override
    public Try<T> invert(final byte[] bytes) {
        return Inversion$.MODULE$.attempt(bytes, (Function1<byte[], T>)new GenericAvroCodec$$anonfun$invert.GenericAvroCodec$$anonfun$invert$2(this));
    }
    
    public GenericAvroCodec(final Schema schema, final Option<CodecFactory> codecFactory) {
        this.com$twitter$bijection$avro$GenericAvroCodec$$schema = schema;
        this.codecFactory = codecFactory;
        Injection$class.$init$(this);
    }
}
