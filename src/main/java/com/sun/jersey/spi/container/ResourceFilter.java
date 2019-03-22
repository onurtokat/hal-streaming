// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.spi.container;

public interface ResourceFilter
{
    ContainerRequestFilter getRequestFilter();
    
    ContainerResponseFilter getResponseFilter();
}
