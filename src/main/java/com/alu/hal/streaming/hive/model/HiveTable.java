// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.hive.model;

import java.io.Serializable;

public enum HiveTable implements Serializable
{
    UNIFIED_WIFI_DVC("unified_wifi_dvc"), 
    UNIFIED_WIFI_DVC2("unified_wifi_dvc_tmp2"), 
    HOME_ASSOC_DEVICE_KPI_KQI_OBS_SWNV_AGG("home_assoc_device_kpi_swnd_agg"), 
    HOME_ACCESS_POINT_KPI_KQI_OBS_SWND_AGG("home_access_point_kpi_swnd_agg"), 
    HOME_ACCESS_POINT_KPI_BASIC_AGG("home_access_point_kpi_basic_agg"), 
    TR98_TRANSPOSE_TABLE("tr98_transpose_table"), 
    TR181_TRANSPOSE_TABLE("tr181_transpose_table"), 
    HOME_RADIO_KPI_BASIC_AGG("home_radio_kpi_basic_agg"), 
    HOME_RADIO_KPI_SWND_AGG("home_radio_kpi_swnd_agg"), 
    HOME_KPI_BASIC_AGG("home_kpi_basic_agg"), 
    HOME_KPI_SWND_AGG("home_kpi_swnd_agg"), 
    HOME_DEVICE_KPI_BASIC_AGG("home_device_kpi_basic_agg"), 
    HOME_DEVICE_KPI_SWND_AGG("home_device_kpi_swnd_agg");
    
    private final String tableName;
    
    private HiveTable(final String tableName) {
        this.tableName = tableName;
    }
    
    @Override
    public String toString() {
        return this.tableName;
    }
    
    public String getTableName() {
        return this.tableName;
    }
}
