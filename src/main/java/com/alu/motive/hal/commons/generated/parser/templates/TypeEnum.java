// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.commons.generated.parser.templates;

import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "TypeEnum")
@XmlEnum
public enum TypeEnum
{
    STRING("string"), 
    UNSIGNED_INT("unsignedInt"), 
    UNSIGNED_LONG("unsignedLong"), 
    INT("int"), 
    LONG("long"), 
    BOOLEAN("boolean");
    
    private final String value;
    
    private TypeEnum(final String v) {
        this.value = v;
    }
    
    public String value() {
        return this.value;
    }
    
    public static TypeEnum fromValue(final String v) {
        for (final TypeEnum c : values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
