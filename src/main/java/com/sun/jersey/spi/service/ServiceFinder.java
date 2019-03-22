// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.spi.service;

import java.security.Permission;
import java.lang.reflect.ReflectPermission;
import java.util.NoSuchElementException;
import com.sun.jersey.core.reflection.ReflectionHelper;
import java.util.TreeSet;
import java.util.HashMap;
import java.net.URLConnection;
import com.sun.jersey.impl.SpiMessages;
import java.util.Set;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.InputStream;
import java.util.jar.Manifest;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.jar.Attributes;
import java.util.ListIterator;
import java.util.List;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.io.IOException;
import java.util.logging.Level;
import java.net.URL;
import java.util.Map;
import java.util.logging.Logger;

public final class ServiceFinder<T> implements Iterable<T>
{
    private static final Logger LOGGER;
    private static final String MANIFEST = "META-INF/MANIFEST.MF";
    private static final String MODULE_VERSION = "META-INF/jersey-module-version";
    private static final String PREFIX = "META-INF/services/";
    private static final String BUNDLE_VERSION_ATTRIBUTE = "Bundle-Version";
    private static final String BUNDLE_SYMBOLIC_NAME_ATTRIBUTE = "Bundle-SymbolicName";
    private static final String BUNDLE_VERSION;
    private static final String BUNDLE_SYMBOLIC_NAME;
    private static final String MODULE_VERSION_VALUE;
    private final Class<T> serviceClass;
    private final String serviceName;
    private final ClassLoader classLoader;
    private final boolean ignoreOnClassNotFound;
    private static final Map<URL, Boolean> manifestURLs;
    
    private static String getBundleAttribute(final String attributeName) {
        try {
            final String version = getManifest(ServiceFinder.class).getMainAttributes().getValue(attributeName);
            if (ServiceFinder.LOGGER.isLoggable(Level.FINE)) {
                ServiceFinder.LOGGER.fine("ServiceFinder " + attributeName + ": " + version);
            }
            return version;
        }
        catch (IOException ex) {
            ServiceFinder.LOGGER.log(Level.FINE, "Error loading META-INF/MANIFEST.MF associated with " + ServiceFinder.class.getName(), ex);
            return null;
        }
    }
    
    private static String getModuleVersion() {
        try {
            final String resource = ServiceFinder.class.getName().replace(".", "/") + ".class";
            final URL url = getResource(ServiceFinder.class.getClassLoader(), resource);
            if (url == null) {
                ServiceFinder.LOGGER.log(Level.FINE, "Error getting " + ServiceFinder.class.getName() + " class as a resource");
                return null;
            }
            return getJerseyModuleVersion(getManifestURL(resource, url));
        }
        catch (IOException ioe) {
            ServiceFinder.LOGGER.log(Level.FINE, "Error loading META-INF/jersey-module-version associated with " + ServiceFinder.class.getName(), ioe);
            return null;
        }
    }
    
    private static Enumeration<URL> filterServiceURLsWithVersion(final String serviceName, final Enumeration<URL> serviceUrls) {
        if (ServiceFinder.BUNDLE_VERSION == null || !serviceUrls.hasMoreElements()) {
            return serviceUrls;
        }
        final List<URL> urls = Collections.list(serviceUrls);
        final ListIterator<URL> li = urls.listIterator();
        while (li.hasNext()) {
            final URL url = li.next();
            try {
                final URL manifestURL = getManifestURL(serviceName, url);
                synchronized (ServiceFinder.manifestURLs) {
                    final Boolean keep = ServiceFinder.manifestURLs.get(manifestURL);
                    if (keep != null) {
                        if (!keep) {
                            if (ServiceFinder.LOGGER.isLoggable(Level.CONFIG)) {
                                ServiceFinder.LOGGER.config("Ignoring service URL: " + url);
                            }
                            li.remove();
                        }
                        else {
                            if (!ServiceFinder.LOGGER.isLoggable(Level.FINE)) {
                                continue;
                            }
                            ServiceFinder.LOGGER.fine("Including service URL: " + url);
                        }
                    }
                    else if (!compatibleManifest(manifestURL)) {
                        if (ServiceFinder.LOGGER.isLoggable(Level.CONFIG)) {
                            ServiceFinder.LOGGER.config("Ignoring service URL: " + url);
                        }
                        li.remove();
                        ServiceFinder.manifestURLs.put(manifestURL, false);
                    }
                    else {
                        if (ServiceFinder.LOGGER.isLoggable(Level.FINE)) {
                            ServiceFinder.LOGGER.fine("Including service URL: " + url);
                        }
                        ServiceFinder.manifestURLs.put(manifestURL, true);
                    }
                }
            }
            catch (IOException ex) {
                ServiceFinder.LOGGER.log(Level.FINE, "Error loading META-INF/MANIFEST.MF associated with " + url, ex);
            }
        }
        return Collections.enumeration(urls);
    }
    
    private static boolean compatibleManifest(final URL manifestURL) throws IOException {
        final Attributes as = getManifest(manifestURL).getMainAttributes();
        final String symbolicName = as.getValue("Bundle-SymbolicName");
        final String version = as.getValue("Bundle-Version");
        if (ServiceFinder.LOGGER.isLoggable(Level.FINE)) {
            ServiceFinder.LOGGER.fine("Checking META-INF/MANIFEST.MF URL: " + manifestURL + "\n  " + "Bundle-SymbolicName" + ": " + symbolicName + "\n  " + "Bundle-Version" + ": " + version);
        }
        if (symbolicName != null && symbolicName.startsWith("com.sun.jersey") && !ServiceFinder.BUNDLE_VERSION.equals(version)) {
            return false;
        }
        final String moduleVersion = getJerseyModuleVersion(manifestURL);
        return moduleVersion == null || (moduleVersion.equals(ServiceFinder.MODULE_VERSION_VALUE) && (symbolicName == null || !(ServiceFinder.BUNDLE_SYMBOLIC_NAME.startsWith("com.sun.jersey") ^ symbolicName.startsWith("com.sun.jersey"))));
    }
    
    private static String getJerseyModuleVersion(final URL manifestURL) {
        try {
            final URL moduleVersionURL = new URL(manifestURL.toString().replace("META-INF/MANIFEST.MF", "META-INF/jersey-module-version"));
            return new BufferedReader(new InputStreamReader(moduleVersionURL.openStream())).readLine();
        }
        catch (IOException ioe) {
            ServiceFinder.LOGGER.log(Level.FINE, "Error loading META-INF/jersey-module-version associated with " + ServiceFinder.class.getName(), ioe);
            return null;
        }
    }
    
    private static Manifest getManifest(final Class c) throws IOException {
        final String resource = c.getName().replace(".", "/") + ".class";
        final URL url = getResource(c.getClassLoader(), resource);
        if (url == null) {
            throw new IOException("Resource not found: " + url);
        }
        return getManifest(resource, url);
    }
    
    private static Manifest getManifest(final String name, final URL serviceURL) throws IOException {
        return getManifest(getManifestURL(name, serviceURL));
    }
    
    private static URL getManifestURL(final String name, final URL serviceURL) throws IOException {
        return new URL(serviceURL.toString().replace(name, "META-INF/MANIFEST.MF"));
    }
    
    private static Manifest getManifest(final URL url) throws IOException {
        final InputStream in = url.openStream();
        try {
            return new Manifest(in);
        }
        finally {
            in.close();
        }
    }
    
    private static URL getResource(final ClassLoader loader, final String name) throws IOException {
        if (loader == null) {
            return getResource(name);
        }
        final URL resource = loader.getResource(name);
        if (resource != null) {
            return resource;
        }
        return getResource(name);
    }
    
    private static URL getResource(final String name) throws IOException {
        if (ServiceFinder.class.getClassLoader() != null) {
            return ServiceFinder.class.getClassLoader().getResource(name);
        }
        return ClassLoader.getSystemResource(name);
    }
    
    private static Enumeration<URL> getResources(final ClassLoader loader, final String name) throws IOException {
        if (loader == null) {
            return getResources(name);
        }
        final Enumeration<URL> resources = loader.getResources(name);
        if (resources.hasMoreElements()) {
            return resources;
        }
        return getResources(name);
    }
    
    private static Enumeration<URL> getResources(final String name) throws IOException {
        if (ServiceFinder.class.getClassLoader() != null) {
            return ServiceFinder.class.getClassLoader().getResources(name);
        }
        return ClassLoader.getSystemResources(name);
    }
    
    public static <T> ServiceFinder<T> find(final Class<T> service, final ClassLoader loader) throws ServiceConfigurationError {
        return find(service, loader, false);
    }
    
    public static <T> ServiceFinder<T> find(final Class<T> service, final ClassLoader loader, final boolean ignoreOnClassNotFound) throws ServiceConfigurationError {
        return new ServiceFinder<T>(service, loader, ignoreOnClassNotFound);
    }
    
    public static <T> ServiceFinder<T> find(final Class<T> service) throws ServiceConfigurationError {
        return find(service, Thread.currentThread().getContextClassLoader(), false);
    }
    
    public static <T> ServiceFinder<T> find(final Class<T> service, final boolean ignoreOnClassNotFound) throws ServiceConfigurationError {
        return find(service, Thread.currentThread().getContextClassLoader(), ignoreOnClassNotFound);
    }
    
    public static ServiceFinder<?> find(final String serviceName) throws ServiceConfigurationError {
        return new ServiceFinder<Object>(Object.class, serviceName, Thread.currentThread().getContextClassLoader(), false);
    }
    
    public static void setIteratorProvider(final ServiceIteratorProvider sip) throws SecurityException {
        setInstance(sip);
    }
    
    private ServiceFinder(final Class<T> service, final ClassLoader loader, final boolean ignoreOnClassNotFound) {
        this(service, service.getName(), loader, ignoreOnClassNotFound);
    }
    
    private ServiceFinder(final Class<T> service, final String serviceName, final ClassLoader loader, final boolean ignoreOnClassNotFound) {
        this.serviceClass = service;
        this.serviceName = serviceName;
        this.classLoader = loader;
        this.ignoreOnClassNotFound = ignoreOnClassNotFound;
    }
    
    @Override
    public Iterator<T> iterator() {
        return (Iterator<T>)getInstance().createIterator(this.serviceClass, this.serviceName, this.classLoader, this.ignoreOnClassNotFound);
    }
    
    private Iterator<Class<T>> classIterator() {
        return (Iterator<Class<T>>)getInstance().createClassIterator(this.serviceClass, this.serviceName, this.classLoader, this.ignoreOnClassNotFound);
    }
    
    public T[] toArray() throws ServiceConfigurationError {
        final List<T> result = new ArrayList<T>();
        for (final T t : this) {
            result.add(t);
        }
        return result.toArray((T[])Array.newInstance(this.serviceClass, result.size()));
    }
    
    public Class<T>[] toClassArray() throws ServiceConfigurationError {
        final List<Class<T>> result = new ArrayList<Class<T>>();
        final Iterator<Class<T>> i = this.classIterator();
        while (i.hasNext()) {
            result.add(i.next());
        }
        return result.toArray((Class<T>[])Array.newInstance(Class.class, result.size()));
    }
    
    private static void fail(final String serviceName, final String msg, final Throwable cause) throws ServiceConfigurationError {
        final ServiceConfigurationError sce = new ServiceConfigurationError(serviceName + ": " + msg);
        sce.initCause(cause);
        throw sce;
    }
    
    private static void fail(final String serviceName, final String msg) throws ServiceConfigurationError {
        throw new ServiceConfigurationError(serviceName + ": " + msg);
    }
    
    private static void fail(final String serviceName, final URL u, final int line, final String msg) throws ServiceConfigurationError {
        fail(serviceName, u + ":" + line + ": " + msg);
    }
    
    private static int parseLine(final String serviceName, final URL u, final BufferedReader r, final int lc, final List<String> names, final Set<String> returned) throws IOException, ServiceConfigurationError {
        String ln = r.readLine();
        if (ln == null) {
            return -1;
        }
        final int ci = ln.indexOf(35);
        if (ci >= 0) {
            ln = ln.substring(0, ci);
        }
        ln = ln.trim();
        final int n = ln.length();
        if (n != 0) {
            if (ln.indexOf(32) >= 0 || ln.indexOf(9) >= 0) {
                fail(serviceName, u, lc, SpiMessages.ILLEGAL_CONFIG_SYNTAX());
            }
            int cp = ln.codePointAt(0);
            if (!Character.isJavaIdentifierStart(cp)) {
                fail(serviceName, u, lc, SpiMessages.ILLEGAL_PROVIDER_CLASS_NAME(ln));
            }
            for (int i = Character.charCount(cp); i < n; i += Character.charCount(cp)) {
                cp = ln.codePointAt(i);
                if (!Character.isJavaIdentifierPart(cp) && cp != 46) {
                    fail(serviceName, u, lc, SpiMessages.ILLEGAL_PROVIDER_CLASS_NAME(ln));
                }
            }
            if (!returned.contains(ln)) {
                names.add(ln);
                returned.add(ln);
            }
        }
        return lc + 1;
    }
    
    private static Iterator<String> parse(final String serviceName, final URL u, final Set<String> returned) throws ServiceConfigurationError {
        InputStream in = null;
        BufferedReader r = null;
        final ArrayList<String> names = new ArrayList<String>();
        try {
            final URLConnection uConn = u.openConnection();
            uConn.setUseCaches(false);
            in = uConn.getInputStream();
            r = new BufferedReader(new InputStreamReader(in, "utf-8"));
            int lc = 1;
            while ((lc = parseLine(serviceName, u, r, lc, names, returned)) >= 0) {}
        }
        catch (IOException x) {
            fail(serviceName, ": " + x);
            try {
                if (r != null) {
                    r.close();
                }
                if (in != null) {
                    in.close();
                }
            }
            catch (IOException y) {
                fail(serviceName, ": " + y);
            }
        }
        finally {
            try {
                if (r != null) {
                    r.close();
                }
                if (in != null) {
                    in.close();
                }
            }
            catch (IOException y2) {
                fail(serviceName, ": " + y2);
            }
        }
        return names.iterator();
    }
    
    static {
        LOGGER = Logger.getLogger(ServiceFinder.class.getName());
        BUNDLE_VERSION = getBundleAttribute("Bundle-Version");
        BUNDLE_SYMBOLIC_NAME = getBundleAttribute("Bundle-SymbolicName");
        MODULE_VERSION_VALUE = getModuleVersion();
        manifestURLs = new HashMap<URL, Boolean>();
    }
    
    private static class AbstractLazyIterator<T>
    {
        final Class<T> service;
        final String serviceName;
        final ClassLoader loader;
        final boolean ignoreOnClassNotFound;
        Enumeration<URL> configs;
        Iterator<String> pending;
        Set<String> returned;
        String nextName;
        
        private AbstractLazyIterator(final Class<T> service, final String serviceName, final ClassLoader loader, final boolean ignoreOnClassNotFound) {
            this.configs = null;
            this.pending = null;
            this.returned = new TreeSet<String>();
            this.nextName = null;
            this.service = service;
            this.serviceName = serviceName;
            this.loader = loader;
            this.ignoreOnClassNotFound = ignoreOnClassNotFound;
        }
        
        protected final void setConfigs() {
            if (this.configs == null) {
                try {
                    final String fullName = "META-INF/services/" + this.serviceName;
                    this.configs = filterServiceURLsWithVersion(fullName, getResources(this.loader, fullName));
                }
                catch (IOException x) {
                    fail(this.serviceName, ": " + x);
                }
            }
        }
        
        public boolean hasNext() throws ServiceConfigurationError {
            if (this.nextName != null) {
                return true;
            }
            this.setConfigs();
            while (this.nextName == null) {
                while (this.pending == null || !this.pending.hasNext()) {
                    if (!this.configs.hasMoreElements()) {
                        return false;
                    }
                    this.pending = parse(this.serviceName, this.configs.nextElement(), this.returned);
                }
                this.nextName = this.pending.next();
                if (this.ignoreOnClassNotFound) {
                    try {
                        ReflectionHelper.classForNameWithException(this.nextName, this.loader);
                    }
                    catch (ClassNotFoundException ex3) {
                        if (ServiceFinder.LOGGER.isLoggable(Level.CONFIG)) {
                            ServiceFinder.LOGGER.log(Level.CONFIG, SpiMessages.PROVIDER_NOT_FOUND(this.nextName, this.service));
                        }
                        this.nextName = null;
                    }
                    catch (NoClassDefFoundError ex) {
                        if (ServiceFinder.LOGGER.isLoggable(Level.CONFIG)) {
                            ServiceFinder.LOGGER.log(Level.CONFIG, SpiMessages.DEPENDENT_CLASS_OF_PROVIDER_NOT_FOUND(ex.getLocalizedMessage(), this.nextName, this.service));
                        }
                        this.nextName = null;
                    }
                    catch (ClassFormatError ex2) {
                        if (ServiceFinder.LOGGER.isLoggable(Level.CONFIG)) {
                            ServiceFinder.LOGGER.log(Level.CONFIG, SpiMessages.DEPENDENT_CLASS_OF_PROVIDER_FORMAT_ERROR(ex2.getLocalizedMessage(), this.nextName, this.service));
                        }
                        this.nextName = null;
                    }
                }
            }
            return true;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
    private static final class LazyClassIterator<T> extends AbstractLazyIterator<T> implements Iterator<Class<T>>
    {
        private LazyClassIterator(final Class<T> service, final String serviceName, final ClassLoader loader, final boolean ignoreOnClassNotFound) {
            super((Class)service, serviceName, loader, ignoreOnClassNotFound);
        }
        
        @Override
        public Class<T> next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            final String cn = this.nextName;
            this.nextName = null;
            try {
                return (Class<T>)ReflectionHelper.classForNameWithException(cn, this.loader);
            }
            catch (ClassNotFoundException ex3) {
                fail(this.serviceName, SpiMessages.PROVIDER_NOT_FOUND(cn, this.service));
            }
            catch (NoClassDefFoundError ex) {
                fail(this.serviceName, SpiMessages.DEPENDENT_CLASS_OF_PROVIDER_NOT_FOUND(ex.getLocalizedMessage(), cn, this.service));
            }
            catch (ClassFormatError ex2) {
                fail(this.serviceName, SpiMessages.DEPENDENT_CLASS_OF_PROVIDER_FORMAT_ERROR(ex2.getLocalizedMessage(), cn, this.service));
            }
            catch (Exception x) {
                fail(this.serviceName, SpiMessages.PROVIDER_CLASS_COULD_NOT_BE_LOADED(cn, this.service, x.getLocalizedMessage()), x);
            }
            return null;
        }
    }
    
    private static final class LazyObjectIterator<T> extends AbstractLazyIterator<T> implements Iterator<T>
    {
        private T t;
        
        private LazyObjectIterator(final Class<T> service, final String serviceName, final ClassLoader loader, final boolean ignoreOnClassNotFound) {
            super((Class)service, serviceName, loader, ignoreOnClassNotFound);
        }
        
        @Override
        public boolean hasNext() throws ServiceConfigurationError {
            if (this.nextName != null) {
                return true;
            }
            this.setConfigs();
            while (this.nextName == null) {
                while (this.pending == null || !this.pending.hasNext()) {
                    if (!this.configs.hasMoreElements()) {
                        return false;
                    }
                    this.pending = parse(this.serviceName, this.configs.nextElement(), this.returned);
                }
                this.nextName = this.pending.next();
                try {
                    this.t = this.service.cast(ReflectionHelper.classForNameWithException(this.nextName, this.loader).newInstance());
                }
                catch (ClassNotFoundException ex4) {
                    if (this.ignoreOnClassNotFound) {
                        if (ServiceFinder.LOGGER.isLoggable(Level.WARNING)) {
                            ServiceFinder.LOGGER.log(Level.WARNING, SpiMessages.PROVIDER_NOT_FOUND(this.nextName, this.service));
                        }
                        this.nextName = null;
                    }
                    else {
                        fail(this.serviceName, SpiMessages.PROVIDER_NOT_FOUND(this.nextName, this.service));
                    }
                }
                catch (NoClassDefFoundError ex) {
                    if (this.ignoreOnClassNotFound) {
                        if (ServiceFinder.LOGGER.isLoggable(Level.CONFIG)) {
                            ServiceFinder.LOGGER.log(Level.CONFIG, SpiMessages.DEPENDENT_CLASS_OF_PROVIDER_NOT_FOUND(ex.getLocalizedMessage(), this.nextName, this.service));
                        }
                        this.nextName = null;
                    }
                    else {
                        fail(this.serviceName, SpiMessages.DEPENDENT_CLASS_OF_PROVIDER_NOT_FOUND(ex.getLocalizedMessage(), this.nextName, this.service), ex);
                    }
                }
                catch (ClassFormatError ex2) {
                    if (this.ignoreOnClassNotFound) {
                        if (ServiceFinder.LOGGER.isLoggable(Level.CONFIG)) {
                            ServiceFinder.LOGGER.log(Level.CONFIG, SpiMessages.DEPENDENT_CLASS_OF_PROVIDER_FORMAT_ERROR(ex2.getLocalizedMessage(), this.nextName, this.service));
                        }
                        this.nextName = null;
                    }
                    else {
                        fail(this.serviceName, SpiMessages.DEPENDENT_CLASS_OF_PROVIDER_FORMAT_ERROR(ex2.getLocalizedMessage(), this.nextName, this.service), ex2);
                    }
                }
                catch (Exception ex3) {
                    fail(this.serviceName, SpiMessages.PROVIDER_COULD_NOT_BE_CREATED(this.nextName, this.service, ex3.getLocalizedMessage()), ex3);
                }
            }
            return true;
        }
        
        @Override
        public T next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            final String cn = this.nextName;
            this.nextName = null;
            return this.t;
        }
    }
    
    public abstract static class ServiceIteratorProvider<T>
    {
        private static volatile ServiceIteratorProvider sip;
        
        private static ServiceIteratorProvider getInstance() {
            ServiceIteratorProvider result = ServiceIteratorProvider.sip;
            if (result == null) {
                synchronized (ServiceIteratorProvider.class) {
                    result = ServiceIteratorProvider.sip;
                    if (result == null) {
                        result = (ServiceIteratorProvider.sip = new DefaultServiceIteratorProvider());
                    }
                }
            }
            return result;
        }
        
        private static void setInstance(final ServiceIteratorProvider sip) throws SecurityException {
            final SecurityManager security = System.getSecurityManager();
            if (security != null) {
                final ReflectPermission rp = new ReflectPermission("suppressAccessChecks");
                security.checkPermission(rp);
            }
            synchronized (ServiceIteratorProvider.class) {
                ServiceIteratorProvider.sip = sip;
            }
        }
        
        public abstract Iterator<T> createIterator(final Class<T> p0, final String p1, final ClassLoader p2, final boolean p3);
        
        public abstract Iterator<Class<T>> createClassIterator(final Class<T> p0, final String p1, final ClassLoader p2, final boolean p3);
    }
    
    public static final class DefaultServiceIteratorProvider<T> extends ServiceIteratorProvider<T>
    {
        @Override
        public Iterator<T> createIterator(final Class<T> service, final String serviceName, final ClassLoader loader, final boolean ignoreOnClassNotFound) {
            return new LazyObjectIterator<T>((Class)service, serviceName, loader, ignoreOnClassNotFound);
        }
        
        @Override
        public Iterator<Class<T>> createClassIterator(final Class<T> service, final String serviceName, final ClassLoader loader, final boolean ignoreOnClassNotFound) {
            return new LazyClassIterator<T>((Class)service, serviceName, loader, ignoreOnClassNotFound);
        }
    }
}
