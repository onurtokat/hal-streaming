// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.commons.plugin.dto.augmentation;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.util.Iterator;
import java.util.Map;
import java.util.LinkedList;
import com.alu.motive.hal.datacollector.commons.configuration.NonexistentConfiguration;
import com.alu.motive.hal.datacollector.commons.configuration.DataCollectorConfigurationService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class AugmentingConfiguration
{
    Logger logger;
    private String pluginName;
    private AugmentingParameters requestParameterMapping;
    private AugmentingParameters httpHeaderMapping;
    private AugmentingParameters runtimeAddedParameters;
    
    public AugmentingConfiguration(final String pluginName) {
        this.logger = LoggerFactory.getLogger(AugmentingConfiguration.class);
        this.requestParameterMapping = null;
        this.httpHeaderMapping = null;
        this.runtimeAddedParameters = null;
        this.pluginName = pluginName;
    }
    
    public void configureAugmentingRequestParams(final DataCollectorConfigurationService configurationService) {
        final String queryParameterMappingCfgPath = "plugins/" + this.pluginName + "/output/augmentation/queryParameterMapping";
        try {
            this.requestParameterMapping = this.readAugParameterConfigs(queryParameterMappingCfgPath, configurationService);
        }
        catch (NonexistentConfiguration e) {
            this.logger.info("Augmenting request parameters not defined for plugin " + this.pluginName);
            this.logger.info(e.getMessage(), e);
            this.requestParameterMapping = null;
        }
    }
    
    public void configureAugmentingHeaderParams(final DataCollectorConfigurationService configurationService) {
        final String queryParameterMappingCfgPath = "plugins/" + this.pluginName + "/output/augmentation/httpHeaderMapping";
        try {
            this.httpHeaderMapping = this.readAugParameterConfigs(queryParameterMappingCfgPath, configurationService);
        }
        catch (NonexistentConfiguration e) {
            this.logger.info("Augmenting header parameters not defined for plugin " + this.pluginName);
            this.logger.info(e.getMessage(), e);
            this.httpHeaderMapping = null;
        }
    }
    
    public void configureAugmentingRuntimeParams(final DataCollectorConfigurationService configurationService) {
        final String queryParameterMappingCfgPath = "plugins/" + this.pluginName + "/output/augmentation/runtimeAddedParameters";
        try {
            this.runtimeAddedParameters = this.readAugParameterConfigs(queryParameterMappingCfgPath, configurationService);
        }
        catch (NonexistentConfiguration e) {
            this.logger.info("Augmenting runtime parameters not defined for plugin " + this.pluginName);
            this.logger.info(e.getMessage(), e);
            this.runtimeAddedParameters = null;
        }
    }
    
    private AugmentingParameters readAugParameterConfigs(final String queryOrderPath, final DataCollectorConfigurationService configurationService) throws NonexistentConfiguration {
        AugmentingParameters augmentingParams = null;
        final Map<String, String> queryParameters = configurationService.getList(queryOrderPath);
        if (queryParameters != null && !queryParameters.isEmpty()) {
            final LinkedList<AugmentingParameter> qpaList = new LinkedList<AugmentingParameter>();
            for (final String parameterPosition : queryParameters.keySet()) {
                int paramPosition = -1;
                AugmentingParameter augmentingParameter = null;
                try {
                    paramPosition = Integer.parseInt(parameterPosition);
                    final String paramPosPath = queryOrderPath + "/" + paramPosition;
                    final String path = paramPosPath + "/" + "paramName";
                    final String paramName = configurationService.getDCProperty(path);
                    if (paramName == null) {
                        throw new NonexistentConfiguration(path);
                    }
                    final String paramType = configurationService.getDCProperty(paramPosPath + "/" + "paramType");
                    final String paramValue = configurationService.getDCProperty(paramPosPath + "/" + "paramValue");
                    augmentingParameter = new AugmentingParameter(paramName, AugmentingParameterType.fromString(paramType), paramValue, paramPosition);
                    final String newParamName = configurationService.getDCProperty(paramPosPath + "/" + "renameAs");
                    if (newParamName != null && !newParamName.isEmpty()) {
                        augmentingParameter.setNewParamName(newParamName);
                    }
                    qpaList.add(augmentingParameter);
                }
                catch (NumberFormatException e) {
                    this.logger.error("Bad integer position configured for " + queryOrderPath + ". (" + parameterPosition + "). Ignoring it.", e);
                }
                catch (NonexistentConfiguration e2) {
                    this.logger.error("No value configured for " + queryOrderPath + "/" + paramPosition + ". Ignoring it");
                }
            }
            if (!qpaList.isEmpty()) {
                augmentingParams = new AugmentingParameters();
                augmentingParams.setParameters(qpaList);
            }
        }
        return augmentingParams;
    }
    
    public AugmentingParameters getHttpHeaderMapping() {
        return this.httpHeaderMapping;
    }
    
    public AugmentingParameters getQueryParameterMapping() {
        return this.requestParameterMapping;
    }
    
    public AugmentingParameters getRuntimeAddedParameters() {
        return this.runtimeAddedParameters;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
