// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector.logging;

import org.slf4j.LoggerFactory;
import java.io.IOException;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.slf4j.Logger;
import com.squareup.okhttp.Interceptor;

class LogRequestInterceptor implements Interceptor
{
    private static final Logger LOG;
    
    @Override
    public Response intercept(final Chain chain) throws IOException {
        final Request request = chain.request();
        final long t1 = System.nanoTime();
        LogRequestInterceptor.LOG.debug(String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));
        final Response response = chain.proceed(request);
        final long t2 = System.nanoTime();
        LogRequestInterceptor.LOG.debug(String.format("Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1000000.0, response.headers()));
        return response;
    }
    
    static {
        LOG = LoggerFactory.getLogger(LogRequestInterceptor.class);
    }
}
