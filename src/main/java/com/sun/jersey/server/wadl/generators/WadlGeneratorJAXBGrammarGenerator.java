// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.server.wadl.generators;

import com.sun.jersey.api.JResponse;
import javax.xml.bind.JAXBIntrospector;
import java.io.IOException;
import javax.xml.bind.JAXBException;
import java.util.logging.Level;
import java.io.Writer;
import java.io.CharArrayWriter;
import javax.xml.transform.Result;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.stream.StreamResult;
import javax.xml.bind.JAXBContext;
import java.lang.reflect.ParameterizedType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;
import com.sun.jersey.server.wadl.ApplicationDescription;
import java.util.HashMap;
import com.sun.research.ws.wadl.Response;
import com.sun.research.ws.wadl.Resources;
import java.util.Collection;
import java.util.Collections;
import javax.xml.bind.annotation.XmlSeeAlso;
import com.sun.research.ws.wadl.Resource;
import java.util.Iterator;
import com.sun.research.ws.wadl.Representation;
import javax.ws.rs.core.MediaType;
import javax.xml.namespace.QName;
import com.sun.research.ws.wadl.Param;
import com.sun.jersey.api.model.AbstractMethod;
import com.sun.research.ws.wadl.Request;
import com.sun.research.ws.wadl.Method;
import com.sun.jersey.api.model.AbstractResourceMethod;
import com.sun.jersey.api.model.AbstractResource;
import com.sun.research.ws.wadl.Application;
import java.util.ArrayList;
import java.util.HashSet;
import com.sun.jersey.server.wadl.WadlGeneratorImpl;
import java.lang.reflect.Type;
import com.sun.jersey.api.model.Parameter;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import com.sun.jersey.server.wadl.WadlGenerator;

public class WadlGeneratorJAXBGrammarGenerator implements WadlGenerator
{
    private static final Logger LOG;
    private static final Set<Class> SPECIAL_GENERIC_TYPES;
    private WadlGenerator _delegate;
    private Set<Class> _seeAlso;
    private List<Pair> _hasTypeWantsName;
    
    private static final HasType parameter(final Parameter param) {
        return new HasType() {
            @Override
            public Class getPrimaryClass() {
                return param.getParameterClass();
            }
            
            @Override
            public Type getType() {
                return param.getParameterType();
            }
        };
    }
    
    public WadlGeneratorJAXBGrammarGenerator() {
        this._delegate = new WadlGeneratorImpl();
    }
    
    @Override
    public void setWadlGeneratorDelegate(final WadlGenerator delegate) {
        this._delegate = delegate;
    }
    
    @Override
    public String getRequiredJaxbContextPath() {
        return this._delegate.getRequiredJaxbContextPath();
    }
    
    @Override
    public void init() throws Exception {
        this._delegate.init();
        this._seeAlso = new HashSet<Class>();
        this._hasTypeWantsName = new ArrayList<Pair>();
    }
    
    @Override
    public Application createApplication() {
        return this._delegate.createApplication();
    }
    
    @Override
    public Method createMethod(final AbstractResource ar, final AbstractResourceMethod arm) {
        return this._delegate.createMethod(ar, arm);
    }
    
    @Override
    public Request createRequest(final AbstractResource ar, final AbstractResourceMethod arm) {
        return this._delegate.createRequest(ar, arm);
    }
    
    @Override
    public Param createParam(final AbstractResource ar, final AbstractMethod am, final Parameter p) {
        final Param param = this._delegate.createParam(ar, am, p);
        if (p.getSource() == Parameter.Source.ENTITY) {
            this._hasTypeWantsName.add(new Pair(parameter(p), new WantsName() {
                @Override
                public void setName(final QName name) {
                    param.setType(name);
                }
            }));
        }
        return param;
    }
    
    @Override
    public Representation createRequestRepresentation(final AbstractResource ar, final AbstractResourceMethod arm, final MediaType mt) {
        final Representation rt = this._delegate.createRequestRepresentation(ar, arm, mt);
        for (final Parameter p : arm.getParameters()) {
            if (p.getSource() == Parameter.Source.ENTITY) {
                this._hasTypeWantsName.add(new Pair(parameter(p), new WantsName() {
                    @Override
                    public void setName(final QName name) {
                        rt.setElement(name);
                    }
                }));
            }
        }
        return rt;
    }
    
    @Override
    public Resource createResource(final AbstractResource ar, final String path) {
        final Class cls = ar.getResourceClass();
        final XmlSeeAlso seeAlso = cls.getAnnotation(XmlSeeAlso.class);
        if (seeAlso != null) {
            Collections.addAll(this._seeAlso, (Class[])seeAlso.value());
        }
        return this._delegate.createResource(ar, path);
    }
    
    @Override
    public Resources createResources() {
        return this._delegate.createResources();
    }
    
    @Override
    public Response createResponse(final AbstractResource ar, final AbstractResourceMethod arm) {
        final Response response = this._delegate.createResponse(ar, arm);
        if (response != null) {
            final HasType hasType = new HasType() {
                @Override
                public Class getPrimaryClass() {
                    return arm.getReturnType();
                }
                
                @Override
                public Type getType() {
                    return arm.getGenericReturnType();
                }
            };
            for (final Representation representation : response.getRepresentation()) {
                this._hasTypeWantsName.add(new Pair(hasType, new WantsName() {
                    @Override
                    public void setName(final QName name) {
                        representation.setElement(name);
                    }
                }));
            }
        }
        return response;
    }
    
    @Override
    public ExternalGrammarDefinition createExternalGrammar() {
        final Map<String, ApplicationDescription.ExternalGrammar> extraFiles = new HashMap<String, ApplicationDescription.ExternalGrammar>();
        final Resolver resolver = this.buildModelAndSchemas(extraFiles);
        final ExternalGrammarDefinition previous = this._delegate.createExternalGrammar();
        previous.map.putAll(extraFiles);
        if (resolver != null) {
            previous.addResolver(resolver);
        }
        return previous;
    }
    
    private Resolver buildModelAndSchemas(final Map<String, ApplicationDescription.ExternalGrammar> extraFiles) {
        final Set<Class> classSet = new HashSet<Class>(this._seeAlso);
        for (final Pair pair : this._hasTypeWantsName) {
            final HasType hasType = pair.hasType;
            final Class clazz = hasType.getPrimaryClass();
            if (clazz.getAnnotation(XmlRootElement.class) != null) {
                classSet.add(clazz);
            }
            else {
                if (!WadlGeneratorJAXBGrammarGenerator.SPECIAL_GENERIC_TYPES.contains(clazz)) {
                    continue;
                }
                final Type type = hasType.getType();
                if (!(type instanceof ParameterizedType)) {
                    continue;
                }
                final Type parameterType = ((ParameterizedType)type).getActualTypeArguments()[0];
                if (!(parameterType instanceof Class)) {
                    continue;
                }
                classSet.add((Class)parameterType);
            }
        }
        JAXBIntrospector introspector = null;
        try {
            final JAXBContext context = JAXBContext.newInstance((Class[])classSet.toArray(new Class[classSet.size()]));
            final List<StreamResult> results = new ArrayList<StreamResult>();
            context.generateSchema(new SchemaOutputResolver() {
                int counter = 0;
                
                @Override
                public Result createOutput(final String namespaceUri, final String suggestedFileName) {
                    final StreamResult result = new StreamResult(new CharArrayWriter());
                    result.setSystemId("xsd" + this.counter++ + ".xsd");
                    results.add(result);
                    return result;
                }
            });
            for (final StreamResult result : results) {
                final CharArrayWriter writer = (CharArrayWriter)result.getWriter();
                final byte[] contents = writer.toString().getBytes("UTF8");
                extraFiles.put(result.getSystemId(), new ApplicationDescription.ExternalGrammar(MediaType.APPLICATION_XML_TYPE, contents));
            }
            introspector = context.createJAXBIntrospector();
        }
        catch (JAXBException e) {
            WadlGeneratorJAXBGrammarGenerator.LOG.log(Level.SEVERE, "Failed to generate the schema for the JAX-B elements", e);
        }
        catch (IOException e2) {
            WadlGeneratorJAXBGrammarGenerator.LOG.log(Level.SEVERE, "Failed to generate the schema for the JAX-B elements due to an IO error", e2);
        }
        if (introspector != null) {
            final JAXBIntrospector copy = introspector;
            return new Resolver() {
                @Override
                public QName resolve(final Class type) {
                    Object parameterClassInstance = null;
                    try {
                        parameterClassInstance = type.newInstance();
                    }
                    catch (InstantiationException ex) {
                        WadlGeneratorJAXBGrammarGenerator.LOG.log(Level.SEVERE, null, ex);
                    }
                    catch (IllegalAccessException ex2) {
                        WadlGeneratorJAXBGrammarGenerator.LOG.log(Level.SEVERE, null, ex2);
                    }
                    if (parameterClassInstance == null) {
                        return null;
                    }
                    return copy.getElementName(parameterClassInstance);
                }
            };
        }
        return null;
    }
    
    @Override
    public void attachTypes(final ApplicationDescription introspector) {
        if (introspector != null) {
            for (int i = this._hasTypeWantsName.size(), j = 0; j < i; ++j) {
                final Pair pair = this._hasTypeWantsName.get(j);
                final WantsName nextToProcess = pair.wantsName;
                final HasType nextType = pair.hasType;
                Class<?> parameterClass = (Class<?>)nextType.getPrimaryClass();
                if (WadlGeneratorJAXBGrammarGenerator.SPECIAL_GENERIC_TYPES.contains(parameterClass)) {
                    parameterClass = (Class<?>)((ParameterizedType)nextType.getType()).getActualTypeArguments()[0];
                }
                final QName name = introspector.resolve(parameterClass);
                if (name != null) {
                    nextToProcess.setName(name);
                }
                else {
                    WadlGeneratorJAXBGrammarGenerator.LOG.info("Couldn't find JAX-B element for class " + parameterClass.getName());
                }
            }
        }
    }
    
    static {
        LOG = Logger.getLogger(WadlGeneratorJAXBGrammarGenerator.class.getName());
        SPECIAL_GENERIC_TYPES = new HashSet<Class>() {
            {
                ((HashSet<Class<JResponse>>)this).add(JResponse.class);
                ((HashSet<Class<List>>)this).add(List.class);
            }
        };
    }
    
    private class Pair
    {
        HasType hasType;
        WantsName wantsName;
        
        public Pair(final HasType hasType, final WantsName wantsName) {
            this.hasType = hasType;
            this.wantsName = wantsName;
        }
    }
    
    private interface HasType
    {
        Class getPrimaryClass();
        
        Type getType();
    }
    
    private interface WantsName
    {
        void setName(final QName p0);
    }
}
