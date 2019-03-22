// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

class RequestExec extends Request
{
    private byte[] command;
    
    RequestExec(final byte[] command) {
        this.command = new byte[0];
        this.command = command;
    }
    
    public void request(final Session session, final Channel channel) throws Exception {
        super.request(session, channel);
        final Buffer buffer = new Buffer();
        final Packet packet = new Packet(buffer);
        packet.reset();
        buffer.putByte((byte)98);
        buffer.putInt(channel.getRecipient());
        buffer.putString("exec".getBytes());
        buffer.putByte((byte)(this.waitForReply() ? 1 : 0));
        buffer.putString(this.command);
        this.write(packet);
    }
}
