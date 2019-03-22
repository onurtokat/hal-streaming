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
@XmlType(name = "", propOrder = { "tableName", "hqlReferenceFile", "row" })
@XmlRootElement(name = "SchemaDefinitionTemplate")
public class SchemaDefinitionTemplate
{
    @XmlElement(name = "TableName", required = true)
    protected String tableName;
    @XmlElement(name = "HqlReferenceFile", required = true)
    protected String hqlReferenceFile;
    @XmlElement(name = "Row")
    protected List<Row> row;
    
    public String getTableName() {
        return this.tableName;
    }
    
    public void setTableName(final String value) {
        this.tableName = value;
    }
    
    public String getHqlReferenceFile() {
        return this.hqlReferenceFile;
    }
    
    public void setHqlReferenceFile(final String value) {
        this.hqlReferenceFile = value;
    }
    
    public List<Row> getRow() {
        if (this.row == null) {
            this.row = new ArrayList<Row>();
        }
        return this.row;
    }
    
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = { "hiveColumnName", "hiveType", "columnAlias", "modelType", "friendlyName", "longDescription", "unit", "format" })
    public static class Row
    {
        @XmlElement(name = "HiveColumnName", required = true)
        protected String hiveColumnName;
        @XmlElement(name = "HiveType", required = true)
        protected String hiveType;
        @XmlElement(name = "ColumnAlias", required = true)
        protected String columnAlias;
        @XmlElement(name = "ModelType", required = true)
        protected String modelType;
        @XmlElement(name = "FriendlyName", required = true)
        protected String friendlyName;
        @XmlElement(name = "LongDescription", required = true)
        protected String longDescription;
        @XmlElement(name = "Unit", required = true)
        protected String unit;
        @XmlElement(name = "Format")
        protected String format;
        
        public String getHiveColumnName() {
            return this.hiveColumnName;
        }
        
        public void setHiveColumnName(final String value) {
            this.hiveColumnName = value;
        }
        
        public String getHiveType() {
            return this.hiveType;
        }
        
        public void setHiveType(final String value) {
            this.hiveType = value;
        }
        
        public String getColumnAlias() {
            return this.columnAlias;
        }
        
        public void setColumnAlias(final String value) {
            this.columnAlias = value;
        }
        
        public String getModelType() {
            return this.modelType;
        }
        
        public void setModelType(final String value) {
            this.modelType = value;
        }
        
        public String getFriendlyName() {
            return this.friendlyName;
        }
        
        public void setFriendlyName(final String value) {
            this.friendlyName = value;
        }
        
        public String getLongDescription() {
            return this.longDescription;
        }
        
        public void setLongDescription(final String value) {
            this.longDescription = value;
        }
        
        public String getUnit() {
            return this.unit;
        }
        
        public void setUnit(final String value) {
            this.unit = value;
        }
        
        public String getFormat() {
            return this.format;
        }
        
        public void setFormat(final String value) {
            this.format = value;
        }
    }
}
