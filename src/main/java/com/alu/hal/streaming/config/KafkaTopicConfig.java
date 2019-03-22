// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.config;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.AbstractMap;
import java.net.URI;
import java.util.Properties;
import com.alu.hal.streaming.hive.model.AvroKafkaTopic;
import com.alu.hal.streaming.hive.model.KafkaTopicId;
import java.util.Map;
import org.apache.log4j.Logger;

public class KafkaTopicConfig
{
    private static Logger LOG;
    private static final String PROPERTIES = "/kafkaTopic.properties";
    private String tr69reportsTopicName;
    private Map<KafkaTopicId, AvroKafkaTopic> avroTopicsById;
    
    private KafkaTopicConfig() {
        try {
            final Properties properties = new Properties();
            properties.load(KafkaTopicConfig.class.getResourceAsStream("/kafkaTopic.properties"));
            this.avroTopicsById = this.createAvroTopicsById(properties);
            this.tr69reportsTopicName = properties.getProperty(this.getTopicNamePropertyKey(KafkaTopicId.TR69REPORTS));
        }
        catch (Exception e) {
            KafkaTopicConfig.LOG.warn("/kafkaTopic.properties not loaded", e);
        }
    }
    
    private Map<KafkaTopicId, AvroKafkaTopic> createAvroTopicsById(final Properties properties) {
        final String topicName;
        final String avroSchemaUrlString;
        final AvroKafkaTopic avroKafkaTopic;
        return KafkaTopicId.getAvroKafkaTopicIds().stream().filter(avroTopicConfigId -> properties.containsKey(this.getTopicNamePropertyKey(avroTopicConfigId)) && properties.containsKey(this.getAvroSchemaUrlPropertyKey(avroTopicConfigId))).map(avroTopicConfigId -> {
            topicName = properties.getProperty(this.getTopicNamePropertyKey(avroTopicConfigId));
            avroSchemaUrlString = properties.getProperty(this.getAvroSchemaUrlPropertyKey(avroTopicConfigId));
            avroKafkaTopic = new AvroKafkaTopic(URI.create(avroSchemaUrlString), topicName);
            return new AbstractMap.SimpleEntry(avroTopicConfigId, avroKafkaTopic);
        }).collect(Collectors.toMap((Function<? super Object, ? extends KafkaTopicId>)Map.Entry::getKey, (Function<? super Object, ? extends AvroKafkaTopic>)Map.Entry::getValue));
    }
    
    private String getTopicNamePropertyKey(final KafkaTopicId kafkaTopicId) {
        return "kafka." + kafkaTopicId.getConfigId() + ".topic.name";
    }
    
    private String getAvroSchemaUrlPropertyKey(final KafkaTopicId kafkaTopicId) {
        return "kafka." + kafkaTopicId.getConfigId() + ".url";
    }
    
    public static KafkaTopicConfig instance() {
        return SingletonHolder.INSTANCE;
    }
    
    public AvroKafkaTopic getAvroKafkaTopic(final KafkaTopicId kafkaTopicId) {
        return this.avroTopicsById.get(kafkaTopicId);
    }
    
    public String getTr69reportsTopicName() {
        return this.tr69reportsTopicName;
    }
    
    static {
        KafkaTopicConfig.LOG = Logger.getLogger(KafkaTopicConfig.class);
    }
    
    private static class SingletonHolder
    {
        public static final KafkaTopicConfig INSTANCE;
        
        static {
            INSTANCE = new KafkaTopicConfig(null);
        }
    }
}
