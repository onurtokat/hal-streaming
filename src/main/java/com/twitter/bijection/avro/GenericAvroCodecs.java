// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection.avro;

import org.apache.avro.file.CodecFactory;
import scala.reflect.ClassTag;
import com.twitter.bijection.Injection;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes = "\u0006\u0001\u0005Es!B\u0001\u0003\u0011\u0003Y\u0011!E$f]\u0016\u0014\u0018nY!we>\u001cu\u000eZ3dg*\u00111\u0001B\u0001\u0005CZ\u0014xN\u0003\u0002\u0006\r\u0005I!-\u001b6fGRLwN\u001c\u0006\u0003\u000f!\tq\u0001^<jiR,'OC\u0001\n\u0003\r\u0019w.\\\u0002\u0001!\taQ\"D\u0001\u0003\r\u0015q!\u0001#\u0001\u0010\u0005E9UM\\3sS\u000e\feO]8D_\u0012,7m]\n\u0003\u001bA\u0001\"!\u0005\u000b\u000e\u0003IQ\u0011aE\u0001\u0006g\u000e\fG.Y\u0005\u0003+I\u0011a!\u00118z%\u00164\u0007\"B\f\u000e\t\u0003A\u0012A\u0002\u001fj]&$h\bF\u0001\f\u0011\u0015QR\u0002\"\u0001\u001c\u0003\u0015\t\u0007\u000f\u001d7z+\ta2\u0005\u0006\u0002\u001euA!adH\u00115\u001b\u0005!\u0011B\u0001\u0011\u0005\u0005%IeN[3di&|g\u000e\u0005\u0002#G1\u0001A!\u0002\u0013\u001a\u0005\u0004)#!\u0001+\u0012\u0005\u0019J\u0003CA\t(\u0013\tA#CA\u0004O_RD\u0017N\\4\u0011\u0005)\u0012T\"A\u0016\u000b\u00051j\u0013aB4f]\u0016\u0014\u0018n\u0019\u0006\u0003\u00079R!a\f\u0019\u0002\r\u0005\u0004\u0018m\u00195f\u0015\u0005\t\u0014aA8sO&\u00111g\u000b\u0002\u000e\u000f\u0016tWM]5d%\u0016\u001cwN\u001d3\u0011\u0007E)t'\u0003\u00027%\t)\u0011I\u001d:bsB\u0011\u0011\u0003O\u0005\u0003sI\u0011AAQ=uK\")1(\u0007a\u0001y\u000511o\u00195f[\u0006\u0004\"!\u0010 \u000e\u00035J!aP\u0017\u0003\rM\u001b\u0007.Z7b\u0011\u0015\tU\u0002\"\u0001C\u0003=9\u0018\u000e\u001e5D_6\u0004(/Z:tS>tWCA\"H)\r!\u0005+\u0015\u000b\u0003\u000b\"\u0003BAH\u0010GiA\u0011!e\u0012\u0003\u0006I\u0001\u0013\r!\n\u0005\b\u0013\u0002\u000b\t\u0011q\u0001K\u0003))g/\u001b3f]\u000e,G%\u000f\t\u0004\u0017:3U\"\u0001'\u000b\u00055\u0013\u0012a\u0002:fM2,7\r^\u0005\u0003\u001f2\u0013\u0001b\u00117bgN$\u0016m\u001a\u0005\u0006w\u0001\u0003\r\u0001\u0010\u0005\u0006%\u0002\u0003\raU\u0001\rG>$Wm\u0019$bGR|'/\u001f\t\u0003)^k\u0011!\u0016\u0006\u0003-6\nAAZ5mK&\u0011\u0001,\u0016\u0002\r\u0007>$Wm\u0019$bGR|'/\u001f\u0005\u000656!\taW\u0001\u0015o&$\bN\u0011>jaJ\u001au.\u001c9sKN\u001c\u0018n\u001c8\u0016\u0005q\u0003GCA/e)\tq\u0016\r\u0005\u0003\u001f?}#\u0004C\u0001\u0012a\t\u0015!\u0013L1\u0001&\u0011\u001d\u0011\u0017,!AA\u0004\r\f1\"\u001a<jI\u0016t7-\u001a\u00132aA\u00191JT0\t\u000bmJ\u0006\u0019\u0001\u001f\t\u000b\u0019lA\u0011A4\u0002-]LG\u000f\u001b#fM2\fG/Z\"p[B\u0014Xm]:j_:,\"\u0001\u001b7\u0015\u0007%\u0004\u0018\u000f\u0006\u0002k[B!adH65!\t\u0011C\u000eB\u0003%K\n\u0007Q\u0005C\u0004oK\u0006\u0005\t9A8\u0002\u0017\u00154\u0018\u000eZ3oG\u0016$\u0013'\r\t\u0004\u0017:[\u0007\"B\u001ef\u0001\u0004a\u0004b\u0002:f!\u0003\u0005\ra]\u0001\u0011G>l\u0007O]3tg&|g\u000eT3wK2\u0004\"!\u0005;\n\u0005U\u0014\"aA%oi\")q/\u0004C\u0001q\u0006)r/\u001b;i':\f\u0007\u000f]=D_6\u0004(/Z:tS>tWCA=~)\rQ\u00181\u0001\u000b\u0003wz\u0004BAH\u0010}iA\u0011!% \u0003\u0006IY\u0014\r!\n\u0005\t\u007fZ\f\t\u0011q\u0001\u0002\u0002\u0005YQM^5eK:\u001cW\rJ\u00193!\rYe\n \u0005\u0006wY\u0004\r\u0001\u0010\u0005\b\u0003\u000fiA\u0011AA\u0005\u0003!!xNQ5oCJLX\u0003BA\u0006\u0003#!B!!\u0004\u0002\u0014A)adHA\biA\u0019!%!\u0005\u0005\r\u0011\n)A1\u0001&\u0011\u0019Y\u0014Q\u0001a\u0001y!9\u0011qC\u0007\u0005\u0002\u0005e\u0011A\u0002;p\u0015N|g.\u0006\u0003\u0002\u001c\u0005\u0005B\u0003BA\u000f\u0003c\u0001bAH\u0010\u0002 \u0005\r\u0002c\u0001\u0012\u0002\"\u00111A%!\u0006C\u0002\u0015\u0002B!!\n\u0002,9\u0019\u0011#a\n\n\u0007\u0005%\"#\u0001\u0004Qe\u0016$WMZ\u0005\u0005\u0003[\tyC\u0001\u0004TiJLgn\u001a\u0006\u0004\u0003S\u0011\u0002BB\u001e\u0002\u0016\u0001\u0007A\bC\u0005\u000265\t\n\u0011\"\u0001\u00028\u0005\u0001s/\u001b;i\t\u00164G.\u0019;f\u0007>l\u0007O]3tg&|g\u000e\n3fM\u0006,H\u000e\u001e\u00133+\u0011\tI$a\u0014\u0016\u0005\u0005m\"fA:\u0002>-\u0012\u0011q\b\t\u0005\u0003\u0003\nY%\u0004\u0002\u0002D)!\u0011QIA$\u0003%)hn\u00195fG.,GMC\u0002\u0002JI\t!\"\u00198o_R\fG/[8o\u0013\u0011\ti%a\u0011\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW\r\u0002\u0004%\u0003g\u0011\r!\n")
public final class GenericAvroCodecs
{
    public static <T extends GenericRecord> int withDeflateCompression$default$2() {
        return GenericAvroCodecs$.MODULE$.withDeflateCompression$default$2();
    }
    
    public static <T extends GenericRecord> Injection<T, String> toJson(final Schema schema) {
        return GenericAvroCodecs$.MODULE$.toJson(schema);
    }
    
    public static <T extends GenericRecord> Injection<T, byte[]> toBinary(final Schema schema) {
        return GenericAvroCodecs$.MODULE$.toBinary(schema);
    }
    
    public static <T extends GenericRecord> Injection<T, byte[]> withSnappyCompression(final Schema schema, final ClassTag<T> evidence$12) {
        return GenericAvroCodecs$.MODULE$.withSnappyCompression(schema, evidence$12);
    }
    
    public static <T extends GenericRecord> Injection<T, byte[]> withDeflateCompression(final Schema schema, final int compressionLevel, final ClassTag<T> evidence$11) {
        return GenericAvroCodecs$.MODULE$.withDeflateCompression(schema, compressionLevel, evidence$11);
    }
    
    public static <T extends GenericRecord> Injection<T, byte[]> withBzip2Compression(final Schema schema, final ClassTag<T> evidence$10) {
        return GenericAvroCodecs$.MODULE$.withBzip2Compression(schema, evidence$10);
    }
    
    public static <T extends GenericRecord> Injection<T, byte[]> withCompression(final Schema schema, final CodecFactory codecFactory, final ClassTag<T> evidence$9) {
        return GenericAvroCodecs$.MODULE$.withCompression(schema, codecFactory, evidence$9);
    }
    
    public static <T extends GenericRecord> Injection<T, byte[]> apply(final Schema schema) {
        return GenericAvroCodecs$.MODULE$.apply(schema);
    }
}
