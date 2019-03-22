// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector;

import okio.Buffer;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;
import java.io.IOException;
import com.squareup.okhttp.MediaType;
import okio.BufferedSource;
import com.squareup.okhttp.ResponseBody;

public class ProgressResponseBody extends ResponseBody
{
    private final ResponseBody responseBody;
    private final ProgressListener progressListener;
    private BufferedSource bufferedSource;
    
    public ProgressResponseBody(final ResponseBody responseBody, final ProgressListener progressListener) {
        this.responseBody = responseBody;
        this.progressListener = progressListener;
    }
    
    @Override
    public MediaType contentType() {
        return this.responseBody.contentType();
    }
    
    @Override
    public long contentLength() throws IOException {
        return this.responseBody.contentLength();
    }
    
    @Override
    public BufferedSource source() throws IOException {
        if (this.bufferedSource == null) {
            this.bufferedSource = Okio.buffer(this.source(this.responseBody.source()));
        }
        return this.bufferedSource;
    }
    
    private Source source(final Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;
            
            @Override
            public long read(final Buffer sink, final long byteCount) throws IOException {
                final long bytesRead = super.read(sink, byteCount);
                this.totalBytesRead += ((bytesRead != -1L) ? bytesRead : 0L);
                ProgressResponseBody.this.progressListener.update(this.totalBytesRead, ProgressResponseBody.this.responseBody.contentLength(), bytesRead == -1L);
                return bytesRead;
            }
        };
    }
    
    public interface ProgressListener
    {
        void update(final long p0, final long p1, final boolean p2);
    }
}
