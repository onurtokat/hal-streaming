// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.KeyStore;
import javax.net.ssl.KeyManager;
import java.security.GeneralSecurityException;
import javax.net.ssl.SSLSocketFactory;
import java.security.SecureRandom;
import javax.net.ssl.TrustManagerFactory;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.TrustManager;
import javax.net.ssl.SSLContext;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;
import java.net.URLConnection;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.internal.http.HttpMethod;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Call;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import okio.BufferedSink;
import okio.Source;
import okio.Okio;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import com.squareup.okhttp.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import com.nokia.hal.nbi.connector.auth.OAuth;
import com.nokia.hal.nbi.connector.auth.ApiKeyAuth;
import java.util.Iterator;
import com.nokia.hal.nbi.connector.auth.HttpBasicAuth;
import java.text.ParseException;
import java.util.Date;
import java.util.Collections;
import java.util.TimeZone;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import com.squareup.okhttp.OkHttpClient;
import java.io.InputStream;
import java.text.DateFormat;
import com.nokia.hal.nbi.connector.auth.Authentication;
import java.util.Map;

public class ApiClient
{
    public static final double JAVA_VERSION;
    public static final boolean IS_ANDROID;
    public static final int ANDROID_SDK_VERSION;
    public static final String LENIENT_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    private String basePath;
    private boolean lenientOnJson;
    private boolean debugging;
    private Map<String, String> defaultHeaderMap;
    private String tempFolderPath;
    private Map<String, Authentication> authentications;
    private DateFormat dateFormat;
    private DateFormat datetimeFormat;
    private boolean lenientDatetimeFormat;
    private int dateLength;
    private InputStream sslCaCert;
    private boolean verifyingSsl;
    private OkHttpClient httpClient;
    private JSON json;
    private HttpLoggingInterceptor loggingInterceptor;
    
    public ApiClient() {
        this.basePath = "http://localhost:8081/nbi/metadata";
        this.lenientOnJson = false;
        this.debugging = false;
        this.defaultHeaderMap = new HashMap<String, String>();
        this.tempFolderPath = null;
        this.httpClient = new OkHttpClient();
        this.verifyingSsl = true;
        this.json = new JSON(this);
        (this.dateFormat = new SimpleDateFormat("yyyy-MM-dd")).setTimeZone(TimeZone.getTimeZone("UTC"));
        this.initDatetimeFormat();
        this.lenientDatetimeFormat = true;
        this.setUserAgent("Swagger-Codegen/1.0.0/java");
        this.authentications = new HashMap<String, Authentication>();
        this.authentications = Collections.unmodifiableMap((Map<? extends String, ? extends Authentication>)this.authentications);
    }
    
    public String getBasePath() {
        return this.basePath;
    }
    
    public ApiClient setBasePath(final String basePath) {
        this.basePath = basePath;
        return this;
    }
    
    public OkHttpClient getHttpClient() {
        return this.httpClient;
    }
    
    public ApiClient setHttpClient(final OkHttpClient httpClient) {
        this.httpClient = httpClient;
        return this;
    }
    
    public JSON getJSON() {
        return this.json;
    }
    
    public ApiClient setJSON(final JSON json) {
        this.json = json;
        return this;
    }
    
    public boolean isVerifyingSsl() {
        return this.verifyingSsl;
    }
    
    public ApiClient setVerifyingSsl(final boolean verifyingSsl) {
        this.verifyingSsl = verifyingSsl;
        this.applySslSettings();
        return this;
    }
    
    public InputStream getSslCaCert() {
        return this.sslCaCert;
    }
    
    public ApiClient setSslCaCert(final InputStream sslCaCert) {
        this.sslCaCert = sslCaCert;
        this.applySslSettings();
        return this;
    }
    
    public DateFormat getDateFormat() {
        return this.dateFormat;
    }
    
    public ApiClient setDateFormat(final DateFormat dateFormat) {
        this.dateFormat = dateFormat;
        this.dateLength = this.dateFormat.format(new Date()).length();
        return this;
    }
    
    public DateFormat getDatetimeFormat() {
        return this.datetimeFormat;
    }
    
    public ApiClient setDatetimeFormat(final DateFormat datetimeFormat) {
        this.datetimeFormat = datetimeFormat;
        return this;
    }
    
    public boolean isLenientDatetimeFormat() {
        return this.lenientDatetimeFormat;
    }
    
    public ApiClient setLenientDatetimeFormat(final boolean lenientDatetimeFormat) {
        this.lenientDatetimeFormat = lenientDatetimeFormat;
        return this;
    }
    
    public Date parseDate(final String str) {
        if (str == null) {
            return null;
        }
        try {
            return this.dateFormat.parse(str);
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    
    public Date parseDatetime(String str) {
        if (str == null) {
            return null;
        }
        DateFormat format;
        if (this.lenientDatetimeFormat) {
            str = str.replaceAll("[zZ]\\z", "+0000");
            str = str.replaceAll("([+-]\\d{2}):(\\d{2})\\z", "$1$2");
            str = str.replaceAll("([+-]\\d{2})\\z", "$100");
            str = str.replaceAll("(:\\d{1,2})([+-]\\d{4})\\z", "$1.000$2");
            format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        }
        else {
            format = this.datetimeFormat;
        }
        try {
            return format.parse(str);
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    
    public Date parseDateOrDatetime(final String str) {
        if (str == null) {
            return null;
        }
        if (str.length() <= this.dateLength) {
            return this.parseDate(str);
        }
        return this.parseDatetime(str);
    }
    
    public String formatDate(final Date date) {
        return this.dateFormat.format(date);
    }
    
    public String formatDatetime(final Date date) {
        return this.datetimeFormat.format(date);
    }
    
    public Map<String, Authentication> getAuthentications() {
        return this.authentications;
    }
    
    public Authentication getAuthentication(final String authName) {
        return this.authentications.get(authName);
    }
    
    public void setUsername(final String username) {
        for (final Authentication auth : this.authentications.values()) {
            if (auth instanceof HttpBasicAuth) {
                ((HttpBasicAuth)auth).setUsername(username);
                return;
            }
        }
        throw new RuntimeException("No HTTP basic authentication configured!");
    }
    
    public void setPassword(final String password) {
        for (final Authentication auth : this.authentications.values()) {
            if (auth instanceof HttpBasicAuth) {
                ((HttpBasicAuth)auth).setPassword(password);
                return;
            }
        }
        throw new RuntimeException("No HTTP basic authentication configured!");
    }
    
    public void setApiKey(final String apiKey) {
        for (final Authentication auth : this.authentications.values()) {
            if (auth instanceof ApiKeyAuth) {
                ((ApiKeyAuth)auth).setApiKey(apiKey);
                return;
            }
        }
        throw new RuntimeException("No API key authentication configured!");
    }
    
    public void setApiKeyPrefix(final String apiKeyPrefix) {
        for (final Authentication auth : this.authentications.values()) {
            if (auth instanceof ApiKeyAuth) {
                ((ApiKeyAuth)auth).setApiKeyPrefix(apiKeyPrefix);
                return;
            }
        }
        throw new RuntimeException("No API key authentication configured!");
    }
    
    public void setAccessToken(final String accessToken) {
        for (final Authentication auth : this.authentications.values()) {
            if (auth instanceof OAuth) {
                ((OAuth)auth).setAccessToken(accessToken);
                return;
            }
        }
        throw new RuntimeException("No OAuth2 authentication configured!");
    }
    
    public ApiClient setUserAgent(final String userAgent) {
        this.addDefaultHeader("User-Agent", userAgent);
        return this;
    }
    
    public ApiClient addDefaultHeader(final String key, final String value) {
        this.defaultHeaderMap.put(key, value);
        return this;
    }
    
    public boolean isLenientOnJson() {
        return this.lenientOnJson;
    }
    
    public ApiClient setLenientOnJson(final boolean lenient) {
        this.lenientOnJson = lenient;
        return this;
    }
    
    public boolean isDebugging() {
        return this.debugging;
    }
    
    public ApiClient setDebugging(final boolean debugging) {
        if (debugging != this.debugging) {
            if (debugging) {
                (this.loggingInterceptor = new HttpLoggingInterceptor()).setLevel(HttpLoggingInterceptor.Level.BODY);
                this.httpClient.interceptors().add(this.loggingInterceptor);
            }
            else {
                this.httpClient.interceptors().remove(this.loggingInterceptor);
                this.loggingInterceptor = null;
            }
        }
        this.debugging = debugging;
        return this;
    }
    
    public String getTempFolderPath() {
        return this.tempFolderPath;
    }
    
    public ApiClient setTempFolderPath(final String tempFolderPath) {
        this.tempFolderPath = tempFolderPath;
        return this;
    }
    
    public int getConnectTimeout() {
        return this.httpClient.getConnectTimeout();
    }
    
    public ApiClient setConnectTimeout(final int connectionTimeout) {
        this.httpClient.setConnectTimeout(connectionTimeout, TimeUnit.MILLISECONDS);
        return this;
    }
    
    public String parameterToString(final Object param) {
        if (param == null) {
            return "";
        }
        if (param instanceof Date) {
            return this.formatDatetime((Date)param);
        }
        if (param instanceof Collection) {
            final StringBuilder b = new StringBuilder();
            for (final Object o : (Collection)param) {
                if (b.length() > 0) {
                    b.append(",");
                }
                b.append(String.valueOf(o));
            }
            return b.toString();
        }
        return String.valueOf(param);
    }
    
    public List<Pair> parameterToPairs(String collectionFormat, final String name, final Object value) {
        final List<Pair> params = new ArrayList<Pair>();
        if (name == null || name.isEmpty() || value == null) {
            return params;
        }
        Collection valueCollection = null;
        if (!(value instanceof Collection)) {
            params.add(new Pair(name, this.parameterToString(value)));
            return params;
        }
        valueCollection = (Collection)value;
        if (valueCollection.isEmpty()) {
            return params;
        }
        collectionFormat = ((collectionFormat == null || collectionFormat.isEmpty()) ? "csv" : collectionFormat);
        if (collectionFormat.equals("multi")) {
            for (final Object item : valueCollection) {
                params.add(new Pair(name, this.parameterToString(item)));
            }
            return params;
        }
        String delimiter = ",";
        if (collectionFormat.equals("csv")) {
            delimiter = ",";
        }
        else if (collectionFormat.equals("ssv")) {
            delimiter = " ";
        }
        else if (collectionFormat.equals("tsv")) {
            delimiter = "\t";
        }
        else if (collectionFormat.equals("pipes")) {
            delimiter = "|";
        }
        final StringBuilder sb = new StringBuilder();
        for (final Object item2 : valueCollection) {
            sb.append(delimiter);
            sb.append(this.parameterToString(item2));
        }
        params.add(new Pair(name, sb.substring(1)));
        return params;
    }
    
    public String sanitizeFilename(final String filename) {
        return filename.replaceAll(".*[/\\\\]", "");
    }
    
    public boolean isJsonMime(final String mime) {
        return mime != null && mime.matches("(?i)application\\/json(;.*)?");
    }
    
    public String selectHeaderAccept(final String[] accepts) {
        if (accepts.length == 0) {
            return null;
        }
        for (final String accept : accepts) {
            if (this.isJsonMime(accept)) {
                return accept;
            }
        }
        return StringUtil.join(accepts, ",");
    }
    
    public String selectHeaderContentType(final String[] contentTypes) {
        if (contentTypes.length == 0) {
            return "application/json";
        }
        for (final String contentType : contentTypes) {
            if (this.isJsonMime(contentType)) {
                return contentType;
            }
        }
        return contentTypes[0];
    }
    
    public String escapeString(final String str) {
        try {
            return URLEncoder.encode(str, "utf8").replaceAll("\\+", "%20");
        }
        catch (UnsupportedEncodingException e) {
            return str;
        }
    }
    
    public <T> T deserialize(final Response response, final Type returnType) throws ApiException {
        if (response == null || returnType == null) {
            return null;
        }
        if ("byte[]".equals(returnType.toString())) {
            try {
                return (T)(Object)response.body().bytes();
            }
            catch (IOException e) {
                throw new ApiException(e);
            }
        }
        if (returnType.equals(File.class)) {
            return (T)this.downloadFileFromResponse(response);
        }
        String respBody;
        try {
            if (response.body() != null) {
                respBody = response.body().string();
            }
            else {
                respBody = null;
            }
        }
        catch (IOException e2) {
            throw new ApiException(e2);
        }
        if (respBody == null || "".equals(respBody)) {
            return null;
        }
        String contentType = response.headers().get("Content-Type");
        if (contentType == null) {
            contentType = "application/json";
        }
        if (this.isJsonMime(contentType)) {
            return this.json.deserialize(respBody, returnType);
        }
        if (returnType.equals(String.class)) {
            return (T)respBody;
        }
        throw new ApiException("Content type \"" + contentType + "\" is not supported for type: " + returnType, response.code(), response.headers().toMultimap(), respBody);
    }
    
    public RequestBody serialize(final Object obj, final String contentType) throws ApiException {
        if (obj instanceof byte[]) {
            return RequestBody.create(MediaType.parse(contentType), (byte[])obj);
        }
        if (obj instanceof File) {
            return RequestBody.create(MediaType.parse(contentType), (File)obj);
        }
        if (this.isJsonMime(contentType)) {
            String content;
            if (obj != null) {
                content = this.json.serialize(obj);
            }
            else {
                content = null;
            }
            return RequestBody.create(MediaType.parse(contentType), content);
        }
        throw new ApiException("Content type \"" + contentType + "\" is not supported");
    }
    
    public File downloadFileFromResponse(final Response response) throws ApiException {
        try {
            final File file = this.prepareDownloadFile(response);
            final BufferedSink sink = Okio.buffer(Okio.sink(file));
            sink.writeAll(response.body().source());
            sink.close();
            return file;
        }
        catch (IOException e) {
            throw new ApiException(e);
        }
    }
    
    public File prepareDownloadFile(final Response response) throws IOException {
        String filename = null;
        final String contentDisposition = response.header("Content-Disposition");
        if (contentDisposition != null && !"".equals(contentDisposition)) {
            final Pattern pattern = Pattern.compile("filename=['\"]?([^'\"\\s]+)['\"]?");
            final Matcher matcher = pattern.matcher(contentDisposition);
            if (matcher.find()) {
                filename = this.sanitizeFilename(matcher.group(1));
            }
        }
        String prefix = null;
        String suffix = null;
        if (filename == null) {
            prefix = "download-";
            suffix = "";
        }
        else {
            final int pos = filename.lastIndexOf(".");
            if (pos == -1) {
                prefix = filename + "-";
            }
            else {
                prefix = filename.substring(0, pos) + "-";
                suffix = filename.substring(pos);
            }
            if (prefix.length() < 3) {
                prefix = "download-";
            }
        }
        if (this.tempFolderPath == null) {
            return File.createTempFile(prefix, suffix);
        }
        return File.createTempFile(prefix, suffix, new File(this.tempFolderPath));
    }
    
    public <T> ApiResponse<T> execute(final Call call) throws ApiException {
        return this.execute(call, null);
    }
    
    public <T> ApiResponse<T> execute(final Call call, final Type returnType) throws ApiException {
        try {
            final Response response = call.execute();
            final T data = this.handleResponse(response, returnType);
            return new ApiResponse<T>(response.code(), response.headers().toMultimap(), data);
        }
        catch (IOException e) {
            throw new ApiException(e);
        }
    }
    
    public <T> void executeAsync(final Call call, final ApiCallback<T> callback) {
        this.executeAsync(call, null, callback);
    }
    
    public <T> void executeAsync(final Call call, final Type returnType, final ApiCallback<T> callback) {
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Request request, final IOException e) {
                callback.onFailure(new ApiException(e), 0, null);
            }
            
            @Override
            public void onResponse(final Response response) throws IOException {
                T result;
                try {
                    result = ApiClient.this.handleResponse(response, returnType);
                }
                catch (ApiException e) {
                    callback.onFailure(e, response.code(), response.headers().toMultimap());
                    return;
                }
                callback.onSuccess(result, response.code(), response.headers().toMultimap());
            }
        });
    }
    
    public <T> T handleResponse(final Response response, final Type returnType) throws ApiException {
        if (!response.isSuccessful()) {
            String respBody = null;
            if (response.body() != null) {
                try {
                    respBody = response.body().string();
                }
                catch (IOException e) {
                    throw new ApiException(response.message(), e, response.code(), response.headers().toMultimap());
                }
            }
            throw new ApiException(response.message(), response.code(), response.headers().toMultimap(), respBody);
        }
        if (returnType == null || response.code() == 204) {
            return null;
        }
        return (T)this.deserialize(response, returnType);
    }
    
    public Call buildCall(final String path, final String method, final List<Pair> queryParams, final Object body, final Map<String, String> headerParams, final Map<String, Object> formParams, final String[] authNames, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        this.updateParamsForAuth(authNames, queryParams, headerParams);
        final String url = this.buildUrl(path, queryParams);
        final Request.Builder reqBuilder = new Request.Builder().url(url);
        this.processHeaderParams(headerParams, reqBuilder);
        String contentType = headerParams.get("Content-Type");
        if (contentType == null) {
            contentType = "application/json";
        }
        RequestBody reqBody;
        if (!HttpMethod.permitsRequestBody(method)) {
            reqBody = null;
        }
        else if ("application/x-www-form-urlencoded".equals(contentType)) {
            reqBody = this.buildRequestBodyFormEncoding(formParams);
        }
        else if ("multipart/form-data".equals(contentType)) {
            reqBody = this.buildRequestBodyMultipart(formParams);
        }
        else if (body == null) {
            if ("DELETE".equals(method)) {
                reqBody = null;
            }
            else {
                reqBody = RequestBody.create(MediaType.parse(contentType), "");
            }
        }
        else {
            reqBody = this.serialize(body, contentType);
        }
        Request request = null;
        if (progressRequestListener != null && reqBody != null) {
            final ProgressRequestBody progressRequestBody = new ProgressRequestBody(reqBody, progressRequestListener);
            request = reqBuilder.method(method, progressRequestBody).build();
        }
        else {
            request = reqBuilder.method(method, reqBody).build();
        }
        return this.httpClient.newCall(request);
    }
    
    public String buildUrl(final String path, final List<Pair> queryParams) {
        final StringBuilder url = new StringBuilder();
        url.append(this.basePath).append(path);
        if (queryParams != null && !queryParams.isEmpty()) {
            String prefix = path.contains("?") ? "&" : "?";
            for (final Pair param : queryParams) {
                if (param.getValue() != null) {
                    if (prefix != null) {
                        url.append(prefix);
                        prefix = null;
                    }
                    else {
                        url.append("&");
                    }
                    final String value = this.parameterToString(param.getValue());
                    url.append(this.escapeString(param.getName())).append("=").append(this.escapeString(value));
                }
            }
        }
        return url.toString();
    }
    
    public void processHeaderParams(final Map<String, String> headerParams, final Request.Builder reqBuilder) {
        for (final Map.Entry<String, String> param : headerParams.entrySet()) {
            reqBuilder.header(param.getKey(), this.parameterToString(param.getValue()));
        }
        for (final Map.Entry<String, String> header : this.defaultHeaderMap.entrySet()) {
            if (!headerParams.containsKey(header.getKey())) {
                reqBuilder.header(header.getKey(), this.parameterToString(header.getValue()));
            }
        }
    }
    
    public void updateParamsForAuth(final String[] authNames, final List<Pair> queryParams, final Map<String, String> headerParams) {
        for (final String authName : authNames) {
            final Authentication auth = this.authentications.get(authName);
            if (auth == null) {
                throw new RuntimeException("Authentication undefined: " + authName);
            }
            auth.applyToParams(queryParams, headerParams);
        }
    }
    
    public RequestBody buildRequestBodyFormEncoding(final Map<String, Object> formParams) {
        final FormEncodingBuilder formBuilder = new FormEncodingBuilder();
        for (final Map.Entry<String, Object> param : formParams.entrySet()) {
            formBuilder.add(param.getKey(), this.parameterToString(param.getValue()));
        }
        return formBuilder.build();
    }
    
    public RequestBody buildRequestBodyMultipart(final Map<String, Object> formParams) {
        final MultipartBuilder mpBuilder = new MultipartBuilder().type(MultipartBuilder.FORM);
        for (final Map.Entry<String, Object> param : formParams.entrySet()) {
            if (param.getValue() instanceof File) {
                final File file = param.getValue();
                final Headers partHeaders = Headers.of("Content-Disposition", "form-data; name=\"" + param.getKey() + "\"; filename=\"" + file.getName() + "\"");
                final MediaType mediaType = MediaType.parse(this.guessContentTypeFromFile(file));
                mpBuilder.addPart(partHeaders, RequestBody.create(mediaType, file));
            }
            else {
                final Headers partHeaders2 = Headers.of("Content-Disposition", "form-data; name=\"" + param.getKey() + "\"");
                mpBuilder.addPart(partHeaders2, RequestBody.create(null, this.parameterToString(param.getValue())));
            }
        }
        return mpBuilder.build();
    }
    
    public String guessContentTypeFromFile(final File file) {
        final String contentType = URLConnection.guessContentTypeFromName(file.getName());
        if (contentType == null) {
            return "application/octet-stream";
        }
        return contentType;
    }
    
    private void initDatetimeFormat() {
        String formatWithTimeZone = null;
        if (ApiClient.IS_ANDROID) {
            if (ApiClient.ANDROID_SDK_VERSION >= 18) {
                formatWithTimeZone = "yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ";
            }
        }
        else if (ApiClient.JAVA_VERSION >= 1.7) {
            formatWithTimeZone = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
        }
        if (formatWithTimeZone != null) {
            this.datetimeFormat = new SimpleDateFormat(formatWithTimeZone);
        }
        else {
            (this.datetimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")).setTimeZone(TimeZone.getTimeZone("UTC"));
        }
    }
    
    private void applySslSettings() {
        try {
            final KeyManager[] keyManagers = null;
            TrustManager[] trustManagers = null;
            HostnameVerifier hostnameVerifier = null;
            if (!this.verifyingSsl) {
                final TrustManager trustAll = new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
                    }
                    
                    @Override
                    public void checkServerTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
                    }
                    
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                };
                final SSLContext sslContext = SSLContext.getInstance("TLS");
                trustManagers = new TrustManager[] { trustAll };
                hostnameVerifier = new HostnameVerifier() {
                    @Override
                    public boolean verify(final String hostname, final SSLSession session) {
                        return true;
                    }
                };
            }
            else if (this.sslCaCert != null) {
                final char[] password = null;
                final CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                final Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(this.sslCaCert);
                if (certificates.isEmpty()) {
                    throw new IllegalArgumentException("expected non-empty set of trusted certificates");
                }
                final KeyStore caKeyStore = this.newEmptyKeyStore(password);
                int index = 0;
                for (final Certificate certificate : certificates) {
                    final String certificateAlias = "ca" + Integer.toString(index++);
                    caKeyStore.setCertificateEntry(certificateAlias, certificate);
                }
                final TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                trustManagerFactory.init(caKeyStore);
                trustManagers = trustManagerFactory.getTrustManagers();
            }
            if (keyManagers != null || trustManagers != null) {
                final SSLContext sslContext2 = SSLContext.getInstance("TLS");
                sslContext2.init(keyManagers, trustManagers, new SecureRandom());
                this.httpClient.setSslSocketFactory(sslContext2.getSocketFactory());
            }
            else {
                this.httpClient.setSslSocketFactory(null);
            }
            this.httpClient.setHostnameVerifier(hostnameVerifier);
        }
        catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }
    
    private KeyStore newEmptyKeyStore(final char[] password) throws GeneralSecurityException {
        try {
            final KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, password);
            return keyStore;
        }
        catch (IOException e) {
            throw new AssertionError((Object)e);
        }
    }
    
    static {
        JAVA_VERSION = Double.parseDouble(System.getProperty("java.specification.version"));
        boolean isAndroid;
        try {
            Class.forName("android.app.Activity");
            isAndroid = true;
        }
        catch (ClassNotFoundException e) {
            isAndroid = false;
        }
        IS_ANDROID = isAndroid;
        int sdkVersion = 0;
        if (ApiClient.IS_ANDROID) {
            try {
                sdkVersion = Class.forName("android.os.Build$VERSION").getField("SDK_INT").getInt(null);
            }
            catch (Exception e2) {
                try {
                    sdkVersion = Integer.parseInt((String)Class.forName("android.os.Build$VERSION").getField("SDK").get(null));
                }
                catch (Exception ex) {}
            }
        }
        ANDROID_SDK_VERSION = sdkVersion;
    }
}
