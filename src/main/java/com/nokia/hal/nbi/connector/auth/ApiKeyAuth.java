// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector.auth;

import java.util.Map;
import com.nokia.hal.nbi.connector.Pair;
import java.util.List;

public class ApiKeyAuth implements Authentication
{
    private final String location;
    private final String paramName;
    private String apiKey;
    private String apiKeyPrefix;
    
    public ApiKeyAuth(final String location, final String paramName) {
        this.location = location;
        this.paramName = paramName;
    }
    
    public String getLocation() {
        return this.location;
    }
    
    public String getParamName() {
        return this.paramName;
    }
    
    public String getApiKey() {
        return this.apiKey;
    }
    
    public void setApiKey(final String apiKey) {
        this.apiKey = apiKey;
    }
    
    public String getApiKeyPrefix() {
        return this.apiKeyPrefix;
    }
    
    public void setApiKeyPrefix(final String apiKeyPrefix) {
        this.apiKeyPrefix = apiKeyPrefix;
    }
    
    @Override
    public void applyToParams(final List<Pair> queryParams, final Map<String, String> headerParams) {
        if (this.apiKey == null) {
            return;
        }
        String value;
        if (this.apiKeyPrefix != null) {
            value = this.apiKeyPrefix + " " + this.apiKey;
        }
        else {
            value = this.apiKey;
        }
        if ("query".equals(this.location)) {
            queryParams.add(new Pair(this.paramName, value));
        }
        else if ("header".equals(this.location)) {
            headerParams.put(this.paramName, value);
        }
    }
}
