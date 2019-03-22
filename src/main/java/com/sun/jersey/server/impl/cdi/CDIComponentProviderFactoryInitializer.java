// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.server.impl.cdi;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.logging.Level;
import com.sun.jersey.server.impl.InitialContextHelper;
import com.sun.jersey.spi.container.WebApplication;
import com.sun.jersey.api.core.ResourceConfig;
import java.util.logging.Logger;

public class CDIComponentProviderFactoryInitializer
{
    private static final Logger LOGGER;
    
    public static void initialize(final ResourceConfig rc, final WebApplication wa) {
        try {
            final InitialContext ic = InitialContextHelper.getInitialContext();
            if (ic == null) {
                return;
            }
            final Object beanManager = ic.lookup("java:comp/BeanManager");
            if (beanManager == null) {
                CDIComponentProviderFactoryInitializer.LOGGER.config("The CDI BeanManager is not available. JAX-RS CDI support is disabled.");
                return;
            }
            rc.getSingletons().add(new CDIComponentProviderFactory(beanManager, rc, wa));
            CDIComponentProviderFactoryInitializer.LOGGER.info("CDI support is enabled");
        }
        catch (NamingException ex) {
            CDIComponentProviderFactoryInitializer.LOGGER.log(Level.CONFIG, "The CDI BeanManager is not available. JAX-RS CDI support is disabled.", ex);
        }
    }
    
    static {
        LOGGER = Logger.getLogger(CDIComponentProviderFactoryInitializer.class.getName());
    }
}
