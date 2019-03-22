// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.config.insights.hql;

import org.slf4j.LoggerFactory;
import java.util.regex.Matcher;
import com.nokia.hal.config.insights.InsightMonitoredPointType;
import com.nokia.hal.config.insights.InsightFlow;
import com.nokia.hal.config.insights.InsightCategory;
import com.nokia.hal.config.insights.InsightDomain;
import java.util.regex.Pattern;
import org.slf4j.Logger;

public class PlaceholderForViews
{
    private static final Logger LOG;
    private static String regex;
    private static Pattern pattern;
    private static final String VIEW_LEVEL = "VIEW_LEVEL";
    private static final Integer NUMBER_OF_FIELDS;
    private static final String SEPARATOR = ",";
    private static final Integer DOMAIN_INDEX;
    private static final Integer INSIGHT_CATEGORY_INDEX;
    private static final Integer FLOW_INDEX;
    private static final Integer MONITORED_POINT_TYPE_INDEX;
    private static final Integer VIEW_LEVEL_INDEX;
    private String originalLine;
    private InsightDomain domain;
    private InsightCategory insightsCategory;
    private InsightFlow flow;
    private InsightMonitoredPointType monitoredPointType;
    
    public static Boolean isPlaceholder(final String line) {
        return PlaceholderForViews.pattern.matcher(line).find();
    }
    
    public PlaceholderForViews(final String line) {
        this.originalLine = line;
        final Matcher matcher = PlaceholderForViews.pattern.matcher(line);
        if (!matcher.find()) {
            PlaceholderForViews.LOG.error("Invalid placeholder for views: " + this.originalLine);
            throw new HalInvalidPlaceholderForViewsException("Invalid placeholder for views: " + this.originalLine);
        }
        final String[] fields = matcher.group(1).split(",");
        if (fields.length != PlaceholderForViews.NUMBER_OF_FIELDS) {
            throw new HalInvalidPlaceholderForViewsException("Invalid placeholder for views: " + this.originalLine);
        }
        if (fields[PlaceholderForViews.DOMAIN_INDEX].isEmpty() || fields[PlaceholderForViews.INSIGHT_CATEGORY_INDEX].isEmpty() || fields[PlaceholderForViews.FLOW_INDEX].isEmpty() || fields[PlaceholderForViews.VIEW_LEVEL_INDEX].isEmpty() || !fields[PlaceholderForViews.VIEW_LEVEL_INDEX].equals("VIEW_LEVEL")) {
            PlaceholderForViews.LOG.error("Invalid placeholder for views: " + this.originalLine);
            throw new HalInvalidPlaceholderForViewsException("Invalid placeholder for views: " + this.originalLine);
        }
        this.domain = InsightDomain.fromString(fields[PlaceholderForViews.DOMAIN_INDEX]);
        this.insightsCategory = InsightCategory.fromString(fields[PlaceholderForViews.INSIGHT_CATEGORY_INDEX]);
        this.flow = InsightFlow.fromString(fields[PlaceholderForViews.FLOW_INDEX]);
        this.monitoredPointType = InsightMonitoredPointType.fromString(fields[PlaceholderForViews.MONITORED_POINT_TYPE_INDEX]);
    }
    
    public InsightDomain getDomain() {
        return this.domain;
    }
    
    public InsightCategory getInsightsCategory() {
        return this.insightsCategory;
    }
    
    public InsightFlow getFlow() {
        return this.flow;
    }
    
    public InsightMonitoredPointType getMonitoredPointType() {
        return this.monitoredPointType;
    }
    
    static {
        LOG = LoggerFactory.getLogger(PlaceholderForViews.class);
        PlaceholderForViews.regex = "^\\s*--\\s*\\[([^]]*)]";
        PlaceholderForViews.pattern = Pattern.compile(PlaceholderForViews.regex, 8);
        NUMBER_OF_FIELDS = 7;
        DOMAIN_INDEX = 0;
        INSIGHT_CATEGORY_INDEX = 1;
        FLOW_INDEX = 2;
        MONITORED_POINT_TYPE_INDEX = 3;
        VIEW_LEVEL_INDEX = 5;
    }
}
