// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.parser.tr69;

import com.alu.motive.hal.datacollector.commons.plugin.parser.ParseException;

public class InvalidDeviceIdException extends ParseException
{
    private static final long serialVersionUID = -8670651192695351562L;
    
    public InvalidDeviceIdException(final String message) {
        super(message);
    }
    
    public InvalidDeviceIdException(final String msg, final Exception e) {
        super(msg, e);
    }
}
