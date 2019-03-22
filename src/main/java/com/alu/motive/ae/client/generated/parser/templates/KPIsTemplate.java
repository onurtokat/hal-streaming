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
@XmlType(name = "", propOrder = { "id", "name", "sourceTable", "targetKPITable", "description", "version", "sourceFilter", "cassandraExportTable", "kpi" })
@XmlRootElement(name = "KPIsTemplate")
public class KPIsTemplate
{
    @XmlElement(name = "ID", required = true)
    protected String id;
    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "SourceTable", required = true)
    protected String sourceTable;
    @XmlElement(name = "TargetKPITable", required = true)
    protected String targetKPITable;
    @XmlElement(name = "Description", required = true)
    protected String description;
    @XmlElement(name = "Version", required = true)
    protected String version;
    @XmlElement(name = "SourceFilter", required = true)
    protected String sourceFilter;
    @XmlElement(name = "CassandraExportTable")
    protected String cassandraExportTable;
    @XmlElement(name = "KPI")
    protected List<KPI> kpi;
    
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
    
    public String getTargetKPITable() {
        return this.targetKPITable;
    }
    
    public void setTargetKPITable(final String value) {
        this.targetKPITable = value;
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
    
    public String getSourceFilter() {
        return this.sourceFilter;
    }
    
    public void setSourceFilter(final String value) {
        this.sourceFilter = value;
    }
    
    public String getCassandraExportTable() {
        return this.cassandraExportTable;
    }
    
    public void setCassandraExportTable(final String value) {
        this.cassandraExportTable = value;
    }
    
    public List<KPI> getKPI() {
        if (this.kpi == null) {
            this.kpi = new ArrayList<KPI>();
        }
        return this.kpi;
    }
    
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = { "id", "decimalPlaces", "toBeExported", "derivedFlag", "domain", "category", "formula" })
    public static class KPI
    {
        @XmlElement(name = "ID", required = true)
        protected String id;
        @XmlElement(name = "DecimalPlaces")
        protected int decimalPlaces;
        @XmlElement(name = "ToBeExported")
        protected boolean toBeExported;
        @XmlElement(name = "DerivedFlag")
        protected boolean derivedFlag;
        @XmlElement(name = "Domain")
        protected String domain;
        @XmlElement(name = "Category")
        protected String category;
        @XmlElement(name = "Formula", required = true)
        protected List<Formula> formula;
        
        public String getID() {
            return this.id;
        }
        
        public void setID(final String value) {
            this.id = value;
        }
        
        public int getDecimalPlaces() {
            return this.decimalPlaces;
        }
        
        public void setDecimalPlaces(final int value) {
            this.decimalPlaces = value;
        }
        
        public boolean isToBeExported() {
            return this.toBeExported;
        }
        
        public void setToBeExported(final boolean value) {
            this.toBeExported = value;
        }
        
        public boolean isDerivedFlag() {
            return this.derivedFlag;
        }
        
        public void setDerivedFlag(final boolean value) {
            this.derivedFlag = value;
        }
        
        public String getDomain() {
            return this.domain;
        }
        
        public void setDomain(final String value) {
            this.domain = value;
        }
        
        public String getCategory() {
            return this.category;
        }
        
        public void setCategory(final String value) {
            this.category = value;
        }
        
        public List<Formula> getFormula() {
            if (this.formula == null) {
                this.formula = new ArrayList<Formula>();
            }
            return this.formula;
        }
        
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = { "kpiFilter", "expression" })
        public static class Formula
        {
            @XmlElement(name = "KPIFilter", required = true)
            protected String kpiFilter;
            @XmlElement(name = "Expression", required = true)
            protected String expression;
            
            public String getKPIFilter() {
                return this.kpiFilter;
            }
            
            public void setKPIFilter(final String value) {
                this.kpiFilter = value;
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
