package org.typesense.api;

import java.util.Map;
import org.typesense.api.utils.URLEncoding;

public class Document {
    private String collectionName;
    private String documentId;
    private ApiCall apiCall;
    private final String endpoint;

    Document(String collectionName, String documentId, ApiCall apiCall) {
        this.collectionName = collectionName;
        this.documentId = documentId;
        this.apiCall = apiCall;

        this.endpoint = Collections.RESOURCE_PATH + "/" + URLEncoding.encodeURIComponent(this.collectionName)
                + Documents.RESOURCE_PATH + "/" + URLEncoding.encodeURIComponent(this.documentId);
    }

    /**
     * Retrieve a document.
     *
     * <p>
     * Fetch an individual document from a collection by using its ID.
     *
     * <p>
     * HTTP: GET /collections/{collectionName}/documents/{documentId}
     *
     * @see <a href="https://typesense.org/docs/latest/api/documents.html">Typesense docs</a>
     */
    public Map<String, Object> retrieve() throws Exception {
        return this.apiCall.get(endpoint, null, Map.class);
    }

    /**
     * Delete a document.
     *
     * <p>
     * Delete an individual document from a collection by using its ID.
     *
     * <p>
     * HTTP: DELETE /collections/{collectionName}/documents/{documentId}
     *
     * @see <a href="https://typesense.org/docs/latest/api/documents.html">Typesense docs</a>
     */
    public Map<String, Object> delete() throws Exception {
        return this.apiCall.delete(this.endpoint, null, Map.class);
    }

    /**
     * Update a document.
     *
     * <p>
     * Update an individual document from a collection by using its ID. The update can be partial.
     *
     * <p>
     * HTTP: PATCH /collections/{collectionName}/documents/{documentId}
     *
     * @see <a href="https://typesense.org/docs/latest/api/documents.html">Typesense docs</a>
     */
    public Map<String, Object> update(Map<String, Object> document) throws Exception {
        return this.apiCall.patch(this.endpoint, document, null, Map.class);
    }

}
