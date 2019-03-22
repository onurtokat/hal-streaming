// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.ae.client.template;

import java.util.Set;
import com.alu.motive.ae.client.generated.parser.templates.AttributesTemplate;
import com.alu.motive.ae.client.generated.parser.templates.UseCasesTemplate;
import com.alu.motive.ae.client.generated.parser.templates.KPIsTemplate;
import java.util.List;
import com.alu.motive.ae.client.template.wrapper.DataMappingWrapper;
import com.alu.motive.ae.client.template.wrapper.SchemaDefinitionWrapper;
import java.util.Map;

public interface AETemplates
{
    Map<String, SchemaDefinitionWrapper> getAllSchemaDefinitionTemplates();
    
    SchemaDefinitionWrapper getSchemaDefinitionTemplate(final String p0);
    
    Map<String, DataMappingWrapper> getAllDataMappingTemplates();
    
    DataMappingWrapper getDataMappingTemplate(final String p0);
    
    List<KPIsTemplate> getAllKPIsTemplates();
    
    List<KPIsTemplate> getKPIsTemplates(final String p0);
    
    List<UseCasesTemplate> getAllUseCasesTemplates();
    
    List<UseCasesTemplate> getUseCasesTemplates(final String p0);
    
    List<AttributesTemplate> getAllAttributesTemplates();
    
    List<AttributesTemplate> getAttributesTemplates(final String p0);
    
    Set<String> getCaseValuesForUseCasesTemplates();
}
