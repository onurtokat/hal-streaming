// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.format.draftv3;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.format.FormatAttribute;
import java.math.BigInteger;
import com.github.fge.jsonschema.format.AbstractFormatAttribute;

public final class UTCMillisecAttribute extends AbstractFormatAttribute
{
    private static final int EPOCH_BITLENGTH = 31;
    private static final BigInteger ONE_THOUSAND;
    private static final FormatAttribute INSTANCE;
    
    public static FormatAttribute getInstance() {
        return UTCMillisecAttribute.INSTANCE;
    }
    
    private UTCMillisecAttribute() {
        super("utc-millisec", NodeType.INTEGER, new NodeType[] { NodeType.NUMBER });
    }
    
    @Override
    public void validate(final ProcessingReport report, final MessageBundle bundle, final FullData data) throws ProcessingException {
        final JsonNode instance = data.getInstance().getNode();
        BigInteger epoch = instance.bigIntegerValue();
        if (epoch.signum() == -1) {
            report.warn(this.newMsg(data, bundle, "warn.format.epoch.negative").putArgument("value", instance));
            return;
        }
        epoch = epoch.divide(UTCMillisecAttribute.ONE_THOUSAND);
        if (epoch.bitLength() > 31) {
            report.warn(this.newMsg(data, bundle, "warn.format.epoch.overflow").putArgument("value", instance));
        }
    }
    
    static {
        ONE_THOUSAND = new BigInteger("1000");
        INSTANCE = new UTCMillisecAttribute();
    }
}
