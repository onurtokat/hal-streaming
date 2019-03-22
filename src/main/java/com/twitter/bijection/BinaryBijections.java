// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import java.nio.ByteBuffer;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes = "\u0006\u0001I4q!\u0001\u0002\u0011\u0002\u0007\u0005\u0011B\u0001\tCS:\f'/\u001f\"jU\u0016\u001cG/[8og*\u00111\u0001B\u0001\nE&TWm\u0019;j_:T!!\u0002\u0004\u0002\u000fQ<\u0018\u000e\u001e;fe*\tq!A\u0002d_6\u001c\u0001aE\u0002\u0001\u0015A\u0001\"a\u0003\b\u000e\u00031Q\u0011!D\u0001\u0006g\u000e\fG.Y\u0005\u0003\u001f1\u0011a!\u00118z%\u00164\u0007CA\t\u0013\u001b\u0005\u0011\u0011BA\n\u0003\u0005A\u0019FO]5oO\nK'.Z2uS>t7\u000fC\u0003\u0016\u0001\u0011\u0005a#\u0001\u0004%S:LG\u000f\n\u000b\u0002/A\u00111\u0002G\u0005\u000331\u0011A!\u00168ji\"91\u0004\u0001b\u0001\n\u0007a\u0012\u0001\u00042zi\u0016\u001c(GQ;gM\u0016\u0014X#A\u000f\u0011\tEq\u0002EJ\u0005\u0003?\t\u0011\u0011BQ5kK\u000e$\u0018n\u001c8\u0011\u0007-\t3%\u0003\u0002#\u0019\t)\u0011I\u001d:bsB\u00111\u0002J\u0005\u0003K1\u0011AAQ=uKB\u0011q\u0005L\u0007\u0002Q)\u0011\u0011FK\u0001\u0004]&|'\"A\u0016\u0002\t)\fg/Y\u0005\u0003[!\u0012!BQ=uK\n+hMZ3s\u0011\u0019y\u0003\u0001)A\u0005;\u0005i!-\u001f;fgJ\u0012UO\u001a4fe\u0002BQ!\r\u0001\u0005\nI\nAaY8qsR!qcM\u001eA\u0011\u0015!\u0004\u00071\u00016\u0003-Ig\u000e];u'R\u0014X-Y7\u0011\u0005YJT\"A\u001c\u000b\u0005aR\u0013AA5p\u0013\tQtGA\u0006J]B,Ho\u0015;sK\u0006l\u0007\"\u0002\u001f1\u0001\u0004i\u0014\u0001D8viB,Ho\u0015;sK\u0006l\u0007C\u0001\u001c?\u0013\tytG\u0001\u0007PkR\u0004X\u000f^*ue\u0016\fW\u000eC\u0004BaA\u0005\t\u0019\u0001\"\u0002\u0015\t,hMZ3s'&TX\r\u0005\u0002\f\u0007&\u0011A\t\u0004\u0002\u0004\u0013:$\bF\u0001\u0019G!\t9%*D\u0001I\u0015\tIE\"\u0001\u0006b]:|G/\u0019;j_:L!a\u0013%\u0003\u000fQ\f\u0017\u000e\u001c:fG\"AQ\n\u0001EC\u0002\u0013\ra*\u0001\ncsR,7OM${SB\u0004X\r\u001a\"zi\u0016\u001cX#A(\u0011\tEq\u0002\u0005\u0015\t\u0003#EK!A\u0015\u0002\u0003\u0019\u001dS\u0016\u000e\u001d9fI\nKH/Z:\t\u0011Q\u0003\u0001\u0012!Q!\n=\u000b1CY=uKN\u0014tI_5qa\u0016$')\u001f;fg\u0002B\u0001B\u0016\u0001\t\u0006\u0004%\u0019aV\u0001\rEf$Xm\u001d\u001aCCN,g\u0007N\u000b\u00021B!\u0011C\b\u0011Z!\t\t\",\u0003\u0002\\\u0005\ta!)Y:fmQ\u001aFO]5oO\"AQ\f\u0001E\u0001B\u0003&\u0001,A\u0007csR,7O\r\"bg\u00164D\u0007\t\u0005\b?\u0002\u0011\r\u0011b\u0001a\u0003M\u0011\u0017\u0010^3te\u001dS\u0016\u000e\u001d9fI\n\u000b7/\u001a\u001c5+\u0005\t\u0007\u0003B\t\u001fA\t\u0004\"!E2\n\u0005\u0011\u0014!aE$[SB\u0004X\r\u001a\"bg\u00164Dg\u0015;sS:<\u0007B\u00024\u0001A\u0003%\u0011-\u0001\u000bcsR,7OM$[SB\u0004X\r\u001a\"bg\u00164D\u0007\t\u0005\bQ\u0002\t\n\u0011\"\u0003j\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIM*\u0012A\u001b\u0016\u0003\u0005.\\\u0013\u0001\u001c\t\u0003[Bl\u0011A\u001c\u0006\u0003_\"\u000b\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\n\u0005Et'!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0002")
public interface BinaryBijections extends StringBijections
{
    void com$twitter$bijection$BinaryBijections$_setter_$bytes2Buffer_$eq(final Bijection p0);
    
    void com$twitter$bijection$BinaryBijections$_setter_$bytes2GZippedBase64_$eq(final Bijection p0);
    
    Bijection<byte[], ByteBuffer> bytes2Buffer();
    
    Bijection<byte[], byte[]> bytes2GzippedBytes();
    
    Bijection<byte[], String> bytes2Base64();
    
    Bijection<byte[], String> bytes2GZippedBase64();
}
