// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.json.impl.reader;

import javax.xml.stream.Location;

public class StartDocumentEvent extends JsonReaderXmlEvent
{
    public StartDocumentEvent(final Location location) {
        this.location = location;
    }
    
    @Override
    public boolean isStartDocument() {
        return true;
    }
    
    @Override
    public int getEventType() {
        return 7;
    }
    
    @Override
    public String toString() {
        return "StartDocumentEvent()";
    }
}
