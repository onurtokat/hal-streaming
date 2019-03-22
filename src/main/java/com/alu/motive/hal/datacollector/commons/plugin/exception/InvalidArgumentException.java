// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.commons.plugin.exception;

public class InvalidArgumentException extends Exception
{
    public static final long serialVersionUID = 987654321123456789L;
    String argumentName;
    Object argumentValue;
    String message;
    
    public InvalidArgumentException(final String name, final Object value, final String message) {
        super(message);
        this.argumentName = null;
        this.message = null;
        this.argumentName = name;
        this.argumentValue = value;
        this.message = message;
    }
    
    public String getArgumentName() {
        return this.argumentName;
    }
    
    public void setArgumentName(final String argumentName) {
        this.argumentName = argumentName;
    }
    
    public Object getArgumentValue() {
        return this.argumentValue;
    }
    
    public void setArgumentValue(final Object argumentValue) {
        this.argumentValue = argumentValue;
    }
    
    @Override
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(final String message) {
        this.message = message;
    }
}
