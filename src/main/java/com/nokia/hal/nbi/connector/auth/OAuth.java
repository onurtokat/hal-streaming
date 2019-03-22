// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector.auth;

import java.util.Map;
import com.nokia.hal.nbi.connector.Pair;
import java.util.List;

public class OAuth implements Authentication
{
    private String accessToken;
    
    public String getAccessToken() {
        return this.accessToken;
    }
    
    public void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }
    
    @Override
    public void applyToParams(final List<Pair> queryParams, final Map<String, String> headerParams) {
        if (this.accessToken != null) {
            headerParams.put("Authorization", "Bearer " + this.accessToken);
        }
    }
}
