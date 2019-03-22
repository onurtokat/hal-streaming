// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection.avro;

import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import scala.Function0;
import scala.Predef$;
import scala.Option;
import scala.Some;
import scala.reflect.ClassTag;
import org.apache.avro.file.CodecFactory;
import com.twitter.bijection.Injection;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.Schema;

public final class GenericAvroCodecs$
{
    public static final GenericAvroCodecs$ MODULE$;
    
    static {
        new GenericAvroCodecs$();
    }
    
    public <T extends GenericRecord> Injection<T, byte[]> apply(final Schema schema) {
        return new GenericAvroCodec<T>(schema, GenericAvroCodec$.MODULE$.$lessinit$greater$default$2());
    }
    
    public <T extends GenericRecord> Injection<T, byte[]> withCompression(final Schema schema, final CodecFactory codecFactory, final ClassTag<T> evidence$9) {
        return new GenericAvroCodec<T>(schema, new Some<CodecFactory>(codecFactory));
    }
    
    public <T extends GenericRecord> Injection<T, byte[]> withBzip2Compression(final Schema schema, final ClassTag<T> evidence$10) {
        return this.withCompression(schema, CodecFactory.bzip2Codec(), evidence$10);
    }
    
    public <T extends GenericRecord> Injection<T, byte[]> withDeflateCompression(final Schema schema, final int compressionLevel, final ClassTag<T> evidence$11) {
        Predef$.MODULE$.require(1 <= compressionLevel && compressionLevel <= 9, (Function0<Object>)new GenericAvroCodecs$$anonfun$withDeflateCompression.GenericAvroCodecs$$anonfun$withDeflateCompression$2());
        return this.withCompression(schema, CodecFactory.deflateCodec(compressionLevel), evidence$11);
    }
    
    public <T extends GenericRecord> int withDeflateCompression$default$2() {
        return 5;
    }
    
    public <T extends GenericRecord> Injection<T, byte[]> withSnappyCompression(final Schema schema, final ClassTag<T> evidence$12) {
        return this.withCompression(schema, CodecFactory.snappyCodec(), evidence$12);
    }
    
    public <T extends GenericRecord> Injection<T, byte[]> toBinary(final Schema schema) {
        final GenericDatumWriter writer = new GenericDatumWriter(schema);
        final GenericDatumReader reader = new GenericDatumReader(schema);
        return new BinaryAvroCodec<T>(writer, reader);
    }
    
    public <T extends GenericRecord> Injection<T, String> toJson(final Schema schema) {
        final GenericDatumWriter writer = new GenericDatumWriter(schema);
        final GenericDatumReader reader = new GenericDatumReader(schema);
        return new JsonAvroCodec<T>(schema, writer, reader);
    }
    
    private GenericAvroCodecs$() {
        MODULE$ = this;
    }
}
