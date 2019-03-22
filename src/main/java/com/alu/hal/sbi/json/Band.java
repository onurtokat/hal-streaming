// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.sbi.json;

import org.apache.avro.Schema;
import org.apache.avro.specific.AvroGenerated;

@AvroGenerated
public enum Band
{
    BAND24, 
    BAND50;
    
    public static final Schema SCHEMA$;
    
    public static Schema getClassSchema() {
        return Band.SCHEMA$;
    }
    
    static {
        SCHEMA$ = new Schema.Parser().parse("{\"type\":\"enum\",\"name\":\"Band\",\"namespace\":\"com.alu.hal.sbi.json\",\"symbols\":[\"BAND24\",\"BAND50\"]}");
    }
}
