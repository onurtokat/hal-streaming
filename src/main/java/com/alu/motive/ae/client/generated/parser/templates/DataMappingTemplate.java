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
@XmlType(name = "", propOrder = { "id", "name", "sourceTable", "sourceDataFilter", "sourceDataEntityFilter", "intermediateTable", "intermediateDataFilter", "intermediateDataTimeFilter", "targetTable", "actionType", "entityClass", "description", "version", "parameter" })
@XmlRootElement(name = "DataMappingTemplate")
public class DataMappingTemplate
{
    @XmlElement(name = "ID", required = true)
    protected String id;
    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "SourceTable", required = true)
    protected String sourceTable;
    @XmlElement(name = "SourceDataFilter", required = true)
    protected String sourceDataFilter;
    @XmlElement(name = "SourceDataEntityFilter", required = true)
    protected String sourceDataEntityFilter;
    @XmlElement(name = "IntermediateTable", required = true)
    protected String intermediateTable;
    @XmlElement(name = "IntermediateDataFilter", required = true)
    protected String intermediateDataFilter;
    @XmlElement(name = "IntermediateDataTimeFilter", required = true)
    protected String intermediateDataTimeFilter;
    @XmlElement(name = "TargetTable", required = true)
    protected String targetTable;
    @XmlElement(name = "ActionType", required = true)
    protected String actionType;
    @XmlElement(name = "EntityClass")
    protected String entityClass;
    @XmlElement(name = "Description", required = true)
    protected String description;
    @XmlElement(name = "Version", required = true)
    protected String version;
    @XmlElement(name = "Parameter")
    protected List<Parameter> parameter;
    
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
    
    public String getSourceDataFilter() {
        return this.sourceDataFilter;
    }
    
    public void setSourceDataFilter(final String value) {
        this.sourceDataFilter = value;
    }
    
    public String getSourceDataEntityFilter() {
        return this.sourceDataEntityFilter;
    }
    
    public void setSourceDataEntityFilter(final String value) {
        this.sourceDataEntityFilter = value;
    }
    
    public String getIntermediateTable() {
        return this.intermediateTable;
    }
    
    public void setIntermediateTable(final String value) {
        this.intermediateTable = value;
    }
    
    public String getIntermediateDataFilter() {
        return this.intermediateDataFilter;
    }
    
    public void setIntermediateDataFilter(final String value) {
        this.intermediateDataFilter = value;
    }
    
    public String getIntermediateDataTimeFilter() {
        return this.intermediateDataTimeFilter;
    }
    
    public void setIntermediateDataTimeFilter(final String value) {
        this.intermediateDataTimeFilter = value;
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
    
    public String getEntityClass() {
        return this.entityClass;
    }
    
    public void setEntityClass(final String value) {
        this.entityClass = value;
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
    
    public List<Parameter> getParameter() {
        if (this.parameter == null) {
            this.parameter = new ArrayList<Parameter>();
        }
        return this.parameter;
    }
    
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = { "id", "aggregationMethod", "parameterFilter", "expression", "format", "rollingFlag", "rollingOver", "rollingMax", "deltaThreshold", "rebootParameterID" })
    public static class Parameter
    {
        @XmlElement(name = "ID", required = true)
        protected String id;
        @XmlElement(name = "Aggregation_Method", required = true)
        protected String aggregationMethod;
        @XmlElement(name = "Parameter_Filter", required = true)
        protected String parameterFilter;
        @XmlElement(name = "Expression", required = true)
        protected String expression;
        @XmlElement(name = "Format", required = true)
        protected String format;
        @XmlElement(name = "RollingFlag", required = true)
        protected String rollingFlag;
        @XmlElement(name = "RollingOver", required = true)
        protected String rollingOver;
        @XmlElement(name = "RollingMax", required = true)
        protected String rollingMax;
        @XmlElement(name = "DeltaThreshold", required = true)
        protected String deltaThreshold;
        @XmlElement(name = "RebootParameterID")
        protected String rebootParameterID;
        
        public String getID() {
            return this.id;
        }
        
        public void setID(final String value) {
            this.id = value;
        }
        
        public String getAggregationMethod() {
            return this.aggregationMethod;
        }
        
        public void setAggregationMethod(final String value) {
            this.aggregationMethod = value;
        }
        
        public String getParameterFilter() {
            return this.parameterFilter;
        }
        
        public void setParameterFilter(final String value) {
            this.parameterFilter = value;
        }
        
        public String getExpression() {
            return this.expression;
        }
        
        public void setExpression(final String value) {
            this.expression = value;
        }
        
        public String getFormat() {
            return this.format;
        }
        
        public void setFormat(final String value) {
            this.format = value;
        }
        
        public String getRollingFlag() {
            return this.rollingFlag;
        }
        
        public void setRollingFlag(final String value) {
            this.rollingFlag = value;
        }
        
        public String getRollingOver() {
            return this.rollingOver;
        }
        
        public void setRollingOver(final String value) {
            this.rollingOver = value;
        }
        
        public String getRollingMax() {
            return this.rollingMax;
        }
        
        public void setRollingMax(final String value) {
            this.rollingMax = value;
        }
        
        public String getDeltaThreshold() {
            return this.deltaThreshold;
        }
        
        public void setDeltaThreshold(final String value) {
            this.deltaThreshold = value;
        }
        
        public String getRebootParameterID() {
            return this.rebootParameterID;
        }
        
        public void setRebootParameterID(final String value) {
            this.rebootParameterID = value;
        }
    }
}
