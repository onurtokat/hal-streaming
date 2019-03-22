// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.commons.generated.parser.templates;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Mapping", propOrder = { "deviceType" })
public class Mapping
{
    @XmlElement(required = true)
    protected List<DeviceType> deviceType;
    @XmlAttribute(name = "type", required = true)
    protected String type;
    
    public List<DeviceType> getDeviceType() {
        if (this.deviceType == null) {
            this.deviceType = new ArrayList<DeviceType>();
        }
        return this.deviceType;
    }
    
    public String getType() {
        return this.type;
    }
    
    public void setType(final String value) {
        this.type = value;
    }
}
