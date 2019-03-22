// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

class RequestEnv extends Request
{
    byte[] name;
    byte[] value;
    
    RequestEnv() {
        this.name = new byte[0];
        this.value = new byte[0];
    }
    
    void setEnv(final byte[] name, final byte[] value) {
        this.name = name;
        this.value = value;
    }
    
    public void request(final Session session, final Channel channel) throws Exception {
        super.request(session, channel);
        final Buffer buffer = new Buffer();
        final Packet packet = new Packet(buffer);
        packet.reset();
        buffer.putByte((byte)98);
        buffer.putInt(channel.getRecipient());
        buffer.putString("env".getBytes());
        buffer.putByte((byte)(this.waitForReply() ? 1 : 0));
        buffer.putString(this.name);
        buffer.putString(this.value);
        this.write(packet);
    }
}
