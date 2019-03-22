// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.ae.client;

import java.io.File;

public class JobFileInfo
{
    private static final String NL;
    private static final String PATH_SEPARATOR = "/";
    private final File sourceFile;
    private final String pathOfTheFileInArchive;
    
    public JobFileInfo(final File sourceFile) {
        this(sourceFile, "/");
    }
    
    public JobFileInfo(final File sourceFile, final String fileLocationInTheArchive) {
        this(sourceFile, fileLocationInTheArchive, sourceFile.getName());
    }
    
    public JobFileInfo(final File sourceFile, final String fileLocationInTheArchive, final String fileNameInArchive) {
        this.sourceFile = sourceFile;
        String normalizedFL;
        if (fileLocationInTheArchive == null) {
            normalizedFL = "/";
        }
        else {
            normalizedFL = (fileLocationInTheArchive.endsWith("/") ? fileLocationInTheArchive : (fileLocationInTheArchive + "/"));
        }
        final String normalizedFn = fileNameInArchive.startsWith("/") ? fileNameInArchive.substring(1) : fileNameInArchive;
        this.pathOfTheFileInArchive = normalizedFL + normalizedFn;
    }
    
    public File getSourceFile() {
        return this.sourceFile;
    }
    
    public String getPathOfTheFileInArchive() {
        return this.pathOfTheFileInArchive;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = 31 * result + ((this.pathOfTheFileInArchive == null) ? 0 : this.pathOfTheFileInArchive.hashCode());
        result = 31 * result + ((this.sourceFile == null) ? 0 : this.sourceFile.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final JobFileInfo other = (JobFileInfo)obj;
        return this.isFieldEqual(this.pathOfTheFileInArchive, other.pathOfTheFileInArchive) && this.isFieldEqual(this.sourceFile, other.sourceFile);
    }
    
    private boolean isFieldEqual(final Object thisFieldValue, final Object otherFieldValue) {
        if (thisFieldValue == null) {
            return otherFieldValue == null;
        }
        return thisFieldValue.equals(otherFieldValue);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(super.toString());
        sb.append("[");
        sb.append(JobFileInfo.NL);
        sb.append("  sourceFile = ");
        sb.append((this.sourceFile == null) ? "null" : this.sourceFile.getAbsolutePath());
        sb.append(JobFileInfo.NL);
        sb.append("  pathOfTheFileInArchive = ");
        sb.append(this.pathOfTheFileInArchive);
        sb.append(JobFileInfo.NL);
        sb.append("]");
        return sb.toString();
    }
    
    static {
        NL = System.getProperty("line.separator");
    }
}
