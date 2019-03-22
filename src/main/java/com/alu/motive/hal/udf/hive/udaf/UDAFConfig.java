// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.udf.hive.udaf;

import org.slf4j.LoggerFactory;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.List;
import com.alu.motive.hal.udf.hive.udtf.InvalidHdfsUrlException;
import java.io.IOException;
import org.apache.hadoop.fs.FileSystem;
import java.io.Reader;
import com.alu.motive.hal.udf.hive.udtf.HDFSHelper;
import java.util.Properties;
import org.slf4j.Logger;

public class UDAFConfig
{
    private static final Logger LOG;
    private static UDAFConfig INSTANCE;
    private final Properties properties;
    
    private UDAFConfig(final String hdfsURL, final String filePath) throws IOException, InvalidHdfsUrlException {
        this.properties = new Properties();
        final FileSystem fileSystem = HDFSHelper.getFileSystem(hdfsURL);
        this.properties.load(HDFSHelper.getInputStreamFromFile(hdfsURL, filePath));
        HDFSHelper.close(fileSystem);
        log("properties loaded from hdfsURL=" + hdfsURL + " file=" + filePath);
    }
    
    public static void log(final String message) {
        UDAFConfig.LOG.info(message);
    }
    
    public static UDAFConfig getInstance(final String hdfsURL, final String filePath) throws IOException, InvalidHdfsUrlException {
        if (UDAFConfig.INSTANCE == null) {
            UDAFConfig.INSTANCE = new UDAFConfig(hdfsURL, filePath);
        }
        log("returning INSTANCE=" + UDAFConfig.INSTANCE);
        return UDAFConfig.INSTANCE;
    }
    
    public String getString(final String key) {
        return this.properties.getProperty(key);
    }
    
    public String getString(final String key, final String defaultValue) {
        return this.properties.getProperty(key, defaultValue);
    }
    
    public int getInteger(final String key) {
        return Integer.parseInt(this.properties.getProperty(key));
    }
    
    public int getInteger(final String key, final int defaultValue) {
        return Integer.parseInt(this.properties.getProperty(key, Integer.toString(defaultValue)));
    }
    
    public long getLong(final String key) {
        return Long.parseLong(this.properties.getProperty(key));
    }
    
    public long getLong(final String key, final long defaultValue) {
        return Long.parseLong(this.properties.getProperty(key, Long.toString(defaultValue)));
    }
    
    public List<String> getListOfString(final String key) {
        return Pattern.compile(",").splitAsStream(this.properties.getProperty(key, "")).map((Function<? super String, ?>)String::trim).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
    }
    
    public List<Integer> getListOfInteger(final String key) {
        return Pattern.compile(",").splitAsStream(this.properties.getProperty(key, "")).map((Function<? super String, ?>)Integer::valueOf).collect((Collector<? super Object, ?, List<Integer>>)Collectors.toList());
    }
    
    public List<Double> getListOfDouble(final String key) {
        return Pattern.compile(",").splitAsStream(this.properties.getProperty(key, "")).map((Function<? super String, ?>)Double::valueOf).collect((Collector<? super Object, ?, List<Double>>)Collectors.toList());
    }
    
    public boolean getBoolean(final String key, final boolean defaultValue) {
        return Boolean.valueOf(this.properties.getProperty(key, Boolean.toString(defaultValue)));
    }
    
    static {
        LOG = LoggerFactory.getLogger(UDAFConfig.class);
        UDAFConfig.INSTANCE = null;
    }
}
