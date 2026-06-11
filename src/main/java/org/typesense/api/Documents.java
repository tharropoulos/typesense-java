package org.typesense.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.typesense.api.utils.URLEncoding;
import org.typesense.model.DeleteDocumentsParameters;
import org.typesense.model.ExportDocumentsParameters;
import org.typesense.model.ImportDocumentsParameters;
import org.typesense.model.IndexAction;
import org.typesense.model.SearchParameters;
import org.typesense.model.SearchResult;
import org.typesense.model.UpdateDocumentsParameters;

public class Documents {

    private String collectionName;
    private ApiCall apiCall;
    private Configuration configuration;

    public static final String RESOURCE_PATH = "/documents";

    Documents(String collectionName, ApiCall apiCall, Configuration configuration) {
        this.collectionName = collectionName;
        this.apiCall = apiCall;
        this.configuration = configuration;
    }

    /**
     * Index a document.
     *
     * <p>
     * A document to be indexed in a given collection must conform to the schema of the collection.
     *
     * <p>
     * HTTP: POST /collections/{collectionName}/documents
     *
     * @see <a href="https://typesense.org/docs/latest/api/documents.html">Typesense docs</a>
     */
    public Map<String, Object> create(Map<String, Object> document) throws Exception {
        return this.apiCall.post(getEndPoint("/"), document, null, Map.class);
    }

    /**
     * Index a document.
     *
     * <p>
     * A document to be indexed in a given collection must conform to the schema of the collection.
     *
     * <p>
     * HTTP: POST /collections/{collectionName}/documents
     *
     * @see <a href="https://typesense.org/docs/latest/api/documents.html">Typesense docs</a>
     */
    public String create(String document) throws Exception {
        return this.apiCall.post(getEndPoint("/"), document, null, String.class);
    }

    /**
     * Index a document.
     *
     * <p>
     * A document to be indexed in a given collection must conform to the schema of the collection.
     *
     * <p>
     * HTTP: POST /collections/{collectionName}/documents
     *
     * @see <a href="https://typesense.org/docs/latest/api/documents.html">Typesense docs</a>
     */
    public String create(Map<String, Object> document, ImportDocumentsParameters queryParameters) throws Exception {
        return this.apiCall.post(getEndPoint("/"), document, queryParameters, String.class);
    }

    /**
     * Index a document.
     *
     * <p>
     * A document to be indexed in a given collection must conform to the schema of the collection.
     *
     * <p>
     * HTTP: POST /collections/{collectionName}/documents
     *
     * @see <a href="https://typesense.org/docs/latest/api/documents.html">Typesense docs</a>
     */
    public Map<String, Object> upsert(Map<String, Object> document) throws Exception {
        ImportDocumentsParameters queryParameters = new ImportDocumentsParameters();
        queryParameters.action(IndexAction.UPSERT);

        return this.apiCall.post(getEndPoint("/"), document, queryParameters, Map.class);
    }

    /**
     * Search for documents in a collection.
     *
     * <p>
     * Search for documents in a collection that match the search criteria.
     *
     * <p>
     * HTTP: GET /collections/{collectionName}/documents/search
     *
     * @see <a href="https://typesense.org/docs/latest/api/documents.html">Typesense docs</a>
     */
    public SearchResult search(SearchParameters searchParameters) throws Exception {
        return this.apiCall.get(getEndPoint("search"), searchParameters, org.typesense.model.SearchResult.class);
    }

    /**
     * Delete a bunch of documents.
     *
     * <p>
     * Delete a bunch of documents that match a specific filter condition. Use the `batch_size` parameter to control the number of documents that should deleted at a time. A larger value will speed up deletions, but will impact performance of other operations running on the server.
     *
     * <p>
     * HTTP: DELETE /collections/{collectionName}/documents
     *
     * @see <a href="https://typesense.org/docs/latest/api/documents.html">Typesense docs</a>
     */
    public Map<String, Object> delete(DeleteDocumentsParameters queryParameters) throws Exception {
        return this.apiCall.delete(getEndPoint("/"), queryParameters, Map.class);
    }

    /**
     * Export all documents in a collection.
     *
     * <p>
     * Export all documents in a collection in JSON lines format.
     *
     * <p>
     * HTTP: GET /collections/{collectionName}/documents/export
     *
     * @see <a href="https://typesense.org/docs/latest/api/documents.html">Typesense docs</a>
     */
    public String export() throws Exception {
        return this.apiCall.get(getEndPoint("export"), null, String.class);
    }

    /**
     * Export all documents in a collection.
     *
     * <p>
     * Export all documents in a collection in JSON lines format.
     *
     * <p>
     * HTTP: GET /collections/{collectionName}/documents/export
     *
     * @see <a href="https://typesense.org/docs/latest/api/documents.html">Typesense docs</a>
     */
    public String export(ExportDocumentsParameters exportDocumentsParameters) throws Exception {
        return this.apiCall.get(getEndPoint("export"), exportDocumentsParameters, String.class);
    }

    /**
     * Import documents into a collection.
     *
     * <p>
     * The documents to be imported must be formatted in a newline delimited JSON structure. You can feed the output file from a Typesense export operation directly as import.
     *
     * <p>
     * HTTP: POST /collections/{collectionName}/documents/import
     *
     * @see <a href="https://typesense.org/docs/latest/api/documents.html">Typesense docs</a>
     */
    public String import_(String document, ImportDocumentsParameters queryParameters) throws Exception {
        return this.apiCall.post(this.getEndPoint("import"), document, queryParameters, String.class);
    }

    /**
     * Import documents into a collection.
     *
     * <p>
     * The documents to be imported must be formatted in a newline delimited JSON structure. You can feed the output file from a Typesense export operation directly as import.
     *
     * <p>
     * HTTP: POST /collections/{collectionName}/documents/import
     *
     * @see <a href="https://typesense.org/docs/latest/api/documents.html">Typesense docs</a>
     */
    public String import_(Collection<?> documents,
            ImportDocumentsParameters queryParameters) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List<String> jsonLines = new ArrayList<>();

        for (Object document : documents) {
            try {
                jsonLines.add(mapper.writeValueAsString(document));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String reqBody = String.join("\n", jsonLines);
        return this.apiCall.post(this.getEndPoint("import"), reqBody, queryParameters, String.class);
    }

    /**
     * <a href=
     * "https://typesense.org/docs/0.25.1/api/documents.html#update-by-query">Update
     * by query</a>
     *
     * @param document                  - Document
     * @param updateDocumentsParameters - {@link UpdateDocumentsParameters}
     * @return
     * @throws Exception
     */
    public Map<String, Integer> update(Map<String, Object> document,
            UpdateDocumentsParameters updateDocumentsParameters) throws Exception {
        return this.apiCall.patch(this.getEndPoint("/"), document, updateDocumentsParameters, Map.class);
    }

    public String getEndPoint(String target) {
        return Collections.RESOURCE_PATH + "/" + URLEncoding.encodeURIComponent(this.collectionName)
                + Documents.RESOURCE_PATH + "/" + target;
    }
}
