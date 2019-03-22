// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.ae.hdfs;

import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;
import java.util.Map;
import org.apache.hadoop.fs.Path;
import java.io.IOException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

public class HdfsUtils
{
    public static final String TIMESTAMP_SEPARATOR = "_";
    private static final String EXISTS_MSG = "determine the existence of file";
    private static final String CREATE_FILE_MSG = "create the file";
    private static final String RETRIEVE_MSG = "retrieve last created file";
    private static final String LIST_MSG = "list files";
    private static final String DELETE_MSG = "delete file";
    private static final String READ_MSG = "read the file content";
    private static final String REPLACE_MSG = "replace the file content";
    private static final String COPY_IN_HDFS_MSG = "copy a file from HDFS to HDFS";
    private static final String COPY_TO_HDFS_MSG = "copy a file from the local filesystem to HDFS";
    private static final String COPY_TO_LOCAL_MSG = "copy a file from HDFS to the local filesystem";
    private static final String CREATE_DIRS_MSG = "create a directory";
    private static final Logger LOG;
    
    public static boolean exists(final String hdfsURL, final String filePath) {
        boolean exists = false;
        try {
            exists = (StringUtils.isNotBlank(filePath) && HdfsUtilsImpl.exists(HdfsUtils.LOG, hdfsURL, filePath));
        }
        catch (IOException e) {
            throwWithParams("determine the existence of file", hdfsURL, filePath, e);
        }
        return exists;
    }
    
    public static String getLastCreatedFileWithPrefix(final String hdfsURL, final String folderName, final String prefix) {
        String fileName = null;
        try {
            fileName = HdfsUtilsImpl.getLastCreatedFileWithPrefix(HdfsUtils.LOG, hdfsURL, folderName, prefix);
        }
        catch (IOException e) {
            throwWithParams("retrieve last created file", hdfsURL, folderName, prefix, e);
        }
        return fileName;
    }
    
    public static void copyFilesFromHDFSToHDFS(final String hdfsURL, final String sourceFolderPath, final String targetFolderPath, final String filePrefix) {
        try {
            HdfsUtilsImpl.copyFilesFromHDFSToHDFS(HdfsUtils.LOG, hdfsURL, sourceFolderPath, targetFolderPath, filePrefix);
        }
        catch (IOException e) {
            throwWithParams("copy a file from HDFS to HDFS", hdfsURL, sourceFolderPath, targetFolderPath, e);
        }
    }
    
    public static void createFileInHDFS(final String hdfsURL, final String filePath) {
        try {
            HdfsUtilsImpl.createFileInHDFS(HdfsUtils.LOG, hdfsURL, filePath);
        }
        catch (IOException e) {
            throwWithParams("create the file", hdfsURL, filePath, e);
        }
    }
    
    public static void deleteFilesInFolderByPrefix(final String hdfsURL, final String folderName, final String prefix) {
        HdfsUtils.LOG.debug("deleteFilesInFolderByPrefix for {}, and {}", folderName, prefix);
        try {
            HdfsUtilsImpl.deleteFilesInFolderByPrefix(HdfsUtils.LOG, hdfsURL, folderName, prefix);
        }
        catch (IOException e) {
            throwWithParams("list files", hdfsURL, folderName, prefix, e);
        }
        catch (HdfsUtilsImplWithFS.CannotDeleteFileException e2) {
            final Path filePath = e2.getFile().getPath();
            throwWithParams("delete file", hdfsURL, filePath.getParent().getName(), filePath.getName(), e2.getCause());
        }
    }
    
    public static String readFileContent(final String hdfsURL, final String filePath) {
        HdfsUtils.LOG.debug("readFromFile {}", filePath);
        String content = null;
        try {
            content = HdfsUtilsImpl.readFileContent(HdfsUtils.LOG, hdfsURL, filePath);
            HdfsUtils.LOG.debug("readFromFile returns {}", content);
        }
        catch (IOException e) {
            throwWithParams("read the file content", hdfsURL, filePath, e);
        }
        return content;
    }
    
    public static void copyToHdfs(final String hdfsURL, final Map<String, String> sourceDestinationFilePaths) {
        for (final Map.Entry<String, String> entry : sourceDestinationFilePaths.entrySet()) {
            copyToHdfs(hdfsURL, entry.getKey(), entry.getValue());
        }
    }
    
    public static void copyToHdfs(final String hdfsURL, final List<String> sourcePaths, final String destinationPath) {
        final Map<String, String> sourceDestinationFilePaths = new HashMap<String, String>();
        for (final String sourceFile : sourcePaths) {
            sourceDestinationFilePaths.put(sourceFile, destinationPath + "/" + getFileNameFromPath(sourceFile));
        }
        copyToHdfs(hdfsURL, sourceDestinationFilePaths);
    }
    
    private static String getFileNameFromPath(final String path) {
        return new Path(path).getName();
    }
    
    public static void copyToHdfs(final String hdfsURL, final String sourcePath, final String destinationPath) {
        try {
            HdfsUtilsImpl.copyToHdfs(HdfsUtils.LOG, hdfsURL, sourcePath, destinationPath);
        }
        catch (IOException e) {
            throw new RuntimeException(String.format("%s %s, %s, %s", getErrorMessageStart("copy a file from the local filesystem to HDFS"), hdfsURL, sourcePath, destinationPath), e);
        }
    }
    
    public static void copyToLocal(final String hdfsURL, final String sourcePath, final String destinationPath) {
        try {
            HdfsUtilsImpl.copyToLocal(HdfsUtils.LOG, hdfsURL, sourcePath, destinationPath);
        }
        catch (IOException e) {
            throw new RuntimeException(String.format("%s %s, %s, %s", getErrorMessageStart("copy a file from HDFS to the local filesystem"), hdfsURL, sourcePath, destinationPath), e);
        }
    }
    
    public static boolean mkdirs(final String hdfsURL, final String path) {
        boolean result = false;
        try {
            result = HdfsUtilsImpl.mkdirs(HdfsUtils.LOG, hdfsURL, path);
        }
        catch (IOException e) {
            throwWithParams("create a directory", hdfsURL, path, e);
        }
        return result;
    }
    
    public static boolean delete(final String hdfsURL, final String path, final boolean recursive) {
        boolean result = false;
        try {
            result = HdfsUtilsImpl.delete(HdfsUtils.LOG, hdfsURL, path, recursive);
        }
        catch (IOException e) {
            throwWithParams("delete file", hdfsURL, path, e);
        }
        return result;
    }
    
    public static boolean rename(final String hdfsURL, final String sourceFilePath, final String targetFilePath) {
        try {
            return HdfsUtilsImpl.rename(HdfsUtils.LOG, hdfsURL, sourceFilePath, targetFilePath);
        }
        catch (IOException e) {
            final String msg = "Exception while trying to rename '" + sourceFilePath + "' to '" + targetFilePath + "'";
            HdfsUtils.LOG.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }
    
    public static void createNewFileFromFileAndReplaceContent(final String hdfsURL, final String oldFilePath, final String newFilePath, final String oldText, final String newText) {
        HdfsUtils.LOG.debug("readFromFile {}, replace {} with {} and create new file ", oldFilePath, oldText, newText, newFilePath);
        System.out.println("readFromFile " + oldFilePath + " replace " + oldText + " with " + newText + " and create new file " + newFilePath);
        try {
            HdfsUtilsImpl.replaceFileContentAndCreateNewFile(HdfsUtils.LOG, hdfsURL, oldFilePath, newFilePath, oldText, newText);
        }
        catch (IOException e) {
            throwWithParams("replace the file content", hdfsURL, oldFilePath, e);
        }
    }
    
    private static void throwWithParams(final String specificMessage, final String hdfsUrl, final String folderName, final String prefix, final Throwable e) {
        final Map<String, String> params = new HashMap<String, String>();
        if (hdfsUrl != null) {
            params.put("HDFS_URL", hdfsUrl);
        }
        if (folderName != null) {
            params.put("folder", folderName);
        }
        if (prefix != null) {
            params.put("prefix", prefix);
        }
        final String message = getErrorMessage(specificMessage, params);
        HdfsUtils.LOG.error(message.toString(), e);
        if (e != null) {
            throw new RuntimeException(message, e);
        }
        throw new RuntimeException(message);
    }
    
    private static void throwWithParams(final String specificMessage, final String hdfsUrl, final String folderName, final Throwable e) {
        throwWithParams(specificMessage, hdfsUrl, folderName, null, e);
    }
    
    private static String getErrorMessage(final String specificMessage, final Map<String, String> params) {
        final StringBuilder message = new StringBuilder(getErrorMessageStart(specificMessage));
        for (final Map.Entry<String, String> entry : params.entrySet()) {
            message.append(entry.getKey());
            message.append("=");
            message.append(entry.getValue());
            message.append(" ");
        }
        return message.toString();
    }
    
    private static String getErrorMessageStart(final String specificMessage) {
        return "Got exception while trying to " + specificMessage + " with the parameter(s) : ";
    }
    
    static {
        LOG = LoggerFactory.getLogger(HdfsUtils.class);
    }
}
