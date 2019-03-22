// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector;

public class Configuration
{
    private static ApiClient defaultApiClient;
    
    public static ApiClient getDefaultApiClient() {
        return Configuration.defaultApiClient;
    }
    
    public static void setDefaultApiClient(final ApiClient apiClient) {
        Configuration.defaultApiClient = apiClient;
    }
    
    static {
        Configuration.defaultApiClient = new ApiClient();
    }
}
