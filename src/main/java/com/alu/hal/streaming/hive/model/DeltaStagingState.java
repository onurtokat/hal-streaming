// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.hive.model;

import com.alu.hal.streaming.config.WifiSparkConfig;
import org.apache.spark.streaming.State;
import org.apache.spark.api.java.Optional;
import java.util.ArrayList;
import org.apache.spark.streaming.Time;
import java.util.List;
import org.apache.log4j.Logger;
import java.io.Serializable;

public class DeltaStagingState implements Serializable
{
    private static final long serialVersionUID = 1L;
    private static Logger LOG;
    private final List<MgdDevTransposeColumnState> mgdDevTransposeColumnStates;
    private Time batchTime;
    
    public DeltaStagingState(final Time currentBatchTime) {
        this.mgdDevTransposeColumnStates = new ArrayList<MgdDevTransposeColumnState>();
        this.batchTime = currentBatchTime;
    }
    
    public static DeltaStagingState updateDeltaStagingState(final Optional<MgdDevTransposeColumnState> mgdReport, final State<DeltaStagingState> state, final Time currentBatchTime) {
        DeltaStagingState.LOG.debug("updateDeltaStage called with state=" + state + " TR69report:" + mgdReport + " and batchtime=" + currentBatchTime);
        final DeltaStagingState stagingState = (DeltaStagingState)(state.exists() ? state.get() : new DeltaStagingState(currentBatchTime));
        if (currentBatchTime.greater(stagingState.batchTime)) {
            DeltaStagingState.LOG.debug("new batch time here...");
            stagingState.batchTime = currentBatchTime;
            final MgdDevTransposeColumnState lastReport = stagingState.mgdDevTransposeColumnStates.get(stagingState.mgdDevTransposeColumnStates.size() - 1);
            stagingState.mgdDevTransposeColumnStates.clear();
            stagingState.mgdDevTransposeColumnStates.add(lastReport);
        }
        if (mgdReport.isPresent()) {
            stagingState.mgdDevTransposeColumnStates.add((MgdDevTransposeColumnState)mgdReport.get());
        }
        stagingState.mgdDevTransposeColumnStates.sort(MgdDevTransposeColumnState.COMPARE_BY_COLLECTION_TIME);
        return stagingState;
    }
    
    public static List<MgdDevReport> getDelta(final DeltaStagingState state, final WifiSparkConfig config) {
        final List<MgdDevReport> deltaComputedMgdDevReports = new ArrayList<MgdDevReport>();
        final List<MgdDevReport> receivedReports = getMgdDevReports(state, config);
        for (int i = 1; i < receivedReports.size(); ++i) {
            receivedReports.get(i).computeDelta(receivedReports.get(i - 1));
            deltaComputedMgdDevReports.add(receivedReports.get(i));
        }
        return deltaComputedMgdDevReports;
    }
    
    @Override
    public String toString() {
        return "DeltaStagingState [mgdDevReports=" + this.mgdDevTransposeColumnStates + ", batchTime=" + this.batchTime + "]";
    }
    
    public static List<MgdDevReport> getMgdDevReports(final DeltaStagingState state, final WifiSparkConfig config) {
        final List<MgdDevReport> completeReports = new ArrayList<MgdDevReport>();
        final List<MgdDevTransposeColumnState> receivedReports = state.mgdDevTransposeColumnStates;
        DeltaStagingState.LOG.debug("in state there were received " + receivedReports.size() + " elements");
        for (final MgdDevTransposeColumnState tr69Report : receivedReports) {
            final MgdDevReport report = (MgdDevReport)MgdDevReport.create(tr69Report, config).get();
            completeReports.add(report);
        }
        return completeReports;
    }
    
    public Time getBatchTime() {
        return this.batchTime;
    }
    
    public List<MgdDevTransposeColumnState> getMgdDevTransposeColumnStates() {
        return this.mgdDevTransposeColumnStates;
    }
    
    static {
        DeltaStagingState.LOG = Logger.getLogger(DeltaStagingState.class);
    }
}
