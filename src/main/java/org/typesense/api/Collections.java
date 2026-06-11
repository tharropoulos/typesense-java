package org.typesense.api;

import org.typesense.model.CollectionResponse;
import org.typesense.model.CollectionSchema;

public class Collections {

    private final ApiCall apiCall;
    public final static String RESOURCE_PATH = "/collections";

    public Collections(ApiCall apiCall){
        this.apiCall = apiCall;
    }

    /**
     * Create a new collection.
     *
     * <p>
     * When a collection is created, we give it a name and describe the fields that will be indexed from the documents added to the collection.
     *
     * <p>
     * HTTP: POST /collections
     *
     * @see <a href="https://typesense.org/docs/latest/api/collections.html">Typesense docs</a>
     */
    public CollectionResponse create(CollectionSchema c) throws Exception {
        return this.apiCall.post(RESOURCE_PATH, c, null, CollectionResponse.class);
    }

    /**
     * Create a new collection.
     *
     * <p>
     * When a collection is created, we give it a name and describe the fields that will be indexed from the documents added to the collection.
     *
     * <p>
     * HTTP: POST /collections
     *
     * @see <a href="https://typesense.org/docs/latest/api/collections.html">Typesense docs</a>
     */
    public CollectionResponse create(String schemaJson) throws Exception {
        return this.apiCall.post(RESOURCE_PATH, schemaJson, null, CollectionResponse.class);
    }

    /**
     * List all collections.
     *
     * <p>
     * Returns a summary of all your collections. The collections are returned sorted by creation date, with the most recent collections appearing first.
     *
     * <p>
     * HTTP: GET /collections
     *
     * @see <a href="https://typesense.org/docs/latest/api/collections.html">Typesense docs</a>
     */
    public CollectionResponse[] retrieve() throws Exception {
        return this.apiCall.get(RESOURCE_PATH, null, CollectionResponse[].class);
    }

}
