package com.labs1904.nifi;

import io.swagger.client.ApiException;
import io.swagger.client.api.AccessApi;
import io.swagger.client.api.CountersApi;
import io.swagger.client.api.SystemdiagnosticsApi;
import io.swagger.client.model.SystemDiagnosticsEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jcreasy
 */
public class NifiClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(NifiClient.class);

    public static int getAvailableProcessors() throws Exception {
        SystemdiagnosticsApi apiInstance = new SystemdiagnosticsApi();
        Boolean nodewise = true; // Boolean | Whether or not to include the breakdown per node. Optional, defaults to false
        apiInstance.getApiClient().setBasePath("http://localhost:8080/nifi-api");
        try {
            SystemDiagnosticsEntity result = apiInstance.getSystemDiagnostics(nodewise, null);
            return result.getSystemDiagnostics().getAggregateSnapshot().getAvailableProcessors();
        } catch (ApiException e) {
            LOGGER.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

    public static String accessNifi() {
        SystemdiagnosticsApi apiInstance = new SystemdiagnosticsApi();
        Boolean nodewise = true; // Boolean | Whether or not to include the breakdown per node. Optional, defaults to false
        apiInstance.getApiClient().setBasePath("http://localhost:8080/nifi-api");
        try {
            SystemDiagnosticsEntity result = apiInstance.getSystemDiagnostics(nodewise, null);
            return result.getSystemDiagnostics().toString();
        } catch (ApiException e) {
            LOGGER.error(e.getMessage(), e);
            return e.getMessage();
        }
    }
}
