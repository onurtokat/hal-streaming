// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.uritemplate.vars.values;

import com.github.fge.msgsimple.load.MessageBundleLoader;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.uritemplate.URITemplateMessageBundle;
import java.util.Map;
import java.util.List;
import com.github.fge.msgsimple.bundle.MessageBundle;

public abstract class VariableValue
{
    protected static final MessageBundle BUNDLE;
    private final ValueType type;
    
    protected VariableValue(final ValueType type) {
        this.type = type;
    }
    
    public final ValueType getType() {
        return this.type;
    }
    
    public String getScalarValue() {
        throw new IllegalArgumentException(VariableValue.BUNDLE.printf("value.notScalar", this.type));
    }
    
    public List<String> getListValue() {
        throw new IllegalArgumentException(VariableValue.BUNDLE.printf("value.notList", this.type));
    }
    
    public Map<String, String> getMapValue() {
        throw new IllegalArgumentException(VariableValue.BUNDLE.printf("value.notMap", this.type));
    }
    
    public abstract boolean isEmpty();
    
    static {
        BUNDLE = MessageBundles.getBundle(URITemplateMessageBundle.class);
    }
}
