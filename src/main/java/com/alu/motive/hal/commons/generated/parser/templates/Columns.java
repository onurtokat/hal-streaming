// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.commons.generated.parser.templates;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "columns", propOrder = { "column" })
public class Columns
{
    @XmlElement(required = true)
    protected List<Column> column;
    
    public List<Column> getColumn() {
        if (this.column == null) {
            this.column = new ArrayList<Column>();
        }
        return this.column;
    }
}
