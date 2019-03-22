// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

class RequestShell extends Request
{
    public void request(final Session session, final Channel channel) throws Exception {
        super.request(session, channel);
        final Buffer buffer = new Buffer();
        final Packet packet = new Packet(buffer);
        packet.reset();
        buffer.putByte((byte)98);
        buffer.putInt(channel.getRecipient());
        buffer.putString("shell".getBytes());
        buffer.putByte((byte)(this.waitForReply() ? 1 : 0));
        this.write(packet);
    }
}
