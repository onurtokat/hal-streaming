// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector;

import java.util.List;
import java.util.Map;

public class ApiResponse<T>
{
    private final int statusCode;
    private final Map<String, List<String>> headers;
    private final T data;
    
    public ApiResponse(final int statusCode, final Map<String, List<String>> headers) {
        this(statusCode, headers, null);
    }
    
    public ApiResponse(final int statusCode, final Map<String, List<String>> headers, final T data) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.data = data;
    }
    
    public int getStatusCode() {
        return this.statusCode;
    }
    
    public Map<String, List<String>> getHeaders() {
        return this.headers;
    }
    
    public T getData() {
        return this.data;
    }
}
