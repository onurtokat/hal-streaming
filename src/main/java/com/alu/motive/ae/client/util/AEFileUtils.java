// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.ae.client.util;

import org.slf4j.LoggerFactory;
import com.alu.motive.ae.client.exception.AEException;
import com.alu.motive.ae.client.exception.AEIssueType;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.io.FileInputStream;
import org.slf4j.Logger;

public class AEFileUtils
{
    private static final Logger LOG;
    private static final String FILE_ENCODING = "UTF-8";
    
    public static String readFileContent(final String fileAbsolutePath) {
        final StringBuilder content = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileAbsolutePath), Charset.forName("UTF-8")));
            readLines(content, reader);
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException("File not found");
        }
        catch (IOException e2) {
            throw new RuntimeException("IO Error occured");
        }
        finally {
            closeReader(reader);
        }
        return content.toString();
    }
    
    private static void readLines(final StringBuilder content, final BufferedReader reader) throws IOException {
        String line = "";
        while ((line = reader.readLine()) != null) {
            content.append(line).append("\n");
        }
    }
    
    private static void closeReader(final BufferedReader reader) {
        if (reader != null) {
            try {
                reader.close();
            }
            catch (IOException e) {
                AEFileUtils.LOG.info("Unable to close file resource");
            }
        }
    }
    
    public static File createFile(final File parentDir, final String fileRelativePath, final String fileContent) {
        PrintWriter printWriter = null;
        try {
            final File newFile = createNewFile(parentDir, fileRelativePath);
            printWriter = new PrintWriter(newFile, "UTF-8");
            printWriter.print(fileContent);
            return newFile;
        }
        catch (IOException e) {
            throw new RuntimeException("Exception while writting to file '" + fileRelativePath + "' inside " + parentDir, e);
        }
        finally {
            if (printWriter != null) {
                printWriter.flush();
                printWriter.close();
            }
        }
    }
    
    public static boolean fileExists(final File parentDir, final String fileRelativePath) {
        final File file = new File(parentDir, fileRelativePath);
        return file.exists();
    }
    
    private static File createNewFile(final File parentDir, final String fileRelativePath) throws IOException {
        validateParentDir(parentDir);
        final File newFile = new File(parentDir, fileRelativePath);
        if (newFile.exists()) {
            throw new AEException("The file '" + newFile.getName() + "' already exists", AEIssueType.ERROR);
        }
        final File parentFile = newFile.getParentFile();
        if (!parentFile.exists() && !parentFile.mkdirs()) {
            throw new RuntimeException("Cannot create the necessary directories for '" + parentFile.getAbsolutePath() + "'");
        }
        if (!newFile.createNewFile()) {
            throw new RuntimeException("Cannot create the file '" + newFile.getAbsolutePath() + "'");
        }
        return newFile;
    }
    
    private static void validateParentDir(final File parentDir) {
        validatePath(parentDir);
        if (!parentDir.isDirectory()) {
            throw new IllegalArgumentException("The file '" + parentDir + "' is not a directory");
        }
    }
    
    private static void validatePath(final File parentDir) {
        if (parentDir == null) {
            throw new IllegalArgumentException("The parent directory cannot be null");
        }
        if (!parentDir.exists()) {
            throw new IllegalArgumentException("The file '" + parentDir + "' does not exist");
        }
    }
    
    static {
        LOG = LoggerFactory.getLogger(AEFileUtils.class);
    }
}
