// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.commons.generated.parser.templates;

import javax.xml.bind.annotation.XmlSchemaType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Group", propOrder = { "name", "path", "referredByParameter", "parameter", "group" })
public class Group
{
    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "Path", required = true)
    protected String path;
    @XmlElement(name = "ReferredByParameter")
    protected String referredByParameter;
    @XmlElement(name = "Parameter", required = true)
    protected List<Parameter> parameter;
    @XmlElement(name = "Group")
    protected List<Group> group;
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String value) {
        this.name = value;
    }
    
    public String getPath() {
        return this.path;
    }
    
    public void setPath(final String value) {
        this.path = value;
    }
    
    public String getReferredByParameter() {
        return this.referredByParameter;
    }
    
    public void setReferredByParameter(final String value) {
        this.referredByParameter = value;
    }
    
    public List<Parameter> getParameter() {
        if (this.parameter == null) {
            this.parameter = new ArrayList<Parameter>();
        }
        return this.parameter;
    }
    
    public List<Group> getGroup() {
        if (this.group == null) {
            this.group = new ArrayList<Group>();
        }
        return this.group;
    }
    
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {})
    public static class Parameter
    {
        @XmlElement(name = "Name", required = true)
        protected String name;
        @XmlElement(name = "Type", required = true)
        @XmlSchemaType(name = "normalizedString")
        protected TypeEnum type;
        @XmlElement(name = "ReferredGroup")
        protected String referredGroup;
        @XmlElement(name = "DeviceParameters")
        protected DeviceParameters deviceParameters;
        @XmlElement(name = "Counter")
        protected Counter counter;
        
        public String getName() {
            return this.name;
        }
        
        public void setName(final String value) {
            this.name = value;
        }
        
        public TypeEnum getType() {
            return this.type;
        }
        
        public void setType(final TypeEnum value) {
            this.type = value;
        }
        
        public String getReferredGroup() {
            return this.referredGroup;
        }
        
        public void setReferredGroup(final String value) {
            this.referredGroup = value;
        }
        
        public DeviceParameters getDeviceParameters() {
            return this.deviceParameters;
        }
        
        public void setDeviceParameters(final DeviceParameters value) {
            this.deviceParameters = value;
        }
        
        public Counter getCounter() {
            return this.counter;
        }
        
        public void setCounter(final Counter value) {
            this.counter = value;
        }
        
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = { "rebootParameter", "maxValueThreshold" })
        public static class Counter
        {
            @XmlElement(name = "RebootParameter")
            protected String rebootParameter;
            @XmlElement(name = "MaxValueThreshold")
            protected Long maxValueThreshold;
            
            public String getRebootParameter() {
                return this.rebootParameter;
            }
            
            public void setRebootParameter(final String value) {
                this.rebootParameter = value;
            }
            
            public Long getMaxValueThreshold() {
                return this.maxValueThreshold;
            }
            
            public void setMaxValueThreshold(final Long value) {
                this.maxValueThreshold = value;
            }
        }
        
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = { "deviceParameter" })
        public static class DeviceParameters
        {
            @XmlElement(name = "DeviceParameter", required = true)
            protected List<DeviceParameter> deviceParameter;
            
            public List<DeviceParameter> getDeviceParameter() {
                if (this.deviceParameter == null) {
                    this.deviceParameter = new ArrayList<DeviceParameter>();
                }
                return this.deviceParameter;
            }
        }
    }
}
