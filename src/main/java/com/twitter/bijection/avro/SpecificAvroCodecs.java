// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection.avro;

import org.apache.avro.file.CodecFactory;
import com.twitter.bijection.Injection;
import org.apache.avro.specific.SpecificRecordBase;
import scala.reflect.ClassTag;
import org.apache.avro.Schema;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes = "\u0006\u0001\u0005%s!B\u0001\u0003\u0011\u0003Y\u0011AE*qK\u000eLg-[2BmJ|7i\u001c3fGNT!a\u0001\u0003\u0002\t\u00054(o\u001c\u0006\u0003\u000b\u0019\t\u0011BY5kK\u000e$\u0018n\u001c8\u000b\u0005\u001dA\u0011a\u0002;xSR$XM\u001d\u0006\u0002\u0013\u0005\u00191m\\7\u0004\u0001A\u0011A\"D\u0007\u0002\u0005\u0019)aB\u0001E\u0001\u001f\t\u00112\u000b]3dS\u001aL7-\u0011<s_\u000e{G-Z2t'\ti\u0001\u0003\u0005\u0002\u0012)5\t!CC\u0001\u0014\u0003\u0015\u00198-\u00197b\u0013\t)\"C\u0001\u0004B]f\u0014VM\u001a\u0005\u0006/5!\t\u0001G\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003-AQAG\u0007\u0005\u0002m\tQ!\u00199qYf,\"\u0001H\u0012\u0015\u0005uQ\u0004\u0003\u0002\u0010 CQj\u0011\u0001B\u0005\u0003A\u0011\u0011\u0011\"\u00138kK\u000e$\u0018n\u001c8\u0011\u0005\t\u001aC\u0002\u0001\u0003\u0006Ie\u0011\r!\n\u0002\u0002)F\u0011a%\u000b\t\u0003#\u001dJ!\u0001\u000b\n\u0003\u000f9{G\u000f[5oOB\u0011!FM\u0007\u0002W)\u0011A&L\u0001\tgB,7-\u001b4jG*\u00111A\f\u0006\u0003_A\na!\u00199bG\",'\"A\u0019\u0002\u0007=\u0014x-\u0003\u00024W\t\u00112\u000b]3dS\u001aL7MU3d_J$')Y:f!\r\tRgN\u0005\u0003mI\u0011Q!\u0011:sCf\u0004\"!\u0005\u001d\n\u0005e\u0012\"\u0001\u0002\"zi\u0016DqaO\r\u0002\u0002\u0003\u000fA(\u0001\u0006fm&$WM\\2fIE\u00022!\u0010!\"\u001b\u0005q$BA \u0013\u0003\u001d\u0011XM\u001a7fGRL!!\u0011 \u0003\u0011\rc\u0017m]:UC\u001eDQaQ\u0007\u0005\u0002\u0011\u000bqb^5uQ\u000e{W\u000e\u001d:fgNLwN\\\u000b\u0003\u000b&#\"AR'\u0015\u0005\u001dS\u0005\u0003\u0002\u0010 \u0011R\u0002\"AI%\u0005\u000b\u0011\u0012%\u0019A\u0013\t\u000f-\u0013\u0015\u0011!a\u0002\u0019\u0006QQM^5eK:\u001cW\r\n\u001a\u0011\u0007u\u0002\u0005\nC\u0003O\u0005\u0002\u0007q*\u0001\u0007d_\u0012,7MR1di>\u0014\u0018\u0010\u0005\u0002Q'6\t\u0011K\u0003\u0002S[\u0005!a-\u001b7f\u0013\t!\u0016K\u0001\u0007D_\u0012,7MR1di>\u0014\u0018\u0010C\u0003W\u001b\u0011\u0005q+\u0001\u000bxSRD'I_5qe\r{W\u000e\u001d:fgNLwN\\\u000b\u00031n#\"!\u0017/\u0011\tyy\"\f\u000e\t\u0003Em#Q\u0001J+C\u0002\u0015Bq!X+\u0002\u0002\u0003\u000fa,\u0001\u0006fm&$WM\\2fIM\u00022!\u0010![\u0011\u0015\u0001W\u0002\"\u0001b\u0003Y9\u0018\u000e\u001e5EK\u001ad\u0017\r^3D_6\u0004(/Z:tS>tWC\u00012g)\t\u0019'\u000e\u0006\u0002eOB!adH35!\t\u0011c\rB\u0003%?\n\u0007Q\u0005C\u0004i?\u0006\u0005\t9A5\u0002\u0015\u00154\u0018\u000eZ3oG\u0016$C\u0007E\u0002>\u0001\u0016DQa[0A\u00021\f\u0001cY8naJ,7o]5p]2+g/\u001a7\u0011\u0005Ei\u0017B\u00018\u0013\u0005\rIe\u000e\u001e\u0005\u0006A6!\t\u0001]\u000b\u0003cR$\"A];\u0011\tyy2\u000f\u000e\t\u0003EQ$Q\u0001J8C\u0002\u0015BqA^8\u0002\u0002\u0003\u000fq/\u0001\u0006fm&$WM\\2fIU\u00022!\u0010!t\u0011\u0015IX\u0002\"\u0001{\u0003U9\u0018\u000e\u001e5T]\u0006\u0004\b/_\"p[B\u0014Xm]:j_:,\"a\u001f@\u0015\u0005q|\b\u0003\u0002\u0010 {R\u0002\"A\t@\u0005\u000b\u0011B(\u0019A\u0013\t\u0013\u0005\u0005\u00010!AA\u0004\u0005\r\u0011AC3wS\u0012,gnY3%mA\u0019Q\bQ?\t\u000f\u0005\u001dQ\u0002\"\u0001\u0002\n\u0005AAo\u001c\"j]\u0006\u0014\u00180\u0006\u0003\u0002\f\u0005EA\u0003BA\u0007\u0003'\u0001RAH\u0010\u0002\u0010Q\u00022AIA\t\t\u0019!\u0013Q\u0001b\u0001K!Q\u0011QCA\u0003\u0003\u0003\u0005\u001d!a\u0006\u0002\u0015\u00154\u0018\u000eZ3oG\u0016$s\u0007\u0005\u0003>\u0001\u0006=\u0001bBA\u000e\u001b\u0011\u0005\u0011QD\u0001\u0007i>T5o\u001c8\u0016\t\u0005}\u0011q\u0005\u000b\u0005\u0003C\ti\u0004\u0006\u0003\u0002$\u0005]\u0002C\u0002\u0010 \u0003K\tI\u0003E\u0002#\u0003O!a\u0001JA\r\u0005\u0004)\u0003\u0003BA\u0016\u0003cq1!EA\u0017\u0013\r\tyCE\u0001\u0007!J,G-\u001a4\n\t\u0005M\u0012Q\u0007\u0002\u0007'R\u0014\u0018N\\4\u000b\u0007\u0005=\"\u0003\u0003\u0006\u0002:\u0005e\u0011\u0011!a\u0002\u0003w\t!\"\u001a<jI\u0016t7-\u001a\u00139!\u0011i\u0004)!\n\t\u0011\u0005}\u0012\u0011\u0004a\u0001\u0003\u0003\naa]2iK6\f\u0007\u0003BA\"\u0003\u000bj\u0011!L\u0005\u0004\u0003\u000fj#AB*dQ\u0016l\u0017\r")
public final class SpecificAvroCodecs
{
    public static <T extends SpecificRecordBase> Injection<T, String> toJson(final Schema schema, final ClassTag<T> evidence$8) {
        return SpecificAvroCodecs$.MODULE$.toJson(schema, evidence$8);
    }
    
    public static <T extends SpecificRecordBase> Injection<T, byte[]> toBinary(final ClassTag<T> evidence$7) {
        return SpecificAvroCodecs$.MODULE$.toBinary(evidence$7);
    }
    
    public static <T extends SpecificRecordBase> Injection<T, byte[]> withSnappyCompression(final ClassTag<T> evidence$6) {
        return SpecificAvroCodecs$.MODULE$.withSnappyCompression(evidence$6);
    }
    
    public static <T extends SpecificRecordBase> Injection<T, byte[]> withDeflateCompression(final ClassTag<T> evidence$5) {
        return SpecificAvroCodecs$.MODULE$.withDeflateCompression(evidence$5);
    }
    
    public static <T extends SpecificRecordBase> Injection<T, byte[]> withDeflateCompression(final int compressionLevel, final ClassTag<T> evidence$4) {
        return SpecificAvroCodecs$.MODULE$.withDeflateCompression(compressionLevel, evidence$4);
    }
    
    public static <T extends SpecificRecordBase> Injection<T, byte[]> withBzip2Compression(final ClassTag<T> evidence$3) {
        return SpecificAvroCodecs$.MODULE$.withBzip2Compression(evidence$3);
    }
    
    public static <T extends SpecificRecordBase> Injection<T, byte[]> withCompression(final CodecFactory codecFactory, final ClassTag<T> evidence$2) {
        return SpecificAvroCodecs$.MODULE$.withCompression(codecFactory, evidence$2);
    }
    
    public static <T extends SpecificRecordBase> Injection<T, byte[]> apply(final ClassTag<T> evidence$1) {
        return SpecificAvroCodecs$.MODULE$.apply(evidence$1);
    }
}
