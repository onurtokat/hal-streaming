// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.ae.client.action.nest.model;

public enum CommonErrors
{
    NO_INPUT_DATA("Input data is not provided. The query cannot be created."), 
    NO_MAPPING_DATA("Data mapping is not provided. The query cannot be created."), 
    INVALID_UDF_JAR_LOCATION("Invalid UDF jar location. The query cannot be created."), 
    NO_TEMPLATES("Templates are not provided. The query cannot be created."), 
    NO_SCHEMA_DEFINITIONS("Schema definition templates are not provided. The query cannot be created."), 
    NO_SCHEMA_DEFINITION_FOR_TABLE("No schema definition template found for the table %s. The query cannot be created.");
    
    private final String message;
    
    private CommonErrors(final String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return this.message;
    }
}
