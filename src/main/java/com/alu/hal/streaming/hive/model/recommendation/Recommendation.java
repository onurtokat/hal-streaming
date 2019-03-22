// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.hive.model.recommendation;

import java.util.UUID;
import org.apache.log4j.Logger;
import java.io.Serializable;

public abstract class Recommendation implements Serializable
{
    private static Logger LOG;
    private long lastUpdateTs;
    private RecommendationState state;
    private int positiveCount;
    private int negativeCount;
    private long beginDate;
    private long endDate;
    private UUID id;
    
    public Recommendation() {
        this.lastUpdateTs = 0L;
        this.lastUpdateTs = this.getCurrentTimeInSeconds();
        this.state = RecommendationState.NEW;
        this.positiveCount = 0;
        this.negativeCount = 0;
        this.id = UUID.randomUUID();
    }
    
    public long getLastUpdateTs() {
        return this.lastUpdateTs;
    }
    
    public void setLastUpdateTs(final long lastUpdateTs) {
        this.lastUpdateTs = lastUpdateTs;
    }
    
    public RecommendationState getState() {
        return this.state;
    }
    
    public int getPositiveCount() {
        return this.positiveCount;
    }
    
    public int getNegativeCount() {
        return this.negativeCount;
    }
    
    protected void updateStateAndCounts(final long latestQueryTs, final int positiveThreshold, final int negativeThreshold, final boolean positiveObservation) {
        if (this.state != RecommendationState.INVALID_NO_REPORTING && this.getBeginDate() > latestQueryTs && latestQueryTs != 0L) {
            return;
        }
        if (positiveObservation) {
            ++this.positiveCount;
            this.negativeCount = 0;
        }
        else {
            ++this.negativeCount;
            this.positiveCount = 0;
        }
        switch (this.state) {
            case NEW:
            case INVALID_NO_REPORTING:
            case TIMEDOUT_NO_REPORTING: {
                if (this.positiveCount >= positiveThreshold) {
                    this.state = RecommendationState.ACTIVE;
                    final boolean b = false;
                    this.negativeCount = (b ? 1 : 0);
                    this.positiveCount = (b ? 1 : 0);
                    this.setBeginDate(latestQueryTs);
                    this.setEndDate(0L);
                    this.setLastUpdateTs(latestQueryTs);
                    this.id = UUID.randomUUID();
                    break;
                }
                break;
            }
            case ACTIVE_NO_REPORTING: {
                if (this.negativeCount >= negativeThreshold) {
                    this.state = RecommendationState.INVALID;
                    final boolean b2 = false;
                    this.negativeCount = (b2 ? 1 : 0);
                    this.positiveCount = (b2 ? 1 : 0);
                    this.setEndDate(latestQueryTs);
                    this.setLastUpdateTs(latestQueryTs);
                    break;
                }
                if (positiveObservation) {
                    this.state = RecommendationState.ACTIVE;
                    this.setLastUpdateTs(latestQueryTs);
                    break;
                }
                break;
            }
        }
        Recommendation.LOG.info("updateStateAndCounts result: positiveObservation=" + positiveObservation + ",recommendation=" + this.toString());
    }
    
    public boolean isSendNotification() {
        return this.state.equals(RecommendationState.ACTIVE);
    }
    
    public boolean isToExport() {
        return this.state.equals(RecommendationState.ACTIVE) || this.state.equals(RecommendationState.INVALID) || this.state.equals(RecommendationState.NO_DATA);
    }
    
    protected void initialize() {
        switch (this.state) {
            case ACTIVE: {
                this.state = RecommendationState.ACTIVE_NO_REPORTING;
                break;
            }
            case INVALID: {
                this.state = RecommendationState.INVALID_NO_REPORTING;
                break;
            }
            case NO_DATA: {
                this.state = RecommendationState.TIMEDOUT_NO_REPORTING;
                break;
            }
        }
    }
    
    protected boolean isTimedOut() {
        return this.state == RecommendationState.NO_DATA || this.state == RecommendationState.TIMEDOUT_NO_REPORTING;
    }
    
    protected void resetCounters() {
        final boolean b = false;
        this.negativeCount = (b ? 1 : 0);
        this.positiveCount = (b ? 1 : 0);
    }
    
    @Override
    public String toString() {
        return "Recommendation{lastUpdateTs=" + this.lastUpdateTs + " uuid=" + this.id + ", state=" + this.state + ", positiveCount=" + this.positiveCount + ", negativeCount=" + this.negativeCount + '}';
    }
    
    public void setState(final RecommendationState state) {
        this.state = state;
    }
    
    public long getBeginDate() {
        return this.beginDate;
    }
    
    public void setBeginDate(final long beginDate) {
        this.beginDate = beginDate;
    }
    
    public long getEndDate() {
        return this.endDate;
    }
    
    public void setEndDate(final long endDate) {
        this.endDate = endDate;
    }
    
    public UUID getId() {
        return this.id;
    }
    
    protected long getCurrentTimeInSeconds() {
        return System.currentTimeMillis() / 1000L;
    }
    
    static {
        Recommendation.LOG = Logger.getLogger(Recommendation.class);
    }
}
