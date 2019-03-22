// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

class RequestX11 extends Request
{
    public void setCookie(final String s) {
        ChannelX11.cookie = s.getBytes();
    }
    
    public void request(final Session session, final Channel channel) throws Exception {
        super.request(session, channel);
        final Buffer buffer = new Buffer();
        final Packet packet = new Packet(buffer);
        packet.reset();
        buffer.putByte((byte)98);
        buffer.putInt(channel.getRecipient());
        buffer.putString("x11-req".getBytes());
        buffer.putByte((byte)(this.waitForReply() ? 1 : 0));
        buffer.putByte((byte)0);
        buffer.putString("MIT-MAGIC-COOKIE-1".getBytes());
        buffer.putString(ChannelX11.getFakedCookie(session));
        buffer.putInt(0);
        this.write(packet);
        session.x11_forwarding = true;
    }
}
