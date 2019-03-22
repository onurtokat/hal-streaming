// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.main.cli;

enum RetCode
{
    ALL_OK(0), 
    CMD_ERROR(2), 
    VALIDATION_FAILURE(100), 
    SCHEMA_SYNTAX_ERROR(101);
    
    private final int retCode;
    
    private RetCode(final int retCode) {
        this.retCode = retCode;
    }
    
    int get() {
        return this.retCode;
    }
}
