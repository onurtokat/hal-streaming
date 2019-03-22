// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

class RequestPtyReq extends Request
{
    private String ttype;
    private int tcol;
    private int trow;
    private int twp;
    private int thp;
    private byte[] terminal_mode;
    
    RequestPtyReq() {
        this.ttype = "vt100";
        this.tcol = 80;
        this.trow = 24;
        this.twp = 640;
        this.thp = 480;
        this.terminal_mode = "".getBytes();
    }
    
    void setCode(final String s) {
    }
    
    void setTType(final String ttype) {
        this.ttype = ttype;
    }
    
    void setTerminalMode(final byte[] terminal_mode) {
        this.terminal_mode = terminal_mode;
    }
    
    void setTSize(final int tcol, final int trow, final int twp, final int thp) {
        this.tcol = tcol;
        this.trow = trow;
        this.twp = twp;
        this.thp = thp;
    }
    
    public void request(final Session session, final Channel channel) throws Exception {
        super.request(session, channel);
        final Buffer buffer = new Buffer();
        final Packet packet = new Packet(buffer);
        packet.reset();
        buffer.putByte((byte)98);
        buffer.putInt(channel.getRecipient());
        buffer.putString("pty-req".getBytes());
        buffer.putByte((byte)(this.waitForReply() ? 1 : 0));
        buffer.putString(this.ttype.getBytes());
        buffer.putInt(this.tcol);
        buffer.putInt(this.trow);
        buffer.putInt(this.twp);
        buffer.putInt(this.thp);
        buffer.putString(this.terminal_mode);
        this.write(packet);
    }
}
