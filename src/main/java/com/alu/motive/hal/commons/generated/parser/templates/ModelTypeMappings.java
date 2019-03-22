// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.commons.generated.parser.templates;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "mapping" })
@XmlRootElement(name = "modelTypeMappings")
public class ModelTypeMappings
{
    @XmlElement(required = true)
    protected List<Mapping> mapping;
    
    public List<Mapping> getMapping() {
        if (this.mapping == null) {
            this.mapping = new ArrayList<Mapping>();
        }
        return this.mapping;
    }
}
