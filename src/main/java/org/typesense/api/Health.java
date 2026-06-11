package org.typesense.api;

import java.util.Map;

public class Health {

    private ApiCall apiCall;
    public static final String RESOURCEPATH = "/health";

    public Health(ApiCall apiCall) {
        this.apiCall = apiCall;
    }

    /**
     * Checks if Typesense server is ready to accept requests.
     *
     * <p>
     * HTTP: GET /health
     *
     * @see <a href="https://typesense.org/docs/latest/api/cluster-operations.html#health">Typesense docs</a>
     */
    public Map<String, Object> retrieve() throws Exception {
        return this.apiCall.get(RESOURCEPATH, null, Map.class);
    }
}
