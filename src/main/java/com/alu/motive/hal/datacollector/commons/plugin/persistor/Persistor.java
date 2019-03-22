// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.commons.plugin.persistor;

import com.alu.motive.hal.datacollector.commons.plugin.dto.DataCollectorDTO;

public interface Persistor
{
    void autoConfigure() throws Exception;
    
    void persist(final DataCollectorDTO p0) throws Exception;
}
