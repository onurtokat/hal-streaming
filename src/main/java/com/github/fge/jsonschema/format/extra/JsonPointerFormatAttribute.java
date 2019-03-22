// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.format.extra;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.format.FormatAttribute;
import com.github.fge.jsonschema.format.AbstractFormatAttribute;

public final class JsonPointerFormatAttribute extends AbstractFormatAttribute
{
    private static final FormatAttribute INSTANCE;
    
    private JsonPointerFormatAttribute() {
        super("json-pointer", NodeType.STRING, new NodeType[0]);
    }
    
    public static FormatAttribute getInstance() {
        return JsonPointerFormatAttribute.INSTANCE;
    }
    
    @Override
    public void validate(final ProcessingReport report, final MessageBundle bundle, final FullData data) throws ProcessingException {
        final String value = data.getInstance().getNode().textValue();
        try {
            new JsonPointer(value);
        }
        catch (JsonPointerException ignored) {
            report.error(this.newMsg(data, bundle, "err.format.jsonpointer.invalid").putArgument("value", value));
        }
    }
    
    static {
        INSTANCE = new JsonPointerFormatAttribute();
    }
}
