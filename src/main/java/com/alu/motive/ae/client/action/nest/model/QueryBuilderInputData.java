// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.ae.client.action.nest.model;

import com.alu.motive.ae.client.action.AEActionInputData;
import com.alu.motive.ae.client.HiveUdf;
import java.util.List;
import java.util.Map;
import com.alu.motive.ae.client.InputFileInfo;
import java.io.File;
import com.alu.motive.ae.client.TemplateFilesDefinition;
import com.alu.motive.ae.client.AEJobDTO;
import com.alu.motive.ae.client.template.AETemplates;
import com.alu.motive.ae.client.AEUserDefinedConfigs;

public class QueryBuilderInputData
{
    private final AEUserDefinedConfigs aeConfiguration;
    private final AETemplates aeTemplates;
    private final AEJobDTO aeJob;
    private final TemplateFilesDefinition templateFilesDefinition;
    private final File templatesDirectory;
    private final InputFileInfo hivePropertiesFile;
    private final Map<String, String> jobVariables;
    private final String caseValue;
    private final List<HiveUdf> udfs;
    
    public QueryBuilderInputData(final String caseValue, final AEActionInputData aInputData, final TemplateFilesDefinition templateFilesDefinition, final File templatesDirectory, final InputFileInfo hivePropertiesFile) {
        this.caseValue = caseValue;
        this.aeConfiguration = aInputData.getAeConfiguration();
        this.aeJob = aInputData.getAEJob();
        this.aeTemplates = aInputData.getAETemplates();
        this.templateFilesDefinition = templateFilesDefinition;
        this.templatesDirectory = templatesDirectory;
        this.hivePropertiesFile = hivePropertiesFile;
        this.jobVariables = aInputData.getJobVariables();
        this.udfs = aInputData.getUdfs();
    }
    
    public String getCaseValue() {
        return this.caseValue;
    }
    
    public AEUserDefinedConfigs getAEConfiguration() {
        return this.aeConfiguration;
    }
    
    public AEJobDTO getAEJob() {
        return this.aeJob;
    }
    
    public AETemplates getAETemplates() {
        return this.aeTemplates;
    }
    
    public InputFileInfo getHivePropertiesFile() {
        return this.hivePropertiesFile;
    }
    
    public Map<String, String> getJobVariables() {
        return this.jobVariables;
    }
    
    public File getTemplatesDirectory() {
        return this.templatesDirectory;
    }
    
    public String getTemplatesPrefix(final String templateType) {
        return this.templateFilesDefinition.getPrefix(templateType);
    }
    
    public String getTemplatesPrefix(final String templateType, final String caseValue) {
        return this.templateFilesDefinition.getPrefix(templateType, caseValue);
    }
    
    public List<String> getAvailableTemplateTypes() {
        return this.templateFilesDefinition.getAvailableTemplateTypes();
    }
    
    List<String> getAvailableTemplateTypeCases(final String templateType) {
        return this.templateFilesDefinition.getAvailableCases(templateType);
    }
    
    public List<HiveUdf> getHiveUdfs() {
        return this.udfs;
    }
}
