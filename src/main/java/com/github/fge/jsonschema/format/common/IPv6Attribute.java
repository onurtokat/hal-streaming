// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.format.common;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.net.InetAddresses;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.format.FormatAttribute;
import com.github.fge.jsonschema.format.AbstractFormatAttribute;

public final class IPv6Attribute extends AbstractFormatAttribute
{
    private static final int IPV6_LENGTH = 16;
    private static final FormatAttribute INSTANCE;
    
    public static FormatAttribute getInstance() {
        return IPv6Attribute.INSTANCE;
    }
    
    private IPv6Attribute() {
        super("ipv6", NodeType.STRING, new NodeType[0]);
    }
    
    @Override
    public void validate(final ProcessingReport report, final MessageBundle bundle, final FullData data) throws ProcessingException {
        final JsonNode instance = data.getInstance().getNode();
        final String ipaddr = instance.textValue();
        if (InetAddresses.isInetAddress(ipaddr) && InetAddresses.forString(ipaddr).getAddress().length == 16) {
            return;
        }
        report.error(this.newMsg(data, bundle, "err.format.invalidIPV6Address").putArgument("value", ipaddr));
    }
    
    static {
        INSTANCE = new IPv6Attribute();
    }
}
