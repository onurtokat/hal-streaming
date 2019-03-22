// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

public class RequestSubsystem extends Request
{
    private String subsystem;
    
    public RequestSubsystem() {
        this.subsystem = null;
    }
    
    public void request(final Session session, final Channel channel, final String subsystem, final boolean reply) throws Exception {
        this.setReply(reply);
        this.subsystem = subsystem;
        this.request(session, channel);
    }
    
    public void request(final Session session, final Channel channel) throws Exception {
        super.request(session, channel);
        final Buffer buffer = new Buffer();
        final Packet packet = new Packet(buffer);
        packet.reset();
        buffer.putByte((byte)98);
        buffer.putInt(channel.getRecipient());
        buffer.putString("subsystem".getBytes());
        buffer.putByte((byte)(this.waitForReply() ? 1 : 0));
        buffer.putString(this.subsystem.getBytes());
        this.write(packet);
    }
}
