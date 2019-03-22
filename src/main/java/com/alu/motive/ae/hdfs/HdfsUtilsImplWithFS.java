// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.ae.hdfs;

import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.hadoop.fs.FSDataInputStream;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import org.apache.hadoop.fs.PathFilter;
import java.util.Arrays;
import java.util.Comparator;
import org.apache.hadoop.fs.FileStatus;
import org.slf4j.Logger;
import java.io.IOException;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FileSystem;

class HdfsUtilsImplWithFS
{
    private static final String UTF_8_ENCODING = "UTF-8";
    
    static boolean exists(final FileSystem fileSystem, final String filePath) throws IOException {
        return fileSystem.exists(new Path(filePath));
    }
    
    static String getLastCreatedFileWithPrefix(final Logger log, final FileSystem fileSystem, final String folderName, final String prefix) throws IOException {
        return getLastCreatedFileName(getFilesForFilter(log, fileSystem, folderName, prefix));
    }
    
    private static String getLastCreatedFileName(final FileStatus[] files) {
        if (files == null || files.length == 0) {
            return null;
        }
        if (files.length > 1) {
            Arrays.sort(files, new Comparator<FileStatus>() {
                @Override
                public int compare(final FileStatus f1, final FileStatus f2) {
                    return Long.valueOf(f2.getModificationTime()).compareTo(Long.valueOf(f1.getModificationTime()));
                }
            });
        }
        final String fileName = files[0].getPath().getName();
        return fileName;
    }
    
    private static FileStatus[] getFilesForFilter(final Logger log, final FileSystem fileSystem, final String folderName, final String prefix) throws IOException {
        log.debug("getting files from the '{}' folder having the name starting with '{}'", folderName, prefix);
        final FileStatus[] files = fileSystem.listStatus(new Path(folderName), createFilter(log, prefix));
        if (files == null) {
            throw new RuntimeException(String.format("The folder %s does not exists", folderName));
        }
        return files;
    }
    
    private static PathFilter createFilter(final Logger log, final String prefix) {
        return new PathFilter() {
            @Override
            public boolean accept(final Path path) {
                return isMatchingFilename(path.getName(), prefix);
            }
        };
    }
    
    private static boolean isMatchingFilename(final String fileName, final String prefix) {
        if (!fileName.startsWith(prefix + "_")) {
            return false;
        }
        final String timestamp = fileName.substring((prefix + "_").length());
        if (timestamp.matches("[0-9]+")) {
            System.out.println("found file " + fileName + " for the prefix " + prefix);
            return true;
        }
        return false;
    }
    
    static void createFileInHDFS(final Logger log, final FileSystem fileSystem, final String filePath) throws IOException {
        final Path destPath = new Path(filePath);
        fileSystem.createNewFile(destPath);
        log.debug("file {} was created in HDFS", destPath.getName());
    }
    
    static void deleteFilesInFolderByPrefix(final Logger log, final FileSystem fileSystem, final String folderName, final String prefix) throws IOException, CannotDeleteFileException {
        deleteFilesInFolder(log, fileSystem, getFilesForFilter(log, fileSystem, folderName, prefix));
    }
    
    private static void deleteFilesInFolder(final Logger log, final FileSystem fileSystem, final FileStatus[] files) throws CannotDeleteFileException {
        if (files == null) {
            return;
        }
        log.debug("received {} files to delete", (Object)files.length);
        for (final FileStatus file : files) {
            try {
                fileSystem.delete(file.getPath(), true);
                log.debug("file deleted : {}", file.getPath().getName());
            }
            catch (IOException e) {
                throw new CannotDeleteFileException(file, e);
            }
        }
    }
    
    static String readFileContent(final Logger log, final FileSystem fileSystem, final String filePath) throws IOException {
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
    
    static void replaceFileContentAndCreateNewFile(final Logger log, final FileSystem fileSystem, final String oldFilePath, final String newFilePath, final String oldText, final String newText) throws IOException {
        FSDataInputStream in = null;
        try {
            in = fileSystem.open(new Path(oldFilePath));
            final BufferedReader bfr = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            final BufferedWriter br = new BufferedWriter(new OutputStreamWriter(fileSystem.create(new Path(newFilePath), true), "UTF-8"));
            copyData(oldText, newText, bfr, br);
            br.close();
            log.debug("file {} was written " + newFilePath);
        }
        finally {
            if (in != null) {
                in.close();
            }
        }
    }
    
    static void copyFilesFromHDFSToHDFS(final Logger log, final FileSystem fileSystem, final String sourceFolderPath, final String targetFolderPath, final String filePrefix) throws IOException {
        final FileStatus[] files = fileSystem.listStatus(new Path(sourceFolderPath));
        if (files == null) {
            throw new RuntimeException(String.format("The folder %s does not exists", sourceFolderPath));
        }
        int index = 0;
        for (final FileStatus file : files) {
            final String sourceFileName = file.getPath().getName();
            final String pathSeparator = "/";
            final String sourceFile = sourceFolderPath + pathSeparator + sourceFileName;
            final String fileNamePrefix = (index > 0) ? (filePrefix + "_" + index) : filePrefix;
            final String newFile = targetFolderPath + pathSeparator + fileNamePrefix;
            System.out.println("copy " + sourceFile + " to " + newFile);
            replaceFileContentAndCreateNewFile(log, fileSystem, sourceFile, newFile, null, null);
            ++index;
        }
    }
    
    private static void copyData(final String oldText, final String newText, final BufferedReader bfr, final BufferedWriter br) throws IOException {
        boolean replaceText = false;
        if ((oldText != null && !oldText.isEmpty()) || (newText != null && !newText.isEmpty())) {
            replaceText = true;
        }
        String str;
        while ((str = bfr.readLine()) != null) {
            if (replaceText) {
                str = str.replace(oldText, newText);
            }
            br.write(str);
            br.newLine();
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
    
    static void copyToHdfs(final Logger log, final FileSystem fileSystem, final String sourcePath, final String destinationPath) throws IOException {
        fileSystem.copyFromLocalFile(new Path(sourcePath), new Path(destinationPath));
        log.debug("file '{}' was copied to '{}' in HDFS", sourcePath, destinationPath);
    }
    
    static void copyToLocal(final Logger log, final FileSystem fileSystem, final String sourcePath, final String destinationPath) throws IOException {
        fileSystem.copyToLocalFile(new Path(sourcePath), new Path(destinationPath));
        log.debug("file '{}' from HDFS was copied to '{}' in the local filesystem", sourcePath, destinationPath);
    }
    
    static boolean mkdirs(final Logger log, final FileSystem fileSystem, final String path) throws IOException {
        final boolean result = fileSystem.mkdirs(new Path(path));
        if (result) {
            log.debug("the '{}' directory was successfully created", path);
        }
        else {
            log.debug("unable to create the '{}' directory", path);
        }
        return result;
    }
    
    static boolean delete(final Logger log, final FileSystem fileSystem, final String path, final boolean recursive) throws IOException {
        final boolean result = fileSystem.delete(new Path(path), recursive);
        if (result) {
            log.debug("'{}' was successfully deleted", path);
        }
        else {
            log.debug("unable to delete '{}'", path);
        }
        return result;
    }
    
    static boolean rename(final FileSystem fileSystem, final String sourceFilePath, final String targetFilePath) throws IOException {
        return fileSystem.rename(new Path(sourceFilePath), new Path(targetFilePath));
    }
    
    static class CannotDeleteFileException extends Exception
    {
        private final FileStatus file;
        
        public CannotDeleteFileException(final FileStatus file, final IOException cause) {
            super(cause);
            this.file = file;
        }
        
        public FileStatus getFile() {
            return this.file;
        }
    }
}
