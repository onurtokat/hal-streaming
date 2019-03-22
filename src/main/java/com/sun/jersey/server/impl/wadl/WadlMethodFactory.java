// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.server.impl.wadl;

import com.sun.research.ws.wadl.Application;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.server.impl.model.method.ResourceHttpOptionsMethod;
import com.sun.jersey.spi.dispatch.RequestDispatcher;
import javax.ws.rs.core.MediaType;
import com.sun.jersey.core.header.MediaTypes;
import com.sun.jersey.api.uri.UriTemplate;
import com.sun.jersey.server.wadl.WadlApplicationContext;
import com.sun.jersey.api.model.AbstractResource;
import java.util.List;
import java.util.Map;
import com.sun.jersey.server.impl.model.method.ResourceMethod;

final class WadlMethodFactory
{
    public static final class WadlOptionsMethod extends ResourceMethod
    {
        public WadlOptionsMethod(final Map<String, List<ResourceMethod>> methods, final AbstractResource resource, final String path, final WadlApplicationContext wadlApplicationContext) {
            super("OPTIONS", UriTemplate.EMPTY, MediaTypes.GENERAL_MEDIA_TYPE_LIST, MediaTypes.GENERAL_MEDIA_TYPE_LIST, false, new WadlOptionsMethodDispatcher(methods, resource, path, wadlApplicationContext));
        }
        
        @Override
        public String toString() {
            return "WADL OPTIONS method";
        }
    }
    
    private static final class WadlOptionsMethodDispatcher extends ResourceHttpOptionsMethod.OptionsRequestDispatcher
    {
        private final AbstractResource resource;
        private final String path;
        private final WadlApplicationContext wadlApplicationContext;
        
        WadlOptionsMethodDispatcher(final Map<String, List<ResourceMethod>> methods, final AbstractResource resource, final String path, final WadlApplicationContext wadlApplicationContext) {
            super(methods);
            this.resource = resource;
            this.path = path;
            this.wadlApplicationContext = wadlApplicationContext;
        }
        
        @Override
        public void dispatch(final Object o, final HttpContext context) {
            if (this.wadlApplicationContext.isWadlGenerationEnabled()) {
                final Application a = this.wadlApplicationContext.getApplication(context.getUriInfo(), this.resource, this.path);
                context.getResponse().setResponse(Response.ok(a, MediaTypes.WADL).header("Allow", this.allow).build());
            }
            else {
                context.getResponse().setResponse(Response.status(Response.Status.NO_CONTENT).header("Allow", this.allow).build());
            }
        }
    }
}
