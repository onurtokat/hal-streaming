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
@XmlType(name = "", propOrder = { "group" })
@XmlRootElement(name = "DataMappingTemplate")
public class DataMappingTemplate
{
    @XmlElement(name = "Group", required = true)
    protected List<Group> group;
    
    public List<Group> getGroup() {
        if (this.group == null) {
            this.group = new ArrayList<Group>();
        }
        return this.group;
    }
}
