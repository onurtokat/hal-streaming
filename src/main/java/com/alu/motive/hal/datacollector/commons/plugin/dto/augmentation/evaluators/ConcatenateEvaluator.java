// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.commons.plugin.dto.augmentation.evaluators;

public class ConcatenateEvaluator implements AugmentingParamValueEvaluator<String>
{
    static final String CONCAT_FUNCTION = "concat(";
    String[] objects2concat;
    
    public ConcatenateEvaluator(final String functionString) {
        final String parameters = this.extractParameters(functionString);
        this.objects2concat = parameters.split(",");
        for (int i = 0; i < this.objects2concat.length; ++i) {
            this.objects2concat[i] = ((this.objects2concat[i] != null) ? this.objects2concat[i].trim() : null);
        }
    }
    
    private String extractParameters(final String functionString) {
        final int beginIndex = functionString.indexOf("(") + 1;
        final int length = functionString.lastIndexOf(")");
        return functionString.substring(beginIndex, length);
    }
    
    @Override
    public String eval() {
        final StringBuilder sb = new StringBuilder();
        for (final Object object : this.objects2concat) {
            sb.append(object);
        }
        return sb.toString();
    }
}
