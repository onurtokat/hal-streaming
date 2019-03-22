// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.json.impl.writer;

import java.util.HashMap;
import java.util.Collection;
import java.util.Arrays;
import java.util.HashSet;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.namespace.NamespaceContext;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import com.sun.xml.bind.v2.model.runtime.RuntimeReferencePropertyInfo;
import com.sun.xml.bind.v2.model.runtime.RuntimePropertyInfo;
import com.sun.xml.bind.v2.runtime.property.Property;
import com.sun.xml.bind.v2.runtime.XMLSerializer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import org.codehaus.jackson.JsonGenerationException;
import javax.xml.stream.XMLStreamException;
import java.util.ArrayList;
import com.sun.jersey.api.json.JSONConfiguration;
import org.codehaus.jackson.JsonGenerator;
import java.util.Map;
import java.util.Set;
import java.lang.reflect.Type;
import java.util.List;
import javax.xml.stream.XMLStreamWriter;

public class Stax2JacksonWriter implements XMLStreamWriter
{
    private final boolean attrsWithPrefix;
    static final String XML_SCHEMA_INSTANCE = "http://www.w3.org/2001/XMLSchema-instance";
    JacksonStringMergingGenerator generator;
    final List<ProcessingInfo> processingStack;
    boolean writingAttr;
    static final Type[] _pt;
    static final Type[] _pta;
    static final Type[] _nst;
    static final Set<Type> primitiveTypes;
    static final Set<Type> primitiveTypeArrays;
    static final Set<Type> nonStringTypes;
    static final Map<Type, Type> pta2pMap;
    
    static <T> T pop(final List<T> stack) {
        return stack.remove(stack.size() - 1);
    }
    
    static <T> T peek(final List<T> stack) {
        return (stack.size() > 0) ? stack.get(stack.size() - 1) : null;
    }
    
    static <T> T peek2nd(final List<T> stack) {
        return (stack.size() > 1) ? stack.get(stack.size() - 2) : null;
    }
    
    public Stax2JacksonWriter(final JsonGenerator generator) {
        this(generator, JSONConfiguration.DEFAULT);
    }
    
    public Stax2JacksonWriter(final JsonGenerator generator, final JSONConfiguration config) {
        this.processingStack = new ArrayList<ProcessingInfo>();
        this.writingAttr = false;
        this.attrsWithPrefix = config.isUsingPrefixesAtNaturalAttributes();
        this.generator = JacksonStringMergingGenerator.createGenerator(generator);
    }
    
    @Override
    public void writeStartElement(final String localName) throws XMLStreamException {
        this.writeStartElement(null, localName, null);
    }
    
    @Override
    public void writeStartElement(final String namespaceURI, final String localName) throws XMLStreamException {
        this.writeStartElement(null, localName, namespaceURI);
    }
    
    private void ensureStartObjectBeforeFieldName(final ProcessingInfo pi) throws JsonGenerationException, IOException {
        if (pi != null && pi.afterFN) {
            this.generator.writeStartObject();
            peek2nd(this.processingStack).startObjectWritten = true;
            pi.afterFN = false;
        }
    }
    
    @Override
    public void writeStartElement(final String prefix, final String localName, final String namespaceURI) throws XMLStreamException {
        try {
            this.pushPropInfo(localName);
            final ProcessingInfo currentPI = peek(this.processingStack);
            final ProcessingInfo parentPI = peek2nd(this.processingStack);
            if (!currentPI.isArray) {
                if (parentPI != null && parentPI.lastUnderlyingPI != null && parentPI.lastUnderlyingPI.isArray) {
                    this.generator.writeEndArray();
                    parentPI.afterFN = false;
                }
                this.ensureStartObjectBeforeFieldName(parentPI);
                this.generator.writeFieldName(localName);
                currentPI.afterFN = true;
            }
            else if (parentPI == null || !currentPI.equals(parentPI.lastUnderlyingPI)) {
                if (parentPI != null && parentPI.lastUnderlyingPI != null && parentPI.lastUnderlyingPI.isArray) {
                    this.generator.writeEndArray();
                    parentPI.afterFN = false;
                }
                this.ensureStartObjectBeforeFieldName(parentPI);
                this.generator.writeFieldName(localName);
                this.generator.writeStartArray();
                currentPI.afterFN = true;
            }
            else {
                currentPI.afterFN = true;
            }
        }
        catch (IOException ex) {
            Logger.getLogger(Stax2JacksonWriter.class.getName()).log(Level.SEVERE, null, ex);
            throw new XMLStreamException(ex);
        }
    }
    
    private void pushPropInfo(final String elementName) {
        final ProcessingInfo parentPI = peek(this.processingStack);
        if (elementName != null && parentPI != null && parentPI.lastUnderlyingPI != null && elementName.equals(parentPI.lastUnderlyingPI.elementName)) {
            this.processingStack.add(new ProcessingInfo(parentPI.lastUnderlyingPI));
            return;
        }
        final XMLSerializer xs = XMLSerializer.getInstance();
        final Property cp = (xs == null) ? null : xs.getCurrentProperty();
        final RuntimePropertyInfo ri = (cp == null) ? null : cp.getInfo();
        final Type rt = (ri == null) ? null : ri.getRawType();
        final String dn = (ri == null) ? null : ri.getName();
        if (null == rt) {
            if (this.writingAttr) {
                this.processingStack.add(new ProcessingInfo(elementName, ri, false, null));
                return;
            }
            this.processingStack.add(new ProcessingInfo(elementName, ri, false, null));
        }
        else {
            if (Stax2JacksonWriter.primitiveTypes.contains(rt)) {
                this.processingStack.add(new ProcessingInfo(elementName, ri, false, rt));
                return;
            }
            if (ri.isCollection() && !this.isWildcardElement(ri) && (parentPI == null || !parentPI.isArray || parentPI.rpi != ri)) {
                this.processingStack.add(new ProcessingInfo(elementName, ri, true, rt));
                return;
            }
            this.processingStack.add(new ProcessingInfo(elementName, ri, false, rt));
        }
    }
    
    private boolean isWildcardElement(final RuntimePropertyInfo ri) {
        return ri instanceof RuntimeReferencePropertyInfo && ((RuntimeReferencePropertyInfo)ri).getWildcard() != null;
    }
    
    @Override
    public void writeEmptyElement(final String localName) throws XMLStreamException {
        this.writeEmptyElement(null, localName, null);
    }
    
    @Override
    public void writeEmptyElement(final String namespaceURI, final String localName) throws XMLStreamException {
        this.writeEmptyElement(null, localName, namespaceURI);
    }
    
    @Override
    public void writeEmptyElement(final String prefix, final String localName, final String namespaceURI) throws XMLStreamException {
        this.writeStartElement(prefix, localName, namespaceURI);
        this.writeEndElement();
    }
    
    private void cleanlyEndObject(final ProcessingInfo pi) throws IOException {
        if (pi.startObjectWritten) {
            this.generator.writeEndObject();
        }
        else if (pi.afterFN && pi.lastUnderlyingPI == null) {
            this.generator.writeNull();
        }
    }
    
    @Override
    public void writeEndElement() throws XMLStreamException {
        try {
            final ProcessingInfo removedPI = pop(this.processingStack);
            final ProcessingInfo currentPI = peek(this.processingStack);
            if (currentPI != null) {
                currentPI.lastUnderlyingPI = removedPI;
            }
            if (removedPI.lastUnderlyingPI != null && removedPI.lastUnderlyingPI.isArray) {
                this.generator.writeEndArray();
            }
            this.cleanlyEndObject(removedPI);
        }
        catch (IOException ex) {
            Logger.getLogger(Stax2JacksonWriter.class.getName()).log(Level.SEVERE, null, ex);
            throw new XMLStreamException(ex);
        }
    }
    
    @Override
    public void writeEndDocument() throws XMLStreamException {
        try {
            this.generator.writeEndObject();
        }
        catch (IOException ex) {
            Logger.getLogger(Stax2JacksonWriter.class.getName()).log(Level.SEVERE, null, ex);
            throw new XMLStreamException(ex);
        }
    }
    
    @Override
    public void close() throws XMLStreamException {
        try {
            this.generator.close();
        }
        catch (IOException ex) {
            Logger.getLogger(Stax2JacksonWriter.class.getName()).log(Level.SEVERE, null, ex);
            throw new XMLStreamException(ex);
        }
    }
    
    @Override
    public void flush() throws XMLStreamException {
        try {
            this.generator.flush();
        }
        catch (IOException ex) {
            Logger.getLogger(Stax2JacksonWriter.class.getName()).log(Level.SEVERE, null, ex);
            throw new XMLStreamException(ex);
        }
    }
    
    @Override
    public void writeAttribute(final String localName, final String value) throws XMLStreamException {
        this.writeAttribute(null, null, localName, value);
    }
    
    @Override
    public void writeAttribute(final String namespaceURI, final String localName, final String value) throws XMLStreamException {
        this.writeAttribute(null, namespaceURI, localName, value);
    }
    
    @Override
    public void writeAttribute(final String prefix, final String namespaceURI, final String localName, final String value) throws XMLStreamException {
        this.writingAttr = true;
        this.writeStartElement(prefix, this.attrsWithPrefix ? ("@" + localName) : localName, namespaceURI);
        this.writingAttr = false;
        this.writeCharacters(value, "type".equals(localName) && "http://www.w3.org/2001/XMLSchema-instance".equals(namespaceURI));
        this.writeEndElement();
    }
    
    @Override
    public void writeNamespace(final String prefix, final String namespaceURI) throws XMLStreamException {
    }
    
    @Override
    public void writeDefaultNamespace(final String uri) throws XMLStreamException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void writeComment(final String data) throws XMLStreamException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void writeProcessingInstruction(final String target) throws XMLStreamException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void writeProcessingInstruction(final String target, final String data) throws XMLStreamException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void writeCData(final String data) throws XMLStreamException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void writeDTD(final String dtd) throws XMLStreamException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void writeEntityRef(final String name) throws XMLStreamException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void writeStartDocument() throws XMLStreamException {
        this.writeStartDocument(null, null);
    }
    
    @Override
    public void writeStartDocument(final String version) throws XMLStreamException {
        this.writeStartDocument(null, version);
    }
    
    @Override
    public void writeStartDocument(final String encoding, final String version) throws XMLStreamException {
        try {
            this.generator.writeStartObject();
        }
        catch (IOException ex) {
            if (!(ex instanceof SocketTimeoutException) && !(ex instanceof SocketException)) {
                Logger.getLogger(Stax2JacksonWriter.class.getName()).log(Level.SEVERE, "IO exception", ex);
                throw new XMLStreamException(ex);
            }
            Logger.getLogger(Stax2JacksonWriter.class.getName()).log(Level.FINE, "Socket excption", ex);
        }
    }
    
    private void writeCharacters(final String text, final boolean forceString) throws XMLStreamException {
        try {
            final ProcessingInfo currentPI = peek(this.processingStack);
            if (currentPI.startObjectWritten && !currentPI.afterFN) {
                this.generator.writeFieldName("$");
            }
            currentPI.afterFN = false;
            if (forceString || !Stax2JacksonWriter.nonStringTypes.contains(currentPI.t)) {
                if (!currentPI.isArray) {
                    this.generator.writeStringToMerge(text);
                }
                else {
                    this.generator.writeString(text);
                }
            }
            else if (Boolean.TYPE == currentPI.t || Boolean.class == currentPI.t) {
                this.generator.writeBoolean(Boolean.parseBoolean(text));
            }
            else {
                this.generator.writeNumber(text);
            }
        }
        catch (IOException ex) {
            Logger.getLogger(Stax2JacksonWriter.class.getName()).log(Level.SEVERE, null, ex);
            throw new XMLStreamException(ex);
        }
    }
    
    @Override
    public void writeCharacters(final String text) throws XMLStreamException {
        this.writeCharacters(text, false);
    }
    
    @Override
    public void writeCharacters(final char[] text, final int start, final int length) throws XMLStreamException {
        this.writeCharacters(new String(text, start, length));
    }
    
    @Override
    public String getPrefix(final String uri) throws XMLStreamException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void setPrefix(final String prefix, final String uri) throws XMLStreamException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void setDefaultNamespace(final String uri) throws XMLStreamException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void setNamespaceContext(final NamespaceContext context) throws XMLStreamException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public NamespaceContext getNamespaceContext() {
        return null;
    }
    
    @Override
    public Object getProperty(final String name) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    static {
        _pt = new Type[] { Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Boolean.TYPE, Character.TYPE, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Boolean.class, Character.class, String.class };
        _pta = new Type[] { byte[].class, short[].class, int[].class, long[].class, float[].class, double[].class, boolean[].class, char[].class, Byte[].class, Short[].class, Integer[].class, Long[].class, Float[].class, Double[].class, Boolean[].class, Character[].class, String[].class };
        _nst = new Type[] { Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Boolean.TYPE, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Boolean.class, BigInteger.class, BigDecimal.class };
        primitiveTypes = new HashSet<Type>() {
            {
                this.addAll(Arrays.asList(Stax2JacksonWriter._pt));
            }
        };
        primitiveTypeArrays = new HashSet<Type>() {
            {
                this.addAll(Arrays.asList(Stax2JacksonWriter._pta));
            }
        };
        nonStringTypes = new HashSet<Type>() {
            {
                this.addAll(Arrays.asList(Stax2JacksonWriter._nst));
            }
        };
        pta2pMap = new HashMap<Type, Type>() {
            {
                for (int i = 0; i < Stax2JacksonWriter._pta.length; ++i) {
                    this.put(Stax2JacksonWriter._pta[i], Stax2JacksonWriter._pt[i]);
                }
            }
        };
    }
    
    private static class ProcessingInfo
    {
        RuntimePropertyInfo rpi;
        boolean isArray;
        Type t;
        ProcessingInfo lastUnderlyingPI;
        boolean startObjectWritten;
        boolean afterFN;
        String elementName;
        
        public ProcessingInfo(final String elementName, final RuntimePropertyInfo rpi, final boolean isArray, final Type t) {
            this.startObjectWritten = false;
            this.afterFN = false;
            this.elementName = elementName;
            this.rpi = rpi;
            this.isArray = isArray;
            this.t = t;
        }
        
        public ProcessingInfo(final ProcessingInfo pi) {
            this(pi.elementName, pi.rpi, pi.isArray, pi.t);
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (obj == null) {
                return false;
            }
            if (this.getClass() != obj.getClass()) {
                return false;
            }
            final ProcessingInfo other = (ProcessingInfo)obj;
            return (this.rpi == other.rpi || (this.rpi != null && this.rpi.equals(other.rpi))) && this.isArray == other.isArray && (this.t == other.t || (this.t != null && this.t.equals(other.t)));
        }
        
        @Override
        public int hashCode() {
            int hash = 5;
            hash = 47 * hash + ((this.rpi != null) ? this.rpi.hashCode() : 0);
            hash = 47 * hash + (this.isArray ? 1 : 0);
            hash = 47 * hash + ((this.t != null) ? this.t.hashCode() : 0);
            return hash;
        }
    }
}
