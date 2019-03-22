// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection.avro;

import org.apache.avro.Schema;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import scala.Function0;
import scala.Predef$;
import scala.Option;
import scala.Some;
import org.apache.avro.file.CodecFactory;
import scala.reflect.package$;
import com.twitter.bijection.Injection;
import org.apache.avro.specific.SpecificRecordBase;
import scala.reflect.ClassTag;

public final class SpecificAvroCodecs$
{
    public static final SpecificAvroCodecs$ MODULE$;
    
    static {
        new SpecificAvroCodecs$();
    }
    
    public <T extends SpecificRecordBase> Injection<T, byte[]> apply(final ClassTag<T> evidence$1) {
        final Class klass = package$.MODULE$.classTag(evidence$1).runtimeClass();
        return new SpecificAvroCodec<T>(klass, SpecificAvroCodec$.MODULE$.$lessinit$greater$default$2());
    }
    
    public <T extends SpecificRecordBase> Injection<T, byte[]> withCompression(final CodecFactory codecFactory, final ClassTag<T> evidence$2) {
        final Class klass = package$.MODULE$.classTag(evidence$2).runtimeClass();
        return new SpecificAvroCodec<T>(klass, new Some<CodecFactory>(codecFactory));
    }
    
    public <T extends SpecificRecordBase> Injection<T, byte[]> withBzip2Compression(final ClassTag<T> evidence$3) {
        return this.withCompression(CodecFactory.bzip2Codec(), evidence$3);
    }
    
    public <T extends SpecificRecordBase> Injection<T, byte[]> withDeflateCompression(final int compressionLevel, final ClassTag<T> evidence$4) {
        Predef$.MODULE$.require(1 <= compressionLevel && compressionLevel <= 9, (Function0<Object>)new SpecificAvroCodecs$$anonfun$withDeflateCompression.SpecificAvroCodecs$$anonfun$withDeflateCompression$1());
        return this.withCompression(CodecFactory.deflateCodec(compressionLevel), evidence$4);
    }
    
    public <T extends SpecificRecordBase> Injection<T, byte[]> withDeflateCompression(final ClassTag<T> evidence$5) {
        return this.withDeflateCompression(5, evidence$5);
    }
    
    public <T extends SpecificRecordBase> Injection<T, byte[]> withSnappyCompression(final ClassTag<T> evidence$6) {
        return this.withCompression(CodecFactory.snappyCodec(), evidence$6);
    }
    
    public <T extends SpecificRecordBase> Injection<T, byte[]> toBinary(final ClassTag<T> evidence$7) {
        final SpecificRecordBase record = (SpecificRecordBase)package$.MODULE$.classTag(evidence$7).runtimeClass().newInstance();
        final Schema schema = record.getSchema();
        final SpecificDatumWriter writer = new SpecificDatumWriter(schema);
        final SpecificDatumReader reader = new SpecificDatumReader(schema);
        return new BinaryAvroCodec<T>(writer, reader);
    }
    
    public <T extends SpecificRecordBase> Injection<T, String> toJson(final Schema schema, final ClassTag<T> evidence$8) {
        final Class klass = package$.MODULE$.classTag(evidence$8).runtimeClass();
        final SpecificDatumWriter writer = new SpecificDatumWriter(klass);
        final SpecificDatumReader reader = new SpecificDatumReader(klass);
        return new JsonAvroCodec<T>(schema, writer, reader);
    }
    
    private SpecificAvroCodecs$() {
        MODULE$ = this;
    }
}
