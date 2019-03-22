// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

class RequestSignal extends Request
{
    private String signal;
    
    RequestSignal() {
        this.signal = "KILL";
    }
    
    public void setSignal(final String signal) {
        this.signal = signal;
    }
    
    public void request(final Session session, final Channel channel) throws Exception {
        super.request(session, channel);
        final Buffer buffer = new Buffer();
        final Packet packet = new Packet(buffer);
        packet.reset();
        buffer.putByte((byte)98);
        buffer.putInt(channel.getRecipient());
        buffer.putString("signal".getBytes());
        buffer.putByte((byte)(this.waitForReply() ? 1 : 0));
        buffer.putString(this.signal.getBytes());
        this.write(packet);
    }
}
