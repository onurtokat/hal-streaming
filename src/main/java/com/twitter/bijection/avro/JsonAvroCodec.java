// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection.avro;

import com.twitter.bijection.Inversion$;
import scala.util.Try;
import org.apache.avro.io.JsonEncoder;
import com.twitter.bijection.Injection$;
import org.apache.avro.io.Encoder;
import java.io.OutputStream;
import org.apache.avro.io.EncoderFactory;
import java.io.ByteArrayOutputStream;
import scala.Function1;
import com.twitter.bijection.Bijection;
import com.twitter.bijection.Injection$class;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.Schema;
import scala.reflect.ScalaSignature;
import com.twitter.bijection.Injection;

@ScalaSignature(bytes = "\u0006\u0001\r4A!\u0001\u0002\u0001\u0017\ti!j]8o\u0003Z\u0014xnQ8eK\u000eT!a\u0001\u0003\u0002\t\u00054(o\u001c\u0006\u0003\u000b\u0019\t\u0011BY5kK\u000e$\u0018n\u001c8\u000b\u0005\u001dA\u0011a\u0002;xSR$XM\u001d\u0006\u0002\u0013\u0005\u00191m\\7\u0004\u0001U\u0011A\"G\n\u0004\u00015\u0019\u0002C\u0001\b\u0012\u001b\u0005y!\"\u0001\t\u0002\u000bM\u001c\u0017\r\\1\n\u0005Iy!AB!osJ+g\r\u0005\u0003\u0015+]\u0011S\"\u0001\u0003\n\u0005Y!!!C%oU\u0016\u001cG/[8o!\tA\u0012\u0004\u0004\u0001\u0005\u000bi\u0001!\u0019A\u000e\u0003\u0003Q\u000b\"\u0001H\u0010\u0011\u00059i\u0012B\u0001\u0010\u0010\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"A\u0004\u0011\n\u0005\u0005z!aA!osB\u00111E\n\b\u0003\u001d\u0011J!!J\b\u0002\rA\u0013X\rZ3g\u0013\t9\u0003F\u0001\u0004TiJLgn\u001a\u0006\u0003K=A\u0001B\u000b\u0001\u0003\u0002\u0003\u0006IaK\u0001\u0007g\u000eDW-\\1\u0011\u00051\u0012T\"A\u0017\u000b\u0005\rq#BA\u00181\u0003\u0019\t\u0007/Y2iK*\t\u0011'A\u0002pe\u001eL!aM\u0017\u0003\rM\u001b\u0007.Z7b\u0011!)\u0004A!A!\u0002\u00131\u0014AB<sSR,'\u000fE\u00028u]i\u0011\u0001\u000f\u0006\u0003s5\n!![8\n\u0005mB$a\u0003#biVlwK]5uKJD\u0001\"\u0010\u0001\u0003\u0002\u0003\u0006IAP\u0001\u0007e\u0016\fG-\u001a:\u0011\u0007]zt#\u0003\u0002Aq\tYA)\u0019;v[J+\u0017\rZ3s\u0011\u0015\u0011\u0005\u0001\"\u0001D\u0003\u0019a\u0014N\\5u}Q!AIR$I!\r)\u0005aF\u0007\u0002\u0005!)!&\u0011a\u0001W!)Q'\u0011a\u0001m!)Q(\u0011a\u0001}!)!\n\u0001C\u0001\u0017\u0006)\u0011\r\u001d9msR\u0011!\u0005\u0014\u0005\u0006\u001b&\u0003\raF\u0001\u0002C\")q\n\u0001C\u0001!\u00061\u0011N\u001c<feR$\"!U1\u0011\u0007IsvC\u0004\u0002T9:\u0011Ak\u0017\b\u0003+js!AV-\u000e\u0003]S!\u0001\u0017\u0006\u0002\rq\u0012xn\u001c;?\u0013\u0005I\u0011BA\u0004\t\u0013\t)a!\u0003\u0002^\t\u00059\u0001/Y2lC\u001e,\u0017BA0a\u0005\u001d\tE\u000f^3naRT!!\u0018\u0003\t\u000b\tt\u0005\u0019\u0001\u0012\u0002\u0007M$(\u000f")
public class JsonAvroCodec<T> implements Injection<T, String>
{
    public final Schema com$twitter$bijection$avro$JsonAvroCodec$$schema;
    private final DatumWriter<T> writer;
    public final DatumReader<T> com$twitter$bijection$avro$JsonAvroCodec$$reader;
    
    @Override
    public <C> Injection<T, C> andThen(final Injection<String, C> g) {
        return (Injection<T, C>)Injection$class.andThen(this, g);
    }
    
    @Override
    public <C> Injection<T, C> andThen(final Bijection<String, C> bij) {
        return (Injection<T, C>)Injection$class.andThen(this, bij);
    }
    
    @Override
    public <C> Function1<T, C> andThen(final Function1<String, C> g) {
        return (Function1<T, C>)Injection$class.andThen(this, g);
    }
    
    @Override
    public <T> Injection<T, String> compose(final Injection<T, T> g) {
        return (Injection<T, String>)Injection$class.compose(this, g);
    }
    
    @Override
    public <T> Injection<T, String> compose(final Bijection<T, T> bij) {
        return (Injection<T, String>)Injection$class.compose(this, bij);
    }
    
    @Override
    public <T> Function1<T, String> compose(final Function1<T, T> g) {
        return (Function1<T, String>)Injection$class.compose(this, g);
    }
    
    @Override
    public Function1<T, String> toFunction() {
        return (Function1<T, String>)Injection$class.toFunction(this);
    }
    
    @Override
    public String apply(final T a) {
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        final JsonEncoder encoder = EncoderFactory.get().jsonEncoder(this.com$twitter$bijection$avro$JsonAvroCodec$$schema, stream);
        this.writer.write(a, encoder);
        encoder.flush();
        return Injection$.MODULE$.invert(stream.toByteArray(), Injection$.MODULE$.utf8()).get();
    }
    
    @Override
    public Try<T> invert(final String str) {
        return Inversion$.MODULE$.attempt(str, (Function1<String, T>)new JsonAvroCodec$$anonfun$invert.JsonAvroCodec$$anonfun$invert$4(this));
    }
    
    public JsonAvroCodec(final Schema schema, final DatumWriter<T> writer, final DatumReader<T> reader) {
        this.com$twitter$bijection$avro$JsonAvroCodec$$schema = schema;
        this.writer = writer;
        this.com$twitter$bijection$avro$JsonAvroCodec$$reader = reader;
        Injection$class.$init$(this);
    }
}
