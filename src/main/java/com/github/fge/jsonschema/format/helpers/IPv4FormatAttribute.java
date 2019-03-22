// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.format.helpers;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.google.common.net.InetAddresses;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.format.AbstractFormatAttribute;

public final class IPv4FormatAttribute extends AbstractFormatAttribute
{
    private static final int IPV4_LENGTH = 4;
    
    public IPv4FormatAttribute(final String fmt) {
        super(fmt, NodeType.STRING, new NodeType[0]);
    }
    
    @Override
    public void validate(final ProcessingReport report, final MessageBundle bundle, final FullData data) throws ProcessingException {
        final String ipaddr = data.getInstance().getNode().textValue();
        if (InetAddresses.isInetAddress(ipaddr) && InetAddresses.forString(ipaddr).getAddress().length == 4) {
            return;
        }
        report.error(this.newMsg(data, bundle, "err.format.invalidIPv4Address").putArgument("value", ipaddr));
    }
}
