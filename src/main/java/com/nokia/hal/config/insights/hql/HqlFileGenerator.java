// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.config.insights.hql;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import org.apache.hadoop.fs.Path;
import java.util.AbstractMap;
import org.apache.hadoop.fs.FileSystem;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import com.nokia.hal.nbi.connector.model.SubmittedFormula;
import java.util.List;

public class HqlFileGenerator
{
    public static final String WIFI_KPI_PREFIX = "WiFi_KPI_";
    public static final String WIFI_KQI_PREFIX = "WiFi_KQI_";
    public static final String WIFI_OBSERVATION_ = "WiFi_Observation_";
    private Integer nbrKPISlots;
    private Integer nbrKQISlots;
    private Integer nbrObservationSlots;
    private List<SubmittedFormula> formulas;
    private static Logger LOG;
    
    public Integer getNbrKPISlots() {
        return this.nbrKPISlots;
    }
    
    public void setNbrKPISlots(final Integer nbrKPISlots) {
        this.nbrKPISlots = nbrKPISlots;
    }
    
    public Integer getNbrKQISlots() {
        return this.nbrKQISlots;
    }
    
    public void setNbrKQISlots(final Integer nbrKQISlots) {
        this.nbrKQISlots = nbrKQISlots;
    }
    
    public Integer getNbrObservationSlots() {
        return this.nbrObservationSlots;
    }
    
    public void setNbrObservationSlots(final Integer nbrObservationSlots) {
        this.nbrObservationSlots = nbrObservationSlots;
    }
    
    public List<SubmittedFormula> getFormulas() {
        return this.formulas;
    }
    
    public void setFormulas(final List<SubmittedFormula> formulas) {
        this.formulas = formulas;
    }
    
    public HqlFileGenerator(final List<SubmittedFormula> formulas, final Integer nbrKPISlots, final Integer nbrKQISlots, final Integer nbrObservationSlots) {
        this.formulas = formulas;
        this.nbrKPISlots = nbrKPISlots;
        this.nbrKQISlots = nbrKQISlots;
        this.nbrObservationSlots = nbrObservationSlots;
    }
    
    public HqlFileGenerator(final List<SubmittedFormula> formulas) {
        this.formulas = formulas;
        this.nbrKPISlots = 0;
        this.nbrKQISlots = 0;
        this.nbrObservationSlots = 0;
    }
    
    public String generateFile(final String template) throws IllegalArgumentException {
        final StringBuilder finalFile = new StringBuilder();
        final String regex = "^[^-]*--\\s*\\[([^]]*)]";
        final Pattern pattern = Pattern.compile(regex, 8);
        final List<PlaceholderProperties> currentPlaceHolderGroupTopLevel = new ArrayList<PlaceholderProperties>();
        final List<PlaceholderProperties> currentPlaceHolderGroupUncompress = new ArrayList<PlaceholderProperties>();
        final List<PlaceholderProperties> currentPlaceHolderGroupOther = new ArrayList<PlaceholderProperties>();
        for (final String line : template.split("\n")) {
            final Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                final PlaceholderProperties placeholder = new PlaceholderProperties(matcher.group(1));
                if (placeholder.getTopLevel()) {
                    currentPlaceHolderGroupTopLevel.add(placeholder);
                }
                else if (placeholder.getUncompress()) {
                    currentPlaceHolderGroupUncompress.add(placeholder);
                }
                else {
                    currentPlaceHolderGroupOther.add(placeholder);
                }
            }
            else {
                finalFile.append(this.getHqlExpressionFromAllPlaceholderGroups(currentPlaceHolderGroupTopLevel, currentPlaceHolderGroupUncompress, currentPlaceHolderGroupOther, true));
                finalFile.append(line + "\n");
            }
        }
        finalFile.append(this.getHqlExpressionFromAllPlaceholderGroups(currentPlaceHolderGroupTopLevel, currentPlaceHolderGroupUncompress, currentPlaceHolderGroupOther, true));
        return finalFile.toString();
    }
    
    protected String getHqlExpressionFromAllPlaceholderGroups(final List<PlaceholderProperties> placeHolderGroupTopLevel, final List<PlaceholderProperties> placeHolderGroupUncompress, final List<PlaceholderProperties> placeHolderGroupOther, final boolean clearPlaceHolderGroups) {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.getHqlExpressionFromPlaceholderGroup(placeHolderGroupOther, false, false));
        sb.append(this.getHqlExpressionFromPlaceholderGroup(placeHolderGroupTopLevel, true, false));
        sb.append(this.getHqlExpressionFromPlaceholderGroup(placeHolderGroupUncompress, false, true));
        if (clearPlaceHolderGroups) {
            placeHolderGroupOther.clear();
            placeHolderGroupTopLevel.clear();
            placeHolderGroupUncompress.clear();
        }
        return sb.toString();
    }
    
    protected String getHqlExpressionFromPlaceholderGroup(final List<PlaceholderProperties> placeHolderGroup, final boolean isTopLevel, final boolean isUncompress) {
        final StringBuilder sb = new StringBuilder();
        final PlaceholderProperties dumpAllColumnsOrderedPlaceholder = this.getDumpAllColumnsOrderedPlaceholder(placeHolderGroup);
        Map<String, ExpressionOrderPair> allExpressions;
        List<String> orderedExpressionKeys;
        if ((isTopLevel || isUncompress) && dumpAllColumnsOrderedPlaceholder != null) {
            allExpressions = new HashMap<String, ExpressionOrderPair>();
            this.addEmptySlots(allExpressions, "WiFi_KPI_", this.nbrKPISlots);
            this.addEmptySlots(allExpressions, "WiFi_KQI_", this.nbrKQISlots);
            this.addEmptySlots(allExpressions, "WiFi_Observation_", this.nbrObservationSlots);
            allExpressions.putAll(this.getAllExpressions(placeHolderGroup));
            this.populateColOrderForCustomColSlots(allExpressions, "WiFi_KPI_", this.nbrKPISlots, 1000);
            this.populateColOrderForCustomColSlots(allExpressions, "WiFi_KQI_", this.nbrKQISlots, 2000);
            this.populateColOrderForCustomColSlots(allExpressions, "WiFi_Observation_", this.nbrObservationSlots, 3000);
            orderedExpressionKeys = allExpressions.values().stream().sorted(Comparator.comparingInt(el -> (el.getColOrder() == null) ? 0 : el.getColOrder())).map(el -> el.getPhysicalTarget()).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
        }
        else {
            allExpressions = this.getAllExpressions(placeHolderGroup);
            orderedExpressionKeys = allExpressions.keySet().stream().sorted().collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
        }
        for (final String expKey : orderedExpressionKeys) {
            sb.append(", " + allExpressions.get(expKey).getExpression() + "\n");
        }
        return sb.toString();
    }
    
    private void populateColOrderForCustomColSlots(final Map<String, ExpressionOrderPair> expressionList, final String prefix, final Integer nbrSlots, final Integer firstColOrder) {
        for (Integer i = 1; i < nbrSlots + 1; ++i) {
            expressionList.get(String.format(prefix + "%02d", i)).setColOrder(firstColOrder + i);
        }
    }
    
    private void addEmptySlots(final Map<String, ExpressionOrderPair> expressionList, final String prefix, final Integer nbrSlots) {
        for (Integer i = 1; i < nbrSlots + 1; ++i) {
            expressionList.put(String.format(prefix + "%02d", i), new ExpressionOrderPair(String.format(prefix + "%02d", i), null, String.format("NULL as " + prefix + "%02d", i)));
        }
    }
    
    private Map<String, ExpressionOrderPair> getAllExpressions(final List<PlaceholderProperties> placeHolderGroup) {
        final Map<String, ExpressionOrderPair> result = new HashMap<String, ExpressionOrderPair>();
        for (final PlaceholderProperties ph : placeHolderGroup) {
            if (!ph.isDumpAllColumnsOrdered()) {
                result.putAll(this.getHqlExpression(ph));
            }
        }
        return result;
    }
    
    private PlaceholderProperties getDumpAllColumnsOrderedPlaceholder(final List<PlaceholderProperties> placeHolderGroup) {
        for (final PlaceholderProperties ph : placeHolderGroup) {
            if (ph.isDumpAllColumnsOrdered()) {
                return ph;
            }
        }
        return null;
    }
    
    private Map<String, ExpressionOrderPair> getHqlExpression(final PlaceholderProperties placeholder) {
        final Map<String, ExpressionOrderPair> result = new HashMap<String, ExpressionOrderPair>();
        final List<SubmittedFormula> filteredFormulas = this.getFilteredFormulas(placeholder);
        for (final SubmittedFormula formula : filteredFormulas) {
            if (this.checkSubmittedFormulaFields(placeholder, formula)) {
                final StringBuilder sb = new StringBuilder();
                if (placeholder.getUncompress()) {
                    sb.append("CASE WHEN " + formula.getPhysicalTarget() + " IN ('Infinity','NaN') THEN NULL ELSE " + formula.getPhysicalTarget() + " END");
                }
                else if (placeholder.getAlias() != null && !placeholder.getAlias().isEmpty()) {
                    sb.append(placeholder.getAlias()).append(".");
                    sb.append(formula.getPhysicalTarget());
                }
                else {
                    sb.append(formula.getTranslatedExpression());
                }
                sb.append(" as ");
                sb.append(formula.getPhysicalTarget());
                result.put(formula.getPhysicalTarget(), new ExpressionOrderPair(formula.getPhysicalTarget(), formula.getColOrder(), sb.toString()));
            }
        }
        return result;
    }
    
    private boolean checkSubmittedFormulaFields(final PlaceholderProperties placeholder, final SubmittedFormula formula) {
        boolean generateFormula = true;
        if (((!placeholder.getTopLevel() && !placeholder.getUncompress()) || (placeholder.getTopLevel() && placeholder.getAlias() != null && !placeholder.getAlias().isEmpty())) && (formula.getPhysicalTarget() == null || formula.getPhysicalTarget().isEmpty())) {
            HqlFileGenerator.LOG.warn("PhysicalTarget missing for insight: " + formula.getInsightUID() + ". Formula won't be inserted into the query.");
            generateFormula = false;
        }
        if ((placeholder.getAlias() == null || placeholder.getAlias().isEmpty()) && !placeholder.getUncompress() && (formula.getTranslatedExpression() == null || formula.getTranslatedExpression().isEmpty())) {
            HqlFileGenerator.LOG.warn("Expression missing for insight: " + formula.getInsightUID() + ", flow: " + formula.getFlow() + ", monitored point type: " + formula.getMonitoredPointType() + ". Formula won't be inserted into the query.");
            generateFormula = false;
        }
        if ((placeholder.getTopLevel() || placeholder.getUncompress()) && formula.getToBeExported() && (formula.getPhysicalTarget() == null || formula.getPhysicalTarget().isEmpty())) {
            HqlFileGenerator.LOG.warn("PhysicalTarget missing for insight: " + formula.getInsightUID() + ", flow: " + formula.getFlow() + ", monitored point type: " + formula.getMonitoredPointType() + ". Formula won't be inserted into the query.");
            generateFormula = false;
        }
        return generateFormula;
    }
    
    protected List<PlaceholderProperties> getTemplatePlaceholders(final String templateText) throws IllegalArgumentException {
        final List<PlaceholderProperties> result = new ArrayList<PlaceholderProperties>();
        final String regex = "^[^-]*--\\s*\\[([^]]*)]";
        final Pattern pattern = Pattern.compile(regex, 8);
        final Matcher matcher = pattern.matcher(templateText);
        while (matcher.find()) {
            final PlaceholderProperties prop = new PlaceholderProperties(matcher.group(1));
            result.add(prop);
        }
        return result;
    }
    
    protected List<SubmittedFormula> getFilteredFormulas(final PlaceholderProperties placeholder) {
        final List<SubmittedFormula> result = new ArrayList<SubmittedFormula>();
        List<String> expressionPatterns;
        if (placeholder.getExpression() != null && !placeholder.getExpression().isEmpty()) {
            expressionPatterns = Arrays.asList(placeholder.getExpression().split("&"));
        }
        else {
            expressionPatterns = new ArrayList<String>();
        }
        if (HqlFileGenerator.LOG.isDebugEnabled()) {
            this.logFilterAppliedToFormulas(placeholder, expressionPatterns);
        }
        return this.formulas.stream().filter(formula -> this.shouldFormulaBeIncluded(placeholder, expressionPatterns, formula)).collect((Collector<? super Object, ?, List<SubmittedFormula>>)Collectors.toList());
    }
    
    private void logFilterAppliedToFormulas(final PlaceholderProperties placeholder, final List<String> expressionPatterns) {
        final StringBuilder sb = new StringBuilder("Placeholder [" + placeholder.getText() + "]. Following filter will be applied: ");
        if (placeholder.getDomain() != null && !placeholder.getDomain().isEmpty()) {
            sb.append("domain = '").append(placeholder.getDomain()).append("' AND ");
        }
        if (placeholder.getCategory() != null && !placeholder.getCategory().isEmpty()) {
            sb.append("category = '").append(placeholder.getCategory()).append("' AND ");
        }
        if (placeholder.getFlow() != null && !placeholder.getFlow().isEmpty()) {
            sb.append("flow = '").append(placeholder.getFlow()).append("' AND ");
        }
        if (placeholder.getMonitoredPointType() != null && !placeholder.getMonitoredPointType().isEmpty()) {
            sb.append("monitoredPointType = '").append(placeholder.getMonitoredPointType()).append("' AND ");
        }
        if (placeholder.getExpression() != null && !placeholder.getExpression().isEmpty()) {
            for (String expPattern : expressionPatterns) {
                boolean notExpression;
                if (expPattern.startsWith("!")) {
                    notExpression = true;
                    expPattern = expPattern.substring(1);
                }
                else {
                    notExpression = false;
                }
                sb.append("expression " + (notExpression ? "NOT " : "") + "LIKE '%").append(expPattern).append("%' AND ");
            }
        }
        if (placeholder.getTopLevel() || placeholder.getUncompress()) {
            sb.append("toBeExported = true");
        }
        if (sb.lastIndexOf("AND") == sb.length() - 4) {
            sb.delete(sb.length() - 5, sb.length());
        }
        HqlFileGenerator.LOG.debug(sb.toString());
    }
    
    private boolean shouldFormulaBeIncluded(final PlaceholderProperties placeholder, final List<String> expressionPatterns, final SubmittedFormula formula) {
        boolean addFormula = false;
        if ((placeholder.getDomain() == null || placeholder.getDomain().isEmpty() || (formula.getDomain() != null && formula.getDomain().equals(placeholder.getDomain()))) && (placeholder.getCategory() == null || placeholder.getCategory().isEmpty() || (formula.getCategory() != null && formula.getCategory().equals(placeholder.getCategory()))) && (placeholder.getFlow() == null || placeholder.getFlow().isEmpty() || (formula.getFlow() != null && formula.getFlow().equals(placeholder.getFlow()))) && (placeholder.getMonitoredPointType() == null || placeholder.getMonitoredPointType().isEmpty() || (formula.getMonitoredPointType() != null && formula.getMonitoredPointType().equals(placeholder.getMonitoredPointType()))) && ((!placeholder.getTopLevel() && !placeholder.getUncompress()) || (formula.getToBeExported() != null && formula.getToBeExported()))) {
            addFormula = true;
        }
        for (String expPattern : expressionPatterns) {
            boolean notExpression;
            if (expPattern.startsWith("!")) {
                notExpression = true;
                expPattern = expPattern.substring(1);
            }
            else {
                notExpression = false;
            }
            if ((notExpression && !expPattern.isEmpty() && (formula.getTranslatedExpression() == null || formula.getTranslatedExpression().contains(expPattern))) || (!notExpression && !expPattern.isEmpty() && (formula.getTranslatedExpression() == null || !formula.getTranslatedExpression().contains(expPattern)))) {
                addFormula = false;
            }
        }
        return addFormula;
    }
    
    public void process(final String hdfsURL, final String hqlTemplateFolder, final String hqlFileFolder, final List<String> hqlTemplateFileNames) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     0: getstat\u0131c       com/nokia/hal/config/insights/hql/HqlFileGenerator.LOG:Lorg/apache/log4j/Logger;
        //     3: ldc             "Start process of HQL generation"
        //     5: \u0131nvokev\u0131rtual   org/apache/log4j/Logger.info:(Ljava/lang/Object;)V
        //     8: new             Lorg/apache/hadoop/conf/Configuration;
        //    11: dup            
        //    12: \u0131nvokespec\u0131al   org/apache/hadoop/conf/Configuration.<init>:()V
        //    15: astore          conf
        //    17: aload           conf
        //    19: \u0131nvokestat\u0131c    org/apache/hadoop/fs/FileSystem.get:(Lorg/apache/hadoop/conf/Configuration;)Lorg/apache/hadoop/fs/FileSystem;
        //    22: astore          fs
        //    24: getstat\u0131c       com/nokia/hal/config/insights/hql/HqlFileGenerator.LOG:Lorg/apache/log4j/Logger;
        //    27: new             Ljava/lang/StringBuilder;
        //    30: dup            
        //    31: \u0131nvokespec\u0131al   java/lang/StringBuilder.<init>:()V
        //    34: ldc             "filesystem: "
        //    36: \u0131nvokev\u0131rtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    39: aload           fs
        //    41: \u0131nvokev\u0131rtual   java/lang/Object.toString:()Ljava/lang/String;
        //    44: \u0131nvokev\u0131rtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    47: \u0131nvokev\u0131rtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    50: \u0131nvokev\u0131rtual   org/apache/log4j/Logger.info:(Ljava/lang/Object;)V
        //    53: aload           hqlTemplateFileNames
        //    55: \u0131nvoke\u0131nterface java/util/List.stream:()Ljava/util/stream/Stream;
        //    60: aload_0         /* this */
        //    61: aload_1         /* hdfsURL */
        //    62: aload_2         /* hqlTemplateFolder */
        //    63: aload           fs
        //    65: \u0131nvokedynam\u0131c   apply:(Lcom/nokia/hal/config/insights/hql/HqlFileGenerator;Ljava/lang/String;Ljava/lang/String;Lorg/apache/hadoop/fs/FileSystem;)Ljava/util/function/Function;
        //    70: \u0131nvoke\u0131nterface java/util/stream/Stream.map:(Ljava/util/function/Function;)Ljava/util/stream/Stream;
        //    75: aload_3         /* hqlFileFolder */
        //    76: aload           fs
        //    78: \u0131nvokedynam\u0131c   accept:(Ljava/lang/String;Lorg/apache/hadoop/fs/FileSystem;)Ljava/util/function/Consumer;
        //    83: \u0131nvoke\u0131nterface java/util/stream/Stream.forEach:(Ljava/util/function/Consumer;)V
        //    88: goto            98
        //    91: astore          e
        //    93: aload           e
        //    95: \u0131nvokev\u0131rtual   java/io/IOException.printStackTrace:()V
        //    98: getstat\u0131c       com/nokia/hal/config/insights/hql/HqlFileGenerator.LOG:Lorg/apache/log4j/Logger;
        //   101: ldc             "Finish process of HQL generation"
        //   103: \u0131nvokev\u0131rtual   org/apache/log4j/Logger.info:(Ljava/lang/Object;)V
        //   106: return         
        //    Signature:
        //  (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List<Ljava/lang/String;>;)V
        //    LocalVariableTable:
        //  Start  Length  Slot  Name                  Signature
        //  -----  ------  ----  --------------------  ----------------------------------------------------
        //  17     71      5     conf                  Lorg/apache/hadoop/conf/Configuration;
        //  24     64      6     fs                    Lorg/apache/hadoop/fs/FileSystem;
        //  93     5       5     e                     Ljava/io/IOException;
        //  0      107     0     this                  Lcom/nokia/hal/config/insights/hql/HqlFileGenerator;
        //  0      107     1     hdfsURL               Ljava/lang/String;
        //  0      107     2     hqlTemplateFolder     Ljava/lang/String;
        //  0      107     3     hqlFileFolder         Ljava/lang/String;
        //  0      107     4     hqlTemplateFileNames  Ljava/util/List;
        //    LocalVariableTypeTable:
        //  Start  Length  Slot  Name                  Signature
        //  -----  ------  ----  --------------------  ------------------------------------
        //  0      107     4     hqlTemplateFileNames  Ljava/util/List<Ljava/lang/String;>;
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  0      88     91     98     Ljava/io/IOException;
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Could not infer any expression.
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:374)
        //     at com.strobel.decompiler.ast.TypeAnalysis.run(TypeAnalysis.java:96)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:344)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:317)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:238)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:123)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    static {
        HqlFileGenerator.LOG = Logger.getLogger(HqlFileGenerator.class);
    }
}
