// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector;

import java.util.List;
import java.util.Map;

public class ApiException extends Exception
{
    private int code;
    private Map<String, List<String>> responseHeaders;
    private String responseBody;
    
    public ApiException() {
        this.code = 0;
        this.responseHeaders = null;
        this.responseBody = null;
    }
    
    public ApiException(final Throwable throwable) {
        super(throwable);
        this.code = 0;
        this.responseHeaders = null;
        this.responseBody = null;
    }
    
    public ApiException(final String message) {
        super(message);
        this.code = 0;
        this.responseHeaders = null;
        this.responseBody = null;
    }
    
    public ApiException(final String message, final Throwable throwable, final int code, final Map<String, List<String>> responseHeaders, final String responseBody) {
        super(message, throwable);
        this.code = 0;
        this.responseHeaders = null;
        this.responseBody = null;
        this.code = code;
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
    }
    
    public ApiException(final String message, final int code, final Map<String, List<String>> responseHeaders, final String responseBody) {
        this(message, null, code, responseHeaders, responseBody);
    }
    
    public ApiException(final String message, final Throwable throwable, final int code, final Map<String, List<String>> responseHeaders) {
        this(message, throwable, code, responseHeaders, null);
    }
    
    public ApiException(final int code, final Map<String, List<String>> responseHeaders, final String responseBody) {
        this(null, null, code, responseHeaders, responseBody);
    }
    
    public ApiException(final int code, final String message) {
        super(message);
        this.code = 0;
        this.responseHeaders = null;
        this.responseBody = null;
        this.code = code;
    }
    
    public ApiException(final int code, final String message, final Map<String, List<String>> responseHeaders, final String responseBody) {
        this(code, message);
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
    }
    
    public int getCode() {
        return this.code;
    }
    
    public Map<String, List<String>> getResponseHeaders() {
        return this.responseHeaders;
    }
    
    public String getResponseBody() {
        return this.responseBody;
    }
}
