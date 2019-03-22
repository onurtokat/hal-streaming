// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.utils;

import java.io.BufferedWriter;
import java.nio.file.OpenOption;
import java.io.BufferedReader;
import java.io.IOException;
import com.alu.hal.streaming.exception.HalWifiException;
import com.alu.hal.streaming.hive.model.ParamType;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.apache.spark.sql.types.StructField;
import java.util.function.Function;
import java.util.stream.Stream;
import org.apache.spark.sql.types.StructType;
import java.util.HashMap;

import com.alu.hal.streaming.config.WifiSparkConfig;
import java.util.Map;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.FileSystems;
import java.io.File;

public class CreateWifiTableScriptGenerator
{
    public static final String TEMPLATE_FILENAME = "createWifiTables.hql.template";
    public static final String OUTPUT_FILENAME = "createWifiTables.hql";
    public static final String QUERY_DIR = "/query";
    public static final String TR98_COLS_WITH_TYPES_VAR_REGEX = "\\$TR98_COLS_WITH_TYPES";
    public static final String TR181_COLS_WITH_TYPES_VAR_REGEX = "\\$TR181_COLS_WITH_TYPES";
    public static final String UNIFIED_COLS_WITH_TYPES_VAR_REGEX = "\\$UNIFIED_COLS_WITH_TYPES";
    
    public static void main(final String[] args) {
        if (args.length != 2) {
            System.out.println("Unable to run. Following arguments missing: <createWifiTables.hql.template directory> <generated createWifiTables.hql output directory>");
            System.exit(1);
        }
        final File templateDirectory = new File(args[0]);
        final File outputDirectory = new File(args[1]);
        final Path templateFilePath = FileSystems.getDefault().getPath(templateDirectory.getAbsolutePath(), "createWifiTables.hql.template");
        if (!Files.isRegularFile(templateFilePath, new LinkOption[0])) {
            System.out.println("Cannot access template file at " + templateFilePath);
            System.exit(1);
        }
        if (!isWritableDirectory(outputDirectory)) {
            System.exit(1);
        }
        final String template = loadTemplate(templateFilePath);
        final Map<String, String> varReplacements = createVarReplacementMap();
        final String parsedTemplate = parseTemplate(template, varReplacements);
        final Path outputFilePath = Paths.get(outputDirectory.getAbsolutePath(), "createWifiTables.hql");
        toFile(parsedTemplate, outputFilePath);
    }
    
    private static String parseTemplate(final String template, final Map<String, String> varReplacements) {
        return template.replaceAll("\\$TR98_COLS_WITH_TYPES", varReplacements.get("\\$TR98_COLS_WITH_TYPES")).replaceAll("\\$TR181_COLS_WITH_TYPES", varReplacements.get("\\$TR181_COLS_WITH_TYPES")).replaceAll("\\$UNIFIED_COLS_WITH_TYPES", varReplacements.get("\\$UNIFIED_COLS_WITH_TYPES"));
    }
    
    private static Map<String, String> createVarReplacementMap() {
        final WifiSparkConfig wifiSparkConfig = new WifiSparkConfig(CreateWifiTableScriptGenerator.class.getResource("/HAL_ModelType-Mapping.xml"), CreateWifiTableScriptGenerator.class.getResource("/HAL_DataMapping-TR98.xml"), CreateWifiTableScriptGenerator.class.getResource("/HAL_DataMapping-TR181.xml"), CreateWifiTableScriptGenerator.class.getResource("/HAL_Unified-Mapping.xml"), null);
        final Map<String, String> varReplacementMap = new HashMap<String, String>();
        final StructType tr98schema = wifiSparkConfig.getTr98Metadata().getTransposeTableMetadata().getTransposeSchema();
        final String tr98Replacement = getColumnNameTypePairs(tr98schema);
        varReplacementMap.put("\\$TR98_COLS_WITH_TYPES", tr98Replacement);
        final StructType tr181schema = wifiSparkConfig.getTr181Metadata().getTransposeTableMetadata().getTransposeSchema();
        final String tr181Replacement = getColumnNameTypePairs(tr181schema);
        varReplacementMap.put("\\$TR181_COLS_WITH_TYPES", tr181Replacement);
        final StructType unifiedSchema = wifiSparkConfig.getUnifiedTableMetadata().getSchema();
        final String unifiedReplacement = getColumnNameTypePairs(unifiedSchema);
        varReplacementMap.put("\\$UNIFIED_COLS_WITH_TYPES", unifiedReplacement);
        return varReplacementMap;
    }
    
    private static String getColumnNameTypePairs(final StructType schema) {
        return Stream.of(schema.fields()).map((Function<? super StructField, ?>)CreateWifiTableScriptGenerator::buildColumnNameTypePair).collect((Collector<? super Object, ?, String>)Collectors.joining(",\n"));
    }
    
    private static String buildColumnNameTypePair(final StructField field) {
        return field.name() + " " + ParamType.fromDataType(field.dataType()).getHiveType();
    }
    
    private static String loadTemplate(final Path createWifiTablesTemplate) {
        System.out.println("Loading template " + createWifiTablesTemplate.toAbsolutePath());
        try (final BufferedReader reader = Files.newBufferedReader(createWifiTablesTemplate)) {
            return reader.lines().map((Function<? super String, ?>)String::trim).collect((Collector<? super Object, ?, String>)Collectors.joining(" \n"));
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new HalWifiException("Unable to read template file " + createWifiTablesTemplate.toAbsolutePath());
        }
    }
    
    private static void toFile(final String createWifiTablesHql, final Path createWifiTablesFilePath) {
        try (final BufferedWriter writer = Files.newBufferedWriter(createWifiTablesFilePath, new OpenOption[0])) {
            writer.write(createWifiTablesHql);
            System.out.println("Create-wifi-tables script successfully written to " + createWifiTablesFilePath);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new HalWifiException("Unable to write to file " + createWifiTablesFilePath.toAbsolutePath().toString());
        }
    }
    
    private static boolean isWritableDirectory(final File directory) {
        if (!directory.exists()) {
            System.out.println("Directory " + directory.getAbsolutePath() + " does not exist");
            return false;
        }
        if (!directory.isDirectory()) {
            System.out.println("Directory " + directory.getAbsolutePath() + " is not a directory");
            return false;
        }
        if (!directory.canWrite()) {
            System.out.println("Directory " + directory.getAbsolutePath() + " is not writable");
            return false;
        }
        return true;
    }
}
