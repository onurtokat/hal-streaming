// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.json.impl.reader;

import java.util.LinkedList;
import javax.xml.stream.XMLStreamException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;

public abstract class JsonReaderXmlEvent
{
    Location location;
    QName name;
    String text;
    List<Attribute> attributes;
    boolean attributesChecked;
    
    public abstract int getEventType();
    
    public boolean isAttribute() {
        return false;
    }
    
    public boolean isCharacters() {
        return false;
    }
    
    public boolean isEndDocument() {
        return false;
    }
    
    public boolean isEndElement() {
        return false;
    }
    
    public boolean isEntityReference() {
        return false;
    }
    
    public boolean isNamespace() {
        return false;
    }
    
    public boolean isProcessingInstruction() {
        return false;
    }
    
    public boolean isStartDocument() {
        return false;
    }
    
    public boolean isStartElement() {
        return false;
    }
    
    public int getAttributeCount() {
        return (null != this.attributes) ? this.attributes.size() : 0;
    }
    
    public String getAttributeLocalName(final int index) {
        return this.getAttributeName(index).getLocalPart();
    }
    
    public QName getAttributeName(final int index) {
        if (null == this.attributes || index >= this.attributes.size()) {
            throw new IndexOutOfBoundsException();
        }
        return this.attributes.get(index).name;
    }
    
    public String getAttributePrefix(final int index) {
        return this.getAttributeName(index).getPrefix();
    }
    
    public String getAttributeType(final int index) {
        return null;
    }
    
    public String getAttributeNamespace(final int index) {
        return this.getAttributeName(index).getNamespaceURI();
    }
    
    public String getAttributeValue(final int index) {
        if (null == this.attributes || index >= this.attributes.size()) {
            throw new IndexOutOfBoundsException();
        }
        return this.attributes.get(index).value;
    }
    
    public String getAttributeValue(final String namespaceURI, final String localName) {
        if (null == this.attributes || null == localName || "".equals(localName)) {
            throw new NoSuchElementException();
        }
        final QName askedFor = new QName(namespaceURI, localName);
        for (final Attribute a : this.attributes) {
            if (askedFor.equals(a.name)) {
                return a.value;
            }
        }
        throw new NoSuchElementException();
    }
    
    public boolean isAttributeSpecified(final int index) {
        return null != this.attributes && this.attributes.size() >= index;
    }
    
    public String getText() {
        if (null != this.text) {
            return this.text;
        }
        throw new IllegalStateException();
    }
    
    public char[] getTextCharacters() {
        if (null != this.text) {
            return this.text.toCharArray();
        }
        throw new IllegalStateException();
    }
    
    public int getTextCharacters(final int sourceStart, final char[] target, final int targetStart, final int length) throws XMLStreamException {
        if (null != this.text) {
            System.arraycopy(this.text.toCharArray(), sourceStart, target, targetStart, length);
            return length;
        }
        throw new IllegalStateException();
    }
    
    public int getTextStart() {
        if (null != this.text) {
            return 0;
        }
        throw new IllegalStateException();
    }
    
    public int getTextLength() {
        if (null != this.text) {
            return this.text.length();
        }
        throw new IllegalStateException();
    }
    
    public boolean hasName() {
        return null != this.name;
    }
    
    public QName getName() {
        if (null != this.name) {
            return this.name;
        }
        throw new UnsupportedOperationException();
    }
    
    public String getLocalName() {
        if (null != this.name) {
            return this.name.getLocalPart();
        }
        throw new UnsupportedOperationException();
    }
    
    public String getPrefix() {
        if (null != this.name) {
            return this.name.getPrefix();
        }
        return null;
    }
    
    public Location getLocation() {
        return this.location;
    }
    
    public void addAttribute(final QName name, final String value) {
        if (null == this.attributes) {
            this.attributes = new LinkedList<Attribute>();
        }
        this.attributes.add(new Attribute(name, value));
    }
    
    public static class Attribute
    {
        QName name;
        String value;
        
        public Attribute(final QName name, final String value) {
            this.name = name;
            this.value = value;
        }
    }
}
