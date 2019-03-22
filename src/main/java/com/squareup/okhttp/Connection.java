// 
// Decompiled by Procyon v0.5.30
// 

package com.squareup.okhttp;

import java.net.Socket;

public interface Connection
{
    Route getRoute();
    
    Socket getSocket();
    
    Handshake getHandshake();
    
    Protocol getProtocol();
}
