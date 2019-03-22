// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.exception;

import java.net.URISyntaxException;

public class WifiTemplateParseException extends HalWifiException
{
    public WifiTemplateParseException(final String message) {
        super(message);
    }
    
    public WifiTemplateParseException(final String message, final URISyntaxException e) {
        super(message, e);
    }
}
