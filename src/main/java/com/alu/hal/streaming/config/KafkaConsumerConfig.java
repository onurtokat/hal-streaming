// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.config;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.Properties;
import org.apache.log4j.Logger;

public class KafkaConsumerConfig
{
    private static Logger LOG;
    private static final String PROPERTIES = "/kafkaConsumer.properties";
    private final Properties properties;
    
    public Properties getProperties() {
        return this.properties;
    }
    
    public Map<String, Object> getMap() {
        return this.properties.entrySet().stream().collect(Collectors.toMap(key -> key.getKey(), value -> value.getValue()));
    }
    
    private KafkaConsumerConfig() {
        this.properties = new Properties();
        try {
            this.properties.load(KafkaProducerConfig.class.getResourceAsStream("/kafkaConsumer.properties"));
        }
        catch (Exception e) {
            KafkaConsumerConfig.LOG.warn("/kafkaConsumer.properties not found");
        }
    }
    
    public static KafkaConsumerConfig instance() {
        return SingletonHolder.INSTANCE;
    }
    
    @Override
    public String toString() {
        return this.properties.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue()).collect((Collector<? super Object, ?, String>)Collectors.joining(" , "));
    }
    
    static {
        KafkaConsumerConfig.LOG = Logger.getLogger(KafkaProducerConfig.class);
    }
    
    private static class SingletonHolder
    {
        public static final KafkaConsumerConfig INSTANCE;
        
        static {
            INSTANCE = new KafkaConsumerConfig(null);
        }
    }
}
