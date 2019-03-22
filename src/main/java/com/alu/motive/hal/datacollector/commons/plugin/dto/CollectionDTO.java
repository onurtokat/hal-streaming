// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.commons.plugin.dto;

import java.util.Collection;

public interface CollectionDTO<T extends DataCollectorDTO> extends DataCollectorDTO
{
    Collection<T> getAllDTOs();
}
