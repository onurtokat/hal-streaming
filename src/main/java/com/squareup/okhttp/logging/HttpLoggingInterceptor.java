// 
// Decompiled by Procyon v0.5.30
// 

package com.squareup.okhttp.logging;

import com.squareup.okhttp.internal.Platform;
import java.io.IOException;
import okio.BufferedSource;
import com.squareup.okhttp.ResponseBody;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Connection;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.internal.http.HttpEngine;
import java.util.concurrent.TimeUnit;
import okio.BufferedSink;
import okio.Buffer;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Response;
import java.nio.charset.Charset;
import com.squareup.okhttp.Interceptor;

public final class HttpLoggingInterceptor implements Interceptor
{
    private static final Charset UTF8;
    private final Logger logger;
    private volatile Level level;
    
    public HttpLoggingInterceptor() {
        this(Logger.DEFAULT);
    }
    
    public HttpLoggingInterceptor(final Logger logger) {
        this.level = Level.NONE;
        this.logger = logger;
    }
    
    public HttpLoggingInterceptor setLevel(final Level level) {
        if (level == null) {
            throw new NullPointerException("level == null. Use Level.NONE instead.");
        }
        this.level = level;
        return this;
    }
    
    public Level getLevel() {
        return this.level;
    }
    
    @Override
    public Response intercept(final Chain chain) throws IOException {
        final Level level = this.level;
        final Request request = chain.request();
        if (level == Level.NONE) {
            return chain.proceed(request);
        }
        final boolean logBody = level == Level.BODY;
        final boolean logHeaders = logBody || level == Level.HEADERS;
        final RequestBody requestBody = request.body();
        final boolean hasRequestBody = requestBody != null;
        final Connection connection = chain.connection();
        final Protocol protocol = (connection != null) ? connection.getProtocol() : Protocol.HTTP_1_1;
        String requestStartMessage = "--> " + request.method() + ' ' + request.httpUrl() + ' ' + protocol(protocol);
        if (!logHeaders && hasRequestBody) {
            requestStartMessage = requestStartMessage + " (" + requestBody.contentLength() + "-byte body)";
        }
        this.logger.log(requestStartMessage);
        if (logHeaders) {
            if (hasRequestBody) {
                if (requestBody.contentType() != null) {
                    this.logger.log("Content-Type: " + requestBody.contentType());
                }
                if (requestBody.contentLength() != -1L) {
                    this.logger.log("Content-Length: " + requestBody.contentLength());
                }
            }
            final Headers headers = request.headers();
            for (int i = 0, count = headers.size(); i < count; ++i) {
                final String name = headers.name(i);
                if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                    this.logger.log(name + ": " + headers.value(i));
                }
            }
            if (!logBody || !hasRequestBody) {
                this.logger.log("--> END " + request.method());
            }
            else if (this.bodyEncoded(request.headers())) {
                this.logger.log("--> END " + request.method() + " (encoded body omitted)");
            }
            else {
                final Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);
                final Charset charset = HttpLoggingInterceptor.UTF8;
                final MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    contentType.charset(HttpLoggingInterceptor.UTF8);
                }
                this.logger.log("");
                this.logger.log(buffer.readString(charset));
                this.logger.log("--> END " + request.method() + " (" + requestBody.contentLength() + "-byte body)");
            }
        }
        final long startNs = System.nanoTime();
        final Response response = chain.proceed(request);
        final long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
        final ResponseBody responseBody = response.body();
        this.logger.log("<-- " + protocol(response.protocol()) + ' ' + response.code() + ' ' + response.message() + " (" + tookMs + "ms" + (logHeaders ? "" : (", " + responseBody.contentLength() + "-byte body")) + ')');
        if (logHeaders) {
            final Headers headers2 = response.headers();
            for (int j = 0, count2 = headers2.size(); j < count2; ++j) {
                this.logger.log(headers2.name(j) + ": " + headers2.value(j));
            }
            if (!logBody || !HttpEngine.hasBody(response)) {
                this.logger.log("<-- END HTTP");
            }
            else if (this.bodyEncoded(response.headers())) {
                this.logger.log("<-- END HTTP (encoded body omitted)");
            }
            else {
                final BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE);
                final Buffer buffer2 = source.buffer();
                Charset charset2 = HttpLoggingInterceptor.UTF8;
                final MediaType contentType2 = responseBody.contentType();
                if (contentType2 != null) {
                    charset2 = contentType2.charset(HttpLoggingInterceptor.UTF8);
                }
                if (responseBody.contentLength() != 0L) {
                    this.logger.log("");
                    this.logger.log(buffer2.clone().readString(charset2));
                }
                this.logger.log("<-- END HTTP (" + buffer2.size() + "-byte body)");
            }
        }
        return response;
    }
    
    private boolean bodyEncoded(final Headers headers) {
        final String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }
    
    private static String protocol(final Protocol protocol) {
        return (protocol == Protocol.HTTP_1_0) ? "HTTP/1.0" : "HTTP/1.1";
    }
    
    static {
        UTF8 = Charset.forName("UTF-8");
    }
    
    public enum Level
    {
        NONE, 
        BASIC, 
        HEADERS, 
        BODY;
    }
    
    public interface Logger
    {
        public static final Logger DEFAULT = new Logger() {
            @Override
            public void log(final String message) {
                Platform.get().log(message);
            }
        };
        
        void log(final String p0);
    }
}
