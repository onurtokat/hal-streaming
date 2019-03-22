// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.json.impl.reader;

import java.util.HashSet;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import java.util.logging.Level;
import javax.xml.stream.Location;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.io.IOException;
import com.sun.jersey.api.json.JSONConfiguration;
import java.io.Reader;
import java.util.Set;
import java.util.List;
import java.util.Queue;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamReader;

public class JsonXmlStreamReader implements XMLStreamReader
{
    private static final Logger LOGGER;
    boolean jsonRootUnwrapping;
    String rootElementName;
    char nsSeparator;
    CharSequence nsSeparatorAsSequence;
    final Map<String, String> revertedXml2JsonNs;
    final Collection<String> attrAsElemNames;
    JsonLexer lexer;
    JsonToken lastToken;
    final Queue<JsonReaderXmlEvent> eventQueue;
    List<ProcessingState> processingStack;
    int depth;
    private static final Set<Integer> valueTokenTypes;
    
    public JsonXmlStreamReader(final Reader reader, final JSONConfiguration config) throws IOException {
        this(reader, config.isRootUnwrapping() ? "rootElement" : null, config);
    }
    
    public JsonXmlStreamReader(final Reader reader, final String rootElementName) throws IOException {
        this(reader, rootElementName, JSONConfiguration.DEFAULT);
    }
    
    public JsonXmlStreamReader(final Reader reader, final String rootElementName, final JSONConfiguration config) throws IOException {
        this.revertedXml2JsonNs = new HashMap<String, String>();
        this.attrAsElemNames = new LinkedList<String>();
        this.eventQueue = new LinkedList<JsonReaderXmlEvent>();
        this.jsonRootUnwrapping = (rootElementName != null);
        this.rootElementName = rootElementName;
        if (config.getAttributeAsElements() != null) {
            this.attrAsElemNames.addAll(config.getAttributeAsElements());
        }
        if (config.getXml2JsonNs() != null) {
            for (final String uri : config.getXml2JsonNs().keySet()) {
                this.revertedXml2JsonNs.put(config.getXml2JsonNs().get(uri), uri);
            }
        }
        this.nsSeparator = config.getNsSeparator();
        this.nsSeparatorAsSequence = new StringBuffer(1).append(this.nsSeparator);
        this.lexer = new JsonLexer(reader);
        this.depth = 0;
        (this.processingStack = new ArrayList<ProcessingState>()).add(new ProcessingState());
        this.readNext();
    }
    
    void colon() throws IOException {
        final JsonToken token = this.nextToken();
        if (token.tokenType != 5) {
            throw new IOException("Colon expected instead of \"" + token.tokenText + "\"");
        }
    }
    
    JsonToken nextToken() throws IOException {
        final JsonToken result = this.lexer.yylex();
        return result;
    }
    
    private void valueRead() {
        if (LaState.BEFORE_VALUE_IN_KV_PAIR == this.processingStack.get(this.depth).state) {
            this.processingStack.get(this.depth).state = LaState.AFTER_OBJ_KV_PAIR;
        }
        else if (LaState.BEFORE_NEXT_ARRAY_ELEM == this.processingStack.get(this.depth).state) {
            this.processingStack.get(this.depth).state = LaState.AFTER_ARRAY_ELEM;
        }
        else if (LaState.AFTER_ARRAY_START_BRACE == this.processingStack.get(this.depth).state) {
            this.processingStack.get(this.depth).state = LaState.AFTER_ARRAY_ELEM;
        }
    }
    
    private void readNext() throws IOException {
        this.readNext(false);
    }
    
    private void readNext(boolean checkAttributesOnly) throws IOException {
        if (!checkAttributesOnly) {
            this.eventQueue.poll();
        }
        while (this.eventQueue.isEmpty() || checkAttributesOnly) {
            this.lastToken = this.nextToken();
            if (null == this.lastToken || LaState.END == this.processingStack.get(this.depth).state) {
                if (this.jsonRootUnwrapping) {
                    this.generateEEEvent(this.processingStack.get(this.depth).lastName);
                }
                this.eventQueue.add(new EndDocumentEvent(new StaxLocation(this.lexer)));
                break;
            }
            switch (this.processingStack.get(this.depth).state) {
                case START: {
                    if (0 == this.depth) {
                        this.eventQueue.add(new StartDocumentEvent(new StaxLocation(this.lexer)));
                        this.processingStack.get(this.depth).state = LaState.AFTER_OBJ_START_BRACE;
                        if (this.jsonRootUnwrapping) {
                            this.processingStack.get(this.depth).lastName = this.rootElementName;
                            final StartElementEvent event = this.generateSEEvent(this.processingStack.get(this.depth).lastName);
                            this.processingStack.get(this.depth).eventToReadAttributesFor = event;
                        }
                        switch (this.lastToken.tokenType) {
                            case 1: {
                                this.processingStack.add(new ProcessingState(LaState.AFTER_OBJ_START_BRACE));
                                ++this.depth;
                                break;
                            }
                            case 3: {
                                this.processingStack.add(new ProcessingState(LaState.AFTER_ARRAY_START_BRACE));
                                ++this.depth;
                                break;
                            }
                            case 7:
                            case 8:
                            case 9:
                            case 10:
                            case 11: {
                                this.eventQueue.add(new CharactersEvent(this.lastToken.tokenText, new StaxLocation(this.lexer)));
                                this.processingStack.get(this.depth).state = LaState.END;
                                break;
                            }
                        }
                    }
                    this.processingStack.get(this.depth).state = LaState.AFTER_OBJ_START_BRACE;
                    continue;
                }
                case AFTER_OBJ_START_BRACE: {
                    switch (this.lastToken.tokenType) {
                        case 7: {
                            if (!this.lastToken.tokenText.startsWith("@") && !this.attrAsElemNames.contains(this.lastToken.tokenText)) {
                                final StartElementEvent event = this.generateSEEvent(this.lastToken.tokenText);
                                this.processingStack.get(this.depth).eventToReadAttributesFor = event;
                                checkAttributesOnly = false;
                                this.processingStack.get(this.depth).lastName = this.lastToken.tokenText;
                                this.colon();
                                this.processingStack.get(this.depth).state = LaState.BEFORE_VALUE_IN_KV_PAIR;
                                continue;
                            }
                            final String attrName = this.lastToken.tokenText.startsWith("@") ? this.lastToken.tokenText : ("@" + this.lastToken.tokenText);
                            this.colon();
                            this.lastToken = this.nextToken();
                            if (!JsonXmlStreamReader.valueTokenTypes.contains(this.lastToken.tokenType)) {
                                throw new IOException("Attribute value expected instead of \"" + this.lastToken.tokenText + "\"");
                            }
                            if (null != this.processingStack.get(this.depth - 1).eventToReadAttributesFor) {
                                this.processingStack.get(this.depth - 1).eventToReadAttributesFor.addAttribute(this.createQName(attrName.substring(1)), this.lastToken.tokenText);
                            }
                            this.lastToken = this.nextToken();
                            switch (this.lastToken.tokenType) {
                                case 2: {
                                    this.processingStack.remove(this.depth);
                                    --this.depth;
                                    this.valueRead();
                                    checkAttributesOnly = false;
                                    continue;
                                }
                                case 6: {
                                    continue;
                                }
                                default: {
                                    throw new IOException("'\"', or '}' expected instead of \"" + this.lastToken.tokenText + "\"");
                                }
                            }
                            continue;
                        }
                        case 2: {
                            this.generateEEEvent(this.processingStack.get(this.depth).lastName);
                            checkAttributesOnly = false;
                            this.processingStack.remove(this.depth);
                            --this.depth;
                            this.valueRead();
                            continue;
                        }
                        default: {
                            continue;
                        }
                    }
                    break;
                }
                case BEFORE_OBJ_NEXT_KV_PAIR: {
                    switch (this.lastToken.tokenType) {
                        case 7: {
                            final StartElementEvent event = this.generateSEEvent(this.lastToken.tokenText);
                            this.processingStack.get(this.depth).eventToReadAttributesFor = event;
                            this.processingStack.get(this.depth).lastName = this.lastToken.tokenText;
                            this.colon();
                            this.processingStack.get(this.depth).state = LaState.BEFORE_VALUE_IN_KV_PAIR;
                            continue;
                        }
                    }
                    continue;
                }
                case BEFORE_VALUE_IN_KV_PAIR: {
                    switch (this.lastToken.tokenType) {
                        case 1: {
                            this.processingStack.add(new ProcessingState(LaState.AFTER_OBJ_START_BRACE));
                            ++this.depth;
                            continue;
                        }
                        case 3: {
                            this.processingStack.add(new ProcessingState(LaState.AFTER_ARRAY_START_BRACE));
                            ++this.depth;
                            continue;
                        }
                        case 7:
                        case 8:
                        case 9:
                        case 10: {
                            this.eventQueue.add(new CharactersEvent(this.lastToken.tokenText, new StaxLocation(this.lexer)));
                            this.processingStack.get(this.depth).state = LaState.AFTER_OBJ_KV_PAIR;
                            continue;
                        }
                        case 11: {
                            this.processingStack.get(this.depth).state = LaState.AFTER_OBJ_KV_PAIR;
                            continue;
                        }
                        default: {
                            continue;
                        }
                    }
                    break;
                }
                case AFTER_OBJ_KV_PAIR: {
                    switch (this.lastToken.tokenType) {
                        case 6: {
                            this.processingStack.get(this.depth).state = LaState.BEFORE_OBJ_NEXT_KV_PAIR;
                            this.generateEEEvent(this.processingStack.get(this.depth).lastName);
                            continue;
                        }
                        case 2: {
                            this.generateEEEvent(this.processingStack.get(this.depth).lastName);
                            this.processingStack.remove(this.depth);
                            --this.depth;
                            this.valueRead();
                            continue;
                        }
                        default: {
                            continue;
                        }
                    }
                    break;
                }
                case AFTER_ARRAY_START_BRACE: {
                    switch (this.lastToken.tokenType) {
                        case 1: {
                            this.processingStack.add(new ProcessingState(LaState.AFTER_OBJ_START_BRACE));
                            this.processingStack.get(this.depth).eventToReadAttributesFor = this.processingStack.get(this.depth - 1).eventToReadAttributesFor;
                            ++this.depth;
                            continue;
                        }
                        case 3: {
                            this.processingStack.add(new ProcessingState(LaState.AFTER_ARRAY_START_BRACE));
                            ++this.depth;
                            continue;
                        }
                        case 4: {
                            this.processingStack.remove(this.depth);
                            --this.depth;
                            this.valueRead();
                            continue;
                        }
                        case 7: {
                            this.eventQueue.add(new CharactersEvent(this.lastToken.tokenText, new StaxLocation(this.lexer)));
                            this.processingStack.get(this.depth).state = LaState.AFTER_ARRAY_ELEM;
                            continue;
                        }
                        default: {
                            continue;
                        }
                    }
                    break;
                }
                case BEFORE_NEXT_ARRAY_ELEM: {
                    final StartElementEvent event = this.generateSEEvent(this.processingStack.get(this.depth - 1).lastName);
                    switch (this.lastToken.tokenType) {
                        case 1: {
                            this.processingStack.add(new ProcessingState(LaState.AFTER_OBJ_START_BRACE));
                            this.processingStack.get(this.depth).eventToReadAttributesFor = event;
                            ++this.depth;
                            continue;
                        }
                        case 3: {
                            this.processingStack.add(new ProcessingState(LaState.AFTER_ARRAY_START_BRACE));
                            ++this.depth;
                            continue;
                        }
                        case 7: {
                            this.eventQueue.add(new CharactersEvent(this.lastToken.tokenText, new StaxLocation(this.lexer)));
                            this.processingStack.get(this.depth).state = LaState.AFTER_ARRAY_ELEM;
                            continue;
                        }
                        default: {
                            continue;
                        }
                    }
                    break;
                }
                case AFTER_ARRAY_ELEM: {
                    switch (this.lastToken.tokenType) {
                        case 4: {
                            this.processingStack.remove(this.depth);
                            --this.depth;
                            this.valueRead();
                            continue;
                        }
                        case 6: {
                            this.processingStack.get(this.depth).state = LaState.BEFORE_NEXT_ARRAY_ELEM;
                            this.generateEEEvent(this.processingStack.get(this.depth - 1).lastName);
                            continue;
                        }
                    }
                    break;
                }
            }
        }
    }
    
    @Override
    public int getAttributeCount() {
        JsonXmlStreamReader.LOGGER.entering(JsonXmlStreamReader.class.getName(), "getAttributeCount");
        assert !this.eventQueue.isEmpty();
        if (!this.eventQueue.peek().attributesChecked) {
            try {
                this.readNext(true);
            }
            catch (IOException ex) {}
            this.eventQueue.peek().attributesChecked = true;
        }
        final int result = this.eventQueue.peek().getAttributeCount();
        JsonXmlStreamReader.LOGGER.exiting(JsonXmlStreamReader.class.getName(), "getAttributeCount", result);
        return result;
    }
    
    @Override
    public int getEventType() {
        JsonXmlStreamReader.LOGGER.entering(JsonXmlStreamReader.class.getName(), "getEventType");
        assert !this.eventQueue.isEmpty();
        final int result = this.eventQueue.peek().getEventType();
        JsonXmlStreamReader.LOGGER.exiting(JsonXmlStreamReader.class.getName(), "getEventType", result);
        return result;
    }
    
    @Override
    public int getNamespaceCount() {
        JsonXmlStreamReader.LOGGER.entering(JsonXmlStreamReader.class.getName(), "getNamespaceCount");
        JsonXmlStreamReader.LOGGER.exiting(JsonXmlStreamReader.class.getName(), "getNamespaceCount", 0);
        return 0;
    }
    
    @Override
    public int getTextLength() {
        JsonXmlStreamReader.LOGGER.entering(JsonXmlStreamReader.class.getName(), "getTextLength");
        assert !this.eventQueue.isEmpty();
        final int result = this.eventQueue.peek().getTextLength();
        JsonXmlStreamReader.LOGGER.exiting(JsonXmlStreamReader.class.getName(), "getTextLength", result);
        return result;
    }
    
    @Override
    public int getTextStart() {
        JsonXmlStreamReader.LOGGER.entering(JsonXmlStreamReader.class.getName(), "getTextStart");
        assert !this.eventQueue.isEmpty();
        final int result = this.eventQueue.peek().getTextStart();
        JsonXmlStreamReader.LOGGER.exiting(JsonXmlStreamReader.class.getName(), "getTextStart", result);
        return result;
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
    public void close() throws XMLStreamException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public boolean hasName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public boolean hasNext() throws XMLStreamException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public boolean hasText() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public boolean isCharacters() {
        JsonXmlStreamReader.LOGGER.entering(JsonXmlStreamReader.class.getName(), "isCharacters");
        assert !this.eventQueue.isEmpty();
        final boolean result = this.eventQueue.peek().isCharacters();
        JsonXmlStreamReader.LOGGER.exiting(JsonXmlStreamReader.class.getName(), "isCharacters", result);
        return result;
    }
    
    @Override
    public boolean isEndElement() {
        JsonXmlStreamReader.LOGGER.entering(JsonXmlStreamReader.class.getName(), "isEndElement");
        assert !this.eventQueue.isEmpty();
        final boolean result = this.eventQueue.peek().isEndElement();
        JsonXmlStreamReader.LOGGER.exiting(JsonXmlStreamReader.class.getName(), "isEndElement", result);
        return result;
    }
    
    @Override
    public boolean isStandalone() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public boolean isStartElement() {
        JsonXmlStreamReader.LOGGER.entering(JsonXmlStreamReader.class.getName(), "isStartElement");
        assert !this.eventQueue.isEmpty();
        final boolean result = this.eventQueue.peek().isStartElement();
        JsonXmlStreamReader.LOGGER.exiting(JsonXmlStreamReader.class.getName(), "isStartElement", result);
        return result;
    }
    
    @Override
    public boolean isWhiteSpace() {
        JsonXmlStreamReader.LOGGER.entering(JsonXmlStreamReader.class.getName(), "isWhiteSpace");
        final boolean result = false;
        JsonXmlStreamReader.LOGGER.exiting(JsonXmlStreamReader.class.getName(), "isWhiteSpace", result);
        return result;
    }
    
    @Override
    public boolean standaloneSet() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public char[] getTextCharacters() {
        JsonXmlStreamReader.LOGGER.entering(JsonXmlStreamReader.class.getName(), "getTextCharacters");
        assert !this.eventQueue.isEmpty();
        final char[] result = this.eventQueue.peek().getTextCharacters();
        JsonXmlStreamReader.LOGGER.exiting(JsonXmlStreamReader.class.getName(), "getTextCharacters", result);
        return result;
    }
    
    @Override
    public boolean isAttributeSpecified(final int attribute) {
        JsonXmlStreamReader.LOGGER.entering(JsonXmlStreamReader.class.getName(), "isAttributeSpecified");
        assert !this.eventQueue.isEmpty();
        final boolean result = this.eventQueue.peek().isAttributeSpecified(attribute);
        JsonXmlStreamReader.LOGGER.exiting(JsonXmlStreamReader.class.getName(), "isAttributeSpecified", result);
        return result;
    }
    
    @Override
    public int getTextCharacters(final int sourceStart, final char[] target, final int targetStart, final int length) throws XMLStreamException {
        JsonXmlStreamReader.LOGGER.entering(JsonXmlStreamReader.class.getName(), "getTextCharacters");
        assert !this.eventQueue.isEmpty();
        final int result = this.eventQueue.peek().getTextCharacters(sourceStart, target, targetStart, length);
        JsonXmlStreamReader.LOGGER.exiting(JsonXmlStreamReader.class.getName(), "getTextCharacters", result);
        return result;
    }
    
    @Override
    public String getCharacterEncodingScheme() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String getElementText() throws XMLStreamException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String getEncoding() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String getLocalName() {
        JsonXmlStreamReader.LOGGER.entering(JsonXmlStreamReader.class.getName(), "getLocalName");
        assert !this.eventQueue.isEmpty();
        final String result = this.eventQueue.peek().getLocalName();
        JsonXmlStreamReader.LOGGER.exiting(JsonXmlStreamReader.class.getName(), "getLocalName", result);
        return result;
    }
    
    @Override
    public String getNamespaceURI() {
        JsonXmlStreamReader.LOGGER.entering(JsonXmlStreamReader.class.getName(), "getNamespaceURI");
        assert !this.eventQueue.isEmpty();
        final String result = this.eventQueue.peek().getName().getNamespaceURI();
        JsonXmlStreamReader.LOGGER.exiting(JsonXmlStreamReader.class.getName(), "getNamespaceURI", result);
        return result;
    }
    
    @Override
    public String getPIData() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String getPITarget() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String getPrefix() {
        JsonXmlStreamReader.LOGGER.entering(JsonXmlStreamReader.class.getName(), "getPrefix");
        assert !this.eventQueue.isEmpty();
        final String result = this.eventQueue.peek().getPrefix();
        JsonXmlStreamReader.LOGGER.exiting(JsonXmlStreamReader.class.getName(), "getPrefix", result);
        return result;
    }
    
    @Override
    public String getText() {
        JsonXmlStreamReader.LOGGER.entering(JsonXmlStreamReader.class.getName(), "getText");
        assert !this.eventQueue.isEmpty();
        final String result = this.eventQueue.peek().getText();
        JsonXmlStreamReader.LOGGER.exiting(JsonXmlStreamReader.class.getName(), "getText", result);
        return result;
    }
    
    @Override
    public String getVersion() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String getAttributeLocalName(final int index) {
        JsonXmlStreamReader.LOGGER.entering(JsonXmlStreamReader.class.getName(), "getAttributeLocalName");
        assert !this.eventQueue.isEmpty();
        final String result = this.eventQueue.peek().getAttributeLocalName(index);
        JsonXmlStreamReader.LOGGER.exiting(JsonXmlStreamReader.class.getName(), "getAttributeLocalName", result);
        return result;
    }
    
    @Override
    public QName getAttributeName(final int index) {
        JsonXmlStreamReader.LOGGER.entering(JsonXmlStreamReader.class.getName(), "getAttributeName");
        assert !this.eventQueue.isEmpty();
        final QName result = this.eventQueue.peek().getAttributeName(index);
        JsonXmlStreamReader.LOGGER.exiting(JsonXmlStreamReader.class.getName(), "getAttributeName", result);
        return result;
    }
    
    @Override
    public String getAttributeNamespace(final int index) {
        JsonXmlStreamReader.LOGGER.entering(JsonXmlStreamReader.class.getName(), "getAttributeNamespace");
        assert !this.eventQueue.isEmpty();
        final String result = this.eventQueue.peek().getAttributeNamespace(index);
        JsonXmlStreamReader.LOGGER.exiting(JsonXmlStreamReader.class.getName(), "getAttributeNamespace", result);
        return result;
    }
    
    @Override
    public String getAttributePrefix(final int index) {
        JsonXmlStreamReader.LOGGER.entering(JsonXmlStreamReader.class.getName(), "getAttributePrefix");
        assert !this.eventQueue.isEmpty();
        final String result = this.eventQueue.peek().getAttributePrefix(index);
        JsonXmlStreamReader.LOGGER.exiting(JsonXmlStreamReader.class.getName(), "getAttributePrefix", result);
        return result;
    }
    
    @Override
    public String getAttributeType(final int index) {
        JsonXmlStreamReader.LOGGER.entering(JsonXmlStreamReader.class.getName(), "getAttributeType");
        assert !this.eventQueue.isEmpty();
        final String result = this.eventQueue.peek().getAttributeType(index);
        JsonXmlStreamReader.LOGGER.exiting(JsonXmlStreamReader.class.getName(), "getAttributeType", result);
        return result;
    }
    
    @Override
    public String getAttributeValue(final int index) {
        JsonXmlStreamReader.LOGGER.entering(JsonXmlStreamReader.class.getName(), "getAttributeValue");
        assert !this.eventQueue.isEmpty();
        final String result = this.eventQueue.peek().getAttributeValue(index);
        JsonXmlStreamReader.LOGGER.exiting(JsonXmlStreamReader.class.getName(), "getAttributeValue", result);
        return result;
    }
    
    @Override
    public String getAttributeValue(final String namespaceURI, final String localName) {
        JsonXmlStreamReader.LOGGER.entering(JsonXmlStreamReader.class.getName(), "getAttributeValue");
        assert !this.eventQueue.isEmpty();
        final String result = this.eventQueue.peek().getAttributeValue(namespaceURI, localName);
        JsonXmlStreamReader.LOGGER.exiting(JsonXmlStreamReader.class.getName(), "getAttributeValue", result);
        return result;
    }
    
    @Override
    public String getNamespacePrefix(final int arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String getNamespaceURI(final int arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public NamespaceContext getNamespaceContext() {
        JsonXmlStreamReader.LOGGER.entering(JsonXmlStreamReader.class.getName(), "getNamespaceContext");
        final NamespaceContext result = new JsonNamespaceContext();
        JsonXmlStreamReader.LOGGER.exiting(JsonXmlStreamReader.class.getName(), "getNamespaceContext", result);
        return result;
    }
    
    @Override
    public QName getName() {
        JsonXmlStreamReader.LOGGER.entering(JsonXmlStreamReader.class.getName(), "getName");
        assert !this.eventQueue.isEmpty();
        final QName result = this.eventQueue.peek().getName();
        JsonXmlStreamReader.LOGGER.exiting(JsonXmlStreamReader.class.getName(), "getName");
        return result;
    }
    
    @Override
    public Location getLocation() {
        JsonXmlStreamReader.LOGGER.entering(JsonXmlStreamReader.class.getName(), "getLocation");
        assert !this.eventQueue.isEmpty();
        final Location result = this.eventQueue.peek().getLocation();
        JsonXmlStreamReader.LOGGER.exiting(JsonXmlStreamReader.class.getName(), "getLocation", result);
        return result;
    }
    
    @Override
    public Object getProperty(final String arg0) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void require(final int arg0, final String arg1, final String arg2) throws XMLStreamException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String getNamespaceURI(final String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private StartElementEvent generateSEEvent(final String name) {
        StartElementEvent event = null;
        if (!"$".equals(name)) {
            event = new StartElementEvent(this.createQName(name), new StaxLocation(this.lexer));
            this.eventQueue.add(event);
        }
        return event;
    }
    
    private void generateEEEvent(final String name) {
        if (null != name && !"$".equals(name)) {
            this.eventQueue.add(new EndElementEvent(this.createQName(name), new StaxLocation(this.lexer)));
        }
    }
    
    private QName createQName(final String name) {
        if (this.revertedXml2JsonNs.isEmpty() || !name.contains(this.nsSeparatorAsSequence)) {
            return new QName(name);
        }
        final int dotIndex = name.indexOf(this.nsSeparator);
        final String prefix = name.substring(0, dotIndex);
        final String suffix = name.substring(dotIndex + 1);
        return this.revertedXml2JsonNs.containsKey(prefix) ? new QName(this.revertedXml2JsonNs.get(prefix), suffix) : new QName(name);
    }
    
    static {
        LOGGER = Logger.getLogger(JsonXmlStreamReader.class.getName());
        valueTokenTypes = new HashSet<Integer>() {
            {
                this.add(10);
                this.add(9);
                this.add(11);
                this.add(8);
                this.add(7);
            }
        };
    }
    
    private enum LaState
    {
        START, 
        END, 
        AFTER_OBJ_START_BRACE, 
        BEFORE_OBJ_NEXT_KV_PAIR, 
        BEFORE_COLON_IN_KV_PAIR, 
        BEFORE_VALUE_IN_KV_PAIR, 
        AFTER_OBJ_KV_PAIR, 
        AFTER_ARRAY_START_BRACE, 
        BEFORE_NEXT_ARRAY_ELEM, 
        AFTER_ARRAY_ELEM;
    }
    
    private static final class ProcessingState
    {
        String lastName;
        LaState state;
        JsonReaderXmlEvent eventToReadAttributesFor;
        
        ProcessingState() {
            this(LaState.START);
        }
        
        ProcessingState(final LaState state) {
            this(state, null);
        }
        
        ProcessingState(final LaState state, final String name) {
            this.state = state;
            this.lastName = name;
        }
        
        @Override
        public String toString() {
            return String.format("{lastName:%s,laState:%s}", this.lastName, this.state);
        }
    }
}
