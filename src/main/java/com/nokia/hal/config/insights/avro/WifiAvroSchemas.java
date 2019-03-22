// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.config.insights.avro;

import com.nokia.hal.config.insights.InsightFlow;
import com.nokia.hal.config.insights.InsightMonitoredPointType;
import com.nokia.hal.config.insights.InsightDomain;

public class WifiAvroSchemas
{
    public enum SchemaNames
    {
        KPI_HOME_WIFI_ACCESS_POINT("home_access_point_kpi_basic_agg.avsc", InsightDomain.WIFI, InsightMonitoredPointType.ACCESS_POINT, InsightFlow.STREAMING), 
        KPI_HOME_WIFI_AGG_ACCESS_POINT("home_access_point_kpi_swnd_agg.avsc", InsightDomain.WIFI, InsightMonitoredPointType.ACCESS_POINT, InsightFlow.SLIDING_WINDOW), 
        KPI_HOME_WIFI_AGG_ASSOC_DEVICE("home_assoc_device_kpi_swnd_agg.avsc", InsightDomain.WIFI, InsightMonitoredPointType.ASSOC_DEVICE, InsightFlow.SLIDING_WINDOW), 
        KPI_HOME_WIFI_RADIO("home_radio_kpi_basic_agg.avsc", InsightDomain.WIFI, InsightMonitoredPointType.RADIO, InsightFlow.STREAMING), 
        KPI_HOME_WIFI_RADIO_SWND_HQL("home_radio_kpi_swnd_agg.avsc", InsightDomain.WIFI, InsightMonitoredPointType.RADIO, InsightFlow.SLIDING_WINDOW), 
        KPI_HOME_WIFI_MGD_DEVICE("home_device_kpi_basic_agg.avsc", InsightDomain.WIFI, InsightMonitoredPointType.MGD_DEVICE, InsightFlow.STREAMING), 
        KPI_HOME_WIFI_MGD_DEVICE_SWND("home_device_kpi_swnd_agg.avsc", InsightDomain.WIFI, InsightMonitoredPointType.MGD_DEVICE, InsightFlow.SLIDING_WINDOW), 
        KPI_HOME_WIFI("home_kpi_basic_agg.avsc", InsightDomain.WIFI, InsightMonitoredPointType.HOME, InsightFlow.STREAMING), 
        KPI_HOME_WIFI_SWND("home_kpi_swnd_agg.avsc", InsightDomain.WIFI, InsightMonitoredPointType.HOME, InsightFlow.SLIDING_WINDOW), 
        KPI_HOME_WIFI_ACCESS_POINT_DY("home_access_point_kpi_kqi_obs_agg_dy.avsc", InsightDomain.WIFI, InsightMonitoredPointType.ACCESS_POINT, InsightFlow.DAILY), 
        KPI_HOME_WIFI_ASSOC_DEVICE_DY("home_assoc_device_kpi_kqi_obs_agg_dy.avsc", InsightDomain.WIFI, InsightMonitoredPointType.ASSOC_DEVICE, InsightFlow.DAILY), 
        KPI_HOME_WIFI_RADIO_DY("home_radio_kpi_kqi_obs_agg_dy.avsc", InsightDomain.WIFI, InsightMonitoredPointType.RADIO, InsightFlow.DAILY), 
        KPI_HOME_WIFI_MGD_DEVICE_DY("home_device_kpi_kqi_obs_agg_dy.avsc", InsightDomain.WIFI, InsightMonitoredPointType.MGD_DEVICE, InsightFlow.DAILY), 
        KPI_HOME_WIFI_DY("home_kpi_kqi_obs_agg_dy.avsc", InsightDomain.WIFI, InsightMonitoredPointType.HOME, InsightFlow.DAILY);
        
        private String avscFileName;
        private InsightDomain domain;
        private InsightMonitoredPointType mpType;
        private InsightFlow flow;
        
        private SchemaNames(final String avscFileName, final InsightDomain domain, final InsightMonitoredPointType mpType, final InsightFlow flow) {
            this.avscFileName = avscFileName;
            this.domain = domain;
            this.mpType = mpType;
            this.flow = flow;
        }
        
        public String getAvscFileName() {
            return this.avscFileName;
        }
        
        public InsightDomain getDomain() {
            return this.domain;
        }
        
        public InsightMonitoredPointType getMonitoredPointType() {
            return this.mpType;
        }
        
        public InsightFlow getFlow() {
            return this.flow;
        }
    }
}
