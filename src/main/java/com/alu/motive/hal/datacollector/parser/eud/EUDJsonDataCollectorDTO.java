// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.parser.eud;

import com.alu.motive.hal.datacollector.commons.plugin.dto.augmentation.AugmentingParameterType;
import java.util.Iterator;
import com.alu.motive.hal.datacollector.commons.plugin.dto.augmentation.AugmentingParameter;
import com.alu.motive.hal.datacollector.commons.plugin.dto.augmentation.AugmentingParameters;
import com.alu.motive.hal.datacollector.commons.plugin.dto.JsonDTO;

public class EUDJsonDataCollectorDTO implements JsonDTO
{
    private String json;
    private String augmentedJson;
    private long timestamp;
    private AugmentingParameters augmentingParameters;
    
    public EUDJsonDataCollectorDTO(final String json) {
        this.json = json;
        this.augmentedJson = json;
    }
    
    @Override
    public long getTimeStamp() {
        return this.timestamp;
    }
    
    public void setTimeStamp(final long timeStamp) {
        this.timestamp = timeStamp;
    }
    
    @Override
    public void setAugmentingParameters(final AugmentingParameters augmentedParamsMap) {
        this.augmentingParameters = augmentedParamsMap;
        if (this.augmentingParameters != null && this.augmentingParameters.size() > 0) {
            this.updateAugumentedJson();
        }
    }
    
    @Override
    public AugmentingParameters getAugmentingParameters() {
        return this.augmentingParameters;
    }
    
    private void updateAugumentedJson() {
        final StringBuffer augmentedJsonBuff = new StringBuffer("{");
        for (final AugmentingParameter parameter : this.augmentingParameters.getParameters()) {
            augmentedJsonBuff.append("\"" + parameter.getName() + "\": ");
            final Object value = parameter.getValue();
            switch (parameter.getType()) {
                case NUMBER: {
                    augmentedJsonBuff.append(value + ",");
                    continue;
                }
                default: {
                    augmentedJsonBuff.append("\"" + value + "\",");
                    continue;
                }
            }
        }
        augmentedJsonBuff.append(this.json.substring(this.json.indexOf(123) + 1));
        this.augmentedJson = augmentedJsonBuff.toString();
    }
    
    @Override
    public String toString() {
        return this.augmentedJson;
    }
    
    @Override
    public String toJson() {
        return this.augmentedJson;
    }
}
