// 
// Decompiled by Procyon v0.5.30
// 

package com.motive.mas.configuration.service.api;

import java.util.Map;

public interface ConfigurationService
{
    Map<String, Node> getAllNodes(final String p0);
    
    void set(final String p0, final byte[] p1) throws Exception;
    
    void remove(final String p0) throws Exception;
    
    byte[] get(final String p0);
    
    void addListenerToPath(final String p0, final NodeListener p1);
    
    void unregisterAllListeners();
    
    void atomicBatchUpdate(final Map<String, byte[]> p0) throws Exception;
    
    void atomicBatchUpdate(final Map<String, byte[]> p0, final String p1) throws Exception;
}
