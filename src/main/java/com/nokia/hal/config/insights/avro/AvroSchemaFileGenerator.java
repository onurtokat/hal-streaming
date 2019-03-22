// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.config.insights.avro;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import org.apache.hadoop.fs.Path;
import java.util.AbstractMap;
import org.apache.hadoop.fs.FileSystem;
import java.util.function.Predicate;
import com.nokia.hal.nbi.connector.model.SubmittedFormulas;
import java.util.ArrayList;
import java.util.Iterator;
import com.nokia.hal.config.insights.InsightFlow;
import com.nokia.hal.config.insights.InsightMonitoredPointType;
import com.nokia.hal.config.insights.InsightDomain;
import java.util.List;
import com.nokia.hal.nbi.connector.model.SubmittedFormula;
import org.apache.log4j.Logger;

public class AvroSchemaFileGenerator
{
    private static Logger LOG;
    
    protected boolean checkSubmittedFormulaFields(final SubmittedFormula formula) {
        boolean generateFormula = true;
        if (formula.getPhysicalTarget() == null || formula.getPhysicalTarget().isEmpty()) {
            AvroSchemaFileGenerator.LOG.warn("PhysicalTarget missing for insight: " + formula.getInsightUID() + ". Alias will not be added to avro file.");
            generateFormula = false;
        }
        if (formula.getRestName() == null || formula.getRestName().isEmpty()) {
            AvroSchemaFileGenerator.LOG.warn("RestName missing for insight: " + formula.getInsightUID() + ". Alias will not be added to avro file.");
            generateFormula = false;
        }
        return generateFormula;
    }
    
    public String generateFile(final List<SubmittedFormula> formulas, final InsightDomain domain, final InsightMonitoredPointType mpType, final InsightFlow flow, final String template) {
        String finalTemplate = template;
        final List<SubmittedFormula> filteredFormulas = this.getFilteredFormulas(formulas, domain, mpType, flow);
        for (final SubmittedFormula formula : filteredFormulas) {
            if (this.checkSubmittedFormulaFields(formula)) {
                if (finalTemplate.indexOf(formula.getPhysicalTarget()) >= 0) {
                    if (!formula.getPhysicalTarget().equals(formula.getRestName())) {
                        finalTemplate = finalTemplate.replaceAll("(\"name\"\\s*:\\s*\"" + formula.getPhysicalTarget() + "\"\\s*,)", "$1 \"aliases\" : [\"" + formula.getRestName() + "\"],");
                    }
                    else {
                        AvroSchemaFileGenerator.LOG.info("Alias '" + formula.getRestName() + "' not added because restName is the same as physicalTarget. domain: " + domain.getMetadataValue() + ", monitoredPointType: " + mpType.getMetadataValue() + ", flow: " + flow.getMetadataValue());
                    }
                }
                else {
                    AvroSchemaFileGenerator.LOG.warn("Field '" + formula.getPhysicalTarget() + "' was not found in the Avro schema for domain: " + domain.getMetadataValue() + ", monitoredPointType: " + mpType.getMetadataValue() + ", flow: " + flow.getMetadataValue());
                }
            }
        }
        return finalTemplate;
    }
    
    protected List<SubmittedFormula> getFilteredFormulas(final List<SubmittedFormula> formulas, final InsightDomain domain, final InsightMonitoredPointType monitoredPointType, final InsightFlow flow) {
        final List<SubmittedFormula> result = new ArrayList<SubmittedFormula>();
        for (final SubmittedFormula formula : formulas) {
            if ((domain == null || formula.getDomain().equals(domain.getMetadataValue())) && (flow == null || formula.getFlow().equals(flow.getMetadataValue())) && (monitoredPointType == null || (formula.getMonitoredPointType().equals(monitoredPointType.getMetadataValue()) && formula.getToBeExported()))) {
                result.add(formula);
            }
        }
        return result;
    }
    
    public void process(final SubmittedFormulas formulas, final String hdfsURL, final String avscFileFolder, final String avscTemplateFolder, final Predicate<WifiAvroSchemas.SchemaNames> filterFunction) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     0: new             Lorg/apache/hadoop/conf/Configuration;
        //     3: dup            
        //     4: \u0131nvokespec\u0131al   org/apache/hadoop/conf/Configuration.<init>:()V
        //     7: \u0131nvokestat\u0131c    org/apache/hadoop/fs/FileSystem.get:(Lorg/apache/hadoop/conf/Configuration;)Lorg/apache/hadoop/fs/FileSystem;
        //    10: astore          fs
        //    12: ldc             Lcom/nokia/hal/config/insights/avro/WifiAvroSchemas$SchemaNames;.class
        //    14: \u0131nvokestat\u0131c    java/util/EnumSet.allOf:(Ljava/lang/Class;)Ljava/util/EnumSet;
        //    17: \u0131nvokev\u0131rtual   java/util/EnumSet.stream:()Ljava/util/stream/Stream;
        //    20: aload           filterFunction
        //    22: \u0131nvoke\u0131nterface java/util/stream/Stream.filter:(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
        //    27: aload_0         /* this */
        //    28: aload_2         /* hdfsURL */
        //    29: aload           avscTemplateFolder
        //    31: aload           fs
        //    33: aload_1         /* formulas */
        //    34: \u0131nvokedynam\u0131c   apply:(Lcom/nokia/hal/config/insights/avro/AvroSchemaFileGenerator;Ljava/lang/String;Ljava/lang/String;Lorg/apache/hadoop/fs/FileSystem;Lcom/nokia/hal/nbi/connector/model/SubmittedFormulas;)Ljava/util/function/Function;
        //    39: \u0131nvoke\u0131nterface java/util/stream/Stream.map:(Ljava/util/function/Function;)Ljava/util/stream/Stream;
        //    44: aload_2         /* hdfsURL */
        //    45: aload_3         /* avscFileFolder */
        //    46: aload           fs
        //    48: \u0131nvokedynam\u0131c   accept:(Ljava/lang/String;Ljava/lang/String;Lorg/apache/hadoop/fs/FileSystem;)Ljava/util/function/Consumer;
        //    53: \u0131nvoke\u0131nterface java/util/stream/Stream.forEach:(Ljava/util/function/Consumer;)V
        //    58: goto            68
        //    61: astore          e
        //    63: aload           e
        //    65: \u0131nvokev\u0131rtual   java/io/IOException.printStackTrace:()V
        //    68: return         
        //    Signature:
        //  (Lcom/nokia/hal/nbi/connector/model/SubmittedFormulas;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/function/Predicate<Lcom/nokia/hal/config/insights/avro/WifiAvroSchemas$SchemaNames;>;)V
        //    LocalVariableTable:
        //  Start  Length  Slot  Name                Signature
        //  -----  ------  ----  ------------------  ------------------------------------------------------------
        //  12     46      6     fs                  Lorg/apache/hadoop/fs/FileSystem;
        //  63     5       6     e                   Ljava/io/IOException;
        //  0      69      0     this                Lcom/nokia/hal/config/insights/avro/AvroSchemaFileGenerator;
        //  0      69      1     formulas            Lcom/nokia/hal/nbi/connector/model/SubmittedFormulas;
        //  0      69      2     hdfsURL             Ljava/lang/String;
        //  0      69      3     avscFileFolder      Ljava/lang/String;
        //  0      69      4     avscTemplateFolder  Ljava/lang/String;
        //  0      69      5     filterFunction      Ljava/util/function/Predicate;
        //    LocalVariableTypeTable:
        //  Start  Length  Slot  Name            Signature
        //  -----  ------  ----  --------------  ------------------------------------------------------------------------------------------------
        //  0      69      5     filterFunction  Ljava/util/function/Predicate<Lcom/nokia/hal/config/insights/avro/WifiAvroSchemas$SchemaNames;>;
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  0      58     61     68     Ljava/io/IOException;
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
        AvroSchemaFileGenerator.LOG = Logger.getLogger(AvroSchemaFileGenerator.class);
    }
}
