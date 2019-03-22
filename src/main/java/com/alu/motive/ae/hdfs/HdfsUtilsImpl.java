// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.ae.hdfs;

import org.apache.hadoop.conf.Configuration;
import java.io.IOException;
import org.apache.hadoop.fs.FileSystem;
import org.slf4j.Logger;

class HdfsUtilsImpl
{
    static boolean exists(final Logger log, final String hdfsURL, final String filePath) throws IOException {
        FileSystem fileSystem = null;
        try {
            fileSystem = getFileSystem(log, hdfsURL);
            return HdfsUtilsImplWithFS.exists(fileSystem, filePath);
        }
        finally {
            close(log, fileSystem);
        }
    }
    
    static String getLastCreatedFileWithPrefix(final Logger log, final String hdfsURL, final String folderName, final String prefix) throws IOException {
        FileSystem fileSystem = null;
        try {
            fileSystem = getFileSystem(log, hdfsURL);
            return HdfsUtilsImplWithFS.getLastCreatedFileWithPrefix(log, fileSystem, folderName, prefix);
        }
        finally {
            close(log, fileSystem);
        }
    }
    
    static void createFileInHDFS(final Logger log, final String hdfsURL, final String filePath) throws IOException {
        FileSystem fileSystem = null;
        try {
            fileSystem = getFileSystem(log, hdfsURL);
            HdfsUtilsImplWithFS.createFileInHDFS(log, fileSystem, filePath);
        }
        finally {
            close(log, fileSystem);
        }
    }
    
    static void deleteFilesInFolderByPrefix(final Logger log, final String hdfsURL, final String folderName, final String prefix) throws IOException, HdfsUtilsImplWithFS.CannotDeleteFileException {
        FileSystem fileSystem = null;
        try {
            fileSystem = getFileSystem(log, hdfsURL);
            HdfsUtilsImplWithFS.deleteFilesInFolderByPrefix(log, fileSystem, folderName, prefix);
        }
        finally {
            close(log, fileSystem);
        }
    }
    
    static String readFileContent(final Logger log, final String hdfsURL, final String filePath) throws IOException {
        FileSystem fileSystem = null;
        try {
            fileSystem = getFileSystem(log, hdfsURL);
            return HdfsUtilsImplWithFS.readFileContent(log, fileSystem, filePath);
        }
        finally {
            close(log, fileSystem);
        }
    }
    
    static void replaceFileContentAndCreateNewFile(final Logger log, final String hdfsURL, final String oldFilePath, final String newFilePath, final String oldText, final String newText) throws IOException {
        FileSystem fileSystem = null;
        try {
            fileSystem = getFileSystem(log, hdfsURL);
            HdfsUtilsImplWithFS.replaceFileContentAndCreateNewFile(log, fileSystem, oldFilePath, newFilePath, oldText, newText);
        }
        finally {
            close(log, fileSystem);
        }
    }
    
    static void copyFilesFromHDFSToHDFS(final Logger log, final String hdfsURL, final String sourceFolderPath, final String targetFolderPath, final String filePrefix) throws IOException {
        FileSystem fileSystem = null;
        try {
            fileSystem = getFileSystem(log, hdfsURL);
            HdfsUtilsImplWithFS.copyFilesFromHDFSToHDFS(log, fileSystem, sourceFolderPath, targetFolderPath, filePrefix);
        }
        finally {
            close(log, fileSystem);
        }
    }
    
    static void copyToHdfs(final Logger log, final String hdfsURL, final String sourcePath, final String destinationPath) throws IOException {
        FileSystem fileSystem = null;
        try {
            fileSystem = getFileSystem(log, hdfsURL);
            HdfsUtilsImplWithFS.copyToHdfs(log, fileSystem, sourcePath, destinationPath);
        }
        finally {
            close(log, fileSystem);
        }
    }
    
    static void copyToLocal(final Logger log, final String hdfsURL, final String sourcePath, final String destinationPath) throws IOException {
        FileSystem fileSystem = null;
        try {
            fileSystem = getFileSystem(log, hdfsURL);
            HdfsUtilsImplWithFS.copyToLocal(log, fileSystem, sourcePath, destinationPath);
        }
        finally {
            close(log, fileSystem);
        }
    }
    
    static boolean mkdirs(final Logger log, final String hdfsURL, final String path) throws IOException {
        FileSystem fileSystem = null;
        try {
            fileSystem = getFileSystem(log, hdfsURL);
            return HdfsUtilsImplWithFS.mkdirs(log, fileSystem, path);
        }
        finally {
            close(log, fileSystem);
        }
    }
    
    static boolean delete(final Logger log, final String hdfsURL, final String path, final boolean recursive) throws IOException {
        FileSystem fileSystem = null;
        try {
            fileSystem = getFileSystem(log, hdfsURL);
            return HdfsUtilsImplWithFS.delete(log, fileSystem, path, recursive);
        }
        finally {
            close(log, fileSystem);
        }
    }
    
    static boolean rename(final Logger log, final String hdfsURL, final String sourceFilePath, final String targetFilePath) throws IOException {
        FileSystem fileSystem = null;
        try {
            fileSystem = getFileSystem(log, hdfsURL);
            return HdfsUtilsImplWithFS.rename(fileSystem, sourceFilePath, targetFilePath);
        }
        finally {
            close(log, fileSystem);
        }
    }
    
    private static FileSystem getFileSystem(final Logger log, final String hdfsURL) {
        final Configuration conf = new Configuration();
        FileSystem fs = null;
        conf.set("fs.defaultFS", hdfsURL);
        conf.setBoolean("fs.hdfs.impl.disable.cache", true);
        try {
            fs = FileSystem.get(conf);
            if (fs == null) {
                final String msg = "Cannot create the filesystem for HDFS URL = '" + hdfsURL + "'";
                log.error(msg);
                throw new RuntimeException(msg);
            }
        }
        catch (IOException e) {
            final String msg2 = "Got exception while trying to create the filesystem for HDFS URL = '" + hdfsURL + "' : " + e.getMessage();
            log.error(msg2, e);
            throw new RuntimeException(msg2, e);
        }
        return fs;
    }
    
    private static void close(final Logger log, FileSystem fileSystem) {
        if (fileSystem != null) {
            try {
                fileSystem.close();
                fileSystem = null;
            }
            catch (IOException e) {
                log.error("Error closing the FileSystem: ", e);
            }
        }
        else {
            log.info("FileSystem is null, cannot close");
        }
    }
}
