// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.json.impl.provider.entity;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import com.sun.jersey.api.json.JSONMarshaller;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.OutputStream;
import javax.xml.bind.Marshaller;
import javax.xml.bind.JAXBException;
import java.nio.charset.Charset;
import java.io.Reader;
import java.io.InputStreamReader;
import com.sun.jersey.api.json.JSONJAXBContext;
import com.sun.jersey.core.provider.AbstractMessageReaderWriterProvider;
import java.io.InputStream;
import javax.xml.bind.Unmarshaller;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.core.Context;
import com.sun.jersey.core.util.FeaturesAndProperties;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Providers;
import com.sun.jersey.core.provider.jaxb.AbstractRootElementProvider;

public class JSONRootElementProvider extends AbstractRootElementProvider
{
    boolean jacksonEntityProviderTakesPrecedence;
    
    JSONRootElementProvider(final Providers ps) {
        super(ps);
        this.jacksonEntityProviderTakesPrecedence = false;
    }
    
    JSONRootElementProvider(final Providers ps, final MediaType mt) {
        super(ps, mt);
        this.jacksonEntityProviderTakesPrecedence = false;
    }
    
    @Context
    @Override
    public void setConfiguration(final FeaturesAndProperties fp) {
        super.setConfiguration(fp);
        this.jacksonEntityProviderTakesPrecedence = fp.getFeature("com.sun.jersey.api.json.POJOMappingFeature");
    }
    
    @Override
    public boolean isReadable(final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
        return !this.jacksonEntityProviderTakesPrecedence && super.isReadable(type, genericType, annotations, mediaType);
    }
    
    @Override
    public boolean isWriteable(final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
        return !this.jacksonEntityProviderTakesPrecedence && super.isWriteable(type, genericType, annotations, mediaType);
    }
    
    @Override
    protected final Object readFrom(final Class<Object> type, final MediaType mediaType, final Unmarshaller u, final InputStream entityStream) throws JAXBException {
        final Charset c = AbstractMessageReaderWriterProvider.getCharset(mediaType);
        return JSONJAXBContext.getJSONUnmarshaller(u).unmarshalFromJSON(new InputStreamReader(entityStream, c), type);
    }
    
    @Override
    protected void writeTo(final Object t, final MediaType mediaType, final Charset c, final Marshaller m, final OutputStream entityStream) throws JAXBException {
        final JSONMarshaller jsonMarshaller = JSONJAXBContext.getJSONMarshaller(m);
        if (this.isFormattedOutput()) {
            jsonMarshaller.setProperty("com.sun.jersey.api.json.JSONMarshaller.formatted", true);
        }
        jsonMarshaller.marshallToJSON(t, new OutputStreamWriter(entityStream, c));
    }
    
    @Produces({ "application/json" })
    @Consumes({ "application/json" })
    public static final class App extends JSONRootElementProvider
    {
        public App(@Context final Providers ps) {
            super(ps, MediaType.APPLICATION_JSON_TYPE);
        }
    }
    
    @Produces({ "*/*" })
    @Consumes({ "*/*" })
    public static final class General extends JSONRootElementProvider
    {
        public General(@Context final Providers ps) {
            super(ps);
        }
        
        @Override
        protected boolean isSupported(final MediaType m) {
            return !this.jacksonEntityProviderTakesPrecedence && m.getSubtype().endsWith("+json");
        }
    }
}
