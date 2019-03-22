// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.parser.tr69;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import com.alu.motive.hal.datacollector.commons.plugin.dto.augmentation.AugmentingParameter;
import com.alu.motive.hal.commons.dto.ParameterDTO;
import java.util.List;
import com.alu.motive.hal.commons.dto.DeviceIdDTO;
import com.alu.motive.hal.datacollector.commons.plugin.dto.augmentation.AugmentingParameters;
import com.alu.motive.hal.datacollector.commons.plugin.dto.DataCollectorDTO;
import com.alu.motive.hal.commons.dto.TR69DTO;

public class TR69DataCollectorDTO extends TR69DTO implements DataCollectorDTO
{
    private static final long serialVersionUID = 1L;
    AugmentingParameters augmentingParams;
    
    public TR69DataCollectorDTO() {
    }
    
    public TR69DataCollectorDTO(final DeviceIdDTO devId) {
        this.setDeviceId(devId);
        this.setTimeStamp(System.currentTimeMillis());
    }
    
    @Override
    public void setAugmentingParameters(final AugmentingParameters augmentingParams) {
        if (this.augmentingParams != augmentingParams) {
            this.augmentingParams = augmentingParams;
            if (this.augmentingParams != null) {
                final List<ParameterDTO> paramList = this.augmentParamListToEnd();
                this.setParameterList(paramList);
            }
        }
    }
    
    @Override
    public AugmentingParameters getAugmentingParameters() {
        return this.augmentingParams;
    }
    
    private List<ParameterDTO> augmentParamListToEnd() {
        final List<ParameterDTO> paramList = this.getCreateParamList();
        final LinkedList<AugmentingParameter> parameters = this.augmentingParams.getParameters();
        if (parameters != null) {
            for (final AugmentingParameter parameter : parameters) {
                final ParameterDTO newParamDTO = (parameter != null) ? parameter.toParameterDTO() : null;
                paramList.add(newParamDTO);
            }
        }
        return paramList;
    }
    
    private List<ParameterDTO> getCreateParamList() {
        List<ParameterDTO> paramList = this.getParameterList();
        if (paramList == null) {
            paramList = new ArrayList<ParameterDTO>();
        }
        return paramList;
    }
}
