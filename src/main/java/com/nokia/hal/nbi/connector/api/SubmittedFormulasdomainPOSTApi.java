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
import java.util.Collection;
import com.nokia.hal.nbi.connector.Pair;
import java.util.ArrayList;
import com.squareup.okhttp.Call;
import com.nokia.hal.nbi.connector.ProgressRequestBody;
import com.nokia.hal.nbi.connector.ProgressResponseBody;
import com.nokia.hal.nbi.connector.Configuration;
import com.nokia.hal.nbi.connector.ApiClient;

public class SubmittedFormulasdomainPOSTApi
{
    private ApiClient apiClient;
    
    public SubmittedFormulasdomainPOSTApi() {
        this(Configuration.getDefaultApiClient());
    }
    
    public SubmittedFormulasdomainPOSTApi(final ApiClient apiClient) {
        this.apiClient = apiClient;
    }
    
    public ApiClient getApiClient() {
        return this.apiClient;
    }
    
    public void setApiClient(final ApiClient apiClient) {
        this.apiClient = apiClient;
    }
    
    private Call submittedFormulasDomainPostCall(final String domain, final String flow, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        final Object localVarPostBody = null;
        final String localVarPath = "/submittedFormulas/{domain}".replaceAll("\\{format\\}", "json").replaceAll("\\{domain\\}", this.apiClient.escapeString(domain.toString()));
        final List<Pair> localVarQueryParams = new ArrayList<Pair>();
        if (flow != null) {
            localVarQueryParams.addAll(this.apiClient.parameterToPairs("", "flow", flow));
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
        return this.apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }
    
    private Call submittedFormulasDomainPostValidateBeforeCall(final String domain, final String flow, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        if (domain == null) {
            throw new ApiException("Missing the required parameter 'domain' when calling submittedFormulasDomainPost(Async)");
        }
        final Call call = this.submittedFormulasDomainPostCall(domain, flow, progressListener, progressRequestListener);
        return call;
    }
    
    public void submittedFormulasDomainPost(final String domain, final String flow) throws ApiException {
        this.submittedFormulasDomainPostWithHttpInfo(domain, flow);
    }
    
    public ApiResponse<Void> submittedFormulasDomainPostWithHttpInfo(final String domain, final String flow) throws ApiException {
        final Call call = this.submittedFormulasDomainPostValidateBeforeCall(domain, flow, null, null);
        return this.apiClient.execute(call);
    }
    
    public Call submittedFormulasDomainPostAsync(final String domain, final String flow, final ApiCallback<Void> callback) throws ApiException {
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
        final Call call = this.submittedFormulasDomainPostValidateBeforeCall(domain, flow, progressListener, progressRequestListener);
        this.apiClient.executeAsync(call, callback);
        return call;
    }
}
