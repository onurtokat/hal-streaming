// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

class RequestWindowChange extends Request
{
    int width_columns;
    int height_rows;
    int width_pixels;
    int height_pixels;
    
    RequestWindowChange() {
        this.width_columns = 80;
        this.height_rows = 24;
        this.width_pixels = 640;
        this.height_pixels = 480;
    }
    
    void setSize(final int width_columns, final int height_rows, final int width_pixels, final int height_pixels) {
        this.width_columns = width_columns;
        this.height_rows = height_rows;
        this.width_pixels = width_pixels;
        this.height_pixels = height_pixels;
    }
    
    public void request(final Session session, final Channel channel) throws Exception {
        super.request(session, channel);
        final Buffer buffer = new Buffer();
        final Packet packet = new Packet(buffer);
        packet.reset();
        buffer.putByte((byte)98);
        buffer.putInt(channel.getRecipient());
        buffer.putString("window-change".getBytes());
        buffer.putByte((byte)(this.waitForReply() ? 1 : 0));
        buffer.putInt(this.width_columns);
        buffer.putInt(this.height_rows);
        buffer.putInt(this.width_pixels);
        buffer.putInt(this.height_pixels);
        this.write(packet);
    }
}
