// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.hive.model;

import org.apache.spark.api.java.Optional;
import com.alu.motive.hal.commons.dto.DeviceIdDTO;
import java.util.HashMap;
import com.alu.motive.hal.commons.generated.parser.templates.ModelTypeMappings;
import java.util.Map;
import java.io.Serializable;

public class DeviceModelTypeMappingConfig implements Serializable
{
    public static final String SEPARATOR = "_";
    private Map<String, ModelType> deviceModelTypeMap;
    
    public DeviceModelTypeMappingConfig(final ModelTypeMappings modelTypeMappings) {
        this.deviceModelTypeMap = new HashMap<String, ModelType>();
        modelTypeMappings.getMapping().stream().forEach(modelTypeMapping -> modelTypeMapping.getDeviceType().stream().forEach(deviceType -> this.deviceModelTypeMap.put(deviceType.getOui() + "_" + deviceType.getProductClass(), ModelType.findModel(modelTypeMapping.getType()))));
    }
    
    public Optional<ModelType> getModelType(final DeviceIdDTO deviceIdDTO) {
        return (Optional<ModelType>)Optional.fromNullable((Object)this.deviceModelTypeMap.get(deviceIdDTO.getOui() + "_" + deviceIdDTO.getProductClass()));
    }
}
