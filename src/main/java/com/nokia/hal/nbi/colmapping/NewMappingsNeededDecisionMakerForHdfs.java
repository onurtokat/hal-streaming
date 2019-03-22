// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.colmapping;

import org.slf4j.LoggerFactory;
import java.util.regex.Matcher;
import com.nokia.hal.nbi.colmapping.exception.HalColumnsMappingInvalidMarkerFormatException;
import com.nokia.hal.nbi.colmapping.exception.HalColumnsMappingHdfsAccessException;
import com.alu.motive.ae.hdfs.HdfsUtils;
import java.util.regex.Pattern;
import org.slf4j.Logger;

public class NewMappingsNeededDecisionMakerForHdfs extends NewMappingsNeededDecisionMakerAbstract
{
    static final Logger LOG;
    private String hdfsUrl;
    protected static final Pattern PATTERN;
    protected static final String markerPrefix = "LAST_SUBMISSION_TIME";
    protected String appFolderName;
    
    public NewMappingsNeededDecisionMakerForHdfs(final InsightsEditorNbiApi nbiApi) {
        super(nbiApi);
        this.appFolderName = "";
    }
    
    NewMappingsNeededDecisionMakerForHdfs(final InsightsEditorNbiApi nbiApi, final String hdfsUrl, final String appFolderName) {
        super(nbiApi);
        this.appFolderName = "";
        this.hdfsUrl = hdfsUrl;
        this.appFolderName = appFolderName;
    }
    
    public NewMappingsNeededDecisionMakerForHdfs setHdfsUrl(final String hdfsUrl) {
        this.hdfsUrl = hdfsUrl;
        return this;
    }
    
    public NewMappingsNeededDecisionMakerForHdfs setAppFolder(final String appFolder) {
        this.appFolderName = appFolder;
        return this;
    }
    
    @Override
    public void updateLastSubmissionTime() {
        HdfsUtils.deleteFilesInFolderByPrefix(this.hdfsUrl, this.appFolderName, "LAST_SUBMISSION_TIME");
        final String filePath = this.generateNewMarkerName(this.lastSubmissionTimeFromIE);
        HdfsUtils.createFileInHDFS(this.hdfsUrl, filePath);
    }
    
    private String generatePath() {
        return this.appFolderName + "/" + "LAST_SUBMISSION_TIME" + "_";
    }
    
    private String generateNewMarkerName(final Long newSubmissionTime) {
        return this.generatePath() + newSubmissionTime.toString();
    }
    
    @Override
    protected Long getLastSubmissionTime() throws HalColumnsMappingHdfsAccessException {
        Long submissionTime = -1L;
        try {
            NewMappingsNeededDecisionMakerForHdfs.LOG.debug("checking HDFS for marker files with hdfsUrl: {} , markerFolderName: {} , markerPrefix: {}", this.hdfsUrl, this.appFolderName, "LAST_SUBMISSION_TIME");
            final String fileName = HdfsUtils.getLastCreatedFileWithPrefix(this.hdfsUrl, this.appFolderName, "LAST_SUBMISSION_TIME");
            NewMappingsNeededDecisionMakerForHdfs.LOG.debug("retrieved the following marker from HDFS: {}", fileName);
            if (fileName == null || fileName.isEmpty()) {
                NewMappingsNeededDecisionMakerForHdfs.LOG.warn("no marker files found in HDFS, this is normal if it is the first time this job has ran. Path is: {}*", this.generatePath());
            }
            else {
                submissionTime = this.getTSFromName(fileName);
            }
        }
        catch (Exception ex) {
            ColumnsMappingForNbi.logException(NewMappingsNeededDecisionMakerForHdfs.LOG, ex);
            NewMappingsNeededDecisionMakerForHdfs.LOG.error("failed to get saved last submission time for path: {}", this.generatePath());
            throw new HalColumnsMappingHdfsAccessException(String.format("could not retrieve marker file from HDFS: %s*", this.generatePath()));
        }
        return submissionTime;
    }
    
    protected Long getTSFromName(final String fileName) throws HalColumnsMappingInvalidMarkerFormatException {
        Long ts = 0L;
        final Matcher matcher = NewMappingsNeededDecisionMakerForHdfs.PATTERN.matcher(fileName);
        if (matcher.matches()) {
            if (!matcher.group(1).toString().equals("LAST_SUBMISSION_TIME")) {
                throw new HalColumnsMappingInvalidMarkerFormatException(fileName);
            }
            ts = Long.parseLong(matcher.group(2).toString());
        }
        return ts;
    }
    
    static {
        LOG = LoggerFactory.getLogger(NewMappingsNeededDecisionMakerForHdfs.class);
        PATTERN = Pattern.compile("^(\\w+)_(\\d+)");
    }
}
