// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.format.draftv3;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.google.i18n.phonenumbers.NumberParseException;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.format.FormatAttribute;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.github.fge.jsonschema.format.AbstractFormatAttribute;

public final class PhoneAttribute extends AbstractFormatAttribute
{
    private static final PhoneNumberUtil PARSER;
    private static final FormatAttribute INSTANCE;
    
    public static FormatAttribute getInstance() {
        return PhoneAttribute.INSTANCE;
    }
    
    private PhoneAttribute() {
        super("phone", NodeType.STRING, new NodeType[0]);
    }
    
    @Override
    public void validate(final ProcessingReport report, final MessageBundle bundle, final FullData data) throws ProcessingException {
        final String input = data.getInstance().getNode().textValue();
        try {
            if (input.startsWith("+")) {
                PhoneAttribute.PARSER.parse(input, "ZZ");
            }
            else {
                PhoneAttribute.PARSER.parse(input, "FR");
            }
        }
        catch (NumberParseException ignored) {
            report.error(this.newMsg(data, bundle, "err.format.invalidPhoneNumber").putArgument("value", input));
        }
    }
    
    static {
        PARSER = PhoneNumberUtil.getInstance();
        INSTANCE = new PhoneAttribute();
    }
}
