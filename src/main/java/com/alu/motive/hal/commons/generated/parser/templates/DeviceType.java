// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.commons.generated.parser.templates;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeviceType", propOrder = { "productClass", "oui" })
public class DeviceType
{
    @XmlElement(required = true)
    protected String productClass;
    @XmlElement(required = true)
    protected String oui;
    
    public String getProductClass() {
        return this.productClass;
    }
    
    public void setProductClass(final String value) {
        this.productClass = value;
    }
    
    public String getOui() {
        return this.oui;
    }
    
    public void setOui(final String value) {
        this.oui = value;
    }
}
