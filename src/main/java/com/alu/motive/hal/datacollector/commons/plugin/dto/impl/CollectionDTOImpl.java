// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.commons.plugin.dto.impl;

import java.util.Collection;
import com.alu.motive.hal.datacollector.commons.plugin.dto.augmentation.AugmentingParameters;
import java.util.LinkedList;
import com.alu.motive.hal.datacollector.commons.plugin.dto.CollectionDTO;
import com.alu.motive.hal.datacollector.commons.plugin.dto.DataCollectorDTO;

public class CollectionDTOImpl<T extends DataCollectorDTO> implements CollectionDTO<T>
{
    private LinkedList<T> list;
    private AugmentingParameters augParams;
    
    public CollectionDTOImpl() {
        this.list = new LinkedList<T>();
    }
    
    @Override
    public long getTimeStamp() {
        return 0L;
    }
    
    @Override
    public void setAugmentingParameters(final AugmentingParameters augmentingParameters) {
        this.augParams = augmentingParameters;
    }
    
    @Override
    public AugmentingParameters getAugmentingParameters() {
        return this.augParams;
    }
    
    @Override
    public Collection<T> getAllDTOs() {
        return this.list;
    }
    
    public void add(final T dto) {
        this.list.add(dto);
    }
}
