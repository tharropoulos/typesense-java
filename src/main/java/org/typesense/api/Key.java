package org.typesense.api;

import org.typesense.model.ApiKey;

public class Key {

    private Long id;
    private ApiCall apiCall;

    public Key(Long id, ApiCall apiCall) {
        this.id = id;
        this.apiCall = apiCall;
    }

    /**
     * Retrieve (metadata about) a key.
     *
     * <p>
     * Retrieve (metadata about) a key. Only the key prefix is returned when you retrieve a key. Due to security reasons, only the create endpoint returns the full API key.
     *
     * <p>
     * HTTP: GET /keys/{keyId}
     *
     * @see <a href="https://typesense.org/docs/latest/api/api-keys.html">Typesense docs</a>
     */
    public ApiKey retrieve() throws Exception {
        return this.apiCall.get(this.getEndpoint(), null, ApiKey.class);
    }

    /**
     * Delete an API key given its ID.
     *
     * <p>
     * HTTP: DELETE /keys/{keyId}
     *
     * @see <a href="https://typesense.org/docs/latest/api/api-keys.html">Typesense docs</a>
     */
    public ApiKey delete() throws Exception {
        return this.apiCall.delete(this.getEndpoint(), null, ApiKey.class);
    }

    private String getEndpoint(){
        return Keys.RESOURCEPATH + "/" + this.id;
    }
}
