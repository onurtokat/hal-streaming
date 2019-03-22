// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection.avro;

import scala.None$;
import org.apache.avro.file.CodecFactory;
import scala.Option;
import org.apache.avro.generic.GenericRecord;
import scala.Serializable;

public final class GenericAvroCodec$ implements Serializable
{
    public static final GenericAvroCodec$ MODULE$;
    
    static {
        new GenericAvroCodec$();
    }
    
    public <T extends GenericRecord> Option<CodecFactory> $lessinit$greater$default$2() {
        return (Option<CodecFactory>)None$.MODULE$;
    }
    
    private Object readResolve() {
        return GenericAvroCodec$.MODULE$;
    }
    
    private GenericAvroCodec$() {
        MODULE$ = this;
    }
}
