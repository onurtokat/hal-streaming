// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector.api;

import com.nokia.hal.nbi.connector.ApiCallback;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.nokia.hal.nbi.connector.ApiResponse;
import com.nokia.hal.nbi.connector.model.InsightsMetadataItem;
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
import com.nokia.hal.nbi.connector.model.NewInsightsMetadataItem;
import com.nokia.hal.nbi.connector.Configuration;
import com.nokia.hal.nbi.connector.ApiClient;

public class InsightsMetadataPOSTApi
{
    private ApiClient apiClient;
    
    public InsightsMetadataPOSTApi() {
        this(Configuration.getDefaultApiClient());
    }
    
    public InsightsMetadataPOSTApi(final ApiClient apiClient) {
        this.apiClient = apiClient;
    }
    
    public ApiClient getApiClient() {
        return this.apiClient;
    }
    
    public void setApiClient(final ApiClient apiClient) {
        this.apiClient = apiClient;
    }
    
    private Call insightsMetadataPostCall(final NewInsightsMetadataItem insightsMetadata, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        final String localVarPath = "/insightsMetadata".replaceAll("\\{format\\}", "json");
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
        return this.apiClient.buildCall(localVarPath, "POST", localVarQueryParams, insightsMetadata, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }
    
    private Call insightsMetadataPostValidateBeforeCall(final NewInsightsMetadataItem insightsMetadata, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        if (insightsMetadata == null) {
            throw new ApiException("Missing the required parameter 'insightsMetadata' when calling insightsMetadataPost(Async)");
        }
        final Call call = this.insightsMetadataPostCall(insightsMetadata, progressListener, progressRequestListener);
        return call;
    }
    
    public InsightsMetadataItem insightsMetadataPost(final NewInsightsMetadataItem insightsMetadata) throws ApiException {
        final ApiResponse<InsightsMetadataItem> resp = this.insightsMetadataPostWithHttpInfo(insightsMetadata);
        return resp.getData();
    }
    
    public ApiResponse<InsightsMetadataItem> insightsMetadataPostWithHttpInfo(final NewInsightsMetadataItem insightsMetadata) throws ApiException {
        final Call call = this.insightsMetadataPostValidateBeforeCall(insightsMetadata, null, null);
        final Type localVarReturnType = new TypeToken<InsightsMetadataItem>() {}.getType();
        return this.apiClient.execute(call, localVarReturnType);
    }
    
    public Call insightsMetadataPostAsync(final NewInsightsMetadataItem insightsMetadata, final ApiCallback<InsightsMetadataItem> callback) throws ApiException {
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
        final Call call = this.insightsMetadataPostValidateBeforeCall(insightsMetadata, progressListener, progressRequestListener);
        final Type localVarReturnType = new TypeToken<InsightsMetadataItem>() {}.getType();
        this.apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
}
