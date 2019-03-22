// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.ae.client.template;

public enum ModelType
{
    ENTITY("Entity"), 
    PARTITION("Partition"), 
    DYNAMIC_PARTITION("DynamicPartition"), 
    HOUR_PARTITION("HourPartition"), 
    QUARTER_PARTITION("QuarterPartition"), 
    DAY_PARTITION("DayPartition"), 
    START_GP_PARTITION("StartGPPartition"), 
    WEEK_PARTITION("WeekPartition"), 
    ATTRIBUTE("Attribute"), 
    MONTH_PARTITION("MonthPartition"), 
    REF_ATTRIBUTE("RefAttribute"), 
    COLLECTED_ATTRIBUTE("CollectedAttribute"), 
    ENTITY_ATTRIBUTE("EntityAttribute"), 
    TIME_ATTRIBUTE("TimeAttribute"), 
    ATTRIBUTE_EVT_TYPE("AttributeEvtType"), 
    ATTRIBUTE_EVT_START("AttributeDateEvtStart"), 
    ATTRIBUTE_EVT_DATE("AttributeEvtDate"), 
    ATTRIBUTE_TEMPLATE_ID("AttributeTemplateID"), 
    KPI("KPI"), 
    KQI("KQI"), 
    OBSERVATION("Obs"), 
    ROW("Row"), 
    PARAMETER("Parameter"), 
    PARAMETER_NAME("ParameterName"), 
    PARAMETER_VALUE("ParameterValue"), 
    INTERVAL_PARAMETER("IntervalParameter"), 
    PARAMETER_TEMPLATE_ID("Parameter_TemplateID"), 
    PARAMETER_USE_CASE_ID("Parameter_UseCaseID"), 
    PARAMETER_SEVERITY("Parameter_Severity"), 
    PARAMETER_ADDITIONAL_INFO("Parameter_AdditionalInfo"), 
    PARAMETER_USE_CASE_NAME("Parameter_UseCaseName"), 
    PARAMETER_TEMPLATE_NAME("Parameter_TemplateName"), 
    PARAMETER_ADDITIONAL_TEXT("Parameter_AdditionalText"), 
    PARAMETER_ACTION("Parameter_Action"), 
    PROVIDED("Provided"), 
    EXTERNAL("External"), 
    INV_ENTITY_TYPE("EntityType"), 
    INV_DATE("InvDate"), 
    INV_MP_JOIN_TO_ACCOUNT("JoinToAccount");
    
    private final String label;
    
    private ModelType(final String theId) {
        this.label = theId;
    }
    
    public String getLabel() {
        return this.label;
    }
    
    public boolean isPartition() {
        return this.name().contains("PARTITION");
    }
    
    public boolean isAttribute() {
        return this.name().contains("ATTRIBUTE") || this.name().contains("ENTITY");
    }
    
    @Override
    public String toString() {
        return this.label;
    }
    
    public static ModelType fromString(final String aValue) {
        for (final ModelType modelType : values()) {
            if (modelType.label.equals(aValue)) {
                return modelType;
            }
        }
        return null;
    }
    
    public static boolean isPartitionValue(final String modelTypeValue) {
        final ModelType modelType = fromString(modelTypeValue);
        return modelType != null && modelType.isPartition();
    }
}
