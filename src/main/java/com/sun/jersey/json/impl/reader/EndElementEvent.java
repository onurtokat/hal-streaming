// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.json.impl.reader;

import javax.xml.stream.Location;
import javax.xml.namespace.QName;

public class EndElementEvent extends JsonReaderXmlEvent
{
    public EndElementEvent(final QName name, final Location location) {
        this.name = name;
        this.location = location;
    }
    
    @Override
    public boolean isEndElement() {
        return true;
    }
    
    @Override
    public int getEventType() {
        return 2;
    }
    
    @Override
    public String toString() {
        return "EndElementEvent(" + this.name + ")";
    }
}
