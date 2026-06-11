package org.typesense.api;

import java.util.Map;

public class Metrics {

    private ApiCall apiCall;
    public static final String RESOURCEPATH = "/metrics.json";

    public Metrics(ApiCall apiCall) {
        this.apiCall = apiCall;
    }

    /**
     * Get current RAM, CPU, Disk and Network usage metrics.
     *
     * <p>
     * Retrieve the metrics.
     *
     * <p>
     * HTTP: GET /metrics.json
     *
     * @see <a href="https://typesense.org/docs/latest/api/cluster-operations.html">Typesense docs</a>
     */
    public Map<String, String> retrieve() throws Exception {
        return this.apiCall.get(RESOURCEPATH, null, Map.class);
    }
}
