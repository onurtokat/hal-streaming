// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.server.wadl.generators.resourcedoc;

import com.sun.jersey.server.wadl.ApplicationDescription;
import com.sun.research.ws.wadl.Resources;
import com.sun.jersey.server.wadl.generators.resourcedoc.model.ParamDocType;
import com.sun.jersey.api.model.Parameter;
import com.sun.jersey.api.model.AbstractMethod;
import java.util.List;
import java.util.Iterator;
import com.sun.jersey.server.wadl.generators.resourcedoc.model.ResponseDocType;
import com.sun.research.ws.wadl.ParamStyle;
import com.sun.research.ws.wadl.Param;
import com.sun.jersey.server.wadl.generators.resourcedoc.model.WadlParamType;
import com.sun.research.ws.wadl.Response;
import com.sun.research.ws.wadl.Request;
import com.sun.jersey.server.wadl.generators.resourcedoc.model.RepresentationDocType;
import com.sun.research.ws.wadl.Representation;
import javax.ws.rs.core.MediaType;
import com.sun.jersey.server.wadl.generators.resourcedoc.model.MethodDocType;
import com.sun.research.ws.wadl.Method;
import com.sun.jersey.api.model.AbstractResourceMethod;
import com.sun.jersey.server.wadl.generators.resourcedoc.model.ClassDocType;
import com.sun.research.ws.wadl.Doc;
import com.sun.research.ws.wadl.Resource;
import com.sun.jersey.api.model.AbstractResource;
import com.sun.research.ws.wadl.Application;
import com.sun.jersey.server.wadl.generators.resourcedoc.xhtml.Elements;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.JAXBContext;
import com.sun.jersey.server.wadl.generators.resourcedoc.model.ResourceDocType;
import java.io.InputStream;
import java.io.File;
import com.sun.jersey.server.wadl.WadlGenerator;

public class WadlGeneratorResourceDocSupport implements WadlGenerator
{
    private WadlGenerator _delegate;
    private File _resourceDocFile;
    private InputStream _resourceDocStream;
    private ResourceDocAccessor _resourceDoc;
    
    public WadlGeneratorResourceDocSupport() {
    }
    
    public WadlGeneratorResourceDocSupport(final WadlGenerator wadlGenerator, final ResourceDocType resourceDoc) {
        this._delegate = wadlGenerator;
        this._resourceDoc = new ResourceDocAccessor(resourceDoc);
    }
    
    @Override
    public void setWadlGeneratorDelegate(final WadlGenerator delegate) {
        this._delegate = delegate;
    }
    
    public void setResourceDocFile(final File resourceDocFile) {
        if (this._resourceDocStream != null) {
            throw new IllegalStateException("The resourceDocStream property is already set, therefore you cannot set the resourceDocFile property. Only one of both can be set at a time.");
        }
        this._resourceDocFile = resourceDocFile;
    }
    
    public void setResourceDocStream(final InputStream resourceDocStream) {
        if (this._resourceDocStream != null) {
            throw new IllegalStateException("The resourceDocFile property is already set, therefore you cannot set the resourceDocStream property. Only one of both can be set at a time.");
        }
        this._resourceDocStream = resourceDocStream;
    }
    
    @Override
    public void init() throws Exception {
        if (this._resourceDocFile == null && this._resourceDocStream == null) {
            throw new IllegalStateException("Neither the resourceDocFile nor the resourceDocStream is set, one of both is required.");
        }
        this._delegate.init();
        final JAXBContext c = JAXBContext.newInstance(ResourceDocType.class);
        final Unmarshaller m = c.createUnmarshaller();
        final Object resourceDocObj = (this._resourceDocFile != null) ? m.unmarshal(this._resourceDocFile) : m.unmarshal(this._resourceDocStream);
        final ResourceDocType resourceDoc = ResourceDocType.class.cast(resourceDocObj);
        this._resourceDoc = new ResourceDocAccessor(resourceDoc);
    }
    
    @Override
    public String getRequiredJaxbContextPath() {
        String name = Elements.class.getName();
        name = name.substring(0, name.lastIndexOf(46));
        return (this._delegate.getRequiredJaxbContextPath() == null) ? name : (this._delegate.getRequiredJaxbContextPath() + ":" + name);
    }
    
    @Override
    public Application createApplication() {
        return this._delegate.createApplication();
    }
    
    @Override
    public Resource createResource(final AbstractResource r, final String path) {
        final Resource result = this._delegate.createResource(r, path);
        final ClassDocType classDoc = this._resourceDoc.getClassDoc(r.getResourceClass());
        if (classDoc != null && !this.isEmpty(classDoc.getCommentText())) {
            final Doc doc = new Doc();
            doc.getContent().add(classDoc.getCommentText());
            result.getDoc().add(doc);
        }
        return result;
    }
    
    @Override
    public Method createMethod(final AbstractResource r, final AbstractResourceMethod m) {
        final Method result = this._delegate.createMethod(r, m);
        final MethodDocType methodDoc = this._resourceDoc.getMethodDoc(r.getResourceClass(), m.getMethod());
        if (methodDoc != null && !this.isEmpty(methodDoc.getCommentText())) {
            final Doc doc = new Doc();
            doc.getContent().add(methodDoc.getCommentText());
            result.getDoc().add(doc);
        }
        return result;
    }
    
    @Override
    public Representation createRequestRepresentation(final AbstractResource r, final AbstractResourceMethod m, final MediaType mediaType) {
        final Representation result = this._delegate.createRequestRepresentation(r, m, mediaType);
        final RepresentationDocType requestRepresentation = this._resourceDoc.getRequestRepresentation(r.getResourceClass(), m.getMethod(), result.getMediaType());
        if (requestRepresentation != null) {
            result.setElement(requestRepresentation.getElement());
            this.addDocForExample(result.getDoc(), requestRepresentation.getExample());
        }
        return result;
    }
    
    @Override
    public Request createRequest(final AbstractResource r, final AbstractResourceMethod m) {
        return this._delegate.createRequest(r, m);
    }
    
    @Override
    public Response createResponse(final AbstractResource r, final AbstractResourceMethod m) {
        final ResponseDocType responseDoc = this._resourceDoc.getResponse(r.getResourceClass(), m.getMethod());
        Response response;
        if (responseDoc != null && responseDoc.hasRepresentations()) {
            response = new Response();
            for (final RepresentationDocType representationDoc : responseDoc.getRepresentations()) {
                final Representation wadlRepresentation = new Representation();
                wadlRepresentation.setElement(representationDoc.getElement());
                wadlRepresentation.setMediaType(representationDoc.getMediaType());
                this.addDocForExample(wadlRepresentation.getDoc(), representationDoc.getExample());
                this.addDoc(wadlRepresentation.getDoc(), representationDoc.getDoc());
                response.getStatus().add(representationDoc.getStatus());
                response.getRepresentation().add(wadlRepresentation);
            }
        }
        else {
            response = this._delegate.createResponse(r, m);
        }
        if (responseDoc != null && !responseDoc.getWadlParams().isEmpty()) {
            for (final WadlParamType wadlParamType : responseDoc.getWadlParams()) {
                final Param param = new Param();
                param.setName(wadlParamType.getName());
                param.setStyle(ParamStyle.fromValue(wadlParamType.getStyle()));
                param.setType(wadlParamType.getType());
                this.addDoc(param.getDoc(), wadlParamType.getDoc());
                response.getParam().add(param);
            }
        }
        if (responseDoc != null && !this.isEmpty(responseDoc.getReturnDoc())) {
            this.addDoc(response.getDoc(), responseDoc.getReturnDoc());
        }
        return response;
    }
    
    private void addDocForExample(final List<Doc> docs, final String example) {
        if (!this.isEmpty(example)) {
            final Doc doc = new Doc();
            final Elements pElement = Elements.el("p").add(Elements.val("h6", "Example")).add(Elements.el("pre").add(Elements.val("code", example)));
            doc.getContent().add(pElement);
            docs.add(doc);
        }
    }
    
    private void addDoc(final List<Doc> docs, final String text) {
        if (!this.isEmpty(text)) {
            final Doc doc = new Doc();
            doc.getContent().add(text);
            docs.add(doc);
        }
    }
    
    @Override
    public Param createParam(final AbstractResource r, final AbstractMethod m, final Parameter p) {
        final Param result = this._delegate.createParam(r, m, p);
        final ParamDocType paramDoc = this._resourceDoc.getParamDoc(r.getResourceClass(), (m == null) ? null : m.getMethod(), p);
        if (paramDoc != null && !this.isEmpty(paramDoc.getCommentText())) {
            final Doc doc = new Doc();
            doc.getContent().add(paramDoc.getCommentText());
            result.getDoc().add(doc);
        }
        return result;
    }
    
    @Override
    public Resources createResources() {
        return this._delegate.createResources();
    }
    
    private boolean isEmpty(final String text) {
        return text == null || text.length() == 0 || "".equals(text.trim());
    }
    
    @Override
    public ExternalGrammarDefinition createExternalGrammar() {
        return this._delegate.createExternalGrammar();
    }
    
    @Override
    public void attachTypes(final ApplicationDescription egd) {
        this._delegate.attachTypes(egd);
    }
}
