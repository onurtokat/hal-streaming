// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.commons.message.util;

import org.apache.avro.io.JsonEncoder;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.JsonDecoder;
import org.apache.avro.io.Decoder;
import org.apache.avro.generic.GenericDatumReader;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.generic.GenericData;
import java.io.IOException;
import org.apache.avro.io.Encoder;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.io.BinaryEncoder;
import java.io.OutputStream;
import org.apache.avro.io.EncoderFactory;
import java.io.ByteArrayOutputStream;
import org.apache.avro.Schema;

public class JsonConverter
{
    public static byte[] convertToAvro(final byte[] data, final Schema schema) throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(outputStream, null);
        final GenericDatumWriter<Object> writer = new GenericDatumWriter<Object>(schema);
        writer.write(readRecord(data, schema), encoder);
        encoder.flush();
        return outputStream.toByteArray();
    }
    
    private static GenericData.Record readRecord(final byte[] data, final Schema schema) throws IOException {
        final JsonDecoder decoder = DecoderFactory.get().jsonDecoder(schema, new ByteArrayInputStream(data));
        final DatumReader<GenericData.Record> reader = new GenericDatumReader<GenericData.Record>(schema);
        return reader.read(null, decoder);
    }
    
    public static GenericRecord getGenericRecord(final byte[] avro, final Schema schema) throws IOException {
        final BinaryDecoder binaryDecoder = DecoderFactory.get().binaryDecoder(avro, null);
        return new GenericDatumReader<GenericRecord>(schema).read(null, binaryDecoder);
    }
    
    public static byte[] convertToJson(final byte[] avro, final Schema schema) throws IOException {
        final GenericRecord record = getGenericRecord(avro, schema);
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final JsonEncoder jsonEncoder = EncoderFactory.get().jsonEncoder(schema, outputStream);
        new GenericDatumWriter<GenericRecord>(schema).write(record, jsonEncoder);
        jsonEncoder.flush();
        return outputStream.toByteArray();
    }
}
