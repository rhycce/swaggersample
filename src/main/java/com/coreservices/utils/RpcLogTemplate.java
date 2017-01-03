package com.coreservices.utils;

import java.util.Map;

/**
 * Created by janet on 1/3/17.
 */
public class RpcLogTemplate {
    private final String localhost;
    private final String remoteHost;
    private final String client;
    private final String serviceInstance;
    private final Map<String, String[]> params;
    private final String clientapp;
    private final String requestUri;
    private final String serviceApp;
    private final long requestTimestamp;
    private long responseTimestamp;
    private String responseCode;

    public RpcLogTemplate(String remoteHost, String client, String clientapp, String localhost, String serviceInstance, String serviceApp, Map<String, String[]> parameterMap, String requestURI, long time) {
        this.remoteHost = remoteHost;
        this.client = client;
        this.localhost = localhost;
        this.serviceInstance = serviceInstance;
        this.clientapp = clientapp;
        this.params = parameterMap;
        this.serviceApp = serviceApp;
        this.requestUri = requestURI;
        this.requestTimestamp = time;

    }

    public void setResponseTimestamp(long responseTimestamp) {
        this.responseTimestamp = responseTimestamp;
    }

    public long getResponseTimestamp() {
        return responseTimestamp;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseCode() {
        return responseCode;
    }
}
