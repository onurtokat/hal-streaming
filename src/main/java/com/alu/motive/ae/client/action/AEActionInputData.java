// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.ae.client.action;

import com.alu.motive.ae.client.HiveUdf;
import java.util.List;
import java.util.Map;
import com.alu.motive.ae.client.template.AETemplates;
import com.alu.motive.ae.client.TemplateFilesDefinition;
import com.alu.motive.ae.client.AEJobDTO;
import com.alu.motive.ae.client.AEUserDefinedConfigs;
import java.io.File;

public interface AEActionInputData
{
    File getArchiveContentDir();
    
    AEUserDefinedConfigs getAeConfiguration();
    
    AEJobDTO getAEJob();
    
    TemplateFilesDefinition getTemplateFilesDefinition();
    
    AETemplates getAETemplates();
    
    String getBody();
    
    String getMyFirstElementName();
    
    String getNextElementName();
    
    Map<String, String> getJobVariables();
    
    List<HiveUdf> getUdfs();
}
