// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.api.wadl;

import java.util.List;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Set;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.server.wadl.WadlBuilder;
import com.sun.jersey.server.impl.modelapi.annotation.IntrospectionModeller;
import com.sun.jersey.api.model.AbstractResource;
import java.util.HashSet;
import com.sun.jersey.api.core.ClasspathResourceConfig;
import javax.xml.bind.Marshaller;
import java.util.Iterator;
import com.sun.research.ws.wadl.Application;
import com.sun.jersey.server.wadl.ApplicationDescription;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import javax.xml.bind.JAXBContext;
import com.sun.research.ws.wadl.Resources;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.Reference;
import java.io.File;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.Task;

public class WadlGeneratorTask extends Task
{
    private Path classpath;
    private File wadlFile;
    private String baseUri;
    
    public Path getClasspath() {
        return this.classpath;
    }
    
    public void setClasspath(final Path classpath) {
        if (this.classpath == null) {
            this.classpath = classpath;
        }
        else {
            this.classpath.append(classpath);
        }
    }
    
    public Path createClasspath() {
        if (this.classpath == null) {
            this.classpath = new Path(this.getProject());
        }
        return this.classpath.createPath();
    }
    
    public void setClasspathRef(final Reference r) {
        this.createClasspath().setRefid(r);
    }
    
    public File getDestfile() {
        return this.wadlFile;
    }
    
    public void setDestfile(final File wadlFile) {
        this.wadlFile = wadlFile;
    }
    
    public String getbaseUri() {
        return this.baseUri;
    }
    
    public void setBaseUri(final String baseUri) {
        this.baseUri = baseUri;
    }
    
    public void execute() throws BuildException {
        if (this.classpath == null) {
            throw new BuildException("The classpath is not defined");
        }
        if (this.wadlFile == null) {
            throw new BuildException("destfile attribute required", this.getLocation());
        }
        if (this.baseUri == null || this.baseUri.length() == 0) {
            throw new BuildException("baseUri attribute required", this.getLocation());
        }
        try {
            final ApplicationDescription ad = this.createApplication(this.classpath.list());
            final Application a = ad.getApplication();
            for (final Resources resources : a.getResources()) {
                resources.setBase(this.baseUri);
            }
            final JAXBContext c = JAXBContext.newInstance("com.sun.research.ws.wadl", this.getClass().getClassLoader());
            final Marshaller m = c.createMarshaller();
            final OutputStream out = new BufferedOutputStream(new FileOutputStream(this.wadlFile));
            try {
                m.marshal(a, out);
            }
            finally {
                out.close();
            }
            final File wadlChildren = new File(this.wadlFile.getPath() + "-/");
            wadlChildren.mkdirs();
            for (final String key : ad.getExternalMetadataKeys()) {
                final File nextFile = new File(wadlChildren, "key");
                final ApplicationDescription.ExternalGrammar em = ad.getExternalGrammar(key);
                final OutputStream out2 = new BufferedOutputStream(new FileOutputStream(nextFile));
                try {
                    out2.write(em.getContent());
                }
                finally {
                    out2.close();
                }
            }
        }
        catch (Exception e) {
            throw new BuildException((Throwable)e);
        }
    }
    
    private ApplicationDescription createApplication(final String[] paths) {
        final ClassLoader cl = Thread.currentThread().getContextClassLoader();
        final ClassLoader ncl = new Loader(this.classpath.list(), this.getClass().getClassLoader());
        Thread.currentThread().setContextClassLoader(ncl);
        try {
            final ResourceConfig rc = new ClasspathResourceConfig(this.classpath.list());
            rc.validate();
            final Set<AbstractResource> s = new HashSet<AbstractResource>();
            for (final Class c : rc.getRootResourceClasses()) {
                s.add(IntrospectionModeller.createResource(c));
            }
            return new WadlBuilder().generate(s);
        }
        catch (Exception e) {
            throw new BuildException((Throwable)e);
        }
        finally {
            Thread.currentThread().setContextClassLoader(cl);
        }
    }
    
    private static class Loader extends URLClassLoader
    {
        Loader(final String[] paths, final ClassLoader parent) {
            super(getURLs(paths), parent);
        }
        
        Loader(final String[] paths) {
            super(getURLs(paths));
        }
        
        public Class findClass(final String name) throws ClassNotFoundException {
            final Class c = super.findClass(name);
            return c;
        }
        
        private static URL[] getURLs(final String[] paths) {
            final List<URL> urls = new ArrayList<URL>();
            for (final String path : paths) {
                try {
                    urls.add(new File(path).toURI().toURL());
                }
                catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
            final URL[] us = urls.toArray(new URL[0]);
            return us;
        }
    }
}
