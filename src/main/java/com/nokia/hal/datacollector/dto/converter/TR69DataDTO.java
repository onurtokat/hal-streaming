// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.datacollector.dto.converter;

import java.util.Iterator;
import com.alu.motive.hal.datacollector.commons.plugin.dto.augmentation.AugmentingParameter;
import java.util.HashMap;
import java.util.Map;
import com.alu.motive.hal.datacollector.parser.tr69.TR69DataCollectorDTO;
import com.alu.motive.hal.commons.dto.ParameterDTO;
import com.alu.motive.hal.datacollector.commons.plugin.dto.augmentation.AugmentingParameters;
import com.alu.motive.hal.datacollector.commons.plugin.dto.MappedDTO;
import com.alu.motive.hal.datacollector.parser.tr69.TR69Parameter;

public class TR69DataDTO extends TR69Parameter implements MappedDTO
{
    private static final long serialVersionUID = 7171560264604596941L;
    private long receivingTimestamp;
    private String deviceId;
    private String oui;
    private String productClass;
    private String serialNumber;
    AugmentingParameters augParams;
    
    public TR69DataDTO(final ParameterDTO parameter, final TR69DataCollectorDTO tr69DCDTO) {
        this.receivingTimestamp = tr69DCDTO.getTimeStamp();
        this.oui = tr69DCDTO.getDeviceId().getOui();
        this.productClass = tr69DCDTO.getDeviceId().getProductClass();
        this.serialNumber = tr69DCDTO.getDeviceId().getSerialNumber();
        this.deviceId = this.oui + "-" + this.productClass + "-" + this.serialNumber;
        this.setName(parameter.getName());
        this.setValue(parameter.getValue());
        (this.augParams = tr69DCDTO.getAugmentingParameters()).sort();
    }
    
    @Override
    public long getTimeStamp() {
        return this.receivingTimestamp;
    }
    
    @Override
    public void setAugmentingParameters(final AugmentingParameters augmentingParameters) {
    }
    
    @Override
    public AugmentingParameters getAugmentingParameters() {
        return this.augParams;
    }
    
    @Override
    public Map<String, Object> toMap() {
        final Map<String, Object> record = new HashMap<String, Object>();
        record.put("device_id", this.deviceId);
        record.put("oui", this.oui);
        record.put("product_class", this.productClass);
        record.put("serial_number", this.serialNumber);
        record.put("parameter_name", this.getName());
        record.put("parameter_value", this.getValue());
        if (this.augParams != null) {
            for (final AugmentingParameter augParam : this.augParams.getParameters()) {
                record.put(augParam.getName(), augParam.getValue());
            }
        }
        return record;
    }
    
    @Override
    public String toString() {
        final StringBuilder strBldr = new StringBuilder();
        strBldr.append(this.getClass().getSimpleName());
        strBldr.append("{deviceId=" + this.deviceId + ", oui=" + this.oui + ", product_class=" + this.productClass + ", serial_number=" + this.serialNumber + ", parameter_name=").append(this.getName()).append(", parameter_value=").append(this.getValue());
        if (this.augParams != null) {
            for (final AugmentingParameter augParam : this.augParams.getParameters()) {
                strBldr.append(", ").append(augParam.getName()).append("=").append(augParam.getValue());
            }
        }
        strBldr.append("}");
        return strBldr.toString();
    }
}
