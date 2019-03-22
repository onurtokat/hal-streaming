// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.server.impl.wadl;

import com.sun.jersey.server.impl.uri.PathPattern;
import com.sun.jersey.server.impl.model.method.ResourceMethod;
import java.util.List;
import java.util.Map;
import com.sun.jersey.spi.inject.InjectableProvider;
import java.lang.reflect.Type;
import javax.ws.rs.core.Context;
import com.sun.jersey.spi.inject.SingletonTypeInjectableProvider;
import com.sun.jersey.core.spi.factory.InjectableProviderFactory;
import com.sun.jersey.api.model.AbstractResource;
import java.util.Set;
import com.sun.jersey.server.wadl.WadlApplicationContext;
import com.sun.jersey.api.core.ResourceConfig;
import java.util.logging.Logger;

public final class WadlFactory
{
    private static final Logger LOGGER;
    private final boolean isWadlEnabled;
    private final ResourceConfig _resourceConfig;
    private WadlApplicationContext wadlApplicationContext;
    
    public WadlFactory(final ResourceConfig resourceConfig) {
        this.isWadlEnabled = isWadlEnabled(resourceConfig);
        this._resourceConfig = resourceConfig;
    }
    
    public boolean isSupported() {
        return this.isWadlEnabled;
    }
    
    public WadlApplicationContext createWadlApplicationContext(final Set<AbstractResource> rootResources) {
        if (!this.isSupported()) {
            return null;
        }
        return new WadlApplicationContextImpl(rootResources, this._resourceConfig);
    }
    
    public void init(final InjectableProviderFactory ipf, final Set<AbstractResource> rootResources) {
        if (!this.isSupported()) {
            return;
        }
        this.wadlApplicationContext = new WadlApplicationContextImpl(rootResources, this._resourceConfig);
        ipf.add(new SingletonTypeInjectableProvider<Context, WadlApplicationContext>(WadlApplicationContext.class, this.wadlApplicationContext) {});
    }
    
    public ResourceMethod createWadlOptionsMethod(final Map<String, List<ResourceMethod>> methods, final AbstractResource resource, final PathPattern p) {
        if (!this.isSupported()) {
            return null;
        }
        if (p == null) {
            return new WadlMethodFactory.WadlOptionsMethod(methods, resource, null, this.wadlApplicationContext);
        }
        final String path = p.getTemplate().getTemplate().substring(1);
        return new WadlMethodFactory.WadlOptionsMethod(methods, resource, path, this.wadlApplicationContext);
    }
    
    private static boolean isWadlEnabled(final ResourceConfig resourceConfig) {
        return !resourceConfig.getFeature("com.sun.jersey.config.feature.DisableWADL");
    }
    
    static {
        LOGGER = Logger.getLogger(WadlFactory.class.getName());
    }
}
