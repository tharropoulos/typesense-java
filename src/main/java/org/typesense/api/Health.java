package org.typesense.api;

import org.typesense.model.HealthStatus;

public class Health {

    private ApiCall apiCall;
    public static final String RESOURCEPATH = "/health";

    public Health(ApiCall apiCall) {
        this.apiCall = apiCall;
    }

    public HealthStatus retrieve() throws Exception {
        return this.apiCall.get(RESOURCEPATH, null, HealthStatus.class);
    }
}
