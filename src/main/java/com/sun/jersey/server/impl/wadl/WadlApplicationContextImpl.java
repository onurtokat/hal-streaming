// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.server.impl.wadl;

import com.sun.research.ws.wadl.Doc;
import com.sun.research.ws.wadl.Include;
import com.sun.research.ws.wadl.Grammars;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import com.sun.jersey.server.wadl.WadlGenerator;
import com.sun.research.ws.wadl.Resource;
import com.sun.jersey.server.wadl.WadlBuilder;
import java.util.Iterator;
import com.sun.research.ws.wadl.Application;
import com.sun.research.ws.wadl.Resources;
import com.sun.jersey.server.wadl.ApplicationDescription;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBException;
import com.sun.jersey.api.wadl.config.WadlGeneratorConfigLoader;
import com.sun.jersey.api.core.ResourceConfig;
import javax.xml.bind.JAXBContext;
import com.sun.jersey.api.wadl.config.WadlGeneratorConfig;
import com.sun.jersey.api.model.AbstractResource;
import java.util.Set;
import java.util.logging.Logger;
import com.sun.jersey.server.wadl.WadlApplicationContext;

public class WadlApplicationContextImpl implements WadlApplicationContext
{
    private static final Logger LOG;
    private boolean wadlGenerationEnabled;
    private final Set<AbstractResource> rootResources;
    private final WadlGeneratorConfig wadlGeneratorConfig;
    private JAXBContext jaxbContext;
    
    public WadlApplicationContextImpl(final Set<AbstractResource> rootResources, final ResourceConfig resourceConfig) {
        this.wadlGenerationEnabled = true;
        this.rootResources = rootResources;
        this.wadlGeneratorConfig = WadlGeneratorConfigLoader.loadWadlGeneratorsFromConfig(resourceConfig);
        try {
            this.jaxbContext = JAXBContext.newInstance(this.wadlGeneratorConfig.createWadlGenerator().getRequiredJaxbContextPath());
        }
        catch (JAXBException ex) {
            this.jaxbContext = null;
        }
    }
    
    @Override
    public ApplicationDescription getApplication(final UriInfo ui) {
        final ApplicationDescription a = this.getWadlBuilder().generate(this.rootResources);
        final Application application = a.getApplication();
        for (final Resources resources : application.getResources()) {
            resources.setBase(ui.getBaseUri().toString());
        }
        this.attachExternalGrammar(application, a, ui.getRequestUri());
        return a;
    }
    
    @Override
    public Application getApplication(final UriInfo info, final AbstractResource resource, final String path) {
        final ApplicationDescription description = this.getApplication(info);
        final WadlGenerator wadlGenerator = this.wadlGeneratorConfig.createWadlGenerator();
        final Application a = (path == null) ? new WadlBuilder(wadlGenerator).generate(description, resource) : new WadlBuilder(wadlGenerator).generate(description, resource, path);
        for (final Resources resources : a.getResources()) {
            resources.setBase(info.getBaseUri().toString());
        }
        this.attachExternalGrammar(a, description, info.getRequestUri());
        for (final Resources resources : a.getResources()) {
            final Resource r = resources.getResource().get(0);
            r.setPath(info.getBaseUri().relativize(info.getAbsolutePath()).toString());
            r.getParam().clear();
        }
        return a;
    }
    
    @Override
    public JAXBContext getJAXBContext() {
        return this.jaxbContext;
    }
    
    private WadlBuilder getWadlBuilder() {
        return this.wadlGenerationEnabled ? new WadlBuilder(this.wadlGeneratorConfig.createWadlGenerator()) : null;
    }
    
    @Override
    public void setWadlGenerationEnabled(final boolean wadlGenerationEnabled) {
        this.wadlGenerationEnabled = wadlGenerationEnabled;
    }
    
    @Override
    public boolean isWadlGenerationEnabled() {
        return this.wadlGenerationEnabled;
    }
    
    private void attachExternalGrammar(final Application application, final ApplicationDescription applicationDescription, URI requestURI) {
        final String requestURIPath = requestURI.getPath();
        if (requestURIPath.endsWith("application.wadl")) {
            requestURI = UriBuilder.fromUri(requestURI).replacePath(requestURIPath.substring(0, requestURIPath.lastIndexOf(47) + 1)).build(new Object[0]);
        }
        final String root = application.getResources().get(0).getBase();
        final UriBuilder extendedPath = (root != null) ? UriBuilder.fromPath(root).path("/application.wadl/") : UriBuilder.fromPath("./application.wadl/");
        final URI rootURI = (root != null) ? UriBuilder.fromPath(root).build(new Object[0]) : null;
        Grammars grammars;
        if (application.getGrammars() != null) {
            WadlApplicationContextImpl.LOG.info("The wadl application already contains a grammars element, we're adding elements of the provided grammars file.");
            grammars = application.getGrammars();
        }
        else {
            grammars = new Grammars();
            application.setGrammars(grammars);
        }
        for (final String path : applicationDescription.getExternalMetadataKeys()) {
            final URI schemaURI = extendedPath.clone().path(path).build(new Object[0]);
            final String schemaURIS = schemaURI.toString();
            final String requestURIs = requestURI.toString();
            final String schemaPath = (rootURI != null) ? requestURI.relativize(schemaURI).toString() : schemaURI.toString();
            final Include include = new Include();
            include.setHref(schemaPath);
            final Doc doc = new Doc();
            doc.setLang("en");
            doc.setTitle("Generated");
            include.getDoc().add(doc);
            grammars.getInclude().add(include);
        }
    }
    
    static {
        LOG = Logger.getLogger(WadlApplicationContextImpl.class.getName());
    }
}
