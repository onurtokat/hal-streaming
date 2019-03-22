// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.runtime.BoxedUnit;
import scala.Predef$;
import java.io.OutputStream;
import java.io.InputStream;

public abstract class BinaryBijections$class
{
    public static void com$twitter$bijection$BinaryBijections$$copy(BinaryBijections $this, InputStream inputStream, OutputStream outputStream, int bufferSize) {
    Label_0068:
        while (true) {
            final byte[] buf = new byte[bufferSize];
            final int read = inputStream.read(buf, 0, Predef$.MODULE$.byteArrayOps(buf).size());
            switch (read) {
                default: {
                    outputStream.write(buf, 0, read);
                    final BinaryBijections binaryBijections = $this;
                    final InputStream \u0131nputStream = inputStream;
                    final OutputStream outputStream2 = outputStream;
                    bufferSize = bufferSize;
                    outputStream = outputStream2;
                    inputStream = \u0131nputStream;
                    $this = binaryBijections;
                    continue;
                }
                case -1: {
                    break Label_0068;
                }
            }
        }
        final BoxedUnit un\u0131t = BoxedUnit.UNIT;
    }
    
    public static int com$twitter$bijection$BinaryBijections$$copy$default$3(final BinaryBijections $this) {
        return 1024;
    }
    
    public static Bijection bytes2GzippedBytes(final BinaryBijections $this) {
        return (Bijection)new BinaryBijections$$anon.BinaryBijections$$anon$4($this);
    }
    
    public static Bijection bytes2Base64(final BinaryBijections $this) {
        return (Bijection)new BinaryBijections$$anon.BinaryBijections$$anon$5($this);
    }
    
    public static void $init$(final BinaryBijections $this) {
        $this.com$twitter$bijection$BinaryBijections$_setter_$bytes2Buffer_$eq((Bijection)new BinaryBijections$$anon.BinaryBijections$$anon$3($this));
        $this.com$twitter$bijection$BinaryBijections$_setter_$bytes2GZippedBase64_$eq((Bijection)new BinaryBijections$$anon.BinaryBijections$$anon$6($this));
    }
}
