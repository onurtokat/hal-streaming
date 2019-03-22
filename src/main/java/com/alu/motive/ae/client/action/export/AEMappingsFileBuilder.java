// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.ae.client.action.export;

import com.alu.motive.ae.client.exception.AEException;
import com.alu.motive.ae.client.action.export.model.MappingsBuilderInputData;

public interface AEMappingsFileBuilder
{
    String generateMappings(final MappingsBuilderInputData p0) throws AEException;
}
