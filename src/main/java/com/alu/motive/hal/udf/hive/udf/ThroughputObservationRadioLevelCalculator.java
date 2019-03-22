// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.udf.hive.udf;

import java.util.HashMap;
import org.apache.commons.lang3.tuple.Pair;
import java.util.Map;

public class ThroughputObservationRadioLevelCalculator
{
    public static final String DEGRADED_THROUGHPUT_OBSERVATION = "Degraded";
    public static final String HIGH_THROUGHPUT_OBSERVATION = "High";
    public static final String LOW_THROUGHPUT_OBSERVATION = "Low";
    public static final String UNKNOWN_THROUGHPUT_OBSERVATION = "Unknown";
    public static final String DEGRADED_CONTENTION_OBSERVATION = "degraded";
    public static final String LOW_CONTENTION_OBSERVATION = "low";
    public static final String HIGH_CONTENTION_OBSERVATION = "high";
    public static final String LOW_INTERFERENCE_OBSERVATION = "low";
    public static final String HIGH_INTERFERENCE_OBSERVATION = "high";
    public static final String UNKNOWN_INTERFERENCE_OBSERVATION = "unknown";
    public static final String NULL;
    private Map<Pair<String, String>, String> observation;
    
    public ThroughputObservationRadioLevelCalculator() {
        (this.observation = new HashMap<Pair<String, String>, String>()).put(Pair.of("low", "low"), "Low");
        this.observation.put(Pair.of("low", "degraded"), "Low");
        this.observation.put(Pair.of("low", "high"), "Low");
        this.observation.put(Pair.of("high", "low"), "Low");
        this.observation.put(Pair.of("high", "degraded"), "Degraded");
        this.observation.put(Pair.of("high", "high"), "High");
        this.addMappingForMissingObservations();
        this.addMappingsWhenOnlyInterferenceObservationIsAvailable();
        this.addMappingsWhenOnlyContentionObservationIsAvailable();
    }
    
    private void addMappingsWhenOnlyContentionObservationIsAvailable() {
        this.observation.put(Pair.of((String)null, "degraded"), "Degraded");
        this.observation.put(Pair.of((String)null, "low"), "Low");
        this.observation.put(Pair.of((String)null, "high"), "High");
        this.observation.put(Pair.of("unknown", "degraded"), "Degraded");
        this.observation.put(Pair.of("unknown", "low"), "Low");
        this.observation.put(Pair.of("unknown", "high"), "High");
    }
    
    private void addMappingsWhenOnlyInterferenceObservationIsAvailable() {
        this.observation.put(Pair.of("low", (String)null), "Low");
        this.observation.put(Pair.of("high", (String)null), "High");
    }
    
    private void addMappingForMissingObservations() {
        this.observation.put(Pair.of((String)null, (String)null), "Unknown");
        this.observation.put(Pair.of("unknown", (String)null), "Unknown");
    }
    
    public String evaluate(final String interferenceObservation, final String contentionObservation) {
        final String lowerCaseInterference = (null == interferenceObservation) ? null : interferenceObservation.toLowerCase();
        final String lowerCaseContention = (null == contentionObservation) ? null : contentionObservation.toLowerCase();
        return this.observation.get(Pair.of(lowerCaseInterference, lowerCaseContention));
    }
    
    static {
        NULL = null;
    }
}
