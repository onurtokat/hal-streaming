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
import org.apache.avro.specific.SpecificDatumWriter;
import scala.Function1;
import com.twitter.bijection.Bijection;
import com.twitter.bijection.Injection$class;
import org.apache.avro.file.CodecFactory;
import scala.Option;
import scala.reflect.ScalaSignature;
import com.twitter.bijection.Injection;
import org.apache.avro.specific.SpecificRecordBase;

@ScalaSignature(bytes = "\u0006\u0001\u0005Ma\u0001B\u0001\u0003\u0001-\u0011\u0011c\u00159fG&4\u0017nY!we>\u001cu\u000eZ3d\u0015\t\u0019A!\u0001\u0003bmJ|'BA\u0003\u0007\u0003%\u0011\u0017N[3di&|gN\u0003\u0002\b\u0011\u00059Ao^5ui\u0016\u0014(\"A\u0005\u0002\u0007\r|Wn\u0001\u0001\u0016\u00051I2c\u0001\u0001\u000e'A\u0011a\"E\u0007\u0002\u001f)\t\u0001#A\u0003tG\u0006d\u0017-\u0003\u0002\u0013\u001f\t1\u0011I\\=SK\u001a\u0004B\u0001F\u000b\u0018U5\tA!\u0003\u0002\u0017\t\tI\u0011J\u001c6fGRLwN\u001c\t\u00031ea\u0001\u0001B\u0003\u001b\u0001\t\u00071DA\u0001U#\tar\u0004\u0005\u0002\u000f;%\u0011ad\u0004\u0002\b\u001d>$\b.\u001b8h!\t\u0001\u0003&D\u0001\"\u0015\t\u00113%\u0001\u0005ta\u0016\u001c\u0017NZ5d\u0015\t\u0019AE\u0003\u0002&M\u00051\u0011\r]1dQ\u0016T\u0011aJ\u0001\u0004_J<\u0017BA\u0015\"\u0005I\u0019\u0006/Z2jM&\u001c'+Z2pe\u0012\u0014\u0015m]3\u0011\u00079YS&\u0003\u0002-\u001f\t)\u0011I\u001d:bsB\u0011aBL\u0005\u0003_=\u0011AAQ=uK\"A\u0011\u0007\u0001B\u0001B\u0003%!'A\u0003lY\u0006\u001c8\u000fE\u00024m]q!A\u0004\u001b\n\u0005Uz\u0011A\u0002)sK\u0012,g-\u0003\u00028q\t)1\t\\1tg*\u0011Qg\u0004\u0005\tu\u0001\u0011\t\u0011)A\u0005w\u0005a1m\u001c3fG\u001a\u000b7\r^8ssB\u0019a\u0002\u0010 \n\u0005uz!AB(qi&|g\u000e\u0005\u0002@\u00056\t\u0001I\u0003\u0002BG\u0005!a-\u001b7f\u0013\t\u0019\u0005I\u0001\u0007D_\u0012,7MR1di>\u0014\u0018\u0010C\u0003F\u0001\u0011\u0005a)\u0001\u0004=S:LGO\u0010\u000b\u0004\u000f&S\u0005c\u0001%\u0001/5\t!\u0001C\u00032\t\u0002\u0007!\u0007C\u0004;\tB\u0005\t\u0019A\u001e\t\u000b1\u0003A\u0011A'\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u0005)r\u0005\"B(L\u0001\u00049\u0012!A1\t\u000bE\u0003A\u0011\u0001*\u0002\r%tg/\u001a:u)\t\u00196\rE\u0002UA^q!!\u00160\u000f\u0005YkfBA,]\u001d\tA6,D\u0001Z\u0015\tQ&\"\u0001\u0004=e>|GOP\u0005\u0002\u0013%\u0011q\u0001C\u0005\u0003\u000b\u0019I!a\u0018\u0003\u0002\u000fA\f7m[1hK&\u0011\u0011M\u0019\u0002\b\u0003R$X-\u001c9u\u0015\tyF\u0001C\u0003e!\u0002\u0007!&A\u0003csR,7oB\u0004g\u0005\u0005\u0005\t\u0012A4\u0002#M\u0003XmY5gS\u000e\feO]8D_\u0012,7\r\u0005\u0002IQ\u001a9\u0011AAA\u0001\u0012\u0003I7c\u00015\u000eUB\u0011ab[\u0005\u0003Y>\u0011AbU3sS\u0006d\u0017N_1cY\u0016DQ!\u00125\u0005\u00029$\u0012a\u001a\u0005\ba\"\f\n\u0011\"\u0001r\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%eU\u0011!/`\u000b\u0002g*\u00121\b^\u0016\u0002kB\u0011ao_\u0007\u0002o*\u0011\u00010_\u0001\nk:\u001c\u0007.Z2lK\u0012T!A_\b\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002}o\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0005\u000biy'\u0019A\u000e\t\u0011}D\u0017\u0011!C\u0005\u0003\u0003\t1B]3bIJ+7o\u001c7wKR\u0011\u00111\u0001\t\u0005\u0003\u000b\ty!\u0004\u0002\u0002\b)!\u0011\u0011BA\u0006\u0003\u0011a\u0017M\\4\u000b\u0005\u00055\u0011\u0001\u00026bm\u0006LA!!\u0005\u0002\b\t1qJ\u00196fGR\u0004")
public class SpecificAvroCodec<T extends SpecificRecordBase> implements Injection<T, byte[]>
{
    public final Class<T> com$twitter$bijection$avro$SpecificAvroCodec$$klass;
    private final Option<CodecFactory> codecFactory;
    
    public static <T extends SpecificRecordBase> Option<CodecFactory> $lessinit$greater$default$2() {
        return SpecificAvroCodec$.MODULE$.$lessinit$greater$default$2();
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
        final SpecificDatumWriter writer = new SpecificDatumWriter(a.getSchema());
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
        return Inversion$.MODULE$.attempt(bytes, (Function1<byte[], T>)new SpecificAvroCodec$$anonfun$invert.SpecificAvroCodec$$anonfun$invert$1(this));
    }
    
    public SpecificAvroCodec(final Class<T> klass, final Option<CodecFactory> codecFactory) {
        this.com$twitter$bijection$avro$SpecificAvroCodec$$klass = klass;
        this.codecFactory = codecFactory;
        Injection$class.$init$(this);
    }
}
