// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.server.osgi;

import javax.ws.rs.ext.RuntimeDelegate;
import com.sun.jersey.server.impl.provider.RuntimeDelegateImpl;
import org.osgi.framework.BundleContext;
import java.util.logging.Logger;
import org.osgi.framework.BundleActivator;

public class Activator implements BundleActivator
{
    private static final Logger LOGGER;
    
    public void start(final BundleContext bc) throws Exception {
        Activator.LOGGER.config("jersey-server bundle activator registers JAX-RS RuntimeDelegate instance");
        RuntimeDelegate.setInstance(new RuntimeDelegateImpl());
    }
    
    public void stop(final BundleContext bc) throws Exception {
    }
    
    static {
        LOGGER = Logger.getLogger(Activator.class.getName());
    }
}
