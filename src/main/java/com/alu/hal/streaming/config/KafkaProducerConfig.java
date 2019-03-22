// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.config;

import java.util.Hashtable;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import java.util.Properties;
import org.apache.log4j.Logger;

public class KafkaProducerConfig
{
    private static Logger LOG;
    private static final String PROPERTIES = "/kafkaProducer.properties";
    private final Properties properties;
    
    public Properties getProperties() {
        return this.properties;
    }
    
    private KafkaProducerConfig() {
        this.properties = new Properties();
        try {
            this.properties.load(KafkaProducerConfig.class.getResourceAsStream("/kafkaProducer.properties"));
            if (this.properties.getProperty("key.serializer") == null || this.properties.getProperty("value.serializer") == null) {
                KafkaProducerConfig.LOG.warn("Kafka producer properties key.serializer and/or value.serializer are set at /kafkaProducer.propertiesThese values will be overridden by following mandatory values key.serializer=" + StringSerializer.class.getName() + " and " + "value.serializer" + "=" + ByteArraySerializer.class.getName());
                ((Hashtable<String, Class<StringSerializer>>)this.properties).put("key.serializer", StringSerializer.class);
                ((Hashtable<String, Class<ByteArraySerializer>>)this.properties).put("value.serializer", ByteArraySerializer.class);
            }
        }
        catch (Exception e) {
            KafkaProducerConfig.LOG.warn("/kafkaProducer.properties not found");
        }
    }
    
    public static KafkaProducerConfig instance() {
        return SingletonHolder.INSTANCE;
    }
    
    @Override
    public String toString() {
        return this.properties.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue()).collect((Collector<? super Object, ?, String>)Collectors.joining(" , "));
    }
    
    static {
        KafkaProducerConfig.LOG = Logger.getLogger(KafkaProducerConfig.class);
    }
    
    private static class SingletonHolder
    {
        public static final KafkaProducerConfig INSTANCE;
        
        static {
            INSTANCE = new KafkaProducerConfig(null);
        }
    }
}
