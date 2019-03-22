// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector.auth;

import com.squareup.okhttp.Credentials;
import java.util.Map;
import com.nokia.hal.nbi.connector.Pair;
import java.util.List;

public class HttpBasicAuth implements Authentication
{
    private String username;
    private String password;
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(final String password) {
        this.password = password;
    }
    
    @Override
    public void applyToParams(final List<Pair> queryParams, final Map<String, String> headerParams) {
        if (this.username == null && this.password == null) {
            return;
        }
        headerParams.put("Authorization", Credentials.basic((this.username == null) ? "" : this.username, (this.password == null) ? "" : this.password));
    }
}
