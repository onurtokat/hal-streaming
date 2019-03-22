// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.ae.client.generated.parser.templates;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "id", "name", "sourceTable", "targetTable", "actionType", "clazz", "masterClass", "description", "version", "sourceFiltering", "cleaningFiltering", "attribute" })
@XmlRootElement(name = "AttributesTemplate")
public class AttributesTemplate
{
    @XmlElement(name = "ID", required = true)
    protected String id;
    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "SourceTable", required = true)
    protected String sourceTable;
    @XmlElement(name = "TargetTable", required = true)
    protected String targetTable;
    @XmlElement(name = "ActionType", required = true)
    protected String actionType;
    @XmlElement(name = "Class", required = true)
    protected String clazz;
    @XmlElement(name = "MasterClass", required = true)
    protected String masterClass;
    @XmlElement(name = "Description", required = true)
    protected String description;
    @XmlElement(name = "Version", required = true)
    protected String version;
    @XmlElement(name = "SourceFiltering", required = true)
    protected String sourceFiltering;
    @XmlElement(name = "CleaningFiltering", required = true)
    protected String cleaningFiltering;
    @XmlElement(name = "Attribute")
    protected List<Attribute> attribute;
    
    public String getID() {
        return this.id;
    }
    
    public void setID(final String value) {
        this.id = value;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String value) {
        this.name = value;
    }
    
    public String getSourceTable() {
        return this.sourceTable;
    }
    
    public void setSourceTable(final String value) {
        this.sourceTable = value;
    }
    
    public String getTargetTable() {
        return this.targetTable;
    }
    
    public void setTargetTable(final String value) {
        this.targetTable = value;
    }
    
    public String getActionType() {
        return this.actionType;
    }
    
    public void setActionType(final String value) {
        this.actionType = value;
    }
    
    public String getClazz() {
        return this.clazz;
    }
    
    public void setClazz(final String value) {
        this.clazz = value;
    }
    
    public String getMasterClass() {
        return this.masterClass;
    }
    
    public void setMasterClass(final String value) {
        this.masterClass = value;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(final String value) {
        this.description = value;
    }
    
    public String getVersion() {
        return this.version;
    }
    
    public void setVersion(final String value) {
        this.version = value;
    }
    
    public String getSourceFiltering() {
        return this.sourceFiltering;
    }
    
    public void setSourceFiltering(final String value) {
        this.sourceFiltering = value;
    }
    
    public String getCleaningFiltering() {
        return this.cleaningFiltering;
    }
    
    public void setCleaningFiltering(final String value) {
        this.cleaningFiltering = value;
    }
    
    public List<Attribute> getAttribute() {
        if (this.attribute == null) {
            this.attribute = new ArrayList<Attribute>();
        }
        return this.attribute;
    }
    
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = { "id", "formula" })
    public static class Attribute
    {
        @XmlElement(name = "ID", required = true)
        protected String id;
        @XmlElement(name = "Formula", required = true)
        protected List<Formula> formula;
        
        public String getID() {
            return this.id;
        }
        
        public void setID(final String value) {
            this.id = value;
        }
        
        public List<Formula> getFormula() {
            if (this.formula == null) {
                this.formula = new ArrayList<Formula>();
            }
            return this.formula;
        }
        
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = { "attributesFilter", "expression" })
        public static class Formula
        {
            @XmlElement(name = "AttributesFilter", required = true)
            protected String attributesFilter;
            @XmlElement(name = "Expression", required = true)
            protected String expression;
            
            public String getAttributesFilter() {
                return this.attributesFilter;
            }
            
            public void setAttributesFilter(final String value) {
                this.attributesFilter = value;
            }
            
            public String getExpression() {
                return this.expression;
            }
            
            public void setExpression(final String value) {
                this.expression = value;
            }
        }
    }
}
