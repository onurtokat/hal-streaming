// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.format.common;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.util.RhinoHelper;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.format.FormatAttribute;
import com.github.fge.jsonschema.format.AbstractFormatAttribute;

public final class RegexAttribute extends AbstractFormatAttribute
{
    private static final FormatAttribute INSTANCE;
    
    public static FormatAttribute getInstance() {
        return RegexAttribute.INSTANCE;
    }
    
    private RegexAttribute() {
        super("regex", NodeType.STRING, new NodeType[0]);
    }
    
    @Override
    public void validate(final ProcessingReport report, final MessageBundle bundle, final FullData data) throws ProcessingException {
        final String value = data.getInstance().getNode().textValue();
        if (!RhinoHelper.regexIsValid(value)) {
            report.error(this.newMsg(data, bundle, "err.format.invalidRegex").putArgument("value", value));
        }
    }
    
    static {
        INSTANCE = new RegexAttribute();
    }
}
