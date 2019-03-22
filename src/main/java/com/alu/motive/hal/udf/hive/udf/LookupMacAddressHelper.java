// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.udf.hive.udf;

import org.apache.commons.logging.LogFactory;
import java.io.InputStream;
import org.apache.hadoop.fs.Path;
import java.io.InputStreamReader;
import java.io.IOException;
import com.alu.motive.hal.udf.hive.udf.exception.InvalidHDFSUrlException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.commons.logging.Log;

public class LookupMacAddressHelper
{
    private static final Log LOG;
    
    public static FileSystem getFileSystem(final String hdfsURL) throws InvalidHDFSUrlException, IOException {
        final Configuration conf = new Configuration();
        conf.set("fs.defaultFS", hdfsURL);
        conf.setBoolean("fs.hdfs.impl.disable.cache", true);
        final FileSystem fileSystem = FileSystem.get(conf);
        checkHdfsURL(hdfsURL);
        return fileSystem;
    }
    
    public static void close(FileSystem fileSystem) {
        if (fileSystem != null) {
            try {
                fileSystem.close();
                fileSystem = null;
            }
            catch (IOException e) {
                LookupMacAddressHelper.LOG.error("Error closing the FileSystem: ", e);
            }
        }
    }
    
    private static void checkHdfsURL(final String hdfsURL) throws InvalidHDFSUrlException {
        if (hdfsURL == null || hdfsURL.isEmpty()) {
            throw new InvalidHDFSUrlException("the hdfsURL cannot be null or empty");
        }
        if (!hdfsURL.startsWith("hdfs://")) {
            throw new InvalidHDFSUrlException("invalid hdfs format. The format must be 'hdfs://machineIP:port' . Right now is:" + hdfsURL);
        }
    }
    
    public static InputStreamReader getInputStreamFromFile(final FileSystem fileSystem, final String hdfsFilePath) throws IOException {
        if (hdfsFilePath == null || hdfsFilePath.isEmpty()) {
            throw new IOException("Cannot build an input stream from an empty or null file path. Currently is:" + hdfsFilePath);
        }
        final InputStreamReader stream = new InputStreamReader(fileSystem.open(new Path(hdfsFilePath)), "UTF-8");
        return stream;
    }
    
    static {
        LOG = LogFactory.getLog(LookupMacAddressHelper.class.getName());
    }
}
