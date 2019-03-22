// 
// Decompiled by Procyon v0.5.30
// 

package com.google.i18n.phonenumbers;

public class NumberParseException extends Exception
{
    private ErrorType errorType;
    private String message;
    
    public NumberParseException(final ErrorType errorType, final String message) {
        super(message);
        this.message = message;
        this.errorType = errorType;
    }
    
    public ErrorType getErrorType() {
        return this.errorType;
    }
    
    public String toString() {
        final String value = String.valueOf(String.valueOf(this.errorType));
        final String value2 = String.valueOf(String.valueOf(this.message));
        return new StringBuilder(14 + value.length() + value2.length()).append("Error type: ").append(value).append(". ").append(value2).toString();
    }
    
    public enum ErrorType
    {
        INVALID_COUNTRY_CODE, 
        NOT_A_NUMBER, 
        TOO_SHORT_AFTER_IDD, 
        TOO_SHORT_NSN, 
        TOO_LONG;
    }
}
