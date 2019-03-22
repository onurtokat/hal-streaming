// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.api.container.filter;

import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;
import com.sun.jersey.spi.container.ContainerResponseWriter;
import com.sun.jersey.spi.container.ContainerResponse;
import java.io.IOException;
import com.sun.jersey.api.container.ContainerException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import com.sun.jersey.spi.container.ContainerRequestFilter;

public class GZIPContentEncodingFilter implements ContainerRequestFilter, ContainerResponseFilter
{
    @Override
    public ContainerRequest filter(final ContainerRequest request) {
        if (request.getRequestHeaders().containsKey("Content-Encoding") && request.getRequestHeaders().getFirst("Content-Encoding").trim().equals("gzip")) {
            request.getRequestHeaders().remove("Content-Encoding");
            try {
                request.setEntityInputStream(new GZIPInputStream(request.getEntityInputStream()));
            }
            catch (IOException ex) {
                throw new ContainerException(ex);
            }
        }
        return request;
    }
    
    @Override
    public ContainerResponse filter(final ContainerRequest request, final ContainerResponse response) {
        if (response.getEntity() != null && request.getRequestHeaders().containsKey("Accept-Encoding") && !response.getHttpHeaders().containsKey("Content-Encoding") && request.getRequestHeaders().getFirst("Accept-Encoding").contains("gzip")) {
            response.getHttpHeaders().add("Content-Encoding", "gzip");
            response.setContainerResponseWriter(new Adapter(response.getContainerResponseWriter()));
        }
        return response;
    }
    
    private static final class Adapter implements ContainerResponseWriter
    {
        private final ContainerResponseWriter crw;
        private GZIPOutputStream gos;
        
        Adapter(final ContainerResponseWriter crw) {
            this.crw = crw;
        }
        
        @Override
        public OutputStream writeStatusAndHeaders(final long contentLength, final ContainerResponse response) throws IOException {
            return this.gos = new GZIPOutputStream(this.crw.writeStatusAndHeaders(-1L, response));
        }
        
        @Override
        public void finish() throws IOException {
            this.gos.finish();
            this.crw.finish();
        }
    }
}
