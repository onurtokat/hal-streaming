// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.core.provider.jaxb;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Array;
import javax.xml.stream.XMLStreamReader;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamException;
import javax.xml.bind.UnmarshalException;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.IOException;
import javax.xml.bind.Marshaller;
import java.nio.charset.Charset;
import javax.xml.bind.JAXBException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import com.sun.jersey.core.provider.AbstractMessageReaderWriterProvider;
import java.util.Arrays;
import java.io.OutputStream;
import javax.ws.rs.core.MultivaluedMap;
import java.lang.reflect.ParameterizedType;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.List;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Providers;
import com.sun.jersey.core.impl.provider.entity.Inflector;

public abstract class AbstractListElementProvider extends AbstractJAXBProvider<Object>
{
    private final Inflector inflector;
    
    public AbstractListElementProvider(final Providers ps) {
        super(ps);
        this.inflector = Inflector.getInstance();
    }
    
    public AbstractListElementProvider(final Providers ps, final MediaType mt) {
        super(ps, mt);
        this.inflector = Inflector.getInstance();
    }
    
    @Override
    public boolean isReadable(final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
        if (type == List.class || type == Collection.class) {
            return this.verifyGenericType(genericType) && this.isSupported(mediaType);
        }
        return type.isArray() && this.verifyArrayType(type) && this.isSupported(mediaType);
    }
    
    @Override
    public boolean isWriteable(final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
        if (List.class.isAssignableFrom(type)) {
            return this.verifyGenericType(genericType) && this.isSupported(mediaType);
        }
        return type.isArray() && this.verifyArrayType(type) && this.isSupported(mediaType);
    }
    
    private boolean verifyArrayType(Class type) {
        type = (Class<?>)type.getComponentType();
        return type.isAnnotationPresent(XmlRootElement.class) || type.isAnnotationPresent(XmlType.class) || JAXBElement.class.isAssignableFrom(type);
    }
    
    private boolean verifyGenericType(final Type genericType) {
        if (!(genericType instanceof ParameterizedType)) {
            return false;
        }
        final ParameterizedType pt = (ParameterizedType)genericType;
        if (pt.getActualTypeArguments().length > 1) {
            return false;
        }
        final Type ta = pt.getActualTypeArguments()[0];
        if (ta instanceof ParameterizedType) {
            final ParameterizedType lpt = (ParameterizedType)ta;
            return lpt.getRawType() instanceof Class && JAXBElement.class.isAssignableFrom((Class<?>)lpt.getRawType());
        }
        if (!(pt.getActualTypeArguments()[0] instanceof Class)) {
            return false;
        }
        final Class listClass = (Class)pt.getActualTypeArguments()[0];
        return listClass.isAnnotationPresent(XmlRootElement.class) || listClass.isAnnotationPresent(XmlType.class);
    }
    
    @Override
    public final void writeTo(final Object t, final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType, final MultivaluedMap<String, Object> httpHeaders, final OutputStream entityStream) throws IOException {
        try {
            final Collection c = type.isArray() ? Arrays.asList((Object[])t) : ((Collection)t);
            final Class elementType = this.getElementClass(type, genericType);
            final Charset charset = AbstractMessageReaderWriterProvider.getCharset(mediaType);
            final String charsetName = charset.name();
            final Marshaller m = this.getMarshaller(elementType, mediaType);
            m.setProperty("jaxb.fragment", true);
            if (charset != AbstractListElementProvider.UTF8) {
                m.setProperty("jaxb.encoding", charsetName);
            }
            this.setHeader(m, annotations);
            this.writeList(elementType, c, mediaType, charset, m, entityStream);
        }
        catch (JAXBException ex) {
            throw new WebApplicationException(ex, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    public abstract void writeList(final Class<?> p0, final Collection<?> p1, final MediaType p2, final Charset p3, final Marshaller p4, final OutputStream p5) throws JAXBException, IOException;
    
    @Override
    public final Object readFrom(final Class<Object> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType, final MultivaluedMap<String, String> httpHeaders, final InputStream entityStream) throws IOException {
        try {
            final Class elementType = this.getElementClass(type, genericType);
            final Unmarshaller u = this.getUnmarshaller(elementType, mediaType);
            final XMLStreamReader r = this.getXMLStreamReader(elementType, mediaType, u, entityStream);
            final List l = new ArrayList();
            boolean jaxbElement = false;
            for (int event = r.next(); event != 1; event = r.next()) {}
            int event;
            for (event = r.next(); event != 1 && event != 8; event = r.next()) {}
            while (event != 8) {
                if (elementType.isAnnotationPresent(XmlRootElement.class)) {
                    l.add(u.unmarshal(r));
                }
                else if (elementType.isAnnotationPresent(XmlType.class)) {
                    l.add(u.unmarshal(r, (Class<Object>)elementType).getValue());
                }
                else {
                    l.add(u.unmarshal(r, (Class<Object>)elementType));
                    jaxbElement = true;
                }
                for (event = r.getEventType(); event != 1 && event != 8; event = r.next()) {}
            }
            return type.isArray() ? this.createArray(l, jaxbElement ? JAXBElement.class : elementType) : l;
        }
        catch (UnmarshalException ex) {
            throw new WebApplicationException(ex, Response.Status.BAD_REQUEST);
        }
        catch (XMLStreamException ex2) {
            throw new WebApplicationException(ex2, Response.Status.BAD_REQUEST);
        }
        catch (JAXBException ex3) {
            throw new WebApplicationException(ex3, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    private Object createArray(final List l, final Class componentType) {
        final Object array = Array.newInstance(componentType, l.size());
        for (int i = 0; i < l.size(); ++i) {
            Array.set(array, i, l.get(i));
        }
        return array;
    }
    
    protected abstract XMLStreamReader getXMLStreamReader(final Class<?> p0, final MediaType p1, final Unmarshaller p2, final InputStream p3) throws XMLStreamException;
    
    protected Class getElementClass(final Class<?> type, final Type genericType) {
        Type ta;
        if (genericType instanceof ParameterizedType) {
            ta = ((ParameterizedType)genericType).getActualTypeArguments()[0];
        }
        else if (genericType instanceof GenericArrayType) {
            ta = ((GenericArrayType)genericType).getGenericComponentType();
        }
        else {
            ta = type.getComponentType();
        }
        if (ta instanceof ParameterizedType) {
            ta = ((ParameterizedType)ta).getActualTypeArguments()[0];
        }
        return (Class)ta;
    }
    
    private String convertToXmlName(final String name) {
        return name.replace("$", "_");
    }
    
    protected final String getRootElementName(final Class<?> elementType) {
        if (this.isXmlRootElementProcessing()) {
            return this.convertToXmlName(this.inflector.pluralize(this.inflector.demodulize(this.getElementName(elementType))));
        }
        return this.convertToXmlName(this.inflector.decapitalize(this.inflector.pluralize(this.inflector.demodulize(elementType.getName()))));
    }
    
    protected final String getElementName(final Class<?> elementType) {
        String name = elementType.getName();
        final XmlRootElement xre = elementType.getAnnotation(XmlRootElement.class);
        if (xre != null && !xre.name().equals("##default")) {
            name = xre.name();
        }
        return name;
    }
}
