// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.commons.generated.parser.templates;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "column", propOrder = { "normalizedTR181Name", "normalizedTR98Name" })
public class Column
{
    @XmlElement(name = "NormalizedTR181Name", required = true)
    protected String normalizedTR181Name;
    @XmlElement(name = "NormalizedTR98Name")
    protected String normalizedTR98Name;
    
    public String getNormalizedTR181Name() {
        return this.normalizedTR181Name;
    }
    
    public void setNormalizedTR181Name(final String value) {
        this.normalizedTR181Name = value;
    }
    
    public String getNormalizedTR98Name() {
        return this.normalizedTR98Name;
    }
    
    public void setNormalizedTR98Name(final String value) {
        this.normalizedTR98Name = value;
    }
}
