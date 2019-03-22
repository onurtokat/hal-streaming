// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.json.impl.reader;

import javax.xml.namespace.NamespaceContext;
import java.util.Iterator;
import com.sun.jersey.json.impl.ImplMessages;
import com.sun.xml.bind.v2.runtime.unmarshaller.UnmarshallingContext;
import javax.xml.stream.Location;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.xml.stream.XMLStreamException;
import com.sun.jersey.api.json.JSONConfiguration;
import org.codehaus.jackson.JsonToken;
import javax.xml.namespace.QName;
import java.util.Map;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import org.codehaus.jackson.JsonParser;
import javax.xml.stream.XMLStreamReader;

public class Jackson2StaxReader implements XMLStreamReader
{
    JsonParser parser;
    final Queue<JsonReaderXmlEvent> eventQueue;
    final List<ProcessingInfo> processingStack;
    final JsonNamespaceContext namespaceContext;
    private boolean properJAXBVersion;
    private final boolean attrsWithPrefix;
    final Collection<String> elemsExpected;
    final Map<String, QName> qNamesOfExpElems;
    final Collection<String> attrsExpected;
    final Map<String, QName> qNamesOfExpAttrs;
    static final Collection<JsonToken> valueTokens;
    
    static <T> T pop(final List<T> stack) {
        return stack.remove(stack.size() - 1);
    }
    
    static <T> T peek(final List<T> stack) {
        return (stack.size() > 0) ? stack.get(stack.size() - 1) : null;
    }
    
    static <T> T peek2nd(final List<T> stack) {
        return (stack.size() > 1) ? stack.get(stack.size() - 2) : null;
    }
    
    public Jackson2StaxReader(final JsonParser parser) throws XMLStreamException {
        this(parser, JSONConfiguration.DEFAULT);
    }
    
    public Jackson2StaxReader(final JsonParser parser, final JSONConfiguration config) throws XMLStreamException {
        this.eventQueue = new LinkedList<JsonReaderXmlEvent>();
        this.processingStack = new ArrayList<ProcessingInfo>();
        this.namespaceContext = new JsonNamespaceContext();
        this.properJAXBVersion = true;
        this.elemsExpected = new HashSet<String>();
        this.qNamesOfExpElems = new HashMap<String, QName>();
        this.attrsExpected = new HashSet<String>();
        this.qNamesOfExpAttrs = new HashMap<String, QName>();
        this.attrsWithPrefix = config.isUsingPrefixesAtNaturalAttributes();
        this.parser = parser;
        try {
            this.readNext();
        }
        catch (IOException ex) {
            Logger.getLogger(Jackson2StaxReader.class.getName()).log(Level.SEVERE, null, ex);
            throw new XMLStreamException(ex);
        }
    }
    
    @Override
    public Object getProperty(final String name) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private void readNext() throws IOException {
        this.readNext(false);
    }
    
    private QName getQNameForTagLocName(final String localName) {
        return this.getQNameForLocName(localName, this.qNamesOfExpElems);
    }
    
    private QName getQNameForLocName(final String localName, final Map<String, QName> qNamesMap) {
        final QName result = qNamesMap.get(localName);
        if (result != null) {
            return result;
        }
        return new QName(localName);
    }
    
    private void readNext(boolean lookingForAttributes) throws IOException {
        if (!lookingForAttributes) {
            this.eventQueue.poll();
        }
        if (!this.eventQueue.isEmpty() && !lookingForAttributes) {
            return;
        }
        while (true) {
            this.parser.nextToken();
            final JsonToken jtok = this.parser.getCurrentToken();
            final ProcessingInfo pi = peek(this.processingStack);
            switch (jtok) {
                case FIELD_NAME: {
                    String currentName = this.parser.getCurrentName();
                    if (this.attrsWithPrefix && currentName.startsWith("@")) {
                        currentName = currentName.substring(1);
                    }
                    final boolean currentIsAttribute = (!"$".equals(currentName) && this.properJAXBVersion) ? this.attrsExpected.contains(currentName) : (!this.elemsExpected.contains(currentName));
                    if (lookingForAttributes && currentIsAttribute) {
                        this.parser.nextToken();
                        if (Jackson2StaxReader.valueTokens.contains(this.parser.getCurrentToken())) {
                            this.eventQueue.peek().addAttribute(this.getQNameForLocName(currentName, this.qNamesOfExpAttrs), this.parser.getText());
                            continue;
                        }
                        System.out.println(String.format("CurrentName=%s", currentName));
                        throw new IOException("Not an attribute, expected primitive value!");
                    }
                    else {
                        lookingForAttributes = false;
                        if (!"$".equals(currentName)) {
                            final QName currentQName = this.getQNameForTagLocName(currentName);
                            this.eventQueue.add(new StartElementEvent(currentQName, new StaxLocation(this.parser.getCurrentLocation())));
                            this.processingStack.add(new ProcessingInfo(currentQName, false, true));
                            return;
                        }
                        this.parser.nextToken();
                        if (Jackson2StaxReader.valueTokens.contains(this.parser.getCurrentToken())) {
                            this.eventQueue.add(new CharactersEvent(this.parser.getText(), new StaxLocation(this.parser.getCurrentLocation())));
                            return;
                        }
                        throw new IOException("Not a xml value, expected primitive value!");
                    }
                    break;
                }
                case START_OBJECT: {
                    if (pi == null) {
                        this.eventQueue.add(new StartDocumentEvent(new StaxLocation(0, 0, 0)));
                        return;
                    }
                    if (pi.isArray && !pi.isFirstElement) {
                        this.eventQueue.add(new StartElementEvent(pi.name, new StaxLocation(this.parser.getCurrentLocation())));
                        return;
                    }
                    pi.isFirstElement = false;
                    continue;
                }
                case END_OBJECT: {
                    lookingForAttributes = false;
                    this.eventQueue.add(new EndElementEvent(pi.name, new StaxLocation(this.parser.getCurrentLocation())));
                    if (!pi.isArray) {
                        pop(this.processingStack);
                    }
                    if (this.processingStack.isEmpty()) {
                        this.eventQueue.add(new EndDocumentEvent(new StaxLocation(this.parser.getCurrentLocation())));
                    }
                }
                case VALUE_FALSE:
                case VALUE_NULL:
                case VALUE_NUMBER_FLOAT:
                case VALUE_NUMBER_INT:
                case VALUE_TRUE:
                case VALUE_STRING: {
                    if (!pi.isFirstElement) {
                        this.eventQueue.add(new StartElementEvent(pi.name, new StaxLocation(this.parser.getCurrentLocation())));
                    }
                    else {
                        pi.isFirstElement = false;
                    }
                    if (jtok != JsonToken.VALUE_NULL) {
                        this.eventQueue.add(new CharactersEvent(this.parser.getText(), new StaxLocation(this.parser.getCurrentLocation())));
                    }
                    this.eventQueue.add(new EndElementEvent(pi.name, new StaxLocation(this.parser.getCurrentLocation())));
                    if (!pi.isArray) {
                        pop(this.processingStack);
                    }
                    if (this.processingStack.isEmpty()) {
                        this.eventQueue.add(new EndDocumentEvent(new StaxLocation(this.parser.getCurrentLocation())));
                    }
                    lookingForAttributes = false;
                }
                case START_ARRAY: {
                    peek(this.processingStack).isArray = true;
                    continue;
                }
                case END_ARRAY: {
                    pop(this.processingStack);
                    lookingForAttributes = false;
                    continue;
                }
            }
        }
    }
    
    @Override
    public void require(final int arg0, final String arg1, final String arg2) throws XMLStreamException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String getElementText() throws XMLStreamException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public int next() throws XMLStreamException {
        try {
            this.readNext();
            return this.eventQueue.peek().getEventType();
        }
        catch (IOException ex) {
            Logger.getLogger(JsonXmlStreamReader.class.getName()).log(Level.SEVERE, null, ex);
            throw new XMLStreamException(ex);
        }
    }
    
    @Override
    public int nextTag() throws XMLStreamException {
        int eventType;
        for (eventType = this.next(); (eventType == 4 && this.isWhiteSpace()) || (eventType == 12 && this.isWhiteSpace()) || eventType == 6 || eventType == 3 || eventType == 5; eventType = this.next()) {}
        if (eventType != 1 && eventType != 2) {
            throw new XMLStreamException("expected start or end tag", this.getLocation());
        }
        return eventType;
    }
    
    @Override
    public boolean hasNext() throws XMLStreamException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void close() throws XMLStreamException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String getNamespaceURI(final String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public boolean isStartElement() {
        return this.eventQueue.peek().isStartElement();
    }
    
    @Override
    public boolean isEndElement() {
        return this.eventQueue.peek().isEndElement();
    }
    
    @Override
    public boolean isCharacters() {
        return this.eventQueue.peek().isCharacters();
    }
    
    @Override
    public boolean isWhiteSpace() {
        return false;
    }
    
    @Override
    public String getAttributeValue(final String namespaceURI, final String localName) {
        return this.eventQueue.peek().getAttributeValue(namespaceURI, localName);
    }
    
    @Override
    public int getAttributeCount() {
        try {
            if (!this.eventQueue.peek().attributesChecked) {
                this.elemsExpected.clear();
                this.qNamesOfExpElems.clear();
                this.attrsExpected.clear();
                this.qNamesOfExpAttrs.clear();
                final UnmarshallingContext uctx = UnmarshallingContext.getInstance();
                if (uctx != null) {
                    try {
                        final Collection<QName> currExpElems = uctx.getCurrentExpectedElements();
                        for (final QName n : currExpElems) {
                            final String nu = n.getNamespaceURI();
                            if (nu != null && nu.equals("\u0000")) {
                                this.elemsExpected.add("$");
                                this.qNamesOfExpElems.put("$", null);
                            }
                            else {
                                this.elemsExpected.add(n.getLocalPart());
                                this.qNamesOfExpElems.put(n.getLocalPart(), n);
                            }
                        }
                    }
                    catch (NullPointerException ex2) {}
                    if (this.properJAXBVersion) {
                        try {
                            final Collection<QName> currExpAttrs = uctx.getCurrentExpectedAttributes();
                            for (final QName n : currExpAttrs) {
                                this.attrsExpected.add(n.getLocalPart());
                                this.qNamesOfExpAttrs.put(n.getLocalPart(), n);
                            }
                        }
                        catch (NullPointerException npe) {}
                        catch (NoSuchMethodError nsme) {
                            this.properJAXBVersion = false;
                            Logger.getLogger(Jackson2StaxReader.class.getName()).log(Level.SEVERE, ImplMessages.ERROR_JAXB_RI_2_1_12_MISSING(), nsme);
                        }
                    }
                }
                this.readNext(true);
                this.eventQueue.peek().attributesChecked = true;
            }
        }
        catch (IOException ex) {
            Logger.getLogger(Jackson2StaxReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.eventQueue.peek().getAttributeCount();
    }
    
    @Override
    public QName getAttributeName(final int index) {
        return this.eventQueue.peek().getAttributeName(index);
    }
    
    @Override
    public String getAttributeNamespace(final int index) {
        return this.eventQueue.peek().getAttributeNamespace(index);
    }
    
    @Override
    public String getAttributeLocalName(final int index) {
        return this.eventQueue.peek().getAttributeLocalName(index);
    }
    
    @Override
    public String getAttributePrefix(final int index) {
        return this.eventQueue.peek().getAttributePrefix(index);
    }
    
    @Override
    public String getAttributeType(final int index) {
        return this.eventQueue.peek().getAttributeType(index);
    }
    
    @Override
    public String getAttributeValue(final int index) {
        return this.eventQueue.peek().getAttributeValue(index);
    }
    
    @Override
    public boolean isAttributeSpecified(final int index) {
        return this.eventQueue.peek().isAttributeSpecified(index);
    }
    
    @Override
    public int getNamespaceCount() {
        return this.namespaceContext.getNamespaceCount();
    }
    
    @Override
    public String getNamespacePrefix(final int idx) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String getNamespaceURI(final int idx) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public NamespaceContext getNamespaceContext() {
        return this.namespaceContext;
    }
    
    @Override
    public int getEventType() {
        return this.eventQueue.peek().getEventType();
    }
    
    @Override
    public String getText() {
        return this.eventQueue.peek().getText();
    }
    
    @Override
    public char[] getTextCharacters() {
        return this.eventQueue.peek().getTextCharacters();
    }
    
    @Override
    public int getTextCharacters(final int sourceStart, final char[] target, final int targetStart, final int length) throws XMLStreamException {
        return this.eventQueue.peek().getTextCharacters(sourceStart, target, targetStart, length);
    }
    
    @Override
    public int getTextStart() {
        return this.eventQueue.peek().getTextStart();
    }
    
    @Override
    public int getTextLength() {
        return this.eventQueue.peek().getTextLength();
    }
    
    @Override
    public String getEncoding() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public boolean hasText() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Location getLocation() {
        return this.eventQueue.peek().getLocation();
    }
    
    @Override
    public QName getName() {
        return this.eventQueue.peek().getName();
    }
    
    @Override
    public String getLocalName() {
        return this.eventQueue.peek().getLocalName();
    }
    
    @Override
    public boolean hasName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String getNamespaceURI() {
        return this.eventQueue.peek().getName().getNamespaceURI();
    }
    
    @Override
    public String getPrefix() {
        return this.eventQueue.peek().getPrefix();
    }
    
    @Override
    public String getVersion() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public boolean isStandalone() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public boolean standaloneSet() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String getCharacterEncodingScheme() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String getPITarget() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String getPIData() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    static {
        valueTokens = new HashSet<JsonToken>() {
            {
                this.add(JsonToken.VALUE_FALSE);
                this.add(JsonToken.VALUE_TRUE);
                this.add(JsonToken.VALUE_NULL);
                this.add(JsonToken.VALUE_STRING);
                this.add(JsonToken.VALUE_NUMBER_FLOAT);
                this.add(JsonToken.VALUE_NUMBER_INT);
            }
        };
    }
    
    private static class ProcessingInfo
    {
        QName name;
        boolean isArray;
        boolean isFirstElement;
        
        ProcessingInfo(final QName name, final boolean isArray, final boolean isFirstElement) {
            this.name = name;
            this.isArray = isArray;
            this.isFirstElement = isFirstElement;
        }
    }
}
