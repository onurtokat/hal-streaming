// 
// Decompiled by Procyon v0.5.30
// 

package com.squareup.okhttp.internal.framed;

import java.net.SocketTimeoutException;
import okio.AsyncTimeout;
import java.io.EOFException;
import okio.Buffer;
import java.io.InterruptedIOException;
import okio.BufferedSource;
import java.util.Collection;
import java.util.ArrayList;
import okio.Sink;
import okio.Source;
import okio.Timeout;
import java.io.IOException;
import java.util.List;

public final class FramedStream
{
    long unacknowledgedBytesRead;
    long bytesLeftInWriteWindow;
    private final int id;
    private final FramedConnection connection;
    private final List<Header> requestHeaders;
    private List<Header> responseHeaders;
    private final FramedDataSource source;
    final FramedDataSink sink;
    private final StreamTimeout readTimeout;
    private final StreamTimeout writeTimeout;
    private ErrorCode errorCode;
    
    FramedStream(final int id, final FramedConnection connection, final boolean outFinished, final boolean inFinished, final List<Header> requestHeaders) {
        this.unacknowledgedBytesRead = 0L;
        this.readTimeout = new StreamTimeout();
        this.writeTimeout = new StreamTimeout();
        this.errorCode = null;
        if (connection == null) {
            throw new NullPointerException("connection == null");
        }
        if (requestHeaders == null) {
            throw new NullPointerException("requestHeaders == null");
        }
        this.id = id;
        this.connection = connection;
        this.bytesLeftInWriteWindow = connection.peerSettings.getInitialWindowSize(65536);
        this.source = new FramedDataSource((long)connection.okHttpSettings.getInitialWindowSize(65536));
        this.sink = new FramedDataSink();
        this.source.finished = inFinished;
        this.sink.finished = outFinished;
        this.requestHeaders = requestHeaders;
    }
    
    public int getId() {
        return this.id;
    }
    
    public synchronized boolean isOpen() {
        return this.errorCode == null && ((!this.source.finished && !this.source.closed) || (!this.sink.finished && !this.sink.closed) || this.responseHeaders == null);
    }
    
    public boolean isLocallyInitiated() {
        final boolean streamIsClient = (this.id & 0x1) == 0x1;
        return this.connection.client == streamIsClient;
    }
    
    public FramedConnection getConnection() {
        return this.connection;
    }
    
    public List<Header> getRequestHeaders() {
        return this.requestHeaders;
    }
    
    public synchronized List<Header> getResponseHeaders() throws IOException {
        this.readTimeout.enter();
        try {
            while (this.responseHeaders == null && this.errorCode == null) {
                this.waitForIo();
            }
        }
        finally {
            this.readTimeout.exitAndThrowIfTimedOut();
        }
        if (this.responseHeaders != null) {
            return this.responseHeaders;
        }
        throw new IOException("stream was reset: " + this.errorCode);
    }
    
    public synchronized ErrorCode getErrorCode() {
        return this.errorCode;
    }
    
    public void reply(final List<Header> responseHeaders, final boolean out) throws IOException {
        assert !Thread.holdsLock(this);
        boolean outFinished = false;
        synchronized (this) {
            if (responseHeaders == null) {
                throw new NullPointerException("responseHeaders == null");
            }
            if (this.responseHeaders != null) {
                throw new IllegalStateException("reply already sent");
            }
            this.responseHeaders = responseHeaders;
            if (!out) {
                this.sink.finished = true;
                outFinished = true;
            }
        }
        this.connection.writeSynReply(this.id, outFinished, responseHeaders);
        if (outFinished) {
            this.connection.flush();
        }
    }
    
    public Timeout readTimeout() {
        return this.readTimeout;
    }
    
    public Timeout writeTimeout() {
        return this.writeTimeout;
    }
    
    public Source getSource() {
        return this.source;
    }
    
    public Sink getSink() {
        synchronized (this) {
            if (this.responseHeaders == null && !this.isLocallyInitiated()) {
                throw new IllegalStateException("reply before requesting the sink");
            }
        }
        return this.sink;
    }
    
    public void close(final ErrorCode rstStatusCode) throws IOException {
        if (!this.closeInternal(rstStatusCode)) {
            return;
        }
        this.connection.writeSynReset(this.id, rstStatusCode);
    }
    
    public void closeLater(final ErrorCode errorCode) {
        if (!this.closeInternal(errorCode)) {
            return;
        }
        this.connection.writeSynResetLater(this.id, errorCode);
    }
    
    private boolean closeInternal(final ErrorCode errorCode) {
        assert !Thread.holdsLock(this);
        synchronized (this) {
            if (this.errorCode != null) {
                return false;
            }
            if (this.source.finished && this.sink.finished) {
                return false;
            }
            this.errorCode = errorCode;
            this.notifyAll();
        }
        this.connection.removeStream(this.id);
        return true;
    }
    
    void receiveHeaders(final List<Header> headers, final HeadersMode headersMode) {
        assert !Thread.holdsLock(this);
        ErrorCode errorCode = null;
        boolean open = true;
        synchronized (this) {
            if (this.responseHeaders == null) {
                if (headersMode.failIfHeadersAbsent()) {
                    errorCode = ErrorCode.PROTOCOL_ERROR;
                }
                else {
                    this.responseHeaders = headers;
                    open = this.isOpen();
                    this.notifyAll();
                }
            }
            else if (headersMode.failIfHeadersPresent()) {
                errorCode = ErrorCode.STREAM_IN_USE;
            }
            else {
                final List<Header> newHeaders = new ArrayList<Header>();
                newHeaders.addAll(this.responseHeaders);
                newHeaders.addAll(headers);
                this.responseHeaders = newHeaders;
            }
        }
        if (errorCode != null) {
            this.closeLater(errorCode);
        }
        else if (!open) {
            this.connection.removeStream(this.id);
        }
    }
    
    void receiveData(final BufferedSource in, final int length) throws IOException {
        assert !Thread.holdsLock(this);
        this.source.receive(in, length);
    }
    
    void receiveFin() {
        assert !Thread.holdsLock(this);
        final boolean open;
        synchronized (this) {
            this.source.finished = true;
            open = this.isOpen();
            this.notifyAll();
        }
        if (!open) {
            this.connection.removeStream(this.id);
        }
    }
    
    synchronized void receiveRstStream(final ErrorCode errorCode) {
        if (this.errorCode == null) {
            this.errorCode = errorCode;
            this.notifyAll();
        }
    }
    
    private void cancelStreamIfNecessary() throws IOException {
        assert !Thread.holdsLock(this);
        final boolean cancel;
        final boolean open;
        synchronized (this) {
            cancel = (!this.source.finished && this.source.closed && (this.sink.finished || this.sink.closed));
            open = this.isOpen();
        }
        if (cancel) {
            this.close(ErrorCode.CANCEL);
        }
        else if (!open) {
            this.connection.removeStream(this.id);
        }
    }
    
    void addBytesToWriteWindow(final long delta) {
        this.bytesLeftInWriteWindow += delta;
        if (delta > 0L) {
            this.notifyAll();
        }
    }
    
    private void checkOutNotClosed() throws IOException {
        if (this.sink.closed) {
            throw new IOException("stream closed");
        }
        if (this.sink.finished) {
            throw new IOException("stream finished");
        }
        if (this.errorCode != null) {
            throw new IOException("stream was reset: " + this.errorCode);
        }
    }
    
    private void waitForIo() throws InterruptedIOException {
        try {
            this.wait();
        }
        catch (InterruptedException e) {
            throw new InterruptedIOException();
        }
    }
    
    private final class FramedDataSource implements Source
    {
        private final Buffer receiveBuffer;
        private final Buffer readBuffer;
        private final long maxByteCount;
        private boolean closed;
        private boolean finished;
        
        private FramedDataSource(final long maxByteCount) {
            this.receiveBuffer = new Buffer();
            this.readBuffer = new Buffer();
            this.maxByteCount = maxByteCount;
        }
        
        @Override
        public long read(final Buffer sink, final long byteCount) throws IOException {
            if (byteCount < 0L) {
                throw new IllegalArgumentException("byteCount < 0: " + byteCount);
            }
            final long read;
            synchronized (FramedStream.this) {
                this.waitUntilReadable();
                this.checkNotClosed();
                if (this.readBuffer.size() == 0L) {
                    return -1L;
                }
                read = this.readBuffer.read(sink, Math.min(byteCount, this.readBuffer.size()));
                final FramedStream this$0 = FramedStream.this;
                this$0.unacknowledgedBytesRead += read;
                if (FramedStream.this.unacknowledgedBytesRead >= FramedStream.this.connection.okHttpSettings.getInitialWindowSize(65536) / 2) {
                    FramedStream.this.connection.writeWindowUpdateLater(FramedStream.this.id, FramedStream.this.unacknowledgedBytesRead);
                    FramedStream.this.unacknowledgedBytesRead = 0L;
                }
            }
            synchronized (FramedStream.this.connection) {
                final FramedConnection access$500 = FramedStream.this.connection;
                access$500.unacknowledgedBytesRead += read;
                if (FramedStream.this.connection.unacknowledgedBytesRead >= FramedStream.this.connection.okHttpSettings.getInitialWindowSize(65536) / 2) {
                    FramedStream.this.connection.writeWindowUpdateLater(0, FramedStream.this.connection.unacknowledgedBytesRead);
                    FramedStream.this.connection.unacknowledgedBytesRead = 0L;
                }
            }
            return read;
        }
        
        private void waitUntilReadable() throws IOException {
            FramedStream.this.readTimeout.enter();
            try {
                while (this.readBuffer.size() == 0L && !this.finished && !this.closed && FramedStream.this.errorCode == null) {
                    FramedStream.this.waitForIo();
                }
            }
            finally {
                FramedStream.this.readTimeout.exitAndThrowIfTimedOut();
            }
        }
        
        void receive(final BufferedSource in, long byteCount) throws IOException {
            assert !Thread.holdsLock(FramedStream.this);
            while (byteCount > 0L) {
                final boolean finished;
                final boolean flowControlError;
                synchronized (FramedStream.this) {
                    finished = this.finished;
                    flowControlError = (byteCount + this.readBuffer.size() > this.maxByteCount);
                }
                if (flowControlError) {
                    in.skip(byteCount);
                    FramedStream.this.closeLater(ErrorCode.FLOW_CONTROL_ERROR);
                    return;
                }
                if (finished) {
                    in.skip(byteCount);
                    return;
                }
                final long read = in.read(this.receiveBuffer, byteCount);
                if (read == -1L) {
                    throw new EOFException();
                }
                byteCount -= read;
                synchronized (FramedStream.this) {
                    final boolean wasEmpty = this.readBuffer.size() == 0L;
                    this.readBuffer.writeAll(this.receiveBuffer);
                    if (!wasEmpty) {
                        continue;
                    }
                    FramedStream.this.notifyAll();
                }
            }
        }
        
        @Override
        public Timeout timeout() {
            return FramedStream.this.readTimeout;
        }
        
        @Override
        public void close() throws IOException {
            synchronized (FramedStream.this) {
                this.closed = true;
                this.readBuffer.clear();
                FramedStream.this.notifyAll();
            }
            FramedStream.this.cancelStreamIfNecessary();
        }
        
        private void checkNotClosed() throws IOException {
            if (this.closed) {
                throw new IOException("stream closed");
            }
            if (FramedStream.this.errorCode != null) {
                throw new IOException("stream was reset: " + FramedStream.this.errorCode);
            }
        }
    }
    
    final class FramedDataSink implements Sink
    {
        private static final long EMIT_BUFFER_SIZE = 16384L;
        private final Buffer sendBuffer;
        private boolean closed;
        private boolean finished;
        
        FramedDataSink() {
            this.sendBuffer = new Buffer();
        }
        
        @Override
        public void write(final Buffer source, final long byteCount) throws IOException {
            assert !Thread.holdsLock(FramedStream.this);
            this.sendBuffer.write(source, byteCount);
            while (this.sendBuffer.size() >= 16384L) {
                this.emitDataFrame(false);
            }
        }
        
        private void emitDataFrame(final boolean outFinished) throws IOException {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     0: aload_0         /* this */
            //     1: getf\u0131eld        com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink.this$0:Lcom/squareup/okhttp/internal/framed/FramedStream;
            //     4: dup            
            //     5: astore          4
            //     7: mon\u0131torenter   
            //     8: aload_0         /* this */
            //     9: getf\u0131eld        com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink.this$0:Lcom/squareup/okhttp/internal/framed/FramedStream;
            //    12: \u0131nvokestat\u0131c    com/squareup/okhttp/internal/framed/FramedStream.access$1100:(Lcom/squareup/okhttp/internal/framed/FramedStream;)Lcom/squareup/okhttp/internal/framed/FramedStream$StreamTimeout;
            //    15: \u0131nvokev\u0131rtual   com/squareup/okhttp/internal/framed/FramedStream$StreamTimeout.enter:()V
            //    18: aload_0         /* this */
            //    19: getf\u0131eld        com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink.this$0:Lcom/squareup/okhttp/internal/framed/FramedStream;
            //    22: getf\u0131eld        com/squareup/okhttp/internal/framed/FramedStream.bytesLeftInWriteWindow:J
            //    25: lconst_0       
            //    26: lcmp           
            //    27: \u0131fgt            64
            //    30: aload_0         /* this */
            //    31: getf\u0131eld        com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink.finished:Z
            //    34: \u0131fne            64
            //    37: aload_0         /* this */
            //    38: getf\u0131eld        com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink.closed:Z
            //    41: \u0131fne            64
            //    44: aload_0         /* this */
            //    45: getf\u0131eld        com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink.this$0:Lcom/squareup/okhttp/internal/framed/FramedStream;
            //    48: \u0131nvokestat\u0131c    com/squareup/okhttp/internal/framed/FramedStream.access$800:(Lcom/squareup/okhttp/internal/framed/FramedStream;)Lcom/squareup/okhttp/internal/framed/ErrorCode;
            //    51: \u0131fnonnull       64
            //    54: aload_0         /* this */
            //    55: getf\u0131eld        com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink.this$0:Lcom/squareup/okhttp/internal/framed/FramedStream;
            //    58: \u0131nvokestat\u0131c    com/squareup/okhttp/internal/framed/FramedStream.access$900:(Lcom/squareup/okhttp/internal/framed/FramedStream;)V
            //    61: goto            18
            //    64: aload_0         /* this */
            //    65: getf\u0131eld        com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink.this$0:Lcom/squareup/okhttp/internal/framed/FramedStream;
            //    68: \u0131nvokestat\u0131c    com/squareup/okhttp/internal/framed/FramedStream.access$1100:(Lcom/squareup/okhttp/internal/framed/FramedStream;)Lcom/squareup/okhttp/internal/framed/FramedStream$StreamTimeout;
            //    71: \u0131nvokev\u0131rtual   com/squareup/okhttp/internal/framed/FramedStream$StreamTimeout.exitAndThrowIfTimedOut:()V
            //    74: goto            92
            //    77: astore          5
            //    79: aload_0         /* this */
            //    80: getf\u0131eld        com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink.this$0:Lcom/squareup/okhttp/internal/framed/FramedStream;
            //    83: \u0131nvokestat\u0131c    com/squareup/okhttp/internal/framed/FramedStream.access$1100:(Lcom/squareup/okhttp/internal/framed/FramedStream;)Lcom/squareup/okhttp/internal/framed/FramedStream$StreamTimeout;
            //    86: \u0131nvokev\u0131rtual   com/squareup/okhttp/internal/framed/FramedStream$StreamTimeout.exitAndThrowIfTimedOut:()V
            //    89: aload           5
            //    91: athrow         
            //    92: aload_0         /* this */
            //    93: getf\u0131eld        com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink.this$0:Lcom/squareup/okhttp/internal/framed/FramedStream;
            //    96: \u0131nvokestat\u0131c    com/squareup/okhttp/internal/framed/FramedStream.access$1200:(Lcom/squareup/okhttp/internal/framed/FramedStream;)V
            //    99: aload_0         /* this */
            //   100: getf\u0131eld        com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink.this$0:Lcom/squareup/okhttp/internal/framed/FramedStream;
            //   103: getf\u0131eld        com/squareup/okhttp/internal/framed/FramedStream.bytesLeftInWriteWindow:J
            //   106: aload_0         /* this */
            //   107: getf\u0131eld        com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink.sendBuffer:Lokio/Buffer;
            //   110: \u0131nvokev\u0131rtual   okio/Buffer.size:()J
            //   113: \u0131nvokestat\u0131c    java/lang/Math.min:(JJ)J
            //   116: lstore_2        /* toWrite */
            //   117: aload_0         /* this */
            //   118: getf\u0131eld        com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink.this$0:Lcom/squareup/okhttp/internal/framed/FramedStream;
            //   121: dup            
            //   122: getf\u0131eld        com/squareup/okhttp/internal/framed/FramedStream.bytesLeftInWriteWindow:J
            //   125: lload_2         /* toWrite */
            //   126: lsub           
            //   127: putf\u0131eld        com/squareup/okhttp/internal/framed/FramedStream.bytesLeftInWriteWindow:J
            //   130: aload           4
            //   132: mon\u0131torex\u0131t    
            //   133: goto            144
            //   136: astore          6
            //   138: aload           4
            //   140: mon\u0131torex\u0131t    
            //   141: aload           6
            //   143: athrow         
            //   144: aload_0         /* this */
            //   145: getf\u0131eld        com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink.this$0:Lcom/squareup/okhttp/internal/framed/FramedStream;
            //   148: \u0131nvokestat\u0131c    com/squareup/okhttp/internal/framed/FramedStream.access$1100:(Lcom/squareup/okhttp/internal/framed/FramedStream;)Lcom/squareup/okhttp/internal/framed/FramedStream$StreamTimeout;
            //   151: \u0131nvokev\u0131rtual   com/squareup/okhttp/internal/framed/FramedStream$StreamTimeout.enter:()V
            //   154: aload_0         /* this */
            //   155: getf\u0131eld        com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink.this$0:Lcom/squareup/okhttp/internal/framed/FramedStream;
            //   158: \u0131nvokestat\u0131c    com/squareup/okhttp/internal/framed/FramedStream.access$500:(Lcom/squareup/okhttp/internal/framed/FramedStream;)Lcom/squareup/okhttp/internal/framed/FramedConnection;
            //   161: aload_0         /* this */
            //   162: getf\u0131eld        com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink.this$0:Lcom/squareup/okhttp/internal/framed/FramedStream;
            //   165: \u0131nvokestat\u0131c    com/squareup/okhttp/internal/framed/FramedStream.access$600:(Lcom/squareup/okhttp/internal/framed/FramedStream;)I
            //   168: \u0131load_1         /* outFinished */
            //   169: \u0131feq            188
            //   172: lload_2         /* toWrite */
            //   173: aload_0         /* this */
            //   174: getf\u0131eld        com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink.sendBuffer:Lokio/Buffer;
            //   177: \u0131nvokev\u0131rtual   okio/Buffer.size:()J
            //   180: lcmp           
            //   181: \u0131fne            188
            //   184: \u0131const_1       
            //   185: goto            189
            //   188: \u0131const_0       
            //   189: aload_0         /* this */
            //   190: getf\u0131eld        com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink.sendBuffer:Lokio/Buffer;
            //   193: lload_2         /* toWrite */
            //   194: \u0131nvokev\u0131rtual   com/squareup/okhttp/internal/framed/FramedConnection.writeData:(IZLokio/Buffer;J)V
            //   197: aload_0         /* this */
            //   198: getf\u0131eld        com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink.this$0:Lcom/squareup/okhttp/internal/framed/FramedStream;
            //   201: \u0131nvokestat\u0131c    com/squareup/okhttp/internal/framed/FramedStream.access$1100:(Lcom/squareup/okhttp/internal/framed/FramedStream;)Lcom/squareup/okhttp/internal/framed/FramedStream$StreamTimeout;
            //   204: \u0131nvokev\u0131rtual   com/squareup/okhttp/internal/framed/FramedStream$StreamTimeout.exitAndThrowIfTimedOut:()V
            //   207: goto            225
            //   210: astore          7
            //   212: aload_0         /* this */
            //   213: getf\u0131eld        com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink.this$0:Lcom/squareup/okhttp/internal/framed/FramedStream;
            //   216: \u0131nvokestat\u0131c    com/squareup/okhttp/internal/framed/FramedStream.access$1100:(Lcom/squareup/okhttp/internal/framed/FramedStream;)Lcom/squareup/okhttp/internal/framed/FramedStream$StreamTimeout;
            //   219: \u0131nvokev\u0131rtual   com/squareup/okhttp/internal/framed/FramedStream$StreamTimeout.exitAndThrowIfTimedOut:()V
            //   222: aload           7
            //   224: athrow         
            //   225: return         
            //    Exceptions:
            //  throws java.io.IOException
            //    LocalVariableTable:
            //  Start  Length  Slot  Name         Signature
            //  -----  ------  ----  -----------  -----------------------------------------------------------------
            //  0      226     0     this         Lcom/squareup/okhttp/internal/framed/FramedStream$FramedDataSink;
            //  0      226     1     outFinished  Z
            //  117    109     2     toWrite      J
            //    Exceptions:
            //  Try           Handler
            //  Start  End    Start  End    Type
            //  -----  -----  -----  -----  ----
            //  18     64     77     92     Any
            //  77     79     77     92     Any
            //  8      133    136    144    Any
            //  136    141    136    144    Any
            //  154    197    210    225    Any
            //  210    212    210    225    Any
            // 
            // The error that occurred was:
            // 
            // java.lang.IndexOutOfBoundsException: Index: 4, Size: 4
            //     at java.util.ArrayList.rangeCheck(Unknown Source)
            //     at java.util.ArrayList.get(Unknown Source)
            //     at com.strobel.assembler.Collection.get(Collection.java:43)
            //     at java.util.Collections$UnmodifiableList.get(Unknown Source)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.adjustArgumentsForMethodCallCore(AstMethodBodyBuilder.java:1310)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.adjustArgumentsForMethodCall(AstMethodBodyBuilder.java:1283)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformCall(AstMethodBodyBuilder.java:1195)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:714)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformNode(AstMethodBodyBuilder.java:392)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformBlock(AstMethodBodyBuilder.java:333)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformNode(AstMethodBodyBuilder.java:494)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformBlock(AstMethodBodyBuilder.java:333)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:294)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:556)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
            //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
            //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
            //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:317)
            //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:238)
            //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:123)
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
        
        @Override
        public void flush() throws IOException {
            assert !Thread.holdsLock(FramedStream.this);
            synchronized (FramedStream.this) {
                FramedStream.this.checkOutNotClosed();
            }
            while (this.sendBuffer.size() > 0L) {
                this.emitDataFrame(false);
                FramedStream.this.connection.flush();
            }
        }
        
        @Override
        public Timeout timeout() {
            return FramedStream.this.writeTimeout;
        }
        
        @Override
        public void close() throws IOException {
            assert !Thread.holdsLock(FramedStream.this);
            synchronized (FramedStream.this) {
                if (this.closed) {
                    return;
                }
            }
            if (!FramedStream.this.sink.finished) {
                if (this.sendBuffer.size() > 0L) {
                    while (this.sendBuffer.size() > 0L) {
                        this.emitDataFrame(true);
                    }
                }
                else {
                    FramedStream.this.connection.writeData(FramedStream.this.id, true, null, 0L);
                }
            }
            synchronized (FramedStream.this) {
                this.closed = true;
            }
            FramedStream.this.connection.flush();
            FramedStream.this.cancelStreamIfNecessary();
        }
    }
    
    class StreamTimeout extends AsyncTimeout
    {
        @Override
        protected void timedOut() {
            FramedStream.this.closeLater(ErrorCode.CANCEL);
        }
        
        @Override
        protected IOException newTimeoutException(final IOException cause) {
            final SocketTimeoutException socketTimeoutException = new SocketTimeoutException("timeout");
            if (cause != null) {
                socketTimeoutException.initCause(cause);
            }
            return socketTimeoutException;
        }
        
        public void exitAndThrowIfTimedOut() throws IOException {
            if (this.exit()) {
                throw this.newTimeoutException(null);
            }
        }
    }
}
