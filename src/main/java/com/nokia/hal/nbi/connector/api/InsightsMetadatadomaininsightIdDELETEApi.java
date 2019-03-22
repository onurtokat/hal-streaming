// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector.api;

import com.nokia.hal.nbi.connector.ApiCallback;
import com.nokia.hal.nbi.connector.ApiResponse;
import com.nokia.hal.nbi.connector.ApiException;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import com.squareup.okhttp.ResponseBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Interceptor;
import java.util.HashMap;
import com.nokia.hal.nbi.connector.Pair;
import java.util.ArrayList;
import com.squareup.okhttp.Call;
import com.nokia.hal.nbi.connector.ProgressRequestBody;
import com.nokia.hal.nbi.connector.ProgressResponseBody;
import com.nokia.hal.nbi.connector.Configuration;
import com.nokia.hal.nbi.connector.ApiClient;

public class InsightsMetadatadomaininsightIdDELETEApi
{
    private ApiClient apiClient;
    
    public InsightsMetadatadomaininsightIdDELETEApi() {
        this(Configuration.getDefaultApiClient());
    }
    
    public InsightsMetadatadomaininsightIdDELETEApi(final ApiClient apiClient) {
        this.apiClient = apiClient;
    }
    
    public ApiClient getApiClient() {
        return this.apiClient;
    }
    
    public void setApiClient(final ApiClient apiClient) {
        this.apiClient = apiClient;
    }
    
    private Call insightsMetadataDomainInsightIdDeleteCall(final String insightId, final String domain, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        final Object localVarPostBody = null;
        final String localVarPath = "/insightsMetadata/{domain}/{insightId}".replaceAll("\\{format\\}", "json").replaceAll("\\{insightId\\}", this.apiClient.escapeString(insightId.toString())).replaceAll("\\{domain\\}", this.apiClient.escapeString(domain.toString()));
        final List<Pair> localVarQueryParams = new ArrayList<Pair>();
        final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        final Map<String, Object> localVarFormParams = new HashMap<String, Object>();
        final String[] localVarAccepts = { "application/json" };
        final String localVarAccept = this.apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }
        final String[] localVarContentTypes = new String[0];
        final String localVarContentType = this.apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);
        if (progressListener != null) {
            this.apiClient.getHttpClient().networkInterceptors().add(new Interceptor() {
                @Override
                public Response intercept(final Chain chain) throws IOException {
                    final Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder().body(new ProgressResponseBody(originalResponse.body(), progressListener)).build();
                }
            });
        }
        final String[] localVarAuthNames = new String[0];
        return this.apiClient.buildCall(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }
    
    private Call insightsMetadataDomainInsightIdDeleteValidateBeforeCall(final String insightId, final String domain, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        if (insightId == null) {
            throw new ApiException("Missing the required parameter 'insightId' when calling insightsMetadataDomainInsightIdDelete(Async)");
        }
        if (domain == null) {
            throw new ApiException("Missing the required parameter 'domain' when calling insightsMetadataDomainInsightIdDelete(Async)");
        }
        final Call call = this.insightsMetadataDomainInsightIdDeleteCall(insightId, domain, progressListener, progressRequestListener);
        return call;
    }
    
    public void insightsMetadataDomainInsightIdDelete(final String insightId, final String domain) throws ApiException {
        this.insightsMetadataDomainInsightIdDeleteWithHttpInfo(insightId, domain);
    }
    
    public ApiResponse<Void> insightsMetadataDomainInsightIdDeleteWithHttpInfo(final String insightId, final String domain) throws ApiException {
        final Call call = this.insightsMetadataDomainInsightIdDeleteValidateBeforeCall(insightId, domain, null, null);
        return this.apiClient.execute(call);
    }
    
    public Call insightsMetadataDomainInsightIdDeleteAsync(final String insightId, final String domain, final ApiCallback<Void> callback) throws ApiException {
        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;
        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(final long bytesRead, final long contentLength, final boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };
            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(final long bytesWritten, final long contentLength, final boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }
        final Call call = this.insightsMetadataDomainInsightIdDeleteValidateBeforeCall(insightId, domain, progressListener, progressRequestListener);
        this.apiClient.executeAsync(call, callback);
        return call;
    }
}
