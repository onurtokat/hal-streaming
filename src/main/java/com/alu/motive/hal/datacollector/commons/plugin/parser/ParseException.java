// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.commons.plugin.parser;

public class ParseException extends Exception
{
    private static final long serialVersionUID = -7235373353489755903L;
    
    public ParseException(final String message) {
        super(message);
    }
    
    public ParseException(final String message, final Exception e) {
        super(message, e);
    }
}
