// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.ae.client.template.wrapper;

import com.alu.motive.ae.client.generated.parser.templates.DataMappingTemplate;

public interface DataMappingWrapper extends BaseTemplateWrapper<DataMappingTemplate.Parameter>
{
    String getSourceTable();
    
    String getIntermediateTable();
    
    String getTargetTable();
    
    String getTemplateFilter();
    
    String getTemplateDataEntityFilter();
    
    String getIntermediateTemplateFilter();
    
    String getIntermediateDateTimeTemplateFilter();
    
    String getExpression(final String p0);
    
    String getParameterFilter(final String p0);
    
    String getAggregationMethod(final String p0);
    
    String getFormat(final String p0);
    
    String getTemplateId();
}
