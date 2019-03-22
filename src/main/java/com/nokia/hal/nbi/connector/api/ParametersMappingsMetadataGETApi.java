// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector.api;

import com.nokia.hal.nbi.connector.ApiCallback;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.nokia.hal.nbi.connector.ApiResponse;
import com.nokia.hal.nbi.connector.model.ParametersMappingsMetadataResponse;
import com.nokia.hal.nbi.connector.ApiException;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import com.squareup.okhttp.ResponseBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Interceptor;
import java.util.HashMap;
import java.util.Collection;
import com.nokia.hal.nbi.connector.Pair;
import java.util.ArrayList;
import com.squareup.okhttp.Call;
import com.nokia.hal.nbi.connector.ProgressRequestBody;
import com.nokia.hal.nbi.connector.ProgressResponseBody;
import com.nokia.hal.nbi.connector.Configuration;
import com.nokia.hal.nbi.connector.ApiClient;

public class ParametersMappingsMetadataGETApi
{
    private ApiClient apiClient;
    
    public ParametersMappingsMetadataGETApi() {
        this(Configuration.getDefaultApiClient());
    }
    
    public ParametersMappingsMetadataGETApi(final ApiClient apiClient) {
        this.apiClient = apiClient;
    }
    
    public ApiClient getApiClient() {
        return this.apiClient;
    }
    
    public void setApiClient(final ApiClient apiClient) {
        this.apiClient = apiClient;
    }
    
    private Call parametersMappingsMetadataGetCall(final String domain, final String dataModel, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        final Object localVarPostBody = null;
        final String localVarPath = "/parametersMappingsMetadata".replaceAll("\\{format\\}", "json");
        final List<Pair> localVarQueryParams = new ArrayList<Pair>();
        if (domain != null) {
            localVarQueryParams.addAll(this.apiClient.parameterToPairs("", "domain", domain));
        }
        if (dataModel != null) {
            localVarQueryParams.addAll(this.apiClient.parameterToPairs("", "dataModel", dataModel));
        }
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
        return this.apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }
    
    private Call parametersMappingsMetadataGetValidateBeforeCall(final String domain, final String dataModel, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        final Call call = this.parametersMappingsMetadataGetCall(domain, dataModel, progressListener, progressRequestListener);
        return call;
    }
    
    public ParametersMappingsMetadataResponse parametersMappingsMetadataGet(final String domain, final String dataModel) throws ApiException {
        final ApiResponse<ParametersMappingsMetadataResponse> resp = this.parametersMappingsMetadataGetWithHttpInfo(domain, dataModel);
        return resp.getData();
    }
    
    public ApiResponse<ParametersMappingsMetadataResponse> parametersMappingsMetadataGetWithHttpInfo(final String domain, final String dataModel) throws ApiException {
        final Call call = this.parametersMappingsMetadataGetValidateBeforeCall(domain, dataModel, null, null);
        final Type localVarReturnType = new TypeToken<ParametersMappingsMetadataResponse>() {}.getType();
        return this.apiClient.execute(call, localVarReturnType);
    }
    
    public Call parametersMappingsMetadataGetAsync(final String domain, final String dataModel, final ApiCallback<ParametersMappingsMetadataResponse> callback) throws ApiException {
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
        final Call call = this.parametersMappingsMetadataGetValidateBeforeCall(domain, dataModel, progressListener, progressRequestListener);
        final Type localVarReturnType = new TypeToken<ParametersMappingsMetadataResponse>() {}.getType();
        this.apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
}
