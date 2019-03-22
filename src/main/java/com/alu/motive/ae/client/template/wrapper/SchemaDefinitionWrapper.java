// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.ae.client.template.wrapper;

import com.alu.motive.ae.client.template.ModelType;
import java.util.List;
import com.alu.motive.ae.client.generated.parser.templates.SchemaDefinitionTemplate;

public interface SchemaDefinitionWrapper extends BaseTemplateWrapper<SchemaDefinitionTemplate.Row>
{
    String getTableName();
    
    String getHiveColumnName(final String p0);
    
    List<SchemaDefinitionTemplate.Row> getRows(final String p0);
    
    String getColumnAliasByHiveColumnName(final String p0);
    
    String getHiveColumnType(final String p0);
    
    List<String> getHiveColumnNamesByModelType(final ModelType p0);
    
    List<String> getColumnAliasesByModelType(final ModelType p0);
    
    boolean isAttribute(final String p0);
}
