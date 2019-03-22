// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.research.ws.wadl;

import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "ParamStyle")
@XmlEnum
public enum ParamStyle
{
    PLAIN("plain"), 
    QUERY("query"), 
    MATRIX("matrix"), 
    HEADER("header"), 
    TEMPLATE("template");
    
    private final String value;
    
    private ParamStyle(final String v) {
        this.value = v;
    }
    
    public String value() {
        return this.value;
    }
    
    public static ParamStyle fromValue(final String v) {
        for (final ParamStyle c : values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
