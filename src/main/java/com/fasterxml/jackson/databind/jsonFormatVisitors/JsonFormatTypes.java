// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.jsonFormatVisitors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum JsonFormatTypes
{
    STRING, 
    NUMBER, 
    INTEGER, 
    BOOLEAN, 
    OBJECT, 
    ARRAY, 
    NULL, 
    ANY;
    
    @JsonValue
    public String value() {
        return this.name().toLowerCase();
    }
    
    @JsonCreator
    public static JsonFormatTypes forValue(final String s) {
        return valueOf(s.toUpperCase());
    }
}
