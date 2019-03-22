// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.nio.file.Path;
import java.net.URISyntaxException;
import java.io.IOException;
import com.alu.hal.streaming.exception.HalWifiException;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.net.URL;
import com.alu.hal.streaming.hive.model.recommendation.RecommendationType;
import java.util.List;
import org.apache.log4j.Logger;
import java.io.Serializable;

public class WifiRecommendations implements Serializable
{
    private static final long serialVersionUID = 1L;
    private static final String PLACEHOLDER_PATTERN = "\\$\\{(.*?)\\}";
    private static Logger LOG;
    private static final RecommendationsConfig CONFIG;
    private static final UdfConfig UDF_CONFIG;
    private final List<String> recommendations;
    private final List<RecommendationType> recommendationsTypes;
    
    public WifiRecommendations(final URL queryDirResource) {
        this.recommendationsTypes = new ArrayList<RecommendationType>();
        this.recommendations = WifiRecommendations.CONFIG.getListOfRecommendations();
        this.setRecommendations(queryDirResource);
    }
    
    private void setRecommendations(final URL queryDirResource) {
        final String positiveQuery;
        final String negativeQuery;
        final int noPositiveObsBeforeActivation;
        final int noNegativeeObsBeforeDeActivation;
        final long timeout;
        final RecommendationType recType;
        this.recommendations.forEach(recommendation -> {
            positiveQuery = this.loadQuery(recommendation, WifiRecommendations.CONFIG.getProperty(recommendation + ".positive.query.file.name"), queryDirResource);
            negativeQuery = this.loadQuery(recommendation, WifiRecommendations.CONFIG.getProperty(recommendation + ".negative.query.file.name"), queryDirResource);
            noPositiveObsBeforeActivation = Integer.valueOf(WifiRecommendations.CONFIG.getProperty(recommendation + ".no.positive.obs", WifiRecommendations.CONFIG.getProperty("default.no.positive.obs")));
            noNegativeeObsBeforeDeActivation = Integer.valueOf(WifiRecommendations.CONFIG.getProperty(recommendation + ".no.negative.obs", WifiRecommendations.CONFIG.getProperty("default.no.negative.obs")));
            timeout = Long.valueOf(WifiRecommendations.CONFIG.getProperty(recommendation + ".timeout", WifiRecommendations.CONFIG.getProperty("default.timeout")));
            recType = new RecommendationType(recommendation, positiveQuery, negativeQuery, noPositiveObsBeforeActivation, noNegativeeObsBeforeDeActivation, timeout);
            this.recommendationsTypes.add(recType);
        });
    }
    
    private String loadQuery(final String recType, final String queryFileName, final URL queryDir) {
        try {
            final Path dir = Paths.get(queryDir.toURI());
            final Path fullPath = Paths.get(dir.toString(), queryFileName);
            try (final Stream<String> linesStream = Files.lines(fullPath, StandardCharsets.UTF_8)) {
                final String query = linesStream.map((Function<? super String, ?>)String::trim).map(line -> line.concat(" ")).collect((Collector<? super Object, ?, String>)Collectors.joining());
                return this.replaceQueryVars(query, recType);
            }
            catch (IOException e) {
                e.printStackTrace();
                throw new HalWifiException("Unable to read configuration file " + fullPath.toAbsolutePath().toString());
            }
            catch (IllegalArgumentException e2) {
                e2.printStackTrace();
                throw new HalWifiException("Query configuration exception " + fullPath.toAbsolutePath().toString());
            }
        }
        catch (URISyntaxException e3) {
            throw new HalWifiException("Unable to read queries from " + queryDir.getFile(), e3);
        }
    }
    
    private String replaceQueryVars(String query, final String recType) throws IOException {
        final Pattern placeHolderPattern = Pattern.compile("\\$\\{(.*?)\\}");
        final Matcher placeHolderMatcher = placeHolderPattern.matcher(query);
        while (placeHolderMatcher.find()) {
            final String placeHolder = placeHolderMatcher.group();
            final String placeHolderProp = placeHolderMatcher.group(1);
            String placeHolderValue = WifiRecommendations.CONFIG.getProperty(recType + "." + placeHolderProp);
            if (placeHolderValue == null) {
                placeHolderValue = WifiRecommendations.UDF_CONFIG.getProperty(placeHolderProp);
            }
            WifiRecommendations.LOG.info("PlaceHolder=" + placeHolder + " placeHolderProp=" + placeHolderProp + " placeHolderValue=" + placeHolderValue);
            query = query.replace(placeHolder, placeHolderValue);
            WifiRecommendations.LOG.info("query=" + query);
            placeHolderMatcher.reset(query);
        }
        return query;
    }
    
    public List<RecommendationType> getRecommendationsTypes() {
        return this.recommendationsTypes;
    }
    
    static {
        WifiRecommendations.LOG = Logger.getLogger(WifiRecommendations.class);
        CONFIG = RecommendationsConfig.getInstance();
        UDF_CONFIG = UdfConfig.getInstance();
    }
}
