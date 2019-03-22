// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.api.container.filter;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

public class PostReplaceFilter implements ContainerRequestFilter
{
    @Override
    public ContainerRequest filter(final ContainerRequest request) {
        if (!request.getMethod().equalsIgnoreCase("POST")) {
            return request;
        }
        String override = request.getRequestHeaders().getFirst("X-HTTP-Method-Override");
        if (override == null) {
            return request;
        }
        override = override.trim();
        if (override.length() == 0) {
            return request;
        }
        request.setMethod(override);
        return request;
    }
}
