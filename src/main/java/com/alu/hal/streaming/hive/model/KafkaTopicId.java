// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.hive.model;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.EnumSet;

public enum KafkaTopicId
{
    TR69REPORTS("tr69reports", false), 
    UNIFIED_WIFI_DVC_AVRO_TOPIC("unifiedWifiDvc", true), 
    ACCESS_POINT_KPI_BASIC_AVRO_TOPIC("accessPointKpiBasic", true), 
    ACCESS_POINT_KPI_SWND_AVRO_TOPIC("accessPointKpiSwnd", true), 
    ASSOC_DEVICE_KPI_SWND_AVRO_TOPIC("assocDeviceKpiSwnd", true), 
    DEVICE_KPI_BASIC_AVRO_TOPIC("deviceKpiBasic", true), 
    DEVICE_KPI_SWND_AVRO_TOPIC("deviceKpiSwnd", true), 
    HOME_KPI_BASIC_AVRO_TOPIC("homeKpiBasic", true), 
    HOME_KPI_SWND_AVRO_TOPIC("homeKpiSwnd", true), 
    RADIO_KPI_BASIC_AVRO_TOPIC("radioKpiBasic", true), 
    RADIO_KPI_SWND_AVRO_TOPIC("radioKpiSwnd", true), 
    RECOMMENDATIONS_AVRO_TOPIC("recommendations", true);
    
    private String configId;
    private boolean isAvroKafkaTopic;
    
    private KafkaTopicId(final String configId, final boolean isAvroKafkaTopic) {
        this.configId = configId;
        this.isAvroKafkaTopic = isAvroKafkaTopic;
    }
    
    public String getConfigId() {
        return this.configId;
    }
    
    public boolean isAvroKafkaTopic() {
        return this.isAvroKafkaTopic;
    }
    
    public static EnumSet<KafkaTopicId> getAvroKafkaTopicIds() {
        return EnumSet.allOf(KafkaTopicId.class).stream().filter(KafkaTopicId::isAvroKafkaTopic).collect((Collector<? super Object, ?, EnumSet<KafkaTopicId>>)Collectors.toCollection(() -> EnumSet.noneOf(KafkaTopicId.class)));
    }
    
    public static EnumSet<KafkaTopicId> getnonAvroKafkaTopicIds() {
        return EnumSet.allOf(KafkaTopicId.class).stream().filter(kafkaTopicId -> !kafkaTopicId.isAvroKafkaTopic()).collect((Collector<? super Object, ?, EnumSet<KafkaTopicId>>)Collectors.toCollection(() -> EnumSet.noneOf(KafkaTopicId.class)));
    }
}
