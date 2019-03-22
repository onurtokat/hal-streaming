// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.parser.eud;

import com.alu.motive.hal.datacollector.commons.plugin.parser.ParseException;

public class InvalidJSONException extends ParseException
{
    private final String acceptedSchema;
    private final String json;
    private static final long serialVersionUID = 2955770576107541844L;
    
    public InvalidJSONException(final String json, final String schema) {
        super("Json does not match the expected schema.");
        this.json = json;
        this.acceptedSchema = schema;
    }
    
    public String getAcceptedSchema() {
        return this.acceptedSchema;
    }
    
    public String getJson() {
        return this.json;
    }
}
