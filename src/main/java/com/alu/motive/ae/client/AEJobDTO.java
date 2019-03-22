// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.ae.client;

import javax.persistence.Transient;
import org.apache.commons.lang.StringUtils;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "aeJob")
public class AEJobDTO implements Serializable
{
    private static final long serialVersionUID = -6424172403321405031L;
    private static final String DEFAULT_JOB_NAME = "AEJobDefault";
    private static final String DEFAULT_DATA_START_TIME = "2014-03-23T00:00";
    private static final int DEFAULT_DATA_GP = 60;
    private static final String DEFAULT_JOB_START_TIME = "2014-06-23T00:00";
    private static final String DEFAULT_JOB_END_TIME = "2025-03-23T00:00";
    private static final int DEFAULT_JOB_FREQUENCY = 60;
    private String jobName;
    private String jobDescription;
    private String jobId;
    private String jobStartTime;
    private String jobEndTime;
    private int jobFrequency;
    private String dataStartTime;
    private String dataEndTime;
    private int dataGP;
    private transient byte[] jobData;
    private String hdfsDirPath;
    private String updateDirPath;
    
    public String getJobName() {
        return this.jobName;
    }
    
    public void setJobName(final String jobName) {
        this.jobName = jobName;
    }
    
    public String getJobDescription() {
        return this.jobDescription;
    }
    
    public void setJobDescription(final String jobDescription) {
        this.jobDescription = jobDescription;
    }
    
    public String getJobId() {
        return this.jobId;
    }
    
    public void setJobId(final String jobId) {
        this.jobId = jobId;
    }
    
    public String getJobStartTime() {
        return this.jobStartTime;
    }
    
    public void setJobStartTime(final String jobStartTime) {
        this.jobStartTime = jobStartTime;
    }
    
    public String getJobEndTime() {
        return this.jobEndTime;
    }
    
    public void setJobEndTime(final String jobEndTime) {
        this.jobEndTime = jobEndTime;
    }
    
    public int getJobFrequency() {
        return this.jobFrequency;
    }
    
    public void setJobFrequency(final int jobFrequency) {
        this.jobFrequency = jobFrequency;
    }
    
    public String getDataStartTime() {
        return this.dataStartTime;
    }
    
    public void setDataStartTime(final String dataStartTime) {
        this.dataStartTime = dataStartTime;
    }
    
    public String getDataEndTime() {
        return this.dataEndTime;
    }
    
    public void setDataEndTime(final String dataEndTime) {
        this.dataEndTime = dataEndTime;
    }
    
    public int getDataGP() {
        return this.dataGP;
    }
    
    public void setDataGP(final int dataGP) {
        this.dataGP = dataGP;
    }
    
    public byte[] getJobData() {
        return this.jobData;
    }
    
    public void setJobData(final byte[] jobData) {
        this.jobData = jobData;
    }
    
    @Transient
    public boolean isOnDemand() {
        return StringUtils.isNotBlank(this.dataEndTime);
    }
    
    private void setOnDemand(final boolean onDemand) {
    }
    
    public String getHdfsDirPath() {
        return this.hdfsDirPath;
    }
    
    public void setHdfsDirPath(final String hdfsDirPath) {
        this.hdfsDirPath = hdfsDirPath;
    }
    
    public String getUpdateDirPath() {
        return this.updateDirPath;
    }
    
    public void setUpdateDirPath(final String updateDirPath) {
        this.updateDirPath = updateDirPath;
    }
    
    public static AEJobDTO createFakeAEJob() {
        final AEJobDTO job = new AEJobDTO();
        job.setJobName("AEJobDefault");
        job.setDataStartTime("2014-03-23T00:00");
        job.setDataGP(60);
        job.setJobStartTime("2014-06-23T00:00");
        job.setJobEndTime("2025-03-23T00:00");
        job.setJobFrequency(60);
        return job;
    }
    
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("AEJob [jobName=").append(this.jobName);
        builder.append(", jobId=").append(this.jobId);
        builder.append(", jobStartTime=").append(this.jobStartTime);
        builder.append(", jobEndTime=").append(this.jobEndTime);
        builder.append(", jobFrequency=").append(this.jobFrequency);
        builder.append(", dataStartTime=").append(this.dataStartTime);
        builder.append(", dataEndTime=").append(this.dataEndTime);
        builder.append(", hdfsDirPath=").append(this.hdfsDirPath);
        builder.append(", updateDirPath=").append(this.updateDirPath);
        builder.append(", dataGP=").append(this.dataGP).append("]");
        return builder.toString();
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = 31 * result + ((this.jobName == null) ? 0 : this.jobName.hashCode());
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
        final AEJobDTO other = (AEJobDTO)obj;
        if (this.jobName == null) {
            if (other.jobName != null) {
                return false;
            }
        }
        else if (!this.jobName.equals(other.jobName)) {
            return false;
        }
        return true;
    }
}
