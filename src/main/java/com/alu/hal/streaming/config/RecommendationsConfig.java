// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.config;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.List;
import java.util.Set;
import java.io.InputStream;
import java.io.IOException;
import org.apache.log4j.Logger;
import java.util.Properties;

public class RecommendationsConfig
{
    private final Properties configProp;
    private static final String RECOMMENDATIONS_TYPE = "recommendations.type";
    public static final String POSITIVE_QUERY_FILE_NAME_SUFFIX = ".positive.query.file.name";
    public static final String NEGATIVE_QUERY_FILE_NAME_SUFFIX = ".negative.query.file.name";
    public static final String NO_POSITIVE_OBS_SUFFIX = ".no.positive.obs";
    public static final String NO_NEGATIVE_OBS_SUFFIX = ".no.negative.obs";
    public static final String TIMEOUT_SUFIX = ".timeout";
    public static final String DEFAULT_NO_POSITIVE_OBS = "default.no.positive.obs";
    public static final String DEFAULT_NO_NEGATIVE_OBS = "default.no.negative.obs";
    public static final String DEFAULT_TIMEOUT = "default.timeout";
    private static Logger LOG;
    
    private RecommendationsConfig() {
        this.configProp = new Properties();
        final InputStream in = this.getClass().getClassLoader().getResourceAsStream("recommendations.properties");
        RecommendationsConfig.LOG.debug("Read all properties from file");
        try {
            this.configProp.load(in);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static RecommendationsConfig getInstance() {
        return LazyHolder.INSTANCE;
    }
    
    public String getProperty(final String key) {
        return this.configProp.getProperty(key);
    }
    
    public String getProperty(final String key, final String defaultValue) {
        return this.configProp.getProperty(key, defaultValue);
    }
    
    public Set<String> getAllPropertyNames() {
        return this.configProp.stringPropertyNames();
    }
    
    public boolean containsKey(final String key) {
        return this.configProp.containsKey(key);
    }
    
    public List<String> getListOfRecommendations() {
        return Pattern.compile(",").splitAsStream(this.configProp.getProperty("recommendations.type", "")).map((Function<? super String, ?>)String::trim).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
    }
    
    static {
        RecommendationsConfig.LOG = Logger.getLogger(RecommendationsConfig.class);
    }
    
    private static class LazyHolder
    {
        private static final RecommendationsConfig INSTANCE;
        
        static {
            INSTANCE = new RecommendationsConfig(null);
        }
    }
}
