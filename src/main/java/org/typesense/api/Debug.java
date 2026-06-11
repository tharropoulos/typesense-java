package org.typesense.api;

import java.util.Map;

public class Debug {

    private ApiCall apiCall;
    public static final String RESOURCEPATH = "/debug";

    public Debug(ApiCall apiCall) {
        this.apiCall = apiCall;
    }

    /**
     * Print debugging information.
     *
     * <p>
     * HTTP: GET /debug
     *
     * @see <a href="https://typesense.org/docs/latest/api/cluster-operations.html#debug">Typesense docs</a>
     */
    public Map<String, Object> retrieve() throws Exception {
        return this.apiCall.get(RESOURCEPATH, null, Map.class);
    }
}
