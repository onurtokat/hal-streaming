// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.json.impl.reader;

import javax.xml.stream.Location;

public class CharactersEvent extends JsonReaderXmlEvent
{
    public CharactersEvent(final String text, final Location location) {
        this.text = text;
        this.location = location;
    }
    
    @Override
    public boolean isCharacters() {
        return true;
    }
    
    @Override
    public int getEventType() {
        return 4;
    }
    
    @Override
    public String toString() {
        return "CharactersEvent(" + this.text + ")";
    }
}
