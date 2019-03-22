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
@XmlType(name = "", propOrder = { "useCaseTemplateID", "templateName", "description", "version", "sourceTable", "targetTable", "sourceFiltering", "phoenixExportTable", "cassandraExportTable", "sourceTableDefinition", "mavgSourceTable", "mavgSourceFiltering", "prevSourceTable", "prevSourceFiltering", "notificationTable", "notificationFilter", "deviceFilter", "notificationActionSchema", "timeAndAttributeColumnRules", "useCase" })
@XmlRootElement(name = "UseCasesTemplate")
public class UseCasesTemplate
{
    @XmlElement(name = "UseCaseTemplateID", required = true)
    protected String useCaseTemplateID;
    @XmlElement(name = "TemplateName", required = true)
    protected String templateName;
    @XmlElement(name = "Description", required = true)
    protected String description;
    @XmlElement(name = "Version", required = true)
    protected String version;
    @XmlElement(name = "SourceTable", required = true)
    protected String sourceTable;
    @XmlElement(name = "TargetTable", required = true)
    protected String targetTable;
    @XmlElement(name = "SourceFiltering", required = true)
    protected String sourceFiltering;
    @XmlElement(name = "PhoenixExportTable")
    protected String phoenixExportTable;
    @XmlElement(name = "CassandraExportTable")
    protected String cassandraExportTable;
    @XmlElement(name = "SourceTableDefinition")
    protected String sourceTableDefinition;
    @XmlElement(name = "MavgSourceTable")
    protected String mavgSourceTable;
    @XmlElement(name = "MavgSourceFiltering")
    protected String mavgSourceFiltering;
    @XmlElement(name = "PrevSourceTable")
    protected String prevSourceTable;
    @XmlElement(name = "PrevSourceFiltering")
    protected String prevSourceFiltering;
    @XmlElement(name = "NotificationTable")
    protected String notificationTable;
    @XmlElement(name = "NotificationFilter")
    protected String notificationFilter;
    @XmlElement(name = "DeviceFilter")
    protected String deviceFilter;
    @XmlElement(name = "NotificationActionSchema")
    protected String notificationActionSchema;
    @XmlElement(name = "TimeAndAttributeColumnRules", required = true)
    protected TimeAndAttributeColumnRules timeAndAttributeColumnRules;
    @XmlElement(name = "UseCase")
    protected List<UseCase> useCase;
    
    public String getUseCaseTemplateID() {
        return this.useCaseTemplateID;
    }
    
    public void setUseCaseTemplateID(final String value) {
        this.useCaseTemplateID = value;
    }
    
    public String getTemplateName() {
        return this.templateName;
    }
    
    public void setTemplateName(final String value) {
        this.templateName = value;
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
    
    public String getSourceFiltering() {
        return this.sourceFiltering;
    }
    
    public void setSourceFiltering(final String value) {
        this.sourceFiltering = value;
    }
    
    public String getPhoenixExportTable() {
        return this.phoenixExportTable;
    }
    
    public void setPhoenixExportTable(final String value) {
        this.phoenixExportTable = value;
    }
    
    public String getCassandraExportTable() {
        return this.cassandraExportTable;
    }
    
    public void setCassandraExportTable(final String value) {
        this.cassandraExportTable = value;
    }
    
    public String getSourceTableDefinition() {
        return this.sourceTableDefinition;
    }
    
    public void setSourceTableDefinition(final String value) {
        this.sourceTableDefinition = value;
    }
    
    public String getMavgSourceTable() {
        return this.mavgSourceTable;
    }
    
    public void setMavgSourceTable(final String value) {
        this.mavgSourceTable = value;
    }
    
    public String getMavgSourceFiltering() {
        return this.mavgSourceFiltering;
    }
    
    public void setMavgSourceFiltering(final String value) {
        this.mavgSourceFiltering = value;
    }
    
    public String getPrevSourceTable() {
        return this.prevSourceTable;
    }
    
    public void setPrevSourceTable(final String value) {
        this.prevSourceTable = value;
    }
    
    public String getPrevSourceFiltering() {
        return this.prevSourceFiltering;
    }
    
    public void setPrevSourceFiltering(final String value) {
        this.prevSourceFiltering = value;
    }
    
    public String getNotificationTable() {
        return this.notificationTable;
    }
    
    public void setNotificationTable(final String value) {
        this.notificationTable = value;
    }
    
    public String getNotificationFilter() {
        return this.notificationFilter;
    }
    
    public void setNotificationFilter(final String value) {
        this.notificationFilter = value;
    }
    
    public String getDeviceFilter() {
        return this.deviceFilter;
    }
    
    public void setDeviceFilter(final String value) {
        this.deviceFilter = value;
    }
    
    public String getNotificationActionSchema() {
        return this.notificationActionSchema;
    }
    
    public void setNotificationActionSchema(final String value) {
        this.notificationActionSchema = value;
    }
    
    public TimeAndAttributeColumnRules getTimeAndAttributeColumnRules() {
        return this.timeAndAttributeColumnRules;
    }
    
    public void setTimeAndAttributeColumnRules(final TimeAndAttributeColumnRules value) {
        this.timeAndAttributeColumnRules = value;
    }
    
    public List<UseCase> getUseCase() {
        if (this.useCase == null) {
            this.useCase = new ArrayList<UseCase>();
        }
        return this.useCase;
    }
    
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = { "expression" })
    public static class TimeAndAttributeColumnRules
    {
        @XmlElement(name = "Expression")
        protected List<Expression> expression;
        
        public List<Expression> getExpression() {
            if (this.expression == null) {
                this.expression = new ArrayList<Expression>();
            }
            return this.expression;
        }
        
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = { "targetTableColumn", "sourceTableExpression" })
        public static class Expression
        {
            @XmlElement(name = "TargetTableColumn", required = true)
            protected String targetTableColumn;
            @XmlElement(name = "SourceTableExpression", required = true)
            protected String sourceTableExpression;
            
            public String getTargetTableColumn() {
                return this.targetTableColumn;
            }
            
            public void setTargetTableColumn(final String value) {
                this.targetTableColumn = value;
            }
            
            public String getSourceTableExpression() {
                return this.sourceTableExpression;
            }
            
            public void setSourceTableExpression(final String value) {
                this.sourceTableExpression = value;
            }
        }
    }
    
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = { "id", "name", "description", "domain", "category", "expression" })
    public static class UseCase
    {
        @XmlElement(name = "ID", required = true)
        protected String id;
        @XmlElement(name = "Name", required = true)
        protected String name;
        @XmlElement(name = "Description", required = true)
        protected String description;
        @XmlElement(name = "Domain")
        protected String domain;
        @XmlElement(name = "Category")
        protected String category;
        @XmlElement(name = "Expression")
        protected List<Expression> expression;
        
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
        
        public String getDescription() {
            return this.description;
        }
        
        public void setDescription(final String value) {
            this.description = value;
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
        
        public List<Expression> getExpression() {
            if (this.expression == null) {
                this.expression = new ArrayList<Expression>();
            }
            return this.expression;
        }
        
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = { "dataFilter", "severity", "toBeExported", "thresholdExpression", "additionalText", "action" })
        public static class Expression
        {
            @XmlElement(name = "DataFilter", required = true)
            protected String dataFilter;
            @XmlElement(name = "Severity", required = true)
            protected String severity;
            @XmlElement(name = "ToBeExported")
            protected boolean toBeExported;
            @XmlElement(name = "ThresholdExpression", required = true)
            protected String thresholdExpression;
            @XmlElement(name = "AdditionalText", required = true)
            protected String additionalText;
            @XmlElement(name = "Action")
            protected String action;
            
            public String getDataFilter() {
                return this.dataFilter;
            }
            
            public void setDataFilter(final String value) {
                this.dataFilter = value;
            }
            
            public String getSeverity() {
                return this.severity;
            }
            
            public void setSeverity(final String value) {
                this.severity = value;
            }
            
            public boolean isToBeExported() {
                return this.toBeExported;
            }
            
            public void setToBeExported(final boolean value) {
                this.toBeExported = value;
            }
            
            public String getThresholdExpression() {
                return this.thresholdExpression;
            }
            
            public void setThresholdExpression(final String value) {
                this.thresholdExpression = value;
            }
            
            public String getAdditionalText() {
                return this.additionalText;
            }
            
            public void setAdditionalText(final String value) {
                this.additionalText = value;
            }
            
            public String getAction() {
                return this.action;
            }
            
            public void setAction(final String value) {
                this.action = value;
            }
        }
    }
}
