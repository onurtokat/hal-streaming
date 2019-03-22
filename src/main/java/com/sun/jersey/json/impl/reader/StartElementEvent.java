// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.json.impl.reader;

import javax.xml.stream.Location;
import javax.xml.namespace.QName;

public class StartElementEvent extends JsonReaderXmlEvent
{
    public StartElementEvent(final QName name, final Location location) {
        this.name = name;
        this.location = location;
    }
    
    @Override
    public boolean isStartElement() {
        return true;
    }
    
    @Override
    public int getEventType() {
        return 1;
    }
    
    @Override
    public String toString() {
        return "StartElementEvent(" + this.name + ")";
    }
}
