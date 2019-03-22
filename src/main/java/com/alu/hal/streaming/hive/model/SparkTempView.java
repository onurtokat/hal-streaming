// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.hive.model;

import com.alu.hal.streaming.hive.model.recommendation.RecommendationType;
import java.io.Serializable;

public class SparkTempView implements Serializable
{
    public static final SparkTempView TR98_TRANSPOSE;
    public static final SparkTempView TR181_TRANSPOSE;
    public static final SparkTempView UNIFIED_WIFI_DVC;
    public static final SparkTempView AGGREGATED_INPUT_TABLE;
    public static final SparkTempView HOME_ACCESS_POINT_KPI_BASIC_AGG;
    public static final SparkTempView HOME_ACCESS_POINT_KPI_KQI_OBS_SWND_AGG;
    public static final SparkTempView HOME_ASSOC_DEVICE_KPI_KQI_OBS_SWND_AGG;
    public static final SparkTempView HOME_RADIO_KPI_BASIC_AGG;
    public static final SparkTempView HOME_RADIO_KPI_SWND_AGG;
    public static final SparkTempView HOME_KPI_BASIC_AGG;
    public static final SparkTempView HOME_KPI_SWND_AGG;
    public static final SparkTempView HOME_DEVICE_KPI_BASIC_AGG;
    public static final SparkTempView HOME_DEVICE_KPI_SWND_AGG;
    private static final String POSITIVE_OBS_TEMP_VIEW_SUFIX = "_positive_mem";
    private static final String NEGATIVE_OBS_TEMP_VIEW_SUFIX = "_negative_mem";
    private final String tempViewName;
    
    private SparkTempView(final String tempViewName) {
        this.tempViewName = tempViewName;
    }
    
    @Override
    public String toString() {
        return this.tempViewName;
    }
    
    public String getTempViewName() {
        return this.tempViewName;
    }
    
    public static SparkTempView ofTransposeTableOfModelType(final ModelType modelType) {
        switch (modelType) {
            case TR98: {
                return SparkTempView.TR98_TRANSPOSE;
            }
            default: {
                return SparkTempView.TR181_TRANSPOSE;
            }
        }
    }
    
    public static SparkTempView ofRecommendationObservation(final RecommendationType recommendationType, final boolean isPositiveObservation) {
        return isPositiveObservation ? new SparkTempView(recommendationType.getName() + "_positive_mem") : new SparkTempView(recommendationType.getName() + "_negative_mem");
    }
    
    static {
        TR98_TRANSPOSE = new SparkTempView("tr98_transpose_mem");
        TR181_TRANSPOSE = new SparkTempView("tr181_transpose_mem");
        UNIFIED_WIFI_DVC = new SparkTempView("unified_wifi_dvc_mem");
        AGGREGATED_INPUT_TABLE = new SparkTempView("aggregated_input_mem");
        HOME_ACCESS_POINT_KPI_BASIC_AGG = new SparkTempView("home_access_point_kpi_basic_agg_mem");
        HOME_ACCESS_POINT_KPI_KQI_OBS_SWND_AGG = new SparkTempView("home_access_point_kpi_swnd_agg_mem");
        HOME_ASSOC_DEVICE_KPI_KQI_OBS_SWND_AGG = new SparkTempView("home_assoc_device_kpi_swnd_agg_mem");
        HOME_RADIO_KPI_BASIC_AGG = new SparkTempView("home_radio_kpi_basic_agg_mem");
        HOME_RADIO_KPI_SWND_AGG = new SparkTempView("home_radio_kpi_swnd_agg_mem");
        HOME_KPI_BASIC_AGG = new SparkTempView("home_kpi_basic_agg_mem");
        HOME_KPI_SWND_AGG = new SparkTempView("home_kpi_swnd_agg_mem");
        HOME_DEVICE_KPI_BASIC_AGG = new SparkTempView("home_device_kpi_basic_agg_mem");
        HOME_DEVICE_KPI_SWND_AGG = new SparkTempView("home_device_kpi_swnd_agg_mem");
    }
}
