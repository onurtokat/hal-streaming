// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.config;

import java.util.EnumSet;
import java.util.Optional;
import java.util.regex.Matcher;
import com.alu.hal.streaming.exception.HalWifiException;
import java.util.regex.Pattern;

import com.alu.hal.streaming.hive.model.SparkTempView;
import java.util.HashMap;
import java.net.URL;
import java.util.Map;
import org.apache.log4j.Logger;
import java.io.Serializable;

public class WifiQueries implements Serializable
{
    private static final UdfConfig UDF_CONFIG;
    private static final String PLACEHOLDER_PATTERN = "\\$\\{(.*?)\\}";
    private static Logger LOG;
    private String hdfsURL;
    private String hqlFileFolder;
    private String hqlTemplateFileFolder;
    private String avscFileFolder;
    private String avscTemplateFileFolder;
    private static final String KPI_SOURCE_TABLE_VAR = "${KPI_SOURCE_TABLE}";
    private Map<QueryNames, String> queries;
    
    public WifiQueries(final URL queryDirResource) {
        this.hdfsURL = DashBoardConfig.instance().getString(DashBoardConfig.HDFS_URL, "hdfs://mas:8020");
        this.hqlFileFolder = DashBoardConfig.instance().getString("hql.hdfs.file.folder", "/data/hda/queries/wifi-streaming");
        this.hqlTemplateFileFolder = DashBoardConfig.instance().getString("hql.hdfs.templates.folder", "/data/hda/queries/wifi-streaming/templates");
        this.avscFileFolder = DashBoardConfig.instance().getString("avsc.hdfs.file.folder", "/data/hda/kafka-avsc");
        this.avscTemplateFileFolder = DashBoardConfig.instance().getString("avsc.hdfs.templates.folder", "/data/hda/kafka-avsc/templates");
        this.queries = new HashMap<QueryNames, String>();
        this.loadQueries(queryDirResource);
        this.parseQueryVars();
    }
    
    private void parseQueryVars() {
        this.queries.replace(QueryNames.KPI_HOME_WIFI_RADIO, this.queries.get(QueryNames.KPI_HOME_WIFI_RADIO).replace("${KPI_SOURCE_TABLE}", SparkTempView.UNIFIED_WIFI_DVC.getTempViewName()));
        this.queries.replace(QueryNames.KPI_HOME_WIFI_ACCESS_POINT, this.queries.get(QueryNames.KPI_HOME_WIFI_ACCESS_POINT).replace("${KPI_SOURCE_TABLE}", SparkTempView.UNIFIED_WIFI_DVC.getTempViewName()));
        this.queries.replace(QueryNames.KPI_HOME_WIFI_MGD_DEVICE, this.queries.get(QueryNames.KPI_HOME_WIFI_MGD_DEVICE).replace("${KPI_SOURCE_TABLE}", SparkTempView.UNIFIED_WIFI_DVC.getTempViewName()));
        this.queries.replace(QueryNames.KPI_HOME_WIFI, this.queries.get(QueryNames.KPI_HOME_WIFI).replace("${KPI_SOURCE_TABLE}", SparkTempView.UNIFIED_WIFI_DVC.getTempViewName()));
        this.queries.replace(QueryNames.KPI_HOME_WIFI_RADIO_SWND_HQL, this.queries.get(QueryNames.KPI_HOME_WIFI_RADIO_SWND_HQL).replace("${KPI_SOURCE_TABLE}", SparkTempView.AGGREGATED_INPUT_TABLE.getTempViewName()));
        this.queries.replace(QueryNames.KPI_HOME_WIFI_AGG_ACCESS_POINT, this.queries.get(QueryNames.KPI_HOME_WIFI_AGG_ACCESS_POINT).replace("${KPI_SOURCE_TABLE}", SparkTempView.AGGREGATED_INPUT_TABLE.getTempViewName()));
        this.queries.replace(QueryNames.KPI_HOME_WIFI_AGG_ASSOC_DEVICE, this.queries.get(QueryNames.KPI_HOME_WIFI_AGG_ASSOC_DEVICE).replace("${KPI_SOURCE_TABLE}", SparkTempView.AGGREGATED_INPUT_TABLE.getTempViewName()));
        this.queries.replace(QueryNames.KPI_HOME_WIFI_MGD_DEVICE_SWND, this.queries.get(QueryNames.KPI_HOME_WIFI_MGD_DEVICE_SWND).replace("${KPI_SOURCE_TABLE}", SparkTempView.AGGREGATED_INPUT_TABLE.getTempViewName()));
        this.queries.replace(QueryNames.KPI_HOME_WIFI_SWND, this.queries.get(QueryNames.KPI_HOME_WIFI_SWND).replace("${KPI_SOURCE_TABLE}", SparkTempView.AGGREGATED_INPUT_TABLE.getTempViewName()));
        this.replaceUDFParameters();
    }
    
    private void replaceUDFParameters() {
        for (final Map.Entry<QueryNames, String> entry : this.queries.entrySet()) {
            String query = entry.getValue();
            query = this.replaceQueryUdfVars(entry.getKey().getHqlTemplateFileName(), query);
            this.queries.replace(entry.getKey(), query);
        }
    }
    
    private String replaceQueryUdfVars(final String queryName, String query) {
        final Pattern placeHolderPattern = Pattern.compile("\\$\\{(.*?)\\}");
        final Matcher placeHolderMatcher = placeHolderPattern.matcher(query);
        while (placeHolderMatcher.find()) {
            final String placeHolder = placeHolderMatcher.group();
            final String placeHolderProp = placeHolderMatcher.group(1);
            final String placeHolderValue = WifiQueries.UDF_CONFIG.getProperty(placeHolderProp);
            WifiQueries.LOG.info("PlaceHolder=" + placeHolder + " placeHolderProp=" + placeHolderProp + " placeHolderValue=" + placeHolderValue);
            if (placeHolderValue == null) {
                String message = "'" + placeHolderProp + "' placeHolder is not defined in configuration properties";
                WifiQueries.LOG.error(message);
                message = message + "\n Unable to replace placeholders in querie " + queryName;
                throw new HalWifiException(message);
            }
            query = query.replace(placeHolder, placeHolderValue);
            placeHolderMatcher.reset(query);
        }
        WifiQueries.LOG.info("query=" + query);
        return query;
    }
    
    public String getQuery(final QueryNames wifiQueryName) {
        final String query = this.queries.get(wifiQueryName);
        if (query == null) {
            throw new HalWifiException("Query searched with name " + wifiQueryName + " is not available. Ensure that query file is present in the correct directory.");
        }
        return query;
    }
    
    private void loadQueries(final URL queryDir) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     0: aload_1         /* queryDir */
        //     1: \u0131nvokev\u0131rtual   java/net/URL.toURI:()Ljava/net/URI;
        //     4: \u0131nvokestat\u0131c    java/nio/file/Paths.get:(Ljava/net/URI;)Ljava/nio/file/Path;
        //     7: astore_2        /* dir */
        //     8: new             Lorg/apache/hadoop/conf/Configuration;
        //    11: dup            
        //    12: \u0131nvokespec\u0131al   org/apache/hadoop/conf/Configuration.<init>:()V
        //    15: \u0131nvokestat\u0131c    org/apache/hadoop/fs/FileSystem.get:(Lorg/apache/hadoop/conf/Configuration;)Lorg/apache/hadoop/fs/FileSystem;
        //    18: astore_3        /* fs */
        //    19: aload_0         /* this */
        //    20: ldc             Lcom/alu/hal/streaming/config/WifiQueries$QueryNames;.class
        //    22: \u0131nvokestat\u0131c    java/util/EnumSet.allOf:(Ljava/lang/Class;)Ljava/util/EnumSet;
        //    25: \u0131nvokev\u0131rtual   java/util/EnumSet.stream:()Ljava/util/stream/Stream;
        //    28: aload_0         /* this */
        //    29: aload_3         /* fs */
        //    30: \u0131nvokedynam\u0131c   apply:(Lcom/alu/hal/streaming/config/WifiQueries;Lorg/apache/hadoop/fs/FileSystem;)Ljava/util/function/Function;
        //    35: \u0131nvoke\u0131nterface java/util/stream/Stream.map:(Ljava/util/function/Function;)Ljava/util/stream/Stream;
        //    40: \u0131nvokedynam\u0131c   apply:()Ljava/util/function/Function;
        //    45: \u0131nvokedynam\u0131c   apply:()Ljava/util/function/Function;
        //    50: \u0131nvokestat\u0131c    java/util/stream/Collectors.toMap:(Ljava/util/function/Function;Ljava/util/function/Function;)Ljava/util/stream/Collector;
        //    53: \u0131nvoke\u0131nterface java/util/stream/Stream.collect:(Ljava/util/stream/Collector;)Ljava/lang/Object;
        //    58: checkcast       Ljava/util/Map;
        //    61: putf\u0131eld        com/alu/hal/streaming/config/WifiQueries.queries:Ljava/util/Map;
        //    64: goto            80
        //    67: astore_2        /* e */
        //    68: aload_2         /* e */
        //    69: \u0131nvokev\u0131rtual   java/net/URISyntaxException.printStackTrace:()V
        //    72: goto            80
        //    75: astore_2        /* e */
        //    76: aload_2         /* e */
        //    77: \u0131nvokev\u0131rtual   java/io/IOException.printStackTrace:()V
        //    80: return         
        //    LocalVariableTable:
        //  Start  Length  Slot  Name      Signature
        //  -----  ------  ----  --------  ------------------------------------------
        //  8      56      2     dir       Ljava/nio/file/Path;
        //  19     45      3     fs        Lorg/apache/hadoop/fs/FileSystem;
        //  68     4       2     e         Ljava/net/URISyntaxException;
        //  76     4       2     e         Ljava/io/IOException;
        //  0      81      0     this      Lcom/alu/hal/streaming/config/WifiQueries;
        //  0      81      1     queryDir  Ljava/net/URL;
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                         
        //  -----  -----  -----  -----  -----------------------------
        //  0      64     67     75     Ljava/net/URISyntaxException;
        //  0      64     75     80     Ljava/io/IOException;
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
        UDF_CONFIG = UdfConfig.getInstance();
        WifiQueries.LOG = Logger.getLogger(WifiQueries.class);
    }
    
    public enum QueryNames
    {
        KPI_HOME_WIFI_ACCESS_POINT("kpiHomeWifiAccessPoint.hql"), 
        KPI_HOME_WIFI_AGG_ACCESS_POINT("kpiHomeWifiAccessPointSwnd.hql"), 
        KPI_HOME_WIFI_AGG_ASSOC_DEVICE("kpiHomeWifiAssocDevice.hql"), 
        KPI_HOME_WIFI_RADIO("kpiHomeWifiRadio.hql"), 
        KPI_HOME_WIFI_RADIO_SWND_HQL("kpiHomeWifiRadioSwnd.hql"), 
        KPI_HOME_WIFI_MGD_DEVICE("kpiHomeWifiDevice.hql"), 
        KPI_HOME_WIFI_MGD_DEVICE_SWND("kpiHomeWifiDeviceSwnd.hql"), 
        KPI_HOME_WIFI("kpiHomeWifiBasic.hql"), 
        KPI_HOME_WIFI_SWND("kpiHomeWifiSwnd.hql");
        
        private String hqlTemplateFileName;
        
        private QueryNames(final String hqlTemplateFileName) {
            this.hqlTemplateFileName = hqlTemplateFileName;
        }
        
        public String getHqlTemplateFileName() {
            return this.hqlTemplateFileName;
        }
        
        public static Optional<QueryNames> valueOfHqlFileName(final String hqlFileName) {
            return EnumSet.allOf(QueryNames.class).stream().filter(name -> name.getHqlTemplateFileName().equals(hqlFileName)).findFirst();
        }
    }
}
