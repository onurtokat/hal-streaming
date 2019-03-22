// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector;

public class Pair
{
    private String name;
    private String value;
    
    public Pair(final String name, final String value) {
        this.name = "";
        this.value = "";
        this.setName(name);
        this.setValue(value);
    }
    
    private void setName(final String name) {
        if (!this.isValidString(name)) {
            return;
        }
        this.name = name;
    }
    
    private void setValue(final String value) {
        if (!this.isValidString(value)) {
            return;
        }
        this.value = value;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getValue() {
        return this.value;
    }
    
    private boolean isValidString(final String arg) {
        return arg != null && !arg.trim().isEmpty();
    }
}
