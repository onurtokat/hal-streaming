// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.format.extra;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jackson.NodeType;
import java.util.regex.Pattern;
import com.github.fge.jsonschema.format.FormatAttribute;
import com.github.fge.jsonschema.format.AbstractFormatAttribute;

public final class MacAddressFormatAttribute extends AbstractFormatAttribute
{
    private static final FormatAttribute instance;
    private static final Pattern MACADDR;
    
    public static FormatAttribute getInstance() {
        return MacAddressFormatAttribute.instance;
    }
    
    private MacAddressFormatAttribute() {
        super("mac", NodeType.STRING, new NodeType[0]);
    }
    
    @Override
    public void validate(final ProcessingReport report, final MessageBundle bundle, final FullData data) throws ProcessingException {
        final String input = data.getInstance().getNode().textValue();
        if (!MacAddressFormatAttribute.MACADDR.matcher(input).matches()) {
            report.error(this.newMsg(data, bundle, "err.format.macAddr.invalid").putArgument("value", input));
        }
    }
    
    static {
        instance = new MacAddressFormatAttribute();
        MACADDR = Pattern.compile("[A-Za-z0-9]{2}(?::[A-Za-z0-9]{2}){5}");
    }
}
