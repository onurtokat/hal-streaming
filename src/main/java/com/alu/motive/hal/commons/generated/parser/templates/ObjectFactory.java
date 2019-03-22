// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.commons.generated.parser.templates;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory
{
    public Group createGroup() {
        return new Group();
    }
    
    public Group.Parameter createGroupParameter() {
        return new Group.Parameter();
    }
    
    public ModelTypeMappings createModelTypeMappings() {
        return new ModelTypeMappings();
    }
    
    public Mapping createMapping() {
        return new Mapping();
    }
    
    public DataMappingTemplate createDataMappingTemplate() {
        return new DataMappingTemplate();
    }
    
    public UnifiedMappingTemplate createUnifiedMappingTemplate() {
        return new UnifiedMappingTemplate();
    }
    
    public Columns createColumns() {
        return new Columns();
    }
    
    public DeviceType createDeviceType() {
        return new DeviceType();
    }
    
    public DeviceParameter createDeviceParameter() {
        return new DeviceParameter();
    }
    
    public Column createColumn() {
        return new Column();
    }
    
    public Group.Parameter.DeviceParameters createGroupParameterDeviceParameters() {
        return new Group.Parameter.DeviceParameters();
    }
    
    public Group.Parameter.Counter createGroupParameterCounter() {
        return new Group.Parameter.Counter();
    }
}
