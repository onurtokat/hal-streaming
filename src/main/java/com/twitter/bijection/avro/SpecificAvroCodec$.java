// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection.avro;

import scala.None$;
import org.apache.avro.file.CodecFactory;
import scala.Option;
import org.apache.avro.specific.SpecificRecordBase;
import scala.Serializable;

public final class SpecificAvroCodec$ implements Serializable
{
    public static final SpecificAvroCodec$ MODULE$;
    
    static {
        new SpecificAvroCodec$();
    }
    
    public <T extends SpecificRecordBase> Option<CodecFactory> $lessinit$greater$default$2() {
        return (Option<CodecFactory>)None$.MODULE$;
    }
    
    private Object readResolve() {
        return SpecificAvroCodec$.MODULE$;
    }
    
    private SpecificAvroCodec$() {
        MODULE$ = this;
    }
}
