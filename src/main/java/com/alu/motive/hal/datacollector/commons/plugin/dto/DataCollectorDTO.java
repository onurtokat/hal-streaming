// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.commons.plugin.dto;

import com.alu.motive.hal.datacollector.commons.plugin.dto.augmentation.AugmentingParameters;
import java.io.Serializable;

public interface DataCollectorDTO extends Serializable
{
    long getTimeStamp();
    
    void setAugmentingParameters(final AugmentingParameters p0);
    
    AugmentingParameters getAugmentingParameters();
}
