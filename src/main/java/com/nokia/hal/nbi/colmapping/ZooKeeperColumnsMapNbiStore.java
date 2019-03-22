// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.colmapping;

import org.slf4j.LoggerFactory;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import com.motive.mas.configuration.service.api.ConfigurationService;
import org.slf4j.Logger;

public class ZooKeeperColumnsMapNbiStore implements ColumnsMappingNbiStore
{
    static final Logger logger;
    public static final String KPI_COLUMN_ADDITIONAL_INFO_ROOT_ZNODE = "KpiAdditionalInfo";
    private static final String columnsMapRootZNode = "hal/nbi/KpiAdditionalInfo";
    public static final String COLUMNS_MAP_LEAF_ZNODE = "map";
    private ConfigurationService masConfigurationService;
    
    protected String getColumnsMapRootZNode() {
        return "hal/nbi/KpiAdditionalInfo";
    }
    
    @Override
    public void save(final String entityClass, final PeriodType periodType, final Integer gpDuration, final Map<String, InsightAdditionalInfo> mapping) throws Exception {
        ZooKeeperColumnsMapNbiStore.logger.debug("save method called");
        final ObjectMapper mapper = new ObjectMapper();
        if (periodType == PeriodType.GP && gpDuration == null) {
            ZooKeeperColumnsMapNbiStore.logger.error("Illegal arguments on call to save (periodType = GP && gpDuration = null)");
            throw new IllegalArgumentException("gpDuration must be not null when periodType = 'GP'");
        }
        final String path = this.buildZooKeeperPath(entityClass, periodType, gpDuration);
        ZooKeeperColumnsMapNbiStore.logger.debug("Zookeeper path to use: {}", path);
        ZooKeeperColumnsMapNbiStore.logger.info("{} mapping(s) will be stored for entityClass={}, periodType={}, gpDuration={}", mapping.size(), entityClass, periodType.toString(), gpDuration.toString());
        final String mappingJson = mapper.writeValueAsString(mapping);
        ZooKeeperColumnsMapNbiStore.logger.info("{} will be written to Zookeeper path {}", mappingJson, path);
        this.masConfigurationService.set(path, mappingJson.getBytes());
        ZooKeeperColumnsMapNbiStore.logger.info("Successfully set value.");
    }
    
    @Override
    public Map<String, InsightAdditionalInfo> load(final String entityClass, final PeriodType periodType, final Integer gpDuration) throws IOException {
        ZooKeeperColumnsMapNbiStore.logger.debug("load method called");
        if (periodType == PeriodType.GP && gpDuration == null) {
            ZooKeeperColumnsMapNbiStore.logger.error("Illegal arguments on call to save (periodType = GP && gpDuration = null)");
            throw new IllegalArgumentException("gpDuration must be not null when periodType = 'GP'");
        }
        final String path = this.buildZooKeeperPath(entityClass, periodType, gpDuration);
        ZooKeeperColumnsMapNbiStore.logger.debug("Zookeeper path to use: {}", path);
        final byte[] masCSGet = this.masConfigurationService.get(path);
        if (masCSGet == null) {
            ZooKeeperColumnsMapNbiStore.logger.warn("MAS Config Service returned null for path.");
            return null;
        }
        final String mappingJson = new String(masCSGet);
        ZooKeeperColumnsMapNbiStore.logger.info("MAS Config Service returned {} for path {}.", mappingJson, path);
        final Map<String, InsightAdditionalInfo> mapping = new NBIColumnAdditionalInfoCfgUtils().getMapFromJSONCfg(mappingJson);
        ZooKeeperColumnsMapNbiStore.logger.info("Successfully parsed {} mapping(s) for entityClass={}, periodType={}, gpDuration={}", mapping.size(), entityClass, periodType.toString(), gpDuration.toString());
        return mapping;
    }
    
    protected String buildZooKeeperPath(final String entityClass, final PeriodType periodType, final Integer gpDuration) {
        String periodTypeInKey = "";
        if (periodType == PeriodType.GP) {
            periodTypeInKey = PeriodType.GP.toString() + gpDuration.toString() + "min";
        }
        else {
            periodTypeInKey = periodType.toString();
        }
        final StringBuilder pathBuilder = new StringBuilder().append("/").append(this.getColumnsMapRootZNode()).append("/").append(entityClass).append("/").append(periodTypeInKey).append("/").append("map");
        final String path = pathBuilder.toString();
        return path;
    }
    
    @Override
    public void setMasConfigurationService(final ConfigurationService masConfigurationService) {
        this.masConfigurationService = masConfigurationService;
    }
    
    static {
        logger = LoggerFactory.getLogger(ZooKeeperColumnsMapNbiStore.class);
    }
}
