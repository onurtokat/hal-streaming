// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.udf.hive.udtf;

import org.slf4j.LoggerFactory;
import org.apache.hadoop.fs.FSDataInputStream;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import org.apache.hadoop.fs.Path;
import java.io.IOException;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.conf.Configuration;
import java.io.InputStreamReader;
import org.slf4j.Logger;

public class HDFSHelper
{
    private static final Logger LOG;
    private static final String CONF_FS_DEFAULT_NAME = "fs.default.name";
    
    public static InputStreamReader getInputStreamFromFile(final String hdfsURL, final String hdfsFilePath) throws IOException, InvalidHdfsUrlException {
        final Configuration conf = new Configuration();
        conf.set("fs.default.name", hdfsURL);
        final FileSystem fileSystem = FileSystem.get(conf);
        checkHdfsURL(hdfsURL);
        if (hdfsFilePath == null || hdfsFilePath.isEmpty()) {
            throw new IOException("Cannot build an input stream from an empty or null file path. Currently is:" + hdfsFilePath);
        }
        final InputStreamReader stream = new InputStreamReader(fileSystem.open(new Path(hdfsFilePath)), "UTF-8");
        return stream;
    }
    
    public static String readFileContentFromFS(final String hdfsURL, final String filePath) throws IOException {
        FileSystem fileSystem = null;
        try {
            fileSystem = getFileSystem(hdfsURL);
            return readFileContent(fileSystem, filePath);
        }
        finally {
            close(fileSystem);
        }
    }
    
    public static String readFileContent(final FileSystem fileSystem, final String filePath) throws IOException {
        FSDataInputStream in = null;
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final BufferedOutputStream bos = new BufferedOutputStream(baos);
        try {
            in = fileSystem.open(new Path(filePath));
            transferData(in, bos);
            return baos.toString("UTF-8");
        }
        finally {
            bos.close();
            if (in != null) {
                in.close();
            }
        }
    }
    
    public static FileSystem getFileSystem(final String hdfsURL) {
        final Configuration conf = new Configuration();
        FileSystem fs = null;
        conf.set("fs.defaultFS", hdfsURL);
        conf.setBoolean("fs.hdfs.impl.disable.cache", true);
        try {
            fs = FileSystem.get(conf);
            if (fs == null) {
                final String msg = "Cannot create the filesystem for HDFS URL = '" + hdfsURL + "'";
                throw new RuntimeException(msg);
            }
        }
        catch (IOException e) {
            final String msg2 = "Got exception while trying to create the filesystem for HDFS URL = '" + hdfsURL + "' : " + e.getMessage();
            throw new RuntimeException(msg2, e);
        }
        return fs;
    }
    
    public static void close(FileSystem fileSystem) {
        if (fileSystem != null) {
            try {
                fileSystem.close();
                fileSystem = null;
            }
            catch (IOException e) {
                HDFSHelper.LOG.error("Error closing the FileSystem: ", e);
            }
        }
    }
    
    private static void transferData(final FSDataInputStream in, final BufferedOutputStream bos) throws IOException {
        final byte[] buffer = new byte[256];
        int readBytes = 0;
        while ((readBytes = in.read(buffer)) > 0) {
            bos.write(buffer, 0, readBytes);
        }
        bos.flush();
    }
    
    private static void checkHdfsURL(final String hdfsURL) throws InvalidHdfsUrlException {
        if (hdfsURL == null || hdfsURL.isEmpty()) {
            throw new InvalidHdfsUrlException("the hdfsURL cannot be null or empty");
        }
        if (!hdfsURL.startsWith("hdfs://")) {
            throw new InvalidHdfsUrlException("invalid hdfs format. The format must be 'hdfs://machineIP:port' . Right now is:" + hdfsURL);
        }
    }
    
    static {
        LOG = LoggerFactory.getLogger(HDFSHelper.class);
    }
}
