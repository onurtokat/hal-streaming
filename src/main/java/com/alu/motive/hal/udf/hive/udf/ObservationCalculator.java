// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.udf.hive.udf;

public class ObservationCalculator
{
    private double coverageScore;
    private double[] thresholds;
    private String[] observationValues;
    
    public ObservationCalculator(final double coverageScore, final double[] thresholds, final String[] observationValues) {
        this.coverageScore = coverageScore;
        this.thresholds = thresholds;
        this.observationValues = observationValues;
        if (thresholds.length + 1 != observationValues.length) {
            throw new IllegalArgumentException("ObservationValues must contain thresholds.length+1 elements");
        }
    }
    
    public String calculateValue() {
        for (int i = 0; i < this.thresholds.length; ++i) {
            if (this.thresholds[i] > this.coverageScore) {
                return this.observationValues[i];
            }
        }
        return this.observationValues[this.observationValues.length - 1];
    }
}
