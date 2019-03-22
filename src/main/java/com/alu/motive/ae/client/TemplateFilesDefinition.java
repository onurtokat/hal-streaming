// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.ae.client;

import java.util.List;

public interface TemplateFilesDefinition
{
    String getTemplatesDirectoryRelativePath();
    
    String getPrefix(final String p0);
    
    String getPrefix(final String p0, final String p1);
    
    String getSchemaDefinitionPrefix();
    
    List<String> getAvailableTemplateTypes();
    
    List<String> getAvailableCases(final String p0);
}
