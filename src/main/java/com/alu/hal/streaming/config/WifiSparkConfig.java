// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.config;

import org.apache.spark.api.java.Optional;
import com.alu.motive.hal.commons.dto.DeviceIdDTO;
import com.alu.motive.hal.commons.generated.parser.templates.ModelTypeMappings;
import com.alu.motive.hal.commons.generated.parser.templates.UnifiedMappingTemplate;
import com.alu.motive.hal.commons.generated.parser.templates.DataMappingTemplate;
import com.alu.hal.streaming.hive.model.UnifiedTableMetaDataFactory;
import com.alu.hal.streaming.hive.model.ModelMetadataFactory;
import com.alu.hal.streaming.hive.model.ModelType;
import java.net.URISyntaxException;
import java.io.File;
import com.alu.motive.hal.commons.xml.parser.ModelTypeMappingParser;
import com.alu.motive.hal.commons.xml.parser.UnifiedMappingTemplateParser;
import com.alu.motive.hal.commons.xml.parser.DataMappingTemplateParser;
import com.alu.hal.streaming.exception.WifiTemplateParseException;
import java.net.URL;
import java.util.Map;
import com.alu.hal.streaming.hive.model.DeviceModelTypeMappingConfig;
import com.alu.hal.streaming.hive.model.UnifiedTableMetaData;
import com.alu.hal.streaming.hive.model.ModelMetadata;
import org.apache.log4j.Logger;
import java.io.Serializable;

public class WifiSparkConfig implements Serializable
{
    private static final long serialVersionUID = 1L;
    public static final String QUERY_DIR = "/";
    public static final String MODELTYPE_MAPPING = "/HAL_ModelType-Mapping.xml";
    public static final String DATAMAPPING_TR98 = "/HAL_DataMapping-TR98.xml";
    public static final String DATAMAPPING_TR181 = "/HAL_DataMapping-TR181.xml";
    public static final String UNIFIED_MAPPING = "/HAL_Unified-Mapping.xml";
    private static Logger LOG;
    private final ModelMetadata tr98Metadata;
    private final ModelMetadata tr181Metadata;
    private final UnifiedTableMetaData unifiedTableMetadata;
    private final DeviceModelTypeMappingConfig deviceModelTypeMappingConfig;
    private WifiQueries wifiQueries;
    private final Map<String, String> propertiesMap;
    private WifiRecommendations recommendations;
    
    public WifiSparkConfig(final URL modelTypeMappingResource, final URL tr98Resource, final URL tr181Resource, final URL unifiedMappingResource, final URL queryDirResource) throws WifiTemplateParseException {
        this(modelTypeMappingResource, tr98Resource, tr181Resource, unifiedMappingResource, queryDirResource, null);
    }
    
    public WifiSparkConfig(final URL modelTypeMappingResource, final URL tr98Resource, final URL tr181Resource, final URL unifiedMappingResource, final URL queryDirResource, final Map<String, String> broadcastPropertiesMap) throws WifiTemplateParseException {
        this.propertiesMap = broadcastPropertiesMap;
        final DataMappingTemplateParser templateParser = new DataMappingTemplateParser();
        final UnifiedMappingTemplateParser unifiedMappingTemplateParser = new UnifiedMappingTemplateParser();
        final ModelTypeMappingParser modelTypeMappingParser = new ModelTypeMappingParser();
        DataMappingTemplate tr98Template;
        DataMappingTemplate tr181Template;
        UnifiedMappingTemplate unifiedMappingTemplate;
        ModelTypeMappings modelTypeMappings;
        try {
            tr98Template = templateParser.parse(new File(tr98Resource.toURI()));
            tr181Template = templateParser.parse(new File(tr181Resource.toURI()));
            unifiedMappingTemplate = unifiedMappingTemplateParser.parse(new File(unifiedMappingResource.toURI()));
            modelTypeMappings = modelTypeMappingParser.parse(new File(modelTypeMappingResource.toURI()));
        }
        catch (URISyntaxException e) {
            throw new WifiTemplateParseException("Unable to parse the file", e);
        }
        this.tr98Metadata = new ModelMetadataFactory(ModelType.TR98, tr98Template).create();
        this.tr181Metadata = new ModelMetadataFactory(ModelType.TR181, tr181Template).create();
        this.unifiedTableMetadata = new UnifiedTableMetaDataFactory(unifiedMappingTemplate, this.tr98Metadata.getTransposeTableMetadata(), this.tr181Metadata.getTransposeTableMetadata()).create();
        this.deviceModelTypeMappingConfig = new DeviceModelTypeMappingConfig(modelTypeMappings);
        if (queryDirResource != null) {
            this.wifiQueries = new WifiQueries(queryDirResource);
            if (broadcastPropertiesMap != null) {
                final boolean computeRecommendations = broadcastPropertiesMap.get(DashBoardConfig.COMPUTE_RECOMMENDATIONS) != null && Boolean.valueOf(broadcastPropertiesMap.get(DashBoardConfig.COMPUTE_RECOMMENDATIONS));
                if (computeRecommendations) {
                    this.recommendations = new WifiRecommendations(queryDirResource);
                }
            }
        }
    }
    
    public UnifiedTableMetaData getUnifiedTableMetadata() {
        return this.unifiedTableMetadata;
    }
    
    public ModelMetadata getModelMetaDataByModelType(final ModelType modelType) {
        switch (modelType) {
            case TR98: {
                return this.getTr98Metadata();
            }
            case TR181: {
                return this.getTr181Metadata();
            }
            default: {
                WifiSparkConfig.LOG.error("Unsupported Model type " + modelType.name() + "Defaulting to model type" + ModelType.TR181.name());
                return this.getTr181Metadata();
            }
        }
    }
    
    public Optional<ModelType> getModelTypeByDeviceId(final DeviceIdDTO deviceIdDTO) {
        return this.getDeviceModelTypeMappingConfig().getModelType(deviceIdDTO);
    }
    
    public ModelMetadata getTr98Metadata() {
        return this.tr98Metadata;
    }
    
    public ModelMetadata getTr181Metadata() {
        return this.tr181Metadata;
    }
    
    public DeviceModelTypeMappingConfig getDeviceModelTypeMappingConfig() {
        return this.deviceModelTypeMappingConfig;
    }
    
    public WifiQueries getWifiQueries() {
        return this.wifiQueries;
    }
    
    public Map<String, String> getPropertiesMap() {
        return this.propertiesMap;
    }
    
    public WifiRecommendations getRecommendations() {
        return this.recommendations;
    }
    
    static {
        WifiSparkConfig.LOG = Logger.getLogger(WifiSparkConfig.class);
    }
}
