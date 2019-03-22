// 
// Decompiled by Procyon v0.5.30
// 

package com.squareup.okhttp.internal.http;

import java.io.IOException;
import okio.Sink;

public interface CacheRequest
{
    Sink body() throws IOException;
    
    void abort();
}
