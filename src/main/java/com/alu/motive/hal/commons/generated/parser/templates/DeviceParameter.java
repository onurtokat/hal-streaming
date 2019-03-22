// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.commons.generated.parser.templates;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeviceParameter", propOrder = { "name", "path", "conversionScript" })
public class DeviceParameter
{
    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "Path")
    protected String path;
    @XmlElement(name = "ConversionScript")
    protected String conversionScript;
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String value) {
        this.name = value;
    }
    
    public String getPath() {
        return this.path;
    }
    
    public void setPath(final String value) {
        this.path = value;
    }
    
    public String getConversionScript() {
        return this.conversionScript;
    }
    
    public void setConversionScript(final String value) {
        this.conversionScript = value;
    }
}
