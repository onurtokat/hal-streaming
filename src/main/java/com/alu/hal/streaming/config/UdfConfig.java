// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.config;

import java.util.Set;
import org.apache.hadoop.fs.FileSystem;
import com.alu.motive.hal.udf.hive.udtf.InvalidHdfsUrlException;
import java.io.IOException;
import java.io.Reader;
import com.alu.motive.hal.udf.hive.udtf.HDFSHelper;
import org.apache.log4j.Logger;
import java.util.Properties;

public class UdfConfig
{
    private final Properties configProp;
    private static Logger LOG;
    private static final String HDFS_URL = "hdfs.url";
    
    private UdfConfig() {
        this.configProp = new Properties();
        final DashBoardConfig sparkConfig = DashBoardConfig.instance();
        final String udfConfigFilePath = sparkConfig.getString(DashBoardConfig.UDF_THRESHOLDS_FILE, "/data/hda/udfThresholdsFile.properties");
        final String checkPointUrl = sparkConfig.getString(DashBoardConfig.SPARK_CHECKPOINT_URL);
        final String hdfsUrl = checkPointUrl.substring(0, checkPointUrl.lastIndexOf("/"));
        try {
            final FileSystem fileSystem = HDFSHelper.getFileSystem(hdfsUrl);
            this.configProp.load(HDFSHelper.getInputStreamFromFile(hdfsUrl, udfConfigFilePath));
            HDFSHelper.close(fileSystem);
            UdfConfig.LOG.info("properties loaded from hdfsURL=" + hdfsUrl + " file=" + udfConfigFilePath);
            this.configProp.setProperty("hdfs.url", hdfsUrl);
            this.configProp.setProperty(DashBoardConfig.UDF_THRESHOLDS_FILE, udfConfigFilePath);
            this.configProp.setProperty(DashBoardConfig.MAC_FILE, sparkConfig.getString(DashBoardConfig.MAC_FILE, "/data/hda/MACs.txt"));
        }
        catch (IOException e) {
            e.printStackTrace();
            UdfConfig.LOG.error("");
        }
        catch (InvalidHdfsUrlException ex) {
            UdfConfig.LOG.error("Exception during init of udf configuration " + ex.getMessage());
        }
    }
    
    public static UdfConfig getInstance() {
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
    
    static {
        UdfConfig.LOG = Logger.getLogger(RecommendationsConfig.class);
    }
    
    private static class LazyHolder
    {
        private static final UdfConfig INSTANCE;
        
        static {
            INSTANCE = new UdfConfig(null);
        }
    }
}
