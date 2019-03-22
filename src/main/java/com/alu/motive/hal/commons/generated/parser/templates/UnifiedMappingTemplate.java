// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.commons.generated.parser.templates;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "columns" })
@XmlRootElement(name = "UnifiedMappingTemplate")
public class UnifiedMappingTemplate
{
    @XmlElement(required = true)
    protected Columns columns;
    
    public Columns getColumns() {
        return this.columns;
    }
    
    public void setColumns(final Columns value) {
        this.columns = value;
    }
}
