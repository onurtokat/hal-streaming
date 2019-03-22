// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.server.wadl;

import java.util.Iterator;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.sun.research.ws.wadl.Param;
import com.sun.jersey.api.model.Parameter;
import com.sun.jersey.api.model.AbstractMethod;
import com.sun.research.ws.wadl.Response;
import com.sun.research.ws.wadl.Representation;
import javax.ws.rs.core.MediaType;
import com.sun.research.ws.wadl.Request;
import com.sun.research.ws.wadl.Method;
import com.sun.jersey.api.model.AbstractResourceMethod;
import com.sun.research.ws.wadl.Resource;
import com.sun.jersey.api.model.AbstractResource;
import com.sun.research.ws.wadl.Resources;
import com.sun.research.ws.wadl.Application;

public interface WadlGenerator
{
    void setWadlGeneratorDelegate(final WadlGenerator p0);
    
    void init() throws Exception;
    
    String getRequiredJaxbContextPath();
    
    Application createApplication();
    
    Resources createResources();
    
    Resource createResource(final AbstractResource p0, final String p1);
    
    Method createMethod(final AbstractResource p0, final AbstractResourceMethod p1);
    
    Request createRequest(final AbstractResource p0, final AbstractResourceMethod p1);
    
    Representation createRequestRepresentation(final AbstractResource p0, final AbstractResourceMethod p1, final MediaType p2);
    
    Response createResponse(final AbstractResource p0, final AbstractResourceMethod p1);
    
    Param createParam(final AbstractResource p0, final AbstractMethod p1, final Parameter p2);
    
    ExternalGrammarDefinition createExternalGrammar();
    
    void attachTypes(final ApplicationDescription p0);
    
    public static class ExternalGrammarDefinition
    {
        public final Map<String, ApplicationDescription.ExternalGrammar> map;
        private List<Resolver> typeResolvers;
        
        public ExternalGrammarDefinition() {
            this.map = new HashMap<String, ApplicationDescription.ExternalGrammar>();
            this.typeResolvers = new ArrayList<Resolver>();
        }
        
        public void addResolver(final Resolver resolver) {
            assert !this.typeResolvers.contains(resolver) : "Already in list";
            this.typeResolvers.add(resolver);
        }
        
        public QName resolve(final Class type) {
            QName name = null;
            for (final Resolver resolver : this.typeResolvers) {
                name = resolver.resolve(type);
                if (name != null) {
                    break;
                }
            }
            return name;
        }
    }
    
    public interface Resolver
    {
        QName resolve(final Class p0);
    }
}
