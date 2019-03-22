// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.core.osgi;

import java.util.ArrayList;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.sun.jersey.spi.service.ServiceConfigurationError;
import com.sun.jersey.impl.SpiMessages;
import java.util.Iterator;
import java.util.HashMap;
import org.osgi.framework.BundleEvent;
import com.sun.jersey.core.spi.scanning.uri.BundleSchemeScanner;
import com.sun.jersey.core.spi.scanning.uri.UriSchemeScanner;
import com.sun.jersey.spi.service.ServiceFinder;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.net.URL;
import java.util.Enumeration;
import com.sun.jersey.core.spi.scanning.PackageNamesScanner;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleListener;
import java.util.logging.Level;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import org.osgi.framework.BundleContext;
import java.util.logging.Logger;
import org.osgi.framework.SynchronousBundleListener;
import org.osgi.framework.BundleActivator;

public class Activator implements BundleActivator, SynchronousBundleListener
{
    private static final Logger LOGGER;
    private BundleContext bundleContext;
    private ConcurrentMap<Long, Map<String, Callable<List<Class>>>> factories;
    
    public Activator() {
        this.factories = new ConcurrentHashMap<Long, Map<String, Callable<List<Class>>>>();
    }
    
    public synchronized void start(final BundleContext bundleContext) throws Exception {
        Activator.LOGGER.log(Level.FINE, "Activating Jersey core bundle...");
        this.bundleContext = bundleContext;
        this.setOSGiPackageScannerResourceProvider();
        this.registerBundleSchemeScanner();
        this.setOSGiServiceFinderIteratorProvider();
        bundleContext.addBundleListener((BundleListener)this);
        this.registerExistingBundles();
        Activator.LOGGER.log(Level.FINE, "Jersey core bundle activated");
    }
    
    private void registerExistingBundles() {
        for (final Bundle bundle : this.bundleContext.getBundles()) {
            if (bundle.getState() == 4 || bundle.getState() == 8 || bundle.getState() == 32 || bundle.getState() == 16) {
                this.register(bundle);
            }
        }
    }
    
    private void setOSGiPackageScannerResourceProvider() {
        PackageNamesScanner.setResourcesProvider(new PackageNamesScanner.ResourcesProvider() {
            @Override
            public Enumeration<URL> getResources(final String name, final ClassLoader cl) throws IOException {
                final List<URL> result = new LinkedList<URL>();
                for (final Bundle b : Activator.this.bundleContext.getBundles()) {
                    final Enumeration<URL> e = (Enumeration<URL>)b.findEntries(name, "*", false);
                    if (e != null) {
                        result.addAll(Collections.list(e));
                    }
                }
                return Collections.enumeration(result);
            }
        });
    }
    
    private void setOSGiServiceFinderIteratorProvider() {
        ServiceFinder.setIteratorProvider(new OsgiServiceFinder());
    }
    
    private void registerBundleSchemeScanner() {
        OsgiLocator.register(UriSchemeScanner.class.getName(), new Callable<List<Class>>() {
            @Override
            public List<Class> call() throws Exception {
                final List<Class> result = new LinkedList<Class>();
                result.add(BundleSchemeScanner.class);
                return result;
            }
        });
    }
    
    public synchronized void stop(final BundleContext bundleContext) throws Exception {
        Activator.LOGGER.log(Level.FINE, "Deactivating Jersey core bundle...");
        bundleContext.removeBundleListener((BundleListener)this);
        while (!this.factories.isEmpty()) {
            this.unregister(this.factories.keySet().iterator().next());
        }
        Activator.LOGGER.log(Level.FINE, "Jersey core bundle deactivated");
        this.bundleContext = null;
    }
    
    public void bundleChanged(final BundleEvent event) {
        if (event.getType() == 32) {
            this.register(event.getBundle());
        }
        else if (event.getType() == 64 || event.getType() == 16) {
            this.unregister(event.getBundle().getBundleId());
        }
    }
    
    protected void register(final Bundle bundle) {
        if (Activator.LOGGER.isLoggable(Level.FINEST)) {
            Activator.LOGGER.log(Level.FINEST, "checking bundle " + bundle.getBundleId());
        }
        Map<String, Callable<List<Class>>> map = this.factories.get(bundle.getBundleId());
        final Enumeration e = bundle.findEntries("META-INF/services/", "*", false);
        if (e != null) {
            while (e.hasMoreElements()) {
                final URL u = e.nextElement();
                final String url = u.toString();
                if (url.endsWith("/")) {
                    continue;
                }
                final String factoryId = url.substring(url.lastIndexOf("/") + 1);
                if (map == null) {
                    map = new HashMap<String, Callable<List<Class>>>();
                    this.factories.put(bundle.getBundleId(), map);
                }
                map.put(factoryId, new BundleFactoryLoader(factoryId, u, bundle));
            }
        }
        if (map != null) {
            for (final Map.Entry<String, Callable<List<Class>>> entry : map.entrySet()) {
                if (Activator.LOGGER.isLoggable(Level.FINEST)) {
                    Activator.LOGGER.log(Level.FINEST, "registering service for key " + entry.getKey() + "with value " + entry.getValue());
                }
                OsgiLocator.register(entry.getKey(), entry.getValue());
            }
        }
    }
    
    protected void unregister(final long bundleId) {
        final Map<String, Callable<List<Class>>> map = this.factories.remove(bundleId);
        if (map != null) {
            for (final Map.Entry<String, Callable<List<Class>>> entry : map.entrySet()) {
                if (Activator.LOGGER.isLoggable(Level.FINEST)) {
                    Activator.LOGGER.log(Level.FINEST, "unregistering service for key " + entry.getKey() + "with value " + entry.getValue());
                }
                OsgiLocator.unregister(entry.getKey(), entry.getValue());
            }
        }
    }
    
    static {
        LOGGER = Logger.getLogger(Activator.class.getName());
    }
    
    private static final class OsgiServiceFinder<T> extends ServiceFinder.ServiceIteratorProvider<T>
    {
        static final ServiceFinder.ServiceIteratorProvider defaultIterator;
        
        @Override
        public Iterator<T> createIterator(final Class<T> serviceClass, final String serviceName, final ClassLoader loader, final boolean ignoreOnClassNotFound) {
            final List<Class> providerClasses = OsgiLocator.locateAll(serviceName);
            if (!providerClasses.isEmpty()) {
                return new Iterator<T>() {
                    Iterator<Class> it = providerClasses.iterator();
                    
                    @Override
                    public boolean hasNext() {
                        return this.it.hasNext();
                    }
                    
                    @Override
                    public T next() {
                        final Class<T> nextClass = this.it.next();
                        try {
                            return serviceClass.cast(nextClass.newInstance());
                        }
                        catch (Exception ex) {
                            final ServiceConfigurationError sce = new ServiceConfigurationError(serviceName + ": " + SpiMessages.PROVIDER_COULD_NOT_BE_CREATED(nextClass.getName(), serviceClass, ex.getLocalizedMessage()));
                            sce.initCause(ex);
                            throw sce;
                        }
                    }
                    
                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
            return OsgiServiceFinder.defaultIterator.createIterator(serviceClass, serviceName, loader, ignoreOnClassNotFound);
        }
        
        @Override
        public Iterator<Class<T>> createClassIterator(final Class<T> service, final String serviceName, final ClassLoader loader, final boolean ignoreOnClassNotFound) {
            final List<Class> providerClasses = OsgiLocator.locateAll(serviceName);
            if (!providerClasses.isEmpty()) {
                return new Iterator<Class<T>>() {
                    Iterator<Class> it = providerClasses.iterator();
                    
                    @Override
                    public boolean hasNext() {
                        return this.it.hasNext();
                    }
                    
                    @Override
                    public Class<T> next() {
                        return this.it.next();
                    }
                    
                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
            return OsgiServiceFinder.defaultIterator.createClassIterator(service, serviceName, loader, ignoreOnClassNotFound);
        }
        
        static {
            defaultIterator = new ServiceFinder.DefaultServiceIteratorProvider();
        }
    }
    
    private class BundleFactoryLoader implements Callable<List<Class>>
    {
        private final String factoryId;
        private final URL u;
        private final Bundle bundle;
        
        public BundleFactoryLoader(final String factoryId, final URL u, final Bundle bundle) {
            this.factoryId = factoryId;
            this.u = u;
            this.bundle = bundle;
        }
        
        @Override
        public List<Class> call() throws Exception {
            try {
                if (Activator.LOGGER.isLoggable(Level.FINEST)) {
                    Activator.LOGGER.log(Level.FINEST, "creating factories for key: " + this.factoryId);
                }
                final BufferedReader br = new BufferedReader(new InputStreamReader(this.u.openStream(), "UTF-8"));
                final List<Class> factoryClasses = new ArrayList<Class>();
                String factoryClassName;
                while ((factoryClassName = br.readLine()) != null) {
                    if (factoryClassName.trim().length() == 0) {
                        continue;
                    }
                    if (Activator.LOGGER.isLoggable(Level.FINEST)) {
                        Activator.LOGGER.log(Level.FINEST, "factory implementation: " + factoryClassName);
                    }
                    factoryClasses.add(this.bundle.loadClass(factoryClassName));
                }
                br.close();
                return factoryClasses;
            }
            catch (Exception e) {
                Activator.LOGGER.log(Level.WARNING, "exception caught while creating factories: " + e);
                throw e;
            }
            catch (Error e2) {
                Activator.LOGGER.log(Level.WARNING, "error caught while creating factories: " + e2);
                throw e2;
            }
        }
        
        @Override
        public String toString() {
            return this.u.toString();
        }
        
        @Override
        public int hashCode() {
            return this.u.hashCode();
        }
        
        @Override
        public boolean equals(final Object obj) {
            return obj instanceof BundleFactoryLoader && this.u.equals(((BundleFactoryLoader)obj).u);
        }
    }
}
