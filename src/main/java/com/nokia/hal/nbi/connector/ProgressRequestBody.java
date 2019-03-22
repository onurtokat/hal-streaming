// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector;

import okio.Buffer;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;
import java.io.IOException;
import com.squareup.okhttp.MediaType;
import okio.BufferedSink;
import com.squareup.okhttp.RequestBody;

public class ProgressRequestBody extends RequestBody
{
    private final RequestBody requestBody;
    private final ProgressRequestListener progressListener;
    private BufferedSink bufferedSink;
    
    public ProgressRequestBody(final RequestBody requestBody, final ProgressRequestListener progressListener) {
        this.requestBody = requestBody;
        this.progressListener = progressListener;
    }
    
    @Override
    public MediaType contentType() {
        return this.requestBody.contentType();
    }
    
    @Override
    public long contentLength() throws IOException {
        return this.requestBody.contentLength();
    }
    
    @Override
    public void writeTo(final BufferedSink sink) throws IOException {
        if (this.bufferedSink == null) {
            this.bufferedSink = Okio.buffer(this.sink(sink));
        }
        this.requestBody.writeTo(this.bufferedSink);
        this.bufferedSink.flush();
    }
    
    private Sink sink(final Sink sink) {
        return new ForwardingSink(sink) {
            long bytesWritten = 0L;
            long contentLength = 0L;
            
            @Override
            public void write(final Buffer source, final long byteCount) throws IOException {
                super.write(source, byteCount);
                if (this.contentLength == 0L) {
                    this.contentLength = ProgressRequestBody.this.contentLength();
                }
                this.bytesWritten += byteCount;
                ProgressRequestBody.this.progressListener.onRequestProgress(this.bytesWritten, this.contentLength, this.bytesWritten == this.contentLength);
            }
        };
    }
    
    public interface ProgressRequestListener
    {
        void onRequestProgress(final long p0, final long p1, final boolean p2);
    }
}
