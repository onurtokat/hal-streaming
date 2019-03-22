// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.server.impl.wadl;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.xml.bind.Marshaller;
import java.util.Iterator;
import com.sun.research.ws.wadl.Application;
import com.sun.jersey.server.wadl.ApplicationDescription;
import java.io.ByteArrayInputStream;
import java.util.logging.Level;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import com.sun.research.ws.wadl.Resources;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Context;
import com.sun.jersey.server.wadl.WadlApplicationContext;
import java.util.logging.Logger;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
public final class WadlResource
{
    private static final Logger LOGGER;
    private WadlApplicationContext wadlContext;
    private byte[] wadlXmlRepresentation;
    
    public WadlResource(@Context final WadlApplicationContext wadlContext) {
        this.wadlContext = wadlContext;
    }
    
    @Produces({ "application/vnd.sun.wadl+xml", "application/xml" })
    @GET
    public synchronized Response getWadl(@Context final UriInfo uriInfo) {
        if (!this.wadlContext.isWadlGenerationEnabled()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        final ApplicationDescription applicationDescription = this.wadlContext.getApplication(uriInfo);
        final Application application = applicationDescription.getApplication();
        if (this.wadlXmlRepresentation == null) {
            for (final Resources resources : application.getResources()) {
                if (resources.getBase() == null) {
                    resources.setBase(uriInfo.getBaseUri().toString());
                }
            }
            try {
                final Marshaller marshaller = this.wadlContext.getJAXBContext().createMarshaller();
                marshaller.setProperty("jaxb.formatted.output", true);
                final ByteArrayOutputStream os = new ByteArrayOutputStream();
                marshaller.marshal(application, os);
                this.wadlXmlRepresentation = os.toByteArray();
                os.close();
            }
            catch (Exception e) {
                WadlResource.LOGGER.log(Level.WARNING, "Could not marshal wadl Application.", e);
                return Response.ok(applicationDescription).build();
            }
        }
        return Response.ok(new ByteArrayInputStream(this.wadlXmlRepresentation)).build();
    }
    
    @Produces({ "application/xml" })
    @GET
    @Path("{path}")
    public synchronized Response geExternalGramar(@Context final UriInfo uriInfo, @PathParam("path") final String path) {
        if (!this.wadlContext.isWadlGenerationEnabled()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        final ApplicationDescription applicationDescription = this.wadlContext.getApplication(uriInfo);
        final ApplicationDescription.ExternalGrammar externalMetadata = applicationDescription.getExternalGrammar(path);
        if (externalMetadata == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().type(externalMetadata.getType()).entity(externalMetadata.getContent()).build();
    }
    
    static {
        LOGGER = Logger.getLogger(WadlResource.class.getName());
    }
}
