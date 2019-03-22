// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.commons.plugin.dto;

public interface DTOConverter<FROM extends DataCollectorDTO, TO extends DataCollectorDTO>
{
    TO convert(final FROM p0);
}
