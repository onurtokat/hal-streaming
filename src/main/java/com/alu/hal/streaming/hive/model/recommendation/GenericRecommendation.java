// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.hive.model.recommendation;

import scala.Tuple2;
import java.util.List;
import org.apache.spark.api.java.Optional;
import org.apache.log4j.Logger;

public class GenericRecommendation extends Recommendation
{
    private static final long serialVersionUID = 1L;
    private static Logger LOG;
    private final Optional<RecommendationType> recommType;
    private RecommendationData recommData;
    
    public GenericRecommendation(final Optional<RecommendationType> recommType) {
        this.recommType = recommType;
    }
    
    public Optional<GenericRecommendation> update(final List<RecommInputBean> recommInputBeanList, final Optional<GenericRecommendation> recommOptional) {
        final GenericRecommendation recommendation = (GenericRecommendation)recommOptional.or((Object)new GenericRecommendation(this.recommType));
        GenericRecommendation.LOG.debug("update type=" + ((RecommendationType)this.recommType.get()).getName() + " recommendation=" + recommendation.toString());
        recommendation.initialize();
        if (recommInputBeanList.isEmpty()) {
            if (recommendation.isTimedOut()) {
                return (Optional<GenericRecommendation>)Optional.absent();
            }
            if (recommendation.getLastUpdateTs() < this.getCurrentTimeInSeconds() - ((RecommendationType)this.recommType.get()).getTimeout()) {
                recommendation.setState(RecommendationState.NO_DATA);
                final long time = this.getCurrentTimeInSeconds();
                recommendation.setEndDate(time);
                recommendation.setLastUpdateTs(time);
                recommendation.resetCounters();
            }
        }
        else {
            if (recommendation.getState() == RecommendationState.NEW && !recommInputBeanList.get(0).isPositive()) {
                return (Optional<GenericRecommendation>)Optional.absent();
            }
            recommendation.updateReports(recommInputBeanList);
            recommendation.updateData(recommInputBeanList);
        }
        GenericRecommendation.LOG.debug("update return type=" + ((RecommendationType)this.recommType.get()).getName() + " recommendation=" + recommendation.toString());
        return (Optional<GenericRecommendation>)Optional.of((Object)recommendation);
    }
    
    private void updateReports(final List<RecommInputBean> recommInputBeans) {
        this.setLastUpdateTs(this.getCurrentTimeInSeconds());
        recommInputBeans.stream().forEach(recommendationInput -> this.updateStateAndCounts(recommendationInput.getLatestTs(), recommendationInput.getPositiveThreshold(), recommendationInput.getNegativeThreshold(), recommendationInput.isPositive()));
    }
    
    private void updateData(final List<RecommInputBean> recommInputBeans) {
        final RecommInputBean recommInputBean = recommInputBeans.get(recommInputBeans.size() - 1);
        this.setRecommData(recommInputBean);
    }
    
    public static Boolean isToExport(final Tuple2<String, GenericRecommendation> recommendationStateTuple) {
        return recommendationStateTuple._2().isToExport();
    }
    
    public static Boolean isSendNotification(final Tuple2<String, GenericRecommendation> recommendationStateTuple) {
        return recommendationStateTuple._2().isSendNotification();
    }
    
    public RecommendationData getRecommData() {
        return this.recommData;
    }
    
    public void setRecommData(final RecommInputBean recommInput) {
        this.recommData = new RecommendationData(recommInput);
    }
    
    @Override
    public String toString() {
        return super.toString() + " GenericRecommendation [recommType=" + this.recommType + ", recommData=" + this.recommData + "]";
    }
    
    static {
        GenericRecommendation.LOG = Logger.getLogger(GenericRecommendation.class);
    }
}
