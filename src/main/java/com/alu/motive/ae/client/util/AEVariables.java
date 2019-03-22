// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.ae.client.util;

public enum AEVariables
{
    HDFS_URL("hdfsURL") {
        @Override
        public String getComputationFormula(final String suffix) {
            return AEOozieVariables.NAME_NODE.toString();
        }
    }, 
    HDFS_JOB_DIR("hdfsJobDir") {
        @Override
        public String getComputationFormula(final String suffix) {
            return AEOozieVariables.JOB_DIR.toString();
        }
    }, 
    JOB_ID("jobId") {
        @Override
        public String getComputationFormula(final String suffix) {
            return "replaceAll(coordId, \"@.*\",\"\")";
        }
    }, 
    JOB_ID_FOR_TABLE_NAME("jobIdForTableName") {
        @Override
        public String getComputationFormula(final String suffix) {
            return "replaceAll(replaceAll(replaceAll(coordId, \"@.*\",\"\"), \"-\", \"\"), \"..........\\$\", \"\")";
        }
    }, 
    JOB_NAME("jobName") {
        @Override
        public String getComputationFormula(final String suffix) {
            return AEOozieVariables.JOB_NAME.toString();
        }
    }, 
    DATA_START_TIME("dataStartTime"), 
    DATA_START_TIME_CUSTOM1("dataStartTimeCustom1"), 
    DATA_START_TIME_CUSTOM2("dataStartTimeCustom2"), 
    DATA_START_TIME_CUSTOM3("dataStartTimeCustom3"), 
    DATA_START_DATE("dataStartDate"), 
    DATA_END_TIME("dataEndTime"), 
    DATA_GP("dataGP"), 
    DATA_OLDEST_TIME("dataOldestTime"), 
    DATA_HOUR("dataHour"), 
    DATA_DAY("dataDay"), 
    DATA_WEEK("dataWeek"), 
    DATA_MONTH("dataMonth"), 
    REFMODEL_PARTITION_YEAR("refModelDataYear"), 
    REFMODEL_PARTITION_MONTH("refModelDataMonth"), 
    REFMODEL_PARTITION_DAY("refModelDataDay"), 
    PARTITION_START_GP("dataStartGP"), 
    REFMODEL_DAY("refModelDay"), 
    MAVG("mavg"), 
    MAVG_LAST_NUMBER("nbOfPeriodsToComputeMAVG"), 
    PREVIOUS("previous"), 
    PREVIOUS_INPUT_NUMBER("nbOfPeriodsToComputePREVIOUS"), 
    FIRST_OD_TIME_PARTITIONS_INFO("partitionInfoFirstOD"), 
    FIRST_OD_PERIODS_NUMBER("nbOfPeriodsToComputeFirstTransposeOD"), 
    TIME_PARTITIONS_INFO("timePartitionsInfo"), 
    STAGING_PARTITIONS_INFO("stagingPartitionsInfo"), 
    DAY_PARTITION_COLUMN("dayPartitionColumn"), 
    HOUR_PARTITION_COLUMN("hourPartitionColumn"), 
    QUARTER_PARTITION_COLUMN("quarterPartitionColumn"), 
    COLLECTION_TIME_COLUMN("collectionTimeColumn"), 
    DATA_RETENTION_DAYS("dataRetentionDays"), 
    OBSOLETE_INV_RETENTION_DAYS("obsoleteInventoryRetentionDays"), 
    ON_DEMAND_JOB_FIRST_ITERATION("firstIteration"), 
    ON_DEMAND_JOB_LAST_ITERATION("lastIteration"), 
    START_GP_COLUMN("startGPColumn"), 
    GP_COLUMN("gpColumn"), 
    DAY_COLUMN("dayColumn"), 
    WEEK_COLUMN("weekColumn"), 
    MONTH_COLUMN("monthColumn"), 
    SKIP_QUERY_VALIDATION("skipQueryValidation"), 
    HIVE_SEVER_VERSION("hiveServerVersion"), 
    SKIP_ITERATION("skipIteration"), 
    IS_ON_DEMAND_JOB("isOnDemandJob"), 
    CURRENT_TIME("currentTime"), 
    PREVIOUS_TIME("previousTime"), 
    IS_NOTIFICATION_ENABLED("isNotificationEnabled"), 
    SOURCE_FOLDER("sourceDir"), 
    TARGET_FOLDER("targetDir"), 
    FILE_PREFIX("fileNamePrefix"), 
    IS_STAGING_DELAYED("isStagingDelayed"), 
    CREATE_VIRTUAL_ACCOUNTS("createVirtualAccounts"), 
    CUSTOM_PROPERTIES_FILE("customPropertiesFile"), 
    ACTION_RETRY_MAX("retryMax"), 
    ACTION_RETRY_INTERVAL("retryInterval");
    
    public static final String AE_PREFIX = "ae_";
    public static final String FIRST_JAVA_ACTION_NAME = "ParamsComputer";
    private static final String FIRST_CF_PART = "wf:actionData('ParamsComputer')['";
    private final String identifier;
    
    private AEVariables(final String identifier) {
        this.identifier = identifier;
    }
    
    public String getAEIdentifier() {
        return this.identifier;
    }
    
    @Override
    public String toString() {
        return this.identifier;
    }
    
    public String getComputationFormula(final String suffix) {
        final StringBuilder sb = new StringBuilder();
        sb.append("wf:actionData('ParamsComputer')['");
        sb.append(this.identifier);
        if (suffix != null) {
            sb.append(suffix);
        }
        sb.append("']");
        return sb.toString();
    }
    
    public String getDisplayNameAsOozieVariable() {
        return "${" + this.getDisplayName() + "}";
    }
    
    public String getDisplayName() {
        return "ae_" + this.identifier;
    }
    
    public static AEVariables fromString(final String value) {
        for (final AEVariables variable : values()) {
            if (variable.getDisplayName().equalsIgnoreCase(value)) {
                return variable;
            }
        }
        return null;
    }
}
