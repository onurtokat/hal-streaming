// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.json.impl.reader;

import javax.xml.stream.Location;

public class EndDocumentEvent extends JsonReaderXmlEvent
{
    public EndDocumentEvent(final Location location) {
        this.location = location;
    }
    
    @Override
    public boolean isEndDocument() {
        return true;
    }
    
    @Override
    public int getEventType() {
        return 8;
    }
    
    @Override
    public String toString() {
        return "EndDocumentEvent()";
    }
}
