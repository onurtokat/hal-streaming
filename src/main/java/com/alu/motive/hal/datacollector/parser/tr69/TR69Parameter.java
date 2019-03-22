// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.parser.tr69;

import java.io.Serializable;

public class TR69Parameter implements Serializable
{
    private static final long serialVersionUID = 1299579689L;
    private String name;
    private String value;
    private String type;
    
    public TR69Parameter() {
    }
    
    public TR69Parameter(final String name, final String value, final String type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public void setValue(final String value) {
        this.value = value;
    }
    
    public String getType() {
        return this.type;
    }
    
    public void setType(final String type) {
        this.type = type;
    }
    
    @Override
    public String toString() {
        final StringBuilder strBldr = new StringBuilder();
        strBldr.append(this.getClass().getSimpleName() + "{name = ");
        strBldr.append(this.getName());
        strBldr.append(", value = ");
        strBldr.append(this.getValue());
        strBldr.append(", type = ");
        strBldr.append(this.getType());
        strBldr.append("}");
        return strBldr.toString();
    }
}
