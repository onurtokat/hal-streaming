// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.parser.eud;

public class JSONValidationException extends Exception
{
    private static final long serialVersionUID = -1043487472926763344L;
    
    public JSONValidationException() {
        super("The received json could not be validated");
    }
}
