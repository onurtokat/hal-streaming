// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector;

import java.util.List;
import java.util.Map;

public interface ApiCallback<T>
{
    void onFailure(final ApiException p0, final int p1, final Map<String, List<String>> p2);
    
    void onSuccess(final T p0, final int p1, final Map<String, List<String>> p2);
    
    void onUploadProgress(final long p0, final long p1, final boolean p2);
    
    void onDownloadProgress(final long p0, final long p1, final boolean p2);
}
